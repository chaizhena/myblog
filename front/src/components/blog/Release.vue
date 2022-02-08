<template>
    <div>
        <el-card class="box-card">
            <div slot="header" :class="classHeader" class="clearfix">
                <h2>
                    <i class="el-icon-s-promotion"></i>
                    发表评论
                </h2>

                <el-form ref="releaseForm" status-icon :model="releaseForm" :rules="rules" label-width="80px">

                    <el-form-item label="内容"  prop="commit">
                        <el-input type="textarea"
                        :rows="2"
                        placeholder="请留下精彩的评论！"
                         v-model="releaseForm.commit"></el-input>
                    </el-form-item>

                    <el-button
                        :class="classBtn"
                        style="float: right; padding: 3px 0"
                        type="submit"
                        @click="submitForm()"
                        >发布评论</el-button>

                    
                </el-form>
                        
            </div>
            <div >
                <Commit/>
            </div>
        </el-card>
    </div>
</template>

<script>
import Commit from "./Commit"
export default {
    components: {Commit},
    name: "Release",
    data() {
        return {
            classBtn: {
                btn: true,
            },
            classHeader: {
                header: true,
            },
            releaseForm: {
                id: null,
                commit: ''
            },
            rules: {
                commit: [
                {required: true, message: '请输入内容', trigger: 'blur'},
                {min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur'}
                ]                
            }
        };
    },
    methods: {
    
        submitForm() {
            const _this = this
            this.$refs.releaseForm.validate((valid) => {
                if (valid) {
                this.$axios.post('/commit', this.releaseForm).then((res) => {
                    _this.$alert('发表成功', '提示', {
                    confirmButtonText: '确定',
                    callback: action => {
                        _this.$router.push("Commit")
                    }
                    });
  
                });
                } else {
                console.log('error submit!!');
                return false;
                }
            })
            }
    },
};
</script>

<style scoped>
.textarea {
    margin-bottom: 10px;
}
.btn {
    width: 100px;
    height: 30px;
    background-color: rgb(247, 240, 240);
    margin: 10px;
}
.header {
    padding-bottom: 20px;
}
</style>