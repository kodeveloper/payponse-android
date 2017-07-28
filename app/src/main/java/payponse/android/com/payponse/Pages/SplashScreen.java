package payponse.android.com.payponse.Pages;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import payponse.android.com.payponse.Checks.ConnectionControl;
import payponse.android.com.payponse.MainActivity;
import payponse.android.com.payponse.R;

public class SplashScreen extends Activity {
    SharedPreferences preferences;
    @Bind(R.id.splashMain)
    ImageView background;

    @Override
    protected void onStart() {
        super.onStart();
        Picasso.with(this).load(R.drawable.splash).fit().into(background);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =getIntent();
                boolean result =intent.getBooleanExtra("delete",false);
                if (result){
                    preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("isLogin");
                    editor.remove("user_id");
                    editor.apply();
                    Intent loginIntent = new Intent(SplashScreen.this,LoginPages.class);
                    startActivity(loginIntent);
                    finish();


                }
                else
               checkUser();
            }
        },2500);
    }

    void checkUser(){

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (preferences!=null) {
            String login = preferences.getString("isLogin", "0");
            if (login.equals("1")) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("user_id", preferences.getString("user_id", "0"));
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(this,LoginPages.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
