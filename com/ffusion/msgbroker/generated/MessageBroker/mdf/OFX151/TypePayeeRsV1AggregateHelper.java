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
/*  13:    */ public abstract class TypePayeeRsV1AggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypePayeeRsV1Aggregate clone(TypePayeeRsV1Aggregate paramTypePayeeRsV1Aggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypePayeeRsV1Aggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypePayeeRsV1Aggregate localTypePayeeRsV1Aggregate = new TypePayeeRsV1Aggregate();
/*  23: 21 */     localTypePayeeRsV1Aggregate.PayeeLstID = paramTypePayeeRsV1Aggregate.PayeeLstID;
/*  24: 22 */     localTypePayeeRsV1Aggregate.PayeeRsV1Cm = TypePayeeRsV1CmHelper.clone(paramTypePayeeRsV1Aggregate.PayeeRsV1Cm);
/*  25: 23 */     localTypePayeeRsV1Aggregate.PayeeRsV1CmExists = paramTypePayeeRsV1Aggregate.PayeeRsV1CmExists;
/*  26: 24 */     localTypePayeeRsV1Aggregate.ExtdPayee = TypeExtdPayeeV1AggregateHelper.clone(paramTypePayeeRsV1Aggregate.ExtdPayee);
/*  27: 25 */     localTypePayeeRsV1Aggregate.ExtdPayeeExists = paramTypePayeeRsV1Aggregate.ExtdPayeeExists;
/*  28: 26 */     localTypePayeeRsV1Aggregate.PayAcct = StringSeqHelper.clone(paramTypePayeeRsV1Aggregate.PayAcct);
/*  29: 27 */     localTypePayeeRsV1Aggregate.NameOnAcct = StringSeqHelper.clone(paramTypePayeeRsV1Aggregate.NameOnAcct);
/*  30: 28 */     localTypePayeeRsV1Aggregate.NameOnAcctExists = paramTypePayeeRsV1Aggregate.NameOnAcctExists;
/*  31: 29 */     return localTypePayeeRsV1Aggregate;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static TypePayeeRsV1Aggregate read(InputStream paramInputStream)
/*  35:    */   {
/*  36: 35 */     TypePayeeRsV1Aggregate localTypePayeeRsV1Aggregate = new TypePayeeRsV1Aggregate();
/*  37: 36 */     localTypePayeeRsV1Aggregate.PayeeLstID = paramInputStream.read_string();
/*  38: 37 */     localTypePayeeRsV1Aggregate.PayeeRsV1Cm = TypePayeeRsV1CmHelper.read(paramInputStream);
/*  39: 38 */     localTypePayeeRsV1Aggregate.PayeeRsV1CmExists = paramInputStream.read_boolean();
/*  40: 39 */     localTypePayeeRsV1Aggregate.ExtdPayee = TypeExtdPayeeV1AggregateHelper.read(paramInputStream);
/*  41: 40 */     localTypePayeeRsV1Aggregate.ExtdPayeeExists = paramInputStream.read_boolean();
/*  42: 41 */     localTypePayeeRsV1Aggregate.PayAcct = StringSeqHelper.read(paramInputStream);
/*  43: 42 */     localTypePayeeRsV1Aggregate.NameOnAcct = StringSeqHelper.read(paramInputStream);
/*  44: 43 */     localTypePayeeRsV1Aggregate.NameOnAcctExists = paramInputStream.read_boolean();
/*  45: 44 */     return localTypePayeeRsV1Aggregate;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static void write(OutputStream paramOutputStream, TypePayeeRsV1Aggregate paramTypePayeeRsV1Aggregate)
/*  49:    */   {
/*  50: 51 */     if (paramTypePayeeRsV1Aggregate == null) {
/*  51: 53 */       paramTypePayeeRsV1Aggregate = new TypePayeeRsV1Aggregate();
/*  52:    */     }
/*  53: 55 */     paramOutputStream.write_string(paramTypePayeeRsV1Aggregate.PayeeLstID);
/*  54: 56 */     TypePayeeRsV1CmHelper.write(paramOutputStream, paramTypePayeeRsV1Aggregate.PayeeRsV1Cm);
/*  55: 57 */     paramOutputStream.write_boolean(paramTypePayeeRsV1Aggregate.PayeeRsV1CmExists);
/*  56: 58 */     TypeExtdPayeeV1AggregateHelper.write(paramOutputStream, paramTypePayeeRsV1Aggregate.ExtdPayee);
/*  57: 59 */     paramOutputStream.write_boolean(paramTypePayeeRsV1Aggregate.ExtdPayeeExists);
/*  58: 60 */     StringSeqHelper.write(paramOutputStream, paramTypePayeeRsV1Aggregate.PayAcct);
/*  59: 61 */     StringSeqHelper.write(paramOutputStream, paramTypePayeeRsV1Aggregate.NameOnAcct);
/*  60: 62 */     paramOutputStream.write_boolean(paramTypePayeeRsV1Aggregate.NameOnAcctExists);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static String _idl()
/*  64:    */   {
/*  65: 67 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypePayeeRsV1Aggregate";
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
/*  76: 80 */         new StructMember("PayeeRsV1Cm", TypePayeeRsV1CmHelper.type(), null), 
/*  77: 81 */         new StructMember("PayeeRsV1CmExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  78: 82 */         new StructMember("ExtdPayee", TypeExtdPayeeV1AggregateHelper.type(), null), 
/*  79: 83 */         new StructMember("ExtdPayeeExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  80: 84 */         new StructMember("PayAcct", StringSeqHelper.type(), null), 
/*  81: 85 */         new StructMember("NameOnAcct", StringSeqHelper.type(), null), 
/*  82: 86 */         new StructMember("NameOnAcctExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  83:    */       
/*  84: 88 */       _type = localORB.create_struct_tc(id(), "TypePayeeRsV1Aggregate", arrayOfStructMember);
/*  85:    */     }
/*  86: 90 */     return _type;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static void insert(Any paramAny, TypePayeeRsV1Aggregate paramTypePayeeRsV1Aggregate)
/*  90:    */   {
/*  91: 97 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  92: 98 */     write(localOutputStream, paramTypePayeeRsV1Aggregate);
/*  93: 99 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static TypePayeeRsV1Aggregate extract(Any paramAny)
/*  97:    */   {
/*  98:105 */     if (!paramAny.type().equal(type())) {
/*  99:107 */       throw new BAD_OPERATION();
/* 100:    */     }
/* 101:109 */     return read(paramAny.create_input_stream());
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static String id()
/* 105:    */   {
/* 106:114 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePayeeRsV1Aggregate:1.0";
/* 107:    */   }
/* 108:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeRsV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */