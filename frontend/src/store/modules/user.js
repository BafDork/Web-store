export default {
    state: () => ({
      user: null,
    }),
    mutations: {
      setUser(state, user) {
        state.user = user;
      }
    },
    actions: {
      login({ commit }, user) {
        commit('setUser', user);
      },
      logout({ commit }) {
        commit('setUser', null);
        commit('cart/clearCart', null, { root: true }); // Очистка корзины при выходе
      }
    },
    getters: {
      isAuthenticated: (state) => !!state.user,
    }
  };
  