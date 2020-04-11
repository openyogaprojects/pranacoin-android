package com.itstudium.pranacoinwallet;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.android.gms.iid.InstanceID;

import android.app.Activity;

import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment
{
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState)
  {
        /*
        just change the fragment_dashboard
        with the fragment you want to inflate
        like if the class is HomeFragment it should have R.layout.home_fragment
        if it is DashboardFragment it should have R.layout.fragment_dashboard
        */
    return inflater.inflate(R.layout.fragment_home, container, false);
  }
  
  
  public final static int QR_CODE_DIMENSION = 500;
  
  Bitmap    bitmap;
  ImageView imageView;
  
  Context context = PranaWallet.applicationContext();
  
  @Override
  public void onAttach(Context context)
  {
    super.onAttach(context);
    if(context instanceof Activity)
    {
      //TODO Activity a = (Activity) context;
    }
  }
  
  
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
  {
    super.onViewCreated(view, savedInstanceState);
    
    //final Context context = getContext().getApplicationContext();
    final Context context = PranaWallet.applicationContext();
    
    final String idofuser = InstanceID.getInstance(context).getId();
    
    final TextView textView2 = view.findViewById(R.id.textView2);
    
    final ImageView imageView = view.findViewById(R.id.imageView);
    
    final String url1 = "http://95.213.191.196/api.php?action=getbalance&walletid=" + idofuser;
    
    
    WalletDbHelper readAddress = new WalletDbHelper(getActivity());
    SQLiteDatabase database    = readAddress.getReadableDatabase();
    Cursor         cursor      = readAddress.readWallet(database);
    String         pubaddress  = "";
    while(cursor.moveToNext())
    {
      String pubaddr =
          cursor.getString(cursor.getColumnIndex(WalletInfo.WalletEntry.WALLET_PUBADDR));
      pubaddress = pubaddr;
      textView2.setText(pubaddr);
    }
    
    readAddress.close();
    
    final TextView textView1 = view.findViewById(R.id.textView1);
    
    Timer timer = new Timer();
    timer.schedule(new TimerTask()
    {
      @Override
      public void run()
      {
        if(getActivity() != null)
        {
          getActivity().runOnUiThread(new Runnable()
          {
            @Override
            public void run()
            {
              
              final RequestQueue requestQueue1 = Volley.newRequestQueue(context);
              
              StringRequest stringRequest1 =
                  new StringRequest(Request.Method.GET, url1, new Response.Listener<String>()
                  {
                    @Override
                    public void onResponse(String response)
                    {
                      WalletDbHelper writeBalance     = new WalletDbHelper(context);
                      SQLiteDatabase database_balance = writeBalance.getWritableDatabase();
                      writeBalance.addBalance(idofuser, response, database_balance);
                      writeBalance.close();
                      requestQueue1.stop();
                    }
                  }, new Response.ErrorListener()
                  {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                      requestQueue1.stop();
                    }
                  });
              
              requestQueue1.add(stringRequest1);
              
              WalletDbHelper readBalance      = new WalletDbHelper(context);
              SQLiteDatabase database_balance = readBalance.getReadableDatabase();
              Cursor         cursor_balance   = readBalance.readBalance(database_balance);
              while(cursor_balance.moveToNext())
              
              {
                String balance = cursor_balance.getString(
                    cursor_balance.getColumnIndex(WalletInfo.BalanceEntry.WALLET_BALANCE));
                textView1.setText(balance);
              }
              readBalance.close();
              
            }
            
            
          });
        }
      }
    }, 0, 30000);
    
    
    try
    {
      bitmap = TextToImageEncode(pubaddress);
      
      imageView.setImageBitmap(bitmap);
      
    }
    catch(WriterException e)
    {
      e.printStackTrace();
    }
    
  }
  
  Bitmap TextToImageEncode(String Value) throws WriterException
  {
    BitMatrix bitMatrix;
    try
    {
      bitMatrix = new MultiFormatWriter()
          .encode(Value, BarcodeFormat.QR_CODE, QR_CODE_DIMENSION, QR_CODE_DIMENSION, null);
    }
    catch(IllegalArgumentException Illegalargumentexception)
    {
      return null;
    }
    int bitMatrixWidth = bitMatrix.getWidth();
    
    int bitMatrixHeight = bitMatrix.getHeight();
    
    int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];
    
    for(int y = 0; y < bitMatrixHeight; y++)
    {
      int offset = y * bitMatrixWidth;
      
      for(int x = 0; x < bitMatrixWidth; x++)
      {
        pixels[offset + x] =
            bitMatrix.get(x, y) ? getResources().getColor(R.color.QRCodeBlackColor) :
                getResources().getColor(R.color.QRCodeWhiteColor);
      }
    }
    Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
    
    bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
    return bitmap;
    
  }
}


