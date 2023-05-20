package com.ffusion.beans.ach;

import com.ffusion.util.Qsort;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaxFormCache
{
  public static final String TAXFORMS = "TAXFORMS";
  public static final String STATETAX = "STATETAX";
  public static final String FEDERALTAX = "FEDERALTAX";
  public static final String CHILDSUPPORT = "CHILDSUPPORT";
  public static StateTaxFormsCache stateForms;
  public static FederalTaxFormsCache federalForms;
  public static ChildSupportTaxFormsCache childSupportForms;
  
  public TaxFormCache()
  {
    stateForms = new StateTaxFormsCache();
    federalForms = new FederalTaxFormsCache();
    childSupportForms = new ChildSupportTaxFormsCache();
  }
  
  public static String[] getStatesWithTaxForms()
  {
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;
    if (stateForms != null) {
      arrayOfString1 = StateTaxFormsCache.getStatesWithTaxForms();
    }
    if (childSupportForms != null) {
      arrayOfString2 = ChildSupportTaxFormsCache.getStatesWithTaxForms();
    }
    if ((arrayOfString2 == null) && (arrayOfString1 == null)) {
      return null;
    }
    if (arrayOfString2 == null) {
      return arrayOfString1;
    }
    if (arrayOfString1 == null) {
      return arrayOfString2;
    }
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < arrayOfString1.length; i++) {
      localArrayList.add(arrayOfString1[i]);
    }
    for (i = 0; i < arrayOfString2.length; i++) {
      if (!localArrayList.contains(arrayOfString2[i])) {
        localArrayList.add(arrayOfString2[i]);
      }
    }
    String[] arrayOfString3 = (String[])localArrayList.toArray(new String[0]);
    return arrayOfString3;
  }
  
  public static HashMap getStateNamesWithTaxForms()
  {
    HashMap localHashMap1 = null;
    HashMap localHashMap2 = null;
    if (stateForms != null) {
      localHashMap1 = StateTaxFormsCache.getStateNamesWithTaxForms();
    }
    if (childSupportForms != null) {
      localHashMap2 = ChildSupportTaxFormsCache.getStateNamesWithTaxForms();
    }
    if ((localHashMap1 == null) && (localHashMap2 == null)) {
      return null;
    }
    if (localHashMap2 == null) {
      return localHashMap1;
    }
    if (localHashMap1 == null) {
      return localHashMap2;
    }
    localHashMap1.putAll(localHashMap2);
    return localHashMap1;
  }
  
  public static String[] getStatesWithTaxForms(int paramInt)
  {
    if (paramInt == 2) {
      return StateTaxFormsCache.getStatesWithTaxForms();
    }
    if (paramInt == 4) {
      return ChildSupportTaxFormsCache.getStatesWithTaxForms();
    }
    return getStatesWithTaxForms();
  }
  
  public static HashMap getStateNamesWithTaxForms(int paramInt)
  {
    if (paramInt == 2) {
      return StateTaxFormsCache.getStateNamesWithTaxForms();
    }
    if (paramInt == 4) {
      return ChildSupportTaxFormsCache.getStateNamesWithTaxForms();
    }
    return getStateNamesWithTaxForms();
  }
  
  public static TaxForms getTaxForms(int paramInt, String paramString)
  {
    if (paramInt == 2) {
      return StateTaxFormsCache.getTaxFormsForState(paramString);
    }
    if (paramInt == 4) {
      return ChildSupportTaxFormsCache.getTaxFormsForState(paramString);
    }
    if (paramInt == 1) {
      return FederalTaxFormsCache.getTaxForms();
    }
    return null;
  }
  
  public static TaxForm getTaxForm(String paramString)
  {
    TaxForm localTaxForm = federalForms.getTaxForm(paramString);
    if (localTaxForm == null) {
      localTaxForm = stateForms.getTaxForm(paramString);
    }
    if (localTaxForm == null) {
      localTaxForm = childSupportForms.getTaxForm(paramString);
    }
    return localTaxForm;
  }
  
  public void loadFromXMLFile(String paramString)
  {
    TaxFormCache localTaxFormCache = new TaxFormCache();
    String str1 = "";
    PrintWriter localPrintWriter = null;
    BufferedWriter localBufferedWriter = null;
    FileWriter localFileWriter = null;
    try
    {
      InputStream localInputStream = ResourceUtil.getResourceAsStream(this, paramString);
      Object localObject1;
      if (localInputStream != null)
      {
        str1 = Strings.streamToString(localInputStream);
        localObject1 = new XMLHandler();
        ((XMLHandler)localObject1).start(new a(), str1);
      }
      if (DebugLog.getLogger().isLoggable(Level.FINEST))
      {
        localObject1 = new TaxForms();
        Iterator localIterator = ((TaxForms)localObject1).iterator();
        HashMap localHashMap1 = new HashMap();
        HashMap localHashMap2 = new HashMap();
        HashMap localHashMap3 = new HashMap();
        HashMap localHashMap4 = new HashMap();
        HashMap localHashMap5 = new HashMap();
        ArrayList localArrayList1 = new ArrayList();
        int i = 0;
        StringBuffer localStringBuffer = new StringBuffer();
        Object localObject2;
        String str2;
        Object localObject3;
        while (localIterator.hasNext())
        {
          if (localFileWriter == null)
          {
            localFileWriter = new FileWriter("TaxForms.out", false);
            localBufferedWriter = new BufferedWriter(localFileWriter);
            localPrintWriter = new PrintWriter(localBufferedWriter, true);
          }
          localObject2 = (TaxForm)localIterator.next();
          localObject2 = getTaxForm(((TaxForm)localObject2).getID());
          str2 = ((TaxForm)localObject2).getID();
          localObject3 = ((TaxForm)localObject2).getState();
          String str3 = ((TaxForm)localObject2).getName();
          String str4 = ((TaxForm)localObject2).getBankRoutingNumber();
          String str5 = ((TaxForm)localObject2).getBankAccountNumber();
          if (localObject3 == null) {
            localObject3 = "";
          }
          localPrintWriter.print(((TaxForm)localObject2).toString());
          String str6;
          if (localHashMap1.get((String)localObject3 + "_" + str3) != null)
          {
            str6 = "Form:" + str2 + " could be a duplicate of form " + localHashMap1.get(new StringBuffer().append((String)localObject3).append("_").append(str3).toString()) + " - State + name is duplicate: " + (String)localObject3 + "," + str3;
            if (localHashMap5.get(str6) == null)
            {
              localStringBuffer.append('\n').append(str6);
              localHashMap5.put(str6, "x");
            }
          }
          else
          {
            localHashMap1.put((String)localObject3 + "_" + str3, str2);
          }
          if (localHashMap1.get(str2) != null)
          {
            str6 = "Form:" + str2 + " is a duplicate ID in the file";
            if (localHashMap5.get(str6) == null)
            {
              localStringBuffer.append('\n').append(str6);
              localHashMap5.put(str6, "x");
            }
          }
          else
          {
            localHashMap1.put(str2, str2);
          }
          if ((str4 != null) && (localHashMap3.get(str4) != null) && (!localHashMap3.get(str4).equals((String)localObject3 + "_" + ((TaxForm)localObject2).getBankName())))
          {
            str6 = "Routing Number '" + str4 + "' is used for two different states: " + (String)localObject3 + "_" + ((TaxForm)localObject2).getBankName() + " : " + localHashMap3.get(str4);
            if (localHashMap5.get(str6) == null)
            {
              localStringBuffer.append('\n').append(str6);
              localHashMap5.put(str6, "x");
            }
          }
          else
          {
            localHashMap3.put(str4, (String)localObject3 + "_" + ((TaxForm)localObject2).getBankName());
          }
          if ((str5 != null) && (localHashMap3.get(str5) != null))
          {
            str6 = "Account Number '" + str5 + "' is also a routing number, could be wrong";
            if (localHashMap5.get(str6) == null)
            {
              localStringBuffer.append('\n').append(str6);
              localHashMap5.put(str6, "x");
            }
          }
          if ((str5 != null) && (localHashMap4.get(str5) != null) && (!localHashMap4.get(str5).equals((String)localObject3 + "_" + ((TaxForm)localObject2).getBankName())))
          {
            str6 = "Account Number '" + str5 + "' is used for two different states: " + (String)localObject3 + "_" + ((TaxForm)localObject2).getBankName() + " : " + localHashMap4.get(str5);
            if (localHashMap5.get(str6) == null)
            {
              localStringBuffer.append('\n').append(str6);
              localHashMap5.put(str6, "x");
            }
          }
          else
          {
            localHashMap4.put(str5, (String)localObject3 + "_" + ((TaxForm)localObject2).getBankName());
          }
          if (((TaxForm)localObject2).getTypeValue() == 1)
          {
            str6 = ((TaxForm)localObject2).getFieldValue("TXP02").substring(0, 4);
            ArrayList localArrayList2 = (ArrayList)localHashMap2.get(str6);
            String str7 = ((TaxForm)localObject2).getFieldValue("TXP02").substring(4);
            if (localArrayList2 == null)
            {
              localArrayList2 = new ArrayList();
              localArrayList1.add(str6);
            }
            localArrayList2.add(str7 + "(" + ((TaxForm)localObject2).getID() + ")");
            localHashMap2.put(str6, localArrayList2);
          }
          localPrintWriter.println(((TaxForm)localObject2).validate() + "\n\n");
          try
          {
            int j = Integer.parseInt(str2);
            if (j > i) {
              i = j;
            }
          }
          catch (Exception localException1) {}
        }
        if (localPrintWriter != null)
        {
          localPrintWriter.println("\n" + localStringBuffer.toString() + "\n");
          if (localHashMap2.size() > 0)
          {
            Qsort.sortStrings(localArrayList1, 1);
            localObject2 = localArrayList1.iterator();
            while (((Iterator)localObject2).hasNext())
            {
              str2 = (String)((Iterator)localObject2).next();
              localObject3 = (ArrayList)localHashMap2.get(str2);
              Qsort.sortStrings((ArrayList)localObject3, 1);
              localPrintWriter.println("Federal Form TaxTypePrefix: " + str2 + "  Suffixes: " + ((ArrayList)localObject3).toString());
            }
          }
          if (i != 0) {
            localPrintWriter.println("TAXForms: MaxID = " + i + ", Next Avail = " + (i + 1));
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    finally
    {
      try
      {
        if (localFileWriter != null) {
          localFileWriter.close();
        }
        if (localBufferedWriter != null) {
          localBufferedWriter.close();
        }
        if (localPrintWriter != null) {
          localPrintWriter.close();
        }
        localFileWriter = null;
        localBufferedWriter = null;
        localPrintWriter = null;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("STATETAX")) {
        TaxFormCache.stateForms.continueXMLParsing(getHandler());
      } else if (paramString.equalsIgnoreCase("FEDERALTAX")) {
        TaxFormCache.federalForms.continueXMLParsing(getHandler());
      } else if (paramString.equalsIgnoreCase("CHILDSUPPORT")) {
        TaxFormCache.childSupportForms.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.TaxFormCache
 * JD-Core Version:    0.7.0.1
 */