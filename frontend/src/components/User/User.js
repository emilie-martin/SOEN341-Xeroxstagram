import "../../config";
import axios from "axios";
import { Link } from "react-router-dom";
import React, { useState, useEffect } from "react";

import PostImage from "../Post/PostImage";
import Profile from "../Profile/Profile";

import "./User.scss";

export default function User(props) {

    const [Pictures, setPictures] = useState([]);

    useEffect(() => {
        const loadUser = () => {
            axios.get(global.config.BACKEND_URL + "/" + props.username + "/pictures")
            .then(
                (response) => {
                    setPictures(response.data.reverse());
                }
            )
            .catch(
                () => {
                    setPictures([]);
                }
            )
        }
        loadUser();
    }, [props.username])

    return (
        <div className="user-component">
            <Profile username={props.username}/>
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
