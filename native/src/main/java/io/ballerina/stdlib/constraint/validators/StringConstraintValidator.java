package io.ballerina.stdlib.constraint.validators;

import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;
import io.ballerina.stdlib.constraint.Constants;

import java.util.Map;
import java.util.Set;

/**
 * Extern functions for validating constraints related to string type of Ballerina.
 */
public class StringConstraintValidator {

    public static <T extends Comparable<T>> void validate(BMap<BString, Object> constraints, T fieldValue,
                                                          Set<String> failedConstraints) {
        for (Map.Entry<BString, Object> constraint : constraints.entrySet()) {
            long constraintValue = (long) constraint.getValue();
            switch (constraint.getKey().getValue()) {
                case Constants.CONSTRAINT_LENGTH:
                    if (!validateLength(fieldValue, constraintValue)) {
                        failedConstraints.add(Constants.CONSTRAINT_LENGTH);
                    }
                    break;
                case Constants.CONSTRAINT_MIN_LENGTH:
                    if (!validateMinLength(fieldValue, constraintValue)) {
                        failedConstraints.add(Constants.CONSTRAINT_MIN_LENGTH);
                    }
                    break;
                case Constants.CONSTRAINT_MAX_LENGTH:
                    if (!validateMaxLength(fieldValue, constraintValue)) {
                        failedConstraints.add(Constants.CONSTRAINT_MAX_LENGTH);
                    }
                    break;
            }
        }
    }

    private static <T extends Comparable<T>> boolean validateLength(T fieldValue, long constraintValue) {
        return ((String) fieldValue).length() == constraintValue;
    }

    private static <T extends Comparable<T>> boolean validateMinLength(T fieldValue, long constraintValue) {
        return ((String) fieldValue).length() >= constraintValue;
    }

    private static <T extends Comparable<T>> boolean validateMaxLength(T fieldValue, long constraintValue) {
        return ((String) fieldValue).length() <= constraintValue;
    }
}
