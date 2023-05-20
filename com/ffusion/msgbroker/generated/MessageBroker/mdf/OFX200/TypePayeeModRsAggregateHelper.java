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
/*  13:    */ public abstract class TypePayeeModRsAggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypePayeeModRsAggregate clone(TypePayeeModRsAggregate paramTypePayeeModRsAggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypePayeeModRsAggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypePayeeModRsAggregate localTypePayeeModRsAggregate = new TypePayeeModRsAggregate();
/*  23: 21 */     localTypePayeeModRsAggregate.PayeeLstID = paramTypePayeeModRsAggregate.PayeeLstID;
/*  24: 22 */     localTypePayeeModRsAggregate.PayeeModRsCm = TypePayeeModRsCmHelper.clone(paramTypePayeeModRsAggregate.PayeeModRsCm);
/*  25: 23 */     localTypePayeeModRsAggregate.PayeeModRsCmExists = paramTypePayeeModRsAggregate.PayeeModRsCmExists;
/*  26: 24 */     localTypePayeeModRsAggregate.PayAcct = StringSeqHelper.clone(paramTypePayeeModRsAggregate.PayAcct);
/*  27: 25 */     localTypePayeeModRsAggregate.PayAcctExists = paramTypePayeeModRsAggregate.PayAcctExists;
/*  28: 26 */     localTypePayeeModRsAggregate.ExtdPayee = TypeExtdPayeeAggregateHelper.clone(paramTypePayeeModRsAggregate.ExtdPayee);
/*  29: 27 */     localTypePayeeModRsAggregate.ExtdPayeeExists = paramTypePayeeModRsAggregate.ExtdPayeeExists;
/*  30: 28 */     localTypePayeeModRsAggregate.NameOnAcct = StringSeqHelper.clone(paramTypePayeeModRsAggregate.NameOnAcct);
/*  31: 29 */     localTypePayeeModRsAggregate.NameOnAcctExists = paramTypePayeeModRsAggregate.NameOnAcctExists;
/*  32: 30 */     return localTypePayeeModRsAggregate;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static TypePayeeModRsAggregate read(InputStream paramInputStream)
/*  36:    */   {
/*  37: 36 */     TypePayeeModRsAggregate localTypePayeeModRsAggregate = new TypePayeeModRsAggregate();
/*  38: 37 */     localTypePayeeModRsAggregate.PayeeLstID = paramInputStream.read_string();
/*  39: 38 */     localTypePayeeModRsAggregate.PayeeModRsCm = TypePayeeModRsCmHelper.read(paramInputStream);
/*  40: 39 */     localTypePayeeModRsAggregate.PayeeModRsCmExists = paramInputStream.read_boolean();
/*  41: 40 */     localTypePayeeModRsAggregate.PayAcct = StringSeqHelper.read(paramInputStream);
/*  42: 41 */     localTypePayeeModRsAggregate.PayAcctExists = paramInputStream.read_boolean();
/*  43: 42 */     localTypePayeeModRsAggregate.ExtdPayee = TypeExtdPayeeAggregateHelper.read(paramInputStream);
/*  44: 43 */     localTypePayeeModRsAggregate.ExtdPayeeExists = paramInputStream.read_boolean();
/*  45: 44 */     localTypePayeeModRsAggregate.NameOnAcct = StringSeqHelper.read(paramInputStream);
/*  46: 45 */     localTypePayeeModRsAggregate.NameOnAcctExists = paramInputStream.read_boolean();
/*  47: 46 */     return localTypePayeeModRsAggregate;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static void write(OutputStream paramOutputStream, TypePayeeModRsAggregate paramTypePayeeModRsAggregate)
/*  51:    */   {
/*  52: 53 */     if (paramTypePayeeModRsAggregate == null) {
/*  53: 55 */       paramTypePayeeModRsAggregate = new TypePayeeModRsAggregate();
/*  54:    */     }
/*  55: 57 */     paramOutputStream.write_string(paramTypePayeeModRsAggregate.PayeeLstID);
/*  56: 58 */     TypePayeeModRsCmHelper.write(paramOutputStream, paramTypePayeeModRsAggregate.PayeeModRsCm);
/*  57: 59 */     paramOutputStream.write_boolean(paramTypePayeeModRsAggregate.PayeeModRsCmExists);
/*  58: 60 */     StringSeqHelper.write(paramOutputStream, paramTypePayeeModRsAggregate.PayAcct);
/*  59: 61 */     paramOutputStream.write_boolean(paramTypePayeeModRsAggregate.PayAcctExists);
/*  60: 62 */     TypeExtdPayeeAggregateHelper.write(paramOutputStream, paramTypePayeeModRsAggregate.ExtdPayee);
/*  61: 63 */     paramOutputStream.write_boolean(paramTypePayeeModRsAggregate.ExtdPayeeExists);
/*  62: 64 */     StringSeqHelper.write(paramOutputStream, paramTypePayeeModRsAggregate.NameOnAcct);
/*  63: 65 */     paramOutputStream.write_boolean(paramTypePayeeModRsAggregate.NameOnAcctExists);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static String _idl()
/*  67:    */   {
/*  68: 70 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypePayeeModRsAggregate";
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static TypeCode type()
/*  72:    */   {
/*  73: 77 */     if (_type == null)
/*  74:    */     {
/*  75: 79 */       ORB localORB = ORB.init();
/*  76: 80 */       StructMember[] arrayOfStructMember = 
/*  77: 81 */         {
/*  78: 82 */         new StructMember("PayeeLstID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  79: 83 */         new StructMember("PayeeModRsCm", TypePayeeModRsCmHelper.type(), null), 
/*  80: 84 */         new StructMember("PayeeModRsCmExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  81: 85 */         new StructMember("PayAcct", StringSeqHelper.type(), null), 
/*  82: 86 */         new StructMember("PayAcctExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  83: 87 */         new StructMember("ExtdPayee", TypeExtdPayeeAggregateHelper.type(), null), 
/*  84: 88 */         new StructMember("ExtdPayeeExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  85: 89 */         new StructMember("NameOnAcct", StringSeqHelper.type(), null), 
/*  86: 90 */         new StructMember("NameOnAcctExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  87:    */       
/*  88: 92 */       _type = localORB.create_struct_tc(id(), "TypePayeeModRsAggregate", arrayOfStructMember);
/*  89:    */     }
/*  90: 94 */     return _type;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static void insert(Any paramAny, TypePayeeModRsAggregate paramTypePayeeModRsAggregate)
/*  94:    */   {
/*  95:101 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  96:102 */     write(localOutputStream, paramTypePayeeModRsAggregate);
/*  97:103 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static TypePayeeModRsAggregate extract(Any paramAny)
/* 101:    */   {
/* 102:109 */     if (!paramAny.type().equal(type())) {
/* 103:111 */       throw new BAD_OPERATION();
/* 104:    */     }
/* 105:113 */     return read(paramAny.create_input_stream());
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static String id()
/* 109:    */   {
/* 110:118 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypePayeeModRsAggregate:1.0";
/* 111:    */   }
/* 112:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeModRsAggregateHelper
 * JD-Core Version:    0.7.0.1
 */