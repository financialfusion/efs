package com.ffusion.tasks.portal;

import com.ffusion.beans.portal.Advisor;
import com.ffusion.beans.portal.Advisors;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditAdvisors
  extends EditPortal
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Advisors localAdvisors = (Advisors)localHttpSession.getAttribute("AdvisorsEdit");
    if (localAdvisors == null)
    {
      this.error = 9003;
      str = this.taskErrorURL;
    }
    else
    {
      localHttpSession.setAttribute("AdvisorsSettings", localAdvisors);
      if (!processShowItem(paramHttpServletRequest, "ADVISORS"))
      {
        this.error = 9009;
        str = this.taskErrorURL;
      }
      else
      {
        e(localHttpSession);
      }
    }
    return str;
  }
  
  private void e(HttpSession paramHttpSession)
  {
    Advisors localAdvisors1 = (Advisors)paramHttpSession.getAttribute("AdvisorsSettings");
    if (localAdvisors1 != null)
    {
      Advisors localAdvisors2 = (Advisors)paramHttpSession.getAttribute("AdvisorsMaster");
      Advisors localAdvisors3 = new Advisors();
      paramHttpSession.setAttribute("Advisors", localAdvisors3);
      Iterator localIterator = localAdvisors1.iterator();
      while (localIterator.hasNext())
      {
        Advisor localAdvisor1 = (Advisor)localIterator.next();
        Advisor localAdvisor2 = localAdvisors2.getAdvisor(localAdvisor1.getCategory());
        if (localAdvisor2 != null) {
          localAdvisors3.add(localAdvisor2);
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.EditAdvisors
 * JD-Core Version:    0.7.0.1
 */