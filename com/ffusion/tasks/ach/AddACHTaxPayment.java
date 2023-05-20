package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.TaxForm;
import com.ffusion.beans.business.Business;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.MapError;
import java.util.HashMap;
import javax.servlet.http.HttpSession;

public class AddACHTaxPayment
  extends ModifyACHTaxPayment
{
  public AddACHTaxPayment()
  {
    this.currentTaxForm = new TaxForm();
  }
  
  protected String initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    String str = this.successURL;
    setStandardEntryClassCode("CCD");
    setBatchFundsTransactionType(this);
    Object localObject1;
    Object localObject2;
    if ((this.batchID != null) && (this.batchID.length() > 0))
    {
      super.initProcess(paramHttpSession);
      setID(null);
      setTrackingID(null);
    }
    else
    {
      boolean bool;
      if ((this.templateID != null) && (this.templateID.length() > 0))
      {
        bool = initFromTemplate(paramHttpSession);
        if (!bool) {
          str = this.taskErrorURL;
        }
      }
      else
      {
        bool = true;
        localObject1 = (Business)paramHttpSession.getAttribute("Business");
        int i;
        if ((localObject1 != null) && (((Business)localObject1).getACHBatchTypeValue() != 0)) {
          i = ((Business)localObject1).getACHBatchTypeValue();
        }
        localObject2 = (ACHCompany)paramHttpSession.getAttribute("ACHCOMPANY");
        if ((localObject2 != null) && (((ACHCompany)localObject2).getACHBatchTypeValue() != 0)) {
          i = ((ACHCompany)localObject2).getACHBatchTypeValue();
        }
        setBatchType(i);
      }
    }
    try
    {
      HashMap localHashMap = new HashMap();
      localObject1 = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      if ((this.currentTaxForm != null) && (this.currentTaxForm.getID() != null))
      {
        localObject2 = ACH.getTaxForm((SecureUser)localObject1, this.currentTaxForm.getID(), localHashMap);
        if (localObject2 != null)
        {
          setTaxForm((TaxForm)localObject2);
          this.defaultTaxForm = ((TaxForm)localObject2);
          StringBuffer localStringBuffer = new StringBuffer();
          if (((TaxForm)localObject2).getTypeValue() == 1) {
            localStringBuffer.append(" Federal - ");
          } else if ((((TaxForm)localObject2).getTypeValue() == 2) || (((TaxForm)localObject2).getTypeValue() == 4)) {
            localStringBuffer.append(getTaxForm().getState()).append(" - ");
          } else if (((TaxForm)localObject2).getTypeValue() == 3) {
            localStringBuffer.append(" - Other - ");
          }
          localStringBuffer.append(((TaxForm)localObject2).getTaxFormDescription());
          setName(localStringBuffer.toString());
          if (!createNewEntry(paramHttpSession)) {
            str = this.taskErrorURL;
          }
        }
      }
      paramHttpSession.setAttribute("ACHEntries", getACHEntries());
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
      str = this.taskErrorURL;
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
        str = addTaxPayment(paramHttpSession);
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
  
  protected String addTaxPayment(HttpSession paramHttpSession)
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
      localACHBatch = ACH.addACHBatch(localSecureUser, localACHBatch, localHashMap);
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.AddACHTaxPayment
 * JD-Core Version:    0.7.0.1
 */