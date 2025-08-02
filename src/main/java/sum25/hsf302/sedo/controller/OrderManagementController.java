package sum25.hsf302.sedo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sum25.hsf302.sedo.file_enum.OrderStatus;
import sum25.hsf302.sedo.pojo.ComputerDevice;
import sum25.hsf302.sedo.pojo.Order;
import sum25.hsf302.sedo.pojo.OrderDetail;
import sum25.hsf302.sedo.pojo.User;
import sum25.hsf302.sedo.service.ComputerDeviceService;
import sum25.hsf302.sedo.service.OrderDetailServiceImpl;
import sum25.hsf302.sedo.service.OrderService;

import java.util.List;

@Controller
public class OrderManagementController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailServiceImpl orderDetailService;

    @Autowired
    private ComputerDeviceService computerDeviceService;

    @GetMapping("/admin/orders")
    public String listOrders(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "status", required = false) OrderStatus status,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole().getRoleName())) {
            return "redirect:/login";
        }
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderService.searchAndFilterOrders(search, status, pageable);
        model.addAttribute("orderPage", orderPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());
        model.addAttribute("statuses", OrderStatus.values());
        model.addAttribute("search", search);
        model.addAttribute("selectedStatus", status);
        return "order-management";
    }

    @GetMapping("/admin/orders/view/{id}")
    public String viewOrder(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole().getRoleName())) {
            return "redirect:/login";
        }
        Order order = orderService.findById(id);
        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(id);
        model.addAttribute("order", order);
        model.addAttribute("orderDetails", orderDetails);
        return "order-details-admin";
    }

    // Xử lý POST xóa đơn hàng
    @PostMapping("/admin/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole().getRoleName())) {
            return "redirect:/login";
        }
        try {
            orderService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Deleted order successfully!");
        } catch (Exception e) {
            System.out.println("Error deleting order: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Failed to delete order.");
        }
        return "redirect:/admin/orders";
    }

    // Hiển thị form edit order
    @GetMapping("/admin/orders/edit/{id}")
    public String showEditOrder(@PathVariable("id") Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole().getRoleName())) {
            return "redirect:/login";
        }
        Order order = orderService.findById(id);
        if (order == null) {
            return "redirect:/admin/orders"; // hoặc báo lỗi
        }
        model.addAttribute("order", order);
        // Nếu có enum status thì truyền sang luôn
        model.addAttribute("statuses", OrderStatus.values());
        return "order-edit"; // file Thymeleaf cho form edit
    }

    // Xử lý POST update order
    @PostMapping("/admin/orders/edit")
    public String updateOrder(@ModelAttribute("order") Order order, RedirectAttributes redirectAttributes, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole().getRoleName())) {
            return "redirect:/login";
        }
        try {
            // Lấy lại Order cũ từ DB
            Order existingOrder = orderService.findById(order.getId());
            if (existingOrder == null) {
                redirectAttributes.addFlashAttribute("error", "Order not found!");
                return "redirect:/admin/orders";
            }
            // Chỉ cập nhật các field cho phép
            System.out.println("Updating order with ID: " + order.getId());
            // Nếu chuyển sang DELIVERY -> trừ tồn kho
            System.out.println(order.getStatus() + " vs " + existingOrder.getStatus());
            if (order.getStatus().equals(OrderStatus.DELIVERY) && !existingOrder.getStatus().equals(OrderStatus.DELIVERY)) {
                System.out.println("Order status changed to DELIVERY, updating stock...");
                List<OrderDetail> orderDetails = orderDetailService.findByOrder(existingOrder);
                System.out.println(orderDetails != null ? orderDetails.size() : null);
                for (OrderDetail item : orderDetails) {
                    ComputerDevice device = item.getComputer();
                    int newStock = device.getStockQuantity() - item.getQuantity();
                    if (newStock < 0) newStock = 0; // Không cho âm
                    device.setStockQuantity(newStock);
                    computerDeviceService.save(device);
                }
            }
            existingOrder.setStatus(order.getStatus());
            existingOrder.setShippingAddress(order.getShippingAddress());

            orderService.update(existingOrder);

            redirectAttributes.addFlashAttribute("success", "Order updated successfully!");
        } catch (Exception e) {
            System.out.println("Error updating order: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Failed to update order.");
        }
        return "redirect:/admin/orders";
    }
}
