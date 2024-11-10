import axios from '@/plugins/axios';

const API_URL = 'http://localhost:8080/api/user';

class UserService {
  async fetchUser() {
    const response = await axios.get(`${API_URL}/me`);
    return response.data;
  }
}

export default new UserService();