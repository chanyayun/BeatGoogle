<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BallBallGo</title>
</head>
<body bgColor="#FCF3CF">
	<center>
		<img src="https://upload.cc/i1/2019/01/17/1bhVAn.png" width="200"
			height="130">
		<p>
			<img src="https://i.imgur.com/DC0bTMt.png" width="200" height="130">
		<p>
		<form action='${requestUri}' method='get'>

			<input type='text' name='keyword' placeholder='keyword' />

			<form>
				<select id="kind" name="kind">
					<option value="">None</option>
					<option value="NBA">NBA</option>
					<option value="MLB">MLB</option>
					<option value="NFL">NFL</option>
					<option value="NHL">NHL</option>
				</select> <input type='submit' value='submit'
					style="width: 55px; height: 13px" />
			</form>

			<input type="button" value="NBA"
				onclick="location.href='https://www.nba.com/'"
				style="width: 55px; height: 12px"> <input type="button"
				value="MLB" onclick="location.href='https://www.mlb.com/'"
				style="width: 55px; height: 12px"> <input type="button"
				value="NFL" onclick="location.href='https://www.nfl.com/'"
				style="width: 55px; height: 12px"> <input type="button"
				value="NHL" onclick="location.href='https://www.nhl.com/'"
				style="width: 55px; height: 12px">

		</form>

	</center>
</body>
</html>