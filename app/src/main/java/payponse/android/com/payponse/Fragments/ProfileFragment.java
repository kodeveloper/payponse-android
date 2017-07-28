package payponse.android.com.payponse.Fragments;

import android.annotation.SuppressLint;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import payponse.android.com.payponse.Checks.ConnectionControl;
import payponse.android.com.payponse.Pages.SplashScreen;
import payponse.android.com.payponse.R;
import payponse.android.com.payponse.Utils.UserDetails;


@SuppressLint("ValidFragment")
public class ProfileFragment extends Fragment {
    public Context context;
    FragmentManager manager;
    SharedPreferences preferences;
    @Bind(R.id.qrCodeScreen)
    ImageView qrCodeProfile;

    @SuppressLint("ValidFragment")
    public ProfileFragment(FragmentManager currentManager,Context currentContext){
        this.manager=currentManager;
        this.context=currentContext;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View v = inflater.inflate(R.layout.fragment_profile, container, false);
        Context c = v.getContext().getApplicationContext();
        ButterKnife.bind(this, v);
      Picasso.with(c).load("http://kodeveloper.co/payponse/mobile/classes/qrCode.php?url=" + UserDetails.getDeviceId(c)).fit().into(qrCodeProfile);
        return v;
    }
    @OnClick(R.id.overlayMain) void closePage(){
        ProfileFragment pf = (ProfileFragment) manager.findFragmentByTag("profileFragment");
        FragmentTransaction transaction =manager.beginTransaction();
        if (pf!=null){
            transaction.remove(pf);
            transaction.addToBackStack("profileFragment");
            transaction.commit();
        }

    }
    @OnClick(R.id.logOut) void logOut(){
        preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        Intent intent = new Intent(getActivity(), SplashScreen.class);
        intent.putExtra("delete",true);
        startActivity(intent);
    }

}
