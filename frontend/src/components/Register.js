import React from "react";
import axios from "axios";
import { useState} from "react";
import '../config';
import './Register.scss'

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
                <form className="register-form" onSubmit={submit}>
                    <div className="registration-form">
                        <div className = "user-names">
                            <div className="user-names-left">
                                <label>Username</label>
                                <br/>
                                <input name="username"/>
                                <br/>
                                <label>First Name</label>
                                <br/>
                                <input name="firstName"/>
                            </div>
                            <div className="user-names-right">
                                <label>Display name</label>
                                <br/>
                                <input name="displayName"/>
                                <br/>
                                <label>Last Name</label>
                                <br/>
                                <input name="lastName"/>
                            </div>
                        </div>
                        <div className = "user-info">
                            <label>Email</label>
                            <br/>
                            <input name="email"/>
                            <br/>
                            <label>Password</label>
                            <br/>
                            <input name="password" type="password"/>
                            <br/>
                            <label>Birthday</label>
                            <br/>
                            <input name="dateOfBirth" type="date"/>
                            <br/>
                            {errorMsg && <div className="error">Error: {errorMsg}</div>}
                        </div> 
                    </div>
                    <button className ="button" type="submit">
                        Register
                    </button>
                </form>
            </div>
        );
};

export default Register;
