import React from "react";
import axios from "axios";
import { useState } from "react"; 
import '../config'

export const Login = (props)=> {

    const [errorMsg, setErrorMsg] = useState("");

    const  submit = (event)=>{
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
                    <label>Username</label>
                    <input name="username" placeholder="Username"/>
                    <br/>
                    <label>Password</label>
                    <input name="password" type="password" placeholder="Password"/>
                    <br/>
                    {errorMsg && <div className="error">Error: {errorMsg}</div>}
                    <button type="submit">
                        Login
                    </button>
                </form>
            </div>
        );
}

export default Login;