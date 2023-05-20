package com.ffusion.tasks.util;

import com.ffusion.beans.IdCollection;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AssignCollectionElements
  extends BaseTask
{
  private static final String QX = "SESSION";
  private static final String Q0 = "CONTEXT";
  private String QW = "SESSION";
  private String QZ = "";
  private String QY = "";
  private ArrayList QV;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    IdCollection localIdCollection = null;
    Object localObject;
    if (this.QW.equals("CONTEXT"))
    {
      localObject = paramHttpServlet.getServletContext();
      localIdCollection = (IdCollection)((ServletContext)localObject).getAttribute(this.QZ);
    }
    else
    {
      localIdCollection = (IdCollection)localHttpSession.getAttribute(this.QZ);
    }
    if (localIdCollection != null)
    {
      localObject = (Collection)localHttpSession.getAttribute(this.QY);
      if (localObject != null)
      {
        ((Collection)localObject).clear();
        if (this.QV != null)
        {
          Iterator localIterator = this.QV.iterator();
          while (localIterator.hasNext())
          {
            String str2 = (String)localIterator.next();
            ((Collection)localObject).add(localIdCollection.getElementByID(str2));
          }
        }
      }
      else
      {
        this.error = 51;
        str1 = this.taskErrorURL;
      }
    }
    else
    {
      this.error = 51;
      str1 = this.taskErrorURL;
    }
    return str1;
  }
  
  public void setCollectionToUpdate(String paramString)
  {
    this.QY = paramString;
  }
  
  public void setCollectionSource(String paramString)
  {
    this.QZ = paramString;
  }
  
  public void setCollectionSourceLocation(String paramString)
  {
    this.QW = paramString;
  }
  
  public void setCollectionIds(String paramString)
  {
    this.QV = new ArrayList();
    for (int i = paramString.indexOf(","); i != -1; i = paramString.indexOf(","))
    {
      String str = paramString.substring(0, i);
      this.QV.add(new String(str));
      paramString = paramString.substring(i + 1, paramString.length());
    }
    if ((!paramString.equals("")) && (!paramString.equals("-1"))) {
      this.QV.add(new String(paramString));
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.AssignCollectionElements
 * JD-Core Version:    0.7.0.1
 */