package sum25.hsf302.sedo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sum25.hsf302.sedo.pojo.ComputerDevice;
import sum25.hsf302.sedo.service.CategoryService;
import sum25.hsf302.sedo.service.ComputerDeviceService;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ComputerController {

    @Autowired
    private ComputerDeviceService computerDeviceService;

    @Autowired
    private CategoryService categoryService;

    // Xem chi tiết 1 sản phẩm
    @GetMapping("/{id}")
    public String viewProductDetail(@PathVariable Long id, Model model) {
        ComputerDevice product = computerDeviceService.findById(id);
        if (product == null || !product.getActive()) {
            return "404";
        }
        model.addAttribute("product", product);
        return "product-detail";  // view hiển thị chi tiết sản phẩm
    }

    // Danh sách tất cả sản phẩm (có thể thêm phân trang sau)
    @GetMapping("/all")
    public String listAllProducts(Model model) {
        List<ComputerDevice> products = computerDeviceService.findAll();
        model.addAttribute("products", products);
        return "product-list";  // view hiển thị danh sách
    }

    // Tìm kiếm sản phẩm
    @GetMapping("/search")
    public String searchProducts(@RequestParam String keyword, Model model) {
        List<ComputerDevice> results = computerDeviceService.searchProducts(keyword);
        model.addAttribute("products", results);
        return "product-list";
    }

    // 🔢 Lọc theo danh mục
    @GetMapping("/category/{categoryId}")
    public String filterByCategory(@PathVariable("categoryId") Long categoryId, Model model) {
        List<ComputerDevice> products = computerDeviceService.findByCategory(categoryId);
        model.addAttribute("products", products);
        model.addAttribute("category", categoryService.findById(categoryId)); // nếu muốn hiển thị tên danh mục
        return "product-list";
    }

    // 🏷️ Lọc theo thương hiệu
    @GetMapping("/brand")
    public String filterByBrand(@RequestParam("name") String brandName, Model model) {
        List<ComputerDevice> products = computerDeviceService.findByBrand(brandName);
        model.addAttribute("products", products);
        model.addAttribute("brand", brandName);
        return "product-list";
    }
}
