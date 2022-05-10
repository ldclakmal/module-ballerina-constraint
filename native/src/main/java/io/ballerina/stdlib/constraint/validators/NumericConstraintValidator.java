package io.ballerina.stdlib.constraint.validators;

import io.ballerina.runtime.api.values.BDecimal;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;
import io.ballerina.stdlib.constraint.Constants;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * Extern functions for validating constraints related to numeric types of Ballerina.
 */
public class NumericConstraintValidator {

    public static <T extends Comparable<T>> void validate(BMap<BString, Object> constraints, T fieldValue,
                                                          Set<String> failedConstraints) {
        for (Map.Entry<BString, Object> constraint : constraints.entrySet()) {
            T constraintValue = getConstraintValue(constraint);
            fieldValue = wrapFieldValue(fieldValue, constraintValue);
            switch (constraint.getKey().getValue()) {
                case Constants.CONSTRAINT_MIN_VALUE:
                    if (!validateMinValue(fieldValue, constraintValue)) {
                        failedConstraints.add(Constants.CONSTRAINT_MIN_VALUE);
                    }
                    break;
                case Constants.CONSTRAINT_MAX_VALUE:
                    if (!validateMaxValue(fieldValue, constraintValue)) {
                        failedConstraints.add(Constants.CONSTRAINT_MAX_VALUE);
                    }
                    break;
                case Constants.CONSTRAINT_MIN_VALUE_EXCLUSIVE:
                    if (!validateMinValueExclusive(fieldValue, constraintValue)) {
                        failedConstraints.add(Constants.CONSTRAINT_MIN_VALUE_EXCLUSIVE);
                    }
                    break;
                case Constants.CONSTRAINT_MAX_VALUE_EXCLUSIVE:
                    if (!validateMaxValueExclusive(fieldValue, constraintValue)) {
                        failedConstraints.add(Constants.CONSTRAINT_MAX_VALUE_EXCLUSIVE);
                    }
                    break;
            }
        }
    }

    private static <T extends Comparable<T>> T getConstraintValue(Map.Entry<BString, Object> constraint) {
        Object constraintValue = constraint.getValue();
        if (constraintValue instanceof BDecimal) {
            return (T) ((BDecimal) constraintValue).value();
        }
        return (T) constraintValue;
    }

    private static <T extends Comparable<T>> T wrapFieldValue(T fieldValue, T constraintValue) {
        if (constraintValue instanceof BigDecimal) {
            if (fieldValue instanceof Long) {
                return (T) new BigDecimal((Long) fieldValue);
            } else if (fieldValue instanceof Double) {
                return (T) new BigDecimal((Double) fieldValue);
            }
        }
        return fieldValue;
    }

    private static <T extends Comparable<T>> boolean validateMinValue(T fieldValue, T constraintValue) {
        return fieldValue.compareTo(constraintValue) >= 0;
    }

    private static <T extends Comparable<T>> boolean validateMaxValue(T fieldValue, T constraintValue) {
        return fieldValue.compareTo(constraintValue) <= 0;
    }

    private static <T extends Comparable<T>> boolean validateMinValueExclusive(T fieldValue, T constraintValue) {
        return fieldValue.compareTo(constraintValue) > 0;
    }

    private static <T extends Comparable<T>> boolean validateMaxValueExclusive(T fieldValue, T constraintValue) {
        return fieldValue.compareTo(constraintValue) < 0;
    }
}
