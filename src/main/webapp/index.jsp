<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="blackboard.platform.plugin.PlugInUtil"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="/bbData" prefix="bbData"%>
<bbNG:learningSystemPage>
	<bbNG:breadcrumbBar>
		<bbNG:breadcrumb>Create Item</bbNG:breadcrumb>
		<bbNG:breadcrumb>Spring Demo</bbNG:breadcrumb>
	</bbNG:breadcrumbBar>
	<bbNG:pageHeader>
		<bbNG:pageTitleBar title="Spring Demo" iconUrl="assets/img/face.png"
			showIcon="true"></bbNG:pageTitleBar>
	</bbNG:pageHeader>
	<img src="/assets/img/face.png" />
	<img src="assets/img/face.png" />
	<ul>
		<li><a href="helloWorld">Hello World</a>
		<li><a href="hello">Learn API Example</a>
		<li><a href="course_users?cid=_2_1">Advanced Learn API
				Example</a>
		<li><a href="fooController">Hibernate Example</a>
		<li><a href="rest?key=key1&value=value1">rest sample</a></li>
		<li><a href="fooController">Hibernate Example</a>
	</ul>
</bbNG:learningSystemPage>