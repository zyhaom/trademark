<%@page import="java.util.Date"%>
<%@page import="java.util.UUID"%>
<html>
  <body>
    <script src='js/jquery-1.7.2.min.js'></script>
    <script src="js/stomp.js"></script>
    <script>//<![CDATA[
    $(document).ready(function() {
      
         var url = "ws://192.168.1.78:61623";
         var login = "admin";
         var passcode = "password";
         var destination = "/topic/general";

         var client = Stomp.client(url);


         client.connect(login, passcode, function(frame) {
//             client.subscribe(destination, function(message) {
//             });
//         	client.send(destination, {}, "sendMSG : <%=request.getParameter("p") %>");
        	client.send(destination, {}, "sendMSG : <%=UUID.randomUUID()+"\t"+new Date().toLocaleString() %>");
//         	 client.send(destination, {}, "text112");
        	client.disconnect(function() {});
         });
         
      
    });
    //]]></script>
  </body>
</html>
