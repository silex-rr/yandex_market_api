<#import "parts/common.ftl" as c>
<#import "parts/auth.ftl" as a>
<@c.page>
    <div>
       ${authUser.getName()} <@a.logout />
    </div>
</@c.page>