package payponse.android.com.payponse;



import android.app.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import movile.com.creditcardguide.model.IssuerCode;
import movile.com.creditcardguide.view.CreditCardView;
import payponse.android.com.payponse.Fragments.DetailsPage;
import payponse.android.com.payponse.Fragments.ProfileFragment;
import payponse.android.com.payponse.Handlers.DBHandler;
import payponse.android.com.payponse.Pages.AddCartPages;
import payponse.android.com.payponse.Pages.CameraPages;


public class MainActivity extends Activity {
    @Bind(R.id.plusView)
    ImageView plus;
    ProgressDialog pDialog;
    @Bind(R.id.cardContainer)
    LinearLayout cardContainer;
    @OnClick(R.id.btnQrReader)

    void cameraPages() {

        Intent intent = new Intent(this, CameraPages.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        callCards();
//        new AsyncCaller().execute();
    }

    @OnClick(R.id.profile) void getProfile(){

        FragmentManager manager = getFragmentManager();
        ProfileFragment pf = new ProfileFragment(manager,getApplicationContext());
        FragmentTransaction transaction =manager.beginTransaction();
        transaction.add(R.id.mainLayout,pf,"profileFragment");
        transaction.commit();
    }
    @OnClick(R.id.addCart)
    void addCartPage() {
        Intent intent = new Intent(this, AddCartPages.class);
        startActivity(intent);
    }
    void  callCards(){
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Yükleniyor...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

        DBHandler handler = new DBHandler(getApplicationContext());
        List<HashMap<String,String>> list = handler.getAllCards();
        System.out.println(list.size());
        for (int i=0 ; i<list.size();i++){

            HashMap<String,String> send = list.get(i);
            View view;
            LayoutInflater inflater = (LayoutInflater)   this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_cart, null);
            final CreditCardView card = (CreditCardView) view.findViewById(R.id.listCardView);
            card.setTextNumber(send.get("cart_number"));
            if (send.get("cart_number").substring(0,1).equals("4"))
                card.chooseFlag(IssuerCode.VISAELECTRON);
            else
                card.chooseFlag(IssuerCode.MASTERCARD);

            String date_moon = send.get("last_date").substring(0,2);
            String date_year = send.get("last_date").substring(2,4);
            card.setTextExpDate(date_moon+"/"+date_year);

            card.setTextOwner(send.get("owner_name"));
            card.setTextCVV(send.get("ccv_code"));
            card.setId(Integer.parseInt(send.get("cart_id")));
            plus.setVisibility(View.GONE);
            cardContainer.addView(view);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), String.valueOf(card.getId()), Toast.LENGTH_LONG).show();
                }
            });
            card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(getApplicationContext(),"Uzun Basıldı.",Toast.LENGTH_LONG).show();

                    return false;
                }
            });
        }
        pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}


