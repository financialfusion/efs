package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffTime;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditCutOffTime
  extends CutOffTime
  implements CutOffTaskDefines
{
  protected String cutoffSessionName = "CutOffTime";
  protected int error = 0;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected boolean currentlyProcessing = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (this.initFlag)
    {
      if (initProcess(localHttpSession)) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else {
      str = processCutOffTime(paramHttpServletRequest, localHttpSession);
    }
    return str;
  }
  
  public boolean initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    CutOffTime localCutOffTime = (CutOffTime)paramHttpSession.getAttribute(this.cutoffSessionName);
    if (localCutOffTime == null)
    {
      this.error = 26004;
      return false;
    }
    set(localCutOffTime);
    paramHttpSession.setAttribute("OldCutOffTime", localCutOffTime.clone());
    this.error = 0;
    return true;
  }
  
  protected String processCutOffTime(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = editCutOffTime(paramHttpSession);
      }
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String editCutOffTime(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 26018;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    CutOffTime localCutOffTime1 = null;
    try
    {
      localCutOffTime1 = PaymentsAdmin.modifyCutOffTime(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      CutOffTime localCutOffTime2 = new CutOffTime();
      localCutOffTime2.set(localCutOffTime1);
      paramHttpSession.setAttribute(this.cutoffSessionName, localCutOffTime2);
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    if (o(this.validate))
    {
      this.error = 0;
      return true;
    }
    if ((this.validate.indexOf("FIID") != -1) && (o(getFIId())))
    {
      this.error = 26011;
      return false;
    }
    if ((this.validate.indexOf("GROUP") != -1) && (o(getCategory())))
    {
      this.error = 26017;
      return false;
    }
    if ((this.validate.indexOf("DAY") != -1) && (o(getDayOfWeek())))
    {
      this.error = 26007;
      return false;
    }
    if ((this.validate.indexOf("INSTRUCTION_TYPE") != -1) && (o(getInstructionType())))
    {
      this.error = 26003;
      return false;
    }
    if ((this.validate.indexOf("CUT_OFF_TIME") != -1) && ((!validateTimeFormat(getTimeOfDay())) || (o(getTimeOfDay()))))
    {
      this.error = 26008;
      return false;
    }
    if ((this.validate.indexOf("EXTENSION") != -1) && (!validateTimeFormat(getExtension())))
    {
      this.error = 26009;
      return false;
    }
    if ((this.validate.indexOf("STATUS") != -1) && (!validateStatus(getStatus())))
    {
      this.error = 26010;
      return false;
    }
    this.validate = null;
    return true;
  }
  
  protected boolean validateTimeFormat(String paramString)
  {
    if (o(paramString)) {
      return true;
    }
    if (paramString.length() != 5) {
      return false;
    }
    if ((paramString.indexOf(":") == 2) && (paramString.indexOf(":", 3) == -1))
    {
      String str1 = paramString.substring(0, 2);
      String str2 = paramString.substring(3, 5);
      int i;
      int j;
      try
      {
        i = Integer.parseInt(str1);
        j = Integer.parseInt(str2);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        return false;
      }
      return (i >= 0) && (i < 24) && (j >= 0) && (j < 60);
    }
    return false;
  }
  
  protected boolean validateStatus(String paramString)
  {
    return (paramString.equals("ACTIVE")) || (paramString.equals("CANCELEDON")) || (paramString.equals("INACTIVE"));
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!paramString.equalsIgnoreCase("")) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  private boolean o(String paramString)
  {
    return (paramString == null) || (paramString.trim().length() == 0);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.EditCutOffTime
 * JD-Core Version:    0.7.0.1
 */