/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
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
/*  12:    */ public abstract class TypeXferInfoAggregateHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypeXferInfoAggregate clone(TypeXferInfoAggregate paramTypeXferInfoAggregate)
/*  17:    */   {
/*  18: 16 */     if (paramTypeXferInfoAggregate == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypeXferInfoAggregate localTypeXferInfoAggregate = new TypeXferInfoAggregate();
/*  22: 21 */     localTypeXferInfoAggregate.BCCAcctFromUn = TypeBCCAcctFromUnHelper.clone(paramTypeXferInfoAggregate.BCCAcctFromUn);
/*  23: 22 */     localTypeXferInfoAggregate.BCCAcctToUn = TypeBCCAcctToUnHelper.clone(paramTypeXferInfoAggregate.BCCAcctToUn);
/*  24: 23 */     localTypeXferInfoAggregate.TrnAmt = paramTypeXferInfoAggregate.TrnAmt;
/*  25: 24 */     localTypeXferInfoAggregate.DtDue = paramTypeXferInfoAggregate.DtDue;
/*  26: 25 */     localTypeXferInfoAggregate.DtDueExists = paramTypeXferInfoAggregate.DtDueExists;
/*  27: 26 */     return localTypeXferInfoAggregate;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static TypeXferInfoAggregate read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     TypeXferInfoAggregate localTypeXferInfoAggregate = new TypeXferInfoAggregate();
/*  33: 33 */     localTypeXferInfoAggregate.BCCAcctFromUn = TypeBCCAcctFromUnHelper.read(paramInputStream);
/*  34: 34 */     localTypeXferInfoAggregate.BCCAcctToUn = TypeBCCAcctToUnHelper.read(paramInputStream);
/*  35: 35 */     localTypeXferInfoAggregate.TrnAmt = paramInputStream.read_double();
/*  36: 36 */     localTypeXferInfoAggregate.DtDue = paramInputStream.read_string();
/*  37: 37 */     localTypeXferInfoAggregate.DtDueExists = paramInputStream.read_boolean();
/*  38: 38 */     return localTypeXferInfoAggregate;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, TypeXferInfoAggregate paramTypeXferInfoAggregate)
/*  42:    */   {
/*  43: 45 */     if (paramTypeXferInfoAggregate == null) {
/*  44: 47 */       paramTypeXferInfoAggregate = new TypeXferInfoAggregate();
/*  45:    */     }
/*  46: 49 */     TypeBCCAcctFromUnHelper.write(paramOutputStream, paramTypeXferInfoAggregate.BCCAcctFromUn);
/*  47: 50 */     TypeBCCAcctToUnHelper.write(paramOutputStream, paramTypeXferInfoAggregate.BCCAcctToUn);
/*  48: 51 */     paramOutputStream.write_double(paramTypeXferInfoAggregate.TrnAmt);
/*  49: 52 */     paramOutputStream.write_string(paramTypeXferInfoAggregate.DtDue);
/*  50: 53 */     paramOutputStream.write_boolean(paramTypeXferInfoAggregate.DtDueExists);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeXferInfoAggregate";
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static TypeCode type()
/*  59:    */   {
/*  60: 65 */     if (_type == null)
/*  61:    */     {
/*  62: 67 */       ORB localORB = ORB.init();
/*  63: 68 */       StructMember[] arrayOfStructMember = 
/*  64: 69 */         {
/*  65: 70 */         new StructMember("BCCAcctFromUn", TypeBCCAcctFromUnHelper.type(), null), 
/*  66: 71 */         new StructMember("BCCAcctToUn", TypeBCCAcctToUnHelper.type(), null), 
/*  67: 72 */         new StructMember("TrnAmt", localORB.get_primitive_tc(TCKind.tk_double), null), 
/*  68: 73 */         new StructMember("DtDue", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  69: 74 */         new StructMember("DtDueExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "TypeXferInfoAggregate", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, TypeXferInfoAggregate paramTypeXferInfoAggregate)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramTypeXferInfoAggregate);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static TypeXferInfoAggregate extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeXferInfoAggregate:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferInfoAggregateHelper
 * JD-Core Version:    0.7.0.1
 */