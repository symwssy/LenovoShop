import { createStore } from 'vuex';

export default createStore({
  state: {
    isLoggedIn: localStorage.getItem('isLoggedIn') === 'true' || false,
    userId: localStorage.getItem('userId') || 0,
    username: localStorage.getItem('username') || null,
    token: localStorage.getItem('token') || null,
    email: localStorage.getItem('email') || null,
    balance: localStorage.getItem('balance') || null,
    userPic: localStorage.getItem('user_pic') || null,
    manager: localStorage.getItem('isAdmin') === 'true' || false, // 从 isAdmin 获取管理员状态
    phoneNumber: localStorage.getItem('phone_number') || null,
    createTime: localStorage.getItem('create_time') || null,
    updateTime: localStorage.getItem('update_time') || null,
  },

  actions: {
    login({ commit }, userData) {
      commit('setLoginState', true);
      commit('setToken', userData.token);
      commit('setUserId', userData.id);
      commit('setUsername', userData.username);
      commit('setEmail', userData.email);
      commit('setBalance', userData.balance);
      commit('setUserPic', userData.user_pic);
      commit('setManager', userData.manager); // 设置管理员状态
      commit('setPhoneNumber', userData.phone_number);
      commit('setCreateTime', userData.create_time);
      commit('setUpdateTime', userData.update_time);

      localStorage.setItem('isLoggedIn', 'true');
      localStorage.setItem('token', userData.token);
      localStorage.setItem('userId', userData.id);
      localStorage.setItem('username', userData.username);
      localStorage.setItem('email', userData.email);
      localStorage.setItem('balance', userData.balance);
      localStorage.setItem('user_pic', userData.user_pic);
      localStorage.setItem('isAdmin', userData.manager); // 存储管理员状态到 isAdmin
      localStorage.setItem('phone_number', userData.phone_number);
      localStorage.setItem('create_time', userData.create_time);
      localStorage.setItem('update_time', userData.update_time);
    },

    logout({ commit }) {
      commit('setLoginState', false);
      commit('setToken', null);
      commit('setUserId', 0);
      commit('setUsername', null);
      commit('setEmail', null);
      commit('setBalance', null);
      commit('setUserPic', null);
      commit('setManager', false); // 重置管理员状态
      commit('setPhoneNumber', null);
      commit('setCreateTime', null);
      commit('setUpdateTime', null);

      localStorage.removeItem('isLoggedIn');
      localStorage.removeItem('token');
      localStorage.removeItem('userId');
      localStorage.removeItem('username');
      localStorage.removeItem('email');
      localStorage.removeItem('balance');
      localStorage.removeItem('user_pic');
      localStorage.removeItem('isAdmin'); // 移除 isAdmin
      localStorage.removeItem('phone_number');
      localStorage.removeItem('create_time');
      localStorage.removeItem('update_time');
    }
  },

  mutations: {
    setLoginState(state, status) {
      state.isLoggedIn = status;
    },
    setToken(state, token) {
      state.token = token;
    },
    setUserId(state, userId) {
      state.userId = userId;
    },
    setUsername(state, username) {
      state.username = username;
    },
    setEmail(state, email) {
      state.email = email;
    },
    setBalance(state, balance) {
      state.balance = balance;
    },
    setUserPic(state, userPic) {
      state.userPic = userPic;
    },
    setManager(state, manager) {
      state.manager = manager;
    },
    setPhoneNumber(state, phoneNumber) {
      state.phoneNumber = phoneNumber;
    },
    setCreateTime(state, createTime) {
      state.createTime = createTime;
    },
    setUpdateTime(state, updateTime) {
      state.updateTime = updateTime;
    },
  },

  getters: {
    isLoggedIn: state => state.isLoggedIn,
    getUserId: state => state.userId,
    getUsername: state => state.username,
    getEmail: state => state.email,
    getBalance: state => state.balance,
    getUserPic: state => state.userPic,
    isManager: state => state.manager, // 管理员状态 getter
    getPhoneNumber: state => state.phoneNumber,
    getToken: state => state.token,
  }
});
