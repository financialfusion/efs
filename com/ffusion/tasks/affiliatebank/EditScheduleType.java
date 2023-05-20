package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.ScheduleType;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditScheduleType
  extends ScheduleType
  implements CutOffTaskDefines
{
  protected String scheduleSessionName = "ScheduleType";
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
      str = processScheduleType(paramHttpServletRequest, localHttpSession);
    }
    return str;
  }
  
  public boolean initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    ScheduleType localScheduleType = (ScheduleType)paramHttpSession.getAttribute(this.scheduleSessionName);
    if (localScheduleType == null)
    {
      this.error = 26012;
      return false;
    }
    set(localScheduleType);
    paramHttpSession.setAttribute("OldScheduleType", localScheduleType.clone());
    this.error = 0;
    return true;
  }
  
  protected String processScheduleType(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = editScheduleType(paramHttpSession);
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
  
  protected String editScheduleType(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 26018;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    ScheduleType localScheduleType1 = null;
    try
    {
      localScheduleType1 = PaymentsAdmin.modifyScheduleType(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      ScheduleType localScheduleType2 = new ScheduleType();
      localScheduleType2.set(localScheduleType1);
      paramHttpSession.setAttribute(this.scheduleSessionName, localScheduleType2);
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    this.error = 0;
    if (this.validate == null) {
      return true;
    }
    if ((this.validate.indexOf("FIID") != -1) && (n(getFIId())))
    {
      this.error = 26011;
      return false;
    }
    if ((this.validate.indexOf("GROUP") != -1) && (n(getCategory())))
    {
      this.error = 26017;
      return false;
    }
    if ((this.validate.indexOf("INSTRUCTION_TYPE") != -1) && (n(getInstructionType())))
    {
      this.error = 26003;
      return false;
    }
    if ((this.validate.indexOf("START_TIME") != -1) && (!validateTimeFormat(getStartTime())) && (!getUseCutOffsValue()))
    {
      this.error = 26014;
      return false;
    }
    if ((this.validate.indexOf("INTERVAL") != -1) && (n(getInterval())) && (!getUseCutOffsValue()))
    {
      this.error = 26015;
      return false;
    }
    if ((this.validate.indexOf("HANDLER_NAME") != -1) && (n(getHandlerName())))
    {
      this.error = 26016;
      return false;
    }
    this.validate = null;
    return true;
  }
  
  protected boolean validateTimeFormat(String paramString)
  {
    if (n(paramString)) {
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
  
  private boolean n(String paramString)
  {
    return (paramString == null) || (paramString.trim().length() == 0);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.EditScheduleType
 * JD-Core Version:    0.7.0.1
 */