package com.ffusion.reporting;

public abstract interface ReportHTTPHeaders
{
  public static final String CT_CSV = "application/vnd.ms-excel; charset=UTF-8";
  public static final String CT_TAB = "text/plain; charset=UTF-8";
  public static final String CT_PDF = "application/pdf";
  public static final String CT_BAI2 = "text/plain; charset=UTF-8";
  public static final String CT_TEXT = "text/plain; charset=UTF-8";
  public static final String CT_HTML = "text/html; charset=UTF-8";
  public static final String CT_QIF = "application/text; charset=UTF-8";
  public static final String CT_QFX = "application/vnd.intu.QFX; charset=UTF-8";
  public static final String CT_QBO = "application/vnd.intu.QBO; charset=UTF-8";
  public static final String CT_IIF = "application/binary; charset=UTF-8";
  public static final String CT_OFX = "application/x-ofx; charset=UTF-8";
  public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
  public static final String CD_ATTACHMENT_FILE = "attachment;filename=";
  public static final String CD_INLINE = "inline";
  public static final String CD_INLINE_FILE = "inline;filename=";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.reporting.ReportHTTPHeaders
 * JD-Core Version:    0.7.0.1
 */