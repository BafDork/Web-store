<template>
    <div>
        <div v-if="breadcrumbPath.length" class="breadcrumb">
            <span @click="navigateToHome" class="breadcrumb-item">Главная</span>
            <span v-if="breadcrumbPath.length"> / </span>
            <span v-for="(category, index) in breadcrumbPath" 
                    :key="index"
                    @click="navigateToBreadcrumb(category.id)"
                    class="breadcrumb-item">
                {{ category.name }}
                <span v-if="index < breadcrumbPath.length - 1"> / </span>
            </span>
        </div>
  
        <div class="current-category">
            <strong>{{ currentCategoryName }}</strong> ({{ products.length }} товаров)
        </div>
  
        <Controls ref="controls" @sort-change="onSortChange" />
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
                currentCategoryName: '',
                breadcrumbPath: []
            };
        },
    
        components: {
            Controls,
            CategoryList,
            ProductList
        },

        computed: {
            ...mapGetters('catalog', ['showCatalog']),
        },
    
        methods: {
        
            ...mapActions('cart', ['loadCart', 'addToCart']),
            ...mapActions('catalog', ['toggleCatalog']),
        
            navigateToHome() {
                this.currentCategoryId = null;
                this.currentCategoryName = 'Главная';
                this.breadcrumbPath = [];
                this.fetchProducts();
            },
    
            fetchProducts() {
                const fetchFunc = this.currentCategoryId
                ? ProductService.getProductsByCategory(this.currentCategoryId)
                : ProductService.getAllProducts();
        
                fetchFunc
                .then(response => {
                    this.products = response.data;
                    this.sortProducts();
                })
                .catch(error => {
                    console.error("Ошибка при загрузке продуктов:", error);
                });
            },
    
            fetchCategories() {
                CategoryService.getTopLevelCategories()
                .then(response => {
                    this.categories = response.data;
                })
                .catch(error => {
                    console.error("Ошибка при загрузке категорий:", error);
                });
            },

            fetchCart() {
                this.loadCart()
                .then(response => {
                })
                .catch(error => {
                    console.error("Ошибка при загрузке корзины:", error);
                });
            },
    
            sortProducts() {
                this.products.sort((a, b) => {
                if (this.sortOrder === 'asc') {
                    return a.price - b.price;
                } else {
                    return b.price - a.price;
                }
                });
            },
    
            onSortChange(newSortOrder) {
                this.sortOrder = newSortOrder;
                this.sortProducts();
            },
    
            onCategorySelected(categoryId) {
                this.currentCategoryId = categoryId;
                this.updateBreadcrumb(categoryId);
                this.fetchProducts();
            },
    
            findCategoryById(categories, categoryId) {
                for (const category of categories) {
                if (category.id === categoryId) {
                    return category;
                }
                if (category.subCategories && category.subCategories.length > 0) {
                    const foundCategory = this.findCategoryById(category.subCategories, categoryId);
                    if (foundCategory) {
                    return foundCategory;
                    }
                }
                }
                return null;
            },
        
            updateBreadcrumb(categoryId) {
                const category = this.findCategoryById(this.categories, categoryId);
                if (category) {
                    this.currentCategoryName = category.name;
                    this.breadcrumbPath = this.buildBreadcrumbPath(this.categories, categoryId);
                }
            },

            navigateToBreadcrumb(categoryId) {
                this.currentCategoryId = categoryId;
                this.updateBreadcrumb(categoryId);
                this.fetchProducts();
            },

            buildBreadcrumbPath(categories, categoryId, path = []) {
                for (const category of categories) {
                    if (category.id === categoryId) {
                    path.push({ id: category.id, name: category.name });
                    return path;
                    }
                    if (category.subCategories && category.subCategories.length > 0) {
                    const subPath = this.buildBreadcrumbPath(category.subCategories, categoryId, [...path, { id: category.id, name: category.name }]);
                    if (subPath.length) {
                        return subPath;
                    }
                    }
                }
                return [];
            }
        },
    
        mounted() {
            this.fetchCategories();
            this.fetchProducts();
            this.fetchCart();
        },

        watch: {
            $route(to, from) {
            toggleCatalog();
        }
  }
    };
</script>
  
<style scoped>
    .catalog {
        padding: 20px;
    }
    
    .catalog-title.clickable {
        cursor: pointer;
        color: #007bff;
        text-decoration: underline;
        margin-bottom: 10px;
    }

    .breadcrumb-item {
        cursor: pointer;
        color: #007bff;
        text-decoration: underline;
    }
    
    .breadcrumb {
        font-size: 1.1em;
        margin-top: 0.5em;
    }
    
    .current-category {
        font-size: 1.25em;
        font-weight: bold;
        margin: 1em 0;
    }
</style>
  