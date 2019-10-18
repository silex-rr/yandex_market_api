<#include "security.ftl">
<#import "auth.ftl" as a>

<nav class="navbar navbar-expand-lg navbar-light bg-light mb-5">
    <a class="navbar-brand" href="/">YM balancer</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <#if know>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/">Home</a>
                </li>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link" href="/auth/users">User list</a>
                    </li>
                </#if>
            </ul>

            <div class="navbar-text">${userName}</div>
            <div class="navbar-text ml-3"><@a.logout /></div>
        </div>
    </#if>
</nav>