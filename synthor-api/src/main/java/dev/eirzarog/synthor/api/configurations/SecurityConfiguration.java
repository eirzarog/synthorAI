package dev.eirzarog.synthor.api.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*
Enable http basic authentication
All the  requests except login must be authenticated
e.g. when someone uses get all users I need to know who is asking this - filterchain method
spring security getting starting with basic auth - search on internet

Authentication – Identifying the user. Example: logging in with username and password.
Authorization – Giving permissions. Example: an admin can delete users, but a normal user cannot.

Protection against common attacks – Such as CSRF (Cross-Site Request Forgery),
XSS (Cross-Site Scripting), and session fixation.

The best part is that Spring Security is highly customizable. You can use basic login forms,
JWT (JSON Web Tokens), OAuth2, or even integrate with external systems like Google or Facebook login.


AUTHORITIES => ARE USER ROLES


 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    //e.g. when someone uses get all users I need to know who is asking this - filterchain method
    /*
    * CSRF stands for Cross-Site Request Forgery, and it's a sneaky type of cyberattack that exploits the trust a website has in a user's browser.
 What Happens in a CSRF Attack
Imagine you're logged into your bank account in one browser tab. In another tab, you visit a malicious site.
*  That site secretly sends a request to your bank—like transferring money—using your credentials,
* because your browser automatically includes your session cookies. The bank thinks it's you making the request,
* and boom: unauthorized action completed.
    *
    * για να βρει τον χρήστη χρειαζεται ένα service που υλοποιεί loaduserbyusername which returns userdetails
    * */

    //Define security rules
    // Authentication request sends credentials (username, password) and authentication response sends back principal object (logging in user)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers()
                        .anyRequest().authenticated() // or permitAll() for no auth
                )
                .httpBasic(Customizer.withDefaults())
                //.cors(Customizer.withDefaults())
                //.oauth2ResourceServer(auth -> auth.jwt(Customizer.withDefaults()))
                .csrf(csrf -> csrf.disable()); // Disable CSRF

        return http.build();
    }

    @Bean
    @Deprecated
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder();
    }



/*
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000")); // your React dev server
        config.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
// AuthenticationProvider for connection to databases
// LdapAuthenticationProvider authenticates against an LDAP server
// oauth2 google facebook
// bearer token in the request header jwt
 */

/* // it’s the bridge between Spring Security and user data using JWT, DaoAuthenticationProvider authenticates against a user database
    @Bean
    @Deprecated
    public DaoAuthenticationProvider daoAuthenticationProvider(AuthService authService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(authService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws JOSEException {
        com.nimbusds.jose.jwk.RSAKey rsaJwk = new RSAKeyGenerator(2048)
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID("rsa-key-1")
                .generate();

        JWKSet jwkSet = new JWKSet(rsaJwk);
        return new ImmutableJWKSet<>(jwkSet);
    }

    // 2) Expose a JwtEncoder that will sign tokens with our JWK
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) throws JOSEException {
        // 1) select the JWK with your key-ID
        JWKSelector selector = new JWKSelector(
                new JWKMatcher.Builder()
                        .keyID("rsa-key-1")
                        .keyUse(KeyUse.SIGNATURE)
                        .build()
        );
        // 2) query the source
        List<JWK> jwks = jwkSource.get(selector, null);
        if (jwks.isEmpty()) {
            throw new IllegalStateException("No RSA JWK found with keyID rsa-key-1");
        }
        com.nimbusds.jose.jwk.RSAKey rsa = (RSAKey) jwks.getFirst();

        // 3) extract the Java PublicKey
        RSAPublicKey publicKey = rsa.toRSAPublicKey();

        // 4) plug into the Nimbus decoder
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

   */

}
