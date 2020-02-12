import axios from "axios";

const LoginService = (function(){
    return {
        setLoginToken: function(token) {
            localStorage.setItem('access_token', token['access_token']);
            localStorage.setItem('refresh_token', token['refresh_token']);
            axios.defaults.headers.common['Authorization'] =
                "Bearer " + token['access_token'];
        }
    }
})();

export default LoginService;