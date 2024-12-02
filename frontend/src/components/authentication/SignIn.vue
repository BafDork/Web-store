<template>
  <div class="auth-container">
    <h1 class="auth-title">Войти</h1>
    <form @submit.prevent="loginAction" class="auth-form">
      <!-- Email -->
      <div class="form-group">
        <label for="email" class="form-label">Email:</label>
        <input
          type="email"
          id="email"
          class="form-control"
          v-model="email"
          required
          aria-required="true"
          aria-label="Введите ваш email"
        />
      </div>

      <!-- Password -->
      <div class="form-group">
        <label for="password" class="form-label">Пароль:</label>
        <input
          type="password"
          id="password"
          class="form-control"
          v-model="password"
          required
          aria-required="true"
          aria-label="Введите ваш пароль"
        />
      </div>

      <!-- Submit -->
      <button type="submit" class="btn btn-primary w-100">Войти</button>
    </form>

    <!-- Register Link -->
    <div class="auth-footer">
      <router-link to="/auth/sign-up" class="auth-link">
        Зарегистрироваться
      </router-link>
    </div>

    <!-- Error Message -->
    <ErrorMessage v-if="errorMessage" :message="errorMessage" />
  </div>
</template>

<script>
import { mapActions } from 'vuex';
import ErrorMessage from './ErrorMessage.vue';

export default {
  components: { 
    ErrorMessage
  },
  data() {
    return {
      email: '',
      password: '',
      errorMessage: '',
    };
  },
  methods: {
    ...mapActions('user', ['login', 'loadUser']),

    async loginAction() {
      try {
        await this.login({ email: this.email, password: this.password });
        await this.loadUser();
        this.$router.push('/');
      } catch (error) {
        this.errorMessage = this.formatError(error);
      }
    },

    formatError(error) {
      return error.response?.data?.message || 'Ошибка авторизации';
    },
  },
};
</script>

<style scoped>
.auth-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #fff;
}

.auth-title {
  text-align: center;
  margin-bottom: 20px;
  font-size: 1.5rem;
  color: #333;
}

.auth-form .form-group {
  margin-bottom: 15px;
}

.auth-footer {
  margin-top: 15px;
  text-align: center;
}

.auth-link {
  color: #007bff;
  text-decoration: none;
  transition: color 0.3s;
}

.auth-link:hover {
  color: #0056b3;
}

.btn-primary {
  background-color: #007bff;
  border-color: #007bff;
}

.btn-primary:hover {
  background-color: #0056b3;
  border-color: #004085;
}
</style>
