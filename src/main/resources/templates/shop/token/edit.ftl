<#import "../../parts/common.ftl" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <form
        action="/shop/${shop.getId()}/token/edit/<#if token.getId()??>${token.getId()}<#else>new</#if>"
        method="post"
        modelAtribute="token"
        class="form-<#if token.getId()??>edit<#else >add</#if>-shop"
    >
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <h2>
            <#if token.getId()??>
                Edit Token "${token.getName()}"
            <#else >
                Add new Token
            </#if>
            for Shop "${shop.getName()}"
        </h2>

        <@spring.bind 'token.shop' />
        <#if spring.status.error >
            <ul>
                <#list spring.status.errorMessages as error><li>${error}</li></#list>
            </ul>
        </#if>

        <@spring.bind 'token.name' />
        <div class="form-group row <#if spring.status.error>has-error</#if>">
            <label class="col-sm-2 col-form-label">Name</label>
            <div class="col-sm-6">
                <input type="text"
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
            <label class="col-sm-2 col-form-label">Oauth Token</label>
            <div class="col-sm-6">
                <input type="text"
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
            <label class="col-sm-2 col-form-label">Oauth Client Id</label>
            <div class="col-sm-6">
                <input type="text"
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

        <@spring.bind 'token.expireTo' />
        <div class="form-group row <#if spring.status.error>has-error</#if>">
            <label class="col-sm-2 col-form-label">Expire to</label>
            <div class="col-sm-6">
                <input type="date"
                       dataformatas="yyyy-MM-dd"
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

        <button class="btn btn-dark btn-primary btn-block col-sm-8 mt-5" type="submit">
            <#if token.getId()??>
                Update
            <#else >
                Add
            </#if>
        </button>
    </form>
</@c.page>