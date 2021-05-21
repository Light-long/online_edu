<template>
    <div class="app-container">
      <!--查询表单-->
      <div align="center">
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item>
            <el-input v-model="teacherQuery.name" placeholder="讲师姓名"/>
          </el-form-item>
          <el-form-item>
            <el-select v-model="teacherQuery.level" clearable placeholder="讲师头衔">
              <el-option :value="1" label="高级讲师"/>
              <el-option :value="2" label="首席讲师"/>
            </el-select>
          </el-form-item>
          <!--添加时间的区间-->
          <el-form-item label="添加时间">
            <el-date-picker
              v-model="teacherQuery.begin"
              type="datetime"
              placeholder="选择开始时间"
              value-format="yyyy-MM-dd HH:mm:ss"
              default-time="00:00:00"
            />
          </el-form-item>
          <el-form-item>
            <el-date-picker
              v-model="teacherQuery.end"
              type="datetime"
              placeholder="选择截止时间"
              value-format="yyyy-MM-dd HH:mm:ss"
              default-time="00:00:00"
            />
          </el-form-item>
          <!--条件查询getTeacherListPage(),括号一定要加，代表传参数-->
          <el-button type="primary" icon="el-icon-search" @click="getTeacherList()">查询</el-button>
          <el-button type="default" @click="resetData()">清空</el-button>
        </el-form>
      </div >

      <!--表格-->
      <el-table :data="teacherList" border fit highlight-current-row>
        <el-table-column
          label="序号"
          align="center">
          <!--计算序号-->
          <template slot-scope="scope">
            {{ (current - 1) * size + scope.$index + 1 }}
          </template>
        </el-table-column>
        <!--姓名-->
        <el-table-column prop="name" label="姓名" align="center"/>
        <el-table-column label="头衔" align="center">
          <template slot-scope="scope">
            <!--scope表格，row当前行-->
            {{ scope.row.level===1? '高级讲师':'首席讲师' }}
          </template>
        </el-table-column>
        <!--<el-table-column prop="career" label="资历" align="center"/>-->
        <!--排序字段-->
        <el-table-column prop="sort" label="排序" align="center" />
        <!--添加时间-->
        <el-table-column prop="gmtCreate" label="添加时间" align="center"/>
        <!--更新时间-->
        <el-table-column prop="gmtModified" label="最近更新时间" align="center"/>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <router-link :to="'/teacher/edit/'+scope.row.id">
              <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
            </router-link>
            <!--scope代表表格-->
            <el-button type="danger" size="mini" icon="el-icon-delete"
                       @click="deleteTeacherById(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--分页-->
      <el-pagination
        :current-page="current"
        :page-size="size"
        :total="total"
        style="padding: 30px 0; text-align: center;"
        layout="total, prev, pager, next, jumper"
        @current-change="getTeacherList"
      />
    </div>
</template>

<script>
    //引入teacher.js,进行后端接口的调用
    import teacher from "@/api/edu/teacher";
    export default {
      data() {
        return {
          // 初始化数据
          // 当前页码
          current: 1,
          // 每页记录数
          size: 8,
          // teacherQuery对象
          teacherQuery: {},
          // teacher数据集合
          teacherList: [],
          // 总记录数
          total: 0
        }
      },
      created() {
        // 页面渲染之前请求数据
        this.getTeacherList()
      },
      methods: {
        // 获取讲师列表+分页+条件查询
        // 不传参默认当前页是1
        getTeacherList(current = 1) {
          // 点击分页传参更新当前页
          this.current = current;
          teacher.getTeacherListPageByCondition(this.current, this.size, this.teacherQuery)
            .then(response => {
              // 将请求到的数据赋值
              this.teacherList = response.data.records;
              this.total = response.data.total;
              console.log(response.data)
            })
            .catch(error => {
              console.log(error)
            })
        },
        // 清空查询的参数
        resetData() {
          // 清空参数
          this.teacherQuery = {}
          // 查询所有
          this.getTeacherList()
        },
        // 根据id删除讲师
        deleteTeacherById(id) {
          this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          // 点击确定
          }).then(() => {
            // 执行删除
            // 必须加return不然删除后没法刷新页面
            return teacher.deleteTeacher(id)
            // 删除成功
            }).then(() => {
              // 弹出成功提示信息
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              // 更新列表
              this.getTeacherList()
            // 删除失败
            }).catch((response) => { // 失败
              if (response === 'cancel') {
                this.$message({
                  type: 'info',
                  message: '已取消删除'
                })
              } else {
                this.$message({
                  type: 'error',
                  message: '删除失败'
                })
              }
            })
          }
        }
    }
</script>

<style scoped>
  .right {
    margin-right: 50px;
  }
</style>
