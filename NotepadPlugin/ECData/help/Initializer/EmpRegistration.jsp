<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee Registration</title>
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
		-->
		</style>
	</head>
	<body>
            <jsp:include page="header.html" flush="true"/>
		<div align="center"></div>
		<table width="100%"  border="0" align="center">
		  <tr>
			<td height="684"><form name="form1" method="post" action="">
			  <table width="100%" height="733"  border="0" align="center">
				<tr>
				  <th height="37" bgcolor="#EAEAEA" scope="col"><div align="left" class="style4">New Employee Registration </div></th>
				</tr>
				<tr>
				  <td height="37" bgcolor="#FFFF99"><span class="style5">Required Details:</span></td>
				</tr>
				<tr>
				  <td height="338"><table width="100%"  border="0" bgcolor="#FFFFCC">
					  <tr>
                                              <td width="20%"><div align="right" class="style10"><span class="style1"><span class="style2"><font color="BLACK">First Name:</font></span></span></div></td>
						<td width="80%"><input name="textfield" type="text"></td>
					  </tr>
					  <tr>
                                              <td><div align="right" class="style10"><span class="style1"><span class="style2"><font color="BLACK">Middle Name: </font></span></span></div></td>
						<td><input type="text" name="textfield"></td>
					  </tr>
					  <tr>
						<td><div align="right" class="style10"><span class="style1"><span class="style2"><font color="BLACK">Last Name: </font></span></span></div></td>
						<td><input type="text" name="textfield"></td>
					  </tr>
					  <tr>
						<td><div align="right" class="style10"><span class="style1"><span class="style2"><font color="BLACK">Phone: </font></span></span></div></td>
						<td><input type="text" name="textfield"></td>
					  </tr>
					  <tr>
						<td><div align="right" class="style10"><span class="style1"><span class="style2"><font color="BLACK">Date of Birth:</font></span></span></div></td>
						<td><input type="text" name="textfield"></td>
					  </tr>
					  <tr>
						<td><div align="right" class="style10"><span class="style1"><span class="style2"><font color="BLACK">Current Address:</font></span></span></div></td>
						<td><textarea name="textarea" rows="4"></textarea></td>
					  </tr>
					  <tr>
						<td><div align="right" class="style10"><span class="style1">Email:</span></div></td>
						<td><input type="text" name="textfield"></td>
					  </tr>
					  <tr>
						<td><div align="right" class="style10"><span class="style1"><span class="style2"><font color="BLACK">Date of Joining:</font></span></span></div></td>
						<td><input type="text" name="textfield"></td>
					  </tr>
					  <tr>
						<td><div align="right" class="style10"><span class="style1"><font color="BLACK">Designation:</font></span></div></td>
						<td><select name="select">
                                                        <option value="1">---Select Any---</option>
						</select></td>
					  </tr>
					  <tr>
						<td><div align="right" class="style10"><span class="style1"><span class="style2"><font color="BLACK">Stream:</font></span></span></div></td>
						<td><select name="select">
                                                    <option value="1">---Select Any---</option>
						</select></td>
					  </tr>
					  <tr>
						<td><div align="right" class="style10"><span class="style1"><span class="style2"><font color="BLACK">Departement:</font></span></span></div></td>
						<td><select name="select">
                                                    <option value="1">---Select Any---</option>
						</select></td>
					  </tr>
				  </table></td>
				</tr>
				<tr>
				  <td height="37" bgcolor="#FFFF99"><span class="style5">Other Details: </span></td>
				</tr>
				<tr>
				  <td height="208"><table width="100%"  border="0" bgcolor="#FFFFCC">
					<tr>
					  <td width="20%"><div align="right" class="style11"><span class="style1"><font color="BLACK">Father's Name:</font></span></div></td>
					  <td width="80%"><input type="text" name="textfield"></td>
					</tr>
					<tr>
					  <td><div align="right" class="style11"><span class="style1"><font color="BLACK">Mother's Name: </font></span></div></td>
					  <td><input type="text" name="textfield"></td>
					</tr>
					<tr>
					  <td><div align="right" class="style11"><span class="style1"><font color="BLACK">Qualification: </font></span></div></td>
					  <td><textarea name="textarea4" rows="4" id="textarea4"></textarea></td>
					</tr>
					<tr>
					  <td><div align="right" class="style11"><span class="style1"><font color="BLACK">History:</font></span></div></td>
					  <td><textarea name="textarea3" rows="4" id="textarea3"></textarea></td>
					</tr>
					<tr>
					  <td><div align="right" class="style11"><span class="style1"><font color="BLACK">Permanent Address:</font></span></div></td>
					  <td><textarea name="textarea1" rows="4" id="textarea1"></textarea></td>
					</tr>
					<tr>
					  <td><div align="right" class="style11"><span class="style1"><font color="BLACK">Other Address:</font></span></div></td>
					  <td><textarea name="textarea2" rows="4" id="textarea2"></textarea></td>
					</tr>
				  </table></td>
				</tr>
				<tr>
				  <td height="30">
					<div align="right">
					  <input type="submit" name="Submit" value="Save">
					  <input type="button" name="Button" value="Cancel">
				        </div></td></tr>
			  </table>
			</form></td>
		  </tr>
		</table>
                <jsp:include page="footer.html" flush="true"/>
	</body>
</html>
