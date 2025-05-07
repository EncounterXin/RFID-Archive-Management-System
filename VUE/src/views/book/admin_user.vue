<template>

	<div>
		<el-dialog title="修改" :visible.sync="dialogFormVisible">

			<el-input v-model="user.uname" placeholder="请输入学号" style="margin-bottom: 20px; "></el-input>
			<el-input v-model="user.email" placeholder="请输入姓名" style="margin-bottom: 20px;"></el-input>
			<el-input v-model="user.pwd" placeholder="请输入密码" style="margin-bottom: 20px;" show-password>></el-input>

			<el-input v-model="user.age" placeholder="请输入年龄" style="margin-bottom: 20px;"></el-input>
			<!-- <el-input v-model="user.sex" placeholder="请输入确认性别" style="margin-bottom: 20px;" show-password></el-input>
			 -->
			<el-select v-model="user.sex" placeholder="请选择性别" style="margin-bottom: 20px;width: 100%;">
				<el-option v-for="(item, index) in sexOptions" :key="index" :label="item.label" :value="item.value"
					:disabled="item.disabled"></el-option>
			</el-select>
			<el-input v-model="user.major" placeholder="请输入专业" style="margin-bottom: 20px;"></el-input>
			<el-input v-model="user.grade" placeholder="请输入年级" style="margin-bottom: 20px;"></el-input>

			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogFormVisible = false">取 消</el-button>
				<el-button type="primary" @click="updateUserInfoSubmit">确 定</el-button>
			</div>
		</el-dialog>
		<el-dialog title="提示" :visible.sync="dialogFlag" width="30%">
			<div style="text-align: center;">
				<el-input v-model="newPwd" placeholder="请输入新密码"
					style="display:inline-block;width: 200px; margin-right: 10px;">
				</el-input>
				<el-button @click='changePwd' type="primary">修改密码</el-button>
			</div>
		</el-dialog>
		<div style="margin-top: 20px;margin-left: 20px;">
			<span style="font-size: 10px;margin-left: 30px;">学号：</span>
			<el-input v-model="searchData.uname" placeholder="" clearable style="width:120px;margin-right: 5px;">
			</el-input>
			<span style="font-size: 10px;margin-left: 10px;">姓名：</span>
			<el-input v-model="searchData.email" placeholder="" clearable style="width:120px;margin-right: 5px;">
			</el-input>
			<span style="font-size: 10px;margin-left: 10px;">专业：</span>
			<el-input v-model="searchData.major" placeholder="" clearable style="width:120px;margin-right: 5px;">
			</el-input>
			<el-button type="primary" @click="searchDataList(1)">搜索</el-button>
		</div>
		<el-table v-loading="tableLoading" :data="userLits" element-loading-text="数据加载中" border fit
			highlight-current-row row-class-name="myClassList" style="margin-top: 20px;">
			<el-table-column label="学号" align="center">
				<template slot-scope="scope">{{ scope.row.uname }}</template>
			</el-table-column>

			<el-table-column label="用户名" align="center">
				<template slot-scope="scope">{{ scope.row.email }}</template>
			</el-table-column>
			<el-table-column label="密码" align="center">
				<template slot-scope="scope">{{ scope.row.pwd }}</template>
			</el-table-column>
			<el-table-column label="年龄" align="center">
				<template slot-scope="scope">{{ scope.row.age }}</template>
			</el-table-column>
			<el-table-column label="性别" align="center">
				<template slot-scope="scope">{{ scope.row.sex }}</template>
			</el-table-column>
			<el-table-column label="专业" align="center">
				<template slot-scope="scope">{{ scope.row.major }}</template>
			</el-table-column>
			<el-table-column label="年级" align="center">
				<template slot-scope="scope">{{ scope.row.grade }}</template>
			</el-table-column>
			<el-table-column label="信誉分" align="center">
				<template slot-scope="scope">{{ scope.row.score }}</template>
			</el-table-column>
			<el-table-column label="借书次数" align="center">
				<template slot-scope="scope">
					<el-button type="danger" size="mini" v-if="scope.row.bookCount < 10">{{ scope.row.bookCount }}
					</el-button>
					<el-button type="primary" size="mini" v-if="scope.row.bookCount >= 10 && scope.row.bookCount <50">
						{{ scope.row.bookCount }}</el-button>
					<el-button type="success" size="mini" v-if="scope.row.bookCount >= 50">{{ scope.row.bookCount }}
					</el-button>
				</template>
			</el-table-column>
			<el-table-column fixed="right" label="操作" width="120" align="center">
				<template slot-scope="scope">
					<el-button
						@click="dialogFormVisible = true;changeUid = scope.row.id;getUpdateUserInfo(scope.row.id)"
						type="text">修改</el-button>
					<el-button @click="deleteUser(scope.row.id)" type="text">删除</el-button>
				</template>

			</el-table-column>


		</el-table>
		<el-pagination :current-page="page" :page-size="size" :total="total"
			style="padding: 30px 0; text-align: center;" layout="total, prev, pager, next, jumper"
			@current-change="getUserList" />




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
				userLits: undefined,
				uname: undefined,
				dialogFlag: false,
				newPwd: undefined,
				changeUid: undefined,
				searchData: {
					uname: undefined,
					email: undefined,
					major: undefined
				},
				dialogFormVisible: false,
				user: {
					uname: '',
					pwd: '',
					pwdToo: '',
					age: '',
					sex: '',
					major: '',
					grade: '',
					email: '',
				},
				sexOptions: [{
					"label": "男",
					"value": "男"
				}, {
					"label": "女",
					"value": "女"
				}],
			};
		},
		created() {
			this.getUserList()
		},

		methods: {
			updateUserInfoSubmit() {
				bookAxios.updateUserAllInfoAxios(
						this.changeUid,
						this.user.uname,
						this.user.pwd,
						this.user.age,
						this.user.sex,
						this.user.major,
						this.user.grade,
						this.user.email)
					.then(resp => {

						if (resp.code == 20000) {
							this.$message({
								message: resp.message,
								type: 'success'
							});
							this.user.uname = undefined;
							this.user.pwd = undefined;
							this.user.pwdToo = undefined;
							this.user.age = undefined;
							this.user.sex = undefined;
							this.user.major = undefined;
							this.user.grade = undefined;
							this.user.email = undefined;
							this.dialogFormVisible = false
							this.getUserList()
						} else {
							this.$message.error(resp.message);
						}
						this.dialogFormVisible = false




					})
					.catch(resp => {});
			},
			getUpdateUserInfo(id) {
				bookAxios.getUserInfoByUidAxios(id).then(resp => {
					console.log(resp)
					// this.state = 4
					if (resp.code === 20000) {
						// this.$message.success(resp.message)
						this.user.uname = resp.data.data.uname
						this.user.pwd = resp.data.data.pwd
						this.user.age = resp.data.data.age
						this.user.sex = resp.data.data.sex
						this.user.major = resp.data.data.major
						this.user.grade = resp.data.data.grade
						this.user.email = resp.data.data.email
						this.tableLoading = false
					} else {
						this.$message.error(resp.message);
					}
					// this.getFileList(this.page);
				})
			},
			searchDataList(page = 1) {
				this.page = page
				bookAxios.getUserListByConditionAxios(this.searchData.uname,
					this.searchData.email,
					this.searchData.major,
					this.page, this.size).then(resp => {
					console.log(resp)
					// this.state = 4
					if (resp.code === 20000) {
						// this.$message.success(resp.message)
						this.userLits = resp.data.list
						this.total = resp.data.total
						this.tableLoading = false
					} else {
						this.$message.error(resp.message);
					}
					// this.getFileList(this.page);
				})
			},
			getUserList(page = 1) {
				this.page = page
				bookAxios.getUserListAxios(this.page, this.size).then(resp => {
					console.log(resp)
					// this.state = 4
					if (resp.code === 20000) {
						// this.$message.success(resp.message)
						this.userLits = resp.data.list
						this.total = resp.data.total
						this.tableLoading = false
					} else {
						this.$message.error(resp.message);
					}
					// this.getFileList(this.page);
				})
			},
			changePwd() {
				bookAxios.changePwdAxios(this.changeUid, this.newPwd).then(resp => {
					console.log(resp)
					// this.state = 4
					if (resp.code === 20000) {
						this.$message.success(resp.message)

					} else {
						this.$message.error(resp.message);
					}
					this.getUserList(this.page);
				})
			},
			deleteUser(uid) {
				bookAxios.delUserAxios(uid).then(resp => {
					console.log(resp)
					// this.state = 4
					if (resp.code === 20000) {
						this.$message.success(resp.message)

					} else {
						this.$message.error(resp.message);
					}
					this.getUserList(this.page);
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
		}
	}
</script>
<style>


</style>
