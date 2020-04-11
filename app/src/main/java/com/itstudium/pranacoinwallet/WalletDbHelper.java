package com.itstudium.pranacoinwallet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WalletDbHelper extends SQLiteOpenHelper
{
  private static final  String DATABASE_NAME    = "wallets_db";
  private static final int    DATABASE_VERSION = 1;
  
  private static final String CREATE_TABLE_WALLETS = "create table " + WalletInfo.WalletEntry.TABLE_NAME +
                                                     "(" + WalletInfo.WalletEntry.WALLET_ID + " number," + WalletInfo.WalletEntry.WALLET_PUBADDR + " text," +
                                                     WalletInfo.WalletEntry.WALLET_PRIVADDR + " text);";
  private static final String DROP_TABLE_WALLETS   = "drop table if exists " + WalletInfo.WalletEntry.TABLE_NAME;
  
  private static final String CREATE_TABLE_BALANCE = "create table " + WalletInfo.BalanceEntry.TABLE_NAME +
                                                     "(" + WalletInfo.BalanceEntry.WALLET_ID + " number," + WalletInfo.BalanceEntry.WALLET_BALANCE + " number);";
  private static final String DROP_TABLE_BALANCE   = "drop table if exists " + WalletInfo.BalanceEntry.TABLE_NAME;


    public WalletDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database Operations", "Database created...");
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_BALANCE);
        db.execSQL(CREATE_TABLE_WALLETS);

        Log.d("Database Operations", "Tables created...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DROP_TABLE_WALLETS);
        db.execSQL(DROP_TABLE_BALANCE);
        onCreate(db);
    }

    public void addWallet (String wallet_id, String wallet_pubaddr, String wallet_privaddr, SQLiteDatabase database)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(WalletInfo.WalletEntry.WALLET_ID, wallet_id);
        contentValues.put(WalletInfo.WalletEntry.WALLET_PUBADDR, wallet_pubaddr);
        contentValues.put(WalletInfo.WalletEntry.WALLET_PRIVADDR, wallet_privaddr);

        database.insert(WalletInfo.WalletEntry.TABLE_NAME, null, contentValues);
        Log.d("Database Operations", "Wallet entry inserted...");
    }

    public void addBalance (String wallet_id, String wallet_balance, SQLiteDatabase database)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(WalletInfo.BalanceEntry.WALLET_ID, wallet_id);
        contentValues.put(WalletInfo.BalanceEntry.WALLET_BALANCE, wallet_balance);

        database.insert(WalletInfo.BalanceEntry.TABLE_NAME, null, contentValues);
        Log.d("Database Operations", "Balance entry inserted...");
    }

    public Cursor readWallet (SQLiteDatabase database)
    {
        String[] projections = {WalletInfo.WalletEntry.WALLET_ID,
                WalletInfo.WalletEntry.WALLET_PUBADDR, WalletInfo.WalletEntry.WALLET_PRIVADDR};
        Cursor cursor = database.query(WalletInfo.WalletEntry.TABLE_NAME, projections, null, null, null, null, null);
        return cursor;
    }

    public Cursor readBalance (SQLiteDatabase database)
    {
        String[] projections = {WalletInfo.BalanceEntry.WALLET_ID, WalletInfo.BalanceEntry.WALLET_BALANCE};
        Cursor cursor_balance = database.query(WalletInfo.BalanceEntry.TABLE_NAME, projections, null, null, null, null, null);
        return cursor_balance;
    }
}