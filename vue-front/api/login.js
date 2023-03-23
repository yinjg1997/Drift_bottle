import request from '@/utils/request'

export default {
  submitLoginUser(loginInfo) {
    return request({
      url: `/ucenterservice/member/login`,
      method: 'post',
      data:loginInfo
    })
  },
  getLoginUserInfo() {
    return request({
      url: `/ucenterservice/member/getMemberInfo`,
      method: 'get'
    })
  }
}
