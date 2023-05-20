package com.ffusion.beans.ach;

import com.ffusion.util.Qsort;
import com.ffusion.util.XMLHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class StateTaxFormsCache
{
  public static final String STATE = "STATE";
  public static ArrayList commonFields;
  public static HashMap commonByState;
  public static HashMap formsByState;
  protected String compDiscData;
  protected String compEntryDisc;
  protected String comment = "";
  
  public StateTaxFormsCache()
  {
    commonFields = new ArrayList();
    commonByState = new HashMap();
    formsByState = new HashMap();
  }
  
  public TaxForm getTaxForm(String paramString)
  {
    Iterator localIterator = formsByState.keySet().iterator();
    for (int i = 0; localIterator.hasNext(); i++)
    {
      String str = (String)localIterator.next();
      ArrayList localArrayList = (ArrayList)formsByState.get(str);
      if (localArrayList != null)
      {
        int j = localArrayList.size();
        for (int k = 0; k < j; k++)
        {
          TaxForm localTaxForm = (TaxForm)localArrayList.get(k);
          if (localTaxForm.getID().equalsIgnoreCase(paramString)) {
            return createCompleteTaxForm(localTaxForm);
          }
        }
      }
    }
    return null;
  }
  
  protected TaxForm createCompleteTaxForm(TaxForm paramTaxForm)
  {
    TaxForm localTaxForm1 = paramTaxForm.shallowCopy();
    String str = paramTaxForm.getAddendaFormat();
    int i = commonFields.size();
    ArrayList localArrayList = paramTaxForm.getFields();
    if (!localTaxForm1.getNoDefaultFieldsValue()) {
      for (int j = 0; j < i; j++)
      {
        TaxField localTaxField = (TaxField)commonFields.get(j);
        if (localTaxField.getFieldname().startsWith(str)) {
          localTaxForm1.addField(localTaxField);
        }
      }
    }
    TaxForm localTaxForm2 = (TaxForm)commonByState.get(paramTaxForm.getState());
    localArrayList = localTaxForm2.getFields();
    i = localArrayList.size();
    if (!localTaxForm1.getNoDefaultFieldsValue()) {
      for (k = 0; k < i; k++)
      {
        localObject = (TaxField)localArrayList.get(k);
        if (((TaxField)localObject).getFieldname().startsWith(str)) {
          localTaxForm1.addOrMergeField((TaxField)localObject);
        }
      }
    }
    localArrayList = paramTaxForm.getFields();
    i = localArrayList.size();
    for (int k = 0; k < i; k++)
    {
      localObject = (TaxField)localArrayList.get(k);
      if ((((TaxField)localObject).getFieldname().startsWith(str)) && (localTaxForm1.addOrMergeField((TaxField)localObject))) {
        localTaxForm1.getFieldByName(((TaxField)localObject).getFieldname()).setIsCustom(true);
      }
    }
    Qsort.sortSortables(localTaxForm1.getFields(), "", 1);
    k = 0;
    Object localObject = null;
    int m;
    do
    {
      m = 0;
      TaxFormBank localTaxFormBank = new TaxFormBank();
      if (k < localTaxForm2.getDepositAccounts().size())
      {
        localObject = (TaxFormBank)localTaxForm2.getDepositAccounts().get(k);
        localTaxFormBank.addOrMerge((TaxFormBank)localObject);
        m = 1;
      }
      if (k < paramTaxForm.getDepositAccounts().size())
      {
        localObject = (TaxFormBank)paramTaxForm.getDepositAccounts().get(k);
        localTaxFormBank.addOrMerge((TaxFormBank)localObject);
        m = 1;
      }
      if (m != 0) {
        localTaxForm1.getDepositAccounts().add(localTaxFormBank);
      }
      k++;
    } while (((paramTaxForm.getDepositAccounts().size() == 0) || (k != paramTaxForm.getDepositAccounts().size())) && (m != 0));
    if (localTaxForm1.getDepositAccounts().size() >= 1) {
      localTaxForm1.getDepositAccount().set((TaxFormBank)localTaxForm1.getDepositAccounts().get(0));
    }
    if ((localTaxForm1.getIndividualName() == null) || (localTaxForm1.getIndividualName().length() == 0)) {
      localTaxForm1.setIndividualName(localTaxForm2.getIndividualName());
    }
    if ((localTaxForm1.getCompDiscData() == null) || (localTaxForm1.getCompDiscData().length() == 0)) {
      localTaxForm1.setCompDiscData(localTaxForm2.getCompDiscData());
    }
    if ((localTaxForm1.getCompEntryDisc() == null) || (localTaxForm1.getCompEntryDisc().length() == 0)) {
      localTaxForm1.setCompEntryDisc(localTaxForm2.getCompEntryDisc());
    }
    if ((localTaxForm1.getComment() == null) || (localTaxForm1.getComment().length() == 0)) {
      localTaxForm1.setComment(localTaxForm2.getComment());
    }
    if ((localTaxForm1.getCompDiscData() == null) || (localTaxForm1.getCompDiscData().length() == 0)) {
      localTaxForm1.setCompDiscData(getCompDiscData());
    }
    if ((localTaxForm1.getCompEntryDisc() == null) || (localTaxForm1.getCompEntryDisc().length() == 0)) {
      localTaxForm1.setCompEntryDisc(getCompEntryDisc());
    }
    if ((localTaxForm1.getComment() == null) || (localTaxForm1.getComment().length() == 0)) {
      localTaxForm1.setComment(getComment());
    }
    return localTaxForm1;
  }
  
  public static String[] getTaxFormNamesByState(String paramString)
  {
    String[] arrayOfString = null;
    ArrayList localArrayList = (ArrayList)formsByState.get(paramString);
    if (localArrayList != null)
    {
      int i = localArrayList.size();
      arrayOfString = new String[i];
      for (int j = 0; j < i; j++)
      {
        TaxForm localTaxForm = (TaxForm)localArrayList.get(j);
        arrayOfString[j] = localTaxForm.getName();
      }
    }
    return arrayOfString;
  }
  
  public static TaxForms getTaxFormsForState(String paramString)
  {
    TaxForms localTaxForms = null;
    ArrayList localArrayList = (ArrayList)formsByState.get(paramString);
    if (localArrayList != null)
    {
      int i = localArrayList.size();
      localTaxForms = new TaxForms();
      for (int j = 0; j < i; j++)
      {
        TaxForm localTaxForm1 = (TaxForm)localArrayList.get(j);
        TaxForm localTaxForm2 = localTaxForm1.shallowCopy();
        localTaxForms.add(localTaxForm2);
      }
    }
    return localTaxForms;
  }
  
  public static TaxForms getTaxForms()
  {
    TaxForms localTaxForms = null;
    localTaxForms = new TaxForms();
    Iterator localIterator = formsByState.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      ArrayList localArrayList = (ArrayList)formsByState.get(str);
      if (localArrayList != null)
      {
        int i = localArrayList.size();
        for (int j = 0; j < i; j++)
        {
          TaxForm localTaxForm1 = (TaxForm)localArrayList.get(j);
          TaxForm localTaxForm2 = localTaxForm1.shallowCopy();
          localTaxForms.add(localTaxForm2);
        }
      }
    }
    return localTaxForms;
  }
  
  public static String[] getStatesWithTaxForms()
  {
    String[] arrayOfString = null;
    int i = formsByState.size();
    arrayOfString = new String[i];
    Iterator localIterator = formsByState.keySet().iterator();
    for (int j = 0; localIterator.hasNext(); j++) {
      arrayOfString[j] = ((String)localIterator.next());
    }
    return arrayOfString;
  }
  
  public static HashMap getStateNamesWithTaxForms()
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = formsByState.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      ArrayList localArrayList = (ArrayList)formsByState.get(str);
      if ((localArrayList != null) && (localArrayList.size() > 0))
      {
        TaxForm localTaxForm = (TaxForm)localArrayList.get(0);
        localHashMap.put(localTaxForm.getStateName(), localTaxForm.getState());
      }
    }
    return localHashMap;
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
  
  public String getComment()
  {
    return this.comment;
  }
  
  public void setComment(String paramString)
  {
    this.comment = paramString;
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    TaxForm jdField_int = null;
    
    public a() {}
    
    public void startElement(String paramString)
    {
      Object localObject;
      if (paramString.equalsIgnoreCase("FORM"))
      {
        localObject = new TaxForm();
        ((TaxForm)localObject).setTypeValue(2);
        ((TaxForm)localObject).setState(this.jdField_int.getState());
        ((TaxForm)localObject).setStateName(this.jdField_int.getStateName());
        ArrayList localArrayList = (ArrayList)StateTaxFormsCache.formsByState.get(((TaxForm)localObject).getState());
        if (localArrayList == null)
        {
          localArrayList = new ArrayList();
          StateTaxFormsCache.formsByState.put(((TaxForm)localObject).getState(), localArrayList);
        }
        localArrayList.add(localObject);
        ((TaxForm)localObject).continueXMLParsing(getHandler());
      }
      else if (paramString.equalsIgnoreCase("FIELD"))
      {
        localObject = new TaxField();
        if (this.jdField_int != null) {
          this.jdField_int._addNewField((TaxField)localObject);
        } else {
          StateTaxFormsCache.commonFields.add(localObject);
        }
        ((TaxField)localObject).continueXMLParsing(getHandler());
      }
      else if (paramString.equalsIgnoreCase("STATE"))
      {
        if (this.jdField_int != null) {
          StateTaxFormsCache.commonByState.put(this.jdField_int.getState(), this.jdField_int);
        }
        this.jdField_int = new TaxForm();
      }
      else if ((paramString.equalsIgnoreCase("DEPOSITACCOUNT")) && (this.jdField_int != null))
      {
        this.jdField_int.bank = new TaxFormBank();
        this.jdField_int.depositAccounts.add(this.jdField_int.bank);
        this.jdField_int.bank.continueXMLParsing(getHandler());
      }
    }
    
    public void endElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("STATE"))
      {
        StateTaxFormsCache.commonByState.put(this.jdField_int.getState(), this.jdField_int);
        this.jdField_int = null;
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str1 = new String(paramArrayOfChar, paramInt1, paramInt2);
      str1 = str1.trim();
      String str2 = getElement();
      if (this.jdField_int != null)
      {
        if (str2.equalsIgnoreCase("ABBR")) {
          this.jdField_int.setState(str1);
        } else if (str2.equalsIgnoreCase("INDIVIDUALNAME")) {
          this.jdField_int.setIndividualName(str1);
        } else if (str2.equalsIgnoreCase("COMPANYDISCRETIONARYDATA")) {
          this.jdField_int.setCompDiscData(str1);
        } else if ((str2.equalsIgnoreCase("COMPANYENTRYDISCRIPTION")) || (str2.equalsIgnoreCase("COMPANYENTRYDESCRIPTION"))) {
          this.jdField_int.setCompEntryDisc(str1);
        } else if (str2.equalsIgnoreCase("COMMENT")) {
          this.jdField_int.setComment(str1);
        }
      }
      else if (str2.equalsIgnoreCase("COMPANYDISCRETIONARYDATA")) {
        StateTaxFormsCache.this.setCompDiscData(str1);
      } else if ((str2.equalsIgnoreCase("COMPANYENTRYDISCRIPTION")) || (str2.equalsIgnoreCase("COMPANYENTRYDESCRIPTION"))) {
        StateTaxFormsCache.this.setCompEntryDisc(str1);
      } else if (str2.equalsIgnoreCase("COMMENT")) {
        StateTaxFormsCache.this.setComment(str1);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.StateTaxFormsCache
 * JD-Core Version:    0.7.0.1
 */