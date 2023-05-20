package com.ffusion.beans.messages;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import java.sql.Timestamp;
import java.text.Collator;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class Message
  extends ExtendABean
{
  public static final String MESSAGE = "MESSAGE";
  public static final String ID = "ID";
  public static final String THREAD_ID = "THREAD_ID";
  public static final String TYPE = "TYPE";
  public static final String TO = "TO";
  public static final String TO_NAME = "TO_NAME";
  public static final String TO_TYPE = "TO_TYPE";
  public static final String FROM = "FROM";
  public static final String FROM_NAME = "FROM_NAME";
  public static final String FROM_TYPE = "FROM_TYPE";
  public static final String SUBJECT = "SUBJECT";
  public static final String CREATE_DATE = "CREATE_DATE";
  public static final String READ_DATE = "READ_DATE";
  public static final String ARCHIVED_DATE = "ARCHIVED_DATE";
  public static final String CASE_NUM = "CASE_NUM";
  public static final String MEMO = "MEMO";
  public static final String COMMENT = "COMMENT";
  public static final int DEFAULT_SUBJECT_TRUNCATION_LENGTH = 30;
  protected String id;
  protected String msgThreadID;
  protected String msgThreadStatus;
  protected int type = 1;
  protected String to;
  protected String toName;
  protected String toType;
  protected String from;
  protected String fromName;
  protected String fromType;
  protected String subject;
  protected int subjectTruncationLength = 30;
  protected DateTime createDate;
  protected DateTime readDate;
  protected DateTime archivedDate;
  protected String caseNum;
  protected String memo;
  protected String comment;
  private String fi = "\n";
  
  public Message() {}
  
  public Message(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
  
  public String getID()
  {
    return this.id;
  }
  
  public void setMsgThreadID(String paramString)
  {
    this.msgThreadID = paramString;
  }
  
  public String getMsgThreadID()
  {
    return this.msgThreadID;
  }
  
  public String getMsgThreadStatus()
  {
    return this.msgThreadStatus;
  }
  
  public void setMsgThreadStatus(String paramString)
  {
    this.msgThreadStatus = paramString;
  }
  
  public void setType(String paramString)
  {
    this.type = Integer.parseInt(paramString);
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public String getType()
  {
    String str1 = "";
    try
    {
      String str2 = "MESSAGE_UNDEFINED_STATUS";
      if (this.type == 1) {
        str2 = "MESSAGE_NEW";
      } else if (this.type == 3) {
        str2 = "MESSAGE_IQUERY";
      } else if (this.type == 4) {
        str2 = "MESSAGE_CQUERY";
      } else if (this.type == 5) {
        str2 = "MESSAGE_CLOSED";
      } else if (this.type == 7) {
        str2 = "MESSAGE_AUTOREPLY";
      } else if (this.type == 8) {
        str2 = "MESSAGE_ALERT";
      } else if (this.type == 9) {
        str2 = "MESSAGE_GLOBAL";
      } else if (this.type == 10) {
        str2 = "MESSAGE_DENIED";
      } else if (this.type == 11) {
        str2 = "MESSAGE_APPROVED";
      } else {
        str2 = "MESSAGE_UNDEFINED_STATUS";
      }
      str1 = ResourceUtil.getString(str2, "com.ffusion.beans.messages.resources", this.locale);
    }
    catch (Exception localException)
    {
      str1 = ResourceUtil.getString("MESSAGE_NO_STATUS", "com.ffusion.beans.messages.resources", this.locale);
    }
    return str1;
  }
  
  public int getTypeValue()
  {
    return this.type;
  }
  
  public void setTo(String paramString)
  {
    this.to = paramString;
  }
  
  public void setTo(int paramInt)
  {
    this.to = String.valueOf(paramInt);
  }
  
  public String getTo()
  {
    return this.to;
  }
  
  public int getToValue()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(this.to);
    }
    catch (NumberFormatException localNumberFormatException) {}
    return i;
  }
  
  public void setToName(String paramString)
  {
    this.toName = paramString;
  }
  
  public String getToName()
  {
    return this.toName;
  }
  
  public void setToType(String paramString)
  {
    if (paramString.compareTo("EMPLOYEE") == 0) {
      this.toType = String.valueOf(0);
    } else if (paramString.compareTo("CUSTOMER") == 0) {
      this.toType = String.valueOf(1);
    } else if (paramString.compareTo("QUEUE") == 0) {
      this.toType = String.valueOf(2);
    } else {
      this.toType = paramString;
    }
  }
  
  public String getToType()
  {
    return this.toType;
  }
  
  public int getToTypeValue()
  {
    int i = -1;
    try
    {
      i = Integer.parseInt(this.toType);
    }
    catch (NumberFormatException localNumberFormatException) {}
    return i;
  }
  
  public void setFrom(String paramString)
  {
    this.from = paramString;
  }
  
  public void setFrom(int paramInt)
  {
    this.from = String.valueOf(paramInt);
  }
  
  public String getFrom()
  {
    return this.from;
  }
  
  public int getFromValue()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(this.from);
    }
    catch (NumberFormatException localNumberFormatException) {}
    return i;
  }
  
  public void setFromName(String paramString)
  {
    this.fromName = paramString;
  }
  
  public String getFromName()
  {
    return this.fromName;
  }
  
  public void setFromType(String paramString)
  {
    if (paramString.compareTo("EMPLOYEE") == 0) {
      this.fromType = String.valueOf(0);
    } else if (paramString.compareTo("CUSTOMER") == 0) {
      this.fromType = String.valueOf(1);
    } else if (paramString.compareTo("QUEUE") == 0) {
      this.fromType = String.valueOf(2);
    } else {
      this.fromType = paramString;
    }
  }
  
  public String getFromType()
  {
    return this.fromType;
  }
  
  public int getFromTypeValue()
  {
    int i = -1;
    try
    {
      i = Integer.parseInt(this.fromType);
    }
    catch (NumberFormatException localNumberFormatException) {}
    return i;
  }
  
  public String getTruncatedSubject()
  {
    if (this.subject.length() <= this.subjectTruncationLength) {
      return this.subject;
    }
    if (this.subjectTruncationLength <= 3) {
      return "...";
    }
    return this.subject.substring(0, this.subjectTruncationLength - 3) + "...";
  }
  
  public void setSubjectTruncationLength(String paramString)
  {
    try
    {
      this.subjectTruncationLength = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public void setSubject(String paramString)
  {
    this.subject = paramString;
  }
  
  public String getSubject()
  {
    return this.subject;
  }
  
  public void setDate(String paramString)
  {
    setCreateDate(paramString);
  }
  
  public void setDate(DateTime paramDateTime)
  {
    setCreateDate(paramDateTime);
  }
  
  public void setDate(Date paramDate)
  {
    setCreateDate(paramDate);
  }
  
  public DateTime getDateValue()
  {
    return getCreateDateValue();
  }
  
  public String getDate()
  {
    if (this.createDate != null)
    {
      this.createDate.setFormat(this.datetype);
      return this.createDate.toString();
    }
    return null;
  }
  
  public void setCreateDate(String paramString)
  {
    try
    {
      if (this.createDate == null) {
        this.createDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.createDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setCreateDate(DateTime paramDateTime)
  {
    this.createDate = paramDateTime;
  }
  
  public void setCreateDate(Date paramDate)
  {
    if (this.createDate == null) {
      this.createDate = new DateTime(this.locale);
    }
    this.createDate.setTime(paramDate);
  }
  
  public void setCreateDate(Timestamp paramTimestamp)
  {
    if (this.createDate == null) {
      this.createDate = new DateTime(this.locale);
    }
    this.createDate.setTime(new Date(paramTimestamp.getTime()));
  }
  
  public String getCreateDateHourofDay()
  {
    return String.valueOf(this.createDate.get(11));
  }
  
  public String getCreateDateMinute()
  {
    return String.valueOf(this.createDate.get(12));
  }
  
  public String getCreateTime()
  {
    return DateFormatUtil.getFormatter("h:mm a").format(this.createDate.getTime());
  }
  
  public String getCreateDate()
  {
    if (this.createDate != null)
    {
      this.createDate.setFormat(this.datetype);
      return this.createDate.toString();
    }
    return null;
  }
  
  public DateTime getCreateDateValue()
  {
    return this.createDate;
  }
  
  public Timestamp getCreateDateAsTimestamp()
  {
    return new Timestamp(this.createDate.getTime().getTime());
  }
  
  public void setReadDate(String paramString)
  {
    try
    {
      if (this.readDate == null) {
        this.readDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.readDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setReadDate(DateTime paramDateTime)
  {
    this.readDate = paramDateTime;
  }
  
  public void setReadDate(Date paramDate)
  {
    if (this.readDate == null) {
      this.readDate = new DateTime(this.locale);
    }
    this.readDate.setTime(paramDate);
  }
  
  public void setReadDate(Timestamp paramTimestamp)
  {
    if (paramTimestamp == null) {
      return;
    }
    if (this.readDate == null) {
      this.readDate = new DateTime(this.locale);
    }
    this.readDate.setTime(new Date(paramTimestamp.getTime()));
  }
  
  public String getReadDateHourofDay()
  {
    return String.valueOf(this.readDate.get(11));
  }
  
  public String getReadDateMinute()
  {
    return String.valueOf(this.readDate.get(12));
  }
  
  public String getReadTime()
  {
    return DateFormatUtil.getFormatter("h:mm a").format(this.readDate.getTime());
  }
  
  public String getReadDate()
  {
    if (this.readDate != null)
    {
      this.readDate.setFormat(this.datetype);
      return this.readDate.toString();
    }
    return null;
  }
  
  public DateTime getReadDateValue()
  {
    return this.readDate;
  }
  
  public Timestamp getReadDateAsTimestamp()
  {
    return new Timestamp(this.readDate.getTime().getTime());
  }
  
  public void setArchivedDate(String paramString)
  {
    try
    {
      if (this.archivedDate == null) {
        this.archivedDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.archivedDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setArchivedDate(DateTime paramDateTime)
  {
    this.archivedDate = paramDateTime;
  }
  
  public void setArchivedDate(Date paramDate)
  {
    if (this.archivedDate == null) {
      this.archivedDate = new DateTime(this.locale);
    }
    this.archivedDate.setTime(paramDate);
  }
  
  public void setArchivedDate(Timestamp paramTimestamp)
  {
    if (paramTimestamp == null) {
      return;
    }
    if (this.archivedDate == null) {
      this.archivedDate = new DateTime(this.locale);
    }
    this.archivedDate.setTime(new Date(paramTimestamp.getTime()));
  }
  
  public String getArchivedDateHourofDay()
  {
    return String.valueOf(this.archivedDate.get(11));
  }
  
  public String getArchivedDateMinute()
  {
    return String.valueOf(this.archivedDate.get(12));
  }
  
  public String getArchivedTime()
  {
    return DateFormatUtil.getFormatter("h:mm a").format(this.archivedDate.getTime());
  }
  
  public String getArchivedDate()
  {
    if (this.archivedDate != null)
    {
      this.archivedDate.setFormat(this.datetype);
      return this.archivedDate.toString();
    }
    return null;
  }
  
  public DateTime getArchivedDateValue()
  {
    return this.archivedDate;
  }
  
  public Timestamp getArchivedDateAsTimestamp()
  {
    return new Timestamp(this.archivedDate.getTime().getTime());
  }
  
  public void setCaseNum(String paramString)
  {
    this.caseNum = paramString;
  }
  
  public String getCaseNum()
  {
    return this.caseNum;
  }
  
  public void setMemo(String paramString)
  {
    this.memo = paramString;
  }
  
  public String getMemo()
  {
    return this.memo;
  }
  
  public String getMemoWithBreaks()
  {
    if (this.memo == null) {
      return this.memo;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    StringTokenizer localStringTokenizer = new StringTokenizer(this.memo, "\n", false);
    while (localStringTokenizer.hasMoreTokens()) {
      localStringBuffer.append(localStringTokenizer.nextToken()).append("\n<BR>");
    }
    return localStringBuffer.toString();
  }
  
  public String getMemoWithBreaksOnly()
  {
    if (this.memo == null) {
      return this.memo;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    StringTokenizer localStringTokenizer = new StringTokenizer(this.memo, "\n\r", false);
    while (localStringTokenizer.hasMoreTokens()) {
      localStringBuffer.append(localStringTokenizer.nextToken()).append("<BR>");
    }
    return localStringBuffer.toString();
  }
  
  public ArrayList getMemoLines()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.memo != null)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(this.memo, this.fi, false);
      while (localStringTokenizer.hasMoreTokens()) {
        localArrayList.add(localStringTokenizer.nextToken());
      }
    }
    return localArrayList;
  }
  
  public void setComment(String paramString)
  {
    this.comment = paramString;
  }
  
  public String getComment()
  {
    return this.comment;
  }
  
  public ArrayList getCommentLines()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.comment != null)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(this.comment, this.fi, false);
      while (localStringTokenizer.hasMoreTokens()) {
        localArrayList.add(localStringTokenizer.nextToken());
      }
    }
    return localArrayList;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.createDate != null) {
      this.createDate.setLocale(paramLocale);
    }
    if (this.readDate != null) {
      this.readDate.setLocale(paramLocale);
    }
    if (this.archivedDate != null) {
      this.archivedDate.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.createDate != null) {
      this.createDate.setFormat(paramString);
    }
    if (this.readDate != null) {
      this.readDate.setFormat(paramString);
    }
    if (this.archivedDate != null) {
      this.archivedDate.setFormat(paramString);
    }
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "ID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Message localMessage = (Message)paramObject;
    int i = 1;
    String str = paramString;
    Collator localCollator = doGetCollator();
    paramString = paramString.toUpperCase();
    if (((paramString.equals("ID")) || (paramString.equals("MESSAGE_ID"))) && (getID() != null) && (localMessage.getID() != null)) {
      i = localCollator.compare(getID(), localMessage.getID());
    } else if ((paramString.equals("THREAD_ID")) && (getMsgThreadID() != null) && (localMessage.getMsgThreadID() != null)) {
      i = localCollator.compare(getMsgThreadID(), localMessage.getMsgThreadID());
    } else if (((paramString.equals("CREATE_DATE")) || (paramString.equals("DATE")) || (paramString.equals("CREATE_DATEVALUE")) || (paramString.equals("DATEVALUE"))) && (this.createDate != null) && (localMessage.createDate != null)) {
      i = this.createDate.equals(localMessage.createDate) ? 0 : this.createDate.before(localMessage.createDate) ? -1 : 1;
    } else if (((paramString.equals("READ_DATE")) || (paramString.equals("READ_DATEVALUE"))) && (this.readDate != null) && (localMessage.readDate != null)) {
      i = this.readDate.equals(localMessage.readDate) ? 0 : this.readDate.before(localMessage.readDate) ? -1 : 1;
    } else if (((paramString.equals("ARCHIVED_DATE")) || (paramString.equals("ARCHIVED_DATEVALUE"))) && (this.archivedDate != null) && (localMessage.archivedDate != null)) {
      i = this.archivedDate.equals(localMessage.archivedDate) ? 0 : this.archivedDate.before(localMessage.archivedDate) ? -1 : 1;
    } else if ((paramString.equals("FROM")) && (getFrom() != null) && (localMessage.getFrom() != null)) {
      i = localCollator.compare(getFrom(), localMessage.getFrom());
    } else if ((paramString.equals("TO")) && (getTo() != null) && (localMessage.getTo() != null)) {
      i = localCollator.compare(getTo(), localMessage.getTo());
    } else if ((paramString.equals("FROM_TYPE")) && (getFromType() != null) && (localMessage.getFromType() != null)) {
      i = localCollator.compare(getFromType(), localMessage.getFromType());
    } else if ((paramString.equals("TO_TYPE")) && (getToType() != null) && (localMessage.getToType() != null)) {
      i = localCollator.compare(getToType(), localMessage.getToType());
    } else if ((paramString.equals("FROM_NAME")) && (getFromName() != null) && (localMessage.getFromName() != null)) {
      i = localCollator.compare(getFromName().toLowerCase(), localMessage.getFromName().toLowerCase());
    } else if ((paramString.equals("TO_NAME")) && (getToName() != null) && (localMessage.getToName() != null)) {
      i = localCollator.compare(getToName().toLowerCase(), localMessage.getToName().toLowerCase());
    } else if ((paramString.equals("SUBJECT")) && (getSubject() != null) && (localMessage.getSubject() != null)) {
      i = localCollator.compare(getSubject().toLowerCase(), localMessage.getSubject().toLowerCase());
    } else if ((paramString.equals("MEMO")) && (getMemo() != null) && (localMessage.getMemo() != null)) {
      i = localCollator.compare(getMemo().toLowerCase(), localMessage.getMemo().toLowerCase());
    } else if ((paramString.equals("COMMENT")) && (getComment() != null) && (localMessage.getComment() != null)) {
      i = localCollator.compare(getComment().toLowerCase(), localMessage.getComment().toLowerCase());
    } else if ((paramString.equals("CASE_NUM")) && (getCaseNum() != null) && (localMessage.getCaseNum() != null)) {
      i = localCollator.compare(getCaseNum(), localMessage.getCaseNum());
    } else if ((paramString.equals("TYPE")) || (paramString.equals("MSG_TYPE")) || (paramString.equals("MESSAGE_TYPE"))) {
      i = getTypeValue() - localMessage.getTypeValue();
    } else {
      i = super.compare(paramObject, str);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    String str = paramString1.toUpperCase();
    if ((str.equals("ID")) && (this.id != null)) {
      return isFilterable(this.id, paramString2, paramString3);
    }
    if ((str.equals("TO")) && (this.to != null)) {
      return isFilterable(this.to, paramString2, paramString3);
    }
    if ((str.equals("TO_TYPE")) && (this.toType != null)) {
      return isFilterable(this.toType, paramString2, paramString3);
    }
    if ((str.equals("FROM")) && (this.from != null)) {
      return isFilterable(this.from, paramString2, paramString3);
    }
    if ((str.equals("FROM_TYPE")) && (this.fromType != null)) {
      return isFilterable(this.fromType, paramString2, paramString3);
    }
    if ((str.equals("TYPE")) && (this.type != 0)) {
      return isFilterable(String.valueOf(this.type), paramString2, paramString3);
    }
    if ((str.equals("SUBJECT")) && (this.subject != null)) {
      return isFilterable(this.subject, paramString2, paramString3);
    }
    if ((str.equals("CREATE_DATE")) && (getCreateDateValue() != null)) {
      return getCreateDateValue().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((str.equals("DATE")) && (getCreateDateValue() != null)) {
      return getCreateDateValue().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((str.equals("READ_DATE")) && (getReadDateValue() != null)) {
      return getReadDateValue().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((str.equals("ARCHIVED_DATE")) && (getArchivedDateValue() != null)) {
      return getArchivedDateValue().isFilterable("VALUE" + paramString2 + paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ID"))
    {
      this.id = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("THREAD_ID"))
    {
      this.msgThreadID = paramString2;
    }
    else if ((paramString1.equalsIgnoreCase("MESSAGE_TYPE")) || (paramString1.equalsIgnoreCase("TYPE")))
    {
      setType(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("FROM"))
    {
      this.from = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("FROM_TYPE"))
    {
      this.fromType = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("FROM_NAME"))
    {
      this.fromName = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("TO"))
    {
      this.to = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("TO_TYPE"))
    {
      this.toType = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("TO_NAME"))
    {
      this.toName = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("SUBJECT"))
    {
      this.subject = paramString2;
    }
    else if ((paramString1.equalsIgnoreCase("DATE")) || (paramString1.equalsIgnoreCase("CREATE_DATE")))
    {
      if (this.createDate == null)
      {
        this.createDate = new DateTime(this.locale);
        this.createDate.setFormat(this.datetype);
      }
      this.createDate.fromXMLFormat(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("READ_DATE"))
    {
      if (this.readDate == null)
      {
        this.readDate = new DateTime(this.locale);
        this.readDate.setFormat(this.datetype);
      }
      this.readDate.fromXMLFormat(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("ARCHIVED_DATE"))
    {
      if (this.archivedDate == null)
      {
        this.archivedDate = new DateTime(this.locale);
        this.archivedDate.setFormat(this.datetype);
      }
      this.archivedDate.fromXMLFormat(paramString2);
    }
    else if (paramString1.equalsIgnoreCase("MEMO"))
    {
      this.memo = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("CASE_NUM"))
    {
      this.caseNum = paramString2;
    }
    else if (paramString1.equalsIgnoreCase("COMMENT"))
    {
      this.comment = paramString2;
    }
    else
    {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public void set(Message paramMessage)
  {
    setID(paramMessage.getID());
    setMsgThreadID(paramMessage.getMsgThreadID());
    setType(paramMessage.getTypeValue());
    setTo(paramMessage.getTo());
    setToType(paramMessage.getToType());
    setFrom(paramMessage.getFrom());
    setFromType(paramMessage.getFromType());
    setSubject(paramMessage.getSubject());
    setCaseNum(paramMessage.getCaseNum());
    setMemo(paramMessage.getMemo());
    setComment(paramMessage.getComment());
    if (paramMessage.getCreateDateValue() != null) {
      setCreateDate((DateTime)paramMessage.getCreateDateValue().clone());
    } else {
      setCreateDate((DateTime)null);
    }
    if (paramMessage.getReadDateValue() != null) {
      setReadDate((DateTime)paramMessage.getReadDateValue().clone());
    } else {
      setReadDate((DateTime)null);
    }
    if (paramMessage.getArchivedDateValue() != null) {
      setArchivedDate((DateTime)paramMessage.getArchivedDateValue().clone());
    } else {
      setArchivedDate((DateTime)null);
    }
    super.set(paramMessage);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "MESSAGE");
    XMLHandler.appendTag(localStringBuffer, "ID", this.id);
    XMLHandler.appendTag(localStringBuffer, "THREAD_ID", this.msgThreadID);
    XMLHandler.appendTag(localStringBuffer, "MESSAGE_TYPE", this.type);
    XMLHandler.appendTag(localStringBuffer, "TO", this.to);
    XMLHandler.appendTag(localStringBuffer, "TO_NAME", this.toName);
    XMLHandler.appendTag(localStringBuffer, "TO_TYPE", this.toType);
    XMLHandler.appendTag(localStringBuffer, "FROM", this.from);
    XMLHandler.appendTag(localStringBuffer, "FROM_NAME", this.fromName);
    XMLHandler.appendTag(localStringBuffer, "FROM_TYPE", this.fromType);
    XMLHandler.appendTag(localStringBuffer, "SUBJECT", this.subject);
    if (this.createDate != null) {
      XMLHandler.appendTag(localStringBuffer, "CREATE_DATE", this.createDate.toXMLFormat());
    }
    if (this.readDate != null) {
      XMLHandler.appendTag(localStringBuffer, "READ_DATE", this.readDate.toXMLFormat());
    }
    if (this.archivedDate != null) {
      XMLHandler.appendTag(localStringBuffer, "ARCHIVED_DATE", this.archivedDate.toXMLFormat());
    }
    XMLHandler.appendTag(localStringBuffer, "CASE_NUM", this.caseNum);
    XMLHandler.appendTag(localStringBuffer, "MEMO", this.memo);
    XMLHandler.appendTag(localStringBuffer, "COMMENT", this.comment);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "MESSAGE");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.Message
 * JD-Core Version:    0.7.0.1
 */