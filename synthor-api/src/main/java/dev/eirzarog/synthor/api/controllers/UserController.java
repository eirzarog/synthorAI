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
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
//@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final JwtEncoder jwtEncoder;


    @Autowired
    public UserController(UserService userService, JwtEncoder jwtEncoder) {
        this.userService = userService;
        this.jwtEncoder = jwtEncoder;
        logger.debug("UserController initialized.");
    }


    // JWT auth
    @GetMapping("/login")
    public String login(Authentication authentication){
        User loggedInUser = (User) authentication.getPrincipal();


        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(loggedInUser.getUsername())
                .issuer("http://localhost:8080")
                .claim("role", loggedInUser.getRole())
                .claim("email", loggedInUser.getEmail())
                .claim("name", loggedInUser.getFirstName())
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .build();

        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(claimsSet));

        return jwt.getTokenValue();

    }

//    // Basic auth
//    @GetMapping("/login")
//    public String login(Authentication authentication){
//        User loggedInUser = (User) authentication.getPrincipal();
//        Random r =  new Random();
//        String strOptions = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//
//        String randomKey = "";
//
//        for(int i=0; i<128; i++){
//            randomKey += strOptions.charAt(r.nextInt(strOptions.length()));
//        }
//        return loggedInUser.getEmail() + randomKey;
//    }


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

        Object principal = authentication.getPrincipal();
        String username = principal instanceof Jwt jwt ? jwt.getSubject() : null;

        if (username == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        User user = userService.getUserByUsername(username);
        if (user == null || user.getRole() != UserRole.ADMIN) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.ok(userService.getAllUsers());
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
