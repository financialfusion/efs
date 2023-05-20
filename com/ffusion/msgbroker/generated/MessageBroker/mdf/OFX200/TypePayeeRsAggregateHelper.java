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
/*  13:    */ public abstract class TypePayeeRsAggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypePayeeRsAggregate clone(TypePayeeRsAggregate paramTypePayeeRsAggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypePayeeRsAggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypePayeeRsAggregate localTypePayeeRsAggregate = new TypePayeeRsAggregate();
/*  23: 21 */     localTypePayeeRsAggregate.PayeeLstID = paramTypePayeeRsAggregate.PayeeLstID;
/*  24: 22 */     localTypePayeeRsAggregate.PayeeRsCm = TypePayeeRsCmHelper.clone(paramTypePayeeRsAggregate.PayeeRsCm);
/*  25: 23 */     localTypePayeeRsAggregate.PayeeRsCmExists = paramTypePayeeRsAggregate.PayeeRsCmExists;
/*  26: 24 */     localTypePayeeRsAggregate.ExtdPayee = TypeExtdPayeeAggregateHelper.clone(paramTypePayeeRsAggregate.ExtdPayee);
/*  27: 25 */     localTypePayeeRsAggregate.ExtdPayeeExists = paramTypePayeeRsAggregate.ExtdPayeeExists;
/*  28: 26 */     localTypePayeeRsAggregate.PayAcct = StringSeqHelper.clone(paramTypePayeeRsAggregate.PayAcct);
/*  29: 27 */     localTypePayeeRsAggregate.PayAcctExists = paramTypePayeeRsAggregate.PayAcctExists;
/*  30: 28 */     localTypePayeeRsAggregate.NameOnAcct = StringSeqHelper.clone(paramTypePayeeRsAggregate.NameOnAcct);
/*  31: 29 */     localTypePayeeRsAggregate.NameOnAcctExists = paramTypePayeeRsAggregate.NameOnAcctExists;
/*  32: 30 */     return localTypePayeeRsAggregate;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static TypePayeeRsAggregate read(InputStream paramInputStream)
/*  36:    */   {
/*  37: 36 */     TypePayeeRsAggregate localTypePayeeRsAggregate = new TypePayeeRsAggregate();
/*  38: 37 */     localTypePayeeRsAggregate.PayeeLstID = paramInputStream.read_string();
/*  39: 38 */     localTypePayeeRsAggregate.PayeeRsCm = TypePayeeRsCmHelper.read(paramInputStream);
/*  40: 39 */     localTypePayeeRsAggregate.PayeeRsCmExists = paramInputStream.read_boolean();
/*  41: 40 */     localTypePayeeRsAggregate.ExtdPayee = TypeExtdPayeeAggregateHelper.read(paramInputStream);
/*  42: 41 */     localTypePayeeRsAggregate.ExtdPayeeExists = paramInputStream.read_boolean();
/*  43: 42 */     localTypePayeeRsAggregate.PayAcct = StringSeqHelper.read(paramInputStream);
/*  44: 43 */     localTypePayeeRsAggregate.PayAcctExists = paramInputStream.read_boolean();
/*  45: 44 */     localTypePayeeRsAggregate.NameOnAcct = StringSeqHelper.read(paramInputStream);
/*  46: 45 */     localTypePayeeRsAggregate.NameOnAcctExists = paramInputStream.read_boolean();
/*  47: 46 */     return localTypePayeeRsAggregate;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static void write(OutputStream paramOutputStream, TypePayeeRsAggregate paramTypePayeeRsAggregate)
/*  51:    */   {
/*  52: 53 */     if (paramTypePayeeRsAggregate == null) {
/*  53: 55 */       paramTypePayeeRsAggregate = new TypePayeeRsAggregate();
/*  54:    */     }
/*  55: 57 */     paramOutputStream.write_string(paramTypePayeeRsAggregate.PayeeLstID);
/*  56: 58 */     TypePayeeRsCmHelper.write(paramOutputStream, paramTypePayeeRsAggregate.PayeeRsCm);
/*  57: 59 */     paramOutputStream.write_boolean(paramTypePayeeRsAggregate.PayeeRsCmExists);
/*  58: 60 */     TypeExtdPayeeAggregateHelper.write(paramOutputStream, paramTypePayeeRsAggregate.ExtdPayee);
/*  59: 61 */     paramOutputStream.write_boolean(paramTypePayeeRsAggregate.ExtdPayeeExists);
/*  60: 62 */     StringSeqHelper.write(paramOutputStream, paramTypePayeeRsAggregate.PayAcct);
/*  61: 63 */     paramOutputStream.write_boolean(paramTypePayeeRsAggregate.PayAcctExists);
/*  62: 64 */     StringSeqHelper.write(paramOutputStream, paramTypePayeeRsAggregate.NameOnAcct);
/*  63: 65 */     paramOutputStream.write_boolean(paramTypePayeeRsAggregate.NameOnAcctExists);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static String _idl()
/*  67:    */   {
/*  68: 70 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypePayeeRsAggregate";
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
/*  79: 83 */         new StructMember("PayeeRsCm", TypePayeeRsCmHelper.type(), null), 
/*  80: 84 */         new StructMember("PayeeRsCmExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  81: 85 */         new StructMember("ExtdPayee", TypeExtdPayeeAggregateHelper.type(), null), 
/*  82: 86 */         new StructMember("ExtdPayeeExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  83: 87 */         new StructMember("PayAcct", StringSeqHelper.type(), null), 
/*  84: 88 */         new StructMember("PayAcctExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  85: 89 */         new StructMember("NameOnAcct", StringSeqHelper.type(), null), 
/*  86: 90 */         new StructMember("NameOnAcctExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  87:    */       
/*  88: 92 */       _type = localORB.create_struct_tc(id(), "TypePayeeRsAggregate", arrayOfStructMember);
/*  89:    */     }
/*  90: 94 */     return _type;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static void insert(Any paramAny, TypePayeeRsAggregate paramTypePayeeRsAggregate)
/*  94:    */   {
/*  95:101 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  96:102 */     write(localOutputStream, paramTypePayeeRsAggregate);
/*  97:103 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static TypePayeeRsAggregate extract(Any paramAny)
/* 101:    */   {
/* 102:109 */     if (!paramAny.type().equal(type())) {
/* 103:111 */       throw new BAD_OPERATION();
/* 104:    */     }
/* 105:113 */     return read(paramAny.create_input_stream());
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static String id()
/* 109:    */   {
/* 110:118 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypePayeeRsAggregate:1.0";
/* 111:    */   }
/* 112:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeRsAggregateHelper
 * JD-Core Version:    0.7.0.1
 */