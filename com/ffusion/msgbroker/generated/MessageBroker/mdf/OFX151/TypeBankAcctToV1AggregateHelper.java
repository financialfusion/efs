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
/*  13:    */ public abstract class TypeBankAcctToV1AggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypeBankAcctToV1Aggregate clone(TypeBankAcctToV1Aggregate paramTypeBankAcctToV1Aggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypeBankAcctToV1Aggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypeBankAcctToV1Aggregate localTypeBankAcctToV1Aggregate = new TypeBankAcctToV1Aggregate();
/*  23: 21 */     localTypeBankAcctToV1Aggregate.BankID = paramTypeBankAcctToV1Aggregate.BankID;
/*  24: 22 */     localTypeBankAcctToV1Aggregate.BranchID = paramTypeBankAcctToV1Aggregate.BranchID;
/*  25: 23 */     localTypeBankAcctToV1Aggregate.BranchIDExists = paramTypeBankAcctToV1Aggregate.BranchIDExists;
/*  26: 24 */     localTypeBankAcctToV1Aggregate.AcctID = paramTypeBankAcctToV1Aggregate.AcctID;
/*  27: 25 */     localTypeBankAcctToV1Aggregate.AcctType = ((EnumAccountEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeBankAcctToV1Aggregate.AcctType)));
/*  28: 26 */     localTypeBankAcctToV1Aggregate.AcctKey = paramTypeBankAcctToV1Aggregate.AcctKey;
/*  29: 27 */     localTypeBankAcctToV1Aggregate.AcctKeyExists = paramTypeBankAcctToV1Aggregate.AcctKeyExists;
/*  30: 28 */     return localTypeBankAcctToV1Aggregate;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static TypeBankAcctToV1Aggregate read(InputStream paramInputStream)
/*  34:    */   {
/*  35: 34 */     TypeBankAcctToV1Aggregate localTypeBankAcctToV1Aggregate = new TypeBankAcctToV1Aggregate();
/*  36: 35 */     localTypeBankAcctToV1Aggregate.BankID = paramInputStream.read_string();
/*  37: 36 */     localTypeBankAcctToV1Aggregate.BranchID = paramInputStream.read_string();
/*  38: 37 */     localTypeBankAcctToV1Aggregate.BranchIDExists = paramInputStream.read_boolean();
/*  39: 38 */     localTypeBankAcctToV1Aggregate.AcctID = paramInputStream.read_string();
/*  40: 39 */     localTypeBankAcctToV1Aggregate.AcctType = EnumAccountEnumHelper.read(paramInputStream);
/*  41: 40 */     localTypeBankAcctToV1Aggregate.AcctKey = paramInputStream.read_string();
/*  42: 41 */     localTypeBankAcctToV1Aggregate.AcctKeyExists = paramInputStream.read_boolean();
/*  43: 42 */     return localTypeBankAcctToV1Aggregate;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static void write(OutputStream paramOutputStream, TypeBankAcctToV1Aggregate paramTypeBankAcctToV1Aggregate)
/*  47:    */   {
/*  48: 49 */     if (paramTypeBankAcctToV1Aggregate == null) {
/*  49: 51 */       paramTypeBankAcctToV1Aggregate = new TypeBankAcctToV1Aggregate();
/*  50:    */     }
/*  51: 53 */     paramOutputStream.write_string(paramTypeBankAcctToV1Aggregate.BankID);
/*  52: 54 */     paramOutputStream.write_string(paramTypeBankAcctToV1Aggregate.BranchID);
/*  53: 55 */     paramOutputStream.write_boolean(paramTypeBankAcctToV1Aggregate.BranchIDExists);
/*  54: 56 */     paramOutputStream.write_string(paramTypeBankAcctToV1Aggregate.AcctID);
/*  55: 57 */     EnumAccountEnumHelper.write(paramOutputStream, paramTypeBankAcctToV1Aggregate.AcctType);
/*  56: 58 */     paramOutputStream.write_string(paramTypeBankAcctToV1Aggregate.AcctKey);
/*  57: 59 */     paramOutputStream.write_boolean(paramTypeBankAcctToV1Aggregate.AcctKeyExists);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static String _idl()
/*  61:    */   {
/*  62: 64 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeBankAcctToV1Aggregate";
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static TypeCode type()
/*  66:    */   {
/*  67: 71 */     if (_type == null)
/*  68:    */     {
/*  69: 73 */       ORB localORB = ORB.init();
/*  70: 74 */       StructMember[] arrayOfStructMember = 
/*  71: 75 */         {
/*  72: 76 */         new StructMember("BankID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  73: 77 */         new StructMember("BranchID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  74: 78 */         new StructMember("BranchIDExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  75: 79 */         new StructMember("AcctID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  76: 80 */         new StructMember("AcctType", EnumAccountEnumHelper.type(), null), 
/*  77: 81 */         new StructMember("AcctKey", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  78: 82 */         new StructMember("AcctKeyExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  79:    */       
/*  80: 84 */       _type = localORB.create_struct_tc(id(), "TypeBankAcctToV1Aggregate", arrayOfStructMember);
/*  81:    */     }
/*  82: 86 */     return _type;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static void insert(Any paramAny, TypeBankAcctToV1Aggregate paramTypeBankAcctToV1Aggregate)
/*  86:    */   {
/*  87: 93 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  88: 94 */     write(localOutputStream, paramTypeBankAcctToV1Aggregate);
/*  89: 95 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static TypeBankAcctToV1Aggregate extract(Any paramAny)
/*  93:    */   {
/*  94:101 */     if (!paramAny.type().equal(type())) {
/*  95:103 */       throw new BAD_OPERATION();
/*  96:    */     }
/*  97:105 */     return read(paramAny.create_input_stream());
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static String id()
/* 101:    */   {
/* 102:110 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeBankAcctToV1Aggregate:1.0";
/* 103:    */   }
/* 104:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctToV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */