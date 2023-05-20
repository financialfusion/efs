package com.ffusion.ims21.servlet.parse;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.user.CustomTag;
import com.ffusion.beans.user.CustomTags;
import com.ffusion.ims21.util.DateCalc;
import com.ffusion.ims21.util.DemoKey;
import com.ffusion.ims21.util.KeyValue;
import com.ffusion.ims21.util.XmlReader;
import com.ffusion.util.logging.DebugLog;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

public class MessagePack
{
  PriorityVector jdField_int = new PriorityVector();
  XmlReader jdField_case = null;
  int jdField_for = 0;
  String jdField_new = null;
  String jdField_try = null;
  String jdField_do = null;
  int a = 0;
  String jdField_if = null;
  long jdField_byte = 0L;
  
  public String getYear()
  {
    return this.jdField_new;
  }
  
  public String getMonth()
  {
    return this.jdField_try;
  }
  
  public String getDay()
  {
    return this.jdField_do;
  }
  
  public String getBeginTime()
  {
    return this.jdField_if;
  }
  
  public int loadMessages(String paramString)
    throws Exception
  {
    DebugLog.log("MessagePack.loadMessages: filename=" + paramString);
    this.jdField_case = new XmlReader();
    Object localObject = null;
    if ((this.jdField_case != null) && (this.jdField_case.connect2File(paramString)))
    {
      String str = null;
      a();
      while ((str = this.jdField_case.getTag()) != null)
      {
        str = str.substring(0, str.length() - 1);
        if (str.equalsIgnoreCase("<IMSMSG>"))
        {
          ImsMsg localImsMsg = new ImsMsg();
          try
          {
            if (jdField_if(localImsMsg)) {
              this.jdField_int.add(localImsMsg);
            }
          }
          catch (Exception localException) {}
        }
      }
    }
    return this.jdField_int.size();
  }
  
  public int loadMessages(String paramString, boolean paramBoolean)
    throws Exception
  {
    DebugLog.log("MessagePack.loadMessages: url=" + paramString);
    this.jdField_case = new XmlReader();
    if ((this.jdField_case != null) && (this.jdField_case.connect2Url(paramString, false)))
    {
      a();
      String str;
      while ((str = this.jdField_case.getTag()) != null)
      {
        str = str.substring(0, str.length() - 1);
        if (str.equalsIgnoreCase("<IMSMSG>"))
        {
          ImsMsg localImsMsg = new ImsMsg();
          try
          {
            if (jdField_if(localImsMsg)) {
              this.jdField_int.add(localImsMsg);
            }
          }
          catch (Exception localException) {}
        }
      }
    }
    return this.jdField_int.size();
  }
  
  private void a()
  {
    Calendar localCalendar = Calendar.getInstance();
    this.jdField_new = String.valueOf(localCalendar.get(1));
    this.jdField_try = String.valueOf(localCalendar.get(2) + 1);
    this.jdField_do = String.valueOf(localCalendar.get(5));
    this.a = localCalendar.get(7);
    this.jdField_if = (String.valueOf(localCalendar.get(11)) + String.valueOf(localCalendar.get(12)) + String.valueOf(localCalendar.get(13)));
  }
  
  public Vector getMessageVector()
  {
    return this.jdField_int;
  }
  
  public ImsMsg getNextMessage()
  {
    int i = this.jdField_int.size();
    if (i == 0) {
      return null;
    }
    if (this.jdField_for >= i) {
      this.jdField_for = 0;
    }
    ImsMsg localImsMsg = (ImsMsg)this.jdField_int.elementAt(this.jdField_for);
    this.jdField_for += 1;
    return localImsMsg;
  }
  
  private boolean jdField_if(ImsMsg paramImsMsg)
    throws Exception
  {
    String str;
    while ((str = this.jdField_case.getTag()) != null)
    {
      str = str.substring(0, str.length() - 1);
      if (str.equalsIgnoreCase("</IMSMSG>")) {
        return true;
      }
      if (str.equalsIgnoreCase("<RULES[]>")) {
        jdField_do(paramImsMsg);
      }
      if (str.equalsIgnoreCase("<CUSTOMTAGS>")) {
        a(paramImsMsg);
      }
      KeyValue localKeyValue = new KeyValue(str);
      if (localKeyValue.getKey().equalsIgnoreCase("ID"))
      {
        paramImsMsg.setMessageId(localKeyValue.getValue());
      }
      else if (localKeyValue.getKey().equalsIgnoreCase("BIRTHDAY"))
      {
        paramImsMsg.setBirthDayMessage(true);
      }
      else if (localKeyValue.getKey().equalsIgnoreCase("MSGURL"))
      {
        paramImsMsg.setUrl(XmlReader.getFilenameFromPath(localKeyValue.getValue()));
      }
      else if (localKeyValue.getKey().equalsIgnoreCase("DESTINATION"))
      {
        paramImsMsg.setDestinations(localKeyValue.getValue());
      }
      else if (localKeyValue.getKey().equalsIgnoreCase("ACTIONURL"))
      {
        paramImsMsg.setActionUrl(localKeyValue.getValue());
      }
      else if (localKeyValue.getKey().equalsIgnoreCase("BEGINDATE"))
      {
        if (!validateDate(localKeyValue.getValue(), false)) {
          return false;
        }
        try
        {
          paramImsMsg.setStartDate(new DateTime(localKeyValue.getValue(), Locale.getDefault(), "yyyyMMdd"));
        }
        catch (Exception localException) {}
      }
      else if (localKeyValue.getKey().equalsIgnoreCase("ENDDATE"))
      {
        if (!validateDate(localKeyValue.getValue(), true)) {
          return false;
        }
      }
      else if (localKeyValue.getKey().equalsIgnoreCase("MAXTIME"))
      {
        paramImsMsg.setDisplayTime(Integer.valueOf(localKeyValue.getValue()).intValue());
      }
      else if (localKeyValue.getKey().equalsIgnoreCase("DAYOFWEEK"))
      {
        if (localKeyValue.getValue().indexOf(String.valueOf(this.a)) == -1) {
          return false;
        }
      }
      else if (localKeyValue.getKey().equalsIgnoreCase("PRIORITY"))
      {
        paramImsMsg.setPriority(Integer.valueOf(localKeyValue.getValue()).intValue());
      }
      else if (localKeyValue.getKey().equalsIgnoreCase("DELIVERYCNT"))
      {
        paramImsMsg.setDeliveries(Integer.valueOf(localKeyValue.getValue()).intValue());
      }
    }
    return false;
  }
  
  public boolean validateDate(String paramString, boolean paramBoolean)
  {
    int i = 0;
    int j = 0;
    int k = 0;
    if ((paramString == null) || (paramString.length() != 8)) {
      return false;
    }
    int m = 0;
    try
    {
      m = Integer.valueOf(paramString).intValue();
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return false;
    }
    if (paramString.charAt(4) == '0') {
      i = Integer.valueOf(paramString.substring(5, 6)).intValue();
    } else {
      i = Integer.valueOf(paramString.substring(4, 6)).intValue();
    }
    if (paramString.charAt(6) == '0') {
      j = Integer.valueOf(paramString.substring(7, 8)).intValue();
    } else {
      j = Integer.valueOf(paramString.substring(6, 8)).intValue();
    }
    k = Integer.valueOf(paramString.substring(0, 4)).intValue();
    DateCalc localDateCalc1 = new DateCalc(k, i, j);
    DateCalc localDateCalc2 = new DateCalc();
    if (!paramBoolean)
    {
      if (localDateCalc1.getLongDate() > localDateCalc2.getLongDate()) {
        return false;
      }
    }
    else if (localDateCalc2.getLongDate() > localDateCalc1.getLongDate()) {
      return false;
    }
    return true;
  }
  
  private void a(ImsMsg paramImsMsg)
  {
    String str1;
    while ((str1 = this.jdField_case.getTag()) != null)
    {
      str1 = str1.substring(0, str1.length() - 1);
      if (str1.equalsIgnoreCase("</CUSTOMTAGS>")) {
        return;
      }
      str1 = str1.substring(6, str1.lastIndexOf('"'));
      int i = str1.indexOf(".");
      String str2 = str1.substring(0, i);
      String str3 = str1.substring(i + 1, str1.length());
      CustomTag localCustomTag = paramImsMsg.jdField_char.add(str2);
      localCustomTag.setValue(str3);
    }
  }
  
  private long jdField_do(ImsMsg paramImsMsg)
  {
    DemoKey localDemoKey = paramImsMsg.getTargetingKey();
    String str1;
    while ((str1 = this.jdField_case.getTag()) != null)
    {
      str1 = str1.substring(0, str1.length() - 1);
      if (str1.equalsIgnoreCase("</RULES>")) {
        return localDemoKey.getValue();
      }
      int i = str1.indexOf('=');
      if (i == -1) {
        return 0L;
      }
      String str2 = str1.substring(i + 1);
      str1 = str1.substring(i + 2);
      i = str1.indexOf('(');
      String str3 = str1.substring(0, i);
      str1 = str1.substring(i + 1);
      if (str3.equalsIgnoreCase("ACCTSUM"))
      {
        AcctSumRule localAcctSumRule = new AcctSumRule(str2);
        if (localAcctSumRule.isValid()) {
          paramImsMsg.setAcctSumRule(localAcctSumRule);
        }
      }
      else
      {
        int j;
        int k;
        String str4;
        if (str3.equalsIgnoreCase("ACCTONEOF"))
        {
          j = str2.indexOf("(");
          k = str2.indexOf(")");
          str4 = str2.substring(j + 1, k);
          if ((str4.indexOf("<") != -1) || (str4.indexOf(">") != -1))
          {
            AcctBalRule localAcctBalRule1 = new AcctBalRule(str2, false);
            paramImsMsg.setAcctBalRule(localAcctBalRule1);
          }
          else
          {
            try
            {
              paramImsMsg.setOneOfAcct(jdField_do(str1));
            }
            catch (Exception localException4) {}
          }
        }
        else if (str3.equalsIgnoreCase("ACCTS"))
        {
          j = str2.indexOf("(");
          k = str2.indexOf(")");
          str4 = str2.substring(j + 1, k);
          if ((str4.indexOf("<") != -1) || (str4.indexOf(">") != -1))
          {
            AcctBalRule localAcctBalRule2 = new AcctBalRule(str2, true);
            paramImsMsg.setAcctBalRule(localAcctBalRule2);
          }
          else
          {
            try
            {
              paramImsMsg.setAllAcct(jdField_do(str1));
            }
            catch (Exception localException5) {}
          }
        }
        else if (str3.equalsIgnoreCase("NOACCTONEOF"))
        {
          try
          {
            paramImsMsg.setNoAcctOneOf(jdField_do(str1));
          }
          catch (Exception localException1) {}
        }
        else if (str3.equalsIgnoreCase("NOACCT"))
        {
          try
          {
            paramImsMsg.setNoAcct(jdField_do(str1));
          }
          catch (Exception localException2) {}
        }
        else if (str3.equalsIgnoreCase("GENDER"))
        {
          localDemoKey.setGender(jdField_if(str1));
        }
        else if (str3.equalsIgnoreCase("AGE"))
        {
          localDemoKey.setAge(jdField_if(str1));
        }
        else if (str3.equalsIgnoreCase("INTENDEDUSE"))
        {
          localDemoKey.setUse(jdField_if(str1));
        }
        else if (str3.equalsIgnoreCase("INCOME"))
        {
          localDemoKey.setIncome(jdField_if(str1));
        }
        else if (str3.equalsIgnoreCase("MARRIED"))
        {
          localDemoKey.setMarital(a(str1));
        }
        else if (str3.equalsIgnoreCase("KIDS"))
        {
          localDemoKey.setFamily(a(str1));
        }
        else if (str3.equalsIgnoreCase("OWNHOME"))
        {
          localDemoKey.setResidence(a(str1));
        }
        else if (str3.equalsIgnoreCase("ZIPSTATE"))
        {
          try
          {
            paramImsMsg.setStates(jdField_for(str1));
          }
          catch (Exception localException3) {}
        }
        else
        {
          return 0L;
        }
      }
    }
    return localDemoKey.getValue();
  }
  
  private int[] jdField_do(String paramString)
  {
    int i = paramString.indexOf(')');
    if (-1 == i) {
      return null;
    }
    String str1 = paramString.substring(0, i);
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, ";");
    int j = localStringTokenizer.countTokens();
    if (j == 0) {
      return null;
    }
    int[] arrayOfInt = new int[j];
    int k = 0;
    while (localStringTokenizer.hasMoreTokens())
    {
      String str2 = localStringTokenizer.nextToken();
      arrayOfInt[(k++)] = Integer.parseInt(str2);
    }
    return arrayOfInt;
  }
  
  private String[] jdField_for(String paramString)
  {
    int i = paramString.indexOf(')');
    if (-1 == i) {
      return null;
    }
    String str = paramString.substring(0, i);
    StringTokenizer localStringTokenizer = new StringTokenizer(str, ";");
    int j = localStringTokenizer.countTokens();
    if (j == 0) {
      return null;
    }
    String[] arrayOfString = new String[j];
    int k = 0;
    while (localStringTokenizer.hasMoreTokens()) {
      arrayOfString[(k++)] = localStringTokenizer.nextToken();
    }
    return arrayOfString;
  }
  
  private int jdField_if(String paramString)
  {
    int i = 0;
    int j = -1;
    int k = -1;
    String str1 = "";
    String str2 = paramString;
    String str3 = "";
    do
    {
      j = str2.indexOf(';');
      if (-1 == j)
      {
        k = str2.indexOf(')');
        if (-1 == k) {
          return i;
        }
        str3 = str2.substring(0, k);
      }
      else
      {
        str3 = str2.substring(0, j);
        str2 = str2.substring(j + 1);
      }
      str1 = str3.substring(0, 1);
      if ((str3.length() > 1) && (str3.charAt(1) != ';')) {
        str1 = str3.substring(0, 2);
      }
      try
      {
        i |= Integer.valueOf(str1).intValue();
      }
      catch (NumberFormatException localNumberFormatException)
      {
        System.err.println("Number format exception  -  " + localNumberFormatException);
      }
    } while (-1 != j);
    return i;
  }
  
  private int a(String paramString)
  {
    if (-1 != paramString.indexOf("YES")) {
      return 1;
    }
    if (-1 != paramString.indexOf("NO")) {
      return 2;
    }
    return 0;
  }
  
  public String toString()
  {
    if (this.jdField_int != null)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      for (int i = 0; i < this.jdField_int.size(); i++)
      {
        ImsMsg localImsMsg = (ImsMsg)this.jdField_int.elementAt(i);
        localStringBuffer.append(localImsMsg).append("\n");
      }
      return localStringBuffer.toString();
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.ims21.servlet.parse.MessagePack
 * JD-Core Version:    0.7.0.1
 */