package com.ffusion.tasks.util;

import com.ffusion.jtf.Task;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckSerializable
  implements Task
{
  private String c;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    FileOutputStream localFileOutputStream = null;
    try
    {
      localFileOutputStream = new FileOutputStream(this.c);
      ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localFileOutputStream);
      ArrayList localArrayList1 = new ArrayList();
      ArrayList localArrayList2 = new ArrayList();
      jdMethod_for(paramHttpServlet.getServletContext(), localObjectOutputStream, localArrayList1, localArrayList2);
      jdMethod_for(paramHttpServletRequest.getSession(), localObjectOutputStream, localArrayList1, localArrayList2);
      jdMethod_for(paramHttpServletResponse, localArrayList1, localArrayList2);
    }
    catch (Throwable localThrowable)
    {
      paramHttpServlet.log("CheckSerializable " + localThrowable);
    }
    finally
    {
      if (localFileOutputStream != null) {
        localFileOutputStream.close();
      }
    }
    return "";
  }
  
  private void jdMethod_for(ServletContext paramServletContext, ObjectOutputStream paramObjectOutputStream, ArrayList paramArrayList1, ArrayList paramArrayList2)
  {
    Enumeration localEnumeration = paramServletContext.getAttributeNames();
    if (localEnumeration != null) {
      while (localEnumeration.hasMoreElements())
      {
        Object localObject = null;
        try
        {
          String str = (String)localEnumeration.nextElement();
          localObject = paramServletContext.getAttribute(str);
          paramObjectOutputStream.writeObject(localObject);
          if (!paramArrayList1.contains(localObject.getClass().toString())) {
            paramArrayList1.add(localObject.getClass().toString());
          }
        }
        catch (Throwable localThrowable)
        {
          if (!paramArrayList2.contains(localObject.getClass().toString())) {
            paramArrayList2.add(localObject.getClass().toString());
          }
        }
      }
    }
  }
  
  private void jdMethod_for(HttpSession paramHttpSession, ObjectOutputStream paramObjectOutputStream, ArrayList paramArrayList1, ArrayList paramArrayList2)
  {
    Enumeration localEnumeration = paramHttpSession.getAttributeNames();
    if (localEnumeration != null) {
      while (localEnumeration.hasMoreElements())
      {
        Object localObject = null;
        try
        {
          String str = (String)localEnumeration.nextElement();
          localObject = paramHttpSession.getAttribute(str);
          paramObjectOutputStream.writeObject(localObject);
          if (!paramArrayList1.contains(localObject.getClass().toString())) {
            paramArrayList1.add(localObject.getClass().toString());
          }
        }
        catch (Throwable localThrowable)
        {
          if (!paramArrayList2.contains(localObject.getClass().toString())) {
            paramArrayList2.add(localObject.getClass().toString());
          }
        }
      }
    }
  }
  
  private void jdMethod_for(HttpServletResponse paramHttpServletResponse, ArrayList paramArrayList1, ArrayList paramArrayList2)
    throws IOException
  {
    paramHttpServletResponse.setContentType("text/html; charset=UTF-8");
    PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
    localPrintWriter.print("Not Serializable Classes:");
    Iterator localIterator = paramArrayList2.iterator();
    while (localIterator.hasNext()) {
      localPrintWriter.print("<br>" + localIterator.next());
    }
    localPrintWriter.print("<br><br>Serializable Classes:");
    localIterator = paramArrayList1.iterator();
    while (localIterator.hasNext()) {
      localPrintWriter.print("<br>" + localIterator.next());
    }
    paramHttpServletResponse.getWriter().flush();
  }
  
  public void setURL(String paramString)
  {
    this.c = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.CheckSerializable
 * JD-Core Version:    0.7.0.1
 */