<template>
  <div class="cart-container">
    <!-- Список товаров -->
    <div class="cart-items">
      <h2 class="cart-label">Корзина ({{ cartCount }} товара)</h2>
      <ul v-if="cartItems.length" class="cart-products">
        <ProductCard
          v-for="item in cartItems"
          :key="item.product.id"
          :item="item"
          @increaseQuantity="changeQuantity(item.product.id, item.quantity + 1)"
          @decreaseQuantity="changeQuantity(item.product.id, item.quantity - 1)"
          @remove="removeFromCartAction(item.product.id)"
        />
      </ul>
      <p v-else class="empty-cart">Корзина пуста</p>
    </div>

    <!-- Сводка -->
    <div class="cart-summary">
      <h4>Ваш заказ:</h4>
      <div class="summary-details">
        <p><strong>Количество:</strong> {{ cartCount }}</p>
        <p><strong>Итого:</strong> {{ formatPrice(cartTotal) }}</p>
      </div>
      <button
        @click="checkout"
        :disabled="!cartItems.length"
        class="btn btn-success"
      >
        Оформить заказ
      </button>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';
import ProductCard from './ProductCard.vue'; // Вынесли карточку товара
import { formatPrice } from '@/utils/formatPrice';
import OrderService from '@/services/OrderService';

export default {
  components: { ProductCard },
  computed: {
    ...mapGetters('cart', ['cartItems', 'cartTotal', 'cartCount']),
  },
  methods: {
    ...mapActions('cart', ['loadCart', 'removeFromCart', 'updateQuantity', 'clearCart']),

    async changeQuantity(productId, newQuantity) {
      try {
        if (newQuantity <= 0) {
          await this.removeFromCart({ productId });
        } else {
          await this.updateQuantity({ productId, quantity: newQuantity });
        }
      } catch (error) {
        console.error('Ошибка изменения количества:', error);
      }
    },

    async removeFromCartAction(productId) {
      try {
        await this.removeFromCart({ productId });
      } catch (error) {
        console.error('Ошибка удаления из корзины:', error);
      }
    },

    async checkout() {
      try {
        const response = await OrderService.createOrder();
        alert(`Заказ успешно оформлен! Номер заказа: ${response.orderId}`);
        await this.clearCart();
      } catch (error) {
        console.error('Ошибка оформления заказа:', error);
        alert('Не удалось оформить заказ. Попробуйте позже.');
      }
    },

    formatPrice(price) {
      return formatPrice(price);
    },
  },
  mounted() {
    this.loadCart();
  },
};
</script>

<style scoped>
:root {
  --primary-color: #56a841;
  --secondary-color: #f9f9f9;
  --border-color: #ddd;
  --font-color: #000;
}

.cart-container {
  display: flex;
  gap: 20px;
  margin: 20px;
}

.cart-items {
  flex: 3;
  background-color: var(--secondary-color);
  border: 1px solid var(--border-color);
  border-radius: 5px;
  padding: 16px;
}

.cart-label {
  font-size: 1.5rem;
  margin-bottom: 16px;
  font-weight: bold;
}

.cart-products {
  list-style: none;
  padding: 0;
}

.empty-cart {
  font-size: 1.2rem;
  color: var(--font-color);
}

.cart-summary {
  flex: 1;
  background-color: var(--secondary-color);
  border: 1px solid var(--border-color);
  border-radius: 5px;
  padding: 16px;
}

.cart-summary h4 {
  font-size: 1.3rem;
  margin-bottom: 16px;
}

.summary-details {
  font-size: 1rem;
  margin-bottom: 20px;
}

.btn-success {
  width: 100%;
}
</style>
