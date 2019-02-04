import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/index'

import VModal from 'vue-js-modal'
import Notifications from 'vue-notification'
import VTooltip from 'v-tooltip'

import { connect } from "./util/ws";

import VueSimpleContextMenu from 'vue-simple-context-menu'
Vue.component('vue-simple-context-menu', VueSimpleContextMenu)

import VueLodash from 'vue-lodash';
Vue.use(VueLodash)

connect();

Vue.use(VModal, { dynamic: true });
Vue.use(Notifications);
Vue.use(VTooltip);

Vue.directive('click-outside', {
    bind(el, binding) {
        el.addEventListener('click', e => e.stopPropagation());
        document.body.addEventListener('click', binding.value);
    },
    unbind(el, binding) {
        //document.body.removeEventListener('click', binding.value);
    }
});

import fontawesome from '@fortawesome/fontawesome'
import brands from '@fortawesome/fontawesome-free-brands'
import { faSpinner } from '@fortawesome/fontawesome-free-solid'

fontawesome.library.add(brands, faSpinner );

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
