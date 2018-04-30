<template>
  <input type="text"
    :value="value"
    :placeholder="placeholder"
    @input="emitInput"/>
</template>

<script>
  export default {
    name: 'base-input',
    props: {
      value: String,
      placeholder: String,
      isRequired: Boolean
    },
    methods: {
      emitInput($event) {
        this.$emit('input', $event.target.value);
      },
      isValid() {
        if (!this.required) return true;
        return this.value && this.value.length > 0;
      }
    }
  }
</script>

<style lang="sass" scoped>
input
  flex: 1
  outline: none
  color: #737373
  font-size: 0.8rem
  font-weight: 400
  font-family: 'Open Sans', sans-serif
  padding: 10px
  border: 1px solid hsla(0, 0%, 0%, 0.2)
  box-shadow: 0 1px 2px 0 hsla(0, 0%, 0%, 0.2)
  background-color: rgb(255, 255, 255)
</style>
