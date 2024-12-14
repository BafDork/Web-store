<template>
	<div class="product-list">
	  <ProductCard 
		v-for="product in products" 
		:key="product.id" 
		:product="product" 
	  />
	  <p v-if="products.length === 0" class="no-products">Продукты не найдены</p>
	</div>
</template>

<script>
  import ProductCard from './ProductCard.vue';

  export default {
    props: {
      products: {
        type: Array,
        default: () => []
      }
    },
    components: {
      ProductCard
    }
  };
</script>

<style scoped>
.product-list {
  justify-content: space-between;
  margin: 16px;
}

.no-products {
  text-align: center;
  font-size: 1.2em;
  color: #888;
  width: 100%;
}
</style>
