package com.ffusion.tasks.reporting;

import com.ffusion.tasks.BaseTask;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RetrieveFile
  extends BaseTask
  implements ReportingConsts
{
  private String TK;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
    BufferedReader localBufferedReader = new BufferedReader(new FileReader(this.TK));
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 10240;
    for (;;)
    {
      char[] arrayOfChar = new char[i];
      int j = localBufferedReader.read(arrayOfChar, 0, i);
      if (j == -1) {
        break;
      }
      localPrintWriter.write(arrayOfChar);
    }
    localPrintWriter.flush();
    return super.getSuccessURL();
  }
  
  public void setFileName(String paramString)
  {
    this.TK = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.RetrieveFile
 * JD-Core Version:    0.7.0.1
 */