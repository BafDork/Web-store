<template>
  <div class="container mt-5">
    <h1 class="text-center mb-4">Регистрация</h1>
    <form @submit.prevent="signUp" class="needs-validation" validate>
      <!-- Имя -->
      <div class="mb-3">
        <label for="firstName" class="form-label">Имя</label>
        <input 
          v-model="firstName" 
          type="text" 
          id="firstName" 
          class="form-control" 
          placeholder="Введите ваше имя" 
          required 
        />
        <div class="invalid-feedback">Пожалуйста, укажите имя.</div>
      </div>

      <!-- Фамилия -->
      <div class="mb-3">
        <label for="lastName" class="form-label">Фамилия</label>
        <input 
          v-model="lastName" 
          type="text" 
          id="lastName" 
          class="form-control" 
          placeholder="Введите вашу фамилию" 
          required 
        />
        <div class="invalid-feedback">Пожалуйста, укажите фамилию.</div>
      </div>

      <!-- Электронная почта -->
      <div class="mb-3">
        <label for="email" class="form-label">Электронная почта</label>
        <input 
          v-model="email" 
          type="email" 
          id="email" 
          class="form-control" 
          placeholder="example@mail.com" 
          required 
        />
        <div class="invalid-feedback">Пожалуйста, укажите корректный адрес электронной почты.</div>
      </div>

      <!-- Пароль -->
      <div class="mb-3">
        <label for="password" class="form-label">Пароль</label>
        <input 
          v-model="password" 
          type="password" 
          id="password" 
          class="form-control" 
          placeholder="Введите пароль" 
          required 
        />
        <div class="invalid-feedback">Пожалуйста, укажите пароль.</div>
      </div>

      <!-- Ошибка -->
      <p v-if="errorMessage" class="text-danger" v-html="errorMessage"></p>

      <!-- Кнопка регистрации -->
      <button type="submit" class="btn btn-primary w-100">Зарегистрироваться</button>
    </form>
  </div>
</template>

<script>
import AuthService from '@/services/AuthService';

export default {
  data() {
    return {
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      errorMessage: '',
    };
  },
  methods: {
    async signUp() {
      this.errorMessage = '';
      try {
        await AuthService.signUp(this.email, this.password, this.firstName, this.lastName);
        this.$router.push('/auth/sign-in');
      } catch (error) {
        if (error.response && error.response.data) {
          this.errorMessage = error.response.data.message;
        } else {
          this.errorMessage = 'Ошибка регистрации';
        }
      }
    },
  },
};
</script>

<style scoped>
.text-danger {
  font-size: 0.9em;
}
</style>
