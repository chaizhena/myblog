<!--（博客列表）-->
<template>
  <div class="mcontaner">
    <div class="down">
       <!-- v-show="isShow" -->
      <div class="left">  
        <slideBar/>
      </div>
      <div class="center">
        <mainBlogs/>
      </div>
      <div class="right">
        <calendor/>
      </div>
    </div>
  </div>
</template>


<script>
import mainBlogs from "./mainBlog"
import BlogDetail from "./BlogDetail"
import slideBar from "@/components/blog/slideBar"
import calendor from "@/components/blog/calendor"
export default {
  name: "Blogs.vue",
  components: {slideBar,calendor,mainBlogs,BlogDetail},
  computed:{
    isShow(){
      return this.$store.getters.getIsShow
    }
  },
  methods: {
    hide(){
      this.$store.commit('changeIsShow',this.isShow)
    },
  },
  // created(){
  //   console.log(this.isShow,"created ----blogs")
  // }
};
</script>

<style scoped>
.mcontaner {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}
/* .front{
  width: 100%;
  height:50px;
  line-height:45px;
  background-color:#c6c3c3;
  z-index: 100;
  position: fixed;
}
.front .menu{
  width:200px;
}
.front .menu i{
  width:20px;
  height:20px;
  margin-left:20px;
  color:#fcf2f2;
} 
.front .menu span{
  font-size: 20px;
  font-weight: 600;
  color:#fcf2f2;
  margin-left:8px;
  margin-top: 10px;
} */
.down{
  margin-top:0px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: 100%;
  text-align:center;
}
.left {
  /* top:0px;
  left:0;
  position: fixed; 
  height:150%;
  width:10%;
  flex-grow:1;
  background:#fdf6ec;
  z-index:10; */

  top: 50px;
  width:16%;
  background: #fdf6ec;
}
.center {
  flex-grow:2;
  background-color: #fcf2f2; 
  width:60%;
}
.right{
  top: 50px;
  width:20%;
  background: #fdf6ec;
}

</style>