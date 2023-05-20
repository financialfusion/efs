package com.ffusion.jtf.tasks;

import com.ffusion.jtf.JTF;
import com.ffusion.jtf.Task;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetVersion
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
    String str1 = "JTF version: " + JTF.version;
    String str2 = "BPW version: " + JTF.BPWVersion;
    localPrintWriter.println(str1);
    localPrintWriter.println("<br>");
    localPrintWriter.println(str2);
    localPrintWriter.flush();
    paramHttpServlet.log(str1);
    paramHttpServlet.log(str2);
    return "";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.tasks.GetVersion
 * JD-Core Version:    0.7.0.1
 */