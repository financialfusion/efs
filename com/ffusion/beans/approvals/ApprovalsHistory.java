package com.ffusion.beans.approvals;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.util.Locale;

public class ApprovalsHistory
  extends ExtendABean
{
  public static String APPROVALS_HISTORY = "APPROVALS_HISTORY";
  public static String APPROVAL_ITEM = "APPROVAL_ITEM";
  public static String HISTORY_RECORDS = "HISTORY_RECORDS";
  private ApprovalsItem a0b;
  private ApprovalsHistoryRecords a0c;
  
  public ApprovalsItem getApprovalItem()
  {
    return this.a0b;
  }
  
  public void setApprovalItem(ApprovalsItem paramApprovalsItem)
  {
    this.a0b = paramApprovalsItem;
  }
  
  public ApprovalsHistoryRecords getHistoryRecords()
  {
    return this.a0c;
  }
  
  public void setHistoryRecords(ApprovalsHistoryRecords paramApprovalsHistoryRecords)
  {
    this.a0c = paramApprovalsHistoryRecords;
  }
  
  public ApprovalsHistory(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.a0b != null) {
      this.a0b.setLocale(paramLocale);
    }
    if (this.a0c != null) {
      this.a0c.setLocale(paramLocale);
    }
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, APPROVAL_ITEM);
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ApprovalsHistory localApprovalsHistory = (ApprovalsHistory)paramObject;
    int i = 1;
    if (paramString.equals(APPROVAL_ITEM)) {
      this.a0b.compareTo(localApprovalsHistory.getApprovalItem());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsHistory)) {
      return false;
    }
    ApprovalsHistory localApprovalsHistory = (ApprovalsHistory)paramObject;
    return (this.a0b.equals(localApprovalsHistory.getApprovalItem())) && (this.a0c.equals(localApprovalsHistory.getHistoryRecords()));
  }
  
  public void set(ApprovalsHistory paramApprovalsHistory)
  {
    super.set(paramApprovalsHistory);
    setApprovalItem(paramApprovalsHistory.getApprovalItem());
    setHistoryRecords(paramApprovalsHistory.getHistoryRecords());
    setLocale(paramApprovalsHistory.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals(APPROVAL_ITEM))
      {
        if (this.a0b == null) {
          this.a0b = new ApprovalsItem(this.locale);
        }
        this.a0b.fromXML(paramString2);
      }
      else if (paramString1.equals(HISTORY_RECORDS))
      {
        if (this.a0c == null) {
          this.a0c = new ApprovalsHistoryRecords(this.locale);
        }
        this.a0c.fromXML(paramString2);
      }
      else
      {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception", localException);
    }
    return bool;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, APPROVALS_HISTORY);
    if (this.a0b != null) {
      localStringBuffer.append(this.a0b.toXML());
    }
    if (this.a0c != null) {
      localStringBuffer.append(this.a0c.toXML());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, APPROVALS_HISTORY);
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public boolean areObjectsEqual(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == paramObject2) {
      return true;
    }
    if ((paramObject1 == null) || (paramObject2 == null)) {
      return false;
    }
    return paramObject1.equals(paramObject2);
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      ApprovalsHistory.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals(ApprovalsHistory.APPROVAL_ITEM))
      {
        if (ApprovalsHistory.this.a0b == null) {
          ApprovalsHistory.this.a0b = new ApprovalsItem(ApprovalsHistory.this.locale);
        }
        ApprovalsHistory.this.a0b.continueXMLParsing(getHandler());
      }
      else if (paramString.equals(ApprovalsHistory.HISTORY_RECORDS))
      {
        if (ApprovalsHistory.this.a0c == null) {
          ApprovalsHistory.this.a0c = new ApprovalsHistoryRecords(ApprovalsHistory.this.locale);
        }
        ApprovalsHistory.this.a0c.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsHistory
 * JD-Core Version:    0.7.0.1
 */