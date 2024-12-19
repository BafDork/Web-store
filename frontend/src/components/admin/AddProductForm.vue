<template>
  <div class="container mt-5">
    <h2 class="mb-4">Добавить новый продукт</h2>
    <form @submit.prevent="addProduct">
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
      <button type="submit" class="btn btn-primary">Добавить продукт</button>
    </form>
  </div>
</template>

  
  <script>
  import CategoryService from '@/services/CategoryService';
  import ProductService from '@/services/ProductService';
  
  export default {
    data() {
      return {
        product: {
          name: '',
          description: '',
          price: 0,
          discountPrice: null,
          stock: 0,
          imageUrl: '',
          features: '',
          categoryIds: [],
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
      async addProduct() {
        try {
          await ProductService.addProduct(this.product);
          alert('Продукт добавлен');
          this.resetForm();
        } catch (error) {
          console.error('Ошибка при добавлении продукта:', error);
          alert('Не удалось добавить продукт.');
        }
      },
      resetForm() {
        this.product = {
          name: '',
          description: '',
          price: 0,
          discountPrice: null,
          stock: 0,
          imageUrl: '',
          features: '',
          categoryIds: [],
        };
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
  