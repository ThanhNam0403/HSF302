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
import sum25.hsf302.sedo.pojo.Category;
import sum25.hsf302.sedo.pojo.ComputerDevice;
import sum25.hsf302.sedo.pojo.ComputerImage;
import sum25.hsf302.sedo.pojo.User;
import sum25.hsf302.sedo.service.CategoryService;
import sum25.hsf302.sedo.service.ComputerDeviceService;

import java.util.List;

@Controller
@RequestMapping("/admin/computer-devices")
public class ComputerDeviceManagementController {

    @Autowired
    private ComputerDeviceService computerDeviceService;

    @Autowired
    private CategoryService categoryService;

    // Danh sách & tìm kiếm & phân trang
    @GetMapping
    public String listDevices(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "search", required = false) String search,
            Model model,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole().getRoleName())) {
            return "redirect:/login";
        }

        int size = 10;
        Page<ComputerDevice> devicePage;
        if (search != null && !search.trim().isEmpty()) {
            devicePage = computerDeviceService.searchDevices(search.trim(), PageRequest.of(page, size));
        } else {
            devicePage = computerDeviceService.findAll(PageRequest.of(page, size));
        }
        model.addAttribute("devicePage", devicePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", devicePage.getTotalPages());
        model.addAttribute("search", search);
        return "computer-device-management";
    }

    // Xóa
    @GetMapping("/delete/{id}")
    public String deleteDevice(@PathVariable Long id, RedirectAttributes redirect, HttpSession session) {
        // Kiểm tra quyền truy cập
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole().getRoleName())) {
            return "redirect:/login";
        }
        computerDeviceService.deleteById(id);
        redirect.addFlashAttribute("success", "Device deleted!");
        return "redirect:/admin/computer-devices";
    }

    // Chuyển sang trang create
    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession session) {
        // Kiểm tra quyền truy cập
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole().getRoleName())) {
            return "redirect:/login";
        }
        model.addAttribute("device", new ComputerDevice());
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        // Thêm các list cần thiết như category, brand nếu có
        return "device-create";
    }

    // Xử lý create
    @PostMapping("/create")
    public String createDevice(@ModelAttribute("device") ComputerDevice device,@RequestParam(name = "imageUrl", required = false) String imageUrl, RedirectAttributes redirect, HttpSession session) {
        // Kiểm tra quyền truy cập
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole().getRoleName())) {
            return "redirect:/login";
        }
        try {
            if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                // Tạo đối tượng ComputerImage (giả sử có constructor nhận url)
                ComputerImage image = new ComputerImage();
                image.setImageUrl(imageUrl);
                image.setComputer(device);
                device.setComputerImages(List.of(image)); // Nếu chỉ nhập 1 ảnh
            }
            if (computerDeviceService.findByName(device.getName()) != null) {
                redirect.addFlashAttribute("error", "tên sản phẩm đã tồn tại");
                return "redirect:/admin/computer-devices/create";
            }
            computerDeviceService.save(device);
            redirect.addFlashAttribute("success", "tạo sản phẩm thành công");
            return "redirect:/admin/computer-devices";
        }catch (Exception e) {
            redirect.addFlashAttribute("error", "Lỗi khi tạo sản phẩm: " + e.getMessage());
            return "redirect:/admin/computer-devices/create";
        }
    }

    // Chuyển sang trang edit
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model,HttpSession session) {
        // Kiểm tra quyền truy cập
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole().getRoleName())) {
            return "redirect:/login";
        }
        ComputerDevice device = computerDeviceService.findById(id);
        model.addAttribute("device", device);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        // Thêm các list cần thiết như category, brand nếu có
        return "device-edit";
    }

    // Xử lý edit
    @PostMapping("/edit")
    public String editDevice(@ModelAttribute("device") ComputerDevice device, RedirectAttributes redirect,
                             @RequestParam(name = "imageUrl", required = false) String imageUrl,HttpSession session , Model model) {
        // Kiểm tra quyền truy cập
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole().getRoleName())) {
            return "redirect:/login";
        }
        try {
            // Tìm lại device gốc để giữ các thông tin liên kết khác (nếu cần)
            ComputerDevice original = computerDeviceService.findById(device.getId());

            // Cập nhật thông tin ảnh
            if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                ComputerImage img;
                if (original.getComputerImages() != null && !original.getComputerImages().isEmpty()) {
                    img = original.getComputerImages().get(0);
                    img.setImageUrl(imageUrl); // update url mới
                } else {
                    img = new ComputerImage();
                    img.setImageUrl(imageUrl);
                    img.setComputer(original);
                    original.setComputerImages(List.of(img));
                }
            } else {
                // Nếu bỏ trống imageUrl thì xóa ảnh luôn (nếu muốn)
                original.setComputerImages(null);
            }

            // Cập nhật các trường khác
            original.setName(device.getName());
            original.setBrand(device.getBrand());
            original.setModel(device.getModel());
            original.setDescription(device.getDescription());
            original.setCpuModel(device.getCpuModel());
            original.setRam(device.getRam());
            original.setPrice(device.getPrice());
            original.setStockQuantity(device.getStockQuantity());
            original.setSpecifications(device.getSpecifications());
            original.setIsActive(device.getIsActive());
            original.setCategory(device.getCategory());

            computerDeviceService.save(original);
            redirect.addFlashAttribute("success", "Device updated successfully!");
            return "redirect:/admin/computer-devices";
        } catch (Exception e) {
            model.addAttribute("error", "Update device failed: " + e.getMessage());
            model.addAttribute("categories", categoryService.findAll());
            return "device-edit";
        }
    }
}