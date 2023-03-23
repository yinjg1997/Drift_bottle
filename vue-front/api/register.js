import request from '@/utils/request'

export default {
  sendCode(phoneNumber) {
    return request({
      url: `/smsservice/sms/sendSms/${phoneNumber}`,
      method: 'get'
    })
  },
  registerMember(formItem) {
    return request({
      url: `/ucenterservice/member/register`,
      method: 'post',
      data:formItem
    })
  }
}
