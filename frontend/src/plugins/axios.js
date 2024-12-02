import axios from 'axios';
import AuthService from '@/services/AuthService';

axios.interceptors.request.use(
  (request) => {
    const token = AuthService.getToken();
    if (token) {
      request.headers.Authorization = `Bearer ${token}`;
    }
    return request;
  },
  (error) => {
    console.error('Ошибка запроса:', error);
    return Promise.reject(error);
  }
);

axios.interceptors.response.use(
  (response) => {
    if (response.data?.token) {
      const { token } = response.data;
      localStorage.setItem('token', token);
      console.log('Новый токен сохранен');
    }
    return response;
  },
  (error) => {
    console.error('Ошибка ответа:', error);
    if (error.response && error.response.status === 401) {
      AuthService.logout();
    }
    return Promise.reject(error);
  }
);

export default axios;
