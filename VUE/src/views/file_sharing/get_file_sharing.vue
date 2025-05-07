<template>

  <div>

    <div style="text-align: center; margin-top: 30px;">

      <div style="margin-top: 20px;">
        <span> 提 取 码 ：</span>
        <el-input style="width: 175px;" v-model="file_code">

        </el-input><el-button style="width: 100px;margin-left: 20px;" type="primary" @click="getFileInfo()">提取</el-button>
      </div>
      <div style="margin-top: 20px;">
        文件名称：<el-input style="width: 300px;"  :disabled="file_name_flag" v-model="file_name"></el-input>
      </div>
      <div style="margin-top: 20px;">
        <span>文本信息：</span>
        <el-input style="width: 175px;"  :disabled="file_text_flag" v-model="file_text"></el-input>
        </el-input><el-button style="width: 100px;margin-left: 20px;" type="primary"  v-clipboard:copy="file_text" v-clipboard:success="onCopy" v-clipboard:error="onError" >复制</el-button>
      </div>

      <div style="margin-top: 20px;">
        <span>上传时间：</span>
        <el-input style="width: 300px;"  :disabled="file_date_flag" v-model="file_date"></el-input>
      </div>
      <div style="margin-top: 20px;">
        <span>文件下载：</span>
        <span style="width: 300px;">
          <el-button style="width: 300px;" type="primary" :disabled='file_upload_button_flag' @click="download()">下载</el-button>
        </span>

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
        file_code: '',
        file_name: '',
        file_text: '',
        return_file_name: '', //状态选择栏的状态
        file_url:'',
        file_date:'',
        file_name_flag:true,
        file_text_flag:true,
        file_upload_button_flag:true,
        file_date_flag:true,
        //自定义参数结束------
      };
    },

    created() {
         let code = this.$route.params.code;
         console.log(code);
         if(code == ':code' || code == null || code == ''){
           this.file_code = ''
         }else{
           this.file_code = code
         }
    },

    methods: {
      getFileInfo(){
        // fileCode,fileName,fileText,returnFileName,fileUrl
        this.file_name_flag=true
        this.file_text_flag=true
        this.file_upload_button_flag=true
        this.file_date_flag=true


       file_sharing.getFileInfoByCode(this.file_code).then(resp=>{
         console.log(resp)
         // this.state = 4
         if(resp.code === 20000){
           // this.$message.success(resp.message)
           this.file_name = resp.data.data.fileName
           this.file_text = resp.data.data.fileText
           this.file_url = resp.data.data.fileUrl
           this.file_date = this.formatTimer(resp.data.data.fileCreateTime)

           if(this.file_name != null && this.file_name != ''){
              this.file_name_flag = false
           }
           if(this.file_text != null && this.file_text != ''){
              this.file_text_flag = false
           }
           if(this.file_url != null && this.file_url != ''){
              this.file_upload_button_flag = false
           }

           // if(this.file_date != null && this.file_date != ''){
           //    this.file_date_flag = false
           // }
         }else{
           this.$message.error(resp.message)
         }

       })
      },
      download(){

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
      onCopy (e) {
         this.$message.success("内容已复制到剪切板！")
      },
      // 复制失败时的回调函数
      onError (e) {
         this.$message.error("抱歉，复制失败！")
      }

    }
  };
</script>
<style>
</style>
