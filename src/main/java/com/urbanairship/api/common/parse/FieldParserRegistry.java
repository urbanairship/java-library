/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;

import com.google.common.base.Optional;

public interface FieldParserRegistry<T, R extends JsonObjectReader<T>> {

    Optional<FieldParser<R>> getFieldParser(String fieldName);

}
