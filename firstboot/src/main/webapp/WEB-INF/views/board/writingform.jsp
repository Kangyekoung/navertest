<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="js/jquery-3.6.4.min.js"></script>
<script>

</script>
</head>
<body>
<!--  form 요청파라미터명 = dto 멤버변수명 =  테이블 컬럼명 동일 -->
<form action="boardwrite" method=post enctype="multipart/form-data" >
제목 : <input type=text name="title" ><br>
내용 :<textarea rows="5" cols="50" name="contents">
 여기에 내용을 입력하세요.
</textarea><br>
사진 :<input type=file name="file1" ><br>
작성자 : <input type=text name="writer" value="${sessionid }" readonly><br>
글암호 : <input type=text name="pw"><br>
<input type=submit value="글저장">
</form>


</form>
</body>
</html>