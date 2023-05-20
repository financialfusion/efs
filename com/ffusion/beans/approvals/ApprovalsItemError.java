package com.ffusion.beans.approvals;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.text.Collator;
import java.util.Locale;
import java.util.StringTokenizer;

public class ApprovalsItemError
  extends ExtendABean
{
  private ApprovalsItem aZX;
  private int aZY;
  private String aZW;
  
  public ApprovalsItem getApprovalItem()
  {
    return this.aZX;
  }
  
  public void setApprovalItem(ApprovalsItem paramApprovalsItem)
  {
    this.aZX = paramApprovalsItem;
  }
  
  public int getErrorCode()
  {
    return this.aZY;
  }
  
  public void setErrorCode(int paramInt)
  {
    this.aZY = paramInt;
    if ((this.aZW == null) || (this.aZW == ""))
    {
      String str = "com.ffusion.tasks.errors";
      this.aZW = ResourceUtil.getString("Error" + this.aZY + "_descr", "com.ffusion.tasks.errors", super.getLocale());
    }
  }
  
  public String getErrorMessage()
  {
    return this.aZW;
  }
  
  public void setErrorMessage(String paramString)
  {
    this.aZW = paramString;
  }
  
  public ApprovalsItemError(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(" (approvalItem=").append(this.aZX).append(")");
    localStringBuffer.append(" errorCode=").append(this.aZY);
    localStringBuffer.append(" errorMessage=").append(this.aZW);
    return localStringBuffer.toString();
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.aZX != null) {
      this.aZX.setLocale(paramLocale);
    }
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      if (str1.equalsIgnoreCase("ERRORMESSAGE")) {
        return this.aZW.equals(str2);
      }
      if (str1.equalsIgnoreCase("ERRORCODE"))
      {
        int i;
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException)
        {
          return false;
        }
        return this.aZY == i;
      }
      if ((str1.equalsIgnoreCase("ITEMID")) || (str1.equalsIgnoreCase("ITEMTYPE")) || (str1.equalsIgnoreCase("ITEMSUBTYPE")) || (str1.equalsIgnoreCase("SUBMITTINGUSERID"))) {
        return this.aZX.isFilterable(paramString);
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "APPROVALITEM");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ApprovalsItemError localApprovalsItemError = (ApprovalsItemError)paramObject;
    int i = 1;
    if ((paramString.equals("ERRORMESSAGE")) && (this.aZW != null) && (localApprovalsItemError.getErrorMessage() != null))
    {
      Collator localCollator = doGetCollator();
      i = localCollator.compare(this.aZW, localApprovalsItemError.getErrorMessage());
    }
    else if (paramString.equals("ERRORCODE"))
    {
      i = this.aZY - localApprovalsItemError.getErrorCode();
    }
    else if ((paramString.equals("APPROVALITEM")) && (this.aZX != null) && (localApprovalsItemError.getApprovalItem() != null))
    {
      i = this.aZX.compareTo(localApprovalsItemError.getApprovalItem());
    }
    else if ((paramString.equals("ITEMID")) && (this.aZX != null) && (localApprovalsItemError.getApprovalItem() != null))
    {
      i = this.aZX.compare(localApprovalsItemError.getApprovalItem(), "ITEMID");
    }
    else if ((paramString.equals("ITEMTYPE")) && (this.aZX != null) && (localApprovalsItemError.getApprovalItem() != null))
    {
      i = this.aZX.compare(localApprovalsItemError.getApprovalItem(), "ITEMTYPE");
    }
    else if ((paramString.equals("ITEMSUBTYPE")) && (this.aZX != null) && (localApprovalsItemError.getApprovalItem() != null))
    {
      i = this.aZX.compare(localApprovalsItemError.getApprovalItem(), "ITEMSUBTYPE");
    }
    else if ((paramString.equals("ITEM")) && (this.aZX != null) && (localApprovalsItemError.getApprovalItem() != null))
    {
      i = this.aZX.compare(localApprovalsItemError.getApprovalItem(), "ITEM");
    }
    else if ((paramString.equals("SUBMITTINGUSERID")) && (this.aZX != null) && (localApprovalsItemError.getApprovalItem() != null))
    {
      i = this.aZX.compare(localApprovalsItemError.getApprovalItem(), "SUBMITTINGUSERID");
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsItemError)) {
      return false;
    }
    ApprovalsItemError localApprovalsItemError = (ApprovalsItemError)paramObject;
    return (areObjectsEqual(this.aZW, localApprovalsItemError.getErrorMessage())) && (areObjectsEqual(this.aZX, localApprovalsItemError.getApprovalItem())) && (this.aZY == localApprovalsItemError.getErrorCode());
  }
  
  public void set(ApprovalsItemError paramApprovalsItemError)
  {
    super.set(paramApprovalsItemError);
    setErrorCode(paramApprovalsItemError.getErrorCode());
    setErrorMessage(paramApprovalsItemError.getErrorMessage());
    setApprovalItem(paramApprovalsItemError.getApprovalItem());
    setLocale(paramApprovalsItemError.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("ERRORMESSAGE")) {
        this.aZW = paramString2;
      } else if (paramString1.equals("ERRORCODE")) {
        this.aZY = Integer.parseInt(paramString2);
      } else {
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
    XMLHandler.appendBeginTag(localStringBuffer, "APPROVALS_ITEM_ERROR");
    XMLHandler.appendTag(localStringBuffer, "ERRORCODE", this.aZY);
    XMLHandler.appendTag(localStringBuffer, "ERRORMESSAGE", this.aZW);
    if (this.aZX != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "APPROVALITEM");
      localStringBuffer.append(this.aZX.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "APPROVALITEM");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "APPROVALS_ITEM_ERROR");
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
      ApprovalsItemError.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("APPROVALITEM"))
      {
        if (ApprovalsItemError.this.aZX == null) {
          ApprovalsItemError.this.aZX = new ApprovalsItem(ApprovalsItemError.this.locale);
        }
        ApprovalsItemError.this.aZX.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsItemError
 * JD-Core Version:    0.7.0.1
 */