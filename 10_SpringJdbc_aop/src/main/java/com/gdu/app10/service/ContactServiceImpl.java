package com.gdu.app10.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gdu.app10.dao.ContactDao;
import com.gdu.app10.dto.ContactDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor  // private final ContactDao contactDao;에 @Autowired를 하기 위한 코드이다.
@Service  // ContactService 타입의 객체(Bean)을 Spring Container에 저장한다.
public class ContactServiceImpl implements ContactService {

  private final ContactDao contactDao; //@RequiredArgsConstructor 어노테이션을 사용하여 자동으로 생성자 주입코드가 생성됩니다.
  
  @Override
  public int addContact(ContactDto contactDto) { // 연락처 정보를 추가하는 메소
    int addResult = contactDao.insert(contactDto);
    return addResult;
  }

  @Override
  public int modifyContact(ContactDto contactDto) { // 연락처 정보를 수정하는 메서
    int modifyResult = contactDao.update(contactDto);
    return modifyResult;
  }

  @Override
  public int deleteContact(int contact_no) { // 연락처 정보를 삭제하는 메서드
    int deleteResult = contactDao.delete(contact_no);
    return deleteResult;
  }

  @Override
  public List<ContactDto> getContactList() { // 연락처 목록을 조회하는 메서드
    return contactDao.selectList();
  }

  @Override
  public ContactDto getContactByNo(int contact_no) { //특정 연락처를 조회하는 메서드
    return contactDao.selectContactByNo(contact_no);
  }
  
  @Override
	public void txTest() {
		// TODO Auto-generated method stub
		//AOP를 활용한 트랜잭션 처리 테스트 메소드
	  
	  	//"성공1개 + 실패1개" DB처리를 동시에 수행했을 때 모두 실패로 되는지 확인하기
	  	
	  	// 성공
	  	contactDao.insert(new ContactDto(0, "이름", "전화번호","이메일", "주소", null));
	  	
	  	//실패
	   contactDao.insert(new ContactDto()); //의도적으로 오류를 발생시키기위해 ContactDto를 전달하지 않고 ContactDao.insert를 호출합니다.
	  	
	}

}