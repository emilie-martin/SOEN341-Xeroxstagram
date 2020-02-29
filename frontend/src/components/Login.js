import React from "react";
import axios from "axios";
import { useState } from "react"; 
import '../config'
import './Login.scss'

export const Login = (props) => {

    const [errorMsg, setErrorMsg] = useState("");

    const submit = (event) => {
        event.preventDefault();
        axios.post(global.config.BACKEND_URL + "/account/login",
            {
                "username": event.target.username.value,
                "password": event.target.password.value
            })
            .then(
                (response) => {
                    props.onSuccess(response);
                },
                (error) => {
                    if (error.response)
                    {
                        setErrorMsg("Invalid credentials");
                    } 
                    else
                    {
                        setErrorMsg("An unknown error occurred");
                    }
                }
            )
    };

    return (
        <div className="login">
            <form onSubmit={submit}>
                <div className="login-username">
                    <label>Username</label>
                    <br/>
                    <input name="username" placeholder="Enter username" className="login-field"/>
                </div>
                <div className="login-password">
                    <label>Password</label>
                    <br/>
                    <input name="password" type="password" placeholder="Enter password" className="login-field"/>
                </div>
                <button className="button" type="submit">Login</button>
                {errorMsg && <div className="error">{errorMsg}</div>}
            </form>
        </div>
    );
}

export default Login;