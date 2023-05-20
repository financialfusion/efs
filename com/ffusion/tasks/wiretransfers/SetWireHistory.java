package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.wiretransfers.WireHistories;
import com.ffusion.beans.wiretransfers.WireHistory;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetWireHistory
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  private String Dn;
  private String Dm;
  
  public SetWireHistory()
  {
    this.beanSessionName = "WireHistory";
    this.collectionSessionName = "WireHistories";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    WireHistories localWireHistories = (WireHistories)localHttpSession.getAttribute(this.collectionSessionName);
    if (localWireHistories == null)
    {
      this.error = 12004;
    }
    else if (((this.id == null) || (this.id.length() == 0)) && ((this.Dn == null) || (this.Dn.length() == 0)))
    {
      this.error = 81;
    }
    else
    {
      WireHistory localWireHistory = null;
      if ((this.id != null) && (this.id.length() > 0))
      {
        if ((this.Dm != null) && (this.Dm.length() > 0))
        {
          int i = 0;
          for (int j = 0; j < localWireHistories.size(); j++)
          {
            localWireHistory = (WireHistory)localWireHistories.get(j);
            if ((localWireHistory.getID().equals(this.id)) && (localWireHistory.getDate().equals(this.Dm)))
            {
              i = 1;
              break;
            }
          }
          if (i == 0) {
            localWireHistory = (WireHistory)localWireHistories.getFirstByFilter("ID=" + this.id);
          }
          this.Dm = null;
          this.id = null;
        }
        else
        {
          localWireHistory = (WireHistory)localWireHistories.getFirstByFilter("ID=" + this.id);
        }
      }
      else {
        localWireHistory = (WireHistory)localWireHistories.getFirstByFilter("TrackingID=" + this.Dn);
      }
      if (localWireHistory == null)
      {
        this.error = 81;
      }
      else
      {
        localHttpSession.setAttribute(this.beanSessionName, localWireHistory);
        str = this.successURL;
      }
    }
    return str;
  }
  
  public void setTrackingID(String paramString)
  {
    this.Dn = paramString;
  }
  
  public String getTrackingID()
  {
    return this.Dn;
  }
  
  public void setDate(String paramString)
  {
    this.Dm = paramString;
  }
  
  public String getDate()
  {
    return this.Dm;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.SetWireHistory
 * JD-Core Version:    0.7.0.1
 */