package kr.co.spboot.controller;

import java.io.Console;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.spboot.entity.JpaEntity;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/test")
public class JpaController {

	private kr.co.spboot.repository.JpaRepository repso;
	
    @Autowired
    public JpaController(kr.co.spboot.repository.JpaRepository jpaRepository) {
        this.repso = jpaRepository;
    }
	
	@RequestMapping("/insert") // http://localhost:9000/test/insert?id=idnumber&pwd=pwdnumber
	public void insertJpa(@RequestParam("id") String id, @RequestParam("pwd") String pwd) {
		JpaEntity vo = new JpaEntity();
		vo.setId(id);
		vo.setPwd(pwd);
		repso.save(vo); //리파지토리에서 상속한 JPA인터페이스가 save를 가지고 있다.
	}
	
	@RequestMapping("/get") // http://localhost:9000/test/get
		//Optional<JpaEntity> => 1개만 찾을 때
	public Optional<JpaEntity> getJpa(@RequestParam("id") String id/* <---requestparam 이해하기*/) { //@RequestParam을 사용할 경우 파라미터를 보내주지 않으면 에러 발생
	Optional<JpaEntity> idprint = repso.findById(id); //mybatis의 selectone과 동일하다
	//여러개 찾을 때
	//List<JpaEntity> idprint = repso.findAll(); //mybatis의 selectlist와 동일하다
	return idprint;	
	}
	
	
	
    @RequestMapping("/update") // http://localhost:9000/test/update?id=asd&newPwd=123
    public void updateJpa(@RequestParam("id") String id, @RequestParam("newPwd") String newPwd) {
        Optional<JpaEntity> optionalEntity = repso.findById(id);
        if (optionalEntity.isPresent()) { //optionalEntity.isPresent() => null이 아닐 경우 true / null일 경우 false null이 아니면 if문이 실행된다. 
            JpaEntity entity = optionalEntity.get(); // findById로 찾은 데이터 값을 추출한다.
            System.out.println("entity"+entity);
            entity.setPwd(newPwd);
            repso.save(entity); //데이터가 있을 경우 save 사용 시 update가 된다 / 없으면 데이터 저장
        } else {
            // ID에 해당하는 엔티티가 없는 경우 예외 처리
            throw new IllegalArgumentException("Invalid ID: " + id);
        }
    }
    
    @RequestMapping("/delete") // http://localhost:9000/test/delete
    public void deleteJpa(@RequestParam("id") String id) {
        Optional<JpaEntity> optionalEntity = repso.findById(id);
        if (optionalEntity.isPresent()) {
            JpaEntity entity = optionalEntity.get();
            repso.delete(entity);
        } else {
            throw new IllegalArgumentException("Invalid ID: " + id);
        }
    }
}
