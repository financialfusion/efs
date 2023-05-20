/*  1:   */ package com.ffusion.banksim.interfaces;
/*  2:   */ 
/*  3:   */ import java.util.Locale;
/*  4:   */ import java.util.logging.Level;
/*  5:   */ 
/*  6:   */ public class BSConstants
/*  7:   */ {
/*  8:16 */   public static int PAGESIZE = 10;
/*  9:19 */   public static Locale currentLocale = Locale.getDefault();
/* 10:20 */   public static String LOCKBOXID = "LockboxID";
/* 11:21 */   public static String LockboxID = "LockboxID";
/* 12:22 */   public static String ACCOUNTID = "AccountID";
/* 13:   */   public static final String BAI_FILE_IDENTIFIER = "BAI_FILE_IDENTIFIER";
/* 14:   */   public static final String PAGE_SIZE = "PAGESIZE";
/* 15:   */   public static final int NONE = 0;
/* 16:   */   public static final int LIVE = 1;
/* 17:   */   public static final int BAI = 2;
/* 18:   */   public static final String LOG_NAME = "com.ffusion.banksim";
/* 19:43 */   public static final Level LOG_LEVEL = Level.ALL;
/* 20:   */   public static final String LOG_FILENAME = "banksim.log";
/* 21:   */   public static final String BANKSIM_PROPERTIES = "banksim.properties";
/* 22:   */   public static final String BS_ACCOUNT_NUMBER = "BS_ACCOUNT_NUMBER";
/* 23:   */   public static final String MIN_TIME_GENERAL = "GeneralMinTime";
/* 24:   */   public static final String MIN_TIME_SIGNON = "SignOnMinTime";
/* 25:   */   public static final String MIN_TIME_SET_PASSWORD = "SetPasswordMinTime";
/* 26:   */   public static final String MIN_TIME_ADD_BANK = "AddBankMinTime";
/* 27:   */   public static final String MIN_TIME_ADD_BANKS = "AddBanksMinTime";
/* 28:   */   public static final String MIN_TIME_GET_BANK = "GetBankMinTime";
/* 29:   */   public static final String MIN_TIME_UPDATE_BANK = "UpdateBankMinTime";
/* 30:   */   public static final String MIN_TIME_UPDATE_BANKS = "UpdateBanksMinTime";
/* 31:   */   public static final String MIN_TIME_DELETE_BANK = "DeleteBankMinTime";
/* 32:   */   public static final String MIN_TIME_ADD_CUSTOMER = "AddCustomerMinTime";
/* 33:   */   public static final String MIN_TIME_ADD_CUSTOMERS = "AddCustomersMinTime";
/* 34:   */   public static final String MIN_TIME_UPDATE_CUSTOMER = "UpdateCustomerMinTime";
/* 35:   */   public static final String MIN_TIME_UPDATE_CUSTOMERS = "UpdateCustomersMinTime";
/* 36:   */   public static final String MIN_TIME_DELETE_CUSTOMER = "DeleteCustomerMinTime";
/* 37:   */   public static final String MIN_TIME_ADD_ACCOUNT = "AddAccountMinTime";
/* 38:   */   public static final String MIN_TIME_ADD_ACCOUNTS = "AddAccountsMinTime";
/* 39:   */   public static final String MIN_TIME_GET_ACCOUNTS = "GetAccountsMinTime";
/* 40:   */   public static final String MIN_TIME_GET_ACCOUNT = "GetAccountMinTime";
/* 41:   */   public static final String MIN_TIME_UPDATE_ACCOUNT = "UpdateAccountMinTime";
/* 42:   */   public static final String MIN_TIME_UPDATE_ACCOUNTS = "UpdateAccountsMinTime";
/* 43:   */   public static final String MIN_TIME_DELETE_ACCOUNT = "DeleteAccountMinTime";
/* 44:   */   public static final String MIN_TIME_ADD_TRANSFER = "AddTransferMinTime";
/* 45:   */   public static final String MIN_TIME_ADD_TRANSFERS = "AddTransfersMinTime";
/* 46:   */   public static final String MIN_TIME_GET_TRANSACTIONS = "GetTransactionsMinTime";
/* 47:   */   public static final String MIN_TIME_OPEN_PAGED_TRANSACTIONS = "OpenPagedTransactionsMinTime";
/* 48:   */   public static final String MIN_TIME_CLOSE_PAGED_TRANSACTIONS = "ClosePagedTransactionsMinTime";
/* 49:   */   public static final String MIN_TIME_GET_NEXT_PAGE = "GetNextPageMinTime";
/* 50:   */   public static final String MIN_TIME_GET_PREV_PAGE = "GetPrevPageMinTime";
/* 51:   */   public static final String MIN_TIME_ADD_MAIL_MESSAGE = "AddMailMessage";
/* 52:   */   public static final String MIN_TIME_GET_MAIL_MESSAGES = "GetMailMessages";
/* 53:   */   public static final String SECURE_USER = "SecureUser";
/* 54:   */   public static final String PAGING_CONTEXT_REPORT_CRITERIA = "ReportCriteria";
/* 55:   */   public static final String PAGING_CONTEXT_SEARCH_CRITERIA_ACCOUNT = "Account";
/* 56:   */   public static final String PAGING_CONTEXT_SEARCH_CRITERIA_ROUT_NUM = "RoutingNum";
/* 57:   */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.interfaces.BSConstants
 * JD-Core Version:    0.7.0.1
 */