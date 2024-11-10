<template>
  <div id="app">
    <Header />
    <router-view></router-view> 
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';
import Header from './components/Header.vue';

export default {
  components: {
    Header,
  },

  computed: {
    ...mapGetters('user', ['isAuthenticated']),
  },

  methods: {
    ...mapActions('user', ['loadUser']),

    async fetchUser() {
      try {
        await this.loadUser();
      } catch (error) {
        console.error("Ошибка при загрузке пользователя:", error);
      }
    }
  },

  created() {
    const token = localStorage.getItem('token');
    if (token) {
      this.fetchUser();
    }
  }

};
</script>
