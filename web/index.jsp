<%-- 
    Document   : index
    Created on : 4 ส.ค. 2553, 9:28:23
    Author     : Zing
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<p style="color:   #ffffff">${error.message}</p>
<html>
   <head>
	<meta http-equiv="Content-Type" content="text/html" charset=utf-8" />
        <!-- import javascript and CSS-->
        <link type="text/css" href="css/dark-hive/jquery-ui-1.8.2.custom.css" rel="stylesheet" />
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.2.custom.min.js"></script>

        <title>:: Log in Page And Register ::</title>

        <!-- initail value componet for Register -->
        <style type="text/css">
		.toggler { width: 1024px; height: 768px; background-image: url("images/loginpage/loginbg.jpg"); background-position: center;  }/*working area status*/
		#button {  padding: .5em 1em; text-decoration: none; }/*register buttom status*/
                #button2 { padding: .5em 1em; text-decoration: none; }/*log in buttom status*/
                #button3 { padding: .5em 1em; text-decoration: none; }/*log in buttom status*/
		#effect { width: 300px; top: 50px; height: 500px; padding: 0.4em; position: absolute; }/*register input component status*/
                #effect2 { width: 300px; top: 50px; height: 180px; padding: 0.4em; position: absolute; }/*log in input component status*/
		#effect h3 { margin: 0; padding: 0.4em; text-align: center; }
                .leftDiv{ width: 320px; height: 700px; top: 0px; margin: auto;  }/*register working area status*/
                .centerDiv{ width: 384px; height: 700px; top: 0px; margin: auto; }
                .rightDiv{ width: 320px; height: 700px; top: 0px; margin: auto;}/*log in working area status*/
	</style>
        <!-- End Initial -->
	 <!-- use show effect on login button-->
            <!-- regiter project-->
                <script type="text/javascript">
                    showReg=false;
                    $(function() {
                        //run the currently selected effect
                        function runEffect(){
			//get effect type from
                            var selectedEffect = 'slide';
                        //most effect types need no options passed by default
                            var options = {width: 280,height: 185};
			//run the effect
                            if(!showReg)
                            {
                                $("#effect").show(selectedEffect,options,500);
                            }
                                else
                            {
                                var selectedEffect = 'explode';
                                var options = {width: 1,height: 1};
                                $("#effect:visible").hide(selectedEffect,options,300)
                            }
                        };
		//use RegisterEffect
                        $("#button").click(
                            function() {
                                runEffect();
                                showReg=!showReg;
                                return false;
                            }
                        )
                        $("#effect").hide();
                    });
                </script>
            <!-- End of register project-->
            <!-- login project-->
             <script type="text/javascript">
                    show=false;
                    $(function() {
                        //run the currently selected effect
                        function runEffect(){
			//get effect type from
                            var selectedEffect = 'fold';
                        //most effect types need no options passed by default
                            var options = {width: 280,height: 185};
			//run the effect
                            if(!show)
                            {
                                $("#effect2").show(selectedEffect,options,500);
                            }
                                else
                            {
                                var selectedEffect = 'explode';
                                var options = {width: 1,height: 1};
                                $("#effect2:visible").hide(selectedEffect,options,300)
                            }
                        };
		//use loginEffect
                        $("#button2").click(
                            function() {
                                runEffect();
                                show=!show;
                                return false;
                            }
                        )
                        $("#effect2").hide();
                    });
                </script>
          <!-- End of login project-->
          <!-- datedicker view -->
                <script type="text/javascript">
                // Calendar for register
                    $(function() {
                        $('#datepicker').datepicker({
                             yearRange: '1910:1995' ,
                             changeMonth: true,
                             changeYear: true
                        });
                    });
                </script>
          <!-- End of datedicker view -->
        <!-- End of show effect on login button-->

        <!--ดักค่าว่างจากtextboxตอนLogin-->
        <script type="text/javascript">
            function fncSubmitLogin(){

            if(document.formLogin.email.value=="" && document.formLogin.password.value=="")
                {
                    alert('กรุณากรอก e-mail และ password');
                    document.formLogin.email.focus();
                    document.formLogin.password.focus();
                    return false;
                }

            if(document.formLogin.email.value=="")
                {
                    alert('กรุณากรอก e-mail');
                    document.formLogin.email.focus();
                    return false;
                }

            if(document.formLogin.password.value=="")
                {
                    alert('กรุณากรอก password');
                    document.formLogin.password.focus();
                    return false;
                }

            
                document.formLogin.submit;
                
            }
        </script>
        <!--End of ดักค่าว่างจากtextboxตอนLogin-->
        
        <script type="text/javascript">
            function fncSubmitRegister(){

            if(document.formRegister.name.value=="" )
                {
                    alert('กรุณากรอกข้อมูลให้ครบ');
                    document.formRegister.name.focus();
                    return false;
                }

            if(document.formRegister.email.value=="" )
                {
                    alert('กรุณากรอกข้อมูลให้ครบ');
                    document.formRegister.email.focus();
                    return false;
                }

            if(document.formRegister.conferm_email.value=="" )
                {
                    alert('กรุณากรอกข้อมูลให้ครบ');
                    document.formRegister.conferm_email.focus();
                    return false;
                }

            if(document.formRegister.conferm_email.value=="" )
                {
                    alert('กรุณากรอกข้อมูลให้ครบ');
                    document.formRegister.conferm_email.focus();
                    return false;
                }

            if(document.formRegister.password.value=="" )
                {
                    alert('กรุณากรอกข้อมูลให้ครบ');
                    document.formRegister.password.focus();
                    return false;
                }

            if(document.formRegister.Confirm_password.value=="" )
                {
                    alert('กรุณากรอกข้อมูลให้ครบ');
                    document.formRegister.Confirm_password.focus();
                    return false;
                }

            if(document.formRegister.birthDate.value=="" )
                {
                    alert('กรุณากรอกข้อมูลให้ครบ');
                    document.formRegister.birthDate.focus();
                    return false;
                }

                document.formRegister.submit;

            }
        </script>
    <!-- Dialog Box Script  -->
       <script type="text/javascript">

    $(function() {
     
        //stop showing dialog
        $("#dialog-confirm").dialog({autoOpen: false});
        // event when you click on dog picture
        $("#button").click(function() {
            $("#dialog-confirm").dialog({autoOpen: true});
        });
        // status of dialog
		$("#dialog-confirm").dialog({
			resizable: true,
                        width:600,
			height:600,
			modal: true,
			buttons: {
                            ok: function() {
                                $(this).dialog('close')
                            }
			}
		});
	});
	</script>
        <!-- End of dialog script -->
        
   </head>
        <body bgcolor="black">
		<div class="toggler" style="margin:auto;" >
                    <!-- Preparing dog dialog box -->
                        <div id="dialog-confirm" title="Term of use">
                            <p style="width: 500px; height: 500px; position: absolute;">
                    
                            <%@include file="termofuse.jsp" %>
                            </p>
                        </div>
        <!-- End Preparing dog dialog box -->
                    <table border="0">
                        <tr>
                            <td>
                                <!-- Register Div -->
                                    <div id="effect" class="ui-widget-content ui-corner-all">
                                        <h3 class="ui-widget-header ui-corner-all">:: Register ::</h3>
                                        <form action="Register" method="post" name="formRegister">
                                            <table border="0" cellpadding="5px" >
                                            <tr>
                                                <td>Username :</td>
                                                <td><input type="text" name ="name" size="23"></td>
                                            </tr>
                                            <tr>
                                                <td>E-mail :</td>
                                                <td><input type="text" name="email" size="23"></td>
                                            </tr>
                                            <tr>
                                              <td>Confirm E-mail :</td>
                                              <td><input type="text" name="conferm_email" size="23"></td>
                                            </tr>
                                            <tr>
                                                <td>Password :</td>
                                                <td><input type="password" name="password" size="23"></td>
                                            </tr>
                                            <tr>
                                                <td>Confirm Password :</td>
                                                <td><input type="password" name="Confirm_password" size="23"></td>
                                            </tr>
                                            <tr>
                                                <td>BirthDay: </td>
                                                <td><input type="text" name="birthDate" id="datepicker"/></td>
                                            </tr>
                                            <tr>
                                                <td>sex : </td>
                                                <td>
                                                    <input type="radio" name="sex" value="male" checked />Male
                                                    <input type="radio" name="sex" value="female" />Female
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td>
                                                    <input type="submit" value="Register now" onclick="return fncSubmitRegister()">
                                                </td>
                                            </tr>
                                            </table>
                                        </form>
                                </div>
                            <div class="leftDiv">
                                <a href="#" id="button" class="ui-state-default ui-corner-all">Register</a>
                            </div>
                         <!-- End Register Div -->
                        </td>
                        <td>
                            <div class="centerDiv"></div>
                        </td>
                        <!-- log in Div -->
                        <td>
                            <div id="effect2" class="ui-widget-content ui-corner-all">
				<h3 class="ui-widget-header ui-corner-all">:: Login ::</h3>
                        <form action="Login" method="post" name ="formLogin" >
                                    <table border="0" cellpadding="5px" >
                                     <tr>
                                         <td>E-mail :</td>
                                         <td><input type="text" name ="email" size="23"></td>
                                     </tr>
                                     <tr>
                                        <td>Password :</td>
                                        <td><input type="password" name="password" size="23"></td>
                                     </tr>
                                     <tr>
                                         <td></td>
                                         <td>
                                             <input type="submit" value="Log in" onclick="return fncSubmitLogin()">
                                         </td>
                                     </tr>
                                    </table>
                                </form>
			</div>
                    <div class="rightDiv">
                        <%if(request.getSession().getAttribute("player")==null){%>
                        <a href="#" id="button2" class="ui-state-default ui-corner-all">log in</a>
                        <%}else{%>
                        <a href="Game.jsp" id="button3" class="ui-state-default ui-corner-all">Go to Game</a>
                        <%}%>
                    </div>
                    <!-- End log in Div -->
                    </td>
                  </tr>
                 </table>
            </div>
	</body>
</html>
