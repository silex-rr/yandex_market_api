<#import "../parts/common.ftl" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <form method="post" action="/shop/add" modelAtribute="shopForm" class="form-add-shop">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <h2 class="mb-5">Create new Shop</h2>

        <@spring.bind 'shopForm.userOwner'/>
        <#if spring.status.error>
            <ul class="text-danger">
                <#list spring.status.errorMessages as error> <li>${error}</li> </#list>
            </ul>
        </#if>

        <@spring.bind 'shopForm.name'/>
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

        <@spring.bind 'shopForm.ymLogin'/>
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

        <@spring.bind 'shopForm.ymCompanyId'/>
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

        <@spring.bind 'shopForm.ymRegionId'/>
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


        <button class="btn btn-dark btn-primary btn-block col-sm-8 mt-5" type="submit">Create</button>

    </form>
</@c.page>