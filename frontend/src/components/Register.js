import React from "react";
import axios from "axios";
import { useState } from "react";
import '../config';

export const Register = (props) => {

    const [errorMsg, setErrorMessage] = useState("");

    // Submit registration form
    const submit = (event) => {
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
                    props.onSuccess(response);
                }
            ).catch(
                (e) => {
                    if (e.response && e.response.data) {
                        if (e.response.data.errors) {
                            setErrorMessage("Please fill the form correctly.");
                        } else {
                            setErrorMessage(e.response.data.message);
                        }
                    } else {
                        setErrorMessage("An unknown error occurred.");
                    }
                }
            )
    }

        return (
            <div className="register">
                <form onSubmit={submit}>
                    <label>Username</label>
                    <input name="username"/>
                    <br/>
                    <label>Password</label>
                    <input name="password" type="password"/>
                    <br/>
                    <label>Email</label>
                    <input name="email"/>
                    <br/>
                    <label>First name</label>
                    <input name="firstName"/>
                    <br/>
                    <label>Last name</label>
                    <input name="lastName"/>
                    <br/>
                    <label>Birth date</label>
                    <input name="dateOfBirth" type="date"/>
                    <br/>
                    {errorMsg && <div className="error">Error: {errorMsg}</div>}
                    <button type="submit">
                        Register
                    </button>
                </form>
            </div>
        );
};

export default Register;
