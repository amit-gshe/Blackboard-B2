<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/import.jsp" %>

<%
  String URL = PlugInUtil.getUri("cx", "springdemo", "");
%>
<bbNG:learningSystemPage>
	<bbData:context id="cxCtx">
		<bbNG:pageHeader>
			<bbNG:pageTitleBar title="添加视频"></bbNG:pageTitleBar>
		</bbNG:pageHeader>
		<form enctype="multipart/form-data"
			action="<%=URL%>item/create?course_id=<%=cxCtx.getCourseId().toExternalString()%>&content_id=<%=cxCtx.getContentId().toExternalString()%>"
			method="post">
			<bbNG:dataCollection>
				<bbNG:step title="视频信息">
					<bbNG:dataElement isRequired="true" label="名称">
						<input type="text" name="videoName" id="name" size="45" maxlength="64" />
					</bbNG:dataElement>
					<bbNG:dataElement isRequired="true" label="描述">
						<textarea rows="3" cols="10" name="body"></textarea>
					</bbNG:dataElement>
					<bbNG:dataElement isRequired="true" label="上传视频">
						<input type="file" name="videoFile" id="file" />
					</bbNG:dataElement>
				</bbNG:step>
				<bbNG:stepSubmit title="提交" />
			</bbNG:dataCollection>
		</form>
	</bbData:context>
</bbNG:learningSystemPage>