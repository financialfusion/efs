package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.entitlements.EntitlementsUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SaveRestrictedCalculations
  extends BaseTask
  implements AffiliateBankTask
{
  private int aPv = -1;
  private String aPw = null;
  private String aPx = null;
  private String aPu = "RestrictedCalculations";
  private String aPt = "NewRestrictedCalculations";
  protected boolean _entBypass = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    if (this._entBypass) {
      localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ArrayList localArrayList1 = null;
    AffiliateBank localAffiliateBank = new AffiliateBank();
    if ((this.aPv == -1) && (this.aPw == null))
    {
      this.error = 25516;
      str1 = this.taskErrorURL;
    }
    else
    {
      try
      {
        int i = this.aPv;
        if (this.aPw != null)
        {
          localAffiliateBank = AffiliateBankAdmin.getAffiliateBankByRoutingNumber(localSecureUser, this.aPw, localHashMap);
          i = localAffiliateBank.getAffiliateBankID();
        }
        localArrayList1 = (ArrayList)localHttpSession.getAttribute(this.aPt);
        AffiliateBankAdmin.saveRestrictedCalculations(localSecureUser, i, localArrayList1, localHashMap);
        ArrayList localArrayList2 = (ArrayList)localHttpSession.getAttribute(this.aPu);
        if (this.aPx == null) {
          this.aPx = String.valueOf(i);
        }
        HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 2, this.aPx);
        ArrayList localArrayList3 = new ArrayList(localArrayList1);
        Iterator localIterator = localArrayList2.iterator();
        StringBuffer localStringBuffer;
        while (localIterator.hasNext())
        {
          localObject1 = (String)localIterator.next();
          int j = localArrayList3.indexOf(localObject1);
          if (j == -1) {
            try
            {
              String str3 = ResourceUtil.getString((String)localObject1, "com.ffusion.beans.accounts.resources", Locale.getDefault());
              localHistoryTracker.logChange(localHistoryTracker.lookupField(AffiliateBank.BEAN_NAME, AffiliateBank.AFFILIATE_BANK_CALCULATIONS), null, str3, "not restricted");
            }
            catch (Exception localException1)
            {
              localStringBuffer = new StringBuffer("Unable to lookup ");
              localStringBuffer.append((String)localObject1);
              localStringBuffer.append(" in the resource bundble ");
              localStringBuffer.append("com.ffusion.beans.accounts.resources");
              DebugLog.log(Level.SEVERE, localStringBuffer.toString());
            }
          } else {
            localArrayList3.remove(j);
          }
        }
        Object localObject1 = localArrayList3.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          String str2 = (String)((Iterator)localObject1).next();
          try
          {
            String str4 = ResourceUtil.getString(str2, "com.ffusion.beans.accounts.resources", Locale.getDefault());
            localHistoryTracker.logChange(localHistoryTracker.lookupField(AffiliateBank.BEAN_NAME, AffiliateBank.AFFILIATE_BANK_CALCULATIONS), null, str4, "restricted");
          }
          catch (Exception localException2)
          {
            localStringBuffer = new StringBuffer("Unable to lookup ");
            localStringBuffer.append(str2);
            localStringBuffer.append(" in the resource bundble ");
            localStringBuffer.append("com.ffusion.beans.accounts.resources");
            DebugLog.log(Level.SEVERE, localStringBuffer.toString());
          }
        }
        try
        {
          HistoryAdapter.addHistory(localHistoryTracker.getHistories());
        }
        catch (ProfileException localProfileException)
        {
          DebugLog.log(Level.SEVERE, "Add History failed for SaveRestrictedCalculations: " + localProfileException.toString());
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
      finally
      {
        this._entBypass = false;
      }
    }
    if (this.error == 0) {
      str1 = this.successURL;
    }
    return str1;
  }
  
  public void setAffiliateBankID(String paramString)
  {
    try
    {
      this.aPv = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.aPv = -1;
    }
  }
  
  public void setRoutingNumber(String paramString)
  {
    this.aPw = paramString;
  }
  
  public void setHistoryId(String paramString)
  {
    this.aPx = paramString;
  }
  
  public void setOldListName(String paramString)
  {
    this.aPu = paramString;
  }
  
  public void setNewListName(String paramString)
  {
    this.aPt = paramString;
  }
  
  public void setEntitlementBypass(String paramString)
  {
    this._entBypass = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEntitlementBypass()
  {
    return Boolean.toString(this._entBypass);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.SaveRestrictedCalculations
 * JD-Core Version:    0.7.0.1
 */