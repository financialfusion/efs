package com.ffusion.beans.user;

import com.ffusion.util.Filterable;
import com.ffusion.util.Sortable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import com.ffusion.util.beans.ExtendABean;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;

public class ContactPoint
  extends ExtendABean
  implements Sortable, XMLable, Filterable, Serializable
{
  public static final int CONTACT_POINT_TYPE_ALERTS_CENTER = 1;
  public static final int CONTACT_POINT_TYPE_EMAIL = 2;
  public static final String CONTACT_POINT = "ContactPoint";
  public static final String CONTACT_POINT_ID = "ContactPointID";
  public static final String DIRECTORY_ID = "DirectoryID";
  public static final String CONTACT_POINT_NAME = "ContactPointName";
  public static final String CONTACT_POINT_TYPE = "ContactPointType";
  public static final String ADDRESS = "Address";
  public static final String SECURE = "Secure";
  private int jdField_for = -1;
  private int jdField_new = -1;
  private String jdField_int = null;
  private int jdField_if = -1;
  private String jdField_do = null;
  private boolean a = false;
  
  public ContactPoint(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setContactPointID(int paramInt)
  {
    this.jdField_for = paramInt;
  }
  
  public int getContactPointID()
  {
    return this.jdField_for;
  }
  
  public void setDirectoryID(int paramInt)
  {
    this.jdField_new = paramInt;
  }
  
  public int getDirectoryID()
  {
    return this.jdField_new;
  }
  
  public void setName(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getName()
  {
    return this.jdField_int;
  }
  
  public void setContactPointType(int paramInt)
  {
    this.jdField_if = paramInt;
  }
  
  public int getContactPointType()
  {
    return this.jdField_if;
  }
  
  public String getContactPointTypeString()
  {
    return Integer.toString(this.jdField_if);
  }
  
  public void setAddress(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getAddress()
  {
    return this.jdField_do;
  }
  
  public void setSecure(boolean paramBoolean)
  {
    this.a = paramBoolean;
  }
  
  public boolean getSecure()
  {
    return this.a;
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
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ContactPoint");
    XMLHandler.appendTag(localStringBuffer, "ContactPointID", this.jdField_for);
    XMLHandler.appendTag(localStringBuffer, "DirectoryID", this.jdField_new);
    XMLHandler.appendTag(localStringBuffer, "ContactPointName", this.jdField_int);
    XMLHandler.appendTag(localStringBuffer, "ContactPointType", this.jdField_if);
    XMLHandler.appendTag(localStringBuffer, "Address", this.jdField_do);
    XMLHandler.appendTag(localStringBuffer, "Secure", new Boolean(this.a).toString());
    XMLHandler.appendEndTag(localStringBuffer, "ContactPoint");
    return localStringBuffer.toString();
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
  
  public int compare(Object paramObject, String paramString)
  {
    int i = 0;
    Collator localCollator = doGetCollator();
    if (!(paramObject instanceof ContactPoint)) {
      return 0;
    }
    ContactPoint localContactPoint = (ContactPoint)paramObject;
    if (paramString.equals("ContactPointID")) {
      i = this.jdField_for - localContactPoint.getContactPointID();
    } else if (paramString.equals("DirectoryID")) {
      i = this.jdField_new - localContactPoint.getDirectoryID();
    } else if ((paramString.equals("ContactPointName")) && (this.jdField_int != null) && (localContactPoint.getName() != null)) {
      i = localCollator.compare(getName(), localContactPoint.getName());
    } else if (paramString.equals("ContactPointType")) {
      i = this.jdField_if - localContactPoint.getContactPointType();
    } else if ((paramString.equals("Address")) && (this.jdField_do != null) && (localContactPoint.getAddress() != null)) {
      i = localCollator.compare(getAddress(), localContactPoint.getAddress());
    } else if (paramString.equals("Secure"))
    {
      if (this.a == localContactPoint.getSecure()) {
        i = 0;
      } else if (!this.a) {
        i = -1;
      } else {
        i = 1;
      }
    }
    else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  private int jdField_if(String paramString1, String paramString2)
  {
    if (paramString1 == paramString2) {
      return 0;
    }
    if (paramString1 == null) {
      return -1;
    }
    return paramString1.compareTo(paramString2);
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    String str = paramString1.toUpperCase();
    if (str.equals("ContactPointID")) {
      return isFilterable(this.jdField_for, paramString2, paramString3);
    }
    if (str.equals("DirectoryID")) {
      return isFilterable(this.jdField_new, paramString2, paramString3);
    }
    if ((str.equals("ContactPointName")) && (this.jdField_int != null)) {
      return isFilterable(this.jdField_int, paramString2, paramString3);
    }
    if (str.equals("ContactPointType")) {
      return isFilterable(this.jdField_if, paramString2, paramString3);
    }
    if ((str.equals("Address")) && (this.jdField_do != null)) {
      return isFilterable(this.jdField_do, paramString2, paramString3);
    }
    if (str.equals("Secure")) {
      return isFilterable(Boolean.toString(this.a), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof ContactPoint)) {
      return false;
    }
    ContactPoint localContactPoint = (ContactPoint)paramObject;
    if (this.jdField_for != localContactPoint.getContactPointID()) {
      return false;
    }
    if (this.jdField_new != localContactPoint.getDirectoryID()) {
      return false;
    }
    if ((this.jdField_int != localContactPoint.getName()) && ((this.jdField_int == null) || (!this.jdField_int.equals(localContactPoint.getName())))) {
      return false;
    }
    if (this.jdField_if != localContactPoint.getContactPointType()) {
      return false;
    }
    if ((this.jdField_do != localContactPoint.getAddress()) && ((this.jdField_do == null) || (!this.jdField_do.equals(localContactPoint.getAddress())))) {
      return false;
    }
    return this.a == localContactPoint.getSecure();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.user.ContactPoint
 * JD-Core Version:    0.7.0.1
 */