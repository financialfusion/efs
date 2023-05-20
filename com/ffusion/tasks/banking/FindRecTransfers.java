package com.ffusion.tasks.banking;

import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FindRecTransfers
  extends FindTransfers
{
  protected boolean removePending = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    RecTransfers localRecTransfers = (RecTransfers)localHttpSession.getAttribute("RecTransfers");
    if (localRecTransfers == null)
    {
      this.error = 1005;
    }
    else if (this.locale == null)
    {
      this.error = 41;
    }
    else if (!setStartDate())
    {
      this.error = 1021;
    }
    else if (!setEndDate())
    {
      this.error = 1022;
    }
    else
    {
      findTransfers(localRecTransfers);
      str = this.successURL;
    }
    if (this.removePending)
    {
      Transfers localTransfers = (Transfers)localHttpSession.getAttribute("Transfers");
      for (int i = localTransfers.size() - 1; i >= 0; i--)
      {
        Transfer localTransfer = (Transfer)localTransfers.get(i);
        if ((localTransfer.getRecTransferID() != null) && (localRecTransfers.getByID(localTransfer.getRecTransferID()) != null) && (localTransfer.getStatus() == 2)) {
          localTransfers.remove(i);
        }
      }
      this.removePending = false;
    }
    return str;
  }
  
  public void setRemovePending(String paramString)
  {
    this.removePending = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getRemovePending()
  {
    return String.valueOf(this.removePending);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.FindRecTransfers
 * JD-Core Version:    0.7.0.1
 */