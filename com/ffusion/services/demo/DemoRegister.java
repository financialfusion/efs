package com.ffusion.services.demo;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.beans.register.RegisterCategories;
import com.ffusion.beans.register.RegisterCategory;
import com.ffusion.beans.register.RegisterPayee;
import com.ffusion.beans.register.RegisterPayees;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.beans.register.ServerTransaction;
import com.ffusion.efs.adapters.profile.Profile;
import com.ffusion.efs.adapters.profile.ProfileDefines;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

public class DemoRegister
  implements ProfileDefines
{
  private static final String ae7 = "demoRegister.xml";
  private String ae4 = null;
  private String ae2 = null;
  private ArrayList ae0 = null;
  private RegisterTransactions ae6 = null;
  private RegisterTransactions ae5 = null;
  private RegisterCategories ae3 = null;
  private RegisterPayees ae1 = null;
  private boolean aeY = false;
  private boolean aeZ = false;
  protected static HashMap m_InitHashMap;
  
  public void DemoRegister()
  {
    this.ae0 = new ArrayList();
    this.ae6 = new RegisterTransactions();
    this.ae5 = new RegisterTransactions();
    this.ae3 = new RegisterCategories();
    this.ae1 = new RegisterPayees();
  }
  
  public void addRegisterTransaction(RegisterTransaction paramRegisterTransaction)
  {
    Random localRandom = new Random();
    paramRegisterTransaction.setRegisterId(String.valueOf(localRandom.nextInt()));
    this.ae6.add(paramRegisterTransaction);
  }
  
  public void addRegisterTransactions(RegisterTransactions paramRegisterTransactions)
  {
    Iterator localIterator = paramRegisterTransactions.iterator();
    while (localIterator.hasNext()) {
      addRegisterTransaction((RegisterTransaction)localIterator.next());
    }
  }
  
  public void addUpdateRegisterTransactions(RegisterTransactions paramRegisterTransactions)
  {
    Iterator localIterator = paramRegisterTransactions.iterator();
    while (localIterator.hasNext()) {
      addRegisterTransaction((RegisterTransaction)localIterator.next());
    }
  }
  
  public void addBankTransactions(RegisterTransactions paramRegisterTransactions)
  {
    if (this.aeZ) {
      return;
    }
    this.ae5 = new RegisterTransactions();
    Random localRandom = new Random();
    Iterator localIterator = paramRegisterTransactions.iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
      localRegisterTransaction.setRegisterId(String.valueOf(localRandom.nextInt()));
      this.ae5.add(localRegisterTransaction);
    }
  }
  
  public void addRegisterCategory(RegisterCategory paramRegisterCategory)
  {
    Random localRandom = new Random();
    paramRegisterCategory.setId(String.valueOf(localRandom.nextInt()));
    this.ae3.add(paramRegisterCategory);
  }
  
  public void addSrvrTranRegisterCategory(ArrayList paramArrayList)
  {
    this.ae0.add(paramArrayList);
  }
  
  public void modifySrvrTranRegisterCategory(ServerTransaction paramServerTransaction)
  {
    Iterator localIterator = this.ae0.iterator();
    while (localIterator.hasNext())
    {
      ServerTransaction localServerTransaction = (ServerTransaction)localIterator.next();
      if (paramServerTransaction.getRecServerTID() == null)
      {
        if (localServerTransaction.getServerTID().equals(paramServerTransaction.getServerTID()))
        {
          localServerTransaction.setRegCategoryID(paramServerTransaction.getRegCategoryID());
          break;
        }
      }
      else if (localServerTransaction.getRecServerTID().equals(paramServerTransaction.getRecServerTID()))
      {
        localServerTransaction.setRegCategoryID(paramServerTransaction.getRegCategoryID());
        break;
      }
    }
  }
  
  public void deleteSrvrTranRegisterCategory(String paramString, boolean paramBoolean)
  {
    Iterator localIterator = this.ae0.iterator();
    while (localIterator.hasNext())
    {
      ServerTransaction localServerTransaction = (ServerTransaction)localIterator.next();
      if (paramBoolean)
      {
        if (localServerTransaction.getRecServerTID().equals(paramString)) {
          localIterator.remove();
        }
      }
      else if (localServerTransaction.getServerTID().equals(paramString)) {
        localIterator.remove();
      }
    }
  }
  
  public void addRegisterPayee(RegisterPayee paramRegisterPayee)
  {
    Random localRandom = new Random();
    paramRegisterPayee.setId(String.valueOf(localRandom.nextInt()));
    this.ae1.add(paramRegisterPayee);
  }
  
  protected void loadTransactions()
    throws ProfileException
  {
    try
    {
      Reader localReader = getXMLReader();
      if (localReader == null) {
        Profile.handleError("DemoRegister.loadTransactions()", "Unable to initialize xml reader", 1);
      }
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), localReader);
      this.aeY = true;
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, "DemoRegister.loadTransactions()");
    }
  }
  
  public void getRegisterTransactions(RegisterTransactions paramRegisterTransactions, DateTime paramDateTime1, DateTime paramDateTime2, boolean paramBoolean)
    throws ProfileException
  {
    RegisterTransaction localRegisterTransaction1 = (RegisterTransaction)paramRegisterTransactions.get(0);
    String str1 = (String)localRegisterTransaction1.get("ACCOUNT");
    String str2 = localRegisterTransaction1.getReferenceNumber();
    paramRegisterTransactions.clear();
    if ((str1 == null) || (str1.equals(""))) {
      Profile.handleError("DemoRegister.getRegisterTransactions", "Invalid account ID", 3811);
    }
    try
    {
      if ((this.ae4 == null) || (this.ae2 == null))
      {
        int i = str1.lastIndexOf("-");
        this.ae4 = str1.substring(0, i);
        this.ae2 = str1.substring(i + 1, str1.length());
      }
      if (!this.aeY) {
        loadTransactions();
      }
      if (paramDateTime1 != null)
      {
        paramDateTime1.set(11, 0);
        paramDateTime1.set(12, 0);
        paramDateTime1.set(13, 0);
        paramDateTime1.set(14, 0);
      }
      if (paramDateTime2 != null)
      {
        paramDateTime2.set(11, 23);
        paramDateTime2.set(12, 59);
        paramDateTime2.set(13, 59);
        paramDateTime2.set(14, 999);
      }
      DateTime localDateTime1 = new DateTime();
      int j = localDateTime1.get(1);
      int k = localDateTime1.get(2);
      Iterator localIterator = null;
      RegisterTransaction localRegisterTransaction2;
      RegisterTransaction localRegisterTransaction3;
      if (this.ae5 != null)
      {
        localIterator = this.ae5.iterator();
        while (localIterator.hasNext())
        {
          localRegisterTransaction2 = (RegisterTransaction)localIterator.next();
          localRegisterTransaction3 = new RegisterTransaction();
          localRegisterTransaction3.set(localRegisterTransaction2);
          if ((str2 != null) && (!str2.equals("")))
          {
            if (localRegisterTransaction2.getStatusValue() != 1) {
              continue;
            }
            localRegisterTransaction3.put("DATE_RANGE", "unknown");
          }
          else if ((paramDateTime1 != null) && (localRegisterTransaction2.getDateIssuedValue().before(paramDateTime1)))
          {
            if (localRegisterTransaction2.getStatusValue() != 1) {
              continue;
            }
            localRegisterTransaction3.put("DATE_RANGE", "before");
          }
          else if ((paramDateTime2 != null) && (localRegisterTransaction2.getDateIssuedValue().after(paramDateTime2)))
          {
            if (localRegisterTransaction2.getStatusValue() != 1) {
              continue;
            }
            localRegisterTransaction3.put("DATE_RANGE", "after");
          }
          else
          {
            localRegisterTransaction3.put("DATE_RANGE", "in");
          }
          this.ae6.add(localRegisterTransaction3);
        }
        this.aeZ = true;
        this.ae5 = null;
      }
      localIterator = this.ae6.iterator();
      while (localIterator.hasNext())
      {
        localRegisterTransaction2 = (RegisterTransaction)localIterator.next();
        localRegisterTransaction3 = null;
        DateTime localDateTime2 = localRegisterTransaction2.getDateIssuedValue();
        String str3 = "unknown";
        localDateTime2.set(1, j);
        localDateTime2.set(2, k);
        if ((str2 != null) && (!str2.equals("")))
        {
          if (str2.equals(localRegisterTransaction2.getReferenceNumber()))
          {
            localRegisterTransaction3 = new RegisterTransaction();
            str3 = "in";
          }
          else
          {
            if ((!paramBoolean) || ((localRegisterTransaction2.getStatusValue() != 0) && (localRegisterTransaction2.getStatusValue() != 1))) {
              continue;
            }
            localRegisterTransaction3 = new RegisterTransaction();
          }
        }
        else if (localDateTime2 != null)
        {
          if ((paramBoolean) && ((localRegisterTransaction2.getStatusValue() == 0) || (localRegisterTransaction2.getStatusValue() == 1)))
          {
            localRegisterTransaction3 = new RegisterTransaction();
            if ((paramDateTime1 != null) && (localDateTime2.before(paramDateTime1))) {
              str3 = "before";
            } else if ((paramDateTime2 != null) && (localDateTime2.after(paramDateTime2))) {
              str3 = "after";
            } else {
              str3 = "in";
            }
          }
          else
          {
            if (((paramDateTime1 != null) && (localRegisterTransaction2.getDateIssuedValue().before(paramDateTime1))) || ((paramDateTime2 != null) && (localRegisterTransaction2.getDateIssuedValue().after(paramDateTime2)))) {
              continue;
            }
            localRegisterTransaction3 = new RegisterTransaction();
            str3 = "in";
          }
        }
        else {
          localRegisterTransaction3 = new RegisterTransaction();
        }
        localRegisterTransaction3.set(localRegisterTransaction2);
        localRegisterTransaction3.put("DATE_RANGE", str3);
        paramRegisterTransactions.add(localRegisterTransaction3);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, "DemoRegister.getRegisterTransactions()");
    }
  }
  
  public void getRegisterTransaction(RegisterTransaction paramRegisterTransaction)
    throws ProfileException
  {
    if ((paramRegisterTransaction.getReferenceNumber() == null) || (paramRegisterTransaction.getReferenceNumber().equals(""))) {
      Profile.handleError("DemoRegister.getRegisterTransaction", "Invalid reference number.", 3811);
    }
    RegisterTransactions localRegisterTransactions = new RegisterTransactions();
    localRegisterTransactions.add(paramRegisterTransaction);
    getRegisterTransactions(localRegisterTransactions, null, null, false);
    Iterator localIterator = this.ae6.iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
      if ((localRegisterTransaction.getStatusValue() != 1) && (paramRegisterTransaction.getReferenceNumber().equals(localRegisterTransaction.getReferenceNumber())))
      {
        paramRegisterTransaction.set(localRegisterTransaction);
        break;
      }
    }
  }
  
  public void getRegisterCategories(RegisterCategories paramRegisterCategories)
    throws ProfileException
  {
    if (this.ae3.size() == 0)
    {
      localObject = getXMLReader();
      if (localObject == null) {
        Profile.handleError("DemoRegister.getRegisterCategories()", "Unable to initialize xml reader", 1);
      }
      this.ae3 = new RegisterCategories();
      this.ae3.startXMLParsing((Reader)localObject);
    }
    Object localObject = this.ae3.iterator();
    while (((Iterator)localObject).hasNext())
    {
      RegisterCategory localRegisterCategory1 = (RegisterCategory)((Iterator)localObject).next();
      RegisterCategory localRegisterCategory2 = new RegisterCategory();
      localRegisterCategory2.set(localRegisterCategory1);
      paramRegisterCategories.add(localRegisterCategory2);
    }
  }
  
  public void getRegisterDefaultCategories(RegisterCategories paramRegisterCategories) {}
  
  public void getRegisterCategoriesForPayments(Payments paramPayments, RecPayments paramRecPayments)
  {
    Iterator localIterator = this.ae0.iterator();
    while (localIterator.hasNext())
    {
      ServerTransaction localServerTransaction = (ServerTransaction)localIterator.next();
      String str = String.valueOf(localServerTransaction.getRegCategoryID());
      Object localObject;
      if (localServerTransaction.getRecServerTID() == null)
      {
        localObject = paramPayments.getByID(localServerTransaction.getServerTID());
        if (localObject != null) {
          ((Payment)localObject).set("REGISTER_CATEGORY_ID", str);
        }
      }
      else
      {
        localObject = paramRecPayments.getByRecID(localServerTransaction.getRecServerTID());
        if (localObject != null) {
          ((RecPayment)localObject).set("REGISTER_CATEGORY_ID", str);
        }
        Payments localPayments = paramPayments.getByRecPaymentID(localServerTransaction.getRecServerTID());
        if (localPayments != null)
        {
          ListIterator localListIterator = localPayments.listIterator();
          while (localListIterator.hasNext())
          {
            Payment localPayment = (Payment)localListIterator.next();
            localPayment.set("REGISTER_CATEGORY_ID", str);
          }
        }
      }
    }
  }
  
  public void setDefaultRegisterAccount(String paramString) {}
  
  public void getRegisterPayees(RegisterPayees paramRegisterPayees)
    throws ProfileException
  {
    if (this.ae1.size() == 0)
    {
      localObject = getXMLReader();
      if (localObject == null) {
        Profile.handleError("DemoRegister.getRegisterPayees()", "Unable to initialize xml reader", 1);
      }
      this.ae1 = new RegisterPayees();
      this.ae1.startXMLParsing((Reader)localObject);
    }
    Object localObject = this.ae1.iterator();
    while (((Iterator)localObject).hasNext())
    {
      RegisterPayee localRegisterPayee1 = (RegisterPayee)((Iterator)localObject).next();
      RegisterPayee localRegisterPayee2 = new RegisterPayee();
      localRegisterPayee2.set(localRegisterPayee1);
      paramRegisterPayees.add(localRegisterPayee2);
    }
  }
  
  public void modifyRegisterTransaction(RegisterTransaction paramRegisterTransaction)
  {
    RegisterTransaction localRegisterTransaction = this.ae6.getByRegisterId(paramRegisterTransaction.getRegisterId());
    if (localRegisterTransaction != null)
    {
      this.ae6.remove(localRegisterTransaction);
      this.ae6.add(paramRegisterTransaction);
    }
  }
  
  public void modifyRegisterTransactions(RegisterTransactions paramRegisterTransactions)
  {
    Iterator localIterator = paramRegisterTransactions.iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction1 = (RegisterTransaction)localIterator.next();
      RegisterTransaction localRegisterTransaction2 = this.ae6.getByRegisterId(localRegisterTransaction1.getRegisterId());
      if (localRegisterTransaction2 != null)
      {
        this.ae6.remove(localRegisterTransaction2);
        this.ae6.add(localRegisterTransaction1);
      }
      else
      {
        localRegisterTransaction2 = this.ae5.getByRegisterId(localRegisterTransaction1.getRegisterId());
        if (localRegisterTransaction2 != null)
        {
          this.ae5.remove(localRegisterTransaction2);
          if (localRegisterTransaction1.getStatusValue() == 1)
          {
            localRegisterTransaction1.setRegisterId(String.valueOf(new Random().nextInt()));
            this.ae5.add(localRegisterTransaction1);
          }
          else
          {
            this.ae6.add(localRegisterTransaction1);
          }
        }
      }
    }
  }
  
  public void modifyRegisterCategory(RegisterCategory paramRegisterCategory)
  {
    RegisterCategory localRegisterCategory = this.ae3.getById(paramRegisterCategory.getId());
    if (localRegisterCategory != null)
    {
      this.ae3.remove(localRegisterCategory);
      this.ae3.add(paramRegisterCategory);
    }
  }
  
  public void modifyRegisterPayee(RegisterPayee paramRegisterPayee)
  {
    RegisterPayee localRegisterPayee = this.ae1.getById(paramRegisterPayee.getId());
    if (localRegisterPayee != null)
    {
      this.ae1.remove(localRegisterPayee);
      this.ae1.add(paramRegisterPayee);
    }
  }
  
  public void deleteRegisterTransaction(RegisterTransaction paramRegisterTransaction)
  {
    Iterator localIterator = this.ae6.iterator();
    RegisterTransaction localRegisterTransaction;
    while (localIterator.hasNext())
    {
      localRegisterTransaction = (RegisterTransaction)localIterator.next();
      if (localRegisterTransaction.getRegisterId().equals(paramRegisterTransaction.getRegisterId()))
      {
        localIterator.remove();
        break;
      }
    }
    if (this.ae5 != null)
    {
      localIterator = this.ae5.iterator();
      while (localIterator.hasNext())
      {
        localRegisterTransaction = (RegisterTransaction)localIterator.next();
        if (localRegisterTransaction.getRegisterId().equals(paramRegisterTransaction.getRegisterId())) {
          localIterator.remove();
        }
      }
    }
  }
  
  public void deleteRegisterTransactions(RegisterTransactions paramRegisterTransactions)
  {
    Iterator localIterator1 = paramRegisterTransactions.iterator();
    while (localIterator1.hasNext())
    {
      RegisterTransaction localRegisterTransaction1 = (RegisterTransaction)localIterator1.next();
      RegisterTransaction localRegisterTransaction2 = null;
      Iterator localIterator2;
      if (this.ae6 != null)
      {
        localIterator2 = this.ae6.iterator();
        while (localIterator2.hasNext())
        {
          localRegisterTransaction2 = (RegisterTransaction)localIterator2.next();
          if (localRegisterTransaction2.getRegisterId().equals(localRegisterTransaction1.getRegisterId())) {
            localIterator2.remove();
          }
        }
      }
      if (this.ae5 != null)
      {
        localIterator2 = this.ae5.iterator();
        while (localIterator2.hasNext())
        {
          localRegisterTransaction2 = (RegisterTransaction)localIterator2.next();
          if (localRegisterTransaction2.getRegisterId().equals(localRegisterTransaction1.getRegisterId())) {
            localIterator2.remove();
          }
        }
      }
    }
  }
  
  public void deleteOldUnreconciledRegisterTransactions(int paramInt)
  {
    DateTime localDateTime = new DateTime();
    localDateTime.add(6, -1 * paramInt);
    Iterator localIterator = this.ae6.iterator();
    RegisterTransaction localRegisterTransaction;
    while (localIterator.hasNext())
    {
      localRegisterTransaction = (RegisterTransaction)localIterator.next();
      if (localRegisterTransaction.getDateIssuedValue().before(localDateTime)) {
        localIterator.remove();
      }
    }
    if (this.ae5 != null)
    {
      localIterator = this.ae5.iterator();
      while (localIterator.hasNext())
      {
        localRegisterTransaction = (RegisterTransaction)localIterator.next();
        if (localRegisterTransaction.getDateIssuedValue().before(localDateTime)) {
          localIterator.remove();
        }
      }
    }
  }
  
  public void deleteRegisterTransactionsByServerTID(ArrayList paramArrayList)
  {
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      I(str);
    }
  }
  
  private void I(String paramString)
  {
    Iterator localIterator;
    RegisterTransaction localRegisterTransaction;
    if (this.ae6 != null)
    {
      localIterator = this.ae6.iterator();
      while (localIterator.hasNext())
      {
        localRegisterTransaction = (RegisterTransaction)localIterator.next();
        String str = (String)localRegisterTransaction.get("SERVER_TID");
        if ((str != null) && (str.equals(paramString))) {
          localIterator.remove();
        }
      }
    }
    if (this.ae5 != null)
    {
      localIterator = this.ae5.iterator();
      while (localIterator.hasNext())
      {
        localRegisterTransaction = (RegisterTransaction)localIterator.next();
        if (localRegisterTransaction.getID().equals(paramString)) {
          localIterator.remove();
        }
      }
    }
  }
  
  public void deleteRegisterCategory(RegisterCategory paramRegisterCategory)
  {
    Iterator localIterator = this.ae3.iterator();
    while (localIterator.hasNext())
    {
      RegisterCategory localRegisterCategory = (RegisterCategory)localIterator.next();
      if (localRegisterCategory.getId().equals(paramRegisterCategory.getId())) {
        localIterator.remove();
      }
    }
  }
  
  public void deleteRegisterPayee(RegisterPayee paramRegisterPayee)
  {
    Iterator localIterator = this.ae1.iterator();
    while (localIterator.hasNext())
    {
      RegisterPayee localRegisterPayee = (RegisterPayee)localIterator.next();
      if (localRegisterPayee.getId().equals(paramRegisterPayee.getId())) {
        localIterator.remove();
      }
    }
  }
  
  public void transferDefaultCategories() {}
  
  public void reassignTransactionsCategory(String paramString1, String paramString2) {}
  
  public void reconcileRegisterTransactions(RegisterTransactions paramRegisterTransactions1, RegisterTransactions paramRegisterTransactions2)
  {
    modifyRegisterTransactions(paramRegisterTransactions1);
    deleteRegisterTransactions(paramRegisterTransactions2);
  }
  
  public void modifyRegisterAccountData(Account paramAccount) {}
  
  public void modifyRegisterAccountsData(Accounts paramAccounts) {}
  
  protected Reader getXMLReader()
  {
    StringReader localStringReader = null;
    try
    {
      if (m_InitHashMap == null) {
        m_InitHashMap = new HashMap();
      }
      String str = "";
      if ((m_InitHashMap != null) && (m_InitHashMap.get("demoRegister.xml") != null))
      {
        str = (String)m_InitHashMap.get("demoRegister.xml");
      }
      else
      {
        InputStream localInputStream = ResourceUtil.getResourceAsStream(this, "demoRegister.xml");
        if (localInputStream == null)
        {
          DebugLog.log("DEMO INIT ERROR: Init File Not Found 'demoRegister.xml'");
        }
        else
        {
          str = Strings.streamToString(localInputStream);
          m_InitHashMap.put("demoRegister.xml", str);
        }
      }
      localStringReader = new StringReader(str);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("DEMO INIT ERROR: Invalid Init File 'demoRegister.xml'");
    }
    return localStringReader;
  }
  
  protected class a
    extends XMLHandler
  {
    boolean jdField_new = false;
    boolean jdField_int = false;
    boolean jdField_byte = false;
    String jdField_try = null;
    
    protected a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("REGISTER_TRANSACTIONS"))
      {
        this.jdField_new = true;
        if ((this.jdField_int) && (this.jdField_byte)) {
          DemoRegister.this.ae6.continueXMLParsing(getHandler());
        }
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {
      if (paramString.equals("ACCOUNT")) {
        this.jdField_new = false;
      }
      if ((paramString.equals("NUMBER")) && (!this.jdField_new)) {
        if (this.jdField_try.equals(DemoRegister.this.ae4)) {
          this.jdField_int = true;
        } else {
          this.jdField_int = false;
        }
      }
      if ((paramString.equals("TYPE")) && (!this.jdField_new)) {
        if (this.jdField_try.equals(DemoRegister.this.ae2)) {
          this.jdField_byte = true;
        } else {
          this.jdField_byte = false;
        }
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      this.jdField_try = new String(paramArrayOfChar, paramInt1, paramInt2);
      this.jdField_try = this.jdField_try.trim();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.DemoRegister
 * JD-Core Version:    0.7.0.1
 */