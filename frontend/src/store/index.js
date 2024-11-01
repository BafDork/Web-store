import { createStore } from 'vuex';
import user from './modules/user';
import cart from './modules/cart';
import catalog from './modules/catalog';

export default createStore({
  modules: {
    user,
    cart,
    catalog,
  }
});