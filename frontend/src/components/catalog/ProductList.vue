<template>
	<div class="product-list">
	  	<ProductCard v-for="product in products" :key="product.id" :product="product" />
	</div>
</template>
  
<script>
  	import ProductCard from './ProductCard.vue';
  
  	export default {
		props: {
			products: Array
		},
		components: {
			ProductCard
		}
  	};
</script>
  
<style scoped>
	.product-list {
		display: block;
		width: 100%;
	}
</style>
  