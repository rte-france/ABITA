/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.common.validators;

import com.web.common.validators.NullableDigits;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

/**
 * Implémentation qui permet la validation des chaînes de caractères vides et non définies
 * @see org.hibernate.validator.constraints.impl.DigitsValidatorForString
 */
public class NullableDigitsValidatorForString implements ConstraintValidator<com.web.common.validators.NullableDigits, String> {

  private int maxIntegerLength;

  private int maxFractionLength;

  @Override
  public void initialize(NullableDigits constraintAnnotation) {
    maxIntegerLength = constraintAnnotation.integer();
    maxFractionLength = constraintAnnotation.fraction();
    validateParameters();
  }

  @Override
  public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
    // null values and empty strings are valid
    if (str == null || str.isEmpty()) {
      return true;
    }

    BigDecimal bigNum = getBigDecimalValue(str);
    if (bigNum == null) {
      return false;
    }

    int integerPartLength = bigNum.precision() - bigNum.scale();
    int fractionPartLength = bigNum.scale() < 0 ? 0 : bigNum.scale();

    return maxIntegerLength >= integerPartLength && maxFractionLength >= fractionPartLength;
  }

  private BigDecimal getBigDecimalValue(String str) {
    BigDecimal bd;
    try {
      bd = new BigDecimal(str);
    } catch (NumberFormatException nfe) {
      return null;
    }
    return bd;
  }

  private void validateParameters() {
    if (maxIntegerLength < 0) {
      throw new IllegalArgumentException("The length of the integer part cannot be negative.");
    }
    if (maxFractionLength < 0) {
      throw new IllegalArgumentException("The length of the fraction part cannot be negative.");
    }
  }
}
