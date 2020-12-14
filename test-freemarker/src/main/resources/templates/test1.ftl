<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <title>Hello World!</title>
</head>
<body>
Hello ${name}!
<#if list??>
    <#list list as value>
        ${value}
    </#list>
</#if>
<#--<#if model??>             
    <#list model as item>                 
 <div class="item"  style="background‐image: url(${item.value});"></div>
</#list>         
</#if>-->

</body>
</html>