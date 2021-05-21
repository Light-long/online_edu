import request from '@/utils/request'

export default {
    // 查询+分页+组合条件
    getTeacherListPageByCondition(current,size,teacherQuery) {
      return request({
        url: `/edu/teacher/pageTeacherCondition/${current}/${size}`,
        method: 'post',
        // teacherQuery条件对象，后端使用@RequestBody接收
        // data表示把对象转换为json传递到接口
        data: teacherQuery
      })
    },

    // 逻辑删除
    deleteTeacher(id) {
      return request({
        url: `/edu/teacher/${id}`,
        method: 'delete'
      })
    },

    // 添加讲师
    addTeacher(teacher) {
      return request({
        url: `/edu/teacher/addTeacher`,
        method: 'post',
        data: teacher
      })
    },

    // 根据id查询讲师
    getTeacherById(id) {
      return request({
        url: `/edu/teacher/${id}`,
        method: 'get'
      })
    },

    // 更新讲师
    updateTeacher(id,teacher) {
      return request({
        url: `/edu/teacher/${id}`,
        method: 'put',
        data: teacher
      })
    }
}
