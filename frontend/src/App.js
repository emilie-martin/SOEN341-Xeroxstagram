import axios from "axios";
import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Switch, Route, Link, withRouter, Redirect } from "react-router-dom";
import localStorageService from "./services/LocalStorageService";

import About from "./pages/About";
import Post from "./components/Post";
import PostPicture from "./components/PostPicture";
import Login from "./components/Login";
import Register from "./components/Register"
import User from "./components/User";

import "./App.scss";
import "./config"

const setTokensAndLogin = (response) => {
	localStorageService.setToken(response.data);
	localStorageService.setBearerToken();
}

axios.interceptors.response.use(
	(response) => {
		return response; // no need to refresh token if successful
	},
	(error) => {
		if (error.response && error.response.data && error.response.data.error === "invalid_token") {
			const request = error.config;
			delete axios.defaults.headers.common.Authorization;
			delete request.headers.Authorization;

			// try request without user logged in
			if (!localStorageService.getRefreshToken()) {
				localStorageService.clearAllTokens();
				return axios(request);
			}
			
			// expired token
			return axios.post(global.config.BACKEND_URL + "/account/refresh", {"token": localStorageService.getRefreshToken()})
				.then( (response) => {
						setTokensAndLogin(response);
						return axios(request);
					},
					() => {
						// re-authentication  failed; retry request with no user logged in
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

export const App = () => {
	const [username, setUsername] = useState("");
	const [currentUser, setCurrentUser] = useState();

	useEffect(() => {
		if (localStorageService.getAccessToken()) {
			localStorageService.setBearerToken();
		}
		setLoggedInState();
	}, [])

	const setLoggedInState = () => {
		axios.get(global.config.BACKEND_URL + "/account").then (
			(response) => { 
				setCurrentUser(response.data)
			}).catch(
			() => { 
				setCurrentUser(null)
			}
		)
	}

	const login = (response) => {
		setTokensAndLogin(response);
		setLoggedInState();
	}

	const logout = () => {
		localStorageService.clearAllTokens();
		delete axios.defaults.headers.common.Authorization;
		setLoggedInState();
	}

	const handleChangeUser = (e) => {
		setUsername(e.target.value)
	}

	return (
		<div className="App">
			<header className="App-header">
				<h1>Instagram++</h1>
			</header>
			<Router>
				<div className="navBar">

					<div className="navLinks">
						<Link to="/">Home</Link>
						<Link to="/about">About</Link>
					</div>

					<Link to={`/account/${username}`}>
						<input value={username} placeholder="Search..." onChange={(e) => handleChangeUser(e)} />
					</Link>

					{ currentUser ?
						<div className="registered-user-options">
							<p>Logged in as: <Link to={`/account/${username}`}>{username}</Link></p>
							<Link to="/logout">Logout</Link>
							<Link to="/post">Post Picture</Link>
						</div>
						:
						<div className="unregistered-user-options">
							<Link to="/login">Login</Link><br />
							<Link to="/register">Register</Link><br />
						</div>
					}

				</div>
					<hr />
				<Switch>
					<Route exact path="/about"
						render={ () => <About/>}
					/>

					<Route exact path="/register"
						render={ (props) => currentUser ? <Redirect to='/' /> : <Register {...props} onSuccess={(r) => login(r)} /> }
					/>
					<Route exact path="/login"
						render={ (props) => currentUser ? <Redirect to='/' /> : <Login {...props} onSuccess={(r) => login(r)} /> }
					/>
					<Route exact path="/logout"
						render={() => { logout();
						return <Redirect to='/' />;
					}} />
					<Route exact path="/post"
						render={(props) => { return currentUser ? <PostPicture {...props} /> : <Redirect to='/' />; }}
					/>
					<Route path="/post/:id" render={({ match }) => (<Post id={match.params.id} />)} />
					<Route path="/account/:username" render={({ match }) => (<User username={match.params.username} />)} />
				</Switch>
			</Router>
		</div>
	);
}
export default withRouter(App);
