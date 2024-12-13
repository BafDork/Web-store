import axios from '@/plugins/axios';

const API_URL = 'http://localhost:8080/api/product';

class ProductService {

  static async getProductById(productId) {
    try {
      const response = await axios.get(`${API_URL}/${productId}`);
      return response.data;
    } catch (error) {
      console.error(`Ошибка при получении продукта с ID ${productId}:`, error);
      throw error;
    }
  }

  static async getAllProducts() {
    try {
      const response = await axios.get(`${API_URL}/all`);
      return response.data;
    } catch (error) {
      console.error('Ошибка при получении всех продуктов:', error);
      throw error;
    }
  }

  static async getProductsByCategory(categoryId) {
    try {
      const response = await axios.get(`${API_URL}/category/${categoryId}`);
      return response.data;
    } catch (error) {
      console.error(`Ошибка при получении продуктов для категории с ID ${categoryId}:`, error);
      throw error;
    }
  }
}

export default ProductService;
