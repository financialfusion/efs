package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.ScheduleCategory;
import com.ffusion.beans.affiliatebank.ScheduleType;
import com.ffusion.beans.affiliatebank.ScheduleTypes;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetScheduleType
  extends ExtendedBaseTask
  implements CutOffTaskDefines
{
  private String GD;
  
  public SetScheduleType()
  {
    this.collectionSessionName = "ScheduleCategory";
    this.beanSessionName = "ScheduleType";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if ((this.GD == null) || (this.GD.length() == 0))
    {
      this.error = 26003;
      str = this.taskErrorURL;
    }
    ScheduleCategory localScheduleCategory = (ScheduleCategory)localHttpSession.getAttribute(this.collectionSessionName);
    if (localScheduleCategory == null)
    {
      this.error = 26002;
      str = this.taskErrorURL;
    }
    ScheduleTypes localScheduleTypes = localScheduleCategory.getScheduleTypes();
    ScheduleType localScheduleType = (ScheduleType)localScheduleTypes.getFirstByFilter("InstructionType=" + this.GD);
    if (localScheduleType == null)
    {
      this.error = 26012;
      str = this.taskErrorURL;
    }
    localHttpSession.setAttribute(this.beanSessionName, localScheduleType);
    return str;
  }
  
  public String getInstructionType()
  {
    return this.GD;
  }
  
  public void setInstructionType(String paramString)
  {
    this.GD = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.SetScheduleType
 * JD-Core Version:    0.7.0.1
 */