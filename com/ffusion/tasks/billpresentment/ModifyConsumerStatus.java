package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpresentment.ConsumerStatus;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BillPresentment;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyConsumerStatus
  extends ConsumerStatus
  implements Task
{
  private boolean Jl = true;
  private boolean Jk = true;
  private String Jo;
  private String Jn;
  private String Ji;
  private String Jm;
  private int Jj;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    boolean bool = true;
    String str = this.Jm;
    if (this.Jl) {
      bool = init(localHttpSession);
    }
    if (bool)
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      this.Jj = 0;
      try
      {
        ConsumerStatus localConsumerStatus = BillPresentment.modifyConsumerStatus(localSecureUser, this, localHashMap);
        set(localConsumerStatus);
      }
      catch (CSILException localCSILException)
      {
        this.Jj = MapError.mapError(localCSILException);
      }
      if (this.Jj == 0) {
        localHttpSession.setAttribute("ConsumerStatus", this);
      } else {
        str = this.Ji;
      }
    }
    else
    {
      str = this.Jn;
    }
    return str;
  }
  
  public boolean init(HttpSession paramHttpSession)
  {
    ConsumerStatus localConsumerStatus = (ConsumerStatus)paramHttpSession.getAttribute("ConsumerStatus");
    if (localConsumerStatus == null)
    {
      this.Jj = 6508;
      return false;
    }
    set(localConsumerStatus);
    setLocale(localConsumerStatus.getLocale());
    return true;
  }
  
  public final void setInitialize(String paramString)
  {
    if (paramString.trim().toLowerCase().equals("true")) {
      this.Jl = true;
    } else {
      this.Jl = false;
    }
  }
  
  public final void setProcess(String paramString)
  {
    if (paramString.trim().toLowerCase().equals("true")) {
      this.Jk = true;
    } else {
      this.Jk = false;
    }
  }
  
  public final void setValidate(String paramString)
  {
    if (!paramString.equals("")) {
      this.Jo = paramString.toUpperCase();
    } else {
      this.Jo = null;
    }
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.Jo != null)
    {
      if (this.Jo.indexOf("TCACCEPTED") != -1) {
        bool = validateTCAccepted();
      }
      if ((bool) && (this.Jo.indexOf("NEWBILLERNOTIFIEDDATE") != -1)) {
        bool = validateNewBillerNotifiedDate();
      }
      if ((bool) && (this.Jo.indexOf("BILLDUENOTIFYDESIRED") != -1)) {
        bool = validateBillDueNotifyDesired();
      }
      if ((bool) && (this.Jo.indexOf("NEWBILLNOTIFYDESIRED") != -1)) {
        bool = validateNewBillNotifyDesired();
      }
      if ((bool) && (this.Jo.indexOf("ACCOUNTINFONOTIFYDESIRED") != -1)) {
        bool = validateAccountInfoNotifyDesired();
      }
      if ((bool) && (this.Jo.indexOf("NEWBILLERNOTIFYDESIRED") != -1)) {
        bool = validateNewBillerNotifyDesired();
      }
      this.Jo = null;
    }
    return bool;
  }
  
  protected boolean validateTCAccepted()
  {
    return validateYN(getTCAccepted(), 6621, 6622);
  }
  
  protected boolean validateBillDueNotifyDesired()
  {
    return validateYN(getBillDueNotifyDesired(), 6623, 6624);
  }
  
  protected boolean validateNewBillNotifyDesired()
  {
    return validateYN(getNewBillNotifyDesired(), 6625, 6626);
  }
  
  protected boolean validateAccountInfoNotifyDesired()
  {
    return validateYN(getAccountInfoNotifyDesired(), 6627, 6628);
  }
  
  protected boolean validateNewBillerNotifyDesired()
  {
    return validateYN(getNewBillerNotifyDesired(), 6629, 6630);
  }
  
  protected boolean validateYN(String paramString, int paramInt1, int paramInt2)
  {
    this.Jj = 0;
    if (paramString == null)
    {
      this.Jj = paramInt1;
    }
    else
    {
      paramString = paramString.trim().toUpperCase();
      if ((!paramString.equals("N")) && (!paramString.equals("Y"))) {
        this.Jj = paramInt2;
      }
    }
    return this.Jj == 0;
  }
  
  protected boolean validateNewBillerNotifiedDate()
  {
    this.Jj = 0;
    DateTime localDateTime1 = new DateTime(getLocale());
    if (getNewBillerNotifiedDate() == null)
    {
      setNewBillerNotifiedDate(localDateTime1);
      this.Jj = 6631;
    }
    else
    {
      DateTime localDateTime2 = (DateTime)getNewBillerNotifiedDateValue().clone();
      localDateTime2.set(1, localDateTime1.get(1));
      localDateTime2.set(2, localDateTime1.get(2));
      localDateTime2.set(5, localDateTime1.get(5));
    }
    return this.Jj == 0;
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.Jm = paramString;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.Ji = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.Jn = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.Jj);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.ModifyConsumerStatus
 * JD-Core Version:    0.7.0.1
 */