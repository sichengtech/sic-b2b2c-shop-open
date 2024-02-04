$(function() {

    $(function () {
        $(window).scroll(function () {
            if ($(window).scrollTop() >= 50) {
                $('#goTop').fadeIn();
            }
            else {
                $('#goTop').fadeOut();
            }
        });
    });

    // 回到顶部
    $('#goTop').click(function() {
        $('html,body').animate({ scrollTop: 0 }, 500);
    })
})