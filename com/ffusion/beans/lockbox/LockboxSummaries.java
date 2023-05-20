package com.ffusion.beans.lockbox;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class LockboxSummaries
  extends FilteredList
  implements Localeable, XMLable
{
  public static final String STR_OUTER_TAG = "LOCKBOX_SUMMARIES";
  
  public LockboxSummaries() {}
  
  public LockboxSummaries(Locale paramLocale)
  {
    super(paramLocale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "LOCKBOX_SUMMARIES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((LockboxSummary)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "LOCKBOX_SUMMARIES");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString) {}
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  public LockboxSummary add()
  {
    LockboxSummary localLockboxSummary = new LockboxSummary(this.locale);
    super.add(localLockboxSummary);
    return localLockboxSummary;
  }
  
  public LockboxSummary create()
  {
    return add();
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof LockboxSummaries))) {
      return false;
    }
    LockboxSummaries localLockboxSummaries = (LockboxSummaries)paramObject;
    if (super.size() != localLockboxSummaries.size()) {
      return false;
    }
    if (this.locale != localLockboxSummaries.locale)
    {
      if ((this.locale == null) || (localLockboxSummaries.locale == null)) {
        return false;
      }
      if (!this.locale.equals(localLockboxSummaries.locale)) {
        return false;
      }
    }
    return super.containsAll(localLockboxSummaries);
  }
  
  private class a
    extends XMLHandler
  {
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("LOCKBOX_SUMMARY"))
      {
        LockboxSummary localLockboxSummary = new LockboxSummary(LockboxSummaries.this.locale);
        localLockboxSummary.continueXMLParsing(getHandler());
        LockboxSummaries.this.add(localLockboxSummary);
      }
    }
    
    a(LockboxSummaries.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.lockbox.LockboxSummaries
 * JD-Core Version:    0.7.0.1
 */