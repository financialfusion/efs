package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.ach.ACHCompanySummaries;
import com.ffusion.beans.ach.ACHCompanySummary;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetACHCompanySummaries
  extends BaseTask
  implements Task
{
  protected String companiesName = "ACHCOMPANIES";
  protected String batchType = "ACHBatch";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str;
    if (getACHCompanySummaries(localHttpSession)) {
      str = this.successURL;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean getACHCompanySummaries(HttpSession paramHttpSession)
  {
    ACHCompanies localACHCompanies = (ACHCompanies)paramHttpSession.getAttribute(this.companiesName);
    if (localACHCompanies == null)
    {
      this.error = 16505;
      return false;
    }
    ACHCompany localACHCompany = null;
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = localACHCompanies.iterator();
    while (localIterator.hasNext())
    {
      localACHCompany = (ACHCompany)localIterator.next();
      localArrayList.add(localACHCompany.getID());
    }
    ACHCompanySummaries localACHCompanySummaries = null;
    HashMap localHashMap = new HashMap();
    localHashMap.put("BatchType", this.batchType);
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    Object localObject;
    if (localArrayList.size() > 0)
    {
      localObject = (String[])localArrayList.toArray(new String[0]);
      try
      {
        localACHCompanySummaries = ACH.getACHCompanySummaries(localSecureUser, (String[])localObject, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
    }
    if ((this.error == 0) && (localACHCompanySummaries != null))
    {
      localIterator = localACHCompanies.iterator();
      localObject = null;
      while (localIterator.hasNext())
      {
        localACHCompany = (ACHCompany)localIterator.next();
        localObject = localACHCompanySummaries.getByCompanyID(localACHCompany.getID());
        localACHCompany.setCompanySummary((ACHCompanySummary)localObject);
      }
    }
    return this.error == 0;
  }
  
  public final void setCompaniesInSessionName(String paramString)
  {
    this.companiesName = paramString;
  }
  
  public final void setBatchType(String paramString)
  {
    this.batchType = paramString;
  }
  
  public final String getBatchType()
  {
    return this.batchType;
  }
  
  public final String getCompaniesInSessionName()
  {
    return this.companiesName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetACHCompanySummaries
 * JD-Core Version:    0.7.0.1
 */