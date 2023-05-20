package com.ffusion.util.beans.filemonitor;

import com.ffusion.util.FilteredList;
import java.util.Locale;

public class FMFileDescriptions
  extends FilteredList
{
  public FMFileDescriptions() {}
  
  public FMFileDescriptions(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public FMFileDescription create()
  {
    FMFileDescription localFMFileDescription = createNoAdd();
    super.add(localFMFileDescription);
    return localFMFileDescription;
  }
  
  public FMFileDescription createNoAdd()
  {
    FMFileDescription localFMFileDescription = new FMFileDescription();
    localFMFileDescription.setLocale(this.locale);
    return localFMFileDescription;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.filemonitor.FMFileDescriptions
 * JD-Core Version:    0.7.0.1
 */