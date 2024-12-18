import axios from '@/plugins/axios'; 

const API_URL = 'http://localhost:8080/api/review';
const ADMIN_URL = 'http://localhost:8080/admin/review';

class ReviewService {

  static async getReviews(productId) {
    try {
      const response = await axios.get(`${API_URL}/${productId}`);
      return response.data;
    } catch (error) {
      console.error(`Ошибка при получении отзывов для продукта с ID ${productId}:`, error);
      throw error;
    }
  }

  static async addReview(reviewData) {
    try {
      const response = await axios.post(`${API_URL}`, reviewData);
      return response.data;
    } catch (error) {
      console.error('Ошибка при добавлении отзыва:', error);
      throw error;
    }
  }

  static async addPhoto(reviewId, formData) {
    try {
      const response = await axios.post(`${API_URL}-photos/${reviewId}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        }
      });
      return response.data;
    } catch (error) {
      console.error('Ошибка при добавлении фотографии:', error);
      throw error;
    }
  }

  static async getPhoto(photoId) {
    try {
      const response = await axios.get(`${API_URL}-photos/${photoId}`, {
        responseType: 'arraybuffer',
      });
      return response.data;
    } catch (error) {
      console.error('Ошибка при добавлении фотографии:', error);
      throw error;
    }
  }

  static async deletePhoto(photoId) {
    try {
      const response = await axios.delete(`${ADMIN_URL}-photos/delete/${photoId}`);
      return response.data;
    } catch (error) {
      console.error(`Ошибка при удалении фото с ID ${photoId}:`, error);
      throw error;
    }
  }

  static async deleteReview(reviewId) {
    try {
      const response = await axios.delete(`${ADMIN_URL}/delete/${reviewId}`);
      return response.data;
    } catch (error) {
      console.error(`Ошибка при удалении отзыва с ID ${reviewId}:`, error);
      throw error;
    }
  }
}

export default ReviewService;
