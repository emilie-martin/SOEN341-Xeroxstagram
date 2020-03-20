import '../../config';
import axios from "axios";
import React, { useState, useEffect } from "react";
import Post from "../Post/Post";
import Profile from "../Profile/Profile";
import FollowingButton from "../Following/FollowingButton";

import './User.scss';

export default function User(props) {

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
                    if (error.response && error.response.data && error.response.data.message) {
                        setErrorMsg(error.response.data.message);
                    }
                    else {
                        setErrorMsg("An unknown error occurred.");
                    }
                }
            )
        }
        loadUser();
    }, [props.username])

    return (
        <div className="user-component">
            <div className="profile-wrapper">
                <Profile username={props.username}></Profile>
            </div>
            {!(props.currentUser === props.username) &&
                <FollowingButton {...props} class='following-user'></FollowingButton>
            }
            {errorMsg && <div className="error">{errorMsg}</div>}
            {
                Pictures.map((id) => (
                    <div className="single-post" key={id}>
                        <Post currentUser={props.currentUser} id={id} />
                        <br />
                    </div>
                ))
            }
        </div>
    );
};
