package com.itstudium.pranacoinwallet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.iid.InstanceID;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class SendFragment extends Fragment
{
    private String toast;
    EditText edit_text = null;
    public SendFragment()
    {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        displayToast();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //final Context context = getContext().getApplicationContext();
        final Context context = PranaWallet.applicationContext();
        final String idofuser = InstanceID.getInstance(context).getId();
        View view = inflater.inflate(R.layout.fragment_send, container, false);
        Button scan = view.findViewById(R.id.buttonScan);
        scan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                scanFromFragment();
            }
        });
        Button send = view.findViewById(R.id.buttonSend);
        final EditText recepientaddr = view.findViewById(R.id.input_address);
        final EditText sum = view.findViewById(R.id.Sum);
        send.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                String recepaddr = recepientaddr.getText().toString();
                String amount = sum.getText().toString();
                String url1 = "http://95.213.191.196/api.php?action=sendprana&walletid="+idofuser+"&recipientaddr="+recepaddr+"&sum="+amount;

                //final RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity().getApplicationContext());
                final RequestQueue requestQueue1 = Volley.newRequestQueue(context);

                StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                toast = "Successful transaction!";
                                displayToast();
                                requestQueue1.stop();
                            }
                        },
                    new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        toast = "Error! Something went wrong!";
                        displayToast();
                        requestQueue1.stop();
                    }
                });

                requestQueue1.add(stringRequest1);
            }
        });

        return view;
    }



    public void scanFromFragment()
    {
        IntentIntegrator.forSupportFragment(this).initiateScan();
    }

    private void displayToast()
    {
        if(getActivity() != null && toast != null)
        {
            Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
            toast = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null)
        {
            if(result.getContents() == null)
            {
                //TODO toast = "Cancelled from fragment";
            }
            else
              {
                toast = result.getContents();
            }
            View view = getView();
            if(view != null)
            {
              edit_text = view.findViewById(R.id.input_address);
              edit_text.setText(toast);
            }
            // At this point we may or may not have a reference to the activity
            displayToast();
        }
    }

}