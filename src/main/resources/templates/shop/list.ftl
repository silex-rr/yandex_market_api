<#import "../parts/common.ftl" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <h2 class="mb-5">List of stores</h2>
    <table class="table">
        <thead class="thead-dark">
            <th scope="col">Name</th>
            <th scope="col">Enable</th>
            <th scope="col">YM Company ID</th>
            <th scope="col">YM Region ID</th>
            <th scope="col">YM Tokens</th>
            <th scope="col">Actions</th>
        </thead>
        <tbody>
            <#list shops as shop>
                <tr>
                    <td>${shop.getName()}</td>
                    <td>
                        <#if shop.isEnable()>
                            Yes
                        <#else>
                            No
                        </#if>
                    </td>
                    <td>${shop.getYmCompanyId()}</td>
                    <td>${shop.getYmRegionId()}</td>
                    <td>

                        <a href="/shop/${shop.getId()}/YMToken/list">
                            <#if shop.getYMTokens()??>
                                ${shop.getYMTokens()?size}
                            <#else >
                                0
                            </#if>
                        </a>
                    </td>
                    <td>
                        <a href="/shop/edit/${shop.getId()}" class="btn d-inline p-2 text-primary">
                            <i class="fas fa-pen"></i>
                        </a>
                        <form method="post" action="/shop/list" class="d-inline p-2">
                            <input type="hidden" name="delete" value="${shop.getId()}" />
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                            <button type="submit" onclick="return confirm('Are you sure?')" class="btn text-danger"> <i class="fas fa-trash"></i></button>
<#--                            <button onclick="return confirm('Are you sure?')" class="btn btn-danger" title="Delete this shop">del</button>-->
                        </form>
                    </td>
                </tr>
            <#else>
                <tr>
                    <td colspan="4">
                        You don't have any shops yet
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>
    <a class="btn btn-dark" href="/shop/edit/new">Add new</a>
</@c.page>