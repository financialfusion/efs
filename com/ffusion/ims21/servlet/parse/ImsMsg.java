package com.ffusion.ims21.servlet.parse;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.user.CustomTag;
import com.ffusion.beans.user.CustomTags;
import com.ffusion.ims21.util.AcctKey;
import com.ffusion.ims21.util.DemoKey;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.StringTokenizer;

public class ImsMsg
{
  String a = "";
  String e = "";
  String jdField_if = "";
  int d = 0;
  String[] jdField_byte = null;
  String[] jdField_try = null;
  boolean jdField_else = false;
  DateTime jdField_int = null;
  DemoKey jdField_case = new DemoKey();
  int c = 1;
  int b = 0;
  CustomTags jdField_char = new CustomTags(Locale.getDefault());
  Hashtable jdField_void = a();
  int[] jdField_goto = null;
  int[] jdField_new = null;
  int[] jdField_null = null;
  int[] jdField_for = null;
  AcctSumRule jdField_do = null;
  AcctBalRule jdField_long = null;
  
  public void setCustomTags(CustomTags paramCustomTags)
  {
    this.jdField_char = paramCustomTags;
  }
  
  public CustomTags getCustomTags()
  {
    return this.jdField_char;
  }
  
  public boolean hasCustomTags()
  {
    return this.jdField_char.size() > 0;
  }
  
  public boolean compareTags(HashMap paramHashMap)
  {
    boolean bool = false;
    if ((this.jdField_char == null) || (this.jdField_char.size() == 0)) {
      bool = true;
    } else if (paramHashMap == null) {
      bool = false;
    } else {
      for (int i = 0; i < this.jdField_char.size(); i++)
      {
        String str1 = ((CustomTag)this.jdField_char.get(i)).getTagName();
        String str2 = ((CustomTag)this.jdField_char.get(i)).getValue();
        String str3 = (String)paramHashMap.get(str1.toUpperCase());
        if (str2.equals(str3))
        {
          bool = true;
          break;
        }
      }
    }
    return bool;
  }
  
  public void setDeliveries(int paramInt)
  {
    this.b = paramInt;
  }
  
  public int getDeliveries()
  {
    return this.b;
  }
  
  public void setPriority(int paramInt)
  {
    this.c = paramInt;
  }
  
  public int getPriority()
  {
    return this.c;
  }
  
  public void setTargeting(long paramLong)
  {
    this.jdField_case.setValue(paramLong);
  }
  
  public long getTargeting()
  {
    return this.jdField_case.getValue();
  }
  
  public DemoKey getTargetingKey()
  {
    return this.jdField_case;
  }
  
  public String getMessageId()
  {
    return this.a;
  }
  
  public String getUrl()
  {
    return this.e;
  }
  
  public String getActionUrl()
  {
    return this.jdField_if;
  }
  
  public int getDisplayTime()
  {
    return this.d;
  }
  
  public void setMessageId(String paramString)
  {
    this.a = paramString;
  }
  
  public void setUrl(String paramString)
  {
    this.e = paramString;
  }
  
  public void setActionUrl(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public void setDisplayTime(int paramInt)
  {
    this.d = paramInt;
  }
  
  public void setDestinations(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ";");
    int i = localStringTokenizer.countTokens();
    if (i == 0)
    {
      this.jdField_byte = null;
    }
    else
    {
      this.jdField_byte = new String[i];
      int j = 0;
      while (localStringTokenizer.hasMoreTokens())
      {
        String str = localStringTokenizer.nextToken();
        this.jdField_byte[(j++)] = str;
      }
    }
  }
  
  public void setStates(String[] paramArrayOfString)
  {
    this.jdField_try = paramArrayOfString;
  }
  
  public String[] getStates()
  {
    return this.jdField_try;
  }
  
  public boolean getBirthDayMessage()
  {
    return this.jdField_else;
  }
  
  public void setBirthDayMessage(boolean paramBoolean)
  {
    this.jdField_else = paramBoolean;
  }
  
  public DateTime getStartDate()
  {
    return this.jdField_int;
  }
  
  public void setStartDate(DateTime paramDateTime)
  {
    this.jdField_int = paramDateTime;
  }
  
  public boolean isValidDestination(String[] paramArrayOfString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
      return (this.jdField_byte == null) || (this.jdField_byte.length == 0);
    }
    if ((this.jdField_byte == null) || (this.jdField_byte.length == 0)) {
      return false;
    }
    for (int i = 0; i < paramArrayOfString.length; i++) {
      for (int j = 0; j < this.jdField_byte.length; j++) {
        if (paramArrayOfString[i].equals(this.jdField_byte[j])) {
          return true;
        }
      }
    }
    return false;
  }
  
  private static Hashtable a()
  {
    return new Hashtable(353, 0.75F);
  }
  
  public void reset()
  {
    this.jdField_void = a();
  }
  
  public boolean checkAccountRules(int[] paramArrayOfInt)
  {
    int i;
    int j;
    int k;
    AcctKey localAcctKey2;
    if (this.jdField_goto != null)
    {
      if (paramArrayOfInt == null) {
        return false;
      }
      for (i = 0; i < this.jdField_goto.length; i++)
      {
        j = 0;
        for (k = 0; k < paramArrayOfInt.length; k++)
        {
          localAcctKey2 = new AcctKey(paramArrayOfInt[k]);
          if (this.jdField_goto[i] == localAcctKey2.getAcctId()) {
            j = 1;
          }
        }
        if (j == 0) {
          return false;
        }
      }
    }
    if (this.jdField_new != null)
    {
      if (paramArrayOfInt == null) {
        return false;
      }
      i = 0;
      for (j = 0; (j < this.jdField_new.length) && (i == 0); j++) {
        for (k = 0; k < paramArrayOfInt.length; k++)
        {
          localAcctKey2 = new AcctKey(paramArrayOfInt[k]);
          if (this.jdField_new[j] == localAcctKey2.getAcctId()) {
            i = 1;
          }
        }
      }
      if (i == 0) {
        return false;
      }
    }
    if ((this.jdField_null != null) && (paramArrayOfInt != null))
    {
      i = 0;
      for (j = 0; j < this.jdField_null.length; j++)
      {
        i = 0;
        for (k = 0; k < paramArrayOfInt.length; k++)
        {
          localAcctKey2 = new AcctKey(paramArrayOfInt[k]);
          if (this.jdField_null[j] == localAcctKey2.getAcctId())
          {
            i = 1;
            break;
          }
        }
        if (i == 0) {
          break;
        }
      }
      if (i == 1) {
        return false;
      }
    }
    if ((this.jdField_for != null) && (paramArrayOfInt != null)) {
      for (i = 0; i < this.jdField_for.length; i++) {
        for (j = 0; j < paramArrayOfInt.length; j++)
        {
          AcctKey localAcctKey1 = new AcctKey(paramArrayOfInt[j]);
          if (this.jdField_for[i] == localAcctKey1.getAcctId()) {
            return false;
          }
        }
      }
    }
    if (this.jdField_do != null)
    {
      if (paramArrayOfInt == null) {
        return false;
      }
      if (!this.jdField_do.validateAccts(paramArrayOfInt)) {
        return false;
      }
    }
    if (this.jdField_long != null)
    {
      if (paramArrayOfInt == null) {
        return false;
      }
      if (!this.jdField_long.validateAccts(paramArrayOfInt)) {
        return false;
      }
    }
    return true;
  }
  
  public void setAllAcct(int[] paramArrayOfInt)
  {
    this.jdField_goto = paramArrayOfInt;
  }
  
  public void setOneOfAcct(int[] paramArrayOfInt)
  {
    this.jdField_new = paramArrayOfInt;
  }
  
  public void setNoAcctOneOf(int[] paramArrayOfInt)
  {
    this.jdField_null = paramArrayOfInt;
  }
  
  public void setNoAcct(int[] paramArrayOfInt)
  {
    this.jdField_for = paramArrayOfInt;
  }
  
  public void setAcctSumRule(AcctSumRule paramAcctSumRule)
  {
    this.jdField_do = paramAcctSumRule;
  }
  
  public void setAcctBalRule(AcctBalRule paramAcctBalRule)
  {
    this.jdField_long = paramAcctBalRule;
  }
  
  public boolean checkState(DemoKey paramDemoKey)
  {
    if (this.jdField_try == null) {
      return true;
    }
    if ((this.jdField_try != null) && (paramDemoKey.getState() == null)) {
      return false;
    }
    for (int i = 0; i < this.jdField_try.length; i++) {
      if (this.jdField_try[i].equals(paramDemoKey.getState())) {
        return true;
      }
    }
    return false;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("MessageId= ").append(this.a).append("\n").append("MessageUrl= ").append(this.e).append("\n");
    localStringBuffer.append("ActionUrl= ").append(this.jdField_if).append("\n").append("DisplayTime= ").append(this.d).append("\n");
    localStringBuffer.append("BirthDayMessage = ").append(this.jdField_else).append("\n").append("Target= ").append(this.jdField_case).append("\n");
    localStringBuffer.append("Priority= ").append(this.c).append("\n").append("Deliveries= ").append(this.b).append("\n");
    int i;
    if (this.jdField_byte != null)
    {
      localStringBuffer.append("\nDestintations");
      for (i = 0; i < this.jdField_byte.length; i++) {
        localStringBuffer.append(this.jdField_byte[i]).append(";");
      }
    }
    if (this.jdField_goto != null)
    {
      localStringBuffer.append("\nHas all accounts");
      for (i = 0; i < this.jdField_goto.length; i++) {
        localStringBuffer.append(this.jdField_goto[i]);
      }
    }
    if (this.jdField_do != null)
    {
      localStringBuffer.append("\nAccount Sumation");
      localStringBuffer.append(this.jdField_do);
    }
    if (this.jdField_long != null)
    {
      localStringBuffer.append("\nAccount Balances");
      localStringBuffer.append(this.jdField_long);
    }
    if (this.jdField_try != null)
    {
      localStringBuffer.append("\nStates");
      for (i = 0; i < this.jdField_try.length; i++) {
        localStringBuffer.append(" ").append(this.jdField_try[i]);
      }
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.ims21.servlet.parse.ImsMsg
 * JD-Core Version:    0.7.0.1
 */