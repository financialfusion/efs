package com.ffusion.beans.messages;

import com.ffusion.util.FilteredList;
import java.util.Locale;

public class EntitledMsgTypes
  extends FilteredList
{
  public EntitledMsgTypes() {}
  
  public EntitledMsgTypes(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.EntitledMsgTypes
 * JD-Core Version:    0.7.0.1
 */