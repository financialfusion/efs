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
/*  12:    */ public abstract class TypeInvoiceAggregateHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypeInvoiceAggregate clone(TypeInvoiceAggregate paramTypeInvoiceAggregate)
/*  17:    */   {
/*  18: 16 */     if (paramTypeInvoiceAggregate == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypeInvoiceAggregate localTypeInvoiceAggregate = new TypeInvoiceAggregate();
/*  22: 21 */     localTypeInvoiceAggregate.InvNo = paramTypeInvoiceAggregate.InvNo;
/*  23: 22 */     localTypeInvoiceAggregate.InvTotalAmt = paramTypeInvoiceAggregate.InvTotalAmt;
/*  24: 23 */     localTypeInvoiceAggregate.InvPaidAmt = paramTypeInvoiceAggregate.InvPaidAmt;
/*  25: 24 */     localTypeInvoiceAggregate.InvDate = paramTypeInvoiceAggregate.InvDate;
/*  26: 25 */     localTypeInvoiceAggregate.InvDesc = paramTypeInvoiceAggregate.InvDesc;
/*  27: 26 */     localTypeInvoiceAggregate.Discount = TypeDiscountAggregateHelper.clone(paramTypeInvoiceAggregate.Discount);
/*  28: 27 */     localTypeInvoiceAggregate.DiscountExists = paramTypeInvoiceAggregate.DiscountExists;
/*  29: 28 */     localTypeInvoiceAggregate.Adjustment = TypeAdjustmentAggregateHelper.clone(paramTypeInvoiceAggregate.Adjustment);
/*  30: 29 */     localTypeInvoiceAggregate.AdjustmentExists = paramTypeInvoiceAggregate.AdjustmentExists;
/*  31: 30 */     localTypeInvoiceAggregate.LineItem = TypeLineItemAggregateSeqHelper.clone(paramTypeInvoiceAggregate.LineItem);
/*  32: 31 */     return localTypeInvoiceAggregate;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static TypeInvoiceAggregate read(InputStream paramInputStream)
/*  36:    */   {
/*  37: 37 */     TypeInvoiceAggregate localTypeInvoiceAggregate = new TypeInvoiceAggregate();
/*  38: 38 */     localTypeInvoiceAggregate.InvNo = paramInputStream.read_string();
/*  39: 39 */     localTypeInvoiceAggregate.InvTotalAmt = paramInputStream.read_float();
/*  40: 40 */     localTypeInvoiceAggregate.InvPaidAmt = paramInputStream.read_float();
/*  41: 41 */     localTypeInvoiceAggregate.InvDate = paramInputStream.read_string();
/*  42: 42 */     localTypeInvoiceAggregate.InvDesc = paramInputStream.read_string();
/*  43: 43 */     localTypeInvoiceAggregate.Discount = TypeDiscountAggregateHelper.read(paramInputStream);
/*  44: 44 */     localTypeInvoiceAggregate.DiscountExists = paramInputStream.read_boolean();
/*  45: 45 */     localTypeInvoiceAggregate.Adjustment = TypeAdjustmentAggregateHelper.read(paramInputStream);
/*  46: 46 */     localTypeInvoiceAggregate.AdjustmentExists = paramInputStream.read_boolean();
/*  47: 47 */     localTypeInvoiceAggregate.LineItem = TypeLineItemAggregateSeqHelper.read(paramInputStream);
/*  48: 48 */     return localTypeInvoiceAggregate;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static void write(OutputStream paramOutputStream, TypeInvoiceAggregate paramTypeInvoiceAggregate)
/*  52:    */   {
/*  53: 55 */     if (paramTypeInvoiceAggregate == null) {
/*  54: 57 */       paramTypeInvoiceAggregate = new TypeInvoiceAggregate();
/*  55:    */     }
/*  56: 59 */     paramOutputStream.write_string(paramTypeInvoiceAggregate.InvNo);
/*  57: 60 */     paramOutputStream.write_float(paramTypeInvoiceAggregate.InvTotalAmt);
/*  58: 61 */     paramOutputStream.write_float(paramTypeInvoiceAggregate.InvPaidAmt);
/*  59: 62 */     paramOutputStream.write_string(paramTypeInvoiceAggregate.InvDate);
/*  60: 63 */     paramOutputStream.write_string(paramTypeInvoiceAggregate.InvDesc);
/*  61: 64 */     TypeDiscountAggregateHelper.write(paramOutputStream, paramTypeInvoiceAggregate.Discount);
/*  62: 65 */     paramOutputStream.write_boolean(paramTypeInvoiceAggregate.DiscountExists);
/*  63: 66 */     TypeAdjustmentAggregateHelper.write(paramOutputStream, paramTypeInvoiceAggregate.Adjustment);
/*  64: 67 */     paramOutputStream.write_boolean(paramTypeInvoiceAggregate.AdjustmentExists);
/*  65: 68 */     TypeLineItemAggregateSeqHelper.write(paramOutputStream, paramTypeInvoiceAggregate.LineItem);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static String _idl()
/*  69:    */   {
/*  70: 73 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeInvoiceAggregate";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static TypeCode type()
/*  74:    */   {
/*  75: 80 */     if (_type == null)
/*  76:    */     {
/*  77: 82 */       ORB localORB = ORB.init();
/*  78: 83 */       StructMember[] arrayOfStructMember = 
/*  79: 84 */         {
/*  80: 85 */         new StructMember("InvNo", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  81: 86 */         new StructMember("InvTotalAmt", localORB.get_primitive_tc(TCKind.tk_float), null), 
/*  82: 87 */         new StructMember("InvPaidAmt", localORB.get_primitive_tc(TCKind.tk_float), null), 
/*  83: 88 */         new StructMember("InvDate", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  84: 89 */         new StructMember("InvDesc", localORB.get_primitive_tc(TCKind.tk_string), null), 
/*  85: 90 */         new StructMember("Discount", TypeDiscountAggregateHelper.type(), null), 
/*  86: 91 */         new StructMember("DiscountExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  87: 92 */         new StructMember("Adjustment", TypeAdjustmentAggregateHelper.type(), null), 
/*  88: 93 */         new StructMember("AdjustmentExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  89: 94 */         new StructMember("LineItem", TypeLineItemAggregateSeqHelper.type(), null) };
/*  90:    */       
/*  91: 96 */       _type = localORB.create_struct_tc(id(), "TypeInvoiceAggregate", arrayOfStructMember);
/*  92:    */     }
/*  93: 98 */     return _type;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static void insert(Any paramAny, TypeInvoiceAggregate paramTypeInvoiceAggregate)
/*  97:    */   {
/*  98:105 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  99:106 */     write(localOutputStream, paramTypeInvoiceAggregate);
/* 100:107 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static TypeInvoiceAggregate extract(Any paramAny)
/* 104:    */   {
/* 105:113 */     if (!paramAny.type().equal(type())) {
/* 106:115 */       throw new BAD_OPERATION();
/* 107:    */     }
/* 108:117 */     return read(paramAny.create_input_stream());
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static String id()
/* 112:    */   {
/* 113:122 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeInvoiceAggregate:1.0";
/* 114:    */   }
/* 115:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeInvoiceAggregateHelper
 * JD-Core Version:    0.7.0.1
 */