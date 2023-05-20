package com.ffusion.services.demo;

import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;
import com.ffusion.services.BusinessService;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

public class BusinessAdminService
  implements BusinessService
{
  public static final String BUSINESS_ADMIN = "BUSINESS_ADMIN";
  public static final String USERNAME = "USERNAME";
  public static final String PASSWORD = "PASSWORD";
  private String c9;
  private String c8;
  
  public void setInitURL(String paramString) {}
  
  public int changePIN(String paramString1, String paramString2)
  {
    return 0;
  }
  
  public int initialize(String paramString)
  {
    int i = 0;
    try
    {
      InputStream localInputStream = ResourceUtil.getResourceAsStream(this, paramString);
      if (localInputStream == null)
      {
        i = 7;
      }
      else
      {
        String str = Strings.streamToString(localInputStream);
        XMLTag localXMLTag1 = new XMLTag();
        localXMLTag1.build(str);
        XMLTag localXMLTag2;
        if ((localXMLTag1 != null) && ((localXMLTag2 = localXMLTag1.getContainedTag("BUSINESS_ADMIN")) != null)) {
          updateUsernamePasswordFromXML(localXMLTag2);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      System.out.println("Error initializing file" + localThrowable);
      localThrowable.printStackTrace();
      i = 8;
    }
    return i;
  }
  
  protected boolean updateUsernamePasswordFromXML(XMLTag paramXMLTag)
  {
    ArrayList localArrayList = null;
    boolean bool = false;
    localArrayList = paramXMLTag.getContainedTagList();
    Iterator localIterator = localArrayList.iterator();
    bool = true;
    try
    {
      while (localIterator.hasNext())
      {
        paramXMLTag = (XMLTag)localIterator.next();
        String str1 = paramXMLTag.getTagName();
        String str2 = paramXMLTag.getTagContent();
        if (str1.equals("USERNAME")) {
          this.c9 = str2;
        } else if (str1.equals("PASSWORD")) {
          this.c8 = str2;
        }
      }
    }
    catch (Exception localException)
    {
      System.out.println("exception getting username and password. " + localException);
    }
    return bool;
  }
  
  public void setSettings(String paramString) {}
  
  public String getSettings()
  {
    return null;
  }
  
  public int signOn(String paramString1, String paramString2)
  {
    if ((paramString1.compareTo(this.c9) == 0) && (paramString2.compareTo(this.c8) == 0)) {
      return 0;
    }
    return 4004;
  }
  
  public void signOff() {}
  
  public int modifyBusiness(Business paramBusiness)
  {
    return 4010;
  }
  
  public int addBusiness(Business paramBusiness)
  {
    return 4010;
  }
  
  public int deactivateBusiness(Business paramBusiness)
  {
    return 4010;
  }
  
  public int reactivateBusiness(Business paramBusiness)
  {
    return 4010;
  }
  
  public int purgeDeactivatedBusiness(Business paramBusiness)
  {
    return 4010;
  }
  
  public int getBusiness(Business paramBusiness)
  {
    return 4010;
  }
  
  public int getBusinesses(Businesses paramBusinesses)
  {
    return 4010;
  }
  
  public int enrollBusiness(Business paramBusiness)
  {
    return 4010;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.BusinessAdminService
 * JD-Core Version:    0.7.0.1
 */