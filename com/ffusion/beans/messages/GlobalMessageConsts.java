package com.ffusion.beans.messages;

public abstract interface GlobalMessageConsts
{
  public static final int RECORD_TYPE_MESSAGE = 1;
  public static final int RECORD_TYPE_TEMPLATE = 2;
  public static final int[] RecordTypes = { 1, 2 };
  public static final int COLOR_BLACK = 1;
  public static final int COLOR_RED = 2;
  public static final int COLOR_BLUE = 3;
  public static final int COLOR_GREEN = 4;
  public static final int COLOR_YELLOW = 5;
  public static final int PRIORITY_NORMAL = 1;
  public static final int PRIORITY_INFORMATIONAL = 2;
  public static final int PRIORITY_HIGH = 3;
  public static final int MSG_TYPE_SECURE = 1;
  public static final int MSG_TYPE_UNSECURE = 2;
  public static final int MSG_TYPE_ACH = 3;
  public static final int MSG_TYPE_WIRE = 4;
  public static final int MSG_TYPE_HOME = 5;
  public static final int MSG_TYPE_LOGIN = 6;
  public static final int MSG_TYPE_ADMIN = 7;
  public static final int MSG_TYPE_PAYMENTS = 8;
  public static final int MSG_TYPE_TRANSFERS = 9;
  public static final int[] MessageTypes = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
  public static final int ALL_CUSTOMERS = 0;
  public static final int ALL_SERVICE_PKG = 1;
  public static final int ALL_BUSINESS_CUSTOMERS = 2;
  public static final int ALL_CONSUMER_CUSTOMERS = 3;
  public static final int SPECIFIC_CUSTOMERS = 4;
  public static final int[] GroupTypes = { 0, 1, 2, 3, 4 };
  public static final int SUITE_CB = 1;
  public static final int SUITE_CEFS = 2;
  public static final int[] SuiteTypes = { 1, 2 };
  public static final int ALL_AFFILIATE_BANKS = 0;
  public static final int FILTER_TYPE_ENTITLEMENT = 1;
  public static final int FILTER_TYPE_REPORT = 2;
  public static final String FILTER_TYPE = "FILTERTYPE";
  public static final String FILTER_VALUE = "FILTERVALUE";
  public static final String RECIPIENT_LIST_ID = "RECIPIENTLISTID";
  public static final String RECIPIENT_TYPE = "RECIPIENTTYPE";
  public static final String RECIPIENT_DIR_ID = "RECIPIENTDIRID";
  public static final String CUSTOMER_ID = "CUSTOMERID";
  public static final String CUSTOMER_NAME = "CUSTOMERNAME";
  public static final String FILTERS_KEY = "_filters";
  public static final String RECIPIENTS_KEY = "_recipients";
  public static final String FILTERS_RECIPIENTS_LOADED_KEY = "loaded";
  public static final String FILTERS_RECIPIENTS_MODIFIED_KEY = "FILTERS_RECIPIENTS_MODIFIED_KEY";
  public static final int RCPT_TYPE_CONSUMER = 1;
  public static final int RCPT_TYPE_BUSINESS = 2;
  public static final String TO_GROUP_NAME_ALL_BUSINESS_ADMINISTRATORS = "All Business Administrators";
  public static final String TO_GROUP_NAME_ALL_BUSINESS_CUSTOMERS = "All Business Customers";
  public static final String TO_GROUP_NAME_ALL_CONSUMER_CUSTOMERS = "All Consumer Customers";
  public static final String TO_GROUP_NAME_ALL_SERVICE_PACKAGES = "All Service Packages";
  public static final String TO_GROUP_NAME_ALL_ACH_CUSTOMERS = "All ACH Customers";
  public static final String TO_GROUP_NAME_ALL_WIRE_CUSTOMERS = "All Wire Customers";
  public static final String TO_GROUP_NAME_ALL_BILL_PAYMENTS_CUSTOMERS = "All Bill Payments Customers";
  public static final String TO_GROUP_NAME_ALL_TRANSFERS_CUSTOMERS = "All Transfers Customers";
  public static final String TO_GROUP_NAME_ALL_CONSUMER_BILL_PAYMENTS_CUSTOMERS = "All Consumer Bill Payments Customers";
  public static final String TO_GROUP_NAME_ALL_CORPORATE_BILL_PAYMENTS_CUSTOMERS = "All Corporate Bill Payments Customers";
  public static final String TO_GROUP_NAME_ALL_CONSUMER_TRANSFERS_CUSTOMERS = "All Consumer Transfers Customers";
  public static final String TO_GROUP_NAME_ALL_CORPORATE_TRANSFERS_CUSTOMERS = "All Corporate Transfers Customers";
  public static final String TO_GROUP_NAME_ALL_ONLINE_BANKING_CUSTOMERS = "All Online Banking Customers";
  public static final String TO_GROUP_NAME_SPECIFIC_CUSTOMERS = "Specific Customers";
  public static final String TO_GROUP_NAME_ADMINISTRATORS_IN_PREFIX = "Administrators in ";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.GlobalMessageConsts
 * JD-Core Version:    0.7.0.1
 */