import CartService from '@/services/CartService';

export default {
  state: {
    items: [],
  },

  mutations: {
    setCart(state, items) {
      state.items = items;
    },

    addItem(state, { productId, quantity }) {
      const existingItem = state.items.find(item => item.productId === productId);
      if (existingItem) {
        existingItem.quantity += quantity;
      } else {
        state.items.push({ productId, quantity });
      }
    },

    updateItemQuantity(state, { productId, quantity }) {
      const existingItem = state.items.find(item => item.productId === productId);
      if (existingItem) {
        existingItem.quantity = quantity;
      }
    },

    removeItem(state, productId) {
      state.items = state.items.filter(item => item.productId !== productId);
    },
  },

  actions: {
    async fetchCart({ commit }) {
      const response = await CartService.getCart();
      commit('setCart', response.data.items);
    },

    async addToCart({ commit }, { productId, quantity }) {
      await CartService.addToCart(productId, quantity);
      commit('addItem', { productId, quantity });
    },

    async updateCartItemQuantity({ commit }, { productId, quantity }) {
      await CartService.updateProductQuantity(productId, quantity);
      commit('updateItemQuantity', { productId, quantity });
    },

    async removeProductFromCart({ commit }, productId) {
      await CartService.removeProductFromCart(productId);
      commit('removeItem', productId);
    },
  },

  getters: {
    cartItems: state => state.items,
    cartCount: state => state.items.reduce((count, item) => count + item.quantity, 0),
    cartTotal: state => state.items.reduce((total, item) => total + item.price * item.quantity, 0),
  },
};
