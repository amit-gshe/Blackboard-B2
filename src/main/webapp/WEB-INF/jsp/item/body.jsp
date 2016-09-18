<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<div style="margin-top: 15px; word-wrap: break-word;">
	<video height="480" width="100%" id="player" src="${url}" controls="controls" autoplay="autoplay">
 		</video>
	<script src="https://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
	<script type="text/javascript">
		$(function(){
			console.log("jQuery loaded!");
		})
<!--  		解决jQuery冲突 -->
		$.noConflict();
	</script>
</div>