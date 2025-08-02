package sum25.hsf302.sedo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sum25.hsf302.sedo.file_enum.OrderStatus;
import sum25.hsf302.sedo.pojo.Order;
import sum25.hsf302.sedo.pojo.User;
import sum25.hsf302.sedo.service.ComputerDeviceService;
import sum25.hsf302.sedo.service.OrderService;
import sum25.hsf302.sedo.service.UserService;

@Controller
public class AdminDashboardController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ComputerDeviceService computerDeviceService;
    @Autowired
    private UserService userService;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model, HttpSession session, @RequestParam(value = "page", defaultValue = "0") int page // Page bắt đầu từ 0
    ) {

        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole().getRoleName())) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);

        int size = 10; // số order/trang

        Page<Order> orderPage = orderService.findAllByOrderByCreatedAtDesc(page, size);

        // Tổng sản phẩm
        long totalProducts = computerDeviceService.countActiveDevices();
        // Tổng đơn hàng
        long totalOrders = orderService.countAll();
        // Tổng người dùng
        long totalUsers = userService.countActiveUsers();
        // Tổng doanh thu (chỉ lấy đơn đã giao)
        Double totalRevenue = orderService.sumTotalAmountByStatus(OrderStatus.DELIVERY); // bạn nên để trả về Double
        // Tổng đơn hàng đã giao
        long deliveredOrders = orderService.countAllByStatusEquals(OrderStatus.DELIVERY);


        // Truyền dữ liệu cho view
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalRevenue", totalRevenue == null ? 0 : totalRevenue);
        model.addAttribute("deliveredOrders", deliveredOrders);

        // Truyền page và các biến phân trang
        model.addAttribute("orderPage", orderPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());

        return "admin-dashboard";
    }
}
