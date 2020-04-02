import "../../config";
import axios from "axios";
import { Link, useHistory } from "react-router-dom";
import React, { useState, useEffect } from "react";

import PostImage from "../Post/PostImage";
import Profile from "../Profile/Profile";

import "./User.scss";

export default function User(props) {

    const [Pictures, setPictures] = useState([]);
    const history = useHistory();
    useEffect(() => {
        const loadUser = () => {
            axios.get(global.config.BACKEND_URL + "/" + props.username + "/pictures")
                .then(
                    (response) => {
                        setPictures(response.data.reverse());
                    }
                )
                .catch(
                    (error) => {
                        if (error.response.data.error === "Not Found") {
                            alert("The user doesn't exist, redirecting to home");
                        } else {
                            alert("unexpected error, redirecting to home");
                        }
                        history.push("/");
                    }
                )
        }
        loadUser();
    }, [props.username, history])

    return (
        <div className="user-component">
            <Profile currentUser={props.currentUser} username={props.username} />
            <div className="all-posts">
                {
                    Pictures.map((id) => (
                        <div className="single-post" key={id}>
                            <Link to={`/post/${id}`}>
                                <PostImage pictureId={id} />
                            </Link>
                        </div>
                    ))
                }
            </div>
        </div>
    );
}
