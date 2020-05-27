<#import "../../parts/common.ftl" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <h2 class="mb-5">List of yours API Tokens</h2>
    <table class="table" style="
        table-layout: fixed;
        word-wrap: break-word;
    "
    >
        <thead class="thead-dark">
            <tr>
                <th scope="col">Enable</th>
                <th scope="col">Expire to</th>
                <th scope="col" class="w-75">Token</th>
                <th scope="col">Delete</th>
            </tr>
        </thead>
        <tbody>
            <#list tokens as token>
                <tr>
                    <td>
                        <form method="post" action="/api/token/list" class="d-inline p-2">
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                            <input type="hidden" name="token" value="${token.getId()}" />

                            <#if token.isEnable()>
                                <input type="hidden" name="action" value="disable" />

                                <button type="submit" class="btn text-success">
                                    <i class="fas fa-2x fa-toggle-on"></i>
                                </button>
                            <#else>
                                <input type="hidden" name="action" value="enable" />

                                <button type="submit" class="btn">
                                    <i class="fas fa-2x fa-toggle-off"></i>
                                </button>
                            </#if>
                        </form>
                    </td>
                    <td nowrap="nowrap">${token.getExpireTo()?string("yyyy-MM-dd")}</td>
                    <td>${token.getToken()}</td>
                    <td>
                        <form method="post" action="/api/token/list" class="d-inline p-2">
                            <input type="hidden" name="token" value="${token.getId()}" />
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                            <input type="hidden" name="action" value="delete" />
                            <button type="submit" onclick="return confirm('Are you sure?')" class="btn text-danger fa-2x">
                                <i class="fas fa-trash"></i>
                            </button>
                        </form>
                    </td>
                </tr>
            <#else>
                <tr>
                    <td colspan="3">
                        You don't have any API Tokens for this shop yet
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>
    <form method="post" action="/api/token/list" class="d-inline p-2">
        <input type="hidden" name="action" value="addNew" />
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-dark">Add new</button>
        <#--                            <button onclick="return confirm('Are you sure?')" class="btn btn-danger" title="Delete this shop">del</button>-->
    </form>


</@c.page>