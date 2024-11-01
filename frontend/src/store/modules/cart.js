import CartService from '@/services/CartService';

export default {
  state: {
    items: [],
  },

  mutations: {
    setCart(state, items) {
      state.items = items;
    },

    addItem(state, item) {
      state.items.push(item);
    },

    removeItem(state, productId) {
      state.items = state.items.filter(item => item.productId !== productId);
    },
  },

  actions: {
    async fetchCart({ commit }, userId) {
      const response = await CartService.getCart(userId);
      commit('setCart', response.data.products);
    },

    // ВОНЯЕТ ПИЗДЕЖОМ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    async addToCart({ commit }, { userId, productId, quantity }) {
      await CartService.addToCart(userId, productId, quantity);
      commit('addItem', { productId, quantity });
    },

    async removeFromCart({ commit }, { userId, productId }) {
      await CartService.removeFromCart(userId, productId);
      commit('removeItem', productId);
    },
  },

  getters: {
    cartItems: state => state.items,
    cartTotal: state => state.items.reduce((total, item) => total + item.price * item.quantity, 0),
  },
};
