package com.ffusion.beans.affiliatebank;

import com.ffusion.beans.Contact;
import java.util.Locale;

public class AffiliateBankI18N
  extends Contact
{
  private int pF;
  private String pI;
  private String pE;
  private String pH;
  private String pG;
  
  public AffiliateBankI18N() {}
  
  public AffiliateBankI18N(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setAffiliateBankID(int paramInt)
  {
    this.pF = paramInt;
  }
  
  public int getAffiliateBankID()
  {
    return this.pF;
  }
  
  public void setLanguage(String paramString)
  {
    this.pI = paramString;
  }
  
  public String getLanguage()
  {
    return this.pI;
  }
  
  public void setEmailSubject(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0))
    {
      this.pE = paramString.trim();
      return;
    }
    this.pE = null;
  }
  
  public String getEmailSubject()
  {
    return this.pE;
  }
  
  public void setEmailMemo(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0))
    {
      this.pH = paramString.trim();
      return;
    }
    this.pH = null;
  }
  
  public String getEmailMemo()
  {
    return this.pH;
  }
  
  public void setEmailFrom(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0))
    {
      this.pG = paramString.trim();
      return;
    }
    this.pG = null;
  }
  
  public String getEmailFrom()
  {
    return this.pG;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.affiliatebank.AffiliateBankI18N
 * JD-Core Version:    0.7.0.1
 */