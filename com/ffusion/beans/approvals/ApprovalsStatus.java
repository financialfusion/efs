package com.ffusion.beans.approvals;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.text.Collator;
import java.util.Locale;
import java.util.StringTokenizer;

public class ApprovalsStatus
  extends ExtendABean
{
  private int aZ3;
  private int aZZ;
  private String aZ5;
  private DateTime aZ0;
  private String aZ2;
  private ApprovalsChainItem aZ1;
  private ApprovalsItem aZ4;
  
  public int getApprovingUserID()
  {
    return this.aZ3;
  }
  
  public void setApprovingUserID(int paramInt)
  {
    this.aZ3 = paramInt;
  }
  
  public int getApprovingUserType()
  {
    return this.aZZ;
  }
  
  public void setApprovingUserType(int paramInt)
  {
    this.aZZ = paramInt;
  }
  
  public String getDecision()
  {
    return this.aZ5;
  }
  
  public void setDecision(String paramString)
  {
    this.aZ5 = paramString;
  }
  
  public DateTime getDecisionDate()
  {
    return this.aZ0;
  }
  
  public void setDecisionDate(DateTime paramDateTime)
  {
    this.aZ0 = paramDateTime;
  }
  
  public String getProcessingState()
  {
    return this.aZ2;
  }
  
  public void setProcessingState(String paramString)
  {
    this.aZ2 = paramString;
  }
  
  public ApprovalsChainItem getCurrentChainItem()
  {
    return this.aZ1;
  }
  
  public void setCurrentChainItem(ApprovalsChainItem paramApprovalsChainItem)
  {
    this.aZ1 = paramApprovalsChainItem;
  }
  
  public ApprovalsItem getApprovalItem()
  {
    return this.aZ4;
  }
  
  public void setApprovalItem(ApprovalsItem paramApprovalsItem)
  {
    this.aZ4 = paramApprovalsItem;
  }
  
  public ApprovalsStatus(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.aZ1 != null) {
      this.aZ1.setLocale(paramLocale);
    }
    if (this.aZ4 != null) {
      this.aZ4.setLocale(paramLocale);
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
        return this.aZ5.equals(str2);
      }
      int i;
      if (str1.equalsIgnoreCase("APPROVINGUSERID"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          return false;
        }
        return this.aZ3 == i;
      }
      if (str1.equalsIgnoreCase("APPROVINGUSERTYPE"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          return false;
        }
        return this.aZZ == i;
      }
      if (str1.equalsIgnoreCase("DECISIONDATE")) {
        return this.aZ0.isFilterable(paramString);
      }
      if (str1.equalsIgnoreCase("PROCESSINGSTATE")) {
        return this.aZ2.equals(str2);
      }
      if ((str1.equalsIgnoreCase("ITEMID")) || (str1.equalsIgnoreCase("ITEMTYPE")) || (str1.equalsIgnoreCase("ITEMSUBTYPE")) || (str1.equalsIgnoreCase("SUBMITTINGUSERID"))) {
        return this.aZ4.isFilterable(paramString);
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
    ApprovalsStatus localApprovalsStatus = (ApprovalsStatus)paramObject;
    int i = 1;
    if ((paramString.equals("DECISION")) && (this.aZ5 != null) && (localApprovalsStatus.getDecision() != null))
    {
      Collator localCollator = doGetCollator();
      i = localCollator.compare(this.aZ5, localApprovalsStatus.getDecision());
    }
    else if (paramString.equals("APPROVINGUSERID"))
    {
      i = this.aZ3 - localApprovalsStatus.getApprovingUserID();
    }
    else if (paramString.equals("APPROVINGUSERTYPE"))
    {
      i = this.aZZ - localApprovalsStatus.getApprovingUserType();
    }
    else if ((paramString.equals("DECISIONDATE")) && (this.aZ0 != null) && (localApprovalsStatus.getDecisionDate() != null))
    {
      i = this.aZ0.compare(localApprovalsStatus.getDecisionDate(), "DECISIONDATE");
    }
    else if ((paramString.equals("PROCESSINGSTATE")) && (this.aZ2 != null) && (localApprovalsStatus.getProcessingState() != null))
    {
      i = this.aZ2.compareToIgnoreCase(localApprovalsStatus.getProcessingState());
    }
    else if ((paramString.equals("CURRENTCHAINITEM")) && (this.aZ1 != null) && (localApprovalsStatus.getCurrentChainItem() != null))
    {
      i = this.aZ1.compareTo(localApprovalsStatus.getCurrentChainItem());
    }
    else if ((paramString.equals("APPROVALITEM")) && (this.aZ4 != null) && (localApprovalsStatus.getApprovalItem() != null))
    {
      i = this.aZ4.compareTo(localApprovalsStatus.getApprovalItem());
    }
    else if ((paramString.equals("ITEMID")) && (this.aZ4 != null) && (localApprovalsStatus.getApprovalItem() != null))
    {
      i = this.aZ4.compare(localApprovalsStatus.getApprovalItem(), "ITEMID");
    }
    else if ((paramString.equals("ITEMTYPE")) && (this.aZ4 != null) && (localApprovalsStatus.getApprovalItem() != null))
    {
      i = this.aZ4.compare(localApprovalsStatus.getApprovalItem(), "ITEMTYPE");
    }
    else if ((paramString.equals("ITEMSUBTYPE")) && (this.aZ4 != null) && (localApprovalsStatus.getApprovalItem() != null))
    {
      i = this.aZ4.compare(localApprovalsStatus.getApprovalItem(), "ITEMSUBTYPE");
    }
    else if ((paramString.equals("ITEM")) && (this.aZ4 != null) && (localApprovalsStatus.getApprovalItem() != null))
    {
      i = this.aZ4.compare(localApprovalsStatus.getApprovalItem(), "ITEM");
    }
    else if ((paramString.equals("SUBMITTINGUSERID")) && (this.aZ4 != null) && (localApprovalsStatus.getApprovalItem() != null))
    {
      i = this.aZ4.compare(localApprovalsStatus.getApprovalItem(), "SUBMITTINGUSERID");
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsStatus)) {
      return false;
    }
    ApprovalsStatus localApprovalsStatus = (ApprovalsStatus)paramObject;
    return (areObjectsEqual(this.aZ5, localApprovalsStatus.getDecision())) && (this.aZ3 == localApprovalsStatus.getApprovingUserID()) && (this.aZZ == localApprovalsStatus.getApprovingUserType()) && (areObjectsEqual(this.aZ0, localApprovalsStatus.getDecisionDate())) && (areObjectsEqual(this.aZ2, localApprovalsStatus.getProcessingState())) && (areObjectsEqual(this.aZ1, localApprovalsStatus.getCurrentChainItem())) && (areObjectsEqual(this.aZ4, localApprovalsStatus.getApprovalItem()));
  }
  
  public void set(ApprovalsStatus paramApprovalsStatus)
  {
    super.set(paramApprovalsStatus);
    setDecision(paramApprovalsStatus.getDecision());
    setApprovingUserID(paramApprovalsStatus.getApprovingUserID());
    setApprovingUserType(paramApprovalsStatus.getApprovingUserType());
    setDecisionDate(paramApprovalsStatus.getDecisionDate());
    setProcessingState(paramApprovalsStatus.getProcessingState());
    setCurrentChainItem(paramApprovalsStatus.getCurrentChainItem());
    setApprovalItem(paramApprovalsStatus.getApprovalItem());
    setLocale(paramApprovalsStatus.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("DECISION")) {
        this.aZ5 = paramString2;
      } else if (paramString1.equals("APPROVINGUSERID")) {
        this.aZ3 = Integer.parseInt(paramString2);
      } else if (paramString1.equals("APPROVINGUSERTYPE")) {
        this.aZZ = Integer.parseInt(paramString2);
      } else if (paramString1.equals("DECISIONDATE")) {
        this.aZ0 = new DateTime(paramString2, this.locale);
      } else if (paramString1.equals("PROCESSINGSTATE")) {
        this.aZ2 = paramString2;
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
    XMLHandler.appendBeginTag(localStringBuffer, "APPROVALS_STATUS");
    XMLHandler.appendTag(localStringBuffer, "DECISION", this.aZ5);
    XMLHandler.appendTag(localStringBuffer, "APPROVINGUSERID", this.aZ3);
    XMLHandler.appendTag(localStringBuffer, "APPROVINGUSERTYPE", this.aZZ);
    if (this.aZ0 != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "DECISIONDATE");
      localStringBuffer.append(this.aZ0.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "DECISIONDATE");
    }
    XMLHandler.appendTag(localStringBuffer, "PROCESSINGSTATE", this.aZ2);
    if (this.aZ1 != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "CURRENTCHAINITEM");
      localStringBuffer.append(this.aZ1.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "CURRENTCHAINITEM");
    }
    if (this.aZ4 != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "APPROVALITEM");
      localStringBuffer.append(this.aZ4.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "APPROVALITEM");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "APPROVALS_STATUS");
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
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("approvingUserID=").append(this.aZ3);
    localStringBuffer.append(" approvingUserType=").append(this.aZZ);
    localStringBuffer.append(" decision=").append(this.aZ5);
    localStringBuffer.append(" decisionDate=").append(this.aZ0);
    localStringBuffer.append(" processingState=").append(this.aZ2);
    localStringBuffer.append(" (currentChainItem=").append(this.aZ1).append(")");
    localStringBuffer.append(" (approvalItem=").append(this.aZ4).append(")");
    return localStringBuffer.toString();
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      ApprovalsStatus.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("CURRENTCHAINITEM"))
      {
        if (ApprovalsStatus.this.aZ1 == null) {
          ApprovalsStatus.this.aZ1 = new ApprovalsChainItem(ApprovalsStatus.this.locale);
        }
        ApprovalsStatus.this.aZ1.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("APPROVALITEM"))
      {
        if (ApprovalsStatus.this.aZ4 == null) {
          ApprovalsStatus.this.aZ4 = new ApprovalsItem(ApprovalsStatus.this.locale);
        }
        ApprovalsStatus.this.aZ4.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsStatus
 * JD-Core Version:    0.7.0.1
 */