import React from "react";
import axios from "axios";
import localStorageService from "../services/LocalStorageService";
import '../config'

export default function Login() {
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
