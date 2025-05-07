<template>

  <div>

    <!-- 抽屉 右侧文件上传进度 -->
    <el-drawer title="文件上传进度:" :visible.sync="table" direction="rtl" size="40%">
      <el-table v-loading="false" :data="filePercentList" height="90%" element-loading-text="数据加载中" border fit
        highlight-current-row row-class-name="myClassList">
        <el-table-column label="文件名" align="center" width="400">
          <template slot-scope="scope">{{ scope.row.name }}</template>
        </el-table-column>
        <el-table-column label="进度" align="center">
          <!-- <template slot-scope="scope">{{ scope.row.percent }}</template> -->

          <template slot-scope="scope">
            <!-- <el-progress  width=60px type="circle" :percentage="scope.row.percent"  ></el-progress> -->
            <el-progress :percentage="scope.row.percent" :format="format" :status="scope.row.status"></el-progress>
          </template>

        </el-table-column>

      </el-table>
    </el-drawer>


    <!-- 分享文件时显示 -->

    <el-dialog title="提示" :visible.sync="shareDialogFlag" width="30%" >
      <div style="text-align: center;">
        <el-radio-group v-model="fileShareDate" size="medium">
          <el-radio v-for="(item, index) in fileShareDateOptions" :key="index" :label="item.value" :disabled="item.disabled"
            style="margin-bottom: 20px;">{{item.label}}</el-radio>
        </el-radio-group><br /><br /><br />
        <el-button @click='panShareFile' type="primary">开始分享</el-button>
        <div style="margin-top: 20px;" :hidden="shareSuccessFlag">
          <span>链接：</span>
          <span style="width: 175px;">{{ file_share_url }}</span>
          </el-input>
          <el-button style="width: 100px;margin-left: 20px;" type="text" v-clipboard:copy="file_share_url"
            v-clipboard:success="onCopy" v-clipboard:error="onError">复制</el-button>
        </div>
      </div>
    </el-dialog>
    <div style="margin-top: 20px;margin-left: 20px;">
      <el-upload class="upload-demo" action="" :show-file-list="false" :http-request="fnUploadRequest" :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload" multiple style="display:inline-block;" :file-list="fileList">
        <el-button type="primary" style="display:inline-block;">上传文件(多选)</el-button>
      </el-upload>
      <span style="margin-left: 40px;">文件名：</span>
      <el-input v-model="searchFileName" placeholder="请输入内容" style="display:inline-block;width: 200px;"></el-input>
      <el-button type="primary" @click="getFileList()" icon="el-icon-search" circle style="display:inline-block;margin-left: 10px;"></el-button>
      <el-button type="primary" @click="table = true" style="display:inline-block;float:right;margin-right: 20px; ">查看上传进度</el-button>
    </div>
    <el-table v-loading="tableLoading" :data="serverFileLits" element-loading-text="数据加载中" border fit
      highlight-current-row row-class-name="myClassList" @sort-change="changeSort" style="margin-top: 20px;">
      <el-table-column label="文件名称" align="center" sortable>
        <template slot-scope="scope">{{ scope.row.fileName }}</template>
      </el-table-column>

      <el-table-column label="上传时间" align="center" width="300" sortable>
        <template slot-scope="scope">{{ formatTimer(scope.row.createDate) }}</template>
      </el-table-column>
      <el-table-column label="文件大小" align="center" width="150" sortable>
        <template slot-scope="scope">{{ fileSizeUtil(scope.row.fileSize) }}</template>
      </el-table-column>
      <el-table-column label="文件类型" align="center" width="150" sortable>
        <template slot-scope="scope">{{ scope.row.fileType }}</template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button @click="download( scope.row.fileUrl)" type="text">下载</el-button>
          <el-button type="text" @click="shareSuccessFlag=true;fileShareId = scope.row.id;shareDialogFlag = true">分享</el-button>
          <el-button @click="deleteFile(scope.row.id)" type="text">删除</el-button>
        </template>

      </el-table-column>


    </el-table>
    <el-pagination :current-page="page" :page-size="size" :total="total" style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper" @current-change="getFileList" />




  </div>
</template>

<script>
  import file_sharing from "@/api/file_sharing";
  import Clipboard from 'clipboard';
  import axios from 'axios';
  import OSS from 'ali-oss'
  // let OSS = require('ali-oss');

  let client = new OSS({
    region: 'oss-cn-beijing',
    accessKeyId: 'LTAI5tB6UtnuB71w6WMN8zLH',
    accessKeySecret: 'fG8OB3sd5W76hBWVGCg7tFYu8oYKp8',
    bucket: 'scwtest20200706', //bucket名字
  });

  export default {
    name: "upload",
    data() {
      return {
        list: null, // 数据列表
        page: 1,
        size: 10,
        total: 0,
        tableLoading: true,


        //自定义参数
        imageUrl: '',
        fileList: [],
        filePercentList: [],
        fileSize: 0,
        table: false,
        serverFileLits: [], //后端获取的文件列表 用于前端展示
        fileSort: 0,
        searchFileName: '',
        shareDialogFlag: false,
        fileShareDate: 4,
        fileShareId: '',
        file_share_url: '11',
        shareSuccessFlag: true,
        fileShareDateOptions: [{
            label: "一天",
            value: 1
          }, {
            label: "七天",
            value: 2
          },
          {
            label: "一个月",
            value: 3
          },
          {
            label: "永久",
            value: 4
          },

        ]



      };
    },
    created() {
      this.getFileList();
    },

    methods: {
      //分享
      panShareFile() {

        file_sharing.panShareFile(this.fileShareId,this.fileShareDate).then(resp => {
          console.log(resp)
          // this.state = 4
          if (resp.code === 20000) {
            this.$message.success(resp.message)
            this.file_share_url = resp.data.fileCode
            this.shareSuccessFlag = false
          } else {
            this.$message.error(resp.message);
          }
          // this.getFileList(this.page);

        })

      },
      //删除
      deleteFile(fileId) {
        if (fileId == null || fileId == '') {
          this.$message.error('请选择要删除的文件');
          return
        }
        file_sharing.panDeleteFileById(fileId).then(resp => {
          console.log(resp)
          // this.state = 4
          if (resp.code === 20000) {
            this.$message.success(resp.message)
          } else {
            this.$message.error(resp.message);
          }
          this.getFileList(this.page);

        })

      },
      //下载
      download(url) {
        if (url == null || url == '') {
          this.$message.error('下载链接获取失败');
          return
        }
        location.href = url
      },
      onCopy(e) {
        this.$message.success("内容已复制到剪切板！")
      },
      // 复制失败时的回调函数
      onError(e) {
        this.$message.error("抱歉，复制失败！")
      },
      //重新排序
      changeSort(val) {
        console.log(val.column.label) // column: {…} order: "ascending" prop: "date"
        // 根据当前排序重新获取后台数据,一般后台会需要一个排序的参数
        console.log(val.order);

        let tableType = val.column.label
        let tableSort = val.order

        //sort - 0：时间倒序 1：时间正序
        //       2：文件大小 从大到小 3：从小到大
        //       4：文件类型倒序 5：文件类型正序
        //       6：文件名称倒序 7：文件名称正序
        if (tableType == '文件名称' && tableSort == 'ascending') {
          this.fileSort = 7
        }
        if (tableType == '文件名称' && tableSort == 'descending') {
          this.fileSort = 6
        }
        if (tableType == '上传时间' && tableSort == 'ascending') {
          this.fileSort = 1
        }
        if (tableType == '上传时间' && tableSort == 'descending') {
          this.fileSort = 0
        }
        if (tableType == '文件大小' && tableSort == 'ascending') {
          this.fileSort = 3
        }
        if (tableType == '文件大小' && tableSort == 'descending') {
          this.fileSort = 2
        }
        if (tableType == '文件类型' && tableSort == 'ascending') {
          this.fileSort = 5
        }
        if (tableType == '文件类型' && tableSort == 'descending') {
          this.fileSort = 4
        }
        this.getFileList()


      },
      //图片上传成功回调
      handleAvatarSuccess(res, file, fileList) {

        let fileName = file.name
        let fileSize = file.size
        let fileUrl = ''
        // let  type  = fileName.substr(fileName.length-3,file.length)

        if(res.res.requestUrls[0].indexOf('?uploadId=')>0){
           fileUrl = res.res.requestUrls[0].substr(0, res.res.requestUrls[0].lastIndexOf('?'))
        }else{
           fileUrl = res.res.requestUrls[0]
        }

        console.log(res);
        console.log(fileUrl);

        let fileId = file.uid

        file_sharing.panAddFile(fileName, fileUrl, fileSize)
          .then(resp => {
            console.log(resp)

            if (resp.code === 20000) {

              console.log("fileId", fileId);
              if (resp.data.line == -1) {
                this.filePercentList.forEach(item => {
                  // console.log(item);
                  if (item.id == fileId) {
                    item.status = 'exception';
                  }
                })
              } else {
                this.getFileList(this.page, this.fileSort)
              }

            } else {
              console.log('list', this.filePercentList);
            }

          })
      },
      //进度条使用  100%时显示为 上传成功
      format(percentage) {
        return percentage >= 100 ? '上传成功' : `${percentage}%`;
      },
      // 上传之前
      beforeAvatarUpload(file, fileList) {

        if (file.name != null && file.name != '') {
          this.table = true
          this.filePercentList.unshift({

            'id': file.uid,
            'name': file.name,
            'size': file.size,
            'percent': 0,
            'status': '',
          })

        }
        this.fileSize++

      },

      async fnUploadRequest(options) {
        // console.log(this.fileList);
        try {
          let file = options.file; // 拿到 file
          let fileName = file.name

          let date = new Date().getTime()
          let fileNames = `${date}_${fileName}` // 拼接文件名，保证唯一，这里使用时间戳+原文件名
          // console.log("准备上传");
          // 上传文件,这里是上传到OSS的 uploads文件夹下
          // client.put("file_sharing/"+fileNames, file).then(res=>{
          //     if (res.res.statusCode === 200) {
          //         options.onSuccess(res)
          //     }else {
          //         options.onError("上传失败")
          //     }
          // })

          // let file_l = this.filePercentList
          // console.log(options);
          const _this = this
          client.multipartUpload("file_sharing/" + fileNames, file, {
            progress: async function(p) {
              let e = {}
              e.percent = p * 100
              let fileId = file.uid
              // console.log('this',_this.filePercentList);
              _this.filePercentList.forEach(item => {
                // console.log(item);
                if (item.id == fileId) {
                  item.percent = Math.floor(e.percent);
                }
              })
              options.onProgress(e)
            }
          }).then(res => {
            if (res.res.statusCode === 200) {
              options.onSuccess(res)
            } else {
              options.onError("上传失败")
            }
          })

        } catch (e) {
          options.onError("上传失败")
        }
      },
      getFileList(page = 1) {
        this.tableLoading = true
        this.page = page
        file_sharing.panGetFileList(page, this.size, this.fileSort, this.searchFileName).then(resp => {

          console.log(resp)
          this.serverFileLits = resp.data.list
          this.total = resp.data.total
          this.tableLoading = false

        })
      },
      //时间格式化
      formatTimer: function(value) {
        // console.log("时间" + value);

        let date = new Date(value);
        let y = date.getFullYear();
        let MM = date.getMonth() + 1;
        MM = MM < 10 ? "0" + MM : MM;
        let d = date.getDate();
        d = d < 10 ? "0" + d : d;
        let h = date.getHours();
        h = h < 10 ? "0" + h : h;
        let m = date.getMinutes();
        m = m < 10 ? "0" + m : m;
        let s = date.getSeconds();
        s = s < 10 ? "0" + s : s;
        return y + "-" + MM + "-" + d + " " + h + ":" + m;
      },
      //文件大小工具类
      fileSizeUtil(fileSize) {

        if (fileSize >= 1073741824) {
          let size = fileSize / 1073741824

          return parseFloat(size).toFixed(2) + " G"
        } else {

          let size = fileSize / 1048576
          return parseFloat(size).toFixed(2) + " M"
        }


      }
    }
  }
</script>
<style>


</style>
