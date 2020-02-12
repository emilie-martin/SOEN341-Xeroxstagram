import React from "react";
import axios from "axios";
import loginService from "../services/LoginService";
import '../config'

export default function Login() {
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
                "dateOfBirth": new Date(event.target.dateOfBirth.value).toISOString()
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
                    loginService.setLoginToken(response.data);
                }
            ).catch(
                (error) => {
                    // todo: handle error
                    console.log(error);
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
                <button type="submit">
                    Register
                </button>
            </form>
        </div>
    );
}
