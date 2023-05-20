package com.ffusion.tasks.ach;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHAddenda;
import com.ffusion.beans.ach.ACHAddendas;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.ach.ACHOffsetAccount;
import com.ffusion.beans.ach.ACHOffsetAccounts;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.ach.TaxField;
import com.ffusion.beans.ach.TaxForm;
import com.ffusion.beans.ach.TaxFormBank;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.MapError;
import com.ffusion.util.RoutingNumberUtil;
import com.ffusion.util.Strings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpSession;

public class ModifyACHTaxPayment
  extends ModifyACHBatch
{
  protected HashMap fieldValues;
  protected HashMap validatedValues;
  protected TaxForm defaultTaxForm = null;
  protected boolean useDefaultTaxForm = false;
  protected TaxForm currentTaxForm = null;
  protected String currentField;
  protected HashMap errorMap = null;
  protected static String EntryTaxFormID = "EntryTaxFormID";
  protected static String DepositAccountID = "DepositAccountID";
  protected static String DefaultTaxFormID = "DefaultTaxFormID";
  
  public ModifyACHTaxPayment()
  {
    setBatchFundsTransactionType(this);
    this.fieldValues = new HashMap();
    this.validatedValues = new HashMap();
    this.errorMap = new HashMap();
  }
  
  protected String initProcess(HttpSession paramHttpSession)
  {
    String str = super.initProcess(paramHttpSession);
    if ((str == null) || (str.equals(this.successURL)))
    {
      this.currentEntry = ((ACHEntry)this.entries.get(0));
      this.currentAddenda = ((ACHAddenda)this.currentEntry.getAddendas().get(0));
      this.defaultTaxForm = getTaxForm();
      setInitialValues(paramHttpSession);
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
        str = modifyTaxPayment(paramHttpSession);
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
  
  protected void setBatchFundsTransactionType(ACHBatch paramACHBatch)
  {
    paramACHBatch.setType(12);
    paramACHBatch.setACHType("TaxPayment");
  }
  
  protected String modifyTaxPayment(HttpSession paramHttpSession)
  {
    String str = null;
    ACHBatch localACHBatch = new ACHBatch(this.locale);
    localACHBatch.set(this);
    setBatchFundsTransactionType(localACHBatch);
    HashMap localHashMap = new HashMap();
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      localACHBatch = ACH.modifyACHBatch(localSecureUser, localACHBatch, this.originalBatch, localHashMap);
      set(localACHBatch);
      if (this.clearEntriesFlag) {
        getACHEntries().clear();
      }
    }
    catch (CSILException localCSILException)
    {
      if ((localCSILException.getCode() == 20003) || (localCSILException.getServiceError() == 20003)) {
        paramHttpSession.setAttribute("ExceededLimits", localHashMap.get("ExceededLimits"));
      }
      this.error = MapError.mapError(localCSILException, paramHttpSession);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      ACHBatches localACHBatches = (ACHBatches)paramHttpSession.getAttribute(this.batchesName);
      if (localACHBatches != null) {
        localACHBatches.add(localACHBatch);
      }
      paramHttpSession.setAttribute("ACHBatch", localACHBatch);
    }
    return str;
  }
  
  protected boolean processParameter(HttpSession paramHttpSession, String paramString1, String paramString2)
  {
    Object localObject1;
    Object localObject2;
    if (DefaultTaxFormID.equals(paramString1))
    {
      if ((this.entries.size() == 0) || ((this.defaultTaxForm == null) && (this.useDefaultTaxForm)))
      {
        localObject1 = new HashMap();
        localObject2 = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        try
        {
          TaxForm localTaxForm = ACH.getTaxForm((SecureUser)localObject2, paramString2, (HashMap)localObject1);
          if (localTaxForm != null)
          {
            this.defaultTaxForm = localTaxForm;
            if ((localTaxForm.getCompEntryDisc() != null) && (localTaxForm.getCompEntryDisc().length() > 0) && ((getCoEntryDesc() == null) || (getCoEntryDesc().length() == 0))) {
              setCoEntryDesc(localTaxForm.getCompEntryDisc());
            }
            if ((localTaxForm.getCompDiscData() != null) && (localTaxForm.getCompDiscData().length() > 0) && ((getCoDiscretionaryData() == null) || (getCoDiscretionaryData().length() == 0))) {
              setCoDiscretionaryData(localTaxForm.getCompDiscData());
            }
          }
        }
        catch (CSILException localCSILException1)
        {
          localCSILException1.printStackTrace();
        }
        return true;
      }
      this.error = 16186;
      return false;
    }
    Object localObject3;
    if (SetAllAmounts.equals(paramString1))
    {
      localObject1 = paramString2;
      localObject2 = this.entries.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = (ACHEntry)((Iterator)localObject2).next();
        processEditEntryID(paramHttpSession, ((ACHEntry)localObject3).getID());
        if (this.originalCurrentEntry != null)
        {
          setInitialValues(paramHttpSession);
          Iterator localIterator = this.currentTaxForm.getFields().iterator();
          while (localIterator.hasNext())
          {
            this.error = 0;
            TaxField localTaxField = (TaxField)localIterator.next();
            if ((localTaxField.getEditableValue()) && ("N2".equalsIgnoreCase(localTaxField.getDataType()))) {
              this.fieldValues.put(localTaxField.getFieldname(), localObject1);
            }
          }
          if (super.processParameter(paramHttpSession, SaveEntry, "true") != true)
          {
            this.currentEntry = null;
            this.currentAddenda = null;
            this.currentTaxForm = null;
            this.originalCurrentEntry = null;
            return false;
          }
        }
      }
      this.currentEntry = null;
      this.currentAddenda = null;
      this.currentTaxForm = null;
      this.originalCurrentEntry = null;
      return true;
    }
    if (CancelEntry.equals(paramString1))
    {
      if ((this.originalCurrentEntry != null) && (this.entries.size() == 1) && (!this.originalCurrentEntry.getTaxFormID().equals(this.currentEntry.getTaxFormID())))
      {
        localObject1 = new HashMap();
        localObject2 = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        try
        {
          localObject3 = ACH.getTaxForm((SecureUser)localObject2, this.originalCurrentEntry.getTaxFormID(), (HashMap)localObject1);
          if (localObject3 != null)
          {
            this.defaultTaxForm = ((TaxForm)((TaxForm)localObject3).clone());
            if ((((TaxForm)localObject3).getCompEntryDisc() != null) && (((TaxForm)localObject3).getCompEntryDisc().length() > 0) && ((getCoEntryDesc() == null) || (getCoEntryDesc().length() == 0))) {
              setCoEntryDesc(((TaxForm)localObject3).getCompEntryDisc());
            }
            if ((((TaxForm)localObject3).getCompDiscData() != null) && (((TaxForm)localObject3).getCompDiscData().length() > 0) && ((getCoDiscretionaryData() == null) || (getCoDiscretionaryData().length() == 0))) {
              setCoDiscretionaryData(((TaxForm)localObject3).getCompDiscData());
            }
            if ((this.taxForm == null) || (!this.defaultTaxForm.getID().equals(this.taxForm.getID()))) {
              setTaxForm(this.defaultTaxForm);
            }
          }
        }
        catch (CSILException localCSILException2)
        {
          localCSILException2.printStackTrace();
        }
      }
      if ((this.originalCurrentEntry == null) && (this.currentEntry != null) && (this.entries != null))
      {
        this.entries.remove(this.currentEntry);
        if ((this.entries.size() == 0) && (!this.useDefaultTaxForm)) {
          this.defaultTaxForm = null;
        }
      }
      this.originalCurrentEntry = null;
      this.currentEntry = null;
      this.currentAddenda = null;
      this.currentTaxForm = null;
      return true;
    }
    if (DeleteEntryID.equals(paramString1))
    {
      localObject1 = this.entries.getByID(paramString2);
      if ((localObject1 != null) && (this.entries != null))
      {
        this.entries.remove(localObject1);
        if ((this.entries.size() == 0) && (!this.useDefaultTaxForm)) {
          this.defaultTaxForm = null;
        }
      }
      this.currentEntry = null;
      this.currentAddenda = null;
      this.originalCurrentEntry = null;
      this.currentTaxForm = null;
      return true;
    }
    if ((EntryTaxFormID.equals(paramString1)) && (this.currentEntry != null) && (paramString2.trim().length() > 0) && (!paramString2.equals(this.currentEntry.getTaxFormID())))
    {
      this.currentEntry.setTaxFormID(paramString2);
      if (this.currentAddenda != null) {
        this.currentAddenda.setPmtRelatedInfo("");
      }
      return setInitialValues(paramHttpSession);
    }
    if ((DepositAccountID.equals(paramString1)) && (this.currentEntry != null) && (this.currentTaxForm != null) && (paramString2.trim().length() > 0) && (!paramString2.equals(getTaxDepositAccountID())))
    {
      this.currentTaxForm.setDepositAccountID(paramString2);
      newPayee(paramHttpSession);
      setInitialPayeeValuesFromTaxForm();
      return true;
    }
    if (EditEntryID.equals(paramString1))
    {
      processEditEntryID(paramHttpSession, paramString2);
      if (this.originalCurrentEntry != null) {
        return setInitialValues(paramHttpSession);
      }
    }
    return super.processParameter(paramHttpSession, paramString1, paramString2);
  }
  
  protected void processEditEntryID(HttpSession paramHttpSession, String paramString)
  {
    this.originalCurrentEntry = this.entries.getByID(paramString);
    if (this.originalCurrentEntry != null)
    {
      this.currentEntry = ((ACHEntry)this.originalCurrentEntry.clone());
      this.currentAddenda = ((ACHAddenda)this.currentEntry.getAddendas().get(0));
      if (this.currentEntry.getTaxFormID() != null)
      {
        HashMap localHashMap = new HashMap();
        SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        try
        {
          TaxForm localTaxForm = ACH.getTaxForm(localSecureUser, this.currentEntry.getTaxFormID(), localHashMap);
          if (localTaxForm != null) {
            this.currentTaxForm = ((TaxForm)localTaxForm.clone());
          }
        }
        catch (CSILException localCSILException)
        {
          localCSILException.printStackTrace();
        }
      }
      else if (this.defaultTaxForm != null)
      {
        this.currentEntry.setTaxFormID(this.defaultTaxForm.getID());
        this.originalCurrentEntry.setTaxFormID(this.defaultTaxForm.getID());
      }
    }
  }
  
  protected boolean setInitialValues(HttpSession paramHttpSession)
  {
    int i = 0;
    if ((this.useDefaultTaxForm) && (this.defaultTaxForm != null) && (this.currentEntry.getTaxFormID() == null))
    {
      this.currentEntry.setTaxFormID(this.defaultTaxForm.getID());
      this.currentTaxForm = ((TaxForm)this.defaultTaxForm.clone());
    }
    if ((this.currentEntry.getTaxFormID() != null) && (this.defaultTaxForm != null) && (this.currentEntry.getTaxFormID().equals(this.defaultTaxForm.getID()))) {
      i = 1;
    }
    if ((this.currentEntry.getTaxFormID() != null) && (i == 0) && ((this.currentTaxForm == null) || (!this.currentEntry.getTaxFormID().equals(this.currentTaxForm.getID()))))
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      try
      {
        TaxForm localTaxForm = ACH.getTaxForm(localSecureUser, this.currentEntry.getTaxFormID(), localHashMap);
        if (localTaxForm != null)
        {
          int j = 1;
          if ((this.defaultTaxForm == null) || (this.entries.size() == 1))
          {
            this.defaultTaxForm = ((TaxForm)localTaxForm.clone());
            if ((localTaxForm.getCompEntryDisc() != null) && (localTaxForm.getCompEntryDisc().length() > 0) && ((getCoEntryDesc() == null) || (getCoEntryDesc().length() == 0))) {
              setCoEntryDesc(localTaxForm.getCompEntryDisc());
            }
            if ((localTaxForm.getCompDiscData() != null) && (localTaxForm.getCompDiscData().length() > 0) && ((getCoDiscretionaryData() == null) || (getCoDiscretionaryData().length() == 0))) {
              setCoDiscretionaryData(localTaxForm.getCompDiscData());
            }
          }
          if ((localTaxForm.getCompEntryDisc() != null) || (this.defaultTaxForm.getCompEntryDisc() != null)) {
            if ((localTaxForm.getCompEntryDisc() != null) && (!localTaxForm.getCompEntryDisc().equals(this.defaultTaxForm.getCompEntryDisc()))) {
              j = 0;
            } else if ((this.defaultTaxForm.getCompEntryDisc() != null) && (!this.defaultTaxForm.getCompEntryDisc().equals(localTaxForm.getCompEntryDisc()))) {
              j = 0;
            }
          }
          if ((localTaxForm.getCompDiscData() != null) || (this.defaultTaxForm.getCompDiscData() != null)) {
            if ((localTaxForm.getCompDiscData() != null) && (!localTaxForm.getCompDiscData().equals(this.defaultTaxForm.getCompDiscData()))) {
              j = 0;
            } else if ((this.defaultTaxForm.getCompDiscData() != null) && (!this.defaultTaxForm.getCompDiscData().equals(localTaxForm.getCompDiscData()))) {
              j = 0;
            }
          }
          if (j == 0)
          {
            if (this.currentTaxForm != null) {
              this.currentEntry.setTaxFormID(this.currentTaxForm.getID());
            }
            this.error = 16188;
            return false;
          }
          if (this.currentTaxForm != null) {
            newPayee(paramHttpSession);
          }
          this.currentTaxForm = localTaxForm;
        }
        else
        {
          i = 1;
        }
      }
      catch (CSILException localCSILException)
      {
        localCSILException.printStackTrace();
        i = 1;
      }
    }
    if (this.defaultTaxForm != null)
    {
      if ((i != 0) || ((this.currentTaxForm == null) && (this.useDefaultTaxForm))) {
        this.currentTaxForm = ((TaxForm)this.defaultTaxForm.clone());
      }
      if ((this.taxForm == null) || (!this.defaultTaxForm.getID().equals(this.taxForm.getID()))) {
        setTaxForm(this.defaultTaxForm);
      }
    }
    setInitialFieldValuesFromTaxForm();
    if ((this.currentAddenda != null) && (this.currentTaxForm != null)) {
      setTaxValuesFromAddenda(this.currentAddenda.getPmtRelatedInfo());
    }
    return true;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    this.error = 0;
    if ((getBatchTypeValue() != 1) && (getOffsetAccountID() != null) && (getOffsetAccountID().length() > 0))
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
    }
    this.error = validateTaxFields();
    if (this.error == 0) {
      this.error = validateTaxRoutingNumber();
    }
    if ((this.error == 0) && (this.parameters.get(SaveEntry) == null)) {
      super.validateInput(paramHttpSession);
    }
    return this.error == 0;
  }
  
  public int validateTaxRoutingNumber()
  {
    if ((this.currentEntry == null) || (this.currentAddenda == null) || (this.currentTaxForm == null)) {
      return 0;
    }
    if ((getTaxAccountNumber() == null) || (getTaxAccountNumber().length() == 0)) {
      return 16146;
    }
    if ((getTaxRoutingNumber() == null) || (getTaxRoutingNumber().length() != 9)) {
      return 16101;
    }
    if (!RoutingNumberUtil.isValidRoutingNumber(getTaxRoutingNumber(), true)) {
      return 16101;
    }
    return 0;
  }
  
  protected int validateTaxFields()
  {
    if ((this.currentEntry == null) || (this.currentAddenda == null) || (this.currentTaxForm == null)) {
      return 0;
    }
    this.errorMap.clear();
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.currentTaxForm.getFields().iterator();
    boolean bool = false;
    Object localObject2;
    while (localIterator.hasNext())
    {
      this.error = 0;
      localObject1 = (TaxField)localIterator.next();
      if ((!((TaxField)localObject1).getEditableValue()) && (((TaxField)localObject1).anyLeadingOrTrailing()))
      {
        localObject2 = (String)this.fieldValues.get(((TaxField)localObject1).getFieldname());
        if (localObject2 == null) {
          localObject2 = "";
        }
        localObject2 = ((TaxField)localObject1).addSpaceFilledOrZeroFilled((String)localObject2);
        this.fieldValues.put(((TaxField)localObject1).getFieldname(), localObject2);
      }
      else if (((TaxField)localObject1).getEditableValue())
      {
        bool = ((TaxField)localObject1).getRequiredValue();
        localObject2 = (String)this.fieldValues.get(((TaxField)localObject1).getFieldname());
        if (localObject2 == null) {
          localObject2 = "";
        }
        if ((!bool) && (((TaxField)localObject1).anyLeadingOrTrailing()) && (("AN".equalsIgnoreCase(((TaxField)localObject1).getDataType())) || ("ID".equalsIgnoreCase(((TaxField)localObject1).getDataType())))) {
          localObject2 = ((TaxField)localObject1).addSpaceFilledOrZeroFilled((String)localObject2);
        }
        if ((((String)localObject2).length() > 0) && (("AN".equalsIgnoreCase(((TaxField)localObject1).getDataType())) || ("ID".equalsIgnoreCase(((TaxField)localObject1).getDataType()))))
        {
          localObject2 = ((TaxField)localObject1).addSpaceFilledOrZeroFilled((String)localObject2);
          this.fieldValues.put(((TaxField)localObject1).getFieldname(), localObject2);
          if ((((String)localObject2).indexOf('*') >= 0) || (((String)localObject2).indexOf('\\') >= 0) || ((((TaxField)localObject1).getNumbersOnlyValue()) && (Strings.countNumbers((String)localObject2) != ((String)localObject2).length()))) {
            this.error = 16177;
          }
        }
        if ((bool) && ((localObject2 == null) || (((String)localObject2).length() == 0)))
        {
          if ("TXP01".equalsIgnoreCase(((TaxField)localObject1).getFieldname())) {
            this.error = 16172;
          }
          if ("TXP02".equalsIgnoreCase(((TaxField)localObject1).getFieldname())) {
            this.error = 16173;
          }
          if ("TXP03".equalsIgnoreCase(((TaxField)localObject1).getFieldname())) {
            this.error = 16174;
          }
          if ("N2".equalsIgnoreCase(((TaxField)localObject1).getDataType())) {
            this.error = 16175;
          }
          if (this.error == 0) {
            this.error = 16145;
          }
        }
        if ("AN".equalsIgnoreCase(((TaxField)localObject1).getDataType()))
        {
          if (((String)localObject2).length() > ((TaxField)localObject1).getMaxLengthValue())
          {
            if (((TaxField)localObject1).getMaxLengthValue() == 15) {
              this.error = 16171;
            }
            if (((TaxField)localObject1).getMaxLengthValue() == 9) {
              if ("DED05".equalsIgnoreCase(((TaxField)localObject1).getFieldname())) {
                this.error = 16176;
              } else {
                this.error = 16169;
              }
            }
            if (((TaxField)localObject1).getMaxLengthValue() == 6) {
              this.error = 16168;
            }
            if (this.error == 0) {
              this.error = 16143;
            }
          }
          if ((((String)localObject2).length() < ((TaxField)localObject1).getMinLengthValue()) && (((String)localObject2).length() > 0))
          {
            if (((TaxField)localObject1).getMinLengthValue() == 15) {
              this.error = 16171;
            }
            if (((TaxField)localObject1).getMinLengthValue() == 9) {
              if ("DED05".equalsIgnoreCase(((TaxField)localObject1).getFieldname())) {
                this.error = 16176;
              } else {
                this.error = 16169;
              }
            }
            if (this.error == 0) {
              this.error = 16142;
            }
          }
        }
        else if ("DT".equalsIgnoreCase(((TaxField)localObject1).getDataType()))
        {
          try
          {
            DateTime localDateTime = new DateTime((String)localObject2, this.locale, this.datetype);
            localObject2 = new DateTime(localDateTime.getTime(), this.locale, ((TaxField)localObject1).getMaxLengthValue() == 8 ? "yyyyMMdd" : "yyMMdd").toString();
            if (((String)localObject2).length() > ((TaxField)localObject1).getMaxLengthValue()) {
              this.error = 16143;
            }
            if (((String)localObject2).length() < ((TaxField)localObject1).getMinLengthValue()) {
              this.error = 16142;
            }
          }
          catch (Throwable localThrowable1)
          {
            this.error = 16118;
          }
        }
        else if ("ID".equalsIgnoreCase(((TaxField)localObject1).getDataType()))
        {
          if (((String)localObject2).length() > ((TaxField)localObject1).getMaxLengthValue())
          {
            if (((TaxField)localObject1).getMaxLengthValue() == 5) {
              this.error = 16167;
            }
            if (this.error == 0) {
              this.error = 16143;
            }
          }
          if ((((String)localObject2).length() < ((TaxField)localObject1).getMinLengthValue()) && (((String)localObject2).length() > 0))
          {
            if (((TaxField)localObject1).getMinLengthValue() == 5) {
              this.error = 16167;
            }
            if (this.error == 0) {
              this.error = 16142;
            }
          }
          if ((this.error == 0) && (((String)localObject2).trim().length() > 0) && (((TaxField)localObject1).getOptions() != null))
          {
            if (localArrayList.contains(((String)localObject2).trim())) {
              this.error = 16162;
            }
            if (((TaxField)localObject1).getFieldname().startsWith("TXP")) {
              localArrayList.add(((String)localObject2).trim());
            }
          }
        }
        else if ("N2".equalsIgnoreCase(((TaxField)localObject1).getDataType()))
        {
          try
          {
            Currency localCurrency = new Currency((String)localObject2, this.locale);
            localObject2 = localCurrency.removeFormatting(localCurrency.toString());
            int i = ((String)localObject2).length();
            this.fieldValues.put(((TaxField)localObject1).getFieldname(), localObject2);
            localObject2 = ((String)localObject2).substring(0, i - 3) + ((String)localObject2).substring(i - 2);
            if (((TaxField)localObject1).getLeadingZerosValue()) {
              while (((String)localObject2).length() < ((TaxField)localObject1).getMaxLengthValue()) {
                localObject2 = "0" + (String)localObject2;
              }
            }
            if (((String)localObject2).length() > ((TaxField)localObject1).getMaxLengthValue())
            {
              if (((TaxField)localObject1).getMaxLengthValue() == 10) {
                this.error = 16170;
              }
              if (this.error == 0) {
                this.error = 16125;
              }
            }
            if ((((String)localObject2).length() < ((TaxField)localObject1).getMinLengthValue()) && (((String)localObject2).length() > 0))
            {
              if (((TaxField)localObject1).getMinLengthValue() == 10) {
                this.error = 16170;
              }
              if (this.error == 0) {
                this.error = 16124;
              }
            }
          }
          catch (Throwable localThrowable2)
          {
            this.error = 16117;
          }
        }
        if (this.error != 0) {
          this.errorMap.put(((TaxField)localObject1).getFieldname(), "" + this.error);
        }
        this.validatedValues.put(((TaxField)localObject1).getFieldname(), localObject2);
      }
    }
    Object localObject1 = new HashMap();
    ((HashMap)localObject1).putAll(this.fieldValues);
    this.fieldValues.putAll(this.validatedValues);
    if (this.currentAddenda != null) {
      this.currentAddenda.setPmtRelatedInfo(createTaxAddendaString());
    }
    if (this.currentEntry != null)
    {
      this.currentEntry.setAmount(getTaxTotalAmountString());
      if ((this.currentEntry.getPrenote().equalsIgnoreCase("true")) && (getTaxTotalAmount() > 0L)) {
        this.error = 16149;
      }
    }
    if ((this.error == 0) && (this.errorMap.size() > 0))
    {
      localObject2 = this.errorMap.keySet().iterator();
      while (((Iterator)localObject2).hasNext())
      {
        String str = (String)((Iterator)localObject2).next();
        if (this.errorMap.get(str) != null)
        {
          Integer localInteger = new Integer((String)this.errorMap.get(str));
          this.error = localInteger.intValue();
          break;
        }
      }
    }
    this.fieldValues.putAll((Map)localObject1);
    return this.error;
  }
  
  protected boolean createNewEntry(HttpSession paramHttpSession)
  {
    this.currentTaxForm = null;
    boolean bool = super.createNewEntry(paramHttpSession);
    if (this.currentEntry != null) {
      this.currentEntry.setAmountIsDebit(false);
    }
    return bool;
  }
  
  protected void setInitialFieldValuesFromTaxForm()
  {
    this.errorMap.clear();
    this.fieldValues.clear();
    this.validatedValues.clear();
    if (this.currentTaxForm == null) {
      return;
    }
    Iterator localIterator = this.currentTaxForm.getFields().iterator();
    while (localIterator.hasNext())
    {
      TaxField localTaxField = (TaxField)localIterator.next();
      String str1 = localTaxField.getFieldname();
      String str2 = localTaxField.getValue();
      str2 = jdMethod_for(localTaxField, str2);
      if (str2 == null) {
        str2 = "";
      }
      this.fieldValues.put(str1, str2);
    }
    setInitialPayeeValuesFromTaxForm();
  }
  
  protected void setInitialPayeeValuesFromTaxForm()
  {
    if (this.currentTaxForm == null) {
      return;
    }
    ACHPayee localACHPayee = this.currentEntry.getAchPayee();
    if ((localACHPayee.getName() == null) || (localACHPayee.getName().length() == 0)) {
      localACHPayee.setName(this.currentTaxForm.getIndividualName());
    }
    if ((localACHPayee.getName() == null) || (localACHPayee.getName().length() == 0)) {
      localACHPayee.setName(this.currentTaxForm.getBankName());
    }
    if (localACHPayee.getName() == null) {
      localACHPayee.setName("");
    }
    if (localACHPayee.getName().length() > 22) {
      localACHPayee.setName(localACHPayee.getName().substring(0, 22));
    }
    if ((localACHPayee.getUserAccountNumber() == null) || (localACHPayee.getUserAccountNumber().length() == 0)) {
      localACHPayee.setUserAccountNumber(this.currentTaxForm.getIdentificationNumber());
    }
    if ((localACHPayee.getAccountNumber() == null) || (localACHPayee.getAccountNumber().length() == 0)) {
      localACHPayee.setAccountNumber(this.currentTaxForm.getBankAccountNumber());
    }
    if ((localACHPayee.getAccountType() == null) || (localACHPayee.getAccountType().length() == 0)) {
      localACHPayee.setAccountType(this.currentTaxForm.getBankAccountType());
    }
    if ((localACHPayee.getRoutingNumber() == null) || (localACHPayee.getRoutingNumber().length() == 0)) {
      localACHPayee.setRoutingNumber(this.currentTaxForm.getBankRoutingNumber());
    }
  }
  
  private String jdMethod_for(TaxField paramTaxField, String paramString)
  {
    if ("DT".equalsIgnoreCase(paramTaxField.getDataType()))
    {
      try
      {
        DateTime localDateTime = new DateTime(paramString, this.locale, paramTaxField.getMaxLengthValue() == 8 ? "yyyyMMdd" : "yyMMdd");
        paramString = new DateTime(localDateTime.getTime(), this.locale, this.datetype).toString();
      }
      catch (Throwable localThrowable) {}
    }
    else if (("N2".equalsIgnoreCase(paramTaxField.getDataType())) && (paramString != null))
    {
      while ((paramString.length() > 1) && ("0".equals(paramString.substring(0, 1)))) {
        paramString = paramString.substring(1);
      }
      while (paramString.length() <= 2) {
        paramString = "0" + paramString;
      }
      int i = paramString.length();
      if (i >= 2) {
        paramString = paramString.substring(0, i - 2) + "." + paramString.substring(i - 2);
      }
    }
    return paramString;
  }
  
  public String getTaxDepositAccountID()
  {
    TaxFormBank localTaxFormBank = new TaxFormBank();
    localTaxFormBank.setBankAccountNumber(this.currentEntry.getAchPayee().getAccountNumber());
    localTaxFormBank.setBankAccountType(this.currentEntry.getAchPayee().getAccountType());
    localTaxFormBank.setBankName(this.currentEntry.getAchPayee().getName());
    localTaxFormBank.setBankRoutingNumber(this.currentEntry.getAchPayee().getRoutingNumber());
    return localTaxFormBank.getID();
  }
  
  public String getUserModifiedDepositAccount()
  {
    if ((this.currentTaxForm == null) || (this.currentEntry == null)) {
      return "false";
    }
    Iterator localIterator = this.currentTaxForm.getDepositAccounts().iterator();
    boolean bool = false;
    while (localIterator.hasNext())
    {
      TaxFormBank localTaxFormBank = (TaxFormBank)localIterator.next();
      if (!localTaxFormBank.isEmpty())
      {
        if (localTaxFormBank.getID().equalsIgnoreCase(getTaxDepositAccountID())) {
          return "false";
        }
        bool = true;
      }
    }
    return "" + bool;
  }
  
  public String getTaxRoutingNumber()
  {
    if (this.currentEntry == null) {
      return null;
    }
    return this.currentEntry.getAchPayee().getRoutingNumber();
  }
  
  public void setTaxRoutingNumber(String paramString)
  {
    if (this.currentEntry == null) {
      return;
    }
    this.currentEntry.getAchPayee().setRoutingNumber(paramString);
  }
  
  public String getTaxAccountNumber()
  {
    if (this.currentEntry == null) {
      return null;
    }
    return this.currentEntry.getAchPayee().getAccountNumber();
  }
  
  public void setTaxAccountNumber(String paramString)
  {
    if (this.currentEntry == null) {
      return;
    }
    this.currentEntry.getAchPayee().setAccountNumber(paramString);
  }
  
  public String getTaxAccountType()
  {
    if (this.currentEntry == null) {
      return null;
    }
    return this.currentEntry.getAchPayee().getAccountType();
  }
  
  public void setTaxAccountType(String paramString)
  {
    if (this.currentEntry == null) {
      return;
    }
    this.currentEntry.getAchPayee().setAccountType(paramString);
  }
  
  public String getTaxReceivingCompanyName()
  {
    if (this.currentEntry == null) {
      return null;
    }
    return this.currentEntry.getAchPayee().getName();
  }
  
  public void setTaxReceivingCompanyName(String paramString)
  {
    if (this.currentEntry == null) {
      return;
    }
    this.currentEntry.getAchPayee().setName(paramString);
  }
  
  public String getTaxIdentificationNumber()
  {
    if (this.currentEntry == null) {
      return null;
    }
    return this.currentEntry.getAchPayee().getUserAccountNumber();
  }
  
  public void setTaxIdentificationNumber(String paramString)
  {
    if (this.currentEntry == null) {
      return;
    }
    this.currentEntry.getAchPayee().setUserAccountNumber(paramString);
  }
  
  public String getTaxType()
  {
    return this.currentTaxForm.getType();
  }
  
  public void setTaxType(String paramString)
  {
    this.currentTaxForm.setType(paramString);
  }
  
  public String getTaxState()
  {
    return this.currentTaxForm.getState();
  }
  
  public void setTaxState(String paramString)
  {
    this.currentTaxForm.setState(paramString);
  }
  
  public String getTaxID()
  {
    return this.currentTaxForm.getID();
  }
  
  public void setTaxID(String paramString)
  {
    this.currentTaxForm.setID(paramString);
  }
  
  public String getPrenote()
  {
    return this.currentEntry.getPrenote();
  }
  
  public void setPrenote(String paramString)
  {
    this.currentEntry.setPrenote(paramString);
  }
  
  public ArrayList getTaxFields()
  {
    return this.currentTaxForm.getFields();
  }
  
  public String getTXP01()
  {
    return (String)this.fieldValues.get("TXP01");
  }
  
  public void setTXP01(String paramString)
  {
    this.fieldValues.put("TXP01", paramString);
  }
  
  public String getTXP02()
  {
    return (String)this.fieldValues.get("TXP02");
  }
  
  public void setTXP02(String paramString)
  {
    this.fieldValues.put("TXP02", paramString);
  }
  
  public String getTXP03()
  {
    return (String)this.fieldValues.get("TXP03");
  }
  
  public void setTXP03(String paramString)
  {
    this.fieldValues.put("TXP03", paramString);
  }
  
  public String getTXP04()
  {
    return (String)this.fieldValues.get("TXP04");
  }
  
  public void setTXP04(String paramString)
  {
    this.fieldValues.put("TXP04", paramString);
  }
  
  public String getTXP05()
  {
    return (String)this.fieldValues.get("TXP05");
  }
  
  public void setTXP05(String paramString)
  {
    this.fieldValues.put("TXP05", paramString);
  }
  
  public String getTXP06()
  {
    return (String)this.fieldValues.get("TXP06");
  }
  
  public void setTXP06(String paramString)
  {
    this.fieldValues.put("TXP06", paramString);
  }
  
  public String getTXP07()
  {
    return (String)this.fieldValues.get("TXP07");
  }
  
  public void setTXP07(String paramString)
  {
    this.fieldValues.put("TXP07", paramString);
  }
  
  public String getTXP08()
  {
    return (String)this.fieldValues.get("TXP08");
  }
  
  public void setTXP08(String paramString)
  {
    this.fieldValues.put("TXP08", paramString);
  }
  
  public String getTXP09()
  {
    return (String)this.fieldValues.get("TXP09");
  }
  
  public void setTXP09(String paramString)
  {
    this.fieldValues.put("TXP09", paramString);
  }
  
  public String getTXP10()
  {
    return (String)this.fieldValues.get("TXP10");
  }
  
  public void setTXP10(String paramString)
  {
    this.fieldValues.put("TXP10", paramString);
  }
  
  public String getDED01()
  {
    return (String)this.fieldValues.get("DED01");
  }
  
  public void setDED01(String paramString)
  {
    this.fieldValues.put("DED01", paramString);
  }
  
  public String getDED02()
  {
    return (String)this.fieldValues.get("DED02");
  }
  
  public void setDED02(String paramString)
  {
    this.fieldValues.put("DED02", paramString);
  }
  
  public String getDED03()
  {
    return (String)this.fieldValues.get("DED03");
  }
  
  public void setDED03(String paramString)
  {
    this.fieldValues.put("DED03", paramString);
  }
  
  public String getDED04()
  {
    return (String)this.fieldValues.get("DED04");
  }
  
  public void setDED04(String paramString)
  {
    this.fieldValues.put("DED04", paramString);
  }
  
  public String getDED05()
  {
    return (String)this.fieldValues.get("DED05");
  }
  
  public void setDED05(String paramString)
  {
    this.fieldValues.put("DED05", paramString);
  }
  
  public String getDED06()
  {
    return (String)this.fieldValues.get("DED06");
  }
  
  public void setDED06(String paramString)
  {
    this.fieldValues.put("DED06", paramString);
  }
  
  public String getDED07()
  {
    return (String)this.fieldValues.get("DED07");
  }
  
  public void setDED07(String paramString)
  {
    this.fieldValues.put("DED07", paramString);
  }
  
  public String getDED08()
  {
    return (String)this.fieldValues.get("DED08");
  }
  
  public void setDED08(String paramString)
  {
    this.fieldValues.put("DED08", paramString);
  }
  
  public String getDED09()
  {
    return (String)this.fieldValues.get("DED09");
  }
  
  public void setDED09(String paramString)
  {
    this.fieldValues.put("DED09", paramString);
  }
  
  public String getTaxAddendaString()
  {
    if (this.currentAddenda != null) {
      return this.currentAddenda.getPmtRelatedInfo();
    }
    return "";
  }
  
  public String createTaxAddendaString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    if (this.currentTaxForm.getAddendaFormat().equals("TXP"))
    {
      localStringBuffer.append("TXP*");
      localStringBuffer.append(getTXP01());
      localStringBuffer.append("*");
      localStringBuffer.append(getTXP02());
      localStringBuffer.append("*");
      localStringBuffer.append(getTXP03());
      localStringBuffer.append("*");
      if ((getTXP04() == null) || (getTXP04().length() == 0))
      {
        if (this.currentTaxForm.getTypeValue() == 1) {
          localStringBuffer.append(getTXP02());
        } else {
          localStringBuffer.append("T");
        }
      }
      else {
        localStringBuffer.append(getTXP04());
      }
      localStringBuffer.append("*");
      if (getTXP05() != null) {
        localStringBuffer.append(getTXP05());
      }
      localStringBuffer.append("*");
      TaxField localTaxField1 = this.currentTaxForm.getFieldByName("TXP06");
      TaxField localTaxField2 = this.currentTaxForm.getFieldByName("TXP07");
      String str;
      if ((localTaxField1 != null) && ("ID".equals(localTaxField1.getDataType())) && (localTaxField2 != null) && ("N2".equals(localTaxField2.getDataType())))
      {
        if ((getTXP06() != null) && (getTXP06().length() != 0))
        {
          localStringBuffer.append(getTXP06());
          localStringBuffer.append("*");
          str = getTXP07();
          if ((str == null) || (str.length() == 0)) {
            str = "0";
          }
          localStringBuffer.append(str);
          localStringBuffer.append("*");
          i = 1;
        }
        else
        {
          localStringBuffer.append("*");
          localStringBuffer.append("*");
        }
      }
      else
      {
        if (getTXP06() != null) {
          localStringBuffer.append(getTXP06());
        }
        localStringBuffer.append("*");
        if (getTXP07() != null) {
          localStringBuffer.append(getTXP07());
        }
        localStringBuffer.append("*");
      }
      localTaxField1 = this.currentTaxForm.getFieldByName("TXP08");
      localTaxField2 = this.currentTaxForm.getFieldByName("TXP09");
      if ((localTaxField1 != null) && ("ID".equals(localTaxField1.getDataType())) && (localTaxField2 != null) && ("N2".equals(localTaxField2.getDataType())))
      {
        if ((getTXP08() != null) && (getTXP08().length() != 0) && (i != 0))
        {
          localStringBuffer.append(getTXP08());
          localStringBuffer.append("*");
          str = getTXP09();
          if ((str == null) || (str.length() == 0)) {
            str = "0";
          }
          localStringBuffer.append(str);
          localStringBuffer.append("*");
        }
        else
        {
          localStringBuffer.append("*");
          localStringBuffer.append("*");
        }
      }
      else
      {
        if (getTXP08() != null) {
          localStringBuffer.append(getTXP08());
        }
        localStringBuffer.append("*");
        if (getTXP09() != null) {
          localStringBuffer.append(getTXP09());
        }
        localStringBuffer.append("*");
      }
      if ((this.currentTaxForm.getTypeValue() != 1) && (getTXP10() != null)) {
        localStringBuffer.append(getTXP10());
      }
      while (localStringBuffer.charAt(localStringBuffer.length() - 1) == '*') {
        localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
      }
      localStringBuffer.append("\\");
    }
    else if (this.currentTaxForm.getAddendaFormat().equals("DED"))
    {
      localStringBuffer.append("DED*");
      localStringBuffer.append(getDED01());
      localStringBuffer.append("*");
      localStringBuffer.append(getDED02());
      localStringBuffer.append("*");
      localStringBuffer.append(getDED03());
      localStringBuffer.append("*");
      localStringBuffer.append(getDED04());
      localStringBuffer.append("*");
      localStringBuffer.append(getDED05());
      localStringBuffer.append("*");
      localStringBuffer.append(getDED06());
      localStringBuffer.append("*");
      if (getDED07() != null) {
        localStringBuffer.append(getDED07());
      }
      localStringBuffer.append("*");
      if (getDED08() != null) {
        localStringBuffer.append(getDED08());
      }
      localStringBuffer.append("*");
      if (getDED09() != null) {
        localStringBuffer.append(getDED09());
      }
      while (localStringBuffer.charAt(localStringBuffer.length() - 1) == '*') {
        localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
      }
      localStringBuffer.append("\\");
    }
    return localStringBuffer.toString();
  }
  
  public void setCurrentField(String paramString)
  {
    this.currentField = paramString;
  }
  
  public String getFieldError()
  {
    if ((this.currentField == null) || (this.errorMap.size() == 0)) {
      return "";
    }
    if (this.errorMap.get(this.currentField) != null) {
      return (String)this.errorMap.get(this.currentField);
    }
    return "";
  }
  
  public void setTaxValuesFromAddenda(String paramString)
  {
    if ((paramString == null) || (paramString.length() < 4)) {
      return;
    }
    String str1 = paramString.substring(0, 3);
    if ((paramString.startsWith("TXP*")) || (paramString.startsWith("DED*")))
    {
      paramString = paramString.substring(4);
      int i = paramString.indexOf("*");
      int j = paramString.indexOf("\\");
      int k = 0;
      int m = 1;
      int n = 0;
      this.fieldValues.clear();
      while (i != -1)
      {
        String str2 = paramString.substring(k, i);
        String str3 = str1 + (m < 10 ? "0" : "") + m;
        TaxField localTaxField = this.currentTaxForm.getFieldByName(str3);
        if (localTaxField != null)
        {
          String str4 = jdMethod_for(localTaxField, str2);
          if (str4.length() > 0) {
            this.fieldValues.put(str3, str4);
          }
        }
        if (n != 0) {
          break;
        }
        k = i + 1;
        i = paramString.indexOf("*", k);
        if (i < 0)
        {
          n = 1;
          i = j;
        }
        m++;
      }
    }
  }
  
  public long getTaxTotalAmount()
  {
    long l = 0L;
    TaxField localTaxField = null;
    if (this.currentTaxForm.getAddendaFormat().equals("TXP"))
    {
      Iterator localIterator = this.currentTaxForm.getFields().iterator();
      while (localIterator.hasNext())
      {
        this.error = 0;
        localTaxField = (TaxField)localIterator.next();
        if ((localTaxField.getEditableValue()) && ("N2".equalsIgnoreCase(localTaxField.getDataType()))) {
          l += new Long(j((String)this.fieldValues.get(localTaxField.getFieldname()))).longValue();
        }
      }
    }
    else if (this.currentTaxForm.getAddendaFormat().equals("DED"))
    {
      l = new Long(j(getDED04())).longValue();
    }
    return l;
  }
  
  private static String j(String paramString)
  {
    String str = "";
    if (paramString == null) {
      paramString = "0";
    }
    for (int i = 0; i < paramString.length(); i++)
    {
      char c = paramString.charAt(i);
      if (Character.isDigit(c)) {
        str = str + c;
      }
    }
    return str;
  }
  
  public String getTaxTotalAmountString()
  {
    long l = getTaxTotalAmount();
    String str = "" + l;
    int i = str.length();
    if (i == 1)
    {
      str = "0" + str;
      i = 2;
    }
    if (i <= 2) {
      str = "0." + str;
    } else {
      str = str.substring(0, i - 2) + "." + str.substring(i - 2);
    }
    return str;
  }
  
  public void setEntryTaxFormID(String paramString)
  {
    this.parameters.put(EntryTaxFormID, paramString);
  }
  
  public void setDepositAccountID(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.parameters.put(DepositAccountID, paramString);
    }
  }
  
  public TaxForm getDefaultTaxForm()
  {
    if (this.defaultTaxForm == null) {
      return getTaxForm();
    }
    return this.defaultTaxForm;
  }
  
  public TaxForm getCurrentTaxForm()
  {
    return this.currentTaxForm;
  }
  
  public void setDefaultTaxFormID(String paramString)
  {
    this.parameters.put(DefaultTaxFormID, paramString);
    this.useDefaultTaxForm = true;
  }
  
  public void setUseDefaultTaxForm(String paramString)
  {
    this.useDefaultTaxForm = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.ModifyACHTaxPayment
 * JD-Core Version:    0.7.0.1
 */