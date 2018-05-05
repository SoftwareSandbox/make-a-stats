<template>
  <section class="base-input-list">
    <section v-for="(_, index) in list" :key="index">
      <base-input-group 
        ref="list"
        v-bind="$attrs"
        v-model="list[index]"
        :is-required="isRequired"
        :btn-icon="iconAtIndex(index)"
        @btn-click="onClickAtIndex(index)"/>
    </section>
  </section>
</template>

<script>
export default {
  name: 'base-input-list',
  props: {
    list: Array,
    isRequired: Boolean
  },
  methods: {
    iconAtIndex(index) {
      return this.isLastInput(index) ? 'fa-plus' : 'fa-trash-alt';
    },
    onClickAtIndex(index) {
      return this.isLastInput(index) ? this.addInput() : this.deleteInput(index);
    },
    addInput() {
      this.list.push('');
      this.focusInput(this.list.length - 1);
    },
    deleteInput(index) {
      this.list.splice(index, 1);
      this.focusInput(index);
    },
    isLastInput(index) {
      return index === this.list.length - 1;
    },
    focusInput(index) {
      this.$nextTick(() => this.$refs.list[index].$el.querySelector('input').focus());
    },
    validate(event) {
      this.$refs.list.forEach(item => item.validate(event));
    }
  }
}
</script>

<style lang="sass" scoped>
.base-input-list
  display: flex
  flex-direction: column

  > section
    flex: 1
    display: flex
    flex-direction: row
    margin-bottom: 10px

    button
      border-left: 0
</style>
