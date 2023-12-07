import { k as defineComponent, q as reactive, l as openBlock, J as createElementBlock, j as createVNode, n as withCtx, u as unref, F as Fragment, R as createTextVNode, p as resolveComponent } from './element-plus.39264cdf.js';

const _hoisted_1 = /* @__PURE__ */ createTextVNode(" \u4E2A\u4EBA\u4E3B\u9875 ");
const _hoisted_2 = /* @__PURE__ */ createTextVNode("\u786E\u8BA4\u66F4\u6539");
const _hoisted_3 = /* @__PURE__ */ createTextVNode("\u53D6\u6D88");
const _sfc_main = /* @__PURE__ */ defineComponent({
  setup(__props) {
    const form = reactive({
      name: "",
      region: "",
      date1: "",
      date2: "",
      delivery: false,
      type: [],
      resource: "",
      desc: ""
    });
    const onSubmit = () => {
      console.log("submit!");
    };
    return (_ctx, _cache) => {
      const _component_el_input = resolveComponent("el-input");
      const _component_el_form_item = resolveComponent("el-form-item");
      const _component_el_option = resolveComponent("el-option");
      const _component_el_select = resolveComponent("el-select");
      const _component_el_button = resolveComponent("el-button");
      const _component_el_form = resolveComponent("el-form");
      return openBlock(), createElementBlock(Fragment, null, [
        _hoisted_1,
        createVNode(_component_el_form, {
          model: unref(form),
          "label-width": "120px"
        }, {
          default: withCtx(() => [
            createVNode(_component_el_form_item, { label: "\u59D3\u540D" }, {
              default: withCtx(() => [
                createVNode(_component_el_input, {
                  modelValue: unref(form).name,
                  "onUpdate:modelValue": _cache[0] || (_cache[0] = ($event) => unref(form).name = $event)
                }, null, 8, ["modelValue"])
              ]),
              _: 1
            }),
            createVNode(_component_el_form_item, { label: "\u8EAB\u4EFD" }, {
              default: withCtx(() => [
                createVNode(_component_el_select, {
                  modelValue: unref(form).region,
                  "onUpdate:modelValue": _cache[1] || (_cache[1] = ($event) => unref(form).region = $event),
                  placeholder: "\u8BF7\u9009\u62E9"
                }, {
                  default: withCtx(() => [
                    createVNode(_component_el_option, {
                      label: "\u8F85\u5BFC\u5458",
                      value: "fdy"
                    }),
                    createVNode(_component_el_option, {
                      label: "\u5BBF\u7BA1",
                      value: "sg"
                    }),
                    createVNode(_component_el_option, {
                      label: "\u5176\u4ED6",
                      value: "others"
                    })
                  ]),
                  _: 1
                }, 8, ["modelValue"])
              ]),
              _: 1
            }),
            createVNode(_component_el_form_item, { label: "\u8054\u7CFB\u65B9\u5F0F" }, {
              default: withCtx(() => [
                createVNode(_component_el_input, {
                  modelValue: unref(form).desc,
                  "onUpdate:modelValue": _cache[2] || (_cache[2] = ($event) => unref(form).desc = $event),
                  type: "textarea"
                }, null, 8, ["modelValue"])
              ]),
              _: 1
            }),
            createVNode(_component_el_form_item, null, {
              default: withCtx(() => [
                createVNode(_component_el_button, {
                  type: "primary",
                  onClick: onSubmit
                }, {
                  default: withCtx(() => [
                    _hoisted_2
                  ]),
                  _: 1
                }),
                createVNode(_component_el_button, null, {
                  default: withCtx(() => [
                    _hoisted_3
                  ]),
                  _: 1
                })
              ]),
              _: 1
            })
          ]),
          _: 1
        }, 8, ["model"])
      ], 64);
    };
  }
});

export { _sfc_main as default };
