package board.spring.mybatis;

import org.springframework.stereotype.Component;

@Component
public class TestDTO {
	String memId;
	String memPw;
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemPw() {
		return memPw;
	}
	public void setMemPw(String memPw) {
		this.memPw = memPw;
	}
}
