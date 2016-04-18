package payponse.android.com.payponse.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

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
import payponse.android.com.payponse.Utils.UserDetails;

public class DetailsPage extends Fragment {

    AsyncHttpClient client = new AsyncHttpClient();
    private String phoneNumber=null;
    @Bind(R.id.emailData) EditText userMail;
    @Bind(R.id.nameData) EditText userName;
    @Bind(R.id.surnameData)EditText userSurname;
    @Bind(R.id.passwordData) EditText userPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.details_page, container, false);
        ButterKnife.bind(this,v);
        phoneNumber= getArguments().getString("phone_number");
        return v;
    }
    @OnClick(R.id.completeRegister) void CompleteRegister(){

        HashMap<String,String> result = InputControl.detailsCheck(
                userName.getText().toString(),
                userSurname.getText().toString(),
                userMail.getText().toString(),
                userPassword.getText().toString());
        if (result.get("isOK").equals("1")){
            RequestParams params = new RequestParams();
            params.put("payponseId", UserDetails.getDeviceId(getActivity()));
            params.put("phone_number",phoneNumber);
            params.put("master_passwrod","master");
            params.put("password",userPassword.getText().toString());
            client.post("http://kodeveloper.co/payponse/mobile/index.php", params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    if (statusCode==200){
                        try {
                            JSONObject resultObject = new JSONObject(responseString);
                            if (resultObject.getString("isOK").equals("1")){
                                sendDetails(resultObject.getString("user_id"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }else{
            Toast.makeText(getContext(),result.get("error_message"),Toast.LENGTH_LONG).show();
        }
    }
    void sendDetails(final String user_id){
        RequestParams params = new RequestParams();
        params.put("uid",user_id);
        params.put("name",userName.getText().toString());
        params.put("surname",userSurname.getText().toString());
        params.put("email",userMail.getText().toString());
        params.put("model", Build.MODEL);
        params.put("carrier", UserDetails.getCarrierName(getActivity()));
        params.put("os_type","Android");
        params.put("os_version",Build.VERSION.RELEASE);
        params.put("device_token","device_token");

        client.post("http://kodeveloper.co/payponse/mobile/userDetail.php", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (statusCode==200){
                  //  try {
                      //  JSONObject resultObject = new JSONObject(responseString);
                       // if (resultObject.getString("isOK").equals("1")){

                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.putExtra("user_id",user_id);
                            startActivity(intent);
                            getActivity().finish();
                       // }
                  //  } catch (JSONException e) {
                     //   e.printStackTrace();
                  //  }
                }
            }
        });
    }
}
