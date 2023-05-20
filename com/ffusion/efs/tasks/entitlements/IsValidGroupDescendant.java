package com.ffusion.efs.tasks.entitlements;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IsValidGroupDescendant
  extends BaseTask
  implements Task
{
  private int MR = -1;
  private String MT = null;
  private int MQ = -1;
  private String MS = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    try
    {
      String str1 = this.successURL;
      this.error = 0;
      try
      {
        EntitlementGroup localEntitlementGroup = null;
        if (this.MQ == this.MR)
        {
          if (this.MS != null)
          {
            localEntitlementGroup = Entitlements.getEntitlementGroup(this.MQ);
            if (!this.MS.equals(localEntitlementGroup.getEntGroupType()))
            {
              this.error = 20001;
              str1 = this.serviceErrorURL;
            }
          }
        }
        else
        {
          int i = this.MQ;
          localEntitlementGroup = Entitlements.getEntitlementGroup(i);
          if ((this.MS != null) && (!this.MS.equals(localEntitlementGroup.getEntGroupType())))
          {
            this.error = 20001;
            str1 = this.serviceErrorURL;
            String str3 = str1;
            return str3;
          }
          for (;;)
          {
            i = localEntitlementGroup.getParentId();
            if (i == this.MR) {
              break;
            }
            if ((this.MT != null) && (this.MT.equals(localEntitlementGroup.getEntGroupType())))
            {
              this.error = 20001;
              str1 = this.serviceErrorURL;
              break;
            }
            if (i == 0)
            {
              this.error = 20001;
              str1 = this.serviceErrorURL;
              break;
            }
            localEntitlementGroup = Entitlements.getEntitlementGroup(i);
          }
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
      String str2 = str1;
      return str2;
    }
    finally
    {
      reset();
    }
  }
  
  public void setAncestorEntGroupId(String paramString)
  {
    try
    {
      this.MR = Integer.parseInt(paramString);
    }
    catch (Throwable localThrowable)
    {
      this.MR = -1;
    }
  }
  
  public void setAncestorEntGroupType(String paramString)
  {
    this.MT = paramString;
  }
  
  public void setDescendantEntGroupId(String paramString)
  {
    try
    {
      this.MQ = Integer.parseInt(paramString);
    }
    catch (Throwable localThrowable)
    {
      this.MQ = -1;
    }
  }
  
  public void setDescendantEntGroupType(String paramString)
  {
    this.MS = paramString;
  }
  
  protected void reset()
  {
    this.MR = -1;
    this.MT = null;
    this.MQ = -1;
    this.MS = null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.IsValidGroupDescendant
 * JD-Core Version:    0.7.0.1
 */