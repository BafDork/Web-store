import axios from '@/plugins/axios';

const API_URL = 'http://localhost:8080/api/category';
const ADMIN_URL = 'http://localhost:8080/admin/category';

class CategoryService {

  static async getCategoryById(categoryId) {
    try {
      const response = await axios.get(`${API_URL}/${categoryId}`);
      return response.data;
    } catch (error) {
      console.error(`Ошибка при получении категории с ID ${categoryId}:`, error);
      throw error;
    }
  }

  static async getAllCategories() {
    try {
      const response = await axios.get(`${API_URL}/all`);
      return response.data;
    } catch (error) {
      console.error('Ошибка при получении всех категорий:', error);
      throw error;
    }
  }
  
  static async getTopLevelCategories() {
    try {
      const response = await axios.get(`${API_URL}/top-level`);
      return response.data;
    } catch (error) {
      console.error('Ошибка при получении верхнего уровня категорий:', error);
      throw error;
    }
  }

  static async addCategory(category) {
    try {
      const response = await axios.post(`${ADMIN_URL}/add`, category);
      return response.data;
    } catch (error) {
      console.error('Ошибка при добавлении категории:', error);
      throw error;
    }
  }

  static async deleteCategory(categoryId) {
    try {
      const response = await axios.delete(`${ADMIN_URL}/delete/${categoryId}`);
      return response.data;
    } catch (error) {
      console.error('Ошибка при удалении категории:', error);
      throw error;
    }
  }

  static async updateCategory(categoryId, category) {
    try {
      const response = await axios.put(`${ADMIN_URL}/update/${categoryId}`, category);
      return response.data;
    } catch (error) {
      console.error(`Ошибка при обновлении категории с ID ${categoryId}:`, error);
      throw error;
    }
  }
}

export default CategoryService;
