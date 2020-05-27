<#import "../../parts/common.ftl" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <h2 class="mb-5">List of YM Tokens for shop "<a href="/shop/edit/${shop.getId()}">${shop.getName()}"</a></h2>
    <table class="table">
        <thead class="thead-dark">
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Enable</th>
                <th scope="col">Oauth Token</th>
                <th scope="col">Oauth client ID</th>
                <th scope="col">Expire to</th>
                <th scope="col">Type</th>
                <th scope="col">Actions</th>
            </tr>
        </thead>
        <tbody>
            <#list shop.getYMTokens() as YMToken>
                <tr>
                    <td>${YMToken.getName()}</td>
                    <td>
                        <#if YMToken.isEnable()>
                            Yes
                        <#else>
                            No
                        </#if>
                    </td>
                    <td>${YMToken.getOauthToken()}</td>
                    <td>${YMToken.getOauthClientId()}</td>
                    <td>${YMToken.getExpireTo()?string("yyyy-MM-dd")}</td>
                    <td>${YMToken.getType()}</td>
                    <td>
                        <a href="/shop/${shop.getId()}/YMToken/edit/${YMToken.getId()}" class="btn d-inline p-2 text-primary">
                            <i class="fas fa-pen"></i>
                        </a>
                        <form method="post" action="/shop/${shop.getId()}/YMToken/list" class="d-inline p-2">
                            <input type="hidden" name="delete" value="${YMToken.getId()}" />
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                            <button type="submit" onclick="return confirm('Are you sure?')" class="btn text-danger"> <i class="fas fa-trash"></i></button>
                        </form>
                    </td>
                </tr>
            <#else>
                <tr>
                    <td colspan="7">
                        You don't have any YandexMarket Tokens for this shop yet
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>
    <a class="btn btn-dark" href="/shop/${shop.getId()}/YMToken/edit/new">Add new</a>
</@c.page>