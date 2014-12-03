$(document).ready(function() {

  // Wrap it in a div for centering
  $(".document img").wrap("<div class='image-wrap'></div>");

  $("#search-toggle").bind('click', function() {
    $(".search-wrapper").toggleClass('active');
  });

  // function lazifyImages() {
  //   var images = document.getElementsByClassName('lazy');
  //   for(var i = 0; i < images.length; i++) {
  //       var thisImage = images[i];
  //       var orgSrc = thisImage.getAttribute('src');
  //       var width = thisImage.naturalWidth;
  //       var height = thisImage.naturalHeight;
  //       thisImage.setAttribute("height", height);
  //       thisImage.setAttribute("width", width);
  //       thisImage.setAttribute("data-src", orgSrc);
  //       thisImage.setAttribute('src', '_images/ph.png');
  //   }
  // }
  // window.onload = function(){
  //   lazifyImages();
  // }

  // $('img.lazy').unveil(100, function() {
  //   $(this).load(function() {
  //     this.style.opacity = 1;
  //   });
  // });

  $("#menu-toggle").bind('click', function(e) {
    $("#main-nav-wrap").toggleClass('active');
    $(".search-wrapper").toggleClass('active');
  });

  $("#toc-toggle").bind('click', function(e) {
    $("body, .sidebar-first").toggleClass('push-right');
  });

  $(".sidebar-first .local-toc a").bind('click', function(e) {
    $("body, #side-nav").toggleClass('push-right');
  });



  var width = Math.max( $(window).width(), window.innerWidth);

  if (width < 990) {
    $('.header').toggleClass('fixed');
    $('#mobile-toc').waypoint('sticky', {
      direction: 'down right',
      stuckClass: 'stuck',
      wrapper: '<div class="mobile-toc-wrapper" />'
    });
  }

  // ======================================
  // Helper functions
  // ======================================
  // Get section or article by href
  function getRelatedContent(el){
    return $($(el).attr('href'));
  }
  // Get link by section or article id
  function getRelatedNavigation(el){
    var linkId =  $(el).attr("id");
    return $('nav.toc a[href=#'+linkId+']');
  }


  // ======================================
  // Expand subnav(s)
  // ======================================
  $('nav.toc a').on('click',function(e){
    $(this).next('ul').toggleClass('active');
  });


  // ======================================
  // Waypoints
  // ======================================
  // Default cwaypoint settings

  var wpDefaults={
    context: window,
    continuous: true,
    enabled: true,
    horizontal: false,
    offset: 60,
    triggerOnce: false
  };

  $('.section')
     .waypoint(function(direction) {
     // Highlight element when related content
     // is 10% percent from the bottom...
     // remove if below
       getRelatedNavigation(this).toggleClass('active', direction === 'down');
       getRelatedNavigation(this).next('ul').toggleClass('active', direction === 'down');
     }, {
       offset: '20%'
     })
     .waypoint(function(direction) {
     // Highlight element when bottom of related content
     // is 60px from the top - remove if less
       getRelatedNavigation(this).toggleClass('active', direction === 'up');
       getRelatedNavigation(this).next('ul').toggleClass('active', direction === 'up');
     }, {
       offset: function() {  return -$(this).height() + 60; }
     });

});
