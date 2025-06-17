package com.example.spring02_todolist.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Table(name="todotbl")
@Entity
public class TodoEntity {
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_seq_generator")
	@SequenceGenerator(name="todo_seq_generator", sequenceName="todotbl_id_seq", allocationSize=1)
	private int id;//primary key
	private int completed; //할 일이 완료되었는지 여부를 나타내는 필드입니다 (예: 0=미완료, 1=완료).
	private String todoname;
}


/*
@GeneratedValue:
JPA에서 엔티티의 기본 키 생성을 자동화하기 위해 사용되는 어노테이션입니다. 
strategy = GenerationType.SEQUENCE:
기본 키 생성을 데이터베이스 시퀀스를 통해 수행하도록 지정합니다. 


Oracle 데이터베이스는 시퀀스를 지원하며, `@GeneratedValue(strategy = GenerationType.SEQUENCE)`를 사용하면 데이터베이스의 시퀀스를 사용하여 각 엔티티 인스턴스에 고유한 ID 값을 할당합니다. 
@SequenceGenerator:
@GeneratedValue와 함께 사용되어 시퀀스의 이름, 초기값, 증가값 등을 설정할 수 있습니다. 필요에 따라 @SequenceGenerator를 사용하여 시퀀스를 정의해야 합니다. 
위 예시에서 member_seq_generator는 MEMBER_SEQ라는 시퀀스를 참조하며, allocationSize = 1은 한 번에 하나씩 시퀀스 값을 가져오도록 설정합니다. 
*/



