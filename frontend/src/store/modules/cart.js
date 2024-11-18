import CartService from '@/services/CartService';

export default {
  namespaced: true,
  state: {
    cart: [],
  },

  mutations: {
    setCart(state, cart) {
      state.cart = cart;
    },

    addItem(state, { product, quantity }) {
      const existingItem = state.cart.find(i => i.product.id === product.id);
      if (existingItem) {
        existingItem.quantity += quantity;
      } else {
        state.cart.push({ product, quantity });
      }
    },

    removeItem(state, productId) {
      state.cart = state.cart.filter(item => item.product.id !== productId);
    },

    updateItemQuantity(state, { productId, quantity }) {
      const item = state.cart.find(i => i.product.id === productId);
      if (item) {
        item.quantity = quantity;
      }
    }
  },

  actions: {
    async loadCart({ commit }) {
      const response = await CartService.getCart();
      commit('setCart', response.data);
    },

    async addToCart({ commit }, { product, quantity }) {
      await CartService.addToCart(product.id, quantity);
      commit('addItem', { product, quantity });
    },

    async removeFromCart({ commit }, { productId }) {
      await CartService.removeFromCart(productId);
      commit('removeItem', productId);
    },

    async updateQuantity({ commit }, { productId, quantity }) {
      await CartService.updateQuantity(productId, quantity);
      commit('updateItemQuantity', { productId, quantity });
    },

    clearCart({ commit }) {
      commit('setCart', []);
    }
  },

  getters: {
    cartItems: state => state.cart,
    cartCount: state => state.cart.length,
    cartTotal: state => state.cart.reduce((total, item) => total + ((item.product.price || item.product.discountPrice) * item.quantity), 0),
    isProductInCart: (state) => (productId) => (state.cart && state.cart.some(item => item.product.id === productId)),
  }
};
