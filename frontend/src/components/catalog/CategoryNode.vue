<template>
    <li class="category-item">
      <span 
        :class="{ active: activeCategory === category.id }" 
        @click="selectCategory(category.id)"
      >
        {{ category.name }}
      </span>
      <ul v-if="category.subCategories && category.subCategories.length > 0">
        <CategoryNode 
          v-for="subCategory in category.subCategories" 
          :key="subCategory.id" 
          :category="subCategory" 
          :activeCategory="activeCategory" 
          @category-selected="$emit('category-selected', $event)"
        />
      </ul>
    </li>
  </template>
  
  <script>
  export default {
    props: {
      category: {
        type: Object,
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
  .category-item {
    cursor: pointer;
    list-style: none;
    padding: 8px 0;
    transition: background-color 0.2s ease, padding-left 0.2s ease;
  }
  
  .category-item:hover {
    background-color: #f9f9f9;
    padding-left: 20px;
  }
  
  .active {
    font-weight: bold;
    color: #007bff;
  }
  </style>
  