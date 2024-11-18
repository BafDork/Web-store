import axios from '@/plugins/axios';

const API_URL = 'http://localhost:8080/auth';

class AuthService {
  async login(email, password) {
    await axios.post(`${API_URL}/sign-in`, { email, password });
  }

  async signUp(email, password, firstName, lastName) {
    await axios.post(`${API_URL}/sign-up`, {
      firstName,
      lastName,
      email,
      password
    });
  }

  logout() {
    localStorage.removeItem('token');
  }

  getToken() {
    return localStorage.getItem('token');
  }
}

// поправить на статик
export default new AuthService();
