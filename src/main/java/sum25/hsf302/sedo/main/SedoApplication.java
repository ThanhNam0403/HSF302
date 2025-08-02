package sum25.hsf302.sedo.main;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import sum25.hsf302.sedo.pojo.*;
import sum25.hsf302.sedo.repository.CategoryRepository;
import sum25.hsf302.sedo.service.*;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Paths;
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

    @Autowired
    private CategoryRepository categoryRepository;


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

            Category pcBuild = new Category();
            pcBuild.setName("PC Build");
            pcBuild.setDescription("Danh mục dành cho các linh kiện PC");

// Lưu category cha trước để có ID
            categoryRepository.save(pcBuild);

// CPU
            Category cpu = new Category();
            cpu.setName("CPU");
            cpu.setDescription("Bộ vi xử lý");
            cpu.setParentCategory(pcBuild);
            categoryRepository.save(cpu);

// Mainboard
            Category mainboard = new Category();
            mainboard.setName("Mainboard");
            mainboard.setDescription("Bo mạch chủ");
            mainboard.setParentCategory(pcBuild);
            categoryRepository.save(mainboard);

// RAM
            Category ram = new Category();
            ram.setName("RAM");
            ram.setDescription("Bộ nhớ trong");
            ram.setParentCategory(pcBuild);
            categoryRepository.save(ram);

            // SSD
            Category ssd = new Category();
            ssd.setName("SSD");
            ssd.setDescription("Ổ cứng thể rắn");
            ssd.setParentCategory(pcBuild);
            categoryRepository.save(ssd);

// HDD
            Category hdd = new Category();
            hdd.setName("HDD");
            hdd.setDescription("Ổ cứng truyền thống");
            hdd.setParentCategory(pcBuild);
            categoryRepository.save(hdd);

// PSU
            Category psu = new Category();
            psu.setName("PSU");
            psu.setDescription("Bộ nguồn");
            psu.setParentCategory(pcBuild);
            categoryRepository.save(psu);

// VGA
            Category vga = new Category();
            vga.setName("VGA");
            vga.setDescription("Card đồ họa");
            vga.setParentCategory(pcBuild);
            categoryRepository.save(vga);

// Case
            Category pcCase = new Category();
            pcCase.setName("Case");
            pcCase.setDescription("Vỏ máy tính");
            pcCase.setParentCategory(pcBuild);
            categoryRepository.save(pcCase);

// CPU Cooler
            Category cooler = new Category();
            cooler.setName("CPU Cooler");
            cooler.setDescription("Tản nhiệt CPU");
            cooler.setParentCategory(pcBuild);
            categoryRepository.save(cooler);

            Category keyboard = new Category();
            keyboard.setName("Keyboard");
            keyboard.setDescription("Bàn phím máy tính");
            keyboard.setParentCategory(pcBuild);
            categoryRepository.save(keyboard);

            Category mouse = new Category();
            mouse.setName("Mouse");
            mouse.setDescription("Chuột máy tính");
            mouse.setParentCategory(pcBuild);
            categoryRepository.save(mouse);

            Category headset = new Category();
            headset.setName("Headset");
            headset.setDescription("Tai nghe máy tính");
            headset.setParentCategory(pcBuild);
            categoryRepository.save(headset);

            Category speaker = new Category();
            speaker.setName("Speaker");
            speaker.setDescription("Loa máy tính");
            speaker.setParentCategory(pcBuild);
            categoryRepository.save(speaker);

            Category monitor = new Category();
            monitor.setName("Monitor");
            monitor.setDescription("Màn hình máy tính");
            monitor.setParentCategory(pcBuild);
            categoryRepository.save(monitor);


        }
        ComputerDevice i5_1 = computerDeviceService.findByName("Intel Core i5-12400F");
        createAndSaveImage(i5_1, "i5-12400F.png");

        ComputerDevice i5_2 = computerDeviceService.findByName("Intel Core i5-13400F");
        createAndSaveImage(i5_2, "i5-12400F.png");

        ComputerDevice i5_3 = computerDeviceService.findByName("Intel Core i5-14600KF");
        createAndSaveImage(i5_3, "i5-12400F.png");

        ComputerDevice i7_1 = computerDeviceService.findByName("Intel Core i7-12700K");
        createAndSaveImage(i7_1, "i7-12700F.png");

        ComputerDevice i7_2 = computerDeviceService.findByName("Intel Core i7-13700K");
        createAndSaveImage(i7_2, "i7-12700F.png");

        ComputerDevice i7_3 = computerDeviceService.findByName("Intel Core i7-14700KF");
        createAndSaveImage(i7_3, "i7-12700F.png");

        ComputerDevice i9_1 = computerDeviceService.findByName("Intel Core i9-13900K");
        createAndSaveImage(i9_1, "i9-13900KF.png");

        ComputerDevice i9_2 = computerDeviceService.findByName("Intel Core i9-14900KF");
        createAndSaveImage(i9_2, "i9-13900KF.png");
        //ảnh bo mạch chủ
        ComputerDevice ryzen_5_1 = computerDeviceService.findByName("Mainboard MSI B660M Mortar");
        createAndSaveImage(ryzen_5_1, "bmc.png");

        ComputerDevice ryzen_5_2 = computerDeviceService.findByName("Mainboard ASUS TUF B660-PLUS");
        createAndSaveImage(ryzen_5_2, "bmc.png");

        ComputerDevice ryzen_5_3 = computerDeviceService.findByName("Mainboard Gigabyte B660M DS3H");
        createAndSaveImage(ryzen_5_3, "bmc.png");

        ComputerDevice ryzen_7_1 = computerDeviceService.findByName("Mainboard ASRock B660M Pro RS");
        createAndSaveImage(ryzen_7_1, "bmc.png");

        ComputerDevice ryzen_7_2 = computerDeviceService.findByName("Mainboard MSI MAG B660 Tomahawk");
        createAndSaveImage(ryzen_7_2, "bmc.png");

        ComputerDevice ryzen_7_3 = computerDeviceService.findByName("Mainboard ASUS PRIME B660M-K");
        createAndSaveImage(ryzen_7_3, "bmc.png");

        ComputerDevice ryzen_9_1 = computerDeviceService.findByName("Mainboard Gigabyte B660 Gaming X");
        createAndSaveImage(ryzen_9_1, "bmc.png");

        ComputerDevice ryzen_9_2 = computerDeviceService.findByName("Mainboard ASRock B660M-HDV");
        createAndSaveImage(ryzen_9_2, "bmc.png");

        ComputerDevice ram_1 = computerDeviceService.findByName("Corsair Vengeance LPX 16GB");
        createAndSaveImage(ram_1, "ram.png");

        ComputerDevice ram_2 = computerDeviceService.findByName("Kingston Fury Beast 16GB");
        createAndSaveImage(ram_2, "ram.png");

        ComputerDevice ram_3 = computerDeviceService.findByName("G.Skill Ripjaws V 16GB");
        createAndSaveImage(ram_3, "ram.png");

        ComputerDevice ram_4 = computerDeviceService.findByName("ADATA XPG GAMMIX D30 16GB");
        createAndSaveImage(ram_4, "ram.png");

        ComputerDevice ram_5 = computerDeviceService.findByName("TeamGroup T-Force Delta RGB 16GB");
        createAndSaveImage(ram_5, "ram.png");

        ComputerDevice ram_6 = computerDeviceService.findByName("Patriot Viper Steel 16GB");
        createAndSaveImage(ram_6, "ram.png");

        ComputerDevice ram_7 = computerDeviceService.findByName("Apacer Panther Golden 16GB");
        createAndSaveImage(ram_7, "ram.png");

        ComputerDevice ram_8 = computerDeviceService.findByName("Silicon Power Gaming 16GB");
        createAndSaveImage(ram_8, "ram.png");

        //hdd image
        ComputerDevice hdd_1 = computerDeviceService.findByName("Seagate Barracuda 1TB");
        createAndSaveImage(hdd_1, "hdd.png");

        ComputerDevice hdd_2 = computerDeviceService.findByName("Western Digital Blue 1TB");
        createAndSaveImage(hdd_2, "hdd.png");

        ComputerDevice hdd_3 = computerDeviceService.findByName("Seagate FireCuda 2TB SSHD");
        createAndSaveImage(hdd_3, "hdd.png");

        ComputerDevice hdd_4 = computerDeviceService.findByName("Samsung 870 EVO 500GB");
        createAndSaveImage(hdd_4, "hdd.png");

        ComputerDevice hdd_5 = computerDeviceService.findByName("Kingston NV2 1TB Gen4");
        createAndSaveImage(hdd_5, "hdd.png");

        ComputerDevice hdd_6 = computerDeviceService.findByName("WD Black SN770 1TB");
        createAndSaveImage(hdd_6, "hdd.png");

        //ssd image
        ComputerDevice ssd_1 = computerDeviceService.findByName("Samsung 980 NVMe 1TB");
        createAndSaveImage(ssd_1, "ssd.png");

        ComputerDevice ssd_2 = computerDeviceService.findByName("WD Blue SN570 1TB");
        createAndSaveImage(ssd_2, "ssd.png");

        ComputerDevice ssd_3 = computerDeviceService.findByName("Kingston NV2 1TB");
        createAndSaveImage(ssd_3, "ssd.png");

        ComputerDevice ssd_4 = computerDeviceService.findByName("Crucial P3 1TB");
        createAndSaveImage(ssd_4, "ssd.png");

        ComputerDevice ssd_5 = computerDeviceService.findByName("ADATA XPG SX8200 Pro 1TB");
        createAndSaveImage(ssd_5, "ssd.png");

        ComputerDevice ssd_6 = computerDeviceService.findByName("Lexar NM620 1TB");
        createAndSaveImage(ssd_6, "ssd.png");

        ComputerDevice ssd_7 = computerDeviceService.findByName("Team MP33 1TB");
        createAndSaveImage(ssd_7, "ssd.png");

        ComputerDevice ssd_8 = computerDeviceService.findByName("Gigabyte AORUS Gen4 7000s 1TB");
        createAndSaveImage(ssd_8, "ssd.png");

        //vga image
        ComputerDevice vga_1 = computerDeviceService.findByName("ASUS Dual GeForce RTX 3060");
        createAndSaveImage(vga_1, "vga.png");

        ComputerDevice vga_2 = computerDeviceService.findByName("GIGABYTE GeForce RTX 3060 GAMING OC");
        createAndSaveImage(vga_2, "vga.png");

        ComputerDevice vga_3 = computerDeviceService.findByName("MSI GeForce RTX 3060 VENTUS 2X");
        createAndSaveImage(vga_3, "vga.png");

        ComputerDevice vga_4 = computerDeviceService.findByName("ZOTAC Gaming RTX 3060 Twin Edge");
        createAndSaveImage(vga_4, "vga.png");

        ComputerDevice vga_5 = computerDeviceService.findByName("INNO3D RTX 3060 Twin X2 OC");
        createAndSaveImage(vga_5, "vga.png");

        ComputerDevice vga_6 = computerDeviceService.findByName("ASUS TUF Gaming RTX 3060 OC");
        createAndSaveImage(vga_6, "vga.png");

        ComputerDevice vga_7 = computerDeviceService.findByName("Palit GeForce RTX 3060 DUAL OC");
        createAndSaveImage(vga_7, "vga.png");

        // PSU image
        ComputerDevice psu_1 = computerDeviceService.findByName("Cooler Master MWE 550W");
        createAndSaveImage(psu_1, "psu.png");

        ComputerDevice psu_2 = computerDeviceService.findByName("Corsair CV550 550W");
        createAndSaveImage(psu_2, "psu.png");

        ComputerDevice psu_3 = computerDeviceService.findByName("Antec VP550 Plus 550W");
        createAndSaveImage(psu_3, "psu.png");

        ComputerDevice psu_4 = computerDeviceService.findByName("Seasonic S12III 550W");
        createAndSaveImage(psu_4, "psu.png");

        ComputerDevice psu_5 = computerDeviceService.findByName("Xigmatek X-Power III 550W");
        createAndSaveImage(psu_5, "psu.png");

        ComputerDevice psu_6 = computerDeviceService.findByName("FSP Power Supply HEXA+ 550W");
        createAndSaveImage(psu_6, "psu.png");

        ComputerDevice psu_7 = computerDeviceService.findByName("Gigabyte P550B 550W");
        createAndSaveImage(psu_7, "psu.png");

        ComputerDevice psu_8 = computerDeviceService.findByName("DeepCool DN550 550W");
        createAndSaveImage(psu_8, "psu.png");

        // Case image
        ComputerDevice case_1 = computerDeviceService.findByName("Xigmatek Gemini Arctic");
        createAndSaveImage(case_1, "case.png");

        ComputerDevice case_2 = computerDeviceService.findByName("Cooler Master MasterBox MB311L ARGB");
        createAndSaveImage(case_2, "case.png");

        ComputerDevice case_3 = computerDeviceService.findByName("Corsair 4000D Airflow");
        createAndSaveImage(case_3, "case.png");

        ComputerDevice case_4 = computerDeviceService.findByName("MSI MAG Forge 100R");
        createAndSaveImage(case_4, "case.png");

        ComputerDevice case_5 = computerDeviceService.findByName("Xigmatek Aquarius Plus Arctic");
        createAndSaveImage(case_5, "case.png");

        ComputerDevice case_6 = computerDeviceService.findByName("NZXT H510");
        createAndSaveImage(case_6, "case.png");

        //cooler image
        ComputerDevice cooler_1 = computerDeviceService.findByName("Cooler Master Hyper 212 Black");
        createAndSaveImage(cooler_1, "cooler.png");

        ComputerDevice cooler_2 = computerDeviceService.findByName("Noctua NH-U12S Redux");
        createAndSaveImage(cooler_2, "cooler.png");

        ComputerDevice cooler_3 = computerDeviceService.findByName("ID-Cooling SE-224-XT ARGB");
        createAndSaveImage(cooler_3, "cooler.png");

        ComputerDevice cooler_4 = computerDeviceService.findByName("Deepcool GAMMAXX 400 V2");
        createAndSaveImage(cooler_4, "cooler.png");

        ComputerDevice cooler_5 = computerDeviceService.findByName("Lian Li Galahad 240 ARGB");
        createAndSaveImage(cooler_5, "cooler.png");

        ComputerDevice cooler_6 = computerDeviceService.findByName("Corsair iCUE H100i Elite Capellix");
        createAndSaveImage(cooler_6, "cooler.png");

        //anh cua man hinh
        ComputerDevice monitor_1 = computerDeviceService.findByName("LG UltraGear 27GN750-B");
        createAndSaveImage(monitor_1, "monitor.png");

        ComputerDevice monitor_2 = computerDeviceService.findByName("ASUS TUF Gaming VG249Q1A");
        createAndSaveImage(monitor_2, "monitor.png");

        ComputerDevice monitor_3 = computerDeviceService.findByName("MSI Optix G241");
        createAndSaveImage(monitor_3, "monitor.png");

        ComputerDevice monitor_4 = computerDeviceService.findByName("Samsung Odyssey G3 LF24G35TFWEXXV");
        createAndSaveImage(monitor_4, "monitor.png");

        ComputerDevice monitor_5 = computerDeviceService.findByName("ViewSonic VX2476-SMH");
        createAndSaveImage(monitor_5, "monitor.png");

        ComputerDevice monitor_6 = computerDeviceService.findByName("AOC 24G2");
        createAndSaveImage(monitor_6, "monitor.png");

        ComputerDevice monitor_7 = computerDeviceService.findByName("Gigabyte G24F 2");
        createAndSaveImage(monitor_7, "monitor.png");

        ComputerDevice monitor_8 = computerDeviceService.findByName("Dell S2421HGF");
        createAndSaveImage(monitor_8, "monitor.png");

        //anh cua ban phim
        ComputerDevice keyboard_1 = computerDeviceService.findByName("Logitech K120");
        createAndSaveImage(keyboard_1, "keyboard.png");

        ComputerDevice keyboard_2 = computerDeviceService.findByName("Razer BlackWidow V3");
        createAndSaveImage(keyboard_2, "keyboard.png");

        ComputerDevice keyboard_3 = computerDeviceService.findByName("Corsair K70 RGB TKL");
        createAndSaveImage(keyboard_3, "keyboard.png");

        ComputerDevice keyboard_4 = computerDeviceService.findByName("DareU EK1280");
        createAndSaveImage(keyboard_4, "keyboard.png");

        ComputerDevice keyboard_5 = computerDeviceService.findByName("Akko 3068 Silent");
        createAndSaveImage(keyboard_5, "keyboard.png");

        ComputerDevice keyboard_6 = computerDeviceService.findByName("Fuhlen L411");
        createAndSaveImage(keyboard_6, "keyboard.png");

        ComputerDevice keyboard_7 = computerDeviceService.findByName("Keychron K2");
        createAndSaveImage(keyboard_7, "keyboard.png");

        ComputerDevice keyboard_8 = computerDeviceService.findByName("Rapoo V500 Pro");
        createAndSaveImage(keyboard_8, "keyboard.png");

        //anh cua chuot
        ComputerDevice mouse_1 = computerDeviceService.findByName("Logitech G102 Lightsync");
        createAndSaveImage(mouse_1, "mouse.png");

        ComputerDevice mouse_2 = computerDeviceService.findByName("Razer DeathAdder Essential");
        createAndSaveImage(mouse_2, "mouse.png");

        ComputerDevice mouse_3 = computerDeviceService.findByName("Fuhlen G90 Pro");
        createAndSaveImage(mouse_3, "mouse.png");

        ComputerDevice mouse_4 = computerDeviceService.findByName("Rapoo V16");
        createAndSaveImage(mouse_4, "mouse.png");

        ComputerDevice mouse_5 = computerDeviceService.findByName("Corsair Harpoon RGB Wireless");
        createAndSaveImage(mouse_5, "mouse.png");

        ComputerDevice mouse_6 = computerDeviceService.findByName("DareU EM908");
        createAndSaveImage(mouse_6, "mouse.png");

        ComputerDevice mouse_7 = computerDeviceService.findByName("Apple Magic Mouse 2");
        createAndSaveImage(mouse_7, "mouse.png");

        ComputerDevice mouse_8 = computerDeviceService.findByName("Logitech M331 Silent Plus");
        createAndSaveImage(mouse_8, "mouse.png");

        //anh cua tai nghe
        ComputerDevice headset_1 = computerDeviceService.findByName("HyperX Cloud Stinger");
        createAndSaveImage(headset_1, "headphone.png");

        ComputerDevice headset_2 = computerDeviceService.findByName("Logitech G733 Lightspeed");
        createAndSaveImage(headset_2, "headphone.png");

        ComputerDevice headset_3 = computerDeviceService.findByName("Razer Kraken X");
        createAndSaveImage(headset_3, "headphone.png");

        ComputerDevice headset_4 = computerDeviceService.findByName("Corsair HS50 Pro");
        createAndSaveImage(headset_4, "headphone.png");

        ComputerDevice headset_5 = computerDeviceService.findByName("Sony WH-CH510");
        createAndSaveImage(headset_5, "headphone.png");

        ComputerDevice headset_6 = computerDeviceService.findByName("SteelSeries Arctis 1");
        createAndSaveImage(headset_6, "headphone.png");

        ComputerDevice headset_7 = computerDeviceService.findByName("Apple AirPods Pro");
        createAndSaveImage(headset_7, "headphone.png");

        ComputerDevice headset_8 = computerDeviceService.findByName("SoundPeats G1");
        createAndSaveImage(headset_8, "headphone.png");

        //anh cua loa
        ComputerDevice speaker_1 = computerDeviceService.findByName("Logitech Z313");
        createAndSaveImage(speaker_1, "speakers.png");

        ComputerDevice speaker_2 = computerDeviceService.findByName("Microlab M108");
        createAndSaveImage(speaker_2, "speakers.png");

        ComputerDevice speaker_3 = computerDeviceService.findByName("Fenda F380X");
        createAndSaveImage(speaker_3, "speakers.png");

        ComputerDevice speaker_4 = computerDeviceService.findByName("Edifier R1280DB");
        createAndSaveImage(speaker_4, "speakers.png");

        ComputerDevice speaker_5 = computerDeviceService.findByName("SoundMax A2116");
        createAndSaveImage(speaker_5, "speakers.png");

        ComputerDevice speaker_6 = computerDeviceService.findByName("Creative Pebble V3");
        createAndSaveImage(speaker_6, "speakers.png");

        ComputerDevice speaker_7 = computerDeviceService.findByName("Logitech Z625");
        createAndSaveImage(speaker_7, "speakers.png");

        ComputerDevice speaker_8 = computerDeviceService.findByName("Microlab FC330");
        createAndSaveImage(speaker_8, "speakers.png");

        //anh laptop
        ComputerDevice laptop_acer1 = computerDeviceService.findByName("Acer Aspire 7 A715-76G-59MW");
        createAndSaveImage(laptop_acer1, "acer.png");

        ComputerDevice laptop_acer2 = computerDeviceService.findByName("Acer Aspire 3 A315-59-31BT");
        createAndSaveImage(laptop_acer2, "acer.png");

        ComputerDevice laptop_acer3 = computerDeviceService.findByName("Acer Aspire 5 A514-55-55KD");
        createAndSaveImage(laptop_acer3, "acer.png");

        ComputerDevice laptop_acer4 = computerDeviceService.findByName("Acer Nitro 5 AN515-58-52SP");
        createAndSaveImage(laptop_acer4, "acer.png");

        ComputerDevice laptop_acer5 = computerDeviceService.findByName("Acer Aspire 5 A514-55-380G");
        createAndSaveImage(laptop_acer5, "acer.png");

        ComputerDevice laptop_acer6 = computerDeviceService.findByName("Acer Aspire 7 A715-76G-58ZR");
        createAndSaveImage(laptop_acer6, "acer.png");

        ComputerDevice laptop_acer7 = computerDeviceService.findByName("Acer Nitro 5 AN515-58-5046");
        createAndSaveImage(laptop_acer7, "acer.png");

        ComputerDevice laptop_acer8 = computerDeviceService.findByName("Acer Aspire 3 A315-59-32H1");
        createAndSaveImage(laptop_acer8, "acer.png");

        ComputerDevice laptop_acer9 = computerDeviceService.findByName("Acer Aspire 5 A514-55-378B");
        createAndSaveImage(laptop_acer9, "acer.png");

        ComputerDevice laptop_acer10 = computerDeviceService.findByName("Acer Swift 3 SF314-512-53VK");
        createAndSaveImage(laptop_acer10, "acer.png");

        //anh asus
        ComputerDevice laptop_asus1 = computerDeviceService.findByName("ASUS Vivobook 15 OLED A1505VA-L1118W");
        createAndSaveImage(laptop_asus1, "asus.png");

        ComputerDevice laptop_asus2 = computerDeviceService.findByName("ASUS TUF Gaming F15 FX507ZC4-HN074W");
        createAndSaveImage(laptop_asus2, "asus.png");

        ComputerDevice laptop_asus3 = computerDeviceService.findByName("ASUS Vivobook 15 OLED X1505VA-L1107W");
        createAndSaveImage(laptop_asus3, "asus.png");

        ComputerDevice laptop_asus4 = computerDeviceService.findByName("ASUS ROG Strix G16 G614JU-N3088W");
        createAndSaveImage(laptop_asus4, "asus.png");

        ComputerDevice laptop_asus5 = computerDeviceService.findByName("ASUS ZenBook 14 OLED UX3402VA-KM356W");
        createAndSaveImage(laptop_asus5, "asus.png");

        ComputerDevice laptop_asus6 = computerDeviceService.findByName("ASUS Vivobook X1404ZA-NK144W");
        createAndSaveImage(laptop_asus6, "asus.png");

        ComputerDevice laptop_asus7 = computerDeviceService.findByName("ASUS TUF Gaming F15 FX506HF-HN015W");
        createAndSaveImage(laptop_asus7, "asus.png");

        ComputerDevice laptop_asus8 = computerDeviceService.findByName("ASUS Vivobook Go 14 E1404FA-NK356W");
        createAndSaveImage(laptop_asus8, "asus.png");

        ComputerDevice laptop_asus9 = computerDeviceService.findByName("ASUS Vivobook 14 X1404VA-NK349W");
        createAndSaveImage(laptop_asus9, "asus.png");

        ComputerDevice laptop_asus10 = computerDeviceService.findByName("ASUS ZenBook 14X OLED UX3404VA-KM061W");
        createAndSaveImage(laptop_asus10, "asus.png");

        //anh dell
        ComputerDevice laptop_dell1 = computerDeviceService.findByName("Dell Inspiron 15 3530 N3530C i3");
        createAndSaveImage(laptop_dell1, "dell.png");

        ComputerDevice laptop_dell2 = computerDeviceService.findByName("Dell Inspiron 15 3530 N3530A i5");
        createAndSaveImage(laptop_dell2, "dell.png");

        ComputerDevice laptop_dell3 = computerDeviceService.findByName("Dell Inspiron 14 7430 7430B i5");
        createAndSaveImage(laptop_dell3, "dell.png");

        ComputerDevice laptop_dell4 = computerDeviceService.findByName("Dell Vostro 15 3520 7T2YC1");
        createAndSaveImage(laptop_dell4, "dell.png");

        ComputerDevice laptop_dell5 = computerDeviceService.findByName("Dell XPS 13 9315 705JK2");
        createAndSaveImage(laptop_dell5, "dell.png");

        ComputerDevice laptop_dell6 = computerDeviceService.findByName("Dell G15 5530 P159G003LBL");
        createAndSaveImage(laptop_dell6, "dell.png");

        ComputerDevice laptop_dell7 = computerDeviceService.findByName("Dell Vostro 14 3430 V4I7117W");
        createAndSaveImage(laptop_dell7, "dell.png");

        ComputerDevice laptop_dell8 = computerDeviceService.findByName("Dell Inspiron 14 7430 7430C i7");
        createAndSaveImage(laptop_dell8, "dell.png");

        ComputerDevice laptop_dell9 = computerDeviceService.findByName("Dell Inspiron 16 5630 5630A i7");
        createAndSaveImage(laptop_dell9, "dell.png");

        ComputerDevice laptop_dell10 = computerDeviceService.findByName("Dell Vostro 16 5630 V16C1003W");
        createAndSaveImage(laptop_dell10, "dell.png");

        //anh lenovo
        ComputerDevice laptop_lenovo1 = computerDeviceService.findByName("Lenovo Legion 5 16IRX9 83DG004GVN");
        createAndSaveImage(laptop_lenovo1, "lenovo.png");

        ComputerDevice laptop_lenovo2 = computerDeviceService.findByName("Lenovo Legion 5 16IAH7 82RB00GCVN");
        createAndSaveImage(laptop_lenovo2, "lenovo.png");

        ComputerDevice laptop_lenovo3 = computerDeviceService.findByName("Lenovo Legion Slim 7 16IAH7 82TF004CVN");
        createAndSaveImage(laptop_lenovo3, "lenovo.png");

        ComputerDevice laptop_lenovo4 = computerDeviceService.findByName("Lenovo LOQ 15IAX9 83DV000PVN");
        createAndSaveImage(laptop_lenovo4, "lenovo.png");

        ComputerDevice laptop_lenovo5 = computerDeviceService.findByName("Lenovo IdeaPad 5 14IAU7 82SD00Q8VN");
        createAndSaveImage(laptop_lenovo5, "lenovo.png");

        ComputerDevice laptop_lenovo6 = computerDeviceService.findByName("Lenovo Yoga 7 14IRL8 83BR001VVN");
        createAndSaveImage(laptop_lenovo6, "lenovo.png");

        ComputerDevice laptop_lenovo7 = computerDeviceService.findByName("Lenovo ThinkBook 14 G4 IAP 21DH003RVN");
        createAndSaveImage(laptop_lenovo7, "lenovo.png");

        ComputerDevice laptop_lenovo8 = computerDeviceService.findByName("Lenovo ThinkPad E14 Gen 5 21JK001MVN");
        createAndSaveImage(laptop_lenovo8, "lenovo.png");

        ComputerDevice laptop_lenovo9 = computerDeviceService.findByName("Lenovo Yoga Slim 6 14APU8 82X3007JVN");
        createAndSaveImage(laptop_lenovo9, "lenovo.png");

        ComputerDevice laptop_lenovo10 = computerDeviceService.findByName("Lenovo V14 G4 IAH 83A0006CVN");
        createAndSaveImage(laptop_lenovo10, "lenovo.png");

        //anh msi
        ComputerDevice laptop_msi1 = computerDeviceService.findByName("MSI Modern 15 B12M-047VN");
        createAndSaveImage(laptop_msi1, "msi1.png");

        ComputerDevice laptop_msi2 = computerDeviceService.findByName("MSI Katana 15 B13VEK-254VN");
        createAndSaveImage(laptop_msi2, "msi1.png");

        ComputerDevice laptop_msi3 = computerDeviceService.findByName("MSI GF63 Thin 11UC-443VN");
        createAndSaveImage(laptop_msi3, "msi1.png");

        ComputerDevice laptop_msi4 = computerDeviceService.findByName("MSI Modern 14 C11M-020VN");
        createAndSaveImage(laptop_msi4, "msi1.png");

        ComputerDevice laptop_msi5 = computerDeviceService.findByName("MSI Prestige 14 Evo B13M-601VN");
        createAndSaveImage(laptop_msi5, "msi1.png");

        ComputerDevice laptop_msi6 = computerDeviceService.findByName("MSI Modern 14 C7M-212VN");
        createAndSaveImage(laptop_msi6, "msi1.png");

        ComputerDevice laptop_msi7 = computerDeviceService.findByName("MSI Vector GP66 12UGS-643VN");
        createAndSaveImage(laptop_msi7, "msi.png");

        ComputerDevice laptop_msi8 = computerDeviceService.findByName("MSI Katana 17 B12VEK-434VN");
        createAndSaveImage(laptop_msi8, "msi.png");

        ComputerDevice laptop_msi9 = computerDeviceService.findByName("MSI Modern 15 B12M-251VN");
        createAndSaveImage(laptop_msi9, "msi.png");

        ComputerDevice laptop_msi10 = computerDeviceService.findByName("MSI Stealth 15 A13VF-050VN");
        createAndSaveImage(laptop_msi10, "msi.png");

    }


    // Helper method to create and save computer image
    private void createAndSaveImage(ComputerDevice device, String imageUrl) {
        if (device != null) {
            // Giả sử bạn có phương thức trong computerImageService kiểm tra ảnh đã tồn tại:
            boolean exists = computerImageService.existsByComputerAndImageUrl(device, imageUrl);

            if (!exists) {
                ComputerImage img = new ComputerImage();
                img.setComputer(device);
                img.setImageUrl(imageUrl);
                img.setPrimary(true);
                computerImageService.save(img);
            } else {
                System.out.println("Image already exists for device: " + device.getName());
            }
        } else {
            System.out.println("Device not found for image: " + imageUrl);
        }
    }



}
