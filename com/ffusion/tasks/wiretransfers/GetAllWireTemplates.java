package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAllWireTemplates
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected String pageSize = null;
  private int Dx = 0;
  private int Dy = -1;
  
  public GetAllWireTemplates()
  {
    this.collectionSessionName = "AllWireTemplates";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    String str1 = this.successURL;
    this.error = 0;
    WireTransfers localWireTransfers = null;
    HashMap localHashMap1 = new HashMap();
    if ((this.pageSize != null) && (this.pageSize.length() > 0)) {
      localHashMap1.put("PAGESIZE", this.pageSize);
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser.getUserType() == 2)
    {
      if (getBusinessID() == 0)
      {
        this.error = 12034;
        return this.taskErrorURL;
      }
    }
    else {
      setBusinessID(String.valueOf(localSecureUser.getBusinessID()));
    }
    try
    {
      localWireTransfers = Wire.getAllWireTemplates(localSecureUser, getBusinessID(), localHashMap1);
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = com.ffusion.csil.core.Entitlements.getCumulativeEntitlements(this.Dy);
      HashMap localHashMap2 = new HashMap(localEntitlements.size());
      Iterator localIterator = localEntitlements.iterator();
      String str2 = null;
      while (localIterator.hasNext())
      {
        localObject = (Entitlement)localIterator.next();
        str2 = ((Entitlement)localObject).getObjectType();
        if ((str2 != null) && (str2.equals("Wire Template")) && (((Entitlement)localObject).getOperationName() == null)) {
          localHashMap2.put(((Entitlement)localObject).getObjectId(), localObject);
        }
      }
      if (localHashMap2.size() == 0)
      {
        localHttpSession.setAttribute(this.collectionSessionName, localWireTransfers);
        return str1;
      }
      Object localObject = localWireTransfers.iterator();
      while (((Iterator)localObject).hasNext())
      {
        WireTransfer localWireTransfer = (WireTransfer)((Iterator)localObject).next();
        String str3 = localWireTransfer.getID();
        if (localHashMap2.containsKey(str3)) {
          ((Iterator)localObject).remove();
        }
      }
      localHttpSession.setAttribute(this.collectionSessionName, localWireTransfers);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    this.Dy = -1;
    return str1;
  }
  
  public void setBusinessID(String paramString)
  {
    this.Dx = Integer.parseInt(paramString);
  }
  
  public int getBusinessID()
  {
    return this.Dx;
  }
  
  public void setGroupID(String paramString)
  {
    this.Dy = Integer.parseInt(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetAllWireTemplates
 * JD-Core Version:    0.7.0.1
 */