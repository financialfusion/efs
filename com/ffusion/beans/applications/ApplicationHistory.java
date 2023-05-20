package com.ffusion.beans.applications;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.Filterable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;

public class ApplicationHistory
  extends ExtendABean
  implements Serializable, Filterable
{
  protected String ownerID;
  protected String modifierID;
  protected String productID;
  protected String appID;
  protected String statusID;
  protected String historyComment;
  protected DateTime modifiedDate;
  
  protected ApplicationHistory() {}
  
  public ApplicationHistory(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.datetype = "SHORT";
  }
  
  public void setOwnerID(String paramString)
  {
    this.ownerID = paramString;
  }
  
  public String getOwnerID()
  {
    return this.ownerID;
  }
  
  public void setModifierID(String paramString)
  {
    this.modifierID = paramString;
  }
  
  public String getModifierID()
  {
    return this.modifierID;
  }
  
  public void setProductID(String paramString)
  {
    this.productID = paramString;
  }
  
  public String getProductID()
  {
    return this.productID;
  }
  
  public void setAppID(String paramString)
  {
    this.appID = paramString;
  }
  
  public String getAppID()
  {
    return this.appID;
  }
  
  public void setStatusID(String paramString)
  {
    this.statusID = paramString;
  }
  
  public String getStatusID()
  {
    return this.statusID;
  }
  
  public void setHistoryComment(String paramString)
  {
    this.historyComment = paramString;
  }
  
  public String getHistoryComment()
  {
    return this.historyComment;
  }
  
  public void setModifiedDate(Calendar paramCalendar)
  {
    this.modifiedDate = new DateTime(paramCalendar, this.locale);
  }
  
  public void setModifiedDate(DateTime paramDateTime)
  {
    this.modifiedDate = paramDateTime;
  }
  
  public void setModifiedDateString(String paramString)
  {
    try
    {
      if (this.modifiedDate == null) {
        try
        {
          this.modifiedDate = new DateTime(paramString, this.locale, "MM-dd-yyyy HH:mm:ss");
        }
        catch (Exception localException)
        {
          this.modifiedDate = new DateTime(paramString, this.locale, "MM-dd-yyyy");
        }
      } else {
        this.modifiedDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getModifiedDateString()
  {
    if (this.modifiedDate != null) {
      return this.modifiedDate.toString();
    }
    return null;
  }
  
  public DateTime getModifiedDate()
  {
    return this.modifiedDate;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.modifiedDate != null) {
      this.modifiedDate.setLocale(this.locale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.modifiedDate != null) {
      this.modifiedDate.setFormat(paramString);
    }
  }
  
  public void set(ApplicationHistory paramApplicationHistory)
  {
    super.set(paramApplicationHistory);
    setAppID(paramApplicationHistory.getAppID());
    setModifierID(paramApplicationHistory.getModifierID());
    setOwnerID(paramApplicationHistory.getOwnerID());
    setProductID(paramApplicationHistory.getProductID());
    setStatusID(paramApplicationHistory.getStatusID());
    setHistoryComment(paramApplicationHistory.getHistoryComment());
    if (paramApplicationHistory.getModifiedDate() != null) {
      setModifiedDate((DateTime)paramApplicationHistory.getModifiedDate().clone());
    }
  }
  
  public void setMember(String paramString1, String paramString2)
  {
    if (paramString1.equalsIgnoreCase("OWNER_ID")) {
      setOwnerID(paramString2);
    } else if (paramString1.equalsIgnoreCase("MODIFIER_ID")) {
      setModifierID(paramString2);
    } else if (paramString1.equalsIgnoreCase("PRODUCT_ID")) {
      this.productID = paramString2;
    } else if (paramString1.equalsIgnoreCase("APP_ID")) {
      this.appID = paramString2;
    } else if (paramString1.equalsIgnoreCase("STATUS_ID")) {
      this.statusID = paramString2;
    } else if (paramString1.equalsIgnoreCase("HISTORY_COMMENT")) {
      this.historyComment = paramString2;
    } else if (paramString1.equalsIgnoreCase("MODIFIED_DATE")) {
      setModifiedDateString(paramString2);
    } else {
      super.set(paramString1, paramString2);
    }
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
    XMLHandler.appendBeginTag(localStringBuffer, "HISTORY");
    XMLHandler.appendTag(localStringBuffer, "OWNER_ID", this.ownerID);
    XMLHandler.appendTag(localStringBuffer, "MODIFIER_ID", this.modifierID);
    XMLHandler.appendTag(localStringBuffer, "PRODUCT_ID", this.productID);
    XMLHandler.appendTag(localStringBuffer, "STATUS_ID", this.statusID);
    XMLHandler.appendTag(localStringBuffer, "APP_ID", this.appID);
    XMLHandler.appendTag(localStringBuffer, "HISTORY_COMMENT", this.historyComment);
    XMLHandler.appendTag(localStringBuffer, "MODIFIED_DATE", getModifiedDateString());
    localStringBuffer.append(super.getXML());
    XMLHandler.appendBeginTag(localStringBuffer, "HISTORY");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new ExtendABean.InternalXMLHandler(this), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("STATUS_ID")) && (this.statusID != null)) {
      return isFilterable(this.statusID, paramString2, paramString3);
    }
    if ((paramString1.equals("PRODUCT_ID")) && (this.productID != null)) {
      return isFilterable(this.productID, paramString2, paramString3);
    }
    if ((paramString1.equals("OWNER_ID")) && (this.ownerID != null)) {
      return isFilterable(this.ownerID, paramString2, paramString3);
    }
    if ((paramString1.equals("MODIFIER_ID")) && (this.modifierID != null)) {
      return isFilterable(this.modifierID, paramString2, paramString3);
    }
    if ((paramString1.equals("APP_ID")) && (this.appID != null)) {
      return isFilterable(this.appID, paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ApplicationHistory localApplicationHistory = (ApplicationHistory)paramObject;
    int i = 1;
    if ((paramString.equals("PRODUCT_ID")) && (getProductID() != null) && (localApplicationHistory.getProductID() != null)) {
      i = getProductID().compareTo(localApplicationHistory.getProductID());
    } else if ((paramString.equals("APP_ID")) && (getAppID() != null) && (localApplicationHistory.getAppID() != null)) {
      i = getAppID().compareTo(localApplicationHistory.getAppID());
    } else if ((paramString.equals("OWNER_ID")) && (getOwnerID() != null) && (localApplicationHistory.getOwnerID() != null)) {
      i = getOwnerID().compareTo(localApplicationHistory.getOwnerID());
    } else if ((paramString.equals("MODIFIER_ID")) && (getModifierID() != null) && (localApplicationHistory.getModifierID() != null)) {
      i = getModifierID().compareTo(localApplicationHistory.getModifierID());
    } else if ((paramString.equals("STATUS_ID")) && (getStatusID() != null) && (localApplicationHistory.getStatusID() != null)) {
      i = getStatusID().compareTo(localApplicationHistory.getStatusID());
    } else if ((paramString.equals("MODIFIED_DATE")) && (getModifiedDate() != null) && (localApplicationHistory.getModifiedDate() != null))
    {
      if (getModifiedDate().after(localApplicationHistory.getModifiedDate())) {
        i = 1;
      } else if (getModifiedDate().before(localApplicationHistory.getModifiedDate())) {
        i = -1;
      } else {
        i = 0;
      }
    }
    else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.ApplicationHistory
 * JD-Core Version:    0.7.0.1
 */