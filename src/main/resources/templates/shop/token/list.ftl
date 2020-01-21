<#import "../../parts/common.ftl" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <h2 class="mb-5">List of Tokens for shop "<a href="/shop/edit/${shop.getId()}">${shop.getName()}"</a></h2>
    <table class="table">
        <thead class="thead-dark">
            <th scope="col">Name</th>
            <th scope="col">Enable</th>
            <th scope="col">Oauth Token</th>
            <th scope="col">Oauth client ID</th>
            <th scope="col">Expire to</th>
            <th scope="col">Type</th>
            <th scope="col">Actions</th>
        </thead>
        <tbody>
            <#list shop.getTokens() as token>
                <tr>
                    <td>${token.getName()}</td>
                    <td>
                        <#if token.isEnable()>
                            Yes
                        <#else>
                            No
                        </#if>
                    </td>
                    <td>${token.getOauthToken()}</td>
                    <td>${token.getOauthClientId()}</td>
                    <td>${token.getExpireTo()}</td>
                    <td>${token.getType()}</td>
                    <td>${token}</td>
                    <td>${token}</td>
                    <td>
                        <a href="/shop/${shop.getId()}/token/${token.getId()}" class="btn d-inline p-2 text-primary">
                            <i class="fas fa-pen"></i>
                        </a>
                        <form method="post" action="/shop/${shop.getId()}/token/list" class="d-inline p-2">
                            <input type="hidden" name="delete" value="${token.getId()}" />
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                            <button type="submit" onclick="return confirm('Are you sure?')" class="btn text-danger"> <i class="fas fa-trash"></i></button>
<#--                            <button onclick="return confirm('Are you sure?')" class="btn btn-danger" title="Delete this shop">del</button>-->
                        </form>
                    </td>
                </tr>
            <#else>
                <tr>
                    <td colspan="7">
                        You don't have any tokens for this shop yet
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>
    <a class="btn btn-dark" href="/shop/${shop.getId()}/token/edit/new">Add new</a>
</@c.page>