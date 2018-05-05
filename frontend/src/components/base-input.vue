<template>
  <section class="base-input" :class="hasErrorsClass">
    <input type="text" 
      v-bind="$attrs" 
      :value="value"
      @input="emitInputEvent"/>
    <span v-show="hasErrors">{{ errorMessage }}</span>
  </section>
</template>

<script>
import ValidationBehaviour from './validation-behaviour'

export default {
  name: 'base-input',
  inheritAttrs: false,
  mixins: [
    ValidationBehaviour
  ],
  props: {
    value: String
  },
  computed: {
    isValid() {
      return this.value.length > 0
    }
  },
  methods: {
    emitInputEvent($event) {
      this.$emit('input', $event.target.value);
    },
  },
  watch: {
    value() {
      this.validate();
    }
  }
}
</script>

<style lang="sass" scoped>
.base-input
  flex: 1
  display: flex
  flex-direction: column

  &.has-errors 
    > input
      border: 2px solid rgb(220,53,69)

    > span
      color: rgb(220,53,69)
      font-weight: 700
      font-size: 0.7rem
      padding: 5px 2px

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
