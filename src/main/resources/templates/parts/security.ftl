<#assign
    know = Session.SPRING_SECURITY_CONTEXT??
>

<#if know>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        userId = user.getId()
        userName = user.getName()
        isAdmin = user.isAdmin()
    >

<#--    ${user.getName()} /  ${user.getLogin()} / ${user.getUid()}-->
<#else>
    <#assign
        userId = 0
        userName = "guest"
        isAdmin = false
    >
</#if>