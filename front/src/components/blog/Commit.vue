<template>
    <div class="commit-wrap">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <h2>
                    <i class="el-icon-s-order"></i> 
                    评论列表   
                </h2>
            </div>
            <div v-for="m in commits" :key="m.id" class="text item">
                {{m.commit}}
                <span/>
            </div>
        </el-card>
    </div>
</template>

<script>
export default {
    name: "Commit",
    data(){
        return {
            commits:[],
        }
    },
    methods: {
        //请求结果进行赋值
        getCommit() {
            this.$axios.get("/getCommit" ).then((res) => {
                console.log(res);
                this.commits = res.data.data;
                
            });
        }
    },
    //调用 created方法
    created() {
        this.getCommit();
    }
};
</script>

<style>
.commit-wrap{
    margin-bottom:100px;
}
</style>