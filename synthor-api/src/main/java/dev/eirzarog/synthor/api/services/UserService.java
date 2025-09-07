package dev.eirzarog.synthor.api.services;

import dev.eirzarog.synthor.api.enums.UserRole;
import dev.eirzarog.synthor.api.enums.UserStatus;
import dev.eirzarog.synthor.api.exceptions.GlobalException;
import dev.eirzarog.synthor.api.models.User;
import dev.eirzarog.synthor.api.models.dtos.requests.UserCreateRequest;
import dev.eirzarog.synthor.api.models.dtos.requests.UserUpdateRequest;
import dev.eirzarog.synthor.api.models.dtos.responses.UserResponse;
import dev.eirzarog.synthor.api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
//@RequiredArgsConstructor is Lombok's auto injection for final fields
public class UserService   {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        logger.debug("UserService initialized with UserRepository.");
    }

    public boolean isUsernameAvailable(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return !userRepository.existsByUsername(username);
    }

    public boolean isEmailAvailable(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return !userRepository.existsByEmail(email);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User createUser(UserCreateRequest request) throws GlobalException {

        logger.info("Creating new user with username: {}", request.getUsername());

        // Create new user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
//              .password(passwordEncoder.encode(request.getPassword()))
                .password(request.getPassword())
        // Set defaults for null values or use @builder.Default in entity
//                .status(request.getStatus() != null ? UserStatus.valueOf(request.getStatus()) : UserStatus.ACTIVE)
//                .role(request.getRole() != null ? UserRole.valueOf(request.getRole()) : UserRole.USER)
                .build();

        User savedUser = userRepository.save(user);
        logger.info("User created successfully with username: {}", savedUser.getUsername());

        return savedUser;
    }


    public User updateUser(Long id, UserUpdateRequest request) throws GlobalException {

        logger.info("Updating user with username: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new GlobalException(HttpStatus.BAD_REQUEST, "User not found."));

        if(request.getUsername() != null){user.setUsername(request.getUsername());}

        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getPassword() != null) {
            user.setPassword(request.getPassword());
        }
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        User updatedUser = userRepository.save(user);

        logger.info("User updated successfully with username: {}", updatedUser.getUsername());

        return updatedUser;
    }


    public void deleteUser(Long id) throws GlobalException {
        logger.info("Deleting user with id: {}", id);

        if (!userRepository.existsById(id)) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "User not found.");
        }

        userRepository.deleteById(id);
        logger.info("User deleted successfully with id: {}", id);
    }



// Using Mapper
//    public UserResponse createUser(UserRequest userRequest) {
//        User user = userMapper.toEntity(userRequest);
//        user.setCreatedAt(LocalDateTime.now());
//        User savedUser = userRepository.save(user);
//        return userMapper.toResponse(savedUser);
//    }

//    public UserResponse getUserById(Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        return userMapper.toResponse(user);
//    }



/*
    // Idempotent method to update or create a user
    public User updateOrCreateUser(Long userId, UserRequest userRequest) {
        // Αυτό είναι idempotent - αντικαθιστά πλήρως τα δεδομένα
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            // Update existing user
            user.setFirstName(userRequest.getFirstName());
            user.setEmail(userRequest.getEmail());
        } else {
            // Create new user with specific ID
            user = new User(userId, userRequest.getFirstName(),userRequest.getEmail());
        }

        return userRepository.save(user);
    }
*/


/*  @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        return user;
    }
*/

//    public User getLoggedInUser(Authentication authentication) throws GlobalException {
//        User loggedInUser = (User) authentication.getPrincipal();
//        return loggedInUser;
//    }



}
