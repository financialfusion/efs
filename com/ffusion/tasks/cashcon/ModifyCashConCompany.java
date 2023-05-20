package com.ffusion.tasks.cashcon;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffTimes;
import com.ffusion.beans.cashcon.CashConAccounts;
import com.ffusion.beans.cashcon.CashConCompany;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.core.CashCon;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyCashConCompany
  extends BaseTask
  implements Task
{
  public static final String VC_COMPANY_NAME = "CompanyName";
  public static final String VC_COMPANY_ID = "CompanyID";
  public static final String VC_FUNDS_CONCENTRATION = "FundsConcentration";
  public static final String VC_FUNDS_DISBURSEMENT = "FundsDisbursement";
  public static final String ALPHANUMERIC_SET = ".<(+|&!$*);^-/,%_>?_:#@'=\"";
  protected static final int COMPANY_NAME_MAX_LENGTH = 16;
  protected static final int COMPANY_ID_MAX_LENGTH = 10;
  protected String cashconName = "CashConCompany";
  protected String originalCompanyName = "OriginalCashConCompany";
  protected String fiId;
  protected int entitlementGroupId;
  protected String m_validationCriteria = null;
  protected boolean m_doProcess = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    CashConCompany localCashConCompany1 = (CashConCompany)localHttpSession.getAttribute(this.cashconName);
    CashConCompany localCashConCompany2 = (CashConCompany)localHttpSession.getAttribute(this.originalCompanyName);
    if (localCashConCompany1 == null)
    {
      this.error = 24002;
      str = this.taskErrorURL;
    }
    else if ((this.fiId == null) || (this.fiId.length() == 0))
    {
      this.error = 24004;
      str = this.taskErrorURL;
    }
    else if (validateInput(localCashConCompany1))
    {
      if (this.m_doProcess)
      {
        HashMap localHashMap = new HashMap();
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        try
        {
          CashCon.modifyCashConCompany(localSecureUser, localCashConCompany1, localCashConCompany2, this.fiId, localHashMap);
          if (((localCashConCompany1.getConcEnabled() ^ localCashConCompany2.getConcEnabled())) || ((localCashConCompany1.getDisbEnabled() ^ localCashConCompany2.getDisbEnabled())))
          {
            com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), getEntitlementGroupId());
            Entitlement localEntitlement;
            if ((localCashConCompany1.getConcEnabled() ^ localCashConCompany2.getConcEnabled()))
            {
              localEntitlement = new Entitlement();
              localEntitlement.setObjectType("CashConCompany");
              localEntitlement.setObjectId(localCashConCompany1.getBPWID());
              localEntitlement.setOperationName("Cash Con - Deposit Entry");
              if (!localCashConCompany1.getConcEnabled()) {
                localEntitlements.add(localEntitlement);
              } else {
                localEntitlements.remove(localEntitlement);
              }
            }
            if ((localCashConCompany1.getDisbEnabled() ^ localCashConCompany2.getDisbEnabled()))
            {
              localEntitlement = new Entitlement();
              localEntitlement.setObjectType("CashConCompany");
              localEntitlement.setObjectId(localCashConCompany1.getBPWID());
              localEntitlement.setOperationName("Cash Con - Disbursement Request");
              if (!localCashConCompany1.getDisbEnabled()) {
                localEntitlements.add(localEntitlement);
              } else {
                localEntitlements.remove(localEntitlement);
              }
            }
            com.ffusion.csil.core.Entitlements.setRestrictedEntitlements(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), getEntitlementGroupId(), localEntitlements);
          }
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException, localHttpSession);
          str = this.serviceErrorURL;
        }
      }
    }
    else
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setFiId(String paramString)
  {
    this.fiId = paramString;
  }
  
  public final void setCashConSessionName(String paramString)
  {
    this.cashconName = paramString;
  }
  
  public final void setOriginalCompany(String paramString)
  {
    this.originalCompanyName = paramString;
  }
  
  public void setEntitlementGroupId(String paramString)
  {
    this.entitlementGroupId = Integer.parseInt(paramString);
  }
  
  public int getEntitlementGroupId()
  {
    return this.entitlementGroupId;
  }
  
  public void setProcess(String paramString)
  {
    this.m_doProcess = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.m_validationCriteria = paramString;
  }
  
  protected boolean validateInput(CashConCompany paramCashConCompany)
  {
    boolean bool = true;
    if (this.m_validationCriteria != null)
    {
      if (this.m_validationCriteria.indexOf("CompanyName") != -1) {
        bool = validateCompanyName(paramCashConCompany.getCompanyName());
      }
      if ((bool) && (this.m_validationCriteria.indexOf("CompanyID") != -1)) {
        bool = validateCompanyID(paramCashConCompany.getCompanyID());
      }
      if ((bool) && (this.m_validationCriteria.indexOf("FundsConcentration") != -1)) {
        bool = validateFundsConcentration(paramCashConCompany);
      }
      if ((bool) && (this.m_validationCriteria.indexOf("FundsDisbursement") != -1)) {
        bool = validateFundsDisbursement(paramCashConCompany);
      }
    }
    return bool;
  }
  
  protected boolean validateCompanyName(String paramString)
  {
    boolean bool = true;
    bool = (paramString != null) && (paramString.length() > 0) && (paramString.length() <= 16);
    for (int i = 0; (bool) && (i < paramString.length()); i++) {
      bool = (Character.isLetterOrDigit(paramString.charAt(i))) || (paramString.charAt(i) == ' ') || (".<(+|&!$*);^-/,%_>?_:#@'=\"".indexOf(paramString.charAt(i)) != -1);
    }
    if (!bool) {
      this.error = 24100;
    }
    return bool;
  }
  
  protected boolean validateCompanyID(String paramString)
  {
    boolean bool = true;
    bool = (paramString != null) && (paramString.length() > 0) && (paramString.length() <= 10);
    for (int i = 0; (bool) && (i < paramString.length()); i++) {
      bool = (Character.isLetterOrDigit(paramString.charAt(i))) || (paramString.charAt(i) == ' ') || (".<(+|&!$*);^-/,%_>?_:#@'=\"".indexOf(paramString.charAt(i)) != -1);
    }
    if (!bool) {
      this.error = 24101;
    }
    return bool;
  }
  
  protected boolean validateFundsConcentration(CashConCompany paramCashConCompany)
  {
    boolean bool = true;
    if (paramCashConCompany.getConcEnabled()) {
      if (paramCashConCompany.getConcAccounts().size() <= 0)
      {
        bool = false;
        this.error = 24102;
      }
      else if (paramCashConCompany.getConcAccounts().getDefaultAccount() == null)
      {
        bool = false;
        this.error = 24106;
      }
      else if (paramCashConCompany.getConcCutOffs().size() <= 0)
      {
        bool = false;
        this.error = 24103;
      }
    }
    return bool;
  }
  
  protected boolean validateFundsDisbursement(CashConCompany paramCashConCompany)
  {
    boolean bool = true;
    if (paramCashConCompany.getDisbEnabled()) {
      if (paramCashConCompany.getDisbAccounts().size() <= 0)
      {
        bool = false;
        this.error = 24104;
      }
      else if (paramCashConCompany.getDisbAccounts().getDefaultAccount() == null)
      {
        bool = false;
        this.error = 24107;
      }
      else if (paramCashConCompany.getDisbCutOffs().size() <= 0)
      {
        bool = false;
        this.error = 24105;
      }
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.ModifyCashConCompany
 * JD-Core Version:    0.7.0.1
 */