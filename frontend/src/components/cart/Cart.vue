<template>
  <div>
    <h1>Корзина</h1>
    <ul v-if="cartItems && cartItems.length">
      <li v-for="item in cartItems" :key="item.product.id">
        <h2>{{ item.product.name }}</h2>
        <p>{{ formatPrice(finalPrice(item.product))}} ₽</p>

        <!-- Контроллер для изменения количества -->
        <div class="quantity-control">
          <button @click="decreaseQuantity(item)">-</button>
          <input type="number" v-model.number="item.quantity" />
          <button @click="increaseQuantity(item)" :disabled="item.quantity >= item.product.stock">+</button>
        </div>

        <p>Количество: {{ item.quantity }}</p>
        <p>Сумма: {{ formatPrice(finalPrice(item.product) * item.quantity) }} ₽</p>

        <!-- Кнопка для удаления товара из корзины -->
        <button @click="removeFromCartAction(item.product.id)">Удалить</button>
      </li>
    </ul>
    <p v-else>Корзина пуста</p>
    <h2>Итого: {{ formatPrice(cartTotal) }} ₽</h2>
    <button @click="checkout">Оформить заказ</button>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';
import { formatPrice } from '@/utils/formatPrice';

export default {

  computed: {
    ...mapGetters('cart', ['cartItems', 'cartTotal'])
  },

  methods: {
    ...mapActions('cart', ['loadCart', 'removeFromCart', 'updateQuantity']),

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
      this.removeFromCart({ productId: productId });
    },

    async fetchCart() {
      try {
        await this.loadCart();
      } catch (error) {
        console.error("Ошибка при загрузке корзины:", error);
      };
    },

    checkout() {
      alert('Заказ оформлен!');
    },

    finalPrice(product) {
      if (product.discountPrice) {
        return product.discountPrice;
      } else {
        return product.price;
      }
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
