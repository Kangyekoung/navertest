package board.spring.mybatis;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardController {
	@Autowired
	@Qualifier("boardServiceImpl")
	BoardService service;
	
	@RequestMapping("/")
	public String start() {
		return "board/start";
	}
	
	@GetMapping("/boardwrite")
	public String writeform() {
		return "board/writingform";
	}
	
	@PostMapping("/boardwrite")
	public ModelAndView writeprocess(BoardDTO dto) throws IllegalStateException, IOException{
		String savePath = "c:/kdt/upload/";
		
		MultipartFile file1 =  dto.getFile1();
		String newFilename1 = null;
		if(!file1.isEmpty()) {
			String originalname1 = file1.getOriginalFilename(); //file1의 파일.확장자 가져오기
			String beforeext1 = originalname1.substring(0, originalname1.indexOf("."));//파일 이름만 추출
			String ext1 = originalname1.substring(originalname1.indexOf("."));//xml //확장자 추출
			//newFilename1 = beforeext1 + "(" + UUID.randomUUID().toString() +  ")" +  ext1; //복사한파일 새로운 이름만들기
			newFilename1 = dto.getWriter() + "_" + ext1;
			file1.transferTo(new java.io.File(savePath + newFilename1 )); //파일 복사
			
			dto.setFile_url(newFilename1);
		}//if 
		
	
		int insertcount = service.insertBoard(dto);
		ModelAndView mv = new ModelAndView();
		mv.addObject("insertcount", insertcount);
		//mv.setViewName("board/writeresult");
		//mv.setViewName("redirect:/boardlist");//모델2개 전달받는 리스트뷰  
		mv.setViewName("board/start");
		return mv;
	}
	
	@RequestMapping("/boardlist")
	public ModelAndView boardlist(@RequestParam(value="page", required=false, defaultValue="1") int page) {
		//전체 게시물 갯수 (9) 가져와서 몇페이지까지 (1페이지당 4개 게시물) -  1 2 3
		int totalBoard = service.getTotalBoard();
	
		//page번호 해당 게시물 4개 리스트 조회 
		int limitcount = 4;
		int limitindex = (page-1)*limitcount;
		int limit [] = new int[2];
		limit[0] = limitindex;
		limit[1] = limitcount;
		
		/*  1.  List<BoardDTO> -- 서비스 메소드 (limitindex,  limitcount);
		 *  2.  board-mapping.xml
		 * select * from board order by writingtime desc limit 배열[0],배열[1]
		 *  3. 1번 결과를 모델로 추가 저장
		 *  4. 뷰 3번 모델 저장 테이블 태그 출력
		 * */
		
		List<BoardDTO> list = service.boardList(limit);
				
		ModelAndView mv = new ModelAndView();
		mv.addObject("totalBoard", totalBoard);
		mv.addObject("boardList", list);
		
		mv.setViewName("board/list");
		return mv;
	}
	
	@RequestMapping("/boarddetail")
	public ModelAndView boarddetail(int seq) {
		ModelAndView mv = new ModelAndView();
		BoardDTO dto = service.updateViewcountAndGetDetail(seq);
		mv.addObject("detaildto", dto);
		mv.setViewName("board/detail");
		return mv;
	}
	
	@RequestMapping("/boarddelete")
	public String boarddelete(int seq){
		service.deleteBoard(seq);
		//return "board/list"; //list.jsp 이동(모델 전달 없다 - nULLpOINTER.. 500) 
		return "redirect:/boardlist"; // boardlist 매핑 메소드 실행 호출
	}

	@RequestMapping("/boardupdate")
	public String boardupdate(BoardDTO dto) {
		service.updateBoard(dto);
		return "redirect:/boardlist";
	}
	
	
	@RequestMapping("/boardsearchlist")
	public ModelAndView boardserch(
		@RequestParam(value="item", required = false, defaultValue = "all")	String item,
		@RequestParam(value="word", required = false, defaultValue = "")	String word
		//,
		//@RequestParam(value="page", required = false, defaultValue = "1")	int page
		) {
		
		HashMap map = new HashMap();
		if(item.equals("all")) { item = null;}
		map.put("colname", item);
		map.put("colvalue", "%"+ word + "%");
		//map.put("limitindex", (page-1)*4);
		//map.put("limitcount", 4);
		
		List<BoardDTO> searchlist = service.searchList(map);
		int searchcount = service.getSearchBoard(map);
		
		//System.out.println("searchlist : " + searchlist.get(0).seq);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("boardList", searchlist);
		mv.addObject("totalBoard", searchcount);
		mv.setViewName("board/searchlist");
		return mv;
	}
	
	@RequestMapping("/boardWriterInform")
	public ModelAndView boardWriterInform(int seq) {
		BoardMemberDTO writerdto = service.boardWriterInform(seq);
		System.out.println("seq : " + writerdto.getSeq());
		ModelAndView mv = new ModelAndView();
		mv.addObject("writerdto", writerdto);
		mv.setViewName("board/writerinform");
		return mv;
	}
}







