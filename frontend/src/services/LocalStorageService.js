const LocalStorageService = (function(){
    return {
        setToken: function(token) {
            localStorage.setItem('access_token', token['access_token']);
            localStorage.setItem('refresh_token', token['refresh_token']);
        },
        getAccessToken: function() {
            return localStorage.getItem('access_token');
        },
        getRefreshToken: function() {
            return localStorage.getItem('refresh_token');
        },
        clearAllTokens: function() {
            localStorage.removeItem('access_token');
            localStorage.removeItem('refresh_token');
        }
    }
})();

export default LocalStorageService;