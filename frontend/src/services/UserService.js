import axios from '@/plugins/axios';

const API_URL = 'http://localhost:8080/api/user';

class UserService {
  
  static async fetchUser() {
    try {
      const response = await axios.get(`${API_URL}/me`);
      return response.data;
    } catch (error) {
      console.error('Ошибка при получении данных пользователя:', error);
      throw error;
    }
  }
}

export default UserService;
