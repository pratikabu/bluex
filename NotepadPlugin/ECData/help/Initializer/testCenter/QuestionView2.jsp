<jsp:include page="/staticPages/header.html" flush="true" />
<%
long tid=(Long)request.getSession(false).getAttribute("tid");
com.gmail.pratikabu.extremity.components.TestDisplayGenerator tdg=
        new com.gmail.pratikabu.extremity.components.TestDisplayGenerator(tid);


%>
<table width="100%"  border="0">
  <tr>
    <th scope="col"><div align="center"></div>
      <div align="center">
        <form name="form1" method="get" action="">
          <table width="800" border="0">
            <tr>
              <th bgcolor="#FF9900" scope="col"><table width="800" border="0" bgcolor="#FFFF99">
                  <tr>
                    <th scope="col"><table width="100%"  border="0">
                        <tr>
                          <th scope="col"><table width="100%"  border="0">
                              <tr>
                                <td width="61%" scope="col"><div align="left"><strong>Test Name: <%= tdg.getTestName() %> </strong></div></td>
                                <td width="39%" scope="col"><div align="right">Time Left: 00:00 mins </div></td>
                              </tr>
                          </table></th>
                        </tr>
                        <tr>
                          <td><div align="left">Test Section: Section 1 </div></td>
                        </tr>
                        <tr>
                          <td><div align="left">Question Category: Advanced </div></td>
                        </tr>
                    </table></th>
                  </tr>
                  <tr>
                    <td><hr></td>
                  </tr>
                  <tr>
                    <td><div align="left">Q1: Questions will be displayed here and the description will be shown here. </div></td>
                  </tr>
                  <tr>
                    <td><hr></td>
                  </tr>
                  <tr>
                    <td><div align="left">
                      <p>
                        <input name="optionSelected" type="radio" value="1">
  Option 1 to be selected. </p>
                      <p>
                        <input name="optionSelected" type="radio" value="2">
  Option 2 to be selected. </p>
                      <p>
                        <input name="optionSelected" type="radio" value="3">
  Option 3 to be selected. </p>
                      <p>
                        <input name="optionSelected" type="radio" value="4">
  Option 4 to be selected.</p> 
                      </div></td>
                  </tr>
                  <tr>
                    <td><hr></td>
                  </tr>
                  <tr>
                    <td><table width="100%"  border="0">
                        <tr>
                          <td width="26%"><div align="left"><a href="/Extremity/testCenter/QuestionMenu.jsp">Question Menu</a> </div></td>
                          <td width="48%"><div align="center"><a href="/Extremity/processQuestion?next=prev">Previous</a> |
                          <a href="/Extremity/processQuestion?next=next">Next</a> </div></td>
                          <td width="26%"><div align="right"><a href="/Extremity/processQuestion?marked=true">Mark</a></div></td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td><div align="left">
                        <hr>
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="right"><a href="#">End Test</a></div></td>
                  </tr>
              </table></th>
            </tr>
          </table>
        </form>
      </div></th>
  </tr>
</table>
<jsp:include page="/staticPages/footer.html" flush="true" />