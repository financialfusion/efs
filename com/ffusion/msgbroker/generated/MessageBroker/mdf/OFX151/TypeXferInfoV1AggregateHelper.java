/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
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
/*  12:    */ public abstract class TypeXferInfoV1AggregateHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypeXferInfoV1Aggregate clone(TypeXferInfoV1Aggregate paramTypeXferInfoV1Aggregate)
/*  17:    */   {
/*  18: 16 */     if (paramTypeXferInfoV1Aggregate == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypeXferInfoV1Aggregate localTypeXferInfoV1Aggregate = new TypeXferInfoV1Aggregate();
/*  22: 21 */     localTypeXferInfoV1Aggregate.BCCAcctFromV1Un = TypeBCCAcctFromV1UnHelper.clone(paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un);
/*  23: 22 */     localTypeXferInfoV1Aggregate.BCCAcctToV1Un = TypeBCCAcctToV1UnHelper.clone(paramTypeXferInfoV1Aggregate.BCCAcctToV1Un);
/*  24: 23 */     localTypeXferInfoV1Aggregate.TrnAmt = paramTypeXferInfoV1Aggregate.TrnAmt;
/*  25: 24 */     localTypeXferInfoV1Aggregate.DtDue = paramTypeXferInfoV1Aggregate.DtDue;
/*  26: 25 */     localTypeXferInfoV1Aggregate.DtDueExists = paramTypeXferInfoV1Aggregate.DtDueExists;
/*  27: 26 */     return localTypeXferInfoV1Aggregate;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static TypeXferInfoV1Aggregate read(InputStream paramInputStream)
/*  31:    */   {
/*  32: 32 */     TypeXferInfoV1Aggregate localTypeXferInfoV1Aggregate = new TypeXferInfoV1Aggregate();
/*  33: 33 */     localTypeXferInfoV1Aggregate.BCCAcctFromV1Un = TypeBCCAcctFromV1UnHelper.read(paramInputStream);
/*  34: 34 */     localTypeXferInfoV1Aggregate.BCCAcctToV1Un = TypeBCCAcctToV1UnHelper.read(paramInputStream);
/*  35: 35 */     localTypeXferInfoV1Aggregate.TrnAmt = paramInputStream.read_double();
/*  36: 36 */     localTypeXferInfoV1Aggregate.DtDue = paramInputStream.read_string();
/*  37: 37 */     localTypeXferInfoV1Aggregate.DtDueExists = paramInputStream.read_boolean();
/*  38: 38 */     return localTypeXferInfoV1Aggregate;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void write(OutputStream paramOutputStream, TypeXferInfoV1Aggregate paramTypeXferInfoV1Aggregate)
/*  42:    */   {
/*  43: 45 */     if (paramTypeXferInfoV1Aggregate == null) {
/*  44: 47 */       paramTypeXferInfoV1Aggregate = new TypeXferInfoV1Aggregate();
/*  45:    */     }
/*  46: 49 */     TypeBCCAcctFromV1UnHelper.write(paramOutputStream, paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un);
/*  47: 50 */     TypeBCCAcctToV1UnHelper.write(paramOutputStream, paramTypeXferInfoV1Aggregate.BCCAcctToV1Un);
/*  48: 51 */     paramOutputStream.write_double(paramTypeXferInfoV1Aggregate.TrnAmt);
/*  49: 52 */     paramOutputStream.write_string(paramTypeXferInfoV1Aggregate.DtDue);
/*  50: 53 */     paramOutputStream.write_boolean(paramTypeXferInfoV1Aggregate.DtDueExists);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static String _idl()
/*  54:    */   {
/*  55: 58 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeXferInfoV1Aggregate";
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static TypeCode type()
/*  59:    */   {
/*  60: 65 */     if (_type == null)
/*  61:    */     {
/*  62: 67 */       ORB localORB = ORB.init();
/*  63: 68 */       StructMember[] arrayOfStructMember = 
/*  64: 69 */         {
/*  65: 70 */         new StructMember("BCCAcctFromV1Un", TypeBCCAcctFromV1UnHelper.type(), null), 
/*  66: 71 */         new StructMember("BCCAcctToV1Un", TypeBCCAcctToV1UnHelper.type(), null), 
/*  67: 72 */         new StructMember("TrnAmt", localORB.get_primitive_tc(TCKind.tk_double), null), 
/*  68: 73 */         new StructMember("DtDue", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  69: 74 */         new StructMember("DtDueExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  70:    */       
/*  71: 76 */       _type = localORB.create_struct_tc(id(), "TypeXferInfoV1Aggregate", arrayOfStructMember);
/*  72:    */     }
/*  73: 78 */     return _type;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void insert(Any paramAny, TypeXferInfoV1Aggregate paramTypeXferInfoV1Aggregate)
/*  77:    */   {
/*  78: 85 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  79: 86 */     write(localOutputStream, paramTypeXferInfoV1Aggregate);
/*  80: 87 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static TypeXferInfoV1Aggregate extract(Any paramAny)
/*  84:    */   {
/*  85: 93 */     if (!paramAny.type().equal(type())) {
/*  86: 95 */       throw new BAD_OPERATION();
/*  87:    */     }
/*  88: 97 */     return read(paramAny.create_input_stream());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String id()
/*  92:    */   {
/*  93:102 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeXferInfoV1Aggregate:1.0";
/*  94:    */   }
/*  95:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferInfoV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */