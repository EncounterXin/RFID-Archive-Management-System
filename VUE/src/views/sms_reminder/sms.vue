<template>
  <div class="app-container">
    <div class="parent">
      <!--查询表单-->
      <el-form :inline="true" class="demo-form-inline">
        <!--        <div style="width: 200px;">
          <el-input v-model="targetFloorNumber" placeholder="请输宿舍楼号" style="margin-bottom: 20px;"></el-input>
          <el-input v-model="targetDormitoryNumber" placeholder="请输入宿舍号" style="margin-bottom: 20px;"></el-input>

          <el-button type="primary" @click="register()" style="margin-left: 150px;">提交</el-button>
        </div> -->
        <!-- 标题 -->
        <!--       <el-form-item label="查询条件">
          <el-radio-group v-model="state" size="medium">
            <el-radio v-for="(item, index) in stateOptions" :key="index" :label="item.value" :disabled="item.disabled"
              style="margin-bottom: 20px;">{{item.label}}</el-radio>
          </el-radio-group>
        </el-form-item> -->


        <!--    <el-button type="primary" icon="el-icon-search" @click="getHistoryList(1)" style="margin-left: 20px;">查询</el-button>
        <br />
        账号：<el-input style="width: 150px;" v-model="acct"></el-input> -->
        <span style="margin-left: 20px;">手机号：</span>
        <el-input style="width: 150px;" v-model="phone"></el-input>
        <a style="margin-left: 20px;"  target="_blank" href="https://qqe2.com/cron/index">Cron：</a>
        <el-input style="width: 150px;" v-model="cron"></el-input>
        <el-button type="primary" @click="addPhone()" style="margin-left: 20px;">添加</el-button>
        <el-button type="primary" @click="getSMSList()" style="margin-left: 20px;">搜索</el-button>
        <!-- <el-button type="default" @click="resetData()">清空</el-button> -->
      </el-form>
    </div>
    <br>
    <!-- 表格 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="数据加载中" border fit highlight-current-row
      row-class-name="myClassList">
      <el-table-column label="手机号" align="center">
        <template slot-scope="scope">{{ scope.row.phone }}</template>
      </el-table-column>
      <el-table-column label="Cron" align="center">
        <template slot-scope="scope">{{ scope.row.cron }}</template>
      </el-table-column>
      <el-table-column label="状态" width="200" align="center">
        <div :style="{'color':scope.row.state=='1'? 'green':'red'}" slot-scope="scope">{{ scope.row.state == '1'?'运行中':'已关闭' }}</div>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" size="mini" icon="el-icon-success" @click="openSMS(scope.row.id)">开启</el-button><br />
          <el-button type="text" size="mini" icon="el-icon-error" @click="closeSMS(scope.row.id)">结束</el-button><br />
          <!-- delQuartzById -->
          <el-button type="text" size="mini" icon="el-icon-error" @click="delQuartzById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination :current-page="page" :page-size="size" :total="total" style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper" @current-change="getSMSList" />
  </div>
</template>

<script>
  import sms_reminder from "@/api/sms_reminder";

  export default {
    data() {
      return {
        listLoading: false, // 是否显示loading信息
        list: null, // 数据列表
        page: 1,
        size: 10,
        total: 0,

        //自定义参数开始------
        phone: undefined,
        cron: undefined
        //自定义参数结束------
      };
    },

    created() {
      this.getSMSList(1)
    },

    methods: {
      addPhone() {
        console.log("test")
        sms_reminder.addPhone(this.phone, this.cron).then(resp => {
          console.log(resp)
          if (resp.code === 20000) {
            this.$message.success(resp.message)
            this.getSMSList(this.page)
          }
        })
      },
      getSMSList(page = 1) {
        this.page = page
        console.log("test")
        sms_reminder.getSMSList(this.page, this.size,this.phone).then(resp => {
          console.log(resp)
          if (resp.code === 20000) {

            this.list = resp.data.list
            this.total = resp.data.total
          }
        })
      },
      openSMS(id) {
        console.log("test")
        sms_reminder.openSMS(id).then(resp => {
          console.log(resp)
          if (resp.code === 20000) {
            this.$message.success(resp.message)
            this.getSMSList(this.page)
          }
        })
      },
      closeSMS(id) {
        console.log("test")
        sms_reminder.closeSMS(id).then(resp => {
          console.log(resp)
          if (resp.code === 20000) {
            this.$message.success(resp.message)
            this.getSMSList(this.page)
          }
        })
      },
      delQuartzById(id){
        sms_reminder.delQuartzById(id).then(resp => {
          console.log(resp)
          if (resp.code === 20000) {
            this.$message.success(resp.message)
            this.getSMSList(this.page)
          }
        })
      }
    }
  };
</script>
<style scoped>
  .myClassList .info {
    width: 450px;
    overflow: hidden;
  }

  .myClassList .info .pic {
    width: 150px;
    height: 90px;
    overflow: hidden;
    float: left;
  }

  .myClassList .info .pic a {
    display: block;
    width: 100%;
    height: 100%;
    margin: 0;
    padding: 0;
  }

  .myClassList .info .pic img {
    display: block;
    width: 100%;
  }

  .myClassList td .info .title {
    width: 280px;
    float: right;
    height: 90px;
  }

  .myClassList td .info .title a {
    display: block;
    height: 48px;
    line-height: 24px;
    overflow: hidden;
    color: #00baf2;
    margin-bottom: 12px;
  }

  .myClassList td .info .title p {
    line-height: 20px;
    margin-top: 5px;
    color: #818181;
  }

  .parent {
    width: 5600px;
    /* height: 200px; */
    /* border: 1px solid #f00; */
    display: table-cell;
    text-align: center;
    vertical-align: middle;
  }
</style>
