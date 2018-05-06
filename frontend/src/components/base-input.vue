<template>
  <section class="base-input" :class="validationClass">
    <input type="text" 
      v-bind="$attrs" 
      :value="value"
      @input="emitInput"/>
    <validation-messages :behaviour="this" />
  </section>
</template>

<script>
import ValidationMessages from './validation/validation-messages'
import ValidationBehaviour from './validation/validation-behaviour'

export default {
  name: 'base-input',
  inheritAttrs: false,
  mixins: [
    ValidationBehaviour
  ],
  components: {
    ValidationMessages
  },
  props: {
    value: String
  },
  methods: {
    emitInput($event) {
      this.$emit('input', $event.target.value);
    },
    isValid() {
      return this.value.length > 0;
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
