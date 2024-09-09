import { createStore } from 'vuex';

export default createStore({
  state: {
    isLoggedIn: localStorage.getItem('isLoggedIn') === 'true' || false,
    userId: localStorage.getItem('userId') || null,
    username: localStorage.getItem('username') || null,
    token: localStorage.getItem('token') || null,
  },
  actions: {
    login({ commit }, { token, userId, username }) {
      commit('setLoginState', true);
      commit('setToken', token);
      commit('setUserId', userId);
      commit('setUsername', username);
      localStorage.setItem('isLoggedIn', 'true');
      localStorage.setItem('token', token);
      localStorage.setItem('userId', userId);
      localStorage.setItem('username', username);
    },
    logout({ commit }) {
      commit('setLoginState', false);
      commit('setToken', null);
      commit('setUserId', null);
      commit('setUsername', null);
      localStorage.removeItem('isLoggedIn');
      localStorage.removeItem('token');
      localStorage.removeItem('userId');
      localStorage.removeItem('username');
      // 这里可以添加发送登出请求到后端的代码
    },
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
  },
  getters: {
    isLoggedIn: state => state.isLoggedIn,
    getUserId: state => state.userId,
    getUsername: state => state.username,
    getToken: state => state.token,
  }
});
