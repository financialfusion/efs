package com.ffusion.fileimporter.fileadapters;

import com.ffusion.beans.Currency;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.fileimporter.ErrorMessage;
import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.fileimporter.ImportError;
import com.ffusion.beans.fileimporter.ValidationError;
import com.ffusion.csil.FIException;
import com.ffusion.ffs.bpw.util.swift.SWIFTParseException;
import com.ffusion.ffs.bpw.util.swift.SWIFTParser;
import com.ffusion.msgbroker.factory.MBInstanceFactory;
import com.ffusion.msgbroker.interfaces.IMBInstance;
import com.ffusion.msgbroker.interfaces.MBException;
import com.ffusion.util.enums.UserAssignedAmount;
import com.ffusion.util.logging.DebugLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;

public class TransferImportAdapter
  implements IFileAdapter
{
  private String aRl = null;
  private String aRc = null;
  private String aQ8 = null;
  private String aRi = null;
  private boolean aRe;
  private String aRd = null;
  private String aRm = null;
  private String aQ9 = null;
  private String aRb = null;
  private String aRg = null;
  private String aRf = null;
  private String aRj = null;
  private String aRa = null;
  private IMBInstance aRk = null;
  private HashMap aRh = null;
  
  public synchronized void initialize(HashMap paramHashMap)
    throws FIException
  {
    String str1 = "TransferImportAdapter : initialize";
    HashMap localHashMap = null;
    try
    {
      localHashMap = (HashMap)paramHashMap.get("MESSAGEBROKER");
    }
    catch (Exception localException)
    {
      FIException localFIException1 = new FIException(str1, 9610, "Performed a get on a null HashMap", localException);
      DebugLog.throwing("Performed a get on a null HashMap", localFIException1);
      throw localFIException1;
    }
    if (localHashMap == null)
    {
      DebugLog.log("Settings HashMaps cannot be null. ");
      throw new FIException(str1, 9610, "Settings HashMaps cannot be null");
    }
    int i = 0;
    this.aRb = ((String)localHashMap.get("USERID"));
    this.aRg = ((String)localHashMap.get("PASSWORD"));
    this.aRi = ((String)localHashMap.get("DBTYPE"));
    this.aRl = ((String)localHashMap.get("MESSAGESET"));
    this.aRc = ((String)localHashMap.get("MESSAGENAME101"));
    this.aQ8 = ((String)localHashMap.get("MESSAGENAME103"));
    if ((this.aRb == null) || (this.aRg == null) || (this.aRi == null) || (this.aRl == null) || (this.aRc == null) || (this.aQ8 == null)) {
      i = 1;
    }
    int j = 0;
    String str2 = null;
    if (localHashMap.get("CTXFACTORY") != null)
    {
      j = 1;
      this.aRf = ((String)localHashMap.get("CTXFACTORY"));
      this.aRj = ((String)localHashMap.get("JNDIURL"));
      this.aRa = ((String)localHashMap.get("JNDICTX"));
      if ((this.aRj == null) || (this.aRa == null)) {
        i = 1;
      }
    }
    else
    {
      this.aQ9 = ((String)localHashMap.get("DBNAME"));
      if (this.aQ9 == null) {
        i = 1;
      }
      if ((this.aRi != null) && (!this.aRi.equals("DB2:AS390")))
      {
        str2 = (String)localHashMap.get("USETHINDRIVER");
        this.aRd = ((String)localHashMap.get("MACHINE"));
        this.aRm = ((String)localHashMap.get("PORTNUM"));
        if ((str2 == null) || (this.aRd == null) || (this.aRm == null)) {
          i = 1;
        }
      }
    }
    if (i != 0)
    {
      DebugLog.log("All connection fields required to connect to the database being used must be non-null");
      throw new FIException(str1, 9610, "All necessary MB connection params are required");
    }
    if ((str2 != null) && (str2.equalsIgnoreCase("true"))) {
      this.aRe = true;
    } else {
      this.aRe = false;
    }
    FIException localFIException2;
    try
    {
      if ((this.aRi.equalsIgnoreCase("ASA")) || (this.aRi.equalsIgnoreCase("ASE"))) {
        if (j != 0) {
          this.aRk = MBInstanceFactory.createHAInstanceFromJConnect(this.aRb, this.aRg, this.aRf, this.aRj, this.aRa);
        } else {
          this.aRk = MBInstanceFactory.createInstanceFromJConnect(this.aRb, this.aRg, this.aRd, this.aRm, this.aQ9);
        }
      }
      if ((this.aRi.equalsIgnoreCase("ORACLE:OCI8")) || (this.aRi.equalsIgnoreCase("ORACLE:THIN"))) {
        this.aRk = MBInstanceFactory.createInstanceFromOracle(this.aRb, this.aRg, this.aRd, this.aRm, this.aQ9, this.aRe);
      }
      if (this.aRi.equalsIgnoreCase("DB2:NET")) {
        this.aRk = MBInstanceFactory.createInstanceFromDB2(this.aRb, this.aRg, this.aRd, this.aRm, this.aQ9, false);
      }
      if (this.aRi.equalsIgnoreCase("DB2:APP")) {
        this.aRk = MBInstanceFactory.createInstanceFromDB2(this.aRb, this.aRg, this.aRd, this.aRm, this.aQ9, true);
      }
      if (this.aRi.equalsIgnoreCase("DB2:UN2")) {
        this.aRk = MBInstanceFactory.createInstanceFromDB2UniversalDriver(this.aRb, this.aRg, this.aQ9);
      }
      if (this.aRi.equalsIgnoreCase("DB2:AS390")) {
        this.aRk = MBInstanceFactory.createInstanceFromDB2390(this.aRb, this.aRg, this.aQ9);
      }
      if (this.aRi.equalsIgnoreCase("MSSQL:THIN")) {
        this.aRk = MBInstanceFactory.createInstanceFromMSSQL(this.aRb, this.aRg, this.aRd, this.aRm, this.aQ9);
      }
    }
    catch (MBException localMBException1)
    {
      localFIException2 = new FIException(9600, localMBException1);
      DebugLog.throwing("Could not connect to DB", localFIException2);
      throw localFIException2;
    }
    if (this.aRk == null)
    {
      DebugLog.log("The MBInstanceFactory returned a null instance, or failed due to an unsupported db type");
      throw new FIException(str1, 9601, "MBInstanceFactory returned a null instance, or failed due to an unsupported db type");
    }
    try
    {
      this.aRk.loadMessageSet(this.aRl);
    }
    catch (MBException localMBException2)
    {
      localFIException2 = new FIException(str1, 9605, "Could not load MB message set", localMBException2);
      DebugLog.throwing("Could not load the MB message set", localFIException2);
      throw localFIException2;
    }
  }
  
  public void open(HashMap paramHashMap)
    throws FIException
  {}
  
  public void close(HashMap paramHashMap)
    throws FIException
  {}
  
  public void processFile(InputStream paramInputStream, HashMap paramHashMap)
    throws FIException
  {
    String str1 = "TransferImportAdapter:processFile";
    ErrorMessages localErrorMessages = new ErrorMessages();
    if (this.aRk == null)
    {
      DebugLog.log("MB Instance is null. Cannot proceed with parse.");
      throw new FIException(str1, 9603, "MB Instance is null, cannot proceed.");
    }
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      char[] arrayOfChar = new char[100];
      int i = 0;
      while ((i = localBufferedReader.read(arrayOfChar, 0, 100)) > 0) {
        localStringBuffer.append(arrayOfChar, 0, i);
      }
    }
    catch (IOException localIOException)
    {
      localObject1 = new FIException(str1, 9608, "InputStream readLine failed.", localIOException);
      DebugLog.throwing("InputStream readLine failed", (Throwable)localObject1);
      throw ((Throwable)localObject1);
    }
    String str2 = localStringBuffer.toString();
    Object localObject1 = null;
    try
    {
      localObject1 = SWIFTParser.getInstance();
      ((SWIFTParser)localObject1).initialize(this.aRk);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      localObject2 = new FIException(9603, localException);
      DebugLog.throwing("Unable to create a SWIFT file parser", (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    if (localObject1 == null)
    {
      DebugLog.log("SWIFT parser used to parse Payment file is null");
      throw new FIException(str1, 9603, "createParser returned null");
    }
    if (((SWIFTParser)localObject1).isInitialized()) {
      DebugLog.log("SWIFT parser IS INITIALIZED");
    } else {
      DebugLog.log("SWIFT parser IS NOT INITIALIZED");
    }
    Transfers localTransfers = new Transfers();
    Object localObject2 = null;
    try
    {
      localObject2 = ((SWIFTParser)localObject1).parse(str2, "com.ffusion.beans.banking.Transfer", "MT103");
    }
    catch (SWIFTParseException localSWIFTParseException)
    {
      localSWIFTParseException.printStackTrace();
      throw new FIException(str1, 9604, "Parse errors");
    }
    Accounts localAccounts = (Accounts)paramHashMap.get("TransferAccounts");
    localAccounts.setFilter("All");
    if ((localAccounts == null) || (localAccounts.size() < 2)) {
      throw new FIException(getClass().getName(), 2, "Accounts not found in Extra hash map");
    }
    if (localObject2 != null)
    {
      long l = System.currentTimeMillis();
      for (int j = 0; j < localObject2.length; j++)
      {
        Transfer localTransfer = (Transfer)localObject2[j];
        l += 1L;
        localTransfer.setID("" + l);
        String str3 = null;
        if (localTransfer.getFromAccount() != null)
        {
          if (localTransfer.getFromAccount().getRoutingNum() != null) {
            str3 = localTransfer.getFromAccount().getRoutingNum();
          } else if (localTransfer.getFromAccount().getBicAccount() != null) {
            str3 = localTransfer.getFromAccount().getBicAccount();
          }
          if ((localTransfer.getFromAccountNum() != null) && (localTransfer.getFromAccount().getNumber() == null)) {
            localTransfer.getFromAccount().setNumber(localTransfer.getFromAccountNum());
          }
        }
        Account localAccount1 = localAccounts.getByNumberAndRoutingNum(localTransfer.getFromAccountNum(), str3);
        Account localAccount2 = localAccounts.getByNumberAndBIC(localTransfer.getFromAccountNum(), str3);
        if ((localTransfer.getFromAccount() == null) || ((localAccount1 == null) && (localAccount2 == null))) {
          localTransfer.addValidationError("Transfer From Account is not valid", localTransfer.getFromAccount() == null ? "" : localTransfer.getFromAccount().getDisplayText());
        } else if (localAccount1 != null) {
          localTransfer.setFromAccount(localAccount1);
        } else {
          localTransfer.setFromAccount(localAccount2);
        }
        String str4 = null;
        if (localTransfer.getToAccount() != null) {
          if (localTransfer.getToAccount().getRoutingNum() != null) {
            str4 = localTransfer.getToAccount().getRoutingNum();
          } else if (localTransfer.getToAccount().getBicAccount() != null) {
            str4 = localTransfer.getToAccount().getBicAccount();
          }
        }
        localAccount1 = localAccounts.getByNumberAndRoutingNum(localTransfer.getToAccountNum(), str4);
        localAccount2 = localAccounts.getByNumberAndBIC(localTransfer.getToAccountNum(), str4);
        if ((localTransfer.getToAccount() == null) || ((localAccount1 == null) && (localAccount2 == null))) {
          localTransfer.addValidationError("Transfer To Account is not valid", localTransfer.getToAccount() == null ? "" : localTransfer.getToAccount().getDisplayText());
        } else if (localAccount1 != null) {
          localTransfer.setToAccount(localAccount1);
        } else {
          localTransfer.setToAccount(localAccount2);
        }
        Object localObject3;
        Object localObject4;
        Object localObject5;
        if ((localTransfer.getValidationErrors() == null) || (localTransfer.getValidationErrors().size() == 0))
        {
          if (localTransfer.getAmountValue() != null)
          {
            localObject3 = localTransfer.getAmountValue().getCurrencyCode();
            localObject4 = localTransfer.getToAccount().getCurrencyCode();
            if (localObject4 == null) {
              localObject4 = "";
            }
            localObject5 = localTransfer.getFromAccount().getCurrencyCode();
            if (localObject5 == null) {
              localObject5 = "";
            }
            localTransfer.setUserAssignedAmountFlag(UserAssignedAmount.SINGLE);
            if (!((String)localObject4).equals(localObject5)) {
              if (((String)localObject4).equals(localObject3))
              {
                localTransfer.setUserAssignedAmountFlag(UserAssignedAmount.TO);
                localTransfer.setToAmount((Currency)localTransfer.getAmountValue().clone());
                localTransfer.getAmountValue().setCurrencyCode(localTransfer.getFromAccount().getCurrencyCode());
                localTransfer.getAmountValue().setAmount(new BigDecimal(0.0D));
              }
              else
              {
                localTransfer.setUserAssignedAmountFlag(UserAssignedAmount.FROM);
              }
            }
          }
          if (localTransfer.getFromAccount().get("EXTACCTID") != null) {
            localTransfer.setExtAcctId((String)localTransfer.getFromAccount().get("EXTACCTID"));
          } else if (localTransfer.getToAccount().get("EXTACCTID") != null) {
            localTransfer.setExtAcctId((String)localTransfer.getToAccount().get("EXTACCTID"));
          }
          localTransfers.add(localTransfer);
        }
        else
        {
          localObject3 = localTransfer.getValidationErrors().iterator();
          localErrorMessages.setOperationCanContinue(false);
          while (((Iterator)localObject3).hasNext())
          {
            localObject4 = (ValidationError)((Iterator)localObject3).next();
            localObject5 = new ImportError(1, ((ValidationError)localObject4).getTitle(), ((ValidationError)localObject4).getMessage(), "", null, new Integer(j));
            localErrorMessages.add((ErrorMessage)localObject5);
          }
        }
      }
    }
    paramHashMap.put("ImportTransfers", localTransfers);
    paramHashMap.put("ImportErrors", localErrorMessages);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.TransferImportAdapter
 * JD-Core Version:    0.7.0.1
 */