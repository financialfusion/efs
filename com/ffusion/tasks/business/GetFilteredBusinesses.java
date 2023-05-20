package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.affiliatebank.AffiliateBankAssignment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetFilteredBusinesses
  extends BaseTask
  implements BusinessTask
{
  String gv;
  String gu;
  String gw;
  String gy;
  String gA;
  String gx;
  String gC;
  private boolean gB = false;
  private String gz = "BusinessesSearchList";
  public static final int TYPE_PERSONAL_BANKER = 0;
  public static final int TYPE_ACCOUNT_REP = 1;
  
  public void setBusinessName(String paramString)
  {
    this.gv = paramString;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.gw = paramString;
  }
  
  public void setStatus(String paramString)
  {
    this.gu = paramString;
  }
  
  public void setFirstName(String paramString)
  {
    this.gy = paramString;
  }
  
  public void setLastName(String paramString)
  {
    this.gA = paramString;
  }
  
  public void setPersonalBanker(String paramString)
  {
    this.gx = paramString;
  }
  
  public void setAccountRep(String paramString)
  {
    this.gC = paramString;
  }
  
  public static com.ffusion.beans.business.Business findBusiness(HttpSession paramHttpSession, String paramString, int paramInt)
  {
    Businesses localBusinesses1 = null;
    Businesses localBusinesses2 = null;
    if (paramInt == 0)
    {
      localBusinesses1 = (Businesses)paramHttpSession.getAttribute("BusinessesWithoutBanker");
      localBusinesses2 = (Businesses)paramHttpSession.getAttribute("TempAssignedPbBusinesses");
    }
    else if (paramInt == 1)
    {
      localBusinesses1 = (Businesses)paramHttpSession.getAttribute("BusinessesWithoutRep");
      localBusinesses2 = (Businesses)paramHttpSession.getAttribute("TempAssignedArBusinesses");
    }
    Businesses localBusinesses3 = new Businesses();
    localBusinesses3.addAll(localBusinesses1);
    localBusinesses3.addAll(localBusinesses2);
    com.ffusion.beans.business.Business localBusiness = null;
    Iterator localIterator = localBusinesses3.iterator();
    while (localIterator.hasNext())
    {
      localBusiness = (com.ffusion.beans.business.Business)localIterator.next();
      if (localBusiness.getId().equalsIgnoreCase(paramString)) {
        break;
      }
      localBusiness = null;
    }
    return localBusiness;
  }
  
  public static boolean isAssignedToAffiliate(HttpServletRequest paramHttpServletRequest, String paramString, int paramInt)
  {
    boolean bool = false;
    ArrayList localArrayList = new ArrayList();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (paramInt == 0) {
      str = paramHttpServletRequest.getParameter("TransferPersonalBankersIds");
    } else if (paramInt == 1) {
      str = paramHttpServletRequest.getParameter("TransferAccountRepsIds");
    }
    int i = str.indexOf(",");
    if (i == -1) {
      localArrayList.add(str);
    }
    Object localObject;
    if ((str != null) && (i > -1))
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(str, ",");
      localObject = "";
      while (localStringTokenizer.hasMoreTokens())
      {
        localObject = localStringTokenizer.nextToken();
        localArrayList.add(localObject);
      }
    }
    for (int j = 0; j < localArrayList.size(); j++)
    {
      localObject = findBusiness(localHttpSession, (String)localArrayList.get(j), paramInt);
      if ((localObject != null) && (((com.ffusion.beans.business.Business)localObject).getAffiliateBankID() == Integer.parseInt(paramString)))
      {
        bool = true;
        break;
      }
    }
    return bool;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Object localObject = new Businesses((Locale)localHttpSession.getAttribute("java.util.Locale"));
    com.ffusion.beans.business.Business localBusiness = new com.ffusion.beans.business.Business((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localBusiness.setBankId(localSecureUser.getBankID());
    localBusiness.setBusinessName(this.gv);
    localBusiness.setStatus(this.gu);
    localBusiness.setPersonalBanker(this.gx);
    localBusiness.setAccountRep(this.gC);
    ArrayList localArrayList1 = null;
    ArrayList localArrayList2 = null;
    if ((this.gw != null) && ((AffiliateBankAssignment.hasAffiliatedBanks(this.gw)) || (AffiliateBankAssignment.isMultiBank(this.gw))))
    {
      localArrayList2 = AffiliateBankAssignment.getAllAffiliateBanks(localHttpSession);
      localArrayList1 = AffiliateBankAssignment.parseAffiliateIds(localArrayList2, this.gw);
    }
    BusinessEmployee localBusinessEmployee = null;
    if ((this.gy != null) || (this.gA != null))
    {
      localBusinessEmployee = new BusinessEmployee((Locale)localHttpSession.getAttribute("java.util.Locale"));
      localBusinessEmployee.setFirstName(this.gy);
      localBusinessEmployee.setLastName(this.gA);
    }
    try
    {
      HashMap localHashMap = new HashMap(2);
      localHashMap.put("BUSINESSES", localObject);
      if ((localArrayList1 != null) && (localArrayList2 != null))
      {
        Iterator localIterator = localArrayList1.iterator();
        Businesses localBusinesses2 = new Businesses((Locale)localHttpSession.getAttribute("java.util.Locale"));
        while (localIterator.hasNext())
        {
          AffiliateBank localAffiliateBank = (AffiliateBank)localIterator.next();
          localBusiness.setAffiliateBankID(localAffiliateBank.getId());
          localBusinesses2 = com.ffusion.csil.core.Business.getFilteredBusinesses(localSecureUser, localBusiness, localBusinessEmployee, localHashMap);
          ((Businesses)localObject).addAll(localBusinesses2);
        }
      }
      else if ((this.gw != null) && (!this.gw.trim().equals("")))
      {
        localBusiness.setAffiliateBankID(this.gw);
        localObject = com.ffusion.csil.core.Business.getFilteredBusinesses(localSecureUser, localBusiness, localBusinessEmployee, localHashMap);
      }
      else
      {
        localObject = com.ffusion.csil.core.Business.getFilteredBusinesses(localSecureUser, localBusiness, localBusinessEmployee, localHashMap);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      if (this.gB)
      {
        Businesses localBusinesses1 = (Businesses)localHttpSession.getAttribute(this.gz);
        if (localBusinesses1 != null)
        {
          localBusinesses1.set((Businesses)localObject);
          localObject = localBusinesses1;
        }
      }
      localHttpSession.setAttribute(this.gz, localObject);
      str = this.successURL;
    }
    return str;
  }
  
  public void setSearchListName(String paramString)
  {
    this.gz = paramString;
  }
  
  public void setAppendTo(boolean paramBoolean)
  {
    this.gB = paramBoolean;
  }
  
  public void setAppendTo(String paramString)
  {
    this.gB = paramString.equalsIgnoreCase("true");
  }
  
  public boolean getAppendToBoolean()
  {
    return this.gB;
  }
  
  public String getAppendTo()
  {
    return this.gB == true ? "true" : "false";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.GetFilteredBusinesses
 * JD-Core Version:    0.7.0.1
 */