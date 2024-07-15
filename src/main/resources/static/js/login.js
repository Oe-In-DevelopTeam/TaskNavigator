$(document).ready(function () {
  // 페이지 로드 시 토큰 삭제
  Cookies.remove('Authorization', {path: '/'});
});

  // $.ajaxPrefilter를 사용하여 모든 AJAX 요청에 헤더를 추가합니다.

  const href = location.href;
  const host = 'http://' + window.location.host;

  $('button[type="submit"]').click(function (event) {
    event.preventDefault(); // 폼의 기본 제출 동작을 막습니다.

    let username = $('#userId').val();
    let password = $('#userPassword').val();

    console.log(username);

    $.ajax({
      type: "POST",
      async: false, // 동기 요청(비추천: UI를 멈추게 할 수 있음)
      url: `/users/login`,
      contentType: "application/json",
      data: JSON.stringify({userId: username, password: password}),
    })
    .done(function (res, status, xhr) {
      const token = xhr.getResponseHeader('Authorization');

      // 토큰을 쿠키에 저장합니다.
      Cookies.set('Authorization', token, {path: '/'});
      console.log("input!!!!");

      $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
        jqXHR.setRequestHeader('Authorization', token);
      });

      // 홈 페이지로 리다이렉션합니다.
      window.location.href = host + '/home';
    })
    .fail(function (jqXHR, textStatus) {
      alert("Login Fail");
    });
  });
