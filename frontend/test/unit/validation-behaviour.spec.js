import Vue from 'vue'
import { shallow } from '@vue/test-utils'
import ValidationBehaviour from '@/components/validation/validation-behaviour'

const CUSTOM_VALIDATION_MESSAGE = 'CUSTOM_VALIDATION_MESSAGE';

describe('ValidationBehaviour', () => {

  let component, componentWrapper;
  let event = {
    preventDefault: () => {}
  };

  beforeEach(() => {
    spyOn(event, "preventDefault").and.callThrough();
  });

  beforeEach(() => {
    componentWrapper = shallow(createTestComponent(true), { mixins: [ValidationBehaviour] });
    component = componentWrapper.vm;
  });

  it('should be constructed without validation errors', () => {
    expectNoValidationErrors(component);
  });

  describe('.validate', () => {

    describe('when input is valid', () => {

      let component, componentWrapper;

      beforeEach(() => {
        componentWrapper = shallow(createTestComponent(true), { mixins: [ValidationBehaviour] });
        component = componentWrapper.vm;
      });
    
      describe('when validation is required', () => {
        it('should not stop event propagation', () => {
          componentWrapper.setProps({ isRequired: true });

          component.validate(event);
    
          expectNoValidationErrors(component);
          expect(event.preventDefault).not.toHaveBeenCalled();
        });
      });

      describe('when validation is not required', () => {
        it('should not stop event propagation', () => {
          componentWrapper.setProps({ isRequired: false });

          component.validate(event);
    
          expectNoValidationErrors(component);
          expect(event.preventDefault).not.toHaveBeenCalled();
        });
      });
    });

    describe('when input is invalid', () => {

      let component, componentWrapper;

      beforeEach(() => {
        componentWrapper = shallow(createTestComponent(false), { mixins: [ValidationBehaviour] });
        component = componentWrapper.vm;
      });

      describe('when validation is required', () => {

        beforeEach(() => {
          componentWrapper.setProps({ isRequired: true });
        });

        it('should stop event propagation', () => {
          component.validate(event);
    
          expectValidationErrors(component);
          expect(event.preventDefault).toHaveBeenCalled();
        });

        it('should handle missing event parameter', () => {
          component.validate();
    
          expectValidationErrors(component);
        });

        it('should use the message template', () => {
          componentWrapper.setProps({ validationMessageTemplate: CUSTOM_VALIDATION_MESSAGE });

          component.validate();

          expectValidationErrorsWithMessage(component, CUSTOM_VALIDATION_MESSAGE);
        });
      });

      describe('when validation is not required', () => {

        it('should not stop event propagation', () => {
          componentWrapper.setProps({ isRequired: false });

          component.validate(event);
    
          expectNoValidationErrors(component);
          expect(event.preventDefault).not.toHaveBeenCalled();
        });
      });
    });
  });
});

const expectValidationErrors = component => {
  expectValidationErrorsWithMessage(component, 'Field is required');
};

const expectValidationErrorsWithMessage = (component, message) => {
  expect(component.hasErrors).toBeTruthy();
  expect(component.validationClass).toBe('has-errors');
  expect(component.errorMessage).toBe(message);
};

const expectNoValidationErrors = component => {
  expect(component.hasErrors).toBeFalsy();
  expect(component.validationClass).toBe('');
  expect(component.errorMessage).toBeUndefined();
};

const createTestComponent = valid => {
  return {
    methods: {
      isValid() {
        return valid;
      }
    },
    render() {}
  };
}