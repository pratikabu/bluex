<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Registration- Extremity</title>
		<style type="text/css">
		<!--
		.style1 {font-family: Geneva, Arial, Helvetica, sans-serif}
		.style2 {font-size: 14px}
		.style4 {
			font-family: Geneva, Arial, Helvetica, sans-serif;
			font-size: 16px;
			color: #666666;
		}
		.style5 {
			font-family: Geneva, Arial, Helvetica, sans-serif;
			font-size: 14px;
			color: #666666;
		}
		.style10 {color: #333333; }
		.style11 {font-size: 14px; color: #333333; }
.style12 {font-family: Geneva, Arial, Helvetica, sans-serif; font-size: 14px; }
		-->
		</style>
        <script type="text/javascript">

            function saveRegistration(){
                var valid=true;

                if(document.form1.txtName.value==""){
                    valid=false;
                    alert("Name cannot be empty.");
                    document.form1.txtName.focus();
                }else if(document.form1.txtUsername.value==""){
                    valid=false;
                    alert("Username cannot be empty.");
                    document.form1.txtUsername.focus();
                }else if(document.form1.txtPassword.value!=document.form1.txtConfirmPassword.value){
                    valid=false;
                    alert("Password do match with Confirm Password.");
                    document.form1.txtPassword.focus();
                }else if(document.form1.txtPassword.value==""){
                    valid=false;
                    alert("Password cannot be empty.");
                    document.form1.txtPassword.focus();
                }else if(document.form1.txtPhone.value==""){
                    valid=false;
                    alert("Phone cannot be empty.");
                    document.form1.txtPhone.focus();
                }else if(document.form1.txtDOB.value==""){
                    valid=false;
                    alert("Date of Birth cannot be empty and should be in a proper format.\nAs: MM/DD/YYYY");
                    document.form1.txtDOB.focus();
                }else if(document.form1.txtEmail.value==""){
                    valid=false;
                    alert("Email cannot be empty.");
                    document.form1.txtEmail.focus();
                }

                if(valid){
                    document.form1.action="/Extremity/registerStudent";
                    document.form1.submit();
                }
            }

            function cancelRegistration(){
                document.location.href="/Extremity/login.jsp";
            }
        </script>
	</head>
	<body onload="document.form1.txtName.focus();">
            <jsp:include page="staticPages/header.html" flush="true" />
		<div align="center"></div>
		<table width="100%"  border="0" align="center">
		  <tr>
			<td height="490"><form name="form1" method="post" action="">
			  <table width="100%" height="488"  border="0" align="center">
				<tr>
				  <th height="37" bgcolor="#EAEAEA" scope="col"><div align="left" class="style4">New Student Registration </div></th>
				</tr>
				<tr>
				  <td height="37" bgcolor="#FFFF99"><span class="style5">Required Details:</span></td>
				</tr>
				<tr>
				  <td height="276"><table width="100%"  border="0" bgcolor="#FFFFCC">
					  <tr>
                                              <td width="20%"><div align="right" class="style10"><span class="style1"><span class="style2"><font color="BLACK">Name:</font></span></span></div></td>
						<td width="80%"><input name="txtName" type="text" id="txtName"></td>
					  </tr>
					  <tr>
					    <td><div align="right" class="style12">User Name:</div></td>
					    <td><div align="left">
					      <input name="txtUsername" type="text" id="txtUsername">
					    </div></td>
				    </tr>
					  <tr>
					    <td><div align="right" class="style12">Password:</div></td>
					    <td><div align="left">
					      <input name="txtPassword" type="password" id="txtPassword">
					    </div></td>
				    </tr>
					  <tr>
					    <td><div align="right" class="style12">Confirm Password: </div></td>
					    <td><input name="txtConfirmPassword" type="password" id="txtConfirmPassword"></td>
				    </tr>
					  <tr>
						<td><div align="right" class="style10"><span class="style1"><span class="style2"><font color="BLACK">Phone: </font></span></span></div></td>
						<td><input name="txtPhone" type="text" id="txtPhone"></td>
					  </tr>
					  <tr>
						<td><div align="right" class="style10"><span class="style1"><span class="style2"><font color="BLACK">Date of Birth:</font></span></span></div></td>
						<td><input name="txtDOB" type="text" id="txtDOB"> Format: MM/DD/YYYY</td>
					  </tr>
					  <tr>
						<td><div align="right" class="style10"><span class="style1"><span class="style2"><font color="BLACK">Address:</font></span></span></div></td>
						<td><textarea name="taAddress" rows="4" id="taAddress"></textarea></td>
					  </tr>
					  <tr>
						<td><div align="right" class="style10"><span class="style1"><span class="style2"><font color="BLACK">Email</font></span>:</span></div></td>
						<td><input name="txtEmail" type="text" id="txtEmail"></td>
					  </tr>
				  </table></td>
				</tr>
				<tr>
				  <td height="37" bgcolor="#FFFF99"><span class="style5">Other Details: </span></td>
				</tr>
				<tr>
				  <td height="56"><table width="100%"  border="0" bgcolor="#FFFFCC">
					<tr>
					  <td width="20%"><div align="right" class="style11"><span class="style1"><font color="BLACK">Father's Name:</font></span></div></td>
					  <td width="80%"><input name="txtFatherName" type="text" id="txtFatherName"></td>
					</tr>
					<tr>
					  <td><div align="right" class="style11"><span class="style1"><font color="BLACK">Mother's Name: </font></span></div></td>
					  <td><input name="txtMotherName" type="text" id="txtMotherName"></td>
					</tr>
				  </table></td>
				</tr>
				<tr>
				  <td height="30">
					<div align="right">
                        <input type="button" name="Button" value="Save" onclick="saveRegistration()">
                        <input type="button" name="Button" value="Cancel" onclick="cancelRegistration()">
			        </div></td></tr>
			  </table>
			</form></td>
		  </tr>
		</table>
                <jsp:include page="staticPages/footer.html" flush="true"/>
	</body>
</html>
