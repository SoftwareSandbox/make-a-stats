<script>
  export default {
    props: {
      isRequired: Boolean,
      defaultErrorMessage: {
        default: "Field is required",
        type: String
      }
    },
    data() {
      return {
        errorMessage: undefined
      }
    },
    computed: {
      hasErrors() {
        return this.errorMessage;
      },
      hasErrorsClass() {
        return this.hasErrors ? 'has-errors' : '';
      },
    },
    methods: {
      validate(event) {
        if (!this.isRequired || this.isValid) {
          this.errorMessage = undefined;
        } else {
          this.errorMessage = this.defaultErrorMessage;
          event && event.preventDefault();
        }
      }
    }
  }
</script>