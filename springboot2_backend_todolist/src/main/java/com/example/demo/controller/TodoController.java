package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TodoDTO;
import com.example.demo.service.TodoService;

@RestController //@responseBody + @controller
//@Controller
public class TodoController {
	
	@Autowired 
	private TodoService todoService;
	
	public TodoController() {
		
	}
	
	//http://localhost:8090/todo/all
	//@ResponseBody //json으로 결과값 리턴
	@GetMapping("/todo/all")
	public List<TodoDTO> getList() throws Exception{
		return todoService.search();
	}

}
