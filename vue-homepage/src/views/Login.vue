<script setup lang="ts">
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const credentials = ref({
    username: '',
    password: ''
});

const router = useRouter();
const login = () => {
    axios.post('/api/login', credentials.value)
        .then((res: { data: { code: number; }; }) => {
            console.log(res);
            if (res.data.code === 0) {
                router.push('/homepage');
            } else {
                alert('用户名或密码错误');
            }
        })
        .catch((err: any) => {
            console.log(err);
        });
};
</script>

<template>
    <div class="bg-picture" />
    <div class="content">
        <div class="login-container">
            <form @submit.prevent="login">
                <h2>Login</h2>
                <input type="text" id="username" v-model="credentials.username" required placeholder="用户名">
                <input type="password" id="password" v-model="credentials.password" required placeholder="密码">
                <button type="submit">登录</button>
            </form>
        </div>
    </div>
</template>

<style lang="less" scoped>
@mainColor: #072056; // 本页面主色调
@button-hover-color: #0056b3; // 按钮悬停颜色

.bg-picture {
    width: 100%;
    height: 100vh;
    background: url('../assets/bg.png') no-repeat center center fixed;
    background-size: cover;
    position: absolute;
    top: 0;
    left: 0;
    z-index: 1;
}

.content {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    z-index: 2;
}

.login-container {
    max-width: 500px;
    margin: auto;
    margin-top: 100px;
    padding: 50px 80px;
    border: 0;
    border-radius: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    background-color: rgba(194, 206, 219, 0.8);
    box-shadow: 8px 8px 20px rgba(34, 204, 228, 0.8);

    form {
        display: flex;
        flex-direction: column;
        align-items: center;

        h2 {
            margin-bottom: 30px;
            color: @mainColor;
            font-size: 50px;
            font-weight: bold;
        }

        margin-bottom: 15px;

        input[type="text"],
        input[type="password"] {
            width: 80%;
            padding: 15px;
            margin-bottom: 10px;
            border: 0;
            border-bottom: 1px solid @mainColor;
            border-radius: 4px;
            background-color: transparent;

            &:focus {
                outline: none;
                border-bottom: 1px solid darken(@mainColor, 10%);
            }

            &::placeholder {
                color: @mainColor;
            }

            &::content {
                color: @mainColor;
            }
        }

        button {
            margin: auto;
            margin-top: 20px;
            width: 50%;
            padding: 10px;
            border: none;
            border-radius: 10px;
            background-color: @mainColor;
            color: white;
            font-size: 16px;
            cursor: pointer;

            &:hover {
                background-color: darken(@mainColor, 5%);
            }
        }
    }
}

@border-color: #ddd;
</style>
