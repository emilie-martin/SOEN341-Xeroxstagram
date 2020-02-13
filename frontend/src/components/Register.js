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
        event.persist();
        axios.post(global.config.BACKEND_URL + "/account/register",
            {
                "username": event.target.username.value,
                "password": event.target.password.value,
                "email": event.target.email.value,
                "firstName": event.target.firstName.value,
                "lastName": event.target.lastName.value,
                "dateOfBirth": event.target.dateOfBirth.value
            })
            .then(
                () => {
                    return axios.post(global.config.BACKEND_URL + "/account/login",
                    {
                        "username": event.target.username.value,
                        "password": event.target.password.value
                    })
                }
            ).then(
                (response) => {
                    localStorageService.setToken(response.data);
                    localStorageService.setBearerToken();
                    history.push("/");
                    window.location.reload();
                }
            ).catch(
                (e) => {
                    if(e.response.data.errors) {
                        setError({msg: "Please fill the form correctly."});
                    } else {
                        setError({msg: e.response.data.message});
                    }
                }
            )
    }

    return(
        <div className="register">
            <form onSubmit={submit}>
                <label>Username</label>
                <input name="username"></input>
                <br/>
                <label>Password</label>
                <input name="password" type="password"></input>
                <br/>
                <label>Email</label>
                <input name="email"></input>
                <br/>
                <label>First name</label>
                <input name="firstName"></input>
                <br/>
                <label>Last name</label>
                <input name="lastName"></input>
                <br/>
                <label>Birth date</label>
                <input name="dateOfBirth" type="date"></input>
                <br/>
                {error.msg && <div className="error">Error: {error.msg}</div>}
                <button type="submit">
                    Register
                </button>
            </form>
        </div>
    );
}
