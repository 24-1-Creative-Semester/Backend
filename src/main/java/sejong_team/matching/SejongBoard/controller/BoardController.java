package sejong_team.matching.SejongBoard.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sejong_team.matching.SejongBoard.entity.Board;
import sejong_team.matching.SejongBoard.repository.BoardRepository;
import sejong_team.matching.SejongBoard.service.BoardService;

import java.util.List;
import java.util.Optional;

@Controller
public class BoardController {

    @Autowired
   private BoardService boardService;

    //여기 리포지토리 만들어줄 때 오토와일드 붙여야함
    @Autowired
    private BoardRepository boardRepository;

    /*
    * BoardNotFoundException 클래스 생성:

BoardNotFoundException이라는 이름의 클래스를 직접 만들거나, RuntimeException을 상속받는 새로운 예외 클래스를 만듭니다.
예외 클래스는 적절한 메시지와 생성자를 포함해야 합니다.
*
* -> 이거 특정 게시글 조회에서 예외처리 클래스를 따로 만들어준거임 제미나이가 만들어줌
* RuntimeException은 Java에서 발생할 수 있는 모든 예외의 기본 클래스입니다.*/
    /*
    //예외 잡아주는 클래스
    public class BoardNotFoundException extends RuntimeException { //->찐 메소드

        public BoardNotFoundException() {
            super("게시글 정보를 찾지 못했습니다.");
        }

    }
*/

    //게시글 작성폼->근데 이건 걍 html로 있어야 하는 거 아닌가 짜피 게시글 작성폼은 프론트에서 만드는거니까..?
    //얘는 필요없다
    /*@GetMapping("/board/write")
    public String boardWriteForm(){

        return "boardwrite";
    }*/

    //게시물 작성폼에서 작성 후 넘어오는 거 처리해주는 메소드
    //시발 어케했냐 진짜 개스트레스
    /*@PostMapping("/board/pro")
    public String boardWritePro(Board board){

        boardService.write(board);

        return "";
    }*/


    //게시물 작성폼에서 작성 후 넘어오는 거 처리해주는 찐 메소드
    @PostMapping("/board/writepro")
    public ResponseEntity<Board> createBoard(@RequestBody Board board) throws Exception{ //->찐 메소드
        // 데이터 유효성 검사

        // DB 저장
        //boardRepository.save(board);
        boardService.write(board);

        // 성공 응답
        return ResponseEntity.ok(board);
    }
/*
    //게시글 리스트 쫙 불러오는 메소드
    @GetMapping("/board/list")
    public String boardList(Model model){

        model.addAttribute("list",boardService.boardList());//이걸 실행하면 리스트가 반환이 되는데 그 반환된 리스트를 리스트라는 이름으로 받아서 넘긴다
        return "boardlist";
    }*/

    //게시글 리스트 쫙 불러오는 메소드(검색 기능도 포함됨)
    @GetMapping("/board/list") //->찐 메소드
    public ResponseEntity<List<Board>> boardList(@RequestParam String searchKeyword) {//->찐 메소드

        //-> /board/list?searchKeyword= 이런식으로 감
        // 서치키워드가 아무것도 없을 때(그니까 아무것도 검색 안 했을 때)->리스트 전체 반환
        // 서치키워드가 포함된 게시물을 찾을 수 없을 때->빈 리스트 반환
        // 서치키워드가 포함된 게시물이 있을 때->그 리스트들만 모아서 반환
        List<Board> list=boardService.boardSearchList(searchKeyword);

       return ResponseEntity.ok(list);
        //난 페이징 처리를 안 했기 때문에 Page가 아닌 List로 해줘야함 즉 리포지토리나 서비스, 컨트롤러 전부 Page->List로 반환 타입 변경
    }

    //태그 기능
    @GetMapping("/board/tag")
    public ResponseEntity<List<Board>> boardTag(@RequestParam String tagWord){

        List<Board> list=boardService.boardSearchList(tagWord);

        return ResponseEntity.ok(list);
    }



    //특정 게시글 조회 메소드 ->http://localhost:8080/board/view?id=120 이런 식으로 id가 120인 게시글을 불러올 때 쓰는 메소드임
    @GetMapping("/board/view")
    public ResponseEntity<Board> boardView(@RequestParam Integer id) { //->찐 메소드

        /*
        try {
            Board board = boardService.boardView(id);
            return ResponseEntity.ok(board);
        } catch (BoardNotFoundException e) { //
            // 게시글 정보를 찾지 못할 경우 처리
            throw new BoardNotFoundException();
            //return ResponseEntity.notFound().build();//->흐음 이거 메시지나 이런 걸로 띄우는 게 나은 것 같기도? 어라 근데 return을 메시지로 띄우면 프론트한테 메시지가 가는 거 아님? 조회니까 데이터를 return으로 돌려줘야하는 거 아닌가..헷갈리니 보림이한테 물어보자
        }
*/
        Optional<Board> board=boardRepository.findById(id);

        if(board.isPresent()){
            Board realboard=boardService.boardView(id);
            return ResponseEntity.ok(realboard);
        }
        else{
            return ResponseEntity.notFound().build();//정보 찾기 실패했을 때 404 not found 처리
        }

    }
/*
    @GetMapping("/board/view") //locaclhost:8080//board/view?id=1  ->여기서 1이 매개변수 인티저 id로 들감,id가 보드뷰로 들어가서 아이디 1인 게시글 불러와줌
    public String boardView(Model model, Integer id){//다시 넘겨줄 때는 매개변수를 모델로 해줘야함


        //단축키: 컨+알+B=그 함수로 바로 이동
        model.addAttribute("board",boardService.boardview(id));
        return "boardview";
    }
    */
/*

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){

        boardService.boardDelete(id);

        return "redirect:/board/list";
    }
*/

    //ResponseEntity.ok().build(); ->Spring Boot에서 HTTP 200 OK 응답 코드를 생성하고 응답 본문 없이 클라이언트에 전달하는 데 사용됩니다.
    //여기서 응답 본문 주고 싶으면 build()없애고 body("ㅋㅋ옛다") 이거 넣으면 됨
    /*return ResponseEntity.ok().body("게시글 삭제 성공"); 이 코드에서 오류가 떴는데 error: incompatible types: inference variable T has incompatible bounds
      return ResponseEntity.ok().body("�Խñ� ���� ����"); 이게 문제래

->원인:오류는 제네릭 타입 변수 T의 상한과 하한이 서로 호환되지 않을 때 발생합니다. 즉, T 타입이 두 개 이상의 상충되는 제약 조건을 만족해야 하는 상황입니다.
->해결방법:ResponseEntity.ok().body() 메서드의 타입 파라미터 명시: ResponseEntity.ok().body() 메서드의 타입 파라미터를 명시적으로 지정하여 T 타입의 상한을 제한할 수 있습니다.
String 타입으로 변환: 반환하려는 값을 String 타입으로 변환하여 T 타입의 하한을 충족시킬 수 있습니다.
코드 수정: 상황에 따라 코드를 수정하여 제약 조건을 완화하거나 다른 방식으로 응답을 구성할 수 있습니다.*/
   @GetMapping("/board/delete") //http://localhost:8080/board/delete?id=65
    public ResponseEntity<String> boardDelete(Integer id) { //->찐 메소드

        try {
            boardService.boardDelete(id);
            return ResponseEntity.ok().body("게시글 삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("게시글이 존재하지 않습니다.");
        }

    }

    //수정 페이지에서 기존 게시글을 불러와주는 메소드
    @GetMapping("/board/modify/{id}")
    public ResponseEntity<Board> boardModify(@PathVariable("id") Integer id, Model model) { //->찐 메소드

        model.addAttribute("board", boardService.boardView(id));
        Board board=boardService.boardView(id);

        return ResponseEntity.ok(board);
    }
/*
    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
    }*/

    //시이바 드디어 됐다 이거 @RequestBody Board board 붙여주니까 됨 원래 코드는 이랬음
    //진짜 개스트레스 원래 코드도 Board board-> @RequestBody Board board 이렇게 어노테이션 하나 붙여주니 바로 해결됨 근데 왜 처음에 강의에서는 된거냐 정말 의문
    //@RequestBody 어노테이션은 클라이언트 요청 본문에서 JSON 데이터를 추출하여 Board 객체에 바인딩하도록 지시합니다.
    /*@PostMapping("/board/update/{id}")
public ResponseEntity<String> boardUpdate(@PathVariable("id") Integer id, Board board) {

    Board boardTemp = boardService.boardView(id);
    boardTemp.setTitle(board.getTitle());
    boardTemp.setContent(board.getContent());

    boardService.update(boardTemp);

    return ResponseEntity.ok("수정이 완료되었습니다.");
}*/
    //수정 후 디비 저장하는 메소드
   @PostMapping("/board/update/{id}")
    public ResponseEntity<Board> boardUpdate(@PathVariable("id") Integer id, @RequestBody Board board) throws Exception { //->찐 메소드


        Board oldboard=boardService.boardView(id);
        oldboard.setTitle(board.getTitle());
        oldboard.setContent(board.getContent());

        boardService.write(oldboard);

        return ResponseEntity.ok(oldboard);//이건 수정 후 게시글을 불러와주는건데 굳이 필요없으면 걍 "게시글 수정 완료" 메시지 띄워도 될 듯 프론트랑 이야기 해보자
    }

    //게시물 등록/삭제/수정/조회 완료
    // 검색, 스크랩, 참여인원, 태그만 하면 됨

//-------------------------------------------------------------
    /*
    @PostMapping("/board/writepro")
    public ResponseEntity<Board> createBoard(@RequestBody Board board,@RequestParam("file") MultipartFile file) throws Exception{ //->찐 메소드
        // 데이터 유효성 검사

        // DB 저장
        //boardRepository.save(board);
        boardService.write(board,file);

        // 성공 응답
        return ResponseEntity.ok(board);
    }*/
    /*
     //게시글 리스트 쫙 불러오는 메소드
    @GetMapping("/board/list") //->찐 메소드
    public ResponseEntity<List<Board>> boardList() {
        List<Board> list = boardService.boardList();
        return ResponseEntity.ok(list);
    }*/

/*
    //특정 게시글 조회 메소드 ->http://localhost:8080/board/view?id=120 이런 식으로 id가 120인 게시글을 불러올 때 쓰는 메소드임
    @GetMapping("/board/view")
    public ResponseEntity<Board> boardView(@RequestParam Integer id) { //->찐 메소드

        try {
            Board board = boardService.boardView(id);
            return ResponseEntity.ok(board);
        } catch (BoardNotFoundException e) { //
            // 게시글 정보를 찾지 못할 경우 처리
            return ResponseEntity.notFound().build();//->흐음 이거 메시지나 이런 걸로 띄우는 게 나은 것 같기도? 어라 근데 return을 메시지로 띄우면 프론트한테 메시지가 가는 거 아님? 조회니까 데이터를 return으로 돌려줘야하는 거 아닌가..헷갈리니 보림이한테 물어보자
        }
    }*/
    /*
    @GetMapping("/board/delete") //http://localhost:8080/board/delete?id=65
    public ResponseEntity<String> boardDelete(Integer id) { //->찐 메소드

        try {
            boardService.boardDelete(id);
            return ResponseEntity.ok().body("게시글 삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("게시글이 존재하지 않습니다.");
        }

    }*/
/*
    //수정 페이지에서 기존 게시글을 불러와주는 메소드
    @GetMapping("/board/modify/{id}")
    public ResponseEntity<Board> boardModify(@PathVariable("id") Integer id, Model model) { //->찐 메소드

        model.addAttribute("board", boardService.boardView(id));
        Board board=boardService.boardView(id);

        return ResponseEntity.ok(board);
    }*/
    /*
     @PostMapping("/board/update/{id}")
    public ResponseEntity<Board> boardUpdate(@PathVariable("id") Integer id, @RequestBody Board board,@RequestParam("file") MultipartFile file) throws Exception { //->찐 메소드


        Board oldboard=boardService.boardView(id);
        oldboard.setTitle(board.getTitle());
        oldboard.setContent(board.getContent());

        boardService.write(oldboard,file);

        return ResponseEntity.ok(oldboard);//이건 수정 후 게시글을 불러와주는건데 굳이 필요없으면 걍 "게시글 수정 완료" 메시지 띄워도 될 듯 프론트랑 이야기 해보자
    }*/
}
