package com.christianfrom.retrofitrest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.christianfrom.retrofitrest.Model.Bil;
import com.christianfrom.retrofitrest.Model.Comment;
import com.christianfrom.retrofitrest.REST.ApiUtils;
import com.christianfrom.retrofitrest.REST.BilService;
import com.christianfrom.retrofitrest.REST.CommentsService;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MYCOMMENTS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void getAndShowData() {
        EditText nummerpladeView = findViewById(R.id.mainNummerpladeEditView);
        String nummerplade = nummerpladeView.getText().toString().trim();
        String apiToken = "7wf6h3n421492bzvsn1k6x5emc2bff3m";

        if (nummerplade.length() == 0) {
            nummerpladeView.setError("No input");
            return;
        }

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("X-AUTH-TOKEN", apiToken)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://v1.motorapi.dk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BilService service = retrofit.create(BilService.class);

        Call<Bil> bilCall = service.getBil(nummerplade, apiToken);
        bilCall.enqueue(new Callback<Bil>(){
            @Override
            public void onResponse(Call<Bil> call, Response<Bil> response){
                TextView messageView = findViewById(R.id.mainMessageTextView);
                TextView modelView = findViewById(R.id.mainCarModelTextView);
                TextView makeView = findViewById(R.id.mainCarMakeTextView);
                TextView variantView = findViewById(R.id.mainCarVariantTextView);
                TextView yearView = findViewById(R.id.mainCarYearTextView);
                if (response.isSuccessful()) {
                    String message = response.message();
                    Bil bil = response.body();
                    Log.d(LOG_TAG, message + " " + bil);
                    modelView.setText("Model: " + bil.getModel());
                    makeView.setText("Mærke: " + bil.getMake());
                    variantView.setText("Variant: " + bil.getVariant());
                    yearView.setText("Årgang: " + bil.getModelYear());
                } else {
                    modelView.setText("");
                    makeView.setText("");
                    variantView.setText("");
                    yearView.setText("");
                    if (response.code() == 404) {
                        messageView.setText("No such car: " + nummerplade);
                    } else {
                        messageView.setText(String.format("Not working %d %s", response.code(), response.message()));
                    }

                }
            }

            @Override
            public void onFailure(Call<Bil> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }


        });

    }

/*
    private void getAllComments() {
        CommentsService cs = ApiUtils.getCommentsService();
        Call<List<Comment>> getAllCommentsCall = cs.getAllComments();
        TextView messageView = findViewById(R.id.mainMessageTextView);

        messageView.setText("");
        getAllCommentsCall.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    List<Comment> allComments = response.body();
                    Log.d(LOG_TAG, allComments.toString());
                    populateRecyclerView(allComments);
                } else {
                    String message = "Problem " + response.code() + " " + response.message();
                    Log.d(LOG_TAG, message);
                    messageView.setText(message);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
                messageView.setText(t.getMessage());
            }
        });

    }

    private void populateRecyclerView(List<Comment> allComments)
    {
        RecyclerView recyclerView = findViewById(R.id.mainCommentsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewSimpleAdapter adapter = new RecyclerViewSimpleAdapter<>(allComments);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position, item) -> {
            Comment comment = (Comment) item;
            Log.d(LOG_TAG, item.toString());
            Intent intent = new Intent(MainActivity.this, SingleCommentActivity.class);
            intent.putExtra(SingleCommentActivity.COMMENT, comment);
            Log.d(LOG_TAG, "putExtra " + comment.toString());
            startActivity(intent);
        });
    }
*/

    public void findBilButton(View view) {
        getAndShowData();
    }
}
