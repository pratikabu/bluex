<%-- 
    Document   : test
    Created on : Oct 21, 2008, 8:55:11 PM
    Author     : Blue
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welocome to Home Page</title>
    </head>
    <body>
        <jsp:include page="/staticPages/header.html" flush="true" />
        <% 
            Long userID=(Long)request.getSession(false).getAttribute("userID");
            Integer userType=(Integer)request.getSession(false).getAttribute("userType");
            String userTypeString=userType==1?"Employee":"Student";
            out.println("<h2>Welcome "+userTypeString+"</h2>");

            com.gmail.pratikabu.extremity.components.GenericTasksStripGenerator gtsp=
                    new com.gmail.pratikabu.extremity.components.GenericTasksStripGenerator();
            //next line is for the generic task strip
        %>
        <%= gtsp.getGeneratedTasksStrip(userType) %>
        <table width="100%"  border="0">
              <%
              com.gmail.pratikabu.extremity.components.SpecificTasksBoxGenerator stbg=
                      new com.gmail.pratikabu.extremity.components.SpecificTasksBoxGenerator();
              String[] tasks=stbg.getTaskBoxex(userID,userType);
              for(int i=0;i<tasks.length;i++){
                  if(i%2==0)
                      out.println("<tr>");
                  out.println("<td width=\"50%\">"+tasks[i]+"</td>");
                  if(i%2==1)
                      out.println("</tr>");
              }
              %>
        </table>
        <jsp:include page="/staticPages/footer.html" flush="true" />
    </body>
</html>
