package com.example.spring02_todolist.controller;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import com.example.spring02_todolist.dto.TodoDTO;
import com.example.spring02_todolist.repository.TodoRepository;
import com.example.spring02_todolist.service.TodoService;
import lombok.extern.slf4j.Slf4j;

/*
[TodoTbl API URI 설계]
조회   /todo/all                 GET
등록   /todo                     POST
수정   /todo                     PUT
삭제   /todo/{id}                DELETE
*/


/*
 [HTTP 상태 코드]
  상황	                            코드	        의미
내부 서버 오류 (예: 롤백된 DB 트랜잭션)	500	        Internal Server Error
클라이언트 잘못 (필드 누락 등)	        400	        Bad Request
요청한 자원이 없음 (삭제 대상 없음 등)	404	        Not Found
 */


//다른 도메인에 대한 접근 허용
//@CrossOrigin(origins= {"http://127.0.0.1:3000", "http://172.16.133.6"})
@CrossOrigin("*")

@Slf4j
@RestController
public class TodoController {

    private final TodoRepository todoRepository;
	
	//DI(Dependency Injection): 의존성 주입
	@Autowired
	private TodoService todoService;

	public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;

    }
	
	@GetMapping(value="/todo/all")
	public ResponseEntity<List<TodoDTO>> getList()  throws Exception{
		//log.info("getList()=>{}", todoService.search());
		//return new ResponseEntity<List<TodoDTO>>(todoService.search(),HttpStatus.OK);
		return ResponseEntity.ok(todoService.search());
	}
	
	//{"completed":0, "todoname":"잠자기"}
		@PostMapping(value="/todo")
		public ResponseEntity<HashMap<String, String>> postTodo(@RequestBody TodoDTO dto) {
			try {
				todoService.insert(dto);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("createDate", new Date().toString());
				map.put("message", "Insert OK");
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));
				
				//return new ResponseEntity<HashMap<String, String>>(map, headers, HttpStatus.OK);
				return ResponseEntity.ok().headers(headers).body(map);
			}catch(Exception e) {
				HashMap<String, String> errorMap = new HashMap<String, String>();
				errorMap.put("createDate", new Date().toString());
				errorMap.put("message", "등록 실패: " + e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
			}
		}

	@PutMapping(value="/todo")
	public ResponseEntity<Map<String, String>> putTodo(@RequestBody TodoDTO dto){
		Map<String, String> response = new HashMap<>();
		try {
			dto.setCompleted(dto.getCompleted() == 0 ?1 : 0);
			todoService.update(dto);
			response.put("id",String.valueOf(dto.getId()));
			return ResponseEntity.ok(response);
		}catch(Exception e) {
			response.put("status", "error");
			response.put("messasge", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
	}
	
	// http://localhost:8090/todo/2
		@DeleteMapping(value = "/todo/{id}")
		public ResponseEntity<Void> deleteTodo(@PathVariable("id") int id) {
			try {
				todoService.delete(id);
				// RESTful 방식에서 삭제 성공 시 일반적으로 반환 없이 상태 코드만 응답 (204 No Content 응답)을 사용
				return ResponseEntity.noContent().build();
			} catch (Exception e) {
				//삭제할 ID가 없을 경우:404 not found
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		}
}


