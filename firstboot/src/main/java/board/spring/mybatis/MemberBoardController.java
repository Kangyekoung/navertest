package board.spring.mybatis;



import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberBoardController {
	@Autowired
	@Qualifier("memberServiceImpl")
	MemberService service;
	
@GetMapping("/boardlogin")
public String loginform() {
	return "board/loginform";
}
@PostMapping("/boardlogin")
public String loginprocess(String memberid, int pw, HttpSession session) {
	//1> c_member id, pw 확인
	MemberDTO dto = service.oneMember(memberid);
	if(dto != null) {
		if(dto.getPw() == pw) {
			session.setAttribute("sessionid", memberid);
		}
		else {
			//암호가 다르면 암호 다시 입력해야 한다
			//session.setAttribute("sessionid", "암호 다시 입력해야 한다");
		}
	}
	else {
		//id 회원가입부터 해야 한다
		//session.setAttribute("sessionid", "회원가입부터 해야 한다");
	}
	
	return "board/start";
}
@RequestMapping("/test")
public String test() {
	
	return "board/ajaxInput";
}


@RequestMapping("/boardlogout")
public String logout(HttpSession session) {
	if(session.getAttribute("sessionid") != null) {//로그인 먼저 해야 로그아웃 가능한다
		System.out.println(session.getAttribute("sessionid"));
	session.removeAttribute("sessionid");
	}
	return "board/start";
}

//@PostMapping("/ajaxParam")
//@ResponseBody 
//public String ajaxParam(@RequestParam Map<String, Object>  parameters ) throws Exception {
//	System.out.println("ajaxParam");
//	String json = parameters.get("paramList").toString();
//	ObjectMapper mapper = new ObjectMapper();
//	
//	//전달받은 요청데이터를 PlayerDTO 객체저장 ArrayList 변환
//	List list = mapper.readValue(json, new TypeReference<ArrayList>(){} );
//	//TestDTO dto = (TestDTO)list.get(0);
//	
//	System.out.println(list.get(0).getClass());
//	System.out.println(list.get(1).getClass());
//
//	TestDTO dto = mapper.convertValue(list.get(0), TestDTO.class);
//	
//	System.out.println(dto.getMemId());
//	
//	TestDTO2 dto2 =  mapper.convertValue(list.get(1), TestDTO2.class);
//	
//	System.out.println(dto2.getFile1().getName());
//	
//	return "dto.getMemId():" + dto.getMemId() +
//			"dto.getMemPw():" + dto.getMemPw()+
//			"dto2.file1.getOriginalFilename() :" + dto2.getFile1();
//
//}

@PostMapping("/ajaxParam1")
@ResponseBody 
public String ajaxParam1(@RequestPart(name = "key1") TestDTO dto, @RequestPart(name = "key2") TestDTO2 dto2, 
		@RequestPart(name = "key3") MultipartFile multipartFile, @RequestPart(name = "key4") MultipartFile multipartFile4) throws IOException {

	
	String savePath = "c:/kdt/upload/";
	
	MultipartFile file1 =  multipartFile;
	String newFilename1 = null;
	if(!file1.isEmpty()) { //화면에서 파일선택 버튼o
		String originalname1 = file1.getOriginalFilename(); //file1의 파일.확장자 가져오기
		String beforeext1 = originalname1.substring(0, originalname1.indexOf("."));//파일 이름만 추출
		String ext1 = originalname1.substring(originalname1.indexOf("."));//xml //확장자 추출
		//newFilename1 = beforeext1 + "(" + UUID.randomUUID().toString() +  ")" +  ext1; //복사한파일 새로운 이름만들기
		newFilename1 = "test" + "_" + ext1; //test.txt
		file1.transferTo(new java.io.File(savePath + newFilename1 )); //파일 복사
		
		dto2.setFileUrl1("/upload/" +newFilename1 ); ///upload/test.txt
	}//if 
	else {//화면에서 파일선택 버튼x, ->db 이미 존재 or view에서 파일upload자체를 안했던가
		System.out.println(dto2.getFileUrl1());
	}
	
	return "xxx";
}


}
