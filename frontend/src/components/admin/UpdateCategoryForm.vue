<template>
  <div class="container mt-5">
    <h2 class="mb-4">Редактировать категорию</h2>
    <form @submit.prevent="updateCategory">
      <div class="mb-3">
        <label for="select-category" class="form-label">Выберите категорию для редактирования</label>
        <select id="select-category" v-model="selectedCategoryId" @change="onCategoryChange" class="form-select" required>
          <option disabled value="">-- Выберите категорию --</option>
          <option v-for="cat in categories" :key="cat.id" :value="cat.id">{{ cat.name }}</option>
        </select>
      </div>

      <div v-if="category">
        <div class="mb-3">
          <label for="category-name" class="form-label">Название категории</label>
          <input type="text" id="category-name" v-model="category.name" class="form-control" required />
        </div>
        <div class="mb-3">
          <label for="parent-category" class="form-label">Родительская категория</label>
          <select id="parent-category" v-model="category.parentId" class="form-select">
            <option value="">-- Нет родительской категории --</option>
            <option v-for="cat in categories" :key="cat.id" :value="cat.id" :disabled="cat.id === category.id">{{ cat.name }}</option>
          </select>
        </div>
        <button type="submit" class="btn btn-primary">Обновить категорию</button>
      </div>
    </form>
  </div>
</template>

<script>
import CategoryService from '@/services/CategoryService';

export default {
  data() {
    return {
      selectedCategoryId: '',
      category: null,
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
    async onCategoryChange() {
      if (!this.selectedCategoryId) {
        this.category = null;
        return;
      }
      try {
        const category = await CategoryService.getCategoryById(this.selectedCategoryId);
        this.category = category;
      } catch (error) {
        console.error('Ошибка при получении категории', error);
        alert('Не удалось загрузить категорию');
      }
    },
    async updateCategory() {
      try {
        await CategoryService.updateCategory(this.category.id, this.category);
        alert('Категория обновлена');
      } catch (error) {
        console.error('Ошибка при обновлении категории', error);
        alert('Не удалось обновить категорию');
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
