package com.web.common.validators;

import com.web.common.validators.NullableDigitsValidatorForString;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation pour traiter la validation Digits avec des chaînes de caractères vides et non définies
 * @see javax.validation.constraints.Digits
 */
// @formatter:off
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Constraint(validatedBy = { NullableDigitsValidatorForString.class })
//@formatter:on
@Retention(RUNTIME)
@Documented
public @interface NullableDigits {

  /**
   * @return {@link javax.validation.constraints.Digits#message()}
   */
  String message() default "{javax.validation.constraints.Digits.message}";

  /**
   * @return {@link javax.validation.constraints.Digits#groups()}
   */
  //@formatter:off
  Class<?>[] groups() default { };
  //@formatter:on

  /**
   * @return {@link javax.validation.constraints.Digits#payload()}
   */
  //@formatter:off
  Class<? extends Payload>[] payload() default { };
  //@formatter:on

  /**
   * @return {@link javax.validation.constraints.Digits#integer()}
   */
  int integer();

  /**
   * @return {@link javax.validation.constraints.Digits#fraction()}
   */
  int fraction();

  /**
   * {@link javax.validation.constraints.Digits$List}
   */
  //@formatter:off
  @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
  //@formatter:on
  @Retention(RUNTIME)
  @Documented
  @interface List {

    /** {@link javax.validation.constraints.Digits.List#value()} */
    NullableDigits[] value();
  }
}
