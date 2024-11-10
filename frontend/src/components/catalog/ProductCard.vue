<template>
  <div class="product-card">
    <img :src="product.imageUrl" alt="Product Image" class="product-image" />
    <div class="product-details">
      <h2 class="product-name">{{ product.name }}</h2>
      <p class="product-description">{{ product.description }}</p>
      <p>
        <span :class="getAvailabilityClass(product.stock)">
          {{ getAvailabilityMessage(product.stock) }}
        </span>
      </p>
      <div class="price-info">
        <p v-if="isAuthenticated && product.discountPrice !== null">
          <span class="original-price">{{ formatPrice(product.price) }}</span>
          <span class="discounted-price">{{ formatPrice(product.discountPrice) }}</span>
        </p>
        <p v-else>
          Цена: {{ formatPrice(product.price) }}
        </p>
      </div>
      <button 
        v-if="!isAuthenticated" 
        @click="redirectToLogin">
        В корзину
      </button>
      <button 
        v-else 
        :disabled="product.stock < 1 || isInCart" 
        @click="addToCartAction">
        {{ isInCart ? "Уже в корзине" : "В корзину" }}
      </button>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import { formatPrice } from '@/utils/formatPrice';

export default {
  props: {
    product: Object,
  },

  computed: {
    ...mapGetters('cart', ['isProductInCart']),
    ...mapGetters('user', ['isAuthenticated']),

    isInCart() {
      return this.isProductInCart(this.product.id);
    }
  },

  methods: {
    ...mapActions('cart', ['addToCart']),

    redirectToLogin() {
      this.$router.push('/auth/sign-in');
    },

    addToCartAction() {
      if (!this.isInCart) {
        this.addToCart({ product: this.product, quantity: 1 });
      }
    },

    getAvailabilityMessage(stock) {
      return stock > 10 ? "В наличии" : stock > 0 ? "Мало" : "Нет в наличии";
    },

    getAvailabilityClass(stock) {
      return stock > 10 ? "in-stock" : stock > 0 ? "low-stock" : "out-of-stock";
    },

    formatPrice(price) {
      return formatPrice(price);
    },
  }
};
</script>

  
  <style scoped>
  .product-card {
    display: flex;
    align-items: flex-start;
    border: 1px solid #ddd;
    border-radius: 5px;
    padding: 16px;
    margin: 10px;
    width: 100%;
    box-sizing: border-box;
  }
  
  .product-image {
    width: 150px;
    height: 150px;
    object-fit: cover;
    margin-right: 20px;
  }
  
  .product-details {
    display: flex;
    flex-direction: column;
    flex: 1;
  }
  
  .product-name {
    font-size: 1.2em;
    margin-bottom: 8px;
  }
  
  .product-description {
    font-size: 0.9em;
    margin-bottom: 8px;
    color: #555;
  }
  
  .price-info {
    font-weight: bold;
    margin-bottom: 10px;
  }
  
  .original-price {
    text-decoration: line-through;
    margin-right: 10px;
    color: #888;
  }
  
  .discounted-price {
    color: #e74c3c;
  }
  
  button {
    align-self: flex-start;
  }
  
  .in-stock {
    color: green;
  }
  
  .low-stock {
    color: orange;
  }
  
  .out-of-stock {
    color: red;
  }
  </style>
  