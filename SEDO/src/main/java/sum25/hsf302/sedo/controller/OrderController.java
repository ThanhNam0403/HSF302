package sum25.hsf302.sedo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sum25.hsf302.sedo.file_enum.OrderStatus;
import sum25.hsf302.sedo.file_enum.PaymentMethod;
import sum25.hsf302.sedo.pojo.*;
import sum25.hsf302.sedo.service.CartDetailService;
import sum25.hsf302.sedo.service.CartService;
import sum25.hsf302.sedo.service.OrderDetailService;
import sum25.hsf302.sedo.service.OrderService;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartDetailService cartDetailService;

    @GetMapping("/my")
    public String viewMyOrders(Model model, Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        List<Order> orders = orderService.getOrdersByCustomerId(userId);
        model.addAttribute("orders", orders);
        return "my-orders";
    }

    @GetMapping("/admin")
    public String viewAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "all-orders";
    }

    @PostMapping("/status/{id}")
    public String updateOrderStatus(@PathVariable Long id) {
        orderService.updateOrderStatusById(id);
        return "redirect:/admin";
    }

    @PostMapping("/confirm")
    public String confirmOrder(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); //
        if (user == null) {
            return "redirect:/login";
        }

        // Lấy cart và cart detail qua service
        Cart cart = cartService.getCartByUser(user);
        List<CartDetail> cartDetails = cartDetailService.getCartDetailsByCart(cart);
        if (cartDetails == null || cartDetails.isEmpty()) {
            return "redirect:/cart";
        }

        // Tính tổng tiền
        BigDecimal totalAmount = cartDetails.stream()
                .map(cd -> cd.getPriceAtAddToCart().multiply(BigDecimal.valueOf(cd.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Tạo Order
        Order order = new Order();
        order.setCustomer(user);
        order.setShippingAddress(user.getAddress()); // Giả định User có address
        order.setTotalAmount(totalAmount);
        Order savedOrder = orderService.createOrder(order);

        // Tạo các OrderDetail từ CartDetail
        for (CartDetail cd : cartDetails) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(savedOrder);
            detail.setComputer(cd.getComputer());
            detail.setVariant(cd.getVariant());
            detail.setQuantity(cd.getQuantity());
            detail.setUnitPrice(cd.getPriceAtAddToCart());
            orderDetailService.save(detail);
        }

        // Xoá cart sau khi đặt hàng
        cartDetailService.clearCartDetails(cart);

        // Truyền thông tin sang trang xác nhận
        model.addAttribute("order", savedOrder);
        model.addAttribute("orderDetails", orderDetailService.findByOrderId(savedOrder.getId()));
        return "order-confirmation";
    }

    @GetMapping("/checkout")
    public String showOrderCheckoutPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Cart cart = cartService.getCartByUser(user);
        List<CartDetail> cartDetails = cartDetailService.getCartDetailsByCart(cart);

        BigDecimal subtotal = cartDetails.stream()
                .map(cd -> cd.getPriceAtAddToCart().multiply(BigDecimal.valueOf(cd.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal tax = subtotal.multiply(BigDecimal.valueOf(0.1));
        BigDecimal shipping = BigDecimal.ZERO;
        BigDecimal total = subtotal.add(tax).add(shipping);

        model.addAttribute("cartItems", cartDetails);
        model.addAttribute("cartSubtotal", subtotal);
        model.addAttribute("taxAmount", tax);
        model.addAttribute("shippingCost", shipping);
        model.addAttribute("cartTotal", total);
        model.addAttribute("shippingAddress", user.getAddress());
        return "order"; // order.html
    }

    @PostMapping("/checkout-selected")
    @ResponseBody
    public Map<String, Object> checkoutSelected(@RequestBody Map<String, List<Long>> payload, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.put("success", false);
            response.put("message", "Not logged in");
            return response;
        }

        List<Long> selectedItemIds = payload.get("itemIds");
        session.setAttribute("checkoutItemIds", selectedItemIds);

        response.put("success", true);
        response.put("redirectUrl", "/cart/checkout");
        return response;
    }

    @PostMapping("/confirm-order")
    public String confirmSelectedOrder(@RequestParam("selectedItemIds") String itemIdsStr,
                                       HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null || itemIdsStr == null || itemIdsStr.isBlank()) return "redirect:/login";

        List<Long> selectedIds = Arrays.stream(itemIdsStr.split(","))
                .map(String::trim).filter(s -> !s.isEmpty())
                .map(Long::parseLong).toList();

        List<CartDetail> selectedItems = cartDetailService.getCartDetailsByIds(selectedIds);
        if (selectedItems.isEmpty()) return "redirect:/cart";

        BigDecimal totalAmount = selectedItems.stream()
                .map(CartDetail::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setCustomer(user);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentMethod(PaymentMethod.COD);
        orderService.createOrder(order);

        for (CartDetail cd : selectedItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setComputer(cd.getComputer());
            detail.setQuantity(cd.getQuantity());
            detail.setUnitPrice(cd.getPriceAtAddToCart());
            orderDetailService.save(detail);
        }

        cartDetailService.removeCartDetailsByIds(selectedIds, user);
        redirectAttributes.addFlashAttribute("order", order);
        return "redirect:/order-success";
    }

    @GetMapping("/order-success")
    public String showOrderSuccessPage() {
        return "order-confirmation";
    }

    @GetMapping("/history")
    public String viewOrderHistory(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<Order> orders = orderService.getOrdersByCustomerId(user.getId());
        model.addAttribute("orders", orders);
        return "order-history";
    }

    @GetMapping("/details/{orderId}")
    public String viewOrderDetails(@PathVariable Long orderId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Order order = orderService.getOrderById(orderId);
        if (order == null || !order.getCustomer().getId().equals(user.getId())) {
            return "redirect:/orders/history";
        }

        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
        model.addAttribute("order", order);
        model.addAttribute("orderDetails", orderDetails);
        return "order-detail";
    }

}