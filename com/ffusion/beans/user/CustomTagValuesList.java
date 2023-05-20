package com.ffusion.beans.user;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;

public class CustomTagValuesList
  extends FilteredList
  implements IMSDefines
{
  public CustomTagValues add()
  {
    CustomTagValues localCustomTagValues = new CustomTagValues();
    add(localCustomTagValues);
    return localCustomTagValues;
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
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
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("TAG"))
      {
        CustomTagValues localCustomTagValues = CustomTagValuesList.this.add();
        localCustomTagValues.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.user.CustomTagValuesList
 * JD-Core Version:    0.7.0.1
 */