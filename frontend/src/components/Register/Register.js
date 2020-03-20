import '../../config';
import axios from "axios";
import React, { useState } from "react";

import './Register.scss'

export default function Register(props) {

    const [errorMsg, setErrorMessage] = useState("");
    
    const submitRegistrationForm = (event) => {
        event.preventDefault();
        event.persist();
        axios.post(global.config.BACKEND_URL + "/account/register",
        {
            "username": event.target.username.value,
            "password": event.target.password.value,
            "email": event.target.email.value,
            "firstName": event.target.firstName.value,
            "lastName": event.target.lastName.value,
            "dateOfBirth": event.target.dateOfBirth.value,
            "displayName":event.target.displayName.value
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
                if (e.response && e.response.data)
                {
                    if (e.response.data.errors)
                    {
                        console.log(e.response.data.errors)
                        setErrorMessage("One or many fields have been filled in incorrectly");
                    } 
                    else { setErrorMessage(e.response.data.message); }
                }
                else { setErrorMessage("An unknown error occurred."); }
            }
        )      
    }
    
    return (
        <div className="register">
            <div className="external">
                <div className="title"><h3>Register</h3></div>
                <form className="register-form" onSubmit={submitRegistrationForm}>
                    <div className="registration-form">
                        <div className = "user-names">
                            <div className="user-names-left">
                                <label>Username</label><br/>
                                <input name="username" type="text" placeholder="Username"/><br/>
                                <label>First Name</label><br/>
                                <input name="firstName" type="text" placeholder="First name"/>
                            </div>
                            <div className="user-names-right">
                                <label>Display name</label><br/>
                                <input name="displayName" type="text" placeholder="Display name"/><br/>
                                <label>Last Name</label><br/>
                                <input name="lastName" type="text" placeholder="Last name"/>
                            </div>
                        </div>
                        <br/>
                        <div className = "user-info">
                            <label>Email</label><br/>
                            <input name="email" type="email" placeholder="Email"/><br/>
                            <label>Password</label><br/>
                            <input name="password" type="password" placeholder="Password"/><br/>
                            <label>Birthday</label><br/>
                            <input name="dateOfBirth" type="date"/><br/>
                            {errorMsg && <div className="error">Error: {errorMsg}</div>}
                        </div>
                    </div>
                    <br/>
                    <div className = "register-button">
                        <button className="button" type="submit">
                            Register
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};
