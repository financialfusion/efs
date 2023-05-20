package com.ffusion.tasks.ach;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetACHBatch
  extends BaseTask
  implements Task
{
  protected String batchName = "ACHBatch";
  protected String batchID = null;
  protected String batchesName = "ACHBatches";
  protected String listPosition = null;
  protected boolean viewModelDate = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    DateTime localDateTime = null;
    ACHBatch localACHBatch = null;
    Object localObject2;
    if (this.listPosition != null) {
      try
      {
        int i = Integer.parseInt(this.listPosition);
        localObject2 = (ACHBatches)localHttpSession.getAttribute(this.batchesName);
        if (localObject2 != null) {
          localACHBatch = (ACHBatch)((ACHBatches)localObject2).get(i);
        }
      }
      catch (Exception localException) {}
    }
    Object localObject1;
    if ((localACHBatch == null) && (this.batchID != null) && (this.batchID.length() > 0))
    {
      localObject1 = (ACHBatches)localHttpSession.getAttribute(this.batchesName);
      if (localObject1 != null) {
        localACHBatch = ((ACHBatches)localObject1).getByID(this.batchID);
      }
    }
    if (localACHBatch == null) {
      localACHBatch = (ACHBatch)localHttpSession.getAttribute(this.batchName);
    }
    if (localACHBatch == null)
    {
      this.error = 16002;
      str = this.taskErrorURL;
    }
    else
    {
      localObject1 = new HashMap();
      if (this.viewModelDate) {
        localDateTime = localACHBatch.getDateValue();
      }
      localObject2 = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        ACHPayees localACHPayees = (ACHPayees)localHttpSession.getAttribute("ACHPayees");
        ((HashMap)localObject1).put("ACHPayees", localACHPayees);
        localACHBatch = ACH.getACHBatch((SecureUser)localObject2, localACHBatch, (HashMap)localObject1);
        if ((this.viewModelDate) && (localDateTime != null)) {
          localACHBatch.setDate(localDateTime);
        }
        localHttpSession.setAttribute(this.batchName, localACHBatch);
        localHttpSession.setAttribute("ACHEntries", localACHBatch.getACHEntries());
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException, localHttpSession);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public final void setBatchID(String paramString)
  {
    this.batchID = paramString;
  }
  
  public final void setBatchName(String paramString)
  {
    this.batchName = paramString;
  }
  
  public final String getBatchName()
  {
    return this.batchName;
  }
  
  public final void setBatchesName(String paramString)
  {
    this.batchesName = paramString;
  }
  
  public final void setListPosition(String paramString)
  {
    this.listPosition = paramString;
  }
  
  public String getViewModelDate()
  {
    return "" + this.viewModelDate;
  }
  
  public void setViewModelDate(String paramString)
  {
    this.viewModelDate = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetACHBatch
 * JD-Core Version:    0.7.0.1
 */