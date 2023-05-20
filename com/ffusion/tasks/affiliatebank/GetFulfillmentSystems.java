package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.FulfillmentSystem;
import com.ffusion.beans.affiliatebank.FulfillmentSystems;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetFulfillmentSystems
  extends ExtendedBaseTask
  implements CutOffTaskDefines
{
  public GetFulfillmentSystems()
  {
    this.collectionSessionName = "FulfillmentSystems";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    FulfillmentSystems localFulfillmentSystems = null;
    localFulfillmentSystems = (FulfillmentSystems)localHttpSession.getAttribute(this.collectionSessionName);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    try
    {
      localFulfillmentSystems = PaymentsAdmin.getFulfillmentSystems(localSecureUser, localHashMap);
      localHttpSession.setAttribute(this.collectionSessionName, localFulfillmentSystems);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public FulfillmentSystems getFulfillmentSystems(HashMap paramHashMap)
  {
    FulfillmentSystems localFulfillmentSystems = new FulfillmentSystems();
    localFulfillmentSystems.add(buildFulfillSys("ON_US", "2", "9999.0000", "com.ffusion.ffs.bpw.fulfill.ON_USHandler"));
    localFulfillmentSystems.add(buildFulfillSys("BPWSAMPLE", "3", "1.0000", "com.ffusion.ffs.bpw.fulfill.SampleFulfillmentHandler"));
    localFulfillmentSystems.add(buildFulfillSys("CheckFree", "1", "0.2500", "com.ffusion.ffs.bpw.fulfill.checkfree.CheckFreeHandler"));
    localFulfillmentSystems.add(buildFulfillSys("ORCC", "5", "0.0000", "com.ffusion.ffs.bpw.fulfill.orcc.ORCCHandler"));
    localFulfillmentSystems.add(buildFulfillSys("METAVANTE", "4", "1.0000", "com.ffusion.ffs.bpw.fulfill.metavante.MetavanteHandler"));
    localFulfillmentSystems.add(buildFulfillSys("RPPS", "6", "1.0000", "com.ffusion.ffs.bpw.fulfill.rpps.RPPSHandler"));
    return localFulfillmentSystems;
  }
  
  public FulfillmentSystem buildFulfillSys(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    FulfillmentSystem localFulfillmentSystem = new FulfillmentSystem();
    localFulfillmentSystem.setFulfillmentSystemName(paramString1);
    localFulfillmentSystem.setRouteId(paramString2);
    localFulfillmentSystem.setPaymentCost(paramString3);
    localFulfillmentSystem.setHandlerName(paramString4);
    return localFulfillmentSystem;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.GetFulfillmentSystems
 * JD-Core Version:    0.7.0.1
 */