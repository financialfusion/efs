package com.ffusion.beans.aggregation;

abstract interface TransactionCategories
{
  public static final int CATEGORY_UNKNOWN = 0;
  public static final int CATEGORY_DEPOSIT = 1;
  public static final int CATEGORY_CHECK = 2;
  public static final int CATEGORY_OTHERDEBIT = 3;
  public static final int CATEGORY_OTHERCREDIT = 4;
  public static final int CATEGORY_ATM = 5;
  public static final int CATEGORY_POINTOFSALE = 6;
  public static final int CATEGORY_FEE = 7;
  public static final int CATEGORY_INTEREST = 8;
  public static final int CATEGORY_DIV = 9;
  public static final int CATEGORY_SRVCHG = 10;
  public static final int CATEGORY_XFER = 11;
  public static final int CATEGORY_CASH = 12;
  public static final int CATEGORY_PAYMENT = 13;
  public static final int CATEGORY_DIRECTDEP = 14;
  public static final int CATEGORY_DIRECTDEBIT = 15;
  public static final int CATEGORY_REPEATPMT = 16;
  public static final int CATEGORY_OTHER = 17;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.aggregation.TransactionCategories
 * JD-Core Version:    0.7.0.1
 */