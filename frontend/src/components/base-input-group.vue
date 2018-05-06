<template>
  <section class="base-input-group" :class="validationClass">
    <section>
      <base-input
        v-bind="$attrs"
        :value="value"
        :class="validationClass"
        @input="emitInput"/>
      <base-button 
        :icon="btnIcon" 
        :class="validationClass"
        @click="emitClick"/>
    </section>
    <validation-messages :behaviour="this"/>
  </section>
</template>

<script>
import ValidationMessages from './validation/validation-messages'
import ValidationBehaviour from './validation/validation-behaviour'

export default {
  name: 'base-input-group',
  mixins: [
    ValidationBehaviour
  ],
  components: {
    ValidationMessages
  },
  props: {
    value: String,
    btnIcon: String
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
</style>
