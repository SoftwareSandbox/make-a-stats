<template>
    <button @click="onClick"
            :class="{'primary': primary}">
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
            primary: Boolean,
            onClick: Function
        }
    }
</script>

<style scoped>
    .primary {
        color: #ffffff;
        background-color: rgb(0, 123, 255);
        box-shadow: 0 1px 2px 0 rgb(0, 111, 230);
        border: 1px solid rgb(0, 111, 230);
    }

    .primary:hover, .primary:focus {
        background-color: rgb(0, 111, 230);
    }

    button {
        outline: none;
        color: #303030;
        font-size: 0.8rem;
        font-weight: 700;
        font-family: 'Open Sans', sans-serif;
        text-align: center;
        text-transform: uppercase;
        padding: 10px;
        border: 1px solid hsla(0, 0%, 0%, 0.2);
        box-shadow: 0 1px 2px 0 hsla(0, 0%, 0%, 0.2);
        background-color: rgba(245, 245, 245, 0.6);
    }

    button:hover, button:focus {
        cursor: pointer;
        background-color: rgb(240, 240, 240);
    }
</style>
