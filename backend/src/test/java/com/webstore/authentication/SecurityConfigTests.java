//package com.webstore.authentication;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//@WithMockUser(username = "admin", roles = {"ADMIN"})
//public class SecurityConfigTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void testAdminAccess() throws Exception {
//        mockMvc.perform(get("/admin/dashboard"))
//                .andExpect(status().isOk());  // Доступ к /admin разрешен для роли ADMIN
//    }
//
//    @Test
//    public void testUserAccessDeniedToAdmin() throws Exception {
//        mockMvc.perform(get("/admin/dashboard"))
//                .andExpect(status().isForbidden());  // Доступ к /admin запрещен для обычного пользователя
//    }
//}