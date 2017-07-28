package payponse.android.com.payponse.Handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by abdullah on 18.04.2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "payponse";
    private static final String TABLE_NAME = "carts";
    private static final String KEY_ID = "cart_id";
    private static final String KEY_NUMBER="cart_number";
    private static final String KEY_OWNER="owner_name";
    private static final String KEY_CCV="security_code";
    private static final String KEY_DATE="last_date";
    private static final String KEY_TYPE="cart_type";
    private static final String KEY_ACTIVE="isActive";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CARDS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                +"id"+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +KEY_ID +" TEXT,"
                +KEY_NUMBER +" TEXT,"
                +KEY_OWNER +" TEXT,"
                +KEY_CCV +" TEXT, "
                +KEY_DATE +" TEXT,"+
                KEY_ACTIVE +" TEXT" +");";
        db.execSQL(CREATE_CARDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Boolean addCart(HashMap<String, String> cart_data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, cart_data.get("cart_id"));
        values.put(KEY_NUMBER, cart_data.get("cart_number"));
        values.put(KEY_OWNER,cart_data.get("owner_name"));
        values.put(KEY_DATE,cart_data.get("last_date"));
        values.put(KEY_CCV,cart_data.get("ccv_code"));
        values.put(KEY_ACTIVE, cart_data.get("isActive"));
        System.out.println(values);
        db.insertOrThrow(TABLE_NAME, null, values);

        db.close(); // Closing database connection
        return true;
    }

    /*// Getting one shop
    public Shop getShop(int id) {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.query(TABLE_SHOPS, new String[] { KEY_ID,
    KEY_NAME, KEY_SH_ADDR }, KEY_ID + "=?",
    new String[] { String.valueOf(id) }, null, null, null, null);
    if (cursor != null)
    cursor.moveToFirst();
    Shop contact = new Shop(Integer.parseInt(cursor.getString(0)),
    cursor.getString(1), cursor.getString(2));
// return shop
return contact;
}*/

    public List<HashMap<String,String>> getAllCards() {
        List<HashMap<String,String>> cartList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String,String> dbResult = new HashMap<String,String>();
                dbResult.put("cart_id", cursor.getString(1));
                dbResult.put("cart_number",cursor.getString(2));
                dbResult.put("owner_name",cursor.getString(3));
                dbResult.put("ccv_code",cursor.getString(4));
                dbResult.put("last_date",cursor.getString(5));
                dbResult.put("isActive",cursor.getString(6));
// Adding contact to list
                cartList.add(dbResult);
            } while (cursor.moveToNext());
            cursor.close();
        }
// returnc contact list
        return cartList;

    }
}
