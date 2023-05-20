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
/*  13:    */ public abstract class TypePmtRsAggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypePmtRsAggregate clone(TypePmtRsAggregate paramTypePmtRsAggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypePmtRsAggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypePmtRsAggregate localTypePmtRsAggregate = new TypePmtRsAggregate();
/*  23: 21 */     localTypePmtRsAggregate.SrvrTID = paramTypePmtRsAggregate.SrvrTID;
/*  24: 22 */     localTypePmtRsAggregate.PayeeLstID = paramTypePmtRsAggregate.PayeeLstID;
/*  25: 23 */     localTypePmtRsAggregate.CurDef = ((EnumCurrencyEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypePmtRsAggregate.CurDef)));
/*  26: 24 */     localTypePmtRsAggregate.PmtInfo = TypePmtInfoAggregateHelper.clone(paramTypePmtRsAggregate.PmtInfo);
/*  27: 25 */     localTypePmtRsAggregate.ExtdPayee = TypeExtdPayeeAggregateHelper.clone(paramTypePmtRsAggregate.ExtdPayee);
/*  28: 26 */     localTypePmtRsAggregate.ExtdPayeeExists = paramTypePmtRsAggregate.ExtdPayeeExists;
/*  29: 27 */     localTypePmtRsAggregate.CheckNum = paramTypePmtRsAggregate.CheckNum;
/*  30: 28 */     localTypePmtRsAggregate.CheckNumExists = paramTypePmtRsAggregate.CheckNumExists;
/*  31: 29 */     localTypePmtRsAggregate.PmtPrcSts = TypePmtPrcStsAggregateHelper.clone(paramTypePmtRsAggregate.PmtPrcSts);
/*  32: 30 */     localTypePmtRsAggregate.RecSrvrTID = paramTypePmtRsAggregate.RecSrvrTID;
/*  33: 31 */     localTypePmtRsAggregate.RecSrvrTIDExists = paramTypePmtRsAggregate.RecSrvrTIDExists;
/*  34: 32 */     return localTypePmtRsAggregate;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static TypePmtRsAggregate read(InputStream paramInputStream)
/*  38:    */   {
/*  39: 38 */     TypePmtRsAggregate localTypePmtRsAggregate = new TypePmtRsAggregate();
/*  40: 39 */     localTypePmtRsAggregate.SrvrTID = paramInputStream.read_string();
/*  41: 40 */     localTypePmtRsAggregate.PayeeLstID = paramInputStream.read_string();
/*  42: 41 */     localTypePmtRsAggregate.CurDef = EnumCurrencyEnumHelper.read(paramInputStream);
/*  43: 42 */     localTypePmtRsAggregate.PmtInfo = TypePmtInfoAggregateHelper.read(paramInputStream);
/*  44: 43 */     localTypePmtRsAggregate.ExtdPayee = TypeExtdPayeeAggregateHelper.read(paramInputStream);
/*  45: 44 */     localTypePmtRsAggregate.ExtdPayeeExists = paramInputStream.read_boolean();
/*  46: 45 */     localTypePmtRsAggregate.CheckNum = paramInputStream.read_string();
/*  47: 46 */     localTypePmtRsAggregate.CheckNumExists = paramInputStream.read_boolean();
/*  48: 47 */     localTypePmtRsAggregate.PmtPrcSts = TypePmtPrcStsAggregateHelper.read(paramInputStream);
/*  49: 48 */     localTypePmtRsAggregate.RecSrvrTID = paramInputStream.read_string();
/*  50: 49 */     localTypePmtRsAggregate.RecSrvrTIDExists = paramInputStream.read_boolean();
/*  51: 50 */     return localTypePmtRsAggregate;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static void write(OutputStream paramOutputStream, TypePmtRsAggregate paramTypePmtRsAggregate)
/*  55:    */   {
/*  56: 57 */     if (paramTypePmtRsAggregate == null) {
/*  57: 59 */       paramTypePmtRsAggregate = new TypePmtRsAggregate();
/*  58:    */     }
/*  59: 61 */     paramOutputStream.write_string(paramTypePmtRsAggregate.SrvrTID);
/*  60: 62 */     paramOutputStream.write_string(paramTypePmtRsAggregate.PayeeLstID);
/*  61: 63 */     EnumCurrencyEnumHelper.write(paramOutputStream, paramTypePmtRsAggregate.CurDef);
/*  62: 64 */     TypePmtInfoAggregateHelper.write(paramOutputStream, paramTypePmtRsAggregate.PmtInfo);
/*  63: 65 */     TypeExtdPayeeAggregateHelper.write(paramOutputStream, paramTypePmtRsAggregate.ExtdPayee);
/*  64: 66 */     paramOutputStream.write_boolean(paramTypePmtRsAggregate.ExtdPayeeExists);
/*  65: 67 */     paramOutputStream.write_string(paramTypePmtRsAggregate.CheckNum);
/*  66: 68 */     paramOutputStream.write_boolean(paramTypePmtRsAggregate.CheckNumExists);
/*  67: 69 */     TypePmtPrcStsAggregateHelper.write(paramOutputStream, paramTypePmtRsAggregate.PmtPrcSts);
/*  68: 70 */     paramOutputStream.write_string(paramTypePmtRsAggregate.RecSrvrTID);
/*  69: 71 */     paramOutputStream.write_boolean(paramTypePmtRsAggregate.RecSrvrTIDExists);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static String _idl()
/*  73:    */   {
/*  74: 76 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypePmtRsAggregate";
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
/*  87: 91 */         new StructMember("PmtInfo", TypePmtInfoAggregateHelper.type(), null), 
/*  88: 92 */         new StructMember("ExtdPayee", TypeExtdPayeeAggregateHelper.type(), null), 
/*  89: 93 */         new StructMember("ExtdPayeeExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  90: 94 */         new StructMember("CheckNum", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  91: 95 */         new StructMember("CheckNumExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  92: 96 */         new StructMember("PmtPrcSts", TypePmtPrcStsAggregateHelper.type(), null), 
/*  93: 97 */         new StructMember("RecSrvrTID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  94: 98 */         new StructMember("RecSrvrTIDExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  95:    */       
/*  96:100 */       _type = localORB.create_struct_tc(id(), "TypePmtRsAggregate", arrayOfStructMember);
/*  97:    */     }
/*  98:102 */     return _type;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static void insert(Any paramAny, TypePmtRsAggregate paramTypePmtRsAggregate)
/* 102:    */   {
/* 103:109 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 104:110 */     write(localOutputStream, paramTypePmtRsAggregate);
/* 105:111 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static TypePmtRsAggregate extract(Any paramAny)
/* 109:    */   {
/* 110:117 */     if (!paramAny.type().equal(type())) {
/* 111:119 */       throw new BAD_OPERATION();
/* 112:    */     }
/* 113:121 */     return read(paramAny.create_input_stream());
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static String id()
/* 117:    */   {
/* 118:126 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypePmtRsAggregate:1.0";
/* 119:    */   }
/* 120:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtRsAggregateHelper
 * JD-Core Version:    0.7.0.1
 */