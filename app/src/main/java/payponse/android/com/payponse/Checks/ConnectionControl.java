package payponse.android.com.payponse.Checks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by abdullah on 19.04.2016.
 */
public class ConnectionControl {

    public static boolean isAccess(Context context){
        boolean isConnect=false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = cm.getActiveNetworkInfo();
        if (i!=null && i.isConnected())
            isConnect=true;

        return isConnect;
    }
}
