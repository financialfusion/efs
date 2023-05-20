package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.ACHSameDayEffDate;
import com.ffusion.ffs.bpw.db.CustRoute;
import com.ffusion.ffs.bpw.db.Customer;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Fulfillment;
import com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerRouteInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;

public class CustomerProcessor
  implements DBConsts, FFSConst, OFXConsts
{
  public int addCustomer(CustomerInfo paramCustomerInfo)
    throws Exception
  {
    int i = 0;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      if (Customer.isExists(paramCustomerInfo.customerID, localFFSConnectionHolder))
      {
        localObject1 = "Customer '" + paramCustomerInfo.customerID + "' alread exists." + " New customer add aborted.";
        throw new Exception((String)localObject1);
      }
      if ((paramCustomerInfo.ssn == null) || (paramCustomerInfo.ssn.length() == 0)) {
        paramCustomerInfo.ssn = paramCustomerInfo.taxID;
      }
      paramCustomerInfo.status = "ACTIVE";
      i = Customer.addCustomer(paramCustomerInfo, localFFSConnectionHolder);
      Object localObject1 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      if (!((PropertyConfig)localObject1).EnforcePayment) {
        CustRoute.createAll(localFFSConnectionHolder, paramCustomerInfo.customerID);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "Failed to add new customer. Error: " + localException.toString() + FFSDebug.stackTrace(localException);
      FFSDebug.log(str);
      throw localException;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return i;
  }
  
  public int updateCustomer(CustomerInfo paramCustomerInfo)
    throws Exception
  {
    if (!Customer.validCustomer(paramCustomerInfo.customerID))
    {
      String str1 = "This customer does not exist or is INACTIVE, customerID=" + paramCustomerInfo.customerID;
      FFSDebug.log(str1, 0);
      throw new Exception(str1);
    }
    int i = 0;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      paramCustomerInfo.status = "ACTIVE";
      int j = 0;
      FulfillmentInfo[] arrayOfFulfillmentInfo = Fulfillment.getFulfillmentInfos(localFFSConnectionHolder);
      for (int k = 0; k < arrayOfFulfillmentInfo.length; k++)
      {
        CustomerRouteInfo localCustomerRouteInfo = CustRoute.getCustomerRoute(paramCustomerInfo.customerID, arrayOfFulfillmentInfo[k].RouteID, localFFSConnectionHolder);
        if (localCustomerRouteInfo != null)
        {
          String str2 = localCustomerRouteInfo.Status;
          if (localCustomerRouteInfo.Status.equals("NEW"))
          {
            str2 = "NEW";
          }
          else if (localCustomerRouteInfo.Status.equals("ACTIVE"))
          {
            str2 = "MOD";
          }
          else
          {
            String str3 = "This customer is currently not allowed to be modified\n current Status: " + str2;
            FFSDebug.log(str3, 0);
            throw new Exception(str3);
          }
          j++;
          CustRoute.updateCustRouteStatus(paramCustomerInfo.customerID, arrayOfFulfillmentInfo[k].RouteID, str2, localFFSConnectionHolder);
        }
      }
      i = Customer.updateCustomer(paramCustomerInfo, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("Failed to update customer. Error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return i;
  }
  
  public int deleteCustomer(String paramString)
    throws Exception
  {
    int i = 0;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      if (!Customer.isExists(paramString, localFFSConnectionHolder))
      {
        String str1 = "Failed to find customer with customerID: " + paramString;
        FFSDebug.log(str1, 0);
        throw new Exception(str1);
      }
      int j = 0;
      FulfillmentInfo[] arrayOfFulfillmentInfo = Fulfillment.getFulfillmentInfos(localFFSConnectionHolder);
      Object localObject1;
      for (int k = 0; k < arrayOfFulfillmentInfo.length; k++)
      {
        localObject1 = CustRoute.getCustomerRoute(paramString, arrayOfFulfillmentInfo[k].RouteID, localFFSConnectionHolder);
        if (localObject1 != null) {
          if (((CustomerRouteInfo)localObject1).Status.equals("NEW"))
          {
            CustRoute.deleteCustRoute(paramString, arrayOfFulfillmentInfo[k].RouteID, localFFSConnectionHolder);
          }
          else if (((CustomerRouteInfo)localObject1).Status.indexOf("CANC") != -1)
          {
            String str2 = "This customer is already deleted current Status: " + ((CustomerRouteInfo)localObject1).Status;
            FFSDebug.log(str2, 0);
            throw new Exception(str2);
          }
        }
        j++;
        CommonProcessor.deletePaymentSchedulesByCustomerID(localFFSConnectionHolder, paramString, arrayOfFulfillmentInfo[k].RouteID);
        CommonProcessor.closeCustPayees(paramString, arrayOfFulfillmentInfo[k].RouteID, localFFSConnectionHolder);
        CustRoute.updateCustRouteStatus(paramString, arrayOfFulfillmentInfo[k].RouteID, "CANC", localFFSConnectionHolder);
      }
      CommonProcessor.deletePaymentSchedulesByCustomerID(localFFSConnectionHolder, paramString);
      CommonProcessor.deleteXferSchedulesByCustomerID(localFFSConnectionHolder, paramString);
      CustomerRouteInfo[] arrayOfCustomerRouteInfo = CustRoute.getAllCustomerRoute(paramString, localFFSConnectionHolder);
      if (arrayOfCustomerRouteInfo.length == 0)
      {
        localObject1 = new Object[] { paramString };
        DBUtil.executeStatement(localFFSConnectionHolder, "DELETE FROM BPW_Customer WHERE CustomerID = ?", (Object[])localObject1);
        DBUtil.executeStatement(localFFSConnectionHolder, "DELETE FROM BPW_CustomerPayee WHERE CustomerID = ?", (Object[])localObject1);
      }
      jdMethod_if(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
      if (j > 0) {
        i = 1;
      }
    }
    catch (Exception localException)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      FFSDebug.log("Failed to Delete customer. Error : " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return i;
  }
  
  public int activateCustomer(String paramString)
    throws Exception
  {
    int i = 0;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      i = Customer.updateCustomerStatus(paramString, "ACTIVE", localFFSConnectionHolder);
      CommonProcessor.enableCustPayees(paramString, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      FFSDebug.log("activateCustomer: Processed activation successfully", 6);
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("Failed to update customer. Error : " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return i;
  }
  
  public int deactivateCustomer(String paramString)
    throws Exception
  {
    if (!Customer.validCustomer(paramString))
    {
      String str = "This customer does not exist or is INACTIVE, customerID=" + paramString;
      FFSDebug.log(str, 0);
      throw new Exception(str);
    }
    int i = 0;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      CommonProcessor.disableCustPayees(paramString, localFFSConnectionHolder);
      CommonProcessor.deletePaymentSchedulesByCustomerID(localFFSConnectionHolder, paramString);
      CommonProcessor.deleteXferSchedulesByCustomerID(localFFSConnectionHolder, paramString);
      i = Customer.updateCustomerStatus(paramString, "INACTIVE", localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      FFSDebug.log("deactivateCustomer: Processed deactivation successfully", 6);
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("Failed to update customer. Error : " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return i;
  }
  
  public int deleteCustomer(String paramString, int paramInt)
    throws Exception
  {
    int i = 0;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      if (!Customer.isExists(paramString, localFFSConnectionHolder))
      {
        localObject1 = "Failed to find customer with customerID: " + paramString;
        FFSDebug.log((String)localObject1, 0);
        throw new Exception((String)localObject1);
      }
      Object localObject1 = CustRoute.getCustomerRoute(paramString, paramInt, localFFSConnectionHolder);
      if (localObject1 != null) {
        if (((CustomerRouteInfo)localObject1).Status.equals("NEW"))
        {
          CustRoute.deleteCustRoute(paramString, paramInt, localFFSConnectionHolder);
        }
        else if (((CustomerRouteInfo)localObject1).Status.indexOf("CANC") != -1)
        {
          localObject2 = "This customer is already deleted current Status: " + ((CustomerRouteInfo)localObject1).Status;
          FFSDebug.log((String)localObject2, 0);
          throw new Exception((String)localObject2);
        }
      }
      CommonProcessor.deletePaymentSchedulesByCustomerID(localFFSConnectionHolder, paramString, paramInt);
      CustRoute.updateCustRouteStatus(paramString, paramInt, "CANC", localFFSConnectionHolder);
      Object localObject2 = CustRoute.getAllCustomerRoute(paramString, localFFSConnectionHolder);
      if (localObject2.length == 0)
      {
        Object[] arrayOfObject = { paramString };
        DBUtil.executeStatement(localFFSConnectionHolder, "DELETE FROM BPW_Customer WHERE CustomerID = ?", arrayOfObject);
        DBUtil.executeStatement(localFFSConnectionHolder, "DELETE FROM BPW_CustomerPayee WHERE CustomerID = ?", arrayOfObject);
      }
      jdMethod_if(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
      i = 1;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("Failed to Delete customer. Error : " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return i;
  }
  
  public CustomerInfo getCustomerInfo(String paramString)
    throws Exception
  {
    CustomerInfo localCustomerInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localCustomerInfo = Customer.getCustomerByID(paramString, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("CustomerProcessor.getCustomerInfo Failed to get customer. Error : ", FFSDebug.stackTrace(localException), 0);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByType(String paramString)
    throws Exception
  {
    CustomerInfo[] arrayOfCustomerInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfCustomerInfo = Customer.getCustomerByType(paramString, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("CustomerProcessor.getCustomerByType Failed to get customer. Error : ", FFSDebug.stackTrace(localException), 0);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByFI(String paramString)
    throws Exception
  {
    CustomerInfo[] arrayOfCustomerInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfCustomerInfo = Customer.getCustomerByFI(paramString, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("CustomerProcessor.getCustomerByFI Failed to get customer. Error : ", FFSDebug.stackTrace(localException), 0);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByCategory(String paramString)
    throws Exception
  {
    CustomerInfo[] arrayOfCustomerInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfCustomerInfo = Customer.getCustomerByCategory(paramString, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("CustomerProcessor.getCustomerByCategory Failed to get customer. Error : ", FFSDebug.stackTrace(localException), 0);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByGroup(String paramString)
    throws Exception
  {
    CustomerInfo[] arrayOfCustomerInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfCustomerInfo = Customer.getCustomerByGroup(paramString, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("CustomerProcessor.getCustomerByGroup Failed to get customer. Error : ", FFSDebug.stackTrace(localException), 0);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByTypeAndFI(String paramString1, String paramString2)
    throws Exception
  {
    CustomerInfo[] arrayOfCustomerInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfCustomerInfo = Customer.getCustomerByTypeAndFI(paramString1, paramString2, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("CustomerProcessor.getCustomerByTypeAndFI Failed to get customer. Error : ", FFSDebug.stackTrace(localException), 0);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByCategoryAndFI(String paramString1, String paramString2)
    throws Exception
  {
    CustomerInfo[] arrayOfCustomerInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfCustomerInfo = Customer.getCustomerByCategoryAndFI(paramString1, paramString2, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("CustomerProcessor.getCustomerByCategoryAndFI Failed to get customer. Error : ", FFSDebug.stackTrace(localException), 0);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfCustomerInfo;
  }
  
  public CustomerInfo[] getCustomerByGroupAndFI(String paramString1, String paramString2)
    throws Exception
  {
    CustomerInfo[] arrayOfCustomerInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      arrayOfCustomerInfo = Customer.getCustomerByGroupAndFI(paramString1, paramString2, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("CustomerProcessor.getCustomerByGroupAndFI Failed to get customer. Error : ", FFSDebug.stackTrace(localException), 0);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return arrayOfCustomerInfo;
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    ACHSameDayEffDateInfo localACHSameDayEffDateInfo = new ACHSameDayEffDateInfo();
    localACHSameDayEffDateInfo.setObjectId(paramString);
    localACHSameDayEffDateInfo.setObjectType(0);
    ACHSameDayEffDate.setACHSameDayEffDateInfo(paramFFSConnectionHolder, localACHSameDayEffDateInfo);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.CustomerProcessor
 * JD-Core Version:    0.7.0.1
 */