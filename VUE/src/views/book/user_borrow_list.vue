<template>

	<div>

    <el-dialog title="提示" :visible.sync="shareDialogFlag" width="30%" >
      <div style="text-align: center;">
          <span>链接：</span>
          <span style="width: 175px;">{{ file_share_url }}</span>
          </el-input>
      </div>
    </el-dialog>
		<div style="margin-top: 20px;margin-left: 20px;">

			<el-select v-model="borrowStatus" placeholder="" clearable :style="{width: '15%'}">
				<el-option v-for="(item, index) in borrowStatusOptions" :key="index" :label="item.label"
					:value="item.value" :disabled="item.disabled"></el-option>
			</el-select>
			<el-button style="margin-left: 10px;" type="primary" @click="selectBorrowListByStatus(1)">搜索</el-button>
		</div>
		<el-table v-loading="tableLoading" :data="bookList" element-loading-text="数据加载中" border fit
			highlight-current-row row-class-name="myClassList" style="margin-top: 20px;">
			<el-table-column label="档案名称" width="300" align="center">
				<template slot-scope="scope">{{ scope.row.bookName }}</template>
			</el-table-column>

			<el-table-column label="所属人" align="center">
				<template slot-scope="scope">{{ scope.row.author }}</template>
			</el-table-column>
			<el-table-column label="出版社" align="center">
				<template slot-scope="scope">{{ scope.row.press }}</template>
			</el-table-column>
			<el-table-column label="借阅时间" align="center">
				<template slot-scope="scope">{{ scope.row.getTime }}</template>
			</el-table-column>
			<el-table-column label="计划归还时间" align="center">
				<template slot-scope="scope">{{ formatTimer(scope.row.endTime) }}</template>
			</el-table-column>
			<el-table-column label="实际归还时间" align="center">
				<template slot-scope="scope">{{ scope.row.returnTime }}</template>
			</el-table-column>

			<el-table-column label="状态" align="center">
				<template slot-scope="scope">
					<el-button type="primary" size="mini" v-if="scope.row.status == '已归还'">{{ scope.row.status }}
					</el-button>
					<el-button type="danger" size="mini" v-if="scope.row.status == '未归还'">{{ scope.row.status }}
					</el-button>
				</template>
			</el-table-column>
			<el-table-column label="逾期情况" align="center">
				<template slot-scope="scope">
					<el-button type="primary" size="mini" v-if="scope.row.overdue == '正常'">{{ scope.row.overdue }}
					</el-button>
					<el-button type="danger" size="mini" v-if="scope.row.overdue == '逾期' ">{{ scope.row.overdue }}
					</el-button>
				</template>
			</el-table-column>
			<el-table-column fixed="right" label="操作" width="100" align="center">
				<template slot-scope="scope">
					  <router-link :to="'/book/bookInfo/'+scope.row.id">
						<el-button type="text" size="mini">查看档案信息</el-button>
					  </router-link>
					  <el-button type="text" @click="shareSuccessFlag=true;fileShareId = scope.row.id;shareDialogFlag = true;getUrl()">分享</el-button>
				</template>
			</el-table-column>
			<!-- 			<el-table-column label="借阅次数" align="center" >
				<template slot-scope="scope">{{ scope.row.selectNumber }}</template>
			</el-table-column>
 -->

		</el-table>
		<el-pagination :current-page="page" :page-size="size" :total="total"
			style="padding: 30px 0; text-align: center;" layout="total, prev, pager, next, jumper"
			@current-change="getbookList" />




	</div>
</template>

<script>
	import bookAxios from "@/api/book";
	import axios from 'axios';


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
				fileShareId:undefined,
				file_share_url:undefined,
				shareDialogFlag:false,
				imageUrl: '',
				bookList: undefined,
				addressName: undefined,
				dialogFlag: false,
				newaddressName: undefined,
				changeAddrssId: undefined,
				addDialogFlag: false,
				updateDialogFlag: false,
				formData: {
					addSortName: undefined,
					addBookSortId: undefined,
					addBookAddress: undefined,
					addreddRfid: undefined,
					addBook: undefined,
					updateBookAddress: undefined,
					publicationDate: undefined,
					author: undefined,
					press: undefined,
					bookCount: undefined,
				},
				updateFormData: {
					addSortName: undefined,
					addBookSortId: undefined,
					addBookAddress: undefined,
					addreddRfid: undefined,
					addBook: undefined,
					updateBookAddress: undefined,
					publicationDate: undefined,
					author: undefined,
					press: undefined,
					bookCount: undefined,
				},
				searchData: {
					bookName: undefined,
					author: undefined,
					press: undefined
				},
				addBookSortIdOptions: [{
					"label": "选项一",
					"value": 1
				}, {
					"label": "选项二",
					"value": 2
				}],
				addBookAddressOptions: [{
					"label": "选项一",
					"value": 1
				}, {
					"label": "选项二",
					"value": 2
				}],
				updateBookSortIdOptions: [{
					"label": "选项一",
					"value": 1
				}, {
					"label": "选项二",
					"value": 2
				}],
				borrowStatus: undefined,
				borrowStatusOptions: [{
					"label": "未归还",
					"value": 0
				}, {
					"label": "已归还",
					"value": 1
				}],
			};
		},
		created() {
			this.selectBorrowList()
		},

		methods: {
			getUrl(){
				console.log(this.fileShareId)
				console.log(window.location.href );
				var url = window.location.href
				url =url.substring(0,url.length-14) + 'bookInfo/'+this.fileShareId
				console.log(url);
				this.file_share_url = url
			},
			selectBorrowListByStatus(page = 1) {
				this.page = page
				bookAxios.selectBorrowListByStatus(this.borrowStatus,this.page, this.size).then(resp => {
					console.log(resp)
					// this.state = 4
					if (resp.code === 20000) {
						// this.$message.success(resp.message)
						this.bookList = resp.data.list
						this.total = resp.data.total
						this.tableLoading = false
					} else {
						this.$message.error(resp.message);
					}
					// this.getFileList(this.page);
				})
			},
			selectBorrowList(page = 1) {
				this.page = page
				bookAxios.selectBorrowList(this.page, this.size).then(resp => {
					console.log(resp)
					// this.state = 4
					if (resp.code === 20000) {
						// this.$message.success(resp.message)
						this.bookList = resp.data.list
						this.total = resp.data.total
						this.tableLoading = false
					} else {
						this.$message.error(resp.message);
					}
					// this.getFileList(this.page);
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
				// return y + "-" + MM + "-" + d + " " + h + ":" + m;
				return y + "-" + MM + "-" + d;
			},
		}
	}
</script>
<style>
	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}
</style>
