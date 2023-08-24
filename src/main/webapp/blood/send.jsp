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
		
		form.action="/blood.do";
		form.method="post";
		form.submit();
		
	}
	
</script>
<body>

	<form>
		<select name="blood">
			<option value="">혈액형을 선택헤</option>
			<option value="A">A형</option>
			<option value="B">B형</option>
			<option value="O">O형</option>
			<option value="AB">AB형</option>
		</select>
	</form>

	<button onClick="send()">결과보기!</button>
</body>
</html>
