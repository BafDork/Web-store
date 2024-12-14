<template>
    <div class="container mt-5">
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="text-primary">Список категорий</h2>
        <button class="btn btn-success" @click="navigateToAddCategory">Добавить категорию</button>
      </div>
      <table class="table table-striped table-hover">
        <thead class="table-primary">
          <tr>
            <th scope="col">#</th>
            <th scope="col">Название</th>
            <th scope="col">Действия</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(category, index) in categories" :key="category.id">
            <th scope="row">{{ index + 1 }}</th>
            <td>{{ category.name }}</td>
            <td>
              <button
                class="btn btn-danger btn-sm"
                @click="deleteCategory(category.id)"
              >
                Удалить
              </button>
            </td>
          </tr>
        </tbody>
      </table>
      <div v-if="categories.length === 0" class="alert alert-warning text-center">
        Категории отсутствуют.
      </div>
    </div>
</template>

<script>
import CategoryService from "@/services/CategoryService";

export default {
  data() {
    return {
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
        console.error("Ошибка при загрузке категорий:", error);
        alert("Не удалось загрузить категории.");
      }
    },
    async deleteCategory(categoryId) {
      if (confirm("Вы уверены, что хотите удалить эту категорию?")) {
        try {
          await CategoryService.deleteCategory(categoryId);
          alert("Категория успешно удалена!");
          this.fetchCategories();
        } catch (error) {
          console.error("Ошибка при удалении категории:", error);
          alert("Не удалось удалить категорию. Возможно, у неё есть связанные подкатегории или товары.");
        }
      }
    },
    navigateToAddCategory() {
      this.$router.push("/admin/add-category");
    },
  },
};
</script>

<style scoped>
.container {
  max-width: 900px;
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

h2 {
  font-size: 24px;
  font-weight: 700;
}

.table {
  margin-top: 20px;
}

.alert {
  margin-top: 20px;
}

.btn-success {
  background-color: #28a745;
  border-color: #28a745;
}

.btn-success:hover {
  background-color: #218838;
  border-color: #1e7e34;
}

.btn-danger {
  background-color: #dc3545;
  border-color: #dc3545;
}

.btn-danger:hover {
  background-color: #c82333;
  border-color: #bd2130;
}
</style>
