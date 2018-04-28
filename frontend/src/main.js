import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import VueResource from 'vue-resource'
import Leaderboard from './components/Leaderboard'

Vue.use(VueRouter);
Vue.use(VueResource);

Vue.url.options.root = 'http://' + window.location.hostname + ':8888/api/';
Vue.config.productionTip = false;

const router = new VueRouter({
    routes: [{
        name: 'leaderboard',
        path: '/leaderboard/:id',
        component: Leaderboard
    }]
});

new Vue({
    router,
    render: createElement => createElement(App)
}).$mount('#app');
