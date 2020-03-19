import React from "react";
import axios from "axios";
import { useState } from "react"; 
import '../../config'
import './Login.scss'

export default function Login(props) {

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
                    <input className="login-field" name="username" type="text" placeholder="Enter username"/>
                </div>
                <div className="login-password">
                    <label>Password</label>
                    <br/>
                    <input className="login-field" name="password" type="password" placeholder="Enter password"/>
                </div>
                <button className="button" type="submit">Login</button>
                {errorMsg && <div className="error">{errorMsg}</div>}
            </form>
        </div>
    );
}
