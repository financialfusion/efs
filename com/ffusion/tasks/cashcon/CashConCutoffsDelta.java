package com.ffusion.tasks.cashcon;

import com.ffusion.beans.affiliatebank.CutOffTime;
import com.ffusion.beans.affiliatebank.CutOffTimes;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CashConCutoffsDelta
  extends BaseTask
  implements Task
{
  String z4;
  String z3;
  String z2;
  private String z1 = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    CutOffTimes localCutOffTimes1 = (CutOffTimes)localHttpSession.getAttribute(this.z4);
    CutOffTimes localCutOffTimes2 = (CutOffTimes)localHttpSession.getAttribute(this.z3);
    if (localCutOffTimes1 != null)
    {
      CutOffTimes localCutOffTimes3 = (CutOffTimes)localCutOffTimes1.clone();
      if (localCutOffTimes2 != null)
      {
        Iterator localIterator1 = localCutOffTimes1.iterator();
        CutOffTime localCutOffTime1 = null;
        while (localIterator1.hasNext())
        {
          localCutOffTime1 = (CutOffTime)localIterator1.next();
          Iterator localIterator2 = localCutOffTimes2.iterator();
          while (localIterator2.hasNext())
          {
            CutOffTime localCutOffTime2 = (CutOffTime)localIterator2.next();
            if (localCutOffTime1.getCutOffId().equals(localCutOffTime2.getCutOffId())) {
              localCutOffTimes3.remove(localCutOffTime1);
            }
          }
        }
      }
      localHttpSession.setAttribute(this.z2, localCutOffTimes3);
    }
    else
    {
      this.error = 0;
      str = this.taskErrorURL;
      this.z1 = this.taskErrorURL;
    }
    return str;
  }
  
  public void setPrimaryName(String paramString)
  {
    this.z4 = paramString;
  }
  
  public void setSecondaryName(String paramString)
  {
    this.z3 = paramString;
  }
  
  public void setDeltaName(String paramString)
  {
    this.z2 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.CashConCutoffsDelta
 * JD-Core Version:    0.7.0.1
 */