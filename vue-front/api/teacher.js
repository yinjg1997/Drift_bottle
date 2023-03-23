import request from '@/utils/request'

export default {
  //分页查询讲师数据
  getTeacherList(current, size) {
    return request({
      url: `/eduservice/frontteacher/frontPageTeacher/${current}/${size}`,
      method: 'get'
    })
  },
  //讲师详情
  getTeacherInfo(id) {
    return request({
      url: `/eduservice/frontteacher/frontGetTeacherById/${id}`,
      method: 'get'
    })
  }

}
