<template>
	<div style="width: 90%; margin-left: 5%;margin-top: 30px;">
		<el-container style=" border: 1px solid #eee">
			<el-aside width="200px">
				<el-image style="width: 150px;margin-top: 30px;" :src='imageUrl' fit="scale-down">
				</el-image>
				<div style="margin-left: 30%;">档案</div>
			</el-aside>

			<el-container>
				<el-header style="text-align: left; font-size: 20px;height: 80px;">
					<h3>{{updateFormData.addSortName}}</h3>
				</el-header>

				<el-main>
					<hr />
					<div>
						<table cellpadding="10px" cellspacing="0px" style="width: 100%;">
							<tr>
								<th style="text-align: center;background-color: #00BAF2;">书目信息</th>
								<td style="text-align: left;background-color: #BFCBD9;">机读格式</td>
							</tr>

							<tr>
								<th style="text-align: right;">所属人：</th>
								<td style="text-align: left;">{{updateFormData.author}}</td>
							</tr>
							<tr>
								<th style="text-align: right;">出版社：</th>
								<td style="text-align: left;">{{updateFormData.press}}</td>
							</tr>
							<tr>
								<th style="text-align: right;">所属分类：</th>
								<td style="text-align: left;">{{updateFormData.addBookSortId}}</td>
							</tr>
							<tr>
								<th style="text-align: right;">馆藏区域：</th>
								<td style="text-align: left;">{{updateFormData.addBookAddress}}</td>
							</tr>

							<tr>
								<th style="text-align: right;">档案时间：</th>
								<td style="text-align: left;">{{formatTimer(updateFormData.publicationDate)}}</td>
							</tr>
							<tr>
								<th style="text-align: right;">档案数量：</th>
								<td style="text-align: left;">{{updateFormData.bookCount}}</td>
							</tr>
							<tr>
								<th style="text-align: right;">借阅状态：</th>
								<td style="text-align: left;">{{updateFormData.bookStatus}}</td>
							</tr>
							<tr>
								<th style="text-align: right;">ISBN编号：</th>
								<td style="text-align: left;">{{updateFormData.ISBNCode}}</td>
							</tr>
							<tr>
								<th style="text-align: right;">RFID编号：</th>
								<td style="text-align: left;">{{updateFormData.addreddRfid}}</td>
							</tr>
							


						</table>
					</div>

				</el-main>
			</el-container>
		</el-container>
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
				value: 3.7,
				updateDialogFlag: false,
				updateFormData: {
					id: undefined,
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
					bookStatus: undefined,
					ISBNCode: undefined,
					raters:undefined,
				},
				imageUrl: undefined,
			};
		},
		created() {
			let id = this.$route.params.id;
			console.log("id", id)
			this.gatBookInfoById(id)
		},

		methods: {
			gatBookInfoById(id) {
				bookAxios.getBookInfoByIdAxios(id).then(resp => {
					console.log(resp)
					// this.state = 4
					if (resp.code === 20000) {
						// this.$message.success(resp.message)
						// this.formData.addreddRfid = resp.data.data
						this.updateFormData.id = resp.data.data.id
						this.updateFormData.addSortName = resp.data.data.data
						this.updateFormData.addBookSortId = resp.data.data.sortName + ""
						this.updateFormData.addBookAddress = resp.data.data.addressName + ""
						this.updateFormData.addreddRfid = resp.data.data.rfidId
						this.updateFormData.addSortName = resp.data.data.bookName
						this.updateFormData.updateBookAddress = resp.data.data.data
						this.updateFormData.publicationDate = resp.data.data.publicationDate
						this.updateFormData.author = resp.data.data.author
						this.updateFormData.press = resp.data.data.press
						this.updateFormData.bookCount = resp.data.data.bookCount
						this.imageUrl = resp.data.data.photoUrl
						this.updateFormData.bookStatus = resp.data.data.bookStatus
						this.updateFormData.ISBNCode  = resp.data.data.isbn
						// this.updateFormData.ISBNCode = '9787121302954'
						this.updateFormData.raters  = resp.data.data.raters
						this.updateDialogFlag = true;
					} else {
						this.$message.error(resp.message);
					}
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

	table th {
		text-align: right;
		width: 15%;
	}

	table td {
		text-align: left;
		width: 85%;
	}
</style>
