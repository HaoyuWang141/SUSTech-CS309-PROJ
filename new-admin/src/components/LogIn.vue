<template>
    <div class="login-wrap">
        <el-form class="login-container">
            <h1 class="title">用户登陆</h1>
            <el-form-item >
                <el-input type="text" v-model="username"  placeholder="用户账号" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item >
                <el-input type="password" v-model="password" placeholder="用户密码" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button class="login-button" type="primary" @click="doLogin" style="width: 100%;">登陆</el-button>
            </el-form-item>
<!--            <el-row style="text-align: center;margin-top: -10px;">-->
<!--                <el-link type="primary" @click="doRegister">注册账号</el-link>-->
<!--&lt;!&ndash;                <el-link type="primary" >忘记密码</el-link>&ndash;&gt;-->
<!--            </el-row>-->
        </el-form>
    </div>
</template>

<script>
import axios from 'axios'
import qs from 'qs';
import {ElMessageBox} from "element-plus";
export default{
    name:'LogIn',
    data:function(){
        return {
            username:'',
            password:''
        }
    },
    methods:{
        doLogin:function(){
            let username=this.username;
            let password=this.password;

            axios.get('api/admin/adminAccount/get').then(resp=>{
              for (let i = 0; i < resp.data.length; i++) {
                if (resp.data[i].account_name === username &&
                    resp.data[i].password === password) {
                  localStorage.setItem('act',username)
                  localStorage.setItem('pw',password)
                  this.$router.push('/student')
                }
              }

              // alert('密码错误')
            }).catch(err=>{
              console.log("出现错误"+err);
            })
        }
    }
}
</script>

<style>
.login-wrap {
    box-sizing: border-box;
    width: 100%;
    height: 100%;


    position: absolute;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;

    padding-top: 10%;
//padding-bottom: 10%;
    background-image: url("./bg.jpg");
    /* background-color: #112346; */
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;
}

.login-container {
    border-radius: 10px;
    margin: auto;
    width: 350px;
    padding: 30px 35px 15px 35px;
//background: #fff;
//border: 1px solid #eaeaea;
    text-align: left;
//box-shadow: 0 0 20px 2px rgba(0, 0, 0, 0.1);
    backdrop-filter: saturate(70%) blur(8px);
}
.login-button{
    background-color: aqua;
    opacity: 0.6;
    backdrop-filter: saturate(80%) blur(9px);
}
.title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
}
</style>