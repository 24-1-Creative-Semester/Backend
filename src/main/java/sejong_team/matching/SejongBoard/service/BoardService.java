package sejong_team.matching.SejongBoard.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sejong_team.matching.SejongBoard.entity.Board;
import sejong_team.matching.SejongBoard.repository.BoardRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public class BoardNotFoundException extends RuntimeException {

        public BoardNotFoundException() {
            super();
        }

        public BoardNotFoundException(String message) {
            super(message);
        }

    }

    public class BoardDeleteException extends RuntimeException {

        public BoardDeleteException() {
            super();
        }

        public BoardDeleteException(String message) {
            super(message);
        }

    }
    //글 작성 처리(글 작성 후 디비에 저장)
   public void write(Board board) throws Exception{ //->수정 전까지 찐 메소드

        boardRepository.save(board);
    }
    //컨트롤+스페이스 누르면 자동완성
    //게시글 리스트 보여주는 거(게시글 리스트 쫙 불러와줌)
    public List<Board> boardList(){

        return boardRepository.findAll();
    }

    //특정 게시글 불러오기
    public Board boardView(Integer id){

        return boardRepository.findById(id).get();
    }

    //특정 게시글 삭제
    /*
    * 오류:여기서 db에 있는 아이디가 아닌데도 id=??? 이렇게 값을 넣어서 삭제하면 게시글 삭제 성공이 뜸
    * -> 이유: 원래 내 코드는 if else 코드가 없었음 걍 바로 파라미터로 id 값 받아서 삭제를 해버림
    * 그렇게 되면 파라미터는 정수 값으로 제대로 들어오니까 db에 있든 없든 제대로 들어왔으니 삭제 처리 메시지를 띄운거임
    * 그러니까 컨트롤러에서는 삭제 처리는 했으니 성공으로 메시지 보낸거고 근데 이제 파라미터로 ?id or ?id= 이딴 식으로 제대로
    * 정수 안 넣어주면 이때 오류로 인식하고 게시글 존재하지 않는다고 한거임
    * 요약:. 원래 코드에서는 deleteById 메서드에 파라미터로 전달된 id가 실제로 DB에 존재하는지 확인하지 않고 바로 삭제를 진행
    *
    * 해결: 서비스에서 삭제를 위해 파라미터로 값이 들어왔으면 이 파라미터인 id가 db에 먼저 존재하는지 확인을 해야함
    * 그러기 위해서는 1. Optional 클래스 이용해서 id에 해당하는 엔터티 존재 여부 확인 후 없으면 예외 처리
    * 1번에 해당하는 코드는 내가 지금 쓴 코드임
    * 2. Optional 쓰지 않고 그냥 내가 만든 클래스 쓸 때 findById()사용 ->물론 이건 Optional에서도 쓰긴 함
    * findById()메소드는 엔터티가 존재하지 않으면 null반환 함
    * 2번에 해당하는 코드:
    * public void boardDelete(Integer id) {

    Board board = boardRepository.findById(id).orElse(null);->옵셔널 안 씀

    if (board != null) {
        boardRepository.deleteById(id);
    } else {
        throw new EntityNotFoundException("게시글이 존재하지 않습니다. (id: " + id + ")");
    }
    * 2번이 훨씬 간편한 듯 하다

}*/
    public void boardDelete(Integer id){ // 게시글 번호가 들어오면 딜리트바이아이디 메소드가 실행되면서 그 아이디를 가진 게시글 삭제해줌

        Optional<Board> board = boardRepository.findById(id);

        if(board.isPresent()){
            boardRepository.deleteById(id);
        } else{
            throw new EntityNotFoundException("게시글이 존재하지 않습니다. (id: " + id + ")");
        }

    }
    /*
    * 원래 코드:
    * public void boardDelete(Integer id){ // 게시글 번호가 들어오면 딜리트바이아이디 메소드가 실행되면서 그 아이디를 가진 게시글 삭제해줌

    boardRepository.deleteById(id);
} */

    public void update(Board board) {

        // 1. 기존 게시글 정보 조회
        Board boardTemp = boardRepository.findById(board.getId()).orElse(null);

        if (boardTemp == null) {
            throw new EntityNotFoundException("게시글이 존재하지 않습니다. (id: " + board.getId() + ")");
        }

        // 2. 제목 및 내용 업데이트
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        // 3. 변경 사항 저장
        boardRepository.save(boardTemp);

    }
/*
    public void write(Board board,MultipartFile file) throws Exception{

        String projectPath=System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";//이렇게 해주면 프로젝트 경로를 변수에 담아줌

        UUID uuid=UUID.randomUUID();

        String fileName=uuid+ "_" + file.getOriginalFilename();

        File saveFile=new File(projectPath,fileName);//파일을 생성해줄건데 경로에 넣어줄거고 이름은 이렇게 담긴다

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);
        boardRepository.save(board);
    }
*/

    public List<Board> boardSearchList(String searchKeyword) {
        return boardRepository.findByTitleContaining(searchKeyword);
    }

}
