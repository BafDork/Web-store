import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

class ProductService {

  static getCategories() {
    return axios.get(`${API_URL}/categories`);
  }

  static getAllProducts() {
    return axios.get(`${API_URL}/products`);
  }

  static getProductsByCategory(categoryId) {
    return axios.get(`${API_URL}/products/category/${categoryId}`);
  }
}

export default ProductService;

