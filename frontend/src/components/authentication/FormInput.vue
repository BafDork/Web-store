<template>
    <div class="mb-3">
      <label :for="id" class="form-label">{{ label }}</label>
      <input
        :value="modelValue"
        @input="$emit('update:modelValue', $event.target.value)"
        :type="type"
        :id="id"
        :class="['form-control', { 'is-invalid': hasError }]"
        :placeholder="placeholder"
        :aria-label="placeholder"
        :required="required"
      />
      <div v-if="hasError" class="invalid-feedback">{{ errorMessage }}</div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      modelValue: {
        type: String,
        required: true,
      },
      label: {
        type: String,
        required: true,
      },
      id: {
        type: String,
        required: true,
      },
      type: {
        type: String,
        default: 'text',
      },
      placeholder: {
        type: String,
        default: '',
      },
      required: {
        type: Boolean,
        default: false,
      },
      errorMessage: {
        type: String,
        default: 'Ошибка ввода',
      },
    },
    computed: {
      hasError() {
        return this.required && this.modelValue.trim() === '';
      },
    },
    emits: ['update:modelValue'],
  };
  </script>
  