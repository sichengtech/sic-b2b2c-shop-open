$(function() {
	$('#carousel').owlCarousel({
		items: 1,
		pagination: true,
		navigation: true,
		stopOnHover: true,
		responsive: false,
		autoPlay: 4000,
		navigationText: [
			`<div class="next-slick-arrow next-slick-prev inline medium horizontal"
            style="display:block;">
            <i class="next-icon next-icon-arrow-left next-icon-medium"></i>
        </div>`,
			`<div class="next-slick-arrow next-slick-next inline medium horizontal"
            style="display:block;">
            <i class="next-icon next-icon-arrow-right next-icon-medium"></i>
        </div>`
		]
	});
})