package com.ffusion.tasks.istatements;

import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.beans.istatements.Statement;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddStmtImages
  extends BaseTask
  implements StatementTask
{
  private String Om;
  private String Oo = "Statement";
  private String On = "AvailableImages";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.Om = this.taskErrorURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      ImageResults localImageResults1 = (ImageResults)localHttpSession.getAttribute(this.On);
      Statement localStatement = (Statement)localHttpSession.getAttribute(this.Oo);
      ImageResults localImageResults2 = localStatement.getImageResults();
      if (localImageResults2 == null) {
        localImageResults2 = new ImageResults();
      }
      if ((localImageResults1 != null) && (localImageResults1.size() != 0))
      {
        Iterator localIterator = localImageResults1.iterator();
        while (localIterator.hasNext()) {
          localImageResults2.add(localIterator.next());
        }
      }
      localStatement.setImageResults(localImageResults2);
      localHttpSession.setAttribute(this.Oo, localStatement);
      this.Om = this.successURL;
    }
    catch (Exception localException)
    {
      this.error = 36209;
      this.Om = this.taskErrorURL;
    }
    return this.Om;
  }
  
  public final void setStatementInSessionName(String paramString)
  {
    this.Oo = paramString;
  }
  
  public final void setImageResultsInSessionName(String paramString)
  {
    this.On = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.istatements.AddStmtImages
 * JD-Core Version:    0.7.0.1
 */