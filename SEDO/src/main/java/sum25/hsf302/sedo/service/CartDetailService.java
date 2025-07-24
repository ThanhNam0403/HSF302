// src/main/java/sum25/hsf302/sedo/service/CartDetailService.java
package sum25.hsf302.sedo.service;

import sum25.hsf302.sedo.pojo.Cart;
import sum25.hsf302.sedo.pojo.CartDetail;
import sum25.hsf302.sedo.pojo.ComputerDevice;
import sum25.hsf302.sedo.pojo.User;

import java.util.List;
import java.util.Optional;

public interface CartDetailService {
    Optional<CartDetail> findByCartAndComputer(Cart cart, ComputerDevice computer);
    void deleteByCart(Cart cart);
    List<CartDetail> findAllByCart(Cart cart);
    List<CartDetail> getCartDetailsByCart(Cart cart);
    void clearCartDetails(Cart cart);
    List<CartDetail> getCartDetailsByIds(List<Long> itemIds);

    void removeCartDetailsByIds(List<Long> selectedIds, User user);
    CartDetail getCartDetailById(Long id);
}