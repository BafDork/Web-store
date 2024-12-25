<template>
  <div class="product-page d-flex justify-content-center align-items-center">
    <div class="product-card container">
      <!-- Содержание товара -->
      <div class="row">
        <!-- Изображение товара -->
        <div class="col-md-4 text-center">
          <img 
            :src="product.imageUrl" 
            alt="Product Image" 
            class="product-image img-fluid rounded border" 
          />
        </div>

        <!-- Информация о товаре -->
        <div class="col-md-8">
          <div class="product-info">
            <h2 class="product-name text-success">{{ product.name }}</h2>
            <p class="product-description text-muted">{{ product.description }}</p>

            <div class="product-stock my-3">
              <span :class="['badge', getAvailabilityClass(product.stock)]">
                {{ getAvailabilityMessage(product.stock) }}
              </span>
            </div>

            <div class="price-and-cart d-flex align-items-center mt-4">
              <div class="me-4">
                <p v-if="isAuthenticated && product.discountPrice !== null">
                  <span class="original-price text-decoration-line-through text-muted">
                    {{ formatPrice(product.price) }}
                  </span>
                  <span class="discounted-price text-danger fw-bold ms-2">
                    {{ formatPrice(product.discountPrice) }}
                  </span>
                </p>
                <p v-else class="regular-price fw-bold fs-5">{{ formatPrice(product.price) }}</p>
              </div>
              <button 
                v-if="!isAuthenticated" 
                @click="redirectToLogin"
                class="btn btn-primary">
                В корзину
              </button>
              <button 
                v-else 
                :disabled="product.stock < 1 || isInCart" 
                @click="addToCartAction"
                class="btn btn-primary">
                {{ isInCart ? "Уже в корзине" : "В корзину" }}
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Раздел: Отзывы -->
      <div class="reviews mt-5">
        <h3 class="border-bottom pb-2">Отзывы:</h3>
        <div class="filter-reviews mb-3">
          <select v-model="selectedRating" id="filter" class="form-select w-100">
            <option value="">Все оценки</option>
            <option value="1">1 звезда</option>
            <option value="2">2 звезды</option>
            <option value="3">3 звезды</option>
            <option value="4">4 звезды</option>
            <option value="5">5 звезд</option>
          </select>
        </div>
        <div v-if="filteredReviews.length > 0" class="review-list">
          <ul class="list-unstyled">
            <li v-for="review in filteredReviews" :key="review.id" class="p-3 mb-3 bg-light rounded shadow-sm">
              <strong>{{ review.author }}</strong>
              <span class="text-warning"> ({{ review.rating }} из 5)</span>
              <p class="mb-0 mt-2">{{ review.text }}</p>
              <div v-if="review.photos && review.photos.length > 0" class="review-photos mt-2 d-flex flex-wrap">
                <div v-for="photo in review.photos" :key="photo.id" class="review-photo me-2">
                  <img 
                    :src="photo.file" 
                    alt="Review Photo" 
                    class="img-fluid rounded review-photo-img" 
                    @click="togglePhotoOverlay(photo.file)" 
                  />
                  <!-- Кнопка для удаления фото -->
                  <button 
                    v-if="isAdmin" 
                    @click.stop="deletePhoto(photo.id)"
                    class="btn-close btn-delete-photo position-absolute" 
                    aria-label="Удалить фото"
                  ></button>
                </div>
              </div>
              <!-- Кнопка для удаления отзыва -->
              <button 
                v-if="isAdmin" 
                @click.stop="deleteReview(review.id)" 
                class="btn btn-danger mt-2">
                Удалить отзыв
              </button>
            </li>
          </ul>
        </div>
        <div v-else>
          <p class="text-muted">Отзывов пока нет. Будьте первым, кто оставит отзыв!</p>
        </div>
      </div>

      <!-- Раздел: Добавление отзыва -->
      <div v-if="isAuthenticated" class="add-review mt-4">
        <h3 class="border-bottom pb-2">Добавьте отзыв:</h3>
        <textarea 
          v-model="newReview.text" 
          placeholder="Ваш отзыв..." 
          rows="4" 
          class="form-control mb-3">
        </textarea>
        <div class="rating mb-3">
          <label for="rating" class="form-label">Оценка:</label>
          <div id="rating" class="star-rating">
            <span 
              v-for="n in 5" 
              :key="n" 
              @click="newReview.rating = n"
              :class="['star', { 'selected': newReview.rating >= n }]">
              &#9733;
            </span>
          </div>
        </div>
        <div class="form-group mb-3">
          <label for="photos" class="form-label optional-label">Фото (опционально):</label>
          <input type="file" 
            id="photos" 
            multiple 
            @change="handleFileChange" 
            class="form-control" 
            ref="fileInput"
          />
          <div v-if="newReview.photos.length > 0" class="mt-2">
            <h5>Предпросмотр:</h5>
            <div class="photo-preview d-flex">
              <div v-for="(photo, index) in newReview.photos" :key="index" class="preview-photo me-2">
                <img :src="photo.url" alt="Preview" class="img-thumbnail" width="100" height="100" />
              </div>
            </div>
          </div>
        </div>
        <button @click="submitReview" class="btn btn-success">Отправить отзыв</button>
        <div v-if="emptyReviewWarning" class="alert alert-warning mt-3">Отзыв не может быть пустым!</div>
      </div>
      <div v-else class="not-authenticated text-center mt-4">
        <p class="text-muted">Чтобы оставить отзыв, необходимо авторизоваться.</p>
      </div>

      <!-- Фото поверх окна -->
      <div v-if="overlayPhoto" class="photo-overlay" @click="togglePhotoOverlay(null)">
        <img :src="overlayPhoto" alt="Enlarged Photo" class="enlarged-photo" />
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import { formatPrice } from '@/utils/formatPrice';
import ProductService from '@/services/ProductService';
import ReviewService from '@/services/ReviewService';
import AuthService from '@/services/AuthService';

export default {
  props: ['productId'],

  data() {
    return {
      product: Object,
      reviews: [],
      newReview: {
        text: '',
        rating: 5,
        photos: [],
      },
      selectedRating: '',
      overlayPhoto: null,
      emptyReviewWarning: false,
    };
  },

  computed: {
    ...mapGetters('cart', ['isProductInCart']),
    ...mapGetters('user', ['isAuthenticated']),

    isInCart() {
      return this.isProductInCart(Number(this.productId));
    },

    isAdmin() {
      return AuthService.getRole() === 'ROLE_ADMIN';
    },

    filteredReviews() {
      if (!this.selectedRating) return this.reviews;
      return this.reviews.filter(review => review.rating === parseInt(this.selectedRating));
    },
  },

  async created() {
    this.loadProduct();
    this.loadReviews();
  },

  methods: {
    ...mapActions('cart', ['addToCart']),

    redirectToLogin() {
      this.$router.push('/auth/sign-in');
    },

    addToCartAction() {
      if (!this.isInCart) {
        this.addToCart({ product: this.product, quantity: 1 });
      }
    },

    async loadProduct() {
      try {
        this.product = await ProductService.getProductById(this.productId);
      } catch (error) {
        console.error('Не удалось загрузить продукт:', error);
      }
    },

    async loadReviews() {
      try {
        const reviews = await ReviewService.getReviews(this.productId);
        
        for (let review of reviews) {
          if (review.photos && review.photos.length > 0) {
            for (let i = 0; i < review.photos.length; i++) {
              const photo = await this.loadPhotoForReview(review.photos[i]);
              review.photos[i] = { id: review.photos[i], file: photo };
            }
          }
        }

        this.reviews = reviews;
      } catch (error) {
        console.error('Ошибка при загрузке отзывов:', error);
      }
    },

    async loadPhotoForReview(photoId) {
      try {
        const photoData = await ReviewService.getPhoto(photoId);
        const blob = new Blob([photoData], { type: 'image/jpeg' });
        const imageUrl = URL.createObjectURL(blob);
        return imageUrl;
      } catch (error) {
        console.error('Ошибка при загрузке фотографии для отзыва:', error);
        return null;
      }
    },

    async submitReview() {
      if (this.newReview.text.trim() === '' && this.newReview.photos.length === 0) {
        this.emptyReviewWarning = true;
        return;
      }
      this.emptyReviewWarning = false;

      const reviewData = {
        productId: this.product.id,
        text: this.newReview.text,
        rating: this.newReview.rating,
      };

      try {
        const reviewResponse = await ReviewService.addReview(reviewData);
        const reviewId = reviewResponse.id;
        await this.uploadPhotos(reviewId);
        this.newReview.text = '';
        this.newReview.rating = 5;
        this.newReview.photos = [];
        this.$refs.fileInput.value = '';
        
        this.loadReviews();
      } catch (error) {
        console.error('Ошибка при добавлении отзыва:', error);
      }
    },

    async uploadPhotos(reviewId) {
      try {
        for (let i = 0; i < this.newReview.photos.length; i++) {
          const file = this.newReview.photos[i].file;
          const formData = new FormData();
          formData.append("file", file);
          await ReviewService.addPhoto(reviewId, formData); 
        }
        console.log("Фотографии успешно загружены");
      } catch (error) {
        console.error('Ошибка при добавлении фото:', error);
      }
    },

    async deleteReview(reviewId) {
      try {
        await ReviewService.deleteReview(reviewId);
        this.loadReviews();
      } catch (error) {
        console.error('Ошибка при удалении отзыва:', error);
      }
    },

    async deletePhoto(photoId) {
      try {
        await ReviewService.deletePhoto(photoId);
        this.loadReviews();
      } catch (error) {
        console.error('Ошибка при удалении фото:', error);
      }
    },

    handleFileChange(event) {
      const files = Array.from(event.target.files);
      const photos = files.map(file => ({
        file: file,
        url: URL.createObjectURL(file)
      }));
      this.newReview.photos = photos;
    },

    togglePhotoOverlay(photoUrl) {
      this.overlayPhoto = photoUrl;
    },

    getAvailabilityMessage(stock) {
      if (stock > 10) return "В наличии";
      if (stock > 0) return "Мало";
      return "Нет в наличии";
    },

    getAvailabilityClass(stock) {
      if (stock > 10) return "in-stock";
      if (stock > 0) return "low-stock";
      return "out-of-stock";
    },

    formatPrice(price) {
      return formatPrice(price);
    },
  }
};
</script>

<style scoped>
.product-page {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f8f9fa;
  padding: 20px;
}

.product-card {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 20px;
  max-width: 900px;
}

.product-image {
  max-height: 300px;
  object-fit: cover;
}

.product-name {
  font-size: 1.75rem;
  font-weight: bold;
}

.product-description {
  font-size: 1rem;
  color: #666;
}

.reviews .review-list li {
  border-left: 5px solid #56a841;
  padding: 15px;
}

.review-photo-img {
  max-width: 100px;
  max-height: 100px;
  object-fit: cover;
  border: 1px solid #ddd;
  padding: 2px;
  border-radius: 8px;
  cursor: pointer;
}

.review-photos {
  gap: 10px;
}

.optional-label {
  font-weight: bold;
  font-size: 1rem;
  margin-bottom: 0.5rem;
}

.star-rating {
  font-size: 1.5rem;
  color: #ddd;
  cursor: pointer;
}

.star-rating .star.selected {
  color: #ffc107;
}

.rating label {
  font-size: 1.5rem;
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.badge {
  font-size: 1.5rem;
  margin-left: -1.0rem;
}

.in-stock {
  color: green;
}

.low-stock {
  color: orange;
}

.out-of-stock {
  color: red;
}

.photo-preview {
  display: flex;
}

.preview-photo {
  margin-right: 10px;
}

.photo-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.8);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.enlarged-photo {
  max-width: 90%;
  max-height: 90%;
}

.review-photo {
  position: relative;
  display: inline-block;
}

.review-photo .btn-delete-photo {
  top: 5px;
  right: 5px;
  position: absolute;
  background-color: rgba(255, 0, 0, 0.8);
  color: white;
  border: none;
  width: 25px;
  height: 25px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.review-photo .btn-delete-photo:hover {
  background-color: red;
}

.w-100 {
  width: 100%;
}

.alert-warning {
  color: #856404;
  background-color: #fff3cd;
  border-color: #ffeeba;
}
</style>