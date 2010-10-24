<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Report Center- Extremity</title>
<style type="text/css">
<!--
.style1 {font-family: Geneva, Arial, Helvetica, sans-serif}
.style7 {font-family: Geneva, Arial, Helvetica, sans-serif; font-size: 14px; }
.style9 {font-family: Geneva, Arial, Helvetica, sans-serif; font-size: 12px; }
-->
</style>
</head>

<body onload="document.form1.txtSearch.focus()">
    <jsp:include page="/staticPages/header.html" flush="true" />
    <%
    com.gmail.pratikabu.extremity.components.GenericTasksStripGenerator gtsp=
                new com.gmail.pratikabu.extremity.components.GenericTasksStripGenerator();
        //next line is for the generic task strip
    %>
    <%= gtsp.getGeneratedTasksStrip(1) %>
<table width="100%" border="0">
  <tr>
    <td bgcolor="#5494ea"><table width="100%" border="0">
        <td bgcolor="#F8F8F8"><table width="100%"  border="0">
              <tr>
                <td><div align="right">
                    <form name="form1" method="post" action="">
                        <span class="style1"><font color="BLACK">Search:</font>
                      <input name="txtSearch" type="text" id="txtSearch">
                      </span>
                      <input type="submit" name="Submit" value="&nbsp;&gt;&nbsp;">
                    </form>
                </div></td>
              </tr>
              <tr>
                <td><table width="100%"  border="1">
                    <tr>
                      <th width="64%" scope="col"><div align="left"><span class="style7">Test Name</span></div></th>
                      <th width="23%" scope="col"><div align="left"><span class="style7">Date</span></div></th>
                      <th width="13%" scope="col"><div align="right"><span class="style7">No. of Question </span></div></th>
                    </tr>
                    <%
                    com.gmail.pratikabu.extremity.components.ReportTestLineGenerator rtlg=
                            new com.gmail.pratikabu.extremity.components.ReportTestLineGenerator();
                    %>
                    <%= rtlg.generateTestLines(request.getParameter("txtSearch")) %>
                </table></td>
              </tr>
          </table></td>
        </tr>
    </table></td>
  </tr>
</table>
<jsp:include page="/staticPages/footer.html" flush="true" />
</body>
</html>
