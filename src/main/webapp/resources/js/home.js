$('.question-pane .btn-answer').click(function(event){
	event.preventDefault();
	var selectedBtn = $(this);
	
	var questionPane = selectedBtn.closest('.question-panel');
	$.ajax({
		url: questionPane.data('action'),
		dataType: "json",
		type: "POST",
		data: {
			answer: selectedBtn.attr('value')
		},
		success: function(response, textStatus, jqXHR){
			console.log( "Sample of data:", response);
			
			questionPane.find(".btn-answer[value='" + response.correctAnswer + "']").addClass('correct');
			if(!response.correct){
				selectedBtn.addClass('incorrect');
			}
		},
		error: function(jqXHR, textStatus, errorThrown){
			console.log("Error: ", errorThrown);
		}
	});
});