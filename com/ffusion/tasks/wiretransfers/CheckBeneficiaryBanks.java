package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferBanks;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckBeneficiaryBanks
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected String buttonSessionName = "buttonName";
  protected String initialButtonName;
  protected String intermediaryButtonName;
  protected String receivingButtonName;
  protected boolean initializingTask = false;
  
  public CheckBeneficiaryBanks()
  {
    this.beanSessionName = "WireTransferPayee";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    WireTransferPayee localWireTransferPayee = (WireTransferPayee)localHttpSession.getAttribute(this.beanSessionName);
    if (localWireTransferPayee == null)
    {
      this.error = 12016;
      return this.taskErrorURL;
    }
    String str2 = "";
    WireTransferBank localWireTransferBank1 = localWireTransferPayee.getDestinationBank();
    WireTransferBanks localWireTransferBanks = localWireTransferPayee.getIntermediaryBanks();
    if (this.initializingTask == true)
    {
      str2 = this.initialButtonName;
      this.initializingTask = false;
    }
    else if (localWireTransferBank1 == null)
    {
      str2 = this.initialButtonName;
    }
    else if (localWireTransferBank1.getRoutingNumber().length() > 0)
    {
      str2 = this.intermediaryButtonName;
    }
    else if ((localWireTransferBanks == null) || (localWireTransferBanks.size() == 0))
    {
      str2 = this.receivingButtonName;
    }
    else
    {
      int i = 1;
      for (int j = 0; j < localWireTransferBanks.size(); j++)
      {
        WireTransferBank localWireTransferBank2 = (WireTransferBank)localWireTransferBanks.get(j);
        if (localWireTransferBank2.getRoutingNumber().length() > 0)
        {
          i = 0;
          break;
        }
      }
      if (i == 1) {
        str2 = this.receivingButtonName;
      } else {
        str2 = this.intermediaryButtonName;
      }
    }
    localHttpSession.setAttribute(this.buttonSessionName, str2);
    return str1;
  }
  
  public void setButtonSessionName(String paramString)
  {
    this.buttonSessionName = paramString;
  }
  
  public void setInitialButtonName(String paramString)
  {
    this.initialButtonName = paramString;
  }
  
  public void setIntermediaryButtonName(String paramString)
  {
    this.intermediaryButtonName = paramString;
  }
  
  public void setReceivingButtonName(String paramString)
  {
    this.receivingButtonName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.CheckBeneficiaryBanks
 * JD-Core Version:    0.7.0.1
 */