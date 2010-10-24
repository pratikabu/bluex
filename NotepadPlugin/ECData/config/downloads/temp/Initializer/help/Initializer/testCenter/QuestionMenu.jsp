
<html>
<head>
    <title>Question Menu</title>
<script type="text/javascript">

	function endTest(){
		document.location.href="/Extremity/endTest?tl=" + maxTime;
	}

	//calculation for the time left
	var maxTime;//it should be in seconds ok... do it in seconds

	function startTimerJob(initTime){
		maxTime=initTime;
		processTiming();
	}

    function dispatchQuestion(quesNo){
        document.location.href="/Extremity/processQuestion?nextQues="+quesNo+"&tl="+maxTime;
    }
	
	function processTiming(){
		var min, sec;
		if(maxTime==-1)
			endTest();
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
        //get remaining time
        String strTimeLeft=request.getParameter("tl");
        int timeLeft=Integer.parseInt(strTimeLeft);

        long tcrid=(Long)request.getSession(false).getAttribute("tcrid");
        //set the time left
        com.gmail.pratikabu.extremity.controller.UsefulMethods.updateTimeLeft(strTimeLeft, tcrid);
%>

<body onLoad="startTimerJob(<%= timeLeft %>)">
<jsp:include page="/staticPages/header.html" flush="true" />
<%
    long tid=(Long)request.getSession(false).getAttribute("tid");
    int approved=(Integer)request.getSession(false).getAttribute("approved");
    com.gmail.pratikabu.extremity.components.TestDisplayGenerator tdg=
            new com.gmail.pratikabu.extremity.components.TestDisplayGenerator(tid,approved);
        //next line is for the generic task strip
    %>
<table width="100%"  border="0">
  <tr>
    <th scope="col"><div align="center"></div>
      <div align="center">
        <table width="800" border="0">
          <tr>
            <th bgcolor="#FF9900" scope="col"><table width="800" border="0" bgcolor="#FFFF99">
                <tr>
                  <th scope="col"><table width="100%"  border="0">
                    <tr>
                      <th scope="col"><table width="100%"  border="0">
                        <tr>
                          <td width="61%" scope="col"><div align="left"><strong>Test Name: <%= tdg.getTestName() %>
                          </strong></div></td>
                          <td width="39%" scope="col"><div id="elapsedTime" align="right"></div></td>
                        </tr>
                      </table></th>
                    </tr>
                  </table></th>
                </tr>
                <tr>
                  <td><hr></td>
                </tr>
                <tr>
                  <td><div align="left">
                    <table width="100%"  border="1">
                        <%
                        com.gmail.pratikabu.extremity.components.QuestionMenuGenerator qmg=
                                new com.gmail.pratikabu.extremity.components.QuestionMenuGenerator();
                        java.util.Vector<String> vectBgColor=qmg.getBackGroundColors(tcrid);

                        for(int i=0;i<vectBgColor.size();i++){
                            if(i%10==0)//new line identified
                                out.println("<tr>");

                            out.println("<td bgcolor="+vectBgColor.elementAt(i)+"><div align=\"center\"><a href=\"#\" onClick=\"dispatchQuestion("+(i+1)+")\">Q: "+(i+1)+"</a></div></td>");

                            if(i%10==9)//end the <tr> tag
                                out.println("</tr>");
                        }
                        %>
                    </table>
                  </div></td>
                </tr>
            </table></th>
          </tr>
        </table>
        <p>&nbsp;</p>
        <table width="800" border="0">
          <tr>
            <td><div align="left">
              <p><strong>Legends:</strong></p>
              <table width="116" border="0">
                <tr>
                  <td width="101" bgcolor="#E4E4E4"><div align="center">Not Attempted</div></td>
                </tr>
              </table>
              <table width="117" border="0">
                <tr>
                  <td width="111" bgcolor="#D5F9D5"><div align="center">Attenpted</div></td>
                </tr>
              </table>
              <table width="117" border="0">
                <tr>
                  <td width="111" bgcolor="#FFDDE1"><div align="center">Marked</div></td>
                </tr>
              </table>
            </div></td>
          </tr>
        </table>
      </div></th>
  </tr>
</table>
<jsp:include page="/staticPages/footer.html" flush="true" />
</body>
</html>