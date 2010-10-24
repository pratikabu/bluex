<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Report Display- Extremity</title>
<style type="text/css">
<!--
.style2 {font-family: Geneva, Arial, Helvetica, sans-serif}
.style5 {font-family: Geneva, Arial, Helvetica, sans-serif; font-size: 14px; }
.style7 {font-family: Geneva, Arial, Helvetica, sans-serif; font-size: 14px; color: #FF6600 }
.style6 {font-size: 14}
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
        long tcrid=(Long)request.getSession(false).getAttribute("tcrid");
        request.getSession(false).removeAttribute("tcrid");
        
        com.gmail.pratikabu.extremity.components.ReportDisplayGenerator rdg=
                new com.gmail.pratikabu.extremity.components.ReportDisplayGenerator(tid,tcrid);

        int totalMarks=0, marksObtained=0;
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
                        <td><div align="center"><span class="style6">Test Name: <b><%= rdg.getTestName() %></b></span></div></td>
                      </tr>
                      <tr>
                        <td class="style5"><div align="center">Total Question: <%= rdg.getTotalQuestionNumber() %></div></td>
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
                        String[][] testSections=rdg.getTestSections();
                        for(int i=0;i<testSections.length;i++){
                            String sectionLine="<tr>" +
                                    "<td><div align=\"center\"><span class=\"style5\">Section Name: <b>"+testSections[i][0]+"</b></span></div></td>" +
                                    "</tr><tr>";

                            int[] marksDetails=rdg.getSectionMarks(Long.parseLong(testSections[i][2]));
                            String marksInfo="<td><div align=\"center\"><span class=\"style5\">Total Marks: <b>"+
                                    marksDetails[0]+"</b>, Marks Obtained: <b>"+marksDetails[1]+"</b></td></tr>"+
                                    "</tr><tr><td height=\"53\">";

                            //add these marks to the grand total variables
                            totalMarks+=marksDetails[0];
                            marksObtained+=marksDetails[1];

                            String lineEnds="</span></div></td></tr>";
                            out.println(sectionLine+marksInfo+lineEnds);
                        }
                        %>
                      </table><span class="style7">
                      <b>Grand Totals:-</b> Total Marks: <b><%= totalMarks %></b>, Marks Obtained: <b><%= marksObtained %></b>
                      , Percentage: <b><%= String.format("%.2f", (float)marksObtained/(float)totalMarks*100) %>%</b></span>
                      </td>
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
