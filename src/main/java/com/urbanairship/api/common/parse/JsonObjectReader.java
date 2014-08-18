/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.common.parse;

import java.io.IOException;

public interface JsonObjectReader<T> {

    T validateAndBuild() throws IOException;

}
