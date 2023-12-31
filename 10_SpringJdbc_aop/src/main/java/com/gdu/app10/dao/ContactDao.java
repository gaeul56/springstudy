package com.gdu.app10.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.gdu.app10.dto.ContactDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ContactDao {

  private final JdbcTemplate jdbcTemplate;
  
  /**
   * 연락처 정보 삽입 메소드<br>
   * @param contactDto 삽입할 연락처 정보(name, tel, email, address)
   * @return insertCount 삽입된 행(Row)의 개수, 1이면 삽입 성공, 0이면 삽입 실패
   */
  public int insert(final ContactDto contactDto) {  // 매개변수 변조를 막기 위해서 final 추가
	//SQL 문을 정의하여 연락처 정보를 추가
    String sql = "INSERT INTO CONTACT_T(CONTACT_NO, NAME, TEL, EMAIL, ADDRESS, CREATED_AT) VALUES(CONTACT_SEQ.NEXTVAL, ?, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'))";
    //JdbcTemplate을 사용하여 SQL을 실행하고 PreparedStatementSetter를 사용하여 매개변수를 설정합니
    
    int insertCount = jdbcTemplate.update(sql, new PreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps) throws SQLException {
        ps.setString(1, contactDto.getName());
        ps.setString(2, contactDto.getTel());
        ps.setString(3, contactDto.getEmail());
        ps.setString(4, contactDto.getAddress());
      }
    });
    return insertCount;
  }
  
  /**
   * 수정 메소드<br>
   * @param contactDto 수정할 연락처 정보(contact_no, name, tel, email, address)
   * @return updateCount 수정된 행(Row)의 개수, 1이면 수정 성공, 0이면 수정 실패
   */
  public int update(final ContactDto contactDto) {
    String sql = "UPDATE CONTACT_T SET NAME = ?, TEL = ?, EMAIL = ?, ADDRESS = ? WHERE CONTACT_NO = ?";
    int updateCount = jdbcTemplate.update(sql, new PreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps) throws SQLException {
        ps.setString(1, contactDto.getName());
        ps.setString(2, contactDto.getTel());
        ps.setString(3, contactDto.getEmail());
        ps.setString(4, contactDto.getAddress());
        ps.setInt(5, contactDto.getContact_no());
      }
    });
    return updateCount;
  }
  
  /**
   * 삭제 메소드<br>
   * @param contact_no 삭제할 연락처 번호
   * @return deleteCount 삭제된 행(Row)의 개수, 1이면 삭제 성공, 0이면 삭제 실패
   */
  public int delete(final int contact_no) {
    String sql = "DELETE FROM CONTACT_T WHERE CONTACT_NO = ?";
    int deleteCount = jdbcTemplate.update(sql, new PreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps) throws SQLException {
        ps.setInt(1, contact_no);        
      }
    });
    return deleteCount;
  }
  
  /**
   * 전체 조회 메소드<br>
   * @return 조회된 모든 연락처 정보(ContactDto)
   */
  public List<ContactDto> selectList() {
    String sql = "SELECT CONTACT_NO, NAME, TEL, EMAIL, ADDRESS, CREATED_AT FROM CONTACT_T ORDER BY CONTACT_NO ASC";
    // JdbcTemplate을 사용하여 SQL을 실행하고 결과를 BeanPropertyRowMapper를 사용하여 ContactDto 객체에 매핑합니다.
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ContactDto.class));
  }
  
  /**
   * 상세 조회 메소드<br>
   * @param contact_no 조회할 연락처 번호
   * @return contactDto 조회된 연락처 정보, 조회된 연락처가 없으면 null 반환
   */
  public ContactDto selectContactByNo(final int contact_no) {
    String sql = "SELECT CONTACT_NO, NAME, TEL, EMAIL, ADDRESS, CREATED_AT FROM CONTACT_T WHERE CONTACT_NO = ?";
    // JdbcTemplate을 사용하여 SQL을 실행하고 결과를 BeanPropertyRowMapper를 사용하여 ContactDto 객체에 매핑합니다.
    return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ContactDto.class), contact_no);
  }
  
}