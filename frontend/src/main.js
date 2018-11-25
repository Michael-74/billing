import Vue from 'vue'
import App from './App.vue'
import router from './router'

import VModal from 'vue-js-modal'
import Notifications from 'vue-notification'
import VTooltip from 'v-tooltip'

Vue.use(VModal, { dynamic: true });
Vue.use(Notifications)
Vue.use(VTooltip)

import fontawesome from '@fortawesome/fontawesome'
import brands from '@fortawesome/fontawesome-free-brands'
import { faSpinner } from '@fortawesome/fontawesome-free-solid'

fontawesome.library.add(brands, faSpinner );

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router: router,
  render: h => h(App)
})
