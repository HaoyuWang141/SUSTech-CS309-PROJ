import { u as useRouter, b as useRoute } from './index.a5cdc2ea.js';
import { h } from './element-plus.39264cdf.js';

const _sfc_main = {
  setup() {
    const router = useRouter();
    const route = useRoute();
    router.replace(route.fullPath.replace(/^\/redirect/, ''));
  },
  render() {
    return h('div')
  },
};

export { _sfc_main as default };
