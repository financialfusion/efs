package com.ffusion.beans.approvals;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.text.Collator;
import java.util.Locale;
import java.util.StringTokenizer;

public class ApprovalsDecision
  extends ExtendABean
{
  private String aZM;
  private ApprovalsItem aZL;
  
  public String getDecision()
  {
    return this.aZM;
  }
  
  public void setDecision(String paramString)
  {
    this.aZM = paramString;
  }
  
  public ApprovalsItem getApprovalItem()
  {
    return this.aZL;
  }
  
  public void setApprovalItem(ApprovalsItem paramApprovalsItem)
  {
    this.aZL = paramApprovalsItem;
  }
  
  public ApprovalsDecision(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("decision=").append(this.aZM);
    localStringBuffer.append(" (approvalItem=").append(this.aZL).append(")");
    return localStringBuffer.toString();
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.aZL != null) {
      this.aZL.setLocale(paramLocale);
    }
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      if (str1.equalsIgnoreCase("DECISION")) {
        return this.aZM.equals(str2);
      }
      if ((str1.equalsIgnoreCase("ITEMID")) || (str1.equalsIgnoreCase("ITEMTYPE")) || (str1.equalsIgnoreCase("ITEMSUBTYPE")) || (str1.equalsIgnoreCase("SUBMITTINGUSERID"))) {
        return this.aZL.isFilterable(paramString);
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
    ApprovalsDecision localApprovalsDecision = (ApprovalsDecision)paramObject;
    int i = 1;
    if ((paramString.equals("DECISION")) && (this.aZM != null) && (localApprovalsDecision.getDecision() != null))
    {
      Collator localCollator = doGetCollator();
      i = localCollator.compare(this.aZM, localApprovalsDecision.getDecision());
    }
    else if ((paramString.equals("APPROVALITEM")) && (this.aZL != null) && (localApprovalsDecision.getApprovalItem() != null))
    {
      i = this.aZL.compareTo(localApprovalsDecision.getApprovalItem());
    }
    else if ((paramString.equals("ITEMID")) && (this.aZL != null) && (localApprovalsDecision.getApprovalItem() != null))
    {
      i = this.aZL.compare(localApprovalsDecision.getApprovalItem(), "ITEMID");
    }
    else if ((paramString.equals("ITEMTYPE")) && (this.aZL != null) && (localApprovalsDecision.getApprovalItem() != null))
    {
      i = this.aZL.compare(localApprovalsDecision.getApprovalItem(), "ITEMTYPE");
    }
    else if ((paramString.equals("ITEMSUBTYPE")) && (this.aZL != null) && (localApprovalsDecision.getApprovalItem() != null))
    {
      i = this.aZL.compare(localApprovalsDecision.getApprovalItem(), "ITEMSUBTYPE");
    }
    else if ((paramString.equals("ITEM")) && (this.aZL != null) && (localApprovalsDecision.getApprovalItem() != null))
    {
      i = this.aZL.compare(localApprovalsDecision.getApprovalItem(), "ITEM");
    }
    else if ((paramString.equals("SUBMITTINGUSERID")) && (this.aZL != null) && (localApprovalsDecision.getApprovalItem() != null))
    {
      i = this.aZL.compare(localApprovalsDecision.getApprovalItem(), "SUBMITTINGUSERID");
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsDecision)) {
      return false;
    }
    ApprovalsDecision localApprovalsDecision = (ApprovalsDecision)paramObject;
    return (areObjectsEqual(this.aZM, localApprovalsDecision.getDecision())) && (areObjectsEqual(this.aZL, localApprovalsDecision.getApprovalItem()));
  }
  
  public void set(ApprovalsDecision paramApprovalsDecision)
  {
    super.set(paramApprovalsDecision);
    setDecision(paramApprovalsDecision.getDecision());
    setApprovalItem(paramApprovalsDecision.getApprovalItem());
    setLocale(paramApprovalsDecision.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("DECISION")) {
        this.aZM = paramString2;
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
    XMLHandler.appendBeginTag(localStringBuffer, "APPROVALS_DECISION");
    XMLHandler.appendTag(localStringBuffer, "DECISION", this.aZM);
    if (this.aZL != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "APPROVALITEM");
      localStringBuffer.append(this.aZL.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "APPROVALITEM");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "APPROVALS_DECISION");
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
      ApprovalsDecision.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("APPROVALITEM"))
      {
        if (ApprovalsDecision.this.aZL == null) {
          ApprovalsDecision.this.aZL = new ApprovalsItem(ApprovalsDecision.this.locale);
        }
        ApprovalsDecision.this.aZL.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsDecision
 * JD-Core Version:    0.7.0.1
 */