
<template>
  <div>
    <head-menu class="header"></head-menu>
  </div>
  <div class="GlobalContainer">
    <div class="TitleContainer">
      <el-form-item label="标题">
        <el-input v-model="inputValue" class="TitleInput"></el-input>
      </el-form-item>
    </div>
    <div>
      <el-form-item label="分区">
        <el-select v-model="selectedValue" placeholder="请选择分区" class="SectionSelect">
          <el-option
              v-for="sec of allSections"
              :label="sec.name"
              :key="sec.sectionId"
              :value="sec.sectionId"
          ></el-option>
          <!--          <el-option label=sec.name value="1"></el-option>-->
        </el-select>
      </el-form-item>
    </div>
    <div>
<!--      <QuillEditor ref="myQuillEditor"-->
<!--                   theme="snow"-->
<!--                   v-model="content"-->
<!--                   :options="data.editorOption"-->
<!--                   contentType="html"-->
<!--                   placeholder="内容"-->
<!--      />-->
<!--      &lt;!&ndash; 使用自定义图片上传 &ndash;&gt;-->
<!--      <input type="file" hidden accept=".jpg,.png" ref="fileBtn" @change="handleUpload"/>-->

    </div>
    <div class="ButtonContainer" style="padding-top: 20px">
      <el-button type="primary" @click="postPost">
        发布
      </el-button>
    </div>
  </div>
</template>

<script>
import { useRouter } from 'vue-router'
import axios from "axios";
import {toRaw} from "vue";
import {ElMessageBox} from "element-plus";

export default {
  data() {
    return {
      inputValue: '', // 输入框的值
      selectedValue: '', // 选择器的值
      pid: -1,
      postResp: '',
      allSections: [{sectionId: 0, name: ''}]
    }
  },
  created() {
      if (localStorage.getItem('uid') == null) {
      ElMessageBox.alert('发帖请先登录', '错误！', {
        confirmButtonText: 'OK',
        type: 'error'
      })
      this.$router.push('/mainpage')
    }
    // const router = useRouter()
    // this.pid = parseInt(router.currentRoute.value.params['postId'])
    // console.log("Editting post, pid", this.pid)
    // this.getPostDetail(this.pid)
  },
    mounted() {
        // this.allSections = [{sectionId: 0, name: ''}]
        console.log('qwertyui')
        this.getAllSections();
    },
  methods: {
    getAllSections() {
      axios.get('/api/sections').then(resp => {
        this.allSections = resp.data
      }).catch(e => {
        console.log(e)
      })
    },

    postPost() {
      const postNew = this.$refs.myQuillEditor.getContents()
      const titleNew = this.inputValue
      const secNew = this.selectedValue
      console.log(secNew)
      if (postNew === '' || titleNew === '' || secNew === '') {
        ElMessageBox.alert('请您检查内容完整性', {
          confirmButtonText: 'OK',
          type: 'info'
        })
        return
      }

      let data = {
        uid: localStorage.getItem('uid'),
        title: titleNew,
        content: postNew,
        sectionId: secNew
      }
      console.log(data)
      axios.post('/api/uploadPost',data, {
        headers: {
          'Content-Type' : 'application/json'
        }
      }).then(resp=>{
        console.log(resp.data)
        if (resp.data) {
          ElMessageBox.alert('发布成功', {
            confirmButtonText: 'OK',
            type: 'success'
          })
          this.$router.push('/userinfo/'  + localStorage.getItem('uid'))
        }
      })
    },
  },
}
</script>

<style>
.GlobalContainer {
  border-radius: 10px;
  margin: auto;
  padding: 30px 35px 15px 35px;
}

.TitleInput {
  width: 30%
}

.SectionSelect {
  width: 30%
}

.ButtonContainer {
  float: right
}
</style>

<script setup>
import {QuillEditor} from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import {reactive, onMounted, ref, watch} from 'vue'
import HeadMenu from "@/components/util/HeadMenu.vue";
// import { backsite } from '@/api'
// eslint-disable-next-line no-undef
const props = defineProps(['value'])
// eslint-disable-next-line no-undef
const emit = defineEmits(['updateValue'])
// const content = ref('')
const myQuillEditor = ref()
// watch(() => props.value, (val) => {
//   console.log(toRaw(myQuillEditor.value))
//   toRaw(myQuillEditor.value).setHTML(val)
// }, { deep: true })
const fileBtn = ref()
const data = reactive({
  content: '',
  editorOption: {
    modules: {
      toolbar: [
        ['bold', 'italic', 'underline', 'strike'],
        [{'size': ['small', false, 'large', 'huge']}],
        [{'font': []}],
        [{'align': []}],
        [{'list': 'ordered'}, {'list': 'bullet'}],
        [{'indent': '-1'}, {'indent': '+1'}],
        [{'header': 1}, {'header': 2}],
        // ['image'],
        [{'direction': 'rtl'}],
        [{'color': []}, {'background': []}]
      ]
    },
    placeholder: ''
  }
})
const imgHandler = (state) => {
  if (state) {
    fileBtn.value.click()
  }
}
const setValue = () => {
  const text = toRaw(myQuillEditor.value).getHTML()
  console.log('?: ', text)
}

const handleUpload = (e) => {
  const files = Array.prototype.slice.call(e.target.files)
  console.log(files, "files")
  if (!files) {
    console.log('no file')
    return
  }
  const formdata = new FormData()
  formdata.append('file', files[0])
  // backsite.uploadFile(formdata)
  //   ！！.then(res => {
  //     if (res.data.url) {
  //       const quill = toRaw(myQuillEditor.value).getQuill()
  //       const length = quill.getSelection().index
  //       // 插入图片，res为服务器返回的图片链接地址
  //       quill.insertEmbed(length, 'image', res.data.url)
  //       // 调整光标到最后
  //       quill.setSelection(length + 1)
  //     }
  //   })
}
onMounted(() => {
  const quill = toRaw(myQuillEditor.value).getQuill()
  if (myQuillEditor.value) {
    quill.getModule('toolbar').addHandler('image', imgHandler)
  }
  // toRaw(myQuillEditor.value).setHTML(props.value)
})

</script>

<style scoped lang="scss">
:deep(.ql-editor) {
  min-height: 180px;
}

:deep(.ql-formats) {
  height: 21px;
  line-height: 21px;
}
</style>


