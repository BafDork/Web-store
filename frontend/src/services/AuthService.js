import axios from 'axios';

const AUTH_URL = 'http://localhost:8080/auth';

class AuthService {

  async login(email, password) {
    const response = await axios.post(`${AUTH_URL}/sign-in`, { email, password });
    const { token } = response.data;

    localStorage.setItem('token', token);

    return { token };
  }

  async register(email, password, firstName, lastName) {
    const response = await axios.post(`${AUTH_URL}/sign-up`, {
      email,
      password,
      firstName,
      lastName
    });

    const { token } = response.data;
    localStorage.setItem('token', token);
    
    return { token };
  }

  async logout() {
    localStorage.removeItem('token');
  }

  getToken() {
    return localStorage.getItem('token');
  }
}

export default new AuthService();
