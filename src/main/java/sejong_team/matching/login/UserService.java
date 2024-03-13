package sejong_team.matching.login;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sejong_team.matching.Entity.User;
import sejong_team.matching.Repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserService(UserRepository userRepository, ObjectMapper objectMapper){
        this.userRepository=userRepository;
        this.objectMapper=objectMapper;
    }

    @Transactional
    public void saveUserFromJsonResponse(String responseBody, Long userId){
        boolean isUserExists = userRepository.existsById(userId); // spring data jpa 쿼리 메서드 existsByUserId
        if (isUserExists){
            return;
        }
        try {
            JsonNode jsonResponse = objectMapper.readTree(responseBody);

            String name = jsonResponse.get("result").get("body").get("name").asText();
            String major = jsonResponse.get("result").get("body").get("major").asText();
            String grade = jsonResponse.get("result").get("body").get("grade").asText();
            String status = jsonResponse.get("result").get("body").get("status").asText();

            User user = new User(userId, name, major, status, grade); // user entity 생성

            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("사용자 정보 저장 중 오류가 발생했습니다.");
        }
    }
}
