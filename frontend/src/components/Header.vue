<template>
    <header>
      <nav>
        <router-link to="/">Каталог</router-link>
        <router-link v-if="!isAuthenticated" to="/login">Войти</router-link>
        <router-link v-if="isAuthenticated" to="/cart">Корзина ({{ cartCount }})</router-link>
        <button v-if="isAuthenticated" @click="logout">Выйти</button>
      </nav>
    </header>
  </template>
  
  <script>
  import { mapGetters, mapActions } from 'vuex';
  
  export default {
    computed: {
      ...mapGetters(['isAuthenticated', 'cartCount'])
    },
    methods: {
      ...mapActions(['logout'])
    }
  };
  </script>