import request from '@/utils/request'

export default {
  // 获取所有课程分类
  getAllSubject() {
    return request({
      url: `/edu/subject/getAllSubject`,
      method: 'get'
    })
  }
}
