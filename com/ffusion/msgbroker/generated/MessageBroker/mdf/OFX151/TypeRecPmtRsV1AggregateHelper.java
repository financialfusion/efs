/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*   2:    */ 
/*   3:    */ import com.sybase.CORBA.ObjectVal;
/*   4:    */ import org.omg.CORBA.Any;
/*   5:    */ import org.omg.CORBA.BAD_OPERATION;
/*   6:    */ import org.omg.CORBA.ORB;
/*   7:    */ import org.omg.CORBA.StructMember;
/*   8:    */ import org.omg.CORBA.TCKind;
/*   9:    */ import org.omg.CORBA.TypeCode;
/*  10:    */ import org.omg.CORBA.portable.InputStream;
/*  11:    */ import org.omg.CORBA.portable.OutputStream;
/*  12:    */ 
/*  13:    */ public abstract class TypeRecPmtRsV1AggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypeRecPmtRsV1Aggregate clone(TypeRecPmtRsV1Aggregate paramTypeRecPmtRsV1Aggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypeRecPmtRsV1Aggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypeRecPmtRsV1Aggregate localTypeRecPmtRsV1Aggregate = new TypeRecPmtRsV1Aggregate();
/*  23: 21 */     localTypeRecPmtRsV1Aggregate.RecSrvrTID = paramTypeRecPmtRsV1Aggregate.RecSrvrTID;
/*  24: 22 */     localTypeRecPmtRsV1Aggregate.PayeeLstID = paramTypeRecPmtRsV1Aggregate.PayeeLstID;
/*  25: 23 */     localTypeRecPmtRsV1Aggregate.CurDef = ((EnumCurrencyEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeRecPmtRsV1Aggregate.CurDef)));
/*  26: 24 */     localTypeRecPmtRsV1Aggregate.RecurrInst = TypeRecurrInstAggregateHelper.clone(paramTypeRecPmtRsV1Aggregate.RecurrInst);
/*  27: 25 */     localTypeRecPmtRsV1Aggregate.PmtInfo = TypePmtInfoV1AggregateHelper.clone(paramTypeRecPmtRsV1Aggregate.PmtInfo);
/*  28: 26 */     localTypeRecPmtRsV1Aggregate.InitialAmt = paramTypeRecPmtRsV1Aggregate.InitialAmt;
/*  29: 27 */     localTypeRecPmtRsV1Aggregate.InitialAmtExists = paramTypeRecPmtRsV1Aggregate.InitialAmtExists;
/*  30: 28 */     localTypeRecPmtRsV1Aggregate.FinalAmt = paramTypeRecPmtRsV1Aggregate.FinalAmt;
/*  31: 29 */     localTypeRecPmtRsV1Aggregate.FinalAmtExists = paramTypeRecPmtRsV1Aggregate.FinalAmtExists;
/*  32: 30 */     localTypeRecPmtRsV1Aggregate.ExtdPayee = TypeExtdPayeeV1AggregateHelper.clone(paramTypeRecPmtRsV1Aggregate.ExtdPayee);
/*  33: 31 */     localTypeRecPmtRsV1Aggregate.ExtdPayeeExists = paramTypeRecPmtRsV1Aggregate.ExtdPayeeExists;
/*  34: 32 */     return localTypeRecPmtRsV1Aggregate;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static TypeRecPmtRsV1Aggregate read(InputStream paramInputStream)
/*  38:    */   {
/*  39: 38 */     TypeRecPmtRsV1Aggregate localTypeRecPmtRsV1Aggregate = new TypeRecPmtRsV1Aggregate();
/*  40: 39 */     localTypeRecPmtRsV1Aggregate.RecSrvrTID = paramInputStream.read_string();
/*  41: 40 */     localTypeRecPmtRsV1Aggregate.PayeeLstID = paramInputStream.read_string();
/*  42: 41 */     localTypeRecPmtRsV1Aggregate.CurDef = EnumCurrencyEnumHelper.read(paramInputStream);
/*  43: 42 */     localTypeRecPmtRsV1Aggregate.RecurrInst = TypeRecurrInstAggregateHelper.read(paramInputStream);
/*  44: 43 */     localTypeRecPmtRsV1Aggregate.PmtInfo = TypePmtInfoV1AggregateHelper.read(paramInputStream);
/*  45: 44 */     localTypeRecPmtRsV1Aggregate.InitialAmt = paramInputStream.read_double();
/*  46: 45 */     localTypeRecPmtRsV1Aggregate.InitialAmtExists = paramInputStream.read_boolean();
/*  47: 46 */     localTypeRecPmtRsV1Aggregate.FinalAmt = paramInputStream.read_double();
/*  48: 47 */     localTypeRecPmtRsV1Aggregate.FinalAmtExists = paramInputStream.read_boolean();
/*  49: 48 */     localTypeRecPmtRsV1Aggregate.ExtdPayee = TypeExtdPayeeV1AggregateHelper.read(paramInputStream);
/*  50: 49 */     localTypeRecPmtRsV1Aggregate.ExtdPayeeExists = paramInputStream.read_boolean();
/*  51: 50 */     return localTypeRecPmtRsV1Aggregate;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static void write(OutputStream paramOutputStream, TypeRecPmtRsV1Aggregate paramTypeRecPmtRsV1Aggregate)
/*  55:    */   {
/*  56: 57 */     if (paramTypeRecPmtRsV1Aggregate == null) {
/*  57: 59 */       paramTypeRecPmtRsV1Aggregate = new TypeRecPmtRsV1Aggregate();
/*  58:    */     }
/*  59: 61 */     paramOutputStream.write_string(paramTypeRecPmtRsV1Aggregate.RecSrvrTID);
/*  60: 62 */     paramOutputStream.write_string(paramTypeRecPmtRsV1Aggregate.PayeeLstID);
/*  61: 63 */     EnumCurrencyEnumHelper.write(paramOutputStream, paramTypeRecPmtRsV1Aggregate.CurDef);
/*  62: 64 */     TypeRecurrInstAggregateHelper.write(paramOutputStream, paramTypeRecPmtRsV1Aggregate.RecurrInst);
/*  63: 65 */     TypePmtInfoV1AggregateHelper.write(paramOutputStream, paramTypeRecPmtRsV1Aggregate.PmtInfo);
/*  64: 66 */     paramOutputStream.write_double(paramTypeRecPmtRsV1Aggregate.InitialAmt);
/*  65: 67 */     paramOutputStream.write_boolean(paramTypeRecPmtRsV1Aggregate.InitialAmtExists);
/*  66: 68 */     paramOutputStream.write_double(paramTypeRecPmtRsV1Aggregate.FinalAmt);
/*  67: 69 */     paramOutputStream.write_boolean(paramTypeRecPmtRsV1Aggregate.FinalAmtExists);
/*  68: 70 */     TypeExtdPayeeV1AggregateHelper.write(paramOutputStream, paramTypeRecPmtRsV1Aggregate.ExtdPayee);
/*  69: 71 */     paramOutputStream.write_boolean(paramTypeRecPmtRsV1Aggregate.ExtdPayeeExists);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static String _idl()
/*  73:    */   {
/*  74: 76 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeRecPmtRsV1Aggregate";
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static TypeCode type()
/*  78:    */   {
/*  79: 83 */     if (_type == null)
/*  80:    */     {
/*  81: 85 */       ORB localORB = ORB.init();
/*  82: 86 */       StructMember[] arrayOfStructMember = 
/*  83: 87 */         {
/*  84: 88 */         new StructMember("RecSrvrTID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  85: 89 */         new StructMember("PayeeLstID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  86: 90 */         new StructMember("CurDef", EnumCurrencyEnumHelper.type(), null), 
/*  87: 91 */         new StructMember("RecurrInst", TypeRecurrInstAggregateHelper.type(), null), 
/*  88: 92 */         new StructMember("PmtInfo", TypePmtInfoV1AggregateHelper.type(), null), 
/*  89: 93 */         new StructMember("InitialAmt", localORB.get_primitive_tc(TCKind.tk_double), null), 
/*  90: 94 */         new StructMember("InitialAmtExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  91: 95 */         new StructMember("FinalAmt", localORB.get_primitive_tc(TCKind.tk_double), null), 
/*  92: 96 */         new StructMember("FinalAmtExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  93: 97 */         new StructMember("ExtdPayee", TypeExtdPayeeV1AggregateHelper.type(), null), 
/*  94: 98 */         new StructMember("ExtdPayeeExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  95:    */       
/*  96:100 */       _type = localORB.create_struct_tc(id(), "TypeRecPmtRsV1Aggregate", arrayOfStructMember);
/*  97:    */     }
/*  98:102 */     return _type;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static void insert(Any paramAny, TypeRecPmtRsV1Aggregate paramTypeRecPmtRsV1Aggregate)
/* 102:    */   {
/* 103:109 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 104:110 */     write(localOutputStream, paramTypeRecPmtRsV1Aggregate);
/* 105:111 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static TypeRecPmtRsV1Aggregate extract(Any paramAny)
/* 109:    */   {
/* 110:117 */     if (!paramAny.type().equal(type())) {
/* 111:119 */       throw new BAD_OPERATION();
/* 112:    */     }
/* 113:121 */     return read(paramAny.create_input_stream());
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static String id()
/* 117:    */   {
/* 118:126 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecPmtRsV1Aggregate:1.0";
/* 119:    */   }
/* 120:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtRsV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */