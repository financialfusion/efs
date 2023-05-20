package com.ffusion.beans.approvals;

import com.ffusion.beans.XMLStrings;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class ApprovalsHistoryRecords
  extends FilteredList
  implements XMLStrings, XMLable
{
  public static String APPROVALS_HISTORY_RECORDS = "APPROVALS_HISTORY_RECORDS";
  
  public ApprovalsHistoryRecords(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public ApprovalsHistoryRecord add()
  {
    ApprovalsHistoryRecord localApprovalsHistoryRecord = new ApprovalsHistoryRecord(this.locale);
    add(localApprovalsHistoryRecord);
    return localApprovalsHistoryRecord;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsHistoryRecords)) {
      return false;
    }
    ApprovalsHistoryRecords localApprovalsHistoryRecords = (ApprovalsHistoryRecords)paramObject;
    if (size() != localApprovalsHistoryRecords.size()) {
      return false;
    }
    Locale localLocale1 = this.locale;
    Locale localLocale2 = localApprovalsHistoryRecords.locale;
    if (localLocale1 != null)
    {
      if (localLocale2 != null)
      {
        if (!localLocale1.equals(localLocale2)) {
          return false;
        }
      }
      else {
        return false;
      }
    }
    else if (localLocale2 != null) {
      return false;
    }
    return containsAll(localApprovalsHistoryRecords);
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
    XMLHandler.appendBeginTag(localStringBuffer, APPROVALS_HISTORY_RECORDS);
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ApprovalsHistoryRecord)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, APPROVALS_HISTORY_RECORDS);
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals(ApprovalsHistoryRecord.APPROVALS_HISTORY_RECORD))
      {
        ApprovalsHistoryRecord localApprovalsHistoryRecord = new ApprovalsHistoryRecord(ApprovalsHistoryRecords.this.locale);
        ApprovalsHistoryRecords.this.add(localApprovalsHistoryRecord);
        localApprovalsHistoryRecord.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsHistoryRecords
 * JD-Core Version:    0.7.0.1
 */