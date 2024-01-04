
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
              v-for="sec of this.allSections"
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
<!--                   v-model:content="content"-->
<!--                   :options="data.editorOption"-->
<!--                   contentType="html"-->
<!--                   @update:content="setValue()"-->
<!--                   placeholder="内容"-->
<!--      />-->
<!--      &lt;!&ndash; 使用自定义图片上传 &ndash;&gt;-->
<!--      <input type="file" hidden accept=".jpg,.png" ref="fileBtn" @change="handleUpload"/>-->

    </div>
    <div class="ButtonContainer" style="padding-top: 20px; margin: 20px">
      <el-button type="primary" @click="postPost">
        发布
      </el-button>
    </div>
    <div class="ButtonContainer" style="padding-top: 20px; margin: 20px">
      <el-button type="info" @click="exitEdit">
        取消
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
      allSections: []
    }
  },
  created() {
    this.getAllSections()
    const router = useRouter()
    this.pid = parseInt(router.currentRoute.value.params['postId'])
    console.log("Editting post, pid", this.pid)
    this.getPostDetail(this.pid)
  },
  methods: {
    getAllSections() {
      axios.get('/api/sections').then(resp => {
        this.allSections = resp.data
      }).catch(e => {
        console.log(e)
      })
    },

    exitEdit() {
      this.$router.push('/postshow/' + this.pid)
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
        pid: this.pid,
        uid: localStorage.getItem('uid'),
        title: titleNew,
        content: postNew,
        sectionId: secNew
      }
      console.log(data)
      axios.post('/api/uploadPost',data, {
        params: {
          pid: this.pid
        },
        headers: {
          'Content-Type' : 'application/json'
        }
      }).then(resp=>{
        console.log(resp.data)
        if (resp.data) {
          ElMessageBox.alert('编辑成功', {
            confirmButtonText: 'OK',
            type: 'success'
          })
          this.$router.push('/userinfo/' + localStorage.getItem('uid'))
        }
      })
    },

    async getPostDetail(pid) {
      axios.get('/api/post', {
        params: {
          pid: pid
        }
      }).then(resp => {
        let resData = resp.data
        this.postResp = resData

        if (resData['uid'] !== parseInt(localStorage.getItem('uid'))) {
          console.log('??!? ', resData['uid'], parseInt(localStorage.getItem('uid')))
          ElMessageBox.alert('编辑需登录作者账号', '错误！', {
            confirmButtonText: 'OK',
            type: 'error'
          })
          this.$router.push('/login')
        }

        this.setPostContent(resData)

        this.$refs.myQuillEditor.setContents(this.postResp['content'])
        this.inputValue = this.postResp['title']

      }).catch(e => {
        console.log(e)
      })
      return ''
    },
    async setPostContent(res) {
      console.log(this.postResp, '???')

      this.$refs.myQuillEditor.setContents(this.postResp['content'])
      this.inputValue = this.postResp['title']
      // this.selectedValue = this.postResp['']
    }
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
const content = ref('')
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
    placeholder: '请输入内容...'
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
  toRaw(myQuillEditor.value).setHTML(props.value)
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


