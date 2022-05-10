/*
 * Copyright (c) 2022, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.ballerina.stdlib.constraint;

import io.ballerina.runtime.api.types.AnnotatableType;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.utils.TypeUtils;
import io.ballerina.runtime.api.values.BDecimal;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;
import io.ballerina.stdlib.constraint.validators.NumericConstraintValidator;
import io.ballerina.stdlib.constraint.validators.StringConstraintValidator;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Extern functions for validating constraints.
 */
@SuppressWarnings("unchecked")
public class Constraints {

    public static Object validate(Object obj) {
        Set<String> failedConstraints = new HashSet<>();
        if (obj instanceof BMap) {
            validateRecord(obj, failedConstraints);
        }

        if (!failedConstraints.isEmpty()) {
            return ErrorUtils.buildError(failedConstraints);
        }
        return null;
    }

    private static void validateRecord(Object obj, Set<String> failedConstraints) {
        BMap<BString, Object> record = (BMap<BString, Object>) obj;
        validateRecordAnnotations(record, failedConstraints);
        for (BString key : record.getKeys()) {
            if (record.get(key) instanceof BMap) {
                validateRecord(record.get(key), failedConstraints);
            }
        }
    }

    private static <T extends Comparable<T>> void validateRecordAnnotations(BMap<BString, Object> record,
                                                                            Set<String> failedConstraints) {
        BMap<BString, Object> recordAnnotations = ((AnnotatableType) TypeUtils.getType(record)).getAnnotations();
        for (Map.Entry<BString, Object> entry : recordAnnotations.entrySet()) {
            if (entry.getKey().getValue().startsWith(Constants.PREFIX_RECORD_FILED)) {
                String fieldName = entry.getKey().getValue().substring(Constants.PREFIX_RECORD_FILED.length() + 1);
                T fieldValue = getFieldValue(record, fieldName);
                BMap<BString, Object> constraintAnnotations = (BMap<BString, Object>) entry.getValue();
                for (Map.Entry<BString, Object> annotationRecord : constraintAnnotations.entrySet()) {
                    String annotationTag = annotationRecord.getKey().getValue().
                            substring(Constants.PREFIX_ANNOTATION_RECORD.length());
                    BMap<BString, Object> constraints = (BMap<BString, Object>) annotationRecord.getValue();
                    switch (annotationTag) {
                        case Constants.ANNOTATION_TAG_INT:
                        case Constants.ANNOTATION_TAG_FLOAT:
                        case Constants.ANNOTATION_TAG_NUMBER:
                            NumericConstraintValidator.validate(constraints, fieldValue, failedConstraints);
                            break;
                        case Constants.ANNOTATION_TAG_STRING:
                            StringConstraintValidator.validate(constraints, fieldValue, failedConstraints);
                            break;
                    }
                }
            }
        }
    }

    private static <T extends Comparable<T>> T getFieldValue(BMap<BString, Object> record, String fieldName) {
        Object obj = record.get(StringUtils.fromString(fieldName));
        if (obj instanceof BDecimal) {
            return (T) ((BDecimal) record.get(StringUtils.fromString(fieldName))).value();
        } else if (obj instanceof BString) {
            return (T) ((BString) record.get(StringUtils.fromString(fieldName))).getValue();
        } else {
            return (T) obj;
        }
    }
}
