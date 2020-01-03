<%-- 
    Document   : chooseCard
    Created on : 15 ก.ย. 2553, 13:05:37
    Author     : Zapdos
--%>

<%@page import="sit.int303.grianisland.core.model.Player"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>..:: Choose Card ::..</title>
        <link rel="stylesheet" href="css/galleriffic-2.0/black.css" type="text/css" />
	<link rel="stylesheet" href="css/galleriffic-2.0/galleriffic-5.css" type="text/css" />
        <link rel="stylesheet" type="text/css" href="css/screen.css" media="all" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery.history.js"></script>
	<script type="text/javascript" src="js/jquery.galleriffic.js"></script>
	<script type="text/javascript" src="js/jquery.opacityrollover.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <style type="text/css">
            workingArea{ width: 1020px; height: 760px; top:  8px;    margin: auto; background-color: red;}
            .headerInc{ width: 1020px; height: 160px;   top:  0px; position:relative ;float:left  ;margin: auto; background: grey;}
            .battle{      width: 670px; height: 170px;   top:  0px; float:left; margin: auto; background: #000000; position:relative ;float:left }
            .me{ width: 330px; height: 155px;   top:  0px; float:left; margin: auto; background: #0b93d5; position:relative ;float:left }
            .youOppo{ width: 330px; height: 155px;   top:  0px; float:left; margin: auto; background: #003399; position:relative ;float:left }
            .bDetail{     width: 670px; height: 15px;   top:  0px; float:left; margin: auto; background: #000000; position:relative ;float:left}
            .choose{   width: 670px; height: 330px;   top:  170px; float:left; margin: auto; background: #000000; position:relative ;float:left }
            .puts{      width: 350px; height: 500px;   top:  0px; float:left; margin: auto; background:  #3399FF; position:relative ;float:left }
            .footerInc{ width: 1020px; height: 100px;   top:  0px;  margin: auto; background: grey;        position:relative ;float:left }
            body {background-color: #000;}
        </style>

        <!-- We only want the thunbnails to display when javascript is disabled -->
		<script type="text/javascript">
			document.write('<style>.noscript { display: none; }</style>');
		</script>
        <!-- Config your Gallorize-->
        <script type="text/javascript">
			jQuery(document).ready(function($) {
				// We only want these styles applied when javascript is enabled
				$('div.content').css('display', 'block');

				// Initially set opacity on thumbs and add
				// additional styling for hover effect on thumbs
				var onMouseOutOpacity = 0.67;
				$('#thumbs ul.thumbs li, div.navigation a.pageLink').opacityrollover({
					mouseOutOpacity:   onMouseOutOpacity,
					mouseOverOpacity:  1.0,
					fadeSpeed:         'fast',
					exemptionSelector: '.selected'
				});

				// Initialize Advanced Galleriffic Gallery
				var gallery = $('#thumbs').galleriffic({
					delay:                     2500,
					numThumbs:                 4,
					preloadAhead:              5,
					enableTopPager:            false,
					enableBottomPager:         false,
					imageContainerSel:         '#slideshow',
					controlsContainerSel:      '#controls',
					captionContainerSel:       '#caption',
					loadingContainerSel:       '#loading',
					renderSSControls:          true,
					renderNavControls:         true,
					enableHistory:             true,
					autoStart:                 false,
					syncTransitions:           true,
					defaultTransitionDuration: 900,
					onSlideChange:             function(prevIndex, nextIndex) {
						// 'this' refers to the gallery, which is an extension of $('#thumbs')
						this.find('ul.thumbs').children()
							.eq(prevIndex).fadeTo('fast', onMouseOutOpacity).end()
							.eq(nextIndex).fadeTo('fast', 1.0);

						// Update the photo index display
						this.$captionContainer.find('div.photo-index')
							.html('Photo '+ (nextIndex+1) +' of '+ this.data.length);
					},
					onPageTransitionOut:       function(callback) {
						this.fadeTo('fast', 0.0, callback);
					},
					onPageTransitionIn:        function() {
						var prevPageLink = this.find('a.prev').css('visibility', 'hidden');
						var nextPageLink = this.find('a.next').css('visibility', 'hidden');

						// Show appropriate next / prev page links
						if (this.displayedPage > 0)
							prevPageLink.css('visibility', 'visible');

						var lastPage = this.getNumPages() - 1;
						if (this.displayedPage < lastPage)
							nextPageLink.css('visibility', 'visible');

						this.fadeTo('fast', 1.0);
					}
				});

				/**************** Event handlers for custom next / prev page links **********************/

				gallery.find('a.prev').click(function(e) {
					gallery.previousPage();
					e.preventDefault();
				});

				gallery.find('a.next').click(function(e) {
					gallery.nextPage();
					e.preventDefault();
				});

				/****************************************************************************************/

				/**** Functions to support integration of galleriffic with the jquery.history plugin ****/

				// PageLoad function
				// This function is called when:
				// 1. after calling $.historyInit();
				// 2. after calling $.historyLoad();
				// 3. after pushing "Go Back" button of a browser
				function pageload(hash) {
					// alert("pageload: " + hash);
					// hash doesn't contain the first # character.
					if(hash) {
						$.galleriffic.gotoImage(hash);
					} else {
						gallery.gotoIndex(0);
					}
				}

				// Initialize history plugin.
				// The callback is called at once by present location.hash.
				$.historyInit(pageload, "advanced.html");

				// set onlick event for buttons using the jQuery 1.3 live method
				$("a[rel='history']").live('click', function(e) {
					if (e.button != 0) return true;

					var hash = this.href;
					hash = hash.replace(/^.*#/, '');

					// moves to a new page.
					// pageload is called at once.
					// hash don't contain "#", "?"
					$.historyLoad(hash);

					return false;
				});

				/****************************************************************************************/
			});
		</script>

    </head>
    <jsp:useBean id="player" scope="session" class="Player"/>
    <body>
        <div class="workingArea">
            <div class="headerInc">
                <%@include file="Header.jsp" %>
            </div>
            
            <div class="choose" style="top:20px; margin: 0; padding: 0">
<div class="battle" style="top:0; margin: 0; padding: 0;">

                <div class="me" style="background-color: black;">
                    <img src="images/avatar/${player.avatar}" width="150px" height="150px" align="middle" style="float:right;">
                 </div>
                 <div class="youOppo" style="background-color: black;">
                     <img src="images/avatar/<%=player.getAvatarByName((String)request.getAttribute("opponentname"))%>" width="150px" height="150px">
                 </div>
                 <div class="bDetail"  >
                    <h1>${player.name} VS <%= request.getAttribute("opponentname")%> </h1>
                 </div>
            </div>


                 <font color="gray"> choose your card !? </font><BR>
            <table>
                    <form action="AddBattle">
                         <input type="hidden" name="opponentid" value="<%=request.getAttribute("opponentid")%>"

                <c:forEach var="pull" items="${cardset}">
                
                     <tr><td><input type="submit" value="${pull.cardId}" name="cardId" /></td><td>${pull.cardName}</td></tr>
                </c:forEach>
                     </form>
            </table>
            </div>
            <div class="puts">
                <%@include file="PutsBoard.jsp" %>
            </div>
            <div class="footerInc">
                <%@include file="Footer.jsp"%>
            </div>
        </div>

    </body>
</html>


   