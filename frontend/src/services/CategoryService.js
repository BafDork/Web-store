import axios from '@/plugins/axios';

const API_URL = 'http://localhost:8080/api/category';
const ADMIN_URL = 'http://localhost:8080/admin/category';

class CategoryService {
  
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
}

export default CategoryService;
