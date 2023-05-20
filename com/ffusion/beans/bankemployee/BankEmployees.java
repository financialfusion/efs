package com.ffusion.beans.bankemployee;

import com.ffusion.beans.IdCollection;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;

public class BankEmployees
  extends FilteredList
  implements IdCollection
{
  public static final String BANKEMPLOYEELIST = "BANKEMPLOYEELIST";
  
  public BankEmployees() {}
  
  public BankEmployees(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public BankEmployee add()
  {
    BankEmployee localBankEmployee = new BankEmployee(this.locale);
    add(localBankEmployee);
    return localBankEmployee;
  }
  
  public BankEmployee create()
  {
    BankEmployee localBankEmployee = new BankEmployee(this.locale);
    return localBankEmployee;
  }
  
  public BankEmployee getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BankEmployee localBankEmployee = (BankEmployee)localIterator.next();
      if (localBankEmployee.getId().equals(paramString))
      {
        localObject = localBankEmployee;
        break;
      }
    }
    return localObject;
  }
  
  public Object getElementByID(String paramString)
  {
    return getByID(paramString);
  }
  
  public BankEmployee getByEmployeeID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BankEmployee localBankEmployee = (BankEmployee)localIterator.next();
      if (localBankEmployee.getEmployeeId().equals(paramString))
      {
        localObject = localBankEmployee;
        break;
      }
    }
    return localObject;
  }
  
  public BankEmployee getByUserName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BankEmployee localBankEmployee = (BankEmployee)localIterator.next();
      if (localBankEmployee.getUserName().equalsIgnoreCase(paramString))
      {
        localObject = localBankEmployee;
        break;
      }
    }
    return localObject;
  }
  
  public void removeByID(String paramString)
  {
    int i = 0;
    int j = -1;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BankEmployee localBankEmployee = (BankEmployee)localIterator.next();
      if (localBankEmployee.getId().equals(paramString))
      {
        j = i;
        break;
      }
      i++;
    }
    if (j != -1) {
      remove(j);
    }
  }
  
  public void setRemoveFromFilteredItems(String paramString)
  {
    StringTokenizer localStringTokenizer = null;
    if (this.filteredList != null)
    {
      Iterator localIterator = this.filteredList.iterator();
      while (localIterator.hasNext())
      {
        BankEmployee localBankEmployee = (BankEmployee)localIterator.next();
        String str1 = localBankEmployee.getId();
        int i = 0;
        localStringTokenizer = new StringTokenizer(paramString, ",");
        while (localStringTokenizer.hasMoreTokens())
        {
          String str2 = localStringTokenizer.nextToken();
          if (str2.equals(str1))
          {
            i = 1;
            break;
          }
        }
        if (i != 0) {
          localIterator.remove();
        }
      }
    }
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BANKEMPLOYEELIST");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((BankEmployee)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "BANKEMPLOYEELIST");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public void set(BankEmployees paramBankEmployees)
  {
    Iterator localIterator = paramBankEmployees.iterator();
    while (localIterator.hasNext())
    {
      BankEmployee localBankEmployee = (BankEmployee)localIterator.next();
      if (localBankEmployee != null) {
        add(localBankEmployee);
      }
    }
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("BANKEMPLOYEEINFO"))
      {
        BankEmployee localBankEmployee = BankEmployees.this.add();
        localBankEmployee.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bankemployee.BankEmployees
 * JD-Core Version:    0.7.0.1
 */