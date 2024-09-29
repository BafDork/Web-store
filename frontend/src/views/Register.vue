<template>
    <div>
      <h1>Регистрация</h1>
      <form @submit.prevent="register">
        <label>Имя:</label>
        <input type="text" v-model="firstName" required />
        <label>Фамилия:</label>
        <input type="text" v-model="lastName" required />
        <label>Email:</label>
        <input type="email" v-model="email" required />
        <label>Пароль:</label>
        <input type="password" v-model="password" required />
        <button type="submit">Зарегистрироваться</button>
      </form>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        firstName: '',
        lastName: '',
        email: '',
        password: ''
      };
    },
    methods: {
      register() {
        axios.post('/api/auth/register', {
          firstName: this.firstName,
          lastName: this.lastName,
          email: this.email,
          password: this.password
        })
        .then(() => {
          alert('Успешная регистрация');
          this.$router.push('/login');
        })
        .catch(() => {
          alert('Ошибка регистрации');
        });
      }
    }
  };
  </script>