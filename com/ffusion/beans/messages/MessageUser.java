package com.ffusion.beans.messages;

import com.ffusion.beans.Person;
import java.text.Collator;

public class MessageUser
  extends Person
{
  public static final String ID = "ID";
  public static final String Type = "Type";
  private String oA;
  private String oB;
  
  public String getUserID()
  {
    return this.oA;
  }
  
  public String getUserType()
  {
    return this.oB;
  }
  
  public void setUserID(String paramString)
  {
    this.oA = paramString;
  }
  
  public void setUserType(String paramString)
  {
    this.oB = paramString;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "ID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    MessageUser localMessageUser = (MessageUser)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("ID")) && (getUserID() != null) && (localMessageUser.getUserID() != null)) {
      i = localCollator.compare(getUserID(), localMessageUser.getUserID());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equalsIgnoreCase("ID")) && (getUserID() != null)) {
      return isFilterable(getUserID(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.MessageUser
 * JD-Core Version:    0.7.0.1
 */