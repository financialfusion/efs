package com.ffusion.beans.messages;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;

public class MessageCount
  extends ExtendABean
{
  private String aXm = "";
  private String aXn = "";
  private int aXl = 0;
  
  public void setName(String paramString)
  {
    if (paramString != null) {
      this.aXm = paramString;
    } else {
      this.aXm = "";
    }
  }
  
  public String getName()
  {
    return this.aXm;
  }
  
  public void setCount(String paramString)
  {
    if (paramString != null) {
      this.aXn = paramString;
    } else {
      this.aXn = "0";
    }
  }
  
  public void setCount(int paramInt)
  {
    this.aXn = String.valueOf(paramInt);
  }
  
  public String getCount()
  {
    return this.aXn;
  }
  
  public int getCountValue()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(this.aXn);
    }
    catch (NumberFormatException localNumberFormatException) {}
    return i;
  }
  
  public void setIdentifier(int paramInt)
  {
    this.aXl = paramInt;
  }
  
  public int getIdentifier()
  {
    return this.aXl;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "MESSAGECOUNT");
    XMLHandler.appendTag(localStringBuffer, "ITEM_NAME", this.aXm);
    XMLHandler.appendTag(localStringBuffer, "ITEM_COUNT", this.aXn);
    XMLHandler.appendTag(localStringBuffer, "ITEM_IDENTIFIER", this.aXl);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "MESSAGECOUNT");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.MessageCount
 * JD-Core Version:    0.7.0.1
 */