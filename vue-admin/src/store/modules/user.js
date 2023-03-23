/**
 * 初始化/存储用户状态
 */
import userApi from '@/api/admin/admin'
import {getToken, setToken, removeToken} from '@/utils/auth'
import {resetRouter} from '@/router'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: ''
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token // 拿到从 cookie 中获得的token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo // userInfo 是登录时传递的
    console.log(userInfo)
    return new Promise((resolve, reject) => {
      // RequestBody 的参数对不上后台就无法接收
      userApi.login({name: username, password: password}).then(response => {
        const {data} = response
        /**
         * 获得服务器返回的 token
         */
        commit('SET_TOKEN', data.token) // commit的属性不就是对应 mutations的属性吗
        /**
         * 将token保存在cookie
         */
        setToken(data.token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      // 利用cookie中拿到的token，调用api获取用户信息；再将这些用户信息存储起来
      userApi.getInfo(state.token).then(response => {
        /**
         * 这里的response 已经被src/util/request.js 处理过了
         * 获得服务器返回的数据的 data 部分
         */
        const {data} = response
        if (!data) {
          return reject('Verification failed, please Login again.')
        }
        // { name, avatar }只能接收 data 中有的参数, 否则就无法接收
        const {name, avatar} = data

        commit('SET_NAME', name)
        commit('SET_AVATAR', avatar)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      userApi.logout(state.token).then(() => {
        removeToken() // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

