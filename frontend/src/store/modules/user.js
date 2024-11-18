import AuthService from '@/services/AuthService';
import UserService from '@/services/UserService';

export default {
  namespaced: true,
  state: {
    user: null,
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
    },
    clearUser(state) {
      state.user = null;
    }
  },
  actions: {
    async login({ dispatch }, { email, password }) {
      await AuthService.login(email, password);
      await dispatch('loadUser');
    },

    async register({ dispatch }, { email, password, firstName, lastName }) {
      await AuthService.signUp(email, password, firstName, lastName);
      await dispatch('loadUser');
    },

    async loadUser({ commit }) {
      const user = await UserService.fetchUser();
      commit('setUser', user);
    },

    logout({ commit }) {
      AuthService.logout();
      commit('clearUser');
    }
  },
  getters: {
    getUser: (state) => state.user,
    isAuthenticated: (state) => !!state.user && !!AuthService.getToken(),
    userName: (state) => state.user ? `${state.user.firstName} ${state.user.lastName}` : '',
  }
};
