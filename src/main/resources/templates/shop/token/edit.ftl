<#import "../../parts/common.ftl" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <form
        action="/shop/${shop.getId()}/token/edit/<#if token.getId()??>${token.getId()}<#else>new</#if>"
        method="post"
        modelAtribute="token"
        class="form-<#if isNew??>add<#else >edit</#if>-shop"
    >
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <h2>
            <#if isNew??>
                Add new Token
            <#else >
                Edit Token "${token.getName()}"
            </#if>
            for Shop "${shop.getName()}"
        </h2>

        <@spring.bind 'token.name' />
        <div class="form-group row <#if spring.status.error>has-error</#if>">
            <label class="col-sm-2 col-form-label" for="token.name">Name</label>
            <div class="col-sm-6">
                <input id="token.name" type="text"
                    name="${spring.status.expression}"
                    value="${spring.status.value}"
                    class="form-control"
                    placeholder="Token name"
                />
                <#if spring.status.error>
                    <ul class="text-danger">
                        <#list spring.status.error as error><li>${error}</li></#list>
                    </ul>
                </#if>
            </div>
        </div>

        <@spring.bind 'token.oauthToken' />
        <div class="form-group row <#if spring.status.error>has-error</#if>">
            <label class="col-sm-2 col-form-label" for="token.oauthToken">Oauth Token</label>
            <div class="col-sm-6">
                <input id="token.oauthToken" type="text"
                       name="${spring.status.expression}"
                       value="${spring.status.value}"
                       class="form-control"
                       placeholder="oauth Token"
                />
                <#if spring.status.error>
                    <ul class="text-danger">
                        <#list spring.status.error as error><li>${error}</li></#list>
                    </ul>
                </#if>
            </div>
        </div>

        <@spring.bind 'token.oauthClientId' />
        <div class="form-group row <#if spring.status.error>has-error</#if>">
            <label class="col-sm-2 col-form-label" for="token.oauthClientId">Oauth Client Id</label>
            <div class="col-sm-6">
                <input id="token.oauthClientId" type="text"
                       name="${spring.status.expression}"
                       value="${spring.status.value}"
                       class="form-control"
                       placeholder="oauth Client Id"
                />
                <#if spring.status.error>
                    <ul class="text-danger">
                        <#list spring.status.error as error><li>${error}</li></#list>
                    </ul>
                </#if>
            </div>
        </div>

        <@spring.bind 'token.password' />
        <div class="form-group row <#if spring.status.error>has-error</#if>">
            <label class="col-sm-2 col-form-label" for="token.password">Password</label>
            <div class="col-sm-6">
                <input id="token.password" type="password"
                       name="${spring.status.expression}"
                       value="${spring.status.value}"
                       class="form-control"
                       placeholder="password"
                />
                <#if spring.status.error>
                    <ul class="text-danger">
                        <#list spring.status.error as error><li>${error}</li></#list>
                    </ul>
                </#if>
            </div>
        </div>

        <@spring.bind 'token.expireTo' />
        <div class="form-group row <#if spring.status.error>has-error</#if>">
            <label class="col-sm-2 col-form-label" for="token.expireTo">Expire to</label>
            <div class="col-sm-6">
                <input id="token.expireTo" type="date"
                       placeholder="YYYY-MM-DD"
                       name="${spring.status.expression}"
                       value="${spring.status.value}"
                       class="form-control"
                />
                <#if spring.status.error>
                    <ul class="text-danger">
                        <#list spring.status.error as error><li>${error}</li></#list>
                    </ul>
                </#if>
            </div>
        </div>

        <@spring.bind 'token.enable'/>
        <div class="form-group row <#if spring.status.error>has-error</#if>">
            <label class="col-sm-2 col-form-name" for="token.enable">Enable:</label>
            <div class="col-sm-6">
                <input id="token.enable" type="checkbox" name="${spring.status.expression}"
                       <#if spring.status.value?? && spring.status.value?string=="true">checked="true"</#if>
                       class="form-control"
                />
                <#if spring.status.error>
                    <ul class="text-danger">
                        <#list spring.status.errorMessages as error> <li>${error}</li> </#list>
                    </ul>
                </#if>
            </div>
        </div>

        <button class="btn btn-dark btn-primary btn-block col-sm-8 mt-5" type="submit">
            <#if isNew??>
                Add
            <#else >
                Update
            </#if>
        </button>
    </form>
</@c.page>