<#import "parts/common.ftl" as c>
<#import "parts/auth.ftl" as a>

<@c.page>

<#--    <#list .data_model?keys as key>-->
<#--        <div>${key}</div>-->
<#--    </#list>-->

    <form method="POST" action="/registration" modelAttribute="userForm" class="form-signin">
        <h2 class="form-signin-heading">Create your account</h2>

        <div class="form-group">
            <input type="text" path="username" class="form-control" placeholder="Username"
                        autofocus="true" />
            <errors path="username"></errors>
        </div>

        <div class="form-group">
            <input type="password" path="password" class="form-control" placeholder="Password" />
            <errors path="password"></errors>
        </div>

        <div class="form-group">
            <input type="password" path="passwordConfirm" class="form-control"
                        placeholder="Confirm your password" />
            <errors path="passwordConfirm"></errors>
        </div>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form>


<#-- ${message}!
<@a.login "/registration" /> -->
</@c.page>