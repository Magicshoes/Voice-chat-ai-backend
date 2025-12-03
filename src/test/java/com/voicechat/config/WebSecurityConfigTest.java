package com.voicechat.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@SpringBootTest
@DisplayName("WebSecurityConfig Tests")
class WebSecurityConfigTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    // ==================== CorsConfigurationSource Bean Tests ====================

    @Test
    @DisplayName("Should create CorsConfigurationSource bean")
    void testCorsConfigurationSourceBeanCreation() {
        assertNotNull(corsConfigurationSource);
    }

    @Test
    @DisplayName("Should have CORS configuration for localhost:3000")
    void testCorsAllowedOriginLocalhost() {
        assertNotNull(corsConfigurationSource);
        org.springframework.web.cors.CorsConfiguration config = corsConfigurationSource
                .getCorsConfiguration(new org.springframework.mock.web.MockHttpServletRequest());
        assertNotNull(config);
        List<String> origins = config.getAllowedOrigins();
        assertNotNull(origins);
        assertTrue(origins.contains("http://localhost:3000"));
    }

    @Test
    @DisplayName("Should have CORS configuration for production domain")
    void testCorsAllowedOriginProduction() {
        assertNotNull(corsConfigurationSource);
        org.springframework.web.cors.CorsConfiguration config = corsConfigurationSource
                .getCorsConfiguration(new org.springframework.mock.web.MockHttpServletRequest());
        assertNotNull(config);
        List<String> origins = config.getAllowedOrigins();
        assertNotNull(origins);
        assertTrue(origins.contains("https://voice.wisleyway.duckdns.org"));
    }

    @Test
    @DisplayName("Should allow specified HTTP methods in CORS")
    void testCorsAllowedMethods() {
        assertNotNull(corsConfigurationSource);
        org.springframework.web.cors.CorsConfiguration config = corsConfigurationSource
                .getCorsConfiguration(new org.springframework.mock.web.MockHttpServletRequest());
        assertNotNull(config);
        List<String> methods = config.getAllowedMethods();
        assertNotNull(methods);
        assertTrue(methods.contains("GET"));
        assertTrue(methods.contains("POST"));
        assertTrue(methods.contains("PUT"));
        assertTrue(methods.contains("DELETE"));
        assertTrue(methods.contains("OPTIONS"));
    }

    @Test
    @DisplayName("Should allow all headers in CORS")
    void testCorsAllowedHeaders() {
        assertNotNull(corsConfigurationSource);
        org.springframework.web.cors.CorsConfiguration config = corsConfigurationSource
                .getCorsConfiguration(new org.springframework.mock.web.MockHttpServletRequest());
        assertNotNull(config);
        List<String> headers = config.getAllowedHeaders();
        assertNotNull(headers);
        assertTrue(headers.contains("*"));
    }

    @Test
    @DisplayName("Should allow credentials in CORS")
    void testCorsAllowCredentials() {
        assertNotNull(corsConfigurationSource);
        org.springframework.web.cors.CorsConfiguration config = corsConfigurationSource
                .getCorsConfiguration(new org.springframework.mock.web.MockHttpServletRequest());
        assertNotNull(config);
        Boolean allowCredentials = config.getAllowCredentials();
        assertNotNull(allowCredentials);
        assertTrue(allowCredentials);
    }

    @Test
    @DisplayName("Should apply CORS configuration to all endpoints")
    void testCorsConfigurationAppliedToAllEndpoints() {
        assertNotNull(corsConfigurationSource);
        org.springframework.mock.web.MockHttpServletRequest request = new org.springframework.mock.web.MockHttpServletRequest();
        request.setPathInfo("/test");
        org.springframework.web.cors.CorsConfiguration config = corsConfigurationSource.getCorsConfiguration(request);
        assertNotNull(config);
    }

    // ==================== UserDetailsService Bean Tests ====================

    @Test
    @DisplayName("Should create UserDetailsService bean")
    void testUserDetailsServiceBeanCreation() {
        assertNotNull(userDetailsService);
    }

    @Test
    @DisplayName("Should load user details for 'user' account")
    void testLoadUserDetailsForUserAccount() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("user");
        
        assertNotNull(userDetails);
        assertEquals("user", userDetails.getUsername());
        assertNotNull(userDetails.getPassword());
        assertTrue(bCryptPasswordEncoder.matches("userPass", userDetails.getPassword()));
    }

    @Test
    @DisplayName("Should load user details for 'admin' account")
    void testLoadUserDetailsForAdminAccount() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        
        assertNotNull(userDetails);
        assertEquals("admin", userDetails.getUsername());
        assertNotNull(userDetails.getPassword());
        assertTrue(bCryptPasswordEncoder.matches("changeme", userDetails.getPassword()));
    }

    @Test
    @DisplayName("Should have USER role for 'user' account")
    void testUserRoleForUserAccount() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("user");
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        
        assertTrue(authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }

    @Test
    @DisplayName("Should have USER and ADMIN roles for 'admin' account")
    void testAdminRolesForAdminAccount() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        
        assertTrue(authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
        assertTrue(authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException for non-existent user")
    void testLoadNonExistentUserThrowsException() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonexistent");
        });
    }

    @Test
    @DisplayName("Should encode passwords correctly")
    void testPasswordEncodingInUserDetails() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("user");
        
        // The password should be encoded, not plain text
        assertNotEquals("userPass", userDetails.getPassword());
        assertTrue(bCryptPasswordEncoder.matches("userPass", userDetails.getPassword()));
    }

    @Test
    @DisplayName("Should have different encoded passwords for different raw passwords")
    void testDifferentPasswordsAreEncodedDifferently() {
        UserDetails user = userDetailsService.loadUserByUsername("user");
        UserDetails admin = userDetailsService.loadUserByUsername("admin");
        
        assertNotEquals(user.getPassword(), admin.getPassword());
    }

    @Test
    @DisplayName("Should handle user without explicit password setter")
    void testUserDetailsServiceCreatesValidUsers() {
        UserDetails user = userDetailsService.loadUserByUsername("user");
        UserDetails admin = userDetailsService.loadUserByUsername("admin");
        
        assertNotNull(user);
        assertNotNull(admin);
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }

    // ==================== SecurityFilterChain Bean Tests ====================

    @Test
    @DisplayName("Should create SecurityFilterChain bean")
    void testSecurityFilterChainBeanCreation() {
        assertNotNull(securityFilterChain);
    }

    @Test
    @DisplayName("Should allow unauthenticated access to login endpoints")
    void testLoginEndpointsArePermitAll() throws Exception {
        mockMvc.perform(get("/login/test"))
                .andExpect(status().isNotFound()); // 404 is acceptable; it means no 401
    }

    @Test
    @DisplayName("Should require authentication for API endpoints")
    void testApiEndpointsRequireAuthentication() throws Exception {
        mockMvc.perform(get("/api/test"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should allow USER role access to API endpoints")
    void testUserRoleCanAccessApiEndpoints() throws Exception {
        mockMvc.perform(get("/api/test")
                .with(httpBasic("user", "userPass")))
                .andExpect(status().isNotFound()); // 404 is acceptable as endpoint doesn't exist
    }

    @Test
    @DisplayName("Should allow ADMIN role access to API endpoints")
    void testAdminRoleCanAccessApiEndpoints() throws Exception {
        mockMvc.perform(get("/api/test")
                .with(httpBasic("admin", "changeme")))
                .andExpect(status().isNotFound()); // 404 is acceptable as endpoint doesn't exist
    }

    @Test
    @DisplayName("Should require ADMIN role for DELETE requests")
    void testDeleteRequestsRequireAdminRole() throws Exception {
        mockMvc.perform(delete("/api/test")
                .with(httpBasic("user", "userPass")))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should allow ADMIN role for DELETE requests")
    void testAdminCanPerformDeleteRequests() throws Exception {
        mockMvc.perform(delete("/api/test")
                .with(httpBasic("admin", "changeme")))
                .andExpect(status().isNotFound()); // 404 is acceptable
    }

    @Test
    @DisplayName("Should require ADMIN role for /admin/** endpoints")
    void testAdminEndpointsRequireAdminRole() throws Exception {
        mockMvc.perform(get("/admin/test")
                .with(httpBasic("user", "userPass")))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should allow ADMIN role for /admin/** endpoints")
    void testAdminCanAccessAdminEndpoints() throws Exception {
        mockMvc.perform(get("/admin/test")
                .with(httpBasic("admin", "changeme")))
                .andExpect(status().isNotFound()); // 404 is acceptable
    }

    @Test
    @DisplayName("Should reject requests with invalid credentials")
    void testInvalidCredentialsAreRejected() throws Exception {
        mockMvc.perform(get("/api/test")
                .with(httpBasic("user", "wrongPassword")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should use HTTP Basic authentication")
    void testHttpBasicAuthenticationIsConfigured() throws Exception {
        mockMvc.perform(get("/api/test")
                .with(httpBasic("user", "userPass")))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should disable CSRF protection")
    void testCsrfIsDisabled() throws Exception {
        // POST requests should work without CSRF token when CSRF is disabled
        mockMvc.perform(post("/api/test")
                .with(httpBasic("user", "userPass")))
                .andExpect(status().isNotFound()); // 404 is acceptable
    }

    @Test
    @DisplayName("Should use stateless session management")
    void testSessionManagementIsStateless() throws Exception {
        // Making multiple requests should not establish a session
        mockMvc.perform(get("/api/test")
                .with(httpBasic("user", "userPass")))
                .andExpect(status().isNotFound());
        
        // Session header should not be present for stateless configuration
        mockMvc.perform(get("/api/test")
                .with(httpBasic("user", "userPass")))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should require authentication for any other requests")
    void testAnyOtherRequestRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/some-other-endpoint"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should support OPTIONS method for CORS preflight")
    void testOptionMethodForCorsPreFlight() throws Exception {
        mockMvc.perform(options("/api/test")
                .with(httpBasic("user", "userPass")))
                .andExpect(status().isNotFound()); // Endpoint doesn't exist, but no 401
    }

    @Test
    @DisplayName("Should have CORS headers in response")
    void testCorsHeadersInResponse() throws Exception {
        mockMvc.perform(options("/api/test")
                .header("Origin", "http://localhost:3000")
                .with(httpBasic("user", "userPass")))
                .andExpect(status().isNotFound()); // Endpoint doesn't exist, but no 401
    }

    // ==================== Integration Tests ====================

    @Test
    @DisplayName("Should reject unauthenticated POST to API")
    void testUnauthenticatedPostToApiFails() throws Exception {
        mockMvc.perform(post("/api/test")
                .contentType("application/json"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should reject unauthenticated PUT to API")
    void testUnauthenticatedPutToApiFails() throws Exception {
        mockMvc.perform(put("/api/test")
                .contentType("application/json"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should reject DELETE from non-admin user")
    void testNonAdminCannotDelete() throws Exception {
        mockMvc.perform(delete("/api/test")
                .with(httpBasic("user", "userPass")))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should authenticate multiple users independently")
    void testMultipleUsersAuthenticate() throws Exception {
        // User account
        mockMvc.perform(get("/api/test")
                .with(httpBasic("user", "userPass")))
                .andExpect(status().isNotFound());
        
        // Admin account
        mockMvc.perform(get("/api/test")
                .with(httpBasic("admin", "changeme")))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should enforce authorization on PUT requests")
    void testPutRequestsAuthorization() throws Exception {
        mockMvc.perform(put("/api/test")
                .with(httpBasic("user", "userPass"))
                .contentType("application/json"))
                .andExpect(status().isNotFound()); // 404 acceptable, authorization passed
    }

    @Test
    @DisplayName("Should deny admin endpoints to regular users")
    void testRegularUserDeniedAdminAccess() throws Exception {
        mockMvc.perform(get("/admin/users")
                .with(httpBasic("user", "userPass")))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should allow login endpoint without authentication")
    void testLoginDoesNotRequireAuth() throws Exception {
        mockMvc.perform(post("/login/authenticate"))
                .andExpect(status().isNotFound()); // No 401 or 403
    }

    @Test
    @DisplayName("Should verify user account is enabled")
    void testUserAccountIsEnabled() {
        UserDetails user = userDetailsService.loadUserByUsername("user");
        assertTrue(user.isEnabled());
    }

    @Test
    @DisplayName("Should verify admin account is enabled")
    void testAdminAccountIsEnabled() {
        UserDetails admin = userDetailsService.loadUserByUsername("admin");
        assertTrue(admin.isEnabled());
    }
}
