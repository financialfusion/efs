package com.ffusion.tasks.portal;

import com.ffusion.beans.portal.Calculator;
import com.ffusion.beans.portal.CalculatorCategories;
import com.ffusion.beans.portal.Calculators;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditCalculators
  extends EditPortal
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Calculators localCalculators = (Calculators)localHttpSession.getAttribute("CalculatorsEdit");
    if (localCalculators == null)
    {
      this.error = 9004;
      return this.taskErrorURL;
    }
    localHttpSession.setAttribute("CalculatorsSettings", localCalculators);
    if (!processShowItem(paramHttpServletRequest, "CALCULATORS"))
    {
      this.error = 9009;
      return this.taskErrorURL;
    }
    d(localHttpSession);
    return this.successURL;
  }
  
  private void d(HttpSession paramHttpSession)
  {
    Calculators localCalculators1 = (Calculators)paramHttpSession.getAttribute("CalculatorsSettings");
    if (localCalculators1 == null) {
      return;
    }
    CalculatorCategories localCalculatorCategories = (CalculatorCategories)paramHttpSession.getAttribute("CalculatorsMaster");
    Calculators localCalculators2 = new Calculators();
    paramHttpSession.setAttribute("Calculators", localCalculators2);
    Iterator localIterator = localCalculators1.iterator();
    while (localIterator.hasNext())
    {
      Calculator localCalculator1 = (Calculator)localIterator.next();
      Calculator localCalculator2 = localCalculatorCategories.getCalculator(localCalculator1.getID());
      if (localCalculator2 != null) {
        localCalculators2.add(localCalculator2);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.EditCalculators
 * JD-Core Version:    0.7.0.1
 */