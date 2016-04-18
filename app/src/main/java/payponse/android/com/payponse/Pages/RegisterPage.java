package payponse.android.com.payponse.Pages;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import payponse.android.com.payponse.Checks.InputControl;
import payponse.android.com.payponse.Fragments.DetailsPage;
import payponse.android.com.payponse.R;

public class RegisterPage extends Activity {
    @Bind(R.id.registerMain)
    ImageView registerMain;
    private AsyncHttpClient client = new AsyncHttpClient();
    @Bind(R.id.registerPhone) EditText phoneNumber;

    @Override
    protected void onStart() {
        super.onStart();
        Picasso.with(this).load(R.drawable.backgrounds).fit().into(registerMain);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.checkPhone) void checkPhone(){
        HashMap<String,String> result = InputControl.phoneCheck(phoneNumber.getText().toString());

        if(result != null && result.get("isOK").equals("1")){
            RequestParams params = new RequestParams();
            params.put("phone_data",phoneNumber.getText().toString());
            client.post("http://kodeveloper.co/payponse/mobile/checkPhoneNumber.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {

                    if (statusCode==200){
                        responseString=responseString.replace("\n","");
                        boolean result = Boolean.parseBoolean(responseString);
                        if (!result){
                            showDetails();
                        }else{
                            Toast.makeText(getApplicationContext(),"Kayıt Mevcuttur.",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

        }else
            Toast.makeText(getApplicationContext(),result.get("error_message"),Toast.LENGTH_LONG).show();

    }
    void showDetails(){

        DetailsPage dp = new DetailsPage();
        Bundle bundle = new Bundle();
        bundle.putString("phone_number", phoneNumber.getText().toString());
        FragmentManager manager = getFragmentManager();
        dp.setArguments(bundle);
        findViewById(R.id.phoneContainer).setVisibility(View.GONE);
        FragmentTransaction transaction =manager.beginTransaction();
        transaction.add(R.id.detailsContainer,dp,"detailsFragment");
        transaction.commit();
    }
}
