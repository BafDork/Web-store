import axios from '@/plugins/axios';

const API_URL = 'http://localhost:8080/api/product';

class ProductService {

  static getAllProducts() {
    return axios.get(`${API_URL}/all`);
  }

  static getProductsByCategory(categoryId) {
    return axios.get(`${API_URL}/category/${categoryId}`);
  }
}

export default ProductService;

