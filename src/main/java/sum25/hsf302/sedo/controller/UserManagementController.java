package sum25.hsf302.sedo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sum25.hsf302.sedo.pojo.Role;
import sum25.hsf302.sedo.pojo.User;
import sum25.hsf302.sedo.service.RoleService;
import sum25.hsf302.sedo.service.UserService;

@Controller
public class UserManagementController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/admin/users")
    public String userManagement(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "search", required = false) String search,
            HttpSession session,
            Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole().getRoleName())) {
            return "redirect:/login";
        }
        int size = 10;
        Page<User> userPage;
        if (search != null && !search.trim().isEmpty()) {
            userPage = userService.searchByFullName(search.trim(), PageRequest.of(page, size));
        } else {
            userPage = userService.findAll(PageRequest.of(page, size));
        }
        model.addAttribute("userPage", userPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("search", search);
        return "user-management";
    }

    @GetMapping("admin/users/create-form")
    public String createForm(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole().getRoleName())) {
            return "redirect:/login";
        }

        System.out.println("Creating new user form");
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "user-create";
    }

    @PostMapping("/admin/users/create")
    public String createUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes, HttpSession session) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole().getRoleName())) {
            return "redirect:/login";
        }

        try {
            // Lấy lại Role từ id vì khi submit chỉ có user.role.id, các field khác null
            if (user.getRole() != null && user.getRole().getId() != null) {
                Role role = roleService.findById(user.getRole().getId());
                user.setRole(role);
            }
            System.out.println("Creating user: " + user.getFullName());
            userService.save(user);
            redirectAttributes.addFlashAttribute("success", "User created successfully!");
            return "redirect:/admin/users/create-form";
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error creating user: " + e.getMessage());
            return "redirect:/admin/users/create-form";
        }
    }

    @GetMapping("/admin/users/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole().getRoleName())) {
            return "redirect:/login";
        }
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAll());
        return "user-edit";
    }

    @PostMapping("/admin/users/edit")
    public String updateUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole().getRoleName())) {
            return "redirect:/login";
        }
        userService.save(user);
        redirectAttributes.addFlashAttribute("success", "User updated successfully!");
        return "redirect:/admin/users";
    }

    @GetMapping("admin/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRole().getRoleName())) {
            return "redirect:/login";
        }
        userService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "User deleted successfully!");
        return "redirect:/admin/users";
    }
}
