<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>走走听听</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="走走听听" />
		<meta name="keywords" content="走走听听" />
	</head>
	<body>
		$!{hello}
		
		#for($!viewSpot : $!viewSpotList) 
			$!{viewSpot.id} -- $!{viewSpot.name}
		#end
	</body>
</html>