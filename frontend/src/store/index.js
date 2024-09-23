import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: null,
    cart: []
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
    },
    addToCart(state, product) {
      state.cart.push(product);
    },
    clearCart(state) {
      state.cart = [];
    }
  },
  actions: {
    login({ commit }, user) {
      commit('setUser', user);
    },
    logout({ commit }) {
      commit('setUser', null);
      commit('clearCart');
    },
    addToCart({ commit }, product) {
      commit('addToCart', product);
    }
  },
  getters: {
    isAuthenticated: state => !!state.user,
    cartCount: state => state.cart.length,
    cartTotal: state => state.cart.reduce((total, product) => total + product.price, 0)
  },
  modules: {}
});