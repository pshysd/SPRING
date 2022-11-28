package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

@Controller
public class BoardController {

	@Autowired
	BoardService bService;

	@RequestMapping("list.bo")
	public String selectList(@RequestParam(value = "cpage", defaultValue = "1") int currentPage, Model model) {

//		/WEB-INF/views/board/boardListView.jsp

		int listCount = bService.selectListCount();

		int pageLimit = 10; // 페이지 숫자 < [1],[2],[3],[4],[5],[6],[7],[8],[9],[10] >
		int boardLimit = 5; // 한 페이지에 게시글 숫자

		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);

		ArrayList<Board> list = bService.selectList(pi);

		model.addAttribute("list", list);
		model.addAttribute("pi", pi);

		return "board/boardListView";
	}

	@RequestMapping("enrollForm.bo")
	public String enrollForm() {

		return "board/boardEnrollForm";
	}

	/*
	 * * 다중파일 업로드 기능을 구현하려면 -> jsp에서 여러 개의 input type="file" 요소에 모두 동일한 name 속성을 부여
	 * (ex.upfile) -> Controller에서 MultipartFile[] upfile or List<MultipartFile>
	 * upfile
	 */
	@RequestMapping("insert.bo")
	public ModelAndView insertBoard(Board b, MultipartFile upfile, HttpSession session, ModelAndView mv) {

		System.out.println(b);
		System.out.println(upfile);

//		요청시 name 속성과 필드명을 정확하게 맞췄음에도 불구하고 제대로 된 전달값이 안들어옴
//		요청시 분명히 파일을 넘겼음에도 불구하고 upfile 값이 null
//		-> 파일 업로드에 필요한 Spring 라이브러리를 pom.xml 에 추가하지 않았기 때문
//		파일 업로드용 라이브러리 : commons-fileupload, commons-io

//		MultipartFile : 첨부파일을 선택했든 안했든 생성된 객체 (즉, null 이 아님)
//		다만, fileName 필드에 원본명이 있냐 없냐의 차이

//		전달된 파일이 있을 경우 -> 파일명 수정 작업 후 서버로 업로드
//		-> 원본명, 서버에 업로드된 경로를 이어붙이기
		if (!upfile.getOriginalFilename().equals("")) { // 원본명이 빈문자열과 일치하지 않을 경우 == 첨부된 파일이 있을 경우

			/*
			 * * 파일명 수정 작업 후 서버에 업로드 시키기 ex) "flower.png" -> "2022112210405012345.png"
			 * 
			 * 1. 원본파일명 뽑아오기 String originName = upfile.getOriginalFilename(); //
			 * "flower.png"
			 * 
			 * // 2. 시간 형식을 문자열로 뽑아내기 String currentTime = new
			 * SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			 * 
			 * // 3. 뒤에 붙을 5자리 랜덤값 뽑기 int ranNum = (int)(Math.random() * 90000) + 10000; //
			 * 5자리 랜덤값
			 * 
			 * // 4. 원본파일로부터 확장자만 뽑기 String ext =
			 * originName.substring(originName.lastIndexOf(".")); //
			 * 
			 * // 5. 모두 이어 붙이기 String changeName = currentTime + ranNum + ext;
			 * 
			 * // 6. 업로드 하고자 하는 서버의 물리적인 실제 경로 String savePath =
			 * session.getServletContext().getRealPath("/resources/uploadFiles/");
			 * 
			 * // 7. 경로와 수정파일명을 합체 후 파일을 업로드해주기 try { upfile.transferTo(new File(savePath +
			 * changeName)); } catch (IllegalStateException | IOException e) {
			 * e.printStackTrace(); }
			 */
			String changeName = saveFile(upfile, session);

//			8. 원본명, 서버에 업로드된 수정명을 Board b 에 담기
//			-> boardTitle, boardContent, boardWriter 필드에만 값이 담겨있음
//			-> originName, changeName 필드에도 전달된 파일에 대한 정보를 담을 것임!
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName("/resources/uploadFiles/" + changeName);
//			실제 경로도 같이 이어붙일 것 (FILE_PATH 컬럼을 따로 빼두지 않음)
		}

//		넘어온 첨부파일이 있을 경우 b : 제목, 작성자, 내용, 원본파일명, 경로 + 수정파일명
//		넘어온 첨부파일이 없을 경우 b : 제목, 작성자, 내용
		int result = bService.insertBoard(b);

		if (result > 0) { // 성공: 게시글 리스트 ㅔ이지로 url 재요청 (list.bo)

			session.setAttribute("alertMsg", "게시글 작성 완료");

			mv.setViewName("redirect:/list.bo");

			return mv;

		} else { // 실패 -> 에러 페이지로 포워딩

//			mv.addObject("errorMsg", "게시글 작성 실패");
//			mv.setViewName("common/errorPage");

//			addObject 메소드의 반환형을 ModelAndView 타입임
//			-> 다음과 같이 메소드 체이닝도 가능하다.
			mv.addObject("errorMsg", "게시글 작성 실패").setViewName("common/errorPage");

			return mv;
		}

	}

//	현재 넘어온 첨부파일 그 자체를 수정명으로 서버의 폴더에 저장시키는 메소드 (일반메소드)
//	-> Spring 의 Controller 메소드는 반드시 요청을 처리하는 역할이 아니어도 된다!
	public String saveFile(MultipartFile upfile, HttpSession session) {

//		파일명 수정 작업 후 서버에 업로드 시키기
//		예) "flower.png" -> "2022112210405012345.png"
//		1. 원본파일명 뽑아오기
		String originName = upfile.getOriginalFilename(); // "flower.png"

//		2. 시간 형식을 문자열로 뽑아내기
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

//		3. 뒤에 붙을 5자리 랜덤값 뽑기
		int ranNum = (int) (Math.random() * 90000) + 10000; // 5자리 랜덤값

//		4. 원본파일로부터 확장자만 뽑기
		String ext = originName.substring(originName.lastIndexOf(".")); //

//		5. 모두 이어 붙이기
		String changeName = currentTime + ranNum + ext;

//		6. 업로드 하고자 하는 서버의 물리적인 실제 경로
		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");

//		7. 경로와 수정파일명을 합체 후 파일을 업로드해주기
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return changeName;
	}

	@RequestMapping("detail.bo")
	public ModelAndView selectBoard(int bno, ModelAndView mv) {

//		bno에는 상세조회하고자 하는 해당 게시글 번호가 담겨있음

//		1. 해당 게시글 조회수 증가용 서비스 먼저 호출 결과 받기 (update하고 오기)

		int result = bService.increaseCount(bno);

		if (result > 0) { // 성공적으로 조회수 증가가 일어났다면
//			2. 상세조회 요청
			Board b = bService.selectBoard(bno);

//			조회된 데이터를 담아서 board/boardDetailView.jsp로 포워딩
			mv.addObject("b", b).setViewName("board/boardDetailView");
		} else { // 실패

			mv.addObject("errorMsg", "글 상세 조회 요청에 실패했습니다.").setViewName("common/errorPage");
		}
		return mv;

	}

	@RequestMapping("delete.bo")
	public String deleteBoard(int bno, String filePath, HttpSession session, Model model) {

		int result = bService.deleteBoard(bno);

		if (result > 0) { // 삭제 성공

//			첨부파일이 있을 경우 -> 첨부파일도 삭제
//			filePath에는 해당 게시글의 수정 파일명이 들어있음
//			filePath 값이 빈 문자열이 아니라면 첨부파일이 있었던 경우이다
			if (!filePath.equals("")) {
				String realPath = session.getServletContext().getRealPath(filePath);
				new File(realPath).delete();
			}

			session.setAttribute("alertMsg", "성공적으로 게시글이 삭제되었습니다.");

			return "redirect:/list.bo";
		} else { // 삭제 실패
			model.addAttribute("errorMsg", "삭제에 실패했습니다.");

			return "common/errorPage";
		}
	}

	@RequestMapping("updateForm.bo")
	public String updateForm(int bno, Model model) {

		model.addAttribute("b", bService.selectBoard(bno));

		return "board/boardUpdateForm";
	}

	@RequestMapping("update.bo")
	public String updateBoard(Board b, MultipartFile reupfile, HttpSession session, Model model) {

//		새로 넘어온 첨부파일이 있을 경우
		if (!reupfile.getOriginalFilename().equals("")) {

			System.out.println(b);
//			b의 boardNo: 내가 수정하고자 하느 게시글의 번호 (WHERE) 
//			b의 boardTitle: 수정할 제목 (SET)
//			b의 boardContent: 수정할 내용
//			b의 originName: 기존 첨부파일의 원본명
//			b의 changeName: 기존 첨부파일의 수정명

//			1-1. 기존 첨부파일이 있었을 경우 -> 기존 첨부파일을 찾아서 삭제
			if (b.getOriginName() != null) {
				String realPath = session.getServletContext().getRealPath(b.getChangeName());
				new File(realPath).delete();
			}

//			1-2. 새로 넘어온 첨부파일을 서버에 업로드
			String changeName = saveFile(reupfile, session);

//			1-3. b 객체에 새로 넘어온 첨부파일에 대한 원본명, 수정명을 필드에 담기
			b.setOriginName(reupfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);

			/*
			 * b 에 무조건 담겨있는 내용 boardNo, boardTitle, boardContent
			 * 
			 * 추가적으로 고려해야할 경우의 수 1. 새로 첨부된 파일 X, 기존 첨부파일 X -> originName : null, changeName
			 * : null
			 * 
			 * 2. 새로 첨부된 파일 X, 기존 첨부파일 O -> originName : 기존 첨부파일의 원본명 changeName : 기존 첨부파일의
			 * 수정명 + 파일경로
			 * 
			 * 3. 새로 첨부된 파일 O, 기존 첨부파일 X -> originName : 새로 첨부된 파일의 원본명 changeName : 새로 첨부된
			 * 파일의 수정명 + 파일경로
			 * 
			 * 4. 새로 첨부된 파일 O, 기존 첨부파일 O -> 기존의 파일 삭제 -> 새로이 전달된 파일을 서버에 업로드 -> originName :
			 * 새로 첨부된 파일의 원본명 changeName : 새로 첨부된 파일의 수정명 + 파일경로
			 * 
			 */

//			Service 단으로 b 보내기
			int result = bService.updateBoard(b);

			if (result > 0) { // 게시글 수정 성공

				session.setAttribute("alertMsg", "성공적으로 게시글이 수정되었습니다.");

//			게시글 상세보기 페이지로 url 재요청
				return "redirect:/detail.bo?bno=" + b.getBoardNo();

			} else { // 게시글 수정 실패

				model.addAttribute("errorMsg", "게시글 수정 실패");

				return "common/errorPage";

			}
		}
		return "redirect:/list.bo";
	}

	@ResponseBody
	@RequestMapping(value = "rlist.bo", produces = "application/json; charset=utf-8")
	public String ajaxSelectReplyList(int bno) {
//		System.out.println(bno);

		List<Reply> list = bService.selectReplyList(bno);

		return new Gson().toJson(list);
	}
	
	@ResponseBody
	@RequestMapping(value="rinsert.bo", produces="application/json; charset=utf-8")
	public void ajaxInsertReply(Reply r) {
		System.out.println(r);
	}
	
}