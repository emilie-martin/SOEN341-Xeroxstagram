import React, {useState} from "react";
import axios from "axios";
import localStorageService from "../services/LocalStorageService";
import '../config'
import { useHistory } from "react-router-dom";

export default function Login() {
    let history = useHistory();
    const [error, setError] = useState({msg: ""});

    function submit(event) {
        event.preventDefault();
        axios.post(global.config.BACKEND_URL + "/account/login",
            {
                "username": event.target.username.value,
                "password": event.target.password.value
            })
            .then(
                (response) => {
                    localStorageService.setToken(response.data);
                    localStorageService.setBearerToken();
                    history.push("/");
                    window.location.reload();
                },
                () => {
                    setError({msg: "Invalid credentials"});
                }
            )
    }

    return (
        <div className="login">
            <form onSubmit={submit}>
                <label>Username</label>
                <input name="username"/>
                <br/>
                <label>Password</label>
                <input name="password" type="password"/>
                <br/>
                {error.msg && <div className="error">Error: {error.msg}</div>}
                <button type="submit">
                    Login
                </button>
            </form>
        </div>
    );
}
