package com.ffusion.ffs.bpw.ACHServices;

import com.ffusion.ffs.bpw.BPWServer;
import com.ffusion.ffs.bpw.interfaces.ACHBatchHist;
import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompOffsetAcctInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyList;
import com.ffusion.ffs.bpw.interfaces.ACHCompanySummaryList;
import com.ffusion.ffs.bpw.interfaces.ACHException;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.ACHFileHist;
import com.ffusion.ffs.bpw.interfaces.ACHFileInfo;
import com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.ACHPayeeList;
import com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo;
import com.ffusion.ffs.bpw.interfaces.BPWExtdHist;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo;
import com.ffusion.ffs.bpw.master.BPWEngine;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class ACHBPWServicesBean
  implements SessionBean
{
  SessionContext _context;
  
  public void ejbCreate()
    throws CreateException, RemoteException
  {}
  
  public void setSessionContext(SessionContext paramSessionContext)
    throws RemoteException
  {
    this._context = paramSessionContext;
  }
  
  public void ejbRemove()
    throws RemoteException
  {}
  
  public void ejbPassivate()
    throws RemoteException
  {}
  
  public void ejbActivate()
    throws RemoteException
  {}
  
  public void disconnect()
    throws RemoteException
  {}
  
  public ACHFIInfo addACHFIInfo(ACHFIInfo paramACHFIInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addACHFIInfo(paramACHFIInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addACHFIInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHFIInfo modACHFIInfo(ACHFIInfo paramACHFIInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modACHFIInfo(paramACHFIInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modACHFIInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHFIInfo getACHFIInfo(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHFIInfo(paramString);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHFIInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHFIInfo[] getFIInfoByRTN(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getFIInfoByRTN(paramString);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getFIInfoByRTN failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHFIInfo canACHFIInfo(ACHFIInfo paramACHFIInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.canACHFIInfo(paramACHFIInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** canACHFIInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHFIInfo activateACHFI(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.activateACHFI(paramString);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** activateACHFI failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHCompanyInfo addACHCompany(ACHCompanyInfo paramACHCompanyInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addACHCompany(paramACHCompanyInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addACHCompany failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHCompanyInfo modACHCompany(ACHCompanyInfo paramACHCompanyInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modACHCompany(paramACHCompanyInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modACHCompany failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHCompanyInfo canACHCompany(ACHCompanyInfo paramACHCompanyInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.canACHCompany(paramACHCompanyInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** canACHCompany failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHCompanyInfo activateCompany(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.activateCompany(paramString);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** activateCompany failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHCompanyInfo getACHCompany(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHCompany(paramString);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHCompany failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHCompanyList getACHCompanyList(ACHCompanyList paramACHCompanyList)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHCompanyList(paramACHCompanyList);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHCompanyList failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHCompanySummaryList getACHCompanySummaries(ACHCompanySummaryList paramACHCompanySummaryList)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHCompanySummaries(paramACHCompanySummaryList);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHCompanySummaries failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHPayeeInfo addACHPayee(ACHPayeeInfo paramACHPayeeInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addACHPayee(paramACHPayeeInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addACHPayee failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHPayeeInfo[] addACHPayee(ACHPayeeInfo[] paramArrayOfACHPayeeInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addACHPayee(paramArrayOfACHPayeeInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addACHPayee (Multiple) failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHPayeeInfo modACHPayee(ACHPayeeInfo paramACHPayeeInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modACHPayeeInfo(paramACHPayeeInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modACHPayee failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHPayeeInfo canACHPayee(ACHPayeeInfo paramACHPayeeInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.canACHPayeeInfo(paramACHPayeeInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** canACHPayee failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHPayeeInfo activatePayee(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.activatePayee(paramString);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** activatePayee failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHPayeeInfo getACHPayee(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHPayeeInfo(paramString);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHPayee failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHPayeeList getACHPayeeList(ACHPayeeList paramACHPayeeList)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHPayeeList(paramACHPayeeList);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHPayeeList failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHPayeeInfo updateACHPayeePrenoteStatus(ACHPayeeInfo paramACHPayeeInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.updateACHPayeePrenoteStatus(paramACHPayeeInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** updateACHPayeePrenoteStatus failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHBatchTemplateInfo addACHBatchTemplate(ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addACHBatchTemplate(paramACHBatchTemplateInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addACHBatchTemplate failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHBatchTemplateInfo modACHBatchTemplate(ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modACHBatchTemplate(paramACHBatchTemplateInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modACHBatchTemplate failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHBatchTemplateInfo deleteACHBatchTemplate(ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.deleteACHBatchTemplate(paramACHBatchTemplateInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** deleteACHBatchTemplate failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHBatchTemplateInfo getACHBatchTemplate(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHBatchTemplate(paramString);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHBatchTemplate failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHBatchTemplateInfo[] getACHBatchTemplate(String[] paramArrayOfString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHBatchTemplate(paramArrayOfString);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHBatchTemplate (Multiple) failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHBatchTemplateInfo[] getACHBatchTemplateByCompany(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHBatchTemplateByCompany(paramString);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHBatchTemplateByCompany failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHBatchTemplateInfo[] getACHBatchTemplateByGroup(String paramString1, String paramString2)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHBatchTemplateByGroup(paramString1, paramString2);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHBatchTemplateByGroup failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHBatchInfo addACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addACHBatch(paramACHBatchInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addACHBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHBatchInfo modACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modACHBatch(paramACHBatchInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modACHBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHBatchInfo canACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.canACHBatch(paramACHBatchInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** canACHBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHBatchInfo getACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHBatch(paramACHBatchInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHBatchHist getACHBatchHistory(ACHBatchHist paramACHBatchHist)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHBatchHistory(paramACHBatchHist);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHBatchHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHFileInfo addACHFile(ACHFileInfo paramACHFileInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addACHFile(paramACHFileInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addACHFile failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHFileInfo canACHFile(ACHFileInfo paramACHFileInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.canACHFile(paramACHFileInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** canACHFile failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHFileInfo getACHFile(ACHFileInfo paramACHFileInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHFile(paramACHFileInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHFile failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHFileHist getACHFileHistory(ACHFileHist paramACHFileHist)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHFileHistory(paramACHFileHist);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHFileHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public RecACHBatchInfo addRecACHBatch(RecACHBatchInfo paramRecACHBatchInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addRecACHBatch(paramRecACHBatchInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addRecACHBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public RecACHBatchInfo modRecACHBatch(RecACHBatchInfo paramRecACHBatchInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modRecACHBatch(paramRecACHBatchInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modRecACHBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public RecACHBatchInfo canRecACHBatch(RecACHBatchInfo paramRecACHBatchInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.canRecACHBatch(paramRecACHBatchInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** canRecACHBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public RecACHBatchInfo getRecACHBatch(RecACHBatchInfo paramRecACHBatchInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRecACHBatch(paramRecACHBatchInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getRecACHBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHBatchHist getRecACHBatchHistory(ACHBatchHist paramACHBatchHist)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getRecACHBatchHistory(paramACHBatchHist);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getRecACHBatchHistory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHCompOffsetAcctInfo addACHCompanyOffsetAccount(ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.addACHCompanyOffsetAccount(paramACHCompOffsetAcctInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** addACHCompanyOffsetAccount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHCompOffsetAcctInfo modACHCompanyOffsetAccount(ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.modACHCompnayOffsetAccount(paramACHCompOffsetAcctInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** modACHCompnayOffsetAccount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHCompOffsetAcctInfo canACHCompanyOffsetAccount(ACHCompOffsetAcctInfo paramACHCompOffsetAcctInfo)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.canACHCompanyOffsetAccount(paramACHCompOffsetAcctInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** canACHCompanyOffsetAccount failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHCompOffsetAcctInfo getACHCompanyOffsetAccountByAccountId(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHCompanyOffsetAccountByAccountId(paramString);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHCompanyOffsetAccountByAccountId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHCompOffsetAcctInfo[] getACHCompanyOffsetAccountByCompId(String paramString)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHCompanyOffsetAccountByCompId(paramString);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw new ACHException(localFFSException.getErrCode(), localFFSException.getMessage(), localFFSException);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHCompanyOffsetAccountByCompId failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(localThrowable.getMessage(), localThrowable);
    }
  }
  
  public ACHBatchInfo exportACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.exportACHBatch(paramACHBatchInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      String str = "*** exportACHBatch failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public ACHSameDayEffDateInfo getACHSameDayEffDateInfo(ACHSameDayEffDateInfo paramACHSameDayEffDateInfo)
    throws RemoteException, FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHSameDayEffDateInfo(paramACHSameDayEffDateInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHSameDayEffDateInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(str);
    }
  }
  
  public ACHSameDayEffDateInfo setACHSameDayEffDateInfo(ACHSameDayEffDateInfo paramACHSameDayEffDateInfo)
    throws RemoteException, FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.setACHSameDayEffDateInfo(paramACHSameDayEffDateInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      String str = "*** setACHSameDayEffDateInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public String getDefaultACHBatchEffectiveDate(String paramString1, String paramString2)
    throws RemoteException, FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getDefaultACHBatchEffectiveDate(paramString1, paramString2);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getDefaultACHBatchEffectiveDate failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public PagingInfo getPagedACH(PagingInfo paramPagingInfo)
    throws RemoteException, FFSException
  {
    if (!BPWServer.isAlive()) {
      throw new FFSException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getPagedACH(paramPagingInfo);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(FFSDebug.stackTrace(localFFSException), 0);
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getPagedACH failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new FFSException(localThrowable, str);
    }
  }
  
  public BPWExtdHist getACHTotals(BPWExtdHist paramBPWExtdHist)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHTotals(paramBPWExtdHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHTotals failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public BPWExtdHist getACHParticipantTotals(BPWExtdHist paramBPWExtdHist)
    throws RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      return localBPWEngine.getACHParticipantTotals(paramBPWExtdHist);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** getACHParticipantTotals failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
}


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.ACHServices.ACHBPWServicesBean
 * JD-Core Version:    0.7.0.1
 */