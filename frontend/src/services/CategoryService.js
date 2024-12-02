import axios from '@/plugins/axios';

const API_URL = 'http://localhost:8080/api/category';

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
}

export default CategoryService;
