package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.TodoDTO;

@Mapper
@Repository
public interface TodoDAO {
	public List<TodoDTO> getTodoList() throws Exception; 
	//getTodoList()호출하게 되면 자동적으로 mapper에 설정되어있는 id와 같은 쿼리를 실행하게 됨
}
