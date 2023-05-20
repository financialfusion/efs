package com.ffusion.services.demo;

import com.ffusion.beans.Balance;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.services.AccountService;
import com.ffusion.services.SignOn2;
import com.ffusion.util.HfnEncrypt;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLHandler;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

public abstract class Service
  implements SignOn2, AccountService
{
  protected String m_URL;
  protected static HashMap m_InitHashMap;
  protected int errorCode;
  private String M;
  private boolean N = false;
  private boolean O = false;
  protected Accounts accounts;
  
  public String getLastError()
  {
    return "" + this.errorCode;
  }
  
  public String getLastErrorMessage()
  {
    return "";
  }
  
  public void setForceMustChangePassword(String paramString)
  {
    this.N = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setForceInvalidPassword(String paramString)
  {
    this.O = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setSettings(String paramString)
  {
    this.M = paramString;
  }
  
  public String getSettings()
  {
    return this.M;
  }
  
  public int changePIN(String paramString1, String paramString2)
  {
    return 0;
  }
  
  public int signOn(String paramString1, String paramString2)
  {
    if (this.N) {
      this.errorCode = 102;
    }
    if (this.O) {
      this.errorCode = 101;
    }
    this.N = false;
    this.O = false;
    return this.errorCode;
  }
  
  public int initialize(String paramString)
  {
    this.m_URL = paramString;
    int i = 0;
    try
    {
      if (ResourceUtil.findResourceURL(this, paramString) == null)
      {
        System.out.println("INIT FILE NOT FOUND : " + paramString);
        i = 7;
      }
    }
    catch (Throwable localThrowable)
    {
      i = 8;
    }
    return i;
  }
  
  public void setInitURL(String paramString)
  {
    initialize(paramString);
  }
  
  public static String getXMLString(Object paramObject, String paramString)
  {
    if (m_InitHashMap == null) {
      m_InitHashMap = new HashMap();
    }
    String str = "";
    if ((m_InitHashMap != null) && (m_InitHashMap.get(paramString) != null)) {
      str = (String)m_InitHashMap.get(paramString);
    } else {
      try
      {
        InputStream localInputStream = ResourceUtil.getResourceAsStream(paramObject, paramString);
        if (localInputStream != null)
        {
          str = Strings.streamToString(localInputStream);
          if ((str.indexOf("<?xml") == -1) && (str.indexOf("<?XML") == -1))
          {
            byte[] arrayOfByte = HfnEncrypt.decrypt(str.getBytes(), str.length());
            str = new String(arrayOfByte);
          }
          m_InitHashMap.put(paramString, str);
        }
      }
      catch (Exception localException)
      {
        str = "";
      }
    }
    return str;
  }
  
  public static StringReader getXMLReader(Object paramObject, String paramString)
  {
    String str = getXMLString(paramObject, paramString);
    return new StringReader(str);
  }
  
  public int getAccounts(Accounts paramAccounts)
  {
    this.accounts = paramAccounts;
    a(false);
    this.accounts = null;
    if (paramAccounts.size() > 0)
    {
      GregorianCalendar localGregorianCalendar = new GregorianCalendar();
      int i = localGregorianCalendar.get(1);
      int j = localGregorianCalendar.get(2);
      Iterator localIterator = paramAccounts.iterator();
      while (localIterator.hasNext())
      {
        Account localAccount = (Account)localIterator.next();
        Balance localBalance = localAccount.getCurrentBalance();
        DateTime localDateTime;
        if (localBalance != null)
        {
          localDateTime = localBalance.getDateValue();
          localDateTime.set(1, i);
          localDateTime.set(2, j);
        }
        localBalance = localAccount.getAvailableBalance();
        if (localBalance != null)
        {
          localDateTime = localBalance.getDateValue();
          localDateTime.set(1, i);
          localDateTime.set(2, j);
        }
      }
      return 0;
    }
    return 1;
  }
  
  public int getAccount(Account paramAccount)
  {
    return 2;
  }
  
  public int addAccount(Account paramAccount)
  {
    return 2;
  }
  
  public int modifyAccount(Account paramAccount)
  {
    return 2;
  }
  
  public int deleteAccount(Account paramAccount)
  {
    return 2;
  }
  
  private void a(boolean paramBoolean)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new InternalXMLHandler(paramBoolean), getXMLReader(this, this.m_URL));
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  protected class InternalXMLHandler
    extends XMLHandler
  {
    protected boolean getTransactions;
    
    public InternalXMLHandler() {}
    
    public InternalXMLHandler(boolean paramBoolean)
    {
      this.getTransactions = paramBoolean;
    }
    
    public void startElement(String paramString)
    {
      if ((paramString.equals("ACCOUNTS")) && (Service.this.accounts != null)) {
        Service.this.accounts.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.Service
 * JD-Core Version:    0.7.0.1
 */