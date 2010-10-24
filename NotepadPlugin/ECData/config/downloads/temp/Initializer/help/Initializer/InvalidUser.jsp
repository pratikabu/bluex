<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Invalid User</title>
<style type="text/css">
<!--
.style2 {
	font-family: Geneva, Arial, Helvetica, sans-serif;
	font-size: 14px;
}
.style8 {font-size: 14; font-weight: bold; }
-->
</style>
</head>

<body>
    <jsp:include page="/staticPages/header.html" flush="true" />
<table width="100%"  border="0">
  <tr>
    <td><div align="center">
        <table width="800" border="0">
          <tr>
            <td bgcolor="#CC0000"><table width="100%"  border="0">
                <tr>
                  <td bgcolor="#F8F8F8"><table width="100%"  border="0">
                      <tr>
                        <td align="center" class="style2">Provided details does not match with any account. <font color="RED">Not a valid user.</font></td>
                      </tr>
                    </table>
                      <hr>
                      <p align="center"><a href="/Extremity/login.jsp" class="style2">
                              Relogin</a> </p>
                  <p align="center">&nbsp;</p></td>
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
