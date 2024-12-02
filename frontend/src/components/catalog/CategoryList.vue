<template>
    <div class="catalog-menu">
      <ul>
        <li 
          v-for="category in categories" 
          :key="category.id" 
          @click="selectCategory(category.id)"
          class="category-item"
        >
          <span :class="{ active: activeCategory === category.id }">{{ category.name }}</span>
          <ul v-if="category.subCategories.length > 0" class="sub-category-list">
            <li 
              v-for="subCategory in category.subCategories" 
              :key="subCategory.id" 
              @click.stop="selectCategory(subCategory.id)"
              class="sub-category-item"
            >
              <span :class="{ active: activeCategory === subCategory.id }">{{ subCategory.name }}</span>
            </li>
          </ul>
        </li>
      </ul>
    </div>
</template>
  
<script>
export default {
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

.sub-category-list {
  padding-left: 20px;
}

.sub-category-item {
  cursor: pointer;
  list-style: none;
  padding: 8px 0;
  transition: background-color 0.2s ease;
}

.sub-category-item:hover {
  background-color: #f9f9f9;
  padding-left: 20px;
}

.active {
  font-weight: bold;
  color: #007bff;
}
</style>

