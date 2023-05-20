package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CancelTransfer
  extends BaseTask
  implements Task
{
  private Transfer x3;
  protected String beanSessionName = "Transfer";
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)localHttpSession.getAttribute(this.bankingServiceName);
    this.x3 = ((Transfer)localHttpSession.getAttribute(this.beanSessionName));
    if (this.x3 == null)
    {
      this.error = 1004;
      str = this.taskErrorURL;
    }
    else
    {
      this.error = 0;
      Object localObject;
      SecureUser localSecureUser;
      if ((this.x3 instanceof RecTransfer))
      {
        localObject = null;
        if (localBanking != null)
        {
          localObject = new HashMap();
          ((HashMap)localObject).put("SERVICE", localBanking);
        }
        localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        try
        {
          if ("RECTEMPLATE".equals(this.x3.getTransferType()))
          {
            com.ffusion.csil.core.Banking.deleteRecTransferTemplate(localSecureUser, (RecTransfer)this.x3, (HashMap)localObject);
          }
          else
          {
            RecTransfer localRecTransfer = com.ffusion.csil.core.Banking.cancelRecTransfer(localSecureUser, (RecTransfer)this.x3, (HashMap)localObject);
            ((RecTransfer)this.x3).set(localRecTransfer);
          }
        }
        catch (CSILException localCSILException1)
        {
          this.error = MapError.mapError(localCSILException1);
        }
      }
      else
      {
        localObject = null;
        if (localBanking != null)
        {
          localObject = new HashMap();
          ((HashMap)localObject).put("SERVICE", localBanking);
        }
        localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        try
        {
          if ("TEMPLATE".equals(this.x3.getTransferType()))
          {
            com.ffusion.csil.core.Banking.deleteTransferTemplate(localSecureUser, this.x3, (HashMap)localObject);
          }
          else
          {
            Transfer localTransfer = com.ffusion.csil.core.Banking.cancelTransfer(localSecureUser, this.x3, (HashMap)localObject);
            this.x3.set(localTransfer);
          }
        }
        catch (CSILException localCSILException2)
        {
          this.error = MapError.mapError(localCSILException2);
        }
      }
      if (this.error == 0)
      {
        localHttpSession.setAttribute("TransfersUpdated", "true");
        if ((this.x3 instanceof RecTransfer))
        {
          localObject = (RecTransfers)localHttpSession.getAttribute("RecTransfers");
          if (localObject != null) {
            ((RecTransfers)localObject).remove(this.x3);
          }
        }
        else
        {
          localObject = (Transfers)localHttpSession.getAttribute("Transfers");
          if (localObject != null) {
            ((Transfers)localObject).remove(this.x3);
          }
        }
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
 * Qualified Name:     com.ffusion.tasks.banking.CancelTransfer
 * JD-Core Version:    0.7.0.1
 */