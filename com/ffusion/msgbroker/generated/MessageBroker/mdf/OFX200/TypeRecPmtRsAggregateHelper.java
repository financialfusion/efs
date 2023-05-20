/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
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
/*  13:    */ public abstract class TypeRecPmtRsAggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypeRecPmtRsAggregate clone(TypeRecPmtRsAggregate paramTypeRecPmtRsAggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypeRecPmtRsAggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypeRecPmtRsAggregate localTypeRecPmtRsAggregate = new TypeRecPmtRsAggregate();
/*  23: 21 */     localTypeRecPmtRsAggregate.RecSrvrTID = paramTypeRecPmtRsAggregate.RecSrvrTID;
/*  24: 22 */     localTypeRecPmtRsAggregate.PayeeLstID = paramTypeRecPmtRsAggregate.PayeeLstID;
/*  25: 23 */     localTypeRecPmtRsAggregate.CurDef = ((EnumCurrencyEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeRecPmtRsAggregate.CurDef)));
/*  26: 24 */     localTypeRecPmtRsAggregate.RecurrInst = TypeRecurrInstAggregateHelper.clone(paramTypeRecPmtRsAggregate.RecurrInst);
/*  27: 25 */     localTypeRecPmtRsAggregate.PmtInfo = TypePmtInfoAggregateHelper.clone(paramTypeRecPmtRsAggregate.PmtInfo);
/*  28: 26 */     localTypeRecPmtRsAggregate.InitialAmt = paramTypeRecPmtRsAggregate.InitialAmt;
/*  29: 27 */     localTypeRecPmtRsAggregate.InitialAmtExists = paramTypeRecPmtRsAggregate.InitialAmtExists;
/*  30: 28 */     localTypeRecPmtRsAggregate.FinalAmt = paramTypeRecPmtRsAggregate.FinalAmt;
/*  31: 29 */     localTypeRecPmtRsAggregate.FinalAmtExists = paramTypeRecPmtRsAggregate.FinalAmtExists;
/*  32: 30 */     localTypeRecPmtRsAggregate.ExtdPayee = TypeExtdPayeeAggregateHelper.clone(paramTypeRecPmtRsAggregate.ExtdPayee);
/*  33: 31 */     localTypeRecPmtRsAggregate.ExtdPayeeExists = paramTypeRecPmtRsAggregate.ExtdPayeeExists;
/*  34: 32 */     return localTypeRecPmtRsAggregate;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static TypeRecPmtRsAggregate read(InputStream paramInputStream)
/*  38:    */   {
/*  39: 38 */     TypeRecPmtRsAggregate localTypeRecPmtRsAggregate = new TypeRecPmtRsAggregate();
/*  40: 39 */     localTypeRecPmtRsAggregate.RecSrvrTID = paramInputStream.read_string();
/*  41: 40 */     localTypeRecPmtRsAggregate.PayeeLstID = paramInputStream.read_string();
/*  42: 41 */     localTypeRecPmtRsAggregate.CurDef = EnumCurrencyEnumHelper.read(paramInputStream);
/*  43: 42 */     localTypeRecPmtRsAggregate.RecurrInst = TypeRecurrInstAggregateHelper.read(paramInputStream);
/*  44: 43 */     localTypeRecPmtRsAggregate.PmtInfo = TypePmtInfoAggregateHelper.read(paramInputStream);
/*  45: 44 */     localTypeRecPmtRsAggregate.InitialAmt = paramInputStream.read_double();
/*  46: 45 */     localTypeRecPmtRsAggregate.InitialAmtExists = paramInputStream.read_boolean();
/*  47: 46 */     localTypeRecPmtRsAggregate.FinalAmt = paramInputStream.read_double();
/*  48: 47 */     localTypeRecPmtRsAggregate.FinalAmtExists = paramInputStream.read_boolean();
/*  49: 48 */     localTypeRecPmtRsAggregate.ExtdPayee = TypeExtdPayeeAggregateHelper.read(paramInputStream);
/*  50: 49 */     localTypeRecPmtRsAggregate.ExtdPayeeExists = paramInputStream.read_boolean();
/*  51: 50 */     return localTypeRecPmtRsAggregate;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static void write(OutputStream paramOutputStream, TypeRecPmtRsAggregate paramTypeRecPmtRsAggregate)
/*  55:    */   {
/*  56: 57 */     if (paramTypeRecPmtRsAggregate == null) {
/*  57: 59 */       paramTypeRecPmtRsAggregate = new TypeRecPmtRsAggregate();
/*  58:    */     }
/*  59: 61 */     paramOutputStream.write_string(paramTypeRecPmtRsAggregate.RecSrvrTID);
/*  60: 62 */     paramOutputStream.write_string(paramTypeRecPmtRsAggregate.PayeeLstID);
/*  61: 63 */     EnumCurrencyEnumHelper.write(paramOutputStream, paramTypeRecPmtRsAggregate.CurDef);
/*  62: 64 */     TypeRecurrInstAggregateHelper.write(paramOutputStream, paramTypeRecPmtRsAggregate.RecurrInst);
/*  63: 65 */     TypePmtInfoAggregateHelper.write(paramOutputStream, paramTypeRecPmtRsAggregate.PmtInfo);
/*  64: 66 */     paramOutputStream.write_double(paramTypeRecPmtRsAggregate.InitialAmt);
/*  65: 67 */     paramOutputStream.write_boolean(paramTypeRecPmtRsAggregate.InitialAmtExists);
/*  66: 68 */     paramOutputStream.write_double(paramTypeRecPmtRsAggregate.FinalAmt);
/*  67: 69 */     paramOutputStream.write_boolean(paramTypeRecPmtRsAggregate.FinalAmtExists);
/*  68: 70 */     TypeExtdPayeeAggregateHelper.write(paramOutputStream, paramTypeRecPmtRsAggregate.ExtdPayee);
/*  69: 71 */     paramOutputStream.write_boolean(paramTypeRecPmtRsAggregate.ExtdPayeeExists);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static String _idl()
/*  73:    */   {
/*  74: 76 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeRecPmtRsAggregate";
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
/*  88: 92 */         new StructMember("PmtInfo", TypePmtInfoAggregateHelper.type(), null), 
/*  89: 93 */         new StructMember("InitialAmt", localORB.get_primitive_tc(TCKind.tk_double), null), 
/*  90: 94 */         new StructMember("InitialAmtExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  91: 95 */         new StructMember("FinalAmt", localORB.get_primitive_tc(TCKind.tk_double), null), 
/*  92: 96 */         new StructMember("FinalAmtExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  93: 97 */         new StructMember("ExtdPayee", TypeExtdPayeeAggregateHelper.type(), null), 
/*  94: 98 */         new StructMember("ExtdPayeeExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  95:    */       
/*  96:100 */       _type = localORB.create_struct_tc(id(), "TypeRecPmtRsAggregate", arrayOfStructMember);
/*  97:    */     }
/*  98:102 */     return _type;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static void insert(Any paramAny, TypeRecPmtRsAggregate paramTypeRecPmtRsAggregate)
/* 102:    */   {
/* 103:109 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 104:110 */     write(localOutputStream, paramTypeRecPmtRsAggregate);
/* 105:111 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static TypeRecPmtRsAggregate extract(Any paramAny)
/* 109:    */   {
/* 110:117 */     if (!paramAny.type().equal(type())) {
/* 111:119 */       throw new BAD_OPERATION();
/* 112:    */     }
/* 113:121 */     return read(paramAny.create_input_stream());
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static String id()
/* 117:    */   {
/* 118:126 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeRecPmtRsAggregate:1.0";
/* 119:    */   }
/* 120:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtRsAggregateHelper
 * JD-Core Version:    0.7.0.1
 */