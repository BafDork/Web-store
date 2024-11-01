<template>
    <header>
      <nav>
        <span @click="toggleCatalog" class="catalog-link">Каталог</span>
        <div class="nav-right">
          <router-link v-if="!isAuthenticated" to="/auth/sign-in" class="nav-item">Войти</router-link>
          <router-link v-if="isAuthenticated" to="/cart" class="nav-item">Корзина ({{ cartCount }})</router-link>
          <Logout v-if="isAuthenticated" />
        </div>
      </nav>
    </header>
  </template>
  
  <script>
  import { mapGetters, mapActions } from 'vuex';
  import Logout from '@/components/authentication/Logout.vue';
  
  export default {
    components: {
      Logout,
    },
    computed: {
      ...mapGetters(['isAuthenticated', 'cartCount']),
    },
    methods: {
      ...mapActions(['toggleCatalog']),
    },
  };
  </script>
  
  <style scoped>
  header {
    background-color: #282c34;
    padding: 20px;
  }
  
  .catalog-link {
    cursor: pointer;
    color: #007bff;
    font-weight: bold;
    text-decoration: underline;
  }
  
  nav {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .nav-item {
    color: #61dafb;
    margin-right: 20px;
    text-decoration: none;
  }
  
  .nav-item:hover {
    text-decoration: underline;
  }
  
  .nav-right {
    display: flex;
    align-items: center;
  }
  </style>
  