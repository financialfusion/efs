package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffTimes;
import com.ffusion.beans.affiliatebank.ScheduleCategory;
import com.ffusion.beans.affiliatebank.ScheduleTypes;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetScheduleCategory
  extends ExtendedBaseTask
  implements CutOffTaskDefines
{
  protected ScheduleCategory scheduleCategory = new ScheduleCategory();
  protected String fIId;
  protected String category;
  
  public GetScheduleCategory()
  {
    this.beanSessionName = "ScheduleCategory";
    CutOffTimes localCutOffTimes = new CutOffTimes();
    ScheduleTypes localScheduleTypes = new ScheduleTypes();
    this.scheduleCategory.setCutOffTimes(localCutOffTimes);
    this.scheduleCategory.setScheduleTypes(localScheduleTypes);
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    try
    {
      if ((this.fIId == null) || (this.fIId.length() == 0))
      {
        this.error = 26011;
        return this.taskErrorURL;
      }
      if ((this.category == null) || (this.category.length() == 0))
      {
        this.error = 26001;
        return this.taskErrorURL;
      }
      this.scheduleCategory = PaymentsAdmin.getScheduleCategory(localSecureUser, this.fIId, this.category, localHashMap);
      localHttpSession.setAttribute(this.beanSessionName, this.scheduleCategory);
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof CSILException)) {
        this.error = MapError.mapError((CSILException)localThrowable);
      } else {
        this.error = 37504;
      }
      localThrowable.printStackTrace();
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setFIId(String paramString)
  {
    this.fIId = paramString;
  }
  
  public String getFIId()
  {
    return this.fIId;
  }
  
  public void setCategory(String paramString)
  {
    this.category = paramString;
  }
  
  public String getCategory()
  {
    return this.category;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.GetScheduleCategory
 * JD-Core Version:    0.7.0.1
 */