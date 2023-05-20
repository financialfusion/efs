package com.ffusion.ims21.util;

import java.util.Vector;

public class UrlDoc
{
  Vector a = new Vector(2);
  XmlReader jdField_if = new XmlReader();
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < this.a.size(); i++)
    {
      a locala = (a)this.a.elementAt(i);
      localStringBuffer.append(" i=").append(i).append(" Cmd=").append(locala.toString());
    }
    return localStringBuffer.toString();
  }
  
  public void loadDoc(String paramString1, String paramString2)
    throws Exception
  {
    String str1 = paramString1 + "?BID=" + paramString2;
    if (this.jdField_if.connect2Url(str1, true))
    {
      String str2;
      while ((str2 = this.jdField_if.getTag()) != null)
      {
        str2 = str2.substring(0, str2.length() - 1);
        if (str2.equalsIgnoreCase("<COMMAND>"))
        {
          a locala = new a();
          if (locala.jdMethod_for()) {
            this.a.addElement(locala);
          }
        }
      }
    }
  }
  
  public String getCommandUrl(String paramString)
  {
    String str = null;
    for (int i = 0; i < this.a.size(); i++)
    {
      a locala = (a)this.a.elementAt(i);
      if (paramString.equalsIgnoreCase(locala.jdMethod_do()))
      {
        str = locala.jdField_if();
        break;
      }
    }
    return str;
  }
  
  public class a
  {
    String jdField_if = "";
    String a = "";
    
    public a() {}
    
    String jdField_if()
    {
      return this.a;
    }
    
    String jdMethod_do()
    {
      return this.jdField_if;
    }
    
    public boolean jdMethod_for()
    {
      String str;
      while ((str = UrlDoc.this.jdField_if.getTag()) != null)
      {
        str = str.substring(0, str.length() - 1);
        if (str.equalsIgnoreCase("</COMMAND>")) {
          return true;
        }
        KeyValue localKeyValue = new KeyValue(str);
        if (localKeyValue.getKey().equalsIgnoreCase("ID")) {
          this.jdField_if = localKeyValue.getValue();
        } else if (localKeyValue.getKey().equalsIgnoreCase("URL")) {
          this.a = localKeyValue.getValue();
        }
      }
      return false;
    }
    
    public String a()
    {
      return "Url=" + jdField_if() + " id=" + jdMethod_do();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.ims21.util.UrlDoc
 * JD-Core Version:    0.7.0.1
 */