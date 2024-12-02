import axios from '@/plugins/axios';

const API_URL = 'http://localhost:8080/api/order';

class OrderService {

  static async createOrder() {
    try {
      const response = await axios.post(`${API_URL}/checkout`);
      return response.data;
    } catch (error) {
      console.error('Ошибка при создании заказа:', error);
      throw error;
    }
  }

}

export default OrderService;
