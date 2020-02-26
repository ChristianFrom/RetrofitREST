package com.christianfrom.retrofitrest.REST;
import com.christianfrom.retrofitrest.Model.Comment;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;



public interface CommentsService {
    @GET("comments")
    Call<List<Comment>> getAllComments();
}
