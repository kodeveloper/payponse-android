package payponse.android.com.payponse.Pages;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import butterknife.OnTextChanged;
import cz.msebera.android.httpclient.Header;
import movile.com.creditcardguide.model.IssuerCode;
import movile.com.creditcardguide.view.CreditCardView;
import payponse.android.com.payponse.Handlers.DBHandler;
import payponse.android.com.payponse.MainActivity;
import payponse.android.com.payponse.R;
import payponse.android.com.payponse.Utils.UserDetails;

public class AddCartPages extends AppCompatActivity {

    ProgressDialog dialog;
    @Bind(R.id.ownerName)EditText ownerName;
    @Bind(R.id.lastDate) EditText lastDate;
    @Bind(R.id.securityCode)EditText scode;
    @Bind(R.id.cartNumber)
    EditText cartNumber;
    @Bind(R.id.creditCardView)
    CreditCardView cardView;
    byte cartFront=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_cart_pages);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.creditCardView)void cartReturn(){
        if(cartFront==1){
            cardView.flipToBack();
            cartFront=0;}
        else {// To show the back side
            cardView.flipToFront();
            cartFront=1;
        }// To show the front side
    }

    @OnTextChanged(R.id.cartNumber) void setNumberChange(){
        if (cartFront==0){
            cardView.flipToFront();
            cartFront=1;}
        String cartString =cartNumber.getText().toString();;
        int cartLength =cartNumber.getText().toString().length();
        if (cartLength==1){
            switch (cartNumber.getText().toString()){
                case "4" :

                    cardView.chooseFlag(IssuerCode.VISACREDITO);
                    break;
                case "5":
                    cardView.chooseFlag(IssuerCode.MASTERCARD);
            }
        }
       cardView.setTextNumber(cartString);
       /* if(cartLength%4==0 && cartLength!=16){
            currentNumber=cartString;
            currentNumber+=" ";
            cardView.setTextNumber(currentNumber);
        }*/



    }
    @OnTextChanged(R.id.ownerName) void setOwnerChange(){
        if (cartFront==0){
            cardView.flipToFront();
            cartFront=1;}
        cardView.setTextOwner(ownerName.getText().toString());
    }
    @OnTextChanged(R.id.securityCode) void setSecurityCode(){
        if (cartFront==1){
        cardView.flipToBack();
        cartFront=0;}
        cardView.setTextCVV(scode.getText().toString());
    }
    @OnTextChanged(R.id.lastDate) void setLastDate(){
        String date =lastDate.getText().toString();
        if (cartFront==0){
            cardView.flipToFront();
            cartFront=1;}
        if(date.length()<4)
        cardView.setTextExpDate(date);
        else{
            String first = date.substring(0,2);
            String end = date.substring(2,4);
            cardView.setTextExpDate(first + "/" + end);
        }

    }
    @OnClick(R.id.btnAdd) void addCart(){
        dialog= new ProgressDialog(this);
        dialog.setMessage("Kart Tanımlanıyor.");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();
         sendCartData();
    }
    void sendCartData(){
        String name =ownerName.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("payponseId", UserDetails.getDeviceId(this));
        params.put("name",name);
        params.put("surname",name);
        params.put("cart_number",cartNumber.getText().toString());
        params.put("expired_date",lastDate.getText().toString());
       client.post("http://kodeveloper.co/payponse/mobile/addCrediCard.php", params, new TextHttpResponseHandler() {
           @Override
           public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

           }

           @Override
           public void onSuccess(int statusCode, Header[] headers, String responseString) {
            if (statusCode==200){
                try {
                    JSONObject resultObject = new JSONObject(responseString);
                    if (resultObject.getString("isOK").equals("1")){
                        DBHandler handler = new DBHandler(getApplicationContext());
                        HashMap<String,String> cart = new HashMap<String,String>();
                        cart.put("cart_id",resultObject.getString("card_id"));
                        cart.put("cart_number",cartNumber.getText().toString());
                        cart.put("owner_name",ownerName.getText().toString());
                        cart.put("last_date",lastDate.getText().toString());
                        cart.put("ccv_code",scode.getText().toString());
                        cart.put("isActive", "1");
                        boolean result = handler.addCart(cart);
                        if (result){
                            clear();
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Başarıyla kayıt edildi.",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AddCartPages.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
           }
       });

    }
    void clear(){
        cartNumber.setText("");
        ownerName.setText("");
        lastDate.setText("");
        scode.setText("");
    }
}
