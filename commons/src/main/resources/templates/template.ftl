<#list reports as report>
    <#-- Report level -->
    <#list report.transactionList as expense>
        ${expense.merchant},<#t>
        ${expense.amount},<#t>
        ${expense.created},<#t>
        ${expense.receiptID},<#t>
        ${expense.receiptFilename},<#t>
        ${expense.reportID},<#t>
        ${expense.units.count},<#t>
        ${expense.attendees}<#lt>
    </#list>
</#list>