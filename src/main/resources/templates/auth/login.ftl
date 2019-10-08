<#import "../parts/common.ftl" as c>
<#import "../parts/auth.ftl" as a>

<@c.page>
    Login page
    <@a.login "/auth/login" />
    <a href="/auth/registration">Add new user</a>
</@c.page>