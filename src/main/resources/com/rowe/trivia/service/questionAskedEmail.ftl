<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Answer a question</title>
</head>
<body>
	<p>${userQuestion.contest.question?html}</p>
	<ol type="a">
		<#list userQuestion.contest.possibleAnswers as ans>
			<li>${ans}</li>
		</#list> 
	</ol>
	<div>
		<a href="${applicationURL}/questions/${userQuestion.contest.contestId}/answer.html">Answer this question</a>
	</div>
</body>
</html>