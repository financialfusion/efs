package com.ffusion.services.ofx;

import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Level;

public class Request
  extends Core
  implements BankingDefines, BillPayDefines
{
  private String bS;
  private String bR;
  public static final boolean DEBUG = false;
  
  public boolean startResponse(char[] paramArrayOfChar)
  {
    boolean bool = false;
    start(paramArrayOfChar);
    String str;
    while (((str = getToken()) != null) && (!str.equals("OFX"))) {}
    if (str == null) {
      logError(3);
    } else {
      bool = true;
    }
    return bool;
  }
  
  public HashMap parseRequest(char[] paramArrayOfChar)
  {
    HashMap localHashMap = new HashMap();
    if ((paramArrayOfChar != null) && (startResponse(paramArrayOfChar)))
    {
      String str;
      while (((str = getToken()) != null) && (!str.equals("OFX"))) {
        if ((str.equals("SIGNONMSGSRQV1")) || (str.equals("SIGNUPMSGSRQV1")) || (str.equals("ACCTINFOTRNRQ")) || (str.equals("ACCTINFORQ")) || (str.equals("SONRQ")) || (str.equals("FI")) || (str.equals("CREDITCARDMSGSRQV1")) || (str.equals("CCSTMTTRNRQ")) || (str.equals("CCSTMTRQ")) || (str.equals("BANKMSGSRQV1")) || (str.equals("STMTTRNRQ")) || (str.equals("STMTRQ")) || (str.equals("BANKACCTFROM")) || (str.equals("BANKACCTTO")) || (str.equals("CCACCTFROM")) || (str.equals("CCACCTTO")) || (str.equals("MAIL")) || (str.equals("PINCHTRNRQ")) || (str.equals("PINCHRQ")) || (str.equals("EMAILMSGSRQV1")) || (str.equals("MAILSYNCRQ")) || (str.equals("MAILTRNRQ")) || (str.equals("MAILRQ")) || (str.equals("BANKMAILSYNCRQ")) || (str.equals("BANKMAILTRNRQ")) || (str.equals("BANKMAILRQ")) || (str.equals("INCTRAN")) || (str.equals("INTRATRNRQ")) || (str.equals("INTRARQ")) || (str.equals("XFERINFO")) || (str.equals("BILLPAYMSGSRQV1")) || (str.equals("PAYEESYNCRQ")) || (str.equals("PAYEETRNRQ")) || (str.equals("PAYEERQ")) || (str.equals("PAYEEMODRQ")) || (str.equals("PAYEEDELRQ")) || (str.equals("PAYEE")) || (str.equals("PMTSYNCRQ")) || (str.equals("PMTTRNRQ")) || (str.equals("PMTRQ")) || (str.equals("PMTCANCRQ")) || (str.equals("RECPMTSYNCRQ")) || (str.equals("RECPMTRQ")) || (str.equals("RECURRINST")) || (str.equals("RECPMTMODRQ")) || (str.equals("RECPMTTRNRQ")) || (str.equals("RECPMTCANCRQ")) || (str.equals("PMTINFO")) || (str.equals("PMTMAILSYNCRQ")) || (str.equals("PMTMAILTRNRQ")) || (str.equals("PMTMAILRQ")) || (str.equals("OFX"))) {
          localHashMap.put(str, "");
        } else {
          localHashMap.put(str, getField());
        }
      }
      if ((str != null) && (str.equals("OFX"))) {
        localHashMap.put(str, "");
      }
    }
    return localHashMap;
  }
  
  public char[] getResponse(String paramString1, String paramString2)
  {
    char[] arrayOfChar = null;
    this.bS = null;
    this.bR = paramString2;
    try
    {
      InputStream localInputStream1 = ResourceUtil.getResourceAsStream(this, paramString1);
      String str1 = Strings.streamToString(localInputStream1);
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), str1);
      if (this.bS != null)
      {
        InputStream localInputStream2 = ResourceUtil.getResourceAsStream(this, this.bS);
        String str2 = Strings.streamToString(localInputStream2);
        localInputStream2.close();
        arrayOfChar = str2.toCharArray();
      }
      else
      {
        DebugLog.log(Level.INFO, "Could not find XML tag for:" + this.bR);
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log(Level.WARNING, "Could not find:" + paramString1);
    }
    return arrayOfChar;
  }
  
  private void jdMethod_if(String paramString1, String paramString2)
  {
    if (paramString1.equals(this.bR)) {
      this.bS = paramString2;
    }
  }
  
  protected class a
    extends XMLHandler
  {
    protected a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      Request.this.jdMethod_if(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ofx.Request
 * JD-Core Version:    0.7.0.1
 */