package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TodoDAO;
import com.example.demo.dto.TodoDTO;

@Service 
public class TodoServiceImp implements TodoService {

	@Autowired //TodoDAO의 타입으로 되어있는 DAO의 TodoDAO가 자동적으로 연결됨
	private TodoDAO todoDAO;
	
	public TodoServiceImp() {
		
	}

	@Override
	public List<TodoDTO> search() throws Exception {

		return todoDAO.getTodoList();
	}

}
