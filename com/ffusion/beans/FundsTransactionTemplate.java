package com.ffusion.beans;

import com.ffusion.approvals.IApprovable;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.util.Filterable;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.LocaleUtil;
import com.ffusion.util.Localeable;
import com.ffusion.util.Sortable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.XMLable;
import com.ffusion.util.beans.LocaleableBean;
import com.ffusion.util.logging.AuditLogRecord;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.StringTokenizer;

public class FundsTransactionTemplate
  extends LocaleableBean
  implements Filterable, Sortable, Localeable, XMLable, Serializable, IApprovable
{
  public static final String FUNDSTRANSACTIONTEMPLATE = "FUNDSTRANSACTIONTEMPLATE";
  public static final String SINGLEUSE = "SINGLEUSE";
  protected String name;
  protected int id;
  protected FundsTransaction element;
  protected String group;
  protected boolean singleUse = false;
  protected int userID;
  
  public FundsTransactionTemplate() {}
  
  public FundsTransactionTemplate(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  private void jdMethod_for(String paramString)
  {
    Object localObject;
    if ((paramString.equals("RECTRANSFER")) || (paramString.equals("TRANSFER")))
    {
      localObject = new RecTransfers();
      this.element = ((FundsTransaction)((RecTransfers)localObject).createNoAdd());
    }
    else if ((paramString.equals("RECPAYMENT")) || (paramString.equals("PAYMENT")))
    {
      localObject = new RecPayments();
      this.element = ((FundsTransaction)((RecPayments)localObject).createNoAdd());
    }
    else if ((paramString.equals("RECACHBATCH")) || (paramString.equals("ACHBATCH")))
    {
      localObject = new ACHBatches();
      this.element = ((FundsTransaction)((ACHBatches)localObject).createNoAdd());
    }
    else if ((paramString.equals("REC_WIRE_TRANSFER")) || (paramString.equals("WIRE_TRANSFER")))
    {
      localObject = new WireTransfers();
      this.element = ((FundsTransaction)((WireTransfers)localObject).createNoAdd());
    }
  }
  
  public String getTemplateName()
  {
    return this.name;
  }
  
  public void setTemplateName(String paramString)
  {
    this.name = paramString;
    if ((paramString != null) && (paramString.toUpperCase().indexOf("SINGLE USE") != -1)) {
      this.singleUse = true;
    }
  }
  
  public int getID()
  {
    return this.id;
  }
  
  public void setID(int paramInt)
  {
    this.id = paramInt;
  }
  
  public void setID(String paramString)
  {
    try
    {
      this.id = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public int getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(int paramInt)
  {
    this.userID = paramInt;
  }
  
  public void setUserID(String paramString)
  {
    try
    {
      this.userID = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public String getTemplateGroup()
  {
    return this.group;
  }
  
  public void setTemplateGroup(String paramString)
  {
    this.group = paramString;
  }
  
  public void setSingleUse(String paramString)
  {
    this.singleUse = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getSingleUse()
  {
    return "" + this.singleUse;
  }
  
  public boolean getSingleUseValue()
  {
    return this.singleUse;
  }
  
  public int getApprovalType()
  {
    return this.element.getApprovalType();
  }
  
  public int getApprovalSubType()
  {
    int i = this.element.getType();
    if ((i == 1) || (i == 2)) {
      return 1001;
    }
    if ((i == 3) || (i == 4)) {
      return 1003;
    }
    if ((i == 5) || (i == 6)) {
      return 1005;
    }
    if ((i == 9) || (i == 10) || (i == 12) || (i == 13) || (i == 17) || (i == 18)) {
      return 1007;
    }
    return 1999;
  }
  
  public String getApprovalSubTypeName()
  {
    return this.element.getApprovalSubTypeName() + " Template";
  }
  
  public Currency getApprovalAmount()
  {
    Currency localCurrency1 = null;
    if (this.element.getType() == 9)
    {
      BigDecimal localBigDecimal = new BigDecimal(0.0D);
      ACHBatch localACHBatch = (ACHBatch)this.element;
      ACHEntries localACHEntries = localACHBatch.getACHEntries();
      int i = localACHEntries.size();
      for (int j = 0; j < i; j++)
      {
        ACHEntry localACHEntry = (ACHEntry)localACHEntries.get(j);
        Currency localCurrency2 = localACHEntry.getAmountValue();
        if (localCurrency2 != null) {
          localBigDecimal = localBigDecimal.add(localCurrency2.getAmountValue());
        }
      }
      localCurrency1 = new Currency(localBigDecimal, LocaleUtil.getDefaultLocale());
      return localCurrency1;
    }
    return this.element.getApprovalAmount();
  }
  
  public AuditLogRecord getAuditRecord(SecureUser paramSecureUser, String paramString1, int paramInt, String paramString2)
  {
    return this.element.getAuditRecord(paramSecureUser, paramString1, paramInt, paramString2);
  }
  
  public AuditLogRecord getAuditRecord(SecureUser paramSecureUser, ILocalizable paramILocalizable, int paramInt, String paramString)
  {
    return this.element.getAuditRecord(paramSecureUser, paramILocalizable, paramInt, paramString);
  }
  
  public DateTime getApprovalDueDate()
  {
    Object localObject;
    if (this.element.getType() == 1)
    {
      localObject = (Transfer)this.element;
      return ((Transfer)localObject).getApprovalDueDate();
    }
    if (this.element.getType() == 3)
    {
      localObject = (Payment)this.element;
      return ((Payment)localObject).getApprovalDueDate();
    }
    if (this.element.getType() == 5)
    {
      localObject = (WireTransfer)this.element;
      return ((WireTransfer)localObject).getDueDateValue();
    }
    if (this.element.getType() == 9)
    {
      localObject = (ACHBatch)this.element;
      return ((ACHBatch)localObject).getApprovalDueDate();
    }
    return null;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    FundsTransactionTemplate localFundsTransactionTemplate = (FundsTransactionTemplate)paramObject;
    int i = 0;
    if ((paramString.equalsIgnoreCase("TEMPLATENAME")) && (getTemplateName() != null) && (localFundsTransactionTemplate.getTemplateName() != null)) {
      i = getTemplateName().compareToIgnoreCase(localFundsTransactionTemplate.getTemplateName());
    } else if ((paramString.equalsIgnoreCase("TEMPLATEGROUP")) && (getTemplateGroup() != null) && (localFundsTransactionTemplate.getTemplateGroup() != null)) {
      i = getTemplateGroup().compareToIgnoreCase(localFundsTransactionTemplate.getTemplateGroup());
    } else if ((paramString.equalsIgnoreCase("SINGLEUSE")) && (getSingleUse() != null) && (localFundsTransactionTemplate.getSingleUse() != null)) {
      i = getSingleUse().compareTo(localFundsTransactionTemplate.getSingleUse());
    } else if (this.element != null) {
      i = this.element.compare(localFundsTransactionTemplate.getFundsTransaction(), paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if (this.element != null)
    {
      if (paramString1.equalsIgnoreCase("TEMPLATENAME")) {
        return ExtendABean.isFilterable(getTemplateName(), paramString2, paramString3);
      }
      if (paramString1.equalsIgnoreCase("TEMPLATEGROUP")) {
        return ExtendABean.isFilterable(getTemplateGroup(), paramString2, paramString3);
      }
      if (paramString1.equalsIgnoreCase("SINGLEUSE")) {
        return ExtendABean.isFilterable(getSingleUse(), paramString2, paramString3);
      }
      return this.element.isFilterablePreParsed(paramString1, paramString2, paramString3);
    }
    return false;
  }
  
  public void set(FundsTransaction paramFundsTransaction)
  {
    if (paramFundsTransaction != null) {
      this.element = ((FundsTransaction)paramFundsTransaction.clone());
    } else {
      this.element = null;
    }
  }
  
  public void set(FundsTransactionTemplate paramFundsTransactionTemplate)
  {
    if (paramFundsTransactionTemplate == null) {
      return;
    }
    if (paramFundsTransactionTemplate.getTemplateName() != null) {
      setTemplateName(paramFundsTransactionTemplate.getTemplateName());
    }
    if (paramFundsTransactionTemplate.getTemplateGroup() != null) {
      setTemplateGroup(paramFundsTransactionTemplate.getTemplateGroup());
    }
    setID(paramFundsTransactionTemplate.getID());
    setSingleUse(paramFundsTransactionTemplate.getSingleUse());
    set(paramFundsTransactionTemplate.getFundsTransaction());
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equalsIgnoreCase("TEMPLATENAME")) {
      setTemplateName(XMLUtil.XMLDecode(paramString2));
    } else if (paramString1.equalsIgnoreCase("ID")) {
      setID(paramString2);
    } else if (paramString1.equalsIgnoreCase("TEMPLATEGROUP")) {
      setTemplateGroup(paramString2);
    } else if (paramString1.equalsIgnoreCase("SINGLEUSE")) {
      setSingleUse(paramString2);
    }
    if (this.element != null) {
      this.element.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new InternalXMLHandler(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "FUNDSTRANSACTIONTEMPLATE");
    XMLHandler.appendTag(localStringBuffer, "TEMPLATENAME", getTemplateName());
    XMLHandler.appendTag(localStringBuffer, "TEMPLATEGROUP", getTemplateGroup());
    XMLHandler.appendTag(localStringBuffer, "SINGLEUSE", getSingleUse());
    XMLHandler.appendTag(localStringBuffer, "ID", getID());
    if (this.element != null) {
      localStringBuffer.append(this.element.getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "FUNDSTRANSACTIONTEMPLATE");
    return localStringBuffer.toString();
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new InternalXMLHandler());
  }
  
  public void setFundsTransaction(FundsTransaction paramFundsTransaction)
  {
    if (paramFundsTransaction != null) {
      this.element = ((FundsTransaction)paramFundsTransaction.clone());
    } else {
      this.element = null;
    }
  }
  
  public FundsTransaction getFundsTransaction()
  {
    return this.element;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.element != null) {
      this.element.setLocale(paramLocale);
    }
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=<>!", true);
    boolean bool = false;
    if ((localStringTokenizer.countTokens() == 3) || (localStringTokenizer.countTokens() == 4))
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      String str3 = localStringTokenizer.nextToken();
      if (localStringTokenizer.countTokens() == 1)
      {
        str2 = str2 + str3;
        str3 = localStringTokenizer.nextToken();
      }
      bool = isFilterablePreParsed(str1, str2, str3);
    }
    return bool;
  }
  
  class InternalXMLHandler
    extends XMLHandler
  {
    InternalXMLHandler() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if ((paramString.equals("RECTRANSFER")) || (paramString.equals("TRANSFER")) || (paramString.equals("RECPAYMENT")) || (paramString.equals("PAYMENT")) || (paramString.equals("RECACHBATCH")) || (paramString.equals("ACHBATCH")) || (paramString.equals("REC_WIRE_TRANSFER")) || (paramString.equals("WIRE_TRANSFER")))
      {
        if (FundsTransactionTemplate.this.element == null) {
          FundsTransactionTemplate.this.jdMethod_for(paramString);
        }
        if (FundsTransactionTemplate.this.element != null) {
          FundsTransactionTemplate.this.element.continueXMLParsing(getHandler());
        } else {
          super.startElement(paramString);
        }
      }
      else
      {
        super.startElement(paramString);
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      if (str.length() > 0) {
        FundsTransactionTemplate.this.set(getElement(), str);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.FundsTransactionTemplate
 * JD-Core Version:    0.7.0.1
 */