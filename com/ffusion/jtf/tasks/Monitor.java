package com.ffusion.jtf.tasks;

import com.ffusion.jtf.JTF;
import com.ffusion.jtf.Task;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Monitor
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    int i = -1;
    String str1 = null;
    if ((paramHttpServlet instanceof JTF))
    {
      JTF localJTF = (JTF)paramHttpServlet;
      String str2 = paramHttpServletRequest.getParameter("ms");
      if ((str2 != null) && (str2.length() > 0)) {
        try
        {
          i = Integer.parseInt(str2);
          str1 = localJTF.getMonitorResults();
          localJTF.setMonitor(i);
        }
        catch (NumberFormatException localNumberFormatException)
        {
          i = -1;
        }
      }
      PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
      i = JTF.getMonitor();
      localPrintWriter.println("<html>");
      if (i == -1)
      {
        localPrintWriter.println("Monitor is off");
      }
      else
      {
        localPrintWriter.println("Monitor is on, threshhold is " + i + "ms<br>");
        localPrintWriter.println("Monitor results<br>");
        if ((str1 != null) && (str1.length() > 0)) {
          localPrintWriter.println(str1);
        }
        localPrintWriter.println(localJTF.getMonitorResults());
      }
      localPrintWriter.flush();
      localJTF.setMonitor(i);
    }
    return "";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.tasks.Monitor
 * JD-Core Version:    0.7.0.1
 */