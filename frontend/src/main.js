import Vue from 'vue'
import App from './app.vue'
import VueRouter from 'vue-router'
import VueResource from 'vue-resource'
import LeaderboardView from './components/leaderboard/view/leaderboard-view'
import LeaderboardCreate from './components/leaderboard/leaderboard-create'
import './components/_globals'

Vue.use(VueRouter);
Vue.use(VueResource);

Vue.config.productionTip = false;

const router = new VueRouter({
  routes: [{
    name: 'leaderboard-create',
    path: '/leaderboard',
    component: LeaderboardCreate
  }, {
    name: 'leaderboard',
    path: '/leaderboard/:id',
    component: LeaderboardView
  }, {
    path: '/',
    redirect: '/leaderboard'
  }],
  linkActiveClass: "active",
});

new Vue({
  router,
  render: createElement => createElement(App)
}).$mount('#app');
