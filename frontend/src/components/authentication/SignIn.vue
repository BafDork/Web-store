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
    ...mapActions('user', ['login', 'loadUser']),

    async loginAction() {
      try {
        await this.login({ email: this.email, password: this.password });
        await this.loadUser();
        this.$router.push('/');
      } catch (error) {
        this.errorMessage = error.response ? error.response.data.message : 'Ошибка авторизации';
      }
    }
  }
};
</script>
