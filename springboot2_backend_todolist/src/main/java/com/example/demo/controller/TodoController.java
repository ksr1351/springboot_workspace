package com.example.demo.controller;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TodoDTO;
import com.example.demo.service.TodoService;


@CrossOrigin("*") //프론트에서 어떠한 번호로 요청이 들어와도 모두 허용

//@CrossOrigin({"http://localhost:3000"}) //이 주소에 대한 모든 요청을 모두 허용
@RestController //@responseBody + @controller
//@Controller
public class TodoController {
	
	@Autowired 
	private TodoService todoService;
	
	public TodoController() {
		
	}
	
	//http://localhost:8090/todo/all
	//@ResponseBody //json으로 결과값 리턴
	@GetMapping("/todo/all")//select
	public List<TodoDTO> getList() throws Exception{
		return todoService.search();
	}
	
	
	//insert는 무조건 post 매핑
	//http://localhost:8090/todo
	@PostMapping("/todo") //insert
	public ResponseEntity<Object> postTodo(@RequestBody TodoDTO dto) throws Exception{
		int chk = todoService.insert(dto);
		
		HashMap<String, String> map = new HashMap<>();
		map.put("createDate", new Date().toString());
		map.put("message", "insert Ok");
		map.put("cnt", String.valueOf(chk));
		
		//Http 요청 헤더에 "Content-Type: application/json; charset=UTF-8" 필드를 추가
		HttpHeaders header = new HttpHeaders(); 
		header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		if(chk>=1) {
			//return new ResponseEntity<Object>(HttpStatus.OK);
			//return new ResponseEntity<Object>(map,HttpStatus.OK);
			return new ResponseEntity<Object>(map, header, HttpStatus.OK);
		}else {
			return new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	
	//http://localhost:8090/todo/2/0
	@PutMapping("/todo/{id}/{completed}") //update
	public ResponseEntity<Object> putTodo(@PathVariable("id") int id, 
			@PathVariable("completed") int completed) throws Exception{
		
		TodoDTO dto = new TodoDTO();
		dto.setId(id);
		dto.setCompleted(completed==0 ? 1 : 0);
		todoService.update(dto);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	//http://localhost:8090/todo/1
	@DeleteMapping("/todo/{id}") //delete
	public ResponseEntity<Object> deleteTodo(@PathVariable("id") int id) throws Exception{
		
		todoService.delete(id);
		
		return new ResponseEntity<Object>(HttpStatus.OK);
		
	}

	
	
	
	
	
	
	
	
}//end class

























