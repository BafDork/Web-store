<template>
    <div class="controls d-flex justify-content-end mb-3">
      <select @change="$emit('sort-change', $event.target.value)" class="form-select w-auto">
        <option value="asc">Сначала дешевле</option>
        <option value="desc">Сначала дороже</option>
      </select>
    </div>
  </template>
  
  <script>
    export default {};
  </script>
  
  <style scoped>
    .controls {
      margin-right: 10px;
    }
  
    select {
      font-size: 1em;
    }
  </style>