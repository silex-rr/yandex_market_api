<#import "parts/common.ftl" as c>
<#import "parts/auth.ftl" as a>

<@c.page>
    Login page
    <@a.login "/login" />
    <a href="/registration">Add new user</a>
</@c.page>