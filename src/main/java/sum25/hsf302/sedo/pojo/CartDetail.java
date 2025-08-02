package sum25.hsf302.sedo.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_details")
@Getter
@Setter
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "computer_id", nullable = false)
    private ComputerDevice computer;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    private ComputerVariant variant;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price_at_add_to_cart", nullable = false)
    private BigDecimal priceAtAddToCart;

    @Column(name = "type")
    private String type;

    // 👇 Đây là getter bổ sung để Thymeleaf dùng được




    @Transient
    public BigDecimal getTotalPrice() {
        System.out.println("getTotalPrice() called for cart item id: " + this.id);
        return priceAtAddToCart.multiply(BigDecimal.valueOf(quantity));
    }
}
