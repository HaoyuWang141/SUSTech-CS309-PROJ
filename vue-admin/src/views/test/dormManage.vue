<!--<template>-->
<!--  <dl>-->
<!--    &lt;!&ndash; <dt>页面缓存必须满足以下条件：</dt>-->
<!--    <dd>1. 路由配置name属性</dd>-->
<!--    <dd>2. 当前页面设置name属性，并且跟路由配置的name属性一致，否则无法缓存</dd> &ndash;&gt;-->
<!--    -->
<!--  </dl>-->
<!--</template>-->
<!--<script>-->
<!--import { defineComponent, onActivated } from 'vue'-->

<!--export default defineComponent({-->
<!--  name: 'test-cache', // 该name必须跟路由配置的name一致-->
<!--  setup() {-->
<!--    console.log('cache')-->
<!--    onActivated(() => {-->
<!--      console.log('onActivated')-->
<!--    })-->
<!--  },-->
<!--})-->
<!--</script>-->


<template>
  <pro-table
    ref="table"
    :title="$t('test/list.title')"
    :request="getList"
    :columns="columns"
    :search="searchConfig"
    @selectionChange="handleSelectionChange"
  >

    <!-- 工具栏 -->
    <template #toolbar>
      <el-button type="danger" icon="Delete" @click="batchDelete">
        {{ $t('test/list.batchDelete') }}
      </el-button>
      <el-button type="primary" icon="Plus" @click="$router.push('/test/add')">
        {{ $t('test/list.add') }}
      </el-button>

      <el-button type="primary" icon="Plus" @click="$router.push('/test/add')">
        {{ $t('test/list.batch_Add') }}
      </el-button>

      <el-button type="success" icon="" @click="$router.push('/test/add')">
        {{ $t('发布选宿舍时间') }}
      </el-button>

      <el-button type="primary" icon="Refresh" @click="refresh">
        {{ $t('test/list.refresh') }}
      </el-button>
    </template>
    <template #status="{row}">
      <el-tag :type="row.status === 1 ? 'success' : 'error'">
        {{ row.status === 1 ? $t('public.enabled') : $t('public.disabled') }}
      </el-tag>
    </template>
    <template #operate="scope">
      <el-button
        size="small"
        type="primary"
        @click="$router.push(`/test/edit/${scope.row.id}`)"
      >
        {{ $t('public.edit') }}
      </el-button>
      <el-button size="small" type="danger">
        {{ $t('public.delete') }}
      </el-button>
    </template>
  </pro-table>
</template>

<script>
import { defineComponent, reactive, ref, toRefs } from 'vue'
import { getUsers } from '@/api/test'
export default defineComponent({
  name: 'test-cache',
  setup() {
    // const { proxy } = getCurrentInstance()

    const state = reactive({
      // 表格列配置，大部分属性跟el-table-column配置一样
      columns: [
        { type: 'selection', width: 56 },
        { label: 'id', type: 'index', width: 80},
        {
          label: '宿舍区',
          prop: '宿舍区',
          // sortable: true,
          width: 100,
        },
        {
          label: '楼栋',
          prop: '楼栋',
          sortable: true,
          width: 70,
        },
        {
          label: '楼层',
          prop: '楼层',
          sortable: true,
          width: 70,
        },
        {
          label: '房间号',
          prop: '房间号',
          sortable: true,
          width: 70,
        },
        // {
        //   label: 'public.status',
        //   tdSlot: 'status',
        //   sortable: true,
        //   width: 180,
        // },
        {
          label: 'public.operate',
          width: 180,
          align: 'center',
          tdSlot: 'operate', // 自定义单元格内容的插槽名称
        },
      ],
      // 搜索配置
      searchConfig: {
        labelWidth: '90px', // 必须带上单位
        inputWidth: '400px', // 必须带上单位
        fields: [
          {
            type: 'text',
            label: '宿舍区',
            name: '宿舍区',
            defaultValue: '',
          },
          {
            label: 'public.status',
            name: 'status',
            type: 'select',
            defaultValue: 1,
            options: [
              {
                // name: 'test/list.publish',
                name: '开放选择',
                value: 1,
              },
              {
                // name: 'test/list.nopublish',
                name: '禁用',
                value: 0,
              },
            ],
          },
          {
            label: '性别限定',
            name: 'sex',
            type: 'radio',
            options: [
              {
                name: 'public.male',
                value: 1,
              },
              {
                name: 'public.female',
                value: 0,
              },
              {
                name: '未知',
                value: 2,
              },
            ],
          },
          {
            // label: 'test/list.city',
            label: '年级限定',
            name: 'year',
            type: 'radio-button',
            options: [
              // {
              //   name: 'test/list.bj',
              //   value: 'bj',
              // },
              {
                name: '19级',
                value: '19',
              },{
                name: '20级',
                value: '20',
              },{
                name: '21级',
                value: '21',
              },{
                name: '22级',
                value: '22',
              },{
                name: '23级',
                value: '23',
              },
            ],
          },
          // {
          //   label: 'test/list.hobby',
          //   name: 'hobby',
          //   type: 'checkbox',
          //   defaultValue: ['eat'],
          //   options: [
          //     {
          //       name: 'test/list.eat',
          //       value: 'eat',
          //     },
          //     {
          //       name: 'test/list.sleep',
          //       value: 'sleep',
          //     },
          //     {
          //       name: 'test/list.bit',
          //       value: 'bit',
          //     },
          //   ],
          //   // transform: (val) => val.join(","),
          // },
          // {
          //   label: 'test/list.fruit',
          //   name: 'fruit',
          //   type: 'checkbox-button',
          //   options: [
          //     {
          //       name: 'test/list.apple',
          //       value: 'apple',
          //     },
          //     {
          //       name: 'test/list.banana',
          //       value: 'banana',
          //     },
          //     {
          //       name: 'test/list.orange',
          //       value: 'orange',
          //     },
          //     {
          //       name: 'test/list.grape',
          //       value: 'grape',
          //     },
          //   ],
          //   transform: val => val.join(','),
          // },
          // {
          //   label: 'test/list.date',
          //   name: 'date',
          //   type: 'date',
          // },
          // {
          //   label: 'test/list.time',
          //   name: 'datetime',
          //   type: 'datetime',
          //   defaultValue: '2020-10-10 8:00:00',
          // },
          // {
          //   label: 'test/list.daterange',
          //   name: 'daterange',
          //   type: 'daterange',
          //   trueNames: ['startDate', 'endDate'],
          //   style: { width: '400px' },
          // },
          // {
          //   label: 'test/list.timerange',
          //   name: 'datetimerange',
          //   type: 'datetimerange',
          //   trueNames: ['startTime', 'endTime'],
          //   style: { width: '400px' },
          //   defaultValue: ['2020-10-10 9:00:00', '2020-10-11 18:30:00'],
          // },
          // {
          //   label: 'test/list.num',
          //   name: 'num',
          //   type: 'number',
          //   min: 0,
          //   max: 10,
          // },
        ],
      },
      // 分页配置
      // paginationConfig: {
      //   layout: 'total, prev, pager, next, sizes', // 分页组件显示哪些功能
      //   pageSize: 10, // 每页条数
      //   pageSizes: [5, 10, 20, 50],
      //   style: { 'justify-content': 'flex-end' },
      // },
      selectedItems: [],
      batchDelete() {
        console.log(state.selectedItems)
      },
      // 选择
      handleSelectionChange(arr) {
        state.selectedItems = arr
      },
      // 请求函数
      async getList(params) {
        console.log(params)
        // params是从组件接收的，包含分页和搜索字段。
        const { data } = await getUsers(params)

        // 必须要返回一个对象，包含data数组和total总数
        return {
          data: data.list,
          total: +data.total,
        }
      },
    })
    const table = ref(null)
    const refresh = () => {
      table.value.refresh()
    }

    return { ...toRefs(state), refresh, table }
  },
})
</script>
