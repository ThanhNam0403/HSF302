// src/main/java/sum25/hsf302/sedo/controller/CartController.java
    package sum25.hsf302.sedo.controller;

    import jakarta.servlet.http.HttpSession;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import sum25.hsf302.sedo.pojo.Cart;
    import sum25.hsf302.sedo.pojo.CartDetail;
    import sum25.hsf302.sedo.pojo.User;
    import sum25.hsf302.sedo.service.CartService;
    import sum25.hsf302.sedo.service.CartDetailService;

    import java.math.BigDecimal;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    @Controller
    @RequestMapping("/cart")
    public class CartController {

        @Autowired
        private CartService cartService;

        @Autowired
        private CartDetailService cartDetailService;

        @GetMapping
        public String showCartPage(HttpSession session, Model model) {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return "redirect:/login";
            }

            Cart cart = cartService.getOrCreateCart(user);
            List<CartDetail> cartItems = cartDetailService.findAllByCart(cart);

            BigDecimal cartSubtotal = cartItems.stream()
                    .map(item -> item.getPriceAtAddToCart().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal shippingCost = cartSubtotal.compareTo(BigDecimal.valueOf(100)) >= 0
                    ? BigDecimal.ZERO
                    : BigDecimal.valueOf(10);

            BigDecimal taxAmount = cartSubtotal.multiply(BigDecimal.valueOf(0.10));
            BigDecimal cartTotal = cartSubtotal.add(shippingCost).add(taxAmount);

            model.addAttribute("cartItems", cartItems);
            model.addAttribute("cartSubtotal", cartSubtotal);
            model.addAttribute("shippingCost", shippingCost);
            model.addAttribute("taxAmount", taxAmount);
            model.addAttribute("cartTotal", cartTotal);

            return "cart";
        }

        @PostMapping("/update")
        @ResponseBody
        public Map<String, Object> updateCartItem(@RequestBody Map<String, Object> payload, HttpSession session) {
            Map<String, Object> response = new HashMap<>();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.put("success", false);
                response.put("message", "Not logged in");
                return response;
            }
            Long itemId = Long.valueOf(payload.get("itemId").toString());
            int quantity = Integer.parseInt(payload.get("quantity").toString());
            cartService.updateCartItem(user, itemId, quantity);
            response.put("success", true);
            return response;
        }

        @PostMapping("/remove")
        @ResponseBody
        public Map<String, Object> removeCartItem(@RequestBody Map<String, Object> payload, HttpSession session) {
            Map<String, Object> response = new HashMap<>();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.put("success", false);
                response.put("message", "Not logged in");
                return response;
            }
            Long itemId = Long.valueOf(payload.get("itemId").toString());
            cartService.removeCartItem(user, itemId);
            response.put("success", true);
            return response;
        }

        @PostMapping("/clear")
        @ResponseBody
        public Map<String, Object> clearCart(HttpSession session) {
            Map<String, Object> response = new HashMap<>();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.put("success", false);
                response.put("message", "Not logged in");
                return response;
            }
            cartService.clearCart(user);
            response.put("success", true);
            return response;
        }
        @PostMapping("/add")
        public String addToCart(
                @RequestParam("productId") Long productId,
                @RequestParam("quantity") int quantity,
                HttpSession session
        ) {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return "redirect:/login";
            }

            cartService.addToCart(user, productId, quantity);
            return "redirect:/cart";
        }

    }