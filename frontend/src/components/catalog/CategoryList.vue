<template>
    <div class="catalog-menu">
        <ul>
            <li v-for="category in categories" :key="category.id" @click="selectCategory(category.id)" class="category-item">
                <span :class="{ active: activeCategory === category.id }">{{ category.name }}</span>
                <ul>
                    <li v-for="subCategory in category.subCategories" :key="subCategory.id" @click.stop="selectCategory(subCategory.id)">
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
            categories: Array,
            activeCategory: Number
        },
        methods: {
            selectCategory(categoryId) {
                this.$emit('category-selected', categoryId);
            }
        }
    };
</script>

<style scoped>
    .catalog-menu {
        margin: 10px 0;
    }

    .category-item {
        cursor: pointer;
        list-style: none;
    }

    .category-item:hover {
        background-color: #f0f0f0;
    }

    .active {
        font-weight: bold;
    }
</style>
