package com.ffusion.beans;

import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.XMLHandler;
import java.text.Collator;
import java.util.Locale;

public class Person
  extends Contact
{
  private static final String BEAN_NAME = Person.class.getName();
  private String im;
  private String il;
  private String ik;
  
  public Person() {}
  
  public Person(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setFirstName(String paramString)
  {
    if (paramString != null) {
      paramString = paramString.trim();
    }
    this.im = paramString;
  }
  
  public String getFirstName()
  {
    return this.im;
  }
  
  public String getFirstNameLowerCase()
  {
    return this.im != null ? this.im.toLowerCase() : null;
  }
  
  public void setMiddleName(String paramString)
  {
    if (paramString != null) {
      paramString = paramString.trim();
    }
    this.il = paramString;
  }
  
  public String getMiddleName()
  {
    return this.il;
  }
  
  public String getMiddleNameLowerCase()
  {
    return this.il != null ? this.il.toLowerCase() : null;
  }
  
  public String getMiddleInitial()
  {
    if ((this.il == null) || (this.il.length() == 0)) {
      return "";
    }
    return this.il.substring(0, 1);
  }
  
  public void setLastName(String paramString)
  {
    if (paramString != null) {
      paramString = paramString.trim();
    }
    this.ik = paramString;
  }
  
  public String getLastName()
  {
    return this.ik;
  }
  
  public String getLastNameLowerCase()
  {
    return this.ik != null ? this.ik.toLowerCase() : null;
  }
  
  public String getName()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (getFirstName() != null) {
      localStringBuffer.append(getFirstName() + " ");
    }
    if (getLastName() != null) {
      localStringBuffer.append(getLastName());
    }
    return localStringBuffer.toString();
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("FIRST")) {
      setFirstName(paramString2);
    } else if (paramString1.equals("MIDDLE")) {
      setMiddleName(paramString2);
    } else if (paramString1.equals("LAST")) {
      setLastName(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public void set(Person paramPerson)
  {
    setFirstName(paramPerson.getFirstName());
    setLastName(paramPerson.getLastName());
    setMiddleName(paramPerson.getMiddleName());
    super.set(paramPerson);
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    Person localPerson = (Person)paramObject;
    int i;
    if (this == localPerson)
    {
      i = 0;
    }
    else
    {
      i = getLastName().toLowerCase().compareTo(localPerson.getLastName().toLowerCase());
      if (i == 0)
      {
        i = getFirstName().toLowerCase().compareTo(localPerson.getFirstName().toLowerCase());
        if (i == 0) {
          i = getMiddleName().toLowerCase().compareTo(localPerson.getMiddleName().toLowerCase());
        }
      }
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Person localPerson = (Person)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("FIRST")) && (getFirstName() != null) && (localPerson.getFirstName() != null)) {
      i = localCollator.compare(getFirstName().toLowerCase(this.locale), localPerson.getFirstName().toLowerCase(this.locale));
    } else if ((paramString.equals("MIDDLE")) && (getMiddleName() != null) && (localPerson.getMiddleName() != null)) {
      i = localCollator.compare(getMiddleName().toLowerCase(this.locale), localPerson.getMiddleName().toLowerCase(this.locale));
    } else if ((paramString.equals("LAST")) && (getLastName() != null) && (localPerson.getLastName() != null)) {
      i = localCollator.compare(getLastName().toLowerCase(this.locale), localPerson.getLastName().toLowerCase(this.locale));
    } else if ((paramString.equals("NAME")) && (getName() != null) && (localPerson.getName() != null)) {
      i = localCollator.compare(getName().toLowerCase(this.locale), localPerson.getName().toLowerCase(this.locale));
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("FIRST")) && (getFirstName() != null)) {
      return isFilterable(getFirstName(), paramString2, paramString3);
    }
    if ((paramString1.equals("MIDDLE")) && (getMiddleName() != null)) {
      return isFilterable(getMiddleName(), paramString2, paramString3);
    }
    if ((paramString1.equals("LAST")) && (getLastName() != null)) {
      return isFilterable(getLastName(), paramString2, paramString3);
    }
    if ((paramString1.equals("NAME")) && (getName() != null)) {
      return isFilterable(getName(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "PERSON");
    XMLHandler.appendBeginTag(localStringBuffer, "NAME");
    XMLHandler.appendTag(localStringBuffer, "FIRST", this.im);
    XMLHandler.appendTag(localStringBuffer, "MIDDLE", this.il);
    XMLHandler.appendTag(localStringBuffer, "LAST", this.ik);
    XMLHandler.appendEndTag(localStringBuffer, "NAME");
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "PERSON");
    return localStringBuffer.toString();
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, Person paramPerson, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "FIRST", paramPerson.im, this.im, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "MIDDLE", paramPerson.il, this.il, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "LAST", paramPerson.ik, this.ik, paramString);
    super.logChanges(paramHistoryTracker, paramPerson, paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logDelete(BEAN_NAME, "FIRST", this.im, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "MIDDLE", this.il, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "LAST", this.ik, paramString);
    super.logDeletion(paramHistoryTracker, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, Person paramPerson, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "FIRST", paramPerson.im, this.im, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "MIDDLE", paramPerson.il, this.il, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "LAST", paramPerson.ik, this.ik, paramILocalizable);
    super.logChanges(paramHistoryTracker, paramPerson, paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logDelete(BEAN_NAME, "FIRST", this.im, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "MIDDLE", this.il, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "LAST", this.ik, paramILocalizable);
    super.logDeletion(paramHistoryTracker, paramILocalizable);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.Person
 * JD-Core Version:    0.7.0.1
 */