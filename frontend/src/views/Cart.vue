<template>
    <div>
      <h1>Корзина</h1>
      <ul>
        <li v-for="item in cart" :key="item.id">
          <h2>{{ item.name }}</h2>
          <p>{{ item.price }} ₽</p>
          <button @click="removeFromCart(item)">Удалить</button>
        </li>
      </ul>
      <h2>Итого: {{ cartTotal }} ₽</h2>
      <button @click="checkout">Оформить заказ</button>
    </div>
  </template>
  
  <script>
  import { mapGetters, mapActions } from 'vuex';
  
  export default {
    computed: {
      ...mapGetters(['cart', 'cartTotal'])
    },
    methods: {
      ...mapActions(['removeFromCart']),
      checkout() {
        alert('Заказ оформлен!');
        // Здесь логика оформления заказа
      }
    }
  };
  </script>