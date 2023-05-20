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
/*  13:    */ public abstract class TypeBankAcctToAggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypeBankAcctToAggregate clone(TypeBankAcctToAggregate paramTypeBankAcctToAggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypeBankAcctToAggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypeBankAcctToAggregate localTypeBankAcctToAggregate = new TypeBankAcctToAggregate();
/*  23: 21 */     localTypeBankAcctToAggregate.BankID = paramTypeBankAcctToAggregate.BankID;
/*  24: 22 */     localTypeBankAcctToAggregate.BranchID = paramTypeBankAcctToAggregate.BranchID;
/*  25: 23 */     localTypeBankAcctToAggregate.BranchIDExists = paramTypeBankAcctToAggregate.BranchIDExists;
/*  26: 24 */     localTypeBankAcctToAggregate.AcctID = paramTypeBankAcctToAggregate.AcctID;
/*  27: 25 */     localTypeBankAcctToAggregate.AcctType = ((EnumAccountEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeBankAcctToAggregate.AcctType)));
/*  28: 26 */     localTypeBankAcctToAggregate.AcctKey = paramTypeBankAcctToAggregate.AcctKey;
/*  29: 27 */     localTypeBankAcctToAggregate.AcctKeyExists = paramTypeBankAcctToAggregate.AcctKeyExists;
/*  30: 28 */     return localTypeBankAcctToAggregate;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static TypeBankAcctToAggregate read(InputStream paramInputStream)
/*  34:    */   {
/*  35: 34 */     TypeBankAcctToAggregate localTypeBankAcctToAggregate = new TypeBankAcctToAggregate();
/*  36: 35 */     localTypeBankAcctToAggregate.BankID = paramInputStream.read_string();
/*  37: 36 */     localTypeBankAcctToAggregate.BranchID = paramInputStream.read_string();
/*  38: 37 */     localTypeBankAcctToAggregate.BranchIDExists = paramInputStream.read_boolean();
/*  39: 38 */     localTypeBankAcctToAggregate.AcctID = paramInputStream.read_string();
/*  40: 39 */     localTypeBankAcctToAggregate.AcctType = EnumAccountEnumHelper.read(paramInputStream);
/*  41: 40 */     localTypeBankAcctToAggregate.AcctKey = paramInputStream.read_string();
/*  42: 41 */     localTypeBankAcctToAggregate.AcctKeyExists = paramInputStream.read_boolean();
/*  43: 42 */     return localTypeBankAcctToAggregate;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static void write(OutputStream paramOutputStream, TypeBankAcctToAggregate paramTypeBankAcctToAggregate)
/*  47:    */   {
/*  48: 49 */     if (paramTypeBankAcctToAggregate == null) {
/*  49: 51 */       paramTypeBankAcctToAggregate = new TypeBankAcctToAggregate();
/*  50:    */     }
/*  51: 53 */     paramOutputStream.write_string(paramTypeBankAcctToAggregate.BankID);
/*  52: 54 */     paramOutputStream.write_string(paramTypeBankAcctToAggregate.BranchID);
/*  53: 55 */     paramOutputStream.write_boolean(paramTypeBankAcctToAggregate.BranchIDExists);
/*  54: 56 */     paramOutputStream.write_string(paramTypeBankAcctToAggregate.AcctID);
/*  55: 57 */     EnumAccountEnumHelper.write(paramOutputStream, paramTypeBankAcctToAggregate.AcctType);
/*  56: 58 */     paramOutputStream.write_string(paramTypeBankAcctToAggregate.AcctKey);
/*  57: 59 */     paramOutputStream.write_boolean(paramTypeBankAcctToAggregate.AcctKeyExists);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static String _idl()
/*  61:    */   {
/*  62: 64 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeBankAcctToAggregate";
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
/*  80: 84 */       _type = localORB.create_struct_tc(id(), "TypeBankAcctToAggregate", arrayOfStructMember);
/*  81:    */     }
/*  82: 86 */     return _type;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static void insert(Any paramAny, TypeBankAcctToAggregate paramTypeBankAcctToAggregate)
/*  86:    */   {
/*  87: 93 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  88: 94 */     write(localOutputStream, paramTypeBankAcctToAggregate);
/*  89: 95 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static TypeBankAcctToAggregate extract(Any paramAny)
/*  93:    */   {
/*  94:101 */     if (!paramAny.type().equal(type())) {
/*  95:103 */       throw new BAD_OPERATION();
/*  96:    */     }
/*  97:105 */     return read(paramAny.create_input_stream());
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static String id()
/* 101:    */   {
/* 102:110 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeBankAcctToAggregate:1.0";
/* 103:    */   }
/* 104:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctToAggregateHelper
 * JD-Core Version:    0.7.0.1
 */