package com.example.shop.board.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.shop.board.dto.BoardDTO;
import com.example.shop.board.dto.PageDTO;
import com.example.shop.board.service.BoardService;
import com.example.shop.common.file.FileUpload;

//@CrossOrigin(origins = {"http://localhost:3000"})
@CrossOrigin("*")

@RestController
public class BoardController {

	@Autowired // setter 만들 필요없이 자동 매핑
	private BoardService boardService;

	@Value("${spring.servlet.multipart.location}")
	private String filePath; // 넘어오는 첨부파일을 value의 주소에 저장하겠다는 뜻(application properties)

	@Autowired
	private PageDTO pdto;

	private int currentPage;

	public BoardController() {

	}

	// @PathVariable => HTTP요청 URI의 일부분을 변수로 사용하기 위해 사용됨(URI변수를 매개변수로 사용)

	// http://localhost:8090/board/list/1

	@GetMapping("/board/list/{currentPage}")
	public Map<String, Object> listExecute(@PathVariable("currentPage") int currentPage, PageDTO pv) {
		Map<String, Object> map = new HashMap<>();

		int totalRecord = boardService.countProcess();
		if (totalRecord >= 1) {
			// if (pv.getCurrentPage() == 0)
			this.currentPage = currentPage;
			// else
			// this.currentPage = pv.getCurrentPage();

			this.pdto = new PageDTO(this.currentPage, totalRecord);

			map.put("aList", boardService.listProcess(this.pdto));
			map.put("pv", this.pdto);
		}
		return map;
	}// end listExecute()

	// 게시글 작성
	// @RequestBody :json => 자바객체
	// @ResponseBody : 자바객체 => json
	// @PathVariable : /board/list/:num => /board/list/{num}
	// @RequestParam : /board/list?num=value => /board/list?num=1 => /board/list
	// multipart/form-data로 넘어올 때 @requestBody 선언 없이 그냥 받음 (BoardDTO dto 앞에)

	@PostMapping("/board/write")
	public String writeProExecute(BoardDTO dto, PageDTO pv, HttpServletRequest req, HttpSession session) {
		MultipartFile file = dto.getFilename(); // 파일 업로드

		// System.out.println("file:" + file.getOriginalFilename());

		if (file!=null && !file.isEmpty()) { // 첨부파일이 있으면...
			UUID random = FileUpload.saveCopyFile(file, filePath); // 첨부파일에 대한 정보 가져옴
			dto.setUpload(random + "_" + file.getOriginalFilename());

		}

		dto.setIp(req.getRemoteAddr());

		// AuthInfo authInfo = (AuthInfo)session.getAttribute("authInfo");
		// dto.setMemberEmail(authInfo.getMemberEmail());

		boardService.insertProcess(dto);

		// 답변글이면
		if (dto.getRef() != 0) {
			// ratt.addAttribute("currentPage", pv.getCurrentPage()); // 답변글일때만 현재 페이지 값을
			// 넘겨주면 됨
			return String.valueOf(pv.getCurrentPage());
		} else {
			return String.valueOf(1);
		}

		// return "redirect:/board/list.do";
	}

	// 게시물 내용 가져오기
	@GetMapping("/board/view/{num}")
	public BoardDTO viewExecute(@PathVariable("num") int num) {

		return boardService.contentProcess(num); // 여기서 반환된 결과가 BoardDTO 객체여야함

	}

	// 게시물 수정하기
	@PutMapping("/board/update") // 수정은 put
	public void updateExecute(BoardDTO dto, HttpServletRequest request) throws IllegalStateException, IOException {
		MultipartFile file = dto.getFilename(); // BoardDTO 객체에서 첨부파일 정보 가져오기
		if (file!=null && !file.isEmpty()) { // 첨부파일이 없거나 존재한다면 
			UUID random = FileUpload.saveCopyFile(file, filePath);
			dto.setUpload(random + "_" + file.getOriginalFilename());
			// d:\\download\\temp 경로에 첨부파일 저장
			file.transferTo(new File(random + "_" + file.getOriginalFilename())); // new File()은 새로운 객체를 생성하기 위해 사용됨
		}

		boardService.updateProcess(dto, filePath); // 게시글 정보를 업데이트 / BoardDTO 객체와 파일 경로(filePath)를 인자로 전달

	}

	// 게시물 삭제
	@DeleteMapping("/board/delete/{num}")
	public void deleteExecute(@PathVariable("num") int num, HttpServletRequest request) {
		boardService.deleteProcess(num, filePath); // 첨부파일이 삭제되었는지까지 확인하기

	}

	// 첨부파일 다운로드 받기
	@GetMapping("/board/contentdownload/{filename}")
	public ResponseEntity<Resource> downloadExecute(@PathVariable("filename") String filename) throws IOException {
		
		String fileName = filename.substring(filename.indexOf("_") + 1);
		
		//파일명이 한글일 때 인코딩 작업을 한다.
		String str = URLEncoder.encode(fileName, "UTF-8");
		//원본 파일명에서 공백이 있을 때, +로 표시가 되므로 공백으로 처리해줌
		str = str.replaceAll("\\+", "%20");
		Path path = Paths.get(filePath+"\\" + filename);
		Resource resource = new InputStreamResource(Files.newInputStream(path));
		System.out.println("resource:" + resource.getFilename());
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+str+";")
				.body(resource);
				

	}

}// end class
