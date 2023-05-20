package com.ffusion.beans.istatements;

import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.XMLStrings;
import com.ffusion.util.XMLHandler;

public class StatementReport
  extends ExtendABean
  implements XMLStrings
{
  static final String i = "PDFS_IMAGES_COUNT";
  static final String h = "PDFS_NO_IMAGES_COUNT";
  static final String g = "ACTIVE_ISTMT_USER_COUNT";
  static final String f = "LAST_LOGIN_COUNT";
  protected String activeUserCnt;
  protected String lastLoginCnt;
  protected String pdfWithImageCnt;
  protected String pdfNoImageCnt;
  
  public void setActiveUserCnt(String paramString)
  {
    this.activeUserCnt = paramString;
  }
  
  public String getActiveUserCnt()
  {
    return this.activeUserCnt;
  }
  
  public void setLastLoginCnt(String paramString)
  {
    this.lastLoginCnt = paramString;
  }
  
  public String getLastLoginCnt()
  {
    return this.lastLoginCnt;
  }
  
  public void setPDFWithImageCnt(String paramString)
  {
    this.pdfWithImageCnt = paramString;
  }
  
  public String getPDFWithImageCnt()
  {
    return this.pdfWithImageCnt;
  }
  
  public void setPDFNoImageCnt(String paramString)
  {
    this.pdfNoImageCnt = paramString;
  }
  
  public String getPDFNoImageCnt()
  {
    return this.pdfNoImageCnt;
  }
  
  public void set(StatementReport paramStatementReport)
  {
    super.set(paramStatementReport);
    setActiveUserCnt(paramStatementReport.getActiveUserCnt());
    setLastLoginCnt(paramStatementReport.getLastLoginCnt());
    setPDFWithImageCnt(paramStatementReport.getPDFWithImageCnt());
    setPDFNoImageCnt(paramStatementReport.getPDFNoImageCnt());
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "STATEMENT_REPORT");
    XMLHandler.appendTag(localStringBuffer, "ACTIVE_ISTMT_USER_COUNT", this.activeUserCnt);
    XMLHandler.appendTag(localStringBuffer, "LAST_LOGIN_COUNT", this.lastLoginCnt);
    XMLHandler.appendTag(localStringBuffer, "PDFS_IMAGES_COUNT", this.pdfWithImageCnt);
    XMLHandler.appendTag(localStringBuffer, "PDFS_NO_IMAGES_COUNT", this.pdfNoImageCnt);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "STATEMENT_REPORT");
    return localStringBuffer.toString();
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ACTIVE_ISTMT_USER_COUNT")) {
      this.activeUserCnt = paramString2;
    } else if (paramString1.equals("LAST_LOGIN_COUNT")) {
      this.lastLoginCnt = paramString2;
    } else if (paramString1.equals("PDFS_IMAGES_COUNT")) {
      this.pdfWithImageCnt = paramString2;
    } else if (paramString1.equals("PDFS_NO_IMAGES_COUNT")) {
      this.pdfNoImageCnt = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.istatements.StatementReport
 * JD-Core Version:    0.7.0.1
 */