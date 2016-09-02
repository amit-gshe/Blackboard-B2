<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="blackboard.platform.plugin.PlugInUtil"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="/bbData" prefix="bbData"%>

<% String URL = PlugInUtil.getUri("cx", "springdemo", ""); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Example websocket project</title>
<script type="text/javascript" src="<%=URL %>assets/js/sockjs-1.1.1.min.js"></script>
</head>
<body>hello
</body>
<script type="text/javascript">
function connectWithSockJS() {
	sock = new SockJS("<%=URL %>ws");
	sock.onopen = function() {
		console.log('open');
		sock.send('hello server');
	};
	sock.onmessage = function(e) {
		console.log('message:', e.data);
	};
	sock.onclose = function() {
		console.log('close');
	};
}

window.onload = function() {
	connectWithSockJS();
}
</script>
</html>