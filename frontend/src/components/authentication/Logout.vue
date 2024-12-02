<template>
  <button 
    @click="logoutAction" 
    class="logout-button"
    aria-label="Выйти из аккаунта"
  >
    Выйти
  </button>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  methods: {
    ...mapActions('user', ['logout']),
    
    logoutAction() {
      this.logout();
      this.$router.push('/');
    },
  },
};
</script>

<style scoped>
:root {
  --primary-color: #22b6df;
}

.logout-button {
  color: var(--primary-color);
  background: none;
  border: none;
  cursor: pointer;
  margin-left: 10px;
  font: inherit;
  transition: color 0.3s ease, text-decoration 0.3s ease;
}

.logout-button:hover {
  color: darken(var(--primary-color), 10%);
  text-decoration: underline;
}
</style>
