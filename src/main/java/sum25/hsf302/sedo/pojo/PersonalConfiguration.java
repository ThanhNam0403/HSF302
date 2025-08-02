package sum25.hsf302.sedo.pojo;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "personal_configurations")
public class PersonalConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;
    @Column(name = "config_name", nullable = false)
    private String configName;
    @Column(name = "components", nullable = false)
    private String components; // JSON string
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt = LocalDate.now();
    private LocalDate updatedAt = LocalDate.now();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}