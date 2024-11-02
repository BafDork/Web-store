export default {
    state: () => ({
      user: {
        firstName: '',
        lastName: '',
      },
    }),
    mutations: {
      setUser(state, { firstName, lastName }) {
        state.user.firstName = firstName;
        state.user.lastName = lastName;
      },
    },
    actions: {
      async login({ commit }, user) {
        const response = await UserService.getUser();
        commit('setUser', {
          firstName: response.data.firstName,
          lastName: response.data.lastName,
        });
      },
      logout({ commit }) {
        commit('setUser', null);
        commit('cart/clearCart', null, { root: true });
      }
    },
    getters: {
      isAuthenticated: (state) => !!state.user,
      userName: state => `${state.user.firstName} ${state.user.lastName}`,
    }
  };
  