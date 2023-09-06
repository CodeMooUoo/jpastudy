package kr.co.spboot.repository;

import org.springframework.stereotype.Repository;

import kr.co.spboot.entity.JpaEntity;
@Repository																	  //연동 할 Entity(VO) 이름, Entity의 프라이머리 키 타입
public interface JpaRepository extends org.springframework.data.jpa.repository.JpaRepository<JpaEntity, String> {
//JpaEntity save(JpaEntity entity); // 데이터베이스에 엔티티를 저장하는 메서드 필요 없다.

}
