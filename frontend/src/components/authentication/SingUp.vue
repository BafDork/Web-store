<template>
  <div>
    <h1>Регистрация</h1>
    <form @submit.prevent="signUp">
      <input v-model="username" placeholder="Имя пользователя" required />
      <input v-model="email" type="email" placeholder="Электронная почта" required />
      <input v-model="password" type="password" placeholder="Пароль" required />
      <button type="submit">Зарегистрироваться</button>
    </form>
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </div>
</template>

<script>
import AuthService from '@/services/AuthService';

export default {
  data() {
    return {
      username: '',
      email: '',
      password: '',
      errorMessage: ''
    };
  },
  methods: {
    async signUp() {
      this.errorMessage = '';
      try {
        await AuthService.signUp(this.username, this.email, this.password);
        this.$router.push('/auth/sign-in');
      } catch (error) {
        this.errorMessage = error.response ? error.response.data.message : 'Произошла ошибка. Попробуйте позже.';
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
