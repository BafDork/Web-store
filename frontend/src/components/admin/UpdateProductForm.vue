<template>
  <div class="container mt-5">
    <h2 class="mb-4">Редактировать продукт</h2>
    <form @submit.prevent="updateProduct">
      <div class="mb-3">
        <label for="select-product" class="form-label">Выберите продукт для редактирования</label>
        <select id="select-product" v-model="selectedProductId" @change="onProductChange" class="form-select" required>
          <option disabled value="">-- Выберите продукт --</option>
          <option v-for="prod in products" :key="prod.id" :value="prod.id">{{ prod.name }}</option>
        </select>
      </div>

      <div v-if="product">
        <div class="mb-3">
          <label for="product-name" class="form-label">Название продукта</label>
          <input type="text" id="product-name" v-model="product.name" class="form-control" required />
        </div>
        <div class="mb-3">
          <label for="product-description" class="form-label">Описание</label>
          <textarea id="product-description" v-model="product.description" class="form-control" required></textarea>
        </div>
        <div class="mb-3">
          <label for="product-features" class="form-label">Отличительные признаки</label>
          <textarea id="product-features" v-model="product.features" class="form-control"></textarea>
        </div>
        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="product-price" class="form-label">Цена</label>
            <input type="number" id="product-price" v-model="product.price" class="form-control" required />
          </div>
          <div class="col-md-6 mb-3">
            <label for="product-discount-price" class="form-label">Цена со скидкой</label>
            <input type="number" id="product-discount-price" v-model="product.discountPrice" class="form-control" />
          </div>
        </div>
        <div class="mb-3">
          <label for="product-stock" class="form-label">Количество на складе</label>
          <input type="number" id="product-stock" v-model="product.stock" class="form-control" required />
        </div>
        <div class="mb-3">
          <label for="product-image-url" class="form-label">URL изображения</label>
          <input type="text" id="product-image-url" v-model="product.imageUrl" class="form-control" />
        </div>
        <div class="mb-3">
          <label for="product-categories" class="form-label">Категории</label>
          <select id="product-categories" v-model="product.categoryIds" class="form-select" multiple>
            <option v-for="cat in categories" :key="cat.id" :value="cat.id">{{ cat.name }}</option>
          </select>
        </div>
        <button type="submit" class="btn btn-primary">Обновить продукт</button>
      </div>
    </form>
  </div>
</template>

<script>
import CategoryService from '@/services/CategoryService';
import ProductService from '@/services/ProductService';

export default {
  data() {
    return {
      selectedProductId: '',
      product: null,
      products: [],
      categories: [],
    };
  },
  created() {
    this.fetchProducts();
    this.fetchCategories();
  },
  methods: {
    async fetchProducts() {
      try {
        this.products = await ProductService.getAllProducts();
      } catch (error) {
        console.error('Ошибка при загрузке продуктов', error);
      }
    },
    async fetchCategories() {
      try {
        this.categories = await CategoryService.getAllCategories();
      } catch (error) {
        console.error('Ошибка при загрузке категорий', error);
      }
    },
    async onProductChange() {
      if (!this.selectedProductId) {
        this.product = null;
        return;
      }
      try {
        const product = await ProductService.getProductById(this.selectedProductId);
        this.product = { ...product };
      } catch (error) {
        console.error('Ошибка при загрузке продукта', error);
        alert('Не удалось загрузить продукт');
      }
    },
    async updateProduct() {
      try {
        await ProductService.updateProduct(this.product.id, this.product);
        alert('Продукт обновлен');
      } catch (error) {
        console.error('Ошибка при обновлении продукта', error);
        alert('Не удалось обновить продукт.');
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
