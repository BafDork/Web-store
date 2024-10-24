<template>
    <div>
        <h1>Каталог товаров</h1>
        <Controls ref="controls" @sort-change="fetchProducts" />
        <CategoryList 
            :categories="categories" 
            @category-selected="onCategorySelected" 
            :active-category="currentCategoryId" 
        />
        <ProductList :products="products" />
    </div>
</template>
  
<script>
    import Controls from './Controls.vue';
    import CategoryList from './CategoryList.vue';
    import ProductList from './ProductList.vue';
    import ProductService from "@/services/ProductService";
  
    export default {
        data() {
            return {
                products: [],
                categories: [],
                currentCategoryId: null
            };
        },

        components: {
            Controls,
            CategoryList,
            ProductList
        },

        methods: {
            fetchProducts(sortOrder) {
                if (this.currentCategoryId) {
                    ProductService.getProductsByCategory(this.currentCategoryId, sortOrder)
                    .then(response => {
                        this.products = response.data;
                    })
                    .catch(error => {
                        console.error("Ошибка при загрузке продуктов:", error);
                    });
                } else {
                    ProductService.getAllProducts(sortOrder)
                    .then(response => {
                        this.products = response.data;
                    })
                    .catch(error => {
                        console.error("Ошибка при загрузке продуктов:", error);
                    });
                }
            },

            fetchCategories() {
            ProductService.getCategories()
                .then(response => {
                    this.categories = response.data;
                })
                .catch(error => {
                    console.error("Ошибка при загрузке категорий:", error);
                });
            },

            onCategorySelected(categoryId) {
                this.currentCategoryId = categoryId;
                this.fetchProducts(categoryId);
            }
        },

        mounted() {
            this.fetchCategories();
        }
    };
</script>
  