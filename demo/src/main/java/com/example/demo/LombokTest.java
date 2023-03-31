package com.example.demo;

public class LombokTest {
	
	public static void main(String[] args) {
		MemDTO dto = new MemDTO(); //MemDTO 객체 생성 , 아래 코드들은 객체의 필드값을 설정하는 코드
		dto.setName("홍길동");
		dto.setAge(30);
		dto.setLoc("서울");
		
		System.out.printf("%s %d %s\n", dto.getName(), dto.getAge(), dto.getLoc());
		
		MemDTO dto2 = new MemDTO("고수", 25, "경기");
		System.out.printf("%s %d %s\n", dto2.getName(), dto2.getAge(), dto2.getLoc());
	}

}
