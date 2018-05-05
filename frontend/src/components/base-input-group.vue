<template>
  <section class="base-input-group">
    <section>
      <base-input
        v-bind="$attrs"
        :value="value"
        :class="{'has-errors': errorMessage}"
        @input="emitInput"/>
      <base-button 
        :class="{'has-errors': errorMessage}"
        :icon="btnIcon" 
        @click="emitClick"/>
    </section>
    <span v-show="hasErrors">{{ errorMessage }}</span>
  </section>
</template>

<script>
import ValidationBehaviour from './validation-behaviour'

export default {
  name: 'base-input-group',
  mixins: [
    ValidationBehaviour
  ],
  props: {
    value: String,
    btnIcon: String
  },
  data() {
    return {
      errorMessage: ''
    }
  },
  methods: {
    emitInput(value) {
      this.$emit('input', value);
    },
    emitClick() {
      this.$emit('btn-click');
    },
    isValid() {
      return this.value.length > 0
    }
  },
  watch: {
    value() {
      this.validate();
    }
  }
}
</script>

<style lang="sass" scoped>
.base-input-group
  flex: 1
  display: flex
  flex-direction: column

  > section
    flex: 1
    display: flex
    flex-direction: row

    button
      border-left: 0

      &.has-errors
        color: rgb(255, 255, 255)
        border: 2px solid rgb(220,53,69)
        border-left: 0
        background-color: rgb(220,53,69)

  > span
    color: rgb(220,53,69)
    font-weight: 700
    font-size: 0.7rem
    padding: 5px 2px
</style>
