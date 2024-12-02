<template>
  <header>
    <nav>
      <h2 @click="toggleCatalogAction" class="catalog-link">Каталог</h2>
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
    ...mapGetters('cart', ['cartCount']),

    userDisplayName() {
      const [firstName, lastName] = this.userName.split(' ');
      return `${lastName} ${firstName ? firstName[0] + '.' : ''}`;
    },
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
  border-bottom: 1px solid #ddd;
  padding: 20px;
  background-color: #f8f9fa;
}

.catalog-link {
  cursor: pointer;
  font-weight: bold;
  color: #f54242;
  transition: color 0.3s ease, text-decoration 0.3s ease;
}

.catalog-link:hover {
  text-decoration: underline;
  color: #e03636;
}

nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.nav-item {
  margin-right: 20px;
  text-decoration: none;
  color: #000;
  font-weight: normal;
  transition: color 0.3s ease;
}

.nav-item:hover {
  text-decoration: underline;
  color: #007bff;
}

.nav-right {
  display: flex;
  align-items: center;
  font-size: 1.1em;
}

.user-info {
  font-weight: bold;
  display: flex;
  align-items: center;
  gap: 10px;
}

.user-info button {
  margin-left: 10px;
  background: none;
  border: none;
  color: #f54242;
  cursor: pointer;
  font-size: 1.1em;
}

.user-info button:hover {
  text-decoration: underline;
}
</style>
