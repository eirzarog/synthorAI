package dev.eirzarog.synthor.api.controllers;

import dev.eirzarog.synthor.api.entities.User;
import dev.eirzarog.synthor.api.entities.criteria.UserCriteria;
import dev.eirzarog.synthor.api.entities.dtos.UserDTO;
import dev.eirzarog.synthor.api.entities.dtos.requests.user.CreateUserRequestDTO;
import dev.eirzarog.synthor.api.entities.dtos.requests.user.UpdateUserRequestDTO;
import dev.eirzarog.synthor.api.enumerators.UserRole;
import dev.eirzarog.synthor.api.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

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



//    /users?username=eirzarog → filters by username
//    /users?email=admin@example.com&username=bob → filters by both
    @GetMapping ("/users/filter")
    public List<UserDTO> getUsersByCriteria(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email
    ) {
        UserCriteria criteria = new UserCriteria();
        criteria.setUsername(username);
        criteria.setEmail(email);

        return userService.findUsersByCriteria(criteria);
    }

    // GET http://localhost:8080/users/count?date=2025-09-13
    @GetMapping("/users/count")
    public Long countUsers(@RequestParam String date) {
        return userService.getNewUserCountFromDate(date);
    }

    // Avoiding endpoint returns simply List<User>
    // Jackson sees the User objects and thinks:
    // "I need to serialize everything, including orders and department"
    // This triggers additional database queries! Fixes N+1 queries



    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(Authentication authentication) {

        // Get the username from the authentication object for the logged-in user
       User loggedInUser = (User) authentication.getPrincipal();

        // status code unauthorised 401, user not found

        if (loggedInUser.getRole() != UserRole.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // status code forbidden 403
        }

//      Did you JOIN FETCH a collection? Try adding DISTINCT.
//      Are your relationships bidirectional? Break the loop with Jackson annotations.
//      Consider switching to Set<> or returning DTOs for complete control.
//      That combination will eliminate both the repeated rows on the JPA side and the nested loops on the JSON side.


        return ResponseEntity.ok(userService.getAllUsers()); // status code ok 200
    }

    // Get user by username
    @GetMapping("users/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

//    // Create user
//    @PostMapping("/users")
//    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserRequestDTO request) {
//        return ResponseEntity.ok(userService.createUser(request));
//    }

    // Login user
//    @PostMapping("/login")
//    public ResponseEntity<String> login() {
//        return ResponseEntity.ok("Login successful");
//    }

    // Update user by Id
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequestDTO request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    // Delete user by Id
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
