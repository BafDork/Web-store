<template>
    <button @click="logout" class="logout-button">Выйти</button>
  </template>
  
  <script>
  import { mapActions } from 'vuex';
  
  export default {
    methods: {
      ...mapActions(['logout']),
    },
  };
  </script>
  
  <style scoped>
  .logout-button {
    background: none;
    color: #61dafb;
    border: none;
    cursor: pointer;
    margin-left: 10px;
  }
  </style>
  