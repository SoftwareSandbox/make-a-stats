<script>
export default {
  props: {
    isRequired: Boolean,
    validationMessageTemplate: {
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
    validationClass() {
      return this.hasErrors ? 'has-errors' : '';
    },
  },
  methods: {
    validate(event) {
      if (!this.isRequired || this.isValid()) {
        this.errorMessage = undefined;
      } else {
        this.errorMessage = this.validationMessageTemplate;
        event && event.preventDefault();
      }
    }
  }
}
</script>