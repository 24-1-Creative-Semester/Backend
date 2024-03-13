package sejong_team.matching.Entity;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class User { // 이거 이름 User로 변경
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // 이거 추가
    private String name;
    private String major;
    private String status;
    private String grade;

    public User(Long userId, String name, String major, String status, String grade) {
        this.userId = userId;
        this.name = name;
        this.major = major;
        this.status = status;
        this.grade = grade;
    }
}

