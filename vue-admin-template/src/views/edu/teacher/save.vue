<template>
    <div class="app-container">
      <!--数据表单-->
      <el-form label-width="120px" class="form">
        <el-form-item label="讲师名称">
          <el-input v-model="teacher.name"/>
        </el-form-item>
        <el-form-item label="讲师排序">
          <el-input-number v-model="teacher.sort" controls-position="right" :min="0" :max="5"/>
        </el-form-item>
        <el-form-item label="讲师头衔">
          <el-select v-model="teacher.level" clearable placeholder="请选择">
            <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
            -->
            <el-option :value="1" label="高级讲师"/>
            <el-option :value="2" label="首席讲师"/>
          </el-select>
        </el-form-item>
        <el-form-item label="讲师资历">
          <el-input v-model="teacher.career"/>
        </el-form-item>
        <el-form-item label="讲师简介">
          <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
        </el-form-item>
        <!-- 讲师头像：TODO -->

        <!--提交按钮-->
        <el-form-item>
          <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdateTeacher()">保存
          </el-button>
        </el-form-item>
      </el-form>

    </div>
</template>

<script>
  import teacher from "@/api/edu/teacher";
    export default {
        data() {
          return {
            teacher: {},
            saveBtnDisabled: false
          }
        },
        created() {
          // 判断路由中是否有id，如果有就是更新，需要数据回显
          if (this.$route.params && this.$route.params.id) {
            const id = this.$route.params.id
            this.getTeacher(id)
          }
        },
        methods: {
          saveOrUpdateTeacher() {
            // 判断teacher.id是否有值，如果id有值为更新，没有值为添加
            if (!this.teacher.id) {
              // 添加
              this.saveTeacher()
            } else {
              // 修改
              this.updateTeacher()
            }
          },
          // 修改讲师
          updateTeacher() {
            teacher.updateTeacher(this.teacher.id,this.teacher)
              .then(response => {
                // 弹出成功提示信息
                this.$message({
                  type: 'success',
                  message: '修改成功!'
                })
                // 返回列表页面
                this.$router.push({path: '/teacher/list'})
              })
          },
          // 添加讲师
          saveTeacher() {
            teacher.addTeacher(this.teacher)
              .then(response => {
                // 弹出成功提示信息
                this.$message({
                  type: 'success',
                  message: '添加成功!'
                })
                // 返回列表页面
                this.$router.push({path: '/teacher/list'})
              })
          },
          // 根据id获取讲师信息（回显）
          getTeacher(id) {
            teacher.getTeacherById(id)
              .then(response => {
                this.teacher = response.data.teacher;
              })
          }
        }
    }
</script>

<style scoped>

</style>
