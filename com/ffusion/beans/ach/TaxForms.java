package com.ffusion.beans.ach;

import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class TaxForms
  extends FilteredList
{
  public void set(TaxForms paramTaxForms)
  {
    clear();
    if (paramTaxForms != null)
    {
      int i = paramTaxForms.size();
      for (int j = 0; j < i; j++) {
        add(paramTaxForms.get(j));
      }
    }
  }
  
  public TaxForm getByID(String paramString)
  {
    Object localObject = null;
    if (paramString != null)
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        TaxForm localTaxForm = (TaxForm)localIterator.next();
        if (paramString.equals(localTaxForm.getID()))
        {
          localObject = localTaxForm;
          break;
        }
      }
    }
    return localObject;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.TaxForms
 * JD-Core Version:    0.7.0.1
 */