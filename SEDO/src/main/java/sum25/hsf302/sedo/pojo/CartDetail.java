package sum25.hsf302.sedo.pojo;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_details")
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

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



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
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

    public BigDecimal getPriceAtAddToCart() {
        return priceAtAddToCart;
    }

    public void setPriceAtAddToCart(BigDecimal priceAtAddToCart) {
        this.priceAtAddToCart = priceAtAddToCart;
    }
}
