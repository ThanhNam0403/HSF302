package sum25.hsf302.sedo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sum25.hsf302.sedo.pojo.*;
import sum25.hsf302.sedo.repository.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
            detail.setType("product"); // 🟢 SET TYPE tại đây

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
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cartDetailRepository.deleteByCart(cart);
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
    @Override
    public void addBuildToCart(User user, PCBuild build) {
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        List<ComputerDevice> components = new ArrayList<>();
        if (build.getCpu() != null) components.add(build.getCpu());
        if (build.getMainboard() != null) components.add(build.getMainboard());
        if (build.getRam() != null) components.add(build.getRam());
        if (build.getGpu() != null) components.add(build.getGpu());
        if (build.getHdd() != null) components.add(build.getHdd());
        if (build.getSsd() != null) components.add(build.getSsd());
        if (build.getPsu() != null) components.add(build.getPsu());
        if (build.getPcCase() != null) components.add(build.getPcCase());
        if (build.getCooling() != null) components.add(build.getCooling());
        if (build.getMonitor() != null) components.add(build.getMonitor());
        if (build.getKeyboard() != null) components.add(build.getKeyboard());
        if (build.getMouse() != null) components.add(build.getMouse());
        if (build.getHeadphones() != null) components.add(build.getHeadphones());
        if (build.getSpeakers() != null) components.add(build.getSpeakers());

        for (ComputerDevice device : components) {
            CartDetail item = new CartDetail();
            item.setCart(cart);
            item.setComputer(device);
            item.setQuantity(1);
            item.setPriceAtAddToCart(device.getPrice());
            item.setType("pc-build");

            cartDetailRepository.save(item);
        }

    }

    @Override
    @Transactional
    public void removePcPart(User user, Long computerId) {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Lấy tất cả cartDetail theo cart
        List<CartDetail> cartDetails = cartDetailRepository.findByCart(cart);

        for (CartDetail detail : cartDetails) {
            if (detail.getComputer() != null && detail.getComputer().getId().equals(computerId)) {
                cartDetailRepository.delete(detail);
                break; // Chỉ xoá 1 phần
            }
        }
    }





    @Override
    @Transactional
    public void removeProductFromCart(User user, Long itemId) {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartDetail detail = cartDetailRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!detail.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Item does not belong to user's cart");
        }

        cartDetailRepository.delete(detail);
    }




}