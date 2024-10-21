<template>
    <div>
      <h1>Регистрация</h1>
      <form @submit.prevent="singUp">
        <input v-model="username" placeholder="Имя пользователя" required />
        <input v-model="email" type="email" placeholder="Электронная почта" required />
        <input v-model="password" type="password" placeholder="Пароль" required />
        <button type="submit">Зарегистрироваться</button>
      </form>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
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
      async singUp() {
        this.errorMessage = ''; 
        try {
            const response = await axios.post('http://localhost:8080/auth/sign-up', {
              username: this.username,
              email: this.email,
              password: this.password
            });
            console.log('Пользователь зарегистрирован:', response.data);
            this.$router.push('/login');
        } catch (error) {
            if (error.response) {
                this.errorMessage = error.response.data.message;
            } else {
                this.errorMessage = 'Произошла ошибка. Попробуйте позже.';
            }
        }
      }
    }
  };
  </script>