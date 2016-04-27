/*
 * Copyright 2015 Year4000. All Rights Reserved.
 */

package net.year4000.utilities.router;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.year4000.utilities.value.TypeValue;

@FunctionalInterface
public interface Handle<T> {
    /** Handle the content type from the client */
    T handle(HttpRequest request, HttpResponse response, TypeValue... args) throws RouteHandleException;
}
