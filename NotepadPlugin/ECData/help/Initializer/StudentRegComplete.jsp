<%-- 
    Document   : login
    Created on : Oct 20, 2008, 9:08:04 PM
    Author     : Blue
--%>

<html>
  <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration Successful</title>
        <style type="text/css">
<!--
.style15 {font-family: Geneva, Arial, Helvetica, sans-serif; font-size: 14; }
.style17 {font-family: Geneva, Arial, Helvetica, sans-serif; font-size: 14px; }
.style18 {
	font-family: Geneva, Arial, Helvetica, sans-serif;
	font-weight: bold;
}
.style19 {font-family: Geneva, Arial, Helvetica, sans-serif}
-->
        </style>
  </head>
    <body>
        <jsp:include page="/staticPages/header.html" flush="true" />
        <table width="100%" border="0">
          <tr>
            <td align="center"><table width="800"  border="0" bgcolor="#5494EA">
                <tr>
                  <td><table width="100%"  border="0" bgcolor="#F8F8F8">
                      <tr>
                        <td><div align="center">
                            <p>Successfully Registered.</p>
                          <p>You can now login to the Extremity with your provided details. </p>
                            <br/>
                            <a href="/Extremity/login.jsp">Click here</a> to go to login page.</div></td>
                      </tr>
                  </table></td>
                </tr>
            </table></td>
          </tr>
        </table>
        <jsp:include page="/staticPages/footer.html" flush="true" />
    </body>
</html>