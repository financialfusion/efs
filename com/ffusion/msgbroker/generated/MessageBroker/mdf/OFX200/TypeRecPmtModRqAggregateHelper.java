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
/*  13:    */ public abstract class TypeRecPmtModRqAggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypeRecPmtModRqAggregate clone(TypeRecPmtModRqAggregate paramTypeRecPmtModRqAggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypeRecPmtModRqAggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypeRecPmtModRqAggregate localTypeRecPmtModRqAggregate = new TypeRecPmtModRqAggregate();
/*  23: 21 */     localTypeRecPmtModRqAggregate.CurDef = ((EnumCurrencyEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeRecPmtModRqAggregate.CurDef)));
/*  24: 22 */     localTypeRecPmtModRqAggregate.CurDefExists = paramTypeRecPmtModRqAggregate.CurDefExists;
/*  25: 23 */     localTypeRecPmtModRqAggregate.RecSrvrTID = paramTypeRecPmtModRqAggregate.RecSrvrTID;
/*  26: 24 */     localTypeRecPmtModRqAggregate.RecurrInst = TypeRecurrInstAggregateHelper.clone(paramTypeRecPmtModRqAggregate.RecurrInst);
/*  27: 25 */     localTypeRecPmtModRqAggregate.PmtInfo = TypePmtInfoAggregateHelper.clone(paramTypeRecPmtModRqAggregate.PmtInfo);
/*  28: 26 */     localTypeRecPmtModRqAggregate.InitialAmt = paramTypeRecPmtModRqAggregate.InitialAmt;
/*  29: 27 */     localTypeRecPmtModRqAggregate.InitialAmtExists = paramTypeRecPmtModRqAggregate.InitialAmtExists;
/*  30: 28 */     localTypeRecPmtModRqAggregate.FinalAmt = paramTypeRecPmtModRqAggregate.FinalAmt;
/*  31: 29 */     localTypeRecPmtModRqAggregate.FinalAmtExists = paramTypeRecPmtModRqAggregate.FinalAmtExists;
/*  32: 30 */     localTypeRecPmtModRqAggregate.ModPending = paramTypeRecPmtModRqAggregate.ModPending;
/*  33: 31 */     return localTypeRecPmtModRqAggregate;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static TypeRecPmtModRqAggregate read(InputStream paramInputStream)
/*  37:    */   {
/*  38: 37 */     TypeRecPmtModRqAggregate localTypeRecPmtModRqAggregate = new TypeRecPmtModRqAggregate();
/*  39: 38 */     localTypeRecPmtModRqAggregate.CurDef = EnumCurrencyEnumHelper.read(paramInputStream);
/*  40: 39 */     localTypeRecPmtModRqAggregate.CurDefExists = paramInputStream.read_boolean();
/*  41: 40 */     localTypeRecPmtModRqAggregate.RecSrvrTID = paramInputStream.read_string();
/*  42: 41 */     localTypeRecPmtModRqAggregate.RecurrInst = TypeRecurrInstAggregateHelper.read(paramInputStream);
/*  43: 42 */     localTypeRecPmtModRqAggregate.PmtInfo = TypePmtInfoAggregateHelper.read(paramInputStream);
/*  44: 43 */     localTypeRecPmtModRqAggregate.InitialAmt = paramInputStream.read_double();
/*  45: 44 */     localTypeRecPmtModRqAggregate.InitialAmtExists = paramInputStream.read_boolean();
/*  46: 45 */     localTypeRecPmtModRqAggregate.FinalAmt = paramInputStream.read_double();
/*  47: 46 */     localTypeRecPmtModRqAggregate.FinalAmtExists = paramInputStream.read_boolean();
/*  48: 47 */     localTypeRecPmtModRqAggregate.ModPending = paramInputStream.read_boolean();
/*  49: 48 */     return localTypeRecPmtModRqAggregate;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static void write(OutputStream paramOutputStream, TypeRecPmtModRqAggregate paramTypeRecPmtModRqAggregate)
/*  53:    */   {
/*  54: 55 */     if (paramTypeRecPmtModRqAggregate == null) {
/*  55: 57 */       paramTypeRecPmtModRqAggregate = new TypeRecPmtModRqAggregate();
/*  56:    */     }
/*  57: 59 */     EnumCurrencyEnumHelper.write(paramOutputStream, paramTypeRecPmtModRqAggregate.CurDef);
/*  58: 60 */     paramOutputStream.write_boolean(paramTypeRecPmtModRqAggregate.CurDefExists);
/*  59: 61 */     paramOutputStream.write_string(paramTypeRecPmtModRqAggregate.RecSrvrTID);
/*  60: 62 */     TypeRecurrInstAggregateHelper.write(paramOutputStream, paramTypeRecPmtModRqAggregate.RecurrInst);
/*  61: 63 */     TypePmtInfoAggregateHelper.write(paramOutputStream, paramTypeRecPmtModRqAggregate.PmtInfo);
/*  62: 64 */     paramOutputStream.write_double(paramTypeRecPmtModRqAggregate.InitialAmt);
/*  63: 65 */     paramOutputStream.write_boolean(paramTypeRecPmtModRqAggregate.InitialAmtExists);
/*  64: 66 */     paramOutputStream.write_double(paramTypeRecPmtModRqAggregate.FinalAmt);
/*  65: 67 */     paramOutputStream.write_boolean(paramTypeRecPmtModRqAggregate.FinalAmtExists);
/*  66: 68 */     paramOutputStream.write_boolean(paramTypeRecPmtModRqAggregate.ModPending);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static String _idl()
/*  70:    */   {
/*  71: 73 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeRecPmtModRqAggregate";
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
/*  85: 89 */         new StructMember("PmtInfo", TypePmtInfoAggregateHelper.type(), null), 
/*  86: 90 */         new StructMember("InitialAmt", localORB.get_primitive_tc(TCKind.tk_double), null), 
/*  87: 91 */         new StructMember("InitialAmtExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  88: 92 */         new StructMember("FinalAmt", localORB.get_primitive_tc(TCKind.tk_double), null), 
/*  89: 93 */         new StructMember("FinalAmtExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  90: 94 */         new StructMember("ModPending", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  91:    */       
/*  92: 96 */       _type = localORB.create_struct_tc(id(), "TypeRecPmtModRqAggregate", arrayOfStructMember);
/*  93:    */     }
/*  94: 98 */     return _type;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static void insert(Any paramAny, TypeRecPmtModRqAggregate paramTypeRecPmtModRqAggregate)
/*  98:    */   {
/*  99:105 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 100:106 */     write(localOutputStream, paramTypeRecPmtModRqAggregate);
/* 101:107 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static TypeRecPmtModRqAggregate extract(Any paramAny)
/* 105:    */   {
/* 106:113 */     if (!paramAny.type().equal(type())) {
/* 107:115 */       throw new BAD_OPERATION();
/* 108:    */     }
/* 109:117 */     return read(paramAny.create_input_stream());
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static String id()
/* 113:    */   {
/* 114:122 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeRecPmtModRqAggregate:1.0";
/* 115:    */   }
/* 116:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtModRqAggregateHelper
 * JD-Core Version:    0.7.0.1
 */