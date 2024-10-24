<template>
    <div class="controls">
      <button @click="$emit('toggle-catalog')">Каталог</button>
      <select @change="$emit('sort-change', $event.target.value)">
        <option value="asc">Сначала дешевле</option>
        <option value="desc">Сначала дороже</option>
      </select>
    </div>
  </template>
  
  <script>
  export default {
  };
  </script>
  