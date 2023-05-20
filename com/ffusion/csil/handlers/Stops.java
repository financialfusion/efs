package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.stoppayments.StopCheck;
import com.ffusion.beans.stoppayments.StopChecks;
import com.ffusion.csil.CSILException;
import com.ffusion.services.Stops2;
import com.ffusion.services.Stops3;
import com.ffusion.services.Stops4;
import com.ffusion.util.beans.PagingContext;
import java.util.Calendar;
import java.util.HashMap;

public class Stops
  extends Initialize
{
  private static Stops4 a6I;
  private static final String a6J = "Stop Payment";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "Stops.initialize";
    debug("com.ffusion.csil.handlers.Stops.initialize");
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Stop Payment", str, 20107);
    a6I = (Stops4)HandlerUtil.instantiateService(localHashMap, str, 20107);
  }
  
  public static Object getService()
  {
    return null;
  }
  
  public static StopCheck addStopPayment(SecureUser paramSecureUser, StopCheck paramStopCheck, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Stops.addStopPayment");
    checkExtra(paramHashMap);
    com.ffusion.services.Stops localStops = (com.ffusion.services.Stops)paramHashMap.get("SERVICE");
    if ((localStops == null) || (!(localStops instanceof com.ffusion.services.Stops))) {
      return a6I.addStopPayment(paramSecureUser, paramStopCheck, paramHashMap);
    }
    if ((localStops instanceof Stops4)) {
      return ((Stops4)localStops).addStopPayment(paramSecureUser, paramStopCheck, paramHashMap);
    }
    int i = localStops.addStopPayment(paramStopCheck);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramStopCheck;
  }
  
  public static StopCheck deleteStopPayment(SecureUser paramSecureUser, StopCheck paramStopCheck, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Stops.deleteStopPayment");
    checkExtra(paramHashMap);
    com.ffusion.services.Stops localStops = (com.ffusion.services.Stops)paramHashMap.get("SERVICE");
    if ((localStops == null) || (!(localStops instanceof com.ffusion.services.Stops))) {
      return a6I.deleteStopPayment(paramSecureUser, paramStopCheck, paramHashMap);
    }
    if ((localStops instanceof Stops4)) {
      return ((Stops4)localStops).deleteStopPayment(paramSecureUser, paramStopCheck, paramHashMap);
    }
    int i = localStops.deleteStopPayment(paramStopCheck);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramStopCheck;
  }
  
  public static StopChecks getStopPayments(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Stops.getStopPayments");
    checkExtra(paramHashMap);
    Object localObject = (com.ffusion.services.Stops)paramHashMap.get("SERVICE");
    if ((localObject == null) || (!(localObject instanceof com.ffusion.services.Stops))) {
      localObject = a6I;
    }
    StopChecks localStopChecks = (StopChecks)paramHashMap.get("STOPCHECKS");
    if (localStopChecks == null)
    {
      debug("Missing required parameter: extra.'STOPCHECKS' - creating new StopChecks()");
      localStopChecks = new StopChecks();
    }
    if ((localObject instanceof Stops4)) {
      localStopChecks = jdMethod_for(paramSecureUser, localStopChecks, paramHashMap);
    }
    int i = ((com.ffusion.services.Stops)localObject).getStopPayments(localStopChecks);
    if (i != 0) {
      throwing(-1009, i);
    }
    return localStopChecks;
  }
  
  public static StopChecks getStopPayments(SecureUser paramSecureUser, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Stops.getStopPayments(2)");
    checkExtra(paramHashMap);
    Object localObject = (com.ffusion.services.Stops)paramHashMap.get("SERVICE");
    if ((localObject == null) || (!(localObject instanceof com.ffusion.services.Stops))) {
      localObject = a6I;
    }
    StopChecks localStopChecks = (StopChecks)paramHashMap.get("STOPCHECKS");
    if (localStopChecks == null)
    {
      debug("Missing required parameter: extra.'STOPCHECKS' - creating new StopChecks()");
      localStopChecks = new StopChecks();
    }
    int i = 0;
    if ((localObject instanceof Stops4))
    {
      localStopChecks = jdMethod_for(paramSecureUser, localStopChecks, paramHashMap);
      i = ((Stops4)localObject).getStopPayments(localStopChecks, paramAccounts);
    }
    else if ((localObject instanceof Stops2))
    {
      i = ((Stops2)localObject).getStopPayments(localStopChecks, paramAccounts);
    }
    else
    {
      debug("NOT USING REQUIRED Stops2 service, accounts param ignored");
      i = ((com.ffusion.services.Stops)localObject).getStopPayments(localStopChecks);
    }
    if (i != 0) {
      throwing(-1009, i);
    }
    return localStopChecks;
  }
  
  public static StopChecks getStopPayments(SecureUser paramSecureUser, Accounts paramAccounts, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Stops.getStopPayments(3)");
    checkExtra(paramHashMap);
    Object localObject = (com.ffusion.services.Stops)paramHashMap.get("SERVICE");
    if ((localObject == null) || (!(localObject instanceof com.ffusion.services.Stops))) {
      localObject = a6I;
    }
    StopChecks localStopChecks = (StopChecks)paramHashMap.get("STOPCHECKS");
    if (localStopChecks == null)
    {
      debug("Missing required parameter: extra.'STOPCHECKS' - creating new StopChecks()");
      localStopChecks = new StopChecks();
    }
    if ((localObject instanceof Stops4)) {
      localStopChecks = jdMethod_for(paramSecureUser, localStopChecks, paramHashMap);
    }
    int i = 0;
    if ((localObject instanceof Stops4))
    {
      i = ((Stops4)localObject).getStopPayments(localStopChecks, paramAccounts, paramCalendar1, paramCalendar2);
    }
    else if ((localObject instanceof Stops3))
    {
      i = ((Stops3)localObject).getStopPayments(localStopChecks, paramAccounts, paramCalendar1, paramCalendar2);
    }
    else if ((localObject instanceof Stops2))
    {
      i = ((Stops2)localObject).getStopPayments(localStopChecks, paramAccounts);
    }
    else
    {
      debug("NOT USING REQUIRED Stops2 service, accounts param ignored");
      i = ((com.ffusion.services.Stops)localObject).getStopPayments(localStopChecks);
    }
    if (i != 0) {
      throwing(-1009, i);
    }
    return localStopChecks;
  }
  
  public static StopChecks getStopPayments(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Stops.getStopPayments(3)");
    checkExtra(paramHashMap);
    return a6I.getStopPayments(paramSecureUser, paramPagingContext, paramHashMap);
  }
  
  public static StopCheck modStopPayment(SecureUser paramSecureUser, StopCheck paramStopCheck, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Stops.modStopPayment");
    checkExtra(paramHashMap);
    return a6I.modStopPayment(paramSecureUser, paramStopCheck, paramHashMap);
  }
  
  private static StopChecks jdMethod_for(SecureUser paramSecureUser, StopChecks paramStopChecks, HashMap paramHashMap)
  {
    StopCheck localStopCheck = null;
    if ((paramStopChecks == null) || (paramStopChecks.size() == 0))
    {
      paramStopChecks = new StopChecks();
      localStopCheck = new StopCheck();
      paramStopChecks.add(localStopCheck);
    }
    else
    {
      localStopCheck = (StopCheck)paramStopChecks.get(0);
    }
    localStopCheck.put("SecureUser", paramSecureUser);
    localStopCheck.put("ExtraFields", paramHashMap);
    return paramStopChecks;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.Stops
 * JD-Core Version:    0.7.0.1
 */