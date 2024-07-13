const userNickname = document.getElementById('userNickname');
const userId = document.getElementById('userId');
const password = document.getElementById("userPassword");
const signupButton= document.getElementById('signup-btn');

function handlerSignup(event) {
  event.preventDefault();

  const userNicknameValue = userNickname.value;
  const userIdValue = userId.value;
  const passwordValue = password.value;

  fetch('http://localhost:8080/users/signup', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ username: userNicknameValue, userId: userIdValue, password: passwordValue })
  })
  .then(response => {
    if (!response.ok) {
      throw new Error();
    }
    return response.json();
  })
  .then(data => {
    console.log(data.message);
    window.location.href = 'http://localhost:8080/users/login-page';
  })
  .catch(error => {
    alert('아이디는 4 ~ 10 길이, 영문 소문자, 숫자만 가능합니다. 비밀번호는 8 ~ 15 길이, 영문 대소문자, 특수문자가 포함되어야 합니다.');
  });
}

// 로그인 버튼 클릭 시 handlerLogin 함수 호출
signupButton.addEventListener('click', handlerSignup);
