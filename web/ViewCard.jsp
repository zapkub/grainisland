<%@page errorPage="Error.jsp" %>
<jsp:include page="CheckAuthJSP"  flush="true" />
<%--
    Document   : Game
    Created on : 9 ส.ค. 2553, 21:25:51
    Author     : Vable
--%>
<%@page import="sit.int303.grianisland.core.model.Player"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>:: game play ::</title>
        <link rel="stylesheet" href="css/galleriffic-2.0/black.css" type="text/css" />
	<link rel="stylesheet" href="css/galleriffic-2.0/galleriffic-5.css" type="text/css" />
        <link rel="stylesheet" type="text/css" href="css/screen.css" media="all" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery.history.js"></script>
	<script type="text/javascript" src="js/jquery.galleriffic.js"></script>
	<script type="text/javascript" src="js/jquery.opacityrollover.js"></script>
        <style type="text/css">
            .workingArea{ width: 1020px; height: 760px; top:  8px;    margin: auto; background-color: red;}
            .headerInc{ width: 1020px; height: 160px;   top:  0px; position:relative ;float:left  ;margin: auto; background: grey;}
            .viewCard{      width: 1020px; height: 500px;   top:  0px; float:left; margin: auto; background: #000000; position:relative ;float:left }
            .footerInc{ width: 1020px; height: 100px;   top:  0px;  margin: auto; background: grey;        position:relative ;float:left }
        body {
	background-color: #000;
}
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
					numThumbs:                 5,
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
    <body>
        <div class="workingArea">
            <div class="headerInc">
                <%@include file="Header.jsp" %>
            </div>
            <div class="viewCard">
                 Card Collection
				<!-- Start Advanced Gallery Html Containers -->
                <div class="navigation-container">
                    <div id="thumbs" class="navigation">
                            <a class="pageLink prev" style="visibility: hidden;" href="#" title="Previous Page"></a>

                                    <!-- build script for pull data < ideas by Zapdos Modify by Zing > -->
						<ul class="thumbs noscript" >
                                                    <c:forEach var="pull" items="${cardset}">
							<li>
                                                            <a class="thumb" name= "${pull.cardId}" href="images/cards/resize/${pull.cardId}.jpg" title="${pull.cardId}">
                                                                <img src="images/cards/resize/backCard.jpg" alt="card" />
                                                            </a>
								<div class="caption">
									<div class="image-title">${pull.cardId} ${pull.cardName}</div>
									<div class="image-desc">
                                                                            Description : <br/>
                                                                            ${pull.cardDesc}
                                                                            <br/>
                                                                            Amount :
                                                                            ${pull.cardAmount}
                                                                        </div>
								</div>
							</li>
                                                    </c:forEach>
						</ul>
						<a class="pageLink next" style="visibility: hidden;" href="#" title="Next Page"></a>
					</div>
				</div>
                            <!-- Show card section -->
				<div class="content">
					<div class="slideshow-container">
						<div id="loading" class="loader"></div>
						<div id="slideshow" class="slideshow"></div>
					</div>
					<div id="caption" class="caption-container">
						<div class="photo-index"></div>
					</div>
				</div> 
				<!-- End Gallery Html Containers -->
				<div style="clear: both;"></div>
            </div>
            <div class="footerInc"> <%@include file="Footer.jsp"%></div>
        </div>
        
</html>  

    <!-- Back up Code
    <ul class="thumbs noscript" >
							<li>
                                <a class="thumb" name="001" href="images/cards/resize/001.jpg" title="001">
                                    <img src="images/cards/resize/backCard.jpg" alt="001 : Heer Meow" />
								</a>
								<div class="caption">
									<div class="image-title">Card No. 001 : Heer Meow</div>
									<div class="image-desc">
                                        Description : <br/>
                                        อาศัยอยู่ที่ BestHome อย่างลับๆ ชอบโดน I-Nick และ I-lai-tsua แกล้งเป็นประจำ มีเพื่อนชื่อ Heer NaKak
                                    </div>
								</div>
							</li>

							<li>
								 <a class="thumb" name="001" href="images/cards/resize/002.jpg" title="001">
                                    <img src="images/cards/resize/backCard.jpg" alt="001 : Heer Meow" />
								</a>
								<div class="caption">
									<div class="image-title">Card No. 001 : Heer Meow</div>
									<div class="image-desc">
                                        Description : <br/>
                                        อาศัยอยู่ที่ BestHome อย่างลับๆ ชอบโดน I-Nick และ I-lai-tsua แกล้งเป็นประจำ มีเพื่อนชื่อ Heer NaKak
                                    </div>
                                </div>
							</li>

							<li>
								 <a class="thumb" name="001" href="images/cards/resize/003.jpg" title="001">
                                    <img src="images/cards/resize/backCard.jpg" alt="001 : Heer Meow" />
								</a>
								<div class="caption">
									<div class="image-title">Title #2</div>
									<div class="image-desc">Description</div>
								</div>
							</li>

							<li>
								 <a class="thumb" name="001" href="images/cards/resize/004.jpg" title="001">
                                    <img src="images/cards/resize/backCard.jpg" alt="001 : Heer Meow" />
								</a>
								<div class="caption">
									<div class="image-title">Title #3</div>
									<div class="image-desc">Description</div>
                                </div>
							</li>

							<li>
								 <a class="thumb" name="001" href="images/cards/resize/005.jpg" title="001">
                                    <img src="images/cards/resize/backCard.jpg" alt="001 : Heer Meow" />
								</a>
								<div class="caption">
									<div class="image-title">Title #4</div>
									<div class="image-desc">Description</div>
								</div>
							</li>

							<li>
								 <a class="thumb" name="001" href="images/cards/resize/006.jpg" title="001">
                                    <img src="images/cards/resize/backCard.jpg" alt="001 : Heer Meow" />
								</a>
								<div class="caption">
									<div class="image-title">Title #5</div>
									<div class="image-desc">Description</div>
								</div>
							</li>

							<li>
								 <a class="thumb" name="001" href="images/cards/resize/007.jpg" title="001">
                                    <img src="images/cards/resize/backCard.jpg" alt="001 : Heer Meow" />
								</a>
								<div class="caption">
									<div class="image-title">Title #6</div>
									<div class="image-desc">Description</div>
								</div>
							</li>
						</ul>
						<a class="pageLink next" style="visibility: hidden;" href="#" title="Next Page"></a>
					</div>
				</div>
                <!-- Show card section
				<div class="content">
					<div class="slideshow-container">
						<div id="loading" class="loader"></div>
						<div id="slideshow" class="slideshow"></div>
					</div>
					<div id="caption" class="caption-container">
						<div class="photo-index"></div>
					</div>
				</div>
<!-- End Gallery Html Containers 
-->