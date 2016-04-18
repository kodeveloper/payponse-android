package payponse.android.com.payponse.Utils;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * Created by Abdullah on 14.4.2016.
 */
public class UserDetails {
    public  static String  getDeviceId(Context mainContext){
        String android_id = Settings.Secure.getString(mainContext.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }
    public static String getCarrierName(Context mainContext){
        TelephonyManager manager = (TelephonyManager)mainContext.getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getNetworkOperatorName();
        return carrierName;

    }
}
