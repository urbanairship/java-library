package com.urbanairship.api.client;

import java.io.IOException;

public interface ResponseParser<T>  {
    T parse(String response) throws IOException;
}
