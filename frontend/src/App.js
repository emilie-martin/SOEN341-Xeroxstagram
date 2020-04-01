import "./config"
import axios from "axios";
import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Switch, Route, Link, withRouter, Redirect } from "react-router-dom";
import localStorageService from "./services/LocalStorageService";

import About from "./pages/About";
import EditProfile from "./components/EditProfile/EditProfile";
import Feed from "./components/Feed/Feed";
import Login from "./components/Login/Login";
import Post from "./components/Post/Post";
import PostPicture from "./components/Post/PostPicture";
import Register from "./components/Register/Register"
import User from "./components/User/User";
import Welcome from "./components/Welcome/Welcome"

import "./App.scss";

export const App = () => {
	
	const [username, setUsername] = useState();
	const [currentUser, setCurrentUser] = useState(undefined);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		if (localStorageService.getAccessToken()) {
			localStorageService.setBearerToken();
		}
		setLoggedInState();
	}, [loading])

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
				return axios.post(global.config.BACKEND_URL + "/account/refresh", { "token": localStorageService.getRefreshToken() })
					.then((response) => {
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
	
	const setLoggedInState = () => {
		axios.get(global.config.BACKEND_URL + "/account")
			.then((response) => { setCurrentUser(response.data.username); setLoading(false) })
			.catch(() => { setCurrentUser(null) })
	}

	const login = (response) => {
		setTokensAndLogin(response);
		setLoggedInState();
	}

	const logout = () => {
		localStorageService.clearAllTokens();
		delete axios.defaults.headers.common.Authorization;
		setCurrentUser(null);
	}

	const handleChangeUser = (e) => {
		setUsername(e.target.value);
	}
	return (
		<div className="App">
			<Router>
				<div className="nav-bar">
					<Link to="/" id="app-name">
						<div className="icon">
							<svg className="bi bi-camera" width="1.5em" height="1.5em" viewBox="0 0 20 20" fill="currentColor">
								<path d="M11 7c-1.657 0-4 1.343-4 3a4 4 0 014-4v1z" />
								<path fillRule="evenodd" d="M16.333 5h-2.015A5.97 5.97 0 0011 4a5.972 5.972 0 00-3.318 1H3.667C2.747 5 2 5.746 2 6.667v6.666C2 14.253 2.746 15 3.667 15h4.015c.95.632 2.091 1 3.318 1a5.973 5.973 0 003.318-1h2.015c.92 0 1.667-.746 1.667-1.667V6.667C18 5.747 17.254 5 16.333 5zM3.5 7a.5.5 0 100-1 .5.5 0 000 1zm7.5 8a5 5 0 100-10 5 5 0 000 10z" clipRule="evenodd" />
								<path d="M4 5a1 1 0 011-1h1a1 1 0 010 2H5a1 1 0 01-1-1z" />
							</svg>
						</div>
						Instagram++
					</Link>

					<div className="search-bar">
						<div className="icon">
							<svg className="bi bi-search" width="1.2em" height="1.2em" viewBox="0 0 20 20" fill="currentColor">
								<path fillRule="evenodd" d="M12.442 12.442a1 1 0 011.415 0l3.85 3.85a1 1 0 01-1.414 1.415l-3.85-3.85a1 1 0 010-1.415z" clipRule="evenodd" />
								<path fillRule="evenodd" d="M8.5 14a5.5 5.5 0 100-11 5.5 5.5 0 000 11zM15 8.5a6.5 6.5 0 11-13 0 6.5 6.5 0 0113 0z" clipRule="evenodd" />
							</svg>
						</div>
						<input type="text" value={username} placeholder="Search for user..." onChange={(e) => handleChangeUser(e)} />
						<Link to={`/account/${username}`}
							style={{ border: "1px solid silver", backgroundColor: "black", marginLeft: "5px" }}
						>
							Search
						</Link>
					</div>

					<div className="nav-links">
						<Link to="/">
							<div className="icon">
								<svg className="bi bi-house" width="1.2em" height="1.2em" viewBox="0 0 20 20" fill="currentColor">
									<path fillRule="evenodd" d="M9.646 3.146a.5.5 0 01.708 0l6 6a.5.5 0 01.146.354v7a.5.5 0 01-.5.5h-4.5a.5.5 0 01-.5-.5v-4H9v4a.5.5 0 01-.5.5H4a.5.5 0 01-.5-.5v-7a.5.5 0 01.146-.354l6-6zM4.5 9.707V16H8v-4a.5.5 0 01.5-.5h3a.5.5 0 01.5.5v4h3.5V9.707l-5.5-5.5-5.5 5.5z" clipRule="evenodd" />
								</svg>
							</div>
							Home
						</Link>
						<Link to="/about">
							<div className="icon">
								<svg className="bi bi-question" width="1.2em" height="1.2em" viewBox="0 0 20 20" fill="currentColor">
									<path fillRule="evenodd" d="M10 17a7 7 0 100-14 7 7 0 000 14zm8-7a8 8 0 11-16 0 8 8 0 0116 0z" clipRule="evenodd" />
									<path d="M7.25 8.033h1.32c0-.781.458-1.384 1.36-1.384.685 0 1.313.343 1.313 1.168 0 .635-.374.927-.965 1.371-.673.489-1.206 1.06-1.168 1.987l.007.463h1.307v-.355c0-.718.273-.927 1.01-1.486.609-.463 1.244-.977 1.244-2.056 0-1.511-1.276-2.241-2.673-2.241-1.326 0-2.786.647-2.754 2.533zm1.562 5.516c0 .533.425.927 1.01.927.609 0 1.028-.394 1.028-.927 0-.552-.42-.94-1.029-.94-.584 0-1.009.388-1.009.94z" />
								</svg>
							</div>
							About
						</Link>
						{currentUser
							? <div className="registered-user-options">
									<div className="icon" style={{margin: "3px 0px 0px"}}>
										<Link to={`/account/${currentUser}`}>
											<svg className="bi bi-person" width="1.2em" height="1.2em" viewBox="0 0 20 20" fill="currentColor">
												<path fillRule="evenodd" d="M15 16s1 0 1-1-1-4-6-4-6 3-6 4 1 1 1 1h10zm-9.995-.944v-.002zM5.022 15h9.956a.274.274 0 00.014-.002l.008-.002c-.001-.246-.154-.986-.832-1.664C13.516 12.68 12.289 12 10 12c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664a1.05 1.05 0 00.022.004zm9.974.056v-.002zM10 9a2 2 0 100-4 2 2 0 000 4zm3-2a3 3 0 11-6 0 3 3 0 016 0z" clipRule="evenodd"/>
											</svg>
											{currentUser}
										</Link>
									</div>
									<Link to="/logout">
										<div className="icon">
											<svg className="bi bi-lock" width="1.2em" height="1.2em" viewBox="0 0 20 20" fill="currentColor">
												<path fillRule="evenodd" d="M13.655 9H6.333c-.264 0-.398.068-.471.121a.73.73 0 00-.224.296 1.626 1.626 0 00-.138.59V15c0 .342.076.531.14.635.064.106.151.18.256.237a1.122 1.122 0 00.436.127l.013.001h7.322c.264 0 .398-.068.471-.121a.73.73 0 00.224-.296 1.627 1.627 0 00.138-.59V10c0-.342-.076-.531-.14-.635a.658.658 0 00-.255-.237 1.123 1.123 0 00-.45-.128zm.012-1H6.333C4.5 8 4.5 10 4.5 10v5c0 2 1.833 2 1.833 2h7.334c1.833 0 1.833-2 1.833-2v-5c0-2-1.833-2-1.833-2zM6.5 5a3.5 3.5 0 117 0v3h-1V5a2.5 2.5 0 00-5 0v3h-1V5z" clipRule="evenodd"/>
											</svg>
										</div>
										Logout
									</Link>
								<Link to="/post">
									<div className="icon">
										<svg className="bi bi-upload" width="1.2em" height="1.2em" viewBox="0 0 20 20" fill="currentColor">
											<path fillRule="evenodd" d="M2.5 10a.5.5 0 01.5.5V14a1 1 0 001 1h12a1 1 0 001-1v-3.5a.5.5 0 011 0V14a2 2 0 01-2 2H4a2 2 0 01-2-2v-3.5a.5.5 0 01.5-.5zM7 6.854a.5.5 0 00.707 0L10 4.56l2.293 2.293A.5.5 0 1013 6.146L10.354 3.5a.5.5 0 00-.708 0L7 6.146a.5.5 0 000 .708z" clipRule="evenodd" />
											<path fillRule="evenodd" d="M10 4a.5.5 0 01.5.5v8a.5.5 0 01-1 0v-8A.5.5 0 0110 4z" clipRule="evenodd" />
										</svg>
									</div>
										Upload
									</Link>
							</div>
							: <div className="unregistered-user-options">
								<Link to="/login">
									<div className="icon">
										<svg className="bi bi-unlock" width="1.2em" height="1.2em" viewBox="0 0 20 20" fill="currentColor">
											<path fillRule="evenodd" d="M11.655 9H4.333c-.264 0-.398.068-.471.121a.73.73 0 00-.224.296 1.626 1.626 0 00-.138.59V15c0 .342.076.531.14.635.064.106.151.18.256.237a1.122 1.122 0 00.436.127l.013.001h7.322c.264 0 .398-.068.471-.121a.73.73 0 00.224-.296 1.627 1.627 0 00.138-.59V10c0-.342-.076-.531-.14-.635a.658.658 0 00-.255-.237 1.123 1.123 0 00-.45-.128zm.012-1H4.333C2.5 8 2.5 10 2.5 10v5c0 2 1.833 2 1.833 2h7.334c1.833 0 1.833-2 1.833-2v-5c0-2-1.833-2-1.833-2zM10.5 5a3.5 3.5 0 117 0v3h-1V5a2.5 2.5 0 00-5 0v3h-1V5z" clipRule="evenodd" />
										</svg>
									</div>
										Login
									</Link><br/>
									<Link to="/register">
										<div className="icon">
											<svg className="bi bi-pencil" width="1.2em" height="1.2em" viewBox="0 0 20 20" fill="currentColor">
												<path fillRule="evenodd" d="M13.293 3.293a1 1 0 011.414 0l2 2a1 1 0 010 1.414l-9 9a1 1 0 01-.39.242l-3 1a1 1 0 01-1.266-1.265l1-3a1 1 0 01.242-.391l9-9zM14 4l2 2-9 9-3 1 1-3 9-9z" clipRule="evenodd"/>
												<path fillRule="evenodd" d="M14.146 8.354l-2.5-2.5.708-.708 2.5 2.5-.708.708zM5 12v.5a.5.5 0 00.5.5H6v.5a.5.5 0 00.5.5H7v.5a.5.5 0 00.5.5H8v-1.5a.5.5 0 00-.5-.5H7v-.5a.5.5 0 00-.5-.5H5z" clipRule="evenodd"/>
											</svg>
										</div>
										Register
									</Link><br />
							</div>
						}
					</div>
				</div>
				<hr />
				<Switch>
					<Route exact path="/"
						   render={ () => currentUser
							   ? <Feed currentUser={currentUser}/>
							   : <Welcome/>
						   }
					/>

					<Route exact path="/about"
						render={() => <About />}
					/>

					<Route exact path="/register"
						render={(props) => currentUser
							? <Redirect to='/' />
							: <Register {...props} onSuccess={(r) => login(r)} />
						}
					/>
					<Route exact path="/login"
						render={
							(props) => currentUser
							? <Redirect to='/' />
							: <Login {...props} onSuccess={(r) => login(r)} />
						}
					/>
					<Route exact path="/logout"
						render={() => {
							logout();
							return <Redirect to='/' />;
						}} />
					<Route exact path="/post"
						render={(props) => { return currentUser ? <PostPicture {...props} /> : <Redirect to='/' />; }}
					/>
					<Route path="/post/:id" render={({ match }) => (<Post id={match.params.id} />)} />
					<Route path="/account/:username" render={({ match }) => (<User username={match.params.username} />)} />
					<Route exact path="/accounts/edit"
						render={() => {
							return loading ? 'loading' : (currentUser ? <EditProfile></EditProfile> : <Redirect to='/' />)
						}}
					/>
				</Switch>
			</Router>
		</div>
	);
}
export default withRouter(App);
