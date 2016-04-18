package payponse.android.com.payponse.Pages;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import payponse.android.com.payponse.Checks.InputControl;
import payponse.android.com.payponse.MainActivity;
import payponse.android.com.payponse.R;

public class LoginPages extends Activity {
    SharedPreferences preferences;
    private AsyncHttpClient client = new AsyncHttpClient();
    @Bind(R.id.phoneText) EditText phoneNumber;
    @Bind(R.id.passwordText)EditText password;
    @Bind(R.id.loginMain)
    ImageView loginMain;
    @Override
    protected void onStart() {
        super.onStart();
        Picasso.with(this).load(R.drawable.backgrounds).fit().into(loginMain);
        preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (preferences!=null) {
            String login = preferences.getString("isLogin", "0");
            if (login.equals("1")) {
                Intent intent = new Intent(LoginPages.this, MainActivity.class);
                intent.putExtra("user_id", preferences.getString("user_id", "0"));
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pages);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.LoginButton) void Login(){
        String phone = phoneNumber.getText().toString();
        String user_pass =password.getText().toString();
        if(!phoneNumber.getText().toString().isEmpty() && !password.getText().toString().isEmpty() ) {
            HashMap<String, String> result = InputControl.phoneCheck(phone);
            if (result != null && result.get("isOK").equals("1")) {
                RequestParams params = new RequestParams();
                params.put("phone_data", phone);
                params.put("password", user_pass);
                client.post("http://kodeveloper.co/payponse/mobile/checkUser.php", params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {

                        if (statusCode == 200) {
                            try {
                                JSONObject resultObject = new JSONObject(responseString);
                                if (resultObject.getString("isOK").equals("1")) {
                                    preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("isLogin", "1");
                                    editor.putString("user_id", resultObject.getString("user_id"));
                                    editor.commit();
                                    Intent intent = new Intent(LoginPages.this, MainActivity.class);
                                    intent.putExtra("user_id", resultObject.getString("user_id"));
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), resultObject.getString("error_message"), Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

            } else {
                Toast.makeText(getApplicationContext(), result.get("error_message"), Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Boş Alan Bırakmayınız.",Toast.LENGTH_LONG).show();
        }

    }
    @OnClick(R.id.registerButton) void register(){

        Intent intent = new Intent(LoginPages.this,RegisterPage.class);
        startActivity(intent);
    }
}
