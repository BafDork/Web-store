<template>
    <div class="container mt-5">
      <h2 class="mb-4">Добавить категорию</h2>
      <form @submit.prevent="addCategory">
        <div class="mb-3">
          <label for="category-name" class="form-label">Название категории</label>
          <input type="text" id="category-name" v-model="category.name" class="form-control" required />
        </div>
        <div class="mb-3">
          <label for="parent-category" class="form-label">Родительская категория</label>
          <select id="parent-category" v-model="category.parentId" class="form-select">
            <option v-for="cat in categories" :key="cat.id" :value="cat.id">{{ cat.name }}</option>
          </select>
        </div>
        <button type="submit" class="btn btn-primary">Добавить категорию</button>
      </form>
    </div>
  </template>
  
  <script>
  import CategoryService from '@/services/CategoryService';
  
  export default {
    data() {
      return {
        category: {
          name: '',
          parentId: null,
        },
        categories: [],
      };
    },
    created() {
      this.fetchCategories();
    },
    methods: {
      async fetchCategories() {
        try {
          this.categories = await CategoryService.getAllCategories();
        } catch (error) {
          console.error('Ошибка при загрузке категорий', error);
        }
      },
      async addCategory() {
        try {
          await CategoryService.addCategory(this.category);
          alert('Категория добавлена');
          this.category.name = '';
          this.fetchCategories();
        } catch (error) {
          console.error('Ошибка при добавлении категории', error);
          alert('Не удалось добавить категорию');
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .container {
    max-width: 600px;
  }
  
  h2 {
    text-align: center;
  }
  
  form {
    background-color: #f9f9f9;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }
  
  button {
    width: 100%;
  }
  </style>
  