package com.ffusion.fileimporter.fileadapters;

import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.fileimporter.ImportError;
import com.ffusion.beans.fileimporter.ValidationError;
import com.ffusion.csil.FIException;
import com.ffusion.ffs.bpw.util.swift.SWIFTParseException;
import com.ffusion.ffs.bpw.util.swift.SWIFTParser;
import com.ffusion.msgbroker.factory.MBInstanceFactory;
import com.ffusion.msgbroker.interfaces.IMBInstance;
import com.ffusion.msgbroker.interfaces.MBException;
import com.ffusion.util.logging.DebugLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

public class PaymentImportAdapter
  implements IFileAdapter
{
  private String aUV = null;
  private String aUM = null;
  private String aUI = null;
  private String aUS = null;
  private boolean aUO;
  private String aUN = null;
  private String aUW = null;
  private String aUJ = null;
  private String aUL = null;
  private String aUQ = null;
  private String aUP = null;
  private String aUT = null;
  private String aUK = null;
  private IMBInstance aUU = null;
  private HashMap aUR = null;
  
  public synchronized void initialize(HashMap paramHashMap)
    throws FIException
  {
    String str1 = "PaymentImportAdapter : initialize";
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
    this.aUL = ((String)localHashMap.get("USERID"));
    this.aUQ = ((String)localHashMap.get("PASSWORD"));
    this.aUS = ((String)localHashMap.get("DBTYPE"));
    this.aUV = ((String)localHashMap.get("MESSAGESET"));
    this.aUM = ((String)localHashMap.get("MESSAGENAME101"));
    this.aUI = ((String)localHashMap.get("MESSAGENAME103"));
    if ((this.aUL == null) || (this.aUQ == null) || (this.aUS == null) || (this.aUV == null) || (this.aUM == null) || (this.aUI == null)) {
      i = 1;
    }
    int j = 0;
    String str2 = null;
    if (localHashMap.get("CTXFACTORY") != null)
    {
      j = 1;
      this.aUP = ((String)localHashMap.get("CTXFACTORY"));
      this.aUT = ((String)localHashMap.get("JNDIURL"));
      this.aUK = ((String)localHashMap.get("JNDICTX"));
      if ((this.aUT == null) || (this.aUK == null)) {
        i = 1;
      }
    }
    else
    {
      this.aUJ = ((String)localHashMap.get("DBNAME"));
      if (this.aUJ == null) {
        i = 1;
      }
      if ((this.aUS != null) && (!this.aUS.equals("DB2:AS390")))
      {
        str2 = (String)localHashMap.get("USETHINDRIVER");
        this.aUN = ((String)localHashMap.get("MACHINE"));
        this.aUW = ((String)localHashMap.get("PORTNUM"));
        if ((str2 == null) || (this.aUN == null) || (this.aUW == null)) {
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
      this.aUO = true;
    } else {
      this.aUO = false;
    }
    FIException localFIException2;
    try
    {
      if ((this.aUS.equalsIgnoreCase("ASA")) || (this.aUS.equalsIgnoreCase("ASE"))) {
        if (j != 0) {
          this.aUU = MBInstanceFactory.createHAInstanceFromJConnect(this.aUL, this.aUQ, this.aUP, this.aUT, this.aUK);
        } else {
          this.aUU = MBInstanceFactory.createInstanceFromJConnect(this.aUL, this.aUQ, this.aUN, this.aUW, this.aUJ);
        }
      }
      if ((this.aUS.equalsIgnoreCase("ORACLE:OCI8")) || (this.aUS.equalsIgnoreCase("ORACLE:THIN"))) {
        this.aUU = MBInstanceFactory.createInstanceFromOracle(this.aUL, this.aUQ, this.aUN, this.aUW, this.aUJ, this.aUO);
      }
      if (this.aUS.equalsIgnoreCase("DB2:NET")) {
        this.aUU = MBInstanceFactory.createInstanceFromDB2(this.aUL, this.aUQ, this.aUN, this.aUW, this.aUJ, false);
      }
      if (this.aUS.equalsIgnoreCase("DB2:APP")) {
        this.aUU = MBInstanceFactory.createInstanceFromDB2(this.aUL, this.aUQ, this.aUN, this.aUW, this.aUJ, true);
      }
      if (this.aUS.equalsIgnoreCase("DB2:UN2")) {
        this.aUU = MBInstanceFactory.createInstanceFromDB2UniversalDriver(this.aUL, this.aUQ, this.aUJ);
      }
      if (this.aUS.equalsIgnoreCase("DB2:AS390")) {
        this.aUU = MBInstanceFactory.createInstanceFromDB2390(this.aUL, this.aUQ, this.aUJ);
      }
      if (this.aUS.equalsIgnoreCase("MSSQL:THIN")) {
        this.aUU = MBInstanceFactory.createInstanceFromMSSQL(this.aUL, this.aUQ, this.aUN, this.aUW, this.aUJ);
      }
    }
    catch (MBException localMBException1)
    {
      localFIException2 = new FIException(9600, localMBException1);
      DebugLog.throwing("Could not connect to DB", localFIException2);
      throw localFIException2;
    }
    if (this.aUU == null)
    {
      DebugLog.log("The MBInstanceFactory returned a null instance, or failed due to an unsupported db type");
      throw new FIException(str1, 9601, "MBInstanceFactory returned a null instance, or failed due to an unsupported db type");
    }
    try
    {
      this.aUU.loadMessageSet(this.aUV);
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
    String str1 = "PaymentImportAdapter:processFile";
    ErrorMessages localErrorMessages = new ErrorMessages();
    if (this.aUU == null)
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
      ((SWIFTParser)localObject1).initialize(this.aUU);
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
    Payments localPayments = new Payments();
    Object localObject2 = null;
    try
    {
      localObject2 = ((SWIFTParser)localObject1).parse(str2, "com.ffusion.beans.billpay.Payment", "MT103");
    }
    catch (SWIFTParseException localSWIFTParseException)
    {
      localSWIFTParseException.printStackTrace();
      throw new FIException(str1, 9604, "Parse errors");
    }
    Payees localPayees = (Payees)paramHashMap.get("Payees");
    if ((localPayees == null) || (localPayees.size() == 0)) {
      throw new FIException(getClass().getName(), 2, "Payees not found in Extra hash map");
    }
    if (localObject2 != null)
    {
      long l = System.currentTimeMillis();
      for (int j = 0; j < localObject2.length; j++)
      {
        Payment localPayment = (Payment)localObject2[j];
        l += 1L;
        localPayment.setID("" + l);
        Object localObject3;
        if ((localPayment.getPayee() == null) || (localPayees.getByName(localPayment.getPayee().getName()) == null))
        {
          localPayment.addValidationError("Payee Not Found, please add Payee first", localPayment.getPayee() != null ? localPayment.getPayee().getName() : "");
        }
        else
        {
          localObject3 = localPayees.getByName(localPayment.getPayee().getName());
          ((Payee)localObject3).setNickName(((Payee)localObject3).getName());
          localPayment.setPayeeName(((Payee)localObject3).getName());
          localPayment.setPayee((Payee)localObject3);
        }
        if ((localPayment.getValidationErrors() == null) || (localPayment.getValidationErrors().size() == 0))
        {
          localPayments.add(localPayment);
        }
        else
        {
          localObject3 = localPayment.getValidationErrors().iterator();
          localErrorMessages.setOperationCanContinue(false);
          while (((Iterator)localObject3).hasNext())
          {
            ValidationError localValidationError = (ValidationError)((Iterator)localObject3).next();
            ImportError localImportError = new ImportError(1, localValidationError.getTitle(), localValidationError.getMessage(), "", null, new Integer(j));
            localErrorMessages.add(localImportError);
          }
        }
      }
    }
    Accounts localAccounts = (Accounts)paramHashMap.get("BillPayAccounts");
    if ((localAccounts == null) || (localAccounts.size() == 0)) {
      throw new FIException(getClass().getName(), 2, "Accounts not found in Extra hash map");
    }
    paramHashMap.put("ImportPayments", localPayments);
    paramHashMap.put("ImportErrors", localErrorMessages);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.PaymentImportAdapter
 * JD-Core Version:    0.7.0.1
 */