package com.ffusion.services.profile;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.efs.adapters.profile.AccountAdapter;
import com.ffusion.efs.adapters.profile.BusinessAdapter;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.ObjectPropertyAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.AccountService7;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class AccountService
  implements AccountService7
{
  private static final String jdField_do = "USD";
  private static final String jdField_for = "Restricted Calculations";
  private static final String[] jdField_new = { "Transfers To", "Transfers From", "Payments", "ACHBatch", "CCD + TXP", "Wire Templates Create", "Wire Domestic", "Wire International", "Wire Drawdown", "Wire FED", "Wire Book", "Stops", "Positive Pay", "Cash Con - Disbursement Request", "Cash Con - Deposit Entry" };
  private static final String[] a = { "External Transfers To" };
  private static final String[] jdField_byte = { "External Transfers To" };
  private static final String[][] jdField_try = { jdField_new, a, jdField_byte };
  private static final int jdField_case = 0;
  private static final int jdField_if = 1;
  private static final int jdField_int = 2;
  
  public Account addAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      Account localAccount = AccountAdapter.addAccount(paramAccount, paramSecureUser.getProfileID());
      mapAccount(localAccount);
      User localUser = new User();
      localUser.setId(String.valueOf(paramSecureUser.getProfileID()));
      Users localUsers = CustomerAdapter.getSecondaryUsers(paramSecureUser, localUser, paramHashMap);
      Accounts localAccounts = new Accounts();
      localAccounts.add(paramAccount);
      EntitlementsUtil.addSecondaryUserAccountRestrictions(paramSecureUser, localUsers, false, localAccounts);
      return localAccount;
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public void modifyAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      AccountAdapter.modifyAccount(paramAccount, paramSecureUser.getProfileID());
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public void modifyAccountById(SecureUser paramSecureUser, int paramInt, Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      AccountAdapter.modifyAccount(paramAccount, paramInt);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  private void a(SecureUser paramSecureUser, Account paramAccount, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    String str = paramAccount.getID();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 3;
    if ((str != null) && (str.length() > 0))
    {
      localStringBuffer.append(paramAccount.getBankID());
      localStringBuffer.append(":");
      localStringBuffer.append(paramAccount.getRoutingNum());
      localStringBuffer.append(":");
      localStringBuffer.append(paramAccount.getID());
      ObjectPropertyAdapter.deleteObjectProperty(i, localStringBuffer.toString(), "Restricted Calculations");
    }
    else
    {
      Accounts localAccounts = getAccountsById(paramSecureUser, paramInt, paramHashMap);
      if (localAccounts != null)
      {
        Iterator localIterator = localAccounts.iterator();
        while (localIterator.hasNext())
        {
          Account localAccount = (Account)localIterator.next();
          localStringBuffer.append(localAccount.getBankID());
          localStringBuffer.append(":");
          localStringBuffer.append(localAccount.getRoutingNum());
          localStringBuffer.append(":");
          localStringBuffer.append(localAccount.getID());
          ObjectPropertyAdapter.deleteObjectProperty(i, localStringBuffer.toString(), "Restricted Calculations");
          localStringBuffer.setLength(0);
        }
      }
    }
  }
  
  public void deleteAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      a(paramSecureUser, paramAccount, paramSecureUser.getProfileID(), paramHashMap);
      AccountAdapter.deleteAccount(paramAccount, paramSecureUser.getProfileID());
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public void deleteAccount(SecureUser paramSecureUser, Account paramAccount, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      a(paramSecureUser, paramAccount, paramInt, paramHashMap);
      AccountAdapter.deleteAccount(paramAccount, paramInt);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public Accounts mergeAccounts(SecureUser paramSecureUser, Accounts paramAccounts, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return AccountAdapter.mergeAccounts(paramAccounts, paramInt);
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public Account getAccount(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    throw new ProfileException(3800);
  }
  
  public Account getAccountAddress(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    AccountAdapter.getAccountAddress(paramAccount);
    return paramAccount;
  }
  
  public Accounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return getAccountsById(paramSecureUser, paramSecureUser.getPrimaryUserID(), paramHashMap);
  }
  
  public Accounts searchAccounts(SecureUser paramSecureUser, Account paramAccount, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      Accounts localAccounts = AccountAdapter.searchAccounts(paramAccount, paramInt);
      return localAccounts;
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public Accounts getAccounts(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      Accounts localAccounts = AccountAdapter.getAccounts(paramAccount, paramSecureUser.getProfileID());
      mapAccounts(localAccounts);
      setAccountFilters(localAccounts);
      return localAccounts;
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public Accounts getAccountsById(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      Accounts localAccounts = AccountAdapter.getAccountsById(paramInt);
      localAccounts.setLocale(paramSecureUser.getLocaleLanguage());
      mapAccounts(localAccounts);
      setAccountFilters(localAccounts);
      return localAccounts;
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public Accounts getAccountsByBusinessEmployee(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      Accounts localAccounts = AccountAdapter.getAccountsByBusinessEmployee(paramSecureUser);
      mapAccounts(localAccounts);
      setAccountFilters(localAccounts);
      return localAccounts;
    }
    catch (Exception localException)
    {
      throw new ProfileException(mapError(localException), localException);
    }
  }
  
  public void addAccounts(SecureUser paramSecureUser, Accounts paramAccounts, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      AccountAdapter.addAccounts(paramAccounts, paramInt);
      mapAccounts(paramAccounts);
      User localUser;
      Object localObject;
      if (paramSecureUser.getProfileID() == paramSecureUser.getPrimaryUserID())
      {
        localUser = new User();
        localUser.setId(String.valueOf(paramSecureUser.getProfileID()));
        localObject = CustomerAdapter.getSecondaryUsers(paramSecureUser, localUser, paramHashMap);
        EntitlementsUtil.addSecondaryUserAccountRestrictions(paramSecureUser, (Users)localObject, false, paramAccounts);
      }
      if (paramSecureUser.getUserType() == 2)
      {
        localUser = new User();
        localUser.setId(String.valueOf(paramInt));
        try
        {
          localUser = UserAdmin.getUserById(paramSecureUser, localUser, paramHashMap);
          if (User.CUSTOMER_TYPE_CONSUMER.equals(localUser.getCustomerType()))
          {
            localObject = localUser.getSecureUser();
            Users localUsers = CustomerAdapter.getSecondaryUsers((SecureUser)localObject, localUser, paramHashMap);
            EntitlementsUtil.addSecondaryUserAccountRestrictions((SecureUser)localObject, localUsers, false, paramAccounts);
          }
        }
        catch (Exception localException2) {}
      }
      a(paramSecureUser, paramAccounts, paramInt, true, paramHashMap);
    }
    catch (Exception localException1)
    {
      throw new ProfileException(mapError(localException1), localException1);
    }
  }
  
  public void initialize()
    throws ProfileException
  {}
  
  protected int mapError(Exception paramException)
  {
    int i = 2;
    paramException.printStackTrace(System.out);
    if ((paramException instanceof ProfileException))
    {
      ProfileException localProfileException = (ProfileException)paramException;
      DebugLog.log("mapProfileException: " + localProfileException.where + " : " + localProfileException.why + " : " + localProfileException.code);
      switch (localProfileException.code)
      {
      case 0: 
        i = 0;
        break;
      case 1: 
        i = 3501;
        break;
      default: 
        i = 2;
      }
    }
    return i;
  }
  
  protected void mapAccounts(Accounts paramAccounts)
  {
    Iterator localIterator = paramAccounts.iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      mapAccount(localAccount);
    }
  }
  
  protected void mapAccount(Account paramAccount)
  {
    paramAccount.setAccountGroup(mapAccountType(paramAccount.getTypeValue()));
  }
  
  protected int mapAccountType(int paramInt)
  {
    return Account.getAccountGroupFromType(paramInt);
  }
  
  protected void setAccountFilters(Accounts paramAccounts)
  {
    Iterator localIterator = paramAccounts.iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      localAccount.setFilterable("TransferTo");
      localAccount.setFilterable("TransferFrom");
      localAccount.setFilterable("Transactions");
      localAccount.setFilterable("BillPay");
    }
  }
  
  private static void a(SecureUser paramSecureUser, Accounts paramAccounts, int paramInt, boolean paramBoolean, HashMap paramHashMap)
    throws ProfileException
  {
    com.ffusion.csil.beans.entitlements.Entitlements[] arrayOfEntitlements = a();
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    EntitlementGroup localEntitlementGroup = null;
    Entitlement localEntitlement = null;
    try
    {
      localEntitlementGroup = a(paramInt);
      if (localEntitlementGroup != null)
      {
        int i = localEntitlementGroup.getGroupId();
        Iterator localIterator1 = paramAccounts.iterator();
        while (localIterator1.hasNext())
        {
          Account localAccount = (Account)localIterator1.next();
          String str = EntitlementsUtil.getEntitlementObjectId(localAccount);
          Iterator localIterator2;
          if ((localAccount.getCoreAccount().equals("0")) || (localAccount.getCoreAccount().equals("2")))
          {
            localIterator2 = arrayOfEntitlements[0].iterator();
            while (localIterator2.hasNext())
            {
              localEntitlement = (Entitlement)localIterator2.next();
              localEntitlement.setObjectId(str);
            }
            if (paramBoolean) {
              com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(localEntitlementGroupMember, i, arrayOfEntitlements[0]);
            } else {
              com.ffusion.csil.core.Entitlements.removeRestrictedEntitlements(localEntitlementGroupMember, i, arrayOfEntitlements[0]);
            }
            if (!"USD".equals(localAccount.getCurrencyCode()))
            {
              localIterator2 = arrayOfEntitlements[1].iterator();
              while (localIterator2.hasNext())
              {
                localEntitlement = (Entitlement)localIterator2.next();
                localEntitlement.setObjectId(str);
              }
              if (paramBoolean) {
                com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(localEntitlementGroupMember, i, arrayOfEntitlements[1]);
              } else {
                com.ffusion.csil.core.Entitlements.removeRestrictedEntitlements(localEntitlementGroupMember, i, arrayOfEntitlements[1]);
              }
            }
          }
          else if (localAccount.getCoreAccount().equals("1"))
          {
            localIterator2 = arrayOfEntitlements[2].iterator();
            while (localIterator2.hasNext())
            {
              localEntitlement = (Entitlement)localIterator2.next();
              localEntitlement.setObjectId(str);
            }
            if (paramBoolean) {
              com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(localEntitlementGroupMember, i, arrayOfEntitlements[2]);
            } else {
              com.ffusion.csil.core.Entitlements.removeRestrictedEntitlements(localEntitlementGroupMember, i, arrayOfEntitlements[2]);
            }
          }
        }
      }
    }
    catch (Exception localException)
    {
      throw new ProfileException(2, localException);
    }
  }
  
  private static com.ffusion.csil.beans.entitlements.Entitlements[] a()
  {
    com.ffusion.csil.beans.entitlements.Entitlements[] arrayOfEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements[jdField_try.length];
    Entitlement localEntitlement = null;
    int i = 0;
    for (int j = 0; j < jdField_try.length; j++)
    {
      arrayOfEntitlements[j] = new com.ffusion.csil.beans.entitlements.Entitlements();
      for (int k = 0; k < jdField_try[j].length; k++)
      {
        localEntitlement = new Entitlement();
        localEntitlement.setOperationName(jdField_try[j][k]);
        localEntitlement.setObjectType("Account");
        arrayOfEntitlements[j].add(localEntitlement);
      }
    }
    try
    {
      if (com.ffusion.csil.core.Entitlements.entitlementTypeExists("Sample Bank Report - AcctSummary"))
      {
        localEntitlement = new Entitlement();
        localEntitlement.setOperationName("Sample Bank Report - AcctSummary");
        localEntitlement.setObjectType("Account");
        arrayOfEntitlements[0].add(localEntitlement);
      }
    }
    catch (CSILException localCSILException) {}
    return arrayOfEntitlements;
  }
  
  private static EntitlementGroup a(int paramInt)
    throws Exception
  {
    EntitlementGroup localEntitlementGroup = null;
    Business localBusiness = null;
    localBusiness = BusinessAdapter.getBusiness(paramInt);
    if (localBusiness != null)
    {
      EntitlementGroupMember localEntitlementGroupMember = com.ffusion.csil.core.Entitlements.getMember(localBusiness.getEntitlementGroupMember());
      localEntitlementGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroup(localEntitlementGroupMember.getEntitlementGroupId());
    }
    return localEntitlementGroup;
  }
  
  public ArrayList getRestrictedCalculations(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    int i = 3;
    StringBuffer localStringBuffer = new StringBuffer(paramAccount.getBankID());
    localStringBuffer.append(":");
    localStringBuffer.append(paramAccount.getRoutingNum());
    localStringBuffer.append(":");
    localStringBuffer.append(paramAccount.getID());
    String str1 = localStringBuffer.toString();
    String str2 = ObjectPropertyAdapter.getObjectPropertyValue(i, str1, "Restricted Calculations");
    ArrayList localArrayList = new ArrayList();
    if (str2 != null)
    {
      String str3 = ",";
      StringTokenizer localStringTokenizer = new StringTokenizer(str2, str3);
      while (localStringTokenizer.hasMoreTokens()) {
        localArrayList.add(localStringTokenizer.nextToken());
      }
    }
    return localArrayList;
  }
  
  public void saveRestrictedCalculations(SecureUser paramSecureUser, Account paramAccount, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException
  {
    int i = 3;
    String str1 = paramAccount.getBankID() + ":" + paramAccount.getRoutingNum() + ":" + paramAccount.getID();
    Iterator localIterator = paramArrayList.iterator();
    StringBuffer localStringBuffer = new StringBuffer();
    String str2 = ",";
    int j = 1;
    while (localIterator.hasNext()) {
      if (j != 0)
      {
        localStringBuffer.append((String)localIterator.next());
        j = 0;
      }
      else
      {
        String str3 = (String)localIterator.next();
        localStringBuffer.append(str2);
        localStringBuffer.append(str3);
      }
    }
    ObjectPropertyAdapter.modifyObjectProperty(i, str1, "Restricted Calculations", localStringBuffer.toString());
  }
  
  public Businesses getBusinessesForAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    return AccountAdapter.getBusinessesForAccount(paramAccount, paramHashMap);
  }
  
  public void fillAccountCollection(Accounts paramAccounts, HashMap paramHashMap)
    throws ProfileException
  {
    for (int i = 0; i < paramAccounts.size(); i++)
    {
      Account localAccount = (Account)paramAccounts.get(i);
      Accounts localAccounts = AccountAdapter.getAccounts(localAccount, -1);
      if (localAccounts.size() > 0) {
        localAccount.set((Account)localAccounts.get(0));
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.profile.AccountService
 * JD-Core Version:    0.7.0.1
 */