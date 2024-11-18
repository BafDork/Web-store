import axios from '@/plugins/axios';

const API_URL = 'http://localhost:8080/api/order';

class OrderService {

  static async createOrder() {
    const response = await axios.post(`${API_URL}/checkout`);
    return response.data;
  }

}

export default OrderService;