package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.business.Businesses;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.business.BusinessTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateAssignedBusinesses
  extends BaseTask
  implements BankEmployeeTask, BusinessTask
{
  private String hN;
  private String hO;
  
  private boolean jdMethod_for(Businesses paramBusinesses, AffiliateBanks paramAffiliateBanks)
  {
    boolean bool1 = true;
    if ((paramAffiliateBanks == null) || (paramAffiliateBanks.size() == 0)) {
      return true;
    }
    if (paramBusinesses != null)
    {
      Iterator localIterator = paramBusinesses.iterator();
      com.ffusion.beans.business.Business localBusiness = null;
      AffiliateBank localAffiliateBank = null;
      while (localIterator.hasNext())
      {
        boolean bool2 = false;
        localBusiness = (com.ffusion.beans.business.Business)localIterator.next();
        for (int i = 0; i < paramAffiliateBanks.size(); i++)
        {
          localAffiliateBank = (AffiliateBank)paramAffiliateBanks.get(i);
          if (localBusiness.getAffiliateBankID() == localAffiliateBank.getAffiliateBankID())
          {
            bool2 = true;
            break;
          }
        }
        if (!bool2)
        {
          this.error = 5533;
          bool1 = bool2;
          break;
        }
      }
    }
    return bool1;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = "";
    String str2 = "";
    String str3 = "";
    String str4 = "";
    if ((this.hO != null) && (this.hO.compareTo("PersonalBankerType") == 0))
    {
      str1 = "BusinessesSearchListByPersonalBanker";
      str2 = "TempAssignedPbBusinesses";
      str3 = "AddedPbBusinessNames";
      str4 = "RemovedPbBusinessNames";
    }
    else
    {
      str1 = "BusinessesSearchListByAccountRep";
      str2 = "TempAssignedArBusinesses";
      str3 = "AddedArBusinessNames";
      str4 = "RemovedArBusinessNames";
    }
    this.error = 0;
    String str5 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = null;
    localHashMap = new HashMap(1);
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    Businesses localBusinesses1 = (Businesses)localHttpSession.getAttribute(str1);
    Businesses localBusinesses2 = (Businesses)localHttpSession.getAttribute(str2);
    if (!jdMethod_for(localBusinesses2, (AffiliateBanks)localHttpSession.getAttribute("TempAssignedBanks")))
    {
      str5 = this.taskErrorURL;
    }
    else if ((localBusinesses1 == null) || (localBusinesses2 == null))
    {
      this.error = 5524;
      str5 = this.taskErrorURL;
    }
    else
    {
      Iterator localIterator1 = localBusinesses1.iterator();
      Object localObject1;
      Object localObject2;
      Object localObject3;
      while (localIterator1.hasNext())
      {
        int i = 0;
        com.ffusion.beans.business.Business localBusiness1 = (com.ffusion.beans.business.Business)localIterator1.next();
        localObject1 = localBusiness1.getId();
        localObject2 = localBusinesses2.iterator();
        com.ffusion.beans.business.Business localBusiness2;
        while (((Iterator)localObject2).hasNext())
        {
          localBusiness2 = (com.ffusion.beans.business.Business)((Iterator)localObject2).next();
          localObject3 = localBusiness2.getId();
          if (((String)localObject3).equals(localObject1))
          {
            i = 1;
            break;
          }
        }
        if (i == 0) {
          try
          {
            localBusiness2 = com.ffusion.csil.core.Business.getBusiness(localSecureUser, localBusiness1, localHashMap);
            localObject3 = new com.ffusion.beans.business.Business();
            ((com.ffusion.beans.business.Business)localObject3).set(localBusiness2);
            if ((this.hO != null) && (this.hO.compareTo("PersonalBankerType") == 0)) {
              localBusiness2.setPersonalBanker("0");
            } else {
              localBusiness2.setAccountRep("0");
            }
            localIterator1.remove();
            localArrayList1.add(localBusiness2.getBusinessName());
            com.ffusion.csil.core.Business.modifyBusiness(localSecureUser, localBusiness2, (com.ffusion.beans.business.Business)localObject3, localHashMap);
          }
          catch (CSILException localCSILException1)
          {
            this.error = MapError.mapError(localCSILException1);
            str5 = this.serviceErrorURL;
            break;
          }
        }
      }
      if (this.error == 0)
      {
        Iterator localIterator2 = localBusinesses2.iterator();
        while (localIterator2.hasNext())
        {
          int j = 0;
          localObject1 = (com.ffusion.beans.business.Business)localIterator2.next();
          localObject2 = ((com.ffusion.beans.business.Business)localObject1).getId();
          Iterator localIterator3 = localBusinesses1.iterator();
          Object localObject4;
          while (localIterator3.hasNext())
          {
            localObject3 = (com.ffusion.beans.business.Business)localIterator3.next();
            localObject4 = ((com.ffusion.beans.business.Business)localObject3).getId();
            if (((String)localObject4).equals(localObject2))
            {
              j = 1;
              break;
            }
          }
          if ((j == 0) && (this.hN != null)) {
            try
            {
              localObject3 = com.ffusion.csil.core.Business.getBusiness(localSecureUser, (com.ffusion.beans.business.Business)localObject1, localHashMap);
              localObject4 = new com.ffusion.beans.business.Business();
              ((com.ffusion.beans.business.Business)localObject4).set((com.ffusion.beans.business.Business)localObject3);
              if ((this.hO != null) && (this.hO.compareTo("PersonalBankerType") == 0)) {
                ((com.ffusion.beans.business.Business)localObject3).setPersonalBanker(this.hN);
              } else {
                ((com.ffusion.beans.business.Business)localObject3).setAccountRep(this.hN);
              }
              localBusinesses1.add(localObject3);
              localArrayList2.add(((com.ffusion.beans.business.Business)localObject3).getBusinessName());
              com.ffusion.csil.core.Business.modifyBusiness(localSecureUser, (com.ffusion.beans.business.Business)localObject3, (com.ffusion.beans.business.Business)localObject4, localHashMap);
            }
            catch (CSILException localCSILException2)
            {
              this.error = MapError.mapError(localCSILException2);
              str5 = this.serviceErrorURL;
              break;
            }
          }
        }
      }
      localHttpSession.setAttribute(str4, localArrayList1);
      localHttpSession.setAttribute(str3, localArrayList2);
    }
    return str5;
  }
  
  public void setAssignmentType(String paramString)
  {
    this.hO = paramString;
  }
  
  public void setEmployeeId(String paramString)
  {
    this.hN = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.UpdateAssignedBusinesses
 * JD-Core Version:    0.7.0.1
 */