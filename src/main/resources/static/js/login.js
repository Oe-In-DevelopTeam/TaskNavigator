const userId = document.getElementById('userId');
const password = document.getElementById("userPassword");
const loginButton = document.querySelector('button[type="submit"]');

function handlerLogin(event) {
  event.preventDefault();

  const userIdValue = userId.value;
  const passwordValue = password.value;

  fetch('http://localhost:8080/users/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ userId: userIdValue, password: passwordValue })
  })
  .then(response => {
    if (!response.ok) {
      throw new Error();
    }
    return response.json();
  })
  .then(data => {
    window.location.href = 'http://localhost:8080/home';
  })
  .catch(error => {
    alert('아이디 또는 비밀번호가 일치하지 않습니다.');
  });
}

loginButton.addEventListener('click', handlerLogin);
