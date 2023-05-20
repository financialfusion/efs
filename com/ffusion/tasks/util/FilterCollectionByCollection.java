package com.ffusion.tasks.util;

import com.ffusion.beans.CollectionElement;
import com.ffusion.beans.IdCollection;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FilterCollectionByCollection
  extends BaseTask
{
  private static final String Qz = "SESSION";
  private static final String QB = "CONTEXT";
  private String Qy = "SESSION";
  private String Qx = "";
  private String QA = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    IdCollection localIdCollection = null;
    Object localObject;
    if (this.Qy.equals("CONTEXT"))
    {
      localObject = paramHttpServlet.getServletContext();
      localIdCollection = (IdCollection)((ServletContext)localObject).getAttribute(this.Qx);
    }
    else
    {
      localIdCollection = (IdCollection)localHttpSession.getAttribute(this.Qx);
    }
    if (localIdCollection != null)
    {
      localObject = (IdCollection)localHttpSession.getAttribute(this.QA);
      if (localObject != null)
      {
        jdMethod_for(localIdCollection, (Collection)localObject);
      }
      else
      {
        str = this.taskErrorURL;
        this.error = 51;
      }
    }
    else
    {
      str = this.taskErrorURL;
      this.error = 51;
    }
    return str;
  }
  
  private void jdMethod_for(IdCollection paramIdCollection, Collection paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    int i = paramCollection.size();
    while (localIterator.hasNext())
    {
      CollectionElement localCollectionElement = (CollectionElement)localIterator.next();
      paramIdCollection.removeByID(localCollectionElement.getId());
    }
  }
  
  public void setCollectionToFilter(String paramString)
  {
    this.Qx = paramString;
  }
  
  public void setCollectionSource(String paramString)
  {
    this.QA = paramString;
  }
  
  public void setCollectionSourceLocation(String paramString)
  {
    this.Qy = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.FilterCollectionByCollection
 * JD-Core Version:    0.7.0.1
 */