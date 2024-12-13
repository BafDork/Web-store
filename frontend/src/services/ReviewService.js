import axios from '@/plugins/axios';

const API_URL = 'http://localhost:8080/api/review';

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
}

export default ReviewService;
