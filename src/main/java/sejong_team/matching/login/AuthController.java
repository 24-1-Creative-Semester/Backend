package sejong_team.matching.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {
    private static final String apiUrl = "https://auth.imsejong.com/auth?method=ClassicSession";

    private final RestTemplate restTemplate;
    private final UserService userService;
    private final ObjectMapper mapper;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequestDto request) throws JsonProcessingException {
        String id = request.getId();
        String pw = request.getPw();
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id", id);
        multiValueMap.add("pw", pw);
        log.info("multivaluemap = {}", multiValueMap);


       try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl,request,String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String responseBody = responseEntity.getBody();
                JsonNode jsonNode = mapper.readTree(responseBody);
                boolean isAuthenticated = jsonNode.get("result").get("is_auth").asBoolean();
                if (responseBody != null && isAuthenticated) {
                    // 로그인 성공 시, 사용자 정보 저장
                    Long userId = Long.parseLong(request.getId());
                    userService.saveUserFromJsonResponse(responseBody,userId);
                    return "로그인 성공!";
                } else {
                    return "로그인 실패!";
                }
            } else {
                return "로그인 실패!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "로그인 처리 중 오류가 발생했습니다.";
        }
    }

}