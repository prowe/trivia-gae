<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html >
<html>
<head>
<%@ include file="../includes/headSection.jsp" %>

<title><spring:message code="brand.title" /> - History</title>
</head>
<body>
	<%@ include file="../includes/navbar.jsp" %>
	
	<div id="main-body">
		<h1>Question History</h1>
		<table class="table history-table">
			<thead>
			
			</thead>
			<tbody>
				<c:forEach items="${userQuestionList}" var="uq">
					<tr class="${uq.correct ? 'correct' : 'incorrect'}">
						<td><i class="${uq.correct ? 'fa fa-thumbs-up' : 'fa fa-thumbs-down'}"></i>
							<a class="detail-link" href="#detailModal_${uq.contest.contestId}" data-toggle="modal"><c:out value="${uq.contest.question}" /></a>
							
							<div class="modal fade" id="detailModal_${uq.contest.contestId}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
							  <div class="modal-dialog">
							    <div class="modal-content">
							      <div class="modal-header">
							        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
							      </div>
							      <div class="modal-body">
							        ...
							      </div>
							      <div class="modal-footer">
							        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
							        <button type="button" class="btn btn-primary">Save changes</button>
							      </div>
							    </div><!-- /.modal-content -->
							  </div><!-- /.modal-dialog -->
							</div><!-- /.modal -->	
						</td>
						<td>
							<c:forEach items="${uq.contest.possibleAnswers}" var="ans">
								<span class="answer ${(uq.contest.correctAnswer == ans) ? 'correct-answer' : ''} ${(uq.choosenAnswer == ans) ? 'choosen-answer' : ''}"><c:out value='${ans}' /></span>
							</c:forEach>
						</td>
						<td>${uq.contest.endTime}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<%@ include file="../includes/footer.jsp" %>
</body>
</html>