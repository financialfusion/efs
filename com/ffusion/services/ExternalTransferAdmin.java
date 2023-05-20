package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.exttransfers.ExtTransferAccounts;
import com.ffusion.beans.exttransfers.ExtTransferCompanies;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
import com.ffusion.csil.CSILException;
import java.util.HashMap;

public abstract interface ExternalTransferAdmin
{
  public abstract int initialize(String paramString)
    throws CSILException;
  
  public abstract Accounts getAccountsForExtTransfer(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ExtTransferCompanies getExtTransferCompanies(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ExtTransferCompany addExtTransferCompany(SecureUser paramSecureUser, ExtTransferCompany paramExtTransferCompany, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void modifyExtTransferCompany(SecureUser paramSecureUser, ExtTransferCompany paramExtTransferCompany1, ExtTransferCompany paramExtTransferCompany2, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void deleteExtTransferCompany(SecureUser paramSecureUser, ExtTransferCompany paramExtTransferCompany, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void addExtTransferAccount(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, ExtTransferCompany paramExtTransferCompany, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ExtTransferAccounts getExtTransferAccounts(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, ExtTransferCompany paramExtTransferCompany, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void deleteExtTransferAccount(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, ExtTransferCompany paramExtTransferCompany, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void modifyExtTransferAccount(SecureUser paramSecureUser, ExtTransferAccount paramExtTransferAccount, ExtTransferCompany paramExtTransferCompany, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ExtTransferAccounts getExtTransferAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ExternalTransferAdminException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ExternalTransferAdmin
 * JD-Core Version:    0.7.0.1
 */