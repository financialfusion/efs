/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
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
/*  13:    */ public abstract class TypePayeeModRsV1AggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypePayeeModRsV1Aggregate clone(TypePayeeModRsV1Aggregate paramTypePayeeModRsV1Aggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypePayeeModRsV1Aggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypePayeeModRsV1Aggregate localTypePayeeModRsV1Aggregate = new TypePayeeModRsV1Aggregate();
/*  23: 21 */     localTypePayeeModRsV1Aggregate.PayeeLstID = paramTypePayeeModRsV1Aggregate.PayeeLstID;
/*  24: 22 */     localTypePayeeModRsV1Aggregate.PayeeModRsV1Cm = TypePayeeModRsV1CmHelper.clone(paramTypePayeeModRsV1Aggregate.PayeeModRsV1Cm);
/*  25: 23 */     localTypePayeeModRsV1Aggregate.PayeeModRsV1CmExists = paramTypePayeeModRsV1Aggregate.PayeeModRsV1CmExists;
/*  26: 24 */     localTypePayeeModRsV1Aggregate.PayAcct = StringSeqHelper.clone(paramTypePayeeModRsV1Aggregate.PayAcct);
/*  27: 25 */     localTypePayeeModRsV1Aggregate.ExtdPayee = TypeExtdPayeeV1AggregateHelper.clone(paramTypePayeeModRsV1Aggregate.ExtdPayee);
/*  28: 26 */     localTypePayeeModRsV1Aggregate.ExtdPayeeExists = paramTypePayeeModRsV1Aggregate.ExtdPayeeExists;
/*  29: 27 */     localTypePayeeModRsV1Aggregate.NameOnAcct = StringSeqHelper.clone(paramTypePayeeModRsV1Aggregate.NameOnAcct);
/*  30: 28 */     localTypePayeeModRsV1Aggregate.NameOnAcctExists = paramTypePayeeModRsV1Aggregate.NameOnAcctExists;
/*  31: 29 */     return localTypePayeeModRsV1Aggregate;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static TypePayeeModRsV1Aggregate read(InputStream paramInputStream)
/*  35:    */   {
/*  36: 35 */     TypePayeeModRsV1Aggregate localTypePayeeModRsV1Aggregate = new TypePayeeModRsV1Aggregate();
/*  37: 36 */     localTypePayeeModRsV1Aggregate.PayeeLstID = paramInputStream.read_string();
/*  38: 37 */     localTypePayeeModRsV1Aggregate.PayeeModRsV1Cm = TypePayeeModRsV1CmHelper.read(paramInputStream);
/*  39: 38 */     localTypePayeeModRsV1Aggregate.PayeeModRsV1CmExists = paramInputStream.read_boolean();
/*  40: 39 */     localTypePayeeModRsV1Aggregate.PayAcct = StringSeqHelper.read(paramInputStream);
/*  41: 40 */     localTypePayeeModRsV1Aggregate.ExtdPayee = TypeExtdPayeeV1AggregateHelper.read(paramInputStream);
/*  42: 41 */     localTypePayeeModRsV1Aggregate.ExtdPayeeExists = paramInputStream.read_boolean();
/*  43: 42 */     localTypePayeeModRsV1Aggregate.NameOnAcct = StringSeqHelper.read(paramInputStream);
/*  44: 43 */     localTypePayeeModRsV1Aggregate.NameOnAcctExists = paramInputStream.read_boolean();
/*  45: 44 */     return localTypePayeeModRsV1Aggregate;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static void write(OutputStream paramOutputStream, TypePayeeModRsV1Aggregate paramTypePayeeModRsV1Aggregate)
/*  49:    */   {
/*  50: 51 */     if (paramTypePayeeModRsV1Aggregate == null) {
/*  51: 53 */       paramTypePayeeModRsV1Aggregate = new TypePayeeModRsV1Aggregate();
/*  52:    */     }
/*  53: 55 */     paramOutputStream.write_string(paramTypePayeeModRsV1Aggregate.PayeeLstID);
/*  54: 56 */     TypePayeeModRsV1CmHelper.write(paramOutputStream, paramTypePayeeModRsV1Aggregate.PayeeModRsV1Cm);
/*  55: 57 */     paramOutputStream.write_boolean(paramTypePayeeModRsV1Aggregate.PayeeModRsV1CmExists);
/*  56: 58 */     StringSeqHelper.write(paramOutputStream, paramTypePayeeModRsV1Aggregate.PayAcct);
/*  57: 59 */     TypeExtdPayeeV1AggregateHelper.write(paramOutputStream, paramTypePayeeModRsV1Aggregate.ExtdPayee);
/*  58: 60 */     paramOutputStream.write_boolean(paramTypePayeeModRsV1Aggregate.ExtdPayeeExists);
/*  59: 61 */     StringSeqHelper.write(paramOutputStream, paramTypePayeeModRsV1Aggregate.NameOnAcct);
/*  60: 62 */     paramOutputStream.write_boolean(paramTypePayeeModRsV1Aggregate.NameOnAcctExists);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static String _idl()
/*  64:    */   {
/*  65: 67 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypePayeeModRsV1Aggregate";
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static TypeCode type()
/*  69:    */   {
/*  70: 74 */     if (_type == null)
/*  71:    */     {
/*  72: 76 */       ORB localORB = ORB.init();
/*  73: 77 */       StructMember[] arrayOfStructMember = 
/*  74: 78 */         {
/*  75: 79 */         new StructMember("PayeeLstID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  76: 80 */         new StructMember("PayeeModRsV1Cm", TypePayeeModRsV1CmHelper.type(), null), 
/*  77: 81 */         new StructMember("PayeeModRsV1CmExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  78: 82 */         new StructMember("PayAcct", StringSeqHelper.type(), null), 
/*  79: 83 */         new StructMember("ExtdPayee", TypeExtdPayeeV1AggregateHelper.type(), null), 
/*  80: 84 */         new StructMember("ExtdPayeeExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  81: 85 */         new StructMember("NameOnAcct", StringSeqHelper.type(), null), 
/*  82: 86 */         new StructMember("NameOnAcctExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  83:    */       
/*  84: 88 */       _type = localORB.create_struct_tc(id(), "TypePayeeModRsV1Aggregate", arrayOfStructMember);
/*  85:    */     }
/*  86: 90 */     return _type;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static void insert(Any paramAny, TypePayeeModRsV1Aggregate paramTypePayeeModRsV1Aggregate)
/*  90:    */   {
/*  91: 97 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  92: 98 */     write(localOutputStream, paramTypePayeeModRsV1Aggregate);
/*  93: 99 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static TypePayeeModRsV1Aggregate extract(Any paramAny)
/*  97:    */   {
/*  98:105 */     if (!paramAny.type().equal(type())) {
/*  99:107 */       throw new BAD_OPERATION();
/* 100:    */     }
/* 101:109 */     return read(paramAny.create_input_stream());
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static String id()
/* 105:    */   {
/* 106:114 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePayeeModRsV1Aggregate:1.0";
/* 107:    */   }
/* 108:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeModRsV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */