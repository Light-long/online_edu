import request from '@/utils/request'

export default {
  // 添加课程信息
  addSubjectInfo(courseInfo) {
    return request({
      url: `/edu/course/addCourseInfo`,
      method: 'post',
      data: courseInfo
    })
  }
}
