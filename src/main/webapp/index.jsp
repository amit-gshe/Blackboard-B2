<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="blackboard.platform.plugin.PlugInUtil"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="/bbData" prefix="bbData"%>

<% String URL = PlugInUtil.getUri("cx", "springdemo", ""); %>

<bbNG:learningSystemPage>
	<bbNG:breadcrumbBar>
		<bbNG:breadcrumb>Create Item</bbNG:breadcrumb>
		<bbNG:breadcrumb>Spring Demo</bbNG:breadcrumb>
	</bbNG:breadcrumbBar>
	<bbNG:pageHeader>
		<bbNG:pageTitleBar title="Spring Demo" iconUrl="assets/img/face.png"
			showIcon="true"></bbNG:pageTitleBar>
	</bbNG:pageHeader>
	<ul>
		<li><a href="helloWorld">Hello World</a>
		<li><a href="hello">Learn API Example</a>
		<li><a href="course_users?cid=_2_1">Advanced Learn API
				Example</a>
		<li><a href="fooController">Hibernate Example</a>
		<li><a href="rest?key=key1&value=value1">REST Example</a></li>
		<li><a href="websocket">WebSocket Example</a></li>
	</ul>
	<img src="<%= URL %>assets/img/face.png" />
</bbNG:learningSystemPage>