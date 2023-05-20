package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.enums.UserType;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWInfoBase;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSUtil;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

public class Customer
  implements FFSConst, DBConsts, BPWResource
{
  private CustomerInfo yk;
  private static int yi;
  private static Hashtable ye = new Hashtable();
  private static String yd = " Order By CustomerID";
  private static String yn = " Where CustType = ? ";
  private static String yp = " Where FIID = ? and VirtualCustomer != 'Y' ";
  private static String yh = " Where FIID = ? and VirtualCustomer='Y' ";
  private static String yg = " Where CustCategory = ? ";
  private static String yo = " Where CustGroup = ? ";
  private static String yl = " Where CustType = ? AND FIID = ? and VirtualCustomer != 'Y' ";
  private static String yf = " Where CustCategory = ? AND FIID = ? and VirtualCustomer != 'Y' ";
  private static String ym = " Where CustGroup = ? AND FIID = ? and VirtualCustomer != 'Y' ";
  private static final int yj = 1000;
  
  public Customer()
  {
    this.yk = new CustomerInfo();
  }
  
  public Customer(CustomerInfo paramCustomerInfo)
  {
    this.yk = paramCustomerInfo;
  }
  
  public void setCustomerInfo(CustomerInfo paramCustomerInfo)
  {
    this.yk = paramCustomerInfo;
  }
  
  public CustomerInfo getCustomerInfo()
  {
    return this.yk;
  }
  
  public static void setBatchSize(int paramInt)
  {
    if (paramInt <= 0) {
      yi = 1000;
    } else {
      yi = paramInt;
    }
  }
  
  public static void clearCache()
  {
    ye.clear();
  }
  
  public void setCustomerID(String paramString)
  {
    this.yk.customerID = paramString;
  }
  
  public String getCustomerID()
  {
    return this.yk.customerID;
  }
  
  public static boolean isExists(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSResultSet localFFSResultSet = null;
    try
    {
      Object[] arrayOfObject = { paramString };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CustomerID FROM BPW_Customer WHERE CustomerID=?", arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        boolean bool = true;
        return bool;
      }
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
        }
        catch (Exception localException) {}
      }
    }
    return false;
  }
  
  public static int addCustomer(CustomerInfo paramCustomerInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("Customer.addCustomer start, customerID=" + paramCustomerInfo.customerID, 6);
    int i = 0;
    try
    {
      if (!a(paramFFSConnectionHolder, paramCustomerInfo))
      {
        localObject1 = "FI '" + paramCustomerInfo.fIID + "' does not exists.";
        throw new Exception((String)localObject1);
      }
      if ((paramCustomerInfo.ssn == null) || (paramCustomerInfo.ssn.length() == 0)) {
        paramCustomerInfo.ssn = paramCustomerInfo.taxID;
      }
      Object localObject1 = { paramCustomerInfo.customerID, paramCustomerInfo.firstName, paramCustomerInfo.initial, paramCustomerInfo.lastName, paramCustomerInfo.suffix, paramCustomerInfo.jointFirstName, paramCustomerInfo.jointInitial, paramCustomerInfo.jointLastName, paramCustomerInfo.jointSuffix, paramCustomerInfo.addressLine1, paramCustomerInfo.addressLine2, paramCustomerInfo.city, paramCustomerInfo.state, paramCustomerInfo.zipcode, paramCustomerInfo.country, paramCustomerInfo.status, paramCustomerInfo.countryCode1, paramCustomerInfo.phone1, paramCustomerInfo.email, paramCustomerInfo.countryCode2, paramCustomerInfo.phone2, paramCustomerInfo.jointCountryCode1, paramCustomerInfo.jointPhone1, paramCustomerInfo.jointCountryCode2, paramCustomerInfo.jointPhone2, paramCustomerInfo.securityCode, paramCustomerInfo.limit, paramCustomerInfo.sponsorID, paramCustomerInfo.custType, paramCustomerInfo.custGroup, paramCustomerInfo.custCategory, paramCustomerInfo.fIID, paramCustomerInfo.dateBirth, paramCustomerInfo.extInfo, paramCustomerInfo.billingPlan, paramCustomerInfo.remoteUserKey, FFSUtil.getDateString(), paramCustomerInfo.ssn, paramCustomerInfo.acctVerification, paramCustomerInfo.allowZeroDayProcess, new Integer(paramCustomerInfo.ACHCreditLeadDays), new Integer(paramCustomerInfo.ACHDebitLeadDays), paramCustomerInfo.getVirtualCustomer(), a(paramCustomerInfo.getUserType()) };
      if (a(paramCustomerInfo.getUserType()) != null)
      {
        i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_Customer( CustomerID,      FirstName,      MiddleInitial,      LastName,   Suffix, JointFirstName,  JointMiddleInitial,     JointLastName,  JointSuffix,        AddressLine1,AddressLine2,    City,           State,      ZipCode,        Country,ConsumerStatus,  CtryCode,       Phone,      InetAddr,       SecondCtryCode,SecondPhone,     JointCountryCode,   JointPhone, JointSecondCtryCode, JointSecondPhone,PersonalSecCode, DollarLimit, SponsorID, CustType, CustGroup, CustCategory, FIID, BirthDate, ExtInfo, BillingPlan, RemoteUserKey,Submitdate,      SSN, AcctVerification, AllowZeroDayProcess, ACHCreditLeadDays, ACHDebitLeadDays, VirtualCustomer, UserType ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?, ?, ?, ?, ? ,? ,? ,?, ?, ?, ?, ?, ?, ?)", (Object[])localObject1);
      }
      else
      {
        localObject2 = new Object[] { paramCustomerInfo.customerID, paramCustomerInfo.firstName, paramCustomerInfo.initial, paramCustomerInfo.lastName, paramCustomerInfo.suffix, paramCustomerInfo.jointFirstName, paramCustomerInfo.jointInitial, paramCustomerInfo.jointLastName, paramCustomerInfo.jointSuffix, paramCustomerInfo.addressLine1, paramCustomerInfo.addressLine2, paramCustomerInfo.city, paramCustomerInfo.state, paramCustomerInfo.zipcode, paramCustomerInfo.country, paramCustomerInfo.status, paramCustomerInfo.countryCode1, paramCustomerInfo.phone1, paramCustomerInfo.email, paramCustomerInfo.countryCode2, paramCustomerInfo.phone2, paramCustomerInfo.jointCountryCode1, paramCustomerInfo.jointPhone1, paramCustomerInfo.jointCountryCode2, paramCustomerInfo.jointPhone2, paramCustomerInfo.securityCode, paramCustomerInfo.limit, paramCustomerInfo.sponsorID, paramCustomerInfo.custType, paramCustomerInfo.custGroup, paramCustomerInfo.custCategory, paramCustomerInfo.fIID, paramCustomerInfo.dateBirth, paramCustomerInfo.extInfo, paramCustomerInfo.billingPlan, paramCustomerInfo.remoteUserKey, FFSUtil.getDateString(), paramCustomerInfo.ssn, paramCustomerInfo.acctVerification, paramCustomerInfo.allowZeroDayProcess, new Integer(paramCustomerInfo.ACHCreditLeadDays), new Integer(paramCustomerInfo.ACHDebitLeadDays), paramCustomerInfo.getVirtualCustomer() };
        i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_Customer( CustomerID,      FirstName,      MiddleInitial,      LastName,   Suffix, JointFirstName,  JointMiddleInitial,     JointLastName,  JointSuffix,        AddressLine1,AddressLine2,    City,           State,      ZipCode,        Country,ConsumerStatus,  CtryCode,       Phone,      InetAddr,       SecondCtryCode,SecondPhone,     JointCountryCode,   JointPhone, JointSecondCtryCode, JointSecondPhone,PersonalSecCode, DollarLimit, SponsorID, CustType, CustGroup, CustCategory, FIID, BirthDate, ExtInfo, BillingPlan, RemoteUserKey,Submitdate,      SSN, AcctVerification, AllowZeroDayProcess, ACHCreditLeadDays, ACHDebitLeadDays, VirtualCustomer ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?, ?, ?, ?, ? ,? ,? ,?, ?, ?, ?, ?, ?)", (Object[])localObject2);
      }
    }
    catch (Exception localException)
    {
      localException = localException;
      Object localObject2 = "Failed to add new customer. Error: " + localException.toString() + FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject2);
      throw localException;
    }
    finally {}
    return i;
  }
  
  public static int updateCustomer(CustomerInfo paramCustomerInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("Customer.updateCustomer start, customerID=" + paramCustomerInfo.customerID, 6);
    int i = 0;
    try
    {
      Object[] arrayOfObject1 = { paramCustomerInfo.firstName, paramCustomerInfo.initial, paramCustomerInfo.lastName, paramCustomerInfo.suffix, paramCustomerInfo.jointFirstName, paramCustomerInfo.jointInitial, paramCustomerInfo.jointLastName, paramCustomerInfo.jointSuffix, paramCustomerInfo.addressLine1, paramCustomerInfo.addressLine2, paramCustomerInfo.city, paramCustomerInfo.state, paramCustomerInfo.zipcode, paramCustomerInfo.country, paramCustomerInfo.status, paramCustomerInfo.countryCode1, paramCustomerInfo.phone1, paramCustomerInfo.countryCode2, paramCustomerInfo.phone2, paramCustomerInfo.jointCountryCode1, paramCustomerInfo.jointPhone1, paramCustomerInfo.jointCountryCode2, paramCustomerInfo.jointPhone2, paramCustomerInfo.securityCode, paramCustomerInfo.limit, paramCustomerInfo.sponsorID, paramCustomerInfo.custType, paramCustomerInfo.custGroup, paramCustomerInfo.custCategory, paramCustomerInfo.dateBirth, paramCustomerInfo.extInfo, paramCustomerInfo.billingPlan, paramCustomerInfo.remoteUserKey, paramCustomerInfo.acctVerification, paramCustomerInfo.allowZeroDayProcess, new Integer(paramCustomerInfo.ACHCreditLeadDays), new Integer(paramCustomerInfo.ACHDebitLeadDays), paramCustomerInfo.getVirtualCustomer(), a(paramCustomerInfo.getUserType()), paramCustomerInfo.customerID };
      if (a(paramCustomerInfo.getUserType()) != null)
      {
        i = DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_Customer SET FirstName = ?, MiddleInitial = ?, LastName = ?, Suffix = ?, JointFirstName = ?, JointMiddleInitial = ?, JointLastName = ?, JointSuffix = ?, AddressLine1 = ?, AddressLine2 = ?, City = ?, State = ?, ZipCode = ?, Country = ?, ConsumerStatus = ?, CtryCode = ?, Phone = ?, SecondCtryCode = ?, SecondPhone = ?, JointCountryCode = ?, JointPhone = ?, JointSecondCtryCode = ?, JointSecondPhone = ?, PersonalSecCode = ?, DollarLimit = ?, SponsorID = ?, CustType= ?, CustGroup = ?, CustCategory = ?,  BirthDate = ?, ExtInfo = ?, BillingPlan = ?, RemoteUserKey = ?,  AcctVerification = ?, AllowZeroDayProcess = ?, ACHCreditLeadDays = ?, ACHDebitLeadDays = ?,  VirtualCustomer = ?, UserType = ? WHERE CustomerID = ?", arrayOfObject1);
      }
      else
      {
        Object[] arrayOfObject2 = { paramCustomerInfo.firstName, paramCustomerInfo.initial, paramCustomerInfo.lastName, paramCustomerInfo.suffix, paramCustomerInfo.jointFirstName, paramCustomerInfo.jointInitial, paramCustomerInfo.jointLastName, paramCustomerInfo.jointSuffix, paramCustomerInfo.addressLine1, paramCustomerInfo.addressLine2, paramCustomerInfo.city, paramCustomerInfo.state, paramCustomerInfo.zipcode, paramCustomerInfo.country, paramCustomerInfo.status, paramCustomerInfo.countryCode1, paramCustomerInfo.phone1, paramCustomerInfo.countryCode2, paramCustomerInfo.phone2, paramCustomerInfo.jointCountryCode1, paramCustomerInfo.jointPhone1, paramCustomerInfo.jointCountryCode2, paramCustomerInfo.jointPhone2, paramCustomerInfo.securityCode, paramCustomerInfo.limit, paramCustomerInfo.sponsorID, paramCustomerInfo.custType, paramCustomerInfo.custGroup, paramCustomerInfo.custCategory, paramCustomerInfo.dateBirth, paramCustomerInfo.extInfo, paramCustomerInfo.billingPlan, paramCustomerInfo.remoteUserKey, paramCustomerInfo.acctVerification, paramCustomerInfo.allowZeroDayProcess, new Integer(paramCustomerInfo.ACHCreditLeadDays), new Integer(paramCustomerInfo.ACHDebitLeadDays), paramCustomerInfo.getVirtualCustomer(), paramCustomerInfo.customerID };
        i = DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_Customer SET FirstName = ?, MiddleInitial = ?, LastName = ?, Suffix = ?, JointFirstName = ?, JointMiddleInitial = ?, JointLastName = ?, JointSuffix = ?, AddressLine1 = ?, AddressLine2 = ?, City = ?, State = ?, ZipCode = ?, Country = ?, ConsumerStatus = ?, CtryCode = ?, Phone = ?, SecondCtryCode = ?, SecondPhone = ?, JointCountryCode = ?, JointPhone = ?, JointSecondCtryCode = ?, JointSecondPhone = ?, PersonalSecCode = ?, DollarLimit = ?, SponsorID = ?, CustType= ?, CustGroup = ?, CustCategory = ?,  BirthDate = ?, ExtInfo = ?, BillingPlan = ?, RemoteUserKey = ?,  AcctVerification = ?, AllowZeroDayProcess = ?, ACHCreditLeadDays = ?, ACHDebitLeadDays = ?,  VirtualCustomer = ? WHERE CustomerID = ?", arrayOfObject2);
      }
    }
    catch (Exception localException)
    {
      localException = localException;
      FFSDebug.log("Failed to update customer. Error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    finally {}
    return i;
  }
  
  public static int updateCustomer(String paramString, CustomerInfo paramCustomerInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("Customer.updateCustomer start, oldCustomerID=" + paramString + ",customerID=" + paramCustomerInfo.customerID, 6);
    int i = 0;
    try
    {
      Object[] arrayOfObject1 = { paramCustomerInfo.customerID, paramCustomerInfo.firstName, paramCustomerInfo.initial, paramCustomerInfo.lastName, paramCustomerInfo.suffix, paramCustomerInfo.jointFirstName, paramCustomerInfo.jointInitial, paramCustomerInfo.jointLastName, paramCustomerInfo.jointSuffix, paramCustomerInfo.addressLine1, paramCustomerInfo.addressLine2, paramCustomerInfo.city, paramCustomerInfo.state, paramCustomerInfo.zipcode, paramCustomerInfo.country, paramCustomerInfo.status, paramCustomerInfo.countryCode1, paramCustomerInfo.phone1, paramCustomerInfo.countryCode2, paramCustomerInfo.phone2, paramCustomerInfo.jointCountryCode1, paramCustomerInfo.jointPhone1, paramCustomerInfo.jointCountryCode2, paramCustomerInfo.jointPhone2, paramCustomerInfo.securityCode, paramCustomerInfo.limit, paramCustomerInfo.sponsorID, paramCustomerInfo.custType, paramCustomerInfo.custGroup, paramCustomerInfo.custCategory, paramCustomerInfo.dateBirth, paramCustomerInfo.extInfo, paramCustomerInfo.billingPlan, paramCustomerInfo.remoteUserKey, paramCustomerInfo.acctVerification, paramCustomerInfo.allowZeroDayProcess, new Integer(paramCustomerInfo.ACHCreditLeadDays), new Integer(paramCustomerInfo.ACHDebitLeadDays), paramCustomerInfo.getVirtualCustomer(), a(paramCustomerInfo.getUserType()), paramString };
      if (a(paramCustomerInfo.getUserType()) != null)
      {
        i = DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_Customer SET CustomerID = ?, FirstName = ?, MiddleInitial = ?, LastName = ?, Suffix = ?, JointFirstName = ?, JointMiddleInitial = ?, JointLastName = ?, JointSuffix = ?, AddressLine1 = ?, AddressLine2 = ?, City = ?, State = ?, ZipCode = ?, Country = ?, ConsumerStatus = ?, CtryCode = ?, Phone = ?, SecondCtryCode = ?, SecondPhone = ?, JointCountryCode = ?, JointPhone = ?, JointSecondCtryCode = ?, JointSecondPhone = ?, PersonalSecCode = ?, DollarLimit = ?, SponsorID = ?, CustType= ?, CustGroup = ?, CustCategory = ?,  BirthDate = ?, ExtInfo = ?, BillingPlan = ?, RemoteUserKey = ?,  AcctVerification = ? , AllowZeroDayProcess = ?, ACHCreditLeadDays = ?, ACHDebitLeadDays = ?, VirtualCustomer = ?, UserType = ? WHERE CustomerID = ?", arrayOfObject1);
      }
      else
      {
        Object[] arrayOfObject2 = { paramCustomerInfo.customerID, paramCustomerInfo.firstName, paramCustomerInfo.initial, paramCustomerInfo.lastName, paramCustomerInfo.suffix, paramCustomerInfo.jointFirstName, paramCustomerInfo.jointInitial, paramCustomerInfo.jointLastName, paramCustomerInfo.jointSuffix, paramCustomerInfo.addressLine1, paramCustomerInfo.addressLine2, paramCustomerInfo.city, paramCustomerInfo.state, paramCustomerInfo.zipcode, paramCustomerInfo.country, paramCustomerInfo.status, paramCustomerInfo.countryCode1, paramCustomerInfo.phone1, paramCustomerInfo.countryCode2, paramCustomerInfo.phone2, paramCustomerInfo.jointCountryCode1, paramCustomerInfo.jointPhone1, paramCustomerInfo.jointCountryCode2, paramCustomerInfo.jointPhone2, paramCustomerInfo.securityCode, paramCustomerInfo.limit, paramCustomerInfo.sponsorID, paramCustomerInfo.custType, paramCustomerInfo.custGroup, paramCustomerInfo.custCategory, paramCustomerInfo.dateBirth, paramCustomerInfo.extInfo, paramCustomerInfo.billingPlan, paramCustomerInfo.remoteUserKey, paramCustomerInfo.acctVerification, paramCustomerInfo.allowZeroDayProcess, new Integer(paramCustomerInfo.ACHCreditLeadDays), new Integer(paramCustomerInfo.ACHDebitLeadDays), paramCustomerInfo.getVirtualCustomer(), a(paramCustomerInfo.getUserType()), paramString };
        i = DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_Customer SET CustomerID = ?, FirstName = ?, MiddleInitial = ?, LastName = ?, Suffix = ?, JointFirstName = ?, JointMiddleInitial = ?, JointLastName = ?, JointSuffix = ?, AddressLine1 = ?, AddressLine2 = ?, City = ?, State = ?, ZipCode = ?, Country = ?, ConsumerStatus = ?, CtryCode = ?, Phone = ?, SecondCtryCode = ?, SecondPhone = ?, JointCountryCode = ?, JointPhone = ?, JointSecondCtryCode = ?, JointSecondPhone = ?, PersonalSecCode = ?, DollarLimit = ?, SponsorID = ?, CustType= ?, CustGroup = ?, CustCategory = ?,  BirthDate = ?, ExtInfo = ?, BillingPlan = ?, RemoteUserKey = ?,  AcctVerification = ? , AllowZeroDayProcess = ?, ACHCreditLeadDays = ?, ACHDebitLeadDays = ?, VirtualCustomer = ? WHERE CustomerID = ?", arrayOfObject2);
      }
    }
    catch (Exception localException)
    {
      localException = localException;
      FFSDebug.log("Failed to update customer. Error: " + localException.toString() + FFSDebug.stackTrace(localException));
    }
    finally {}
    return i;
  }
  
  public static boolean validCustomer(String paramString)
    throws Exception
  {
    boolean bool = false;
    FFSDebug.log("Customer.validCustomer start, customerID=" + paramString, 6);
    FFSResultSet localFFSResultSet = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      Object[] arrayOfObject = { paramString };
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, "SELECT CustomerID, FirstName, MiddleInitial, LastName, Suffix, JointFirstName, JointMiddleInitial, JointLastName, JointSuffix, AddressLine1, AddressLine2, City, State, ZipCode, Country, ConsumerStatus, CtryCode, Phone, SecondCtryCode, SecondPhone, JointCountryCode, JointPhone, JointSecondCtryCode, JointSecondPhone, PersonalSecCode, DollarLimit, SponsorID, BillingPlan, RemoteUserKey, Submitdate, SSN, InetAddr, CustType, CustGroup, CustCategory, FIID, BirthDate, ExtInfo, AcctVerification , AllowZeroDayProcess, ACHCreditLeadDays, ACHDebitLeadDays, VirtualCustomer, UserType FROM BPW_Customer WHERE CustomerID=?", arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        String str1 = localFFSResultSet.getColumnString(16);
        if (str1 == null) {
          bool = false;
        } else if (!str1.equalsIgnoreCase("INACTIVE")) {
          bool = true;
        } else {
          bool = false;
        }
      }
      else
      {
        bool = false;
      }
    }
    catch (Exception localException1)
    {
      throw new BPWException(localException1.toString() + FFSDebug.stackTrace(localException1));
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Customer.validCustomer failed:" + localException2.toString());
      }
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Exception localException3)
      {
        String str2 = "Error, failed to release connection to the connection pool: " + FFSDebug.stackTrace(localException3);
        FFSDebug.log(str2, 0);
        FFSDebug.log(str2);
      }
    }
    FFSDebug.log("Customer.validCustomer start, customerID=" + paramString + ", status=" + bool, 6);
    return bool;
  }
  
  public static CustomerInfo getCustomerByID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Customer.getCustomerByID start, customerID=" + paramString, 6);
    CustomerInfo localCustomerInfo1 = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      Object[] arrayOfObject = { paramString };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CustomerID, FirstName, MiddleInitial, LastName, Suffix, JointFirstName, JointMiddleInitial, JointLastName, JointSuffix, AddressLine1, AddressLine2, City, State, ZipCode, Country, ConsumerStatus, CtryCode, Phone, SecondCtryCode, SecondPhone, JointCountryCode, JointPhone, JointSecondCtryCode, JointSecondPhone, PersonalSecCode, DollarLimit, SponsorID, BillingPlan, RemoteUserKey, Submitdate, SSN, InetAddr, CustType, CustGroup, CustCategory, FIID, BirthDate, ExtInfo, AcctVerification , AllowZeroDayProcess, ACHCreditLeadDays, ACHDebitLeadDays, VirtualCustomer, UserType FROM BPW_Customer WHERE CustomerID=?", arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localCustomerInfo1 = new CustomerInfo();
        localCustomerInfo1.customerID = localFFSResultSet.getColumnString(1);
        localCustomerInfo1.firstName = localFFSResultSet.getColumnString(2);
        localCustomerInfo1.initial = localFFSResultSet.getColumnString(3);
        localCustomerInfo1.lastName = localFFSResultSet.getColumnString(4);
        localCustomerInfo1.suffix = localFFSResultSet.getColumnString(5);
        localCustomerInfo1.jointFirstName = localFFSResultSet.getColumnString(6);
        localCustomerInfo1.jointInitial = localFFSResultSet.getColumnString(7);
        localCustomerInfo1.jointLastName = localFFSResultSet.getColumnString(8);
        localCustomerInfo1.jointSuffix = localFFSResultSet.getColumnString(9);
        localCustomerInfo1.addressLine1 = localFFSResultSet.getColumnString(10);
        localCustomerInfo1.addressLine2 = localFFSResultSet.getColumnString(11);
        localCustomerInfo1.city = localFFSResultSet.getColumnString(12);
        localCustomerInfo1.state = localFFSResultSet.getColumnString(13);
        localCustomerInfo1.zipcode = localFFSResultSet.getColumnString(14);
        localCustomerInfo1.country = localFFSResultSet.getColumnString(15);
        localCustomerInfo1.status = localFFSResultSet.getColumnString(16);
        localCustomerInfo1.countryCode1 = localFFSResultSet.getColumnString(17);
        localCustomerInfo1.phone1 = localFFSResultSet.getColumnString(18);
        localCustomerInfo1.countryCode2 = localFFSResultSet.getColumnString(19);
        localCustomerInfo1.phone2 = localFFSResultSet.getColumnString(20);
        localCustomerInfo1.jointCountryCode1 = localFFSResultSet.getColumnString(21);
        localCustomerInfo1.jointPhone1 = localFFSResultSet.getColumnString(22);
        localCustomerInfo1.jointCountryCode2 = localFFSResultSet.getColumnString(23);
        localCustomerInfo1.jointPhone2 = localFFSResultSet.getColumnString(24);
        localCustomerInfo1.securityCode = localFFSResultSet.getColumnString(25);
        localCustomerInfo1.limit = localFFSResultSet.getColumnString(26);
        localCustomerInfo1.sponsorID = localFFSResultSet.getColumnString(27);
        localCustomerInfo1.billingPlan = localFFSResultSet.getColumnString(28);
        localCustomerInfo1.remoteUserKey = localFFSResultSet.getColumnString(29);
        localCustomerInfo1.submitDate = localFFSResultSet.getColumnString(30);
        localCustomerInfo1.ssn = localFFSResultSet.getColumnString(31);
        localCustomerInfo1.email = localFFSResultSet.getColumnString(32);
        localCustomerInfo1.custType = localFFSResultSet.getColumnString(33);
        localCustomerInfo1.custGroup = localFFSResultSet.getColumnString(34);
        localCustomerInfo1.custCategory = localFFSResultSet.getColumnString(35);
        localCustomerInfo1.fIID = localFFSResultSet.getColumnString(36);
        localCustomerInfo1.dateBirth = localFFSResultSet.getColumnString(37);
        localCustomerInfo1.extInfo = localFFSResultSet.getColumnString(38);
        localCustomerInfo1.acctVerification = localFFSResultSet.getColumnString(39);
        localCustomerInfo1.allowZeroDayProcess = localFFSResultSet.getColumnString(40);
        localCustomerInfo1.ACHCreditLeadDays = localFFSResultSet.getColumnInt(41);
        localCustomerInfo1.ACHDebitLeadDays = localFFSResultSet.getColumnInt(42);
        localCustomerInfo1.virtualCustomer = localFFSResultSet.getColumnString(43);
        localCustomerInfo1.setUserType(jdMethod_byte(localFFSResultSet.getColumnInt(44)));
      }
      else
      {
        CustomerInfo localCustomerInfo2 = null;
        return localCustomerInfo2;
      }
      if (localFFSResultSet.getNextRow()) {
        throw new Exception("More than one customer having the same customerID: " + paramString);
      }
    }
    catch (Exception localException1)
    {
      throw new BPWException(localException1.toString() + FFSDebug.stackTrace(localException1));
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Customer.getCustomerByID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Customer.getCustomerByID done, customerID=" + paramString, 6);
    return localCustomerInfo1;
  }
  
  public static boolean isBatchDone(String paramString, int paramInt)
  {
    return ye.get(paramString + paramInt) == null;
  }
  
  public static void clearBatch(String paramString, int paramInt)
  {
    try
    {
      if (ye.get(paramString + paramInt) != null)
      {
        FFSResultSet localFFSResultSet = (FFSResultSet)ye.get(paramString + paramInt);
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
        ye.remove(paramString + paramInt);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Customer.clearBatch failed:" + localException.toString());
    }
  }
  
  public static CustomerInfo[] findCustomersByStatus(String paramString, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("Customer.findCustomersByStatus start, status=" + paramString + ",routeID=" + paramInt, 6);
    ArrayList localArrayList = new ArrayList(32);
    FFSResultSet localFFSResultSet = null;
    try
    {
      int i = 0;
      String str = paramString + paramInt;
      Object[] arrayOfObject = { paramString };
      if (ye.get(str) == null)
      {
        if (paramString.equals("MOD")) {
          localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CustomerID, FirstName, MiddleInitial, LastName, Suffix, JointFirstName, JointMiddleInitial, JointLastName, JointSuffix, AddressLine1, AddressLine2, City, State, ZipCode, Country, ConsumerStatus, CtryCode, Phone, SecondCtryCode, SecondPhone, JointCountryCode, JointPhone, JointSecondCtryCode, JointSecondPhone, PersonalSecCode, DollarLimit, SponsorID, BillingPlan, RemoteUserKey, Submitdate, SSN, InetAddr, CustType, CustGroup, CustCategory, FIID, BirthDate, ExtInfo, AcctVerification, AllowZeroDayProcess, ACHCreditLeadDays, ACHDebitLeadDays, UserType FROM BPW_Customer WHERE BPW_Customer.ConsumerStatus LIKE 'MOD%'", null);
        } else {
          localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CustomerID, FirstName, MiddleInitial, LastName, Suffix, JointFirstName, JointMiddleInitial, JointLastName, JointSuffix, AddressLine1, AddressLine2, City, State, ZipCode, Country, ConsumerStatus, CtryCode, Phone, SecondCtryCode, SecondPhone, JointCountryCode, JointPhone, JointSecondCtryCode, JointSecondPhone, PersonalSecCode, DollarLimit, SponsorID, BillingPlan, RemoteUserKey, Submitdate, SSN, InetAddr, CustType, CustGroup, CustCategory, FIID, BirthDate, ExtInfo, AcctVerification , AllowZeroDayProcess, ACHCreditLeadDays, ACHDebitLeadDays,  UserType FROM BPW_Customer WHERE BPW_Customer.ConsumerStatus=?", arrayOfObject);
        }
        ye.put(str, localFFSResultSet);
      }
      else
      {
        localFFSResultSet = (FFSResultSet)ye.get(str);
      }
      while (localFFSResultSet.getNextRow())
      {
        CustomerInfo localCustomerInfo = new CustomerInfo();
        localCustomerInfo.customerID = localFFSResultSet.getColumnString(1);
        localCustomerInfo.firstName = localFFSResultSet.getColumnString(2);
        localCustomerInfo.initial = localFFSResultSet.getColumnString(3);
        localCustomerInfo.lastName = localFFSResultSet.getColumnString(4);
        localCustomerInfo.suffix = localFFSResultSet.getColumnString(5);
        localCustomerInfo.jointFirstName = localFFSResultSet.getColumnString(6);
        localCustomerInfo.jointInitial = localFFSResultSet.getColumnString(7);
        localCustomerInfo.jointLastName = localFFSResultSet.getColumnString(8);
        localCustomerInfo.jointSuffix = localFFSResultSet.getColumnString(9);
        localCustomerInfo.addressLine1 = localFFSResultSet.getColumnString(10);
        localCustomerInfo.addressLine2 = localFFSResultSet.getColumnString(11);
        localCustomerInfo.city = localFFSResultSet.getColumnString(12);
        localCustomerInfo.state = localFFSResultSet.getColumnString(13);
        localCustomerInfo.zipcode = localFFSResultSet.getColumnString(14);
        localCustomerInfo.country = localFFSResultSet.getColumnString(15);
        localCustomerInfo.status = localFFSResultSet.getColumnString(16);
        localCustomerInfo.countryCode1 = localFFSResultSet.getColumnString(17);
        localCustomerInfo.phone1 = localFFSResultSet.getColumnString(18);
        localCustomerInfo.countryCode2 = localFFSResultSet.getColumnString(19);
        localCustomerInfo.phone2 = localFFSResultSet.getColumnString(20);
        localCustomerInfo.jointCountryCode1 = localFFSResultSet.getColumnString(21);
        localCustomerInfo.jointPhone1 = localFFSResultSet.getColumnString(22);
        localCustomerInfo.jointCountryCode2 = localFFSResultSet.getColumnString(23);
        localCustomerInfo.jointPhone2 = localFFSResultSet.getColumnString(24);
        localCustomerInfo.securityCode = localFFSResultSet.getColumnString(25);
        localCustomerInfo.limit = localFFSResultSet.getColumnString(26);
        localCustomerInfo.sponsorID = localFFSResultSet.getColumnString(27);
        localCustomerInfo.billingPlan = localFFSResultSet.getColumnString(28);
        localCustomerInfo.remoteUserKey = localFFSResultSet.getColumnString(29);
        localCustomerInfo.submitDate = localFFSResultSet.getColumnString(30);
        localCustomerInfo.ssn = localFFSResultSet.getColumnString(31);
        localCustomerInfo.email = localFFSResultSet.getColumnString(32);
        localCustomerInfo.custType = localFFSResultSet.getColumnString(33);
        localCustomerInfo.custGroup = localFFSResultSet.getColumnString(34);
        localCustomerInfo.custCategory = localFFSResultSet.getColumnString(35);
        localCustomerInfo.fIID = localFFSResultSet.getColumnString(36);
        localCustomerInfo.dateBirth = localFFSResultSet.getColumnString(37);
        localCustomerInfo.extInfo = localFFSResultSet.getColumnString(38);
        localCustomerInfo.acctVerification = localFFSResultSet.getColumnString(39);
        localCustomerInfo.allowZeroDayProcess = localFFSResultSet.getColumnString(40);
        localCustomerInfo.ACHCreditLeadDays = localFFSResultSet.getColumnInt(41);
        localCustomerInfo.ACHDebitLeadDays = localFFSResultSet.getColumnInt(42);
        localCustomerInfo.setUserType(jdMethod_byte(localFFSResultSet.getColumnInt(43)));
        localArrayList.add(localCustomerInfo);
        i++;
        if (i == yi)
        {
          FFSDebug.log("Customer.findCustomersByStatus done, status=" + paramString + ", number= " + localArrayList.size(), 6);
          CustomerInfo[] arrayOfCustomerInfo2 = (CustomerInfo[])localArrayList.toArray(new CustomerInfo[localArrayList.size()]);
          return arrayOfCustomerInfo2;
        }
      }
      localFFSResultSet.close();
      ye.remove(paramString + paramInt);
    }
    catch (Exception localException)
    {
      localException = localException;
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
      if (ye != null) {
        ye.remove(paramString + paramInt);
      }
      FFSDebug.log("*** Customer.findCustPayeeByStatus failed: " + FFSDebug.stackTrace(localException));
      throw new BPWException(FFSDebug.stackTrace(localException));
    }
    finally {}
    CustomerInfo[] arrayOfCustomerInfo1 = (CustomerInfo[])localArrayList.toArray(new CustomerInfo[localArrayList.size()]);
    FFSDebug.log("Customer.findCustomersByStatus done, status=" + paramString + ", number= " + localArrayList.size(), 6);
    return arrayOfCustomerInfo1;
  }
  
  public static CustomerInfo[] findCustomersWithPaymentByStatus(String paramString, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("Customer.findCustomersWithPaymentByStatus start, status=" + paramString, 6);
    ArrayList localArrayList = new ArrayList(32);
    FFSResultSet localFFSResultSet = null;
    try
    {
      int i = 0;
      String str = paramString + paramInt;
      Object[] arrayOfObject = { paramString, new Integer(paramInt) };
      if (ye.get(str) == null)
      {
        if (paramString.equals("MOD"))
        {
          arrayOfObject = new Object[] { new Integer(paramInt) };
          localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT c.CustomerID, FirstName, MiddleInitial, LastName, Suffix, JointFirstName, JointMiddleInitial, JointLastName, JointSuffix, AddressLine1, AddressLine2, City, State, ZipCode, Country, ConsumerStatus, CtryCode, Phone, SecondCtryCode, SecondPhone, JointCountryCode, JointPhone, JointSecondCtryCode, JointSecondPhone, PersonalSecCode, DollarLimit, SponsorID, BillingPlan, RemoteUserKey, c.Submitdate, SSN, InetAddr, CustType, CustGroup, CustCategory, FIID, BirthDate, ExtInfo, AcctVerification, AllowZeroDayProcess, ACHCreditLeadDays, ACHDebitLeadDays, UserType FROM BPW_Customer c, BPW_CustomerRoute cr WHERE c.CustomerID = cr.CustomerID AND cr.Status LIKE 'MOD%' AND cr.RouteID = ? AND c.ConsumerStatus='ACTIVE'", arrayOfObject);
        }
        else
        {
          localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT c.CustomerID, FirstName, MiddleInitial, LastName, Suffix, JointFirstName, JointMiddleInitial, JointLastName, JointSuffix, AddressLine1, AddressLine2, City, State, ZipCode, Country, ConsumerStatus, CtryCode, Phone, SecondCtryCode, SecondPhone, JointCountryCode, JointPhone, JointSecondCtryCode, JointSecondPhone, PersonalSecCode, DollarLimit, SponsorID, BillingPlan, RemoteUserKey, c.Submitdate, SSN, InetAddr, CustType, CustGroup, CustCategory, FIID, BirthDate, ExtInfo, AcctVerification, AllowZeroDayProcess, ACHCreditLeadDays, ACHDebitLeadDays, UserType FROM BPW_Customer c, BPW_CustomerRoute cr WHERE c.CustomerID = cr.CustomerID AND cr.Status = ? AND cr.RouteID = ? AND c.ConsumerStatus='ACTIVE'", arrayOfObject);
        }
        ye.put(str, localFFSResultSet);
      }
      else
      {
        localFFSResultSet = (FFSResultSet)ye.get(str);
      }
      while (localFFSResultSet.getNextRow())
      {
        CustomerInfo localCustomerInfo = new CustomerInfo();
        localCustomerInfo.customerID = localFFSResultSet.getColumnString(1);
        localCustomerInfo.firstName = localFFSResultSet.getColumnString(2);
        localCustomerInfo.initial = localFFSResultSet.getColumnString(3);
        localCustomerInfo.lastName = localFFSResultSet.getColumnString(4);
        localCustomerInfo.suffix = localFFSResultSet.getColumnString(5);
        localCustomerInfo.jointFirstName = localFFSResultSet.getColumnString(6);
        localCustomerInfo.jointInitial = localFFSResultSet.getColumnString(7);
        localCustomerInfo.jointLastName = localFFSResultSet.getColumnString(8);
        localCustomerInfo.jointSuffix = localFFSResultSet.getColumnString(9);
        localCustomerInfo.addressLine1 = localFFSResultSet.getColumnString(10);
        localCustomerInfo.addressLine2 = localFFSResultSet.getColumnString(11);
        localCustomerInfo.city = localFFSResultSet.getColumnString(12);
        localCustomerInfo.state = localFFSResultSet.getColumnString(13);
        localCustomerInfo.zipcode = localFFSResultSet.getColumnString(14);
        localCustomerInfo.country = localFFSResultSet.getColumnString(15);
        localCustomerInfo.status = localFFSResultSet.getColumnString(16);
        localCustomerInfo.countryCode1 = localFFSResultSet.getColumnString(17);
        localCustomerInfo.phone1 = localFFSResultSet.getColumnString(18);
        localCustomerInfo.countryCode2 = localFFSResultSet.getColumnString(19);
        localCustomerInfo.phone2 = localFFSResultSet.getColumnString(20);
        localCustomerInfo.jointCountryCode1 = localFFSResultSet.getColumnString(21);
        localCustomerInfo.jointPhone1 = localFFSResultSet.getColumnString(22);
        localCustomerInfo.jointCountryCode2 = localFFSResultSet.getColumnString(23);
        localCustomerInfo.jointPhone2 = localFFSResultSet.getColumnString(24);
        localCustomerInfo.securityCode = localFFSResultSet.getColumnString(25);
        localCustomerInfo.limit = localFFSResultSet.getColumnString(26);
        localCustomerInfo.sponsorID = localFFSResultSet.getColumnString(27);
        localCustomerInfo.billingPlan = localFFSResultSet.getColumnString(28);
        localCustomerInfo.remoteUserKey = localFFSResultSet.getColumnString(29);
        localCustomerInfo.submitDate = localFFSResultSet.getColumnString(30);
        localCustomerInfo.ssn = localFFSResultSet.getColumnString(31);
        localCustomerInfo.email = localFFSResultSet.getColumnString(32);
        localCustomerInfo.custType = localFFSResultSet.getColumnString(33);
        localCustomerInfo.custGroup = localFFSResultSet.getColumnString(34);
        localCustomerInfo.custCategory = localFFSResultSet.getColumnString(35);
        localCustomerInfo.fIID = localFFSResultSet.getColumnString(36);
        localCustomerInfo.dateBirth = localFFSResultSet.getColumnString(37);
        localCustomerInfo.extInfo = localFFSResultSet.getColumnString(38);
        localCustomerInfo.acctVerification = localFFSResultSet.getColumnString(39);
        localCustomerInfo.allowZeroDayProcess = localFFSResultSet.getColumnString(40);
        localCustomerInfo.ACHCreditLeadDays = localFFSResultSet.getColumnInt(41);
        localCustomerInfo.ACHDebitLeadDays = localFFSResultSet.getColumnInt(42);
        localCustomerInfo.setUserType(jdMethod_byte(localFFSResultSet.getColumnInt(43)));
        localArrayList.add(localCustomerInfo);
        i++;
        if (i == yi)
        {
          FFSDebug.log("Customer.findCustomersWithPaymentByStatus done, status=" + paramString + ", number= " + localArrayList.size(), 6);
          CustomerInfo[] arrayOfCustomerInfo2 = (CustomerInfo[])localArrayList.toArray(new CustomerInfo[localArrayList.size()]);
          return arrayOfCustomerInfo2;
        }
      }
      localFFSResultSet.close();
      ye.remove(paramString + paramInt);
    }
    catch (Exception localException)
    {
      localException = localException;
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
      if (ye != null) {
        ye.remove(paramString + paramInt);
      }
      FFSDebug.log("*** Customer.findCustomersWithPaymentByStatus failed: " + FFSDebug.stackTrace(localException));
      throw new BPWException(FFSDebug.stackTrace(localException));
    }
    finally {}
    CustomerInfo[] arrayOfCustomerInfo1 = (CustomerInfo[])localArrayList.toArray(new CustomerInfo[localArrayList.size()]);
    FFSDebug.log("Customer.findCustomersWithPaymentByStatus done, status=" + paramString + ", number= " + localArrayList.size(), 6);
    return arrayOfCustomerInfo1;
  }
  
  public static int updateCustomerStatus(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Customer.updateCustomerStatus start, customerID=" + paramString1 + ",status=" + paramString2, 6);
    Object[] arrayOfObject = { paramString2, paramString1 };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_Customer SET ConsumerStatus = ? WHERE CustomerID = ?", arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** CustomerPayee.updateCustomerStatus failed: " + localException.toString() + FFSDebug.stackTrace(localException));
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
    FFSDebug.log("Customer.updateCustomerStatus done, customerID=" + paramString1, 6);
    return i;
  }
  
  public static int updateCustomerStatusWithRouteID(String paramString1, int paramInt, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Customer.updateCustomerStatusWithRouteID start, customerID=" + paramString1 + ",status=" + paramString2, 6);
    int i = CustRoute.updateCustRouteStatus(paramString1, paramInt, paramString2, paramFFSConnectionHolder);
    FFSDebug.log("Customer.updateCustomerStatusWithRouteID done, customerID=" + paramString1, 6);
    return i;
  }
  
  public static CustomerInfo[] findCustomerByStatus(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("customer.findCustomerByStatus start, status=" + paramString, 6);
    if (paramString == null) {
      return null;
    }
    Vector localVector = new Vector();
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      if (paramString.equals("MOD")) {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CustomerID, FirstName, MiddleInitial, LastName, Suffix, JointFirstName, JointMiddleInitial, JointLastName, JointSuffix, AddressLine1, AddressLine2, City, State, ZipCode, Country, ConsumerStatus, CtryCode, Phone, SecondCtryCode, SecondPhone, JointCountryCode, JointPhone, JointSecondCtryCode, JointSecondPhone, PersonalSecCode, DollarLimit, SponsorID, BillingPlan, RemoteUserKey, Submitdate, SSN, InetAddr, CustType, CustGroup, CustCategory, FIID, BirthDate, ExtInfo, AcctVerification, AllowZeroDayProcess, ACHCreditLeadDays, ACHDebitLeadDays, UserType FROM BPW_Customer WHERE BPW_Customer.ConsumerStatus LIKE 'MOD%'", null);
      } else {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CustomerID, FirstName, MiddleInitial, LastName, Suffix, JointFirstName, JointMiddleInitial, JointLastName, JointSuffix, AddressLine1, AddressLine2, City, State, ZipCode, Country, ConsumerStatus, CtryCode, Phone, SecondCtryCode, SecondPhone, JointCountryCode, JointPhone, JointSecondCtryCode, JointSecondPhone, PersonalSecCode, DollarLimit, SponsorID, BillingPlan, RemoteUserKey, Submitdate, SSN, InetAddr, CustType, CustGroup, CustCategory, FIID, BirthDate, ExtInfo, AcctVerification , AllowZeroDayProcess, ACHCreditLeadDays, ACHDebitLeadDays,  UserType FROM BPW_Customer WHERE BPW_Customer.ConsumerStatus=?", arrayOfObject);
      }
      while (localFFSResultSet.getNextRow())
      {
        CustomerInfo localCustomerInfo = new CustomerInfo();
        localCustomerInfo.customerID = localFFSResultSet.getColumnString(1);
        localCustomerInfo.firstName = localFFSResultSet.getColumnString(2);
        localCustomerInfo.initial = localFFSResultSet.getColumnString(3);
        localCustomerInfo.lastName = localFFSResultSet.getColumnString(4);
        localCustomerInfo.suffix = localFFSResultSet.getColumnString(5);
        localCustomerInfo.jointFirstName = localFFSResultSet.getColumnString(6);
        localCustomerInfo.jointInitial = localFFSResultSet.getColumnString(7);
        localCustomerInfo.jointLastName = localFFSResultSet.getColumnString(8);
        localCustomerInfo.jointSuffix = localFFSResultSet.getColumnString(9);
        localCustomerInfo.addressLine1 = localFFSResultSet.getColumnString(10);
        localCustomerInfo.addressLine2 = localFFSResultSet.getColumnString(11);
        localCustomerInfo.city = localFFSResultSet.getColumnString(12);
        localCustomerInfo.state = localFFSResultSet.getColumnString(13);
        localCustomerInfo.zipcode = localFFSResultSet.getColumnString(14);
        localCustomerInfo.country = localFFSResultSet.getColumnString(15);
        localCustomerInfo.status = localFFSResultSet.getColumnString(16);
        localCustomerInfo.countryCode1 = localFFSResultSet.getColumnString(17);
        localCustomerInfo.phone1 = localFFSResultSet.getColumnString(18);
        localCustomerInfo.countryCode2 = localFFSResultSet.getColumnString(19);
        localCustomerInfo.phone2 = localFFSResultSet.getColumnString(20);
        localCustomerInfo.jointCountryCode1 = localFFSResultSet.getColumnString(21);
        localCustomerInfo.jointPhone1 = localFFSResultSet.getColumnString(22);
        localCustomerInfo.jointCountryCode2 = localFFSResultSet.getColumnString(23);
        localCustomerInfo.jointPhone2 = localFFSResultSet.getColumnString(24);
        localCustomerInfo.securityCode = localFFSResultSet.getColumnString(25);
        localCustomerInfo.limit = localFFSResultSet.getColumnString(26);
        localCustomerInfo.sponsorID = localFFSResultSet.getColumnString(27);
        localCustomerInfo.billingPlan = localFFSResultSet.getColumnString(28);
        localCustomerInfo.remoteUserKey = localFFSResultSet.getColumnString(29);
        localCustomerInfo.submitDate = localFFSResultSet.getColumnString(30);
        localCustomerInfo.ssn = localFFSResultSet.getColumnString(31);
        localCustomerInfo.email = localFFSResultSet.getColumnString(32);
        localCustomerInfo.custType = localFFSResultSet.getColumnString(33);
        localCustomerInfo.custGroup = localFFSResultSet.getColumnString(34);
        localCustomerInfo.custCategory = localFFSResultSet.getColumnString(35);
        localCustomerInfo.fIID = localFFSResultSet.getColumnString(36);
        localCustomerInfo.dateBirth = localFFSResultSet.getColumnString(37);
        localCustomerInfo.extInfo = localFFSResultSet.getColumnString(38);
        localCustomerInfo.acctVerification = localFFSResultSet.getColumnString(39);
        localCustomerInfo.allowZeroDayProcess = localFFSResultSet.getColumnString(40);
        localCustomerInfo.ACHCreditLeadDays = localFFSResultSet.getColumnInt(41);
        localCustomerInfo.ACHDebitLeadDays = localFFSResultSet.getColumnInt(42);
        localCustomerInfo.setUserType(jdMethod_byte(localFFSResultSet.getColumnInt(43)));
        localVector.addElement(localCustomerInfo);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** customer.findCustomerByStatus failed: " + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** customer.findCustomerByStatus failed:" + localException2.toString());
      }
    }
    FFSDebug.log("customer.findCustomerByStatus done, status=" + paramString + ", found Customers:" + localVector.size(), 6);
    return (CustomerInfo[])localVector.toArray(new CustomerInfo[0]);
  }
  
  public static String getCustomerStatus(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSResultSet localFFSResultSet = null;
    String str = null;
    try
    {
      Object[] arrayOfObject = { paramString };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CustomerID, FirstName, MiddleInitial, LastName, Suffix, JointFirstName, JointMiddleInitial, JointLastName, JointSuffix, AddressLine1, AddressLine2, City, State, ZipCode, Country, ConsumerStatus, CtryCode, Phone, SecondCtryCode, SecondPhone, JointCountryCode, JointPhone, JointSecondCtryCode, JointSecondPhone, PersonalSecCode, DollarLimit, SponsorID, BillingPlan, RemoteUserKey, Submitdate, SSN, InetAddr, CustType, CustGroup, CustCategory, FIID, BirthDate, ExtInfo, AcctVerification , AllowZeroDayProcess, ACHCreditLeadDays, ACHDebitLeadDays, VirtualCustomer, UserType FROM BPW_Customer WHERE CustomerID=?", arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str = localFFSResultSet.getColumnString(16);
      } else {
        throw new Exception("Customer.getCustomerStatus: Customer not found. customerID: " + paramString);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("Failed to get customer status. Error : " + localException1.toString() + FFSDebug.stackTrace(localException1));
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Customer.getCustomerStatus failed:" + localException2.toString());
      }
    }
    return str;
  }
  
  public static String getCustomerName(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Customer.getCustomerName start, customerID=" + paramString, 6);
    CustomerInfo localCustomerInfo = getCustomerByID(paramString, paramFFSConnectionHolder);
    String str = null;
    if (localCustomerInfo != null) {
      str = localCustomerInfo.lastName + " " + localCustomerInfo.firstName;
    }
    return str;
  }
  
  public static CustomerInfo getCustomerInfo(String paramString, FFSConnectionHolder paramFFSConnectionHolder, BPWInfoBase paramBPWInfoBase)
    throws BPWException
  {
    FFSDebug.log("Customer.getCustomerInfo start, customerID = " + paramString, 6);
    if (paramBPWInfoBase == null) {
      return getCustomerByID(paramString, paramFFSConnectionHolder);
    }
    if (!paramBPWInfoBase.isCustomerInfoLoaded()) {
      paramBPWInfoBase.setCustomerInfo(getCustomerByID(paramString, paramFFSConnectionHolder));
    }
    return paramBPWInfoBase.getCustomerInfo();
  }
  
  public static CustomerInfo[] getCustomerByType(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Customer.getCustomerByType start, custType=" + paramString, 6);
    try
    {
      String[] arrayOfString = { paramString };
      return a(arrayOfString, yn, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
  }
  
  public static CustomerInfo[] getCustomerByFI(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Customer.getCustomerByFI start, fi=" + paramString, 6);
    try
    {
      String[] arrayOfString = { paramString };
      return a(arrayOfString, yp, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
  }
  
  public static CustomerInfo[] getVirtualCustomerByFI(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Customer.getVirtualCustomerByFI start, fi=" + paramString, 6);
    try
    {
      String[] arrayOfString = { paramString };
      return a(arrayOfString, yh, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
  }
  
  public static CustomerInfo[] getCustomerByCategory(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Customer.getCustomerByCategory start, catg=" + paramString, 6);
    try
    {
      String[] arrayOfString = { paramString };
      return a(arrayOfString, yg, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
  }
  
  public static CustomerInfo[] getCustomerByGroup(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Customer.getCustomerByGroup start, group=" + paramString, 6);
    try
    {
      String[] arrayOfString = { paramString };
      return a(arrayOfString, yo, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
  }
  
  public static CustomerInfo[] getCustomerByTypeAndFI(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Customer.getCustomerByTypeAndFI start, type=", paramString1, "fi", paramString2, 6);
    try
    {
      String[] arrayOfString = { paramString1, paramString2 };
      return a(arrayOfString, yl, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
  }
  
  public static CustomerInfo[] getCustomerByCategoryAndFI(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Customer.getCustomerByTypeAndFI start, catg=", paramString1, "fi", paramString2, 6);
    try
    {
      String[] arrayOfString = { paramString1, paramString2 };
      return a(arrayOfString, yf, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
  }
  
  public static CustomerInfo[] getCustomerByGroupAndFI(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Customer.getCustomerByTypeAndFI start, group=", paramString1, "fi", paramString2, 6);
    try
    {
      String[] arrayOfString = { paramString1, paramString2 };
      return a(arrayOfString, ym, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
  }
  
  private static CustomerInfo[] a(String[] paramArrayOfString, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Customer.getCustomerByCol start, ", 6);
    FFSResultSet localFFSResultSet = null;
    StringBuffer localStringBuffer1 = null;
    ArrayList localArrayList = null;
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      FFSDebug.log("Customer.getCustomerByCol failed. no value specified to filter on", 0);
      return null;
    }
    localStringBuffer1 = new StringBuffer();
    localArrayList = new ArrayList();
    try
    {
      int i = 0;
      StringBuffer localStringBuffer2 = new StringBuffer();
      for (int j = 0; j < paramArrayOfString.length; j++) {
        localStringBuffer2.append(paramArrayOfString[j]);
      }
      String[] arrayOfString = paramArrayOfString;
      localStringBuffer1.append("SELECT CustomerID, FirstName, MiddleInitial, LastName, Suffix, JointFirstName, JointMiddleInitial, JointLastName, JointSuffix, AddressLine1, AddressLine2, City, State, ZipCode, Country, ConsumerStatus, CtryCode, Phone, SecondCtryCode, SecondPhone, JointCountryCode, JointPhone, JointSecondCtryCode, JointSecondPhone, PersonalSecCode, DollarLimit, SponsorID, BillingPlan, RemoteUserKey, Submitdate, SSN, InetAddr, CustType, CustGroup, CustCategory, FIID, BirthDate, ExtInfo, AcctVerification, AllowZeroDayProcess, ACHCreditLeadDays, ACHDebitLeadDays, VirtualCustomer, UserType FROM BPW_Customer ");
      localStringBuffer1.append(paramString);
      localStringBuffer1.append(yd);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer1.toString(), arrayOfString);
      while (localFFSResultSet.getNextRow())
      {
        CustomerInfo localCustomerInfo = new CustomerInfo();
        localCustomerInfo.customerID = localFFSResultSet.getColumnString(1);
        localCustomerInfo.firstName = localFFSResultSet.getColumnString(2);
        localCustomerInfo.initial = localFFSResultSet.getColumnString(3);
        localCustomerInfo.lastName = localFFSResultSet.getColumnString(4);
        localCustomerInfo.suffix = localFFSResultSet.getColumnString(5);
        localCustomerInfo.jointFirstName = localFFSResultSet.getColumnString(6);
        localCustomerInfo.jointInitial = localFFSResultSet.getColumnString(7);
        localCustomerInfo.jointLastName = localFFSResultSet.getColumnString(8);
        localCustomerInfo.jointSuffix = localFFSResultSet.getColumnString(9);
        localCustomerInfo.addressLine1 = localFFSResultSet.getColumnString(10);
        localCustomerInfo.addressLine2 = localFFSResultSet.getColumnString(11);
        localCustomerInfo.city = localFFSResultSet.getColumnString(12);
        localCustomerInfo.state = localFFSResultSet.getColumnString(13);
        localCustomerInfo.zipcode = localFFSResultSet.getColumnString(14);
        localCustomerInfo.country = localFFSResultSet.getColumnString(15);
        localCustomerInfo.status = localFFSResultSet.getColumnString(16);
        localCustomerInfo.countryCode1 = localFFSResultSet.getColumnString(17);
        localCustomerInfo.phone1 = localFFSResultSet.getColumnString(18);
        localCustomerInfo.countryCode2 = localFFSResultSet.getColumnString(19);
        localCustomerInfo.phone2 = localFFSResultSet.getColumnString(20);
        localCustomerInfo.jointCountryCode1 = localFFSResultSet.getColumnString(21);
        localCustomerInfo.jointPhone1 = localFFSResultSet.getColumnString(22);
        localCustomerInfo.jointCountryCode2 = localFFSResultSet.getColumnString(23);
        localCustomerInfo.jointPhone2 = localFFSResultSet.getColumnString(24);
        localCustomerInfo.securityCode = localFFSResultSet.getColumnString(25);
        localCustomerInfo.limit = localFFSResultSet.getColumnString(26);
        localCustomerInfo.sponsorID = localFFSResultSet.getColumnString(27);
        localCustomerInfo.billingPlan = localFFSResultSet.getColumnString(28);
        localCustomerInfo.remoteUserKey = localFFSResultSet.getColumnString(29);
        localCustomerInfo.submitDate = localFFSResultSet.getColumnString(30);
        localCustomerInfo.ssn = localFFSResultSet.getColumnString(31);
        localCustomerInfo.email = localFFSResultSet.getColumnString(32);
        localCustomerInfo.custType = localFFSResultSet.getColumnString(33);
        localCustomerInfo.custGroup = localFFSResultSet.getColumnString(34);
        localCustomerInfo.custCategory = localFFSResultSet.getColumnString(35);
        localCustomerInfo.fIID = localFFSResultSet.getColumnString(36);
        localCustomerInfo.dateBirth = localFFSResultSet.getColumnString(37);
        localCustomerInfo.extInfo = localFFSResultSet.getColumnString(38);
        localCustomerInfo.acctVerification = localFFSResultSet.getColumnString(39);
        localCustomerInfo.allowZeroDayProcess = localFFSResultSet.getColumnString(40);
        localCustomerInfo.ACHCreditLeadDays = localFFSResultSet.getColumnInt(41);
        localCustomerInfo.ACHDebitLeadDays = localFFSResultSet.getColumnInt(42);
        localCustomerInfo.virtualCustomer = localFFSResultSet.getColumnString(43);
        localCustomerInfo.setUserType(jdMethod_byte(localFFSResultSet.getColumnInt(44)));
        localArrayList.add(localCustomerInfo);
        i++;
        if (i == yi)
        {
          FFSDebug.log("Customer.getCustomerByCol done, ", ", number= " + localArrayList.size(), 6);
          CustomerInfo[] arrayOfCustomerInfo2 = (CustomerInfo[])localArrayList.toArray(new CustomerInfo[localArrayList.size()]);
          return arrayOfCustomerInfo2;
        }
      }
      localFFSResultSet.close();
      localFFSResultSet = null;
    }
    catch (Exception localException)
    {
      localException = localException;
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
    finally {}
    CustomerInfo[] arrayOfCustomerInfo1 = (CustomerInfo[])localArrayList.toArray(new CustomerInfo[localArrayList.size()]);
    FFSDebug.log("Customer.getCustomerByCol done, number= " + localArrayList.size(), 6);
    return arrayOfCustomerInfo1;
  }
  
  private static boolean a(FFSConnectionHolder paramFFSConnectionHolder, CustomerInfo paramCustomerInfo)
    throws Exception
  {
    BPWFIInfo localBPWFIInfo = null;
    try
    {
      if ((paramCustomerInfo.fIID == null) || (paramCustomerInfo.fIID.trim().length() == 0))
      {
        String str1 = "***Customer.checkFI failed: customer fiid is null!";
        FFSDebug.log(str1, 0);
        throw new Exception(str1);
      }
      localBPWFIInfo = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, paramCustomerInfo.fIID);
      if (localBPWFIInfo.getFIId() == null) {
        return false;
      }
    }
    catch (Throwable localThrowable)
    {
      String str2 = "***Customer.checkFI failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new Exception(str2);
    }
    return true;
  }
  
  private static Integer a(UserType paramUserType)
  {
    return paramUserType != null ? new Integer(paramUserType.getValue()) : null;
  }
  
  private static UserType jdMethod_byte(int paramInt)
  {
    return paramInt > 0 ? UserType.getEnum(paramInt) : null;
  }
  
  public static boolean isCustomerManagedByBPW()
  {
    return PropertyConfig.getRegisteredProperties().otherProperties.getProperty("bpw.transfer.manageCustomers").equals("BPW");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.Customer
 * JD-Core Version:    0.7.0.1
 */