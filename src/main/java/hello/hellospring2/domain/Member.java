package hello.hellospring2.domain;

import jakarta.persistence.*;


// @Entity가 붙은 순간 jpa가 관리하는 entity
@Entity
public class Member {
    // ID -> pk , IDENTITY -> DB에 데이터 넣을때 자동으로 id 1씩 증가시켜 주는거
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
