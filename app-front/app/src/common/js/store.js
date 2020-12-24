import Vuex from 'vuex'

const store = new Vuex.Store({
  modules: {
  },
  state: {
    // 加载loding
    loading: {
      status: false,
      text: ''
    }
  },
  mutations: {
    // 修改loding状态
    updateLoadingStatus (state, status) {
      state.loading.status = status.status
      state.loading.text = status.text
    }
  },

  actions: {
    // 控制loding
    startLoding ({state, commit}, status) {
      commit('updateLoadingStatus', status)
    }
  }
})
export default store
