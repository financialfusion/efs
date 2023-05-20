package com.ffusion.tasks.ach;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.FundsTransactionTemplate;
import com.ffusion.beans.FundsTransactionTemplates;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHAddenda;
import com.ffusion.beans.ach.ACHAddendas;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.ach.ACHOffsetAccount;
import com.ffusion.beans.ach.ACHOffsetAccounts;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.beans.business.Business;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyACHBatch
  extends ACHBatch
  implements Task
{
  protected static String NAME = "NAME";
  protected static String GROUPID = "GROUPID";
  protected static String FROMACCOUNTID = "FROMACCOUNTID";
  protected static String BATCHSIZE = "BATCHSIZE";
  protected static String ENTRIES = "ENTRIES";
  protected static String HEADERCOMPNAME = "HEADERCOMPNAME";
  protected static String NewEntry = "NewEntry";
  protected static String SaveEntry = "SaveEntry";
  protected static String CancelEntry = "CancelEntry";
  protected static String EditEntryID = "EditEntryID";
  protected static String SetAllAmounts = "SetAllAmounts";
  protected static String DeleteEntryID = "DeleteEntryID";
  protected static String NewCompanyID = "NewCompanyID";
  protected int error = 0;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String dateWasChangedURL = null;
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected boolean clearEntriesFlag = true;
  protected HashMap parameters = new HashMap();
  protected String batchID = null;
  protected String batchName = "ACHBatch";
  protected String batchesName = "ACHBatches";
  protected String minAmount = "1.00";
  protected String maxAmount = "99999999.99";
  protected DateTime originalDate = null;
  protected boolean isTemplate = false;
  protected ACHBatch originalBatch = null;
  protected ACHEntry currentEntry;
  protected ACHAddenda currentAddenda;
  protected String companiesName = "ACHCOMPANIES";
  protected ACHEntry originalCurrentEntry = null;
  protected String templatesCollection = "ACHBatchTemplates";
  protected String payeesCollection = "ACHPayees";
  protected boolean anyAddToManaged = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (this.initFlag) {
      str = initProcess(localHttpSession);
    } else {
      str = doProcess(localHttpSession);
    }
    return str;
  }
  
  protected String initProcess(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.initFlag = false;
    this.error = 0;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    ACHBatch localACHBatch1 = null;
    Object localObject;
    if ((this.batchID != null) && (this.batchID.length() > 0))
    {
      localObject = (ACHBatches)paramHttpSession.getAttribute(this.batchesName);
      if (localObject != null) {
        localACHBatch1 = ((ACHBatches)localObject).getByID(this.batchID);
      }
    }
    if (localACHBatch1 == null) {
      localACHBatch1 = (ACHBatch)paramHttpSession.getAttribute(this.batchName);
    }
    if (localACHBatch1 == null)
    {
      this.error = 16002;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        localObject = new HashMap();
        SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        ACHPayees localACHPayees = (ACHPayees)paramHttpSession.getAttribute("ACHPayees");
        ((HashMap)localObject).put("ACHPayees", localACHPayees);
        ACHBatch localACHBatch2 = (ACHBatch)localACHBatch1.clone();
        localACHBatch1 = ACH.getACHBatch(localSecureUser, localACHBatch2, (HashMap)localObject);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException, paramHttpSession);
        str = this.serviceErrorURL;
      }
      if (this.error == 0) {
        if (localACHBatch1 != null)
        {
          set(localACHBatch1);
          this.originalBatch = localACHBatch1;
          paramHttpSession.setAttribute("ACHEntries", getACHEntries());
        }
        else
        {
          this.error = 16002;
          str = this.taskErrorURL;
        }
      }
    }
    return str;
  }
  
  protected String doProcess(HttpSession paramHttpSession)
  {
    String str = null;
    if (!this.parameters.isEmpty())
    {
      if (processParameters(paramHttpSession)) {
        return this.successURL;
      }
      if (this.error != 0) {
        return this.taskErrorURL;
      }
    }
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = modifyACHBatch(paramHttpSession);
      }
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String modifyACHBatch(HttpSession paramHttpSession)
  {
    String str = null;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      ACHBatch localACHBatch1 = null;
      if (getFrequencyValue() != 0) {
        localACHBatch1 = ACH.modifyRecACHBatch(localSecureUser, this, this.originalBatch, localHashMap);
      } else {
        localACHBatch1 = ACH.modifyACHBatch(localSecureUser, this, this.originalBatch, localHashMap);
      }
      set(localACHBatch1);
      localACHBatch1.getACHEntries().clear();
      if ((this.batchesName != null) && (this.batchesName.length() > 0))
      {
        ACHBatches localACHBatches = (ACHBatches)paramHttpSession.getAttribute(this.batchesName);
        ACHBatch localACHBatch2 = localACHBatches.getByID(localACHBatch1.getID());
        if (localACHBatch2 != null) {
          localACHBatch2.set(localACHBatch1);
        } else {
          localACHBatches.add(localACHBatch1);
        }
      }
      if (this.anyAddToManaged) {
        paramHttpSession.removeAttribute("ACHPayees");
      }
      str = this.successURL;
    }
    catch (CSILException localCSILException)
    {
      if ((localCSILException.getCode() == 20003) || (localCSILException.getServiceError() == 20003)) {
        paramHttpSession.setAttribute("ExceededLimits", localHashMap.get("ExceededLimits"));
      }
      this.error = MapError.mapError(localCSILException, paramHttpSession);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean createNewEntry(HttpSession paramHttpSession)
  {
    ACHEntry localACHEntry = (ACHEntry)this.entries.create();
    ACHAddendas localACHAddendas = localACHEntry.getAddendas();
    ACHAddenda localACHAddenda = (ACHAddenda)localACHAddendas.create();
    localACHEntry.setAmount("0.00");
    this.currentAddenda = localACHAddenda;
    this.currentEntry = localACHEntry;
    newPayee(paramHttpSession);
    return setInitialValues(paramHttpSession);
  }
  
  protected void newPayee(HttpSession paramHttpSession)
  {
    ACHPayee localACHPayee = new ACHPayee();
    localACHPayee.setScope("ACHBatch");
    this.currentEntry.setAchPayee(localACHPayee);
    paramHttpSession.setAttribute("ACHPayee", localACHPayee);
  }
  
  protected boolean processParameters(HttpSession paramHttpSession)
  {
    this.error = 0;
    boolean bool = true;
    Iterator localIterator = this.parameters.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      String str2 = (String)this.parameters.get(str1);
      bool &= processParameter(paramHttpSession, str1, str2);
    }
    if (this.error != 0) {
      bool = false;
    }
    this.parameters.clear();
    return bool;
  }
  
  protected boolean processParameter(HttpSession paramHttpSession, String paramString1, String paramString2)
  {
    Object localObject2;
    Object localObject3;
    if (NewCompanyID.equals(paramString1))
    {
      if (this.entries.size() == 0)
      {
        int i = 1;
        localObject2 = (Business)paramHttpSession.getAttribute("Business");
        if ((localObject2 != null) && (((Business)localObject2).getACHBatchTypeValue() != 0)) {
          i = ((Business)localObject2).getACHBatchTypeValue();
        }
        localObject3 = (ACHCompanies)paramHttpSession.getAttribute("ACHCOMPANIES");
        if (localObject3 == null)
        {
          this.error = 16505;
          return false;
        }
        ACHCompany localACHCompany = ((ACHCompanies)localObject3).getByCompanyID(paramString2);
        if (localACHCompany != null)
        {
          if (localACHCompany.getACHBatchTypeValue() != 0) {
            i = localACHCompany.getACHBatchTypeValue();
          }
          setCoID(localACHCompany.getID());
          setCompanyID(localACHCompany.getCompanyID());
          setCoName(localACHCompany.getCompanyName());
        }
        else
        {
          this.error = 16506;
          return false;
        }
        setBatchType(i);
        return true;
      }
      if (!paramString2.equals(getCompanyID()))
      {
        this.error = 16185;
        return false;
      }
      return true;
    }
    if (CancelEntry.equals(paramString1))
    {
      if ((this.originalCurrentEntry == null) && (this.currentEntry != null) && (this.entries != null)) {
        this.entries.remove(this.currentEntry);
      }
      this.originalCurrentEntry = null;
      this.currentEntry = null;
      this.currentAddenda = null;
      return true;
    }
    Object localObject1;
    if (DeleteEntryID.equals(paramString1))
    {
      localObject1 = this.entries.getByID(paramString2);
      if ((localObject1 != null) && (this.entries != null)) {
        this.entries.remove(localObject1);
      }
      this.currentEntry = null;
      this.currentAddenda = null;
      this.originalCurrentEntry = null;
      return true;
    }
    if (SetAllAmounts.equals(paramString1))
    {
      localObject1 = this.entries.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (ACHEntry)((Iterator)localObject1).next();
        ((ACHEntry)localObject2).setAmount(paramString2);
      }
      return true;
    }
    if (NewEntry.equals(paramString1))
    {
      this.originalCurrentEntry = null;
      return createNewEntry(paramHttpSession);
    }
    if (SaveEntry.equals(paramString1))
    {
      boolean bool = validateInput(paramHttpSession);
      if (bool)
      {
        if ((getBatchTypeValue() == 3) && (this.currentEntry.getOffsetAccountID() != null) && (this.currentEntry.getOffsetAccountID().length() > 0))
        {
          localObject2 = (ACHOffsetAccounts)paramHttpSession.getAttribute("ACHOffsetAccounts");
          localObject3 = ((ACHOffsetAccounts)localObject2).getByID(this.currentEntry.getOffsetAccountID());
          if (localObject3 != null)
          {
            this.currentEntry.setOffsetAccountBankID(((ACHOffsetAccount)localObject3).getRoutingNum());
            this.currentEntry.setOffsetAccountName(((ACHOffsetAccount)localObject3).getNickName());
            this.currentEntry.setOffsetAccountNumber(((ACHOffsetAccount)localObject3).getNumber());
            this.currentEntry.setOffsetAccountType(((ACHOffsetAccount)localObject3).getTypeValue());
          }
        }
        if (this.originalCurrentEntry != null)
        {
          if ((this.isTemplate) && ("ACHTemplate".equals(this.originalCurrentEntry.getAchPayeeScope())) && (this.originalCurrentEntry.getAchPayeeScope().equals(this.currentEntry.getAchPayeeScope())))
          {
            localObject2 = this.originalCurrentEntry.getAchPayee();
            localObject3 = this.currentEntry.getAchPayee();
            if (!((ACHPayee)localObject3).equals(localObject2)) {
              ((ACHPayee)localObject3).setScope("ACHBatch");
            }
          }
          this.originalCurrentEntry.set(this.currentEntry);
        }
        this.currentEntry = null;
        this.currentAddenda = null;
        this.originalCurrentEntry = null;
      }
      return bool;
    }
    if (EditEntryID.equals(paramString1))
    {
      processEditEntryID(paramHttpSession, paramString2);
      if (this.originalCurrentEntry != null) {
        return setInitialValues(paramHttpSession);
      }
    }
    return false;
  }
  
  protected void processEditEntryID(HttpSession paramHttpSession, String paramString)
  {
    this.originalCurrentEntry = this.entries.getByID(paramString);
    if (this.originalCurrentEntry != null)
    {
      this.currentEntry = ((ACHEntry)this.originalCurrentEntry.clone());
      this.currentAddenda = ((ACHAddenda)this.currentEntry.getAddendas().get(0));
    }
  }
  
  protected boolean setInitialValues(HttpSession paramHttpSession)
  {
    return true;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    this.error = 0;
    if (getBatchTypeValue() == 2) {
      if ((getOffsetAccountID() == null) || (getOffsetAccountID().length() == 0))
      {
        this.error = 16508;
        bool = false;
      }
      else
      {
        ACHOffsetAccounts localACHOffsetAccounts = (ACHOffsetAccounts)paramHttpSession.getAttribute("ACHOffsetAccounts");
        ACHOffsetAccount localACHOffsetAccount = localACHOffsetAccounts.getByID(getOffsetAccountID());
        if (localACHOffsetAccount != null)
        {
          setOffsetAccountBankID(localACHOffsetAccount.getRoutingNum());
          setOffsetAccountName(localACHOffsetAccount.getNickName());
          setOffsetAccountNumber(localACHOffsetAccount.getNumber());
          setOffsetAccountType(localACHOffsetAccount.getTypeValue());
        }
        else
        {
          this.error = 16508;
          bool = false;
        }
      }
    }
    if (this.originalBatch != null)
    {
      if (this.originalBatch.getStandardEntryClassCodeValue() != getStandardEntryClassCodeValue())
      {
        this.error = 16165;
        bool = false;
      }
      if ((this.originalBatch.getFrequencyValue() != getFrequencyValue()) && ((this.originalBatch.getFrequencyValue() == 0) || (getFrequencyValue() == 0)))
      {
        this.error = 16166;
        bool = false;
      }
    }
    if ((bool) && (getFrequencyValue() != 0) && (getNumberPaymentsValue() <= 1))
    {
      bool = false;
      this.error = 16184;
    }
    if ((bool) && (this.validate != null))
    {
      if ((bool) && (this.validate.indexOf(NAME) != -1)) {
        bool = validateName(paramHttpSession);
      }
      if ((bool) && (this.validate.indexOf("ACHTYPE") != -1)) {
        bool = validateACHType();
      }
      if ((bool) && (this.validate.indexOf("DATE") != -1)) {
        bool = validateDate(paramHttpSession);
      }
      if ((bool) && (this.validate.indexOf(BATCHSIZE) != -1)) {
        bool = validateBatchSize();
      }
      if ((bool) && (this.validate.indexOf("CODISCRETIONARYDATA") != -1)) {
        bool = validateCoDiscData();
      }
      if ((bool) && (this.validate.indexOf("COID") != -1)) {
        bool = validateCoID();
      }
      if ((bool) && (this.validate.indexOf("STANDARDENTRYCLASSCODE") != -1)) {
        bool = validateStdEntryClassCode();
      }
      if ((bool) && (this.validate.indexOf("COENTRYDESC") != -1)) {
        bool = validateCoEntryDesc();
      }
      if ((bool) && (this.validate.indexOf("ORIGDFIID") != -1)) {
        bool = validateOrigFIID();
      }
      if ((bool) && (this.validate.indexOf(ENTRIES) != -1)) {
        bool = validateEntries(paramHttpSession);
      }
      if ((bool) && (this.validate.indexOf(HEADERCOMPNAME) != -1)) {
        bool = validateHeaderCompName();
      }
    }
    this.validate = null;
    return bool;
  }
  
  protected boolean validateCoDiscData()
  {
    if (this.coDiscretionaryData == null) {
      this.coDiscretionaryData = "";
    }
    if (this.coDiscretionaryData.length() > 20) {
      this.error = 16137;
    }
    return this.error == 0;
  }
  
  protected boolean validateCoID()
  {
    if ((this.coID == null) || (this.coID.length() > 10)) {
      this.error = 16138;
    }
    return this.error == 0;
  }
  
  protected boolean validateHeaderCompName()
  {
    if ((this.headerCompName == null) || (this.headerCompName.length() <= 0))
    {
      if ((getStandardEntryClassCodeValue() == 1) || (getStandardEntryClassCodeValue() == 17)) {
        this.error = 16531;
      }
      if (getStandardEntryClassCodeValue() == 2) {
        this.error = 16535;
      }
      if (getStandardEntryClassCodeValue() == 5) {
        this.error = 16536;
      }
    }
    return this.error == 0;
  }
  
  protected boolean validateDate(HttpSession paramHttpSession)
  {
    if (this.date == null)
    {
      this.error = 16118;
    }
    else
    {
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      String str1 = getDateFormat();
      HashMap localHashMap = new HashMap();
      try
      {
        String str2 = ACH.getACHSECEntitlement(localSecureUser, this, null, null);
        String str3 = ACH.getDefaultEffectiveDate(localSecureUser, getCoID().trim(), str2.trim(), localHashMap);
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date localDate1 = localSimpleDateFormat.parse(str3);
        if (localDate1.after(this.date.getTime()))
        {
          this.error = 16191;
          setDate(new DateTime(localDate1, this.locale));
          if (this.date != null) {
            this.date.setFormat(str1);
          }
        }
        else
        {
          Date localDate2 = null;
          if (getDateValue() != null) {
            try
            {
              localDate2 = ACH.getSmartDate(localSecureUser, getDateValue());
            }
            catch (CSILException localCSILException2) {}
          }
          if (localDate2 != null)
          {
            setDate(new DateTime(localDate2, this.locale));
            if (this.date != null) {
              this.date.setFormat(str1);
            }
          }
        }
        if (("true".equalsIgnoreCase(getDateHasChanged())) && (this.error == 0)) {
          this.error = 16190;
        }
      }
      catch (CSILException localCSILException1)
      {
        this.error = localCSILException1.getCode();
      }
      catch (ParseException localParseException)
      {
        this.error = localParseException.getErrorOffset();
      }
    }
    return this.error == 0;
  }
  
  protected boolean validateStdEntryClassCode()
  {
    if ((this.standardEntryClassCode == null) || (this.standardEntryClassCode.length() > 3)) {
      this.error = 16139;
    }
    return this.error == 0;
  }
  
  protected boolean validateCoEntryDesc()
  {
    if ((this.coEntryDesc == null) || (this.coEntryDesc.trim().length() == 0)) {
      this.error = 16158;
    } else if (this.coEntryDesc.length() > 10) {
      this.error = 16140;
    } else if ("REVERSAL".equalsIgnoreCase(this.coEntryDesc)) {
      this.error = 16532;
    }
    return this.error == 0;
  }
  
  protected boolean validateOrigFIID()
  {
    if ((this.origDFIID == null) || (this.origDFIID.length() > 9)) {
      this.error = 16141;
    }
    return this.error == 0;
  }
  
  protected boolean validateName(HttpSession paramHttpSession)
  {
    this.error = 0;
    if ((this.name == null) || (this.name.length() == 0))
    {
      this.error = 16108;
      return false;
    }
    if (this.name.length() > 128)
    {
      this.error = 16518;
      return false;
    }
    return true;
  }
  
  protected boolean validateACHType()
  {
    if ((this.achType == null) || (this.achType.length() == 0)) {
      this.error = 16130;
    } else {
      this.error = 0;
    }
    return this.error == 0;
  }
  
  protected boolean validateBatchSize()
  {
    if (size() == 0) {
      this.error = 16135;
    } else {
      this.error = 0;
    }
    return this.error == 0;
  }
  
  protected boolean validateEntries(HttpSession paramHttpSession)
  {
    int i = 0;
    this.anyAddToManaged = false;
    if ((this.entries == null) || (this.entries.isEmpty())) {
      return false;
    }
    ACHCompany localACHCompany = (ACHCompany)paramHttpSession.getAttribute("ACHCOMPANY");
    boolean bool = true;
    if ((localACHCompany != null) && (getTaxForm() == null)) {
      bool = localACHCompany.getClassCodeAddendaEntitled(getStandardEntryClassCode());
    }
    Iterator localIterator = this.entries.iterator();
    while (localIterator.hasNext())
    {
      ACHEntry localACHEntry = (ACHEntry)localIterator.next();
      if (localACHEntry.getAchPayee().getAddToListValue() == true) {
        this.anyAddToManaged = true;
      }
      if (!bool) {
        localACHEntry.getAddendas().clear();
      }
      double d = 0.0D;
      if (localACHEntry.getAmountValue() != null) {
        d = localACHEntry.getAmountValue().doubleValue();
      }
      if (d > 99999999.989999995D)
      {
        this.error = 16125;
        return false;
      }
      if (d < 0.0D)
      {
        this.error = 16124;
        return false;
      }
      if (("PRENOTE".equals(localACHEntry.getAction())) && (!localACHEntry.getActiveValue()))
      {
        localACHEntry.addValidationError(localACHEntry.getImportError("PrenoteCondition"), localACHEntry.getImportError("PleaseRecheckHoldStatus"));
        localACHEntry.setAction("PRENOTE" + getDate());
        i++;
      }
      if ((!this.isTemplate) && (localACHEntry.getActiveValue()))
      {
        if (d == 0.0D)
        {
          int j = getStandardEntryClassCodeValue();
          if (localACHEntry.getPrenote().equalsIgnoreCase("false")) {
            if ((j == 12) || (j == 13))
            {
              if (localACHEntry.getAddendaString().length() == 0)
              {
                this.error = 16148;
                return false;
              }
            }
            else
            {
              this.error = 16147;
              return false;
            }
          }
        }
        else if (localACHEntry.getPrenote().equalsIgnoreCase("true"))
        {
          this.error = 16149;
          return false;
        }
        if (!("PRENOTE" + getDate()).equals(localACHEntry.getAction()))
        {
          localACHEntry.setAction("");
          if (("PRENOTE_PENDING".equals(localACHEntry.getAchPayee().getPrenoteStatus())) || ("PRENOTE_REQUESTED".equals(localACHEntry.getAchPayee().getPrenoteStatus()))) {
            if (localACHEntry.getAchPayee().getPrenoteSubmitDateValue() != null)
            {
              DateTime localDateTime = (DateTime)localACHEntry.getAchPayee().getPrenoteSubmitDateValue().clone();
              int k = 6;
              while (k > 0)
              {
                localDateTime.add(5, 1);
                int m = localDateTime.get(7);
                if ((m != 7) && (m != 1))
                {
                  Date localDate = null;
                  try
                  {
                    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
                    localDate = ACH.getSmartDate(localSecureUser, localDateTime);
                  }
                  catch (CSILException localCSILException) {}
                  k--;
                  localDateTime.setTime(localDate);
                }
              }
              if (localDateTime.before(this.date)) {}
            }
            else
            {
              if (this.isTemplate) {
                localACHEntry.addValidationError(localACHEntry.getImportError("PrenoteConditionTemplate"), localACHEntry.getImportError("PleaseRecheckInactiveStatus"));
              } else {
                localACHEntry.addValidationError(localACHEntry.getImportError("PrenoteCondition"), localACHEntry.getImportError("PleaseRecheckHoldStatus"));
              }
              localACHEntry.setActive("false");
              localACHEntry.setAction("PRENOTE" + getDate());
              i++;
            }
          }
        }
      }
    }
    if (i != 0)
    {
      this.error = 16192;
      return false;
    }
    return true;
  }
  
  protected boolean initFromTemplate(HttpSession paramHttpSession)
  {
    FundsTransactionTemplates localFundsTransactionTemplates = null;
    int i = -1;
    try
    {
      i = Integer.parseInt(this.templateID);
    }
    catch (NumberFormatException localNumberFormatException) {}
    if (i == -1)
    {
      this.error = 1039;
    }
    else
    {
      if ((this.templatesCollection == null) || (this.templatesCollection.length() == 0)) {
        this.error = 1034;
      } else {
        localFundsTransactionTemplates = (FundsTransactionTemplates)paramHttpSession.getAttribute(this.templatesCollection);
      }
      if (localFundsTransactionTemplates == null) {
        this.error = 1034;
      }
    }
    if (this.error == 0)
    {
      FundsTransactionTemplate localFundsTransactionTemplate = localFundsTransactionTemplates.getByTemplateID(i);
      if ((localFundsTransactionTemplate != null) && ((localFundsTransactionTemplate.getFundsTransaction() instanceof ACHBatch)))
      {
        ACHBatch localACHBatch = (ACHBatch)localFundsTransactionTemplate.getFundsTransaction().clone();
        if (localACHBatch != null)
        {
          localACHBatch.setTemplateID("" + localFundsTransactionTemplate.getID());
          localACHBatch.setTemplateName(localFundsTransactionTemplate.getTemplateName());
          localACHBatch.setName(localFundsTransactionTemplate.getTemplateName());
          if ((getCoID() != null) && (getCoID().length() > 0)) {
            localACHBatch.setCoID(getCoID());
          }
          if ((getCoName() != null) && (getCoName().length() > 0)) {
            localACHBatch.setCoName(getCoName());
          }
          if ((getCompanyID() != null) && (getCompanyID().length() > 0)) {
            localACHBatch.setCompanyID(getCompanyID());
          }
          if (localACHBatch.getCoEntryDesc() == null) {
            localACHBatch.setCoEntryDesc("");
          }
          localACHBatch.setDate(getDateValue());
          localACHBatch.setDateFormat(getDateFormat());
          localACHBatch.setID(null);
          localACHBatch.setTrackingID(null);
          ACHPayees localACHPayees = (ACHPayees)paramHttpSession.getAttribute(this.payeesCollection);
          localACHBatch.addACHPayees(localACHPayees);
          SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
          ModifyACHTemplate.getFreeFormTemplatePayees(localSecureUser, localACHBatch);
          for (int j = 0; j < localACHBatch.getACHEntries().size(); j++)
          {
            ACHEntry localACHEntry = (ACHEntry)localACHBatch.getACHEntries().get(j);
            String str = localACHEntry.getAchPayeeID();
            ACHPayee localACHPayee = localACHEntry.getAchPayee();
            if ((str != null) && (str.length() > 0) && (localACHPayee == null))
            {
              this.error = 16523;
              break;
            }
          }
          set(localACHBatch);
        }
        else
        {
          this.error = 16002;
        }
      }
    }
    return this.error == 0;
  }
  
  public final void setMinimumAmount(String paramString)
  {
    this.minAmount = paramString;
  }
  
  public final void setMaximumAmount(String paramString)
  {
    this.maxAmount = paramString;
  }
  
  public final void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setIsTemplate(String paramString)
  {
    this.isTemplate = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final String getIsTemplate()
  {
    return "" + this.isTemplate;
  }
  
  public final boolean getIsTemplateValue()
  {
    return this.isTemplate;
  }
  
  public final String getFromTemplate()
  {
    return "" + getFromTemplateValue();
  }
  
  public final boolean getFromTemplateValue()
  {
    return (getTemplateID() != null) && (getTemplateID().length() > 0) && (!this.isTemplate);
  }
  
  public final void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setValidate(String paramString)
  {
    if (!paramString.equals("")) {
      this.validate = paramString.toUpperCase();
    } else {
      this.validate = null;
    }
  }
  
  public final void setClearEntriesFlag(String paramString)
  {
    this.clearEntriesFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.error);
  }
  
  public final void setBatchID(String paramString)
  {
    this.batchID = paramString;
  }
  
  public final void setBatchName(String paramString)
  {
    this.batchName = paramString;
  }
  
  public final String getBatchName()
  {
    return this.batchName;
  }
  
  public final void setBatchesName(String paramString)
  {
    this.batchesName = paramString;
  }
  
  public void setCurrentEntry(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.currentEntry = getACHEntries().getByID(paramString);
    } else {
      this.currentEntry = null;
    }
  }
  
  public ACHEntry getCurrentEntry()
  {
    return this.currentEntry;
  }
  
  public void setEntryOffsetAccountNumber(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setOffsetAccountNumber(paramString);
    }
  }
  
  public void setEntryOffsetAccountType(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setOffsetAccountType(paramString);
    }
  }
  
  public void setEntryOffsetAccountBankID(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setOffsetAccountBankID(paramString);
    }
  }
  
  public void setEntryAmount(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setAmount(paramString);
    }
  }
  
  public void setEntryAmountIsDebit(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setAmountIsDebit(paramString);
    }
  }
  
  public void setEntryActive(String paramString)
  {
    if ((this.currentEntry != null) && (paramString != null) && (!paramString.equalsIgnoreCase(this.currentEntry.getActive())))
    {
      this.currentEntry.setActive(paramString);
      if ("PRENOTE".equals(this.currentEntry.getAction())) {
        this.currentEntry.setAction("PRENOTE" + getDate());
      }
    }
  }
  
  public void setEntryCheckSerialNumber(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setCheckSerialNumber(paramString);
    }
  }
  
  public void setEntryTerminalState(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setTerminalState(paramString);
    }
  }
  
  public void setEntryTerminalCity(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setTerminalCity(paramString);
    }
  }
  
  public void setEntryProcessControlField(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setProcessControlField(paramString);
    }
  }
  
  public void setEntryItemResearchNumber(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setItemResearchNumber(paramString);
    }
  }
  
  public void setEntryPaymentTypeCode(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setPaymentTypeCode(paramString);
    }
  }
  
  public void setEntryIdentificationNumber(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setIdentificationNumber(paramString);
    }
  }
  
  public void setCurrentAddenda(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0) && (this.currentEntry != null)) {
      this.currentAddenda = this.currentEntry.getAddendas().getByID(paramString);
    } else {
      this.currentAddenda = null;
    }
  }
  
  public void setAddendaPmtRelatedInfo(String paramString)
  {
    if (this.currentAddenda != null) {
      this.currentAddenda.setPmtRelatedInfo(paramString);
    }
  }
  
  public String getAddendaPmtRelatedInfo()
  {
    if (this.currentAddenda != null) {
      return this.currentAddenda.getPmtRelatedInfo();
    }
    return null;
  }
  
  public void setAddendaString(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setAddendaString(paramString);
    }
  }
  
  public String getAddendaString()
  {
    if (this.currentEntry != null) {
      return this.currentEntry.getAddendaString();
    }
    return null;
  }
  
  public void setDate(String paramString)
  {
    super.setDate(paramString);
    this.originalDate = ((DateTime)getDateValue().clone());
  }
  
  public String getDateHasChanged()
  {
    String str1 = this.date.getFormat();
    if (this.originalDate == null) {
      return String.valueOf(false);
    }
    this.originalDate.setFormat("yyyy.MM.dd");
    this.date.setFormat("yyyy.MM.dd");
    String str2 = this.date.toString();
    String str3 = this.originalDate.toString();
    this.date.setFormat(str1);
    if (!str2.equals(str3)) {
      return String.valueOf(true);
    }
    return String.valueOf(false);
  }
  
  public final void setCompaniesInSessionName(String paramString)
  {
    this.companiesName = paramString;
  }
  
  public final String getCompaniesInSessionName()
  {
    return this.companiesName;
  }
  
  public void setNewEntry(String paramString)
  {
    this.parameters.put(NewEntry, "" + Boolean.valueOf(paramString).booleanValue());
  }
  
  public void setSaveEntry(String paramString)
  {
    this.parameters.put(SaveEntry, "" + Boolean.valueOf(paramString).booleanValue());
  }
  
  public void setCancelEntry(String paramString)
  {
    this.parameters.put(CancelEntry, "" + Boolean.valueOf(paramString).booleanValue());
  }
  
  public void setEditEntryID(String paramString)
  {
    this.parameters.put(EditEntryID, paramString);
  }
  
  public void setSetAllAmounts(String paramString)
  {
    this.parameters.put(SetAllAmounts, paramString);
  }
  
  public void setDeleteEntryID(String paramString)
  {
    this.parameters.put(DeleteEntryID, paramString);
  }
  
  public void setNewCompanyID(String paramString)
  {
    this.parameters.put(NewCompanyID, paramString);
  }
  
  public String getIsEdit()
  {
    if (this.originalCurrentEntry != null) {
      return "true";
    }
    return "false";
  }
  
  public void setTemplatesCollection(String paramString)
  {
    this.templatesCollection = paramString;
  }
  
  public void setPayeesCollection(String paramString)
  {
    this.payeesCollection = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.ModifyACHBatch
 * JD-Core Version:    0.7.0.1
 */