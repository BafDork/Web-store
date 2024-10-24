import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

class ProductService {
  static getAllProducts(sortOrder) {
    return axios.get(`${API_URL}/products?sort=${sortOrder}`);
  }

  static getCategories() {
    return axios.get(`${API_URL}/categories`);
  }

  static getProductsByCategory(categoryId, sortOrder) {
    return axios.get(`${API_URL}/products?categoryId=${categoryId}&sort=${sortOrder}`);
  }
}

export default ProductService;

