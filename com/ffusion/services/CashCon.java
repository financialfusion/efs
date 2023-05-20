package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffTime;
import com.ffusion.beans.affiliatebank.CutOffTimes;
import com.ffusion.beans.cashcon.CashConAccount;
import com.ffusion.beans.cashcon.CashConAccounts;
import com.ffusion.beans.cashcon.CashConCompanies;
import com.ffusion.beans.cashcon.CashConCompany;
import com.ffusion.beans.cashcon.CashCons;
import com.ffusion.beans.cashcon.Location;
import com.ffusion.beans.cashcon.LocationSearchCriteria;
import com.ffusion.beans.cashcon.LocationSearchResults;
import com.ffusion.beans.cashcon.Locations;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.cashcon.CashConException;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public abstract interface CashCon
{
  public abstract int initialize(String paramString);
  
  public abstract com.ffusion.beans.cashcon.CashCon addCashCon(SecureUser paramSecureUser, com.ffusion.beans.cashcon.CashCon paramCashCon, HashMap paramHashMap)
    throws CashConException;
  
  public abstract com.ffusion.beans.cashcon.CashCon modifyCashCon(SecureUser paramSecureUser, com.ffusion.beans.cashcon.CashCon paramCashCon1, com.ffusion.beans.cashcon.CashCon paramCashCon2, HashMap paramHashMap)
    throws CashConException;
  
  public abstract void deleteCashCon(SecureUser paramSecureUser, com.ffusion.beans.cashcon.CashCon paramCashCon, HashMap paramHashMap)
    throws CashConException;
  
  public abstract CashCons getPagedPendingCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException;
  
  public abstract CashCons getNextPendingCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException;
  
  public abstract CashCons getPreviousPendingCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException;
  
  public abstract CashCons getPagedCompletedCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException;
  
  public abstract CashCons getNextCompletedCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException;
  
  public abstract CashCons getPreviousCompletedCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException;
  
  public abstract CashCons getPagedApprovalCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException;
  
  public abstract CashCons getNextApprovalCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException;
  
  public abstract CashCons getPreviousApprovalCashCons(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CashConException;
  
  public abstract IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CashConException;
  
  public abstract CashConCompanies getCashConCompanies(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CashConException;
  
  public abstract CashConCompany addCashConCompany(SecureUser paramSecureUser, CashConCompany paramCashConCompany, String paramString, HashMap paramHashMap)
    throws CashConException;
  
  public abstract void modifyCashConCompany(SecureUser paramSecureUser, CashConCompany paramCashConCompany1, CashConCompany paramCashConCompany2, String paramString, HashMap paramHashMap)
    throws CashConException;
  
  public abstract void deleteCashConCompany(SecureUser paramSecureUser, CashConCompany paramCashConCompany, String paramString, HashMap paramHashMap)
    throws CashConException;
  
  public abstract void addConcAccount(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException;
  
  public abstract CashConAccounts getConcAccounts(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException;
  
  public abstract void deleteConcAccount(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException;
  
  public abstract void addDisbAccount(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException;
  
  public abstract CashConAccounts getDisbAccounts(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException;
  
  public abstract void deleteDisbAccount(SecureUser paramSecureUser, CashConAccount paramCashConAccount, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException;
  
  public abstract void addConcCutOff(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException;
  
  public abstract CutOffTimes getConcCutOffs(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException;
  
  public abstract void deleteConcCutOff(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException;
  
  public abstract void addDisbCutOff(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException;
  
  public abstract CutOffTimes getDisbCutOffs(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException;
  
  public abstract void deleteDisbCutOff(SecureUser paramSecureUser, CutOffTime paramCutOffTime, CashConCompany paramCashConCompany, HashMap paramHashMap)
    throws CashConException;
  
  public abstract int getNumberOfLocations(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws CashConException;
  
  public abstract LocationSearchResults getLocations(SecureUser paramSecureUser, LocationSearchCriteria paramLocationSearchCriteria, HashMap paramHashMap)
    throws CashConException;
  
  public abstract Locations getLocationsByAccount(SecureUser paramSecureUser, String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean, HashMap paramHashMap)
    throws CashConException;
  
  public abstract Location getLocation(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CashConException;
  
  public abstract void addLocation(SecureUser paramSecureUser, Location paramLocation, HashMap paramHashMap)
    throws CashConException;
  
  public abstract void deleteLocation(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CashConException;
  
  public abstract void modifyLocation(SecureUser paramSecureUser, Location paramLocation, HashMap paramHashMap)
    throws CashConException;
  
  public abstract int getPendingCashConCount(SecureUser paramSecureUser, String paramString1, String paramString2, String[] paramArrayOfString, HashMap paramHashMap)
    throws CashConException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.CashCon
 * JD-Core Version:    0.7.0.1
 */