package com.ffusion.beans.exttransfers;

public abstract interface ExtTransferAccountDefines
{
  public static final int STATUS_UNKNOWN = 0;
  public static final int STATUS_ACTIVE = 1;
  public static final int STATUS_INACTIVE = 2;
  public static final int STATUS_DELETED = 3;
  public static final int VERIFYSTATUS_NOT_REQUIRED = 0;
  public static final int VERIFYSTATUS_NOT_DEPOSITED = 1;
  public static final int VERIFYSTATUS_DEPOSITED = 2;
  public static final int VERIFYSTATUS_DEPOSIT_FAILED = 3;
  public static final int VERIFYSTATUS_VERIFIED = 4;
  public static final int VERIFYSTATUS_NOT_VERIFIED = 5;
  public static final String ETF_ACCTBANKRTNTYPE_SWIFT = "SWIFT";
  public static final String ETF_ACCTBANKRTNTYPE_FEDABA = "FEDABA";
  public static final String ETF_ACCTBANKRTNTYPE_REGIONALBANKID = "REGIONALBANKID";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.exttransfers.ExtTransferAccountDefines
 * JD-Core Version:    0.7.0.1
 */