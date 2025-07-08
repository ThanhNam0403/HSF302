package sum25.hsf302.sedo.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import sum25.hsf302.sedo.pojo.*;
import sum25.hsf302.sedo.service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = "sum25.hsf302.sedo")
@EntityScan("sum25.hsf302.sedo.pojo")
@EnableJpaRepositories("sum25.hsf302.sedo.repository")
public class SedoApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ComputerImageService computerImageService;

    @Autowired
    private ComputerDeviceService computerDeviceService;

    @Autowired
    private ComputerVariantService computerVariantService;



    public static void main(String[] args) {
        SpringApplication.run(SedoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (userService.count() == 0) {

            Category laptop = new Category();
            laptop.setName("Laptop");
            laptop.setDescription("High performance laptops for work and gaming");
            categoryService.save(laptop);

            Category desktop = new Category();
            desktop.setName("Desktop");
            desktop.setDescription("Powerful desktops for professionals");
            categoryService.save(desktop);

            Category monitor = new Category();
            monitor.setName("Monitor");
            monitor.setDescription("HD and 4K monitors");
            categoryService.save(monitor);

            ComputerDevice device1 = new ComputerDevice();
            device1.setName("ASUS ROG Zephyrus G14");
            device1.setDescription("Gaming laptop with Ryzen 9 and RTX 4060");
            device1.setBrand("ASUS");
            device1.setModel("G14-2023");
            device1.setSpecifications("Ryzen 9 7940HS, 16GB RAM, 1TB SSD, RTX 4060");
            device1.setPrice(new BigDecimal("1799.99"));
            device1.setStockQuantity(10);
            device1.setCategory(laptop);
            device1.setActive(true);
            computerDeviceService.save(device1);

            ComputerDevice device2 = new ComputerDevice();
            device2.setName("Dell XPS 8940");
            device2.setDescription("Desktop with Intel i7 and RTX 3060");
            device2.setBrand("Dell");
            device2.setModel("XPS-8940");
            device2.setSpecifications("Intel i7-11700, 16GB RAM, 512GB SSD, RTX 3060");
            device2.setPrice(new BigDecimal("1399.99"));
            device2.setStockQuantity(8);
            device2.setCategory(desktop);
            device2.setActive(true);
            computerDeviceService.save(device2);

            ComputerDevice device3 = new ComputerDevice();
            device3.setName("Samsung 27\" 4K Monitor");
            device3.setDescription("Ultra HD 4K monitor for professional work");
            device3.setBrand("Samsung");
            device3.setModel("U28R550");
            device3.setSpecifications("27 inch, 4K UHD, IPS, 60Hz");
            device3.setPrice(new BigDecimal("349.99"));
            device3.setStockQuantity(15);
            device3.setCategory(monitor);
            device3.setActive(true);
            computerDeviceService.save(device3);

            ComputerImage img1 = new ComputerImage();
            img1.setComputer(device1);
            img1.setImageUrl("s-l1600-7.webp"); // chỉ là tên file, không kèm đường dẫn
            img1.setPrimary(true);
            computerImageService.save(img1);

            ComputerImage img2 = new ComputerImage();
            img2.setComputer(device2);
            img2.setImageUrl("desktop1.webp"); // giả sử bạn có file này trong uploads/ComputerDevices/
            img2.setPrimary(true);
            computerImageService.save(img2);

            ComputerImage img3 = new ComputerImage();
            img3.setComputer(device3);
            img3.setImageUrl("monitor1.webp"); // giả sử bạn có file này trong uploads/ComputerDevices/
            img3.setPrimary(true);
            computerImageService.save(img3);
            ;

            // Lấy danh sách tất cả sản phẩm để tạo variant cho mỗi sản phẩm
            List<ComputerDevice> devices = computerDeviceService.findAll();

            for (ComputerDevice device : devices) {
                ComputerVariant ramVariant = new ComputerVariant();
                ramVariant.setComputer(device);
                ramVariant.setName("RAM");
                ramVariant.setValue("16GB");
                ramVariant.setAdditionalPrice(new BigDecimal("25.00"));
                ramVariant.setStockQuantity(10);

                ComputerVariant storageVariant = new ComputerVariant();
                storageVariant.setComputer(device);
                storageVariant.setName("Storage");
                storageVariant.setValue("512GB SSD");
                storageVariant.setAdditionalPrice(new BigDecimal("35.00"));
                storageVariant.setStockQuantity(8);

                ComputerVariant gpuVariant = new ComputerVariant();
                gpuVariant.setComputer(device);
                gpuVariant.setName("GPU");
                gpuVariant.setValue("NVIDIA GTX 1660");
                gpuVariant.setAdditionalPrice(new BigDecimal("100.00"));
                gpuVariant.setStockQuantity(5);

                // Lưu biến thể vào DB qua computerDevice => cascade sẽ tự lưu nếu set trong device
                device.setComputerVariants(List.of(ramVariant, storageVariant, gpuVariant));
                computerDeviceService.save(device);  // cascade ALL sẽ lưu luôn variants
            }


            // Create Roles
            Role adminRole = new Role();
            adminRole.setRoleName("ADMIN");
            roleService.save(adminRole);

            Role customerRole = new Role();
            customerRole.setRoleName("CUSTOMER");
            roleService.save(customerRole);

            // Create Admin User
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setEmail("admin@sedo.com");
            admin.setPhoneNumber("0123456789");
            admin.setFullName("System Administrator");
            admin.setAddress("123 Admin Street, Tech City");
            admin.setRole(adminRole);
            admin.setCreatedAt(LocalDate.now());
            admin.setUpdatedAt(LocalDate.now());
            admin.setActive(true);
            userService.save(admin);

            // Create Customer User
            User customer = new User();
            customer.setUsername("customer");
            customer.setPassword("customer123");
            customer.setEmail("customer@sedo.com");
            customer.setPhoneNumber("0987654321");
            customer.setFullName("John Customer");
            customer.setAddress("456 Customer Avenue, Shop City");
            customer.setRole(customerRole);
            customer.setCreatedAt(LocalDate.now());
            customer.setUpdatedAt(LocalDate.now());
            customer.setActive(true);
            userService.save(customer);

            // Create Additional Customer
            User customer2 = new User();
            customer2.setUsername("customer2");
            customer2.setPassword("customer456");
            customer2.setEmail("customer2@sedo.com");
            customer2.setPhoneNumber("0987654322");
            customer2.setFullName("Jane Customer");
            customer2.setAddress("789 Customer Boulevard, Shop City");
            customer2.setRole(customerRole);
            customer2.setCreatedAt(LocalDate.now());
            customer2.setUpdatedAt(LocalDate.now());
            customer2.setActive(true);
            userService.save(customer2);

            User customer3 = new User();
            customer3.setUsername("customer3");
            customer3.setPassword("customer789");
            customer3.setEmail("customer3@sedo.com");
            customer3.setPhoneNumber("0987654323");
            customer3.setFullName("Alice Customer");
            customer3.setAddress("101 Customer Lane, Shop City");
            customer3.setRole(customerRole);
            customer3.setCreatedAt(LocalDate.now());
            customer3.setUpdatedAt(LocalDate.now());
            customer3.setActive(true);
            userService.save(customer3);
        }
    }
}