/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*   2:    */ 
/*   3:    */ import com.sybase.ejb.cts.StringSeqHelper;
/*   4:    */ import org.omg.CORBA.Any;
/*   5:    */ import org.omg.CORBA.BAD_OPERATION;
/*   6:    */ import org.omg.CORBA.ORB;
/*   7:    */ import org.omg.CORBA.StructMember;
/*   8:    */ import org.omg.CORBA.TCKind;
/*   9:    */ import org.omg.CORBA.TypeCode;
/*  10:    */ import org.omg.CORBA.portable.InputStream;
/*  11:    */ import org.omg.CORBA.portable.OutputStream;
/*  12:    */ 
/*  13:    */ public abstract class TypePayeeRqAggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypePayeeRqAggregate clone(TypePayeeRqAggregate paramTypePayeeRqAggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypePayeeRqAggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypePayeeRqAggregate localTypePayeeRqAggregate = new TypePayeeRqAggregate();
/*  23: 21 */     localTypePayeeRqAggregate.PayeeRqUn = TypePayeeRqUnHelper.clone(paramTypePayeeRqAggregate.PayeeRqUn);
/*  24: 22 */     localTypePayeeRqAggregate.BankAcctTo = TypeBankAcctToAggregateHelper.clone(paramTypePayeeRqAggregate.BankAcctTo);
/*  25: 23 */     localTypePayeeRqAggregate.BankAcctToExists = paramTypePayeeRqAggregate.BankAcctToExists;
/*  26: 24 */     localTypePayeeRqAggregate.PayAcct = StringSeqHelper.clone(paramTypePayeeRqAggregate.PayAcct);
/*  27: 25 */     localTypePayeeRqAggregate.PayAcctExists = paramTypePayeeRqAggregate.PayAcctExists;
/*  28: 26 */     localTypePayeeRqAggregate.NameOnAcct = StringSeqHelper.clone(paramTypePayeeRqAggregate.NameOnAcct);
/*  29: 27 */     localTypePayeeRqAggregate.NameOnAcctExists = paramTypePayeeRqAggregate.NameOnAcctExists;
/*  30: 28 */     return localTypePayeeRqAggregate;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static TypePayeeRqAggregate read(InputStream paramInputStream)
/*  34:    */   {
/*  35: 34 */     TypePayeeRqAggregate localTypePayeeRqAggregate = new TypePayeeRqAggregate();
/*  36: 35 */     localTypePayeeRqAggregate.PayeeRqUn = TypePayeeRqUnHelper.read(paramInputStream);
/*  37: 36 */     localTypePayeeRqAggregate.BankAcctTo = TypeBankAcctToAggregateHelper.read(paramInputStream);
/*  38: 37 */     localTypePayeeRqAggregate.BankAcctToExists = paramInputStream.read_boolean();
/*  39: 38 */     localTypePayeeRqAggregate.PayAcct = StringSeqHelper.read(paramInputStream);
/*  40: 39 */     localTypePayeeRqAggregate.PayAcctExists = paramInputStream.read_boolean();
/*  41: 40 */     localTypePayeeRqAggregate.NameOnAcct = StringSeqHelper.read(paramInputStream);
/*  42: 41 */     localTypePayeeRqAggregate.NameOnAcctExists = paramInputStream.read_boolean();
/*  43: 42 */     return localTypePayeeRqAggregate;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static void write(OutputStream paramOutputStream, TypePayeeRqAggregate paramTypePayeeRqAggregate)
/*  47:    */   {
/*  48: 49 */     if (paramTypePayeeRqAggregate == null) {
/*  49: 51 */       paramTypePayeeRqAggregate = new TypePayeeRqAggregate();
/*  50:    */     }
/*  51: 53 */     TypePayeeRqUnHelper.write(paramOutputStream, paramTypePayeeRqAggregate.PayeeRqUn);
/*  52: 54 */     TypeBankAcctToAggregateHelper.write(paramOutputStream, paramTypePayeeRqAggregate.BankAcctTo);
/*  53: 55 */     paramOutputStream.write_boolean(paramTypePayeeRqAggregate.BankAcctToExists);
/*  54: 56 */     StringSeqHelper.write(paramOutputStream, paramTypePayeeRqAggregate.PayAcct);
/*  55: 57 */     paramOutputStream.write_boolean(paramTypePayeeRqAggregate.PayAcctExists);
/*  56: 58 */     StringSeqHelper.write(paramOutputStream, paramTypePayeeRqAggregate.NameOnAcct);
/*  57: 59 */     paramOutputStream.write_boolean(paramTypePayeeRqAggregate.NameOnAcctExists);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static String _idl()
/*  61:    */   {
/*  62: 64 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypePayeeRqAggregate";
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static TypeCode type()
/*  66:    */   {
/*  67: 71 */     if (_type == null)
/*  68:    */     {
/*  69: 73 */       ORB localORB = ORB.init();
/*  70: 74 */       StructMember[] arrayOfStructMember = 
/*  71: 75 */         {
/*  72: 76 */         new StructMember("PayeeRqUn", TypePayeeRqUnHelper.type(), null), 
/*  73: 77 */         new StructMember("BankAcctTo", TypeBankAcctToAggregateHelper.type(), null), 
/*  74: 78 */         new StructMember("BankAcctToExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  75: 79 */         new StructMember("PayAcct", StringSeqHelper.type(), null), 
/*  76: 80 */         new StructMember("PayAcctExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  77: 81 */         new StructMember("NameOnAcct", StringSeqHelper.type(), null), 
/*  78: 82 */         new StructMember("NameOnAcctExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  79:    */       
/*  80: 84 */       _type = localORB.create_struct_tc(id(), "TypePayeeRqAggregate", arrayOfStructMember);
/*  81:    */     }
/*  82: 86 */     return _type;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static void insert(Any paramAny, TypePayeeRqAggregate paramTypePayeeRqAggregate)
/*  86:    */   {
/*  87: 93 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  88: 94 */     write(localOutputStream, paramTypePayeeRqAggregate);
/*  89: 95 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static TypePayeeRqAggregate extract(Any paramAny)
/*  93:    */   {
/*  94:101 */     if (!paramAny.type().equal(type())) {
/*  95:103 */       throw new BAD_OPERATION();
/*  96:    */     }
/*  97:105 */     return read(paramAny.create_input_stream());
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static String id()
/* 101:    */   {
/* 102:110 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypePayeeRqAggregate:1.0";
/* 103:    */   }
/* 104:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeRqAggregateHelper
 * JD-Core Version:    0.7.0.1
 */