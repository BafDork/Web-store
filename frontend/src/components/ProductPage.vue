<template>
    <div class="product-page d-flex justify-content-center align-items-center">
      <div class="product-card container">
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
          <div v-if="reviews && reviews.length > 0" class="review-list">
            <ul class="list-unstyled">
              <li v-for="review in reviews" :key="review.id" class="p-3 mb-3 bg-light rounded shadow-sm">
                <strong>{{ review.author }}</strong>
                <span class="text-warning"> ({{ review.rating }} из 5)</span>
                <p class="mb-0 mt-2">{{ review.text }}</p>
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
          <button @click="submitReview" class="btn btn-success">Отправить отзыв</button>
        </div>
        <div v-else class="not-authenticated text-center mt-4">
          <p class="text-muted">Чтобы оставить отзыв, необходимо авторизоваться.</p>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import { mapActions, mapGetters } from 'vuex';
  import { formatPrice } from '@/utils/formatPrice';
  import ProductService from '@/services/ProductService';
  import ReviewService from '@/services/ReviewService';
  
  export default {
    props: ['id'],
  
    data() {
      return {
        product: Object,
        reviews: [],
        newReview: {
          text: '',
          rating: 5,
        },
      };
    },
  
    computed: {
      ...mapGetters('cart', ['isProductInCart']),
      ...mapGetters('user', ['isAuthenticated']),
  
      isInCart() {
        return this.isProductInCart(this.id);
      }
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
          this.product = await ProductService.getProductById(this.id);
        } catch (error) {
          console.error('Не удалось загрузить продукт:', error);
        }
      },
  
      async loadReviews() {
        try {
          const reviews = await ReviewService.getReviews(this.id);
          this.reviews = reviews;
        } catch (error) {
          console.error('Ошибка при загрузке отзывов:', error);
        }
      },
  
      async submitReview() {
        const reviewData = {
          productId: this.product.id,
          text: this.newReview.text,
          rating: this.newReview.rating,
        };
  
        try {
          await ReviewService.addReview(reviewData);
          this.newReview.text = '';
          this.newReview.rating = 5;
          this.loadReviews();
        } catch (error) {
          console.error('Ошибка при добавлении отзыва:', error);
        }
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
  </style>
  
  