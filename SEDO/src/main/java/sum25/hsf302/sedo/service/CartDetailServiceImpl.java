// src/main/java/sum25/hsf302/sedo/service/CartDetailServiceImpl.java
package sum25.hsf302.sedo.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sum25.hsf302.sedo.pojo.Cart;
import sum25.hsf302.sedo.pojo.CartDetail;
import sum25.hsf302.sedo.pojo.ComputerDevice;
import sum25.hsf302.sedo.pojo.User;
import sum25.hsf302.sedo.repository.CartDetailRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Override
    public Optional<CartDetail> findByCartAndComputer(Cart cart, ComputerDevice computer) {
        return cartDetailRepository.findByCartAndComputer(cart, computer);
    }

    @Override
    public void deleteByCart(Cart cart) {
        cartDetailRepository.deleteByCart(cart);
    }

    @Override
    public List<CartDetail> findAllByCart(Cart cart) {
        return cartDetailRepository.findAll()
                .stream()
                .filter(cd -> cd.getCart().getId().equals(cart.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CartDetail> getCartDetailsByCart(Cart cart) {
        return cartDetailRepository.findByCart(cart);
    }

    @Override
    public void clearCartDetails(Cart cart) {
        List<CartDetail> details = cartDetailRepository.findByCart(cart);
        cartDetailRepository.deleteAll(details);
    }

    @Override
    public List<CartDetail> getCartDetailsByIds(List<Long> itemIds) {
        return cartDetailRepository.findAllById(itemIds);
    }

    @Transactional
    @Override
    public void removeCartDetailsByIds(List<Long> selectedIds, User user) {
        cartDetailRepository.deleteByIdInAndCartUser(selectedIds, user);
    }

    @Override
    public CartDetail getCartDetailById(Long id) {
        return cartDetailRepository.findById(id).orElse(null);
    }
}