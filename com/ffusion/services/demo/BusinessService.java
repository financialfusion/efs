package com.ffusion.services.demo;

import com.ffusion.beans.Contact;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class BusinessService
  implements com.ffusion.services.BusinessService
{
  public static final String BUSINESS_GROUP = "BUSINESS_GROUP";
  private Businesses da;
  
  public String getDirectoryReq()
  {
    return "No directory request was made. We are using the demo service.";
  }
  
  public String getDirectoryRes()
  {
    return "No directory response was made. We are using the demo service.";
  }
  
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
      Locale localLocale = new Locale("en", "US");
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
        if (localXMLTag1 != null)
        {
          this.da = new Businesses(localLocale);
          XMLTag localXMLTag2;
          if ((localXMLTag2 = localXMLTag1.getContainedTag("BUSINESS_GROUP")) != null) {
            updateBusinessListFromXML(localXMLTag2, this.da);
          }
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
  
  protected void updateBusinessListFromXML(XMLTag paramXMLTag, Businesses paramBusinesses)
  {
    ArrayList localArrayList = paramXMLTag.getContainedTagList();
    if (localArrayList != null)
    {
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        XMLTag localXMLTag = (XMLTag)localIterator.next();
        if (localXMLTag.getTagName().equalsIgnoreCase(Business.BUSINESS))
        {
          Business localBusiness = new Business(paramBusinesses.getLocale());
          if (updateBusinessFromXML(localXMLTag, localBusiness)) {
            paramBusinesses.add(localBusiness);
          }
        }
      }
    }
  }
  
  protected boolean updateBusinessFromXML(XMLTag paramXMLTag, Business paramBusiness)
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
        if (str1.equals(Business.ID)) {
          paramBusiness.setId(str2);
        } else if (str1.equals(Business.BUSINESSNAME)) {
          paramBusiness.setBusinessName(str2);
        } else if (str1.equals(Business.PASSWORD)) {
          paramBusiness.setPassword(str2);
        } else if (str1.equals(Business.STATUS)) {
          paramBusiness.setStatus(str2);
        } else if (str1.equals("CONTACT")) {
          updateContactFromXML(paramXMLTag, paramBusiness);
        }
      }
    }
    catch (Exception localException)
    {
      System.out.println("exception getting business info" + localException);
    }
    return bool;
  }
  
  protected boolean updateContactFromXML(XMLTag paramXMLTag, Contact paramContact)
  {
    ArrayList localArrayList = null;
    boolean bool = false;
    localArrayList = paramXMLTag.getContainedTagList();
    Iterator localIterator = localArrayList.iterator();
    bool = true;
    while (localIterator.hasNext())
    {
      paramXMLTag = (XMLTag)localIterator.next();
      String str1 = paramXMLTag.getTagName();
      String str2 = paramXMLTag.getTagContent();
      if (str1.equals("PHONE")) {
        paramContact.setPhone(str2);
      } else if (str1.equals("PHONE2")) {
        paramContact.setPhone2(str2);
      } else if (str1.equals("STREET")) {
        paramContact.setStreet(str2);
      } else if (str1.equals("STREET2")) {
        paramContact.setStreet2(str2);
      } else if (str1.equals("EMAIL")) {
        paramContact.setEmail(str2);
      } else if (str1.equals("CITY")) {
        paramContact.setCity(str2);
      } else if (str1.equals("STATE")) {
        paramContact.setState(str2);
      } else if (str1.equals("ZIPCODE")) {
        paramContact.setZipCode(str2);
      } else if (str1.equals("COUNTRY")) {
        paramContact.setCountry(str2);
      }
    }
    return bool;
  }
  
  public void setSettings(String paramString) {}
  
  public String getSettings()
  {
    return "Settings";
  }
  
  public int signOn(String paramString1, String paramString2)
  {
    return 0;
  }
  
  public void signOff() {}
  
  public int modifyBusiness(Business paramBusiness)
  {
    Business localBusiness = this.da.getById(paramBusiness.getIdValue());
    if (localBusiness != null)
    {
      this.da.removeById(paramBusiness.getIdValue());
      this.da.add(paramBusiness);
    }
    return 0;
  }
  
  public int addBusiness(Business paramBusiness)
  {
    if (this.da != null)
    {
      Double localDouble = new Double(Math.random() * 10000.0D);
      paramBusiness.setId(localDouble.intValue());
      this.da.add(paramBusiness);
    }
    return 0;
  }
  
  public int deactivateBusiness(Business paramBusiness)
  {
    Business localBusiness = this.da.getById(paramBusiness.getIdValue());
    if (localBusiness != null)
    {
      this.da.removeById(localBusiness.getIdValue());
      paramBusiness.setStatus("2");
      this.da.add(paramBusiness);
    }
    return 0;
  }
  
  public int reactivateBusiness(Business paramBusiness)
  {
    Business localBusiness = this.da.getById(paramBusiness.getIdValue());
    if (localBusiness != null)
    {
      this.da.removeById(localBusiness.getIdValue());
      localBusiness.setStatus("1");
      this.da.add(localBusiness);
      paramBusiness.set(localBusiness);
      return 0;
    }
    return 4011;
  }
  
  public int purgeDeactivatedBusiness(Business paramBusiness)
  {
    this.da.removeById(paramBusiness.getIdValue());
    return 0;
  }
  
  public int getBusiness(Business paramBusiness)
  {
    Business localBusiness = this.da.getById(paramBusiness.getIdValue());
    if (localBusiness == null) {
      return 4011;
    }
    paramBusiness.set(localBusiness);
    return 0;
  }
  
  public int getBusinesses(Businesses paramBusinesses)
  {
    Iterator localIterator = this.da.iterator();
    while (localIterator.hasNext())
    {
      Business localBusiness = (Business)localIterator.next();
      paramBusinesses.add(localBusiness);
    }
    return 0;
  }
  
  public int enrollBusiness(Business paramBusiness)
  {
    if (this.da != null) {
      this.da.add(paramBusiness);
    }
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.BusinessService
 * JD-Core Version:    0.7.0.1
 */