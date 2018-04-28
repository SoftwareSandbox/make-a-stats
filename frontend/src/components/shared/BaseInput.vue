<template>
    <input type="text"
           :value="value"
           :placeholder="placeholder"
           @input="updateValue"/>
</template>

<script>
    export default {
        name: 'base-input',
        props: {
            value: String,
            placeholder: String
        },
        methods: {
            updateValue($event) {
                this.$emit('input', $event.target.value);
            }
        }
    }
</script>

<style scoped>
    input {
        outline: none;
        color: #737373;
        font-size: 0.8rem;
        padding: 10px;
        border: 1px solid hsla(0, 0%, 0%, 0.2);
        box-shadow: 0 1px 2px 0 hsla(0, 0%, 0%, 0.2);
        background-color: rgb(255, 255, 255);
    }
</style>
