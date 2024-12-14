<template>
  <header>
    <div class="container">
      <nav class="nav">
        <div class="catalog-container">
          <h2 @click="toggleCatalogAction" class="catalog-link">Каталог</h2>
          <CategoryList
            v-if="showCatalog"
            :categories="categories"
            @category-selected="onCategorySelected"
            :active-category="currentCategoryId"
          />
        </div>
        <div class="nav-right">
          <router-link v-if="!isAuthenticated" to="/auth/sign-in" class="nav-item login">Войти</router-link>
          <router-link v-if="isAuthenticated" to="/cart" class="nav-item cart">Корзина ({{ cartCount }})</router-link>
          <div v-if="isAuthenticated" class="user-info">
            {{ userDisplayName }}
            <Logout />
          </div>
        </div>
      </nav>
    </div>
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
    toggleCatalogAction() {
      if (this.$route.path !== '/') {
        this.$router.push('/');
      }
    },
  },
};
</script>

<style scoped>
header {
  border-bottom: 1px solid #ddd;
  background-color: #f8f9fa;
  padding: 10px 0;
}

.container {
  max-width: 1250px;
  margin: 0 auto;
  padding: 0;
}

.nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.catalog-container {
  display: flex;
  align-items: center;
  gap: 20px;
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

.nav-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.nav-item {
  text-decoration: none;
  color: #000;
  font-weight: normal;
  transition: color 0.3s ease;
}

.nav-item:hover {
  text-decoration: underline;
  color: #007bff;
}

.login, .cart {
  padding: 8px 12px;
  border-radius: 4px;
  font-weight: 600;
}

.login {
  background-color: #007bff;
  color: #fff;
  transition: background-color 0.3s ease;
}

.login:hover {
  background-color: #0056b3;
}

.cart {
  background-color: #28a745;
  color: #fff;
  transition: background-color 0.3s ease;
}

.cart:hover {
  background-color: #218838;
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
