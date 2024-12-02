<template>
    <li class="product-card">
      <img :src="item.product.imageUrl" alt="Product Image" class="product-image" />
      <div class="product-info">
        <h3 class="product-name">{{ item.product.name }}</h3>
        <p class="product-price">{{ formatPrice(finalPrice(item.product) * item.quantity) }}</p>
      </div>
      <div class="controls">
        <button @click="$emit('decreaseQuantity')" class="btn btn-outline-danger btn-sm">-</button>
        <span class="quantity-display">{{ item.quantity }}</span>
        <button
          @click="$emit('increaseQuantity')"
          :disabled="item.quantity >= item.product.stock"
          class="btn btn-outline-success btn-sm"
        >
          +
        </button>
        <button @click="$emit('remove')" class="btn btn-danger btn-sm">Удалить</button>
      </div>
    </li>
  </template>
  
  <script>
  import { formatPrice } from '@/utils/formatPrice';
  
  export default {
    props: {
      item: {
        type: Object,
        required: true,
      },
    },
    methods: {
      finalPrice(product) {
        return product.discountPrice || product.price;
      },
      formatPrice(price) {
        return formatPrice(price);
      },
    },
  };
  </script>
  
  <style scoped>
  .product-card {
    display: flex;
    align-items: center;
    border: 1px solid var(--border-color);
    border-radius: 5px;
    padding: 16px;
    margin-bottom: 12px;
  }
  
  .product-image {
    width: 100px;
    height: 100px;
    object-fit: cover;
    border-radius: 5px;
    margin-right: 16px;
  }
  
  .product-info {
    flex: 2;
  }
  
  .product-name {
    font-size: 1.2rem;
    color: var(--primary-color);
    margin-bottom: 8px;
  }
  
  .product-price {
    font-size: 1.1rem;
    font-weight: bold;
    color: var(--font-color);
  }
  
  .controls {
    display: flex;
    align-items: center;
    gap: 8px;
  }
  </style>
  