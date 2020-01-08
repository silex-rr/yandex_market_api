<#import "../parts/common.ftl" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <form
            method="post"
            action="/shop/edit/<#if shop.getId()??>${shop.getId()}<#else>new</#if>"
            modelAtribute="shop"
            class="form-<#if shop.getId()??>edit<#else >add</#if>-shop"
    >
        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <#if shop.getId()??>
            <h2 class="mb-5">Edit Shop "${shop.getName()}"</h2>
        <#else >
            <h2 class="mb-5">Create new Shop</h2>
        </#if>

        <@spring.bind 'shop.userOwner'/>
        <#if spring.status.error>
            <ul class="text-danger">
                <#list spring.status.errorMessages as error> <li>${error}</li> </#list>
            </ul>
        </#if>

        <@spring.bind 'shop.name'/>
        <div class="form-group row <#if spring.status.error>has-error</#if>">
            <label class="col-sm-2 col-form-name">Name:</label>
            <div class="col-sm-6">
                <input type="text" name="${spring.status.expression}" value="${spring.status.value?html}"
                       class="form-control"
                       placeholder="name"
                />
                <#if spring.status.error>
                    <ul class="text-danger">
                        <#list spring.status.errorMessages as error> <li>${error}</li> </#list>
                    </ul>
                </#if>
            </div>
        </div>

        <@spring.bind 'shop.ymLogin'/>
        <div class="form-group row <#if spring.status.error>has-error</#if>">
            <label class="col-sm-2 col-form-name">Yandex Market Login:</label>
            <div class="col-sm-6">
                <input type="text" name="${spring.status.expression}" value="${spring.status.value?html}"
                       class="form-control"
                       placeholder="YM Login"
                />
                <#if spring.status.error>
                    <ul class="text-danger">
                        <#list spring.status.errorMessages as error> <li>${error}</li> </#list>
                    </ul>
                </#if>
            </div>
        </div>

        <@spring.bind 'shop.ymCompanyId'/>
        <div class="form-group row <#if spring.status.error>has-error</#if>">
            <label class="col-sm-2 col-form-name">YM Company ID:</label>
            <div class="col-sm-6">
                <input type="number" name="${spring.status.expression}"
                       value="${spring.status.value?html}"
                       class="form-control"
                       placeholder="Yandex Market Company ID"
                />
                <#if spring.status.error>
                    <ul class="text-danger">
                        <#list spring.status.errorMessages as error> <li>${error}</li> </#list>
                    </ul>
                </#if>
            </div>
        </div>

        <@spring.bind 'shop.ymRegionId'/>
        <div class="form-group row <#if spring.status.error>has-error</#if>">
            <label class="col-sm-2 col-form-name">YM Region ID:</label>
            <div class="col-sm-6">
                <input type="number" name="${spring.status.expression}"
                       value="${spring.status.value?html}"
                       class="form-control"
                       placeholder="Yandex Market Region ID"
                />
                <#if spring.status.error>
                    <ul class="text-danger">
                        <#list spring.status.errorMessages as error> <li>${error}</li> </#list>
                    </ul>
                </#if>
            </div>
        </div>


        <button class="btn btn-dark btn-primary btn-block col-sm-8 mt-5" type="submit">
            <#if shop.getId()??>
                Update
            <#else >
                Create
            </#if>
        </button>

    </form>
</@c.page>