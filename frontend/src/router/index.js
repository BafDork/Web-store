import { createRouter, createWebHistory } from 'vue-router';
import ProductList from '@/views/ProductList.vue';
import SingIn from '@/views/SingIn.vue';
import SingUp from '@/views/SingUp.vue';

const routes = [
  { path: '/', name: 'Home', component: ProductList },
  { path: '/auth/sing-in', name: 'SingIn', component: SingIn },
  { path: '/auth/sing-up', name: 'SingUp', component: SingUp },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
