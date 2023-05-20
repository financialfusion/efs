package com.ffusion.csil.core;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.beans.register.RegisterCategories;
import com.ffusion.beans.register.RegisterCategory;
import com.ffusion.beans.register.RegisterPayee;
import com.ffusion.beans.register.RegisterPayees;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.beans.register.ServerTransaction;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class Register
  extends Initialize
{
  private static Entitlement aBA = new Entitlement("Account Register", null, null);
  private static final String aBB = "com.ffusion.util.logging.audit.register";
  private static final String aBG = "AuditMessage_1.1";
  private static final String aBE = "AuditMessage_1.2";
  private static final String aBL = "AuditMessage_1.3";
  private static final String aBP = "AuditMessage_1.4";
  private static final String aBO = "AuditMessage_2.1";
  private static final String aBD = "AuditMessage_2.2";
  private static final String aBK = "AuditMessage_2.3";
  private static final String aBx = "AuditMessage_2.4";
  private static final String aBt = "AuditMessage_3.1";
  private static final String aBu = "AuditMessage_3.2";
  private static final String aBC = "AuditMessage_3.3";
  private static final String aBv = "AuditMessage_3.4";
  private static final String aBM = "AuditMessage_4";
  private static final String aBF = "AuditMessage_5";
  private static final String aBI = "AuditMessage_6";
  private static final String aBy = "AuditMessage_7";
  private static final String aBz = "AuditMessage_8";
  private static final String aBJ = "AuditMessage_9";
  private static final String aBs = "AuditMessage_10";
  private static final String aBN = "AuditMessage_11";
  private static final String aBw = "AuditMessage_12";
  private static final String aBH = "AuditEntFault_1";
  
  public static void initialize(HashMap paramHashMap)
  {
    debug("com.ffusion.csil.core.Register.initialize");
    com.ffusion.csil.handlers.Register.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return com.ffusion.csil.handlers.Register.getService();
  }
  
  public static RegisterTransaction addRegisterTransaction(SecureUser paramSecureUser, RegisterTransaction paramRegisterTransaction, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Register.AddRegisterTransaction";
    if (!g(paramSecureUser)) {
      throw new CSILException(str1, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    RegisterTransaction localRegisterTransaction = com.ffusion.csil.handlers.Register.addRegisterTransaction(paramSecureUser, paramRegisterTransaction, paramHashMap);
    PerfLog.log(str1, l, true);
    String str2 = TrackingIDGenerator.GetNextID();
    LocalizableString localLocalizableString;
    if ((paramRegisterTransaction.getDateIssued() == null) && (paramRegisterTransaction.getType() == null))
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_1.1", null);
    }
    else
    {
      Object[] arrayOfObject;
      if ((paramRegisterTransaction.getDateIssued() != null) && (paramRegisterTransaction.getType() == null))
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = paramRegisterTransaction.getDateIssued();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_1.2", arrayOfObject);
      }
      else if ((paramRegisterTransaction.getDateIssued() == null) && (paramRegisterTransaction.getType() != null))
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = paramRegisterTransaction.getType();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_1.3", arrayOfObject);
      }
      else
      {
        arrayOfObject = new Object[2];
        arrayOfObject[0] = paramRegisterTransaction.getDateIssued();
        arrayOfObject[1] = paramRegisterTransaction.getType();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_1.4", arrayOfObject);
      }
    }
    audit(paramSecureUser, localLocalizableString, str2, 3000, paramRegisterTransaction.getAmountValue());
    debug(paramSecureUser, str1);
    return localRegisterTransaction;
  }
  
  public static RegisterTransactions addRegisterTransactions(SecureUser paramSecureUser, RegisterTransactions paramRegisterTransactions, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.AddRegisterTransactions";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    RegisterTransactions localRegisterTransactions = com.ffusion.csil.handlers.Register.addRegisterTransactions(paramSecureUser, paramRegisterTransactions, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localRegisterTransactions;
  }
  
  public static RegisterTransactions addUpdateRegisterTransactions(SecureUser paramSecureUser, RegisterTransactions paramRegisterTransactions, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.addUpdateRegisterTransactions";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    RegisterTransactions localRegisterTransactions = com.ffusion.csil.handlers.Register.addUpdateRegisterTransactions(paramSecureUser, paramRegisterTransactions, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localRegisterTransactions;
  }
  
  public static RegisterTransactions addBankTransactions(SecureUser paramSecureUser, RegisterTransactions paramRegisterTransactions, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.AddBankTransactions";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    RegisterTransactions localRegisterTransactions = com.ffusion.csil.handlers.Register.addBankTransactions(paramSecureUser, paramRegisterTransactions, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localRegisterTransactions;
  }
  
  public static void deleteOldUnreconciledTransactions(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.deleteOldUnreconciledTransactions";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.Register.deleteOldUnreconciledTransactions(paramSecureUser, paramInt, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str + paramInt);
  }
  
  public static RegisterTransaction deleteRegisterTransaction(SecureUser paramSecureUser, RegisterTransaction paramRegisterTransaction, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Register.DeleteRegisterTransaction";
    if (!g(paramSecureUser)) {
      throw new CSILException(str1, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    RegisterTransaction localRegisterTransaction = com.ffusion.csil.handlers.Register.deleteRegisterTransaction(paramSecureUser, paramRegisterTransaction, paramHashMap);
    PerfLog.log(str1, l, true);
    String str2 = TrackingIDGenerator.GetNextID();
    LocalizableString localLocalizableString;
    if ((paramRegisterTransaction.getDateIssued() == null) && (paramRegisterTransaction.getType() == null))
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_2.1", null);
    }
    else
    {
      Object[] arrayOfObject;
      if ((paramRegisterTransaction.getDateIssued() != null) && (paramRegisterTransaction.getType() == null))
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = paramRegisterTransaction.getDateIssued();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_2.2", arrayOfObject);
      }
      else if ((paramRegisterTransaction.getDateIssued() == null) && (paramRegisterTransaction.getType() != null))
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = paramRegisterTransaction.getType();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_2.3", arrayOfObject);
      }
      else
      {
        arrayOfObject = new Object[2];
        arrayOfObject[0] = paramRegisterTransaction.getDateIssued();
        arrayOfObject[1] = paramRegisterTransaction.getType();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_2.4", arrayOfObject);
      }
    }
    audit(paramSecureUser, localLocalizableString, str2, 3002, paramRegisterTransaction.getAmountValue());
    debug(paramSecureUser, str1);
    return localRegisterTransaction;
  }
  
  public static RegisterTransactions deleteRegisterTransactions(SecureUser paramSecureUser, RegisterTransactions paramRegisterTransactions, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.DeleteRegisterTransactions";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    RegisterTransactions localRegisterTransactions = com.ffusion.csil.handlers.Register.deleteRegisterTransactions(paramSecureUser, paramRegisterTransactions, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localRegisterTransactions;
  }
  
  public static void deleteRegisterTransactionsByServerTID(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.deleteRegisterTransactionsByTranIds";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.Register.deleteRegisterTransactionsByServerTID(paramSecureUser, paramArrayList, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
  
  public static RegisterTransaction getRegisterTransaction(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.GetRegisterTransaction";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    RegisterTransaction localRegisterTransaction = com.ffusion.csil.handlers.Register.getRegisterTransaction(paramSecureUser, paramString1, paramString2, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localRegisterTransaction;
  }
  
  public static RegisterTransactions getRegisterTransactions(SecureUser paramSecureUser, DateTime paramDateTime1, DateTime paramDateTime2, String paramString1, String paramString2, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.GetRegisterTransactions";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    RegisterTransactions localRegisterTransactions = com.ffusion.csil.handlers.Register.getRegisterTransactions(paramSecureUser, paramDateTime1, paramDateTime2, paramString1, paramString2, paramBoolean, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localRegisterTransactions;
  }
  
  public static RegisterTransaction modifyRegisterTransaction(SecureUser paramSecureUser, RegisterTransaction paramRegisterTransaction, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Register.ModifyRegisterTransaction";
    if (!g(paramSecureUser)) {
      throw new CSILException(str1, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    RegisterTransaction localRegisterTransaction = com.ffusion.csil.handlers.Register.modifyRegisterTransaction(paramSecureUser, paramRegisterTransaction, paramHashMap);
    PerfLog.log(str1, l, true);
    String str2 = TrackingIDGenerator.GetNextID();
    LocalizableString localLocalizableString;
    if ((paramRegisterTransaction.getDateIssued() == null) && (paramRegisterTransaction.getType() == null))
    {
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_3.1", null);
    }
    else
    {
      Object[] arrayOfObject;
      if ((paramRegisterTransaction.getDateIssued() != null) && (paramRegisterTransaction.getType() == null))
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = paramRegisterTransaction.getDateIssued();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_3.2", arrayOfObject);
      }
      else if ((paramRegisterTransaction.getDateIssued() == null) && (paramRegisterTransaction.getType() != null))
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = paramRegisterTransaction.getType();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_3.3", arrayOfObject);
      }
      else
      {
        arrayOfObject = new Object[2];
        arrayOfObject[0] = paramRegisterTransaction.getDateIssued();
        arrayOfObject[1] = paramRegisterTransaction.getType();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_3.4", arrayOfObject);
      }
    }
    audit(paramSecureUser, localLocalizableString, str2, 3003, paramRegisterTransaction.getAmountValue());
    debug(paramSecureUser, str1);
    return localRegisterTransaction;
  }
  
  public static RegisterTransactions modifyRegisterTransactions(SecureUser paramSecureUser, RegisterTransactions paramRegisterTransactions, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.ModifyRegisterTransactions";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    RegisterTransactions localRegisterTransactions = com.ffusion.csil.handlers.Register.modifyRegisterTransactions(paramSecureUser, paramRegisterTransactions, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localRegisterTransactions;
  }
  
  public static RegisterCategory addRegisterCategory(SecureUser paramSecureUser, RegisterCategory paramRegisterCategory, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Register.AddRegisterCategory";
    if (!g(paramSecureUser)) {
      throw new CSILException(str1, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    RegisterCategory localRegisterCategory = com.ffusion.csil.handlers.Register.addRegisterCategory(paramSecureUser, paramRegisterCategory, paramHashMap);
    PerfLog.log(str1, l, true);
    String str2 = TrackingIDGenerator.GetNextID();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramRegisterCategory.getName();
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_4", arrayOfObject);
    audit(paramSecureUser, localLocalizableString, str2, 3004);
    debug(paramSecureUser, str1);
    return localRegisterCategory;
  }
  
  public static ArrayList addSrvrTranRegisterCategory(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.addSrvrTranRegisterCategory";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    ArrayList localArrayList = com.ffusion.csil.handlers.Register.addSrvrTranRegisterCategory(paramSecureUser, paramArrayList, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localArrayList;
  }
  
  public static ServerTransaction modifySrvrTranRegisterCategory(SecureUser paramSecureUser, ServerTransaction paramServerTransaction, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.modifySrvrTranRegisterCategory";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    ServerTransaction localServerTransaction = com.ffusion.csil.handlers.Register.modifySrvrTranRegisterCategory(paramSecureUser, paramServerTransaction, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localServerTransaction;
  }
  
  public static void deleteSrvrTranRegisterCategory(SecureUser paramSecureUser, String paramString, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.deleteSrvrTranRegisterCategory";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.Register.deleteSrvrTranRegisterCategory(paramSecureUser, paramString, paramBoolean, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
  
  public static RegisterCategory deleteRegisterCategory(SecureUser paramSecureUser, RegisterCategory paramRegisterCategory, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Register.DeleteRegisterCategory";
    if (!g(paramSecureUser)) {
      throw new CSILException(str1, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    RegisterCategory localRegisterCategory = com.ffusion.csil.handlers.Register.deleteRegisterCategory(paramSecureUser, paramRegisterCategory, paramHashMap);
    PerfLog.log(str1, l, true);
    String str2 = TrackingIDGenerator.GetNextID();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramRegisterCategory.getName();
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_5", arrayOfObject);
    audit(paramSecureUser, localLocalizableString, str2, 3005);
    debug(paramSecureUser, str1);
    return localRegisterCategory;
  }
  
  public static RegisterCategories getRegisterCategories(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.GetRegisterCategories";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    RegisterCategories localRegisterCategories = com.ffusion.csil.handlers.Register.getRegisterCategories(paramSecureUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localRegisterCategories;
  }
  
  public static RegisterCategories getRegisterDefaultCategories(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.GetRegisterDefaultCategories";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    RegisterCategories localRegisterCategories = com.ffusion.csil.handlers.Register.getRegisterDefaultCategories(paramSecureUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localRegisterCategories;
  }
  
  public static Payments getRegisterCategoriesForPayments(SecureUser paramSecureUser, Payments paramPayments, RecPayments paramRecPayments, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.getRegisterCategoriesForPayments";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    Payments localPayments = com.ffusion.csil.handlers.Register.getRegisterCategoriesForPayments(paramSecureUser, paramPayments, paramRecPayments, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localPayments;
  }
  
  public static Transfers getRegisterCategoriesForTransfers(SecureUser paramSecureUser, Transfers paramTransfers, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.getRegisterCategoriesForTransfers";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    Transfers localTransfers = com.ffusion.csil.handlers.Register.getRegisterCategoriesForTransfers(paramSecureUser, paramTransfers, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localTransfers;
  }
  
  public static RegisterCategory modifyRegisterCategory(SecureUser paramSecureUser, RegisterCategory paramRegisterCategory, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Register.ModifyRegisterCategory";
    if (!g(paramSecureUser)) {
      throw new CSILException(str1, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    RegisterCategory localRegisterCategory = com.ffusion.csil.handlers.Register.modifyRegisterCategory(paramSecureUser, paramRegisterCategory, paramHashMap);
    PerfLog.log(str1, l, true);
    String str2 = TrackingIDGenerator.GetNextID();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramRegisterCategory.getName();
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_6", arrayOfObject);
    audit(paramSecureUser, localLocalizableString, str2, 3006);
    debug(paramSecureUser, str1);
    return localRegisterCategory;
  }
  
  public static RegisterPayee addRegisterPayee(SecureUser paramSecureUser, RegisterPayee paramRegisterPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Register.AddRegisterPayee";
    if (!g(paramSecureUser)) {
      throw new CSILException(str1, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    paramRegisterPayee = com.ffusion.csil.handlers.Register.addRegisterPayee(paramSecureUser, paramRegisterPayee, paramHashMap);
    PerfLog.log(str1, l, true);
    String str2 = TrackingIDGenerator.GetNextID();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramRegisterPayee.getName();
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_7", arrayOfObject);
    audit(paramSecureUser, localLocalizableString, str2, 3007);
    debug(paramSecureUser, str1);
    return paramRegisterPayee;
  }
  
  public static RegisterPayee deleteRegisterPayee(SecureUser paramSecureUser, RegisterPayee paramRegisterPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Register.DeleteRegisterPayee";
    if (!g(paramSecureUser)) {
      throw new CSILException(str1, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    RegisterPayee localRegisterPayee = com.ffusion.csil.handlers.Register.deleteRegisterPayee(paramSecureUser, paramRegisterPayee, paramHashMap);
    PerfLog.log(str1, l, true);
    String str2 = TrackingIDGenerator.GetNextID();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramRegisterPayee.getName();
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_8", arrayOfObject);
    audit(paramSecureUser, localLocalizableString, str2, 3008);
    debug(paramSecureUser, str1);
    return localRegisterPayee;
  }
  
  public static RegisterPayees getRegisterPayees(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.GetRegisterPayees";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    RegisterPayees localRegisterPayees = com.ffusion.csil.handlers.Register.getRegisterPayees(paramSecureUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localRegisterPayees;
  }
  
  public static RegisterPayee modifyRegisterPayee(SecureUser paramSecureUser, RegisterPayee paramRegisterPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Register.ModifyRegisterPayee";
    if (!g(paramSecureUser)) {
      throw new CSILException(str1, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    RegisterPayee localRegisterPayee = com.ffusion.csil.handlers.Register.modifyRegisterPayee(paramSecureUser, paramRegisterPayee, paramHashMap);
    PerfLog.log(str1, l, true);
    String str2 = TrackingIDGenerator.GetNextID();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramRegisterPayee.getName();
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_9", arrayOfObject);
    audit(paramSecureUser, localLocalizableString, str2, 3009);
    debug(paramSecureUser, str1);
    return localRegisterPayee;
  }
  
  public static void transferDefaultCategories(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.TransferDefaultCategories";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.Register.transferDefaultCategories(paramSecureUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
  
  public static void reassignTransactionsCategory(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.ReassignTransactionsCategory";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.Register.reassignTransactionsCategory(paramSecureUser, paramString1, paramString2, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
  
  public static void reconcileRegisterTransactions(SecureUser paramSecureUser, RegisterTransactions paramRegisterTransactions1, RegisterTransactions paramRegisterTransactions2, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Register.reconcileRegisterTransactions";
    if (!g(paramSecureUser)) {
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.Register.reconcileRegisterTransactions(paramSecureUser, paramRegisterTransactions1, paramRegisterTransactions2, paramHashMap);
    PerfLog.log(str1, l, true);
    String str2 = TrackingIDGenerator.GetNextID();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    int j = 0;
    Iterator localIterator = paramRegisterTransactions1.iterator();
    while (localIterator.hasNext())
    {
      localObject = (RegisterTransaction)localIterator.next();
      if (((RegisterTransaction)localObject).getStatusValue() == 2) {
        i++;
      } else if (((RegisterTransaction)localObject).getStatusValue() == 3) {
        j++;
      }
    }
    Object localObject = new Object[2];
    localObject[0] = Integer.toString(j);
    localObject[1] = Integer.toString(i);
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_10", (Object[])localObject);
    audit(paramSecureUser, localLocalizableString, str2, 3020);
    debug(paramSecureUser, str1);
  }
  
  public static Account modifyRegisterAccountData(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Register.ModifyRegisterAccountData";
    if (!g(paramSecureUser)) {
      throw new CSILException(str1, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    Account localAccount = com.ffusion.csil.handlers.Register.modifyRegisterAccountData(paramSecureUser, paramAccount, paramHashMap);
    PerfLog.log(str1, l, true);
    if ((paramHashMap != null) && (paramHashMap.get("ModifyRegisterEnabledStatus") != null))
    {
      String str2 = TrackingIDGenerator.GetNextID();
      boolean bool = Boolean.valueOf((String)paramAccount.get("REG_ENABLED")).booleanValue();
      StringBuffer localStringBuffer = new StringBuffer();
      Object[] arrayOfObject;
      LocalizableString localLocalizableString;
      if (bool)
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = paramAccount.buildLocalizableAccountID();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_11", arrayOfObject);
        audit(paramSecureUser, localLocalizableString, str2, 3010);
      }
      else
      {
        arrayOfObject = new Object[1];
        arrayOfObject[0] = paramAccount.buildLocalizableAccountID();
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditMessage_12", arrayOfObject);
        audit(paramSecureUser, localLocalizableString, str2, 3010);
      }
    }
    debug(paramSecureUser, str1);
    return localAccount;
  }
  
  public static Accounts modifyRegisterAccountsData(SecureUser paramSecureUser, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.ModifyRegisterAccountsData";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    Accounts localAccounts = com.ffusion.csil.handlers.Register.modifyRegisterAccountsData(paramSecureUser, paramAccounts, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAccounts;
  }
  
  public static void setDefaultRegisterAccount(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.SetDefaultRegisterAccount";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.Register.setDefaultRegisterAccount(paramSecureUser, paramString, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
  
  public static RegisterTransactions addRecurringTransactions(SecureUser paramSecureUser, RegisterTransactions paramRegisterTransactions, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.AddRecurringTransactions";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    RegisterTransactions localRegisterTransactions = com.ffusion.csil.handlers.Register.addRecurringTransactions(paramSecureUser, paramRegisterTransactions, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localRegisterTransactions;
  }
  
  public static void deleteOldAndFailedTransactions(SecureUser paramSecureUser, ArrayList paramArrayList, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.deleteOldAndFailedTransactions";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.Register.deleteOldAndFailedTransactions(paramSecureUser, paramArrayList, paramInt, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str + paramInt);
  }
  
  public static void deleteRegisterTransactionsByTranId(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.DeleteRegisterTransactionsByTranId";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.Register.deleteRegisterTransactionsByTranId(paramSecureUser, paramString, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
  
  public static RegisterTransactions getRegisterTransactionsByTranId(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.GetRegisterTransactionsByTranId";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    long l = System.currentTimeMillis();
    RegisterTransactions localRegisterTransactions = com.ffusion.csil.handlers.Register.getRegisterTransactionsByTranId(paramSecureUser, paramString, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localRegisterTransactions;
  }
  
  public static void modifyRegisterTransactionsByTranId(SecureUser paramSecureUser, RegisterTransaction paramRegisterTransaction, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.ModifyRegisterTransactionsByTranId";
    if (!g(paramSecureUser)) {
      throw new CSILException(str, 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.Register.modifyRegisterTransactionsByTranId(paramSecureUser, paramRegisterTransaction, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Register.getReportData";
    String str2 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    EntitlementsUtil.checkReportTypeEntitlement(paramSecureUser, str2, true);
    if (!g(paramSecureUser)) {
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    Reporting.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
    IReportResult localIReportResult = com.ffusion.csil.handlers.Register.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    return localIReportResult;
  }
  
  private static boolean g(SecureUser paramSecureUser)
    throws CSILException
  {
    boolean bool = Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aBA);
    if (!bool)
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.register", "AuditEntFault_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    }
    return bool;
  }
  
  public static RegisterTransactions getPagedTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Register.getPagedTransactions";
    if (!g(paramSecureUser)) {
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    RegisterTransactions localRegisterTransactions = com.ffusion.csil.handlers.Register.getPagedTransactions(paramSecureUser, paramPagingContext, paramHashMap);
    PerfLog.log(str1, l, true);
    String str2 = TrackingIDGenerator.GetNextID();
    debug(paramSecureUser, str1);
    return localRegisterTransactions;
  }
  
  public static RegisterTransactions getNextTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Register.getNextTransactions";
    if (!g(paramSecureUser)) {
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    RegisterTransactions localRegisterTransactions = com.ffusion.csil.handlers.Register.getNextTransactions(paramSecureUser, paramPagingContext, paramHashMap);
    PerfLog.log(str1, l, true);
    String str2 = TrackingIDGenerator.GetNextID();
    debug(paramSecureUser, str1);
    return localRegisterTransactions;
  }
  
  public static RegisterTransactions getPreviousTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Register.getPreviousTransactions";
    if (!g(paramSecureUser)) {
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    RegisterTransactions localRegisterTransactions = com.ffusion.csil.handlers.Register.getPreviousTransactions(paramSecureUser, paramPagingContext, paramHashMap);
    PerfLog.log(str1, l, true);
    String str2 = TrackingIDGenerator.GetNextID();
    debug(paramSecureUser, str1);
    return localRegisterTransactions;
  }
  
  public static RegisterTransactions getLastTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Register.getLastTransactions";
    if (!g(paramSecureUser)) {
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    RegisterTransactions localRegisterTransactions = com.ffusion.csil.handlers.Register.getLastTransactions(paramSecureUser, paramPagingContext, paramHashMap);
    PerfLog.log(str1, l, true);
    String str2 = TrackingIDGenerator.GetNextID();
    debug(paramSecureUser, str1);
    return localRegisterTransactions;
  }
  
  public static RegisterTransaction getLastMatchingTransaction(SecureUser paramSecureUser, RegisterTransaction paramRegisterTransaction, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Register.getPagedTransactions";
    if (!g(paramSecureUser)) {
      throw new CSILException(str1, 20001);
    }
    long l = System.currentTimeMillis();
    RegisterTransaction localRegisterTransaction = com.ffusion.csil.handlers.Register.getLastMatchingTransaction(paramSecureUser, paramRegisterTransaction, paramHashMap);
    PerfLog.log(str1, l, true);
    String str2 = TrackingIDGenerator.GetNextID();
    debug(paramSecureUser, str1);
    return localRegisterTransaction;
  }
  
  public static void autoreconcileOldTransactions(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Register.autoreconcileOldTransactions";
    long l = System.currentTimeMillis();
    com.ffusion.csil.handlers.Register.autoreconcileOldTransactions(paramSecureUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Register
 * JD-Core Version:    0.7.0.1
 */