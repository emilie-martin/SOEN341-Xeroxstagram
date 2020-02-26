import axios from "axios";
import localStorageService from "./services/LocalStorageService";
import React from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link, withRouter, Redirect
} from "react-router-dom";

import Post from "./components/Post";
import Login from "./components/Login";
import Register from "./components/Register"

import "./App.scss";
import "./config"
import User from "./components/User";
import PostPicture from "./components/PostPicture";

let setTokensAndLogin = (response) => {
    localStorageService.setToken(response.data);
    localStorageService.setBearerToken();
}

axios.interceptors.response.use(
    (response) => {
        // success, no need to refresh token
        return response;
    },
    (error) => {
        if (error.response && error.response.data && error.response.data.error === "invalid_token") {
            // our access token has become invalid
            const request = error.config;
            delete axios.defaults.headers.common.Authorization;
            delete request.headers.Authorization;
            if(!localStorageService.getRefreshToken()) {
                localStorageService.clearAllTokens();
                // retry request, but with no user logged in
                return axios(request);
            }
            // our token is expired
            return axios.post(global.config.BACKEND_URL+"/account/refresh",
                {
                    "token": localStorageService.getRefreshToken()
                })
                .then(
                    (response) => {
                        setTokensAndLogin(response);
                        return axios(request);
                    },
                    () => {
                        // re-authentication has failed; retry request, but with no user logged in
                        localStorageService.clearAllTokens();
                        return axios(request);
                    }
                )
        } else {
            // error from backend, but not because of invalid token
            return Promise.reject(error);
        }
    }
);

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            post_id: 0,
            username: "Enter username",
            currentUser: undefined
        }
    }

    componentDidMount() {
        if(localStorageService.getAccessToken()) {
            localStorageService.setBearerToken();
        }
        this.setLoggedInState();
    }

    setLoggedInState() {
        axios.get(global.config.BACKEND_URL + "/account").then(
            (response) => {
                this.setState({currentUser: response.data});
            }
        ).catch(
            () => {
                this.setState({currentUser: null})
            }
        )
    }

    login(response) {
        setTokensAndLogin(response);
        this.setLoggedInState();
    }

    logout() {
        localStorageService.clearAllTokens();
        delete axios.defaults.headers.common.Authorization;
        this.setLoggedInState();
    }

    handleChangePostId = (e) => {
        this.setState({post_id : e.target.value});
    }

    handleChangeUser = (e) => {
        this.setState({username : e.target.value});
    }

    render() {
        return (
            <div className="App">
                <header className="App-header">
                    <h1>Instagram++</h1>
                </header>
                <Router>
                    <div>
                        {this.state.currentUser ?
                            <div>
                                <p>Logged in as: <Link to={`/account/${this.state.currentUser.username}`}>{this.state.currentUser.username}</Link></p>
                                <Link to="/logout">Logout</Link><br/>
                                <Link to="/post">Post Picture</Link><br/>
                            </div>
                            :
                            <div>
                                <Link to="/login">Login</Link><br/>
                                <Link to="/register">Register</Link><br/>
                            </div>
                        }

                        <Link to={`/post/${this.state.post_id}`}>Post #</Link>
                        <input value={this.state.post_id} onChange={this.handleChangePostId}/><br/>

                        <Link to={`/account/${this.state.username}`}>Search user</Link>
                        <input value={this.state.username} onChange={this.handleChangeUser}/>
                    </div>
                    <hr/>
                    <Switch>
                        <Route exact path="/register" render={(props) => this.state.currentUser ? <Redirect to='/'/> :
                            <Register {...props} onSuccess={this.login.bind(this)}/>
                        }/>
                        <Route exact path="/login" render={(props) => this.state.currentUser ? <Redirect to='/'/> :
                            <Login {...props} onSuccess={this.login.bind(this)}/>
                        }/>
                        <Route exact path="/logout" render={() => {
                            this.logout();
                            return <Redirect to='/'/>;
                        }}/>
                        <Route exact path="/post" render={(props) => {
                            return this.state.currentUser ? <PostPicture {...props}/> :  <Redirect to='/'/>;
                        }}/>
                        <Route path="/post/:id" render={({match}) => (<Post id={match.params.id}/>)}/>
                        <Route path="/account/:username" render={({match}) => (<User username={match.params.username}/>)}/>
                    </Switch>
                </Router>
            </div>
        );
    }
}
export default withRouter(App);