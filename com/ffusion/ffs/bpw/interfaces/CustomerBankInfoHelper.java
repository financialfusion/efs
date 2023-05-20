/*   1:    */ package com.ffusion.ffs.bpw.interfaces;
/*   2:    */ 
/*   3:    */ import org.omg.CORBA.Any;
/*   4:    */ import org.omg.CORBA.BAD_OPERATION;
/*   5:    */ import org.omg.CORBA.ORB;
/*   6:    */ import org.omg.CORBA.StructMember;
/*   7:    */ import org.omg.CORBA.TCKind;
/*   8:    */ import org.omg.CORBA.TypeCode;
/*   9:    */ import org.omg.CORBA.portable.InputStream;
/*  10:    */ import org.omg.CORBA.portable.OutputStream;
/*  11:    */ 
/*  12:    */ public abstract class CustomerBankInfoHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static CustomerBankInfo clone(CustomerBankInfo paramCustomerBankInfo)
/*  17:    */   {
/*  18: 16 */     if (paramCustomerBankInfo == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     CustomerBankInfo localCustomerBankInfo = new CustomerBankInfo();
/*  22: 21 */     localCustomerBankInfo.consumerID = paramCustomerBankInfo.consumerID;
/*  23: 22 */     localCustomerBankInfo.routingAndTransitNumber = paramCustomerBankInfo.routingAndTransitNumber;
/*  24: 23 */     localCustomerBankInfo.acctNumber = paramCustomerBankInfo.acctNumber;
/*  25: 24 */     localCustomerBankInfo.acctType = paramCustomerBankInfo.acctType;
/*  26: 25 */     localCustomerBankInfo.settlementRefNumber = paramCustomerBankInfo.settlementRefNumber;
/*  27: 26 */     localCustomerBankInfo.primaryAcctFlag = paramCustomerBankInfo.primaryAcctFlag;
/*  28: 27 */     localCustomerBankInfo.status = paramCustomerBankInfo.status;
/*  29: 28 */     localCustomerBankInfo.submitDate = paramCustomerBankInfo.submitDate;
/*  30: 29 */     return localCustomerBankInfo;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static CustomerBankInfo read(InputStream paramInputStream)
/*  34:    */   {
/*  35: 35 */     CustomerBankInfo localCustomerBankInfo = new CustomerBankInfo();
/*  36: 36 */     localCustomerBankInfo.consumerID = paramInputStream.read_string();
/*  37: 37 */     localCustomerBankInfo.routingAndTransitNumber = paramInputStream.read_string();
/*  38: 38 */     localCustomerBankInfo.acctNumber = paramInputStream.read_string();
/*  39: 39 */     localCustomerBankInfo.acctType = paramInputStream.read_string();
/*  40: 40 */     localCustomerBankInfo.settlementRefNumber = paramInputStream.read_string();
/*  41: 41 */     localCustomerBankInfo.primaryAcctFlag = paramInputStream.read_string();
/*  42: 42 */     localCustomerBankInfo.status = paramInputStream.read_string();
/*  43: 43 */     localCustomerBankInfo.submitDate = paramInputStream.read_string();
/*  44: 44 */     return localCustomerBankInfo;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static void write(OutputStream paramOutputStream, CustomerBankInfo paramCustomerBankInfo)
/*  48:    */   {
/*  49: 51 */     if (paramCustomerBankInfo == null) {
/*  50: 53 */       paramCustomerBankInfo = new CustomerBankInfo();
/*  51:    */     }
/*  52: 55 */     paramOutputStream.write_string(paramCustomerBankInfo.consumerID);
/*  53: 56 */     paramOutputStream.write_string(paramCustomerBankInfo.routingAndTransitNumber);
/*  54: 57 */     paramOutputStream.write_string(paramCustomerBankInfo.acctNumber);
/*  55: 58 */     paramOutputStream.write_string(paramCustomerBankInfo.acctType);
/*  56: 59 */     paramOutputStream.write_string(paramCustomerBankInfo.settlementRefNumber);
/*  57: 60 */     paramOutputStream.write_string(paramCustomerBankInfo.primaryAcctFlag);
/*  58: 61 */     paramOutputStream.write_string(paramCustomerBankInfo.status);
/*  59: 62 */     paramOutputStream.write_string(paramCustomerBankInfo.submitDate);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static String _idl()
/*  63:    */   {
/*  64: 67 */     return "com::ffusion::ffs::bpw::interfaces::CustomerBankInfo";
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static TypeCode type()
/*  68:    */   {
/*  69: 74 */     if (_type == null)
/*  70:    */     {
/*  71: 76 */       ORB localORB = ORB.init();
/*  72: 77 */       StructMember[] arrayOfStructMember = 
/*  73: 78 */         {
/*  74: 79 */         new StructMember("consumerID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  75: 80 */         new StructMember("routingAndTransitNumber", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  76: 81 */         new StructMember("acctNumber", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  77: 82 */         new StructMember("acctType", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  78: 83 */         new StructMember("settlementRefNumber", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  79: 84 */         new StructMember("primaryAcctFlag", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  80: 85 */         new StructMember("status", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  81: 86 */         new StructMember("submitDate", localORB.get_primitive_tc(TCKind.tk_string), null) };
/*  82:    */       
/*  83: 88 */       _type = localORB.create_struct_tc(id(), "CustomerBankInfo", arrayOfStructMember);
/*  84:    */     }
/*  85: 90 */     return _type;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static void insert(Any paramAny, CustomerBankInfo paramCustomerBankInfo)
/*  89:    */   {
/*  90: 97 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  91: 98 */     write(localOutputStream, paramCustomerBankInfo);
/*  92: 99 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static CustomerBankInfo extract(Any paramAny)
/*  96:    */   {
/*  97:105 */     if (!paramAny.type().equal(type())) {
/*  98:107 */       throw new BAD_OPERATION();
/*  99:    */     }
/* 100:109 */     return read(paramAny.create_input_stream());
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static String id()
/* 104:    */   {
/* 105:114 */     return "IDL:com/ffusion/ffs/bpw/interfaces/CustomerBankInfo:1.0";
/* 106:    */   }
/* 107:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CustomerBankInfoHelper
 * JD-Core Version:    0.7.0.1
 */