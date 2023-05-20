package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.messages.MessageCount;
import com.ffusion.beans.messages.MessageCounts;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueMembers;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Messages;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditBankEmployee
  extends AddBankEmployee
{
  private boolean oW = false;
  private String o0 = "1";
  private String oY = "1";
  private String oZ = "0";
  public static final String QUEUE_ASSIGNMENTS = "QUEUE_ASSIGNMENTS";
  private String oX = null;
  private String o1 = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.validInputURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BankEmployee localBankEmployee2;
    Object localObject;
    if (this.oW == true)
    {
      BankEmployee localBankEmployee1 = (BankEmployee)localHttpSession.getAttribute("ModifiedBankEmployee");
      if (localBankEmployee1 != null)
      {
        set(localBankEmployee1);
        localBankEmployee2 = new BankEmployee((Locale)localHttpSession.getAttribute("java.util.Locale"));
        localBankEmployee2.set(localBankEmployee1);
        localHttpSession.setAttribute("OldBankEmployee", localBankEmployee2);
        localObject = localBankEmployee1.getSecureUser();
        EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember((SecureUser)localObject);
        try
        {
          if (com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember, new Entitlement("Manage Corporate Banking", null, null))) {
            setManageCorporateBanking("1");
          } else {
            setManageCorporateBanking("0");
          }
          if (com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember, new Entitlement("Manage Consumer Banking", null, null))) {
            setManageConsumerBanking("1");
          } else {
            setManageConsumerBanking("0");
          }
          if (com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember, new Entitlement("BC Manage Multiple Banks Simultaneously", null, null))) {
            setManageMultipleBanks("1");
          } else {
            setManageMultipleBanks("0");
          }
          this.o0 = getManageConsumerBanking();
          this.oY = getManageCorporateBanking();
          this.oZ = getManageMultipleBanks();
        }
        catch (CSILException localCSILException2) {}
        setEmail2(getEmail());
        this.oW = false;
      }
      else
      {
        str = this.taskErrorURL;
        this.error = 5505;
      }
    }
    else
    {
      localBankEmployee2 = (BankEmployee)localHttpSession.getAttribute("ModifiedBankEmployee");
      localObject = (BankEmployee)localHttpSession.getAttribute("OldBankEmployee");
      if (localObject == null)
      {
        this.error = 5504;
        return this.taskErrorURL;
      }
      if (localBankEmployee2 == null)
      {
        this.error = 5505;
        return this.taskErrorURL;
      }
      boolean bool;
      try
      {
        if ((this.oX == null) || (this.oX.length() <= 0))
        {
          if ((this.o1 != null) && (this.o1.length() > 0))
          {
            this.error = 5;
            return this.taskErrorURL;
          }
          setPassword(((BankEmployee)localObject).getPassword());
          setPassword2(((BankEmployee)localObject).getPassword());
        }
        else
        {
          if ((this.o1 == null) || (!this.oX.equals(this.o1)))
          {
            this.error = 5;
            return this.taskErrorURL;
          }
          setPassword(this.oX);
          setPassword2(this.o1);
        }
        bool = validateInput(localHttpSession);
      }
      catch (CSILException localCSILException1)
      {
        MapError.mapError(localCSILException1);
        return this.serviceErrorURL;
      }
      if (!bool) {
        return this.taskErrorURL;
      }
      if (this.processFlag)
      {
        this.processFlag = false;
        if (this.error != 0) {
          return this.taskErrorURL;
        }
        if (jdMethod_for(localHttpSession, localBankEmployee2, (BankEmployee)localObject)) {
          str = this.successURL;
        } else {
          str = this.serviceErrorURL;
        }
      }
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
    throws CSILException
  {
    boolean bool = true;
    if (this.validate != null)
    {
      if (this.validate.indexOf("QUEUE_ASSIGNMENTS") != -1) {
        bool = checkAssignedCaseTypesForModify(paramHttpSession);
      }
      if (bool) {
        bool = super.validateInput(paramHttpSession);
      }
    }
    return bool;
  }
  
  protected boolean checkAssignedCaseTypesForModify(HttpSession paramHttpSession)
  {
    boolean bool = true;
    MessageQueues localMessageQueues1 = (MessageQueues)paramHttpSession.getAttribute("MessageQueues");
    MessageQueues localMessageQueues2 = (MessageQueues)paramHttpSession.getAttribute("MsgQueueAssignmentsActive");
    if ((localMessageQueues2 == null) || (localMessageQueues1 == null))
    {
      this.error = 8020;
      return false;
    }
    MessageQueues localMessageQueues3 = (MessageQueues)paramHttpSession.getAttribute("AppQueueAssignmentsActive");
    String str = getId();
    localMessageQueues1.setFilter("All");
    Iterator localIterator1 = localMessageQueues1.iterator();
    Object localObject2;
    while (localIterator1.hasNext())
    {
      localObject1 = (MessageQueue)localIterator1.next();
      MessageQueueMembers localMessageQueueMembers = ((MessageQueue)localObject1).getActiveQueueMembers();
      if ((localMessageQueueMembers != null) && (localMessageQueueMembers.size() == 1))
      {
        localObject2 = localMessageQueueMembers.getByID(str);
        if (localObject2 != null)
        {
          MessageQueue localMessageQueue = localMessageQueues2.getByID(((MessageQueue)localObject1).getId());
          if ((localMessageQueue == null) && (localMessageQueues3 != null)) {
            localMessageQueue = localMessageQueues3.getByID(((MessageQueue)localObject1).getId());
          }
          if (localMessageQueue == null)
          {
            this.error = 5527;
            return false;
          }
        }
      }
    }
    Object localObject1 = null;
    try
    {
      localObject1 = Messages.getAssignedMessageCountsByEmployee((SecureUser)paramHttpSession.getAttribute("SecureUser"), getId(), new HashMap());
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return false;
    }
    Iterator localIterator2 = ((MessageCounts)localObject1).iterator();
    while (localIterator2.hasNext())
    {
      localObject2 = (MessageCount)localIterator2.next();
      if (localMessageQueues2.getByID(((MessageCount)localObject2).getName()) == null)
      {
        this.error = 8070;
        bool = false;
        break;
      }
    }
    return bool;
  }
  
  private boolean jdMethod_for(HttpSession paramHttpSession, BankEmployee paramBankEmployee1, BankEmployee paramBankEmployee2)
  {
    boolean bool = true;
    HistoryTracker localHistoryTracker = null;
    Object localObject1;
    Object localObject2;
    try
    {
      HashMap localHashMap = null;
      localObject1 = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      BankEmployeeAdmin.modifyBankEmployee((SecureUser)localObject1, this, localHashMap);
      localObject2 = getSecureUser();
      EntitlementGroupMember localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember((SecureUser)localObject1);
      EntitlementGroupMember localEntitlementGroupMember2 = EntitlementsUtil.getEntitlementGroupMember((SecureUser)localObject2);
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = new com.ffusion.csil.beans.entitlements.Entitlements();
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = new com.ffusion.csil.beans.entitlements.Entitlements();
      localHistoryTracker = new HistoryTracker((SecureUser)localObject1, 18, paramBankEmployee1.getId());
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
      Entitlement localEntitlement;
      if (!this.oY.equals(getManageCorporateBanking())) {
        if (getManageCorporateBanking().equals("0"))
        {
          localEntitlement = new Entitlement("Manage Corporate Banking", null, null);
          localEntitlements1.add(localEntitlement);
          localHistoryTracker.logEntitlementAdd(localEntitlement, localEntitlementTypePropertyLists);
        }
        else
        {
          localEntitlement = new Entitlement("Manage Corporate Banking", null, null);
          localEntitlements2.add(localEntitlement);
          localHistoryTracker.logEntitlementDelete(localEntitlement, localEntitlementTypePropertyLists);
        }
      }
      if (!this.o0.equals(getManageConsumerBanking())) {
        if (getManageConsumerBanking().equals("0"))
        {
          localEntitlement = new Entitlement("Manage Consumer Banking", null, null);
          localEntitlements1.add(localEntitlement);
          localHistoryTracker.logEntitlementAdd(localEntitlement, localEntitlementTypePropertyLists);
        }
        else
        {
          localEntitlement = new Entitlement("Manage Consumer Banking", null, null);
          localEntitlements2.add(localEntitlement);
          localHistoryTracker.logEntitlementDelete(localEntitlement, localEntitlementTypePropertyLists);
        }
      }
      if (!this.oZ.equals(getManageMultipleBanks())) {
        if (getManageMultipleBanks().equals("0"))
        {
          localEntitlement = new Entitlement("BC Manage Multiple Banks Simultaneously", null, null);
          localEntitlements1.add(localEntitlement);
          localHistoryTracker.logEntitlementAdd(localEntitlement, localEntitlementTypePropertyLists);
        }
        else
        {
          localEntitlement = new Entitlement("BC Manage Multiple Banks Simultaneously", null, null);
          localEntitlements2.add(localEntitlement);
          localHistoryTracker.logEntitlementDelete(localEntitlement, localEntitlementTypePropertyLists);
        }
      }
      if (localEntitlements2.size() > 0) {
        com.ffusion.csil.core.Entitlements.removeRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2, localEntitlements2);
      }
      if (localEntitlements1.size() > 0) {
        com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2, localEntitlements1);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      paramBankEmployee1.set(this);
      addBankEmployeeHistory(paramHttpSession, "Modify", paramBankEmployee1, paramBankEmployee2, localHistoryTracker);
      BankEmployees localBankEmployees = (BankEmployees)paramHttpSession.getAttribute("BankEmployees");
      if (localBankEmployees != null)
      {
        localBankEmployees.setFilter("All");
        localObject1 = localBankEmployees.getByID(getId());
        if (localObject1 != null) {
          ((BankEmployee)localObject1).set(this);
        }
      }
      localObject1 = (BankEmployees)paramHttpSession.getAttribute("EmployeeSearchList");
      if (localObject1 != null)
      {
        localObject2 = ((BankEmployees)localObject1).getByID(getId());
        if ((paramBankEmployee2.getStatus().equals(getStatus())) && (localObject2 != null)) {
          ((BankEmployee)localObject2).set(this);
        } else if (localObject2 == null) {
          ((BankEmployees)localObject1).add(this);
        } else {
          ((BankEmployees)localObject1).remove(localObject2);
        }
      }
    }
    else
    {
      bool = false;
    }
    return bool;
  }
  
  public void setInit(String paramString)
  {
    this.oW = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setUpdatedPassword(String paramString)
  {
    this.oX = paramString;
  }
  
  public void setUpdatedPassword2(String paramString)
  {
    this.o1 = paramString;
  }
  
  public String getUpdatedPassword()
  {
    return this.oX;
  }
  
  public String getUpdatedPassword2()
  {
    return this.o1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.EditBankEmployee
 * JD-Core Version:    0.7.0.1
 */