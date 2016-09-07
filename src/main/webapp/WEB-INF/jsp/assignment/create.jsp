<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="blackboard.platform.plugin.PlugInUtil"%>
<%@page import="blackboard.platform.config.BbConfig"%>
<%@page import="blackboard.platform.config.ConfigurationServiceFactory"%>
<%@page import="blackboard.platform.context.Context"%>
<%@page import="blackboard.platform.context.ContextManagerFactory"%>
<%@page import="blackboard.persist.BbPersistenceManager"%>
<%@page
	import="blackboard.platform.contentsystem.manager.DocumentManager"%>
<%@page
	import="blackboard.platform.contentsystem.service.ContentSystemServiceExFactory"%>
<%@page
	import="blackboard.platform.coursecontent.CourseContentManagerFactory"%>
<%@page import="javax.activation.DataHandler"%>
<%@page import="java.util.*"%>
<%@page import="java.util.Properties"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="blackboard.data.user.User"%>
<%@page import="blackboard.persist.user.UserDbLoader"%>
<%@page import="blackboard.data.course.Course"%>
<%@page import="blackboard.persist.course.CourseDbLoader"%>
<%@page import="blackboard.persist.content.ContentDbLoader"%>
<%@page import="blackboard.persist.navigation.CourseTocDbLoader"%>
<%@page import="blackboard.data.navigation.CourseToc"%>
<%@page import="blackboard.platform.plugin.PlugInUtil"%>
<%@page import="blackboard.base.FormattedText"%>
<%@page import="blackboard.data.content.ContentFile"%>
<%@page import="blackboard.data.content.Content"%>
<%@page import="blackboard.data.content.Content.RenderType"%>
<%@page import="blackboard.persist.content.ContentDbPersister"%>
<%@page import="blackboard.platform.filesystem.UploadUtil"%>
<%@page import="blackboard.platform.filesystem.MultipartRequest"%>
<%@page import="blackboard.persist.Id"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib uri="/bbUI" prefix="bbUI"%>
<%@ taglib uri="/bbData" prefix="bbData"%>

<%
  String URL = PlugInUtil.getUri("cx", "springdemo", "");
%>
<bbNG:learningSystemPage>
	<bbData:context id="cxCtx">
		<bbNG:pageHeader>
			<bbNG:pageTitleBar title="添加测验"></bbNG:pageTitleBar>
		</bbNG:pageHeader>
		<form
			action="<%=URL%>item/create?course_id=<%=cxCtx.getCourseId().toExternalString()%>&content_id=<%=cxCtx.getContentId().toExternalString()%>"
			method="post">
			<bbNG:dataCollection>
				<bbNG:step title="测验信息">
					<bbNG:dataElement isRequired="true" label="名称">
						<input type="text" name="name" id="name" size="45" maxlength="64" />
					</bbNG:dataElement>
					<bbNG:dataElement isRequired="true" label="描述">
						<textarea rows="3" cols="10" name="body"></textarea>
					</bbNG:dataElement>
				</bbNG:step>
				<bbNG:stepSubmit title="提交" />
			</bbNG:dataCollection>
		</form>
	</bbData:context>
</bbNG:learningSystemPage>