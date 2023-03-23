import Cookies from 'js-cookie'
/**
 * token 是存储在 cookie 中的
 */
const TokenKey = 'Mustang-Authorization'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
