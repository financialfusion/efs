package com.ffusion.tasks.util;

import com.ffusion.beans.CollectionElement;
import com.ffusion.beans.IdCollection;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TransferCollectionElements
  extends BaseTask
{
  private String QH;
  private String QG;
  private String QF;
  private String QE;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    if ((this.QH == null) || (this.QG == null))
    {
      this.error = 51;
      str = this.taskErrorURL;
    }
    else if ((this.QF != null) && (this.QE != null))
    {
      IdCollection localIdCollection1 = (IdCollection)localHttpSession.getAttribute(this.QH);
      IdCollection localIdCollection2 = (IdCollection)localHttpSession.getAttribute(this.QG);
      if ((localIdCollection1 == null) || (localIdCollection2 == null))
      {
        this.error = 51;
        str = this.taskErrorURL;
      }
      else
      {
        ArrayList localArrayList1 = s(this.QF);
        ArrayList localArrayList2 = s(this.QE);
        jdMethod_for(localArrayList1, localIdCollection1, localIdCollection2);
        jdMethod_for(localArrayList2, localIdCollection2, localIdCollection1);
        jdMethod_for(localArrayList1, localIdCollection1);
        jdMethod_for(localArrayList2, localIdCollection2);
      }
    }
    return str;
  }
  
  private void jdMethod_for(ArrayList paramArrayList, IdCollection paramIdCollection1, IdCollection paramIdCollection2)
  {
    Iterator localIterator1 = paramArrayList.iterator();
    Iterator localIterator2 = null;
    String str1 = "";
    String str2 = "";
    CollectionElement localCollectionElement = null;
    while (localIterator1.hasNext())
    {
      str1 = (String)localIterator1.next();
      int i = 0;
      localIterator2 = paramIdCollection1.iterator();
      while (localIterator2.hasNext())
      {
        localCollectionElement = (CollectionElement)localIterator2.next();
        str2 = localCollectionElement.getId();
        if (str2.equals(str1)) {
          i = 1;
        }
      }
      if (i == 0) {
        paramIdCollection1.add(paramIdCollection2.getElementByID(str1));
      }
    }
  }
  
  private void jdMethod_for(ArrayList paramArrayList, IdCollection paramIdCollection)
  {
    Iterator localIterator1 = null;
    Iterator localIterator2 = paramIdCollection.iterator();
    CollectionElement localCollectionElement = null;
    String str1 = "";
    String str2 = "";
    while (localIterator2.hasNext())
    {
      localCollectionElement = (CollectionElement)localIterator2.next();
      str1 = localCollectionElement.getId();
      int i = 0;
      localIterator1 = paramArrayList.iterator();
      while (localIterator1.hasNext())
      {
        str2 = (String)localIterator1.next();
        if (str2.equals(str1)) {
          i = 1;
        }
      }
      if (i == 0) {
        localIterator2.remove();
      }
    }
  }
  
  private ArrayList s(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = paramString.indexOf(","); i != -1; i = paramString.indexOf(","))
    {
      String str = paramString.substring(0, i);
      localArrayList.add(new String(str));
      paramString = paramString.substring(i + 1, paramString.length());
    }
    if ((!paramString.equals("")) && (!paramString.equals("-1"))) {
      localArrayList.add(new String(paramString));
    }
    return localArrayList;
  }
  
  public void setCollectionName1(String paramString)
  {
    this.QH = paramString;
  }
  
  public void setCollectionName2(String paramString)
  {
    this.QG = paramString;
  }
  
  public void setCollectionIds1(String paramString)
  {
    this.QF = paramString;
  }
  
  public void setCollectionIds2(String paramString)
  {
    this.QE = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.TransferCollectionElements
 * JD-Core Version:    0.7.0.1
 */