package com.ffusion.tasks.banking;

abstract interface ExportFormats
{
  public static final int UNKNOWN = 0;
  public static final int OFX = 1;
  public static final int QIF98 = 2;
  public static final int QIF99 = 3;
  public static final int COMMA_INT = 4;
  public static final int QFX99 = 5;
  public static final int OFX_MMAS = 6;
  public static final int QFX_QBO = 7;
  public static final String OFX_STR = "OFX";
  public static final String QIF98_STR = "QIF98";
  public static final String QIF99_STR = "QIF99";
  public static final String COMMA_STR = "COMMA";
  public static final String QFX99_STR = "QFX99";
  public static final String OFX_MMAS_STR = "OFX_MMAS";
  public static final String QFX_QBO_STR = "QFX_QBO";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.ExportFormats
 * JD-Core Version:    0.7.0.1
 */