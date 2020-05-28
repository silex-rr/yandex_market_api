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

        <h2 class="mb-5"><#if shop.getId()??>Edit Shop "${shop.getName()}"<#else >Add new shop</#if></h2>

        <@spring.bind 'shop.userOwners'/>
        <#if spring.status.error>
            <ul class="text-danger">
                <#list spring.status.errorMessages as error> <li>${error}</li> </#list>
            </ul>
        </#if>

        <@spring.bind 'shop.name'/>
        <div class="form-group row <#if spring.status.error>has-error</#if>">
            <label class="col-sm-2 col-form-name" for="shop.name">Name:</label>
            <div class="col-sm-6">
                <input id="shop.name" type="text" name="${spring.status.expression}" value="${spring.status.value?html}"
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
            <label class="col-sm-2 col-form-name" for="shop.ymLogin">Yandex Market Login:</label>
            <div class="col-sm-6">
                <input id="shop.ymLogin" type="text" name="${spring.status.expression}" value="${spring.status.value?html}"
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
            <label class="col-sm-2 col-form-name" for="shop.ymCompanyId">YM Company ID:</label>
            <div class="col-sm-6">
                <input id="shop.ymCompanyId" type="number" name="${spring.status.expression}"
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
            <label class="col-sm-2 col-form-name" for="shop.ymRegionId">YM Region ID:</label>
            <div class="col-sm-6">
                <input id="shop.ymRegionId" type="number" name="${spring.status.expression}"
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

        <@spring.bind 'shop.requestsPerSecond'/>
        <div class="form-group row <#if spring.status.error>has-error</#if>">
            <label class="col-sm-2 col-form-name" for="shop.requestsPerSecond">YM Region ID:</label>
            <div class="col-sm-6">
                <input id="shop.requestsPerSecond" type="number" step="0.01" name="${spring.status.expression}"
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

        <@spring.bind 'shop.enable'/>
        <div class="form-group row <#if spring.status.error>has-error</#if>">
            <label class="col-sm-2 col-form-name" for="shop.enable">Enable:</label>
            <div class="col-sm-6">
                <input id="shop.enable" type="checkbox" name="${spring.status.expression}"
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
            <#if shop.getId()??>
                Update
            <#else >
                Add
            </#if>
        </button>

    </form>
</@c.page>