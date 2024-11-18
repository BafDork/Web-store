<template>
    <button @click="logout" class="logout-button">Выйти</button>
  </template>
  
  <script>
  import { mapActions } from 'vuex';
  
  export default {
    methods: {
      ...mapActions('user', ['logout']),
    },
  };
  </script>
  
  <style scoped>
  .logout-button {
    color: #22b6df;
    border: none;
    cursor: pointer;
    margin-left: 10px;
    font-size: 1.1em;
  }

  .logout-button:hover {
    text-decoration: underline;
  }
  </style>
  