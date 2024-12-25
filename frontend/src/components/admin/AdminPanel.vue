<template>
    <div class="container py-5">
      <h1 class="text-center mb-4">Админская панель</h1>
  
      <div class="card mb-5">
        <ul class="list-group list-group-flush">
          <li 
            v-for="action in actions" 
            :key="action.name" 
            class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
            @click="navigateTo(action.route)"
          >
            {{ action.label }}
            <span class="badge bg-primary">&rarr;</span>
          </li>
        </ul>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'AdminPanel',
    data() {
      return {
        actions: [
          { name: 'add-category', label: 'Добавить категорию', route: 'add-category', color: 'primary' },
          { name: 'add-product', label: 'Добавить продукт', route: 'add-product', color: 'primary' },
          { name: 'delete-category', label: 'Удалить категорию', route: 'delete-category', color: 'danger' },
          { name: 'delete-product', label: 'Удалить продукт', route: 'delete-product', color: 'danger' },
          { name: 'update-category', label: 'Редактировать категорию', route: 'update-category', color: 'success' },
          { name: 'update-product', label: 'Редактировать продукт', route: 'update-product', color: 'success' },
        ],
      };
    },
    methods: {
      navigateTo(route) {
        this.$router.push({ name: route });
      },
    },
  };
  </script>
  
  <style scoped>
  h1 {
    font-size: 2rem;
    font-weight: bold;
  }
  
  .list-group-item:hover {
    background-color: #f8f9fa;
    cursor: pointer;
  }

  </style>
  