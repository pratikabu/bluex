<%-- 
    Document   : login
    Created on : Oct 20, 2008, 9:08:04 PM
    Author     : Blue
--%>

<html>
  <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Extremity- Login</title>
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
    <body onload="document.form1.txtUsername.focus()">
        <jsp:include page="staticPages/header.html" flush="true" />
        <form name="form1" method="post" action="loginAssist">
          <table width="100%"  border="0">
            <tr>
              <td width="76%"><div align="left">
                <table width="100%" height="100%"  border="0">
                  <tr>
                    <td><span class="style18">Welcome! </span></td>
                  </tr>
                  <tr>
                    <td height="46"><span class="style17">Extremity is a new approach for managing the tedious information of various institution's tasks. It is designed to give you maximum of the information from every source. It is the central for getting the information right on your screen. It is secured and ready to serve. Always. Enjoy it!</span></td>
                  </tr>
                  <tr>
                    <td height="46">&nbsp;</td>
                  </tr>
                </table>
              </div></td>
              <td width="24%"><table width="100%"  border="0" bgcolor="#5494EA">
                <tr>
                  <td><table width="100%"  border="0" bgcolor="#F8F8F8">
                    <tr>
                    <td>
                          <div align="center" class="style17">Enter Information Center</div></td></tr>
                    <tr>
                      <td><div align="center" class="style17"><span class="style17">with your</span> <strong>Account</strong></div></td>
                    </tr>
                    <tr>
                      <td><table align="center">
                        <tr>
                          <td><span class="style15">Username:</span></td>
                          <td><input type="text" name="txtUsername"/></td>
                        </tr>
                        <tr>
                          <td><span class="style15">Password:</span></td>
                          <td><input type="password" name="txtPassword"/></td>
                        </tr>
                        <tr>
                          <td><span class="style15">UserType: </span></td>
                          <td align="right"><div align="left">
                            <select name="cbUserType"><option value="2">Student</option>
							<option value="1">Employee</option>
                            </select>
                          </div></td>
                        </tr>
                        <tr>
                          <td>&nbsp;</td>
                          <td align="right"><input type="submit" value="Login"></td>
                        </tr>
                      </table></td>
                    </tr>
                  </table></td>
                </tr>
              </table>
              </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td><table width="100%"  border="0" bgcolor="#5494EA">
                <tr>
                  <td><table width="100%" height="100%"  border="0" bgcolor="#F8F8F8">
                      <tr>
                        <td width="236"><div align="center" class="style17">New user Registration </div></td>
                      </tr>
                      <tr>
                        <td><div align="center" class="style19"><a href="StudentRegistration.jsp">Student</a></div></td>
                      </tr>
                  </table></td>
                </tr>
              </table></td>
            </tr>
          </table>
        </form>
        <jsp:include page="staticPages/footer.html" flush="true" />
    </body>
</html>