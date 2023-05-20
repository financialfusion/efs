package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AffiliateBankAssignment
  extends BaseTask
  implements AffiliateBankTask
{
  private boolean aPz = false;
  private String aPA = "TransferAffiliateBankIds";
  private String aPy = "TransferAssignedAffiliateBankIds";
  public static final String SESSION_AFFILIATE_BANKS = "AffiliateBanks";
  public static final String SESSION_TEMP_ASSIGNED_BANKS = "TempAssignedBanks";
  
  private boolean jdMethod_for(String paramString, AffiliateBanks paramAffiliateBanks)
  {
    boolean bool = false;
    Iterator localIterator = paramAffiliateBanks.iterator();
    AffiliateBank localAffiliateBank = null;
    while (localIterator.hasNext())
    {
      localAffiliateBank = (AffiliateBank)localIterator.next();
      if (localAffiliateBank.getId().equals(paramString)) {
        bool = true;
      }
    }
    return bool;
  }
  
  private void jdMethod_for(ArrayList paramArrayList, AffiliateBanks paramAffiliateBanks1, AffiliateBanks paramAffiliateBanks2)
  {
    if ((paramArrayList != null) && (paramAffiliateBanks1 != null) && (paramAffiliateBanks2 != null)) {
      for (int i = 0; i < paramArrayList.size(); i++)
      {
        Iterator localIterator = paramAffiliateBanks1.iterator();
        AffiliateBank localAffiliateBank = null;
        String str = null;
        while (localIterator.hasNext())
        {
          localAffiliateBank = (AffiliateBank)localIterator.next();
          str = (String)paramArrayList.get(i);
          if (localAffiliateBank.getId().equals(str))
          {
            if (!jdMethod_for(str, paramAffiliateBanks2)) {
              paramAffiliateBanks2.add(localAffiliateBank);
            }
            localIterator.remove();
          }
        }
      }
    }
  }
  
  private void jdMethod_for(HttpSession paramHttpSession, String paramString, AffiliateBanks paramAffiliateBanks)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramAffiliateBanks.iterator();
    AffiliateBank localAffiliateBank = null;
    while (localIterator.hasNext())
    {
      localAffiliateBank = (AffiliateBank)localIterator.next();
      localArrayList.add(localAffiliateBank.getId());
    }
    paramHttpSession.setAttribute(paramString, jdMethod_char(localArrayList));
  }
  
  private void jdMethod_for(AffiliateBanks paramAffiliateBanks1, AffiliateBanks paramAffiliateBanks2, HttpSession paramHttpSession)
  {
    if ((paramAffiliateBanks1 != null) && (paramAffiliateBanks2 != null))
    {
      jdMethod_for(paramHttpSession, getTransferAffiliateBankIdsKey(), paramAffiliateBanks2);
      jdMethod_for(paramHttpSession, getTransferAssignedAffiliateBankIds(), paramAffiliateBanks1);
    }
  }
  
  private String jdMethod_char(ArrayList paramArrayList)
  {
    String str = "";
    if ((paramArrayList != null) && (paramArrayList.size() > 0))
    {
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext()) {
        str = str + (String)localIterator.next() + ",";
      }
      str = str.substring(0, str.length() - 1);
    }
    return str;
  }
  
  public void synchLists(HttpSession paramHttpSession, ArrayList paramArrayList)
  {
    AffiliateBanks localAffiliateBanks1 = new AffiliateBanks();
    AffiliateBanks localAffiliateBanks2 = new AffiliateBanks();
    AffiliateBanks localAffiliateBanks3 = (AffiliateBanks)paramHttpSession.getAttribute("TempAssignedBanks");
    AffiliateBanks localAffiliateBanks4 = (AffiliateBanks)paramHttpSession.getAttribute("AffiliateBanks");
    if (localAffiliateBanks3 != null) {
      localAffiliateBanks1.addAll(localAffiliateBanks3);
    }
    if (localAffiliateBanks4 != null) {
      localAffiliateBanks2.addAll(localAffiliateBanks4);
    }
    jdMethod_for(paramArrayList, localAffiliateBanks1, localAffiliateBanks2);
    jdMethod_for(localAffiliateBanks1, localAffiliateBanks2, paramHttpSession);
    paramHttpSession.setAttribute("TempAssignedBanks", localAffiliateBanks1);
    paramHttpSession.setAttribute("AffiliateBanks", localAffiliateBanks2);
  }
  
  public void synchLists(HttpSession paramHttpSession)
  {
    synchLists(paramHttpSession, null);
  }
  
  public static ArrayList getAllAffiliateBanks(HttpSession paramHttpSession)
  {
    ArrayList localArrayList = new ArrayList();
    Collection localCollection1 = (Collection)paramHttpSession.getAttribute("AffiliateBanks");
    Collection localCollection2 = (Collection)paramHttpSession.getAttribute("TempAssignedBanks");
    if (localCollection1 != null) {
      localArrayList.addAll(localCollection1);
    }
    if (localCollection2 != null) {
      localArrayList.addAll(localCollection2);
    }
    return localArrayList;
  }
  
  public static AffiliateBanks getFilteredAffiliateBanks(BankEmployee paramBankEmployee, AffiliateBanks paramAffiliateBanks)
  {
    AffiliateBanks localAffiliateBanks = new AffiliateBanks();
    if (paramBankEmployee.getDefaultAffiliateBankId().trim().equalsIgnoreCase("0"))
    {
      localAffiliateBanks = paramAffiliateBanks;
    }
    else if (paramAffiliateBanks != null)
    {
      Iterator localIterator = paramAffiliateBanks.iterator();
      while (localIterator.hasNext())
      {
        AffiliateBank localAffiliateBank = (AffiliateBank)localIterator.next();
        if ((paramBankEmployee.getAffiliateBankIds().contains(localAffiliateBank.getId())) || (paramBankEmployee.getDefaultAffiliateBankId().equalsIgnoreCase(localAffiliateBank.getId()))) {
          localAffiliateBanks.add(localAffiliateBank);
        }
      }
    }
    return localAffiliateBanks;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = "Edit";
    if (isAdding()) {
      str = "Add";
    }
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ArrayList localArrayList = getAllAffiliateBanks(localHttpSession);
    BankEmployee localBankEmployee = (BankEmployee)localHttpSession.getAttribute(str + "BankEmployee");
    localBankEmployee.getAffiliateBankIds().clear();
    AffiliateBanks localAffiliateBanks1 = (AffiliateBanks)parseAffiliateIds(localArrayList, (String)localHttpSession.getAttribute(getTransferAffiliateBankIdsKey()));
    AffiliateBanks localAffiliateBanks2 = (AffiliateBanks)parseAffiliateIds(localArrayList, (String)localHttpSession.getAttribute(getTransferAssignedAffiliateBankIds()));
    localBankEmployee.setAffiliateBankIds(localAffiliateBanks2);
    AffiliateBank localAffiliateBank = null;
    if (localAffiliateBanks2 != null) {
      if (localAffiliateBanks2.size() == 0)
      {
        localBankEmployee.setDefaultAffiliateBankId("");
      }
      else if ((localAffiliateBanks2.size() == 1) || ((localAffiliateBanks2.size() > 1) && ((localBankEmployee.getDefaultAffiliateBankId() == null) || (localBankEmployee.getDefaultAffiliateBankId().trim().length() == 0))))
      {
        localAffiliateBank = (AffiliateBank)localAffiliateBanks2.get(0);
        localBankEmployee.setDefaultAffiliateBankId(localAffiliateBank.getId());
      }
    }
    localHttpSession.setAttribute("AffiliateBanks", localAffiliateBanks1);
    localHttpSession.setAttribute("TempAssignedBanks", localAffiliateBanks2);
    return null;
  }
  
  public static AffiliateBank findAffiliateBank(ArrayList paramArrayList, String paramString)
  {
    AffiliateBank localAffiliateBank = null;
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      localAffiliateBank = (AffiliateBank)localIterator.next();
      if (localAffiliateBank.getId().equalsIgnoreCase(paramString)) {
        break;
      }
    }
    return localAffiliateBank;
  }
  
  public static boolean hasAffiliatedBanks(String paramString)
  {
    boolean bool = false;
    if ((paramString != null) && (paramString.indexOf(",") > -1)) {
      bool = true;
    }
    return bool;
  }
  
  public static boolean isMultiBank(String paramString)
  {
    boolean bool = false;
    if ((paramString != null) && (!hasAffiliatedBanks(paramString)) && (paramString.trim().equalsIgnoreCase("0"))) {
      bool = true;
    }
    return bool;
  }
  
  public static AffiliateBanks convertAffiliateBanks(ArrayList paramArrayList)
  {
    AffiliateBanks localAffiliateBanks = new AffiliateBanks();
    AffiliateBank localAffiliateBank = null;
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      localAffiliateBank = (AffiliateBank)localIterator.next();
      localAffiliateBanks.add(localAffiliateBank);
    }
    return localAffiliateBanks;
  }
  
  public static BankEmployee getEmployeeFromSecureUser(HttpSession paramHttpSession)
  {
    return (BankEmployee)paramHttpSession.getAttribute("CurrentUser");
  }
  
  public static boolean hasAccessToEditEmployee(HttpSession paramHttpSession, BankEmployee paramBankEmployee, String paramString)
  {
    boolean bool = false;
    BankEmployee localBankEmployee = (BankEmployee)paramHttpSession.getAttribute(paramString);
    if (localBankEmployee == null) {
      return false;
    }
    if (localBankEmployee.isSameEmployee(paramBankEmployee)) {
      return false;
    }
    if (paramBankEmployee != null) {
      bool = localBankEmployee.affiliateBankComparisonAcceptable(paramBankEmployee);
    }
    return bool;
  }
  
  public static boolean hasAccessToEditEmployee(HttpSession paramHttpSession, BankEmployee paramBankEmployee, boolean paramBoolean)
  {
    String str = "Edit";
    if (!paramBoolean) {
      str = "Add";
    }
    return hasAccessToEditEmployee(paramHttpSession, paramBankEmployee, str + "BankEmployee");
  }
  
  public static ArrayList parseAffiliateIds(ArrayList paramArrayList, String paramString)
  {
    AffiliateBanks localAffiliateBanks = new AffiliateBanks();
    if (isMultiBank(paramString))
    {
      localAffiliateBanks = convertAffiliateBanks(paramArrayList);
    }
    else
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
      String str = "";
      while (localStringTokenizer.hasMoreTokens())
      {
        str = localStringTokenizer.nextToken();
        AffiliateBank localAffiliateBank = findAffiliateBank(paramArrayList, str);
        if (localAffiliateBank != null) {
          localAffiliateBanks.add(localAffiliateBank);
        }
      }
    }
    return localAffiliateBanks;
  }
  
  public void setAdding(String paramString)
  {
    setAdding(Boolean.valueOf(paramString).booleanValue());
  }
  
  public boolean isAdding()
  {
    return this.aPz;
  }
  
  public void setAdding(boolean paramBoolean)
  {
    this.aPz = paramBoolean;
  }
  
  public String getTransferAffiliateBankIdsKey()
  {
    return this.aPA;
  }
  
  public void setTransferAffiliateBankIdsKey(String paramString)
  {
    this.aPA = paramString;
  }
  
  public String getTransferAssignedAffiliateBankIds()
  {
    return this.aPy;
  }
  
  public void setTransferAssignedAffiliateBankIds(String paramString)
  {
    this.aPy = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.AffiliateBankAssignment
 * JD-Core Version:    0.7.0.1
 */