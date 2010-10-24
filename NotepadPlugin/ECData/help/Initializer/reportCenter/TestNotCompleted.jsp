<%-- 
    Document   : login
    Created on : Oct 20, 2008, 9:08:04 PM
    Author     : Blue
--%>

<html>
  <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test Not Completed</title>
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
            <td align="center"><table width="800"  border="0" bgcolor="#CC0000">
                <tr>
                  <td><table width="100%"  border="0" bgcolor="#F8F8F8">
                      <tr>
                        <td><div align="center">
                            <p>You cannot view your report for selected test.</p>
                          <p><b>Reason:</b> Your test has not yet completed. First complete it.</p>
                            <br/>
                            <a href="/Extremity/reportCenter/ReportCenter.jsp">Click here</a> to go to Report Center.</div></td>
                      </tr>
                  </table></td>
                </tr>
            </table></td>
          </tr>
        </table>
        <jsp:include page="/staticPages/footer.html" flush="true" />
    </body>
</html>