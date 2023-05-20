package com.ffusion.beans.cashcon;

import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean;

public class LocationSearchResult
  extends ExtendABean
  implements Comparable
{
  private int jdField_do = -1;
  private String jdField_for;
  private String jdField_if;
  private String a;
  private String jdField_int;
  
  public String getCompID()
  {
    return this.jdField_int;
  }
  
  public void setCompID(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public int getDivisionID()
  {
    return this.jdField_do;
  }
  
  public void setDivisionID(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public String getLocationID()
  {
    return this.jdField_if;
  }
  
  public void setLocationID(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getLocationName()
  {
    return this.jdField_for;
  }
  
  public void setLocationName(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getLocationBPWID()
  {
    return this.a;
  }
  
  public void setLocationBPWID(String paramString)
  {
    this.a = paramString;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "LOCATION_BPWID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    LocationSearchResult localLocationSearchResult = (LocationSearchResult)paramObject;
    int i = 1;
    String str1;
    String str2;
    if (paramString.equals("LOCATION_NAME"))
    {
      str1 = this.jdField_for;
      str2 = localLocationSearchResult.getLocationName();
      compareStrings(str1, str2);
    }
    else if (paramString.equals("LOCATION_ID"))
    {
      str1 = this.jdField_if;
      str2 = localLocationSearchResult.getLocationID();
      compareStrings(str1, str2);
    }
    else if (paramString.equals("LOCATION_BPWID"))
    {
      str1 = this.a;
      str2 = localLocationSearchResult.getLocationBPWID();
      compareStrings(str1, str2);
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof LocationSearchResult)) {
      return false;
    }
    if (compare(paramObject, "LOCATION_NAME") != 0) {
      return false;
    }
    if (compare(paramObject, "LOCATION_ID") != 0) {
      return false;
    }
    return compare(paramObject, "LOCATION_BPWID") == 0;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("LOCATION_NAME")) {
        this.jdField_for = paramString2;
      } else if (paramString1.equals("LOCATION_ID")) {
        this.jdField_if = paramString2;
      } else if (paramString1.equals("LOCATION_BPWID")) {
        this.a = paramString2;
      } else {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException) {}
    return bool;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("locationName=").append(this.jdField_for);
    localStringBuffer.append(" locationID=").append(this.jdField_if);
    localStringBuffer.append(" locationBPWID=").append(this.a);
    return localStringBuffer.toString();
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
    XMLHandler.appendBeginTag(localStringBuffer, "LOCATION_SEARCH_RESULT");
    if (this.jdField_for != null) {
      XMLHandler.appendTag(localStringBuffer, "LOCATION_NAME", this.jdField_for);
    }
    if (this.jdField_if != null) {
      XMLHandler.appendTag(localStringBuffer, "LOCATION_ID", this.jdField_if);
    }
    if (this.a != null) {
      XMLHandler.appendTag(localStringBuffer, "LOCATION_BPWID", this.a);
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "LOCATION_SEARCH_RESULT");
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
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    String str;
    if (paramString1.equals("LOCATION_NAME"))
    {
      str = this.jdField_for;
      if (str != null) {
        return isFilterable(str, paramString2, paramString3);
      }
    }
    else if (paramString1.equals("LOCATION_ID"))
    {
      str = this.jdField_if;
      if (str != null) {
        return isFilterable(str, paramString2, paramString3);
      }
    }
    else if (paramString1.equals("LOCATION_BPWID"))
    {
      str = this.a;
      if (str != null) {
        return isFilterable(str, paramString2, paramString3);
      }
    }
    else
    {
      return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
    }
    return false;
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      LocationSearchResult.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.cashcon.LocationSearchResult
 * JD-Core Version:    0.7.0.1
 */