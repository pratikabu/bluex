
<%-- 
    Document   : login
    Created on : Oct 20, 2008, 9:08:04 PM
    Author     : Blue
--%>

<html>
  <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm End Test</title>
        <style type="text/css">
<!--
.style19 {
	font-family: Geneva, Arial, Helvetica, sans-serif;
	font-size: 14px;
}
-->
        </style>
<script type="text/javascript">

	function yesOption(){
		document.location.href="/Extremity/endTest?tl=" + maxTime;
	}

	function noOption(){
		document.location.href="/Extremity/testCenter/QuestionMenu.jsp?tl=" + maxTime;
	}
	
	function endTest(){
		document.location.href="/Extremity/endTest?tl=" + maxTime;
	}

	//calculation for the time left
	var maxTime;//it should be in seconds ok... do it in seconds

	function startTimerJob(initTime){
		maxTime=initTime;
		processTiming();
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
        <table width="100%" border="0">
          <tr>
            <td align="center"><table width="800"  border="0" bgcolor="#5494EA">
                <tr>
                  <td><table width="100%"  border="0" bgcolor="#F8F8F8">
                      <tr>
                        <td><div align="center">
                            <form name="form1" method="get" action="">
                              <p class="style19">You are about to end Test. </p>
                              <p class="style19"><div id="elapsedTime"></div></p>
                              <span class="style19">Are you sure?</span>
                              <p></p>
                              <input type="button" name="Button" value="Yes" onclick="yesOption()">&nbsp;
                              <input type="button" name="Button" value="No" onclick="noOption()">
                            </form>
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