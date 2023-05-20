package com.ffusion.beans.messages;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.DateFormatUtil;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class GlobalMessageSearchCriteria
  extends ExtendABean
{
  private Date aWX;
  private Date aWU;
  private ArrayList aWT;
  private int aWV;
  private int aWS = -1;
  private ArrayList aW0;
  private ArrayList aW1;
  private ArrayList aWY;
  private Date aWR;
  private String aWW = "en_US";
  private String aWZ = "MM/dd/yyyy";
  
  public GlobalMessageSearchCriteria() {}
  
  public GlobalMessageSearchCriteria(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setDateFormat(String paramString)
  {
    this.aWZ = paramString;
  }
  
  public void setStatuses(ArrayList paramArrayList)
  {
    this.aWT = paramArrayList;
  }
  
  public void setMsgTypes(ArrayList paramArrayList)
  {
    this.aW0 = paramArrayList;
  }
  
  public Date getCreateDateStart()
  {
    return this.aWX;
  }
  
  public Date getCreateDateEnd()
  {
    return this.aWU;
  }
  
  public ArrayList getStatuses()
  {
    return this.aWT;
  }
  
  public int getRecordType()
  {
    return this.aWV;
  }
  
  public int getApprovalEmployeeID()
  {
    return this.aWS;
  }
  
  public ArrayList getToGroups()
  {
    return this.aW1;
  }
  
  public ArrayList getAffiliateBanks()
  {
    return this.aWY;
  }
  
  public ArrayList getMsgTypes()
  {
    return this.aW0;
  }
  
  public Date getDisplayDate()
  {
    return this.aWR;
  }
  
  public void setCreateDateStart(String paramString)
  {
    try
    {
      DateFormat localDateFormat = DateFormatUtil.getFormatter(this.aWZ + " HH:mm:ss", this.locale);
      paramString = paramString + " 00:00:00";
      this.aWX = localDateFormat.parse(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setCreateDateEnd(String paramString)
  {
    try
    {
      DateFormat localDateFormat = DateFormatUtil.getFormatter(this.aWZ + " HH:mm:ss", this.locale);
      paramString = paramString + " 23:59:59";
      this.aWU = localDateFormat.parse(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setAddStatus(String paramString)
  {
    if (this.aWT == null) {
      this.aWT = new ArrayList();
    }
    this.aWT.add(new Integer(paramString));
  }
  
  public void addStatus(int paramInt)
  {
    if (this.aWT == null) {
      this.aWT = new ArrayList();
    }
    this.aWT.add(new Integer(paramInt));
  }
  
  public void setApprovalEmployeeID(String paramString)
  {
    this.aWS = Integer.parseInt(paramString);
  }
  
  public void setRecordType(String paramString)
  {
    this.aWV = Integer.parseInt(paramString);
  }
  
  public void setRecordType(int paramInt)
  {
    this.aWV = paramInt;
  }
  
  public void setAddMsgTypes(String paramString)
  {
    if (this.aW0 == null) {
      this.aW0 = new ArrayList();
    }
    this.aW0.add(new Integer(paramString));
  }
  
  public void addMsgTypes(int paramInt)
  {
    if (this.aW0 == null) {
      this.aW0 = new ArrayList();
    }
    this.aW0.add(new Integer(paramInt));
  }
  
  public void setDisplayDate(String paramString)
    throws Exception
  {
    DateFormat localDateFormat = DateFormatUtil.getFormatter(this.aWZ, this.locale);
    this.aWR = localDateFormat.parse(paramString);
  }
  
  public void setAddToGroupPair(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ", ");
    int i = 0;
    int j = 0;
    if (localStringTokenizer.hasMoreTokens()) {
      try
      {
        i = Integer.parseInt(localStringTokenizer.nextToken());
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        return;
      }
    }
    if (localStringTokenizer.hasMoreTokens()) {
      try
      {
        j = Integer.parseInt(localStringTokenizer.nextToken());
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        return;
      }
    }
    if (this.aW1 == null) {
      this.aW1 = new ArrayList();
    }
    this.aW1.add(new GlobalMessageToGroup(i, j));
  }
  
  public void setAddAffiliateBank(String paramString)
  {
    if (this.aWY == null) {
      this.aWY = new ArrayList();
    }
    try
    {
      this.aWY.add(new Integer(paramString));
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public void setSearchLanguage(String paramString)
  {
    this.aWW = paramString;
  }
  
  public Timestamp getCreateDateStartAsTimestamp()
  {
    if (this.aWX != null) {
      return new Timestamp(this.aWX.getTime());
    }
    return null;
  }
  
  public Timestamp getCreateDateEndAsTimestamp()
  {
    if (this.aWU != null) {
      return new Timestamp(this.aWU.getTime());
    }
    return null;
  }
  
  public Timestamp getDisplayDateAsTimestamp()
  {
    if (this.aWR != null) {
      return new Timestamp(this.aWR.getTime());
    }
    return null;
  }
  
  public String getSearchLanguage()
  {
    return this.aWW;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.GlobalMessageSearchCriteria
 * JD-Core Version:    0.7.0.1
 */