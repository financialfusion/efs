package com.ffusion.services;

import com.ffusion.beans.portal.NewsHeadline;
import com.ffusion.beans.portal.Stock;
import java.io.Serializable;
import java.util.ArrayList;

public abstract interface PortalContentProvider
  extends Serializable
{
  public abstract ArrayList getHeadlines(Stock paramStock);
  
  public abstract NewsHeadline getNews(String paramString)
    throws Throwable;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.PortalContentProvider
 * JD-Core Version:    0.7.0.1
 */