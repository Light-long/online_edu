<template>
  <div class="app-container">
    <!--搜索框-->
    <el-input v-model="filterText" placeholder="请输入关键字检索" style="margin-bottom:30px;" />
    <!--展示tree-->
    <el-tree
      ref="tree2"
      :data="data2"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />

  </div>
</template>

<script>
  // 引入请求文件
  import subject from "@/api/edu/subject";

  export default {
    data() {
      return {
        filterText: '',
        data2: [],
        defaultProps: {
          children: 'children',
          label: 'title'
        }
      }
    },
    watch: {
      filterText(val) {
        this.$refs.tree2.filter(val)
      }
    },
    created() {
      // 调用获取课程分类方法
      this.getSubject()
    },
    methods: {
      // 获取课程分类
      getSubject() {
        subject.getAllSubject()
          .then(response => {
            console.log(response.data)
            this.data2 = response.data.subjectList;
          })
      },
      filterNode(value, data) {
        if (!value) return true
        return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
      }
    }
  }
</script>
