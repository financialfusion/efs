package com.ffusion.beans.approvals;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.tw.TWTransaction;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.text.Collator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

public class ApprovalsItem
  extends ExtendABean
{
  private int aZF;
  private int aZG;
  private int aZI;
  private int aZJ;
  private String aZE;
  private Object aZH;
  private HashMap aZK;
  private DateTime aZD;
  private DateTime aZC;
  
  public int getItemID()
  {
    return this.aZF;
  }
  
  public void setItemID(int paramInt)
  {
    this.aZF = paramInt;
  }
  
  public int getItemType()
  {
    return this.aZG;
  }
  
  public void setItemType(int paramInt)
  {
    this.aZG = paramInt;
  }
  
  public int getItemSubType()
  {
    return this.aZI;
  }
  
  public void setItemSubType(int paramInt)
  {
    this.aZI = paramInt;
  }
  
  public int getSubmittingUserID()
  {
    return this.aZJ;
  }
  
  public void setSubmittingUserID(int paramInt)
  {
    this.aZJ = paramInt;
  }
  
  public String getSubmittingUserName()
  {
    return this.aZE;
  }
  
  public void setSubmittingUserName(String paramString)
  {
    this.aZE = paramString;
  }
  
  public Object getItem()
  {
    return this.aZH;
  }
  
  public void setItem(Object paramObject)
  {
    this.aZH = paramObject;
  }
  
  public DateTime getSubmissionDate()
  {
    return this.aZD;
  }
  
  public void setSubmissionDate(DateTime paramDateTime)
  {
    this.aZD = paramDateTime;
  }
  
  public DateTime getDueDate()
  {
    return this.aZC;
  }
  
  public void setDueDate(DateTime paramDateTime)
  {
    this.aZC = paramDateTime;
  }
  
  public void setApprovalItemProperty(String paramString1, String paramString2)
  {
    if (this.aZK == null) {
      this.aZK = new HashMap();
    }
    this.aZK.put(paramString1, paramString2);
  }
  
  public String getApprovalItemProperty(String paramString)
  {
    String str = null;
    if ((this.aZK != null) && (this.aZK.containsKey(paramString))) {
      str = (String)this.aZK.get(paramString);
    }
    return str;
  }
  
  public ApprovalsItem(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if ((this.aZH != null) && ((this.aZH instanceof Localeable))) {
      ((Localeable)this.aZH).setLocale(paramLocale);
    }
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      int i;
      if (str1.equalsIgnoreCase("ITEMID"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          return false;
        }
        return this.aZF == i;
      }
      if (str1.equalsIgnoreCase("ITEMTYPE"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          return false;
        }
        return this.aZG == i;
      }
      if (str1.equalsIgnoreCase("ITEMSUBTYPE"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException3)
        {
          return false;
        }
        return this.aZI == i;
      }
      if (str1.equalsIgnoreCase("SUBMITTINGUSERID"))
      {
        try
        {
          i = Integer.parseInt(str2);
        }
        catch (NumberFormatException localNumberFormatException4)
        {
          return false;
        }
        return this.aZJ == i;
      }
      if (str1.equalsIgnoreCase("SUBMITTINGUSERNAME")) {
        return this.aZE.equals(str2);
      }
      if ((str1.equalsIgnoreCase("ITEM")) && ((this.aZH instanceof TWTransaction))) {
        return ((TWTransaction)this.aZH).isFilterable(paramString);
      }
      if ((str1.equalsIgnoreCase("SUBMISSIONDATE")) && (this.aZD != null)) {
        return this.aZD.isFilterable(paramString);
      }
      if ((str1.equalsIgnoreCase("DUEDATE")) && (this.aZC != null)) {
        return this.aZC.isFilterable(paramString);
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "ITEMID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ApprovalsItem localApprovalsItem = (ApprovalsItem)paramObject;
    int i = 1;
    if (paramString.equals("ITEMID"))
    {
      int j = localApprovalsItem.getItemType();
      if (this.aZG == j) {
        i = this.aZF - localApprovalsItem.getItemID();
      } else {
        i = this.aZG - j;
      }
    }
    else if (paramString.equals("ITEMTYPE"))
    {
      i = this.aZG - localApprovalsItem.getItemType();
    }
    else if (paramString.equals("ITEMSUBTYPE"))
    {
      i = this.aZG - localApprovalsItem.getItemSubType();
    }
    else if (paramString.equals("SUBMITTINGUSERID"))
    {
      i = this.aZG - localApprovalsItem.getSubmittingUserID();
    }
    else
    {
      Object localObject1;
      Object localObject2;
      if (paramString.equals("AMOUNT"))
      {
        if ((this.aZG == 1) && (this.aZG == localApprovalsItem.getItemType()))
        {
          localObject1 = (FundsTransaction)((TWTransaction)this.aZH).getTransaction();
          localObject2 = (FundsTransaction)((TWTransaction)localApprovalsItem.getItem()).getTransaction();
          i = ((FundsTransaction)localObject1).getAmountValue().compareTo(((FundsTransaction)localObject2).getAmountValue());
        }
        else
        {
          i = super.compare(paramObject, paramString);
        }
      }
      else if (paramString.equals("Type"))
      {
        if ((this.aZH != null) && (localApprovalsItem.getItem() != null))
        {
          localObject1 = (TWTransaction)this.aZH;
          localObject2 = (TWTransaction)localApprovalsItem.getItem();
          i = compareStrings(((TWTransaction)localObject1).getDisplayTypeAsString(), ((TWTransaction)localObject2).getDisplayTypeAsString(), doGetCollator());
        }
        else
        {
          i = super.compare(paramObject, paramString);
        }
      }
      else if ((paramString.equals("ITEM")) && (this.aZH != null) && (localApprovalsItem.getItem() != null))
      {
        if (this.aZH.equals(localApprovalsItem.getItem())) {
          i = 0;
        } else {
          i = 1;
        }
      }
      else if (paramString.equalsIgnoreCase("SUBMITTINGUSERNAME"))
      {
        localObject1 = doGetCollator();
        i = ((Collator)localObject1).compare(this.aZE, localApprovalsItem.getSubmittingUserName());
      }
      else
      {
        i = super.compare(paramObject, paramString);
      }
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsItem)) {
      return false;
    }
    ApprovalsItem localApprovalsItem = (ApprovalsItem)paramObject;
    return (this.aZF == localApprovalsItem.getItemID()) && (this.aZG == localApprovalsItem.getItemType()) && (this.aZI == localApprovalsItem.getItemSubType()) && (this.aZJ == localApprovalsItem.getSubmittingUserID()) && (this.aZE.equalsIgnoreCase(localApprovalsItem.getSubmittingUserName())) && (areObjectsEqual(this.aZH, localApprovalsItem.getItem())) && (areObjectsEqual(this.aZD, localApprovalsItem.getSubmissionDate())) && (areObjectsEqual(this.aZC, localApprovalsItem.getDueDate()));
  }
  
  public int hashCode()
  {
    return this.aZG * 3 + this.aZF * 5;
  }
  
  public void set(ApprovalsItem paramApprovalsItem)
  {
    super.set(paramApprovalsItem);
    setItemID(paramApprovalsItem.getItemID());
    setItemType(paramApprovalsItem.getItemType());
    setItemSubType(paramApprovalsItem.getItemSubType());
    setSubmittingUserID(paramApprovalsItem.getSubmittingUserID());
    setSubmittingUserName(paramApprovalsItem.getSubmittingUserName());
    setItem(paramApprovalsItem.getItem());
    setLocale(paramApprovalsItem.locale);
    setSubmissionDate(paramApprovalsItem.getSubmissionDate());
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("ITEMID")) {
        this.aZF = Integer.parseInt(paramString2);
      } else if (paramString1.equals("ITEMTYPE")) {
        this.aZG = Integer.parseInt(paramString2);
      } else if (paramString1.equals("ITEMSUBTYPE")) {
        this.aZI = Integer.parseInt(paramString2);
      } else if (paramString1.equals("SUBMITTINGUSERID")) {
        this.aZJ = Integer.parseInt(paramString2);
      } else if (paramString1.equals("SUBMITTINGUSERNAME")) {
        this.aZE = paramString2;
      } else {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception", localException);
    }
    return bool;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "APPROVALS_ITEM");
    XMLHandler.appendTag(localStringBuffer, "ITEMID", this.aZF);
    XMLHandler.appendTag(localStringBuffer, "ITEMTYPE", this.aZG);
    XMLHandler.appendTag(localStringBuffer, "ITEMSUBTYPE", this.aZI);
    XMLHandler.appendTag(localStringBuffer, "SUBMITTINGUSERID", this.aZJ);
    XMLHandler.appendTag(localStringBuffer, "SUBMITTINGUSERNAME", this.aZE);
    if ((this.aZH != null) && ((this.aZH instanceof TWTransaction)))
    {
      XMLHandler.appendBeginTag(localStringBuffer, "ITEM");
      localStringBuffer.append(((TWTransaction)this.aZH).getXML());
      XMLHandler.appendEndTag(localStringBuffer, "ITEM");
    }
    if (this.aZD != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "SUBMISSIONDATE");
      localStringBuffer.append(this.aZD.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "SUBMISSIONDATE");
    }
    if (this.aZC != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "DUEDATE");
      localStringBuffer.append(this.aZC.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "DUEDATE");
    }
    if (this.aZK != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "PROPERTIES");
      Iterator localIterator = this.aZK.keySet().iterator();
      while (localIterator.hasNext())
      {
        XMLHandler.appendBeginTag(localStringBuffer, "PROPERTY");
        String str1 = (String)localIterator.next();
        String str2 = (String)this.aZK.get(str1);
        XMLHandler.appendTag(localStringBuffer, "NAME", str1);
        XMLHandler.appendTag(localStringBuffer, "VALUE", str2);
        XMLHandler.appendEndTag(localStringBuffer, "PROPERTY");
      }
      XMLHandler.appendEndTag(localStringBuffer, "PROPERTIES");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "APPROVALS_ITEM");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new c(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public boolean areObjectsEqual(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == paramObject2) {
      return true;
    }
    if ((paramObject1 == null) || (paramObject2 == null)) {
      return false;
    }
    return paramObject1.equals(paramObject2);
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new c());
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("itemID=").append(this.aZF);
    localStringBuffer.append(" itemType=").append(this.aZG);
    localStringBuffer.append(" itemSubType=").append(this.aZI);
    localStringBuffer.append(" submittingUserID=").append(this.aZJ);
    localStringBuffer.append(" submittingUserName=").append(this.aZE);
    localStringBuffer.append(" submissionDate=").append(this.aZD);
    localStringBuffer.append(" dueDate=").append(this.aZC);
    localStringBuffer.append(" (item=").append(this.aZH).append(")");
    return localStringBuffer.toString();
  }
  
  class a
    extends XMLHandler
  {
    private String jdField_int = null;
    private String jdField_new = null;
    
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str1 = new String(paramArrayOfChar, paramInt1, paramInt2);
      str1 = str1.trim();
      String str2 = getElement();
      if (str2.equals("NAME")) {
        this.jdField_int = str1;
      } else if (str2.equals("VALUE")) {
        this.jdField_new = str1;
      } else {
        ApprovalsItem.this.set(str2, str1);
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {
      if (paramString.equals("PROPERTY"))
      {
        if (this.jdField_int != null) {
          ApprovalsItem.this.aZK.put(this.jdField_int, this.jdField_new);
        }
      }
      else {
        super.endElement(paramString);
      }
    }
  }
  
  class b
    extends XMLHandler
  {
    public b() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("PROPERTY")) {
        getHandler().continueWith(new ApprovalsItem.a(ApprovalsItem.this));
      } else {
        super.startElement(paramString);
      }
    }
  }
  
  class c
    extends XMLHandler
  {
    public c() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      ApprovalsItem.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("ITEM"))
      {
        if (ApprovalsItem.this.aZG == 1)
        {
          if (ApprovalsItem.this.aZH == null) {
            ApprovalsItem.this.aZH = new TWTransaction(ApprovalsItem.this.locale);
          }
          ((TWTransaction)ApprovalsItem.this.aZH).continueXMLParsing(getHandler());
        }
      }
      else if (paramString.equals("SUBMISSIONDATE"))
      {
        if (ApprovalsItem.this.aZD == null) {
          ApprovalsItem.this.aZD = new DateTime(ApprovalsItem.this.locale);
        }
        ApprovalsItem.this.aZD.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("DUEDATE"))
      {
        if (ApprovalsItem.this.aZC == null) {
          ApprovalsItem.this.aZC = new DateTime(ApprovalsItem.this.locale);
        }
        ApprovalsItem.this.aZC.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("PROPERTIES"))
      {
        if (ApprovalsItem.this.aZK == null) {
          ApprovalsItem.this.aZK = new HashMap();
        }
        getHandler().continueWith(new ApprovalsItem.b(ApprovalsItem.this));
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsItem
 * JD-Core Version:    0.7.0.1
 */