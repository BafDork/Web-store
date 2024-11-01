<template>
    <div class="controls">
        <select @change="$emit('sort-change', $event.target.value)">
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
        margin: 20px 0;
    }

    select {
        padding: 5px;
        font-size: 1em;
    }
</style>
