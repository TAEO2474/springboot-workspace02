package com.example.spring02_todolist.service;

import java.util.List;

import com.example.spring02_todolist.dto.TodoDTO;

public interface TodoService {
	public List<TodoDTO> search() throws Exception;
	public TodoDTO insert(TodoDTO dto) throws Exception;
	public void update(TodoDTO dto) throws Exception;
	public void delete(int id) throws Exception;
		
}
