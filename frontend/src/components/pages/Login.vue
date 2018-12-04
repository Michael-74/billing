<template>
    <div class="wrapper__login">
        <div class="login modal">
            <div class="wrapper__modal">
                <div class="modal__l-images modal_blocks">
                    <div class="modal__circle"></div>
                    <div class="modal__images">
                        <img src="../../assets/images/login/people.png" alt="logo-people" class="modal__images-left">
                    </div>
                </div>
                <div class="modal__r-auth modal_blocks">
                    <div class="wrapper__r-block">
                        <div class="modal__logo">
                            <img src="../../assets/images/login/logo.png" class="modal__logo-img" />
                        </div>
                        <div class="modal__title">
                            Вход в систему
                        </div>
                        <div class="modal__restore-password">
                            Если вы забыли пароль, вы можете его <a href="#">восстановить</a>
                        </div>
                        <div class="modal__auth auth">
                            <div class="auth__block">
                                <input type="text" class="auth__input auth__input_style" v-model="login" placeholder="Введите логин в системе">
                            </div>
                            <div class="auth__block">
                                <input type="text" class="auth__input auth__input_style" v-model="password" placeholder="Введите пароль">
                            </div>
                            <div class="auth__block auth__block_submit">
                                <button class="auth__submit auth__input_style" @click="sendForm">Войти в панель управления</button>
                            </div>
                        </div>
                        <div class="modal__reg modal__restore-password">
                            Являетесь сотрудником компании?<br>
                            <a href="#">Заполните форму</a>
                        </div>
                    </div>
                </div>
                <div class="modal__bottom-line"></div>
            </div>
            <div class="modal__q1"></div>
            <div class="modal__q2"></div>
        </div>
        <div class="background"></div>
    </div>
</template>

<script>
import '../../assets/scss/login.scss'
import axios from "axios";

export default {
    data () {
        return {
            login: 'admin',
            password: '123'
        }
    },
    methods: {
        auth: function(data) {
            axios
                .post('/auth/v1/login', data, {
                    headers:{
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    }
                })
                .then(response => {
                    if (response.status === 200) {

                        console.log("status == 200", response);
                        this.$store.commit('changeAuth', {token: response.data.token, username: response.data.user});
                        localStorage.setItem('JWT', response.data.token);
                        localStorage.setItem('username', response.data.user);
                        this.$router.push('/admin')
                    } else {
                        console.log("status != 200:", response)
                    }
                })
                .catch(e => {
                    console.log("error", e);
                    this.$notify({
                        group: 'notify',
                        type: 'error',
                        text: 'Логин или пароль не совпадают'
                    });
                });
        },
        sendForm: function (e) {
            const data = {
                'username': this.login,
                'password': this.password
            };
            this.auth(data);

            e.preventDefault();
        }
    }
}
</script>

<style lang="scss" scoped>
.background{
    height: 100%;
    width: 100%;
    background: url('../../assets/images/login/b_login_common.png') no-repeat bottom left;
    z-index: 1;
    position:fixed;
}
.background::after{
    content: "";
    position: absolute;
    top:0;
    left:0;
    height: 100%;
    width:100%;
    background: #000;
    opacity: 0.1;
    z-index:0;
}
.wrapper__modal {
    overflow: hidden;
    padding: 20px;
    border-radius: 10px;
}
.modal {
    width: 1240px;
    background: #FFF;
    border-radius: 10px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    z-index: 2;
    box-shadow: 10px 30px 70px 0px rgba(0,0,0,0.2);
}
.modal_blocks{
    width: 49%;
}
.modal__l-images {
    float: left;
    position: relative;
}
.modal__r-auth {
    margin-left: 51%;
    padding: 0px 60px;
}
.wrapper__r-block{
    width: 290px;
}
.modal__circle{
    background: #fbf9f9;
    position: absolute;
    top:-15%;
    left:-36%;
    height: 800px;
    width:800px;
    border-radius: 50%;
    z-index: 1;
}
.modal__images{
    z-index: 2;
    position: relative;
    top: 30px;
    left: 20px;
}
.modal__images-left {
    width: 113%;
    height: auto;
    font-size: 0px;
}
.modal__logo-img {
    width: 160px;
    padding: 50px 0 20px;
}
.modal__title {
    color: #2b4761;
    font-weight: bold;
    font-size: 30px;
}
.modal__restore-password {
    color: #777777;
    font-size: 15px;
    padding: 10px 0;
}
.auth {
    padding: 20px 0 30px;
}
.auth__block {
    margin-bottom: 10px;
}
.auth__input {
    font-weight: bold;
    font-size: 14px;
}
.auth__input_style {
    width: 100%;
    padding: 20px;
    border: 0px;
    border-radius: 4px;
    background: #f3f3f3;
}
.auth__block_submit {
    margin-top: 30px;
}
.auth__submit {
    font-weight: bold;
    text-transform: uppercase;
    background: #404aff;
    color: #FFF;
    cursor: pointer;
}
.auth__submit:hover {
    background: #333bbe;
}
.auth__submit:active {
    box-shadow: inset 1px 2px 5px rgba(0,0,0,0.3);
}
.modal__reg {
    margin-top: 50px;
    padding-bottom: 20px;
}
.modal__bottom-line {
    position: absolute;
    left:0;
    bottom: 90px;
    border: 1px solid #f5f5f5;
    width: 100%;
    z-index: 1;
}
.modal__q1 {
    background: url('../../assets/images/login/q1.png') no-repeat center center;
    position: fixed;
    z-index: 1;
    width: 260px;
    height: 150px;
    top: -105px;
    right: -140px;
}
.modal__q2 {
    background: url('../../assets/images/login/q2.png') no-repeat center center;
    position: absolute;
    z-index: 0;
    width: 260px;
    height: 150px;
    top: -75px;
    left: -130px;
}
</style>
