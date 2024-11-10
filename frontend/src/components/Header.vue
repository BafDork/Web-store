<template>
  <header>
    <nav>
      <span @click="toggleCatalogAction" class="catalog-link">Каталог</span>
      <div class="nav-right">
        <router-link v-if="!isAuthenticated" to="/auth/sign-in" class="nav-item">Войти</router-link>
        <router-link v-if="isAuthenticated" to="/cart" class="nav-item">Корзина ({{ cartCount }})</router-link>
        <div v-if="isAuthenticated" class="user-info">
          {{ userDisplayName }}
          <Logout />
        </div>
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
    ...mapGetters('user', ['isAuthenticated', 'userName']),
    ...mapGetters('cart', ['cart', 'cartCount']),

    userDisplayName() {
      const [firstName, lastName] = this.userName.split(" ");
      return `${lastName} ${firstName ? firstName[0] + '.' : ''}`;
    }
  },

  methods: {
    ...mapActions('catalog', ['toggleCatalog']),

    toggleCatalogAction() {
      if (this.$route.path === '/') {
        this.toggleCatalog();
      } else {
        this.$router.push('/');
      }
    },
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
  