$(document).ready(function(){

    if($("#login")){
        $("#login").click(function(){
            $("#loginForm").submit();
        });
    }

    if($("#logOutLink")){
         $("#logOutLink").click(function(){
             $("#logoutForm").submit();
         });
    }

    $("button").click(function() {
         $("#log").html($(this).filter(":checked").val() + " is checked!");
    });

});