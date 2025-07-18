package com.kiylab.spring_basic.config;

import com.kiylab.spring_basic.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.generic.Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
public class DatasourceTest {
  @Autowired
  private DataSource dataSource;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  public void test() {
    log.info("{}", dataSource);
    log.info("{}", jdbcTemplate);

  }
  @Test
  public void testGetMembers() {
    jdbcTemplate.queryForList("select * from tbl_member").forEach(System.out::println);
  }

  @Test
  public void testIter() {
    List<Integer> list = List.of(2,3,4,1);
    Iterator<Integer> iterator = list.iterator();
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }
  }

  @Test
  public void testCursor() throws SQLException {  //접속을 했으면 sql문장을 만들어야함.
    Connection conn = jdbcTemplate.getDataSource().getConnection();
    PreparedStatement ps = conn.prepareStatement("select * from tbl_member where id = ? ");  //?는 지시자임
    ps.setString(1, "sae");
    ResultSet rs = ps.executeQuery();
    while(rs.next()) {  // 이게 커서를 하나씩 옮기게하는
//      log.info(rs.getCursorName());
      int no = rs.getInt(1); // 컬럼 1번을 가져오라는
      String name = rs.getString("name");
      log.info("{} {}", no, name);

    }
  }
  @Test
  public void testCallFunction() {
    int result = jdbcTemplate.queryForObject("select add_num(?,?)", int.class, 10,20 );
    log.info("{}",result);
  }

  @Test
  public void testCallProcedure() {
    SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate);
    call.withCatalogName("pbl").withProcedureName("list_members");

    Map<String, Object> map = call.execute();
    log.info("{}", map);
  }

  @Test
  public void testCallProcedure2() {
    List<Member> members = jdbcTemplate.query("call list_members()",new RowMapper<Member>() {
      @Override
      public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Member.builder().id(rs.getString("id")).name(rs.getString("name")).build();
      }
    });
  }

  @Test
  public void testCallProcedure3() {
    SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withCatalogName("pbl")
            .withProcedureName("find_member_by_id").declareParameters(new SqlParameter("v_id", Types.VARCHAR));
    Map<String, Object> map = call.execute("sae");
    log.info("{}", map);
  }

  @Test
  public void testCallProcedure4() {
    SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withCatalogName("pbl")
            .withProcedureName("find_name_by_id").declareParameters(new SqlParameter("v_id", Types.VARCHAR), new SqlOutParameter("m_name", Types.VARCHAR));
    Map<String, Object> map = call.execute("sae");
    log.info("{}", map.get("m_name"));
  }

  @Test
  public void testCallProcedure5() {
    SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withCatalogName("pbl")
            .withProcedureName("add_five")
            .declareParameters(new SqlOutParameter("num", Types.INTEGER));
    Map<String, Object> map = call.execute(10);
    log.info("{}", map);
  }
}
