<template>
  <div class="catalog-menu">
    <ul>
      <CategoryNode 
        v-for="category in categories" 
        :key="category.id" 
        :category="category" 
        :activeCategory="activeCategory" 
        @category-selected="selectCategory"
      />
    </ul>
  </div>
</template>

<script>
import CategoryNode from './CategoryNode.vue';

export default {
  components: { CategoryNode },
  props: {
    categories: {
      type: Array,
      required: true,
    },
    activeCategory: {
      type: Number,
      default: null,
    },
  },
  methods: {
    selectCategory(categoryId) {
      this.$emit('category-selected', categoryId);
    },
  },
};
</script>

<style scoped>
.catalog-menu {
  margin-left: 28px;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 5px;
  max-width: 250px;
  padding: 10px;
}

ul {
  padding-left: 0;
}

.category-item {
  cursor: pointer;
  list-style: none;
  padding: 10px;
  transition: background-color 0.2s ease, padding-left 0.2s ease;
}

.category-item:hover {
  background-color: #f0f0f0;
  padding-left: 20px;
}

.active {
  font-weight: bold;
  color: #007bff;
}
</style>
