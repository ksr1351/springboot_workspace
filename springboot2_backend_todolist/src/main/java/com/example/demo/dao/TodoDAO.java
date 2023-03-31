package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.TodoDTO;

@Mapper
@Repository//데이터 액세스에 사용됨 
public interface TodoDAO {
	//getTodoList()호출하게 되면 자동적으로 mapper에 설정되어있는 id와 같은 쿼리를 실행하게 됨
	public List<TodoDTO> getTodoList() throws Exception; 
	public int insertTodoList(TodoDTO dto) throws Exception;//굳이 TodoDTO dto 쓰지 않아도 됨
	public int updateTodoList(TodoDTO dto) throws Exception; 
	public int deleteTodoList(int id) throws Exception;
}
