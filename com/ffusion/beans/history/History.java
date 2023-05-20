package com.ffusion.beans.history;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.util.Locale;

public class History
  extends ExtendABean
  implements Comparable
{
  static final String fB = "HISTORYINFO";
  public static final String ID = "ID";
  public static final String IDTYPE = "IDTYPE";
  public static final String MODIFIERID = "MODIFIERID";
  public static final String MODIFIERTYPE = "MODIFIERTYPE";
  public static final String MODIFIERNAME = "MODIFIERNAME";
  public static final String DATACHANGED = "DATACHANGED";
  public static final String DATECHANGED = "DATECHANGED";
  public static final String OLDVALUE = "OLDVALUE";
  public static final String NEWVALUE = "NEWVALUE";
  public static final String COMMENT = "COMMENT";
  public static final String TRACKINGID = "TRACKINGID";
  protected String id;
  protected int idType;
  protected String modifierId;
  protected int modifierType;
  protected String modifierName;
  protected String oldValue;
  protected String newValue;
  protected String comment;
  protected String dataChanged;
  protected DateTime dateChanged;
  protected String trackingID;
  protected ILocalizable localizableOldValue;
  protected ILocalizable localizableNewValue;
  protected ILocalizable localizableComment;
  protected ILocalizable localizableDataChanged;
  
  protected History() {}
  
  public History(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.datetype = "MM/dd/yyyy hh:mm:ss";
  }
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setIdType(int paramInt)
  {
    this.idType = paramInt;
  }
  
  public int getIdTypeValue()
  {
    return this.idType;
  }
  
  public String getIdType()
  {
    return String.valueOf(this.idType);
  }
  
  public void setModifierId(String paramString)
  {
    this.modifierId = paramString;
  }
  
  public String getModifierId()
  {
    return this.modifierId;
  }
  
  public void setModifierType(int paramInt)
  {
    this.modifierType = paramInt;
  }
  
  public int getModifierTypeValue()
  {
    return this.modifierType;
  }
  
  public String getModifierType()
  {
    return String.valueOf(this.modifierType);
  }
  
  public void setModifierName(String paramString)
  {
    this.modifierName = paramString;
  }
  
  public String getModifierName()
  {
    return this.modifierName;
  }
  
  public void setDataChanged(String paramString)
  {
    this.dataChanged = paramString;
  }
  
  public void setLocalizableDataChanged(ILocalizable paramILocalizable)
  {
    this.localizableDataChanged = paramILocalizable;
  }
  
  public String getDataChanged()
  {
    if (this.localizableDataChanged != null) {
      return (String)this.localizableDataChanged.localize(this.locale);
    }
    return this.dataChanged;
  }
  
  public void setOldValue(String paramString)
  {
    this.oldValue = paramString;
  }
  
  public void setLocalizableOldValue(ILocalizable paramILocalizable)
  {
    this.localizableOldValue = paramILocalizable;
  }
  
  public String getOldValue()
  {
    if (this.localizableOldValue != null) {
      return (String)this.localizableOldValue.localize(this.locale);
    }
    return this.oldValue;
  }
  
  public void setNewValue(String paramString)
  {
    this.newValue = paramString;
  }
  
  public void setLocalizableNewValue(ILocalizable paramILocalizable)
  {
    this.localizableNewValue = paramILocalizable;
  }
  
  public String getNewValue()
  {
    if (this.localizableNewValue != null) {
      return (String)this.localizableNewValue.localize(this.locale);
    }
    return this.newValue;
  }
  
  public void setComment(String paramString)
  {
    this.comment = paramString;
  }
  
  public void setLocalizableComment(ILocalizable paramILocalizable)
  {
    this.localizableComment = paramILocalizable;
  }
  
  public String getComment()
  {
    if (this.localizableComment != null) {
      return (String)this.localizableComment.localize(this.locale);
    }
    return this.comment;
  }
  
  public void setDateChanged(DateTime paramDateTime)
  {
    this.dateChanged = paramDateTime;
  }
  
  public void setDateChanged(String paramString)
  {
    try
    {
      if (this.dateChanged == null) {
        this.dateChanged = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.dateChanged.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public DateTime getDateChangedValue()
  {
    return this.dateChanged;
  }
  
  public String getDateChanged()
  {
    if (this.dateChanged == null) {
      return null;
    }
    return this.dateChanged.toString();
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.dateChanged != null) {
      this.dateChanged.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.dateChanged != null) {
      this.dateChanged.setFormat(paramString);
    }
  }
  
  public void set(History paramHistory)
  {
    setId(paramHistory.getId());
    setModifierName(paramHistory.getModifierName());
    setModifierId(paramHistory.getModifierId());
    setModifierType(paramHistory.getModifierTypeValue());
    setDataChanged(paramHistory.getDataChanged());
    setOldValue(paramHistory.getOldValue());
    setNewValue(paramHistory.getNewValue());
    setComment(paramHistory.getComment());
    setTrackingID(paramHistory.getTrackingID());
    setLocalizableComment(paramHistory.localizableComment);
    setLocalizableDataChanged(paramHistory.localizableDataChanged);
    setLocalizableNewValue(paramHistory.localizableNewValue);
    setLocalizableOldValue(paramHistory.localizableOldValue);
    if (paramHistory.getDateChangedValue() != null) {
      setDateChanged((DateTime)paramHistory.getDateChangedValue().clone());
    }
    super.set(paramHistory);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ID")) {
      this.id = paramString2;
    } else if (paramString1.equals("MODIFIERID")) {
      this.modifierId = paramString2;
    } else if (paramString1.equals("MODIFIERTYPE")) {
      this.modifierType = Integer.valueOf(paramString2).intValue();
    } else if (paramString1.equals("MODIFIERNAME")) {
      this.modifierName = paramString2;
    } else if (paramString1.equals("DATECHANGED")) {
      setDateChanged(paramString2);
    } else if (paramString1.equals("OLDVALUE")) {
      this.oldValue = paramString2;
    } else if (paramString1.equals("NEWVALUE")) {
      this.newValue = paramString2;
    } else if (paramString1.equals("COMMENT")) {
      this.comment = paramString2;
    } else if (paramString1.equals("DATACHANGED")) {
      this.dataChanged = paramString2;
    } else if (paramString1.equals("TRACKINGID")) {
      this.trackingID = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    History localHistory = (History)paramObject;
    int i;
    if (this == localHistory) {
      i = 0;
    } else if (getDateChangedValue().after(localHistory.getDateChangedValue())) {
      i = 1;
    } else {
      i = -1;
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    History localHistory = (History)paramObject;
    int i = 1;
    if ((paramString.equals("DATECHANGED")) && (getDateChangedValue() != null) && (localHistory.getDateChangedValue() != null))
    {
      if (getDateChangedValue().after(localHistory.getDateChangedValue())) {
        i = 1;
      } else if (getDateChangedValue().before(localHistory.getDateChangedValue())) {
        i = -1;
      } else {
        i = 0;
      }
    }
    else if ((paramString.equals("MODIFIERNAME")) && (getModifierName() != null) && (localHistory.getModifierName() != null)) {
      i = getModifierName().compareTo(localHistory.getModifierName());
    } else if ((paramString.equals("DATACHANGED")) && (getDataChanged() != null) && (localHistory.getDataChanged() != null)) {
      i = getDataChanged().compareTo(localHistory.getDataChanged());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("MODIFIERNAME")) && (getModifierName() != null)) {
      return isFilterable(getModifierName(), paramString2, paramString3);
    }
    if ((paramString1.equals("DATACHANGED")) && (getDataChanged() != null)) {
      return isFilterable(getDataChanged(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "HISTORYINFO");
    XMLHandler.appendTag(localStringBuffer, "ID", this.id);
    XMLHandler.appendTag(localStringBuffer, "MODIFIERID", this.modifierId);
    XMLHandler.appendTag(localStringBuffer, "MODIFIERTYPE", this.modifierType);
    XMLHandler.appendTag(localStringBuffer, "MODIFIERNAME", this.modifierName);
    XMLHandler.appendTag(localStringBuffer, "OLDVALUE", this.oldValue);
    XMLHandler.appendTag(localStringBuffer, "NEWVALUE", this.newValue);
    XMLHandler.appendTag(localStringBuffer, "DATACHANGED", this.dataChanged);
    if (this.dateChanged != null) {
      XMLHandler.appendTag(localStringBuffer, "DATECHANGED", this.dateChanged.toXMLFormat());
    }
    XMLHandler.appendTag(localStringBuffer, "COMMENT", this.comment);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "HISTORYINFO");
    XMLHandler.appendEndTag(localStringBuffer, "TRACKINGID");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.history.History
 * JD-Core Version:    0.7.0.1
 */