package com.christianfrom.retrofitrest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.christianfrom.retrofitrest.Model.Comment;
import com.christianfrom.retrofitrest.REST.ApiUtils;
import com.christianfrom.retrofitrest.REST.CommentsService;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleCommentActivity extends AppCompatActivity {
    public static final String COMMENT = "comment";
    private static final String LOG_TAG = "COMMENTS";
    private Comment comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_comment);

        Intent intent = getIntent();
        comment = (Comment) intent.getSerializableExtra(COMMENT);

        Log.d(LOG_TAG, comment.toString());


        TextView name = findViewById(R.id.singleCommentNameTextview);
        name.setText(comment.getName());

        TextView postId = findViewById(R.id.singleCommentPostIdText);
        postId.setText("Post ID: " + comment.getId());

        TextView id = findViewById(R.id.singleCommentIdText);
        id.setText("ID: " + comment.getId());

        TextView email = findViewById(R.id.singleCommentEmailText);
        email.setText("E-mail: " + comment.getEmail());

        TextView body = findViewById(R.id.singleCommentBodyText);
        body.setText("Body: " + comment.getBody());
    }




    public void backButtonClicked(View view) {
        finish();
    }


}
