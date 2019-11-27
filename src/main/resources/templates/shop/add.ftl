<#import "../parts/common.ftl" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <form method="post" action="/shop/add" modelAtribute="shopForm" class="form-add-shop">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <h2 class="md-5">Create new Shop</h2>

        <@spring.bind 'shop.name'/>
<#--        <@spring.formInput "shop.name"/>-->
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

    </form>
</@c.page>