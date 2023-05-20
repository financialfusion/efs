package com.ffusion.tasks.positivepay;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.positivepay.PPayCheckRecord;
import com.ffusion.beans.positivepay.PPayCheckRecords;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import java.io.IOException;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateCheckRecords
  extends BaseTask
  implements Task
{
  public static final String PPAY_CHECK_RECORDS = "PPayCheckRecords";
  public static final String DEFAULT_CHECK_DATE_EMPTY_STRING = "MM/DD/YYYY";
  public static final String CHECK_DATE = "checkDate";
  public static final String ACCOUNT = "account";
  public static final String CHECK_NUMBER = "checkNumber";
  public static final String AMOUNT = "amount";
  public static final String ADDITIONAL_DATA = "additionalData";
  public static final String VOID = "void";
  private String aPo = "|";
  private String aPn;
  private String aPm = this.taskErrorURL;
  private boolean aPp = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    PPayCheckRecords localPPayCheckRecords = (PPayCheckRecords)localHttpSession.getAttribute("PPayCheckRecords");
    Locale localLocale = (Locale)localHttpSession.getAttribute("Locale");
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    if (localPPayCheckRecords == null)
    {
      this.error = 22054;
      str1 = this.taskErrorURL;
    }
    else if (this.aPn == null)
    {
      this.error = 22065;
      str1 = this.taskErrorURL;
    }
    else
    {
      int i = 0;
      for (int j = 0; j < localPPayCheckRecords.size(); j++)
      {
        String str2 = paramHttpServletRequest.getParameter("checkDate" + j);
        String str3 = paramHttpServletRequest.getParameter("account" + j);
        String str4 = paramHttpServletRequest.getParameter("checkNumber" + j);
        String str5 = paramHttpServletRequest.getParameter("amount" + j);
        String str6 = paramHttpServletRequest.getParameter("additionalData" + j);
        String str7 = paramHttpServletRequest.getParameter("void" + j);
        if ((str2 != null) && (str2.length() != 0) && (str3 != null) && (str3.length() != 0) && (str4 != null) && (str4.length() != 0) && (str5 != null) && (str5.length() != 0))
        {
          if (this.aPp)
          {
            if (str5.startsWith("-"))
            {
              this.error = 22068;
              str1 = this.aPm;
            }
            else
            {
              try
              {
                double d = Double.parseDouble(str5.trim());
              }
              catch (Exception localException)
              {
                this.error = 22069;
                str1 = this.aPm;
              }
            }
            i++;
            if (j + 1 == localPPayCheckRecords.size())
            {
              this.aPp = false;
              return str1;
            }
          }
          if (((str2.length() != 0) && (!str2.equals(this.aPn))) || (!str3.equals("")) || (!str4.equals("")) || (!str5.equals("")))
          {
            if ((str2.length() == 0) || (str2.equals(this.aPn)) || (str3.equals("")) || (str4.equals("")) || (str5.equals("")))
            {
              if ((str2.length() == 0) || (str2.equals(this.aPn))) {
                this.error = 22060;
              } else if (str3.equals("")) {
                this.error = 22061;
              } else if (str4.equals("")) {
                this.error = 22062;
              } else if (str5.equals("")) {
                this.error = 22063;
              }
              str1 = this.aPm;
            }
            i++;
            PPayCheckRecord localPPayCheckRecord = (PPayCheckRecord)localPPayCheckRecords.get(j);
            StringTokenizer localStringTokenizer = new StringTokenizer(str3, this.aPo);
            String str8 = null;
            if (localStringTokenizer.countTokens() != 4)
            {
              this.error = 22056;
              str1 = this.taskErrorURL;
            }
            else
            {
              localPPayCheckRecord.setAccountID(localStringTokenizer.nextToken());
              localPPayCheckRecord.setBankID(localStringTokenizer.nextToken());
              str8 = localStringTokenizer.nextToken();
              localPPayCheckRecord.setRoutingNumber(localStringTokenizer.nextToken());
            }
            if (str4.length() > 20)
            {
              this.error = 22057;
              str1 = this.aPm;
            }
            localPPayCheckRecord.setCheckNumber(str4);
            if (str2.length() > 10)
            {
              this.error = 22053;
              str1 = this.aPm;
            }
            if ((str2.equals("")) || (str2.equals(this.aPn)) || (str2.trim().equals(""))) {
              localPPayCheckRecord.setCheckDate(null);
            } else {
              try
              {
                localPPayCheckRecord.setCheckDate(new DateTime(str2, localLocale));
              }
              catch (InvalidDateTimeException localInvalidDateTimeException)
              {
                this.error = 22053;
                str1 = this.aPm;
                localPPayCheckRecord.setCheckDate(null);
              }
            }
            if (str5.length() > 31)
            {
              this.error = 22058;
              str1 = this.aPm;
            }
            if (str5.equals("")) {
              localPPayCheckRecord.setAmount(null);
            } else if ((str8 == null) || (str8.length() == 0))
            {
              if (localPPayCheckRecord.getAmount() != null) {
                localPPayCheckRecord.setAmount(new Currency(str5, localPPayCheckRecord.getAmount().getCurrencyCode(), localLocale));
              } else {
                localPPayCheckRecord.setAmount(new Currency(str5, localLocale));
              }
            }
            else {
              localPPayCheckRecord.setAmount(new Currency(str5, str8, localLocale));
            }
            if (str7 != null)
            {
              boolean bool = str7.equalsIgnoreCase("v");
              localPPayCheckRecord.setVoidCheck(bool);
            }
            else
            {
              localPPayCheckRecord.setVoidCheck(false);
            }
            if (str6.length() > 32)
            {
              this.error = 22059;
              str1 = this.aPm;
            }
            localPPayCheckRecord.setAdditionalData(str6);
          }
        }
      }
      if (i == 0)
      {
        this.error = 22064;
        str1 = this.aPm;
      }
    }
    return str1;
  }
  
  public void setValidateOnly(String paramString)
  {
    this.aPp = (paramString.equalsIgnoreCase("true"));
  }
  
  public boolean getValidateOnly()
  {
    return this.aPp;
  }
  
  public void setAccountSeparator(String paramString)
  {
    this.aPo = paramString;
  }
  
  public void setCheckDateEmptyString(String paramString)
  {
    this.aPn = paramString;
  }
  
  public String getCheckDateEmptyString()
  {
    String str = "";
    if (this.aPn != null) {
      str = this.aPn;
    }
    return str;
  }
  
  public void setinvalidInformationEnteredURL(String paramString)
  {
    this.aPm = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.positivepay.CreateCheckRecords
 * JD-Core Version:    0.7.0.1
 */