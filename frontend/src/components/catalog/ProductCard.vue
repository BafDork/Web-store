<template>
  <div class="product-card">
    <!--
    <img src="https://apple-store.net.ru/image/cache/catalog/accesories/iphone-14-pro-max/silicone-case-iphone-14-pro-max-temnaea-noc4-800x800.jpg" alt="Product Image" class="product-image" />
    -->
    <img :src="product.imageUrl" alt="Product Image" class="product-image" />
    <div class="product-details">
      <h2 class="product-name">{{ product.name }}</h2>
      <p class="product-description">{{ product.description }}</p>
    </div>

    <div class="product-stock">
      <p>
        <span :class="getAvailabilityClass(product.stock)">
          {{ getAvailabilityMessage(product.stock) }}
        </span>
      </p>
    </div>

    <div class="price-and-cart">
      <div class="product-price">
        <p v-if="isAuthenticated && product.discountPrice !== null">
          <p class="original-price">{{ formatPrice(product.price) }}</p>
          <p class="discounted-price">{{ formatPrice(product.discountPrice) }}</p>
        </p>
        <p v-else class="regular-price">
          {{ formatPrice(product.price) }}
        </p>
      </div>

      <button 
        v-if="!isAuthenticated" 
        @click="redirectToLogin"
        class="btn btn-primary">
        В корзину
      </button>
      <button 
        v-else 
        :disabled="product.stock < 1 || isInCart" 
        @click="addToCartAction"
        class="btn btn-primary">
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
    border: 1px solid #ddd;
    border-radius: 5px;
    padding: 16px;
    margin: 10px;
    box-sizing: border-box;
    box-shadow: 10px 5px 5px rgb(199, 197, 197);
  }
  
  .product-image {
    width: 150px;
    height: 150px;
    object-fit: scale-down;
    margin-right: 20px;
    width: 15%;

  }
  
  .product-details {
    display: flex;
    flex-direction: column;
    width: 50%;
  }
  
  .product-name {
    font-size: 1.1em;
    margin-bottom: 8px;
    color: #56a841;
  }
  
  .product-description {
    font-size: 0.9em;
    color: #555;
  }

  .product-stock {
    text-align: center;
    width: 20%;
  }
  
  .price-and-cart {
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
    text-align: center;
    width: 15%;
  }

  .product-price {
    display: flex;
    flex-direction: column;
    margin-bottom: auto;
    font-weight: bold;
  }

  .regular-price {
    font-size: 1.2em;
  }
  
  .original-price {
    text-decoration: line-through;
    color: #888;
    margin: 0;
    padding: 0;
  }
  
  .discounted-price {
    font-size: 1.2em;
    color: #e74c3c;
  }
  
  button {
    align-self: stretch;
    margin-bottom: 16px;
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
  