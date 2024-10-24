<template>
    <div class="product-card">
      <h2>{{ product.name }}</h2>
      <p>{{ product.description }}</p>
      <img :src="product.imageUrl" alt="Product Image" />
      <div class="price-info">
        <p v-if="product.discountPrice !== null">
          <span class="original-price">{{ formatPrice(product.price) }}</span>
          <span class="discounted-price">{{ formatPrice(product.discountPrice) }}</span>
        </p>
        <p v-else>
          Цена: {{ formatPrice(product.price) }}
        </p>
      </div>
      <p>
        Наличие: 
        <span :class="getAvailabilityClass(product.stock)">
          {{ getAvailabilityMessage(product.stock) }}
        </span>
      </p>
      <button :disabled="product.stock < 1" @click="addToCart(product)">В корзину</button>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      product: Object
    },
    methods: {
      addToCart(product) {
        // Логика добавления в корзину
      },
      formatPrice(price) {
        return new Intl.NumberFormat('ru-RU', {
          style: 'currency',
          currency: 'RUB'
        }).format(price);
      },
      getAvailabilityMessage(stock) {
        // Логика для наличия
      },
      getAvailabilityClass(stock) {
        // Логика для класса наличия
      }
    }
  };
  </script>
  