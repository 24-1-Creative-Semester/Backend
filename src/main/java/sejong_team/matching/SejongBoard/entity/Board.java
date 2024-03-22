package sejong_team.matching.SejongBoard.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
public class Board {

    //db가 바뀌면 entitiy도 바꿔줘야함 근데 이건 당연함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    /*
    * @Data어노테이션 사용 시 기본적으로 getter, setter, equals, hashCode 메서드가 자동으로 생성되므로
    * 별도로 생성자를 정의할 필요없다고 했으나 특별한 이유 없으면 걍 빈 생성자 하나 넣어주래서 넣었음 그래도 안됨
    * 빈 생성자 없이 @Column(nullable = false) 어노테이션을 사용하면 null 값 할당 오류가 발생합니다.
    * 3번째 문장 제미나이가 알려줌 기억하자 근데 왜 아직도 오류가 나는거냐*/

    private String filename;
    private String filepath;
    //파일은 일단 안 하는걸로 함 근데 필요하면 나중에 수정
    public Board() {
    }
}
