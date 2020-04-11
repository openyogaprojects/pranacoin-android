package com.itstudium.pranacoinwallet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class RestoreFragment extends Fragment
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
        return inflater.inflate(R.layout.fragment_restore, container, false);
    }
}