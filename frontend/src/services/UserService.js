import axios from 'axios';

const API_URL = 'http://localhost:8080/api/user';

class UserService {
  
    async getUser() {
        return await axios.get(`${API_URL}`);
    }
  }
  
  export default new AuthService();