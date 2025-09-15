package dev.eirzarog.synthor.api.configurations;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPublicKey;
import java.util.List;

/*
 * Enable http basic authentication
 * All the  requests except login must be authenticated
 * e.g. when someone uses get all users I need to know who is asking this - filterChain method, Authentication object
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // When someone uses get all users I need to know who is asking utilizing filterChain method
    /*
    * CSRF stands for Cross-Site Request Forgery, and it's a sneaky type of cyberattack that exploits the trust a website has in a user's browser.
    * What Happens in a CSRF Attack
    * Imagine you're logged into your bank account in one browser tab. In another tab, you visit a malicious site.
    * That site secretly sends a request to your bank—like transferring money—using your credentials,
    * because your browser automatically includes your session cookies. The bank thinks it's you making the request,
    * and boom: unauthorized action completed.
    *
    * For finding a user needs a service implementing the method loadUserByUsername which returns UserDetails
    * */

    // Define security rules
    // Authentication request sends credentials (username, password) and authentication response
    // sends back principal object (logging in user)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers()
                        .anyRequest().authenticated() // or permitAll() for no auth
                )
                .httpBasic(Customizer.withDefaults())
                //.cors(Customizer.withDefaults())
                .oauth2ResourceServer(auth -> auth.jwt(Customizer.withDefaults()))
//                .oauth2ResourceServer(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable()); // Disable CSRF

        return http.build();
    }

    @Bean
    @Deprecated
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder();
    }


    // Create asymmetric key JWK with RSA
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


    // Expose a JwtEncoder that will sign tokens with our JWK
    // Creates the JWT, particularly takes json and signs it
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    // Takes the signature and verifies that it comes from JWK and responses with the payload of JWT
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



/*  @Bean
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

    // it’s the bridge between Spring Security and user data using JWT, DaoAuthenticationProvider authenticates against a user database
    @Bean
    @Deprecated
    public DaoAuthenticationProvider daoAuthenticationProvider(AuthService authService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(authService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }*/

}
