<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/import.jsp"%>
<%
  String URL = PlugInUtil.getUri("cx", "springdemo", "");
%>
<bbNG:learningSystemPage>
	<bbData:context id="cxCtx">
		<bbNG:pageHeader>
			<bbNG:pageTitleBar title="视频列表"></bbNG:pageTitleBar>
		</bbNG:pageHeader>
		<table>
			<thead>
				<tr>
					<th>Id</th>
					<th>title</th>
				</tr>
			</thead>
			<c:forEach var="content" items="${contents}">
				<tr>
					<td>${content.getId().toExternalString()}</td>
					<td>${content.title}</td>
				</tr>
			</c:forEach>
		</table>
		
		<table>
			<thead>
				<tr>
					<th>Id</th>
				</tr>
			</thead>
			<c:forEach var="content" items="${contentFiles}">
				<tr>
					<td>${content.getId().toExternalString()}</td>
				</tr>
			</c:forEach>
		</table>
	</bbData:context>
</bbNG:learningSystemPage>