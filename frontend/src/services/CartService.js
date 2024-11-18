import axios from 'axios';

const API_URL = 'http://localhost:8080/api/cart';

class CartService {
  static getCart() {
    return axios.get(API_URL);
  }

  static addToCart(productId, quantity) {
    return axios.post(`${API_URL}/add`, { productId, quantity });
  }

  static removeFromCart(productId) {
    return axios.delete(`${API_URL}/remove/${productId}`);
  }

  static updateQuantity(productId, quantity) {
    return axios.put(`${API_URL}/update-quantity`, { productId, quantity });
  }

  async createOrder() {
    try {
      const response = await axios.post(API_URL + '/checkout');
      return response.data;
    } catch (error) {
      console.error('Ошибка при создании заказа:', error);
      throw error;
    }
  }
}

export default CartService;
