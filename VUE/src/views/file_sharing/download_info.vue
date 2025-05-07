<template>

  <div>



    <div style="margin-top: 20px;margin-left: 20px;">
      <!--      <el-upload class="upload-demo" action="" :show-file-list="false" :http-request="fnUploadRequest" :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload" multiple style="display:inline-block;" :file-list="fileList">
        <el-button type="primary" style="display:inline-block;">上传文件(暂未使用)</el-button>
      </el-upload> -->
      <span style="margin-left: 40px;">文件链接：</span>
      <el-input v-model="url" placeholder="请输入链接" style="display:inline-block;width: 400px;"></el-input>
      <el-button type="primary" @click="addDownload()" style="display:inline-block;margin-left: 10px;">下载</el-button>
    </div>
    <el-table v-loading="tableLoading" :data="serverFileLits" element-loading-text="数据加载中" border fit
      highlight-current-row row-class-name="myClassList" style="margin-top: 20px;">
      <el-table-column label="文件名称" align="center" sortable>
        <template slot-scope="scope">{{ scope.row.fileName }}</template>
      </el-table-column>

      <el-table-column label="文件大小" align="center" width="150" sortable>
        <template slot-scope="scope">{{ scope.row.fileSize }}</template>
      </el-table-column>
      <el-table-column label="进度" align="center" width="150" sortable>
        <template slot-scope="scope">{{ scope.row.completedLength }}</template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="100%" align="center">
        <template slot-scope="scope">
          <el-button @click="delDownloadFile( scope.row.id)" type="text">删除</el-button><br />
          <el-button v-if="scope.row.completedLength != '上传完成'" type="text" @click="setAutoUploadFile( scope.row.id)">上传</el-button>
          <el-button v-if="scope.row.completedLength == '上传完成'" type="text" @click="selectFile( scope.row.ossFileName)">查看</el-button>

        </template>

      </el-table-column>
    </el-table>
    <el-pagination :current-page="page" :page-size="size" :total="total" style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper" @current-change="getDownloadList" />




  </div>
</template>

<script>
  import file_sharing from "@/api/file_sharing";


  export default {
    name: "upload",
    data() {
      return {
        list: null, // 数据列表
        page: 1,
        size: 10,
        total: 0,
        tableLoading: false,


        //自定义参数
        url: '',
        serverFileLits: undefined,
      };
    },
    created() {
      this.getDownloadList();
      this.timer = setInterval(this.timerMethods, 3000);
    },
    beforeDestroy() {
      clearInterval(this.timer);
    },

    methods: {
      selectFile(ossFile){
        location.href = 'https://scwtest20200706.oss-cn-beijing.aliyuncs.com/file_sharing/'+ossFile
      },
      setAutoUploadFile(id){
        file_sharing.setAutoUploadFile(id).then(resp => {
          console.log(resp)
          // this.state = 4
          if (resp.code === 20000) {
            this.$message.success(resp.message)
          } else {
            this.$message.error(resp.message);
          }
          this.getDownloadList(this.page);

        })
      },
      delDownloadFile(id){
        file_sharing.delDownloadFile(id).then(resp => {
          console.log(resp)
          // this.state = 4
          if (resp.code === 20000) {
            this.$message.success(resp.message)
          } else {
            this.$message.error(resp.message);
          }
          this.getDownloadList(this.page);

        })
      },
      timerMethods(){
        this.getDownloadList(this.page)
      },
      addDownload() {
        file_sharing.addDownload(this.url).then(resp => {
          console.log(resp)
          // this.state = 4
          if (resp.code === 20000) {
            this.$message.success(resp.message)
          } else {
            this.$message.error(resp.message);
          }
          this.getDownloadList(this.page);

        })
      },
      getDownloadList(page=1) {
        this.page = page
        file_sharing.getDownloadFileList(page).then(resp => {
          console.log(resp)
          // this.state = 4
          if (resp.code === 20000) {
            // this.$message.success(resp.message)
            console.log(resp.data.list);
            this.serverFileLits = resp.data.list
             this.total = resp.data.total
          } else {
            this.$message.error(resp.message);
          }
          // this.getFileList(this.page);

        })
      },

    }
  }
</script>
<style>


</style>
