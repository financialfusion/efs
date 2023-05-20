package com.ffusion.ffs.bpw.ACHServices;

import com.ffusion.ffs.bpw.interfaces.ACHBatchHist;
import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyList;
import com.ffusion.ffs.bpw.interfaces.ACHCompanySummaryList;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.ACHFileHist;
import com.ffusion.ffs.bpw.interfaces.ACHFileInfo;
import com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.ACHPayeeList;
import com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo;
import com.ffusion.ffs.bpw.interfaces.BPWExtdHist;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo;
import com.ffusion.ffs.interfaces.FFSException;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public abstract interface ACHBPWServices
  extends EJBObject
{
  public abstract void disconnect()
    throws RemoteException;
  
  public abstract ACHFIInfo addACHFIInfo(ACHFIInfo paramACHFIInfo)
    throws RemoteException;
  
  public abstract ACHFIInfo modACHFIInfo(ACHFIInfo paramACHFIInfo)
    throws RemoteException;
  
  public abstract ACHFIInfo getACHFIInfo(String paramString)
    throws RemoteException;
  
  public abstract ACHFIInfo[] getFIInfoByRTN(String paramString)
    throws RemoteException;
  
  public abstract ACHFIInfo canACHFIInfo(ACHFIInfo paramACHFIInfo)
    throws RemoteException;
  
  public abstract ACHFIInfo activateACHFI(String paramString)
    throws RemoteException;
  
  public abstract ACHCompanyInfo addACHCompany(ACHCompanyInfo paramACHCompanyInfo)
    throws RemoteException;
  
  public abstract ACHCompanyInfo modACHCompany(ACHCompanyInfo paramACHCompanyInfo)
    throws RemoteException;
  
  public abstract ACHCompanyInfo canACHCompany(ACHCompanyInfo paramACHCompanyInfo)
    throws RemoteException;
  
  public abstract ACHCompanyInfo activateCompany(String paramString)
    throws RemoteException;
  
  public abstract ACHCompanyInfo getACHCompany(String paramString)
    throws RemoteException;
  
  public abstract ACHCompanyList getACHCompanyList(ACHCompanyList paramACHCompanyList)
    throws RemoteException;
  
  public abstract ACHCompanySummaryList getACHCompanySummaries(ACHCompanySummaryList paramACHCompanySummaryList)
    throws RemoteException;
  
  public abstract ACHPayeeInfo addACHPayee(ACHPayeeInfo paramACHPayeeInfo)
    throws RemoteException;
  
  public abstract ACHPayeeInfo[] addACHPayee(ACHPayeeInfo[] paramArrayOfACHPayeeInfo)
    throws RemoteException;
  
  public abstract ACHPayeeInfo modACHPayee(ACHPayeeInfo paramACHPayeeInfo)
    throws RemoteException;
  
  public abstract ACHPayeeInfo canACHPayee(ACHPayeeInfo paramACHPayeeInfo)
    throws RemoteException;
  
  public abstract ACHPayeeInfo activatePayee(String paramString)
    throws RemoteException;
  
  public abstract ACHPayeeInfo getACHPayee(String paramString)
    throws RemoteException;
  
  public abstract ACHPayeeList getACHPayeeList(ACHPayeeList paramACHPayeeList)
    throws RemoteException;
  
  public abstract ACHPayeeInfo updateACHPayeePrenoteStatus(ACHPayeeInfo paramACHPayeeInfo)
    throws RemoteException;
  
  public abstract ACHBatchTemplateInfo addACHBatchTemplate(ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws RemoteException;
  
  public abstract ACHBatchTemplateInfo modACHBatchTemplate(ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws RemoteException;
  
  public abstract ACHBatchTemplateInfo deleteACHBatchTemplate(ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws RemoteException;
  
  public abstract ACHBatchTemplateInfo getACHBatchTemplate(String paramString)
    throws RemoteException;
  
  public abstract ACHBatchTemplateInfo[] getACHBatchTemplate(String[] paramArrayOfString)
    throws RemoteException;
  
  public abstract ACHBatchTemplateInfo[] getACHBatchTemplateByCompany(String paramString)
    throws RemoteException;
  
  public abstract ACHBatchTemplateInfo[] getACHBatchTemplateByGroup(String paramString1, String paramString2)
    throws RemoteException;
  
  public abstract ACHBatchInfo addACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws RemoteException;
  
  public abstract ACHBatchInfo modACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws RemoteException;
  
  public abstract ACHBatchInfo canACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws RemoteException;
  
  public abstract ACHBatchInfo getACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws RemoteException;
  
  public abstract ACHBatchHist getACHBatchHistory(ACHBatchHist paramACHBatchHist)
    throws RemoteException;
  
  public abstract ACHFileInfo addACHFile(ACHFileInfo paramACHFileInfo)
    throws RemoteException;
  
  public abstract ACHFileInfo canACHFile(ACHFileInfo paramACHFileInfo)
    throws RemoteException;
  
  public abstract ACHFileInfo getACHFile(ACHFileInfo paramACHFileInfo)
    throws RemoteException;
  
  public abstract ACHFileHist getACHFileHistory(ACHFileHist paramACHFileHist)
    throws RemoteException;
  
  public abstract RecACHBatchInfo addRecACHBatch(RecACHBatchInfo paramRecACHBatchInfo)
    throws RemoteException;
  
  public abstract RecACHBatchInfo modRecACHBatch(RecACHBatchInfo paramRecACHBatchInfo)
    throws RemoteException;
  
  public abstract RecACHBatchInfo canRecACHBatch(RecACHBatchInfo paramRecACHBatchInfo)
    throws RemoteException;
  
  public abstract RecACHBatchInfo getRecACHBatch(RecACHBatchInfo paramRecACHBatchInfo)
    throws RemoteException;
  
  public abstract ACHBatchHist getRecACHBatchHistory(ACHBatchHist paramACHBatchHist)
    throws RemoteException;
  
  public abstract ACHCompOffsetAcctInfo addACHCompanyOffsetAccount(ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
    throws RemoteException;
  
  public abstract ACHCompOffsetAcctInfo modACHCompanyOffsetAccount(ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
    throws RemoteException;
  
  public abstract ACHCompOffsetAcctInfo canACHCompanyOffsetAccount(ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
    throws RemoteException;
  
  public abstract ACHCompOffsetAcctInfo getACHCompanyOffsetAccountByAccountId(String paramString)
    throws RemoteException;
  
  public abstract ACHCompOffsetAcctInfo[] getACHCompanyOffsetAccountByCompId(String paramString)
    throws RemoteException;
  
  public abstract ACHBatchInfo exportACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws RemoteException, FFSException;
  
  public abstract ACHSameDayEffDateInfo getACHSameDayEffDateInfo(ACHSameDayEffDateInfo paramACHSameDayEffDateInfo)
    throws RemoteException, FFSException;
  
  public abstract ACHSameDayEffDateInfo setACHSameDayEffDateInfo(ACHSameDayEffDateInfo paramACHSameDayEffDateInfo)
    throws RemoteException, FFSException;
  
  public abstract String getDefaultACHBatchEffectiveDate(String paramString1, String paramString2)
    throws RemoteException, FFSException;
  
  public abstract PagingInfo getPagedACH(PagingInfo paramPagingInfo)
    throws RemoteException, FFSException;
  
  public abstract BPWExtdHist getACHTotals(BPWExtdHist paramBPWExtdHist)
    throws RemoteException;
  
  public abstract BPWExtdHist getACHParticipantTotals(BPWExtdHist paramBPWExtdHist)
    throws RemoteException;
}


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.ACHServices.ACHBPWServices
 * JD-Core Version:    0.7.0.1
 */