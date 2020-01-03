<%@page errorPage="Error.jsp" %>
<jsp:include page="CheckAuthJSP"  flush="true" />
<html>
<head>
<title>Samples : Simple Upload</title>
<script type="text/javascript" language="JavaScript">
function check()
{
    var ext = document.upform.uploadfile.value;
    ext = ext.substring(ext.length-3,ext.length);
    ext = ext.toLowerCase();
    if(ext != 'jpg')
    {
        alert('You selected a .'+ext+' file; please select a .jpg file instead!');
        return false;
    }
    else
        return true;
}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body bgcolor="#FFFFFF" text="#000000">
    <img  src="/GrianIsland/images/avatar/${player.avatar}" />
<form method="post" action="ChangeAvatar" name="upform" enctype="multipart/form-data" onsubmit="return check();">
  <table width="60%" border="0" cellspacing="1" cellpadding="1" align="center" class="style1">
    <tr>
      <td align="left"><b>Select a file to upload :</b></td>
    </tr>
    <tr>
      <td align="left">
        <input type="file" name="uploadfile" size="50">
        </td>
    </tr>
    <tr>
      <td align="left">
		<input type="hidden" name="todo" value="upload">
        <input type="submit" name="Submit" value="Upload">
        <input type="reset" name="Reset" value="Cancel">
        </td>
    </tr>
  </table>
</form>
</body>
</html>
