<template>
  <section class="base-input-list">
    <section v-for="(_, index) in list" :key="index">
        <base-input v-model="list[index]" :placeholder="placeholder" ref="list"/>
        <base-button v-if="isLastInput(index)" icon="fa-plus" @click="addInput()"/>
        <base-button v-if="!isLastInput(index)" icon="fa-trash-alt" @click="deleteInput(index)"/>
    </section>
  </section>
</template>

<script>
import BaseInput from './BaseInput';
import BaseButton from './BaseButton';

export default {
  name: 'base-input-list',
  props: {
    list: Array,
    placeholder: String
  },
  components: {
    BaseInput, BaseButton
  },
  methods: {
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
      this.$nextTick(() => this.$refs.list[index].$el.focus());
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
