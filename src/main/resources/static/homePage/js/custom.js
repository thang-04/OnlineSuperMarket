/****************************************************************************
 * Gambo v2.1
 * Gambo - Online Grocery Supermarket HTML Template by Gambolthemes
 * Copyright 2022 | Gambolthemes | https://gambolthemes.net
 * @package Gambolthemes
 ****************************************************************************/
 
/*----------------------------------------------
Index Of Script
------------------------------------------------

:: Tooltip
:: QTY JS
:: Wishlist Script
:: Toggle JS
:: Payment Method Accordion
:: Countdown
:: Owl Carousel 2
:: Multi Dropdown JS
:: Right Click Disable

------------------------------------------------
Index Of Script
----------------------------------------------*/

/*--- Tooltip JS ---*/

var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  return new bootstrap.Tooltip(tooltipTriggerEl)
})

/*--- QTY JS ---*/
function wcqib_refresh_quantity_increments() {
    jQuery("div.quantity:not(.buttons_added), td.quantity:not(.buttons_added)").each(function(a, b) {
        var c = jQuery(b);
        c.addClass("buttons_added"), c.children().first().before('<input type="button" value="-" class="minus" />'), c.children().last().after('<input type="button" value="+" class="plus" />')
    })
}
String.prototype.getDecimals || (String.prototype.getDecimals = function() {
    var a = this,
        b = ("" + a).match(/(?:\.(\d+))?(?:[eE]([+-]?\d+))?$/);
    return b ? Math.max(0, (b[1] ? b[1].length : 0) - (b[2] ? +b[2] : 0)) : 0
}), jQuery(document).ready(function() {
    wcqib_refresh_quantity_increments()
}), jQuery(document).on("updated_wc_div", function() {
    wcqib_refresh_quantity_increments()
}), jQuery(document).on("click", ".plus, .minus", function() {
    var a = jQuery(this).closest(".quantity").find(".qty"),
        b = parseFloat(a.val()),
        c = parseFloat(a.attr("max")),
        d = parseFloat(a.attr("min")),
        e = a.attr("step");
    b && "" !== b && "NaN" !== b || (b = 0), "" !== c && "NaN" !== c || (c = ""), "" !== d && "NaN" !== d || (d = 0), "any" !== e && "" !== e && void 0 !== e && "NaN" !== parseFloat(e) || (e = 1), jQuery(this).is(".plus") ? c && b >= c ? a.val(c) : a.val((b + parseFloat(e)).toFixed(e.getDecimals())) : d && b <= d ? a.val(d) : b > 0 && a.val((b - parseFloat(e)).toFixed(e.getDecimals())), a.trigger("change")
});

/*--- wishlist script ---*/
$(document).ready(function() {
	$('.like-icon, .like-button').on('click', function(e) {
		e.preventDefault();
		$(this).toggleClass('liked');
		$(this).children('.like-icon').toggleClass('liked');
	});
});

/*--- Toggle ---*/
$('.enable.button')
  .on('click', function() {
    $(this)
      .nextAll('.checkbox')
        .checkbox('enable')
    ;
  })
 ;


/*--- Payment Method Accordion ---*/
$('input[name="paymentmethod"]').on('click', function () {
	var $value = $(this).attr('value');
	$('.return-departure-dts').slideUp();
	$('[data-method="' + $value + '"]').slideDown();
});



/*--- Countdown ---*/
$(".product_countdown-timer").each(function(){
	var $this = $(this);
	$this.countdown($this.data('countdown'), function(event) {
	  $(this).text(
		event.strftime('%D days %H:%M:%S')
	  );
	});
});


/*--- OWL Carousel ---*/
// Banner Home 
$('.offers-banner').owlCarousel({
	stagePadding: 50,
	loop:true,
    margin:15,
	nav:false,
	dots:false,
    autoplay:true,
    autoplayTimeout: 3000,
    autoplayHoverPause:true,
	responsive:{
		0:{
			items:1
		},
		600:{
			items:2
		},
		1000:{
			items:2
		},
		1200:{
			items:3
		},
		1400:{
			items:3
		}
	}
})

// Category Slider
$('.cate-slider').owlCarousel({
	loop:true,
	margin:15,
	nav:true,
	dots:false,
	navText: ["<i class='uil uil-angle-left'></i>", "<i class='uil uil-angle-right'></i>"],
	responsive:{
		0:{
			items:2
		},
		600:{
			items:2
		},
		1000:{
			items:4
		},
		1200:{
			items:6
		},
		1400:{
			items:6
		}
	}
})

// Featured Slider
$('.featured-slider').owlCarousel({
	items: 8,
	loop:false,
	margin:15,
	nav:true,
	dots:false,
	navText: ["<i class='uil uil-angle-left'></i>", "<i class='uil uil-angle-right'></i>"],
	responsive:{
		0:{
			items:1
		},
		600:{
			items:2
		},
		1000:{
			items:3
		},
		1200:{
			items:4
		},
		1400:{
			items:5
		}
	}
})

// Date Slider
$('.date-slider').owlCarousel({
	loop:false,
    margin:10,
	nav:false,
	dots:false,
	responsive:{
		0:{
			items:3
		},
		600:{
			items:4
		},
		1000:{
			items:5
		},
		1200:{
			items:6
		},
		1400:{
			items:7
		}
	}
})

// Banner Home
$('.life-slider').owlCarousel({
	loop:true,
    margin:30,
	nav:true,
	dots:false,
    autoplay:true,
    autoplayTimeout: 3000,
    autoplayHoverPause:true,
	navText: ["<i class='uil uil-angle-left'></i>", "<i class='uil uil-angle-right'></i>"],
	responsive:{
		0:{
			items:1
		},
		600:{
			items:2
		},
		1000:{
			items:2
		},
		1200:{
			items:3
		},
		1400:{
			items:3
		}
	}
})

// Testimonials Slider
$('.testimonial-slider').owlCarousel({
	loop:true,
    margin:30,
	nav:true,
	dots:false,
	autoplay:true,
    autoplayTimeout: 3000,
    autoplayHoverPause:true,
	navText: ["<i class='uil uil-angle-left'></i>", "<i class='uil uil-angle-right'></i>"],
	responsive:{
		0:{
			items:1
		},
		600:{
			items:2
		},
		1000:{
			items:2
		},
		1200:{
			items:3
		},
		1400:{
			items:3
		}
	}
})

// Category Slider
$('.team-slider').owlCarousel({
	loop:true,
	margin:20,
	nav:false,
	dots:false,
	responsive:{
		0:{
			items:1
		},
		600:{
			items:2
		},
		1000:{
			items:3
		},
		1200:{
			items:4
		},
		1400:{
			items:4
		}
	}
})

/*--- Multi Dropdown JS ---*/ 

$(document).ready(function(){
  $('.dropdown-submenu a.submenu-item').on("click", function(e){
    $(this).next('ul').toggle();
    e.stopPropagation();
    e.preventDefault();
  });
});

/*--- Right Click Disable ---*/

window.oncontextmenu = function () {
	return false;
}
$(document).keydown(function (event) {
	if (event.keyCode == 123) {
		return false;
	}
	else if ((event.ctrlKey && event.shiftKey && event.keyCode == 73) || (event.ctrlKey && event.shiftKey && event.keyCode == 74)) {
		return false;
	}
});

document.addEventListener('DOMContentLoaded', function() {
    const categoryModal = document.getElementById('category_model');
    if (categoryModal) {
        categoryModal.addEventListener('show.bs.modal', function () {
            fetch('/api/categories')
                .then(res => res.json())
                .then(data => {
                    const list = document.getElementById('categoryList');
                    list.innerHTML = '';
                    data.forEach(cat => {
                        const li = document.createElement('li');
                        li.innerHTML = `
                            <a href="#" class="single-cat-item" data-id="${cat.categoryId}">
                                <div class="icon"><img src="/homePage/images/category/icon-1.svg" alt=""></div>
                                <div class="text">${cat.name}</div>
                            </a>
                        `;
                        list.appendChild(li);
                    });
                });
        });
    }
});