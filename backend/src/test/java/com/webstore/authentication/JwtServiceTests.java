//package com.webstore.authentication;
//
//@SpringBootTest
//public class JwtServiceTests {
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private UserService userService;
//
//    @Test
//    public void testGenerateAndValidateToken() {
//        UserDetails userDetails = userService.loadUserByUsername("user");
//        String token = jwtService.generateToken(userDetails);
//
//        assertTrue(jwtService.isTokenValid(token, userDetails));  // Проверка валидности токена
//    }
//
//    @Test
//    public void testInvalidToken() {
//        UserDetails userDetails = userService.loadUserByUsername("user");
//        String invalidToken = "invalid.token.string";
//
//        assertFalse(jwtService.isTokenValid(invalidToken, userDetails));  // Проверка, что токен недействителен
//    }
//
//    @Test
//    public void testExtractUsernameFromToken() {
//        UserDetails userDetails = userService.loadUserByUsername("user");
//        String token = jwtService.generateToken(userDetails);
//
//        String usernameFromToken = jwtService.extractUserName(token);
//        assertEquals(userDetails.getUsername(), usernameFromToken);  // Проверка, что username корректен
//    }
//}
