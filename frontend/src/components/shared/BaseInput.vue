<template>
    <input type="text"
           :class="{ 'has-errors': hasErrors }"
           :value="value"
           :placeholder="placeholder"
           @input="updateValue"/>
</template>

<script>
    export default {
        name: 'base-input',
        props: {
            value: String,
            placeholder: String,
            required: Boolean
        },
        data: function () {
            return {
                hasErrors: false
            }
        },
        methods: {
            updateValue($event) {
                this.$emit('input', $event.target.value);
            },
            validate() {
                this.hasErrors = !this.isValid();
                return !this.hasErrors;
            },
            isValid() {
                if (!this.required) return true;
                return this.value && this.value.length > 0;
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

        &.has-errors
            border: 1px solid rgb(220, 53, 69)
            box-shadow: 0 1px 2px 0 rgb(217, 38, 56)
</style>
