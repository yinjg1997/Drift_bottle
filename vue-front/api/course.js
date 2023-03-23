import request from '@/utils/request'

export default {
  getCourseList(current, size, searchObj) {
    return request({
      url: `/eduservice/frontcourse/getFrontCourseList/${current}/${size}`,
      method: 'post',
      data:searchObj
    })
  },
  getAllSubject() {
    return request({
      url: `/eduservice/subject/tree`,
      method: 'get'
    })
  },
  getCourseInfo(courseId) {
    return request({
      url: `/eduservice/frontcourse/getFrontCourseInfo/${courseId}`,
      method: 'get'
    })
  }
}
