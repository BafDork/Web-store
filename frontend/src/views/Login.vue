<template>
    <div>
      <h1>Войти</h1>
      <form @submit.prevent="login">
        <label>Email:</label>
        <input type="email" v-model="email" required />
        <label>Пароль:</label>
        <input type="password" v-model="password" required />
        <button type="submit">Войти</button>
      </form>
      <router-link to="/register">Зарегистрироваться</router-link>
    </div>
  </template>
  
  <script>
  import { mapActions } from 'vuex';
  import axios from 'axios';
  
  export default {
    data() {
      return {
        email: '',
        password: ''
      };
    },
    methods: {
      ...mapActions(['login']),
      login() {
        axios.post('/api/auth/login', { email: this.email, password: this.password })
          .then(response => {
            this.login(response.data.user);
            this.$router.push('/');
          })
          .catch(error => {
            alert('Ошибка авторизации');
          });
      }
    }
  };
  </script>