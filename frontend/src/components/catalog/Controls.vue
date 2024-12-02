<template>
  <div class="controls d-flex justify-content-end mb-3">
    <label for="sort-select" class="visually-hidden">Сортировка</label>
    <select 
      id="sort-select" 
      v-model="selectedSortOrder" 
      @change="onSortChange" 
      class="form-select w-auto"
    >
      <option value="asc">Сначала дешевле</option>
      <option value="desc">Сначала дороже</option>
    </select>
  </div>
</template>

  
<script>
export default {
  data() {
    return {
      selectedSortOrder: 'asc',
    };
  },
  methods: {
    onSortChange() {
      this.$emit('sort-change', this.selectedSortOrder);
    }
  },
};
</script>

<style scoped>
.controls {
  padding: 15px 30px 0 0;
}

select {
  font-size: 1em;
  padding: 0.5em;
  border-radius: 5px;
  border: 1px solid #ccc;
  background-color: #fff;
  cursor: pointer;
  transition: border-color 0.2s ease;
}

select:focus {
  border-color: #007bff;
  outline: none;
}

.form-select {
  padding-right: 30px;
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
