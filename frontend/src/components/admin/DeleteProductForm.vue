<template>
    <div class="container mt-5">
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="text-primary">Список продуктов</h2>
        <button class="btn btn-success" @click="navigateToAddProduct">Добавить продукт</button>
      </div>
      <table class="table table-striped table-hover">
        <thead class="table-primary">
          <tr>
            <th scope="col">#</th>
            <th scope="col">Название</th>
            <th scope="col">Цена</th>
            <th scope="col">Действия</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(product, index) in products" :key="product.id">
            <th scope="row">{{ index + 1 }}</th>
            <td>{{ product.name }}</td>
            <td>{{ product.price }} ₽</td>
            <td>
              <button
                class="btn btn-danger btn-sm"
                @click="deleteProduct(product.id)"
              >
                Удалить
              </button>
            </td>
          </tr>
        </tbody>
      </table>
      <div v-if="products.length === 0" class="alert alert-warning text-center">
        Продукты отсутствуют.
      </div>
    </div>
</template>

<script>
import ProductService from "@/services/ProductService";

export default {
  data() {
    return {
      products: [],
    };
  },
  created() {
    this.fetchProducts();
  },
  methods: {
    async fetchProducts() {
      try {
        this.products = await ProductService.getAllProducts();
      } catch (error) {
        console.error("Ошибка при загрузке продуктов:", error);
        alert("Не удалось загрузить продукты.");
      }
    },
    async deleteProduct(productId) {
      if (confirm("Вы уверены, что хотите удалить этот продукт?")) {
        try {
          await ProductService.deleteProduct(productId);
          alert("Продукт успешно удалён!");
          this.fetchProducts();
        } catch (error) {
          console.error("Ошибка при удалении продукта:", error);
          alert("Не удалось удалить продукт.");
        }
      }
    },
    navigateToAddProduct() {
      this.$router.push("/admin/add-product");
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
