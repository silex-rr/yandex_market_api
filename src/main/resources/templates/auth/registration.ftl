<#import "../parts/common.ftl" as c>
<#import "../parts/auth.ftl" as a>
<#import "/spring.ftl" as spring/>
<@c.page>

<#--    <#list .data_model?keys as key>-->
<#--        <div>${key}</div>-->
<#--    </#list>-->
<#--${}-->

    <form method="POST" action="/auth/registration" modelAttribute="userForm" class="form-signin">
        <h2 class="form-signin-heading">Create your account</h2>

<#--        <div class="form-group">-->
<#--            <input type="text" name="login" class="form-control" placeholder="login"-->
<#--                   value="${userForm.getLogin()}"-->
<#--                   autofocus="true" />-->
<#--            <errors path="login"></errors>-->
<#--        </div>-->

        <@spring.bind 'userForm.login'/>
        Username: <input type="text" name="${spring.status.expression}" value="${spring.status.value?html}"/>
        <#if spring.status.error>
            <#list spring.status.errorMessages as error> <b>${error}</b> <br> </#list>

            <br/>
            <br/>
            <br/>

<#--            <#list spring.status.errors?keys as prop>-->
<#--                ${prop} = ${user.get(prop)}-->
<#--            </#list>-->
<#--            <#list spring.status.getErrorMessages() as error>-->
<#--                <span class="error">${error}</span>-->
<#--                <br>-->
<#--            </#list>-->
        </#if>


<#--        <spring:bind path="username">-->
<#--            <div class="form-group ${status.error ? 'has-error' : ''}">-->
<#--                <form:input type="text" path="username" class="form-control" placeholder="Username"-->
<#--                            autofocus="true"></form:input>-->
<#--                <form:errors path="username"></form:errors>-->
<#--            </div>-->
<#--        </spring:bind>-->


        <div class="form-group">
            <input type="text" name="name" class="form-control" placeholder="Username"
                   value="${userForm.getName()}"
            />
            <errors path="name"></errors>
        </div>

        <div class="form-group">
            <input type="password" name="password" class="form-control" placeholder="Password"
            />
            <errors path="password"></errors>
        </div>

        <div class="form-group">
            <input type="password" name="passwordConfirm" class="form-control"
                        placeholder="Confirm your password" />
            <errors path="passwordConfirm"></errors>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form>


<#-- ${message}!
<@a.login "/registration" /> -->
</@c.page>