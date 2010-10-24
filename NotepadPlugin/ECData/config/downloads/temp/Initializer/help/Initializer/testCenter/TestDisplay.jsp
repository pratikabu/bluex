<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Test Information Display- Extremity</title>
<style type="text/css">
<!--
.style2 {font-family: Geneva, Arial, Helvetica, sans-serif}
.style5 {font-family: Geneva, Arial, Helvetica, sans-serif; font-size: 14px; }
.style6 {font-size: 14}
.style8 {font-size: 14; font-weight: bold; }
.style10 {font-size: 14px}
.style12 {font-family: Geneva, Arial, Helvetica, sans-serif; font-weight: bold; }
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
        long tid=(Long)request.getSession(false).getAttribute("tid");
        int approved=(Integer)request.getSession(false).getAttribute("approved");
        com.gmail.pratikabu.extremity.components.TestDisplayGenerator tdg=
                new com.gmail.pratikabu.extremity.components.TestDisplayGenerator(tid,approved);
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
                        <td><div align="center"><span class="style8">Test Name: <%= tdg.getTestName() %></span></div></td>
                      </tr>
                      <tr>
                        <td class="style5"><div align="center">Total Question: <%= tdg.getTotalQuestionNumber() %></div></td>
                      </tr>
                    </table>
                      <hr>
                      <table>
                        <tr>
                          <td></td>
                        </tr>
                    </table>
                      <div align="left"><span class="style12">Sections Description:</span> </div>
                      <table width="100%"  border="0">
                        <%
                        String[][] testSections=tdg.getTestSections();
                        for(int i=0;i<testSections.length;i++){
                            String sectionLine="<tr>" +
                                    "<td><div align=\"center\"><span class=\"style5\">Section Name: "+testSections[i][0]+"</span></div></td>" +
                                    "</tr><tr>" +
                                    "<td><div align=\"center\"><span class=\"style5\">Total Question: "+testSections[i][1]+"</span></div></td>" +
                                    "</tr><tr><td height=\"53\">" +
                                    "<div align=\"center\">" +
                                    "<table width=\"70%\"  border=\"0\"><tr bgcolor=\"#CCCCCC\">";

                            String categoryStart="<th width=\"43%\" scope=\"col\"><div align=\"left\" class=\"style10\">Category Name </div></th>" +
                                    "<th width=\"30%\" scope=\"col\"><div align=\"right\"><span class=\"style6\">No. of Question </span></div></th></tr>";

                            String[][] catDscr=tdg.getCategoryDescription(Long.parseLong(testSections[i][2]));

                            StringBuffer categoryLine=new StringBuffer("");
                            for(int j=0;j<catDscr.length;j++){
                                categoryLine.append("<tr><td><div align=\"left\"><span class=\"style6\">"+catDscr[j][0]+"</span></div></td>" +
                                        "<td><div align=\"right\"><span class=\"style6\">"+catDscr[j][1]+"</span></div></td></tr>");
                            }

                            String lineEnds="</table>" +
                                    "</div></td></tr>";
                            out.println(sectionLine+categoryStart+categoryLine.toString()+lineEnds);
                        }
                        %>
                      </table>
                      <form action="/Extremity/processRegistry" method="post">
                    <p align="center">
                        <input type="submit" name="Submit" value="Start Test" />
                    </p></form></td>
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
