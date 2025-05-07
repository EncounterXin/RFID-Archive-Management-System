<template>

  <div>

    <div style="text-align: center; margin-top: 80px;">

      <div :hidden="!getFileSuccessFlag">
        <div style="margin-top: 20px;">
          文件名称：<el-input style="width: 300px;" :disabled="true" v-model="file_name"></el-input>
        </div>
        <div style="margin-top: 20px;">
          文件大小：<el-input style="width: 300px;" :disabled="true" v-model="file_size"></el-input>
        </div>
        <div style="margin-top: 20px;">
          文件类型：<el-input style="width: 300px;" :disabled="true" v-model="file_type"></el-input>
        </div>

        <div style="margin-top: 20px;">
          <span>上传时间：</span>
          <el-input style="width: 300px;" :disabled="true" v-model="file_date"></el-input>
        </div>
        <div style="margin-top: 20px;">
          <span>文件下载：</span>
          <span style="width: 300px;">
            <el-button style="width: 300px;" type="primary" :disabled='file_dow_button_flag' @click="download()">下载</el-button>
          </span>

        </div>
      </div>

      <div :hidden="getFileSuccessFlag">
        <div style="font-size: 50px; color: red;margin-top: 200px;">{{getFileErrorMessage}}</div>
      </div>
    </div>

  </div>
</template>

<script>
  import file_sharing from "@/api/file_sharing";
  import Clipboard from 'clipboard';

  export default {
    data() {
      return {
        //自定义参数开始------
        file_code: '111',
        file_name: '222',
        file_size: '333',
        file_type: '444',
        file_date: '555',
        file_url:'',
        file_dow_button_flag: true,
        getFileSuccessFlag:false,
        getFileErrorMessage:'文件获取中...',
        //自定义参数结束------
      };
    },

    created() {
      let code = this.$route.params.code;
      console.log(code);
      if (code == ':code' || code == null || code == '') {
        this.file_code = ''
        this.getFileSuccessFlag = false
        this.getFileErrorMessage = '文件id不能为空'
      } else {
        this.file_code = code
        //远程访问服务器，获取文件信息
        this.getPanFileByShareCode()
      }
    },

    methods: {
      getPanFileByShareCode() {
        // fileCode,fileName,fileText,returnFileName,fileUrl
        this.file_dow_button_flag = true


        file_sharing.getPanFileByShareCode(this.file_code).then(resp => {
          console.log(resp)
          // this.state = 4
          if (resp.code === 20000) {
            this.getFileSuccessFlag = true
            this.$message.success(resp.message)
            this.file_name = resp.data.file.fileName
            this.file_size = this.fileSizeUtil(resp.data.file.fileSize)
            console.log('file_size',this.file_size);

            this.file_type = resp.data.file.fileType
            this.file_date = this.formatTimer(resp.data.file.createDate)
            this.file_dow_button_flag = false
            this.file_url =  resp.data.file.fileUrl
            // if(this.file_date != null && this.file_date != ''){
            //    this.file_date_flag = false
            // }
          } else {
            console.log("1111");
            this.getFileSuccessFlag = false
            this.getFileErrorMessage = resp.message
            console.log('errormessage',this.getFileErrorMessage);
            this.$message.error(resp.message)

          }

        }).catch(resp=>{
          console.log("222");this.getFileSuccessFlag = false
            this.getFileErrorMessage = resp.message
        })
      },
      download() {

        location.href = this.file_url
      },
      formatTimer: function(value) {
        console.log("时间" + value);

        let date = new Date(value);
        console.log("时间2" + date);
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

      copy() {
        console.log("copy");
        var clipboard = new Clipboard('.tag-read')
        clipboard.on('success', e => {
          console.log('复制成功')

          this.$message({
            message: '复制成功',
            type: 'success'
          });
          // 释放内存
          clipboard.destroy()
        })
        clipboard.on('error', e => {
          // 不支持复制
          console.log('该浏览器不支持自动复制')
          this.$message.error('该浏览器不支持自动复制');
          // 释放内存
          clipboard.destroy()
        })
      },
      onCopy(e) {
        this.$message.success("内容已复制到剪切板！")
      },
      // 复制失败时的回调函数
      onError(e) {
        this.$message.error("抱歉，复制失败！")
      },
      //文件大小工具类
      fileSizeUtil(fileSize) {
        console.log(fileSize);

        if (fileSize >= 1073741824) {
          let size = fileSize / 1073741824

          return parseFloat(size).toFixed(2) + " G"
        } else {

          let size = fileSize / 1048576
          return parseFloat(size).toFixed(2) + " M"
        }


      }

    }
  };
</script>
<style>
</style>
