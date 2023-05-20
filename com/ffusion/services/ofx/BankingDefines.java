package com.ffusion.services.ofx;

public abstract interface BankingDefines
  extends Defines
{
  public static final String BANKMAILRQ = "BANKMAILRQ";
  public static final String BANKMAILRS = "BANKMAILRS";
  public static final String CHKMAILRS = "CHKMAILRS";
  public static final String CORRECTACTION = "CORRECTACTION";
  public static final String CORRECTFITID = "CORRECTFITID";
  public static final String DEPMAILRS = "DEPMAILRS";
  public static final String DTAVAIL = "DTAVAIL";
  public static final String DTEND = "DTEND";
  public static final String DTPOSTED = "DTPOSTED";
  public static final String DTSTART = "DTSTART";
  public static final String EMAILMSGSRQV1 = "EMAILMSGSRQV1";
  public static final String EMAILMSGSRSV1 = "EMAILMSGSRSV1";
  public static final String FITID = "FITID";
  public static final String GETMIMETRNRS = "GETMIMETRNRS";
  public static final String INCLUDE = "INCLUDE";
  public static final String INCTRAN = "INCTRAN";
  public static final String INTRACANRQ = "INTRACANRQ";
  public static final String INTRACANRS = "INTRACANRS";
  public static final String INTRAMODRQ = "INTRAMODRQ";
  public static final String INTRAMODRS = "INTRAMODRS";
  public static final String INTRARQ = "INTRARQ";
  public static final String INTRARS = "INTRARS";
  public static final String INV401KSOURCE = "INV401KSOURCE";
  public static final String MAILRQ = "MAILRQ";
  public static final String MAILRS = "MAILRS";
  public static final String MAILSYNCRQ = "MAILSYNCRQ";
  public static final String MAILSYNCRS = "MAILSYNCRS";
  public static final String MAILTRNRQ = "MAILTRNRQ";
  public static final String MAILTRNRS = "MAILTRNRS";
  public static final String RECINTRACANRQ = "RECINTRACANRQ";
  public static final String RECINTRACANRS = "RECINTRACANRS";
  public static final String RECINTRAMODRQ = "RECINTRAMODRQ";
  public static final String RECINTRAMODRS = "RECINTRAMODRS";
  public static final String RECINTRARQ = "RECINTRARQ";
  public static final String RECINTRARS = "RECINTRARS";
  public static final String REFNUM = "REFNUM";
  public static final String SIC = "SIC";
  public static final String STMTTRN = "STMTTRN";
  public static final String TRNTYPE = "TRNTYPE";
  public static final String[] transType = { "CREDIT", "DEBIT", "INT", "DIV", "FEE", "SRVCHG", "DEP", "ATM", "POS", "XFER", "CHECK", "PAYMENT", "CASH", "DIRECTDEP", "DIRECTDEBIT", "REPEATPAYMENT", "OTHER" };
  public static final int[] transMap = { 4, 5, 11, 18, 10, 23, 1, 7, 9, 16, 3, 25, 28, 29, 12, 13, 30 };
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ofx.BankingDefines
 * JD-Core Version:    0.7.0.1
 */