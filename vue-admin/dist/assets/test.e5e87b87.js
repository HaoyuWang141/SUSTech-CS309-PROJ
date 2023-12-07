import { k as service } from './index.a5cdc2ea.js';

// 用户列表
const getUsers = data => {
  return service({
    url: '/api/test/users',
    method: 'post',
    data,
  })
};

export { getUsers as g };
