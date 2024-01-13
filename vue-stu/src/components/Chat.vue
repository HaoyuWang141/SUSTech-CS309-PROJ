<template>
  <div id="chat">
    <div id="messageArea">
      <div v-for="(message, index) in messages" :key="index">{{ message }}</div>
    </div>
    <input v-model="newMessage" type="text" placeholder="Type a message..." />
    <button @click="sendMessage">Send</button>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';

const messages = ref<string[]>([]);
const newMessage = ref('');

let stompClient: Stomp.Client | null = null;

onMounted(() => {
  connect();
});

function connect() {
  const socket = new SockJS('/chat');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, () => {
    stompClient!.subscribe('/topic/public', (message) => {
      messages.value.push(JSON.parse(message.body).content);
    });
  });
}

function sendMessage() {
  if (newMessage.value.trim()) {
    const chatMessage = { content: newMessage.value };
    stompClient!.send('/app/sendMessage', {}, JSON.stringify(chatMessage));
    newMessage.value = '';
  }
}
</script>

<style scoped lang="less">
#chat {
  // Define your chat styles here
  #messageArea {
    // Styles for the message display area
  }
  input, button {
    // Styles for the input field and button
  }
}
</style>
