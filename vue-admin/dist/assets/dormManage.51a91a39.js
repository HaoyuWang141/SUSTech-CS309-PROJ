import { g as getUsers } from './test.e5e87b87.js';
import { _ as _export_sfc } from './index.a5cdc2ea.js';
import { k as defineComponent, q as reactive, r as ref, C as toRefs, p as resolveComponent, l as openBlock, m as createBlock, n as withCtx, j as createVNode, R as createTextVNode, S as toDisplayString } from './element-plus.39264cdf.js';

const _sfc_main = defineComponent({
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
        console.log(state.selectedItems);
      },
      // 选择
      handleSelectionChange(arr) {
        state.selectedItems = arr;
      },
      // 请求函数
      async getList(params) {
        console.log(params);
        // params是从组件接收的，包含分页和搜索字段。
        const { data } = await getUsers(params);

        // 必须要返回一个对象，包含data数组和total总数
        return {
          data: data.list,
          total: +data.total,
        }
      },
    });
    const table = ref(null);
    const refresh = () => {
      table.value.refresh();
    };

    return { ...toRefs(state), refresh, table }
  },
});

function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  const _component_el_button = resolveComponent("el-button");
  const _component_el_tag = resolveComponent("el-tag");
  const _component_pro_table = resolveComponent("pro-table");

  return (openBlock(), createBlock(_component_pro_table, {
    ref: "table",
    title: _ctx.$t('test/list.title'),
    request: _ctx.getList,
    columns: _ctx.columns,
    search: _ctx.searchConfig,
    onSelectionChange: _ctx.handleSelectionChange
  }, {
    toolbar: withCtx(() => [
      createVNode(_component_el_button, {
        type: "danger",
        icon: "Delete",
        onClick: _ctx.batchDelete
      }, {
        default: withCtx(() => [
          createTextVNode(toDisplayString(_ctx.$t('test/list.batchDelete')), 1)
        ]),
        _: 1
      }, 8, ["onClick"]),
      createVNode(_component_el_button, {
        type: "primary",
        icon: "Plus",
        onClick: _cache[0] || (_cache[0] = $event => (_ctx.$router.push('/test/add')))
      }, {
        default: withCtx(() => [
          createTextVNode(toDisplayString(_ctx.$t('test/list.add')), 1)
        ]),
        _: 1
      }),
      createVNode(_component_el_button, {
        type: "primary",
        icon: "Plus",
        onClick: _cache[1] || (_cache[1] = $event => (_ctx.$router.push('/test/add')))
      }, {
        default: withCtx(() => [
          createTextVNode(toDisplayString(_ctx.$t('test/list.batch_Add')), 1)
        ]),
        _: 1
      }),
      createVNode(_component_el_button, {
        type: "success",
        icon: "",
        onClick: _cache[2] || (_cache[2] = $event => (_ctx.$router.push('/test/add')))
      }, {
        default: withCtx(() => [
          createTextVNode(toDisplayString(_ctx.$t('发布选宿舍时间')), 1)
        ]),
        _: 1
      }),
      createVNode(_component_el_button, {
        type: "primary",
        icon: "Refresh",
        onClick: _ctx.refresh
      }, {
        default: withCtx(() => [
          createTextVNode(toDisplayString(_ctx.$t('test/list.refresh')), 1)
        ]),
        _: 1
      }, 8, ["onClick"])
    ]),
    status: withCtx(({row}) => [
      createVNode(_component_el_tag, {
        type: row.status === 1 ? 'success' : 'error'
      }, {
        default: withCtx(() => [
          createTextVNode(toDisplayString(row.status === 1 ? _ctx.$t('public.enabled') : _ctx.$t('public.disabled')), 1)
        ]),
        _: 2
      }, 1032, ["type"])
    ]),
    operate: withCtx((scope) => [
      createVNode(_component_el_button, {
        size: "small",
        type: "primary",
        onClick: $event => (_ctx.$router.push(`/test/edit/${scope.row.id}`))
      }, {
        default: withCtx(() => [
          createTextVNode(toDisplayString(_ctx.$t('public.edit')), 1)
        ]),
        _: 2
      }, 1032, ["onClick"]),
      createVNode(_component_el_button, {
        size: "small",
        type: "danger"
      }, {
        default: withCtx(() => [
          createTextVNode(toDisplayString(_ctx.$t('public.delete')), 1)
        ]),
        _: 1
      })
    ]),
    _: 1
  }, 8, ["title", "request", "columns", "search", "onSelectionChange"]))
}
var dormManage = /*#__PURE__*/_export_sfc(_sfc_main, [['render',_sfc_render]]);

export { dormManage as default };
