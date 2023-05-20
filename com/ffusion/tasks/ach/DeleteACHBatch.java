package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHBatches;
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

public class DeleteACHBatch
  extends BaseTask
  implements Task
{
  protected String batchesName = "ACHBatches";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ACHBatch localACHBatch = (ACHBatch)localHttpSession.getAttribute("ACHBatch");
    if (localACHBatch == null)
    {
      this.error = 16002;
      str = this.taskErrorURL;
    }
    else
    {
      HashMap localHashMap;
      SecureUser localSecureUser;
      if (localACHBatch.getFrequencyValue() != 0)
      {
        localHashMap = new HashMap();
        localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        try
        {
          ACH.deleteRecACHBatch(localSecureUser, localACHBatch, localHashMap);
        }
        catch (CSILException localCSILException1)
        {
          this.error = MapError.mapError(localCSILException1, localHttpSession);
          str = this.serviceErrorURL;
        }
        if (this.error == 0)
        {
          ACHBatches localACHBatches1 = (ACHBatches)localHttpSession.getAttribute(this.batchesName);
          if (localACHBatches1 == null)
          {
            this.error = 16011;
            str = this.taskErrorURL;
          }
          else
          {
            localACHBatches1.remove(localACHBatch);
          }
          str = this.successURL;
        }
      }
      else
      {
        localHashMap = new HashMap();
        localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        try
        {
          ACH.deleteACHBatch(localSecureUser, localACHBatch, localHashMap);
        }
        catch (CSILException localCSILException2)
        {
          this.error = MapError.mapError(localCSILException2, localHttpSession);
          str = this.serviceErrorURL;
        }
        if (this.error == 0)
        {
          ACHBatches localACHBatches2 = (ACHBatches)localHttpSession.getAttribute(this.batchesName);
          if (localACHBatches2 == null)
          {
            this.error = 16010;
            str = this.taskErrorURL;
          }
          else
          {
            localACHBatches2.remove(localACHBatch);
          }
          str = this.successURL;
        }
      }
    }
    return str;
  }
  
  public final void setBatchesName(String paramString)
  {
    this.batchesName = paramString;
  }
  
  public final String getBatchesName()
  {
    return this.batchesName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.DeleteACHBatch
 * JD-Core Version:    0.7.0.1
 */