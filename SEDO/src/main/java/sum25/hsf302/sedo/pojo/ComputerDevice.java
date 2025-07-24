package sum25.hsf302.sedo.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "computer_devices")
@Getter
@Setter
public class ComputerDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "description", columnDefinition = "nvarchar(100)", nullable = false)
    private String description;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "computer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ComputerImage> computerImages;

    @OneToMany(mappedBy = "computer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ComputerVariant> computerVariants;

    @Column(name = "cpu_model")
    private String cpuModel;

    @Column(name = "mainboard_chipset")
    private String mainboardChipset;

    @Column(name = "wattage")
    private Integer wattage;// Áp dụng cho PSU

    @Column(name = "ram")
    private Integer ram;    // Áp dụng cho RAM/SSD
    @Column(name = "type")
    private String type;     // DDR4/DDR5, SSD/HDD, etc.
    @Column(name = "brand", nullable = false)
    private String brand;
    @Column(name = "model", nullable = false)
    private String model;
    @Column(name = "specifications", nullable = false, columnDefinition = "nvarchar(100)")
    private String specifications;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt = LocalDate.now();
    private LocalDate updatedAt = LocalDate.now();




}
