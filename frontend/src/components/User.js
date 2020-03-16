import React from "react";
import axios from "axios";
import Post from "./Post";
import { useState, useEffect} from "react";
import '../config';
import './User.scss';
import FollowingButton from "./FollowingButton";

export const User = props => {

    const [errorMsg, setErrorMsg] = useState("");
    const [Pictures, setPictures] = useState([]);

    useEffect(() => {
        const loadUser = () => {
            axios.get(global.config.BACKEND_URL + "/" + props.username + "/pictures").then(
                (response) => {
                    setErrorMsg("");
                    setPictures(response.data);
                }
            ).catch(
                (error) => {
                    setPictures([]);
                    if(error.response && error.response.data && error.response.data.message)
                    {
                        setErrorMsg(error.response.data.message);
                    } 
                    else
                    {
                        setErrorMsg("An unknown error occurred.");
                    }
                }
            )
        }

        //isUserFollowing();
        loadUser();
      }, [props.username])

    return (
        <div className="user-component">
            <FollowingButton {... props} username={props.username}></FollowingButton>
            {errorMsg && <div className="error">{errorMsg}</div>}
            {
                Pictures.map((id) => (
                    <div className="single-post" key={id}>
                        <Post id={id}/>
                        <br/>
                    </div>
                ))
            }
        </div>
    );
};

export default User;
