package pl.surecase.eu;

import java.util.List;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;
import de.greenrobot.daogenerator.ToManyWithJoinEntity;

public class MboDaoGenerator {
    private static Schema schema;

    public static void main(String args[]) throws Exception {
        int version = 13;
        schema = new Schema(version, "com.mbopartners.mbomobile.data.db.generated.model");
        schema.setDefaultJavaPackageTest("com.mbopartners.mbomobile.data.db.generated.test");
        schema.setDefaultJavaPackageDao("com.mbopartners.mbomobile.data.db.generated.dao");

        createBusinessManager_Table();
        createUserProfileTable();

        Entity dashboard = create_Dashboard_Table();
        Entity dashboardFields = create_DashboardField_Table();

        Entity workOrder = create_WorkOrder_Table();
        Entity timeTask = create_TimeTask_Table();
        Entity company = create_Company_Table();
        Entity timePeriod = create_TimePeriod_Table();
        Entity timeEntry = create_TimeEntry_Table();

        Entity expenses = create_Expense_Table();
        Entity expenseData = create_ExpenseData_Table();
        Entity receipt = create_Receipt_Table();

        Entity expenseType = create_ExpenseType_Table();
        Entity expenseField = create_ExpenseField_Table();
        Entity expenseFieldValue = create_ExpenseFieldValue_Table();
        Entity business_center_table =create_business_center_table();
        Entity business_address_table =create_business_address_table();
        Entity payroll_summary_table=create_payroll_summary_table();
        Entity payroll_nextPayment_table=create_payroll_nextPayment_table();
        Entity payroll_previousPayment_table=create_payroll_previousPayment_table();
        Entity payroll_businessWithHolding_table=create_payroll_businessWithHolding_table();

        Entity payroll_summary_businessWithHolding_table=create_payroll_summary_businessWithHolding_table();
        Entity payroll_summary_businessHolding_payment_amount_table=create_summary_businessHolding_payment_amount_table();

        Entity payroll_personWithHolding_table=create_payroll_personeWithHolding_table();
        Entity payroll_personHolding_payrollTaxes_table=create_personHolding_payrollTaxes_table();
        Entity payroll_businessHolding_payment_amount_table=create_businessHolding_payment_amount_table();
        Entity payroll_businessHolding_businessExpenses_table=create_businessHolding_businessExpenses_table();
        Entity payroll_businessHolding_payrollTaxes_table=create_businessHolding_payrollTaxes_table();
        Entity payroll_personal_expenseReimbersements_table=create_personal_expenseReimbersements_table();
        Entity payroll_personal_deposits=vreate_personal_deposits_table();
        Entity payroll_transaction_table=create_payroll_transaction_table();
        Entity expenseType_2_expenseField_joiner = expenseType_2_expenseField();

        Entity payroll_personWithHolding_gross_amount_table=create_businessHolding_gross_amount_table();
        Entity payroll_personWithHolding_net_amount_table=create_personWithHolding_net_amount_table();
        Entity payroll_personWithHolding_paycheck_amount_table=create_personWithHolding_paycheck_amount_table();
        Entity payroll_personWithHolding_after_tax_deductions_table=create_payroll_personWithHolding_after_tax_deducations_table();


        addOneToManyRelation(dashboard, dashboardFields, "DashboardId", "Fields");

        addOneToManyRelation(workOrder, timeTask, "WorkOrderId", "TimeTasks");
        addToOneRelation(workOrder, company, "CompanyId");
        addOneToManyRelation(workOrder, timePeriod, "WorkOrderId", "TimePeriods");
        addOneToManyRelation(timePeriod, timeEntry, "TimePeriodId", "TimeEntries");

        addOneToManyRelation(payroll_summary_table,payroll_nextPayment_table,"nextPaymentRowId","next_payroll");
        addOneToManyRelation(payroll_summary_table,payroll_previousPayment_table,"previousPaymentRowId","last_payroll");
        addOneToManyRelation(payroll_previousPayment_table,payroll_businessWithHolding_table,"businessWithHoldingRowId","businessWithholding");
        addOneToManyRelation(payroll_previousPayment_table,payroll_personWithHolding_table,"personWithHoldingRowId","personWithHoldingRowId");


        addOneToManyRelation(payroll_previousPayment_table,payroll_summary_businessWithHolding_table,"businessWithHoldingRowId","businessWithholding");
        addOneToManyRelation(payroll_summary_businessHolding_payment_amount_table,payroll_summary_businessWithHolding_table,"paymentAmountRowId","payrollAmount");


        addOneToManyRelation(payroll_personHolding_payrollTaxes_table,payroll_personWithHolding_table,"personPayrollTaxesRowId","payrollTaxes");
        addOneToManyRelation(payroll_personal_expenseReimbersements_table,payroll_personWithHolding_table,"expenseReimbursementsRowId","expenseReimbursements");
        addOneToManyRelation(payroll_personal_deposits,payroll_personWithHolding_table,"depositsRowId","deposits");
        addOneToManyRelation(payroll_personWithHolding_after_tax_deductions_table,payroll_personWithHolding_table,"afterDeductionsRowId","afterTaxDeductions");

        addOneToManyRelation(payroll_businessHolding_payment_amount_table,payroll_businessWithHolding_table,"paymentAmountRowId","payrollAmount");
        addOneToManyRelation(payroll_businessHolding_businessExpenses_table,payroll_businessWithHolding_table,"businessExpensesRowId","businessExpenses");
        addOneToManyRelation(payroll_businessHolding_payrollTaxes_table,payroll_businessWithHolding_table,"businessPayrollTaxesRowId","payrollTaxes");
        addOneToManyRelation(payroll_personWithHolding_gross_amount_table,payroll_personWithHolding_table,"grossAmountRowId","grossAmount");
        addOneToManyRelation(payroll_personWithHolding_net_amount_table,payroll_personWithHolding_table,"netAmountRowId","netAmount");
        addOneToManyRelation(payroll_personWithHolding_paycheck_amount_table,payroll_personWithHolding_table,"payCheckAmountRowId","paycheckAmount");

        addOneToManyRelation(payroll_transaction_table,payroll_businessWithHolding_table,"businessWithHoldingRowId","businessWithholding");
        addOneToManyRelation(payroll_transaction_table,payroll_personWithHolding_table,"personWithHoldingRowId","personWithHoldingRowId");

        addToOneRelation_String(expenses, expenseType, "MboExpenseTypeId", "ExpenseType");
        //addToOneWithoutPropertyRelation(expenses, workOrder, "WorkOrder", "MboId");
        addOneToManyRelation(expenses, expenseData, "ExpenseId", "ExpenseData");
        addOneToManyRelation(expenses, receipt, "ExpenseId", "Receipts");

        addOneToManyRelation_String(expenseType, expenseField, "ExpenseTypeId", "Fields");
        addOneToManyRelation(expenseField, expenseFieldValue, "ExpenseFieldId", "Values");


//        addManyToManyRelationWithJoinEntity(expenseType,expenseType_2_expenseField_joiner, expenseFields);
//
        new DaoGenerator().generateAll(schema, args[0]);
    }




    // ================================================================================
    // Tables declaration
    // ================================================================================
    //
    // ATTENTION !!!
    // Naming convention.
    //
    // "MboId" - id properties comes from MBO system.
    // "Id" - id property generated by mobile application.
    //
    // ================================================================================

    public static Entity createBusinessManager_Table() {
        Entity table = schema.addEntity("TableBusinessManager");
        table.addIdProperty().autoincrement();
        notNull(table.addStringProperty("MboId"));
        notNull(table.addStringProperty("FirstName"));
        notNull(table.addStringProperty("LastName"));
        notNull(table.addStringProperty("Email"));
        return table;
    }

    public static Entity createUserProfileTable() {
        Entity table = schema.addEntity("TableUserProfile");
        table.addIdProperty().autoincrement();
        notNull(table.addStringProperty("MboId"));
        table.addStringProperty("Number");
        table.addStringProperty("Status");
        table.addStringProperty("FirstName");
        table.addStringProperty("LastName");
        table.addStringProperty("Email");
        table.addBooleanProperty("NonbillableAllowed");
        return table;
    }

    public static Entity create_Dashboard_Table() {
        Entity table = schema.addEntity("TableDashboard");
        table.addIdProperty().autoincrement();
        table.addStringProperty("Purpose");
        return table;

    }
    public static Entity create_DashboardField_Table() {
        Entity table = schema.addEntity("TableDashboardField");
        table.addIdProperty().autoincrement();
        table.addStringProperty("Name");
        table.addStringProperty("Value");
        return table;
    }

    //---------------------------------------------------------------------------------

    public static Entity create_WorkOrder_Table() {
        Entity table = schema.addEntity("TableWorkOrder");
        table.addIdProperty().autoincrement();
        notNull(table.addStringProperty("MboId"));
        notNull(table.addStringProperty("Name"));
        notNull(table.addBooleanProperty("TimeEntryAllowed"));
        notNull(table.addBooleanProperty("BillableExpensesAllowed"));
        // timePeriods
        // timeTasks
        //notNull(table.addStringProperty("CompanyId"));
        return table;
    }

    public static Entity create_Company_Table() {
        Entity company = schema.addEntity("TableCompany");
        company.addIdProperty().autoincrement();
        notNull(company.addStringProperty("MboId"));
        notNull(company.addStringProperty("Name"));
        return company;
    }

    public static Entity create_TimePeriod_Table() {
        Entity timePeriod = schema.addEntity("TableTimePeriod");
        timePeriod.addIdProperty().autoincrement();
        notNull(timePeriod.addStringProperty("MboId"));
        notNull(timePeriod.addStringProperty("Name"));
        notNull(timePeriod.addDateProperty("StartDate"));
        notNull(timePeriod.addDateProperty("EndDate"));
        notNull(timePeriod.addBooleanProperty("Current"));
        notNull(timePeriod.addBooleanProperty("Submittable"));
        notNull(timePeriod.addStringProperty("MboWorkOrderId"));

        // TimeEntries
        return timePeriod;
    }

    public static Entity create_TimeTask_Table() {
        Entity timeTask = schema.addEntity("TableTimeTask");
        timeTask.addIdProperty().autoincrement();
        notNull(timeTask.addStringProperty("MboId"));
        notNull(timeTask.addStringProperty("Name"));
        return timeTask;
    }

    public static Entity create_TimeEntry_Table() {
        Entity timeEntry = schema.addEntity("TableTimeEntry");
        timeEntry.addIdProperty().autoincrement();
        notNull(timeEntry.addIntProperty("RecState"));

        timeEntry.addStringProperty("MboId");
        notNull(timeEntry.addStringProperty("WorkOrderId"));
        notNull(timeEntry.addStringProperty("TaskID"));
        notNull(timeEntry.addDoubleProperty("Hours"));
        notNull(timeEntry.addDateProperty("Date"));
        timeEntry.addStringProperty("Note");
        notNull(timeEntry.addBooleanProperty("Editable"));
        timeEntry.addDoubleProperty("Version");
        return timeEntry;
    }

    //---------------------------------------------------------------------------------

    public static Entity create_Expense_Table() {
        Entity expenses = schema.addEntity("TableExpense");
        expenses.addIdProperty().autoincrement();

        notNull(expenses.addStringProperty("MboId"));
        expenses.addStringProperty("MboWorkOrderId");
        expenses.addStringProperty("Description");
        notNull(expenses.addStringProperty("MboAssociateId"));
        //notNull(expenses.addStringProperty("MboExpenseTypeId"));
        notNull(expenses.addDoubleProperty("Amount"));
        notNull(expenses.addBooleanProperty("Editable"));
        notNull(expenses.addBooleanProperty("Billable"));
        expenses.addDoubleProperty("Version");
        //expenses.addStringProperty("lastChangedDate");
        return expenses;
    }

    public static Entity create_ExpenseData_Table() {
        Entity expenseData = schema.addEntity("TableExpenseData");
        expenseData.addIdProperty().autoincrement();

        notNull(expenseData.addStringProperty("Name"));
        notNull(expenseData.addStringProperty("Value"));
        return expenseData;
    }

    public static Entity create_Receipt_Table() {
        Entity receipt = schema.addEntity("TableReceipt");
        receipt.addIdProperty().autoincrement();
        notNull(receipt.addIntProperty("RecState"));

        notNull(receipt.addStringProperty("Filename"));
        notNull(receipt.addStringProperty("MboExpenseId"));
        notNull(receipt.addDateProperty("CreationDate"));
        receipt.addStringProperty("ThumbnailPath");
        receipt.addDoubleProperty("Version");
        return receipt;
    }

    public static Entity create_ExpenseType_Table() {
        Entity table = schema.addEntity("TableExpenseType");

        table.addStringProperty("MboId").primaryKey().notNull();

        notNull(table.addStringProperty("Name"));
        // fields
        return table;
    }

    public static Entity create_ExpenseField_Table() {
        Entity table = schema.addEntity("TableExpenseField");
        table.addIdProperty().autoincrement();

        table.addStringProperty("MboId").notNull();
        table.addStringProperty("Type");
        table.addStringProperty("Name");
        table.addBooleanProperty("Required");
        table.addIntProperty("MaxLength");
        // values = null;
        return table;
    }

    public static Entity create_ExpenseFieldValue_Table() {
        Entity table = schema.addEntity("TableExpenseFieldValue");
        table.addIdProperty().autoincrement();
        table.addStringProperty("MboId");
        table.addStringProperty("Value");
        return table;
    }

    public static Entity expenseType_2_expenseField() {
        Entity table = schema.addEntity("ExpenseType_2_ExpenseField");
        table.addIdProperty().autoincrement();

        return table;
    }

    public static Entity create_business_center_table(){
        Entity table = schema.addEntity("TableBusinessCenter");
        table.addIdProperty().autoincrement();
        table.addStringProperty("BusinessCenterId");
        table.addStringProperty("Name");
        table.addStringProperty("MboId");
        table.addDoubleProperty("Balance");
        return table;
    }
    public static Entity create_business_address_table(){
        Entity table = schema.addEntity("TableBusinessAddress");
        table.addIdProperty().autoincrement();
        table.addStringProperty("line1");
        table.addStringProperty("line2");
        table.addStringProperty("city");
        table.addStringProperty("state");
        table.addStringProperty("postalCode");
        return table;
    }

    public static Entity create_payroll_summary_table()
    {
        Entity table = schema.addEntity("TablePayrollSummary");
        table.addIdProperty().autoincrement();
        table.addDoubleProperty("balance");
        table.addStringProperty("summaryId");
        table.addStringProperty("name");
        return table;
    }

    public static Entity create_payroll_nextPayment_table()
    {
        Entity table = schema.addEntity("TablePayrollNextPayment");
        table.addIdProperty().autoincrement();
        table.addDoubleProperty("amount");
        /*table.addStringProperty("businessCenterId");*/
        table.addStringProperty("calculationMethod");
        /*table.addDateProperty("endDate");*/
        table.addDateProperty("startDate");
        table.addStringProperty("frequency");
        table.addStringProperty("nextPaymentId");
        table.addStringProperty("mboId");
        return table;
    }

    private static Entity create_payroll_previousPayment_table() {
        Entity table = schema.addEntity("TablePayrollPreviousPayment");
        table.addIdProperty().autoincrement();
        table.addStringProperty("businessCenterId");
        table.addDateProperty("date");
        table.addStringProperty("nextPaymentId");
        table.addStringProperty("mboId");
        return table;
    }

    private static Entity create_payroll_businessWithHolding_table() {

        Entity table = schema.addEntity("TableBusinessWithHolding");
        table.addIdProperty().autoincrement();

        return table;
    }

    private static Entity create_payroll_personeWithHolding_table() {
        Entity table = schema.addEntity("TablePersonWithHolding");
        table.addIdProperty().autoincrement();
        table.addStringProperty("federalAllowance");
        return table;

    }

    private static Entity create_businessHolding_payment_amount_table() {

        Entity table = schema.addEntity("TableBusinessPaymentAmount");
        table.addIdProperty().autoincrement();
        table.addDoubleProperty("amount");
        table.addDoubleProperty("amountMtd");
        table.addDoubleProperty("amountYtd");
        table.addStringProperty("name");
        return table;
    }

    private static Entity create_businessHolding_businessExpenses_table() {

        Entity table = schema.addEntity("TableBusinessHoldingExpense");
        table.addIdProperty().autoincrement();
        table.addDoubleProperty("amount");
        table.addDoubleProperty("amountMtd");
        table.addDoubleProperty("amountYtd");
        table.addStringProperty("name");
        return table;
    }

    private static Entity create_businessHolding_payrollTaxes_table() {

        Entity table = schema.addEntity("TableBusinessHoldingPayrollTaxes");
        table.addIdProperty().autoincrement();
        table.addDoubleProperty("amount");
        table.addDoubleProperty("amountMtd");
        table.addDoubleProperty("amountYtd");
        table.addStringProperty("name");
        return table;
    }

    private static Entity create_businessHolding_gross_amount_table() {
        Entity table = schema.addEntity("TablePersonWithHoldingGrossAmount");
        table.addIdProperty().autoincrement();
        table.addDoubleProperty("amount");
        table.addDoubleProperty("amountMtd");
        table.addDoubleProperty("amountYtd");
        table.addStringProperty("name");
        return table;

    }

    private static Entity create_personHolding_payrollTaxes_table() {

        Entity table = schema.addEntity("TablePersonHoldingPayrollTaxes");
        table.addIdProperty().autoincrement();
        table.addDoubleProperty("amount");
        table.addDoubleProperty("amountMtd");
        table.addDoubleProperty("amountYtd");
        table.addStringProperty("name");
        return table;
    }

    private static Entity create_personal_expenseReimbersements_table() {

        Entity table = schema.addEntity("TablePersonalExpenseReimbersements");
        table.addIdProperty().autoincrement();
        table.addDoubleProperty("amount");
        table.addDoubleProperty("amountMtd");
        table.addDoubleProperty("amountYtd");
        table.addStringProperty("name");
        return table;
    }

    private static Entity create_payroll_transaction_table() {

        Entity table=schema.addEntity("TablePayrollTransactions");
        table.addIdProperty().autoincrement();
        table.addStringProperty("transactionId");
        table.addStringProperty("mboId");
        table.addDateProperty("date");
        table.addStringProperty("businessCenterId");
        return table;
    }

    private static Entity vreate_personal_deposits_table() {
        Entity table = schema.addEntity("TablePersonalDeposits");
        table.addIdProperty().autoincrement();
        table.addDoubleProperty("amount");
        table.addDoubleProperty("amountMtd");
        table.addDoubleProperty("amountYtd");
        table.addStringProperty("name");
        return table;

    }

    private static Entity create_payroll_summary_businessWithHolding_table() {

        Entity table = schema.addEntity("TableSummaryBusinessWithHolding");
        table.addIdProperty().autoincrement();

        return table;
    }

    private static Entity create_summary_businessHolding_payment_amount_table() {

        Entity table = schema.addEntity("TableSummaryBusinessPaymentAmount");
        table.addIdProperty().autoincrement();
        table.addDoubleProperty("amount");
        table.addDoubleProperty("amountMtd");
        table.addDoubleProperty("amountYtd");
        table.addStringProperty("name");
        return table;
    }

    private static Entity create_payroll_personWithHolding_after_tax_deducations_table() {

        Entity table = schema.addEntity("TablePersonAfterDeducations");
        table.addIdProperty().autoincrement();
        table.addDoubleProperty("amount");
        table.addDoubleProperty("amountMtd");
        table.addDoubleProperty("amountYtd");
        table.addStringProperty("name");
        return table;
    }

    private static Entity create_personWithHolding_net_amount_table() {

        Entity table = schema.addEntity("TablePersonWithHoldingNetAmount");
        table.addIdProperty().autoincrement();
        table.addDoubleProperty("amount");
        table.addDoubleProperty("amountMtd");
        table.addDoubleProperty("amountYtd");
        table.addStringProperty("name");
        return table;
    }
    private static Entity create_personWithHolding_paycheck_amount_table() {

        Entity table = schema.addEntity("TablePersonWithHoldingPayCheckAmount");
        table.addIdProperty().autoincrement();
        table.addDoubleProperty("amount");
        table.addDoubleProperty("amountMtd");
        table.addDoubleProperty("amountYtd");
        table.addStringProperty("name");
        return table;
    }

    // ================================================================================
    // End of Tables declaration
    // ================================================================================

    // Syntax sugar
    private static Property.PropertyBuilder as_PK(Property.PropertyBuilder pb) {
        pb.primaryKey();
        return pb;
    }

    private static Property.PropertyBuilder notNull(Property.PropertyBuilder pb) {
        pb.notNull();
        return pb;
    }

    public static Property extractProperty(Entity entity, String propertyName) {
        List<Property> properties = entity.getProperties();
        Property extractedProperty = null;
        for (Property property : properties) {
            if (propertyName.equals(property.getPropertyName())) {
                extractedProperty = property;
                break;
            }
        }
        if (extractedProperty == null) {
            throw new IllegalArgumentException("Unknown property : " + propertyName + " in table :" + entity.getTableName());
        } else {
            return extractedProperty;
        }
    }

    private static void addToOneRelation(Entity from, Entity to, String propertyName) {
        Property property = from.addLongProperty(propertyName).notNull().getProperty();
        from.addToOne(to, property);
    }

    private static void addToOneWithoutPropertyRelation(Entity from, Entity to, String propertyName, String fkColumnName) {
        from.addToOneWithoutProperty(propertyName, to, fkColumnName);
    }


    private static void addToOneRelation_String(Entity from, Entity to, String propertyName, String name) {
        Property property = from.addStringProperty(propertyName).notNull().getProperty();
        from.addToOne(to, property, name);
    }

    private static void addOneToManyRelation(Entity one, Entity many, String propertyName, String name) {
        Property property = many.addLongProperty(propertyName).notNull().getProperty();
        many.addToOne(one, property);
        ToMany toMany = one.addToMany(many, property);
        toMany.setName(name);
    }

    private static void addOneToManyRelation_String(Entity one, Entity many, String propertyName, String name) {
        Property property = many.addStringProperty(propertyName).notNull().getProperty();
        many.addToOne(one, property);
        ToMany toMany = one.addToMany(many, property);
        toMany.setName(name);
    }

    private static void addManyToManyRelationWithJoinEntity(Entity from, Entity joinEntity, Entity to) {
        Property id1 = joinEntity.addStringProperty("TypeId").notNull().getProperty();
        Property id2 = joinEntity.addStringProperty("FieldId").notNull().getProperty();
        ToManyWithJoinEntity many_2_many = from.addToMany(to, joinEntity, id1, id2);
    }

}
