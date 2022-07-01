package com.web.common.validators;

import com.web.common.validators.NullableMinValidatorForString;

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
 * Annotation pour traiter la validation Min avec des chaînes de caractères vides et non définies
 * @see javax.validation.constraints.Min
 */
//@formatter:off
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Constraint(validatedBy = { NullableMinValidatorForString.class })
//@formatter:on
@Retention(RUNTIME)
@Documented
public @interface NullableMin {

  /**
   * @return {@link javax.validation.constraints.Min#message()}
   */
  String message() default "{javax.validation.constraints.Min.message}";

  /**
   * @return {@link javax.validation.constraints.Min#groups()}
   */
  //@formatter:off
  Class<?>[] groups() default { };
  //@formatter:on

  /**
   * @return {@link javax.validation.constraints.Min#payload()}
   */
  //@formatter:off
  Class<? extends Payload>[] payload() default { };
  //@formatter:on

  /**
   * @return {@link javax.validation.constraints.Min#value()}
   */
  long value();

  /**
   * {@link javax.validation.constraints.Min$List}
   */
  //@formatter:off
  @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
  //@formatter:on
  @Retention(RUNTIME)
  @Documented
  @interface List {

    /** {@link javax.validation.constraints.Min.List#value()} */
    NullableMin[] value();
  }
}
