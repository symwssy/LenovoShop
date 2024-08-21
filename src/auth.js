// auth.js
export function checkLoginStatus() {
  return new Promise((resolve) => {
    const isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
    resolve(isLoggedIn);
  });
}
