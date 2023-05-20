package com.ffusion.beans.approvals;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.text.Collator;
import java.util.Locale;
import java.util.StringTokenizer;

public class ApprovalsHistoryRecord
  extends ExtendABean
{
  public static String APPROVALS_HISTORY_RECORD = "APPROVALS_HISTORY_RECORD";
  public static String DECISION = "DECISION";
  public static String APPROVING_USER_ID = "APPROVING_USER_ID";
  public static String APPROVING_USER_TYPE = "APPROVING_USER_TYPE";
  public static String DECISION_DATE = "DECISION_DATE";
  public static String PROCESSING_STATE = "PROCESSING_STATE";
  private String aZ7;
  private int aZ6;
  private int aZ9;
  private DateTime aZ8;
  private String a0a;
  
  public String getDecision()
  {
    return this.aZ7;
  }
  
  public void setDecision(String paramString)
  {
    this.aZ7 = paramString;
  }
  
  public int getApprovingUserID()
  {
    return this.aZ6;
  }
  
  public void setApprovingUserID(int paramInt)
  {
    this.aZ6 = paramInt;
  }
  
  public int getApprovingUserType()
  {
    return this.aZ9;
  }
  
  public void setApprovingUserType(int paramInt)
  {
    this.aZ9 = paramInt;
  }
  
  public DateTime getDecisionDate()
  {
    return this.aZ8;
  }
  
  public void setDecisionDate(DateTime paramDateTime)
  {
    this.aZ8 = paramDateTime;
  }
  
  public String getProcessingState()
  {
    return this.a0a;
  }
  
  public void setProcessingState(String paramString)
  {
    this.a0a = paramString;
  }
  
  public ApprovalsHistoryRecord(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.aZ8 != null) {
      this.aZ8.setLocale(paramLocale);
    }
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      if (str1.equalsIgnoreCase(DECISION)) {
        return this.aZ7.equals(str2);
      }
      int i;
      if (str1.equalsIgnoreCase(APPROVING_USER_ID))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          return false;
        }
        return this.aZ6 == i;
      }
      if (str1.equalsIgnoreCase(APPROVING_USER_TYPE))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          return false;
        }
        return this.aZ9 == i;
      }
      if (str1.equalsIgnoreCase(DECISION_DATE)) {
        return this.aZ8.isFilterable(paramString);
      }
      if (str1.equalsIgnoreCase(PROCESSING_STATE)) {
        return this.a0a.equals(str2);
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, DECISION_DATE);
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ApprovalsHistoryRecord localApprovalsHistoryRecord = (ApprovalsHistoryRecord)paramObject;
    int i = 1;
    Collator localCollator;
    if ((paramString.equals(DECISION)) && (this.aZ7 != null) && (localApprovalsHistoryRecord.getDecision() != null))
    {
      localCollator = doGetCollator();
      i = localCollator.compare(this.aZ7, localApprovalsHistoryRecord.getDecision());
    }
    else if (paramString.equals(APPROVING_USER_ID))
    {
      i = this.aZ6 - localApprovalsHistoryRecord.getApprovingUserID();
    }
    else if (paramString.equals(APPROVING_USER_TYPE))
    {
      i = this.aZ9 - localApprovalsHistoryRecord.getApprovingUserType();
    }
    else if (paramString.equals(DECISION_DATE))
    {
      i = this.aZ8.compare(localApprovalsHistoryRecord.getDecisionDate());
    }
    else if ((paramString.equals(PROCESSING_STATE)) && (this.a0a != null) && (localApprovalsHistoryRecord.getProcessingState() != null))
    {
      localCollator = doGetCollator();
      i = localCollator.compare(this.a0a, localApprovalsHistoryRecord.getProcessingState());
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsHistoryRecord)) {
      return false;
    }
    ApprovalsHistoryRecord localApprovalsHistoryRecord = (ApprovalsHistoryRecord)paramObject;
    return (this.aZ7.equals(localApprovalsHistoryRecord.getDecision())) && (this.aZ6 == localApprovalsHistoryRecord.getApprovingUserID()) && (this.aZ9 == localApprovalsHistoryRecord.getApprovingUserType()) && (this.aZ8.equals(localApprovalsHistoryRecord.getDecisionDate())) && (this.a0a.equals(localApprovalsHistoryRecord.getProcessingState()));
  }
  
  public void set(ApprovalsHistoryRecord paramApprovalsHistoryRecord)
  {
    super.set(paramApprovalsHistoryRecord);
    setDecision(paramApprovalsHistoryRecord.getDecision());
    setApprovingUserID(paramApprovalsHistoryRecord.getApprovingUserID());
    setApprovingUserType(paramApprovalsHistoryRecord.getApprovingUserType());
    setDecisionDate(paramApprovalsHistoryRecord.getDecisionDate());
    setProcessingState(paramApprovalsHistoryRecord.getProcessingState());
    setLocale(paramApprovalsHistoryRecord.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals(DECISION))
      {
        this.aZ7 = paramString2;
      }
      else if (paramString1.equals(APPROVING_USER_ID))
      {
        this.aZ6 = Integer.parseInt(paramString2);
      }
      else if (paramString1.equals(APPROVING_USER_TYPE))
      {
        this.aZ9 = Integer.parseInt(paramString2);
      }
      else if (paramString1.equals(DECISION_DATE))
      {
        if (this.aZ8 == null)
        {
          this.aZ8 = new DateTime(this.locale);
          this.aZ8.setFormat(this.datetype);
        }
        this.aZ8.fromXMLFormat(paramString2);
      }
      else if (paramString1.equals(PROCESSING_STATE))
      {
        this.a0a = paramString2;
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
    XMLHandler.appendBeginTag(localStringBuffer, APPROVALS_HISTORY_RECORD);
    XMLHandler.appendTag(localStringBuffer, DECISION, this.aZ7);
    XMLHandler.appendTag(localStringBuffer, APPROVING_USER_ID, this.aZ6);
    XMLHandler.appendTag(localStringBuffer, APPROVING_USER_TYPE, this.aZ9);
    if (this.aZ8 != null) {
      XMLHandler.appendTag(localStringBuffer, DECISION_DATE, this.aZ8.toXMLFormat());
    }
    XMLHandler.appendTag(localStringBuffer, PROCESSING_STATE, this.a0a);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, APPROVALS_HISTORY_RECORD);
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
      ApprovalsHistoryRecord.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      super.startElement(paramString);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsHistoryRecord
 * JD-Core Version:    0.7.0.1
 */