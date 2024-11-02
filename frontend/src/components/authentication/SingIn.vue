<template>
  <div>
    <h1>Войти</h1>
    <form @submit.prevent="loginAction">
      <label>Email:</label>
      <input type="email" v-model="email" required />
      <label>Пароль:</label>
      <input type="password" v-model="password" required />
      <button type="submit">Войти</button>
    </form>
    <router-link to="/auth/sign-up">Зарегистрироваться</router-link>
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </div>
</template>

<script>
import AuthService from '@/services/AuthService';
import { mapActions } from 'vuex';

export default {
  data() {
    return {
      email: '',
      password: '',
      errorMessage: ''
    };
  },
  methods: {
    ...mapActions(['login']),
    async loginAction() {
      try {
        await AuthService.login(this.email, this.password);
        // загрузка данных пользователя !
        this.$router.push('/');
      } catch (error) {
        this.errorMessage = 'Ошибка авторизации';
      }
    }
  }
};
</script>

<style scoped>
.error {
  color: red;
}
</style>
