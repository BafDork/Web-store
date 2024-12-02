<template>
  <div class="registration-container mt-5">
    <h1 class="text-center mb-4">Регистрация</h1>
    <form @submit.prevent="signUp" class="needs-validation" novalidate>
      <!-- Поля формы -->
      <FormInput
        v-model="firstName"
        label="Имя"
        id="firstName"
        type="text"
        placeholder="Введите ваше имя"
        :required="true"
        error-message="Пожалуйста, укажите имя."
      />
      <FormInput
        v-model="lastName"
        label="Фамилия"
        id="lastName"
        type="text"
        placeholder="Введите вашу фамилию"
        :required="true"
        error-message="Пожалуйста, укажите фамилию."
      />
      <FormInput
        v-model="email"
        label="Электронная почта"
        id="email"
        type="email"
        placeholder="example@mail.com"
        :required="true"
        error-message="Пожалуйста, укажите корректный адрес электронной почты."
      />
      <FormInput
        v-model="password"
        label="Пароль"
        id="password"
        type="password"
        placeholder="Введите пароль"
        :required="true"
        error-message="Пожалуйста, укажите пароль."
      />

      <!-- Ошибка -->
      <p v-if="errorMessage" class="text-danger text-center" v-html="errorMessage"></p>

      <!-- Кнопка регистрации -->
      <button type="submit" class="btn btn-primary w-100">Зарегистрироваться</button>
    </form>
  </div>
</template>

<script>
import FormInput from './FormInput.vue';
import AuthService from '@/services/AuthService';

export default {
  components: {
    FormInput,
  },
  data() {
    return {
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      errorMessage: '',
    };
  },
  methods: {
    async signUp() {
      this.errorMessage = '';
      try {
        await AuthService.signUp(this.email, this.password, this.firstName, this.lastName);
        this.$router.push('/auth/sign-in');
      } catch (error) {
        this.handleError(error);
      }
    },
    handleError(error) {
      if (error.response && error.response.data) {
        this.errorMessage = error.response.data.message;
      } else {
        this.errorMessage = 'Ошибка регистрации';
      }
    },
  },
};
</script>

<style scoped>
.registration-container {
  max-width: 500px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: #f9f9f9;
}

.text-danger {
  font-size: 0.9em;
  margin-top: 10px;
}

.btn-primary {
  background-color: #007bff;
  border: none;
  padding: 10px 20px;
  font-size: 1rem;
}

.btn-primary:hover {
  background-color: #0056b3;
}
</style>
