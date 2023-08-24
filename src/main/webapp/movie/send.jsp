<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	function send() {
		//선택한 혈액형의 정보를 서버로 전송
		//혈액형에 대한 판단 결과를 요청
		let form=document.querySelector("form");
		
		form.action="/movie.do";
		form.method="post";
		form.submit();
		
	}
	
</script>
<body>

	<form>
		<select name="movie">
			<option value="">영화를 선택헤</option>
			<option value="슬램덩크">슬램덩크</option>
			<option value="오펜하이머">오펜하이머</option>
			<option value="해리포터">해리포터</option>
			<option value="스파이더맨">스파이더맨</option>
		</select>
	</form>

	<button onClick="send()">결과보기!</button>
</body>
</html>
