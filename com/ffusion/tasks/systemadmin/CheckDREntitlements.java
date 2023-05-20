package com.ffusion.tasks.systemadmin;

import com.ffusion.beans.systemadmin.DRKey;
import com.ffusion.beans.systemadmin.DRSettingKeys;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.systemadmin.SAConstants;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckDREntitlements
  extends BaseTask
  implements SAConstants, SystemAdminTask
{
  private String Vf = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this.Vf == null) {
      return str;
    }
    int i = 0;
    try
    {
      i = Integer.parseInt(this.Vf);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return str;
    }
    try
    {
      DRSettingKeys localDRSettingKeys = (DRSettingKeys)localHttpSession.getAttribute("DRSettingKeys");
      if (localDRSettingKeys == null) {
        return str;
      }
      Iterator localIterator = localDRSettingKeys.iterator();
      while (localIterator.hasNext())
      {
        DRKey localDRKey = (DRKey)localIterator.next();
        String[] arrayOfString = null;
        Entitlement localEntitlement1;
        if (localDRKey.getDataType().equals("CashConcentration"))
        {
          boolean bool = false;
          localEntitlement1 = new Entitlement("Cash Con - Deposit Entry", null, null);
          Entitlement localEntitlement2 = new Entitlement("Cash Con - Disbursement Request", null, null);
          Entitlement localEntitlement3 = new Entitlement("Location Management", null, null);
          bool = Entitlements.checkEntitlement(i, localEntitlement1);
          if (!bool) {
            bool = Entitlements.checkEntitlement(i, localEntitlement2);
          }
          if (!bool) {
            bool = Entitlements.checkEntitlement(i, localEntitlement3);
          }
          if (!bool) {
            localIterator.remove();
          }
        }
        else
        {
          if ((localDRKey.getDataType().equals("Lockbox summaries")) || (localDRKey.getDataType().equals("Lockbox transactions")) || (localDRKey.getDataType().equals("Lockbox credit items")))
          {
            arrayOfString = new String[1];
            arrayOfString[0] = "Lockbox";
          }
          else if ((localDRKey.getDataType().equals("Disbursement summaries")) || (localDRKey.getDataType().equals("Disbursement transactions")))
          {
            arrayOfString = new String[1];
            arrayOfString[0] = "Controlled Disbursements";
          }
          else if (localDRKey.getDataType().equals("AchPayments"))
          {
            arrayOfString = new String[1];
            arrayOfString[0] = "ACHBatch";
          }
          else if (localDRKey.getDataType().equals("BillPayments"))
          {
            arrayOfString = new String[1];
            arrayOfString[0] = "Payments";
          }
          else if (localDRKey.getDataType().equals("ChildSupport"))
          {
            arrayOfString = new String[1];
            arrayOfString[0] = "CCD + DED";
          }
          else if (localDRKey.getDataType().equals("ExternalTransfers"))
          {
            arrayOfString = new String[1];
            arrayOfString[0] = "External Transfers";
          }
          else if (localDRKey.getDataType().equals("PositivePay"))
          {
            arrayOfString = new String[1];
            arrayOfString[0] = "Positive Pay";
          }
          else if (localDRKey.getDataType().equals("TaxPayments"))
          {
            arrayOfString = new String[1];
            arrayOfString[0] = "CCD + TXP";
          }
          else if (localDRKey.getDataType().equals("Transfers"))
          {
            arrayOfString = new String[1];
            arrayOfString[0] = "Transfers";
          }
          else if (localDRKey.getDataType().equals("Wires"))
          {
            arrayOfString = new String[1];
            arrayOfString[0] = "Wires";
          }
          else if (localDRKey.getDataType().equals("Account summaries"))
          {
            arrayOfString = new String[2];
            arrayOfString[0] = "Information Reporting";
            if (localDRKey.getDataClass().equals("I")) {
              arrayOfString[1] = "Information Reporting - Intra Day Summary";
            } else {
              arrayOfString[1] = "Information Reporting - Previous Day Summary";
            }
          }
          else if (localDRKey.getDataType().equals("Account transactions"))
          {
            arrayOfString = new String[2];
            arrayOfString[0] = "Information Reporting";
            if (localDRKey.getDataClass().equals("I")) {
              arrayOfString[1] = "Information Reporting - Intra Day Detail";
            } else {
              arrayOfString[1] = "Information Reporting - Previous Day Detail";
            }
          }
          if (arrayOfString != null)
          {
            MultiEntitlement localMultiEntitlement = new MultiEntitlement();
            localMultiEntitlement.setOperations(arrayOfString);
            localMultiEntitlement.setObjects(new String[] { null }, new String[] { null });
            localEntitlement1 = Entitlements.checkEntitlement(i, localMultiEntitlement);
            if (localEntitlement1 != null) {
              localIterator.remove();
            }
          }
        }
      }
      localHttpSession.setAttribute("DRSettingKeys", localDRSettingKeys);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setEntitlementGroupId(String paramString)
  {
    this.Vf = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.systemadmin.CheckDREntitlements
 * JD-Core Version:    0.7.0.1
 */