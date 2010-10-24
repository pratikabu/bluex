<%-- 
    Document   : login
    Created on : Oct 20, 2008, 9:08:04 PM
    Author     : Blue
--%>

<html>
  <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test Password</title>
        <style type="text/css">
<!--
.style19 {
	font-family: Geneva, Arial, Helvetica, sans-serif;
	font-size: 14px;
}
.style20 {
	color: #CC0000;
	font-family: Geneva, Arial, Helvetica, sans-serif;
	font-size: 14px;
}
-->
        </style>
  </head>
    <body onLoad="document.form1.txtPassword.focus()">
        <jsp:include page="/staticPages/header.html" flush="true" />
        <%
        com.gmail.pratikabu.extremity.components.GenericTasksStripGenerator gtsp=
                    new com.gmail.pratikabu.extremity.components.GenericTasksStripGenerator();
            //next line is for the generic task strip
        %>
        <%= gtsp.getGeneratedTasksStrip(1) %>
        <%
            long tid=-1;
            try{
                tid=Long.parseLong(request.getParameter("tid"));
                request.getSession(false).setAttribute("tid", tid);//set the test id to the session
            }catch(NumberFormatException nfe){
                tid=-1;
            }

            Object error=request.getParameter("error");
        %>
        <table width="100%" border="0">
          <tr>
            <td align="center"><table width="800"  border="0" bgcolor="#5494EA">
                <tr>
                  <td><table width="100%"  border="0" bgcolor="#F8F8F8">
                      <tr>
                        <td><div align="center">
                            <p class="style19">&nbsp;</p>
                            <p class="style19">To move ahead you need a password which is provided by the examiner.<br/>
                            Enter that password here and click on proceed.</p>
                            <form name="form1" method="post" action="/Extremity/processTestPassword">
                              <span class="style19">                              Password:</span>                              
                              <input name="txtPassword" type="password" id="txtPassword">&nbsp;
                              <input type="submit" name="Submit" value="Proceed">
                            </form>
                            <%
                            if(error!=null)
                                out.print("<p class=\"style20\">Password mismatched. Try again. </p>");
                              %>
                          <p class="style20">&nbsp;</p>
                        </div></td>
                      </tr>
                  </table></td>
                </tr>
            </table></td>
          </tr>
        </table>
        <jsp:include page="/staticPages/footer.html" flush="true" />
    </body>
</html>