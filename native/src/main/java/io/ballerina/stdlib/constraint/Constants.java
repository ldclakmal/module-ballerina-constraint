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

/**
 * Constants related to constraint module.
 */
public class Constants {

    static final String PREFIX_RECORD_FILED = "$field$";
    static final String PREFIX_ANNOTATION_RECORD = "ballerina/constraint:1:";

    static final String ANNOTATION_TAG_INT = "Int";
    static final String ANNOTATION_TAG_FLOAT = "Float";
    static final String ANNOTATION_TAG_NUMBER = "Number";
    static final String ANNOTATION_TAG_STRING = "String";

    public static final String CONSTRAINT_MIN_VALUE = "minValue";
    public static final String CONSTRAINT_MAX_VALUE = "maxValue";
    public static final String CONSTRAINT_MIN_VALUE_EXCLUSIVE = "minValueExclusive";
    public static final String CONSTRAINT_MAX_VALUE_EXCLUSIVE = "maxValueExclusive";
    public static final String CONSTRAINT_LENGTH = "length";
    public static final String CONSTRAINT_MIN_LENGTH = "minLength";
    public static final String CONSTRAINT_MAX_LENGTH = "maxLength";

    static final String CONSTRAINT_ERROR = "Error";
}
