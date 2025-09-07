package dev.eirzarog.synthor.api.controllers;

import dev.eirzarog.synthor.api.models.User;
import dev.eirzarog.synthor.api.models.dtos.requests.UserCreateRequest;
import dev.eirzarog.synthor.api.models.dtos.requests.UserUpdateRequest;
import dev.eirzarog.synthor.api.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        logger.debug("UserController initialized with UserService.");
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("users/{username}")
    public ResponseEntity<Optional<User>> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserCreateRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


/*
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/role")
    public ResponseEntity<User> updateUserRole(
            @PathVariable Long id,
            @RequestParam UserRole role) {
        //User updatedUser = userService.updateRole(id, role);
        //return ResponseEntity.ok(updatedUser);
        return ResponseEntity.ok(null);
    }
*/


//    @PostMapping("/users")
//    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUser createUser) {
//        // Μετατροπή DTO σε Entity
//        User user = new User();
//        user.setUsername(createUser.getUsername());
//        user.setEmail(createUser.getEmail());
//        user.setPassword(passwordEncoder.encode(createUser.getPassword()));
//        user.setCreatedAt(LocalDateTime.now());
//        user.setStatus(UserStatus.INACTIVE);
//
//        User savedUser = userService.save(user);
//
//        // Μετατροπή Entity σε Response DTO
//        UserResponse responseDTO = new UserResponse();
//        responseDTO.setId(savedUser.getId());
//        responseDTO.setUsername(savedUser.getUsername());
//        responseDTO.setEmail(savedUser.getEmail());
//        responseDTO.setCreatedAt(savedUser.getCreatedAt());
//
//        return ResponseEntity.ok(responseDTO);
//    }


/*
    @GetMapping("/{username}")
    public User getUserByUserName(@PathVariable String username, Authentication authentication) throws GlobalException {
        User loggedInUser = userService.getLoggedInUser(authentication);

        boolean isAdmin = "ROLE_ADMIN".equals(loggedInUser.getRole());
        boolean isSelf = username.equals(loggedInUser.getUsername());

        if (!(isAdmin || isSelf)) {
            throw new GlobalException(HttpStatus.FORBIDDEN, "You are not allowed to access this resource.");
        }

        return userService.getUserByUsername(username);
    }
    */
}
