<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        table * {margin:5px;}
        table {width:100%;}
    </style>
</head>
<body>
        
    <jsp:include page="../common/header.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>

            <a class="btn btn-secondary" style="float:right;" href="list.bo">목록으로</a>
            <br><br>

            <table id="contentArea" algin="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${b.boardTitle}</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${b.boardWriter}</td>
                    <th>작성일</th>
                    <td>${b.createDate}</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan="3">
                    	<c:choose>
	                    	<c:when test="${empty b.originName }">첨부파일이 없습니다.</c:when>
	                        <c:otherwise>
	                        <a href="${b.changeName}" download="${b.originName}">${b.originName}</a>
	                        </c:otherwise>
	                    </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px;">${b.boardContent }</p></td>
                </tr>
            </table>
            <br>
			<c:if test="${loginUser.userId eq b.boardWriter}">
            <div align="center">
                <a class="btn btn-primary" onclick="postFormSubmit(1);">수정하기</a>
                <a class="btn btn-danger" onclick="postFormSubmit(2);">삭제하기</a>
            </div>
            <br><br>
            
            <form id="postForm" action="" method="post">
                <input type="hidden" name="bno" value="${b.boardNo}">
                <input type="hidden" name="filePath" value="${b.changeName}">
            </form>
            
            <script>
            	const postFormSubmit = (num) => {
                
            		if(num == 1){ // 수정하기 버튼 클릭시 num == 1: updateForm.bo
            			$('#postForm').attr('action','updateForm.bo').submit();
            		}else { // 삭제하기 버튼 클릭시 num == 2: delete.bo
            			$('#postForm').attr('action', 'delete.bo').submit();
                    }
            	}
            </script>
			</c:if>
            <table id="replyArea" class="table" align="center">
                <thead>
                    <tr>
                        <c:choose>
                            <c:when test="${empty loginUser}"> <!-- 로그인 전 -->
                                    <th colspan="2">
                                        <textarea class="form-control" id="content" cols="55" rows="2" style="resize:none; width:100%;" readonly>로그인한 사용자만 이용 가능한 서비스입니다. 로그인 후 이용 바랍니다.</textarea>
                                    </th>
                                 <th style="vertical-align:middle">
                                    <button class="btn btn-secondary" disabled>등록하기</button>
                                </th>
                            </c:when>
                            <c:otherwise> <!-- 로그인 후 -->
                                <th colspan="2">
                                    <textarea class="form-control" id="content" cols="55" rows="2" style="resize:none; width:100%;"></textarea>
                                </th>
                                <th style="vertical-align:middle">
                                    <button class="btn btn-secondary" onclick="addReply();">등록하기</button>
                                </th>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    </tr>
                    <tr>
                        <td colspan="3">댓글(<span id="rcount">0</span>)</td>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <br><br>

    </div>
    <script>
        $(() => {
            selectReplyList();
        });

        const selectReplyList = () => {
            $.ajax({
                url: 'rlist.bo',
                data: {bno: ${b.boardNo}},

                success: (res) => {
                    // console.log(res);

                    let resultStr = '';

                    res.forEach(el => {
                        resultStr += '<tr>'
                                        +'<td>'+ el.replyWriter +'</td>'
                                        +'<td>'+ el.replyContent +'</td>'
                                        +'<td>'+ el.createDate +'</td>'
                                    +'</tr>'
                    });
                    $('#replyArea>tbody').html(resultStr);

                    $('#rcount').text(res.length);
                },

                error: () => {
                    console.log('AJAX 통신 처리 에러');
                }
            })
        };

        const addReply = () => {
            // required 속성은 form 태그 내에서만 유효하다.
            if($('#content').val().trim().length !== 0){
                $.ajax({
                    
                    url: 'rinsert.bo',
                    data: {refBno: ${b.boardNo},
                           replyWriter:'${loginUser.userId}',
                           replyContent: $('#content').val()}, // ajax 요청 또한 커맨드객체방식 가능 (키값을 필드명과 맞춰준다.)
                    success: (res) => {
                        console.log(res)
                        if(res === "success"){
                            // 댓글 작성 성공 시 새로이 댓글 리스트를 불러올 것
                            selectReplyList();

                            // 댓글 작성 창 초기화 효과
                            $('#content').val('');
                        }
                    },

                    error: () => {
                        console.log('댓글 작성 요청 실패');
                    }
                });
            }else{
                alertify.alert('댓글 작성 실패','한 글자 이상 들어가야 합니다.');
                $('#content').focus();
            }
        }
    </script>
    <jsp:include page="../common/footer.jsp" />
    
</body>
</html>