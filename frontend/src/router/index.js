import { createRouter, createWebHistory } from 'vue-router';
import store from '@/store';

const routes = [
  { path: '/', name: 'Catalog', component: () => import('@/components/catalog/Catalog.vue') },
  { path: '/auth/sign-in', name: 'SignIn', component: () => import('@/components/authentication/SignIn.vue') },
  { path: '/auth/sign-up', name: 'SignUp', component: () => import('@/components/authentication/SignUp.vue') },
  { path: '/cart', name: 'Cart', component: () => import('@/components/cart/Cart.vue'), meta: { requiresAuth: true } },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && !store.getters['user/isAuthenticated']) {
    next('/auth/sign-in');
  } else {
    next();
  }
});

export default router;
