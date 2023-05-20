package com.ffusion.tasks.portal;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.portal.PortalItems;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.logging.DebugLog;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MoveItems
  extends BaseTask
  implements Task
{
  private static final String mc = "Checkbox";
  private static final String me = "Ordinal";
  private static final String md = ", ";
  private static final String mb = "DisplayText";
  private static final String l6 = "OldValue";
  private static final String l8 = "Content Display Order";
  private String[] ma;
  private int[] l5;
  private int l7;
  private Properties[] l9;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    PortalItems localPortalItems = (PortalItems)localHttpSession.getAttribute("PortalItems");
    if (localPortalItems == null)
    {
      this.error = 9009;
      str1 = this.taskErrorURL;
    }
    else if (this.ma == null)
    {
      this.error = 9045;
      str1 = this.taskErrorURL;
    }
    else
    {
      this.l9 = new Properties[this.ma.length];
      String[] arrayOfString1 = new String[this.l7];
      String[] arrayOfString2 = new String[this.l7];
      int[] arrayOfInt = new int[this.l7];
      String[] arrayOfString3 = new String[this.l7];
      Properties localProperties = null;
      StringBuffer localStringBuffer = null;
      String str2 = null;
      String str3 = null;
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 1, Integer.toString(localSecureUser.getProfileID()));
      for (int i = 0; i < this.ma.length; i++)
      {
        localProperties = new Properties();
        for (int j = 0; j < this.l5[i]; j++)
        {
          localStringBuffer = new StringBuffer(this.ma[i]);
          localStringBuffer.append("Checkbox");
          localStringBuffer.append(j);
          String str4 = localStringBuffer.toString();
          str2 = paramHttpServletRequest.getParameter(str4);
          localHttpSession.removeAttribute(str4);
          localStringBuffer = new StringBuffer(str4);
          localStringBuffer.append("DisplayText");
          String str5 = localStringBuffer.toString();
          arrayOfString3[j] = paramHttpServletRequest.getParameter(str5);
          localHttpSession.removeAttribute(str5);
          localStringBuffer = new StringBuffer(str4);
          localStringBuffer.append("OldValue");
          str5 = localStringBuffer.toString();
          String str6 = paramHttpServletRequest.getParameter(str5);
          localHttpSession.removeAttribute(str5);
          if ((str6 != null) && (str6.length() != 0)) {
            try
            {
              arrayOfString1[j] = str6;
            }
            catch (Exception localException1) {}
          } else {
            arrayOfString1[j] = "";
          }
          if (str2 != null)
          {
            localStringBuffer = new StringBuffer(this.ma[i]);
            localStringBuffer.append("Ordinal");
            localStringBuffer.append(j);
            str5 = localStringBuffer.toString();
            str3 = (String)localHttpSession.getAttribute(str5);
            localHttpSession.removeAttribute(str5);
            localProperties.put(str3, str2);
            try
            {
              arrayOfString2[j] = str3;
            }
            catch (Exception localException2) {}
          }
          else
          {
            arrayOfString2[j] = "";
          }
        }
        for (j = 0; j < this.l5[i]; j++) {
          if ((arrayOfString2[j] != null) && (!arrayOfString2[j].equals(""))) {
            try
            {
              int k = new Integer(arrayOfString2[j]).intValue();
              if ((k <= 0) || (k > this.l5[i]))
              {
                this.error = 9046;
                return this.taskErrorURL;
              }
            }
            catch (Exception localException3)
            {
              this.error = 9046;
              return this.taskErrorURL;
            }
          }
        }
        for (j = 0; j < this.l5[i]; j++) {
          for (m = j + 1; m < this.l5[i]; m++) {
            if ((arrayOfString2[j] != null) && (!arrayOfString2[j].equals("")) && (arrayOfString2[j].equals(arrayOfString2[m])))
            {
              this.error = 9047;
              return this.taskErrorURL;
            }
          }
        }
        j = 0;
        for (m = 0; m < this.l5[i]; m++) {
          if ((arrayOfString2[m] != null) && (!arrayOfString2[m].equals(""))) {
            j++;
          }
        }
        for (m = 0; (m < this.l5[i]) && ((arrayOfString2[m] == null) || (arrayOfString2[m].equals(""))); m++) {}
        if (m < this.l5[i])
        {
          for (int i2 = 0; i2 < j; i2++)
          {
            for (m = 0; (m < this.l5[i]) && ((arrayOfString2[m] == null) || (arrayOfString2[m].equals(""))); m++) {}
            if (m < this.l5[i])
            {
              int n = new Integer(arrayOfString2[m]).intValue();
              for (i3 = 0; i3 < this.l5[i]; i3++) {
                if ((arrayOfString2[i3] != null) && (!arrayOfString2[i3].equals("")))
                {
                  int i1 = new Integer(arrayOfString2[i3]).intValue();
                  if (i1 <= n)
                  {
                    n = i1;
                    arrayOfInt[i2] = i3;
                  }
                }
              }
              arrayOfString2[arrayOfInt[i2]] = "";
            }
          }
          for (i2 = 0; i2 < j; i2++)
          {
            arrayOfString2[arrayOfInt[i2]] = new Integer(i2 + 1).toString();
            arrayOfInt[i2] = 0;
          }
        }
        this.l9[i] = localProperties;
        String str8 = localHistoryTracker.lookupField(BankEmployee.BEAN_NAME, "PORTAL_CONTENT" + i);
        for (int i3 = 0; i3 < this.l5[i]; i3++)
        {
          if ((arrayOfString1[i3] != arrayOfString2[i3]) && ((arrayOfString1[i3] == null) || (!arrayOfString1[i3].equals(arrayOfString2[i3])))) {
            localHistoryTracker.logChange("Content Display Order", arrayOfString1[i3], arrayOfString2[i3], arrayOfString3[i3]);
          }
          arrayOfString1[i3] = null;
          arrayOfString2[i3] = null;
          arrayOfString3[i3] = null;
        }
      }
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for MoveItems: " + localProfileException.toString());
      }
      localPortalItems.clear();
      Iterator localIterator = null;
      String str7 = null;
      for (int m = 0; m < this.l9.length; m++)
      {
        localIterator = new TreeSet(this.l9[m].keySet()).iterator();
        while (localIterator.hasNext())
        {
          str7 = (String)localIterator.next();
          localPortalItems.add(this.l9[m].getProperty(str7));
        }
      }
    }
    return str1;
  }
  
  public void setPortalGroupNames(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ", ");
      this.ma = new String[localStringTokenizer.countTokens()];
      for (int i = 0; localStringTokenizer.hasMoreTokens(); i++) {
        this.ma[i] = localStringTokenizer.nextToken();
      }
    }
  }
  
  public void setPortalGroupSizes(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ", ");
      this.l5 = new int[this.ma.length];
      this.l7 = 0;
      for (int i = 0; localStringTokenizer.hasMoreTokens(); i++)
      {
        int j = Integer.parseInt(localStringTokenizer.nextToken());
        if (j > this.l7) {
          this.l7 = j;
        }
        this.l5[i] = j;
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.MoveItems
 * JD-Core Version:    0.7.0.1
 */