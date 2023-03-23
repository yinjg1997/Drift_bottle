import request from '@/utils/request'

export default {
  /**
   *
   * @param courseId
   * @returns orderNo
   */
  createOrders(courseId) {
    return request({
      url: `/orderservice/order/createOrder/${courseId}`,
      method: 'post'
    })
  },
  /**
   *
   * @param orderNo
   * @returns order
   */
  getOrdersInfo(orderNo) {
    return request({
      url: `/orderservice/order/getOrderInfo/${orderNo}`,
      method: 'get'
    })
  },
  /**
   *
   * @param orderNo
   * @returns {*}
   */
  createNative(orderNo) {
    return request({
      url: `/orderservice/payLog/createWXPayCode/${orderNo}`,
      method: 'get'
    })
  },
  queryPayStatus(orderNo) {
    return request({
      url: `/orderservice/payLog/queryPayStatus/${orderNo}`,
      method: 'get'
    })
  }
}
