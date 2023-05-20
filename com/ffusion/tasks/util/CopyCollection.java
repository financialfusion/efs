package com.ffusion.tasks.util;

import com.ffusion.tasks.BaseTask;
import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CopyCollection
  extends BaseTask
{
  private String Rq = "";
  private String Rr = "";
  private boolean Rp = true;
  protected boolean _preserveCollectionType = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    Collection localCollection = (Collection)localHttpSession.getAttribute(this.Rq);
    Object localObject1 = (Collection)localHttpSession.getAttribute(this.Rr);
    if (localCollection != null)
    {
      int i = 0;
      if (localObject1 == null)
      {
        i = 1;
        if (this._preserveCollectionType)
        {
          localObject2 = localCollection.getClass();
          try
          {
            localObject1 = (Collection)((Class)localObject2).newInstance();
            if ((localCollection instanceof Localeable))
            {
              Locale localLocale = ((Localeable)localCollection).getLocale();
              ((Localeable)localObject1).setLocale(localLocale);
            }
          }
          catch (Exception localException)
          {
            localObject1 = new FilteredList();
          }
        }
        else
        {
          localObject1 = new FilteredList();
        }
      }
      if (this.Rp) {
        ((Collection)localObject1).clear();
      }
      Object localObject2 = localCollection.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        Object localObject3 = ((Iterator)localObject2).next();
        ((Collection)localObject1).add(localObject3);
      }
      if (i != 0) {
        localHttpSession.setAttribute(this.Rr, localObject1);
      }
    }
    else
    {
      this.error = 51;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setCollectionSource(String paramString)
  {
    this.Rq = paramString;
  }
  
  public void setCollectionDestination(String paramString)
  {
    this.Rr = paramString;
  }
  
  public void setClearDestinationCollection(String paramString)
  {
    this.Rp = new Boolean(paramString).booleanValue();
  }
  
  public void setPreserveCollectionType(String paramString)
  {
    this._preserveCollectionType = new Boolean(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.CopyCollection
 * JD-Core Version:    0.7.0.1
 */