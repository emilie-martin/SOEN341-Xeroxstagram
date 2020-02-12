import axios from "axios";
import localStorageService from "./services/LocalStorageService";
import loginService from "./services/LoginService";
import React from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

import Post from "./components/Post";
import Login from "./components/Login";

import "./App.scss";
import "./config"

axios.interceptors.response.use(
    (response) => {
        // success, no need to refresh token
        return response;
    },
    (error) => {
        if (error.error === "invalid_token") {
            // our access token has become invalid
            const request = error.config;
            delete axios.defaults.headers.common.Authorization;
            if(!localStorageService.getRefreshToken()) {
                localStorageService.clearAllTokens();
                // retry request, but with no user logged in
                return axios(request);
            }
            // our token is expired
            return axios.post("http://localhost:"+global.config.BACKEND_PORT+"/account/refresh",
                {
                    "token": localStorageService.getRefreshToken()
                })
                .then(
                    (response) => {
                        loginService.setLoginToken(response.data);
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
            id: 0
        }
    }
    handleChange = (e) => {
        this.setState({id : e.target.value});
    }
    render() {
      return (
          <div className="App">
              <header className="App-header">
                  <h1>Instagram++</h1>
              </header>
              <Router>
                  <div>
                      <Link to="/register">Register</Link><br/>
                      <Link to="/login">Login</Link><br/>
                      <Link to={`/post/${this.state.id}`}>Post #</Link><input value={this.state.id} onChange={this.handleChange}/>
                  </div>
                  <hr/>
                  <Switch>
                      <Route exact path="/register">

                      </Route>
                      <Route exact path="/login">
                          <Login/>
                      </Route>
                      <Route path="/post/:id" component={Post}/>
                  </Switch>
              </Router>
          </div>
      );
    }
}
export default App;