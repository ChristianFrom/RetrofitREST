package com.christianfrom.retrofitrest.REST;

public class ApiUtils {

    private ApiUtils() {

    }

    //private static final String BASE_URL = "http://jsonplaceholder.typicode.com/";

    private static final String BASE_URL = "http://api.nrpla.de/";
    public static CommentsService getCommentsService(){

        return RetrofitClient.getClient(BASE_URL).create(CommentsService.class);
    }
}
