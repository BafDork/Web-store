<template>
    <div>
      <h1>Смартфоны</h1>
      <div>
        <label>Сортировка: </label>
        <select v-model="sortOrder" @change="sortProducts">
          <option value="asc">Сначала дешевле</option>
          <option value="desc">Сначала дороже</option>
        </select>
      </div>
      <ul>
        <li v-for="product in sortedProducts" :key="product.id">
          <img :src="product.image" alt="Product image" />
          <h2>{{ product.name }}</h2>
          <p>{{ product.description }}</p>
          <p>{{ product.price }} ₽</p>
          <button @click="addToCart(product)" :disabled="product.stock === 0">
            {{ product.stock > 5 ? 'В наличии' : (product.stock > 0 ? 'Мало' : 'Нет в наличии') }}
          </button>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  import { mapActions } from 'vuex';
  import axios from 'axios';
  
  export default {
    data() {
      return {
        products: [],
        sortOrder: 'asc'
      };
    },
    computed: {
      sortedProducts() {
        return this.products.sort((a, b) => {
          return this.sortOrder === 'asc' ? a.price - b.price : b.price - a.price;
        });}
  },
  methods: {
    ...mapActions(['addToCart']),
    sortProducts() {
      // Эта функция перезапускает сортировку при изменении выбора
    }
  },
  created() {
    axios.get(`/api/products/${this.$route.params.categoryId}`)
      .then(response => {
        this.products = response.data;
      });
  }
};
</script>