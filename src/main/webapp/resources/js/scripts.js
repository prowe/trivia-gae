

$(new function(){
	$('.signout-button').click(function(){
		$(this).parents('FORM').submit();
	});
});