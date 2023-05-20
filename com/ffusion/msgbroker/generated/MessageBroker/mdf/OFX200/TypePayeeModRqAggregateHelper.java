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
/*  13:    */ public abstract class TypePayeeModRqAggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypePayeeModRqAggregate clone(TypePayeeModRqAggregate paramTypePayeeModRqAggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypePayeeModRqAggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypePayeeModRqAggregate localTypePayeeModRqAggregate = new TypePayeeModRqAggregate();
/*  23: 21 */     localTypePayeeModRqAggregate.PayeeLstID = paramTypePayeeModRqAggregate.PayeeLstID;
/*  24: 22 */     localTypePayeeModRqAggregate.PayeeModRqCm = TypePayeeModRqCmHelper.clone(paramTypePayeeModRqAggregate.PayeeModRqCm);
/*  25: 23 */     localTypePayeeModRqAggregate.PayeeModRqCmExists = paramTypePayeeModRqAggregate.PayeeModRqCmExists;
/*  26: 24 */     localTypePayeeModRqAggregate.PayAcct = StringSeqHelper.clone(paramTypePayeeModRqAggregate.PayAcct);
/*  27: 25 */     localTypePayeeModRqAggregate.PayAcctExists = paramTypePayeeModRqAggregate.PayAcctExists;
/*  28: 26 */     localTypePayeeModRqAggregate.NameOnAcct = StringSeqHelper.clone(paramTypePayeeModRqAggregate.NameOnAcct);
/*  29: 27 */     localTypePayeeModRqAggregate.NameOnAcctExists = paramTypePayeeModRqAggregate.NameOnAcctExists;
/*  30: 28 */     return localTypePayeeModRqAggregate;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static TypePayeeModRqAggregate read(InputStream paramInputStream)
/*  34:    */   {
/*  35: 34 */     TypePayeeModRqAggregate localTypePayeeModRqAggregate = new TypePayeeModRqAggregate();
/*  36: 35 */     localTypePayeeModRqAggregate.PayeeLstID = paramInputStream.read_string();
/*  37: 36 */     localTypePayeeModRqAggregate.PayeeModRqCm = TypePayeeModRqCmHelper.read(paramInputStream);
/*  38: 37 */     localTypePayeeModRqAggregate.PayeeModRqCmExists = paramInputStream.read_boolean();
/*  39: 38 */     localTypePayeeModRqAggregate.PayAcct = StringSeqHelper.read(paramInputStream);
/*  40: 39 */     localTypePayeeModRqAggregate.PayAcctExists = paramInputStream.read_boolean();
/*  41: 40 */     localTypePayeeModRqAggregate.NameOnAcct = StringSeqHelper.read(paramInputStream);
/*  42: 41 */     localTypePayeeModRqAggregate.NameOnAcctExists = paramInputStream.read_boolean();
/*  43: 42 */     return localTypePayeeModRqAggregate;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static void write(OutputStream paramOutputStream, TypePayeeModRqAggregate paramTypePayeeModRqAggregate)
/*  47:    */   {
/*  48: 49 */     if (paramTypePayeeModRqAggregate == null) {
/*  49: 51 */       paramTypePayeeModRqAggregate = new TypePayeeModRqAggregate();
/*  50:    */     }
/*  51: 53 */     paramOutputStream.write_string(paramTypePayeeModRqAggregate.PayeeLstID);
/*  52: 54 */     TypePayeeModRqCmHelper.write(paramOutputStream, paramTypePayeeModRqAggregate.PayeeModRqCm);
/*  53: 55 */     paramOutputStream.write_boolean(paramTypePayeeModRqAggregate.PayeeModRqCmExists);
/*  54: 56 */     StringSeqHelper.write(paramOutputStream, paramTypePayeeModRqAggregate.PayAcct);
/*  55: 57 */     paramOutputStream.write_boolean(paramTypePayeeModRqAggregate.PayAcctExists);
/*  56: 58 */     StringSeqHelper.write(paramOutputStream, paramTypePayeeModRqAggregate.NameOnAcct);
/*  57: 59 */     paramOutputStream.write_boolean(paramTypePayeeModRqAggregate.NameOnAcctExists);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static String _idl()
/*  61:    */   {
/*  62: 64 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypePayeeModRqAggregate";
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static TypeCode type()
/*  66:    */   {
/*  67: 71 */     if (_type == null)
/*  68:    */     {
/*  69: 73 */       ORB localORB = ORB.init();
/*  70: 74 */       StructMember[] arrayOfStructMember = 
/*  71: 75 */         {
/*  72: 76 */         new StructMember("PayeeLstID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  73: 77 */         new StructMember("PayeeModRqCm", TypePayeeModRqCmHelper.type(), null), 
/*  74: 78 */         new StructMember("PayeeModRqCmExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  75: 79 */         new StructMember("PayAcct", StringSeqHelper.type(), null), 
/*  76: 80 */         new StructMember("PayAcctExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  77: 81 */         new StructMember("NameOnAcct", StringSeqHelper.type(), null), 
/*  78: 82 */         new StructMember("NameOnAcctExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  79:    */       
/*  80: 84 */       _type = localORB.create_struct_tc(id(), "TypePayeeModRqAggregate", arrayOfStructMember);
/*  81:    */     }
/*  82: 86 */     return _type;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static void insert(Any paramAny, TypePayeeModRqAggregate paramTypePayeeModRqAggregate)
/*  86:    */   {
/*  87: 93 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  88: 94 */     write(localOutputStream, paramTypePayeeModRqAggregate);
/*  89: 95 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static TypePayeeModRqAggregate extract(Any paramAny)
/*  93:    */   {
/*  94:101 */     if (!paramAny.type().equal(type())) {
/*  95:103 */       throw new BAD_OPERATION();
/*  96:    */     }
/*  97:105 */     return read(paramAny.create_input_stream());
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static String id()
/* 101:    */   {
/* 102:110 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypePayeeModRqAggregate:1.0";
/* 103:    */   }
/* 104:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeModRqAggregateHelper
 * JD-Core Version:    0.7.0.1
 */