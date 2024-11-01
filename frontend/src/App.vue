<template>
  <div id="app">
    <Header />
    <router-view></router-view> 
  </div>
</template>

<script>
import { mapActions } from 'vuex';
import AuthService from '@/services/AuthService';
import Header from './components/Header.vue';

export default {
  components: {
    Header,
  },
  created() {
    this.checkUser();
  },
  methods: {
    ...mapActions(['login']),
    
    async checkUser() {
      const userId = localStorage.getItem('userId');
      // if (userId) {
      //   try {
      //     const userData = await AuthService.fetchUser(userId);
      //     this.login(userData); // Сохраняем данные пользователя в Vuex
      //   } catch (error) {
      //     console.error('Ошибка при получении данных пользователя:', error);
      //   }
      // }
    }
  }
};
</script>