<template>
  <div class="chat-container">
    <div class="sidebar">
      <!-- 聊天室和用户列表 -->

      <el-menu
          active-text-color="#ffd04b"
          background-color="#000"
          text-color="#fff">
        <el-menu-item>
          <h2>队伍聊天</h2>
        </el-menu-item>
        <el-menu-item @click="selectChat(teamId, 'teamMessage')">
          <el-icon>
            <ChatLineRound/>
          </el-icon>
          抢宿舍小队
        </el-menu-item>
        <el-menu-item>
          <h2>个人聊天</h2>
        </el-menu-item>
        <el-menu-item v-for="user in users" :key="user.id" @click="selectChat(user.id, 'privateMessage')">
          <el-icon>
            <ChatLineRound/>
          </el-icon>
          {{ user.name }}
        </el-menu-item>
      </el-menu>
      <!--      <ul>-->
      <!--        <li v-for="user in users" :key="user" @click="selectChat(user.id, 'privateMessage')">-->
      <!--          {{ user.name }}-->
      <!--        </li>-->
      <!--      </ul>-->
    </div>
    <div class="chat-area">
      <div class="messages">
        <el-scrollbar height="300px">
          <!-- 聊天消息 -->
          <div style="margin-bottom: 10px;" v-for="(message, index) in messages" :key="index"
               :class="message.senderId === studentId?'mine':'theirs'">
            <span>[{{ new Date(message.timestamp).toLocaleString() }}] {{ message.senderId }}</span>
            <p>{{ message.content }}</p>
          </div>
        </el-scrollbar>
      </div>
      <div style="height: 3px; background-color:white;"/>
      <div class="message-input">
        <textarea rows="3" v-model="newMessage" type="text" placeholder="Type a message..."/>
        <el-button @click="sendMessage(newMessage)">Send</el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {ref, onMounted, onUnmounted} from 'vue';
import {io, Socket} from 'socket.io-client';
import {ElMessage} from "element-plus";

interface User {
  name: string;
  id: string;
}

interface Team {
  id: string;
  users: User[];
}

interface Chat {
  id: string;
  type: string;
}

interface Message {
  content: string;
  senderId: string;
  receiverId: string;
  teamId: string;
  timestamp: string;
}

const socket = ref<Socket | null>(null);
const messages = ref<Message[]>([]);
const team = ref<Team>();
const users = ref<User[]>([]);
const currentChat = ref<Chat>({id: '', type: ''});
const newMessage = ref('');
const studentId = localStorage.getItem('studentId');
const teamId = localStorage.getItem('teamId');

onMounted(() => {
  socket.value = io('http://114.132.51.227:8083', {
    query: {
      studentId: studentId,
    }
  });

  socket.value.on('connect', () => {
    console.log('Connected to the server');
  });

  socket.value.on('userList', (userNames: string[]) => {
    for (var name of userNames) {
      users.value.push({name: name, id: name});
    }
  });

  socket.value.on('history', (history) => {
    console.log("get history ->");
    console.log(history);
    messages.value = history;
  });

  socket.value.on('message', (message: Message) => {
    console.log("get message ->");
    messages.value.push(message);
  });

  getUsers();
});

function newPrivateChat(id: string) {
  // 新建一对一聊天的逻辑
}

function getUsers() {
  socket.value?.emit('userList', studentId);
}

const sendMessage = (message: string) => {
  if (message.trim() === '') {
    ElMessage.warning('消息不能为空');
    return;
  }
  if (currentChat.value.id === '') {
    ElMessage.warning('请先选择一个聊天对象');
    return;
  }
  if (message.trim()) {
    console.log(studentId, currentChat.value.id, message);
    socket.value?.emit(currentChat.value.type, {
      senderUserId: studentId,
      targetId: currentChat.value.id,
      message: message
    });
    newMessage.value = ''; // 发送消息后清空输入框
  }
};

const selectChat = (id: string, type: string) => {
  currentChat.value = {id: id, type: type};
  let historyType;
  if (type === 'teamMessage') {
    if (teamId === null) {
      ElMessage.warning('请先加入一个小队');
      return;
    }
    historyType = 'teamHistory';
    socket.value?.emit('joinTeam', id);
  } else {
    historyType = 'privateHistory';
  }
  socket.value?.emit(historyType, {
    myId: localStorage.getItem('studentId'),
    queryId: id
  });
};

onUnmounted(() => {
  socket.value?.disconnect();
});
</script>

<style scoped lang="less">
@import "@/assets/colors.less";

.chat-container {
  display: flex;
  margin: 10px 10%;
  background-color: rgba(0, 0, 0, 0.7);
  padding: 50px;
  border-radius: 22px;
  overflow: hidden; /* 防止子元素溢出 */

  .sidebar {
    margin-left: 20px;
    width: 15%;
    margin-right: 40px;

    .el-menu {
      border: None;
      padding-top: 20px;
    }
  }

  .chat-area {
    color: whitesmoke;
    background-color: #000;
    padding: 10px 80px;
    flex: 1;
    display: flex;
    flex-direction: column; /* 使消息和输入区域垂直排列 */
  }

  .messages {
    flex: 1;
    overflow-y: auto; /* 消息过多时可滚动 */
    margin-bottom: 20px; /* 与输入区域间距 */

    .mine {
      text-align: right;
    }

    .theirs {
      text-align: left;
    }
  }

  .message-input {
    margin-top: 20px;

    textarea {
      min-height: 60px; /* 大约三行文本的高度 */
      width: 60%; /* 宽度调整为填满容器 */
      padding: 20px; /* 可选，为了更好的视觉效果和易用性 */
      background-color: rgba(0, 0, 0, 90%);
      border-radius: 20px;
      border-color: white;
      border-width: 3px;
      color: white;
    }

    .el-button {
      background-color: black; /* 设置按钮的背景色 */
      color: white; /* 设置按钮的文字颜色 */
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* 添加阴影效果 */
      padding: 20px 20px; /* 内边距 */
      font-size: 16px; /* 字体大小 */
      cursor: pointer; /* 鼠标悬停时显示手型指针 */
      margin-left: 50px;
      border-color: white;
      border-width: 3px;
      border-radius: 4px; /* 圆角边框 */
    }

  }
}
</style>
