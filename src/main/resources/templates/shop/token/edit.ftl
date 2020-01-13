<#import "../../parts/common.ftl" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <form
        action="/shop/${shop.getId()}/token/edit/<#if token.getId()??>${token.getId()}<#else>new</#if>"
        method="post"
        modelAtribute="token"
        class="form-<#if token.getId()??>edit<#else >add</#if>-shop"
    >

    </form>
</@c.page>