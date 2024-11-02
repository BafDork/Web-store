import axios from 'axios';

const API_URL = 'http://localhost:8080/api/cart';

class CartService {
  static getCart() {
    return axios.get(`${API_URL}`);
  }

  static addToCart(productId, quantity) {
    return axios.post(`${API_URL}/add`, {
      productId,
      quantity,
    });
  }

  static removeFromCart(productId, quantity) {
    return axios.delete(`${API_URL}/remove`, {
      productId, 
      quantity,
    });
  }
}

export default CartService;