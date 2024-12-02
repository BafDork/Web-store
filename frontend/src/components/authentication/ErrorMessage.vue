<template>
    <p class="error-message text-danger" v-html="message"></p>
  </template>
  
  <script>
  export default {
    props: {
      message: {
        type: String,
        required: true,
      },
    },
  };
  </script>
  
  <style scoped>
  .error-message {
    margin-top: 15px;
    text-align: center;
  }
  </style>