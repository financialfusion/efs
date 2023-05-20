package com.ffusion.fileimporter.fileadapters;

import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferBanks;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.FIException;
import com.ffusion.csil.core.Wire;
import com.ffusion.ffs.bpw.interfaces.ValueInfo;
import com.ffusion.msgbroker.factory.MBInstanceFactory;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAcctWithInsti_57A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAcctWithInsti_57ABCD;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAcctWithInsti_57ACD;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAcctWithInsti_57D;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAdditionalLines;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAmount_32A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAmount_32B;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAmount_33B;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeBankOperationCode_23B;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeChargesAccount_25A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeCode_71A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeCreditor_50A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeCreditor_50AK;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeCreditor_50G;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeCreditor_50H;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeCreditor_50K;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeDate_30;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeDebtor_59;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeDebtor_59AN;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeDetailsOfCharges_71F;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeDetailsOfCharges_71G;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeDetailsPayment_70;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeEnvelopeContents_77T;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeExchangeRate_36;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeFXDealRef_21F;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeGeneralInformationSeq_101;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeInstructionCode_23E;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeInstructionParty_50C;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeInstructionParty_50CL;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeInstructionParty_50L;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeIntermediary_56A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeIntermediary_56ACD;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeIntermediary_56D;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeLine2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeMT101;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeMT103;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeNarrative;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeNarrativeText;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeOrderingCustomer_50GH;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeOrderingInstitution_52A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeOrderingInstitution_52AC;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeOrderingInstitution_52AD;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeOrderingInstitution_52C;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeOrderingInstitution_52D;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeParty;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeRelatedRef_21;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeSenderToReceiverInfoF1_72;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeSenderToReceiverInfoF2_72;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeSenderToReceiverInfo_72;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeSendingInsti_51A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeTimeIndication_13C;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeTransRefNum_20;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeTransTypeCode_26T;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeTransactionDetailsSeq_101;
import com.ffusion.msgbroker.interfaces.IMBInstance;
import com.ffusion.msgbroker.interfaces.IMBMessage;
import com.ffusion.msgbroker.interfaces.IParser;
import com.ffusion.msgbroker.interfaces.MBException;
import com.ffusion.util.EnhancedStringTokenizer;
import com.ffusion.util.logging.DebugLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class WireImportAdapter
  implements IFileAdapter
{
  private String aRN = null;
  private String aRE = null;
  private String aRA = null;
  private String aRK = null;
  private boolean aRG;
  private String aRF = null;
  private String aRO = null;
  private String aRB = null;
  private String aRD = null;
  private String aRI = null;
  private String aRH = null;
  private String aRL = null;
  private String aRC = null;
  private IMBInstance aRM = null;
  private HashMap aRJ = null;
  
  public synchronized void initialize(HashMap paramHashMap)
    throws FIException
  {
    String str1 = "WireImportAdapter : initialize";
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
    this.aRD = ((String)localHashMap.get("USERID"));
    this.aRI = ((String)localHashMap.get("PASSWORD"));
    this.aRK = ((String)localHashMap.get("DBTYPE"));
    this.aRN = ((String)localHashMap.get("MESSAGESET"));
    this.aRE = ((String)localHashMap.get("MESSAGENAME101"));
    this.aRA = ((String)localHashMap.get("MESSAGENAME103"));
    if ((this.aRD == null) || (this.aRI == null) || (this.aRK == null) || (this.aRN == null) || (this.aRE == null) || (this.aRA == null)) {
      i = 1;
    }
    int j = 0;
    String str2 = null;
    if (localHashMap.get("CTXFACTORY") != null)
    {
      j = 1;
      this.aRH = ((String)localHashMap.get("CTXFACTORY"));
      this.aRL = ((String)localHashMap.get("JNDIURL"));
      this.aRC = ((String)localHashMap.get("JNDICTX"));
      if ((this.aRL == null) || (this.aRC == null)) {
        i = 1;
      }
    }
    else
    {
      this.aRB = ((String)localHashMap.get("DBNAME"));
      if (this.aRB == null) {
        i = 1;
      }
      if ((this.aRK != null) && (!this.aRK.equals("DB2:AS390")))
      {
        str2 = (String)localHashMap.get("USETHINDRIVER");
        this.aRF = ((String)localHashMap.get("MACHINE"));
        this.aRO = ((String)localHashMap.get("PORTNUM"));
        if ((str2 == null) || (this.aRF == null) || (this.aRO == null)) {
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
      this.aRG = true;
    } else {
      this.aRG = false;
    }
    FIException localFIException2;
    try
    {
      if ((this.aRK.equalsIgnoreCase("ASA")) || (this.aRK.equalsIgnoreCase("ASE"))) {
        if (j != 0) {
          this.aRM = MBInstanceFactory.createHAInstanceFromJConnect(this.aRD, this.aRI, this.aRH, this.aRL, this.aRC);
        } else {
          this.aRM = MBInstanceFactory.createInstanceFromJConnect(this.aRD, this.aRI, this.aRF, this.aRO, this.aRB);
        }
      }
      if ((this.aRK.equalsIgnoreCase("ORACLE:OCI8")) || (this.aRK.equalsIgnoreCase("ORACLE:THIN"))) {
        this.aRM = MBInstanceFactory.createInstanceFromOracle(this.aRD, this.aRI, this.aRF, this.aRO, this.aRB, this.aRG);
      }
      if (this.aRK.equalsIgnoreCase("DB2:NET")) {
        this.aRM = MBInstanceFactory.createInstanceFromDB2(this.aRD, this.aRI, this.aRF, this.aRO, this.aRB, false);
      }
      if (this.aRK.equalsIgnoreCase("DB2:APP")) {
        this.aRM = MBInstanceFactory.createInstanceFromDB2(this.aRD, this.aRI, this.aRF, this.aRO, this.aRB, true);
      }
      if (this.aRK.equalsIgnoreCase("DB2:UN2")) {
        this.aRM = MBInstanceFactory.createInstanceFromDB2UniversalDriver(this.aRD, this.aRI, this.aRB);
      }
      if (this.aRK.equalsIgnoreCase("DB2:AS390")) {
        this.aRM = MBInstanceFactory.createInstanceFromDB2390(this.aRD, this.aRI, this.aRB);
      }
      if (this.aRK.equalsIgnoreCase("MSSQL:THIN")) {
        this.aRM = MBInstanceFactory.createInstanceFromMSSQL(this.aRD, this.aRI, this.aRF, this.aRO, this.aRB);
      }
    }
    catch (MBException localMBException1)
    {
      localFIException2 = new FIException(9600, localMBException1);
      DebugLog.throwing("Could not connect to DB", localFIException2);
      throw localFIException2;
    }
    if (this.aRM == null)
    {
      DebugLog.log("The MBInstanceFactory returned a null instance, or failed due to an unsupported db type");
      throw new FIException(str1, 9601, "MBInstanceFactory returned a null instance, or failed due to an unsupported db type");
    }
    try
    {
      this.aRM.loadMessageSet(this.aRN);
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
    String str1 = "WireImportAdapter:processFile";
    ErrorMessages localErrorMessages = new ErrorMessages();
    if (this.aRM == null)
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
      localObject1 = this.aRM.createParser();
    }
    catch (MBException localMBException1)
    {
      localObject2 = new FIException(9603, localMBException1);
      DebugLog.throwing("Unable to create a MB wire file parser", (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    if (localObject1 == null)
    {
      DebugLog.log("MB parser used to parse wire file is null");
      throw new FIException(str1, 9603, "createParser returned null");
    }
    IMBMessage localIMBMessage = null;
    Object localObject2 = new WireTransfers();
    String str3;
    ArrayList localArrayList;
    Object localObject5;
    Object localObject4;
    Object localObject6;
    if (str2.indexOf(":28D:") != -1)
    {
      str3 = "-}";
      localArrayList = new ArrayList();
      Object localObject3;
      try
      {
        localArrayList = EnhancedStringTokenizer.getTokens(str2, str3, true);
      }
      catch (Exception localException1)
      {
        localObject3 = new FIException(9604, localException1);
        DebugLog.throwing("com.ffusion.fileimporter.WireImportAdapter.processFile", (Throwable)localObject3);
        throw ((Throwable)localObject3);
      }
      for (int j = 0; j < localArrayList.size(); j++)
      {
        try
        {
          localObject3 = aj((String)localArrayList.get(j));
          localIMBMessage = ((IParser)localObject1).parseToIDL((String)localObject3, this.aRN, this.aRE);
        }
        catch (MBException localMBException2)
        {
          localObject5 = new FIException(9604, localMBException2);
          DebugLog.throwing("Unable to parse MBMessage", (Throwable)localObject5);
          throw ((Throwable)localObject5);
        }
        if (localIMBMessage == null)
        {
          DebugLog.log("MB parseToIDL returned null");
          throw new FIException(str1, 9604, "MB parseToIDL returned null");
        }
        localObject4 = (TypeMT101)localIMBMessage.getIDLInstance();
        localObject5 = ((TypeMT101)localObject4).TransactionDetailsSeq_101;
        localObject6 = (WireTransfer)((WireTransfers)localObject2).create();
        ((WireTransfer)localObject6).setWireType("SINGLE");
        WireTransferPayee localWireTransferPayee = new WireTransferPayee();
        ((WireTransfer)localObject6).setWirePayee(localWireTransferPayee);
        try
        {
          jdMethod_for((TypeMT101)localObject4, (WireTransfer)localObject6);
          jdMethod_for(localObject5[0], (WireTransfer)localObject6);
        }
        catch (Exception localException4)
        {
          throw new FIException(str1, 9604, "Could not map MT101 message", localException4);
        }
      }
    }
    else
    {
      str3 = "-}";
      localArrayList = new ArrayList();
      try
      {
        localArrayList = EnhancedStringTokenizer.getTokens(str2, str3, true);
      }
      catch (Exception localException2)
      {
        localObject4 = new FIException(9604, localException2);
        DebugLog.throwing("com.ffusion.fileimporter.WireImportAdapter.processFile", (Throwable)localObject4);
        throw ((Throwable)localObject4);
      }
      for (int k = 0; k < localArrayList.size(); k++)
      {
        try
        {
          localObject4 = aj((String)localArrayList.get(k));
          localIMBMessage = ((IParser)localObject1).parseToIDL((String)localObject4, this.aRN, this.aRA);
        }
        catch (MBException localMBException3)
        {
          localObject5 = new FIException(9604, localMBException3);
          DebugLog.throwing("Unable to parse MBMessage", (Throwable)localObject5);
          throw ((Throwable)localObject5);
        }
        if (localIMBMessage == null)
        {
          DebugLog.log("MB parseToIDL returned null");
          throw new FIException(str1, 9604, "MB parseToIDL returned null");
        }
        TypeMT103 localTypeMT103 = (TypeMT103)localIMBMessage.getIDLInstance();
        localObject5 = (WireTransfer)((WireTransfers)localObject2).create();
        ((WireTransfer)localObject5).setWireType("SINGLE");
        localObject6 = new WireTransferPayee();
        ((WireTransfer)localObject5).setWirePayee((WireTransferPayee)localObject6);
        try
        {
          jdMethod_for(localTypeMT103, (WireTransfer)localObject5);
        }
        catch (Exception localException3)
        {
          throw new FIException(str1, 9604, "Could not map MT103 message", localException3);
        }
      }
    }
    paramHashMap.put("ImportWireTransfers", localObject2);
    paramHashMap.put("ImportErrors", localErrorMessages);
  }
  
  private void jdMethod_for(TypeMT101 paramTypeMT101, WireTransfer paramWireTransfer)
  {
    TypeGeneralInformationSeq_101 localTypeGeneralInformationSeq_101 = paramTypeMT101.GeneralInformationSeq_101;
    String str1 = localTypeGeneralInformationSeq_101.TransRefNum_20.TransRefNum;
    String str2 = localTypeGeneralInformationSeq_101.Date_30.Date;
    paramWireTransfer.setComment(str1);
    paramWireTransfer.setDateFormat("yyMMdd");
    paramWireTransfer.setDueDate(str2);
    paramWireTransfer.setSettlementDate(str2);
    paramWireTransfer.setDateFormat("SHORT");
  }
  
  private void jdMethod_for(TypeTransactionDetailsSeq_101 paramTypeTransactionDetailsSeq_101, WireTransfer paramWireTransfer)
    throws Exception
  {
    WireTransferPayee localWireTransferPayee = paramWireTransfer.getWirePayee();
    WireTransferBank localWireTransferBank = new WireTransferBank();
    localWireTransferPayee.setDestinationBank(localWireTransferBank);
    String str = paramTypeTransactionDetailsSeq_101.RelatedRef_21.RelatedRef;
    if ((str != null) && (str.length() > 0))
    {
      localObject1 = new ValueInfo();
      ((ValueInfo)localObject1).setValue(str);
      ((ValueInfo)localObject1).setAction("add");
      paramWireTransfer.put("TRANSACTION_REFERENCE", localObject1);
    }
    if (paramTypeTransactionDetailsSeq_101.InstructionCode_23EExists)
    {
      localObject1 = paramTypeTransactionDetailsSeq_101.InstructionCode_23E;
      localObject2 = new StringBuffer();
      for (int i = 0; i < localObject1.length; i++)
      {
        ((StringBuffer)localObject2).append(localObject1[i].Type);
        if (localObject1[i].NarrativeExists)
        {
          ((StringBuffer)localObject2).append("/");
          ((StringBuffer)localObject2).append(localObject1[i].Narrative);
          ((StringBuffer)localObject2).append(" ");
        }
      }
      if (((StringBuffer)localObject2).length() > 0)
      {
        localObject3 = new ValueInfo();
        ((ValueInfo)localObject3).setValue(((StringBuffer)localObject2).toString().trim());
        ((ValueInfo)localObject3).setAction("add");
        paramWireTransfer.put("PAY_INSTRUCT", localObject3);
      }
    }
    Object localObject1 = paramTypeTransactionDetailsSeq_101.Amount_32B.Currency;
    Object localObject2 = paramTypeTransactionDetailsSeq_101.Amount_32B.Amount;
    paramWireTransfer.setAmtCurrencyType((String)localObject1);
    paramWireTransfer.setAmount((String)localObject2);
    Object localObject5;
    Object localObject7;
    if (paramTypeTransactionDetailsSeq_101.InstructionParty_50CLExists)
    {
      localObject3 = "";
      localObject4 = "";
      localObject5 = paramTypeTransactionDetailsSeq_101.InstructionParty_50CL;
      if (((TypeInstructionParty_50CL)localObject5).__preValueTag.equals("50C"))
      {
        localObject4 = ((TypeInstructionParty_50CL)localObject5).InstructionParty_50C.BIC;
        if (((TypeInstructionParty_50CL)localObject5).InstructionParty_50C.BICBranchCodeExists) {
          localObject4 = ((String)localObject4).concat(((TypeInstructionParty_50CL)localObject5).InstructionParty_50C.BICBranchCode);
        } else {
          localObject4 = ((String)localObject4).concat("XXX");
        }
      }
      else
      {
        localObject3 = ((TypeInstructionParty_50CL)localObject5).InstructionParty_50L.PartyIdentifier;
      }
      if (((String)localObject3).length() > 0)
      {
        localObject7 = new ValueInfo();
        ((ValueInfo)localObject7).setValue(localObject3);
        ((ValueInfo)localObject7).setAction("add");
        paramWireTransfer.put("INSTRUCTING_PARTY_NAME", localObject7);
      }
      if (((String)localObject4).length() > 0)
      {
        localObject7 = new ValueInfo();
        ((ValueInfo)localObject7).setValue(localObject4);
        ((ValueInfo)localObject7).setAction("add");
        paramWireTransfer.put("INSTRUCTING_PARTY_BICCODE", localObject7);
      }
    }
    Object localObject8;
    Object localObject10;
    Object localObject11;
    if (paramTypeTransactionDetailsSeq_101.OrderingCustomer_50GHExists)
    {
      localObject3 = null;
      localObject4 = "";
      localObject5 = "";
      localObject7 = "";
      localObject8 = "";
      localObject10 = null;
      localObject11 = paramTypeTransactionDetailsSeq_101.OrderingCustomer_50GH;
      if (((TypeOrderingCustomer_50GH)localObject11).__preValueTag.equals("50G"))
      {
        localObject3 = ((TypeOrderingCustomer_50GH)localObject11).Creditor_50G.Account;
        localObject10 = ((TypeOrderingCustomer_50GH)localObject11).Creditor_50G.BIC;
        if (((TypeOrderingCustomer_50GH)localObject11).Creditor_50G.BICBranchCodeExists) {
          localObject10 = ((String)localObject10).concat(((TypeOrderingCustomer_50GH)localObject11).Creditor_50G.BICBranchCode);
        } else {
          localObject10 = ((String)localObject10).concat("XXX");
        }
      }
      else
      {
        localObject3 = ((TypeOrderingCustomer_50GH)localObject11).Creditor_50H.Account;
        String[] arrayOfString = ((TypeOrderingCustomer_50GH)localObject11).Creditor_50H.NameAddr.Line;
        localObject4 = arrayOfString[0];
        if (arrayOfString.length > 1) {
          localObject5 = arrayOfString[1];
        }
        if (arrayOfString.length > 2) {
          localObject7 = arrayOfString[2];
        }
        if (arrayOfString.length > 3) {
          localObject8 = arrayOfString[3];
        }
      }
      paramWireTransfer.setFromAccountNum((String)localObject3);
    }
    if (paramTypeTransactionDetailsSeq_101.OrderingInstitution_52ACExists)
    {
      localObject3 = "";
      localObject4 = "";
      localObject5 = paramTypeTransactionDetailsSeq_101.OrderingInstitution_52AC;
      if (((TypeOrderingInstitution_52AC)localObject5).__preValueTag.equals("52A"))
      {
        localObject3 = ((TypeOrderingInstitution_52AC)localObject5).OrderingInstitution_52A.BIC;
        if (((TypeOrderingInstitution_52AC)localObject5).OrderingInstitution_52A.BICBranchCodeExists) {
          localObject3 = ((String)localObject3).concat(((TypeOrderingInstitution_52AC)localObject5).OrderingInstitution_52A.BICBranchCode);
        } else {
          localObject3 = ((String)localObject3).concat("XXX");
        }
        if ((((TypeOrderingInstitution_52AC)localObject5).OrderingInstitution_52A.PartyExists) && (((TypeOrderingInstitution_52AC)localObject5).OrderingInstitution_52A.Party.PartyIdentifierExists)) {
          localObject4 = ((TypeOrderingInstitution_52AC)localObject5).OrderingInstitution_52A.Party.PartyIdentifier;
        }
      }
      else
      {
        localObject4 = ((TypeOrderingInstitution_52AC)localObject5).OrderingInstitution_52C.PartyIdentifier;
      }
      if (((String)localObject4).length() > 0)
      {
        localObject7 = new ValueInfo();
        ((ValueInfo)localObject7).setValue(localObject4);
        ((ValueInfo)localObject7).setAction("add");
        paramWireTransfer.put("ACCT_SVC_INSTITUTION_NAME", localObject7);
      }
      if (((String)localObject3).length() > 0)
      {
        localObject7 = new ValueInfo();
        ((ValueInfo)localObject7).setValue(localObject3);
        ((ValueInfo)localObject7).setAction("add");
        paramWireTransfer.put("ACCT_SVC_INSTITUTION_BICCODE", localObject7);
      }
    }
    Object localObject3 = paramTypeTransactionDetailsSeq_101.Beneficiary_59AN;
    if (((TypeDebtor_59AN)localObject3).__preValueTag.equals("59A"))
    {
      DebugLog.log("WARNING: Tag 59A is not supported. Benficiary Account/Address is missing!");
    }
    else
    {
      if (((TypeDebtor_59AN)localObject3).Debtor_59.AccountExists) {
        localWireTransferPayee.setAccountNum(((TypeDebtor_59AN)localObject3).Debtor_59.Account);
      }
      localObject4 = ((TypeDebtor_59AN)localObject3).Debtor_59.NameAddr.Line;
      if ((localObject4 != null) && (localObject4.length > 0)) {
        jdMethod_for(localWireTransferPayee, (String[])localObject4);
      }
    }
    if (paramTypeTransactionDetailsSeq_101.AcctWithInsti_57ACDExists)
    {
      localObject4 = paramTypeTransactionDetailsSeq_101.AcctWithInsti_57ACD;
      if (((TypeAcctWithInsti_57ACD)localObject4).__preValueTag.equals("57A"))
      {
        paramWireTransfer.setWireDestination("INTERNATIONAL");
        localObject5 = ((TypeAcctWithInsti_57ACD)localObject4).AcctWithInsti_57A;
        if ((((TypeAcctWithInsti_57A)localObject5).PartyExists) && (((TypeAcctWithInsti_57A)localObject5).Party.PartyIdentifierExists))
        {
          localObject7 = ((TypeAcctWithInsti_57A)localObject5).Party.PartyIdentifier;
          if ((localObject7 != null) && (((String)localObject7).length() > 0))
          {
            localObject8 = new ValueInfo();
            ((ValueInfo)localObject8).setValue(localObject7);
            ((ValueInfo)localObject8).setAction("add");
            paramWireTransfer.put("PARTY_IDENTIFIER_57", localObject8);
          }
        }
        localObject7 = new StringBuffer(((TypeAcctWithInsti_57A)localObject5).BIC);
        if (((TypeAcctWithInsti_57A)localObject5).BICBranchCodeExists) {
          ((StringBuffer)localObject7).append(((TypeAcctWithInsti_57A)localObject5).BICBranchCode);
        } else {
          ((StringBuffer)localObject7).append("XXX");
        }
        localWireTransferBank.setRoutingSwift(((StringBuffer)localObject7).toString());
      }
      else if (((TypeAcctWithInsti_57ACD)localObject4).__preValueTag.equals("57D"))
      {
        localObject5 = ((TypeAcctWithInsti_57ACD)localObject4).AcctWithInsti_57D;
        if ((((TypeAcctWithInsti_57D)localObject5).PartyExists) && (((TypeAcctWithInsti_57D)localObject5).Party.PartyIdentifierExists))
        {
          localObject7 = ((TypeAcctWithInsti_57D)localObject5).Party.PartyIdentifier;
          int k = ((String)localObject7).indexOf("FW");
          if (k != -1)
          {
            localObject10 = ((String)localObject7).substring(k + 2);
            localWireTransferBank.setRoutingFedWire((String)localObject10);
            if (ai((String)localObject10) == true)
            {
              paramWireTransfer.setWireDestination("BOOKTRANSFER");
              localWireTransferPayee.setPayeeDestination("BOOK");
            }
          }
          else
          {
            DebugLog.log("WARNING: 57D is not allowed without //FW prefix!");
          }
        }
        localObject7 = ((TypeAcctWithInsti_57D)localObject5).NameAddr.Line;
        if ((localObject7 != null) && (localObject7.length > 0)) {
          try
          {
            localWireTransferBank.setBankName(localObject7[0]);
            localWireTransferBank.setStreet(localObject7[1]);
            localWireTransferBank.setStreet2(localObject7[2]);
            localWireTransferBank.setStreet3(localObject7[3]);
          }
          catch (Exception localException1) {}
        }
      }
    }
    if ((!paramWireTransfer.getWireDestination().equals("BOOKTRANSFER")) && (paramTypeTransactionDetailsSeq_101.Intermediary_56ACDExists))
    {
      localObject4 = new WireTransferBanks();
      localWireTransferPayee.setIntermediaryBanks((WireTransferBanks)localObject4);
      localObject5 = ((WireTransferBanks)localObject4).create();
      localObject7 = paramTypeTransactionDetailsSeq_101.Intermediary_56ACD;
      Object localObject9;
      if (((TypeIntermediary_56ACD)localObject7).__preValueTag.equals("56A"))
      {
        localObject9 = ((TypeIntermediary_56ACD)localObject7).Intermediary_56A;
        if ((((TypeIntermediary_56A)localObject9).PartyExists) && (((TypeIntermediary_56A)localObject9).Party.PartyIdentifierExists))
        {
          localObject10 = ((TypeIntermediary_56A)localObject9).Party.PartyIdentifier;
          if ((localObject10 != null) && (((String)localObject10).length() > 0))
          {
            localObject11 = new ValueInfo();
            ((ValueInfo)localObject11).setValue(localObject10);
            ((ValueInfo)localObject11).setAction("add");
            paramWireTransfer.put("PARTY_IDENTIFIER_56", localObject11);
          }
        }
        localObject10 = new StringBuffer(((TypeIntermediary_56A)localObject9).BIC);
        if (((TypeIntermediary_56A)localObject9).BICBranchCodeExists) {
          ((StringBuffer)localObject10).append(((TypeIntermediary_56A)localObject9).BICBranchCode);
        } else {
          ((StringBuffer)localObject10).append("XXX");
        }
        ((WireTransferBank)localObject5).setRoutingSwift(((StringBuffer)localObject10).toString());
      }
      else if (((TypeIntermediary_56ACD)localObject7).__preValueTag.equals("56D"))
      {
        localObject9 = ((TypeIntermediary_56ACD)localObject7).Intermediary_56D;
        if ((((TypeIntermediary_56D)localObject9).PartyExists) && (((TypeIntermediary_56D)localObject9).Party.PartyIdentifierExists))
        {
          localObject10 = ((TypeIntermediary_56D)localObject9).Party.PartyIdentifier;
          int m = ((String)localObject10).indexOf("FW");
          if (m != -1) {
            ((WireTransferBank)localObject5).setRoutingFedWire(((String)localObject10).substring(m + 2));
          }
        }
        localObject10 = ((TypeIntermediary_56D)localObject9).NameAddr.Line;
        if ((localObject10 != null) && (localObject10.length > 0)) {
          try
          {
            ((WireTransferBank)localObject5).setBankName(localObject10[0]);
            ((WireTransferBank)localObject5).setStreet(localObject10[1]);
            ((WireTransferBank)localObject5).setStreet2(localObject10[2]);
            ((WireTransferBank)localObject5).setStreet3(localObject10[3]);
          }
          catch (Exception localException2) {}
        }
      }
    }
    if (paramTypeTransactionDetailsSeq_101.DetailsPayment_70Exists)
    {
      localObject4 = paramTypeTransactionDetailsSeq_101.DetailsPayment_70.Narrative.Line;
      for (int j = 0; j < localObject4.length; j++)
      {
        localObject7 = localObject4[j];
        if (j == 0) {
          paramWireTransfer.setOrigToBeneficiaryInfo1((String)localObject7);
        } else if (j == 1) {
          paramWireTransfer.setOrigToBeneficiaryInfo2((String)localObject7);
        } else if (j == 2) {
          paramWireTransfer.setOrigToBeneficiaryInfo3((String)localObject7);
        } else if (j == 3) {
          paramWireTransfer.setOrigToBeneficiaryInfo4((String)localObject7);
        }
      }
    }
    if (paramTypeTransactionDetailsSeq_101.Amount_33BExists)
    {
      paramWireTransfer.setOrigAmount(paramTypeTransactionDetailsSeq_101.Amount_33B.Price);
      paramWireTransfer.setOrigCurrency(paramTypeTransactionDetailsSeq_101.Amount_33B.Currency);
    }
    Object localObject4 = paramTypeTransactionDetailsSeq_101.DetailsOfCharges_71A.Code;
    Object localObject6;
    if ((localObject4 != null) && (((String)localObject4).length() > 0))
    {
      localObject6 = new ValueInfo();
      ((ValueInfo)localObject6).setValue(localObject4);
      ((ValueInfo)localObject6).setAction("add");
      paramWireTransfer.put("DETAILS_OF_CHARGES", localObject6);
    }
    if ((paramTypeTransactionDetailsSeq_101.ChargesAccount_25AExists) && (paramTypeTransactionDetailsSeq_101.ChargesAccount_25A.AccountIdentificationExists))
    {
      localObject6 = paramTypeTransactionDetailsSeq_101.ChargesAccount_25A.AccountIdentification;
      if ((localObject6 != null) && (((String)localObject6).length() > 0))
      {
        localObject7 = new ValueInfo();
        ((ValueInfo)localObject7).setValue(localObject6);
        ((ValueInfo)localObject7).setAction("add");
        paramWireTransfer.put("CHARGES_ACCOUNT", localObject7);
      }
    }
    if (paramTypeTransactionDetailsSeq_101.FXDealRef_21FExists) {
      paramWireTransfer.setContractNumber(paramTypeTransactionDetailsSeq_101.FXDealRef_21F.FXDealRef);
    }
    if (paramTypeTransactionDetailsSeq_101.ExchangeRate_36Exists)
    {
      localObject6 = paramTypeTransactionDetailsSeq_101.ExchangeRate_36.Rate;
      if (localObject6 != null) {
        paramWireTransfer.setExchangeRate((String)localObject6);
      }
    }
  }
  
  private void jdMethod_for(TypeMT103 paramTypeMT103, WireTransfer paramWireTransfer)
    throws Exception
  {
    WireTransferPayee localWireTransferPayee = paramWireTransfer.getWirePayee();
    WireTransferBank localWireTransferBank = new WireTransferBank();
    localWireTransferPayee.setDestinationBank(localWireTransferBank);
    String str = paramTypeMT103.SendersRef_20.TransRefNum;
    paramWireTransfer.setComment(str);
    if (paramTypeMT103.TimeIndication_13CExists)
    {
      localObject1 = new StringBuffer();
      ((StringBuffer)localObject1).append(paramTypeMT103.TimeIndication_13C[0].Code);
      ((StringBuffer)localObject1).append(" ");
      ((StringBuffer)localObject1).append(paramTypeMT103.TimeIndication_13C[0].Time);
      ((StringBuffer)localObject1).append(" ");
      ((StringBuffer)localObject1).append(paramTypeMT103.TimeIndication_13C[0].Sign);
      ((StringBuffer)localObject1).append(" ");
      ((StringBuffer)localObject1).append(paramTypeMT103.TimeIndication_13C[0].TimeOffset);
      if (((StringBuffer)localObject1).length() > 0)
      {
        localObject2 = new ValueInfo();
        ((ValueInfo)localObject2).setValue(((StringBuffer)localObject1).toString());
        ((ValueInfo)localObject2).setAction("add");
        paramWireTransfer.put("TIME_INDICATION", localObject2);
      }
    }
    Object localObject1 = paramTypeMT103.BankOperationCode_23B.Type;
    if ((localObject1 != null) && (((String)localObject1).length() > 0))
    {
      localObject2 = new ValueInfo();
      ((ValueInfo)localObject2).setValue(localObject1);
      ((ValueInfo)localObject2).setAction("add");
      paramWireTransfer.put("BANK_OPERATION_CODE", localObject2);
    }
    if (paramTypeMT103.InstructionCode_23EExists)
    {
      localObject2 = paramTypeMT103.InstructionCode_23E;
      localObject3 = new StringBuffer();
      for (int i = 0; i < localObject2.length; i++)
      {
        ((StringBuffer)localObject3).append(localObject2[i].Type);
        if (localObject2[i].NarrativeExists)
        {
          ((StringBuffer)localObject3).append("/");
          ((StringBuffer)localObject3).append(localObject2[i].Narrative);
          ((StringBuffer)localObject3).append(" ");
        }
      }
      if (((StringBuffer)localObject3).length() > 0)
      {
        localObject4 = new ValueInfo();
        ((ValueInfo)localObject4).setValue(((StringBuffer)localObject3).toString().trim());
        ((ValueInfo)localObject4).setAction("add");
        paramWireTransfer.put("PAY_INSTRUCT", localObject4);
      }
    }
    if (paramTypeMT103.TransTypeCode_26TExists)
    {
      localObject2 = paramTypeMT103.TransTypeCode_26T.Type;
      if ((localObject2 != null) && (((String)localObject2).length() > 0))
      {
        localObject3 = new ValueInfo();
        ((ValueInfo)localObject3).setValue(localObject2);
        ((ValueInfo)localObject3).setAction("add");
        paramWireTransfer.put("TRANSACTION_TYPE_CODE", localObject3);
      }
    }
    if (paramTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A != null)
    {
      localObject2 = paramTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Date;
      localObject3 = paramTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Currency;
      localObject4 = paramTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Amount;
      paramWireTransfer.setDateFormat("yyMMdd");
      paramWireTransfer.setSettlementDate((String)localObject2);
      paramWireTransfer.setDueDate((String)localObject2);
      paramWireTransfer.setDateFormat("SHORT");
      paramWireTransfer.setAmount((String)localObject4);
      paramWireTransfer.setAmtCurrencyType((String)localObject3);
    }
    if (paramTypeMT103.CurrencyInstructedAmount_33BExists)
    {
      paramWireTransfer.setOrigAmount(paramTypeMT103.CurrencyInstructedAmount_33B.Price);
      paramWireTransfer.setOrigCurrency(paramTypeMT103.CurrencyInstructedAmount_33B.Currency);
    }
    if (paramTypeMT103.ExchangeRate_36Exists)
    {
      localObject2 = paramTypeMT103.ExchangeRate_36.Rate;
      if (localObject2 != null) {
        paramWireTransfer.setExchangeRate((String)localObject2);
      }
    }
    Object localObject2 = paramTypeMT103.OrderingCustomer_50AK;
    Object localObject3 = "";
    Object localObject4 = "";
    String[] arrayOfString1 = null;
    if (((TypeCreditor_50AK)localObject2).__preValueTag.equals("50A"))
    {
      localObject3 = ((TypeCreditor_50AK)localObject2).Creditor_50A.Account;
      localObject4 = ((TypeCreditor_50AK)localObject2).Creditor_50A.BIC;
      if (((TypeCreditor_50AK)localObject2).Creditor_50A.BICBranchCodeExists) {
        localObject4 = ((String)localObject4).concat(((TypeCreditor_50AK)localObject2).Creditor_50A.BICBranchCode);
      } else {
        localObject4 = ((String)localObject4).concat("XXX");
      }
    }
    else
    {
      localObject3 = ((TypeCreditor_50AK)localObject2).Creditor_50K.Account;
      arrayOfString1 = ((TypeCreditor_50AK)localObject2).Creditor_50K.NameAddr.Line;
    }
    if (localObject3 != null) {
      paramWireTransfer.setFromAccountNum((String)localObject3);
    }
    if (paramTypeMT103.SendingInsti_51AExists)
    {
      if (paramTypeMT103.SendingInsti_51A.PartyExists)
      {
        localObject5 = paramTypeMT103.SendingInsti_51A.Party.PartyIdentifier;
        if ((localObject5 != null) && (((String)localObject5).length() > 0))
        {
          localObject6 = new ValueInfo();
          ((ValueInfo)localObject6).setValue(localObject5);
          ((ValueInfo)localObject6).setAction("add");
          paramWireTransfer.put("SEND_INSTITUTION_NAME", localObject6);
        }
      }
      localObject5 = paramTypeMT103.SendingInsti_51A.BIC;
      if (paramTypeMT103.SendingInsti_51A.BICBranchCodeExists) {
        localObject5 = ((String)localObject5).concat(paramTypeMT103.SendingInsti_51A.BICBranchCode);
      } else {
        localObject5 = ((String)localObject5).concat("XXX");
      }
      if ((localObject5 != null) && (((String)localObject5).length() > 0))
      {
        localObject6 = new ValueInfo();
        ((ValueInfo)localObject6).setValue(localObject5);
        ((ValueInfo)localObject6).setAction("add");
        paramWireTransfer.put("SEND_INSTITUTION_BICCODE", localObject6);
      }
    }
    Object localObject5 = paramTypeMT103.BeneficiaryCustomer_59AN;
    if (((TypeDebtor_59AN)localObject5).__preValueTag.equals("59A"))
    {
      DebugLog.log("WARNING: Tag 59A is not supported. Benficiary Account/Address is missing!");
    }
    else
    {
      if (((TypeDebtor_59AN)localObject5).Debtor_59.AccountExists) {
        localWireTransferPayee.setAccountNum(((TypeDebtor_59AN)localObject5).Debtor_59.Account);
      }
      localObject6 = ((TypeDebtor_59AN)localObject5).Debtor_59.NameAddr.Line;
      if ((localObject6 != null) && (localObject6.length > 0)) {
        jdMethod_for(localWireTransferPayee, (String[])localObject6);
      }
    }
    Object localObject7;
    Object localObject9;
    Object localObject12;
    if (paramTypeMT103.AcctWithInsti_57ABCDExists)
    {
      localObject6 = paramTypeMT103.AcctWithInsti_57ABCD;
      if (((TypeAcctWithInsti_57ABCD)localObject6).__preValueTag.equals("57A"))
      {
        paramWireTransfer.setWireDestination("INTERNATIONAL");
        localObject7 = ((TypeAcctWithInsti_57ABCD)localObject6).AcctWithInsti_57A;
        if ((((TypeAcctWithInsti_57A)localObject7).PartyExists) && (((TypeAcctWithInsti_57A)localObject7).Party.PartyIdentifierExists))
        {
          localObject9 = ((TypeAcctWithInsti_57A)localObject7).Party.PartyIdentifier;
          if ((localObject9 != null) && (((String)localObject9).length() > 0))
          {
            ValueInfo localValueInfo1 = new ValueInfo();
            localValueInfo1.setValue(localObject9);
            localValueInfo1.setAction("add");
            paramWireTransfer.put("PARTY_IDENTIFIER_57", localValueInfo1);
          }
        }
        localObject9 = new StringBuffer(((TypeAcctWithInsti_57A)localObject7).BIC);
        if (((TypeAcctWithInsti_57A)localObject7).BICBranchCodeExists) {
          ((StringBuffer)localObject9).append(((TypeAcctWithInsti_57A)localObject7).BICBranchCode);
        } else {
          ((StringBuffer)localObject9).append("XXX");
        }
        localWireTransferBank.setRoutingSwift(((StringBuffer)localObject9).toString());
      }
      else if (((TypeAcctWithInsti_57ABCD)localObject6).__preValueTag.equals("57D"))
      {
        localObject7 = ((TypeAcctWithInsti_57ABCD)localObject6).AcctWithInsti_57D;
        if ((((TypeAcctWithInsti_57D)localObject7).PartyExists) && (((TypeAcctWithInsti_57D)localObject7).Party.PartyIdentifierExists))
        {
          localObject9 = ((TypeAcctWithInsti_57D)localObject7).Party.PartyIdentifier;
          int k = ((String)localObject9).indexOf("FW");
          if (k != -1)
          {
            localObject12 = ((String)localObject9).substring(k + 2);
            localWireTransferBank.setRoutingFedWire((String)localObject12);
            if (ai((String)localObject12) == true)
            {
              paramWireTransfer.setWireDestination("BOOKTRANSFER");
              localWireTransferPayee.setPayeeDestination("BOOK");
            }
          }
          else
          {
            DebugLog.log("WARNING: 57D is not allowed without //FW prefix!");
          }
        }
        localObject9 = ((TypeAcctWithInsti_57D)localObject7).NameAddr.Line;
        if ((localObject9 != null) && (localObject9.length > 0)) {
          try
          {
            localWireTransferBank.setBankName(localObject9[0]);
            localWireTransferBank.setStreet(localObject9[1]);
            localWireTransferBank.setStreet2(localObject9[2]);
            localWireTransferBank.setStreet3(localObject9[3]);
          }
          catch (Exception localException1) {}
        }
      }
    }
    if ((!paramWireTransfer.getWireDestination().equals("BOOKTRANSFER")) && (paramTypeMT103.Intermediary_56ACDExists))
    {
      localObject6 = new WireTransferBanks();
      localWireTransferPayee.setIntermediaryBanks((WireTransferBanks)localObject6);
      localObject7 = ((WireTransferBanks)localObject6).create();
      localObject9 = paramTypeMT103.Intermediary_56ACD;
      Object localObject10;
      if (((TypeIntermediary_56ACD)localObject9).__preValueTag.equals("56A"))
      {
        localObject10 = ((TypeIntermediary_56ACD)localObject9).Intermediary_56A;
        if ((((TypeIntermediary_56A)localObject10).PartyExists) && (((TypeIntermediary_56A)localObject10).Party.PartyIdentifierExists))
        {
          localObject12 = ((TypeIntermediary_56A)localObject10).Party.PartyIdentifier;
          if ((localObject12 != null) && (((String)localObject12).length() > 0))
          {
            ValueInfo localValueInfo2 = new ValueInfo();
            localValueInfo2.setValue(localObject12);
            localValueInfo2.setAction("add");
            paramWireTransfer.put("PARTY_IDENTIFIER_56", localValueInfo2);
          }
        }
        localObject12 = new StringBuffer(((TypeIntermediary_56A)localObject10).BIC);
        if (((TypeIntermediary_56A)localObject10).BICBranchCodeExists) {
          ((StringBuffer)localObject12).append(((TypeIntermediary_56A)localObject10).BICBranchCode);
        } else {
          ((StringBuffer)localObject12).append("XXX");
        }
        ((WireTransferBank)localObject7).setRoutingSwift(((StringBuffer)localObject12).toString());
      }
      else if (((TypeIntermediary_56ACD)localObject9).__preValueTag.equals("56D"))
      {
        localObject10 = ((TypeIntermediary_56ACD)localObject9).Intermediary_56D;
        if ((((TypeIntermediary_56D)localObject10).PartyExists) && (((TypeIntermediary_56D)localObject10).Party.PartyIdentifierExists))
        {
          localObject12 = ((TypeIntermediary_56D)localObject10).Party.PartyIdentifier;
          int i1 = ((String)localObject12).indexOf("FW");
          if (i1 != -1) {
            ((WireTransferBank)localObject7).setRoutingFedWire(((String)localObject12).substring(i1 + 2));
          }
        }
        localObject12 = ((TypeIntermediary_56D)localObject10).NameAddr.Line;
        if ((localObject12 != null) && (localObject12.length > 0)) {
          try
          {
            ((WireTransferBank)localObject7).setBankName(localObject12[0]);
            ((WireTransferBank)localObject7).setStreet(localObject12[1]);
            ((WireTransferBank)localObject7).setStreet2(localObject12[2]);
            ((WireTransferBank)localObject7).setStreet3(localObject12[3]);
          }
          catch (Exception localException2) {}
        }
      }
    }
    if (paramTypeMT103.RemittanceInformation_70Exists)
    {
      localObject6 = paramTypeMT103.RemittanceInformation_70.Narrative.Line;
      for (int j = 0; j < localObject6.length; j++)
      {
        localObject9 = localObject6[j];
        if (j == 0) {
          paramWireTransfer.setOrigToBeneficiaryInfo1((String)localObject9);
        } else if (j == 1) {
          paramWireTransfer.setOrigToBeneficiaryInfo2((String)localObject9);
        } else if (j == 2) {
          paramWireTransfer.setOrigToBeneficiaryInfo3((String)localObject9);
        } else if (j == 3) {
          paramWireTransfer.setOrigToBeneficiaryInfo4((String)localObject9);
        }
      }
    }
    Object localObject6 = paramTypeMT103.DetailsOfCharges_71A.Code;
    Object localObject8;
    if ((localObject6 != null) && (((String)localObject6).length() > 0))
    {
      localObject8 = new ValueInfo();
      ((ValueInfo)localObject8).setValue(localObject6);
      ((ValueInfo)localObject8).setAction("add");
      paramWireTransfer.put("DETAILS_OF_CHARGES", localObject8);
    }
    Object localObject11;
    if (paramTypeMT103.SendersCharges_71FExists)
    {
      localObject8 = new StringBuffer();
      localObject9 = paramTypeMT103.SendersCharges_71F;
      if (localObject9 != null) {
        for (int m = 0; m < localObject9.length; m++) {
          if (localObject9[m] != null)
          {
            ((StringBuffer)localObject8).append(localObject9[m].Currency);
            ((StringBuffer)localObject8).append(localObject9[m].Amount);
            ((StringBuffer)localObject8).append(" ");
          }
        }
      }
      if (((StringBuffer)localObject8).length() > 0)
      {
        localObject11 = new ValueInfo();
        ((ValueInfo)localObject11).setValue(((StringBuffer)localObject8).toString().trim());
        ((ValueInfo)localObject11).setAction("add");
        paramWireTransfer.put("SENDERS_CHARGES", localObject11);
      }
    }
    if (paramTypeMT103.ReceiversCharges_71GExists)
    {
      localObject8 = new StringBuffer();
      ((StringBuffer)localObject8).append(paramTypeMT103.ReceiversCharges_71G.Currency);
      ((StringBuffer)localObject8).append(paramTypeMT103.ReceiversCharges_71G.Amount);
      if (((StringBuffer)localObject8).length() > 0)
      {
        localObject9 = new ValueInfo();
        ((ValueInfo)localObject9).setValue(((StringBuffer)localObject8).toString());
        ((ValueInfo)localObject9).setAction("add");
        paramWireTransfer.put("RECEIVERS_CHARGES", localObject9);
      }
    }
    if (paramTypeMT103.SenderToReceiverInfo_72Exists)
    {
      localObject8 = paramTypeMT103.SenderToReceiverInfo_72;
      if (((TypeSenderToReceiverInfo_72)localObject8).SenderToReceiverInfoF1_72 != null)
      {
        localObject9 = new StringBuffer();
        localObject11 = ((TypeSenderToReceiverInfo_72)localObject8).SenderToReceiverInfoF1_72.Code;
        if (localObject11 != null) {
          ((StringBuffer)localObject9).append((String)localObject11);
        }
        if (((TypeSenderToReceiverInfo_72)localObject8).SenderToReceiverInfoF1_72.AdditionalInformationExists)
        {
          localObject12 = ((TypeSenderToReceiverInfo_72)localObject8).SenderToReceiverInfoF1_72.AdditionalInformation;
          ((StringBuffer)localObject9).append("/");
          ((StringBuffer)localObject9).append((String)localObject12);
        }
        paramWireTransfer.setBankToBankInfo1(((StringBuffer)localObject9).toString());
        localObject12 = new String[5];
        int i2 = 0;
        TypeAdditionalLines[] arrayOfTypeAdditionalLines = ((TypeSenderToReceiverInfo_72)localObject8).SenderToReceiverInfoF1_72.AdditionalLines;
        if ((arrayOfTypeAdditionalLines != null) && (arrayOfTypeAdditionalLines.length > 0)) {
          for (int i3 = 0; i3 < arrayOfTypeAdditionalLines.length; i3++) {
            if (i2 < 5) {
              if (arrayOfTypeAdditionalLines[i3].Line != null)
              {
                localObject12[(i2++)] = arrayOfTypeAdditionalLines[i3].Line;
              }
              else
              {
                StringBuffer localStringBuffer = new StringBuffer(arrayOfTypeAdditionalLines[i3].Line2.Code);
                if (arrayOfTypeAdditionalLines[i3].Line2.NarrativeExists)
                {
                  localStringBuffer.append("/");
                  localStringBuffer.append(arrayOfTypeAdditionalLines[i3].Line2.Narrative);
                }
                localObject12[(i2++)] = localStringBuffer.toString();
              }
            }
          }
        }
        String[] arrayOfString3 = ((TypeSenderToReceiverInfo_72)localObject8).SenderToReceiverInfoF1_72.NarrativeText.Line;
        if ((arrayOfString3 != null) && (arrayOfString3.length > 0)) {
          for (i4 = 0; i4 < arrayOfString3.length; i4++) {
            if (i2 < 5) {
              localObject12[(i2++)] = arrayOfString3[i4];
            }
          }
        }
        for (int i4 = 0; i4 < localObject12.length; i4++) {
          if (i4 == 0) {
            paramWireTransfer.setBankToBankInfo2(localObject12[i4]);
          } else if (i4 == 1) {
            paramWireTransfer.setBankToBankInfo3(localObject12[i4]);
          } else if (i4 == 2) {
            paramWireTransfer.setBankToBankInfo4(localObject12[i4]);
          } else if (i4 == 3) {
            paramWireTransfer.setBankToBankInfo5(localObject12[i4]);
          } else if (i4 == 4) {
            paramWireTransfer.setBankToBankInfo6(localObject12[i4]);
          }
        }
      }
      else if (((TypeSenderToReceiverInfo_72)localObject8).SenderToReceiverInfoF2_72 != null)
      {
        localObject9 = ((TypeSenderToReceiverInfo_72)localObject8).SenderToReceiverInfoF2_72.Narrative.Line;
        for (int n = 0; n < localObject9.length; n++) {
          if (n == 0) {
            paramWireTransfer.setBankToBankInfo1(localObject9[n]);
          } else if (n == 1) {
            paramWireTransfer.setBankToBankInfo2(localObject9[n]);
          } else if (n == 2) {
            paramWireTransfer.setBankToBankInfo3(localObject9[n]);
          } else if (n == 3) {
            paramWireTransfer.setBankToBankInfo4(localObject9[n]);
          } else if (n == 4) {
            paramWireTransfer.setBankToBankInfo5(localObject9[n]);
          } else if (n == 5) {
            paramWireTransfer.setBankToBankInfo6(localObject9[n]);
          }
        }
      }
    }
    if (paramTypeMT103.OrderingInstitution_52ADExists)
    {
      localObject8 = paramTypeMT103.OrderingInstitution_52AD;
      if (((TypeOrderingInstitution_52AD)localObject8).OrderingInstitution_52D != null)
      {
        localObject9 = "";
        if ((((TypeOrderingInstitution_52AD)localObject8).OrderingInstitution_52D.PartyExists) && (((TypeOrderingInstitution_52AD)localObject8).OrderingInstitution_52D.Party.PartyIdentifierExists)) {
          localObject9 = ((TypeOrderingInstitution_52AD)localObject8).OrderingInstitution_52D.Party.PartyIdentifier;
        }
        paramWireTransfer.setByOrderOfAccount((String)localObject9);
        String[] arrayOfString2 = ((TypeOrderingInstitution_52AD)localObject8).OrderingInstitution_52D.NameAddr.Line;
        if (arrayOfString2.length > 0) {
          paramWireTransfer.setByOrderOfName(arrayOfString2[0]);
        }
        if (arrayOfString2.length > 1) {
          paramWireTransfer.setByOrderOfAddress1(arrayOfString2[1]);
        }
        if (arrayOfString2.length > 2) {
          paramWireTransfer.setByOrderOfAddress2(arrayOfString2[2]);
        }
        if (arrayOfString2.length > 3) {
          paramWireTransfer.setByOrderOfAddress3(arrayOfString2[3]);
        }
      }
    }
    if (paramTypeMT103.EnvelopeContents_77TExists)
    {
      localObject8 = paramTypeMT103.EnvelopeContents_77T.Code;
      if ((localObject8 != null) && (((String)localObject8).length() != 0))
      {
        localObject9 = new ValueInfo();
        ((ValueInfo)localObject9).setValue(localObject8);
        ((ValueInfo)localObject9).setAction("add");
        paramWireTransfer.put("ENVELOPE_CONTENTS", localObject9);
      }
    }
  }
  
  private static String aj(String paramString)
  {
    int i = paramString.indexOf("{");
    if (i != -1) {
      paramString = paramString.substring(i);
    }
    return paramString.trim();
  }
  
  private void jdMethod_for(WireTransferPayee paramWireTransferPayee, String[] paramArrayOfString)
  {
    try
    {
      paramWireTransferPayee.setName(paramArrayOfString[0]);
      paramWireTransferPayee.setStreet(paramArrayOfString[1]);
      paramWireTransferPayee.setCountry(paramArrayOfString[3]);
      if (paramWireTransferPayee.getCountry().equals("USA")) {
        paramWireTransferPayee.setCountry("UNITED STATES");
      }
      String str = paramArrayOfString[2];
      StringTokenizer localStringTokenizer = new StringTokenizer(str, ",");
      paramWireTransferPayee.setCity(localStringTokenizer.nextToken());
      str = localStringTokenizer.nextToken();
      localStringTokenizer = new StringTokenizer(str.trim(), " ");
      paramWireTransferPayee.setState(localStringTokenizer.nextToken());
      paramWireTransferPayee.setZipCode(localStringTokenizer.nextToken());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  private boolean ai(String paramString)
  {
    boolean bool = false;
    if (this.aRJ == null) {
      try
      {
        WireTransferBanks localWireTransferBanks = Wire.getBPWFIs();
        if (localWireTransferBanks != null)
        {
          this.aRJ = new HashMap();
          for (int i = 0; i < localWireTransferBanks.size(); i++)
          {
            WireTransferBank localWireTransferBank = (WireTransferBank)localWireTransferBanks.get(i);
            this.aRJ.put(localWireTransferBank.getRoutingFedWire(), localWireTransferBank.getID());
          }
        }
      }
      catch (CSILException localCSILException)
      {
        DebugLog.log("ERROR: Could not retrieve BPW FIInfos.");
      }
      catch (Exception localException1)
      {
        DebugLog.log("ERROR: Could not retrieve BPW FIInfos.");
      }
    } else {
      try
      {
        if (this.aRJ.get(paramString) != null) {
          bool = true;
        }
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.WireImportAdapter
 * JD-Core Version:    0.7.0.1
 */