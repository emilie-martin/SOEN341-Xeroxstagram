import React from "react";
import axios from "axios";
import '../config'

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            errorMsg: ""
        }
    }

    submit(event) {
        event.preventDefault();
        axios.post(global.config.BACKEND_URL + "/account/login",
            {
                "username": event.target.username.value,
                "password": event.target.password.value
            })
            .then(
                (response) => {
                    this.props.onSuccess(response);
                },
                (error) => {
                    if (error.response) {
                        this.setState({errorMsg: "Invalid credentials"});
                    } else {
                        this.setState({errorMsg: "An unknown error occurred."});
                    }
                }
            )
    }

    render() {
        return (
            <div className="login">
                <form onSubmit={this.submit.bind(this)}>
                    <label>Username</label>
                    <input name="username"/>
                    <br/>
                    <label>Password</label>
                    <input name="password" type="password"/>
                    <br/>
                    {this.state.errorMsg && <div className="error">Error: {this.state.errorMsg}</div>}
                    <button type="submit">
                        Login
                    </button>
                </form>
            </div>
        );
    }
}

export default Login;