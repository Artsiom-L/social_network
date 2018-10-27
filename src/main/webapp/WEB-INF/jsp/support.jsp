<%@ include file="header.jsp" %>
<h4>Api examples</h4>
<h6>Authorization type: Basic Auth</h6>
<h5>User api:</h5>
<ul>
    <li>http://localhost/api/user/{id}</li>
    <li>http://localhost/api/user/{id}?add=parameter0;parameter1;...</li>
    <p>Available parameters (They aren't required): friends, groups, dialogues, settings, posts, files, presents</p>
    <li>http://localhost/api/user/1?add=friends;groups;dialogues;settings;posts;files;presents</li>
</ul>
<h5>Group api:</h5>
<ul>
    <li>http://localhost/api/group/{id}</li>
    <li>http://localhost/api/group/{id}?add=parameter0;parameter1..</li>
    <p>Available parameters (They aren't required): subscribers</p>
    <li>http://localhost/api/group/1?add=subscribers</li>
</ul>
<h5>Users api:</h5>
<ul>
    <li>http://localhost/api/users</li>
    <li>http://localhost/api/users?parameter0=value& parameter1=value</li>
    <p>Available parameters (They aren't required): age, location</p>
    <li>http://localhost/api/users?location=there&age=4</li>
</ul>
<%@ include file="footer.jsp" %>
</body>
</html>