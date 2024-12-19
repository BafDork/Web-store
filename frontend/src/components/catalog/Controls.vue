<template>
  <div class="controls d-flex justify-content-start mb-3">
    <!-- В наличии -->
    <div class="form-check me-3">
      <input 
        class="form-check-input" 
        type="checkbox" 
        id="in-stock" 
        v-model="inStock" 
        @change="onFilterChange"
      >
      <label class="form-check-label" for="in-stock">
        В наличии
      </label>
    </div>

    <!-- Фильтры -->
    <div class="form-check me-3">
      <input 
        class="form-check-input" 
        type="checkbox" 
        id="big-screen" 
        v-model="bigScreen" 
        @change="onFilterChange"
      >
      <label class="form-check-label" for="big-screen">
        Большая диагональ
      </label>
    </div>

    <div class="form-check me-3">
      <input 
        class="form-check-input" 
        type="checkbox" 
        id="charger-included" 
        v-model="chargerIncluded" 
        @change="onFilterChange"
      >
      <label class="form-check-label" for="charger-included">
        Зарядка в комплекте
      </label>
    </div>

    <!-- Сортировка -->
    <div class="dropdown me-3">
      <button class="btn btn-secondary dropdown-toggle" type="button" id="sort-dropdown" data-bs-toggle="dropdown" aria-expanded="false">
        Сортировка
      </button>
      <ul class="dropdown-menu" aria-labelledby="sort-dropdown">
        <li><a class="dropdown-item" href="#" @click="onSortChange('asc')">Сначала дешевле</a></li>
        <li><a class="dropdown-item" href="#" @click="onSortChange('desc')">Сначала дороже</a></li>
      </ul>
    </div>

    <!-- Мин цена -->
    <div class="price-filter me-3">
      <label for="min-price" class="form-label">Цена от:</label>
      <input type="number" id="min-price" v-model.number="minPrice" @input="onFilterChange" class="form-control w-auto">
    </div>

    <!-- Макс цена -->
    <div class="price-filter me-3">
      <label for="max-price" class="form-label">Цена до:</label>
      <input type="number" id="max-price" v-model.number="maxPrice" @input="onFilterChange" class="form-control w-auto">
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      selectedSortOrder: 'asc',
      inStock: true,
      bigScreen: false,
      chargerIncluded: false,
      minPrice: 0,
      maxPrice: 9999999,
    };
  },
  methods: {
    onSortChange(sortOrder) {
      this.selectedSortOrder = sortOrder;
      this.$emit('sort-change', {
        sortOrder: this.selectedSortOrder,
        filters: this.getFilters()
      });
    },
    onFilterChange() {
      this.$emit('sort-change', {
        sortOrder: this.selectedSortOrder,
        filters: this.getFilters()
      });
    },
    getFilters() {
      return {
        inStock: this.inStock,
        bigScreen: this.bigScreen,
        chargerIncluded: this.chargerIncluded,
        minPrice: this.minPrice,
        maxPrice: this.maxPrice,
      };
    }
  }
};
</script>

<style scoped>
.controls {
  display: flex;
  align-items: center;
  padding-top: 15px;
}

.select, .dropdown-toggle {
  font-size: 1em;
  padding: 0.5em;
  border-radius: 5px;
  border: 1px solid #ccc;
  background-color: #fff;
  cursor: pointer;
}

.form-check-input {
  margin-right: 8px;
}

.price-filter input {
  width: 120px;
  margin-right: 10px;
}

.dropdown-menu {
  min-width: 200px;
}

.btn-secondary {
  color: #fff;
  background-color: #6c757d;
  border-color: #6c757d;
}

.btn-secondary:hover {
  color: #fff;
  background-color: #5a6268;
  border-color: #545b62;
}

.visually-hidden {
  position: absolute;
  width: 1px;
  height: 1px;
  margin: -1px;
  padding: 0;
  border: 0;
  clip: rect(0, 0, 0, 0);
  clip-path: inset(50%);
  white-space: nowrap;
}
</style>
