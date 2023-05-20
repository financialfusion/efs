package com.ffusion.services.demo;

import com.ffusion.beans.DateTime;
import java.util.Locale;

public class XMLUtil
{
  protected static DateTime parseNewDateTime(String paramString)
  {
    DateTime localDateTime = null;
    try
    {
      localDateTime = new DateTime(paramString, Locale.getDefault(), "MM-dd-yyyy HH:mm:ss");
    }
    catch (Exception localException1)
    {
      try
      {
        localDateTime = new DateTime(paramString, Locale.getDefault(), "MM-dd-yyyy");
      }
      catch (Exception localException2) {}
    }
    return localDateTime;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.XMLUtil
 * JD-Core Version:    0.7.0.1
 */