package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.banking.TransferBatch;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CancelTransferBatch
  extends BaseTask
  implements Task
{
  private TransferBatch er;
  protected String beanSessionName = "TransferBatch";
  protected String collectionSessionName = "TransferTemplates";
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)localHttpSession.getAttribute(this.bankingServiceName);
    this.er = ((TransferBatch)localHttpSession.getAttribute(this.beanSessionName));
    if (this.er == null)
    {
      this.error = 1004;
      str = this.taskErrorURL;
    }
    else
    {
      this.error = 0;
      HashMap localHashMap = null;
      if (localBanking != null)
      {
        localHashMap = new HashMap();
        localHashMap.put("SERVICE", localBanking);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        TransferBatch localTransferBatch = com.ffusion.csil.core.Banking.cancelTransferBatch(localSecureUser, this.er, localHashMap);
        this.er.set(localTransferBatch);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
      if (this.error == 0)
      {
        localHttpSession.setAttribute("TransfersUpdated", "true");
        str = this.successURL;
      }
      else
      {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
  
  public String getBeanSessionName()
  {
    return this.beanSessionName;
  }
  
  public void setBeanSessionName(String paramString)
  {
    this.beanSessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.CancelTransferBatch
 * JD-Core Version:    0.7.0.1
 */