package com.itstudium.pranacoinwallet;

public final class WalletInfo
{

    private WalletInfo()
    {
    }

    public static class WalletEntry
    {
        public static final String TABLE_NAME = "wallet_info";
        public static final String WALLET_ID = "wallet_id";
        public static final String WALLET_PUBADDR = "wallet_pubaddr";
        public static final String WALLET_PRIVADDR = "wallet_privaddr";
    }

    public static class BalanceEntry
    {
        public static final String TABLE_NAME = "wallet_balance";
        public static final String WALLET_ID = "wallet_id";
        public static final String WALLET_BALANCE = "wallet_balance";
    }
}
