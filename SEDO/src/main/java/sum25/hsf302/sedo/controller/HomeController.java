package sum25.hsf302.sedo.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sum25.hsf302.sedo.pojo.Role;
import sum25.hsf302.sedo.pojo.User;
import sum25.hsf302.sedo.pojo.ComputerDevice;
import sum25.hsf302.sedo.pojo.Category;
import sum25.hsf302.sedo.service.RoleService;
import sum25.hsf302.sedo.service.UserService;
import sum25.hsf302.sedo.service.ComputerDeviceService;
import sum25.hsf302.sedo.service.CategoryService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private ComputerDeviceService computerDeviceService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/SEDO")
    public String homePage(@RequestParam(defaultValue = "0") int page,
                           Model model) {
        int pageSize = 9;

        Page<ComputerDevice> featuredPage = computerDeviceService.findFeaturedProductsPaginated(PageRequest.of(page, pageSize));
        List<Category> categories = categoryService.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("featuredProducts", featuredPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", featuredPage.getTotalPages());

        return "homepage";
    }



    @GetMapping("/login")
    public String loginPage(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("rememberedEmail".equals(cookie.getName())) {
                    model.addAttribute("rememberedEmail", cookie.getValue());
                    break;
                }
            }
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        @RequestParam(required = false) String remember,
                        HttpServletResponse response,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        // Validate email định dạng
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (email == null || !Pattern.matches(emailRegex, email)) {
            redirectAttributes.addFlashAttribute("error", "Email không hợp lệ");
            return "redirect:/login";
        }

        // Validate password không để trống
        if (password == null || password.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu không được để trống");
            return "redirect:/login";
        }

        User user = userService.validateUser(email, password);

        if (user != null) {
            session.setAttribute("user", user);

            if (remember != null) {
                Cookie emailCookie = new Cookie("rememberedEmail", email);
                emailCookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
                emailCookie.setPath("/");
                response.addCookie(emailCookie);
                userService.rememberUser(email);
            }

            if ("ADMIN".equals(user.getRole().getRoleName())) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/SEDO";
            }
        }

        redirectAttributes.addFlashAttribute("error", "Email hoặc mật khẩu không đúng");
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }


    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid User user,
                           BindingResult result,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        // Kiểm tra validation lỗi từ entity (email, password...)
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register"; // trả về lại form và hiển thị lỗi
        }

        // Kiểm tra trùng email
        if (userService.findByEmail(user.getEmail()) != null) {
            result.rejectValue("email", "error.user", "Email đã được sử dụng");
        }

        // Kiểm tra trùng username
        if (userService.findByUsername(user.getUsername()) != null) {
            result.rejectValue("username", "error.user", "Tên đăng nhập đã tồn tại");
        }

        // Nếu có lỗi thì quay lại trang đăng ký
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }

        try {
            // Set role
            Role customerRole = roleService.findById(2L);
            user.setRole(customerRole);
            user.setActive(true);
            userService.save(user);

            redirectAttributes.addFlashAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "redirect:/login";

        } catch (Exception e) {
            model.addAttribute("error", "Lỗi hệ thống. Vui lòng thử lại.");
            return "register";
        }
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (!"rememberedEmail".equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }

        session.removeAttribute("user");
        return "redirect:/login?logout";
    }

    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String username,
                              @RequestParam String email,
                              @RequestParam String fullName,
                              @RequestParam String phoneNumber,
                              @RequestParam String address,
                              @RequestParam(required = false) String currentPassword,
                              @RequestParam(required = false) String newPassword,
                              @RequestParam(required = false) String confirmPassword,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Validate current password if trying to change password
        if (newPassword != null && !newPassword.isEmpty()) {
            if (!user.getPassword().equals(currentPassword)) {
                redirectAttributes.addFlashAttribute("error", "Current password is incorrect");
                return "redirect:/profile";
            }
            if (!newPassword.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "New passwords do not match");
                return "redirect:/profile";
            }
            user.setPassword(newPassword);
        }

        user.setUsername(username);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);

        try {
            userService.save(user);
            session.setAttribute("user", user);
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update profile");
        }

        return "redirect:/profile";
    }

    @PostMapping("/profile/upload-image")
    @ResponseBody
    public Map<String, Object> uploadProfileImage(@RequestParam("image") MultipartFile file,
                                                HttpSession session) {
        User user = (User) session.getAttribute("user");
        Map<String, Object> response = new HashMap<>();

        if (user == null) {
            response.put("success", false);
            response.put("error", "User not logged in");
            return response;
        }

        if (file.isEmpty()) {
            response.put("success", false);
            response.put("error", "No file uploaded");
            return response;
        }

        try {
            // Create unique filename
            String fileName = user.getId() + "_" + System.currentTimeMillis() + "_" +
                            file.getOriginalFilename().replaceAll("\\s+", "_");

            // Get the absolute path to resources directory
            String resourcePath = new File("SEDO/src/main/resources/static").getAbsolutePath();
            String uploadDir = resourcePath + "/uploads/profiles/";
            Path uploadPath = Paths.get(uploadDir);

            // Create directories if they don't exist
            Files.createDirectories(uploadPath);

            // Save the file
            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

                // Set relative path for database storage
                String imagePath = "/uploads/profiles/" + fileName;

                // Delete old profile image if exists
                if (user.getProfileImage() != null) {
                    String oldFileName = user.getProfileImage().substring(user.getProfileImage().lastIndexOf("/") + 1);
                    Path oldFilePath = uploadPath.resolve(oldFileName);
                    Files.deleteIfExists(oldFilePath);
                }

                user.setProfileImage(imagePath);
                userService.save(user);
                session.setAttribute("user", user);

                response.put("success", true);
                response.put("imagePath", imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("error", "Failed to upload image: " + e.getMessage());
        }

        return response;
    }


    @GetMapping("/customer/dashboard")
    public String customerDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        System.out.println("Từ dashboard, user trong session là: " + user);

        if (user == null || !"CUSTOMER".equals(user.getRole().getRoleName())) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "customer-dashboard";
    }


}