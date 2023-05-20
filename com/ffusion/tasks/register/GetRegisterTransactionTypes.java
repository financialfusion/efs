package com.ffusion.tasks.register;

import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.banking.TransactionTypeInfo;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.FilteredList;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetRegisterTransactionTypes
  extends ExtendedBaseTask
  implements Task
{
  public static final String TYPE_ID = "TYPE_ID";
  public static final String TYPE_NAME = "TYPE_NAME";
  public static final String REG_TYPES = "RegTypes";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return super.getTaskErrorURL();
    }
    Locale localLocale = localSecureUser.getLocale();
    try
    {
      FilteredList localFilteredList = new FilteredList();
      ResourceBundle localResourceBundle = ResourceBundle.getBundle("com.ffusion.beans.register.resources", localLocale);
      HashMap localHashMap = null;
      try
      {
        localHashMap = Banking.getTransactionTypes(0, new HashMap());
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        return super.getServiceErrorURL();
      }
      Object localObject1;
      Object localObject2;
      String str2;
      Object localObject3;
      try
      {
        String str1 = localResourceBundle.getString("RegTypes");
        localObject1 = new StringTokenizer(str1, ",");
        while (((StringTokenizer)localObject1).hasMoreTokens())
        {
          localObject2 = ((StringTokenizer)localObject1).nextToken();
          str2 = localResourceBundle.getString("TransactionType" + (String)localObject2);
          String str3 = null;
          localObject3 = (TransactionTypeInfo)localHashMap.get(new Integer(str2));
          str3 = ((TransactionTypeInfo)localObject3).getDescription(localLocale);
          String str4 = ((TransactionTypeInfo)localObject3).getAbbr(localLocale);
          a locala = new a(str2, str3, str4);
          localFilteredList.add(locala);
        }
      }
      catch (Exception localException2)
      {
        localObject1 = localResourceBundle.getKeys();
        localObject2 = null;
      }
      while (((Enumeration)localObject1).hasMoreElements())
      {
        str2 = (String)((Enumeration)localObject1).nextElement();
        if ((str2.startsWith("TranType")) || (str2.startsWith("TranTypeAbbr")))
        {
          int i = str2.indexOf(".");
          localObject3 = str2.substring(i + 1);
          localObject2 = jdMethod_for((String)localObject3, localFilteredList);
          if (localObject2 == null)
          {
            localObject2 = new a();
            ((a)localObject2).put("TYPE_ID", new Integer((String)localObject3));
            localFilteredList.add(localObject2);
          }
          if (str2.indexOf("TranType") != -1) {
            ((a)localObject2).put("TYPE_NAME", localResourceBundle.getString("TranType." + (String)localObject3));
          }
          if (str2.indexOf("TranTypeAbbr") != -1) {
            ((a)localObject2).put("TYPE_ABBR", localResourceBundle.getString("TranTypeAbbr." + (String)localObject3));
          }
        }
      }
      localHttpSession.setAttribute("RegisterTransactionTypes", localFilteredList);
    }
    catch (MissingResourceException localMissingResourceException)
    {
      this.error = 43;
      return this.taskErrorURL;
    }
    catch (Exception localException1)
    {
      this.error = 20125;
      return this.taskErrorURL;
    }
    return this.successURL;
  }
  
  private a jdMethod_for(String paramString, FilteredList paramFilteredList)
  {
    Iterator localIterator = paramFilteredList.iterator();
    while (localIterator.hasNext())
    {
      a locala = (a)localIterator.next();
      if (locala.get("TYPE_ID").toString().equals(paramString)) {
        return locala;
      }
    }
    return null;
  }
  
  private class a
    extends ExtendABean
  {
    public a() {}
    
    public a(String paramString1, String paramString2, String paramString3)
    {
      put("TYPE_ID", paramString1);
      put("TYPE_NAME", paramString2);
      put("TYPE_ABBR", paramString3);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.GetRegisterTransactionTypes
 * JD-Core Version:    0.7.0.1
 */