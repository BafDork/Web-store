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
      const token = AuthService.getToken();
      if (token) {
        try {
          // const userData = await AuthService.fetchUser();
          // if (userData) {
          //   this.login(userData);
          // }
        } catch (error) {
          console.error('Ошибка при получении данных пользователя:', error);
        }
      }
    }
  }
};
</script>
