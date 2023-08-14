<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="js/jquery-3.6.4.min.js"></script>
<script>
$(document).ready(function(){
	$("#ajaxbtn").on('click', function(){
		let param1 = { "memId":$('#memId').val(), "memPw":$('#memPw').val() };
		let param2 = { "entno":"1111", "fileUrl": $('#fileUrl').val()};	
		let fileImg = $('#file1')[0].files[0];
		let fileImg2 = $('#file2')[0].files[0];
			    
	    let form = new FormData();

	    form.append("key1", new Blob([JSON.stringify(param1)], {type: "application/json"}))
	    form.append("key2", new Blob([JSON.stringify(param2)], {type: "application/json"}))
	    form.append("key3", fileImg);
	    form.append("key4", fileImg2);

	    $.ajax({
	        type: "POST",
	        url: "/ajaxParam1",
	        contentType: false, //중요 : false 로 선언 시 content-type 헤더가 multipart/form-data로 전송되게 함
	        processData: false,	//중요 : false로 선언 시 formData를 string으로 변환하지 않음
	        enctype : 'multipart/form-data',//중요
	        data: form,
	        success: function (response) {
	            alert(response);
	        }
	    });
	});//ajaxbtn5 end
});//ready end
</script>
</head>
<body>
<input type="text" name="memId" id="memId" >
<input type="text" name="memPw" id="memPw" >
<input type="text" name="fileUrl" id="fileUrl">
<img src=${dto2.FileUrl} />
<input type="file" name="file1" id="file1" value="/upload/test.txt">
<input type="file" name="file2" id="file2">
<input type=button value="전송" id="ajaxbtn">
<div id="result"></div>
</body>
</html>