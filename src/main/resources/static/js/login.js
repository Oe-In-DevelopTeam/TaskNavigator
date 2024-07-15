$(document).ready(function () {
  // 토큰 삭제
  Cookies.remove('Authorization', {path: '/'});
});

const href = location.href;
const host = 'http://' + window.location.host;

$('button[type="submit"]').click(function (event) {
  let username = $('#userId').val();
  let password = $('#userPassword').val();

  console.log(username);

  $.ajax({
    type: "POST",
    url: `/users/login`,
    contentType: "application/json",
    data: JSON.stringify({userId: username, password: password}),
  })
  .done(function (res, status, xhr) {
    const token = xhr.getResponseHeader('Authorization');

    Cookies.set('Authorization', token, {path: '/'})

    $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
      console.log(token);
      jqXHR.setRequestHeader('Authorization', token);
    });

    window.location.href = host + '/home';
  })
  .fail(function (jqXHR, textStatus) {
    alert("Login Fail");
  });
})