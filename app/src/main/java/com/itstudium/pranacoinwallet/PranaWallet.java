package com.itstudium.pranacoinwallet;

import android.app.Application;
import android.content.Context;

import org.jetbrains.annotations.NotNull;

/**
 * Singleton pattern is used
 * TODO make safe for threads 
 */
public final class PranaWallet extends Application
{
  private static PranaWallet instance = null;
  
  public PranaWallet()
  {
    if (instance == null)
    {
      instance = this;
    }
  }
  
  public static PranaWallet getInstance()
  {
    return PranaWallet.instance;
  }
  
  @NotNull
  public static Context applicationContext()
  {
    return getInstance().getApplicationContext();
  }
}