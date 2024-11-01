import axios from 'axios';

const API_URL = 'http://localhost:8080/api/cart';

export default {
  getCart(userId) {
    return axios.get(`${API_URL}/${userId}`);
  },

  addToCart(userId, productId, quantity) {
    return axios.post(`${API_URL}/add`, {
      userId,
      productId,
      quantity,
    });
  },

  removeFromCart(userId, productId) {
    return axios.delete(`${API_URL}/remove`, {
      data: { userId, productId },
    });
  },
};