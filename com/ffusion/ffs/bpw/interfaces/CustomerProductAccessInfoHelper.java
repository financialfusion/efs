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
/*  12:    */ public abstract class CustomerProductAccessInfoHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static CustomerProductAccessInfo clone(CustomerProductAccessInfo paramCustomerProductAccessInfo)
/*  17:    */   {
/*  18: 16 */     if (paramCustomerProductAccessInfo == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     CustomerProductAccessInfo localCustomerProductAccessInfo = new CustomerProductAccessInfo();
/*  22: 21 */     localCustomerProductAccessInfo.consumerID = paramCustomerProductAccessInfo.consumerID;
/*  23: 22 */     localCustomerProductAccessInfo.productType = paramCustomerProductAccessInfo.productType;
/*  24: 23 */     localCustomerProductAccessInfo.accessType = paramCustomerProductAccessInfo.accessType;
/*  25: 24 */     localCustomerProductAccessInfo.statusType = paramCustomerProductAccessInfo.statusType;
/*  26: 25 */     localCustomerProductAccessInfo.submitDate = paramCustomerProductAccessInfo.submitDate;
/*  27: 26 */     return localCustomerProductAccessInfo;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static CustomerProductAccessInfo read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     CustomerProductAccessInfo localCustomerProductAccessInfo = new CustomerProductAccessInfo();
/*  33: 33 */     localCustomerProductAccessInfo.consumerID = paramInputStream.read_string();
/*  34: 34 */     localCustomerProductAccessInfo.productType = paramInputStream.read_string();
/*  35: 35 */     localCustomerProductAccessInfo.accessType = paramInputStream.read_string();
/*  36: 36 */     localCustomerProductAccessInfo.statusType = paramInputStream.read_string();
/*  37: 37 */     localCustomerProductAccessInfo.submitDate = paramInputStream.read_string();
/*  38: 38 */     return localCustomerProductAccessInfo;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, CustomerProductAccessInfo paramCustomerProductAccessInfo)
/*  42:    */   {
/*  43: 45 */     if (paramCustomerProductAccessInfo == null) {
/*  44: 47 */       paramCustomerProductAccessInfo = new CustomerProductAccessInfo();
/*  45:    */     }
/*  46: 49 */     paramOutputStream.write_string(paramCustomerProductAccessInfo.consumerID);
/*  47: 50 */     paramOutputStream.write_string(paramCustomerProductAccessInfo.productType);
/*  48: 51 */     paramOutputStream.write_string(paramCustomerProductAccessInfo.accessType);
/*  49: 52 */     paramOutputStream.write_string(paramCustomerProductAccessInfo.statusType);
/*  50: 53 */     paramOutputStream.write_string(paramCustomerProductAccessInfo.submitDate);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::ffs::bpw::interfaces::CustomerProductAccessInfo";
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static TypeCode type()
/*  59:    */   {
/*  60: 65 */     if (_type == null)
/*  61:    */     {
/*  62: 67 */       ORB localORB = ORB.init();
/*  63: 68 */       StructMember[] arrayOfStructMember = 
/*  64: 69 */         {
/*  65: 70 */         new StructMember("consumerID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  66: 71 */         new StructMember("productType", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  67: 72 */         new StructMember("accessType", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  68: 73 */         new StructMember("statusType", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  69: 74 */         new StructMember("submitDate", localORB.get_primitive_tc(TCKind.tk_string), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "CustomerProductAccessInfo", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, CustomerProductAccessInfo paramCustomerProductAccessInfo)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramCustomerProductAccessInfo);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static CustomerProductAccessInfo extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/ffs/bpw/interfaces/CustomerProductAccessInfo:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CustomerProductAccessInfoHelper
 * JD-Core Version:    0.7.0.1
 */