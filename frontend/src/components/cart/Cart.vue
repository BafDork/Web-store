<template>
  <div>
    <h1>Корзина</h1>
    <ul>
      <li v-for="item in cart" :key="item.id">
        <h2>{{ item.name }}</h2>
        <p>{{ formatPrice(item.price) }} ₽</p>
        
        <!-- Контроллер для изменения количества -->
        <div class="quantity-control">
          <button @click="decreaseQuantity(item)" :disabled="item.quantity <= 1">-</button>
          <input type="number" v-model.number="item.quantity" min="1" />
          <button @click="increaseQuantity(item)">+</button>
        </div>

        <p>Количество: {{ item.quantity }}</p>
        <p>Сумма: {{ formatPrice(item.price * item.quantity) }} ₽</p>

        <!-- Кнопка для удаления товара из корзины -->
        <button @click="removeFromCart(item.id)">Удалить</button>
      </li>
    </ul>
    <h2>Итого: {{ formatPrice(cartTotal) }} ₽</h2>
    <button @click="checkout">Оформить заказ</button>
  </div>
</template>

  
<script>
import { mapGetters, mapActions } from 'vuex';

export default {
  computed: {
    ...mapGetters('cart', ['cart', 'cartTotal'])
  },

  methods: {
    ...mapActions('cart', ['removeFromCart', 'updateCartItemQuantity']),

    formatPrice(price) {
      return new Intl.NumberFormat('ru-RU', {
        style: 'currency',
        currency: 'RUB'
      }).format(price);
    },

    increaseQuantity(item) {
      const newQuantity = item.quantity + 1;
      this.addToCart({ productId: item.id, quantity: newQuantity });
    },

    decreaseQuantity(item) {
      const newQuantity = item.quantity - 1;
      if (newQuantity > 0) {
        this.addToCart({ productId: item.id, quantity: newQuantity });
      }
    },

    removeFromCart(productId) {
      this.removeFromCart(productId);
    },

    checkout() {
      alert('Заказ оформлен!');
    }
  }
};
</script>
