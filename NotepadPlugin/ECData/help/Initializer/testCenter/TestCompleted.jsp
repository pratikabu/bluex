<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Test Completed</title>
<style type="text/css">
<!--
.style2 {
	font-family: Geneva, Arial, Helvetica, sans-serif;
	font-size: 14px;
}
.style8 {font-size: 14; font-weight: bold; }
-->
</style>
</head>

<body>
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

        if(tid==-1)
            tid=(Long)request.getSession(false).getAttribute("tid");
    %>
<table width="100%"  border="0">
  <tr>
    <td><div align="center">
        <table width="800" border="0">
          <tr>
            <td bgcolor="#5494ea"><table width="100%"  border="0">
                <tr>
                  <td bgcolor="#F8F8F8"><table width="100%"  border="0">
                      <tr>
                        <td align="left" class="style2">Your test has been completed. You can view your result by clicking on the link given below. </td>
                      </tr>
                    </table>
                      <hr>
                      <p align="center">
                          <%
                            out.println("<a href=\"/Extremity/reportInitializer?tid="+tid+"\" class=\"style2\">");
                          %>
                              Go to Report Center</a> </p>
                  <p align="center">&nbsp;</p></td>
                </tr>
            </table></td>
          </tr>
        </table>
    </div></td>
  </tr>
</table>
<jsp:include page="/staticPages/footer.html" flush="true" />
</body>
</html>
