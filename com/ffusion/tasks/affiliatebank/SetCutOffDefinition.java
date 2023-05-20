package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffDefinition;
import com.ffusion.beans.affiliatebank.CutOffTime;
import com.ffusion.beans.affiliatebank.CutOffTimes;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetCutOffDefinition
  extends BaseTask
  implements AffiliateBankTask
{
  private int aPF;
  private int aPH;
  private String aPD = "AffiliateBank_CutOff_Definition";
  private int aPC;
  private boolean aPB;
  private boolean aPE;
  public static final String CUTOFF_PREFIX = "CutOff_Def_";
  public static final String CUTOFF_FIELDNAME_DAYOFWEEK = "DayOfWeek_";
  public static final String CUTOFF_FIELDNAME_TIMEOFDAY = "TimeOfDay_";
  public static final String CUTOFF_FIELDNAME_ONETIMEEXTENSION = "OneTimeExtension_";
  public static final String CUTOFF_FIELDNAME_ACTIVE = "Active_";
  private String aPG = "CutOff_Def_";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    CutOffDefinition localCutOffDefinition = new CutOffDefinition();
    CutOffTimes localCutOffTimes = new CutOffTimes();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      for (int i = 1; i <= this.aPC; i++)
      {
        int j = 0;
        String str2 = (String)localHttpSession.getAttribute(this.aPG + "DayOfWeek_" + Integer.toString(i));
        if (str2 != null)
        {
          j = Integer.parseInt(str2);
          localHttpSession.removeAttribute(this.aPG + "DayOfWeek_" + Integer.toString(i));
        }
        String str3 = (String)localHttpSession.getAttribute(this.aPG + "TimeOfDay_" + Integer.toString(i));
        if (str3 == null)
        {
          str3 = "";
          localHttpSession.removeAttribute(this.aPG + "TimeOfDay_" + Integer.toString(i));
        }
        String str4 = (String)localHttpSession.getAttribute(this.aPG + "OneTimeExtension_" + Integer.toString(i));
        if (str4 == null)
        {
          str4 = "";
          localHttpSession.removeAttribute(this.aPG + "OneTimeExtension_" + Integer.toString(i));
        }
        String str5 = (String)localHttpSession.getAttribute(this.aPG + "Active_" + Integer.toString(i));
        if (str5 == null)
        {
          str5 = "false";
          localHttpSession.removeAttribute(this.aPG + "Active_" + Integer.toString(i));
        }
        CutOffTime localCutOffTime = new CutOffTime();
        localCutOffTime.setDayOfWeekValue(j);
        localCutOffTime.setTimeOfDay(str3);
        localCutOffTime.setExtension(str4);
        if ((str5.compareToIgnoreCase("Y") == 0) || (str5.compareToIgnoreCase("TRUE") == 0)) {
          localCutOffTime.setActiveValue(true);
        } else {
          localCutOffTime.setActiveValue(false);
        }
        af(str3);
        af(str4);
        if (str3.compareTo("") != 0) {
          localCutOffTimes.add(localCutOffTime);
        }
      }
      localCutOffDefinition.setCutOffs(localCutOffTimes);
      localCutOffDefinition.setProcessOnHolidaysValue(this.aPB);
      localCutOffDefinition.setCreateEmptyFileValue(this.aPE);
      Object localObject = localHttpSession.getAttribute("com.ffusion.services.Alerts");
      if (localObject != null)
      {
        localHashMap.put("SERVICE", localObject);
      }
      else
      {
        this.error = 25552;
        str1 = this.serviceErrorURL;
      }
      AffiliateBankAdmin.setCutOffDefinition(localSecureUser, this.aPF, this.aPH, localCutOffDefinition, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    catch (ClassCastException localClassCastException)
    {
      this.error = 25550;
      str1 = this.serviceErrorURL;
    }
    catch (Exception localException)
    {
      this.error = 25551;
      str1 = this.serviceErrorURL;
    }
    if (this.error == 0) {
      str1 = this.successURL;
    }
    return str1;
  }
  
  private void af(String paramString)
    throws Exception
  {
    if (paramString.length() != 0) {
      try
      {
        if ((paramString.length() != 5) && (paramString.indexOf(':') != 2)) {
          throw new Exception();
        }
        int i = Integer.parseInt(paramString.substring(0, paramString.indexOf(':')));
        int j = Integer.parseInt(paramString.substring(paramString.indexOf(':') + 1));
        if ((i < 0) || (i > 23) || (j < 0) || (j > 59)) {
          throw new Exception();
        }
      }
      catch (ClassCastException localClassCastException)
      {
        throw new Exception();
      }
    }
  }
  
  public void setAffiliateBankID(String paramString)
  {
    try
    {
      this.aPF = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setAffiliateBankIDValue(int paramInt)
  {
    this.aPF = paramInt;
  }
  
  public String getAffiliateBankID()
  {
    return String.valueOf(this.aPF);
  }
  
  public int getAffiliateBankIDValue()
  {
    return this.aPF;
  }
  
  public void setCutOffType(String paramString)
  {
    try
    {
      this.aPH = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setCutOffTypeValue(int paramInt)
  {
    this.aPH = paramInt;
  }
  
  public String getCutOffType()
  {
    return String.valueOf(this.aPH);
  }
  
  public int getCutOffTypeValue()
  {
    return this.aPH;
  }
  
  public void setMaxCount(String paramString)
  {
    try
    {
      this.aPC = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setMaxCountValue(int paramInt)
  {
    this.aPC = paramInt;
  }
  
  public String getMaxCount()
  {
    return String.valueOf(this.aPC);
  }
  
  public int getMaxCountValue()
  {
    return this.aPC;
  }
  
  public void setSessionName(String paramString)
  {
    this.aPD = paramString;
  }
  
  public String getSessionName()
  {
    return this.aPD;
  }
  
  public void setSessionPrefix(String paramString)
  {
    this.aPG = paramString;
  }
  
  public String getSessionPrefix()
  {
    return this.aPG;
  }
  
  public void setCreateEmptyFile(String paramString)
  {
    try
    {
      this.aPE = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public void setCreateEmptyFileValue(boolean paramBoolean)
  {
    this.aPE = paramBoolean;
  }
  
  public String getCreateEmptyFile()
  {
    if (this.aPE) {
      return "true";
    }
    return "false";
  }
  
  public boolean getCreateEmptyFileValue()
  {
    return this.aPE;
  }
  
  public void setProcessOnHolidays(String paramString)
  {
    try
    {
      this.aPB = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public void setProcessOnHolidaysValue(boolean paramBoolean)
  {
    this.aPB = paramBoolean;
  }
  
  public String getProcessOnHolidays()
  {
    if (this.aPB) {
      return "true";
    }
    return "false";
  }
  
  public boolean getProcessOnHolidaysValue()
  {
    return this.aPB;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.SetCutOffDefinition
 * JD-Core Version:    0.7.0.1
 */