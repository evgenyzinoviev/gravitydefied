package org.happysanta.gd.API;

public interface ResponseHandler {

    void onResponse(Response response);

    void onError(APIException error);
}
