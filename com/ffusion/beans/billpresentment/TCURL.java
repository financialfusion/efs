package com.ffusion.beans.billpresentment;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;

public class TCURL
  extends ExtendABean
  implements Serializable
{
  public static final String TCURL = "TCURL";
  public static final String TCURLID = "TCURLID";
  public static final String PRIMARYURL = "URL";
  public static final String PRINTABLEURL = "PRINTABLEURL";
  private long ID;
  private String URL;
  private String Jt;
  
  public final String getID()
  {
    return String.valueOf(this.ID);
  }
  
  public final long getIDValue()
  {
    return this.ID;
  }
  
  public final void setID(String paramString)
  {
    try
    {
      this.ID = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception setting ID:", localException);
    }
  }
  
  public final void setURL(String paramString)
  {
    this.URL = paramString;
  }
  
  public final String getURL()
  {
    return this.URL;
  }
  
  public final void setPrintableURL(String paramString)
  {
    this.Jt = paramString;
  }
  
  public final String getPrintableURL()
  {
    return this.Jt;
  }
  
  public void set(TCURL paramTCURL)
  {
    setID(paramTCURL.getID());
    setURL(paramTCURL.getURL());
    setPrintableURL(paramTCURL.getPrintableURL());
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("TCURLID")) {
      try
      {
        this.ID = Long.parseLong(paramString2);
      }
      catch (Exception localException)
      {
        DebugLog.throwing("Exception:", localException);
      }
    } else if (paramString1.equals(this.URL)) {
      this.URL = paramString2;
    } else if (paramString1.equals("PRINTABLEURL")) {
      this.Jt = paramString2;
    }
    return bool;
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
    XMLHandler.appendBeginTag(localStringBuffer, "TCURL");
    XMLHandler.appendTag(localStringBuffer, "TCURLID", String.valueOf(this.ID));
    XMLHandler.appendTag(localStringBuffer, "URL", this.URL);
    XMLHandler.appendTag(localStringBuffer, "PRINTABLEURL", this.Jt);
    XMLHandler.appendEndTag(localStringBuffer, "TCURL");
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
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      if (str.length() > 0) {
        TCURL.this.set(getElement(), str);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpresentment.TCURL
 * JD-Core Version:    0.7.0.1
 */