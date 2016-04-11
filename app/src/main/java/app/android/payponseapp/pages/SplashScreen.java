package app.android.payponseapp.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import app.android.payponseapp.MainActivity;
import app.android.payponseapp.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashScreen extends Activity {
    @Bind(R.id.backgorundView)
    ImageView backgroundView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,LoginPage.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Picasso.with(this).load(R.drawable.background_login).fit().into(backgroundView);
    }
}
