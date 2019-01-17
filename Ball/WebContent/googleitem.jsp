<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BallBallGo</title>
</head>
<body bgColor="#FCF3CF">
	<b style="font-size: 16px; color: rgb(163, 148, 128);"><%="[Related keywords]"%></b>
	<br>
	<%
		String[] reList = (String[]) request.getAttribute("related");
		for (int i = 0; i < reList.length; i++) {
	%>
	<h style="font-size:16px ; "><%=reList[i]%></h>
	<br>
	<%
		}
	%>
	<br>

	<b style="font-size: 16px; color: rgb(163, 148, 128);"><%="[Suggested results]"%></b>
	<br>
	<%
		String[][] orderList = (String[][]) request.getAttribute("result");
		for (int i = 0; i < 10 && i < orderList.length; i++) {
	%>
	<h style="font-size:16px ;"><%=orderList[i][0]%></h>
	<br>
	<a href='<%=orderList[i][1]%>'><%=orderList[i][1]%></a>
	<br>
	<b style="font-size: 16px; color: rgb(128, 138, 135);"><%=orderList[i][2]%></b>
	<br>
	<h style="font-size:16px ;color: rgb(128, 138, 135);"><%=orderList[i][3]%></h>
	<br>
	<br>
	<%
		}
	%>
</body>
</html>