<template>
  <div class="container mt-5">
    <h1 class="mb-4">Войти</h1>
    <form @submit.prevent="loginAction">
      <div class="mb-3">
        <label for="email" class="form-label">Email:</label>
        <input 
          type="email" 
          id="email" 
          class="form-control" 
          v-model="email" 
          required 
        />
      </div>

      <div class="mb-3">
        <label for="password" class="form-label">Пароль:</label>
        <input 
          type="password" 
          id="password" 
          class="form-control" 
          v-model="password" 
          required 
        />
      </div>

      <button type="submit" class="btn btn-primary w-100">Войти</button>
    </form>

    <div class="mt-3 text-center">
      <router-link to="/auth/sign-up">Зарегистрироваться</router-link>
    </div>

    <p v-if="errorMessage" class="text-danger" v-html="errorMessage"></p>
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
        if (error.response && error.response.data) {
          this.errorMessage = error.response.data.message;
        } else {
          this.errorMessage = 'Ошибка авторизации';
        }
      }
    }
  }
};
</script>