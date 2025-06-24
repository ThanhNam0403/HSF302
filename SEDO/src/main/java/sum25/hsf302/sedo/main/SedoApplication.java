package sum25.hsf302.sedo.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import sum25.hsf302.sedo.pojo.Role;
import sum25.hsf302.sedo.pojo.User;
import sum25.hsf302.sedo.service.RoleService;
import sum25.hsf302.sedo.service.UserService;

import java.time.LocalDate;

@SpringBootApplication
@ComponentScan(basePackages = "sum25.hsf302.sedo")
@EntityScan("sum25.hsf302.sedo.pojo")
@EnableJpaRepositories("sum25.hsf302.sedo.repository")
public class SedoApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    public static void main(String[] args) {
        SpringApplication.run(SedoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (userService.count() == 0) {
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