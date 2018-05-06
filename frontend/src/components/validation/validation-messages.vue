<template>
  <span v-show="behaviour.hasErrors">
    {{ behaviour.errorMessage }}
  </span>
</template>

<script>
import ValidationBehaviour from './validation-behaviour'

export default {
  name: 'validation-messages',
  props: {
    behaviour: ValidationBehaviour
  }
}
</script>

<style lang="sass" scoped>
.has-errors 
  > span
    color: rgb(220,53,69)
    font-weight: 700
    font-size: 0.55rem
    font-style: italic
    padding: 5px 2px
</style>
