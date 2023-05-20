package com.ffusion.beans.aggregation;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.util.Locale;

public class AccountNVPair
  extends ExtendABean
{
  public static final String ACCOUNTNVPAIR = "ACCOUNTNVPAIR";
  public static final String NAME = "NAME";
  public static final String VALUE = "VALUE";
  public static final String DESCRIPTION = "DESCRIPTION";
  public static final String DISPLAY_FLAG = "DISPLAY_FLAG";
  private String aZp;
  private String aZq;
  private String aZo;
  private boolean aZn;
  
  protected AccountNVPair(Locale paramLocale)
  {
    this.locale = paramLocale;
  }
  
  public void setName(String paramString)
  {
    this.aZp = paramString;
  }
  
  public String getName()
  {
    return this.aZp;
  }
  
  public void setValue(String paramString)
  {
    this.aZq = paramString;
  }
  
  public String getValue()
  {
    return this.aZq;
  }
  
  public void setDescription(String paramString)
  {
    this.aZo = paramString;
  }
  
  public String getDescription()
  {
    return this.aZo;
  }
  
  public void setDisplayFlag(String paramString)
  {
    if ((paramString.equals("1")) || (paramString.equalsIgnoreCase("T"))) {
      this.aZn = true;
    } else {
      this.aZn = false;
    }
  }
  
  public boolean getDisplayFlag()
  {
    return this.aZn;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    if (paramString1.equals("NAME")) {
      this.aZp = paramString2;
    } else if (paramString1.equals("VALUE")) {
      this.aZq = paramString2;
    } else if (paramString1.equals("DESCRIPTION")) {
      this.aZo = paramString2;
    } else if (paramString1.equals("DISPLAY_FLAG")) {
      setDisplayFlag(paramString2);
    }
    return true;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ACCOUNTNVPAIR");
    XMLHandler.appendTag(localStringBuffer, "NAME", this.aZp);
    XMLHandler.appendTag(localStringBuffer, "VALUE", this.aZq);
    XMLHandler.appendTag(localStringBuffer, "DESCRIPTION", this.aZo);
    if (this.aZn) {
      XMLHandler.appendTag(localStringBuffer, "DISPLAY_FLAG", "T");
    } else {
      XMLHandler.appendTag(localStringBuffer, "DISPLAY_FLAG", "F");
    }
    XMLHandler.appendEndTag(localStringBuffer, "ACCOUNTNVPAIR");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.aggregation.AccountNVPair
 * JD-Core Version:    0.7.0.1
 */