/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*   2:    */ 
/*   3:    */ import org.omg.CORBA.Any;
/*   4:    */ import org.omg.CORBA.BAD_OPERATION;
/*   5:    */ import org.omg.CORBA.ORB;
/*   6:    */ import org.omg.CORBA.StructMember;
/*   7:    */ import org.omg.CORBA.TCKind;
/*   8:    */ import org.omg.CORBA.TypeCode;
/*   9:    */ import org.omg.CORBA.portable.InputStream;
/*  10:    */ import org.omg.CORBA.portable.OutputStream;
/*  11:    */ 
/*  12:    */ public abstract class TypePmtInfoAggregateHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypePmtInfoAggregate clone(TypePmtInfoAggregate paramTypePmtInfoAggregate)
/*  17:    */   {
/*  18: 16 */     if (paramTypePmtInfoAggregate == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypePmtInfoAggregate localTypePmtInfoAggregate = new TypePmtInfoAggregate();
/*  22: 21 */     localTypePmtInfoAggregate.BankAcctFrom = TypeBankAcctFromAggregateHelper.clone(paramTypePmtInfoAggregate.BankAcctFrom);
/*  23: 22 */     localTypePmtInfoAggregate.TrnAmt = paramTypePmtInfoAggregate.TrnAmt;
/*  24: 23 */     localTypePmtInfoAggregate.PayeeUn = TypePayeeUnHelper.clone(paramTypePmtInfoAggregate.PayeeUn);
/*  25: 24 */     localTypePmtInfoAggregate.PayeeLstID = paramTypePmtInfoAggregate.PayeeLstID;
/*  26: 25 */     localTypePmtInfoAggregate.PayeeLstIDExists = paramTypePmtInfoAggregate.PayeeLstIDExists;
/*  27: 26 */     localTypePmtInfoAggregate.BankAcctTo = TypeBankAcctToAggregateHelper.clone(paramTypePmtInfoAggregate.BankAcctTo);
/*  28: 27 */     localTypePmtInfoAggregate.BankAcctToExists = paramTypePmtInfoAggregate.BankAcctToExists;
/*  29: 28 */     localTypePmtInfoAggregate.ExtdPmt = TypeExtdPmtAggregateSeqHelper.clone(paramTypePmtInfoAggregate.ExtdPmt);
/*  30: 29 */     localTypePmtInfoAggregate.PayAcct = paramTypePmtInfoAggregate.PayAcct;
/*  31: 30 */     localTypePmtInfoAggregate.DtDue = paramTypePmtInfoAggregate.DtDue;
/*  32: 31 */     localTypePmtInfoAggregate.Memo = paramTypePmtInfoAggregate.Memo;
/*  33: 32 */     localTypePmtInfoAggregate.MemoExists = paramTypePmtInfoAggregate.MemoExists;
/*  34: 33 */     localTypePmtInfoAggregate.BillRefInfo = paramTypePmtInfoAggregate.BillRefInfo;
/*  35: 34 */     localTypePmtInfoAggregate.BillRefInfoExists = paramTypePmtInfoAggregate.BillRefInfoExists;
/*  36: 35 */     localTypePmtInfoAggregate.NameOnAcct = paramTypePmtInfoAggregate.NameOnAcct;
/*  37: 36 */     localTypePmtInfoAggregate.NameOnAcctExists = paramTypePmtInfoAggregate.NameOnAcctExists;
/*  38: 37 */     return localTypePmtInfoAggregate;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static TypePmtInfoAggregate read(InputStream paramInputStream)
/*  42:    */   {
/*  43: 43 */     TypePmtInfoAggregate localTypePmtInfoAggregate = new TypePmtInfoAggregate();
/*  44: 44 */     localTypePmtInfoAggregate.BankAcctFrom = TypeBankAcctFromAggregateHelper.read(paramInputStream);
/*  45: 45 */     localTypePmtInfoAggregate.TrnAmt = paramInputStream.read_double();
/*  46: 46 */     localTypePmtInfoAggregate.PayeeUn = TypePayeeUnHelper.read(paramInputStream);
/*  47: 47 */     localTypePmtInfoAggregate.PayeeLstID = paramInputStream.read_string();
/*  48: 48 */     localTypePmtInfoAggregate.PayeeLstIDExists = paramInputStream.read_boolean();
/*  49: 49 */     localTypePmtInfoAggregate.BankAcctTo = TypeBankAcctToAggregateHelper.read(paramInputStream);
/*  50: 50 */     localTypePmtInfoAggregate.BankAcctToExists = paramInputStream.read_boolean();
/*  51: 51 */     localTypePmtInfoAggregate.ExtdPmt = TypeExtdPmtAggregateSeqHelper.read(paramInputStream);
/*  52: 52 */     localTypePmtInfoAggregate.PayAcct = paramInputStream.read_string();
/*  53: 53 */     localTypePmtInfoAggregate.DtDue = paramInputStream.read_string();
/*  54: 54 */     localTypePmtInfoAggregate.Memo = paramInputStream.read_string();
/*  55: 55 */     localTypePmtInfoAggregate.MemoExists = paramInputStream.read_boolean();
/*  56: 56 */     localTypePmtInfoAggregate.BillRefInfo = paramInputStream.read_string();
/*  57: 57 */     localTypePmtInfoAggregate.BillRefInfoExists = paramInputStream.read_boolean();
/*  58: 58 */     localTypePmtInfoAggregate.NameOnAcct = paramInputStream.read_string();
/*  59: 59 */     localTypePmtInfoAggregate.NameOnAcctExists = paramInputStream.read_boolean();
/*  60: 60 */     return localTypePmtInfoAggregate;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static void write(OutputStream paramOutputStream, TypePmtInfoAggregate paramTypePmtInfoAggregate)
/*  64:    */   {
/*  65: 67 */     if (paramTypePmtInfoAggregate == null) {
/*  66: 69 */       paramTypePmtInfoAggregate = new TypePmtInfoAggregate();
/*  67:    */     }
/*  68: 71 */     TypeBankAcctFromAggregateHelper.write(paramOutputStream, paramTypePmtInfoAggregate.BankAcctFrom);
/*  69: 72 */     paramOutputStream.write_double(paramTypePmtInfoAggregate.TrnAmt);
/*  70: 73 */     TypePayeeUnHelper.write(paramOutputStream, paramTypePmtInfoAggregate.PayeeUn);
/*  71: 74 */     paramOutputStream.write_string(paramTypePmtInfoAggregate.PayeeLstID);
/*  72: 75 */     paramOutputStream.write_boolean(paramTypePmtInfoAggregate.PayeeLstIDExists);
/*  73: 76 */     TypeBankAcctToAggregateHelper.write(paramOutputStream, paramTypePmtInfoAggregate.BankAcctTo);
/*  74: 77 */     paramOutputStream.write_boolean(paramTypePmtInfoAggregate.BankAcctToExists);
/*  75: 78 */     TypeExtdPmtAggregateSeqHelper.write(paramOutputStream, paramTypePmtInfoAggregate.ExtdPmt);
/*  76: 79 */     paramOutputStream.write_string(paramTypePmtInfoAggregate.PayAcct);
/*  77: 80 */     paramOutputStream.write_string(paramTypePmtInfoAggregate.DtDue);
/*  78: 81 */     paramOutputStream.write_string(paramTypePmtInfoAggregate.Memo);
/*  79: 82 */     paramOutputStream.write_boolean(paramTypePmtInfoAggregate.MemoExists);
/*  80: 83 */     paramOutputStream.write_string(paramTypePmtInfoAggregate.BillRefInfo);
/*  81: 84 */     paramOutputStream.write_boolean(paramTypePmtInfoAggregate.BillRefInfoExists);
/*  82: 85 */     paramOutputStream.write_string(paramTypePmtInfoAggregate.NameOnAcct);
/*  83: 86 */     paramOutputStream.write_boolean(paramTypePmtInfoAggregate.NameOnAcctExists);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static String _idl()
/*  87:    */   {
/*  88: 91 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypePmtInfoAggregate";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static TypeCode type()
/*  92:    */   {
/*  93: 98 */     if (_type == null)
/*  94:    */     {
/*  95:100 */       ORB localORB = ORB.init();
/*  96:101 */       StructMember[] arrayOfStructMember = 
/*  97:102 */         {
/*  98:103 */         new StructMember("BankAcctFrom", TypeBankAcctFromAggregateHelper.type(), null), 
/*  99:104 */         new StructMember("TrnAmt", localORB.get_primitive_tc(TCKind.tk_double), null), 
/* 100:105 */         new StructMember("PayeeUn", TypePayeeUnHelper.type(), null), 
/* 101:106 */         new StructMember("PayeeLstID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 102:107 */         new StructMember("PayeeLstIDExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 103:108 */         new StructMember("BankAcctTo", TypeBankAcctToAggregateHelper.type(), null), 
/* 104:109 */         new StructMember("BankAcctToExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 105:110 */         new StructMember("ExtdPmt", TypeExtdPmtAggregateSeqHelper.type(), null), 
/* 106:111 */         new StructMember("PayAcct", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 107:112 */         new StructMember("DtDue", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 108:113 */         new StructMember("Memo", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 109:114 */         new StructMember("MemoExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 110:115 */         new StructMember("BillRefInfo", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 111:116 */         new StructMember("BillRefInfoExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 112:117 */         new StructMember("NameOnAcct", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 113:118 */         new StructMember("NameOnAcctExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 114:    */       
/* 115:120 */       _type = localORB.create_struct_tc(id(), "TypePmtInfoAggregate", arrayOfStructMember);
/* 116:    */     }
/* 117:122 */     return _type;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static void insert(Any paramAny, TypePmtInfoAggregate paramTypePmtInfoAggregate)
/* 121:    */   {
/* 122:129 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 123:130 */     write(localOutputStream, paramTypePmtInfoAggregate);
/* 124:131 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 125:    */   }
/* 126:    */   
/* 127:    */   public static TypePmtInfoAggregate extract(Any paramAny)
/* 128:    */   {
/* 129:137 */     if (!paramAny.type().equal(type())) {
/* 130:139 */       throw new BAD_OPERATION();
/* 131:    */     }
/* 132:141 */     return read(paramAny.create_input_stream());
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static String id()
/* 136:    */   {
/* 137:146 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypePmtInfoAggregate:1.0";
/* 138:    */   }
/* 139:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInfoAggregateHelper
 * JD-Core Version:    0.7.0.1
 */