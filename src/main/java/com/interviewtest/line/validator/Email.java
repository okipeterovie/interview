
package com.interviewtest.line.validator;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

/**
 * @author Ademola Aina
 */

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(value = RetentionPolicy.RUNTIME)
@Repeatable(value = Email.List.class)
@ReportAsSingleViolation
@Pattern(regexp = "")
public @interface Email {

    @Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(value = RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        public Email[] value();
    }

    public String message() default "{Email.message}";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    @OverridesAttribute(constraint = Pattern.class, name = "regexp")
    public String regexp() default ".*";
}
