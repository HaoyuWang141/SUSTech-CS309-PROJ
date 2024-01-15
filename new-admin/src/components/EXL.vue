<template>
  <div>
    <input type="file" @change="handleFileChange">
    <el-button type="primary" @click="parseAndDisplay">解析并显示数据</el-button>
    <el-button @click="exportToExcel">caonima</el-button>
    <div v-if="parsedData.length">
      <h2>解析后的数据</h2>
      <pre>{{ parsedData }}</pre>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import * as XLSX from 'xlsx';

const parsedData = ref([]);

const handleFileChange = (event) => {
  const file = event.target.files[0];
  readFileContent(file);
};

const readFileContent = (file) => {
  const reader = new FileReader();
  reader.onload = (e) => {
    const fileContent = e.target.result;
    // 使用 xlsx 库解析文件内容
    const workbook = XLSX.read(fileContent, { type: 'binary' });
    const sheetName = workbook.SheetNames[0];
    const sheet = workbook.Sheets[sheetName];
    // 将解析的数据存储在 Vue 组件中
    parsedData.value = XLSX.utils.sheet_to_json(sheet, { header: 1 });
  };
  reader.readAsBinaryString(file);
};

const parseAndDisplay = () => {
  // 在控制台上打印解析后的数据
  console.log(parsedData.value);
};


const jsonData = ref([
  { Name: 'John', Age: 28, City: 'New York' },
  { Name: 'Alice', Age: 22, City: '' },
  { Name: 'Bob', Age: 35, City: null },
]);

const exportToExcel = () => {
  const ws = XLSX.utils.json_to_sheet(jsonData.value);
  const wb = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

  // 导出 Excel 文件
  XLSX.writeFile(wb, 'exported_data.xlsx');
};
</script>