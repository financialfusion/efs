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
/*  13:    */ public abstract class TypeRecPmtModRsV1AggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypeRecPmtModRsV1Aggregate clone(TypeRecPmtModRsV1Aggregate paramTypeRecPmtModRsV1Aggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypeRecPmtModRsV1Aggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypeRecPmtModRsV1Aggregate localTypeRecPmtModRsV1Aggregate = new TypeRecPmtModRsV1Aggregate();
/*  23: 21 */     localTypeRecPmtModRsV1Aggregate.CurDef = ((EnumCurrencyEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeRecPmtModRsV1Aggregate.CurDef)));
/*  24: 22 */     localTypeRecPmtModRsV1Aggregate.CurDefExists = paramTypeRecPmtModRsV1Aggregate.CurDefExists;
/*  25: 23 */     localTypeRecPmtModRsV1Aggregate.RecSrvrTID = paramTypeRecPmtModRsV1Aggregate.RecSrvrTID;
/*  26: 24 */     localTypeRecPmtModRsV1Aggregate.RecurrInst = TypeRecurrInstAggregateHelper.clone(paramTypeRecPmtModRsV1Aggregate.RecurrInst);
/*  27: 25 */     localTypeRecPmtModRsV1Aggregate.PmtInfo = TypePmtInfoV1AggregateHelper.clone(paramTypeRecPmtModRsV1Aggregate.PmtInfo);
/*  28: 26 */     localTypeRecPmtModRsV1Aggregate.InitialAmt = paramTypeRecPmtModRsV1Aggregate.InitialAmt;
/*  29: 27 */     localTypeRecPmtModRsV1Aggregate.InitialAmtExists = paramTypeRecPmtModRsV1Aggregate.InitialAmtExists;
/*  30: 28 */     localTypeRecPmtModRsV1Aggregate.FinalAmt = paramTypeRecPmtModRsV1Aggregate.FinalAmt;
/*  31: 29 */     localTypeRecPmtModRsV1Aggregate.FinalAmtExists = paramTypeRecPmtModRsV1Aggregate.FinalAmtExists;
/*  32: 30 */     localTypeRecPmtModRsV1Aggregate.ModPending = paramTypeRecPmtModRsV1Aggregate.ModPending;
/*  33: 31 */     return localTypeRecPmtModRsV1Aggregate;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static TypeRecPmtModRsV1Aggregate read(InputStream paramInputStream)
/*  37:    */   {
/*  38: 37 */     TypeRecPmtModRsV1Aggregate localTypeRecPmtModRsV1Aggregate = new TypeRecPmtModRsV1Aggregate();
/*  39: 38 */     localTypeRecPmtModRsV1Aggregate.CurDef = EnumCurrencyEnumHelper.read(paramInputStream);
/*  40: 39 */     localTypeRecPmtModRsV1Aggregate.CurDefExists = paramInputStream.read_boolean();
/*  41: 40 */     localTypeRecPmtModRsV1Aggregate.RecSrvrTID = paramInputStream.read_string();
/*  42: 41 */     localTypeRecPmtModRsV1Aggregate.RecurrInst = TypeRecurrInstAggregateHelper.read(paramInputStream);
/*  43: 42 */     localTypeRecPmtModRsV1Aggregate.PmtInfo = TypePmtInfoV1AggregateHelper.read(paramInputStream);
/*  44: 43 */     localTypeRecPmtModRsV1Aggregate.InitialAmt = paramInputStream.read_double();
/*  45: 44 */     localTypeRecPmtModRsV1Aggregate.InitialAmtExists = paramInputStream.read_boolean();
/*  46: 45 */     localTypeRecPmtModRsV1Aggregate.FinalAmt = paramInputStream.read_double();
/*  47: 46 */     localTypeRecPmtModRsV1Aggregate.FinalAmtExists = paramInputStream.read_boolean();
/*  48: 47 */     localTypeRecPmtModRsV1Aggregate.ModPending = paramInputStream.read_boolean();
/*  49: 48 */     return localTypeRecPmtModRsV1Aggregate;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static void write(OutputStream paramOutputStream, TypeRecPmtModRsV1Aggregate paramTypeRecPmtModRsV1Aggregate)
/*  53:    */   {
/*  54: 55 */     if (paramTypeRecPmtModRsV1Aggregate == null) {
/*  55: 57 */       paramTypeRecPmtModRsV1Aggregate = new TypeRecPmtModRsV1Aggregate();
/*  56:    */     }
/*  57: 59 */     EnumCurrencyEnumHelper.write(paramOutputStream, paramTypeRecPmtModRsV1Aggregate.CurDef);
/*  58: 60 */     paramOutputStream.write_boolean(paramTypeRecPmtModRsV1Aggregate.CurDefExists);
/*  59: 61 */     paramOutputStream.write_string(paramTypeRecPmtModRsV1Aggregate.RecSrvrTID);
/*  60: 62 */     TypeRecurrInstAggregateHelper.write(paramOutputStream, paramTypeRecPmtModRsV1Aggregate.RecurrInst);
/*  61: 63 */     TypePmtInfoV1AggregateHelper.write(paramOutputStream, paramTypeRecPmtModRsV1Aggregate.PmtInfo);
/*  62: 64 */     paramOutputStream.write_double(paramTypeRecPmtModRsV1Aggregate.InitialAmt);
/*  63: 65 */     paramOutputStream.write_boolean(paramTypeRecPmtModRsV1Aggregate.InitialAmtExists);
/*  64: 66 */     paramOutputStream.write_double(paramTypeRecPmtModRsV1Aggregate.FinalAmt);
/*  65: 67 */     paramOutputStream.write_boolean(paramTypeRecPmtModRsV1Aggregate.FinalAmtExists);
/*  66: 68 */     paramOutputStream.write_boolean(paramTypeRecPmtModRsV1Aggregate.ModPending);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static String _idl()
/*  70:    */   {
/*  71: 73 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeRecPmtModRsV1Aggregate";
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static TypeCode type()
/*  75:    */   {
/*  76: 80 */     if (_type == null)
/*  77:    */     {
/*  78: 82 */       ORB localORB = ORB.init();
/*  79: 83 */       StructMember[] arrayOfStructMember = 
/*  80: 84 */         {
/*  81: 85 */         new StructMember("CurDef", EnumCurrencyEnumHelper.type(), null), 
/*  82: 86 */         new StructMember("CurDefExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  83: 87 */         new StructMember("RecSrvrTID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  84: 88 */         new StructMember("RecurrInst", TypeRecurrInstAggregateHelper.type(), null), 
/*  85: 89 */         new StructMember("PmtInfo", TypePmtInfoV1AggregateHelper.type(), null), 
/*  86: 90 */         new StructMember("InitialAmt", localORB.get_primitive_tc(TCKind.tk_double), null), 
/*  87: 91 */         new StructMember("InitialAmtExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  88: 92 */         new StructMember("FinalAmt", localORB.get_primitive_tc(TCKind.tk_double), null), 
/*  89: 93 */         new StructMember("FinalAmtExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  90: 94 */         new StructMember("ModPending", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  91:    */       
/*  92: 96 */       _type = localORB.create_struct_tc(id(), "TypeRecPmtModRsV1Aggregate", arrayOfStructMember);
/*  93:    */     }
/*  94: 98 */     return _type;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static void insert(Any paramAny, TypeRecPmtModRsV1Aggregate paramTypeRecPmtModRsV1Aggregate)
/*  98:    */   {
/*  99:105 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 100:106 */     write(localOutputStream, paramTypeRecPmtModRsV1Aggregate);
/* 101:107 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static TypeRecPmtModRsV1Aggregate extract(Any paramAny)
/* 105:    */   {
/* 106:113 */     if (!paramAny.type().equal(type())) {
/* 107:115 */       throw new BAD_OPERATION();
/* 108:    */     }
/* 109:117 */     return read(paramAny.create_input_stream());
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static String id()
/* 113:    */   {
/* 114:122 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecPmtModRsV1Aggregate:1.0";
/* 115:    */   }
/* 116:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtModRsV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */