import React from "react";
import axios from "axios";
import loginService from "../services/LoginService";
import '../config'

export default function Login() {
  function submit(event) {
    event.preventDefault();
    axios.post("http://localhost:" + global.config.BACKEND_PORT+"/account/login",
        {
            "username": event.target.username.value,
            "password": event.target.password.value
        })
        .then(
            (response) => {
              loginService.setLoginToken(response.data);
            },
            (error) => {
              console.log(error);
            }
        )
  }

  return(
      <div className="login">
        <form onSubmit={submit}>
          <label>Username</label>
          <input name="username"></input>
          <br/>
          <label>Password</label>
          <input name="password" type="password"></input>
          <br/>
          <button type="submit">
            Login
          </button>
        </form>
      </div>
  );
}
