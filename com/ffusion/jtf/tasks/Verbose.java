package com.ffusion.jtf.tasks;

import com.ffusion.jtf.JTF;
import com.ffusion.jtf.Task;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Verbose
  implements Task
{
  private static final String a59 = "Usage: .../Verbose?Level=&lt;all|finest|finer|fine|config|info|warning|severe|off&gt;";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    if ((paramHttpServlet instanceof JTF))
    {
      JTF localJTF = (JTF)paramHttpServlet;
      String str1 = paramHttpServletRequest.getParameter("Level");
      String str2 = "Usage: .../Verbose?Level=&lt;all|finest|finer|fine|config|info|warning|severe|off&gt;";
      if (setLogLevel(paramHttpServletRequest.getParameter("Level")) == true) {
        str2 = "Logging set to " + str1;
      }
      PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
      localPrintWriter.println(str2);
      localPrintWriter.flush();
    }
    return "";
  }
  
  public static boolean setLogLevel(String paramString)
  {
    boolean bool = true;
    if ((paramString == null) || (paramString.length() == 0)) {
      bool = false;
    } else if (paramString.equalsIgnoreCase("ALL")) {
      DebugLog.setLevel(Level.ALL);
    } else if (paramString.equalsIgnoreCase("FINEST")) {
      DebugLog.setLevel(Level.FINEST);
    } else if (paramString.equalsIgnoreCase("FINER")) {
      DebugLog.setLevel(Level.FINER);
    } else if (paramString.equalsIgnoreCase("FINE")) {
      DebugLog.setLevel(Level.FINE);
    } else if (paramString.equalsIgnoreCase("CONFIG")) {
      DebugLog.setLevel(Level.CONFIG);
    } else if (paramString.equalsIgnoreCase("INFO")) {
      DebugLog.setLevel(Level.INFO);
    } else if (paramString.equalsIgnoreCase("WARNING")) {
      DebugLog.setLevel(Level.WARNING);
    } else if (paramString.equalsIgnoreCase("SEVERE")) {
      DebugLog.setLevel(Level.SEVERE);
    } else if (paramString.equalsIgnoreCase("OFF")) {
      DebugLog.setLevel(Level.OFF);
    } else {
      bool = false;
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.tasks.Verbose
 * JD-Core Version:    0.7.0.1
 */