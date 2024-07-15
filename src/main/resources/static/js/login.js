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
    localStorage.setItem('Authorization', response.headers.get('Authorization'));
    return response.json();
  })
  .then(async data => {
    const resultData = await fetchWithToken('http://localhost:8080/boards');
  })
  .catch(error => {
    alert('아이디 또는 비밀번호가 일치하지 않습니다.');
  });
}

async function fetchWithToken(url, options = {}) {
  const token = localStorage.getItem('Authorization');
  if (!options.headers) {
    options.headers = {};
  }
  if (token) {
    options.headers['Authorization'] = token;
  }

  try {
    const response = await fetch(url, options);
    if (!response.ok) {
      throw new Error('Request failed');
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error:', error);
    throw error;
  }
}

loginButton.addEventListener('click', handlerLogin);
