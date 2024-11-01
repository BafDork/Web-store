import { createRouter, createWebHistory } from 'vue-router';
import store from '@/store';

const routes = [
  { path: '/', name: 'Catalog', component: () => import('@/components/Catalog.vue') },
  { path: '/auth/sing-in', name: 'SingIn', component: () => import('@/components/authentication/SingIn.vue') },
  { path: '/auth/sing-up', name: 'SingUp', component: () => import('@/components/authentication/SingUp.vue') },
  { path: '/cart', name: 'Cart', component: () => import('@/components/cart/Cart.vue'), meta: { requiresAuth: true } },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && !store.getters.isAuthenticated) {
    next('/auth/sing-in');
  } else {
    next();
  }
});

export default router;
