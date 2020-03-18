import '../../config';
import React from "react";
import axios from "axios";
import { useState, useEffect} from "react";

// Project
import Post from "../Post/Post";
import FollowingButton from "../Following/FollowingButton";

// Stylesheets
import './User.scss';

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

        loadUser();
      }, [props.username])

    return (
        <div className="user-component">
            { !(props.currentUser === props.username) &&
            <FollowingButton {... props} currentUser={props.currentUser} username={props.username} class='following-user'></FollowingButton>
            }
            {errorMsg && <div className="error">{errorMsg}</div>}
            {
                Pictures.map((id) => (
                    <div className="single-post" key={id}>
                        <Post currentUser={props.currentUser} id={id}/>
                        <br/>
                    </div>
                ))
            }
        </div>
    );
};

export default User;
