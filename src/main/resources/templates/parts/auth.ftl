<#macro login path>
    <form action="${path}" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">User Name:</label>
            <div class="col-sm-6">
                <input class="form-control" type="text" name="username"
                    placeholder="user name"
                />
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-6">
                <input class="form-control" type="password" name="password"
                    placeholder="password"
                />
            </div>
        </div>
        <div class="form-group row">
            <div class="col-sm-2">
                <button type="submit" class="btn btn-dark btn-primary">Sign In</button>
            </div>
            <div class="col-sm-6 text-right">
                <a href="/auth/registration">Add new user</a>
            </div>
        </div>
    </form>
</#macro>

<#macro logout>
    <form action="/auth/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-dark">Sign Out</button>
<#--        <input type="submit" value="Sign Out" />-->
    </form>
</#macro>