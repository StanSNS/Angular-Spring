package com.example.backend.Service.auth;

import com.example.backend.DTO.RegisterDTO;
import com.example.backend.DTO.UserDTO;
import com.example.backend.Entity.Order;
import com.example.backend.Entity.User;
import com.example.backend.Enums.OrderStatus;
import com.example.backend.Enums.UserRole;
import com.example.backend.Repository.OrderRepository;
import com.example.backend.Repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;

    public UserDTO createUser(RegisterDTO registerDTO) {
        User user = new User();

        user.setEmail(registerDTO.getEmail());
        user.setName(registerDTO.getName());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        User save = userRepository.save(user);

        Order order = new Order();
        order.setAmount(0L);
        order.setTotalAmount(0L);
        order.setDiscount(0L);
        order.setUser(save);
        order.setOrderStatus(OrderStatus.Pending);
        orderRepository.save(order);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(save.getId());

        return userDTO;
    }

    public Boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdmin() {
        User byRole = userRepository.findByRole(UserRole.ADMIN);
        if (byRole == null) {
            User user = new User();
            user.setEmail("admin@abv.bg");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin@abv.bg"));
            userRepository.save(user);
        }
    }
}
