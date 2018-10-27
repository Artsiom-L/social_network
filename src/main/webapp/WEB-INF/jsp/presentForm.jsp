<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="modal fade" id="modal-present" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalLabel">Send present</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form method="POST" action="${contextPath}/present/send/${recipient}" modelAttribute="present"
                           class="form-horizontal" enctype="multipart/form-data" id="present-form">
                    <spring:bind path="signature">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:textarea path="signature" class="form-control"
                                           placeholder="Write a message..."/>
                            <form:errors path="signature"/>
                        </div>
                    </spring:bind>

                    <spring:bind path="name">
                        <div class="custom-control-inline">
                            <label>
                                <input type="radio" name="name" value="first" checked/>
                                <img class="present-image"
                                     src="${contextPath}/present/image/first">
                            </label>
                            <label>
                                <input type="radio" name="name" value="second"/>
                                <img class="present-image"
                                     src="${contextPath}/present/image/second">
                            </label>
                            <label>
                                <input type="radio" name="name" value="third"/>
                                <img class="present-image"
                                     src="${contextPath}/present/image/third">
                            </label>
                        </div>
                    </spring:bind>
                    <!--<button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>-->
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="button-submit" onclick="sendPresentForm(${recipient});">
                    Submit
                </button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>