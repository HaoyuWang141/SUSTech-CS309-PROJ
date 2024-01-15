<script setup lang="ts">
import {ref} from "vue";
import {useRouter} from "vue-router";
import {useAuthStore} from "@/stores/auth";
import {ElMessage} from "element-plus";

const username = ref<string>("");
const password = ref<string>("");

const router = useRouter();
const authStore = useAuthStore();

async function login() {
  try {
    await authStore.login(username.value, password.value);
    console.log("登陆成功: ", username.value, password.value);
    await router.push("/home");
  } catch (error: any) {
    ElMessage.error(error.message);
  }
}
</script>

<template>
  <div class="login-container">
    <form @submit.prevent="login">
      <h2>Login</h2>
      <input
          type="text"
          id="username"
          v-model="username"
          required
          placeholder="用户名"
      />
      <input
          type="password"
          id="password"
          v-model="password"
          required
          placeholder="密码"
      />
      <button type="submit">登录</button>
    </form>
  </div>
</template>

<style lang="less" scoped>
@mainColor: #072056; // 本页面主色调
@button-hover-color: #0056b3; // 按钮悬停颜色
@background-color: #0d1933; // 背景颜色

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
