package com.ffusion.beans.messages;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.Filterable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.Collator;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MessageThread
  extends ExtendABean
  implements Filterable, Serializable
{
  public static final String MESSAGE_ALL_CODE = "0";
  public static final String MESSAGE_NEW_CODE = "1";
  public static final String MESSAGE_OPEN_CODE = "2";
  public static final String MESSAGE_IQUERY_CODE = "3";
  public static final String MESSAGE_CQUERY_CODE = "4";
  public static final String MESSAGE_CLOSED_CODE = "5";
  public static final String MESSAGE_NOTES_CODE = "6";
  public static final String MESSAGE_AUTOREPLY_CODE = "7";
  public static final String MESSAGE_ALERT_CODE = "8";
  public static final String MESSAGE_PENDING_CODE = "9";
  public static final String MESSAGE_DENIED_CODE = "10";
  public static final String MESSAGE_APPROVED_CODE = "11";
  public static final String MESSAGE_PENDING_WITH_EMAIL_CODE = "12";
  public static final String MESSAGE_ALL = "MESSAGE_ALL";
  public static final String MESSAGE_NO_STATUS = "MESSAGE_NO_STATUS";
  public static final String MESSAGE_NEW = "MESSAGE_NEW";
  public static final String MESSAGE_OPEN = "MESSAGE_OPEN";
  public static final String MESSAGE_IQUERY = "MESSAGE_IQUERY";
  public static final String MESSAGE_CQUERY = "MESSAGE_CQUERY";
  public static final String MESSAGE_CLOSED = "MESSAGE_CLOSED";
  public static final String MESSAGE_NOTES = "MESSAGE_NOTES";
  public static final String MESSAGE_AUTOREPLY = "MESSAGE_AUTOREPLY";
  public static final String MESSAGE_PENDING = "MESSAGE_PENDING";
  public static final String MESSAGE_ALERT = "MESSAGE_ALERT";
  public static final String MESSAGE_GLOBAL = "MESSAGE_GLOBAL";
  public static final String MESSAGE_DENIED = "MESSAGE_DENIED";
  public static final String MESSAGE_APPROVED = "MESSAGE_APPROVED";
  public static final String MESSAGE_UNDEFINED_STATUS = "MESSAGE_UNDEFINED_STATUS";
  public static final String EXTRA_KEY_RETRIEVE_FOR = "RETRIEVE_FOR";
  public static final String EXTRA_VALUE_TEAM = "TEAM";
  public static final String EXTRA_VALUE_INDIVIDUAL = "INDIVIDUAL";
  static final String sY = "com.ffusion.beans.messages.resources";
  public static final String ThreadID = "ThreadID";
  public static final String QueueID = "QueueID";
  public static final String EmployeeID = "EmployeeID";
  public static final String EmployeeName = "EmployeeName";
  public static final String DirectoryID = "DirectoryID";
  public static final String DirectoryName = "DirectoryName";
  public static final String Subject = "Subject";
  public static final String ThreadStatus = "ThreadStatus";
  public static final String ThreadStatusName = "ThreadStatusName";
  public static final String NewThreadStatus = "NewThreadStatus";
  public static final String CreateDate = "CreateDate";
  public static final String AgeInDays = "AgeInDays";
  public static final String ReadDate = "ReadDate";
  public static final String ClosedDate = "ClosedDate";
  private String sQ;
  private String sZ;
  private String sT;
  private String s1;
  private String s2;
  private String s4;
  private String s0;
  private String sX;
  private String sP;
  private String sU;
  private String sW;
  private DateTime sR;
  private DateTime sV;
  private DateTime sO;
  private Messages sS;
  private String s3;
  
  public MessageThread() {}
  
  public MessageThread(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.sR != null) {
      this.sR.setLocale(paramLocale);
    }
    if (this.sV != null) {
      this.sV.setLocale(paramLocale);
    }
    if (this.sO != null) {
      this.sO.setLocale(paramLocale);
    }
    if (this.sS != null) {
      this.sS.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.sR != null) {
      this.sR.setFormat(paramString);
    }
    if (this.sV != null) {
      this.sV.setFormat(paramString);
    }
    if (this.sO != null) {
      this.sO.setFormat(paramString);
    }
    if (this.sS != null) {
      this.sS.setDateFormat(paramString);
    }
  }
  
  public String getThreadID()
  {
    return this.sQ;
  }
  
  public String getQueueID()
  {
    return this.sZ;
  }
  
  public String getQueueName()
  {
    return this.sT;
  }
  
  public String getEmployeeID()
  {
    return this.s1;
  }
  
  public String getEmployeeName()
  {
    return this.s2;
  }
  
  public String getDirectoryID()
  {
    return this.s4;
  }
  
  public String getDirectoryName()
  {
    return this.s0;
  }
  
  public String getDirectoryEmail()
  {
    return this.sX;
  }
  
  public String getSubject()
  {
    return this.sP;
  }
  
  public String getThreadStatus()
  {
    return this.sU;
  }
  
  public String getThreadStatusName()
  {
    String str = "";
    if (this.sU == null) {
      str = ResourceUtil.getString("MESSAGE_NO_STATUS", "com.ffusion.beans.messages.resources", this.locale);
    } else if (this.sU.equals("1")) {
      str = ResourceUtil.getString("MESSAGE_NEW", "com.ffusion.beans.messages.resources", this.locale);
    } else if (this.sU.equals("2")) {
      str = ResourceUtil.getString("MESSAGE_OPEN", "com.ffusion.beans.messages.resources", this.locale);
    } else if (this.sU.equals("3")) {
      str = ResourceUtil.getString("MESSAGE_IQUERY", "com.ffusion.beans.messages.resources", this.locale);
    } else if (this.sU.equals("4")) {
      str = ResourceUtil.getString("MESSAGE_CQUERY", "com.ffusion.beans.messages.resources", this.locale);
    } else if (this.sU.equals("5")) {
      str = ResourceUtil.getString("MESSAGE_CLOSED", "com.ffusion.beans.messages.resources", this.locale);
    } else if (this.sU.equals("10")) {
      str = ResourceUtil.getString("MESSAGE_DENIED", "com.ffusion.beans.messages.resources", this.locale);
    } else if ((this.sU.equals("9")) || (this.sU.equals("12"))) {
      str = ResourceUtil.getString("MESSAGE_PENDING", "com.ffusion.beans.messages.resources", this.locale);
    } else if (this.sU.equals("11")) {
      str = ResourceUtil.getString("MESSAGE_APPROVED", "com.ffusion.beans.messages.resources", this.locale);
    } else {
      str = ResourceUtil.getString("MESSAGE_UNDEFINED_STATUS", "com.ffusion.beans.messages.resources", this.locale);
    }
    if (str == null) {
      return "";
    }
    return str;
  }
  
  public String getNewThreadStatus()
  {
    return this.sW;
  }
  
  public String getCreateDate()
  {
    if (this.sR != null)
    {
      this.sR.setFormat(this.datetype);
      return this.sR.toString();
    }
    return null;
  }
  
  public DateTime getCreateDateValue()
  {
    return this.sR;
  }
  
  public Timestamp getCreateDateAsTimestamp()
  {
    return new Timestamp(this.sR.getTime().getTime());
  }
  
  public String getCreateDateHourofDay()
  {
    return String.valueOf(this.sR.get(11));
  }
  
  public String getCreateDateMinute()
  {
    return String.valueOf(this.sR.get(12));
  }
  
  public String getReadDate()
  {
    if (this.sV != null)
    {
      this.sV.setFormat(this.datetype);
      return this.sV.toString();
    }
    return null;
  }
  
  public String getReadDateInMillis()
  {
    String str = "";
    if (this.sV != null)
    {
      Date localDate = this.sV.getTime();
      long l = localDate.getTime();
      str = Long.toString(l);
    }
    return str;
  }
  
  public DateTime getReadDateValue()
  {
    return this.sV;
  }
  
  public Timestamp getReadDateAsTimestamp()
  {
    return new Timestamp(this.sV.getTime().getTime());
  }
  
  public String getClosedDate()
  {
    if (this.sO != null)
    {
      this.sO.setFormat(this.datetype);
      return this.sO.toString();
    }
    return null;
  }
  
  public DateTime getClosedDateValue()
  {
    return this.sO;
  }
  
  public Timestamp getClosedDateAsTimestamp()
  {
    return new Timestamp(this.sO.getTime().getTime());
  }
  
  public Messages getMessages()
  {
    return this.sS;
  }
  
  public String getCaseNum()
  {
    return this.s3;
  }
  
  public String getAgeInDays()
  {
    return Long.toString(getAgeInDaysValue());
  }
  
  public long getAgeInDaysValue()
  {
    float f = this.sR.getDiff(Calendar.getInstance(), 3) * -1.0F;
    return f;
  }
  
  public void setThreadID(String paramString)
  {
    this.sQ = paramString;
  }
  
  public void setQueueID(String paramString)
  {
    this.sZ = paramString;
  }
  
  public void setQueueName(String paramString)
  {
    this.sT = paramString;
  }
  
  public void setEmployeeID(String paramString)
  {
    this.s1 = paramString;
  }
  
  public void setEmployeeName(String paramString)
  {
    this.s2 = paramString;
  }
  
  public void setDirectoryID(String paramString)
  {
    this.s4 = paramString;
  }
  
  public void setDirectoryName(String paramString)
  {
    this.s0 = paramString;
  }
  
  public void setDirectoryEmail(String paramString)
  {
    this.sX = paramString;
  }
  
  public void setSubject(String paramString)
  {
    this.sP = paramString;
  }
  
  public void setThreadStatus(String paramString)
  {
    this.sU = paramString;
  }
  
  public void setNewThreadStatus(String paramString)
  {
    this.sW = paramString;
  }
  
  public void setCreateDate(String paramString)
  {
    try
    {
      if (this.sR == null) {
        this.sR = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.sR.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setCreateDate(DateTime paramDateTime)
  {
    this.sR = paramDateTime;
  }
  
  public void setCreateDate(Date paramDate)
  {
    if (this.sR == null) {
      this.sR = new DateTime(this.locale);
    }
    this.sR.setTime(paramDate);
  }
  
  public void setCreateDate(Timestamp paramTimestamp)
  {
    if (this.sR == null) {
      this.sR = new DateTime(this.locale);
    }
    this.sR.setTime(new Date(paramTimestamp.getTime()));
  }
  
  public void setReadDate(String paramString)
  {
    try
    {
      if (this.sV == null) {
        this.sV = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.sV.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setReadDate(DateTime paramDateTime)
  {
    this.sV = paramDateTime;
  }
  
  public void setReadDate(Date paramDate)
  {
    if (this.sV == null) {
      this.sV = new DateTime(this.locale);
    }
    this.sV.setTime(paramDate);
  }
  
  public void setReadDate(Timestamp paramTimestamp)
  {
    if (this.sV == null) {
      this.sV = new DateTime(this.locale);
    }
    this.sV.setTime(new Date(paramTimestamp.getTime()));
  }
  
  public void setClosedDate(String paramString)
  {
    try
    {
      if (this.sO == null) {
        this.sO = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.sO.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setClosedDate(DateTime paramDateTime)
  {
    this.sO = paramDateTime;
  }
  
  public void setClosedDate(Date paramDate)
  {
    if (this.sO == null) {
      this.sO = new DateTime(this.locale);
    }
    this.sO.setTime(paramDate);
  }
  
  public void setClosedDate(Timestamp paramTimestamp)
  {
    if (this.sO == null) {
      this.sO = new DateTime(this.locale);
    }
    this.sO.setTime(new Date(paramTimestamp.getTime()));
  }
  
  public float getMinutesToOpen()
  {
    if ((this.sR != null) && (this.sV != null)) {
      return this.sR.getDiff(this.sV, 1);
    }
    return -1.0F;
  }
  
  public float getMinutesToClose()
  {
    if ((this.sV != null) && (this.sO != null)) {
      return this.sV.getDiff(this.sO, 1);
    }
    return -1.0F;
  }
  
  public void setMessages(Messages paramMessages)
  {
    this.sS = paramMessages;
  }
  
  public void setCaseNum(String paramString)
  {
    this.s3 = paramString;
  }
  
  public void set(MessageThread paramMessageThread)
  {
    setThreadID(paramMessageThread.getThreadID());
    setQueueID(paramMessageThread.getQueueID());
    setQueueName(paramMessageThread.getQueueName());
    setEmployeeID(paramMessageThread.getEmployeeID());
    setEmployeeName(paramMessageThread.getEmployeeName());
    setDirectoryID(paramMessageThread.getDirectoryID());
    setDirectoryName(paramMessageThread.getDirectoryName());
    setDirectoryEmail(paramMessageThread.getDirectoryEmail());
    setSubject(paramMessageThread.getSubject());
    setThreadStatus(paramMessageThread.getThreadStatus());
    setNewThreadStatus(paramMessageThread.getNewThreadStatus());
    setDateFormat(paramMessageThread.getDateFormat());
    setLocale(paramMessageThread.locale);
    if (paramMessageThread.getCreateDateValue() != null) {
      setCreateDate((DateTime)paramMessageThread.getCreateDateValue().clone());
    }
    if (paramMessageThread.getReadDateValue() != null) {
      setReadDate((DateTime)paramMessageThread.getReadDateValue().clone());
    }
    if (paramMessageThread.getClosedDateValue() != null) {
      setClosedDate((DateTime)paramMessageThread.getClosedDateValue().clone());
    }
    if (paramMessageThread.getMessages() != null) {
      setMessages((Messages)paramMessageThread.getMessages().clone());
    }
    setCaseNum(paramMessageThread.getCaseNum());
    super.set(paramMessageThread);
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "ThreadID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    MessageThread localMessageThread = (MessageThread)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if (paramString.equals("ThreadID")) {
      try
      {
        int j = Integer.parseInt(getThreadID());
        int k = Integer.parseInt(localMessageThread.getThreadID());
        if (j > k) {
          i = 1;
        } else if (k > j) {
          i = -1;
        } else {
          i = 0;
        }
      }
      catch (Exception localException)
      {
        i = getThreadID().compareTo(localMessageThread.getThreadID());
      }
    }
    if ((paramString.equals("QueueID")) && (getQueueID() != null) && (localMessageThread.getQueueID() != null)) {
      i = localCollator.compare(getQueueID(), localMessageThread.getQueueID());
    } else if ((paramString.equals("DirectoryID")) && (getDirectoryID() != null) && (localMessageThread.getDirectoryID() != null)) {
      i = localCollator.compare(getDirectoryID(), localMessageThread.getDirectoryID());
    } else if ((paramString.equals("DirectoryName")) && (getDirectoryName() != null) && (localMessageThread.getDirectoryName() != null)) {
      i = localCollator.compare(getDirectoryName().toLowerCase(), localMessageThread.getDirectoryName().toLowerCase());
    } else if ((paramString.equals("EmployeeID")) && (getEmployeeID() != null) && (localMessageThread.getEmployeeID() != null)) {
      i = localCollator.compare(getEmployeeID(), localMessageThread.getEmployeeID());
    } else if ((paramString.equals("EmployeeName")) && (getEmployeeName() != null) && (localMessageThread.getEmployeeName() != null)) {
      i = localCollator.compare(getEmployeeName().toLowerCase(), localMessageThread.getEmployeeName().toLowerCase());
    } else if ((paramString.equals("Subject")) && (getSubject() != null) && (localMessageThread.getSubject() != null)) {
      i = localCollator.compare(getSubject(), localMessageThread.getSubject());
    } else if ((paramString.equals("ThreadStatus")) && (getThreadStatus() != null) && (localMessageThread.getThreadStatus() != null)) {
      i = localCollator.compare(getThreadStatus(), localMessageThread.getThreadStatus());
    } else if ((paramString.equals("ThreadStatusName")) && (getThreadStatusName() != null) && (localMessageThread.getThreadStatusName() != null)) {
      i = localCollator.compare(getThreadStatusName().toLowerCase(), localMessageThread.getThreadStatusName().toLowerCase());
    } else if ((paramString.equals("CreateDate")) && (this.sR != null) && (localMessageThread.sR != null)) {
      i = this.sR.equals(localMessageThread.sR) ? 0 : this.sR.before(localMessageThread.sR) ? -1 : 1;
    } else if ((paramString.equals("AgeInDays")) && (this.sR != null) && (localMessageThread.sR != null)) {
      i = getAgeInDaysValue() == localMessageThread.getAgeInDaysValue() ? 0 : getAgeInDaysValue() < localMessageThread.getAgeInDaysValue() ? -1 : 1;
    } else if ((paramString.equals("ClosedDate")) && (this.sO != null) && (localMessageThread.sO != null)) {
      i = this.sO.equals(localMessageThread.sO) ? 0 : this.sO.before(localMessageThread.sO) ? -1 : 1;
    } else if ((paramString.equals("ReadDate")) && (this.sV != null) && (localMessageThread.sV != null)) {
      i = this.sV.equals(localMessageThread.sV) ? 0 : this.sV.before(localMessageThread.sV) ? -1 : 1;
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equalsIgnoreCase("ThreadID")) && (this.sQ != null)) {
      return isFilterable(this.sQ, paramString2, paramString3);
    }
    if ((paramString1.equalsIgnoreCase("QueueID")) && (this.sZ != null)) {
      return isFilterable(this.sZ, paramString2, paramString3);
    }
    if ((paramString1.equalsIgnoreCase("EmployeeID")) && (this.s1 != null)) {
      return isFilterable(this.s1, paramString2, paramString3);
    }
    if ((paramString1.equalsIgnoreCase("EmployeeName")) && (this.s2 != null)) {
      return isFilterable(this.s2, paramString2, paramString3);
    }
    if ((paramString1.equalsIgnoreCase("DirectoryID")) && (this.s4 != null)) {
      return isFilterable(this.s4, paramString2, paramString3);
    }
    if ((paramString1.equalsIgnoreCase("DirectoryName")) && (this.s0 != null)) {
      return isFilterable(this.s0, paramString2, paramString3);
    }
    if ((paramString1.equalsIgnoreCase("Subject")) && (this.sP != null)) {
      return isFilterable(this.sP, paramString2, paramString3);
    }
    if ((paramString1.equalsIgnoreCase("ThreadStatus")) && (this.sU != null)) {
      return isFilterable(this.sU, paramString2, paramString3);
    }
    if ((paramString1.equalsIgnoreCase("NewThreadStatus")) && (this.sW != null)) {
      return isFilterable(this.sW, paramString2, paramString3);
    }
    if ((paramString1.equalsIgnoreCase("CreateDate")) && (this.sR != null)) {
      return this.sR.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equalsIgnoreCase("ReadDate")) && (this.sV != null)) {
      return this.sV.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equalsIgnoreCase("ClosedDate")) && (this.sO != null)) {
      return this.sO.isFilterable("VALUE" + paramString2 + paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "MESSAGETHREAD");
    XMLHandler.appendTag(localStringBuffer, "THREAD ID", this.sQ);
    XMLHandler.appendTag(localStringBuffer, "QUEUE ID", this.sZ);
    XMLHandler.appendTag(localStringBuffer, "QUEUE NAME", this.sT);
    XMLHandler.appendTag(localStringBuffer, "EMPLOYEE ID", this.s1);
    XMLHandler.appendTag(localStringBuffer, "EMPLOYEE NAME", this.s2);
    XMLHandler.appendTag(localStringBuffer, "DIRECTORY ID", this.s4);
    XMLHandler.appendTag(localStringBuffer, "DIRECTORY NAME", this.s0);
    XMLHandler.appendTag(localStringBuffer, "DIRECTORY EMAIL", this.sX);
    XMLHandler.appendTag(localStringBuffer, "SUBJECT", this.sP);
    XMLHandler.appendTag(localStringBuffer, "THREAD STATUS", this.sU);
    XMLHandler.appendTag(localStringBuffer, "NEW THREAD STATUS", this.sW);
    if (this.sR != null) {
      XMLHandler.appendTag(localStringBuffer, "CREATE DATE", this.sR.toXMLFormat());
    }
    if (this.sV != null) {
      XMLHandler.appendTag(localStringBuffer, "READ DATE", this.sV.toXMLFormat());
    }
    if (this.sO != null) {
      XMLHandler.appendTag(localStringBuffer, "CLOSED DATE", this.sO.toXMLFormat());
    }
    XMLHandler.appendTag(localStringBuffer, "CASE NUM", this.s3);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "MESSAGETHREAD");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.MessageThread
 * JD-Core Version:    0.7.0.1
 */