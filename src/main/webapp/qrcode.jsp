<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
   		<title>走走听听</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="走走听听" />
		<meta name="keywords" content="走走听听" />
		
		<!-- 腾讯统计 -->
        <script type="text/javascript" src="http://tajs.qq.com/stats?sId=56227192" charset="UTF-8"></script>
        
        <!-- 百度统计 -->
		<script>
		var _hmt = _hmt || [];
		(function() {
		  var hm = document.createElement("script");
		  hm.src = "//hm.baidu.com/hm.js?1edd9987b50a5037be723752a7f6dd01";
		  var s = document.getElementsByTagName("script")[0]; 
		  s.parentNode.insertBefore(hm, s);
		})();
		</script>
	</head>
  
	<body>
 		<img src="/generateqrcode" />
 		
 		<button type="button" onclick="downloadqrcode(this);">下载二维码</button>

 		<script type="text/javascript">
	 		function downloadqrcode(button) {
	 			window.location="/downloadqrcode";
	 		}
 		</script>
 	</body>
</html>
