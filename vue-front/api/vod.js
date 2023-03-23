import request from '@/utils/request'

export default {
  getPlayAuth(vid) {
    return request({
      url: `/vodservice/vod/getVideoPlayAuth/${vid}`,
      method: 'get'
    })
  }

}
