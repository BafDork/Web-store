<template>
    <div>
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
          <strong>{{ currentCategoryName }}</strong> ({{ products.length }} товаров)
        </div>
        <div class="sorting-control">
          <Controls ref="controls" @sort-change="onSortChange" />
        </div>
      </div>
  
      <CategoryList 
        v-if="showCatalog"
        :categories="categories" 
        @category-selected="onCategorySelected" 
        :active-category="currentCategoryId" 
      />
      <ProductList :products="products" />
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
      categories: [],
      currentCategoryId: null,
      sortOrder: 'asc',
      currentCategoryName: 'Главная',
      breadcrumbPath: [],
    };
  },

  components: {
    Controls,
    CategoryList,
    ProductList,
  },

  computed: {
    ...mapGetters('catalog', ['showCatalog']),
    ...mapGetters('user', ['isAuthenticated']),
  },

  methods: {
    ...mapActions('cart', ['loadCart', 'addToCart']),
    ...mapActions('catalog', ['toggleCatalog']),

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
          this.sortProducts();
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

    sortProducts() {
      this.products.sort((a, b) => 
        this.sortOrder === 'asc' ? a.price - b.price : b.price - a.price
      );
    },

    onSortChange(newSortOrder) {
      this.sortOrder = newSortOrder;
      this.sortProducts();
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

  watch: {
    $route() {
      this.toggleCatalog();
    }
  }
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
</style>

  