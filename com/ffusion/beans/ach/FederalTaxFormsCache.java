package com.ffusion.beans.ach;

import com.ffusion.util.XMLHandler;
import java.util.ArrayList;
import java.util.HashMap;

public class FederalTaxFormsCache
{
  public static ArrayList commonFields;
  public static HashMap common;
  public static ArrayList forms;
  protected TaxFormBank bank;
  protected ArrayList depositAccounts = null;
  protected String individualName;
  protected String compDiscData;
  protected String compEntryDisc;
  protected String currentSuffix;
  protected HashMap typeSuffixes = new HashMap();
  
  public FederalTaxFormsCache()
  {
    commonFields = new ArrayList();
    common = new HashMap();
    forms = new ArrayList();
    this.depositAccounts = new ArrayList();
    this.bank = new TaxFormBank();
  }
  
  public static TaxForms getTaxForms()
  {
    TaxForms localTaxForms = null;
    int i = forms.size();
    localTaxForms = new TaxForms();
    for (int j = 0; j < i; j++)
    {
      TaxForm localTaxForm1 = (TaxForm)forms.get(j);
      TaxForm localTaxForm2 = localTaxForm1.shallowCopy();
      localTaxForms.add(localTaxForm2);
    }
    return localTaxForms;
  }
  
  public TaxForm getTaxForm(String paramString)
  {
    int i = forms.size();
    for (int j = 0; j < i; j++)
    {
      TaxForm localTaxForm = (TaxForm)forms.get(j);
      if (localTaxForm.getID().equalsIgnoreCase(paramString)) {
        return createCompleteTaxForm(localTaxForm);
      }
    }
    return null;
  }
  
  protected TaxForm createCompleteTaxForm(TaxForm paramTaxForm)
  {
    TaxForm localTaxForm = paramTaxForm.shallowCopy();
    int i = commonFields.size();
    ArrayList localArrayList = paramTaxForm.getFields();
    if (!localTaxForm.getNoDefaultFieldsValue()) {
      for (j = 0; j < i; j++) {
        localTaxForm.addField((TaxField)commonFields.get(j));
      }
    }
    i = localArrayList.size();
    for (int j = 0; j < i; j++)
    {
      localObject = (TaxField)localArrayList.get(j);
      if (localTaxForm.addOrMergeField((TaxField)localObject)) {
        localTaxForm.getFieldByName(((TaxField)localObject).getFieldname()).setIsCustom(true);
      }
    }
    j = 0;
    Object localObject = null;
    int k;
    do
    {
      k = 0;
      TaxFormBank localTaxFormBank = new TaxFormBank();
      if (j < this.depositAccounts.size())
      {
        localObject = (TaxFormBank)this.depositAccounts.get(j);
        localTaxFormBank.addOrMerge((TaxFormBank)localObject);
        k = 1;
      }
      if (j < paramTaxForm.getDepositAccounts().size())
      {
        localObject = (TaxFormBank)paramTaxForm.getDepositAccounts().get(j);
        localTaxFormBank.addOrMerge((TaxFormBank)localObject);
        k = 1;
      }
      if (k != 0) {
        localTaxForm.getDepositAccounts().add(localTaxFormBank);
      }
      j++;
    } while (((paramTaxForm.getDepositAccounts().size() == 0) || (j != paramTaxForm.getDepositAccounts().size())) && (k != 0));
    if (localTaxForm.getDepositAccounts().size() >= 1) {
      localTaxForm.getDepositAccount().set((TaxFormBank)localTaxForm.getDepositAccounts().get(0));
    }
    if ((localTaxForm.getIndividualName() == null) || (localTaxForm.getIndividualName().length() == 0)) {
      localTaxForm.setIndividualName(getIndividualName());
    }
    if ((localTaxForm.getCompDiscData() == null) || (localTaxForm.getCompDiscData().length() == 0)) {
      localTaxForm.setCompDiscData(getCompDiscData());
    }
    if ((localTaxForm.getCompEntryDisc() == null) || (localTaxForm.getCompEntryDisc().length() == 0)) {
      localTaxForm.setCompEntryDisc(getCompEntryDisc());
    }
    return localTaxForm;
  }
  
  public String getIndividualName()
  {
    return this.individualName;
  }
  
  public void setIndividualName(String paramString)
  {
    this.individualName = paramString;
  }
  
  public String getCompDiscData()
  {
    return this.compDiscData;
  }
  
  public void setCompDiscData(String paramString)
  {
    this.compDiscData = paramString;
  }
  
  public String getCompEntryDisc()
  {
    return this.compEntryDisc;
  }
  
  public void setCompEntryDisc(String paramString)
  {
    this.compEntryDisc = paramString;
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
    {
      Object localObject;
      if (paramString.equalsIgnoreCase("FORM"))
      {
        localObject = new TaxForm();
        ((TaxForm)localObject).setTypeValue(1);
        FederalTaxFormsCache.forms.add(localObject);
        ((TaxForm)localObject).continueXMLParsing(getHandler());
      }
      else if (paramString.equalsIgnoreCase("FIELD"))
      {
        localObject = new TaxField();
        FederalTaxFormsCache.commonFields.add(localObject);
        ((TaxField)localObject).continueXMLParsing(getHandler());
      }
      else if (paramString.equalsIgnoreCase("DEPOSITACCOUNT"))
      {
        FederalTaxFormsCache.this.bank = new TaxFormBank();
        FederalTaxFormsCache.this.depositAccounts.add(FederalTaxFormsCache.this.bank);
        FederalTaxFormsCache.this.bank.continueXMLParsing(getHandler());
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str1 = new String(paramArrayOfChar, paramInt1, paramInt2);
      str1 = str1.trim();
      String str2 = getElement();
      if (str2.equalsIgnoreCase("INDIVIDUALNAME")) {
        FederalTaxFormsCache.this.setIndividualName(str1);
      } else if (str2.equalsIgnoreCase("COMPANYDISCRETIONARYDATA")) {
        FederalTaxFormsCache.this.setCompDiscData(str1);
      } else if ((str2.equalsIgnoreCase("COMPANYENTRYDISCRIPTION")) || (str2.equalsIgnoreCase("COMPANYENTRYDESCRIPTION"))) {
        FederalTaxFormsCache.this.setCompEntryDisc(str1);
      } else if (str2.equalsIgnoreCase("suffix")) {
        FederalTaxFormsCache.this.currentSuffix = str1;
      } else if ((str2.equalsIgnoreCase("suffixType")) && (FederalTaxFormsCache.this.currentSuffix != null)) {
        FederalTaxFormsCache.this.typeSuffixes.put(FederalTaxFormsCache.this.currentSuffix + "Type", str1);
      } else if ((str2.equalsIgnoreCase("suffixDescription")) && (FederalTaxFormsCache.this.currentSuffix != null)) {
        FederalTaxFormsCache.this.typeSuffixes.put(FederalTaxFormsCache.this.currentSuffix + "Description", str1);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.FederalTaxFormsCache
 * JD-Core Version:    0.7.0.1
 */