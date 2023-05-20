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
/*  13:    */ public abstract class TypeRecPmtModRqV1AggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypeRecPmtModRqV1Aggregate clone(TypeRecPmtModRqV1Aggregate paramTypeRecPmtModRqV1Aggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypeRecPmtModRqV1Aggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypeRecPmtModRqV1Aggregate localTypeRecPmtModRqV1Aggregate = new TypeRecPmtModRqV1Aggregate();
/*  23: 21 */     localTypeRecPmtModRqV1Aggregate.CurDef = ((EnumCurrencyEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeRecPmtModRqV1Aggregate.CurDef)));
/*  24: 22 */     localTypeRecPmtModRqV1Aggregate.CurDefExists = paramTypeRecPmtModRqV1Aggregate.CurDefExists;
/*  25: 23 */     localTypeRecPmtModRqV1Aggregate.RecSrvrTID = paramTypeRecPmtModRqV1Aggregate.RecSrvrTID;
/*  26: 24 */     localTypeRecPmtModRqV1Aggregate.RecurrInst = TypeRecurrInstAggregateHelper.clone(paramTypeRecPmtModRqV1Aggregate.RecurrInst);
/*  27: 25 */     localTypeRecPmtModRqV1Aggregate.PmtInfo = TypePmtInfoV1AggregateHelper.clone(paramTypeRecPmtModRqV1Aggregate.PmtInfo);
/*  28: 26 */     localTypeRecPmtModRqV1Aggregate.InitialAmt = paramTypeRecPmtModRqV1Aggregate.InitialAmt;
/*  29: 27 */     localTypeRecPmtModRqV1Aggregate.InitialAmtExists = paramTypeRecPmtModRqV1Aggregate.InitialAmtExists;
/*  30: 28 */     localTypeRecPmtModRqV1Aggregate.FinalAmt = paramTypeRecPmtModRqV1Aggregate.FinalAmt;
/*  31: 29 */     localTypeRecPmtModRqV1Aggregate.FinalAmtExists = paramTypeRecPmtModRqV1Aggregate.FinalAmtExists;
/*  32: 30 */     localTypeRecPmtModRqV1Aggregate.ModPending = paramTypeRecPmtModRqV1Aggregate.ModPending;
/*  33: 31 */     return localTypeRecPmtModRqV1Aggregate;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static TypeRecPmtModRqV1Aggregate read(InputStream paramInputStream)
/*  37:    */   {
/*  38: 37 */     TypeRecPmtModRqV1Aggregate localTypeRecPmtModRqV1Aggregate = new TypeRecPmtModRqV1Aggregate();
/*  39: 38 */     localTypeRecPmtModRqV1Aggregate.CurDef = EnumCurrencyEnumHelper.read(paramInputStream);
/*  40: 39 */     localTypeRecPmtModRqV1Aggregate.CurDefExists = paramInputStream.read_boolean();
/*  41: 40 */     localTypeRecPmtModRqV1Aggregate.RecSrvrTID = paramInputStream.read_string();
/*  42: 41 */     localTypeRecPmtModRqV1Aggregate.RecurrInst = TypeRecurrInstAggregateHelper.read(paramInputStream);
/*  43: 42 */     localTypeRecPmtModRqV1Aggregate.PmtInfo = TypePmtInfoV1AggregateHelper.read(paramInputStream);
/*  44: 43 */     localTypeRecPmtModRqV1Aggregate.InitialAmt = paramInputStream.read_double();
/*  45: 44 */     localTypeRecPmtModRqV1Aggregate.InitialAmtExists = paramInputStream.read_boolean();
/*  46: 45 */     localTypeRecPmtModRqV1Aggregate.FinalAmt = paramInputStream.read_double();
/*  47: 46 */     localTypeRecPmtModRqV1Aggregate.FinalAmtExists = paramInputStream.read_boolean();
/*  48: 47 */     localTypeRecPmtModRqV1Aggregate.ModPending = paramInputStream.read_boolean();
/*  49: 48 */     return localTypeRecPmtModRqV1Aggregate;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static void write(OutputStream paramOutputStream, TypeRecPmtModRqV1Aggregate paramTypeRecPmtModRqV1Aggregate)
/*  53:    */   {
/*  54: 55 */     if (paramTypeRecPmtModRqV1Aggregate == null) {
/*  55: 57 */       paramTypeRecPmtModRqV1Aggregate = new TypeRecPmtModRqV1Aggregate();
/*  56:    */     }
/*  57: 59 */     EnumCurrencyEnumHelper.write(paramOutputStream, paramTypeRecPmtModRqV1Aggregate.CurDef);
/*  58: 60 */     paramOutputStream.write_boolean(paramTypeRecPmtModRqV1Aggregate.CurDefExists);
/*  59: 61 */     paramOutputStream.write_string(paramTypeRecPmtModRqV1Aggregate.RecSrvrTID);
/*  60: 62 */     TypeRecurrInstAggregateHelper.write(paramOutputStream, paramTypeRecPmtModRqV1Aggregate.RecurrInst);
/*  61: 63 */     TypePmtInfoV1AggregateHelper.write(paramOutputStream, paramTypeRecPmtModRqV1Aggregate.PmtInfo);
/*  62: 64 */     paramOutputStream.write_double(paramTypeRecPmtModRqV1Aggregate.InitialAmt);
/*  63: 65 */     paramOutputStream.write_boolean(paramTypeRecPmtModRqV1Aggregate.InitialAmtExists);
/*  64: 66 */     paramOutputStream.write_double(paramTypeRecPmtModRqV1Aggregate.FinalAmt);
/*  65: 67 */     paramOutputStream.write_boolean(paramTypeRecPmtModRqV1Aggregate.FinalAmtExists);
/*  66: 68 */     paramOutputStream.write_boolean(paramTypeRecPmtModRqV1Aggregate.ModPending);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static String _idl()
/*  70:    */   {
/*  71: 73 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeRecPmtModRqV1Aggregate";
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
/*  92: 96 */       _type = localORB.create_struct_tc(id(), "TypeRecPmtModRqV1Aggregate", arrayOfStructMember);
/*  93:    */     }
/*  94: 98 */     return _type;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static void insert(Any paramAny, TypeRecPmtModRqV1Aggregate paramTypeRecPmtModRqV1Aggregate)
/*  98:    */   {
/*  99:105 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 100:106 */     write(localOutputStream, paramTypeRecPmtModRqV1Aggregate);
/* 101:107 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static TypeRecPmtModRqV1Aggregate extract(Any paramAny)
/* 105:    */   {
/* 106:113 */     if (!paramAny.type().equal(type())) {
/* 107:115 */       throw new BAD_OPERATION();
/* 108:    */     }
/* 109:117 */     return read(paramAny.create_input_stream());
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static String id()
/* 113:    */   {
/* 114:122 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecPmtModRqV1Aggregate:1.0";
/* 115:    */   }
/* 116:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtModRqV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */