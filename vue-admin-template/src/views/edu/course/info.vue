<template>
  <div class="app-container">
    <!--步骤条-->
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>

    <!--信息表单-->
    <el-form label-width="120px">
      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
      </el-form-item>
      <!-- 所属分类 TODO -->
      <!-- 课程讲师 TODO -->
      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
      </el-form-item>
      <!-- 课程简介 -->
      <el-form-item label="课程简介">
        <el-input v-model="courseInfo.description" placeholder=""/>
      </el-form-item>
      <!-- 课程封面 TODO -->
      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
      </el-form-item>
    </el-form>

    <!--提交按钮-->
    <div align="center">
      <el-form label-width="120px">
        <el-form-item>
          <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
    import course from "@/api/edu/course";
    export default {
        data() {
          return {
            saveBtnDisabled: false,
            courseInfo: {
              title: '',
              subjectId: '',
              teacherId: '',
              lessonNum: 0,
              description: '',
              cover: '',
              price: 0
            }
          }
        },
        created() {

        },
        methods: {
          // 提交课程信息
          saveOrUpdate() {
            course.addSubjectInfo(this.courseInfo)
              .then(response => {
                // 成功信息
                this.$message({
                  type: 'success',
                  message: '添加课程信息成功!'
                })
                // 跳转到chapter
                this.$router.push({path: '/course/chapter/'+response.data.courseId})
              })
          }
        }
    }
</script>

<style scoped>

</style>
