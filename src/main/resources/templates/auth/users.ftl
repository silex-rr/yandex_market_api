<#import "../parts/common.ftl" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <h2 class="mb-5">List of users</h2>
    <table class="table">
        <thead class="thead-dark">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Login</th>
                <th scope="col">Name</th>
                <th scope="col">eMail</th>
                <th scope="col">Roles</th>
            </tr>
        </thead>
        <tbody>
            <#list users as user>
                <tr>
                    <td>${user.getId()}</td>
                    <td>${user.getLogin()}</td>
                    <td>${user.getUsername()}</td>
                    <td>${user.getEmail()}</td>
                    <td>
                        <#list user.getRoles() as role>
                            ${role}
                        <#else>
                            &ndash;
                        </#list>
                    </td>
                </tr>
            <#else>
                <tr>
                    <td colspan="3">Something is going wrong</td>
                </tr>
            </#list>
        </tbody>
    </table>
</@c.page>