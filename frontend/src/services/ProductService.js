import axios from '@/plugins/axios';

const API_URL = 'http://localhost:8080/api/product';
const ADMIN_URL = 'http://localhost:8080/admin/product';

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

  static async addProduct(product) {
    try {
      const response = await axios.post(`${ADMIN_URL}/add`, product);
      return response.data;
    } catch (error) {
      console.error('Ошибка при добавлении продукта:', error);
      throw error;
    }
  }

  static async deleteProduct(productId) {
    try {
      const response = await axios.delete(`${ADMIN_URL}/delete/${productId}`);
      return response.data;
    } catch (error) {
      console.error('Ошибка при удалении продукта:', error);
      throw error;
    }
  }

  static async updateProduct(productId, product) {
    try {
      const response = await axios.put(`${ADMIN_URL}/update/${productId}`, product);
      return response.data;
    } catch (error) {
      console.error(`Ошибка при обновлении продукта с ID ${productId}:`, error);
      throw error;
    }
  }
}

export default ProductService;
