package com.ffusion.tasks.checkimaging;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.checkimaging.ImageRequest;
import com.ffusion.beans.checkimaging.ImageResult;
import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.beans.lockbox.LockboxCreditItem;
import com.ffusion.beans.lockbox.LockboxCreditItems;
import com.ffusion.beans.positivepay.PPayCheckRecord;
import com.ffusion.beans.positivepay.PPayIssue;
import com.ffusion.beans.positivepay.PPayIssues;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.CheckImaging;
import com.ffusion.tasks.MapError;
import com.ffusion.util.CommBankIdentifier;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLTag;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchImage
  extends ImageRequest
  implements ImageTask
{
  public static final String MODULE_ONLINE_STATEMENTS = "OnlineStatements";
  protected String nextURL;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String traceIdErrorURL;
  protected String verifyFormat;
  protected String searchDate;
  protected String validate;
  protected String minFields;
  protected String imageID;
  protected String itemID;
  protected String module;
  protected int error;
  protected boolean initFlag = false;
  protected boolean initialized = false;
  protected XMLTag tag;
  protected Locale locale;
  protected ArrayList imageIDList = new ArrayList();
  protected ImageResults depositResults = new ImageResults();
  protected String transID;
  protected String pPayAccountID;
  protected String pPayBankID;
  protected String pPayCheckNumber;
  protected String accountInSessionName = "Account";
  protected String transactionInSessionName = "Transaction";
  protected String transactionsInSessionName = "Transactions";
  protected String regTransactionsInSessionName = "RegisterTransactions";
  protected String imagesInSessionName = "ImageResults";
  private String Wt = null;
  private String Wu = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.nextURL = this.taskErrorURL;
    this.error = 0;
    try
    {
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      if (this.initFlag)
      {
        this.initFlag = false;
        initialize(localHttpSession);
        return this.nextURL;
      }
      if (!this.initialized)
      {
        this.error = 124;
        return this.taskErrorURL;
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      if (localSecureUser == null)
      {
        this.error = 38;
        return this.taskErrorURL;
      }
      if ((this.module == null) || (this.module.equals(""))) {
        this.module = paramHttpServletRequest.getParameter("MODULE");
      }
      if ((this.module == null) || (this.module.equals("")))
      {
        this.error = 400036;
        this.nextURL = this.taskErrorURL;
        return this.nextURL;
      }
      if (this.module.equalsIgnoreCase("IMAGESEARCH"))
      {
        this.nextURL = jdMethod_null(localHttpSession, localSecureUser);
      }
      else if (this.module.equalsIgnoreCase("LOCKBOX"))
      {
        this.nextURL = jdMethod_new(localHttpSession, localSecureUser);
      }
      else if (this.module.equalsIgnoreCase("DEPOSIT"))
      {
        this.nextURL = jdMethod_byte(localHttpSession, localSecureUser);
      }
      else if (this.module.equalsIgnoreCase("OFFSET"))
      {
        this.nextURL = jdMethod_try(localHttpSession, localSecureUser);
      }
      else if (this.module.equalsIgnoreCase("ONLINEOFFSET"))
      {
        this.nextURL = jdMethod_long(localHttpSession, localSecureUser);
      }
      else if (this.module.equalsIgnoreCase("TRANSACTION"))
      {
        this.nextURL = jdMethod_case(localHttpSession, localSecureUser);
      }
      else if (this.module.equalsIgnoreCase("POSITIVEPAY"))
      {
        this.nextURL = jdMethod_char(localHttpSession, localSecureUser);
      }
      else if (this.module.equalsIgnoreCase("REGTRANSACTION"))
      {
        this.nextURL = jdMethod_else(localHttpSession, localSecureUser);
      }
      else if ("OnlineStatements".equalsIgnoreCase(this.module))
      {
        this.nextURL = jdMethod_goto(localHttpSession, localSecureUser);
      }
      else
      {
        this.error = 38;
        return this.taskErrorURL;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.out);
    }
    return this.nextURL;
  }
  
  private String jdMethod_null(HttpSession paramHttpSession, SecureUser paramSecureUser)
  {
    this.nextURL = this.taskErrorURL;
    this.error = 0;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    try
    {
      HashMap localHashMap1 = null;
      HashMap localHashMap2 = CheckImaging.getConfigProperties(paramSecureUser, localHashMap1);
      this.tag = ((XMLTag)localHashMap2.get("TRACEID_ERRORS"));
      this.searchDate = ((String)localHashMap2.get("DO_NOT_SEARCH_BEFORE_DATE"));
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    this.traceIDFrom = reformatTraceID(this.traceIDFrom);
    this.traceIDTo = reformatTraceID(this.traceIDTo);
    boolean bool = false;
    bool = validateInput(paramHttpSession);
    if (bool)
    {
      paramHttpSession.setAttribute("ImageRequest", this);
      this.nextURL = this.successURL;
    }
    return this.nextURL;
  }
  
  private String jdMethod_case(HttpSession paramHttpSession, SecureUser paramSecureUser)
  {
    if ((this.transID != null) && (!this.transID.equals("")))
    {
      Account localAccount = (Account)paramHttpSession.getAttribute(this.accountInSessionName);
      Transactions localTransactions = (Transactions)paramHttpSession.getAttribute(this.transactionsInSessionName);
      if (localAccount != null)
      {
        if (localTransactions != null)
        {
          Transaction localTransaction = localTransactions.getByID(this.transID);
          paramHttpSession.setAttribute(this.transactionInSessionName, localTransaction);
          ImageRequest localImageRequest = new ImageRequest(this.locale);
          localImageRequest.setAccountID(localAccount.getID());
          String str2 = localTransaction.getDateFormat();
          String str3 = ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", this.locale);
          localTransaction.setDateFormat(str3);
          localImageRequest.setPostingDateFrom(localTransaction.getDateValue().toString());
          localImageRequest.setPostingDateTo(localTransaction.getDateValue().toString());
          int i = 0;
          String str1 = localTransaction.getAmount();
          if (str1.charAt(0) != '(') {
            i = 1;
          }
          str1 = stripChars(str1);
          if ((str1 != null) && (!str1.equals("")) && (i == 1)) {
            localImageRequest.setTransType("C");
          } else {
            localImageRequest.setTransType("D");
          }
          if (localTransaction.getTypeValue() == 3)
          {
            localImageRequest.setCheckNumberTo(localTransaction.getReferenceNumber());
            localImageRequest.setCheckNumberFrom(localTransaction.getReferenceNumber());
          }
          localImageRequest.setAmountFrom(str1);
          localImageRequest.setAmountTo(str1);
          localImageRequest.setOnUs("O");
          paramHttpSession.setAttribute("ImageRequest", localImageRequest);
          this.nextURL = this.successURL;
        }
        else
        {
          this.error = 400039;
          this.nextURL = this.taskErrorURL;
        }
      }
      else
      {
        this.error = 400023;
        this.nextURL = this.taskErrorURL;
      }
    }
    else
    {
      this.error = 400038;
      this.nextURL = this.taskErrorURL;
    }
    return this.nextURL;
  }
  
  private String jdMethod_goto(HttpSession paramHttpSession, SecureUser paramSecureUser)
  {
    if ((this.transID != null) && (!this.transID.equals("")))
    {
      Account localAccount = (Account)paramHttpSession.getAttribute(this.accountInSessionName);
      Transactions localTransactions = (Transactions)paramHttpSession.getAttribute(this.transactionsInSessionName);
      if (localAccount != null)
      {
        if (localTransactions != null)
        {
          Transaction localTransaction = localTransactions.getByID(this.transID);
          paramHttpSession.setAttribute(this.transactionInSessionName, localTransaction);
          ImageRequest localImageRequest = new ImageRequest(this.locale);
          localImageRequest.setAccountID(localAccount.getID());
          String str2 = ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", this.locale);
          localTransaction.setDateFormat(str2);
          localImageRequest.setPostingDateFrom(localTransaction.getDateValue().toString());
          localImageRequest.setPostingDateTo(localTransaction.getDateValue().toString());
          localImageRequest.setTransType("D");
          localImageRequest.setCheckNumberTo(localTransaction.getReferenceNumber());
          localImageRequest.setCheckNumberFrom(localTransaction.getReferenceNumber());
          String str1 = localTransaction.getAmount();
          localImageRequest.setAmountFrom(str1);
          localImageRequest.setAmountTo(str1);
          paramHttpSession.setAttribute("ImageRequest", localImageRequest);
          this.nextURL = this.successURL;
        }
        else
        {
          this.error = 400039;
          this.nextURL = this.taskErrorURL;
        }
      }
      else
      {
        this.error = 400023;
        this.nextURL = this.taskErrorURL;
      }
    }
    else
    {
      this.error = 400038;
      this.nextURL = this.taskErrorURL;
    }
    return this.nextURL;
  }
  
  private String jdMethod_long(HttpSession paramHttpSession, SecureUser paramSecureUser)
  {
    HashMap localHashMap = null;
    Account localAccount = (Account)paramHttpSession.getAttribute(this.accountInSessionName);
    Transactions localTransactions = (Transactions)paramHttpSession.getAttribute(this.transactionsInSessionName);
    if (localAccount != null)
    {
      if (localTransactions != null)
      {
        if ((this.transID != null) && (!this.transID.equals("")))
        {
          ImageResults localImageResults1 = new ImageResults(this.locale);
          ImageResults localImageResults2 = new ImageResults(this.locale);
          ImageRequest localImageRequest = new ImageRequest(this.locale);
          Transaction localTransaction = localTransactions.getByID(this.transID);
          localImageRequest.setAccountID(localAccount.getID());
          String str1 = localTransaction.getDateFormat();
          String str2 = ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", this.locale);
          localTransaction.setDateFormat(str2);
          localImageRequest.setPostingDateFrom(localTransaction.getDateValue().toString());
          localImageRequest.setPostingDateTo(localTransaction.getDateValue().toString());
          String str3 = stripChars(localTransaction.getAmount());
          localImageRequest.setTransType("C");
          localImageRequest.setAmountFrom(str3);
          localImageRequest.setAmountTo(str3);
          localImageRequest.setOnUs("O");
          try
          {
            localImageResults1 = CheckImaging.getImageIndex(paramSecureUser, localImageRequest, localHashMap);
          }
          catch (CSILException localCSILException1)
          {
            this.error = MapError.mapError(localCSILException1);
            return this.serviceErrorURL;
          }
          Iterator localIterator = localImageResults1.iterator();
          ImageResult localImageResult = (ImageResult)localIterator.next();
          this.imageID = localImageResult.getImageHandle();
          if ((this.imageID != null) && (!this.imageID.equals("")))
          {
            localImageRequest = new ImageRequest(this.locale);
            localImageResults1 = new ImageResults(this.locale);
            String str4 = localImageResult.getSequenceNumber();
            localImageRequest.setTransType("D");
            localImageRequest.setDepositTraceId(str4);
            str1 = localImageRequest.getDateFormat();
            str2 = ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", this.locale);
            localImageRequest.setDateFormat(str2);
            localImageRequest.setPostingDateFrom(localImageResult.getPostingDate());
            localImageRequest.setPostingDateTo(localImageResult.getPostingDate());
            localImageRequest.setDateFormat(str1);
            try
            {
              localImageResults2 = CheckImaging.getImageIndex(paramSecureUser, localImageRequest, localHashMap);
            }
            catch (CSILException localCSILException2)
            {
              this.error = MapError.mapError(localCSILException2);
              this.nextURL = this.serviceErrorURL;
              return this.nextURL;
            }
            localImageResults2.add(localImageResult);
            paramHttpSession.setAttribute("OffsetResults", localImageResults2);
            this.nextURL = this.successURL;
          }
        }
        else
        {
          this.error = 400038;
          this.nextURL = this.taskErrorURL;
        }
      }
      else
      {
        this.error = 400039;
        this.nextURL = this.taskErrorURL;
      }
    }
    else
    {
      this.error = 400023;
      this.nextURL = this.taskErrorURL;
    }
    return this.nextURL;
  }
  
  private String jdMethod_try(HttpSession paramHttpSession, SecureUser paramSecureUser)
  {
    HashMap localHashMap = null;
    ImageResults localImageResults1 = (ImageResults)paramHttpSession.getAttribute(this.imagesInSessionName);
    if (localImageResults1 != null)
    {
      if ((this.imageID != null) && (!this.imageID.equals("")))
      {
        ImageResults localImageResults2 = new ImageResults(this.locale);
        ImageRequest localImageRequest = new ImageRequest(this.locale);
        ImageResult localImageResult = localImageResults1.getByHandle(this.imageID);
        String str3 = localImageResult.getDrCr();
        String str4 = localImageResult.getSequenceNumber();
        String str5 = localImageResult.getDepositTraceID();
        if ((str3 != null) && (str3.equals("Credit")))
        {
          if ((str4 != null) && (!str4.equals("")))
          {
            localImageRequest.setTransType("D");
            localImageRequest.setDepositTraceId(str4);
          }
          else
          {
            localImageRequest.setTransType("C");
            localImageRequest.setTraceIDFrom(str5);
            localImageRequest.setTraceIDTo(str5);
          }
        }
        else
        {
          localImageRequest.setTransType("D");
          localImageRequest.setDepositTraceId(str4);
        }
        String str1 = localImageRequest.getDateFormat();
        String str2 = ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", this.locale);
        localImageRequest.setDateFormat(str2);
        localImageRequest.setPostingDateFrom(localImageResult.getPostingDate());
        localImageRequest.setPostingDateTo(localImageResult.getPostingDate());
        localImageRequest.setDateFormat(str1);
        try
        {
          localImageResults2 = CheckImaging.getImageIndex(paramSecureUser, localImageRequest, localHashMap);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          return this.serviceErrorURL;
        }
        localImageResults2.add(localImageResult);
        paramHttpSession.setAttribute("OffsetResults", localImageResults2);
        paramHttpSession.setAttribute("ImageResult", localImageResult);
        this.nextURL = this.successURL;
      }
      else
      {
        this.error = 400037;
        this.nextURL = this.taskErrorURL;
      }
    }
    else
    {
      this.error = 400030;
      this.nextURL = this.taskErrorURL;
    }
    return this.nextURL;
  }
  
  private String jdMethod_byte(HttpSession paramHttpSession, SecureUser paramSecureUser)
  {
    try
    {
      HashMap localHashMap = null;
      ImageResults localImageResults1 = (ImageResults)paramHttpSession.getAttribute(this.imagesInSessionName);
      if (localImageResults1 != null)
      {
        if ((this.imageID != null) && (!this.imageID.equals("")))
        {
          if (this.initFlag)
          {
            Iterator localIterator1 = this.imageIDList.iterator();
            while (localIterator1.hasNext()) {
              this.depositResults.add(localImageResults1.getByHandle((String)localIterator1.next()));
            }
            paramHttpSession.setAttribute("DepositResults", this.depositResults);
            this.initFlag = false;
            this.nextURL = this.successURL;
          }
          else if (validateInput(paramHttpSession))
          {
            int i = 0;
            ImageResults localImageResults2 = new ImageResults(this.locale);
            ImageResult localImageResult1 = null;
            Iterator localIterator2 = this.depositResults.iterator();
            while (localIterator2.hasNext())
            {
              i += 1;
              ImageResults localImageResults3 = new ImageResults(this.locale);
              localImageResult1 = (ImageResult)localIterator2.next();
              this.transType = "D";
              this.depositTraceId = localImageResult1.getSequenceNumber();
              String str1 = localImageResult1.getDateFormat();
              String str2 = ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", this.locale);
              localImageResult1.setDateFormat(str2);
              setPostingDateFrom(localImageResult1.getPostingDate());
              setPostingDateTo(localImageResult1.getPostingDate());
              localImageResult1.setDateFormat(str1);
              localImageResult1.setDepositID("(" + String.valueOf(i) + ")");
              try
              {
                localImageResults3 = CheckImaging.getImageIndex(paramSecureUser, this, localHashMap);
                Iterator localIterator3 = localImageResults3.iterator();
                while (localIterator3.hasNext())
                {
                  ImageResult localImageResult2 = (ImageResult)localIterator3.next();
                  localImageResult2.setDepositID(localImageResult1.getDepositID());
                  localImageResults2.add(localImageResult2);
                }
                localImageResults2.add(localImageResult1);
              }
              catch (CSILException localCSILException)
              {
                this.error = MapError.mapError(localCSILException);
                this.nextURL = this.serviceErrorURL;
                return this.nextURL;
              }
            }
            if ((localImageResults2 != null) && (!localImageResults2.isEmpty()))
            {
              paramHttpSession.setAttribute("OffsetResults", localImageResults2);
              this.nextURL = this.successURL;
            }
            else
            {
              this.nextURL = this.serviceErrorURL;
            }
          }
          else
          {
            this.nextURL = this.taskErrorURL;
          }
        }
        else
        {
          this.error = 400037;
          this.nextURL = this.taskErrorURL;
        }
      }
      else
      {
        this.error = 400030;
        this.nextURL = this.taskErrorURL;
      }
    }
    catch (Exception localException) {}
    return this.nextURL;
  }
  
  private String jdMethod_new(HttpSession paramHttpSession, SecureUser paramSecureUser)
  {
    int i = 0;
    if ((this.itemID == null) || (this.itemID.length() <= 0))
    {
      this.error = 400034;
      return this.taskErrorURL;
    }
    LockboxCreditItems localLockboxCreditItems = (LockboxCreditItems)paramHttpSession.getAttribute("LOCKBOX_CREDIT_ITEMS");
    if ((localLockboxCreditItems == null) || (localLockboxCreditItems.isEmpty()))
    {
      this.error = 400035;
      return this.taskErrorURL;
    }
    LockboxCreditItem localLockboxCreditItem = null;
    try
    {
      int j = Integer.parseInt(this.itemID);
      for (int k = 0; k < localLockboxCreditItems.size(); k++) {
        if (((LockboxCreditItem)localLockboxCreditItems.get(k)).getItemID() == j)
        {
          localLockboxCreditItem = (LockboxCreditItem)localLockboxCreditItems.get(k);
          break;
        }
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 400044;
      return this.taskErrorURL;
    }
    if (localLockboxCreditItem == null)
    {
      this.error = 400033;
      return this.taskErrorURL;
    }
    try
    {
      paramHttpSession.setAttribute("LOCKBOX_CREDIT_ITEM", localLockboxCreditItem);
      ImageRequest localImageRequest = new ImageRequest(this.locale);
      localImageRequest.setAccountID(localLockboxCreditItem.getAccountID());
      if (localLockboxCreditItem.getCheckDate() == null)
      {
        this.error = 400045;
        return this.taskErrorURL;
      }
      String str2 = localLockboxCreditItem.getDateFormat();
      String str3 = "MM/dd/yyyy";
      localLockboxCreditItem.setDateFormat(str3);
      localImageRequest.setPostingDateFrom(localLockboxCreditItem.getCheckDate().toString());
      localImageRequest.setPostingDateTo(localLockboxCreditItem.getCheckDate().toString());
      localLockboxCreditItem.setDateFormat(str2);
      if (localLockboxCreditItem.getCheckAmount() == null)
      {
        this.error = 400046;
        return this.taskErrorURL;
      }
      String str1 = localLockboxCreditItem.getCheckAmount().toString();
      if (str1.charAt(0) != '(') {
        i = 1;
      }
      str1 = stripChars(str1);
      if ((str1 != null) && (!str1.equals("")) && (i == 1)) {
        localImageRequest.setTransType("C");
      } else {
        localImageRequest.setTransType("D");
      }
      localImageRequest.setCheckNumberTo(localLockboxCreditItem.getCheckNumber());
      localImageRequest.setCheckNumberFrom(localLockboxCreditItem.getCheckNumber());
      localImageRequest.setAmountFrom(str1);
      localImageRequest.setAmountTo(str1);
      localImageRequest.setOnUs("O");
      paramHttpSession.setAttribute("ImageRequest", localImageRequest);
      this.nextURL = this.successURL;
    }
    catch (Exception localException)
    {
      DebugLog.throwing("SearchImage.lockboxSearchImage", localException);
    }
    return this.nextURL;
  }
  
  private String jdMethod_char(HttpSession paramHttpSession, SecureUser paramSecureUser)
  {
    int i = 0;
    if ((this.pPayAccountID == null) && (this.pPayAccountID.equals("")))
    {
      this.error = 400023;
      this.nextURL = this.taskErrorURL;
      return this.nextURL;
    }
    if ((this.pPayBankID == null) && (this.pPayBankID.equals("")))
    {
      this.error = 400040;
      this.nextURL = this.taskErrorURL;
      return this.nextURL;
    }
    if ((this.pPayCheckNumber == null) && (this.pPayCheckNumber.equals("")))
    {
      this.error = 400041;
      this.nextURL = this.taskErrorURL;
      return this.nextURL;
    }
    PPayIssues localPPayIssues = (PPayIssues)paramHttpSession.getAttribute("PPayIssues");
    if (localPPayIssues != null)
    {
      PPayIssue localPPayIssue = null;
      PPayCheckRecord localPPayCheckRecord = null;
      for (int j = 0; j < localPPayIssues.size(); j++)
      {
        localPPayIssue = (PPayIssue)localPPayIssues.get(j);
        if (localPPayIssue != null)
        {
          localPPayCheckRecord = localPPayIssue.getCheckRecord();
          if ((localPPayCheckRecord.getAccountID().equals(this.pPayAccountID)) && (localPPayCheckRecord.getBankID().equals(this.pPayBankID)) && (localPPayCheckRecord.getCheckNumber().equals(this.pPayCheckNumber))) {
            break;
          }
        }
      }
      if (localPPayCheckRecord != null)
      {
        paramHttpSession.setAttribute("PPayIssue", localPPayIssue);
        ImageRequest localImageRequest = new ImageRequest(this.locale);
        localImageRequest.setAccountID(localPPayCheckRecord.getAccountID());
        String str2 = localPPayCheckRecord.getDateFormat();
        String str3 = ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", this.locale);
        localPPayCheckRecord.setDateFormat(str3);
        localImageRequest.setPostingDateFrom(localPPayCheckRecord.getCheckDate().toString());
        localImageRequest.setPostingDateTo(localPPayCheckRecord.getCheckDate().toString());
        localPPayCheckRecord.setDateFormat(str2);
        String str1 = localPPayCheckRecord.getAmount().toString();
        if (str1.charAt(0) != '(') {
          i = 1;
        }
        str1 = stripChars(str1);
        if ((str1 != null) && (!str1.equals("")) && (i == 1)) {
          localImageRequest.setTransType("C");
        } else {
          localImageRequest.setTransType("D");
        }
        localImageRequest.setCheckNumberTo(localPPayCheckRecord.getCheckNumber());
        localImageRequest.setCheckNumberFrom(localPPayCheckRecord.getCheckNumber());
        localImageRequest.setAmountFrom(str1);
        localImageRequest.setAmountTo(str1);
        localImageRequest.setOnUs("O");
        paramHttpSession.setAttribute("ImageRequest", localImageRequest);
        this.nextURL = this.successURL;
      }
      else
      {
        this.error = 400042;
        this.nextURL = this.taskErrorURL;
      }
    }
    else
    {
      this.error = 400043;
      this.nextURL = this.taskErrorURL;
    }
    return this.nextURL;
  }
  
  private String jdMethod_else(HttpSession paramHttpSession, SecureUser paramSecureUser)
  {
    if ((this.transID != null) && (!this.transID.equals("")))
    {
      Account localAccount = (Account)paramHttpSession.getAttribute(this.accountInSessionName);
      RegisterTransactions localRegisterTransactions = (RegisterTransactions)paramHttpSession.getAttribute(this.regTransactionsInSessionName);
      if (localAccount != null)
      {
        if (localRegisterTransactions != null)
        {
          RegisterTransaction localRegisterTransaction = localRegisterTransactions.getById(this.transID);
          paramHttpSession.setAttribute(this.transactionInSessionName, localRegisterTransaction);
          ImageRequest localImageRequest = new ImageRequest(this.locale);
          localImageRequest.setAccountID(localAccount.getID());
          String str2 = localRegisterTransaction.getDateFormat();
          String str3 = ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", this.locale);
          localRegisterTransaction.setDateFormat(str3);
          localImageRequest.setPostingDateFrom(localRegisterTransaction.getDateValue().toString());
          localImageRequest.setPostingDateTo(localRegisterTransaction.getDateValue().toString());
          int i = 0;
          String str1 = localRegisterTransaction.getAmount();
          if (str1.charAt(0) != '(') {
            i = 1;
          }
          str1 = stripChars(str1);
          if ((str1 != null) && (!str1.equals("")) && (i == 1)) {
            localImageRequest.setTransType("C");
          } else {
            localImageRequest.setTransType("D");
          }
          if (localRegisterTransaction.getTypeValue() == 3)
          {
            localImageRequest.setCheckNumberTo(localRegisterTransaction.getReferenceNumber());
            localImageRequest.setCheckNumberFrom(localRegisterTransaction.getReferenceNumber());
          }
          localImageRequest.setAmountFrom(str1);
          localImageRequest.setAmountTo(str1);
          localImageRequest.setOnUs("O");
          paramHttpSession.setAttribute("ImageRequest", localImageRequest);
          this.nextURL = this.successURL;
        }
        else
        {
          this.error = 400039;
          this.nextURL = this.taskErrorURL;
        }
      }
      else
      {
        this.error = 400023;
        this.nextURL = this.taskErrorURL;
      }
    }
    else
    {
      this.error = 400038;
      this.nextURL = this.taskErrorURL;
    }
    return this.nextURL;
  }
  
  protected String reformatTraceID(String paramString)
  {
    int i = 10;
    int j = 5;
    String str = "";
    if ((paramString != null) && (!paramString.equals("")) && (paramString.length() == i) && (paramString.charAt(j) == '0'))
    {
      for (int k = 0; k < i; k++) {
        if ((k != 0) && (k != j)) {
          str = str + paramString.charAt(k);
        }
      }
      return str;
    }
    return paramString;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null) {
      bool = validationCheck(paramHttpSession);
    }
    if (bool)
    {
      if (this.verifyFormat != null) {
        bool = verifyFormatCheck(paramHttpSession);
      }
    }
    else {
      this.verifyFormat = null;
    }
    if (bool) {
      bool = validateDateRange();
    }
    return bool;
  }
  
  protected boolean validationCheck(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null)
    {
      if ((this.validate.indexOf("MINIMUMFIELDS") != -1) && (this.minFields != null) && (!checkMinFields())) {
        bool = setError(400022, paramHttpSession);
      }
      if ((this.validate.indexOf("POSTINGDATETO") != -1) && (this.postingDateTo == null)) {
        bool = setError(400017, paramHttpSession);
      }
      if ((this.validate.indexOf("POSTINGDATEFROM") != -1) && (this.postingDateFrom == null)) {
        bool = setError(400016, paramHttpSession);
      }
      if ((this.accountID == null) || (this.accountID.equals(""))) {
        bool = setError(400023, paramHttpSession);
      }
    }
    return bool;
  }
  
  protected boolean verifyFormatCheck(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if ((this.verifyFormat.indexOf("ACCOUNTID") != -1) && (this.accountID != null) && (!this.accountID.equals("")) && (!validateAccountID())) {
      return false;
    }
    if ((this.verifyFormat.indexOf("POSTINGDATEFROM") != -1) && (this.postingDateFrom != null) && (!this.postingDateFrom.equals("")) && (!validatePostingDateFrom(this.postingDateFrom.toString()))) {
      return false;
    }
    if ((this.verifyFormat.indexOf("POSTINGDATETO") != -1) && (this.postingDateTo != null) && (!this.postingDateTo.equals("")) && (!validatePostingDateTo(this.postingDateTo.toString()))) {
      return false;
    }
    if ((this.verifyFormat.indexOf("TRACEIDFROM") != -1) && (this.traceIDFrom != null) && (!this.traceIDFrom.equals("")) && (!validateTraceIDFrom(this.traceIDFrom))) {
      return false;
    }
    if ((this.verifyFormat.indexOf("TRACEIDTO") != -1) && (this.traceIDTo != null) && (!this.traceIDTo.equals("")) && (!validateTraceIDTo(this.traceIDTo))) {
      return false;
    }
    if ((this.verifyFormat.indexOf("AMOUNTFROM") != -1) && (this.Wt != null) && (!this.Wt.equals("")) && (!validateAmount(this.amountFrom, this.Wt)))
    {
      bool = setError(400018, paramHttpSession);
      return false;
    }
    if ((this.verifyFormat.indexOf("AMOUNTTO") != -1) && (this.Wu != null) && (!this.Wu.equals("")) && (!validateAmount(this.amountTo, this.Wu)))
    {
      bool = setError(400019, paramHttpSession);
      return false;
    }
    if ((this.verifyFormat.indexOf("CHECKNUMBERFROM") != -1) && (this.checkNumberFrom != null) && (!this.checkNumberFrom.equals("")) && (!validateCheckNumberFrom(this.checkNumberFrom))) {
      return false;
    }
    if ((this.verifyFormat.indexOf("CHECKNUMBERTO") != -1) && (this.checkNumberTo != null) && (!this.checkNumberTo.equals("")) && (!validateCheckNumberTo(this.checkNumberTo))) {
      return false;
    }
    if ((this.verifyFormat.indexOf("ROUTINGTRANSITNUMBER") != -1) && (this.routingTransitNumber != null) && (!this.routingTransitNumber.equals("")) && (!validateRoutingTransitNumber(this.routingTransitNumber))) {
      return false;
    }
    if ((this.verifyFormat.indexOf("DEPOSITTRACEID") != -1) && (this.depositTraceId != null) && (!this.depositTraceId.equals("")) && (!validateDepositTraceID(this.depositTraceId))) {
      return false;
    }
    return bool;
  }
  
  protected boolean validateDateRange()
  {
    if ((this.validate.indexOf("POSTINGDATEFROM") != -1) && (this.postingDateFrom != null) && (!this.postingDateFrom.equals("")) && (this.validate.indexOf("POSTINGDATETO") != -1) && (this.postingDateTo != null) && (!this.postingDateTo.equals(""))) {
      try
      {
        com.ffusion.util.beans.DateTime localDateTime1 = new com.ffusion.util.beans.DateTime(this.postingDateFrom.toString(), this.locale, this.datetype);
        com.ffusion.util.beans.DateTime localDateTime2 = new com.ffusion.util.beans.DateTime(this.postingDateTo.toString(), this.locale, this.datetype);
        if (localDateTime1.compare(localDateTime2) > 0)
        {
          this.error = 400047;
          return false;
        }
      }
      catch (Exception localException)
      {
        return false;
      }
    }
    return true;
  }
  
  protected boolean validateAccountID()
  {
    if ((this.accountID == null) || (this.accountID.equals("")))
    {
      this.error = 400023;
      return false;
    }
    return true;
  }
  
  protected boolean validatePostingDateFrom(String paramString)
  {
    if (!validateDate(paramString))
    {
      this.error = 400016;
      return false;
    }
    if (!checkSearchDate(paramString))
    {
      this.error = 400020;
      return false;
    }
    return true;
  }
  
  protected boolean validatePostingDateTo(String paramString)
  {
    if (!validateDate(paramString))
    {
      this.error = 400017;
      return false;
    }
    if (!checkSearchDate(paramString))
    {
      this.error = 400020;
      return false;
    }
    return true;
  }
  
  protected boolean validateTraceIDFrom(String paramString)
  {
    boolean bool = true;
    if (!checkNumeric(paramString))
    {
      this.error = 400003;
      return false;
    }
    if ((paramString.length() != 8) && (paramString.length() != 12))
    {
      this.error = 400004;
      return false;
    }
    String str1 = "";
    for (int i = 0; i < 2; i++)
    {
      char c = paramString.charAt(i);
      str1 = str1 + c;
    }
    try
    {
      ArrayList localArrayList = this.tag.getContainedTagList();
      Iterator localIterator = localArrayList.iterator();
      while ((localIterator.hasNext()) && (bool == true))
      {
        XMLTag localXMLTag = (XMLTag)localIterator.next();
        String str2 = localXMLTag.getTagContent();
        String str3 = str2.substring(0, 2);
        String str4 = str2.substring(3, str2.length());
        if (str1.equalsIgnoreCase(str3))
        {
          this.error = Integer.parseInt(str4);
          bool = false;
        }
      }
    }
    catch (Exception localException)
    {
      this.error = 400007;
      bool = false;
    }
    return bool;
  }
  
  protected boolean validateTraceIDTo(String paramString)
  {
    boolean bool = true;
    if (!checkNumeric(paramString))
    {
      this.error = 400005;
      return false;
    }
    if ((paramString.length() != 8) && (paramString.length() != 12))
    {
      this.error = 400006;
      return false;
    }
    String str1 = "";
    for (int i = 0; i < 2; i++)
    {
      char c = paramString.charAt(i);
      str1 = str1 + c;
    }
    try
    {
      ArrayList localArrayList = this.tag.getContainedTagList();
      Iterator localIterator = localArrayList.iterator();
      while ((localIterator.hasNext()) && (bool == true))
      {
        XMLTag localXMLTag = (XMLTag)localIterator.next();
        String str2 = localXMLTag.getTagContent();
        String str3 = str2.substring(0, 2);
        String str4 = str2.substring(3, str2.length());
        if (str1.equalsIgnoreCase(str3))
        {
          this.error = Integer.parseInt(str4);
          bool = false;
        }
      }
    }
    catch (Exception localException)
    {
      this.error = 400007;
      bool = false;
    }
    return bool;
  }
  
  protected boolean validateAmount(Currency paramCurrency)
  {
    return true;
  }
  
  protected boolean validateAmount(Currency paramCurrency, String paramString)
  {
    boolean bool = true;
    if ((paramCurrency != null) && (paramString != null) && (!paramCurrency.validateCurrency(paramString))) {
      bool = false;
    }
    return bool;
  }
  
  protected boolean validateCheckNumberFrom(String paramString)
  {
    if (!checkNumeric(paramString))
    {
      this.error = 400008;
      return false;
    }
    return true;
  }
  
  protected boolean validateCheckNumberTo(String paramString)
  {
    if (!checkNumeric(paramString))
    {
      this.error = 400010;
      return false;
    }
    return true;
  }
  
  protected boolean validateRoutingTransitNumber(String paramString)
  {
    boolean bool = false;
    try
    {
      bool = CommBankIdentifier.isValid(paramString);
    }
    catch (Exception localException)
    {
      bool = false;
    }
    return bool;
  }
  
  protected boolean checkNumDigits(String paramString, int paramInt)
  {
    return (paramString.length() != 0) && (paramString.length() == paramInt);
  }
  
  protected boolean validateDepositTraceID(String paramString)
  {
    if (!checkNumeric(paramString))
    {
      this.error = 400014;
      return false;
    }
    if (!checkNumDigits(paramString, 8))
    {
      this.error = 400015;
      return false;
    }
    return true;
  }
  
  protected boolean checkNumeric(String paramString)
  {
    int i = paramString.length();
    for (int j = 0; j < i; j++) {
      if (!Character.isDigit(paramString.charAt(j))) {
        return false;
      }
    }
    return true;
  }
  
  protected boolean validateDate(String paramString)
  {
    try
    {
      com.ffusion.util.beans.DateTime localDateTime = new com.ffusion.util.beans.DateTime(paramString, this.locale, this.datetype);
    }
    catch (Exception localException)
    {
      return false;
    }
    return true;
  }
  
  protected boolean checkSearchDate(String paramString)
  {
    String str = "MM/dd/yyyy";
    try
    {
      SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat(str);
      SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat(ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", this.locale));
      Date localDate1 = localSimpleDateFormat1.parse(this.searchDate);
      Date localDate2 = localSimpleDateFormat2.parse(paramString);
      if (localDate2.before(localDate1)) {
        return false;
      }
    }
    catch (Exception localException)
    {
      return false;
    }
    return true;
  }
  
  protected boolean checkMinFields()
  {
    if (((this.minFields != null ? 1 : 0) & (!this.minFields.equals("0") ? 1 : 0)) != 0)
    {
      int i = Integer.valueOf(this.minFields).intValue();
      int j = 0;
      if ((this.accountID != null) && (!this.accountID.equals(""))) {
        j++;
      }
      if ((this.depositAccountID != null) && (!this.depositAccountID.equals(""))) {
        j++;
      }
      if ((this.postingDateFrom != null) && (!this.postingDateFrom.equals(""))) {
        j++;
      }
      if ((this.postingDateTo != null) && (!this.postingDateTo.equals(""))) {
        j++;
      }
      if ((this.traceIDFrom != null) && (!this.traceIDFrom.equals(""))) {
        j++;
      }
      if ((this.traceIDTo != null) && (!this.traceIDTo.equals(""))) {
        j++;
      }
      if ((this.amountFrom != null) && (!this.amountFrom.equals(""))) {
        j++;
      }
      if ((this.amountTo != null) && (!this.amountTo.equals(""))) {
        j++;
      }
      if ((this.routingTransitNumber != null) && (!this.routingTransitNumber.equals(""))) {
        j++;
      }
      if ((this.depositTraceId != null) && (!this.depositTraceId.equals(""))) {
        j++;
      }
      if (j < i) {
        return false;
      }
    }
    return true;
  }
  
  public void setImagesInSessionName(String paramString)
  {
    this.imagesInSessionName = paramString;
  }
  
  public String getImagesInSessionName()
  {
    return this.imagesInSessionName;
  }
  
  public void setAccountInSessionName(String paramString)
  {
    this.accountInSessionName = paramString;
  }
  
  public String getAccountInSessionName()
  {
    return this.accountInSessionName;
  }
  
  public void setTransactionInSessionName(String paramString)
  {
    this.transactionInSessionName = paramString;
  }
  
  public String getTransactionInSessionName()
  {
    return this.transactionInSessionName;
  }
  
  public void setTransactionsInSessionName(String paramString)
  {
    this.transactionsInSessionName = paramString;
  }
  
  public String getTransactionsInSessionName()
  {
    return this.transactionsInSessionName;
  }
  
  public void setRegTransactionsInSessionName(String paramString)
  {
    this.regTransactionsInSessionName = paramString;
  }
  
  public String getRegTransactionsInSessionName()
  {
    return this.regTransactionsInSessionName;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTraceIdErrorURL(String paramString)
  {
    this.traceIdErrorURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!paramString.equalsIgnoreCase("")) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setVerifyFormat(String paramString)
  {
    this.verifyFormat = null;
    if (!paramString.equalsIgnoreCase("")) {
      this.verifyFormat = paramString.toUpperCase();
    }
  }
  
  public void setMinimumFields(String paramString)
  {
    this.minFields = paramString;
  }
  
  public boolean setError(int paramInt, HttpSession paramHttpSession)
  {
    this.error = paramInt;
    return false;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public String getSuccessURL()
  {
    return this.successURL;
  }
  
  public void setImageID(ArrayList paramArrayList)
  {
    this.imageIDList = paramArrayList;
  }
  
  public ArrayList getImageID()
  {
    return this.imageIDList;
  }
  
  public void setInit(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getTransID()
  {
    return this.transID;
  }
  
  public void setTransID(String paramString)
  {
    this.transID = paramString;
  }
  
  public void setImageID(String paramString)
  {
    this.imageID = paramString;
  }
  
  public boolean setError(int paramInt)
  {
    this.error = paramInt;
    return false;
  }
  
  public String stripChars(String paramString)
  {
    String str = "";
    for (int i = 0; i < paramString.length(); i++)
    {
      char c = paramString.charAt(i);
      if (((c >= '0') && (c <= '9')) || (c == '.')) {
        str = str + c;
      }
    }
    return str;
  }
  
  public void setItemID(String paramString)
  {
    this.itemID = paramString;
  }
  
  public String getItemID()
  {
    return this.itemID;
  }
  
  public void setLocale(Locale paramLocale)
  {
    if (paramLocale == null) {
      this.locale = Locale.getDefault();
    } else {
      super.setLocale(paramLocale);
    }
    this.locale = paramLocale;
  }
  
  public Locale getLocale()
  {
    return this.locale;
  }
  
  public String getPPayAccountID()
  {
    return this.pPayAccountID;
  }
  
  public void setPPayAccountID(String paramString)
  {
    this.pPayAccountID = paramString;
  }
  
  public String getPPayBankID()
  {
    return this.pPayBankID;
  }
  
  public void setPPayBankID(String paramString)
  {
    this.pPayBankID = paramString;
  }
  
  public String getPPayCheckNumber()
  {
    return this.pPayCheckNumber;
  }
  
  public void setPPayCheckNumber(String paramString)
  {
    this.pPayCheckNumber = paramString;
  }
  
  public void setModule(String paramString)
  {
    this.module = paramString;
  }
  
  public String getModule()
  {
    return this.module;
  }
  
  protected void initialize(HttpSession paramHttpSession)
  {
    this.error = 0;
    this.nextURL = this.successURL;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      this.nextURL = this.taskErrorURL;
    }
    else
    {
      this.locale = localSecureUser.getLocale();
      this.initialized = true;
    }
  }
  
  public void setAmountFrom(String paramString)
  {
    if ((paramString == null) || (paramString.equals(""))) {
      this.Wt = null;
    } else {
      this.Wt = paramString.trim();
    }
    super.setAmountFrom(paramString);
  }
  
  public void setAmountTo(String paramString)
  {
    if ((paramString == null) || (paramString.equals(""))) {
      this.Wu = null;
    } else {
      this.Wu = paramString.trim();
    }
    super.setAmountTo(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.checkimaging.SearchImage
 * JD-Core Version:    0.7.0.1
 */