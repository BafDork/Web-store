import axios from 'axios';

const API_URL = 'http://localhost:8080/api/cart';

class CartService {
  static async getCart() {
    try {
      const response = await axios.get(API_URL);
      return response.data;
    } catch (error) {
      console.error('Ошибка при получении корзины:', error);
      throw error;
    }
  }

  static async addToCart(productId, quantity) {
    try {
      const response = await axios.post(`${API_URL}/add`, { productId, quantity });
      return response.data;
    } catch (error) {
      console.error('Ошибка при добавлении товара в корзину:', error);
      throw error;
    }
  }

  static async removeFromCart(productId) {
    try {
      const response = await axios.delete(`${API_URL}/remove/${productId}`);
      return response.data;
    } catch (error) {
      console.error('Ошибка при удалении товара из корзины:', error);
      throw error;
    }
  }

  static async updateQuantity(productId, quantity) {
    try {
      const response = await axios.put(`${API_URL}/update-quantity`, { productId, quantity });
      return response.data;
    } catch (error) {
      console.error('Ошибка при обновлении количества товара:', error);
      throw error;
    }
  }

  async createOrder() {
    try {
      const response = await axios.post(`${API_URL}/checkout`);
      return response.data;
    } catch (error) {
      console.error('Ошибка при создании заказа:', error);
      throw error;
    }
  }
}

export default CartService;
