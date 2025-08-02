package sum25.hsf302.sedo.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 4, max = 20, message = "Tên đăng nhập phải từ 4 đến 20 ký tự")
    @Column(nullable = false, name = "username", unique = true, columnDefinition = "nvarchar(100)")
    private String username;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0[0-9]{9})$", message = "Số điện thoại không hợp lệ (ví dụ: 0123456789)")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 100, message = "Họ tên không vượt quá 100 ký tự")
    @Column(name = "full_name", nullable = false, columnDefinition = "nvarchar(100)")
    private String fullName;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(max = 100, message = "Địa chỉ không vượt quá 100 ký tự")
    @Column(name = "address", nullable = false, columnDefinition = "nvarchar(100)")
    private String address;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt = LocalDate.now();

    private LocalDate updatedAt = LocalDate.now();

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "user")
    private List<PCBuild> pcBuilds;

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
