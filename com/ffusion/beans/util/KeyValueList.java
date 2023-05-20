package com.ffusion.beans.util;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;

public class KeyValueList
  extends FilteredList
{
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    String jdField_int;
    
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str1 = new String(paramArrayOfChar, paramInt1, paramInt2);
      str1 = str1.trim();
      String str2 = getElement();
      if (str2.equalsIgnoreCase("KEY"))
      {
        this.jdField_int = str1;
      }
      else if (str2.equalsIgnoreCase("VALUE"))
      {
        KeyValueList.this.add(new KeyValue(this.jdField_int, str1));
        this.jdField_int = null;
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {
      if ((this.jdField_int != null) && (paramString.equalsIgnoreCase("VALUE")))
      {
        KeyValueList.this.add(new KeyValue(this.jdField_int, ""));
        this.jdField_int = null;
      }
      super.endElement(paramString);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.KeyValueList
 * JD-Core Version:    0.7.0.1
 */