import axios from '@/plugins/axios';

const API_URL = 'http://localhost:8080/auth';

class AuthService {
  async login(email, password) {
    try {
      const response = await axios.post(`${API_URL}/sign-in`, { email, password });
      return response.data;
    } catch (error) {
      console.error('Login error:', error);
      throw error;
    }
  }

  async signUp(email, password, firstName, lastName) {
    try {
      const response = await axios.post(`${API_URL}/sign-up`, {
        firstName,
        lastName,
        email,
        password,
      });
      return response.data;
    } catch (error) {
      console.error('Sign-up error:', error);
      throw error;
    }
  }

  logout() {
    localStorage.removeItem('token');
  }

  getToken() {
    return localStorage.getItem('token');
  }

  setToken(token) {
    localStorage.setItem('token', token);
  }
}

export default new AuthService();
