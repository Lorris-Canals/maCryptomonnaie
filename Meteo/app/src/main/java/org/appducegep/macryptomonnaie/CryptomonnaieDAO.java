package org.appducegep.macryptomonnaie;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.Date;

// https://developer.android.com/training/data-storage/sqlite.html

public class CryptomonnaieDAO extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Cryptomonnaie.db";

    public CryptomonnaieDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // https://www.sqlite.org/datatype3.html
    String SQL_CREATION_TABLE = "create table cryptomonnaie(id INTEGER PRIMARY KEY, date TEXT, symbol TEXT, min15 TEXT, last TEXT, buy TEXT, sell TEXT)";



    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("BASEDEDONNEES","MeteoDAO.onCreate()");
        db.execSQL(SQL_CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int avant, int apres) {
        Log.d("BASEDEDONNEES","MeteoDAO.onUpgrade()");
    }

    public void onDowngrade(SQLiteDatabase db, int avant, int apres) {
        Log.d("BASEDEDONNEES","MeteoDAO.onDowngrade()");
    }

    public void ajouterCryptomonnaie(String symbol, String min15, String last, String buy, String sell)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues CryptomonnaieDuMoment = new ContentValues();
        CryptomonnaieDuMoment.put("symbol", symbol);
        CryptomonnaieDuMoment.put("min15", min15);
        CryptomonnaieDuMoment.put("date", DateFormat.format("yyyy-MM-dd HH:mm:ss", (new Date()).getTime()).toString());
        CryptomonnaieDuMoment.put("last",last);
        CryptomonnaieDuMoment.put("buy",buy);
        CryptomonnaieDuMoment.put("sell",sell);
        long newRowId = db.insert("cryptomonnaie", null, CryptomonnaieDuMoment);

    }
}
