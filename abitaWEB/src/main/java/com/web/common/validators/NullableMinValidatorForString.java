package com.web.common.validators;

import com.web.common.validators.NullableMin;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

/**
 * Implémentation qui permet la validation des chaînes de caractères vides et non définies
 * @see org.hibernate.validator.constraints.impl.MinValidatorForString
 */
public class NullableMinValidatorForString implements ConstraintValidator<com.web.common.validators.NullableMin, String> {

  private long minValue;

  @Override
  public void initialize(NullableMin minValue) {
    this.minValue = minValue.value();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    // null values and empty are valid
    if (value == null || value.isEmpty()) {
      return true;
    }
    try {
      return new BigDecimal(value).compareTo(BigDecimal.valueOf(minValue)) != -1;
    } catch (NumberFormatException nfe) {
      return false;
    }
  }
}
