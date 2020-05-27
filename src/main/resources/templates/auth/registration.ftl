<#import "../parts/common.ftl" as c>
<#import "../parts/auth.ftl" as a>
<#import "/spring.ftl" as spring/>
<@c.page>
    <form method="POST" action="/auth/registration" modelAttribute="userForm" class="form-signin">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <h2 class="form-signin-heading mb-5">Create your account</h2>

        <@spring.bind 'userForm.login'/>
        <div class="form-group row <#if spring.status.error>has-error</#if>">
            <label class="col-sm-2 col-form-label">Login:</label>
            <div class="col-sm-6">
                <input type="text" name="${spring.status.expression}" value="${spring.status.value?html}"
                       class="form-control"
                       placeholder="login"
                       autofocus="autofocus"
                />
                <#if spring.status.error>
                    <ul class="text-danger">
                        <#list spring.status.errorMessages as error> <li>${error}</li> </#list>
                    </ul>
                </#if>
            </div>
        </div>

        <@spring.bind 'userForm.name'/>
        <div class="form-group row <#if spring.status.error> has-error</#if>">
            <label class="col-sm-2 col-form-label">User Name:</label>
            <div class="col-sm-6">
                <input type="text" name="${spring.status.expression}" value="${spring.status.value?html}"
                       class="form-control"
                       placeholder="user name"
                />
                <#if spring.status.error>
                    <ul class="text-danger">
                        <#list spring.status.errorMessages as error> <li>${error}</li> </#list>
                    </ul>
                </#if>
            </div>
        </div>

        <@spring.bind 'userForm.email'/>
        <div class="form-group row <#if spring.status.error> has-error</#if>">
            <label class="col-sm-2 col-form-label">E-mail:</label>
            <div class="col-sm-6">
                <input type="text" name="${spring.status.expression}" value="${spring.status.value?html}"
                    class="form-control"
                    placeholder="e-mail"
                />
                <#if spring.status.error>
                    <ul class="text-danger">
                        <#list spring.status.errorMessages as error> <li>${error}</li> </#list>
                    </ul>
                </#if>
            </div>
        </div>

        <@spring.bind 'userForm.password'/>
        <div class="form-group row <#if spring.status.error> has-error</#if>">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-6">
                <input type="password" name="${spring.status.expression}" value=""
                       class="form-control"
                       placeholder="Password"
                />
                <#if spring.status.error>
                    <ul class="text-danger">
                        <#list spring.status.errorMessages as error> <li>${error}</li> </#list>
                    </ul>
                </#if>
            </div>
        </div>

        <@spring.bind 'userForm.passwordConfirm'/>
        <div class="form-group row <#if spring.status.error> has-error</#if>">
            <label class="col-sm-2 col-form-label">Password confirm:</label>
            <div class="col-sm-6">
                <input type="password" name="${spring.status.expression}" value=""
                       class="form-control"
                       placeholder="Confirm your password"
                />
                <#if spring.status.error>
                    <ul class="text-danger">
                        <#list spring.status.errorMessages as error> <li>${error}</li> </#list>
                    </ul>
                </#if>
            </div>
        </div>

        <button class="btn btn-dark btn-primary btn-block col-sm-8 mt-5" type="submit">Submit</button>
    </form>


<#-- ${message}!
<@a.login "/registration" /> -->
</@c.page>