<template>
    <div class="register-wrap">
        <el-form class="register-container">
            <h1 class="title">账户注册</h1>
            <el-form-item>
                <el-input type="text" v-model="username" placeholder="用户名" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input type="text" v-model="mail" placeholder="邮箱"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input class="pwbar" type="password" v-model="password" placeholder="密码"
                          autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button class="login-button" type="primary" @click="doRegister" style="width: 100%;">注册</el-button>
            </el-form-item>
            <el-row style="text-align: center;margin-top: -10px;">
                <el-link type="primary" @click="goLogin">前往登陆</el-link>
            </el-row>
        </el-form>
    </div>
</template>

<script>
import axios from 'axios'
// import { MessageBox } from 'element-ui';
import {ElMessageBox} from 'element-plus';

export default {
    name: 'RegisterFrom',
    data: function () {
        return {
            username: '',
            mail: '',
            password: ''
        }
    },
    methods: {
        doRegister: function () {
            axios.post('/api/register', {username: this.username, mail: this.mail, password: this.password})
                .then(
                    resp => {
                        if (resp.data === -1) {
                            ElMessageBox.alert('注册失败', 'Title', {
                                confirmButtonText: 'OK',
                                type: 'error'
                            })
                        } else {
                            ElMessageBox.alert('注册成功，你的uid是' + resp.data, 'Title', {
                                confirmButtonText: 'OK',
                                type: 'success'
                            }).then(() => {
                                    navigator.clipboard.writeText(resp.data)
                                        .then(() => {
                                            console.log('UID已成功复制到剪贴板');
                                        })
                                        .catch(err => {
                                            console.error('UID无法复制到剪贴板', err);
                                        });
                                    this.$router.push('/login');
                                }
                            )
                        }
                    }
                )
                .catch();
        },
        goLogin: function () {
            this.$router.push('/login')
        },

    }
}
</script>

<style>
.register-wrap {
    box-sizing: border-box;
    width: 100%;
    height: 100%;


    position: absolute;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;

    padding-top: 10%;
//padding-bottom: 10%; background-image: url("./bg.jpg");
    /* background-color: #112346; */
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;
}

.register-container {
    border-radius: 10px;
    margin: auto;
    width: 350px;
    padding: 30px 35px 15px 35px;
//background: #fff; //border: 1px solid #eaeaea; text-align: left;
//box-shadow: 0 0 20px 2px rgba(0, 0, 0, 0.1); backdrop-filter: saturate(70%) blur(8px);
}

.login-button {
    background-color: aqua;
    opacity: 0.6;
    backdrop-filter: saturate(80%) blur(9px);
}

.title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
}

.pwbar {

}
</style>