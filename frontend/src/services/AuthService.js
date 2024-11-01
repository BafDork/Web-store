// src/services/AuthService.js
import axios from 'axios';

class AuthService {
  async login(email, password) {
    const response = await axios.post('http://localhost:8080/auth/sign-in', { email, password });
    const { token, userId } = response.data;

    // Сохраняем токен и userId в localStorage
    localStorage.setItem('token', token);
    localStorage.setItem('userId', userId);
    
    return { token, userId };
  }

  async logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
  }

  getToken() {
    return localStorage.getItem('token');
  }
}

export default new AuthService();
