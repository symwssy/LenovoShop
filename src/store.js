import { createStore } from 'vuex';

export default createStore({
  state: {
    isLoggedIn: false,
    userId: null, // 初始化 userId
    username: null,
  },
  actions: {
    login({ commit }, status) {
      commit('setLoginState', true);
      localStorage.setItem('isLoggedIn', 'true');
    },
    logout({ commit }) {
      commit('setLoginState', false);
      localStorage.removeItem('isLoggedIn');
      // 这里可以添加发送登出请求到后端的代码
    },
    setUsername({ commit }, username) {
      commit('SET_USERNAME', username);
    }
  },
  mutations: {
    setLoginState(state, status) {
      state.isLoggedIn = status;
    },
    setUserId(state, userId) {
      state.userId = userId;
    },
    SET_USERNAME(state, username) {
      state.username = username;
    }
  },
  getters: {
    isLoggedIn: state => state.isLoggedIn,
    getUserId: state => state.userId
  }
});
