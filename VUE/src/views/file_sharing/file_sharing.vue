<template>

  <div>

    <div style="text-align: center; margin-top: 30px;">

      <div style="margin-top: 20px;">
        <span> 提 取 码 ：</span>
        <el-input style="width: 300px;" v-model="file_code"></el-input>
      </div>
      <div style="margin-top: 20px;">
        文件名称：<el-input style="width: 300px;" v-model="file_name"></el-input>
      </div>
      <div style="margin-top: 20px;">
        <span>文本信息：</span>
        <el-input style="width: 300px;" v-model="file_text"></el-input>
      </div>

      <div style="margin-top: 20px;">
        <span>当前状态：</span>
        <el-input style="width: 175px;" :disabled="true" v-model="file_state"></el-input>
        </el-input>
        <el-button style="width: 100px;margin-left: 20px;" type="primary" v-clipboard:copy="file_state"
          v-clipboard:success="onCopy" v-clipboard:error="onError">复制</el-button>
      </div>

    </div>

    <div style="text-align: center;margin-top: 30px;">
      <el-upload :before-upload="beroreUpload" :on-success="handleAvatarSuccess" class="upload-demo" :on-progress="uploadVideoProcess"
        drag action="/admin/file_sharing/uploadFile" :http-request="fnUploadRequest" multiple>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      </el-upload>

      <div style="margin-top: 50px;">
        <el-button type="primary" :disabled="submit_flag" @click="submit()">确定</el-button>
      </div>
    </div>
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
    data() {
      return {
        //自定义参数开始------
        file_code: '',
        file_name: '',
        file_text: '',
        return_file_name: '', //状态选择栏的状态
        file_url: '',
        submit_flag: false,
        file_state: '文件未上传',
        progressPercent: undefined,
        //自定义参数结束------
      };
    },

    created() {

    },

    methods: {
      handleAvatarSuccess(res, file) {
        // console.log(file.response.data.url);

        console.log(file);

        if(res.res.requestUrls[0].indexOf('?uploadId=')>0){
           this.file_url = res.res.requestUrls[0].substr(0, res.res.requestUrls[0].lastIndexOf('?'))
        }else{
           this.file_url = res.res.requestUrls[0]
        }
        

        // this.file_url = res.res.requestUrls[0].substr(0,res.res.requestUrls[0].lastIndexOf('?'))
        console.log("文件上传成功，url:" + this.file_url);
        this.return_file_name = file.name
        // this.return_file_name = file.response.data.fileName
        if (this.file_url == null || this.file_url == '') {
          this.$message.error('上传失败');
          this.file_state = '文件上传失败'
        } else {
          this.$message.success('上传成功');
          this.file_state = '文件上传成功'
          // this.submit_flag = false
        }
      },
      submit() {
        // fileCode,fileName,fileText,returnFileName,fileUrl
        file_sharing.setFileOrMessage(this.file_code, this.file_name, this.file_text, this.return_file_name, this.file_url)
          .then(resp => {
            console.log(resp)
            // this.state = 4
            if (resp.code === 20000) {
              this.$message.success(resp.message)
              this.file_state = resp.data.code
            } else {
              this.$message.error(resp.message)
              this.file_state = '表单提交失败'
            }

          })
      },
      onCopy(e) {
        this.$message.success("内容已复制到剪切板！")
      },
      // 复制失败时的回调函数
      onError(e) {
        this.$message.error("抱歉，复制失败！")
      },
      beroreUpload(e) {
        this.file_state = '文件正在上传... '
      },
      uploadVideoProcess(event, file, fileList) {
        // console.log(event.percent, file, fileList)
        // this.videoFlag = true
        // this.videoUploadPercent = file.percentage.toFixed(0)
        // this.videoUploadPercent = event.percent.toFixed(0)
        // this.videoUploadPercent = Math.floor(event.percent)

        console.log(event);
        // console.log(Math.floor(event.percent));
      },
      // handleRequest(data) {
      //   let formdata = new FormData()
      //   formdata.append('file', data.file)
      //   const config = {
      //     onUploadProgress: progressEvent => {
      //       // progressEvent.loaded:已上传文件大小
      //       // progressEvent.total:被上传文件的总大小
      //       this.progressPercent = Number((progressEvent.loaded / progressEvent.total * 100).toFixed(2))
      //       console.log("进度：" + this.progressPercent);
      //       console.log(progressEvent);
      //     }
      //   }
      //   axios.post("/admin/file_sharing/uploadFile", formdata, config).then(res => {
      //     this.$message.error(res.data.message)
      //   })
      // },
      async fnUploadRequest(options) {
        try {
          let file = options.file; // 拿到 file
          let fileName = file.name

          let date = new Date().getTime()
          let fileNames = `${date}_${fileName}` // 拼接文件名，保证唯一，这里使用时间戳+原文件名
          console.log("准备上传");
          // 上传文件,这里是上传到OSS的 uploads文件夹下
          // client.put("file_sharing/"+fileNames, file).then(res=>{
          //     if (res.res.statusCode === 200) {
          //         options.onSuccess(res)
          //     }else {
          //         options.onError("上传失败")
          //     }
          // })

          client.multipartUpload("file_sharing/" + fileNames, file, {
            progress: async function(p) {
              let e = {}
              e.percent = p * 100
              console.log(e.percent);
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
      }

    }
  };
</script>
<style>
</style>
