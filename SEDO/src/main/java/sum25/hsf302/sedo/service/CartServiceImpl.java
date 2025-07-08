package sum25.hsf302.sedo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sum25.hsf302.sedo.pojo.*;
import sum25.hsf302.sedo.repository.*;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ComputerDeviceRepository computerDeviceRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Override
    @Transactional
    public void addToCart(User user, Long productId, int quantity) {
        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        ComputerDevice product = computerDeviceRepository.findById(productId).orElse(null);
        if (product == null) return;

        Optional<CartDetail> existingDetail = cartDetailRepository.findByCartAndComputer(cart, product);
        CartDetail detail;
        if (existingDetail.isPresent()) {
            detail = existingDetail.get();
            detail.setQuantity(detail.getQuantity() + quantity);
        } else {
            detail = new CartDetail();
            detail.setCart(cart);
            detail.setComputer(product);
            detail.setQuantity(quantity);
            detail.setPriceAtAddToCart(product.getPrice());
        }

        cartDetailRepository.save(detail);
    }


    @Override
    @Transactional
    public void updateCartItem(User user, Long itemId, int quantity) {
        Optional<CartDetail> detailOpt = cartDetailRepository.findById(itemId);
        detailOpt.ifPresent(detail -> {
            if (detail.getCart().getUser().getId().equals(user.getId())) {
                detail.setQuantity(quantity);
                cartDetailRepository.save(detail);
            }
        });
    }


    @Override
    @Transactional
    public void removeCartItem(User user, Long itemId) {
        Optional<CartDetail> detailOpt = cartDetailRepository.findById(itemId);
        detailOpt.ifPresent(detail -> {
            if (detail.getCart().getUser().getId().equals(user.getId())) {
                cartDetailRepository.delete(detail);
            }
        });
    }


    @Override
    @Transactional
    public void clearCart(User user) {
        Cart cart = cartRepository.findById(user.getId()).orElse(null);
        if (cart != null) {
            cartDetailRepository.deleteByCart(cart);
        }
    }

    @Override
    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }

    @Override
    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user)
                .orElse(null);
    }


}