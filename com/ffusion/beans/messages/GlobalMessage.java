package com.ffusion.beans.messages;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.FilteredList;
import com.ffusion.util.ResourceUtil;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class GlobalMessage
  extends GlobalMessageI18N
  implements Serializable, GlobalMessageConsts
{
  public static final String TO_GROUP_ID = "TO_GROUP_ID";
  public static final String FROM_ID = "FROM_ID";
  public static final String FROM_ID_NAME = "FROM_ID_NAME";
  public static final String MESSAGE_TEXT = "MESSAGE_TEXT";
  public static final String CREATE_DATE = "CREATE_DATE";
  public static final String STATUS = "STATUS";
  public static final String APPROVED_BY_ID = "APPROVAL_EMPLOYEE_ID";
  public static final String APPROVED_BY_NAME = "APPROVED_BY_NAME";
  public static final String APPROVED_DATE = "APPROVED_DATE";
  public static final String RECORD_TYPE = "RECORD_TYPE";
  public static final String MSG_TYPE = "MSG_TYPE";
  public static final String TO_GROUP_TYPE = "TO_GROUP_TYPE";
  public static final String COLOR = "COLOR";
  public static final String PRIORITY = "PRIORITY";
  public static final String DISPLAY_FROM_DATE = "DISPLAY_FROM_DATE";
  public static final String DISPLAY_TO_DATE = "DISPLAY_TO_DATE";
  public static final String TEMPLATE_NAME = "TEMPLATE_NAME";
  public static final String SEND_NOW = "SEND_NOW";
  public static final String BANKID = "BANKID";
  public static final String AFFILIATEBANKID = "AFFILIATEBANKID";
  private int aoY;
  private int aoE;
  private String aoK;
  private int aoQ;
  private DateTime aoT;
  private int aoU;
  private int aoI;
  private String aoC;
  private DateTime aoF;
  private int aoL;
  private int aoR;
  private int aoX;
  private int aoN;
  private int aoW;
  private DateTime aoV;
  private DateTime aoD;
  private String aoO;
  private boolean aoJ;
  private String aoH;
  private int aoG;
  private String aoM = "en_US";
  private String aoS = "en_US";
  private HashMap aoP;
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.messages.resources";
  public static final String TO_GROUP_NAME_KEY_PREFIX = "ToGroupName_";
  
  public GlobalMessage()
  {
    super("en_US");
    this.aoL = 1;
    this.aoR = 1;
    this.aoX = 0;
    this.aoN = 1;
    this.aoW = 1;
    this.aoJ = true;
    this.aoS = "en_US";
    this.aoP = new HashMap();
  }
  
  public GlobalMessage(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.locale = paramLocale;
    this.aoL = 1;
    this.aoR = 1;
    this.aoX = 0;
    this.aoN = 1;
    this.aoW = 1;
    this.aoJ = true;
    this.aoP = new HashMap();
    if ((paramLocale.getLanguage().equals("")) || (paramLocale.getCountry().equals(""))) {
      throw new IllegalArgumentException();
    }
    this.aoS = (paramLocale.getLanguage() + "_" + paramLocale.getCountry());
  }
  
  public void setToGroupID(int paramInt)
  {
    this.aoY = paramInt;
  }
  
  public void setToGroupID(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      this.aoY = i;
    }
    catch (Exception localException) {}
  }
  
  public int getToGroupIDValue()
  {
    return this.aoY;
  }
  
  public String getToGroupID()
  {
    return String.valueOf(this.aoY);
  }
  
  public void setToGroupName(String paramString)
  {
    if (validStringData(paramString) == true) {
      if (this.aoS != null) {
        setToGroupName(this.aoS, paramString);
      } else {
        super.setToGroupName(paramString);
      }
    }
  }
  
  public void setToGroupName(String paramString1, String paramString2)
  {
    if (paramString1.equals("en_US")) {
      super.setToGroupName(paramString2);
    } else {
      N(paramString1).setToGroupName(paramString2);
    }
  }
  
  public String getToGroupName()
  {
    if (this.aoS == null) {
      return super.getToGroupName();
    }
    return getToGroupName(this.aoS);
  }
  
  public String getToGroupName(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equalsIgnoreCase("en_US")) {
        return super.getToGroupName();
      }
      GlobalMessageI18N localGlobalMessageI18N = (GlobalMessageI18N)this.aoP.get(paramString);
      if (localGlobalMessageI18N == null) {
        return null;
      }
      return localGlobalMessageI18N.getToGroupName();
    }
    return null;
  }
  
  public String getToGroupDisplayName()
  {
    String str1 = getToGroupName();
    if ((str1 == null) || (str1.length() == 0)) {
      return str1;
    }
    String str2 = null;
    try
    {
      String str3;
      if ((this.aoY == 0) || (this.aoX == 4))
      {
        str3 = "ToGroupName_" + str1.replace(' ', '_');
        str2 = ResourceUtil.getString(str3, "com.ffusion.beans.messages.resources", this.locale);
      }
      else if (this.aoX != 4)
      {
        if (this.aoR == 7)
        {
          str3 = str1.substring("Administrators in ".length());
          String str4 = "ToGroupName_" + str1.substring(0, "Administrators in ".length()).replace(' ', '_');
          String str5 = ResourceUtil.getString(str4, "com.ffusion.beans.messages.resources", this.locale);
          str2 = MessageFormat.format(str5, new Object[] { str3 });
        }
        else
        {
          str2 = str1;
        }
      }
    }
    catch (Exception localException) {}
    if ((str2 == null) || (str2.length() == 0)) {
      str2 = str1;
    }
    return str2;
  }
  
  public void setFromID(int paramInt)
  {
    this.aoE = paramInt;
  }
  
  public void setFromID(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      this.aoE = i;
    }
    catch (Exception localException) {}
  }
  
  public int getFromIDValue()
  {
    return this.aoE;
  }
  
  public String getFromID()
  {
    return String.valueOf(this.aoE);
  }
  
  public void setFromIDName(String paramString)
  {
    if (validStringData(paramString) == true) {
      this.aoK = paramString;
    }
  }
  
  public String getFromIDName()
  {
    return this.aoK;
  }
  
  public void setFromName(String paramString)
  {
    if (validStringData(paramString) == true) {
      if (this.aoS != null) {
        setFromName(this.aoS, paramString);
      } else {
        super.setFromName(paramString);
      }
    }
  }
  
  public void setFromName(String paramString1, String paramString2)
  {
    if (paramString1.equals("en_US")) {
      super.setFromName(paramString2);
    } else {
      N(paramString1).setFromName(paramString2);
    }
  }
  
  public String getFromName()
  {
    if (this.aoS == null) {
      return super.getFromName();
    }
    return getFromName(this.aoS);
  }
  
  public String getFromName(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equalsIgnoreCase("en_US")) {
        return super.getFromName();
      }
      GlobalMessageI18N localGlobalMessageI18N = (GlobalMessageI18N)this.aoP.get(paramString);
      if (localGlobalMessageI18N == null) {
        return null;
      }
      return localGlobalMessageI18N.getFromName();
    }
    return null;
  }
  
  public void setSubject(String paramString)
  {
    if (validStringData(paramString) == true) {
      if (this.aoS != null) {
        setSubject(this.aoS, paramString);
      } else {
        super.setSubject(paramString);
      }
    }
  }
  
  public void setSubject(String paramString1, String paramString2)
  {
    if (paramString1.equals("en_US")) {
      super.setSubject(paramString2);
    } else {
      N(paramString1).setSubject(paramString2);
    }
  }
  
  public String getSubject()
  {
    if (this.aoS == null) {
      return super.getSubject();
    }
    return getSubject(this.aoS);
  }
  
  public String getSubject(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equalsIgnoreCase("en_US")) {
        return super.getSubject();
      }
      GlobalMessageI18N localGlobalMessageI18N = (GlobalMessageI18N)this.aoP.get(paramString);
      if (localGlobalMessageI18N == null) {
        return null;
      }
      return localGlobalMessageI18N.getSubject();
    }
    return null;
  }
  
  public void setGlobalMsgBodyID(int paramInt)
  {
    this.aoQ = paramInt;
  }
  
  public void setGlobalMsgBodyID(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      this.aoQ = i;
    }
    catch (Exception localException) {}
  }
  
  public int getGlobalMsgBodyIDValue()
  {
    return this.aoQ;
  }
  
  public String getGlobalMsgBodyID()
  {
    return String.valueOf(this.aoQ);
  }
  
  public void setMsgText(String paramString)
  {
    if (validStringData(paramString) == true) {
      if (this.aoS != null) {
        setMsgText(this.aoS, paramString);
      } else {
        super.setMsgText(paramString);
      }
    }
  }
  
  public void setMsgText(String paramString1, String paramString2)
  {
    if (paramString1.equals("en_US")) {
      super.setMsgText(paramString2);
    } else {
      N(paramString1).setMsgText(paramString2);
    }
  }
  
  public String getMsgText()
  {
    if (this.aoS == null) {
      return super.getMsgText();
    }
    return getMsgText(this.aoS);
  }
  
  public String getMsgText(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equalsIgnoreCase("en_US")) {
        return super.getMsgText();
      }
      GlobalMessageI18N localGlobalMessageI18N = (GlobalMessageI18N)this.aoP.get(paramString);
      if (localGlobalMessageI18N == null) {
        return null;
      }
      return localGlobalMessageI18N.getMsgText();
    }
    return null;
  }
  
  public void setSearchLanguage(String paramString)
  {
    this.aoM = paramString;
  }
  
  public String getSearchLanguage()
  {
    return this.aoM;
  }
  
  private GlobalMessageI18N N(String paramString)
  {
    GlobalMessageI18N localGlobalMessageI18N = (GlobalMessageI18N)this.aoP.get(paramString);
    if (localGlobalMessageI18N == null)
    {
      localGlobalMessageI18N = new GlobalMessageI18N(paramString);
      localGlobalMessageI18N.setGlobalMsgID(getGlobalMsgIDValue());
      this.aoP.put(paramString, localGlobalMessageI18N);
    }
    return localGlobalMessageI18N;
  }
  
  public void setCreateDate(String paramString)
  {
    try
    {
      if (this.aoT == null) {
        this.aoT = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.aoT.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setCreateDate(DateTime paramDateTime)
  {
    this.aoT = paramDateTime;
  }
  
  public void setCreateDate(Date paramDate)
  {
    if (this.aoT == null) {
      this.aoT = new DateTime(this.locale);
    }
    this.aoT.setTime(paramDate);
  }
  
  public void setCreateDate(Timestamp paramTimestamp)
  {
    if (this.aoT == null) {
      this.aoT = new DateTime(this.locale);
    }
    this.aoT.setTime(new Date(paramTimestamp.getTime()));
  }
  
  public String getCreateDateHourofDay()
  {
    return String.valueOf(this.aoT.get(11));
  }
  
  public String getCreateDateMinute()
  {
    return String.valueOf(this.aoT.get(12));
  }
  
  public String getCreateTime()
  {
    return DateFormatUtil.getFormatter("h:mm a").format(this.aoT.getTime());
  }
  
  public String getCreateDate()
  {
    if (this.aoT != null)
    {
      this.aoT.setFormat(this.datetype);
      return this.aoT.toString();
    }
    return null;
  }
  
  public DateTime getCreateDateValue()
  {
    return this.aoT;
  }
  
  public Timestamp getCreateDateAsTimestamp()
  {
    if (this.aoT != null) {
      return new Timestamp(this.aoT.getTime().getTime());
    }
    return null;
  }
  
  public void setStatus(int paramInt)
  {
    this.aoU = paramInt;
  }
  
  public void setStatus(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      this.aoU = i;
    }
    catch (Exception localException) {}
  }
  
  public int getStatusValue()
  {
    return this.aoU;
  }
  
  public String getStatus()
  {
    return String.valueOf(this.aoU);
  }
  
  public void setApprovalEmployeeID(int paramInt)
  {
    this.aoI = paramInt;
  }
  
  public void setApprovalEmployeeID(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      this.aoI = i;
    }
    catch (Exception localException) {}
  }
  
  public int getApprovalEmployeeIDValue()
  {
    return this.aoI;
  }
  
  public String getApprovalEmployeeID()
  {
    return String.valueOf(this.aoI);
  }
  
  public void setApprovedByName(String paramString)
  {
    if (validStringData(paramString) == true) {
      this.aoC = paramString;
    }
  }
  
  public String getApprovedByName()
  {
    return this.aoC;
  }
  
  public void setApprovedDate(String paramString)
  {
    try
    {
      if (this.aoF == null) {
        this.aoF = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.aoF.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setApprovedDate(DateTime paramDateTime)
  {
    this.aoF = paramDateTime;
  }
  
  public void setApprovedDate(Date paramDate)
  {
    if (this.aoF == null) {
      this.aoF = new DateTime(this.locale);
    }
    this.aoF.setTime(paramDate);
  }
  
  public void setApprovedDate(Timestamp paramTimestamp)
  {
    if (this.aoF == null) {
      this.aoF = new DateTime(this.locale);
    }
    this.aoF.setTime(new Date(paramTimestamp.getTime()));
  }
  
  public String getApprovedDateHourofDay()
  {
    return String.valueOf(this.aoF.get(11));
  }
  
  public String getApprovedDateMinute()
  {
    return String.valueOf(this.aoF.get(12));
  }
  
  public String getApprovedTime()
  {
    return DateFormatUtil.getFormatter("h:mm a").format(this.aoF.getTime());
  }
  
  public String getApprovedDate()
  {
    if (this.aoF != null)
    {
      this.aoF.setFormat(this.datetype);
      return this.aoF.toString();
    }
    return null;
  }
  
  public DateTime getApprovedDateValue()
  {
    return this.aoF;
  }
  
  public Timestamp getApprovedDateAsTimestamp()
  {
    if (this.aoF != null) {
      return new Timestamp(this.aoF.getTime().getTime());
    }
    return null;
  }
  
  public void setRecordType(int paramInt)
  {
    this.aoL = paramInt;
  }
  
  public void setRecordType(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      this.aoL = i;
    }
    catch (Exception localException) {}
  }
  
  public int getRecordTypeValue()
  {
    return this.aoL;
  }
  
  public String getRecordType()
  {
    return String.valueOf(this.aoL);
  }
  
  public void setMsgType(int paramInt)
  {
    this.aoR = paramInt;
  }
  
  public void setMsgType(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      this.aoR = i;
    }
    catch (Exception localException) {}
  }
  
  public int getMsgTypeValue()
  {
    return this.aoR;
  }
  
  public String getMsgType()
  {
    return String.valueOf(this.aoR);
  }
  
  public void setToGroupType(int paramInt)
  {
    this.aoX = paramInt;
  }
  
  public void setToGroupType(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      this.aoX = i;
    }
    catch (Exception localException) {}
  }
  
  public int getToGroupTypeValue()
  {
    return this.aoX;
  }
  
  public String getToGroupType()
  {
    return String.valueOf(this.aoX);
  }
  
  public void setColor(int paramInt)
  {
    this.aoN = paramInt;
  }
  
  public void setColor(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      this.aoN = i;
    }
    catch (Exception localException) {}
  }
  
  public int getColorValue()
  {
    return this.aoN;
  }
  
  public String getColor()
  {
    return String.valueOf(this.aoN);
  }
  
  public void setPriority(int paramInt)
  {
    this.aoW = paramInt;
  }
  
  public void setPriority(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      this.aoW = i;
    }
    catch (Exception localException) {}
  }
  
  public int getPriorityValue()
  {
    return this.aoW;
  }
  
  public String getPriority()
  {
    return String.valueOf(this.aoW);
  }
  
  public void setDisplayFromDate(String paramString)
  {
    try
    {
      if (this.aoV == null) {
        this.aoV = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.aoV.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setDisplayFromDate(DateTime paramDateTime)
  {
    this.aoV = paramDateTime;
  }
  
  public void setDisplayFromDate(Date paramDate)
  {
    if (this.aoV == null) {
      this.aoV = new DateTime(this.locale);
    }
    this.aoV.setTime(paramDate);
  }
  
  public void setDisplayFromDate(Timestamp paramTimestamp)
  {
    if (this.aoV == null) {
      this.aoV = new DateTime(this.locale);
    }
    this.aoV.setTime(new Date(paramTimestamp.getTime()));
  }
  
  public String getDisplayFromDateHourofDay()
  {
    return String.valueOf(this.aoV.get(11));
  }
  
  public String getDisplayFromDateMinute()
  {
    return String.valueOf(this.aoV.get(12));
  }
  
  public String getDisplayFromTime()
  {
    return DateFormatUtil.getFormatter("h:mm a").format(this.aoV.getTime());
  }
  
  public String getDisplayFromDate()
  {
    if (this.aoV != null)
    {
      this.aoV.setFormat(this.datetype);
      return this.aoV.toString();
    }
    return null;
  }
  
  public DateTime getDisplayFromDateValue()
  {
    return this.aoV;
  }
  
  public Timestamp getDisplayFromDateAsTimestamp()
  {
    if (this.aoV != null) {
      return new Timestamp(this.aoV.getTime().getTime());
    }
    return null;
  }
  
  public void setDisplayToDate(String paramString)
  {
    try
    {
      if (this.aoD == null) {
        this.aoD = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.aoD.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setDisplayToDate(DateTime paramDateTime)
  {
    this.aoD = paramDateTime;
  }
  
  public void setDisplayToDate(Date paramDate)
  {
    if (this.aoD == null) {
      this.aoD = new DateTime(this.locale);
    }
    this.aoD.setTime(paramDate);
  }
  
  public void setDisplayToDate(Timestamp paramTimestamp)
  {
    if (this.aoD == null) {
      this.aoD = new DateTime(this.locale);
    }
    this.aoD.setTime(new Date(paramTimestamp.getTime()));
  }
  
  public String getDisplayToDateHourofDay()
  {
    return String.valueOf(this.aoD.get(11));
  }
  
  public String getDisplayToDateMinute()
  {
    return String.valueOf(this.aoD.get(12));
  }
  
  public String getDisplayToTime()
  {
    return DateFormatUtil.getFormatter("h:mm a").format(this.aoD.getTime());
  }
  
  public String getDisplayToDate()
  {
    if (this.aoD != null)
    {
      this.aoD.setFormat(this.datetype);
      return this.aoD.toString();
    }
    return null;
  }
  
  public DateTime getDisplayToDateValue()
  {
    return this.aoD;
  }
  
  public Timestamp getDisplayToDateAsTimestamp()
  {
    if (this.aoD != null) {
      return new Timestamp(this.aoD.getTime().getTime());
    }
    return null;
  }
  
  public void setTemplateName(String paramString)
  {
    this.aoO = paramString;
  }
  
  public String getTemplateName()
  {
    return this.aoO;
  }
  
  public void setSendNow(String paramString)
  {
    try
    {
      boolean bool = Boolean.valueOf(paramString).booleanValue();
      this.aoJ = bool;
    }
    catch (Exception localException) {}
  }
  
  public void setSendNow(boolean paramBoolean)
  {
    this.aoJ = paramBoolean;
  }
  
  public String getSendNow()
  {
    if (this.aoJ) {
      return "true";
    }
    return "false";
  }
  
  public boolean getSendNowValue()
  {
    return this.aoJ;
  }
  
  public void setBankId(String paramString)
  {
    this.aoH = paramString;
  }
  
  public String getBankId()
  {
    return this.aoH;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    try
    {
      this.aoG = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public int getFilterCount()
  {
    FilteredList localFilteredList = (FilteredList)get("_filters");
    if (localFilteredList != null) {
      return localFilteredList.size();
    }
    return 0;
  }
  
  public String getAffiliateBankId()
  {
    return Integer.toString(this.aoG);
  }
  
  public void setAffiliateBankId(int paramInt)
  {
    this.aoG = paramInt;
  }
  
  public int getAffiliateBankIdValue()
  {
    return this.aoG;
  }
  
  public void setCurrentLanguage(String paramString)
  {
    if (paramString == null) {
      this.aoS = null;
    } else if (paramString.length() == 0) {
      this.aoS = null;
    } else {
      this.aoS = paramString;
    }
  }
  
  public String getCurrentLanguage()
  {
    return this.aoS;
  }
  
  public void set(GlobalMessage paramGlobalMessage)
  {
    setGlobalMsgID(paramGlobalMessage.getGlobalMsgID());
    setToGroupID(paramGlobalMessage.getToGroupIDValue());
    setToGroupName("en_US", paramGlobalMessage.getToGroupName("en_US"));
    setFromID(paramGlobalMessage.getFromIDValue());
    setFromIDName(paramGlobalMessage.getFromIDName());
    setFromName("en_US", paramGlobalMessage.getFromName("en_US"));
    setSubject("en_US", paramGlobalMessage.getSubject("en_US"));
    setMsgText("en_US", paramGlobalMessage.getMsgText("en_US"));
    setCreateDate(paramGlobalMessage.getCreateDateValue());
    setStatus(paramGlobalMessage.getStatusValue());
    setApprovalEmployeeID(paramGlobalMessage.getApprovalEmployeeIDValue());
    setApprovedByName(paramGlobalMessage.getApprovedByName());
    setApprovedDate(paramGlobalMessage.getApprovedDateValue());
    setRecordType(paramGlobalMessage.getRecordTypeValue());
    setMsgType(paramGlobalMessage.getMsgTypeValue());
    setToGroupType(paramGlobalMessage.getToGroupTypeValue());
    setColor(paramGlobalMessage.getColorValue());
    setPriority(paramGlobalMessage.getPriorityValue());
    setDisplayFromDate(paramGlobalMessage.getDisplayFromDateValue());
    setDisplayToDate(paramGlobalMessage.getDisplayToDateValue());
    setTemplateName(paramGlobalMessage.getTemplateName());
    setSendNow(paramGlobalMessage.getSendNowValue());
    setBankId(paramGlobalMessage.getBankId());
    setAffiliateBankId(paramGlobalMessage.getAffiliateBankId());
    this.aoP.clear();
    Iterator localIterator = paramGlobalMessage.R();
    if (localIterator != null) {
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        setFromName(str, paramGlobalMessage.getFromName(str));
        setMsgText(str, paramGlobalMessage.getMsgText(str));
        setSubject(str, paramGlobalMessage.getSubject(str));
        setToGroupName(str, paramGlobalMessage.getToGroupName(str));
      }
    }
    setSearchLanguage(paramGlobalMessage.getSearchLanguage());
    setCurrentLanguage(paramGlobalMessage.getCurrentLanguage());
  }
  
  public MessageThread getMessageThread()
  {
    MessageThread localMessageThread = new MessageThread(this.locale);
    localMessageThread.setDirectoryID("0");
    localMessageThread.setEmployeeID(getFromID());
    localMessageThread.setEmployeeName(getFromIDName());
    localMessageThread.setSubject(getSubject());
    localMessageThread.setThreadStatus(String.valueOf(5));
    localMessageThread.setQueueID("0");
    return localMessageThread;
  }
  
  public MessageThread getMessageThread(String paramString)
  {
    MessageThread localMessageThread = new MessageThread(this.locale);
    localMessageThread.setDirectoryID("0");
    localMessageThread.setEmployeeID(getFromID());
    localMessageThread.setEmployeeName(getFromIDName());
    localMessageThread.setSubject(getSubject(paramString));
    localMessageThread.setThreadStatus(String.valueOf(5));
    localMessageThread.setQueueID("0");
    return localMessageThread;
  }
  
  public Message getMessage()
  {
    Message localMessage = new Message(this.locale);
    localMessage.setToType(String.valueOf(1));
    localMessage.setFrom(getGlobalMsgIDValue());
    localMessage.setFromType(String.valueOf(3));
    localMessage.setType(9);
    localMessage.setMemo(getMsgText());
    return localMessage;
  }
  
  public static String getMsgTypeString(int paramInt, Locale paramLocale)
  {
    return ResourceUtil.getString("GlobalMsgType" + paramInt, "com.ffusion.beans.messages.resources", paramLocale);
  }
  
  public int compare(Object paramObject, String paramString)
  {
    GlobalMessage localGlobalMessage = (GlobalMessage)paramObject;
    int i = 1;
    String str = paramString;
    paramString = paramString.toUpperCase();
    if (((paramString.equals("CREATE_DATE")) || (paramString.equals("CREATEDATE")) || (paramString.equals("DATE")) || (paramString.equals("CREATE_DATEVALUE")) || (paramString.equals("DATEVALUE"))) && (this.aoT != null) && (localGlobalMessage.aoT != null))
    {
      i = this.aoT.equals(localGlobalMessage.aoT) ? 0 : this.aoT.before(localGlobalMessage.aoT) ? -1 : 1;
    }
    else if (((paramString.equals("APPROVED_DATE")) || (paramString.equals("APPROVED_DATEVALUE"))) && (this.aoF != null) && (localGlobalMessage.aoF != null))
    {
      i = this.aoF.equals(localGlobalMessage.aoF) ? 0 : this.aoF.before(localGlobalMessage.aoF) ? -1 : 1;
    }
    else if (((paramString.equals("DISPLAY_FROM_DATE")) || (paramString.equals("DISPLAY_FROM_DATEVALUE"))) && (this.aoV != null) && (localGlobalMessage.aoV != null))
    {
      i = this.aoV.equals(localGlobalMessage.aoV) ? 0 : this.aoV.before(localGlobalMessage.aoV) ? -1 : 1;
    }
    else if (((paramString.equals("DISPLAY_TO_DATE")) || (paramString.equals("DISPLAY_TO_DATEVALUE"))) && (this.aoD != null) && (localGlobalMessage.aoD != null))
    {
      i = this.aoD.equals(localGlobalMessage.aoD) ? 0 : this.aoD.before(localGlobalMessage.aoD) ? -1 : 1;
    }
    else
    {
      GlobalMessageI18N localGlobalMessageI18N1;
      GlobalMessageI18N localGlobalMessageI18N2;
      if (paramString.equals("GLOBAL_MESSAGE_ID"))
      {
        if (this.aoS.equals("en_US"))
        {
          i = getGlobalMsgIDValue() - localGlobalMessage.getGlobalMsgIDValue();
        }
        else
        {
          localGlobalMessageI18N1 = (GlobalMessageI18N)this.aoP.get(this.aoS);
          localGlobalMessageI18N2 = (GlobalMessageI18N)localGlobalMessage.aoP.get(this.aoS);
          if ((localGlobalMessageI18N1.getGlobalMsgIDValue() != 0) && (localGlobalMessageI18N2.getGlobalMsgIDValue() != 0)) {
            i = localGlobalMessageI18N1.getGlobalMsgIDValue() - localGlobalMessageI18N2.getGlobalMsgIDValue();
          }
        }
      }
      else if (((paramString.equals("TO_GROUP_NAME")) || (paramString.equalsIgnoreCase("ToGroupName"))) && (getToGroupDisplayName() != null) && (localGlobalMessage.getToGroupDisplayName() != null)) {
        i = getToGroupDisplayName().compareTo(localGlobalMessage.getToGroupDisplayName());
      } else if (paramString.equals("SUBJECT"))
      {
        if (this.aoS.equals("en_US"))
        {
          i = getSubject().compareToIgnoreCase(localGlobalMessage.getSubject());
        }
        else
        {
          localGlobalMessageI18N1 = (GlobalMessageI18N)this.aoP.get(this.aoS);
          localGlobalMessageI18N2 = (GlobalMessageI18N)localGlobalMessage.aoP.get(this.aoS);
          if ((localGlobalMessageI18N1.getSubject() != null) && (localGlobalMessageI18N2.getSubject() != null)) {
            i = localGlobalMessageI18N1.getSubject().compareToIgnoreCase(localGlobalMessageI18N2.getSubject());
          }
        }
      }
      else if (((paramString.equals("FROM_ID_NAME")) || (paramString.equalsIgnoreCase("FromIDName"))) && (getFromIDName() != null) && (localGlobalMessage.getFromIDName() != null)) {
        i = getFromIDName().compareTo(localGlobalMessage.getFromIDName());
      } else if (((paramString.equals("TEMPLATE_NAME")) || (paramString.equalsIgnoreCase("TemplateName"))) && (getTemplateName() != null) && (localGlobalMessage.getTemplateName() != null)) {
        i = getTemplateName().compareTo(localGlobalMessage.getTemplateName());
      } else if ((paramString.equals("STATUS")) && (getStatus() != null) && (localGlobalMessage.getStatus() != null)) {
        i = getStatus().compareTo(localGlobalMessage.getStatus());
      } else if (paramString.equals("TO_GROUP_ID")) {
        i = this.aoY - localGlobalMessage.aoY;
      } else if ((paramString.equals("FROM_ID")) && (this.aoE != 0)) {
        i = this.aoE - localGlobalMessage.aoE;
      } else if ((paramString.equals("MSG_TYPE")) && (this.aoR != 0)) {
        i = this.aoR - localGlobalMessage.aoR;
      } else {
        i = super.compare(paramObject, str);
      }
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    String str = paramString1.toUpperCase();
    if ((str.equals("GLOBAL_MESSAGE_ID")) && (getGlobalMsgIDValue() != 0)) {
      return isFilterable(String.valueOf(getGlobalMsgIDValue()), paramString2, paramString3);
    }
    if (str.equals("TO_GROUP_ID")) {
      return isFilterable(String.valueOf(this.aoY), paramString2, paramString3);
    }
    if ((str.equals("FROM_ID")) && (this.aoE != 0)) {
      return isFilterable(String.valueOf(this.aoE), paramString2, paramString3);
    }
    if ((str.equals("MSG_TYPE")) && (this.aoR != 0)) {
      return isFilterable(String.valueOf(this.aoR), paramString2, paramString3);
    }
    if ((str.equals("CREATE_DATE")) && (getCreateDateValue() != null)) {
      return getCreateDateValue().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((str.equals("APPROVED_DATE")) && (getApprovedDateValue() != null)) {
      return getApprovedDateValue().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((str.equals("DISPLAY_FROM_DATE")) && (getDisplayFromDateValue() != null)) {
      return getDisplayFromDateValue().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((str.equals("DISPLAY_TO_DATE")) && (getDisplayToDateValue() != null)) {
      return getDisplayToDateValue().isFilterable("VALUE" + paramString2 + paramString3);
    }
    if (str.equals("SUBJECT")) {
      if (this.aoS == "en_US")
      {
        if (getSubject() != null) {
          return isFilterable(getSubject(), paramString2, paramString3);
        }
      }
      else
      {
        GlobalMessageI18N localGlobalMessageI18N = (GlobalMessageI18N)this.aoP.get(this.aoS);
        return isFilterable(localGlobalMessageI18N.getSubject(), paramString2, paramString3);
      }
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void setDateFormat(String paramString)
  {
    if (this.aoT != null) {
      this.aoT.setFormat(paramString);
    }
    if (this.aoF != null) {
      this.aoF.setFormat(paramString);
    }
    if (this.aoV != null) {
      this.aoV.setFormat(paramString);
    }
    if (this.aoD != null) {
      this.aoD.setFormat(paramString);
    }
    super.setDateFormat(paramString);
  }
  
  private Iterator R()
  {
    if (this.aoP == null) {
      return null;
    }
    return this.aoP.keySet().iterator();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.GlobalMessage
 * JD-Core Version:    0.7.0.1
 */