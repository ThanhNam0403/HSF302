package sum25.hsf302.sedo.pojo;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "computer_variants")
public class ComputerVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "computer_id", nullable = false)
    private ComputerDevice computer;
    @Column(name = "name", nullable = false,columnDefinition = "nvarchar(100)")
    private String name;
    @Column(name = "value", nullable = false)
    private String value;
    @Column(name = "additional_price", nullable = false)
    private BigDecimal additionalPrice = BigDecimal.ZERO;
    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public ComputerDevice getComputer() {
        return computer;
    }

    public void setComputer(ComputerDevice computer) {
        this.computer = computer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BigDecimal getAdditionalPrice() {
        return additionalPrice;
    }

    public void setAdditionalPrice(BigDecimal additionalPrice) {
        this.additionalPrice = additionalPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
