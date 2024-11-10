import axios from '@/plugins/axios';

const API_URL = 'http://localhost:8080/api/category';

class CategoryService {

  static getTopLevelCategories() {
    return axios.get(`${API_URL}/top-level`);
  }

}

export default CategoryService;

