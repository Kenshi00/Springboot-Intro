package hello.hellospring2.repository;

import hello.hellospring2.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
// 순수 Jdbc보다 JdbcTemplate을 이용하면 훨씬 간단하게 작성할 수 있다. 실무에서도 사용중
public class JdbcTemplateMemberRepository implements MemberRepository{

    // 일단 JdbcTemplate 객체 하나 만들어서 메소드들에 계속 사용함
    private final JdbcTemplate jdbcTemplate;

    // 생성자가 딱 하나만 있으면 @Autowired 생략가능
    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        // jdbcTemplate 객체 넘겨서 SimpleJdbcInsert가 쿼리 만들어준다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?",memberRowMapper(),id);
        return result.stream().findAny(); // 전체 리스트 중 id에 맞는거 찾는듯
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?",memberRowMapper(),name);
        return result.stream().findAny(); // 전체 리스트 중 name에 맞는거 찾는듯
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    // jdbcTemplate이 쿼리를 날린 후 그 결과를 받아서 RowMapper로 매핑 후 member 객체 반환
    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) -> {

            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
