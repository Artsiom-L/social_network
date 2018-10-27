<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalLabel">Create group</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form:form method="POST" action="${contextPath}/group/create" modelAttribute="groupDto"
                           class="form-horizontal" enctype="multipart/form-data" id="group-form">
                    <spring:bind path="name">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:textarea path="name" class="form-control"
                                           placeholder="Write a name..."/>
                            <form:errors path="name"/>
                        </div>
                    </spring:bind>

                    <spring:bind path="groupPhoto">
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="inputGroupFilePrepend">Upload</span>
                            </div>
                            <div class="custom-file">
                                <input type="file" class="custom-file-input" id="inputFile" name="groupPhoto"
                                       aria-describedby="inputGroupFilePrepend">
                                <label class="custom-file-label" for="inputFile">Choose avatar</label>
                            </div>
                        </div>
                    </spring:bind>
                    <!--<button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>-->
                </form:form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="button-submit" onclick="createGroup();">
                    Submit</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>