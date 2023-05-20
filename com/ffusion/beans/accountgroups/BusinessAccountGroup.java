package com.ffusion.beans.accountgroups;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.ExtendABean;
import java.text.Collator;

public class BusinessAccountGroup
  extends ExtendABean
  implements Comparable
{
  private static final String jdField_try = BusinessAccountGroup.class.getName();
  private static final String jdField_new = "ACCOUNT_GROUP";
  private int jdField_do;
  private String a;
  private String jdField_if;
  private int jdField_int;
  private Accounts jdField_for = new Accounts();
  
  public BusinessAccountGroup() {}
  
  public BusinessAccountGroup(int paramInt1, String paramString1, String paramString2, int paramInt2)
  {
    this();
    this.jdField_do = paramInt1;
    this.a = paramString1;
    this.jdField_if = paramString2;
    this.jdField_int = paramInt2;
  }
  
  public int getId()
  {
    return this.jdField_do;
  }
  
  public String getName()
  {
    return this.a;
  }
  
  public String getAcctGroupId()
  {
    return this.jdField_if;
  }
  
  public int getBusDirId()
  {
    return this.jdField_int;
  }
  
  public Accounts getAccounts()
  {
    return this.jdField_for;
  }
  
  public void setId(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public void setName(String paramString)
  {
    this.a = paramString;
  }
  
  public void setAcctGroupId(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public void setBusDirId(int paramInt)
  {
    this.jdField_int = paramInt;
  }
  
  public void setAccounts(Accounts paramAccounts)
  {
    this.jdField_for = paramAccounts;
  }
  
  public void addAccount(Account paramAccount)
  {
    this.jdField_for.add(paramAccount);
  }
  
  public void addAccounts(Accounts paramAccounts)
  {
    this.jdField_for.set(paramAccounts);
  }
  
  public void deleteAccount(Account paramAccount)
  {
    this.jdField_for.removeByID(paramAccount.getID());
  }
  
  public int compareTo(Object paramObject)
  {
    return compare(paramObject, "ID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    BusinessAccountGroup localBusinessAccountGroup = (BusinessAccountGroup)paramObject;
    if (paramString.equals("ID")) {
      return numStringCompare(String.valueOf(this.jdField_do), String.valueOf(localBusinessAccountGroup.getId()));
    }
    if (paramString.equals("NAME")) {
      return localCollator.compare(this.a.toLowerCase(this.locale), localBusinessAccountGroup.getName().toLowerCase(this.locale));
    }
    return super.compare(paramObject, paramString);
  }
  
  public void set(BusinessAccountGroup paramBusinessAccountGroup)
  {
    this.jdField_do = paramBusinessAccountGroup.getId();
    this.a = paramBusinessAccountGroup.getName();
    this.jdField_if = paramBusinessAccountGroup.getAcctGroupId();
    this.jdField_int = paramBusinessAccountGroup.getBusDirId();
    this.jdField_for = paramBusinessAccountGroup.getAccounts();
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(jdField_try, "ACCOUNT_GROUP", getId(), paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logDelete(jdField_try, "ACCOUNT_GROUP", getId(), paramString);
  }
  
  public void logModified(HistoryTracker paramHistoryTracker1, HistoryTracker paramHistoryTracker2, BusinessAccountGroup paramBusinessAccountGroup, String paramString)
  {
    paramHistoryTracker2.detectChange(jdField_try, "NAME", this.a, paramBusinessAccountGroup.getName(), "");
    paramHistoryTracker2.detectChange(jdField_try, "GROUP_ID", this.jdField_if, paramBusinessAccountGroup.getAcctGroupId(), "");
    boolean bool = paramHistoryTracker2.detectAndLogChange(paramHistoryTracker2.lookupField(jdField_try, "GROUP_ACCOUNTS"), this.jdField_for, paramBusinessAccountGroup.getAccounts(), new AccountHistAnalyzer(), paramHistoryTracker2.lookupComment(3), paramHistoryTracker2.lookupComment(4));
    if ((!this.a.equals(paramBusinessAccountGroup.getName())) || (!this.jdField_if.equals(paramBusinessAccountGroup.getAcctGroupId()))) {
      bool = true;
    }
    if (bool) {
      paramHistoryTracker1.logChange(paramHistoryTracker1.lookupField(Business.BEAN_NAME, Business.ACCOUNT_GROUP), null, String.valueOf(this.jdField_do), paramString);
    }
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(jdField_try, "ACCOUNT_GROUP", getId(), paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logDelete(jdField_try, "ACCOUNT_GROUP", getId(), paramILocalizable);
  }
  
  public void logModified(HistoryTracker paramHistoryTracker1, HistoryTracker paramHistoryTracker2, BusinessAccountGroup paramBusinessAccountGroup, ILocalizable paramILocalizable)
  {
    paramHistoryTracker2.detectChange(jdField_try, "NAME", this.a, paramBusinessAccountGroup.getName(), (ILocalizable)null);
    paramHistoryTracker2.detectChange(jdField_try, "GROUP_ID", this.jdField_if, paramBusinessAccountGroup.getAcctGroupId(), (ILocalizable)null);
    boolean bool = paramHistoryTracker2.detectAndLogChange(jdField_try, "GROUP_ACCOUNTS", this.jdField_for, paramBusinessAccountGroup.getAccounts(), new AccountHistAnalyzer(), paramHistoryTracker2.buildLocalizableComment(3), paramHistoryTracker2.buildLocalizableComment(4));
    if ((!this.a.equals(paramBusinessAccountGroup.getName())) || (!this.jdField_if.equals(paramBusinessAccountGroup.getAcctGroupId()))) {
      bool = true;
    }
    if (bool) {
      paramHistoryTracker1.logChange(Business.BEAN_NAME, Business.ACCOUNT_GROUP, null, String.valueOf(this.jdField_do), paramILocalizable);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accountgroups.BusinessAccountGroup
 * JD-Core Version:    0.7.0.1
 */