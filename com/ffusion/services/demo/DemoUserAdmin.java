package com.ffusion.services.demo;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.beans.user.CustomTag;
import com.ffusion.beans.user.CustomTags;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.services.UserAdmin3;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class DemoUserAdmin
  implements UserAdmin3
{
  public static final String SIGNON = "SIGNON";
  public static final String USERNAME = "USERNAME";
  public static final String PASSWORD = "PASSWORD";
  private String c7;
  private String c6;
  
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
          this.c7 = str2;
        } else if (str1.equals("PASSWORD")) {
          this.c6 = str2;
        }
      }
    }
    catch (Exception localException)
    {
      System.out.println("exception getting username and password. " + localException);
    }
    return bool;
  }
  
  public void setInitURL(String paramString) {}
  
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
        if ((localXMLTag1 != null) && ((localXMLTag2 = localXMLTag1.getContainedTag("SIGNON")) != null)) {
          updateUsernamePasswordFromXML(localXMLTag2);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      i = 8;
    }
    if ((this.c7 == null) || (this.c6 == null)) {
      i = 8;
    }
    return i;
  }
  
  public int signOn(String paramString1, String paramString2)
  {
    if (paramString1.compareTo(this.c7) != 0) {
      return 100;
    }
    if (this.c6.compareTo(paramString2) == 0) {
      return 101;
    }
    return 0;
  }
  
  public int changePIN(String paramString1, String paramString2)
  {
    return 2;
  }
  
  public int addUser(User paramUser)
  {
    return 2;
  }
  
  public int getUser(User paramUser)
  {
    return 2;
  }
  
  public int authenticateUser(User paramUser)
  {
    return 2;
  }
  
  public int getUsers(Users paramUsers, User paramUser)
  {
    return 2;
  }
  
  public int modifyUser(User paramUser)
  {
    return 2;
  }
  
  public int modifyUserPassword(User paramUser, String paramString1, String paramString2)
  {
    return 2;
  }
  
  public int addAccount(Account paramAccount)
  {
    return 2;
  }
  
  public int getAccounts(Accounts paramAccounts, Account paramAccount)
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
  
  public int addAdditionalData(String paramString1, String paramString2)
  {
    return 2;
  }
  
  public int getAdditionalData(String paramString, StringBuffer paramStringBuffer)
  {
    return 2;
  }
  
  public int addHistory(Histories paramHistories)
  {
    return 2;
  }
  
  public int getHistory(History paramHistory, Histories paramHistories, Calendar paramCalendar1, Calendar paramCalendar2)
  {
    return 2;
  }
  
  public int deleteUser(User paramUser)
  {
    return 2;
  }
  
  public int getLastError()
  {
    return 2;
  }
  
  public String getLastMessage()
  {
    return null;
  }
  
  public int getHistoryUserType()
  {
    return 2;
  }
  
  public int getHistoryEmployeeType()
  {
    return 2;
  }
  
  public int addCustomTags(CustomTags paramCustomTags, User paramUser)
  {
    return 2;
  }
  
  public int getCustomTags(User paramUser)
  {
    return 2;
  }
  
  public int modifyCustomTags(CustomTag paramCustomTag, User paramUser)
  {
    return 2;
  }
  
  public int deleteCustomTags(CustomTag paramCustomTag, User paramUser)
  {
    return 2;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.DemoUserAdmin
 * JD-Core Version:    0.7.0.1
 */