import { createRouter, createWebHistory } from 'vue-router';
import Catalog from '@/views/catalog/Catalog.vue';
import SingIn from '@/views/SingIn.vue';
import SingUp from '@/views/SingUp.vue';

const routes = [
  { path: '/', name: 'Catalog', component: Catalog },
  { path: '/auth/sing-in', name: 'SingIn', component: SingIn },
  { path: '/auth/sing-up', name: 'SingUp', component: SingUp },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
