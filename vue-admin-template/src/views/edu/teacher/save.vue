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
        <!-- 讲师头像-->
        <el-form-item label="讲师头像">
          <!-- 头衔缩略图 -->
          <pan-thumb :image="teacher.avatar"/>
          <!-- 文件上传按钮 -->
          <el-button type="primary" icon="el-icon-upload" @click="imageCropperShow=true">更换头像
          </el-button>
          <!--
            v-show：是否显示上传组件
            :key：类似于id，如果一个页面多个图片上传控件，可以做区分
            :url：后台上传的url地址
            @close：关闭上传组件
            @crop-upload-success：上传成功后的回调 -->
          <image-cropper
            v-show="imageCropperShow"
            :width="300"
            :height="300"
            :key="imageCropperKey"
            :url="BASE_API+'/oss/file/upLoadAvatar'"
            field="file"
            @close="close"
            @crop-upload-success="cropSuccess"/>
          <!--上面的方法封装过了，调用时不能加()******-->
        </el-form-item>

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
  // 引入组件模块
  import ImageCropper from '@/components/ImageCropper'
  import PanThumb from '@/components/PanThumb'
    export default {
        // 声明组件
        components: { ImageCropper, PanThumb },
        data() {
          return {
            teacher: {},
            // 避免重复提交
            saveBtnDisabled: false,
            // 上传组件是否显示
            imageCropperShow: false,
            // 上传组件key值
            imageCropperKey: 0,
            // 请求IP+Port
            BASE_API: process.env.BASE_API
          }
        },
        created() {
          this.init()
        },
        // 路由监听,路由变化就会执行
        watch: {
          $route(to,from) {
            this.init()
          }
        },
        methods: {
          // 关闭上传组件
          close() {
            this.imageCropperShow = false
            // 上传组件初始化
            this.imageCropperKey = this.imageCropperKey + 1
          },
          // 上传成功
          cropSuccess(data) {
            // 关闭上传组件
            this.imageCropperShow = false
            // 返回图片地址(用于显示)
            this.teacher.avatar = data.url
            // 上传组件初始化
            this.imageCropperKey = this.imageCropperKey + 1
          },
          init() {
            // 判断路由中是否有id，如果有就是更新，需要数据回显
            if (this.$route.params && this.$route.params.id) {
              const id = this.$route.params.id
              this.getTeacher(id)
            } else {
              // 清空表单
              this.teacher = {}
              // 默认头像
              this.teacher.avatar = 'https://online-edu-glxy.oss-cn-shanghai.aliyuncs.com/2021/05/22/default.jpg'
            }
          },
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
