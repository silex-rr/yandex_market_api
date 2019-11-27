<#import "../parts/common.ftl" as c>

<@c.page>
    <h2 class="mb-5">Your shop list</h2>
    <table class="table">
        <thead class="thead-dark">
            <th scope="col">Name</th>
            <th scope="col">Enable</th>
            <th scope="col">YM Company ID</th>
            <th scope="col">YM Region ID</th>
        </thead>
        <tbody>
            <#list shops as shop>
                <tr>
                    <td>
                        <a href="#">${shop.getName()}</a>
                    </td>
                    <td>
                        <#if shop.isEnable()>
                            Yes
                        <#else>
                            No
                        </#if>
                    </td>
                    <td>${shop.getYmCompanyId()}</td>
                    <td>${shop.getYmRegionId()}</td>
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
    <a class="btn btn-dark" href="/shop/add">Add new</a>
</@c.page>