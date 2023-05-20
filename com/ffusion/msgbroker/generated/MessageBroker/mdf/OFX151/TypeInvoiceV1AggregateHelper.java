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
/*  12:    */ public abstract class TypeInvoiceV1AggregateHelper
/*  13:    */ {
/*  14:    */   public static TypeCode _type;
/*  15:    */   
/*  16:    */   public static TypeInvoiceV1Aggregate clone(TypeInvoiceV1Aggregate paramTypeInvoiceV1Aggregate)
/*  17:    */   {
/*  18: 16 */     if (paramTypeInvoiceV1Aggregate == null) {
/*  19: 18 */       return null;
/*  20:    */     }
/*  21: 20 */     TypeInvoiceV1Aggregate localTypeInvoiceV1Aggregate = new TypeInvoiceV1Aggregate();
/*  22: 21 */     localTypeInvoiceV1Aggregate.InvNo = paramTypeInvoiceV1Aggregate.InvNo;
/*  23: 22 */     localTypeInvoiceV1Aggregate.InvTotalAmt = paramTypeInvoiceV1Aggregate.InvTotalAmt;
/*  24: 23 */     localTypeInvoiceV1Aggregate.InvPaidAmt = paramTypeInvoiceV1Aggregate.InvPaidAmt;
/*  25: 24 */     localTypeInvoiceV1Aggregate.InvDate = paramTypeInvoiceV1Aggregate.InvDate;
/*  26: 25 */     localTypeInvoiceV1Aggregate.InvDesc = paramTypeInvoiceV1Aggregate.InvDesc;
/*  27: 26 */     localTypeInvoiceV1Aggregate.Discount = TypeDiscountV1AggregateHelper.clone(paramTypeInvoiceV1Aggregate.Discount);
/*  28: 27 */     localTypeInvoiceV1Aggregate.DiscountExists = paramTypeInvoiceV1Aggregate.DiscountExists;
/*  29: 28 */     localTypeInvoiceV1Aggregate.Adjustment = TypeAdjustmentAggregateHelper.clone(paramTypeInvoiceV1Aggregate.Adjustment);
/*  30: 29 */     localTypeInvoiceV1Aggregate.AdjustmentExists = paramTypeInvoiceV1Aggregate.AdjustmentExists;
/*  31: 30 */     localTypeInvoiceV1Aggregate.LineItem = TypeLineItemV1AggregateSeqHelper.clone(paramTypeInvoiceV1Aggregate.LineItem);
/*  32: 31 */     return localTypeInvoiceV1Aggregate;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static TypeInvoiceV1Aggregate read(InputStream paramInputStream)
/*  36:    */   {
/*  37: 37 */     TypeInvoiceV1Aggregate localTypeInvoiceV1Aggregate = new TypeInvoiceV1Aggregate();
/*  38: 38 */     localTypeInvoiceV1Aggregate.InvNo = paramInputStream.read_string();
/*  39: 39 */     localTypeInvoiceV1Aggregate.InvTotalAmt = paramInputStream.read_float();
/*  40: 40 */     localTypeInvoiceV1Aggregate.InvPaidAmt = paramInputStream.read_float();
/*  41: 41 */     localTypeInvoiceV1Aggregate.InvDate = paramInputStream.read_string();
/*  42: 42 */     localTypeInvoiceV1Aggregate.InvDesc = paramInputStream.read_string();
/*  43: 43 */     localTypeInvoiceV1Aggregate.Discount = TypeDiscountV1AggregateHelper.read(paramInputStream);
/*  44: 44 */     localTypeInvoiceV1Aggregate.DiscountExists = paramInputStream.read_boolean();
/*  45: 45 */     localTypeInvoiceV1Aggregate.Adjustment = TypeAdjustmentAggregateHelper.read(paramInputStream);
/*  46: 46 */     localTypeInvoiceV1Aggregate.AdjustmentExists = paramInputStream.read_boolean();
/*  47: 47 */     localTypeInvoiceV1Aggregate.LineItem = TypeLineItemV1AggregateSeqHelper.read(paramInputStream);
/*  48: 48 */     return localTypeInvoiceV1Aggregate;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static void write(OutputStream paramOutputStream, TypeInvoiceV1Aggregate paramTypeInvoiceV1Aggregate)
/*  52:    */   {
/*  53: 55 */     if (paramTypeInvoiceV1Aggregate == null) {
/*  54: 57 */       paramTypeInvoiceV1Aggregate = new TypeInvoiceV1Aggregate();
/*  55:    */     }
/*  56: 59 */     paramOutputStream.write_string(paramTypeInvoiceV1Aggregate.InvNo);
/*  57: 60 */     paramOutputStream.write_float(paramTypeInvoiceV1Aggregate.InvTotalAmt);
/*  58: 61 */     paramOutputStream.write_float(paramTypeInvoiceV1Aggregate.InvPaidAmt);
/*  59: 62 */     paramOutputStream.write_string(paramTypeInvoiceV1Aggregate.InvDate);
/*  60: 63 */     paramOutputStream.write_string(paramTypeInvoiceV1Aggregate.InvDesc);
/*  61: 64 */     TypeDiscountV1AggregateHelper.write(paramOutputStream, paramTypeInvoiceV1Aggregate.Discount);
/*  62: 65 */     paramOutputStream.write_boolean(paramTypeInvoiceV1Aggregate.DiscountExists);
/*  63: 66 */     TypeAdjustmentAggregateHelper.write(paramOutputStream, paramTypeInvoiceV1Aggregate.Adjustment);
/*  64: 67 */     paramOutputStream.write_boolean(paramTypeInvoiceV1Aggregate.AdjustmentExists);
/*  65: 68 */     TypeLineItemV1AggregateSeqHelper.write(paramOutputStream, paramTypeInvoiceV1Aggregate.LineItem);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static String _idl()
/*  69:    */   {
/*  70: 73 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeInvoiceV1Aggregate";
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
/*  85: 90 */         new StructMember("Discount", TypeDiscountV1AggregateHelper.type(), null), 
/*  86: 91 */         new StructMember("DiscountExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  87: 92 */         new StructMember("Adjustment", TypeAdjustmentAggregateHelper.type(), null), 
/*  88: 93 */         new StructMember("AdjustmentExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  89: 94 */         new StructMember("LineItem", TypeLineItemV1AggregateSeqHelper.type(), null) };
/*  90:    */       
/*  91: 96 */       _type = localORB.create_struct_tc(id(), "TypeInvoiceV1Aggregate", arrayOfStructMember);
/*  92:    */     }
/*  93: 98 */     return _type;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static void insert(Any paramAny, TypeInvoiceV1Aggregate paramTypeInvoiceV1Aggregate)
/*  97:    */   {
/*  98:105 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  99:106 */     write(localOutputStream, paramTypeInvoiceV1Aggregate);
/* 100:107 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static TypeInvoiceV1Aggregate extract(Any paramAny)
/* 104:    */   {
/* 105:113 */     if (!paramAny.type().equal(type())) {
/* 106:115 */       throw new BAD_OPERATION();
/* 107:    */     }
/* 108:117 */     return read(paramAny.create_input_stream());
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static String id()
/* 112:    */   {
/* 113:122 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeInvoiceV1Aggregate:1.0";
/* 114:    */   }
/* 115:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeInvoiceV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */