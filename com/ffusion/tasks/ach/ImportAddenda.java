package com.ffusion.tasks.ach;

import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ImportAddenda
  extends BaseTask
  implements Task
{
  protected String fileName;
  protected String entryName = "ACHEntry";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    try
    {
      System.out.println("Start ImportAddenda");
      ServletContext localServletContext = paramHttpServlet.getServletContext();
      String str = localServletContext.getRealPath("/");
      System.out.println("Docroot: " + str);
      if ((str.charAt(str.length() - 1) == '\\') || (str.charAt(str.length() - 1) == '/')) {
        str = str.substring(0, str.length() - 1);
      }
      importFile(paramHttpServletRequest, paramHttpServletResponse);
      return this.successURL;
    }
    catch (Exception localException)
    {
      System.out.println("UploadFile Exception: " + localException.getMessage());
    }
    return this.taskErrorURL;
  }
  
  public void setFileName(String paramString)
  {
    this.fileName = paramString;
  }
  
  public void importFile(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    ServletInputStream localServletInputStream = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      String str1 = paramHttpServletRequest.getContentType();
      if ((str1 != null) && (str1.indexOf("multipart/form-data") != -1))
      {
        localServletInputStream = paramHttpServletRequest.getInputStream();
        int i = paramHttpServletRequest.getContentLength();
        byte[] arrayOfByte = new byte[i];
        int j = 0;
        int k = 0;
        int m = 0;
        while (k < i)
        {
          j = localServletInputStream.readLine(arrayOfByte, k, i);
          if (j == -1) {
            break;
          }
          k += j;
        }
        System.out.println("ImportFile: Total bytes read: " + k);
        String str2 = new String(arrayOfByte, "ISO8859_1");
        arrayOfByte = null;
        int n = str1.lastIndexOf("=");
        String str3 = str1.substring(n + 1, str1.length());
        String str4 = str2.substring(str2.indexOf("filename=\"") + 10);
        str4 = str4.substring(0, str4.indexOf("\n"));
        str4 = str4.substring(str4.lastIndexOf("\\") + 1, str4.indexOf("\""));
        int i1 = str2.indexOf("filename=\"");
        i1 = str2.indexOf("\n", i1) + 1;
        i1 = str2.indexOf("\n", i1) + 1;
        i1 = str2.indexOf("\n", i1) + 1;
        int i2 = str2.indexOf(str3, i1) - 4;
        str2 = str2.substring(i1, i2);
        StringBuffer localStringBuffer = new StringBuffer();
        ACHEntry localACHEntry = (ACHEntry)localHttpSession.getAttribute(this.entryName);
        if (localACHEntry != null)
        {
          int i3 = 0;
          for (i1 = str2.indexOf("\n"); i1 >= 0; i1 = str2.indexOf("\n", i3))
          {
            if (i1 > 0)
            {
              String str5 = str2.substring(i3, i1 - 1);
              localStringBuffer.append(str5);
            }
            i3 = i1 + 1;
          }
          if (i3 != str2.length()) {
            localStringBuffer.append(str2.substring(i3));
          }
          localACHEntry.setAddendaString(localStringBuffer.toString());
        }
        return;
      }
      System.out.println("Request not multipart/form-data.");
      return;
    }
    catch (Exception localException1)
    {
      try
      {
        System.out.println("Exception Error: " + localException1.getMessage());
        localException1.printStackTrace(System.out);
        System.out.println("Error in doPost: " + localException1);
        System.out.println("An unexpected error has occurred.");
        System.out.println("Error description: " + localException1);
      }
      catch (Exception localException3) {}
      return;
    }
    finally
    {
      try
      {
        localServletInputStream.close();
      }
      catch (Exception localException6) {}
    }
  }
  
  public final void setEntryName(String paramString)
  {
    this.entryName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.ImportAddenda
 * JD-Core Version:    0.7.0.1
 */