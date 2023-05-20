/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
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
/*  12:    */ public abstract class TypePmtInfoV1AggregateHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypePmtInfoV1Aggregate clone(TypePmtInfoV1Aggregate paramTypePmtInfoV1Aggregate)
/*  17:    */   {
/*  18: 16 */     if (paramTypePmtInfoV1Aggregate == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypePmtInfoV1Aggregate localTypePmtInfoV1Aggregate = new TypePmtInfoV1Aggregate();
/*  22: 21 */     localTypePmtInfoV1Aggregate.BankAcctFrom = TypeBankAcctFromV1AggregateHelper.clone(paramTypePmtInfoV1Aggregate.BankAcctFrom);
/*  23: 22 */     localTypePmtInfoV1Aggregate.TrnAmt = paramTypePmtInfoV1Aggregate.TrnAmt;
/*  24: 23 */     localTypePmtInfoV1Aggregate.PayeeUn = TypePayeeUnHelper.clone(paramTypePmtInfoV1Aggregate.PayeeUn);
/*  25: 24 */     localTypePmtInfoV1Aggregate.PayeeLstID = paramTypePmtInfoV1Aggregate.PayeeLstID;
/*  26: 25 */     localTypePmtInfoV1Aggregate.PayeeLstIDExists = paramTypePmtInfoV1Aggregate.PayeeLstIDExists;
/*  27: 26 */     localTypePmtInfoV1Aggregate.BankAcctTo = TypeBankAcctToV1AggregateHelper.clone(paramTypePmtInfoV1Aggregate.BankAcctTo);
/*  28: 27 */     localTypePmtInfoV1Aggregate.BankAcctToExists = paramTypePmtInfoV1Aggregate.BankAcctToExists;
/*  29: 28 */     localTypePmtInfoV1Aggregate.ExtdPmt = TypeExtdPmtV1AggregateSeqHelper.clone(paramTypePmtInfoV1Aggregate.ExtdPmt);
/*  30: 29 */     localTypePmtInfoV1Aggregate.PayAcct = paramTypePmtInfoV1Aggregate.PayAcct;
/*  31: 30 */     localTypePmtInfoV1Aggregate.DtDue = paramTypePmtInfoV1Aggregate.DtDue;
/*  32: 31 */     localTypePmtInfoV1Aggregate.Memo = paramTypePmtInfoV1Aggregate.Memo;
/*  33: 32 */     localTypePmtInfoV1Aggregate.MemoExists = paramTypePmtInfoV1Aggregate.MemoExists;
/*  34: 33 */     localTypePmtInfoV1Aggregate.BillRefInfo = paramTypePmtInfoV1Aggregate.BillRefInfo;
/*  35: 34 */     localTypePmtInfoV1Aggregate.BillRefInfoExists = paramTypePmtInfoV1Aggregate.BillRefInfoExists;
/*  36: 35 */     localTypePmtInfoV1Aggregate.NameOnAcct = paramTypePmtInfoV1Aggregate.NameOnAcct;
/*  37: 36 */     localTypePmtInfoV1Aggregate.NameOnAcctExists = paramTypePmtInfoV1Aggregate.NameOnAcctExists;
/*  38: 37 */     return localTypePmtInfoV1Aggregate;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static TypePmtInfoV1Aggregate read(InputStream paramInputStream)
/*  42:    */   {
/*  43: 43 */     TypePmtInfoV1Aggregate localTypePmtInfoV1Aggregate = new TypePmtInfoV1Aggregate();
/*  44: 44 */     localTypePmtInfoV1Aggregate.BankAcctFrom = TypeBankAcctFromV1AggregateHelper.read(paramInputStream);
/*  45: 45 */     localTypePmtInfoV1Aggregate.TrnAmt = paramInputStream.read_double();
/*  46: 46 */     localTypePmtInfoV1Aggregate.PayeeUn = TypePayeeUnHelper.read(paramInputStream);
/*  47: 47 */     localTypePmtInfoV1Aggregate.PayeeLstID = paramInputStream.read_string();
/*  48: 48 */     localTypePmtInfoV1Aggregate.PayeeLstIDExists = paramInputStream.read_boolean();
/*  49: 49 */     localTypePmtInfoV1Aggregate.BankAcctTo = TypeBankAcctToV1AggregateHelper.read(paramInputStream);
/*  50: 50 */     localTypePmtInfoV1Aggregate.BankAcctToExists = paramInputStream.read_boolean();
/*  51: 51 */     localTypePmtInfoV1Aggregate.ExtdPmt = TypeExtdPmtV1AggregateSeqHelper.read(paramInputStream);
/*  52: 52 */     localTypePmtInfoV1Aggregate.PayAcct = paramInputStream.read_string();
/*  53: 53 */     localTypePmtInfoV1Aggregate.DtDue = paramInputStream.read_string();
/*  54: 54 */     localTypePmtInfoV1Aggregate.Memo = paramInputStream.read_string();
/*  55: 55 */     localTypePmtInfoV1Aggregate.MemoExists = paramInputStream.read_boolean();
/*  56: 56 */     localTypePmtInfoV1Aggregate.BillRefInfo = paramInputStream.read_string();
/*  57: 57 */     localTypePmtInfoV1Aggregate.BillRefInfoExists = paramInputStream.read_boolean();
/*  58: 58 */     localTypePmtInfoV1Aggregate.NameOnAcct = paramInputStream.read_string();
/*  59: 59 */     localTypePmtInfoV1Aggregate.NameOnAcctExists = paramInputStream.read_boolean();
/*  60: 60 */     return localTypePmtInfoV1Aggregate;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static void write(OutputStream paramOutputStream, TypePmtInfoV1Aggregate paramTypePmtInfoV1Aggregate)
/*  64:    */   {
/*  65: 67 */     if (paramTypePmtInfoV1Aggregate == null) {
/*  66: 69 */       paramTypePmtInfoV1Aggregate = new TypePmtInfoV1Aggregate();
/*  67:    */     }
/*  68: 71 */     TypeBankAcctFromV1AggregateHelper.write(paramOutputStream, paramTypePmtInfoV1Aggregate.BankAcctFrom);
/*  69: 72 */     paramOutputStream.write_double(paramTypePmtInfoV1Aggregate.TrnAmt);
/*  70: 73 */     TypePayeeUnHelper.write(paramOutputStream, paramTypePmtInfoV1Aggregate.PayeeUn);
/*  71: 74 */     paramOutputStream.write_string(paramTypePmtInfoV1Aggregate.PayeeLstID);
/*  72: 75 */     paramOutputStream.write_boolean(paramTypePmtInfoV1Aggregate.PayeeLstIDExists);
/*  73: 76 */     TypeBankAcctToV1AggregateHelper.write(paramOutputStream, paramTypePmtInfoV1Aggregate.BankAcctTo);
/*  74: 77 */     paramOutputStream.write_boolean(paramTypePmtInfoV1Aggregate.BankAcctToExists);
/*  75: 78 */     TypeExtdPmtV1AggregateSeqHelper.write(paramOutputStream, paramTypePmtInfoV1Aggregate.ExtdPmt);
/*  76: 79 */     paramOutputStream.write_string(paramTypePmtInfoV1Aggregate.PayAcct);
/*  77: 80 */     paramOutputStream.write_string(paramTypePmtInfoV1Aggregate.DtDue);
/*  78: 81 */     paramOutputStream.write_string(paramTypePmtInfoV1Aggregate.Memo);
/*  79: 82 */     paramOutputStream.write_boolean(paramTypePmtInfoV1Aggregate.MemoExists);
/*  80: 83 */     paramOutputStream.write_string(paramTypePmtInfoV1Aggregate.BillRefInfo);
/*  81: 84 */     paramOutputStream.write_boolean(paramTypePmtInfoV1Aggregate.BillRefInfoExists);
/*  82: 85 */     paramOutputStream.write_string(paramTypePmtInfoV1Aggregate.NameOnAcct);
/*  83: 86 */     paramOutputStream.write_boolean(paramTypePmtInfoV1Aggregate.NameOnAcctExists);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static String _idl()
/*  87:    */   {
/*  88: 91 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypePmtInfoV1Aggregate";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static TypeCode type()
/*  92:    */   {
/*  93: 98 */     if (_type == null)
/*  94:    */     {
/*  95:100 */       ORB localORB = ORB.init();
/*  96:101 */       StructMember[] arrayOfStructMember = 
/*  97:102 */         {
/*  98:103 */         new StructMember("BankAcctFrom", TypeBankAcctFromV1AggregateHelper.type(), null), 
/*  99:104 */         new StructMember("TrnAmt", localORB.get_primitive_tc(TCKind.tk_double), null), 
/* 100:105 */         new StructMember("PayeeUn", TypePayeeUnHelper.type(), null), 
/* 101:106 */         new StructMember("PayeeLstID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 102:107 */         new StructMember("PayeeLstIDExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 103:108 */         new StructMember("BankAcctTo", TypeBankAcctToV1AggregateHelper.type(), null), 
/* 104:109 */         new StructMember("BankAcctToExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 105:110 */         new StructMember("ExtdPmt", TypeExtdPmtV1AggregateSeqHelper.type(), null), 
/* 106:111 */         new StructMember("PayAcct", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 107:112 */         new StructMember("DtDue", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 108:113 */         new StructMember("Memo", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 109:114 */         new StructMember("MemoExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 110:115 */         new StructMember("BillRefInfo", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 111:116 */         new StructMember("BillRefInfoExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 112:117 */         new StructMember("NameOnAcct", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 113:118 */         new StructMember("NameOnAcctExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/* 114:    */       
/* 115:120 */       _type = localORB.create_struct_tc(id(), "TypePmtInfoV1Aggregate", arrayOfStructMember);
/* 116:    */     }
/* 117:122 */     return _type;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static void insert(Any paramAny, TypePmtInfoV1Aggregate paramTypePmtInfoV1Aggregate)
/* 121:    */   {
/* 122:129 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 123:130 */     write(localOutputStream, paramTypePmtInfoV1Aggregate);
/* 124:131 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 125:    */   }
/* 126:    */   
/* 127:    */   public static TypePmtInfoV1Aggregate extract(Any paramAny)
/* 128:    */   {
/* 129:137 */     if (!paramAny.type().equal(type())) {
/* 130:139 */       throw new BAD_OPERATION();
/* 131:    */     }
/* 132:141 */     return read(paramAny.create_input_stream());
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static String id()
/* 136:    */   {
/* 137:146 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypePmtInfoV1Aggregate:1.0";
/* 138:    */   }
/* 139:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInfoV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */