package com.ffusion.beans.aggregation;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.util.Locale;

public class InstitutionAccountType
  extends ExtendABean
{
  public static final String INSTITUTIONACCOUNTTYPE = "INSTITUTIONACCOUNTTYPE";
  public static final String TYPE = "TYPE";
  private int aZr;
  
  protected InstitutionAccountType() {}
  
  protected InstitutionAccountType(Locale paramLocale)
  {
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.locale = paramLocale;
  }
  
  public void setTypeValue(int paramInt)
  {
    this.aZr = paramInt;
  }
  
  public void setType(String paramString)
  {
    try
    {
      this.aZr = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this.aZr = 0;
    }
  }
  
  public int getTypeValue()
  {
    return this.aZr;
  }
  
  public String getType()
  {
    return String.valueOf(this.aZr);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("TYPE")) {
      setType(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    InstitutionAccountType localInstitutionAccountType = (InstitutionAccountType)paramObject;
    int i = 1;
    if ((paramString.equals("TYPE")) && (getType() != null) && (localInstitutionAccountType.getType() != null)) {
      i = getType().compareTo(localInstitutionAccountType.getType());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if (paramString1.equals("TYPE")) {
      return isFilterable(getType(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void set(InstitutionAccountType paramInstitutionAccountType)
  {
    setType(paramInstitutionAccountType.getType());
    super.set(paramInstitutionAccountType);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "INSTITUTIONACCOUNTTYPE");
    XMLHandler.appendTag(localStringBuffer, "TYPE", this.aZr);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "INSTITUTIONACCOUNTTYPE");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.aggregation.InstitutionAccountType
 * JD-Core Version:    0.7.0.1
 */