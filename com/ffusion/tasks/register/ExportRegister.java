package com.ffusion.tasks.register;

import com.ffusion.beans.Currency;
import com.ffusion.beans.register.RegisterCategories;
import com.ffusion.beans.register.RegisterCategory;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.beans.register.TransactionCategories;
import com.ffusion.beans.register.TransactionCategory;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExportRegister
  extends ExtendedBaseTask
  implements Task
{
  static final String EF = "OFX";
  static final String ED = "QIF98";
  static final String EB = "QIF99";
  static final String EE = "1";
  static final String EC = "2";
  static final String EA = "3";
  protected String format = null;
  protected String categoriesSessionName = null;
  protected boolean includeReconciled = true;
  protected boolean includeUnreconciled = true;
  
  public ExportRegister()
  {
    this.collectionSessionName = "RegisterTransactions";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    RegisterTransactions localRegisterTransactions = (RegisterTransactions)localHttpSession.getAttribute(this.collectionSessionName);
    RegisterCategories localRegisterCategories = (RegisterCategories)localHttpSession.getAttribute(this.categoriesSessionName);
    if (localRegisterTransactions == null)
    {
      this.error = 20004;
      return this.taskErrorURL;
    }
    if (localRegisterCategories == null)
    {
      this.error = 20006;
      return this.taskErrorURL;
    }
    if ((!this.includeUnreconciled) && (!this.includeReconciled))
    {
      this.error = 20113;
      return this.taskErrorURL;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.includeUnreconciled) {
      localStringBuffer.append("STATUS=0");
    }
    if (this.includeReconciled)
    {
      if (localStringBuffer.length() > 0) {
        localStringBuffer.append(",");
      }
      localStringBuffer.append("STATUS=2");
      localStringBuffer.append(",");
      localStringBuffer.append("STATUS=3");
    }
    localStringBuffer.append(",or");
    localRegisterTransactions.setFilter(localStringBuffer.toString());
    if (this.format.equalsIgnoreCase("OFX"))
    {
      this.error = 20112;
      return this.taskErrorURL;
    }
    if (this.format.equalsIgnoreCase("QIF98")) {
      exportQif(paramHttpServletResponse, localRegisterTransactions, localRegisterCategories, false);
    } else if (this.format.equalsIgnoreCase("QIF99")) {
      exportQif(paramHttpServletResponse, localRegisterTransactions, localRegisterCategories, true);
    }
    return "";
  }
  
  protected int exportQif(HttpServletResponse paramHttpServletResponse, RegisterTransactions paramRegisterTransactions, RegisterCategories paramRegisterCategories, boolean paramBoolean)
    throws IOException
  {
    paramHttpServletResponse.setContentType("application/text; charset=UTF-8");
    paramHttpServletResponse.setHeader("Content-disposition", "inline;filename=EXPORT.QIF");
    PrintWriter localPrintWriter = null;
    try
    {
      localPrintWriter = paramHttpServletResponse.getWriter();
      localPrintWriter.print(exportQifCategories(paramRegisterCategories));
      localPrintWriter.println("!Type:Bank");
      Iterator localIterator = paramRegisterTransactions.iterator();
      while (localIterator.hasNext())
      {
        RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
        if (paramBoolean) {
          localRegisterTransaction.setDateFormat("MM/dd/yyyy");
        } else {
          localRegisterTransaction.setDateFormat("SHORT");
        }
        StringBuffer localStringBuffer = new StringBuffer();
        if (localRegisterTransaction.getDate() != null)
        {
          localStringBuffer.append("D");
          localStringBuffer.append(localRegisterTransaction.getDateIssued());
          localStringBuffer.append("\r\n");
        }
        localStringBuffer.append("T");
        localStringBuffer.append(formatAmount(localRegisterTransaction.getAmountValue()));
        localStringBuffer.append("\r\n");
        if (localRegisterTransaction.getStatusValue() == 1) {
          localStringBuffer.append("C*\r\n");
        }
        if ((localRegisterTransaction.getStatusValue() == 2) || (localRegisterTransaction.getStatusValue() == 3)) {
          localStringBuffer.append("CX\r\n");
        }
        if (localRegisterTransaction.getReferenceNumber() != null)
        {
          localStringBuffer.append("N");
          localStringBuffer.append(localRegisterTransaction.getReferenceNumber());
          localStringBuffer.append("\r\n");
        }
        if (localRegisterTransaction.getPayeeName() != null)
        {
          localStringBuffer.append("P");
          localStringBuffer.append(localRegisterTransaction.getPayeeName());
          localStringBuffer.append("\r\n");
        }
        else if (localRegisterTransaction.getDescription() != null)
        {
          localStringBuffer.append("P");
          localStringBuffer.append(localRegisterTransaction.getDescription());
          localStringBuffer.append("\r\n");
        }
        if (localRegisterTransaction.getMemo() != null)
        {
          localStringBuffer.append("M");
          localStringBuffer.append(localRegisterTransaction.getMemo());
          localStringBuffer.append("\r\n");
        }
        Object localObject1;
        if (!localRegisterTransaction.getMultipleCategories())
        {
          localObject1 = paramRegisterCategories.getById(localRegisterTransaction.getRegisterCategoryId());
          localStringBuffer.append("L");
          localStringBuffer.append(jdMethod_for((RegisterCategory)localObject1, paramRegisterCategories));
          localStringBuffer.append("\r\n");
        }
        else
        {
          localObject1 = localRegisterTransaction.getTransactionCategories().iterator();
          while (((Iterator)localObject1).hasNext())
          {
            TransactionCategory localTransactionCategory = (TransactionCategory)((Iterator)localObject1).next();
            RegisterCategory localRegisterCategory = paramRegisterCategories.getById(localTransactionCategory.getRegisterCategoryId());
            localStringBuffer.append("S");
            localStringBuffer.append(jdMethod_for(localRegisterCategory, paramRegisterCategories));
            localStringBuffer.append("\r\n");
            localStringBuffer.append("$");
            localStringBuffer.append(formatAmount(localTransactionCategory.getAmountValue()));
            localStringBuffer.append("\r\n");
          }
        }
        localPrintWriter.println("^");
      }
    }
    finally
    {
      localPrintWriter.flush();
      localPrintWriter.close();
    }
    return 0;
  }
  
  protected String exportQifCategories(RegisterCategories paramRegisterCategories)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("!Type:Cat\r\n");
    Iterator localIterator = paramRegisterCategories.iterator();
    while (localIterator.hasNext())
    {
      RegisterCategory localRegisterCategory = (RegisterCategory)localIterator.next();
      localStringBuffer.append("N");
      localStringBuffer.append(jdMethod_for(localRegisterCategory, paramRegisterCategories));
      localStringBuffer.append("\r\n");
      if (localRegisterCategory.getDescription() != null)
      {
        localStringBuffer.append("D");
        localStringBuffer.append(localRegisterCategory.getDescription());
        localStringBuffer.append("\r\n");
      }
      if (localRegisterCategory.getTaxRelatedValue()) {
        localStringBuffer.append("T\r\n");
      }
      if ((localRegisterCategory.getTypeValue() == 0) || (localRegisterCategory.getTypeValue() == 2)) {
        localStringBuffer.append("I\r\n");
      } else {
        localStringBuffer.append("E\r\n");
      }
      localStringBuffer.append("^\r\n");
    }
    return localStringBuffer.toString();
  }
  
  private String jdMethod_for(RegisterCategory paramRegisterCategory, RegisterCategories paramRegisterCategories)
  {
    if (paramRegisterCategory.getParentCategoryValue() == -1) {
      return paramRegisterCategory.getName();
    }
    RegisterCategory localRegisterCategory = paramRegisterCategories.getById(paramRegisterCategory.getParentCategory());
    return localRegisterCategory.getName() + ":" + paramRegisterCategory.getName();
  }
  
  protected String formatAmount(Currency paramCurrency)
  {
    if (paramCurrency.isNegative()) {
      return "-" + paramCurrency.getCurrencyStringNoSymbolNoComma();
    }
    return paramCurrency.getCurrencyStringNoSymbolNoComma();
  }
  
  public void setFormat(String paramString)
  {
    this.format = paramString;
  }
  
  public void setIncludeType(String paramString)
  {
    if (paramString.equals("1"))
    {
      this.includeReconciled = true;
      this.includeUnreconciled = false;
    }
    else if (paramString.equals("2"))
    {
      this.includeReconciled = false;
      this.includeUnreconciled = true;
    }
    else
    {
      this.includeReconciled = true;
      this.includeUnreconciled = true;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.ExportRegister
 * JD-Core Version:    0.7.0.1
 */