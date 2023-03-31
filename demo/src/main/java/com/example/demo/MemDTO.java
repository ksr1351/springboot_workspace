package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//생성자를 자동으로 생성해주는 기능을 제공
@NoArgsConstructor //파라미터를 받지 않는 기본 생성자를 자동으로 생성(파라미터가 없는 생성자)
@AllArgsConstructor //클래스에 선언되어있던 모든 필드들을 파라미터로 받는 생성자를 자동으로 생성(모든 멤버변수를 제공하는 생성자)

@Getter //getter
@Setter //setter
public class MemDTO {
	private String name;
	private int age;
	private String loc;

	
}
