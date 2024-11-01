export default {
    state: () => ({
      showCatalog: false,
    }),
    mutations: {
      toggleCatalog(state) {
        state.showCatalog = !state.showCatalog;
      }
    },
    actions: {
      toggleCatalog({ commit }) {
        commit('toggleCatalog');
      }
    },
    getters: {
      showCatalog: (state) => state.showCatalog,
    }
  };
  