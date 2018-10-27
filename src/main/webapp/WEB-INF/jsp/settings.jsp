<%@ include file="header.jsp" %>
<div class="settings-frame">
    <h3 class="form-signin-heading">Settings</h3>
    <div class="shadow-sm p-3 mb-5 bg-white rounded">
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item">
                <a class="nav-link active" id="general-tab" href="#general" role="tab" aria-controls="general">General</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="others-tab" href="#others" role="tab" aria-controls="others">Others</a>
            </li>
        </ul>
        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade show active" id="general" role="tabpanel" aria-labelledby="general-tab">
                <form:form method="POST" action="${contextPath}/settings" modelAttribute="settings"
                           enctype="multipart/form-data">
                    <spring:bind path="image">
                        <div class="custom-file">
                            <input type="file" name="image" class="custom-file-input" id="image">
                            <label class="custom-file-label" for="image">Choose avatar</label>
                        </div>
                    </spring:bind>
                    <table class="table table-borderless">
                        <thead></thead>
                        <tbody>
                        <tr>
                            <td>Birthday visibility:</td>
                            <td>
                                <spring:bind path="birthdayVisibility">
                                    <div class="custom-control custom-radio custom-control-inline">
                                        <input type="radio" id="birthdayVisibilityTrue" name="birthdayVisibility"
                                               class="custom-control-input" value="true" checked>
                                        <label class="custom-control-label" for="birthdayVisibilityTrue">True</label>
                                    </div>
                                    <div class="custom-control custom-radio custom-control-inline">
                                        <input type="radio" id="birthdayVisibilityFalse" name="birthdayVisibility"
                                               class="custom-control-input" value="false">
                                        <label class="custom-control-label" for="birthdayVisibilityFalse">False</label>
                                    </div>
                                </spring:bind>
                            </td>

                        </tr>
                        <tr>
                            <td>Age visibility:</td>
                            <td>
                                <spring:bind path="ageVisibility">
                                    <div class="custom-control custom-radio custom-control-inline">
                                        <input type="radio" id="ageVisibilityTrue" name="ageVisibility"
                                               class="custom-control-input" value="true" checked>
                                        <label class="custom-control-label" for="ageVisibilityTrue">True</label>
                                    </div>
                                    <div class="custom-control custom-radio custom-control-inline">
                                        <input type="radio" id="ageVisibilityFalse" name="ageVisibility"
                                               class="custom-control-input" value="false">
                                        <label class="custom-control-label" for="ageVisibilityFalse">False</label>
                                    </div>
                                </spring:bind>
                            </td>
                        </tr>
                        <tr>
                            <td>Profile visibility:</td>
                            <td>
                                <spring:bind path="profileVisibility">
                                    <div class="custom-control custom-radio custom-control-inline">
                                        <input type="radio" id="profileVisibilityTrue" name="profileVisibility"
                                               class="custom-control-input" value="true">
                                        <label class="custom-control-label" for="profileVisibilityTrue">True</label>
                                    </div>
                                    <div class="custom-control custom-radio custom-control-inline">
                                        <input type="radio" id="profileVisibilityFalse" name="profileVisibility"
                                               class="custom-control-input" value="false" checked>
                                        <label class="custom-control-label" for="profileVisibilityFalse">False</label>
                                    </div>
                                </spring:bind>
                            </td>
                        </tr>
                        <tr>
                            <td>Location visibility:</td>
                            <td>
                                <spring:bind path="locationVisibility">
                                    <div class="custom-control custom-radio custom-control-inline">
                                        <input type="radio" id="locationVisibilityTrue" name="locationVisibility"
                                               class="custom-control-input" value="true" checked>
                                        <label class="custom-control-label" for="locationVisibilityTrue">True</label>
                                    </div>
                                    <div class="custom-control custom-radio custom-control-inline">
                                        <input type="radio" id="locationVisibilityFalse" name="locationVisibility"
                                               class="custom-control-input" value="false">
                                        <label class="custom-control-label" for="locationVisibilityFalse">False</label>
                                    </div>
                                </spring:bind>
                            </td>
                        </tr>
                        <tr>
                            <td>Gender visibility:</td>
                            <td>
                                <spring:bind path="genderVisibility">
                                    <div class="custom-control custom-radio custom-control-inline">
                                        <input type="radio" id="genderVisibilityTrue" name="genderVisibility"
                                               class="custom-control-input" value="true" checked>
                                        <label class="custom-control-label" for="genderVisibilityTrue">True</label>
                                    </div>
                                    <div class="custom-control custom-radio custom-control-inline">
                                        <input type="radio" id="genderVisibilityFalse" name="genderVisibility"
                                               class="custom-control-input" value="false">
                                        <label class="custom-control-label" for="genderVisibilityFalse">False</label>
                                    </div>
                                </spring:bind>
                            </td>
                        </tr>
                        <tr>
                            <td>Gifts visibility:</td>
                            <td><spring:bind path="giftsVisibility">
                                <div class="custom-control custom-radio custom-control-inline">
                                    <input type="radio" id="giftsVisibilityTrue" name="giftsVisibility"
                                           class="custom-control-input" value="true" checked>
                                    <label class="custom-control-label" for="giftsVisibilityTrue">True</label>
                                </div>
                                <div class="custom-control custom-radio custom-control-inline">
                                    <input type="radio" id="giftsVisibilityFalse" name="giftsVisibility"
                                           class="custom-control-input" value="false">
                                    <label class="custom-control-label" for="giftsVisibilityFalse">False</label>
                                </div>
                            </spring:bind></td>
                        </tr>
                        <tr>
                            <td>Friends visibility:</td>
                            <td>
                                <spring:bind path="friendsVisibility">
                                    <div class="custom-control custom-radio custom-control-inline">
                                        <input type="radio" id="friendsVisibilityTrue" name="friendsVisibility"
                                               class="custom-control-input" value="true" checked>
                                        <label class="custom-control-label" for="friendsVisibilityTrue">True</label>
                                    </div>
                                    <div class="custom-control custom-radio custom-control-inline">
                                        <input type="radio" id="friendsVisibilityFalse" name="friendsVisibility"
                                               class="custom-control-input" value="false">
                                        <label class="custom-control-label" for="friendsVisibilityFalse">False</label>
                                    </div>
                                </spring:bind>
                            </td>
                        </tr>
                        <tr>
                            <td>Group visibility:</td>
                            <td>
                                <spring:bind path="groupVisibility">
                                    <div class="custom-control custom-radio custom-control-inline">
                                        <input type="radio" id="groupVisibilityTrue" name="groupVisibility"
                                               class="custom-control-input" value="true" checked>
                                        <label class="custom-control-label" for="groupVisibilityTrue">True</label>
                                    </div>
                                    <div class="custom-control custom-radio custom-control-inline">
                                        <input type="radio" id="groupVisibilityFalse" name="groupVisibility"
                                               class="custom-control-input" value="false">
                                        <label class="custom-control-label" for="groupVisibilityFalse">False</label>
                                    </div>
                                </spring:bind></td>
                        </tr>
                        <tr>
                            <td>Non friends block:</td>
                            <td>
                                <spring:bind path="nonFriendsBlock">
                                    <div class="custom-control custom-radio custom-control-inline">
                                        <input type="radio" id="nonFriendsBlockTrue" name="nonFriendsBlock"
                                               class="custom-control-input" value="true">
                                        <label class="custom-control-label" for="nonFriendsBlockTrue">True</label>
                                    </div>
                                    <div class="custom-control custom-radio custom-control-inline">
                                        <input type="radio" id="nonFriendsBlockFalse" name="nonFriendsBlock"
                                               class="custom-control-input" value="false" checked>
                                        <label class="custom-control-label" for="nonFriendsBlockFalse">False</label>
                                    </div>
                                </spring:bind></td>
                        </tr>
                        </tbody>
                    </table>
                    <button class="btn btn-lg btn-primary" type="submit">Save</button>
                </form:form>
                <br/>
                <button type="button" class="btn btn-danger" id="delete-account-btn">Delete account</button>
            </div>
            <div class="tab-pane fade" id="others" role="tabpanel" aria-labelledby="others-tab">
                <form:form method="POST" action="/settings/update" modelAttribute="registrationDto" class="form-registration"
                           enctype="multipart/form-data">
                    <spring:bind path="username">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="username" class="form-control" placeholder="Username" required="required"
                                        autofocus="true"/>
                            <form:errors path="username"/>
                        </div>
                    </spring:bind>

                    <spring:bind path="password">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="password" path="password" class="form-control" placeholder="Password"
                                        required="required"/>
                            <form:errors path="password"/>
                        </div>
                    </spring:bind>

                    <spring:bind path="passwordConfirm">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="password" path="passwordConfirm" class="form-control" required="required"
                                        placeholder="Confirm your password"/>
                            <form:errors path="passwordConfirm"/>
                        </div>
                    </spring:bind>

                    <spring:bind path="birthDate">
                        <div class="form-group ${status.error ? 'has-error' : ''}">

                            <form:input type="date" path="birthDate" class="form-control" required="required"/>
                            <form:errors path="birthDate"/>
                        </div>
                    </spring:bind>

                    <spring:bind path="gender">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            Male:<form:radiobutton path="gender" value="0" class="radio-inline"/>
                            Female:<form:radiobutton path="gender" value="1" class="radio-inline"/>
                            <form:errors path="gender"/>
                        </div>
                    </spring:bind>

                    <spring:bind path="name">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="name" class="form-control" required="required"
                                        placeholder="Enter your name"/>
                            <form:errors path="name"/>
                        </div>
                    </spring:bind>

                    <spring:bind path="surname">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="surname" class="form-control" required="required"
                                        placeholder="Enter your surname"/>
                            <form:errors path="surname"/>
                        </div>
                    </spring:bind>

                    <spring:bind path="patronymic">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="patronymic" class="form-control"
                                        placeholder="Enter your patronymic"/>
                            <form:errors path="patronymic"/>
                        </div>
                    </spring:bind>

                    <spring:bind path="location">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="location" class="form-control"
                                        placeholder="Enter your location"/>
                            <form:errors path="location"/>
                        </div>
                    </spring:bind>
                    <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
                </form:form>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
<script>
    var baseUrl = window.location.origin;

    $('#myTab a').on('click', function (e) {
        e.preventDefault();
        $(this).tab('show');
    });

    $("#delete-account-btn").on("click", function () {
        var isSure = confirm("Are you sure?");
        if (isSure) {
            $.post(baseUrl + "/delete");
            window.location.href = baseUrl + "/login";
        }
    });
</script>
</body>
</html>
