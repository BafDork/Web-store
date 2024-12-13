import { createRouter, createWebHistory } from 'vue-router';
import { jwtDecode } from 'jwt-decode';
import AuthService from '@/services/AuthService';
import store from '@/store';


const routes = [
  { path: '/', name: 'catalog', component: () => import('@/components/catalog/Catalog.vue') },
  { path: '/product/:id', name: 'product-page', component: () => import('@/components/ProductPage.vue'), props: true },
  { path: '/auth/sign-in', name: 'sign-in', component: () => import('@/components/authentication/SignIn.vue') },
  { path: '/auth/sign-up', name: 'sign-up', component: () => import('@/components/authentication/SignUp.vue') },
  { path: '/cart', name: 'cart', component: () => import('@/components/cart/Cart.vue'), meta: { requiresAuth: true } },
  { path: '/admin/add-category', name: 'add-category', component: () => import('@/components/admin/AddCategoryForm.vue'), meta: { requiresAuth: true, requiresAdmin: true } }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach(async (to, from, next) => {
  const token = AuthService.getToken();

  if (to.meta.requiresAuth || to.meta.requiresAdmin) {
    if (token) {
      if (!store.getters['user/getUser']) {
        try {
          await store.dispatch('user/loadUser');
        } catch (error) {
          if (error.response?.status === 401) {
            localStorage.removeItem('token');
            return next('/auth/sign-in');
          }
          console.error(error);
        }
      }
      try {
        const decodedToken = jwtDecode(token);
        const userRole = decodedToken.role;
        
        if (userRole !== 'ROLE_ADMIN') {
          return next('/catalog');
        }
      } catch (error) {
        console.error('Ошибка при декодировании токена:', error);
        return next('/auth/sign-in');
      }
    } else {
      return next('/auth/sign-in');
    }
  }
  next();
});

export default router;
