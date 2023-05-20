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
/*  13:    */ public abstract class TypePmtRsV1AggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypePmtRsV1Aggregate clone(TypePmtRsV1Aggregate paramTypePmtRsV1Aggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypePmtRsV1Aggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypePmtRsV1Aggregate localTypePmtRsV1Aggregate = new TypePmtRsV1Aggregate();
/*  23: 21 */     localTypePmtRsV1Aggregate.SrvrTID = paramTypePmtRsV1Aggregate.SrvrTID;
/*  24: 22 */     localTypePmtRsV1Aggregate.PayeeLstID = paramTypePmtRsV1Aggregate.PayeeLstID;
/*  25: 23 */     localTypePmtRsV1Aggregate.CurDef = ((EnumCurrencyEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypePmtRsV1Aggregate.CurDef)));
/*  26: 24 */     localTypePmtRsV1Aggregate.PmtInfo = TypePmtInfoV1AggregateHelper.clone(paramTypePmtRsV1Aggregate.PmtInfo);
/*  27: 25 */     localTypePmtRsV1Aggregate.ExtdPayee = TypeExtdPayeeV1AggregateHelper.clone(paramTypePmtRsV1Aggregate.ExtdPayee);
/*  28: 26 */     localTypePmtRsV1Aggregate.ExtdPayeeExists = paramTypePmtRsV1Aggregate.ExtdPayeeExists;
/*  29: 27 */     localTypePmtRsV1Aggregate.CheckNum = paramTypePmtRsV1Aggregate.CheckNum;
/*  30: 28 */     localTypePmtRsV1Aggregate.CheckNumExists = paramTypePmtRsV1Aggregate.CheckNumExists;
/*  31: 29 */     localTypePmtRsV1Aggregate.PmtPrcSts = TypePmtPrcStsAggregateHelper.clone(paramTypePmtRsV1Aggregate.PmtPrcSts);
/*  32: 30 */     localTypePmtRsV1Aggregate.RecSrvrTID = paramTypePmtRsV1Aggregate.RecSrvrTID;
/*  33: 31 */     localTypePmtRsV1Aggregate.RecSrvrTIDExists = paramTypePmtRsV1Aggregate.RecSrvrTIDExists;
/*  34: 32 */     return localTypePmtRsV1Aggregate;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static TypePmtRsV1Aggregate read(InputStream paramInputStream)
/*  38:    */   {
/*  39: 38 */     TypePmtRsV1Aggregate localTypePmtRsV1Aggregate = new TypePmtRsV1Aggregate();
/*  40: 39 */     localTypePmtRsV1Aggregate.SrvrTID = paramInputStream.read_string();
/*  41: 40 */     localTypePmtRsV1Aggregate.PayeeLstID = paramInputStream.read_string();
/*  42: 41 */     localTypePmtRsV1Aggregate.CurDef = EnumCurrencyEnumHelper.read(paramInputStream);
/*  43: 42 */     localTypePmtRsV1Aggregate.PmtInfo = TypePmtInfoV1AggregateHelper.read(paramInputStream);
/*  44: 43 */     localTypePmtRsV1Aggregate.ExtdPayee = TypeExtdPayeeV1AggregateHelper.read(paramInputStream);
/*  45: 44 */     localTypePmtRsV1Aggregate.ExtdPayeeExists = paramInputStream.read_boolean();
/*  46: 45 */     localTypePmtRsV1Aggregate.CheckNum = paramInputStream.read_string();
/*  47: 46 */     localTypePmtRsV1Aggregate.CheckNumExists = paramInputStream.read_boolean();
/*  48: 47 */     localTypePmtRsV1Aggregate.PmtPrcSts = TypePmtPrcStsAggregateHelper.read(paramInputStream);
/*  49: 48 */     localTypePmtRsV1Aggregate.RecSrvrTID = paramInputStream.read_string();
/*  50: 49 */     localTypePmtRsV1Aggregate.RecSrvrTIDExists = paramInputStream.read_boolean();
/*  51: 50 */     return localTypePmtRsV1Aggregate;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static void write(OutputStream paramOutputStream, TypePmtRsV1Aggregate paramTypePmtRsV1Aggregate)
/*  55:    */   {
/*  56: 57 */     if (paramTypePmtRsV1Aggregate == null) {
/*  57: 59 */       paramTypePmtRsV1Aggregate = new TypePmtRsV1Aggregate();
/*  58:    */     }
/*  59: 61 */     paramOutputStream.write_string(paramTypePmtRsV1Aggregate.SrvrTID);
/*  60: 62 */     paramOutputStream.write_string(paramTypePmtRsV1Aggregate.PayeeLstID);
/*  61: 63 */     EnumCurrencyEnumHelper.write(paramOutputStream, paramTypePmtRsV1Aggregate.CurDef);
/*  62: 64 */     TypePmtInfoV1AggregateHelper.write(paramOutputStream, paramTypePmtRsV1Aggregate.PmtInfo);
/*  63: 65 */     TypeExtdPayeeV1AggregateHelper.write(paramOutputStream, paramTypePmtRsV1Aggregate.ExtdPayee);
/*  64: 66 */     paramOutputStream.write_boolean(paramTypePmtRsV1Aggregate.ExtdPayeeExists);
/*  65: 67 */     paramOutputStream.write_string(paramTypePmtRsV1Aggregate.CheckNum);
/*  66: 68 */     paramOutputStream.write_boolean(paramTypePmtRsV1Aggregate.CheckNumExists);
/*  67: 69 */     TypePmtPrcStsAggregateHelper.write(paramOutputStream, paramTypePmtRsV1Aggregate.PmtPrcSts);
/*  68: 70 */     paramOutputStream.write_string(paramTypePmtRsV1Aggregate.RecSrvrTID);
/*  69: 71 */     paramOutputStream.write_boolean(paramTypePmtRsV1Aggregate.RecSrvrTIDExists);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static String _idl()
/*  73:    */   {
/*  74: 76 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypePmtRsV1Aggregate";
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static TypeCode type()
/*  78:    */   {
/*  79: 83 */     if (_type == null)
/*  80:    */     {
/*  81: 85 */       ORB localORB = ORB.init();
/*  82: 86 */       StructMember[] arrayOfStructMember = 
/*  83: 87 */         {
/*  84: 88 */         new StructMember("SrvrTID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  85: 89 */         new StructMember("PayeeLstID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  86: 90 */         new StructMember("CurDef", EnumCurrencyEnumHelper.type(), null), 
/*  87: 91 */         new StructMember("PmtInfo", TypePmtInfoV1AggregateHelper.type(), null), 
/*  88: 92 */         new StructMember("ExtdPayee", TypeExtdPayeeV1AggregateHelper.type(), null), 
/*  89: 93 */         new StructMember("ExtdPayeeExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  90: 94 */         new StructMember("CheckNum", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  91: 95 */         new StructMember("CheckNumExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  92: 96 */         new StructMember("PmtPrcSts", TypePmtPrcStsAggregateHelper.type(), null), 
/*  93: 97 */         new StructMember("RecSrvrTID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  94: 98 */         new StructMember("RecSrvrTIDExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  95:    */       
/*  96:100 */       _type = localORB.create_struct_tc(id(), "TypePmtRsV1Aggregate", arrayOfStructMember);
/*  97:    */     }
/*  98:102 */     return _type;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static void insert(Any paramAny, TypePmtRsV1Aggregate paramTypePmtRsV1Aggregate)
/* 102:    */   {
/* 103:109 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 104:110 */     write(localOutputStream, paramTypePmtRsV1Aggregate);
/* 105:111 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static TypePmtRsV1Aggregate extract(Any paramAny)
/* 109:    */   {
/* 110:117 */     if (!paramAny.type().equal(type())) {
/* 111:119 */       throw new BAD_OPERATION();
/* 112:    */     }
/* 113:121 */     return read(paramAny.create_input_stream());
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static String id()
/* 117:    */   {
/* 118:126 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtRsV1Aggregate:1.0";
/* 119:    */   }
/* 120:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtRsV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */