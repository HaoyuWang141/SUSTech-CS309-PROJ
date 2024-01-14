<template>
  <div class="chat-container">
    <div class="sidebar">
      <!-- 聊天室和用户列表 -->
      <h3>SUSTech Dormitory</h3>
      <span @click="selectChat(localStorage.getItem('team_id'), 'teamMessage')">抢宿舍小队</span>
      <h3>私聊</h3>
      <ul>
        <li v-for="user in users" :key="user" @click="selectChat(user.id, 'privateMessage')">
          {{ user.name }}
        </li>
      </ul>
    </div>
    <div class="chat-area">
      <div class="messages">
        <!-- 聊天消息 -->
        <div v-for="(message, index) in messages" :key="index"
             :class="{'mine': message.isMine, 'theirs': !message.isMine}">
          <span>[{{ message.time }}] {{ message.from.name }}</span>
          {{ message.text }}
        </div>
      </div>
      <div class="message-input">
        <input v-model="newMessage" type="text" placeholder="Type a message...">
        <button @click="sendMessage(newMessage)">Send</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {ref, onMounted, onUnmounted} from 'vue';
import {io, Socket} from 'socket.io-client';

interface User {
  name: string;
  id: string;
  messages: Message[];
}

interface Team {
  id: string;
  users: User[];
  messages: Message[];
}

interface Chat {
  id: string;
  type: string;
}

interface Message {
  text: string;
  isMine: boolean; // 是否是自己发送的消息
  from: User;
  time: string;
}

const socket = ref<Socket | null>(null);
const messages = ref<Message[]>([]);
const team = ref<Team>();
const users = ref<User[]>([]);
const currentChat = ref<Chat>({id: '', type: ''});
const newMessage = ref('');

onMounted(() => {
  socket.value = io('http://localhost:8083', {
    query: {
      studentId: localStorage.getItem('studentId'),
    }
  });

  socket.value.on('connect', () => {
    console.log('Connected to the server');
  });

  socket.value.on('history', (history: Message[]) => {
    messages.value = history;
  });

  socket.value.on('message', (message: Message) => {
    messages.value.push(message);
  });
});

function newPrivateChat(id: string) {
  // 新建一对一聊天的逻辑
}

function getUsers() {
  // 获取用户列表的逻辑
}

const sendMessage = (message: string) => {
  if (message.trim()) {
    socket.value?.emit(currentChat.value.type, {
      targetUserId: currentChat.value.id,
      teamId: currentChat.value.id,
      message: message
    });
    newMessage.value = ''; // 发送消息后清空输入框
  }
};

const selectChat = (id: string, type: string) => {
  currentChat.value = {id: id, type: type};
  if (type === 'teamMessage') {
    socket.value?.emit('teamHistory', {
      teamId: id,
    });
  } else {
    socket.value?.emit('privateHistory', {
      senderUserId: localStorage.getItem('studentId'),
      targetUserId: id,
    });
  }
};

onUnmounted(() => {
  socket.value?.disconnect();
});
</script>

<style scoped lang="less">
.chat-container {
  display: flex;
}

.sidebar {
  width: 200px;
  /* 其他样式 */
}

.chat-area {
  flex: 1;
  /* 其他样式 */
}

.messages {
  /* 消息区域样式 */
}

.messages .mine {
  text-align: right;
}

.messages .theirs {
  text-align: left;
}

.message-input {
  /* 消息输入框样式 */
}
</style>