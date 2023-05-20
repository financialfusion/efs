package com.ffusion.tasks.billpay;

import java.math.BigDecimal;

public abstract interface Task
  extends com.ffusion.tasks.Task
{
  public static final String BILLPAY = "com.ffusion.services.BillPay";
  public static final String ACCOUNTS = "BillPayAccounts";
  public static final String BILLPAYACCOUNT = "BillPayAccount";
  public static final String ACCOUNT = "Account";
  public static final String PAYEE = "Payee";
  public static final String PAYEES = "Payees";
  public static final String PAYMENT = "Payment";
  public static final String PAYMENTS = "Payments";
  public static final String PAYHISTORY = "PayHistory";
  public static final String RECPAYMENTS = "RecPayments";
  public static final String PAYHISTORYPAYEES = "PayHistoryPayees";
  public static final String REPORT_DATA = "PaymentReportData";
  public static final String PAYMENTTEMPLATES = "PaymentTemplates";
  public static final String NICKNAME = "NICK_NAME";
  public static final int MAX_MEMO_LENGTH = 64;
  public static final BigDecimal MAX_AMOUNT_ALLOWED = com.ffusion.tasks.banking.Task.MAX_AMOUNT_ALLOWED;
  public static final int ERROR_START = 2000;
  public static final int ERROR_NO_SERVICE = 2001;
  public static final int ERROR_NO_PAYEES = 2002;
  public static final int ERROR_NO_PAYMENTS = 2003;
  public static final int ERROR_NO_RECPAYMENTS = 2004;
  public static final int ERROR_NO_ACCOUNTS = 2005;
  public static final int ERROR_NO_PAYMENT = 2006;
  public static final int ERROR_INVALID_ACCOUNT = 2007;
  public static final int ERROR_INVALID_PAYEE = 2008;
  public static final int ERROR_INVALID_PAYMENT = 2009;
  public static final int ERROR_INVALID_RECPAYMENT = 2010;
  public static final int ERROR_USERACCOUNTNUMBER = 2011;
  public static final int ERROR_NAMEEXISTS = 2012;
  public static final int ERROR_PAYEENOTFOUND = 2013;
  public static final int ERROR_PAYMENTSPENDING = 2014;
  public static final int ERROR_MINAMOUNT = 2015;
  public static final int ERROR_MAXAMOUNT = 2016;
  public static final int ERROR_DATETOEARLY = 2017;
  public static final int ERROR_AMOUNT = 2018;
  public static final int ERROR_AMOUNT_TOO_SMALL = 2019;
  public static final int ERROR_AMOUNT_TOO_LARGE = 2020;
  public static final int ERROR_DATE = 2021;
  public static final int ERROR_NUMBERPAYMENTS = 2022;
  public static final int ERROR_PAYEEINFO = 2023;
  public static final int ERROR_STARTDATE = 2024;
  public static final int ERROR_ENDDATE = 2025;
  public static final int ERROR_NAME = 2026;
  public static final int ERROR_PAYMENT_STATUS = 2027;
  public static final int ERROR_NO_PAYEES_TO_ADD = 2028;
  public static final int ERROR_DUPLICATE_PAYMENT = 2029;
  public static final int ERROR_ALREADY_PROCESSING = 2030;
  public static final int ERROR_CLASSNAME_NOT_SPECIFIED = 2031;
  public static final int ERROR_MEMO_TOO_LONG = 2032;
  public static final int TASK_ERROR_USER_NOT_FOUND_IN_SESSION = 2033;
  public static final int ERROR_INVALID_BATCH_NAME = 2034;
  public static final int ERROR_DUPLICATE_BATCH_NAME = 2035;
  public static final int ERROR_NO_PAYMENT_TEMPLATES = 2036;
  public static final int ERROR_NO_DESTINATION_BILLPAY_ACCOUNTS = 2037;
  public static final int ERROR_NO_PAYMENT_TEMPLATENAME = 2038;
  public static final int ERROR_DUPLICATE_PAYMENT_TEMPLATENAME = 2039;
  public static final int ERROR_PAYMENT_TEMPLATE_FOR_PAYEE = 2040;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.Task
 * JD-Core Version:    0.7.0.1
 */