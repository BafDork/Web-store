import axios from '@/plugins/axios';

const API_URL = 'http://localhost:8080/auth';

class AuthService {
  async login(email, password) {
    const response = await axios.post(`${API_URL}/sign-in`, { email, password });
    const { token } = response.data;
    localStorage.setItem('token', token);
    return { token };
  }

  async signUp(email, password, firstName, lastName) {
    const response = await axios.post(`${API_URL}/sign-up`, {
      firstName,
      lastName,
      email,
      password
    });
    const { token } = response.data;
    localStorage.setItem('token', token);
    return { token };
  }

  logout() {
    localStorage.removeItem('token');
  }

  getToken() {
    return localStorage.getItem('token');
  }
}

export default new AuthService();
