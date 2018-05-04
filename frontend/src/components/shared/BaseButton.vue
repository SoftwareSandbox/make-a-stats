<template>
  <button :type="type"
          :class="{'primary': isPrimary}"
          @click="emitClick">
    <i class="fas" v-if="icon" :class="icon"></i>
    {{ name }}
  </button>
</template>

<script>
export default {
  name: 'base-button',
  props: {
    name: String,
    icon: String,
    isPrimary: Boolean
  },
  computed: {
    type() {
      return this.isPrimary ? 'submit' : 'button';
    }
  },
  methods: {
    emitClick() {
      this.$emit('click')
    }
  }
}
</script>

<style lang="sass" scoped>
button
  outline: none
  color: #303030
  font-size: 0.8rem
  font-weight: 700
  font-family: 'Open Sans', sans-serif
  text-align: center
  text-transform: uppercase
  padding: 10px
  border: 1px solid hsla(0, 0%, 0%, 0.2)
  box-shadow: 0 1px 2px 0 hsla(0, 0%, 0%, 0.2)
  background-color: rgba(245, 245, 245, 0.6)

  &:hover, &:focus
    cursor: pointer
    background-color: rgb(240, 240, 240)

  &.primary
    color: rgb(255, 255, 255)
    background-color: rgb(0, 123, 255)
    box-shadow: 0 1px 2px 0 rgb(0, 111, 230)
    border: 1px solid rgb(0, 111, 230)

    &:hover, &:focus
      background-color: rgb(0, 111, 230)
</style>
