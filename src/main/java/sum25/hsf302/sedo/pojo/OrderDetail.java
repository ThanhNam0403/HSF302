package sum25.hsf302.sedo.pojo;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "computer_id", nullable = false)
    private ComputerDevice computer;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private ComputerVariant variant;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ComputerDevice getComputer() {
        return computer;
    }

    public void setComputer(ComputerDevice computer) {
        this.computer = computer;
    }

    public ComputerVariant getVariant() {
        return variant;
    }

    public void setVariant(ComputerVariant variant) {
        this.variant = variant;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}

