<template>
  <div class="container">
    <div v-if="breadcrumbPath.length" class="breadcrumb">
      <span @click="navigateToHome" class="breadcrumb-item">Главная </span>
      <span 
        v-for="(category, index) in breadcrumbPath" 
        :key="index" 
        @click="navigateToBreadcrumb(category.id)" 
        class="breadcrumb-item">
        {{ category.name }}
      </span>
    </div>

    <div class="category-and-controls">
      <div class="current-category">
        <strong>{{ currentCategoryName }}</strong> ({{ products.length }})
      </div>
      <div class="sorting-control">
        <Controls ref="controls" @sort-change="onSortChange" />
      </div>
    </div>

    <div class="content">
      <div class="category-menu">
        <CategoryList 
          :categories="categories" 
          @category-selected="onCategorySelected" 
          :active-category="currentCategoryId" 
        />
      </div>
      <div class="product-list">
        <ProductList :products="filteredProducts" />
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';
import Controls from './Controls.vue';
import CategoryList from './CategoryList.vue';
import ProductList from './ProductList.vue';
import ProductService from "@/services/ProductService";
import CategoryService from "@/services/CategoryService";

export default {
  data() {
    return {
      products: [],
      filteredProducts: [],
      categories: [],
      currentCategoryId: null,
      sortOrder: 'asc',
      currentCategoryName: 'Главная',
      breadcrumbPath: [],
      filters: {
        inStock: true,
        minPrice: 0,
        maxPrice: 9999999,
      },
    };
  },

  components: {
    Controls,
    CategoryList,
    ProductList,
  },

  computed: {
    ...mapGetters('user', ['isAuthenticated']),
  },

  methods: {
    ...mapActions('cart', ['loadCart', 'addToCart']),

    navigateToHome() {
      this.setCategory(null, 'Главная');
      this.fetchProducts();
    },

    fetchProducts() {
      const fetchFunc = this.currentCategoryId
        ? ProductService.getProductsByCategory(this.currentCategoryId)
        : ProductService.getAllProducts();

      fetchFunc
        .then(response => {
          this.products = response;
          this.applyFilters();
        })
        .catch(error => console.error("Ошибка при загрузке продуктов:", error));
    },

    fetchCategories() {
      CategoryService.getTopLevelCategories()
        .then(response => this.categories = response)
        .catch(error => console.error("Ошибка при загрузке категорий:", error));
    },

    fetchCart() {
      this.loadCart()
        .catch(error => console.error("Ошибка при загрузке корзины:", error));
    },

    applyFilters() {
      let filtered = [...this.products];

      if (this.filters.inStock) {
        filtered = filtered.filter(product => product.stock > 0);
      } else {
        filtered = filtered.filter(product => product.stock === 0);
      }

      if (this.filters.bigScreen) {
        filtered = filtered.filter(product => product.features && product.features.includes('Большая диагональ'));
      }

      if (this.filters.chargerIncluded) {
        filtered = filtered.filter(product => product.features && product.features.includes('Зарядка в комплекте'));
      }

      if (this.filters.minPrice !== null) {
        filtered = filtered.filter(product => product.price >= this.filters.minPrice);
      }

      if (this.filters.maxPrice !== null) {
        filtered = filtered.filter(product => product.price <= this.filters.maxPrice);
      }

      this.filteredProducts = filtered;
      this.sortProducts();
    },

    sortProducts() {
      this.filteredProducts.sort((a, b) => 
        this.sortOrder === 'asc' ? a.price - b.price : b.price - a.price
      );
    },

    onSortChange({ sortOrder, filters }) {
      this.sortOrder = sortOrder;
      this.filters = filters;
      this.applyFilters();
    },

    onCategorySelected(categoryId) {
      this.setCategory(categoryId, this.findCategoryName(categoryId));
      this.fetchProducts();
    },

    setCategory(categoryId, categoryName) {
      this.currentCategoryId = categoryId;
      this.currentCategoryName = categoryName;
      this.updateBreadcrumb(categoryId);
    },

    findCategoryName(categoryId) {
      const category = this.findCategoryById(this.categories, categoryId);
      return category ? category.name : 'Главная';
    },

    findCategoryById(categories, categoryId) {
      for (const category of categories) {
        if (category.id === categoryId) return category;
        if (category.subCategories && category.subCategories.length) {
          const foundCategory = this.findCategoryById(category.subCategories, categoryId);
          if (foundCategory) return foundCategory;
        }
      }
      return null;
    },

    updateBreadcrumb(categoryId) {
      this.breadcrumbPath = this.buildBreadcrumbPath(this.categories, categoryId);
    },

    buildBreadcrumbPath(categories, categoryId, path = []) {
      for (const category of categories) {
        if (category.id === categoryId) {
          path.push({ id: category.id, name: category.name });
          return path;
        }
        if (category.subCategories && category.subCategories.length) {
          const subPath = this.buildBreadcrumbPath(category.subCategories, categoryId, [...path, { id: category.id, name: category.name }]);
          if (subPath.length) return subPath;
        }
      }
      return [];
    },

    navigateToBreadcrumb(categoryId) {
      this.setCategory(categoryId, this.findCategoryName(categoryId));
      this.fetchProducts();
    }
  },

  mounted() {
    this.fetchCategories();
    this.fetchProducts();
    if (this.isAuthenticated) this.fetchCart();
  },
};
</script>

<style scoped>
.category-and-controls {
  display: flex;
  align-items: center;
}

.current-category {
  margin-left: 28px;
  font-size: 1.25em;
  font-weight: bold;
  color: #616161;
}

.sorting-control {
  margin-left: auto;
}

.breadcrumb {
  font-size: 1em;
  margin: 10px 0px 0px 28px;
  display: flex;
  gap: 8px;
}

.breadcrumb-item {
  cursor: pointer;
  color: #616161;
  transition: color 0.3s ease;
}

.breadcrumb-item:hover {
  text-decoration: underline;
  color: #007bff;
}

.content {
  display: flex;
  align-items: flex-start;
}

.category-menu {

  width: 250px;
  flex-shrink: 0;
}

.product-list {
  margin-top: 0;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
}
</style>
