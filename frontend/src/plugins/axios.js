// src/plugins/axios.js
import axios from 'axios';
import AuthService from '@/services/AuthService';

// Интерсептор для автоматического добавления токена
axios.interceptors.request.use((config) => {
  const token = AuthService.getToken();
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, (error) => Promise.reject(error));

export default axios;
