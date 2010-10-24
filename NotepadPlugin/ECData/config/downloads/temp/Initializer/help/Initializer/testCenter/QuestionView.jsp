
<html>
<head>
    <title>Question View</title>
<script type="text/javascript">
    //calculation for the time left
	var maxTime;//it should be in seconds ok... do it in seconds

	function previousQuestion(){
		document.form1.action="/Extremity/previousQuestion";
        document.form1.hdTimeLeft.value=maxTime+"";
		document.form1.submit();
	}
	
	function nextQuestion12(){
		document.form1.action="/Extremity/nextQuestion";
        document.form1.hdTimeLeft.value=maxTime+"";
		document.form1.submit();
	}
	
	function markQuestion(){
		document.location.href="/Extremity/processQuestion?marked=true&tl="+maxTime;
	}
	
	function questionMenu(){
		document.location.href="/Extremity/testCenter/QuestionMenu.jsp?tl="+maxTime;
	}
	
	function endTest(){
        document.location.href="/Extremity/testCenter/ConfirmEndTest.jsp?tl="+maxTime;
	}

    function endTestEndTime(){
		document.location.href="/Extremity/endTest?tl=" + maxTime;
    }

	function startTimerJob(initTime){
		maxTime=initTime;
		processTiming();
	}
	
	function processTiming(){
		var min, sec;
		if(maxTime==-1)
            endTestEndTime()();
		else{
			min=maxTime/60;
			var showableMinutes=min+"";
			
			if(min/10 >= 1){//double digit
				showableMinutes=showableMinutes.substr(0, 2);
			}else{
				showableMinutes=showableMinutes.substr(0, 1);
			}
			
			min=parseInt(showableMinutes);
			sec=maxTime-min*60;
			
			showableMinutes=min/10>=1 ? min + "":"0" + min;
			var showableSeconds=sec/10>=1 ? sec + "":"0" + sec;
			document.getElementById('elapsedTime').innerHTML = "Time Left: " + showableMinutes + ":" + showableSeconds + " min";
			maxTime--;
			t = setTimeout('processTiming()',1000);
		}
	}
</script>

</head>
<%
        long tid=(Long)request.getSession(false).getAttribute("tid");
        int approved=(Integer)request.getSession(false).getAttribute("approved");
        com.gmail.pratikabu.extremity.components.TestDisplayGenerator tdg=
                new com.gmail.pratikabu.extremity.components.TestDisplayGenerator(tid,approved);

        String isNextButton=(String)request.getSession(false).getAttribute("nextButton");
        String isPrevButton=(String)request.getSession(false).getAttribute("prevButton");

        long qgid=(Long)request.getSession(false).getAttribute("qgid");
        com.gmail.pratikabu.extremity.components.QuestionViewQuestionData qvqd=
                new com.gmail.pratikabu.extremity.components.QuestionViewQuestionData(qgid);
        com.gmail.pratikabu.extremity.components.QuestionViewQuestionMetaData qvqmd=
                new com.gmail.pratikabu.extremity.components.QuestionViewQuestionMetaData(qvqd.getQid());

        com.gmail.pratikabu.extremity.components.QuestionViewOptionGenerator qvog=
                new com.gmail.pratikabu.extremity.components.QuestionViewOptionGenerator(qvqd.getQid(), qgid, qvqd.isAttempted());

        //get remaining time
        String strTimeLeft=request.getParameter("tl");
        int timeLeft=Integer.parseInt(strTimeLeft);

        long tcrid=(Long)request.getSession(false).getAttribute("tcrid");
        //set the time left
        com.gmail.pratikabu.extremity.controller.UsefulMethods.updateTimeLeft(strTimeLeft, tcrid);
%>

<body onLoad="startTimerJob(<%= timeLeft %>)">

<jsp:include page="/staticPages/header.html" flush="true" />

<table width="100%"  border="0">
  <tr>
    <th scope="col"><div align="center"></div>
      <div align="center">
        <form name="form1" method="post" action="">
            <input name="hdTimeLeft" type="hidden" id="seconds" />
            
            <input type="hidden" name="nextQuestion" value="next"/>
          <table width="800" border="0">
            <tr>
              <th bgcolor="#FF9900" scope="col"><table width="800" border="0" bgcolor="#FFFF99">
                  <tr>
                    <th scope="col"><table width="100%"  border="0">
                        <tr>
                          <th scope="col"><table width="100%"  border="0">
                              <tr>
                                <td width="17%" scope="col"><div align="left">Test Name:  
                                  </div></td>
                                <td width="54%" scope="col"><div align="left"><strong><%= tdg.getTestName() %></strong></div></td>
                                <td width="29%" scope="col"><div id="elapsedTime" align="right"></div></td>
                              </tr>
                          </table></th>
                        </tr>
                        <tr>
                          <td><div align="left">
                            <table width="100%"  border="0">
                              <tr>
                                <td width="17%">Test Section:  </td>
                                <td width="83%"><%= qvqmd.getTestSection() %></td>
                              </tr>
                            </table>
                          </div></td>
                        </tr>
                        <tr>
                          <td><div align="left">
                            <table width="100%"  border="0">
                              <tr>
                                <td width="17%">Question Category: </td>
                                <td width="83%"><%= qvqmd.getQuestionCategory() %> </td>
                              </tr>
                            </table>
                          </div></td>
                        </tr>
                    </table></th>
                  </tr>
                  <tr>
                    <td><hr></td>
                  </tr>
                  <tr>
                      <td><div align="left"><b>Q<%= request.getSession(false).getAttribute("quesNo").toString() %>: </b>
                      <%= qvqd.getQDscr() %> </div></td>
                  </tr>
                  <tr>
                    <td><hr></td>
                  </tr>
                  <tr>
                    <td><div align="left">
                        <%= qvog.getOptionString() %>
                      </div></td>
                  </tr>
                  <tr>
                    <td><hr></td>
                  </tr>
                  <tr>
                    <td><table width="100%"  border="0">
                        <tr>
                          <td width="48%">
                            <div align="left">
                                <%
                                if(isPrevButton.equals("true")){
                                    out.println("<input type=\"button\" name=\"cmdPrev\" value=\"Prev\" onclick=\"previousQuestion()\">");
                                    }
                                %>
                              &nbsp;
                              <%
                                if(isNextButton.equals("true")){
                                    out.println("<input type=\"button\" name=\"cmdNext\" value=\"Next\" onclick=\"nextQuestion12()\">");
                                    }
                                %>
                            </div></td>
                          <td width="26%"><div align="right">
                                  <input type="button" name="cmdMark" value="Mark" onclick="markQuestion()">
                          </div></td>
                        </tr>
                    </table></td>
                  </tr>
                  <tr>
                    <td><div align="left">
                        <hr>
                    </div></td>
                  </tr>
                  <tr>
                    <td><div align="right">
                      <table width="100%"  border="0">
                        <tr>
                          <td><div align="left">
                                  <input type="button" name="cmdQuestionMenu" value="Ques Menu" onclick="questionMenu()">
                          </div></td>
                          <td><div align="right">
                            <input type="button" name="cmdEndTest" value="End Test" onClick="endTest()">
                          </div></td>
                        </tr>
                      </table>
                    </div></td>
                  </tr>
              </table></th>
            </tr>
          </table>
        </form>
      </div></th>
  </tr>
</table>
<jsp:include page="/staticPages/footer.html" flush="true" />
</body>
</html>