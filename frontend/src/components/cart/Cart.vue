<template>
  <div class="cart-container">
    <div class="cart-items">
      <h2 class="cart-label">Корзина ({{ cartCount }} товара)</h2>
      <ul v-if="cartItems && cartItems.length" class="cart-products">
        <li v-for="item in cartItems" :key="item.product.id" class="product-card">
          <!--
          <img src="https://apple-store.net.ru/image/cache/catalog/accesories/iphone-14-pro-max/silicone-case-iphone-14-pro-max-temnaea-noc4-800x800.jpg" alt="Product Image" class="product-image" />
          -->
          <img :src="item.product.imageUrl" alt="Product Image" class="product-image" />
          <h3 class="product-name">{{ item.product.name }}</h3>
          <div class="controls">
            <div class="quantity-control">
              <button @click="decreaseQuantity(item)" class="btn btn-outline-danger btn-sm">-</button>
              <span class="quantity-display">{{ item.quantity }}</span>
              <button @click="increaseQuantity(item)" :disabled="item.quantity >= item.product.stock" class="btn btn-outline-success btn-sm">+</button>
            </div>
            <button @click="removeFromCartAction(item.product.id)" class="btn btn-danger btn-sm">Удалить</button>
          </div>
          <p class="product-price">{{ formatPrice(finalPrice(item.product) * item.quantity) }}</p>
        </li>
      </ul>
      <p v-else>Корзина пуста</p>
    </div>

    <div class="cart-summary">
      <h4>Ваш заказ:</h4>
      <div class="row align-items-center">
        <div class="col-md-6 text-start">
          <p><strong>Количество:</strong></p>
          <p><strong>Итого:</strong></p>
        </div>
        <div class="col-md-6 text-end">
          <p>{{ cartCount }}</p>
          <p>{{ formatPrice(cartTotal) }}</p>
        </div>
      </div>
      <div class="text-center mt-4">
        <button @click="checkout" :disabled="!cartItems.length" class="btn btn-success btn-lg">Оформить заказ</button>
      </div>
    </div>
  </div>
</template>



<script>
import { mapGetters, mapActions } from 'vuex';
import { formatPrice } from '@/utils/formatPrice';
import OrderService from '@/services/OrderService';

export default {
  computed: {
    ...mapGetters('cart', ['cartItems', 'cartTotal', 'cartCount']),
  },

  methods: {
    ...mapActions('cart', ['loadCart', 'removeFromCart', 'updateQuantity', 'clearCart']),

    increaseQuantity(item) {
      const newQuantity = item.quantity + 1;
      if (newQuantity > item.product.stock) {
        this.updateQuantity({ productId: item.product.id, quantity: item.product.stock });
      } else {
        this.updateQuantity({ productId: item.product.id, quantity: newQuantity });
      }
    },

    decreaseQuantity(item) {
      const newQuantity = item.quantity - 1;
      if (newQuantity <= 0) {
        this.removeFromCart({ productId: item.product.id });
      } else {
        this.updateQuantity({ productId: item.product.id, quantity: newQuantity });
      }
    },

    removeFromCartAction(productId) {
      this.removeFromCart({ productId });
    },

    async fetchCart() {
      try {
        await this.loadCart();
      } catch (error) {
        console.error('Ошибка при загрузке корзины:', error);
      }
    },

    async checkout() {
      try {
        const response = await OrderService.createOrder();
        alert('Заказ успешно оформлен! Номер заказа: ' + response.orderId);
        this.clearCart()
      } catch (error) {
        console.error('Ошибка при оформлении заказа:', error);
        alert('Ошибка при оформлении заказа. Попробуйте позже.');
      }
    },

    finalPrice(product) {
      return product.discountPrice || product.price;
    },

    formatPrice(price) {
      return formatPrice(price);
    },
  },

  mounted() {
    this.fetchCart();
  },
};
</script>

<style scoped>
.cart-label {
  font-size: 1.5em;
  font-weight: bold;
  margin-bottom: 20px;
}

.cart-container {
  display: flex;
  gap: 16px;
  margin: 20px;
}

.cart-products {
  list-style: none;
  padding-left: 0;
}

.cart-items {
  flex: 3;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 16px;
}

.product-card {
  display: flex;
  align-items: center;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 16px;
  margin-bottom: 12px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.product-image {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 5px;
  margin-right: 16px;
}

.product-name {
  font-size: 1.2em;
  font-weight: bold;
  color: #56a841;
  flex: 2;
}

.controls {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.quantity-control {
  display: flex;
  align-items: center;
  gap: 10px;
}

.quantity-display {
  font-size: 1.1em;
  font-weight: bold;
}

.product-price {
  font-size: 1.2em;
  font-weight: bold;
  color: #000;
  flex: 1;
  text-align: right;
}

.cart-summary {
  flex: 1;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 20px;
  align-self: flex-start;
}

.cart-summary h4 {
  font-size: 1.3em;
  font-weight: bold;
  margin-bottom: 16px;
}

.cart-summary .btn-success {
  margin-top: 16px;
  width: 100%;
}
</style>


