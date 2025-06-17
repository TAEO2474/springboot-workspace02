package com.example.spring02_todolist.dto;

import com.example.spring02_todolist.entity.TodoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class TodoDTO {
	private int id;
	private int completed; 
	private String todoname;
	
	public TodoEntity toEntity() {
		return TodoEntity.builder()
				.id(id)
				.completed(completed)
				.todoname(todoname)
				.build();
	
	}
	
	public static TodoDTO toDTO(TodoEntity todoEntity) {
	    return TodoDTO.builder()
	        .id(todoEntity.getId())
	        .completed(todoEntity.getCompleted())
	        .todoname(todoEntity.getTodoname())
	        .build();
	}
	}
	

