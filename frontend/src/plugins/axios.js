import axios from 'axios';
import AuthService from '@/services/AuthService';

axios.interceptors.request.use((request) => {
  const token = AuthService.getToken();
  if (token) {
    request.headers.Authorization = `Bearer ${token}`;
  }
  return request;
}, (error) => Promise.reject(error));

axios.interceptors.response.use((response) => {
    if (response.data?.token) {
      const { token } = response.data;
      localStorage.setItem('token', token);
      localStorage.setItem('token', token);
    }
    return response;
  }, (error) => Promise.reject(error));

export default axios;
