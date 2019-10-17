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
        <div class="form-group<#if spring.status.error> has-error</#if>">
            <input type="text" name="${spring.status.expression}" value="${spring.status.value?html}"
                placeholder="Login"
            />
            <#if spring.status.error>
                <ul>
                    <#list spring.status.errorMessages as error> <li>${error}</li> </#list>
                </ul>
            </#if>
        </div>

        <@spring.bind 'userForm.name'/>
        <div class="form-group<#if spring.status.error> has-error</#if>">
            <input type="text" name="${spring.status.expression}" value="${spring.status.value?html}"
                   placeholder="Username"
            />
            <#if spring.status.error>
                <ul>
                    <#list spring.status.errorMessages as error> <li>${error}</li> </#list>
                </ul>
            </#if>
        </div>

        <@spring.bind 'userForm.email'/>
        <div class="form-group<#if spring.status.error> has-error</#if>">
            <input type="text" name="${spring.status.expression}" value="${spring.status.value?html}"
                placeholder="e-mail"
            />
            <#if spring.status.error>
                <ul>
                    <#list spring.status.errorMessages as error> <li>${error}</li> </#list>
                </ul>
            </#if>
        </div>

        <@spring.bind 'userForm.password'/>
        <div class="form-group<#if spring.status.error> has-error</#if>">
            <input type="password" name="${spring.status.expression}" value=""
                   placeholder="Password"
            />
            <#if spring.status.error>
                <ul>
                    <#list spring.status.errorMessages as error> <li>${error}</li> </#list>
                </ul>
            </#if>
        </div>

        <@spring.bind 'userForm.passwordConfirm'/>
        <div class="form-group<#if spring.status.error> has-error</#if>">
            <input type="password" name="${spring.status.expression}" value=""
                   placeholder="Confirm your password"
            />
            <#if spring.status.error>
                <ul>
                    <#list spring.status.errorMessages as error> <li>${error}</li> </#list>
                </ul>
            </#if>
        </div>

<#--        <spring:bind path="username">-->
<#--            <div class="form-group ${status.error ? 'has-error' : ''}">-->
<#--                <form:input type="text" path="username" class="form-control" placeholder="Username"-->
<#--                            autofocus="true"></form:input>-->
<#--                <form:errors path="username"></form:errors>-->
<#--            </div>-->
<#--        </spring:bind>-->


<#--        <div class="form-group">-->
<#--            <input type="text" name="name" class="form-control" placeholder="Username"-->
<#--                   value="${userForm.getName()}"-->
<#--            />-->
<#--            <errors path="name"></errors>-->
<#--        </div>-->

<#--        <div class="form-group">-->
<#--            <input type="password" name="password" class="form-control" placeholder="Password"-->
<#--            />-->
<#--            <errors path="password"></errors>-->
<#--        </div>-->

<#--        <div class="form-group">-->
<#--            <input type="password" name="passwordConfirm" class="form-control"-->
<#--                        placeholder="Confirm your password" />-->
<#--            <errors path="passwordConfirm"></errors>-->
<#--        </div>-->
        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form>


<#-- ${message}!
<@a.login "/registration" /> -->
</@c.page>