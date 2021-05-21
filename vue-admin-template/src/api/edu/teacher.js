import request from '@/utils/request'

export default {
    getTeacherListPageByCondition(current,size,teacherQuery) {
      return request({
        url: `/edu/teacher/pageTeacherCondition/${current}/${size}`,
        method: 'post',
        // teacherQuery条件对象，后端使用@RequestBody接收
        // data表示把对象转换为json传递到接口
        data: teacherQuery
      })
    }
}
