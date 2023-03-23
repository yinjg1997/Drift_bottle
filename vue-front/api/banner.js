import request from '@/utils/request'

export default {
  getListBanner() {
    return request({
      url: `/cmsservice/frontbanner/getBanners`,
      method: 'get'
    })
  }
}
