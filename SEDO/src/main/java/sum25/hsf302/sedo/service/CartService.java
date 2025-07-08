// src/main/java/sum25/hsf302/sedo/service/CartService.java
package sum25.hsf302.sedo.service;

import sum25.hsf302.sedo.pojo.Cart;
import sum25.hsf302.sedo.pojo.User;

public interface CartService {
    void addToCart(User user, Long productId, int quantity);
    void updateCartItem(User user, Long itemId, int quantity);
    void removeCartItem(User user, Long itemId);
    void clearCart(User user);
    Cart getOrCreateCart(User user);
    Cart getCartByUser(User user);
}