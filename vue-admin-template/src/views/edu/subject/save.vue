<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        &nbsp;
        <el-tag>
          <i class="el-icon-download"/>
          <!--从阿里云下载excel模板-->
          <a :href="'https://online-edu-glxy.oss-cn-shanghai.aliyuncs.com/subject.xlsx'">点击下载模版</a>
        </el-tag>
      </el-form-item>
      <el-form-item label="选择Excel">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          :action="BASE_API+'/edu/subject/addSubject'"
          name="file"
          accept="application/vnd.ms-excel">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button
            :loading="loading"
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="submitUpload">{{ fileUploadBtnText }}</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
    export default {
        name: "save",
        data() {
          return {
            // 防止重复点击提交按钮
            importBtnDisabled: false,
            BASE_API: process.env.BASE_API,
            fileUploadBtnText: '上传到服务器',
            loading: false
          }
        },
        created() {

        },
        methods: {
          // 上传文件到后端接口
          submitUpload() {
            this.fileUploadBtnText = '正在上传'
            // 禁止再次点击
            this.importBtnDisabled = true
            this.loading = true
            // 相当于提交表单
            this.$refs.upload.submit()
          },
          // 上传成功
          fileUploadSuccess() {
            this.loading = false
            this.fileUploadBtnText = '上传到服务器'
            // 成功提示信息
            this.$message({
              type: "success",
              message: "添加课程分类成功"
            })
            // 路由跳转到subject/list
            this.$router.push({path: '/subject/list'})
          },
          // 上传失败
          fileUploadError() {
            this.loading = false
            this.fileUploadBtnText = '上传到服务器'
            // 成功提示信息
            this.$message({
              type: "error",
              message: "添加课程分类失败"
            })
          }
        }
    }
</script>

<style scoped>

</style>
