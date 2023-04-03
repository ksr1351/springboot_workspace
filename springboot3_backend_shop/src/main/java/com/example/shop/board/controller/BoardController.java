package com.example.shop.board.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.shop.board.dto.BoardDTO;
import com.example.shop.board.dto.PageDTO;
import com.example.shop.board.service.BoardService;
import com.example.shop.common.file.FileUpload;

@RestController
public class BoardController {

	@Autowired // setter 만들 필요없이 자동 매핑
	private BoardService boardService;
	
	@Value("${spring.servlet.multipart.location")
	private String filename; //넘어오는 첨부파일을 value의 주소에 저장하겠다는 뜻(application properties)

	@Autowired
	private PageDTO pdto;

	private int currentPage;

	public BoardController() {

	}

	// http://localhost:8090/board/list/1

	@GetMapping("/board/list/{currentPage}")
	public Map<String, Object> listExecute(@PathVariable("currentPage") int currentPage, PageDTO pv) {
		Map<String, Object> map = new HashMap<>();

		int totalRecord = boardService.countProcess();
		if (totalRecord >= 1) {
			// if (pv.getCurrentPage() == 0)
			this.currentPage = 1;
			// else
			// this.currentPage = pv.getCurrentPage();

			this.pdto = new PageDTO(this.currentPage, totalRecord);

			map.put("aList", boardService.listProcess(this.pdto));
			map.put("pv", this.pdto);
		}
		return map;
	}// end listExecute()

	
	
	// 게시글 작성
	//@RequestBody :json => 자바객체 
	//@ResponseBody : 자바객체 => json
	//@PathVariable : /board/list/:num => /board/list/{num}
	//@RequestParam : /board/list?num=value   =>  /board/list?num=1  => /board/list
	//multipart/form-data로 넘어올때 @requestBody 선언 없이 그냥 받음 (BoardDTO dto 앞에)
	
	@PostMapping("/board/write")
	public String writeProExecute(BoardDTO dto, PageDTO pv, HttpServletRequest req, HttpSession session) {
		MultipartFile file = dto.getFilename(); // 파일 업로드

		//System.out.println("file:" + file.getOriginalFilename());
		
		if (!file.isEmpty()) { // 첨부파일이 있으면...
			UUID random = FileUpload.saveCopyFile(file, req); // 첨부파일에 대한 정보 가져옴
			dto.setUpload(random + "_" + file.getOriginalFilename());

		}

		dto.setIp(req.getRemoteAddr());

		// AuthInfo authInfo = (AuthInfo)session.getAttribute("authInfo");
		// dto.setMemberEmail(authInfo.getMemberEmail());

		boardService.insertProcess(dto);

		//답변글이면
		if (dto.getRef() != 0) {
			//ratt.addAttribute("currentPage", pv.getCurrentPage()); // 답변글일때만 현재 페이지 값을 넘겨주면 됨
			return String.valueOf(pv.getCurrentPage());
		}else {
			return String.valueOf(1);
		}

		//return "redirect:/board/list.do";
	}

}// end class
