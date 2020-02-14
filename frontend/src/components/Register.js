import React from "react";
import axios from "axios";
import '../config';

class Register extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            errorMsg: ""
        };
    }

    submit(event) {
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
                    this.props.onSuccess(response);
                }
            ).catch(
                (e) => {
                    if (e.response && e.response.data) {
                        if (e.response.data.errors) {
                            this.setState({errorMsg: "Please fill the form correctly."});
                        } else {
                            this.setState({errorMsg: e.response.data.message});
                        }
                    } else {
                        this.setState({errorMsg: "An unknown error occurred."});
                    }
                }
            )
    }

    render() {
        return (
            <div className="register">
                <form onSubmit={this.submit.bind(this)}>
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
                    {this.state.errorMsg && <div className="error">Error: {this.state.errorMsg}</div>}
                    <button type="submit">
                        Register
                    </button>
                </form>
            </div>
        );
    }
}

export default Register;
