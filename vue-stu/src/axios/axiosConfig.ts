import axios from "axios";

// 创建 axios 实例
const axiosInstance = axios.create({
    baseURL: "http://114.132.51.227:8082", // API 的 base URL
    // baseURL: "http://localhost:8082", // API 的 base U /RL
    timeout: 1000, // 请求超时时间
    headers: { "X-Custom-Header": "foobar" }, // 可以添加自定义头
});

export default axiosInstance;
