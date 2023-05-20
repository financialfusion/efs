package com.ffusion.csil.utilejb;

import com.ffusion.approvals.ApprovalsException;
import com.ffusion.approvals.IApprovable;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.approvals.ApprovalsStatus;
import com.ffusion.beans.approvals.ApprovalsStatuses;
import com.ffusion.beans.cashcon.LocationSearchResults;
import com.ffusion.beans.util.BeansConverter;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.Approvals;
import com.ffusion.csil.core.CashCon;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.Util;
import com.ffusion.ffs.bpw.interfaces.CCEntryInfo;
import com.ffusion.ffs.interfaces.FFSException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class UtilEJBSessionBean
  implements SessionBean
{
  public void ejbCreate()
    throws CreateException, EJBException
  {
    Class localClass = Initialize.class;
  }
  
  public void ejbActivate()
    throws EJBException, RemoteException
  {}
  
  public void ejbPassivate()
    throws EJBException, RemoteException
  {}
  
  public void ejbRemove()
    throws EJBException, RemoteException
  {}
  
  public void setSessionContext(SessionContext paramSessionContext)
    throws EJBException, RemoteException
  {}
  
  public void createApprovalItem(int paramInt1, int paramInt2, Serializable paramSerializable, Limits paramLimits, HashMap paramHashMap)
    throws FFSException, EJBException, RemoteException
  {
    SecureUser localSecureUser = null;
    try
    {
      localSecureUser = Util.getSecureUserFromProfile(new SecureUser(), paramInt1);
    }
    catch (CSILException localCSILException1)
    {
      throw new FFSException(a(localCSILException1), "The user identified by userId " + paramInt1 + " could not be retrieved from the system.");
    }
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    a(paramSerializable, localSecureUser, paramHashMap);
    IApprovable localIApprovable;
    String str;
    try
    {
      localIApprovable = (IApprovable)BeansConverter.bpwToEfsBean(paramSerializable, paramHashMap);
    }
    catch (ClassCastException localClassCastException)
    {
      str = "The converted efs bean type is not supported by Approvals.";
      throw new FFSException(localClassCastException, str);
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.out);
      throw new FFSException(localException, "Unable to convert item to efs bean.");
    }
    try
    {
      Util.doApproval(localSecureUser, localIApprovable, paramInt2, paramHashMap, paramLimits);
    }
    catch (CSILException localCSILException2)
    {
      str = "Unhandled exception in UtilEJB.createApprovalItem";
      if ((localCSILException2.childException instanceof ApprovalsException)) {
        str = ((ApprovalsException)localCSILException2.childException).getMessage();
      }
      throw new FFSException(localCSILException2, a(localCSILException2), str);
    }
  }
  
  private void a(Serializable paramSerializable, SecureUser paramSecureUser, HashMap paramHashMap)
    throws FFSException
  {
    try
    {
      if ((paramSerializable instanceof CCEntryInfo))
      {
        LocationSearchResults localLocationSearchResults = CashCon.getEntitledLocations(paramSecureUser, paramHashMap);
        if ((localLocationSearchResults != null) && (!localLocationSearchResults.isEmpty())) {
          paramHashMap.put("LocationResults", localLocationSearchResults);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      String str = "An error occurred while preprocessing the transaction before sending it to the approvals system";
      throw new FFSException(localCSILException, a(localCSILException), str);
    }
  }
  
  public void removeApprovalItem(int paramInt1, int paramInt2, String paramString, HashMap paramHashMap)
    throws FFSException, EJBException, RemoteException
  {
    try
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("TrackingID", paramString);
      localObject = Approvals.getPendingItems(paramInt2, localHashMap, paramHashMap);
      if ((localObject == null) || (((ApprovalsStatuses)localObject).size() == 0)) {
        throw new FFSException("No Approval items matching the itemType and trackingID to remove.");
      }
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      Iterator localIterator = ((ApprovalsStatuses)localObject).iterator();
      while (localIterator.hasNext())
      {
        ApprovalsStatus localApprovalsStatus = (ApprovalsStatus)localIterator.next();
        paramHashMap.put("ApprovalsUser", Util.getSecureUserFromProfile(new SecureUser(), localApprovalsStatus.getApprovalItem().getSubmittingUserID()));
        Approvals.removeApprovalItem(localApprovalsStatus.getApprovalItem(), paramHashMap);
      }
      paramHashMap.remove("ApprovalsUser");
    }
    catch (CSILException localCSILException)
    {
      Object localObject = "Unhandled exception in UtilEJB.removeApprovalItem";
      if ((localCSILException.childException instanceof ApprovalsException)) {
        localObject = ((ApprovalsException)localCSILException.childException).getMessage();
      }
      throw new FFSException(localCSILException, a(localCSILException), (String)localObject);
    }
  }
  
  private int a(Throwable paramThrowable)
  {
    int i = -1;
    if ((paramThrowable instanceof CSILException))
    {
      int j = ((CSILException)paramThrowable).getCode();
      switch (j)
      {
      case 20011: 
        i = 10000;
        break;
      case 30216: 
        i = 10001;
        break;
      }
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.utilejb.UtilEJBSessionBean
 * JD-Core Version:    0.7.0.1
 */