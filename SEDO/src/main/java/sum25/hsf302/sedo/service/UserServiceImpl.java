package sum25.hsf302.sedo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sum25.hsf302.sedo.pojo.User;
import jakarta.servlet.http.HttpSession;
import sum25.hsf302.sedo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @Override
    public Page<User> searchByFullName(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return userRepository.findAll(pageable);
        }
        String searchKeyword = "%" + keyword.trim() + "%";
        return userRepository.findByFullNameLike(searchKeyword, pageable);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setEmail(user.getEmail());
            existingUser.setFullName(user.getFullName());
            existingUser.setActive(user.getActive());
            existingUser.setRole(user.getRole());
            return userRepository.save(existingUser);
        }
        return null; // or throw an exception if user not found
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public int countActiveUsers() {
        return userRepository.countByIsActiveTrue();
    }

    @Override
    public User validateUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElse(null);
        System.out.println("Try login with: " + email + " / " + password);
        if (user != null) {
            System.out.println("User found: " + user.getEmail() + ", Active: " + user.getActive());
            if (user.getPassword().equals(password)) {
                System.out.println("Password match!");
                System.out.println("Password trong DB là: " + user.getPassword());
            } else {
                System.out.println("Password mismatch!");
            }
        } else {
            System.out.println("User not found");
        }

        if (user != null && user.getPassword().equals(password) && user.getActive()) {
            session.setAttribute("userId", user.getId());
            return user;
        }
        return null;
    }


    @Override
    public boolean rememberUser(String email) {
        User user = findByEmail(email);
        if (user != null) {
            session.setAttribute("rememberedUser", email);
            session.setMaxInactiveInterval(7 * 24 * 60 * 60); // 7 days
            return true;
        }
        return false;
    }


    @Override
    public void logout() {
        session.removeAttribute("userId");
        session.removeAttribute("rememberedUser");
        session.invalidate();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}